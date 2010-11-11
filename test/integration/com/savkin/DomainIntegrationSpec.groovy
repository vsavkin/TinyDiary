package com.savkin

import grails.test.*
import grails.plugin.spock.*

class DomainIntegrationSpec extends IntegrationSpec  {	
	
	static transactional = false
	
	def 'should create post with several post types'() {
		setup: 'create post part type'
		def postPartType = new PostPartType(name:"work").save()
		
		and: 'create user'
		def user = new User(username: 'John', password: 'password', enabled: true).save()		
		
		and: 'create post'
		def post = new Post()
		def postPart = new PostPart(text: 'text', type: postPartType)
		post.addToParts(postPart)
		
		and: 'add post to user'
		user.addToPosts(post)
		
		when: 'flush all objects to database'
		user.save(flush: true)
		
		then: 'all posts should be saved too'
		user.posts.size() == 1
		user.posts.toList()[0].parts.size() == 1
		Post.count() == 1
		PostPartType.count() == 1
		
		when: 'delete user'
		user.delete(flush: true)
		
		then: 'all his posts should be deleted too'
		User.count() == 0
		Post.count() == 0
		PostPartType.count() == 1
	}
}
