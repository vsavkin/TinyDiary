package com.savkin
import grails.plugins.springsecurity.Secured

@Secured('IS_AUTHENTICATED_REMEMBERED')
class PostsController {
	AuthService authService
	User user
	List posts
	Date from
	Date to
	
	def index = { 
		user = authService.currentUser()
		posts = user.lastPosts(10)
		render view: 'posts'
	}
	
	def between = {
		user = authService.currentUser()
		posts = user.postsBetween(from = params.from, to = params.to)
		render view: 'posts'
	}
}
