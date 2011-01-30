package com.savkin

import grails.test.*
import grails.plugin.spock.*
import static com.savkin.PostPartType.type

@Mixin([TestUtils, ControllerTestUtils])
class PostsControllerSpec extends ControllerSpec {
	
	def 'should return current user and his last 10 posts'() {
		setup:
		def posts = bunchOfPosts()
		def user = Mock(User)
		asCurrentUser user
		
		when:
		makeRequest 'index'	
		
		then:
		1 * user.lastPosts(10) >> posts
		renderArgs.model.user == user
		renderArgs.model.posts == posts
		controller.session.posts == posts
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
		renderArgs.model.user == user
		renderArgs.model.posts == posts
		controller.session.posts == posts
		renderArgs.view == 'posts'
		renderArgs.model.from == today
		renderArgs.model.to == today + 2
	}

	def 'should return the list of unique part types'() {
		setup:
		mockDomain Post
		mockDomain PostPart

		def (homeType, workType, personalType) =  [type('home'), type('work'), type('personal')]
		mockDomain PostPartType, [homeType, workType, personalType]
		
		def posts = [
			createPostFromParts(home: 'home text', work: 'work text'),
			createPostFromParts(work: 'just text')]

		def user = Mock(User)
		asCurrentUser user

		when:
		makeRequest 'index'

		then:
		1 * user.lastPosts(10) >> posts
		renderArgs.model.types == [homeType, workType]
		controller.session.types == [homeType, workType]
	}

	def 'should render the list of partTypes'() {
		setup:
		mockDomain Post
		mockDomain PostPart

		def types =  [type('home'), type('work'), type('personal')]
		mockDomain PostPartType, types

		def posts = [
			createPostFromParts(home: 'home text', work: 'work text'),
			createPostFromParts(work: 'just text')]
		
		def user = Mock(User)
		asCurrentUser user

		when:
		mockSession.types = types
		mockSession.posts = posts
		makeRequest 'showParts', selectedType: types.first().name

		then:
		renderArgs.template == 'shortPost'
		renderArgs.model.posts == posts[0..<1]
		renderArgs.model.selectedType == types.first()
	}
}
