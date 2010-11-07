package com.savkin

import grails.test.*

class DomainIntegrationTests extends GrailsUnitTestCase {	
	
	void testShouldCreatePostWithSeveralPostTypes() {
		def postPartType = new PostPartType(name:"work")
		postPartType.save()
		
		def user = new User(username: 'John', password: 'password', enabled: true).save()		
		def post = new Post()
		def postPart = new PostPart(text: 'text', type: postPartType)
		post.addToParts(postPart)
		user.addToPosts(post)
		user.save(flush: true)
		
		assertEquals 1, user.posts.size()
		assertEquals 1, user.posts.toList()[0].parts.size()
		assertEquals 1, Post.count()
		assertEquals 1, PostPartType.count()
		
		user.delete()
		
		assertEquals 0, User.count()
		assertEquals 0, Post.count()
		assertEquals 1, PostPartType.count()
	}
}
