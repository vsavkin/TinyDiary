package com.savkin

import grails.test.*

@Mixin([TestUtils, ControllerTestUtils])
class PostsControllerTests extends ControllerUnitTestCase {
	
	void testShouldReturnCurrentUserAnd10LastPosts() {
		def posts = bunchOfPosts()
		def user = mock(User){
			lastPosts(1..1){int count->
				assert count == 10; posts
			}
		}
		asCurrentUser user
		makeRequest 'index'	
		assertEquals user, controller.user
		assertEquals posts, controller.posts
		assertEquals 'posts', renderArgs.view
	}
	
	void testShouldReturnCurrentUserAndAllPostsFromSpecifiedInterval() {		
		def posts = bunchOfPosts()
		def user = mock(User){
			postsBetween(1..1){Date from, Date to->
				assert from == today
				assert to == today + 2
				posts
			}
		}
		asCurrentUser user
		makeRequest 'between', from: today, to: today + 2
		assertEquals user, controller.user
		assertEquals posts, controller.posts
		assertEquals 'posts', renderArgs.view
		assertEquals today, controller.from
		assertEquals today + 2, controller.to
	}
}
