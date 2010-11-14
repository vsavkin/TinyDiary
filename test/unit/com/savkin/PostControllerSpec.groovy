package com.savkin

import grails.test.*
import grails.plugin.spock.*
import com.savkin.parser.*

@Mixin([TestUtils, ControllerTestUtils])
class PostControllerSpec extends ControllerSpec {
	def setup() {
		mockDomain User
		mockDomain Post
		mockDomain PostPart
		mockDomain PostPartType
		mockCommandObject SavePostCommand
	}
	
	def 'should render view with specified post'() {	
		setup:
		def post = Mock(Post)
		mockStatic(Post){
			get(1..1){id->
				assert 100 == id
				post	
			}
		}
		
		when:
		def model = makeRequest('show', id: 100)
		
		then:
		model.post == post
	}
	
	def 'should show create form'() {
		when:
		makeRequest 'create'
		
		then:
		renderArgs.isEmpty()
	}
	
	def 'should show edit form'() {
		setup:
		def post = new Post()
		mockStatic(Post){
			get(1..1){id->
				assert 100 == id
				post
			}
		}
		
		when:
		def model = makeRequest('edit', id: 100)
		
		then:
		renderArgs.isEmpty()
		model.post == post
	}
	
	def 'should save new post'(){
		setup:
		def user = asCurrentUser(createUser())		
		def command = new SavePostCommand(text: 'some text', parser: Mock(PostParser))

		when:
		makePostRequest 'save', command
		
		then:
		command.parser.parse('some text') >> 
			[[type: 'work', text: 'work text'], [type: 'home', text: 'home text']]
			
		assertFlash 'Created'
		redirectArgs.action == 'show'		
		def post = checkSizeAndReturnFirst(1, user.posts)
		def (work, home) = checkSizeAndReturnList(2, post.parts)
		
		work.type.toString() == 'work'
		work.text == 'work text'
		home.type.toString() == 'home'
		home.text == 'home text'
	}
	
	def 'show show error message if saving empty post'(){
		setup:
		def command = new SavePostCommand(text: '')
		
		when:
		def model = makePostRequest('save', command)
		
		then:
		renderArgs.view == 'create'
		model.post == command
		command.hasErrors()
	}
	
	def 'should ignore existing post part types'(){
		setup:
		mockDomain PostPartType, [new PostPartType(name: 'work')]		
		def user = asCurrentUser(createUser())		
		def command = new SavePostCommand(text: 'some text', parser: Mock(PostParser))
		
		when:
		makePostRequest 'save', command
		
		then:
		command.parser.parse('some text') >>
			[[type: 'work', text: 'work text'], [type: 'home', text: 'home text']]
		redirectArgs.action == 'show'
		PostPartType.count() == 2
		PostPartType.findByName('work') != null
		PostPartType.findByName('home') != null
	}
	
	def 'should show error message if update empty post'(){
		setup:
		def command = new SavePostCommand(text: '')
		
		when:
		def model = makePostRequest('update', command)
		
		then:
		renderArgs.view == 'edit'
		model.post == command
		command.hasErrors()
	}
	
	def 'should update existing post'(){
		setup: 'create new user'
		def user = asCurrentUser(createUser())

		and: 'create post part type'
		def postType = new PostPartType(name: 'work')
		mockDomain PostPartType, [postType]
		
		and: 'create post for our user'
		def post = new Post(user: user)
		mockDomain Post, [post]
		
		and: 'add post parts to our post'
		mockDomain PostPart, [new PostPart(post: post, type: postType, text: 'text')]
		
		and: 'creating command'
		def command = new SavePostCommand(id: post.id, text: 'some text', parser: Mock(PostParser))
		
		when:
		makePostRequest 'update', command
		
		then:
		command.parser.parse('some text') >> [[type: 'home', text: 'home text']]
		redirectArgs.action == 'show'
		
		def updatedPost = checkSizeAndReturnFirst(1, Post.list())
		def updatedPostPart = checkSizeAndReturnFirst(1, updatedPost.parts)
		updatedPostPart.type.toString() == 'home'
		updatedPostPart.text == 'home text'
	}
}
