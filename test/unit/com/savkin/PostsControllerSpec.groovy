package com.savkin

import grails.test.*
import grails.plugin.spock.*

@Mixin([TestUtils, ControllerTestUtils])
class PostsControllerSpec extends ControllerSpec {
	
	void 'should return current user and his last 10 posts'() {
		setup:
		def posts = bunchOfPosts()
		def user = Mock(User)
		asCurrentUser user
		
		when:
		makeRequest 'index'	
		
		then:
		1 * user.lastPosts(10) >> posts
		controller.user == user
		controller.posts == posts
		renderArgs.view == 'posts'
	}
	
	def 'should return current user and all posts from specified interval'() {		
		setup:
		def posts = bunchOfPosts()
		def user = Mock(User)
		asCurrentUser user

		when:
		makeRequest 'between', from: today, to: today + 2
		
		then:
		1 * user.postsBetween(today, today + 2) >> posts
		controller.user == user
		controller.posts == posts
		renderArgs.view == 'posts'
		controller.from == today
		controller.to == today + 2
	}
}
