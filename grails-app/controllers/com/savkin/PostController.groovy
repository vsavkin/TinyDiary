package com.savkin

import com.savkin.parser.*
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PostController {
	AuthService authService
	
	def show = {
		def post = authService.currentUser().postById(params.id.toLong())
		modelForShowing post
	}

	def prevPost = {
		def post = authService.currentUser().prevPost(params.id.toLong())
		render view: 'show', model: [post: post]
	}

	def nextPost = {
		def post = authService.currentUser().nextPost(params.id.toLong())
		render view: 'show', model: [post: post]
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

	private modelForShowing(post){
		def user = authService.currentUser()
		def hasNext = user.nextPost(post.id) != null
		def hasPrev = user.prevPost(post.id) != null
		[post: post, hasNext: hasNext, hasPrev: hasPrev]
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