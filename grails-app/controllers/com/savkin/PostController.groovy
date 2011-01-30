package com.savkin

import com.savkin.parser.*
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PostController {
	AuthService authService
	
	def show = {
		def user = authService.currentUser()
		def post = user.postById(params.id.toLong())
		def hasNext = user.nextPost(post.id) != null
		def hasPrev = user.prevPost(post.id) != null
		render view: 'show', model: [post: post, hasNext: hasNext, hasPrev: hasPrev]
	}

	def prevPost = {
		def post = authService.currentUser().prevPost(params.id.toLong())
		redirect action: 'show', id: post.id
	}

	def nextPost = {
		def post = authService.currentUser().nextPost(params.id.toLong())
		redirect action: 'show', id: post.id
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
		[post: authService.currentUser().postById(params.id.toLong())]
	}
	
	def update = {SavePostCommand epc->
		if(epc.hasErrors()){
			render view: 'edit', model: [post: epc]
		}else{
			saveTypes epc.types
			
			def post = Post.get(epc.id)
			post.updateContent epc.postParts
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
	PostParser parser = new PostParser()
	
	def getTypes(){
		parser.parse(text).type
	}
	
	def getPostParts(){
		def result = parser.parse(text)
		result.collect{
			def type = PostPartType.findByName(it.type)
			new PostPart(type: type, text: it.text)
		}
	}
	
	static constraints = {
		text(blank: false)
	}
}