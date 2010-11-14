package com.savkin

import com.savkin.parser.*
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PostController {
	AuthService authService
	
	def show = {
		[post: Post.get(params.id)]
	}
	
	def create = {
	}
	
	def save = {SavePostCommand epc->
		if(epc.hasErrors()){
			render view: 'create', model: [post: epc]
		}else{
			saveTypes epc.types
			
			def user = authService.currentUser()
			def post = newPost(epc.postParts)
			user.addToPosts post
			user.save(flush: true)
			
			flash.message = 'New Post Is Created!'
			redirect action: 'show', id: post.id
		}
	}
	
	def edit = {
		[post: Post.get(params.id)]
	}
	
	def update = {SavePostCommand epc->
		if(epc.hasErrors()){
			render view: 'edit', model: [post: epc]
		}else{
			saveTypes epc.types
			
			def post = Post.get(epc.id)
			post.deleteAllParts()
			epc.postParts.each {post.addToParts it}
			post.save()

			flash.message = 'Post Successfuly Updated!'
			redirect action: 'show', id: post.id
		}
	}
	
	private newPost(postParts){
		def post = new Post()
		postParts.each{post.addToParts it}
		post
	}
	
	private saveTypes(types){
		types.each{
			new PostPartType(name: it).save()
		}
	}
}

class SavePostCommand {
	long id
	Date dateCreated
	String text
	
	def getTypes(){
		PostParser.parseText(text).type
	}
	
	def getPostParts(){
		def result = PostParser.parseText(text)
		result.collect{
			def type = PostPartType.findByName(it.type)
			new PostPart(type: type, text: it.text)
		}
	}
	
	static constraints = {
		text(blank: false)
	}
}