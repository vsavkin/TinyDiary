package com.savkin
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PostsController {
	AuthService authService

	def index = {
		def user = authService.currentUser()
		session.posts = user.lastPosts(10)
		session.types = extractAllTypes()
		render view: 'posts', model: [user: user, posts: session.posts, types: session.types]
	}

	def between = {
		def user = authService.currentUser()
		def (from, to) = [params.from, params.to]
		session.posts = user.postsBetween(from, to)
		session.types = extractAllTypes()
		render view: 'posts', model: [user: user, posts: session.posts, types: session.types, from: from, to: to]
	}

	def showAllParts = {
		render template: 'shortPost', model: [posts: session.posts]
	}

	def showParts = {
		def selectedType = PostPartType.findByName(params.selectedType)
		def posts = session.posts.findAll {it.hasPartType selectedType}
		render template: 'shortPost', model: [posts: posts, selectedType: selectedType]
	}

	private extractAllTypes(){
		session.posts.parts.flatten().type.unique()
	}
}
