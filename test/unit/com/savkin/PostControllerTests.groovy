package com.savkin

import grails.test.*
import com.savkin.parser.*

@Mixin([TestUtils, ControllerTestUtils])
class PostControllerTests extends ControllerUnitTestCase {
	protected void setUp() {
		super.setUp()
		
		mockDomain User
		mockDomain Post
		mockDomain PostPart
		mockDomain PostPartType
		mockCommandObject SavePostCommand
	}
	
	void testShouldShowPost() {	
		def post = new Post()
		mockStatic(Post){
			get(1..1){id->
				assert 100 == id
				post	
			}
		}
		
		def model = makeRequest('show', id: 100)
		assertEquals post, model.post 
	}
	
	void testShouldShowCreateForm() {
		makeRequest 'create'
		assertTrue renderArgs.isEmpty()
	}
	
	void testShouldShowEditForm() {
		def post = new Post()
		mockStatic(Post){
			get(1..1){id->
				assert 100 == id
				post
			}
		}
		
		def model = makeRequest('edit', id: 100)
		assertTrue renderArgs.isEmpty()
		assertEquals post, model.post
	}
	
	void testShouldSaveNewPost(){
		def user = asCurrentUser(createUser())
		
		mock(PostParser){
			parse(1..10){
				[[type: 'work', text: 'work text'],
				 [type: 'home', text: 'home text']]
			}
		}
		makePostRequest 'save', new SavePostCommand(text: 'some text')
		assertEquals 'show', redirectArgs.action
		
		def post = checkSizeAndReturnFirst(1, user.posts)
		def (work, home) = checkSizeAndReturnList(2, post.parts)
		
		assertEquals 'work', work.type.toString()
		assertEquals 'work text', work.text
		assertEquals 'home', home.type.toString()
		assertEquals 'home text', home.text
		assertFlash 'Created'
	}
	
	void testShouldShowErrorMessageIfYouSaveEmptyPost(){
		def command = new SavePostCommand(text: '')
		def model = makePostRequest('save', command)
		
		assertEquals 'create', renderArgs.view
		assertEquals command, model.post
		assertTrue command.hasErrors()
	}
	
	void testShouldIgnoreExistingPostPartTypes(){
		mockDomain PostPartType, [new PostPartType(name: 'work')]		
		
		def user = asCurrentUser(createUser())		
		mock(PostParser){
			parse(1..10){
				[[type: 'work', text: 'work text'],
				 [type: 'home', text: 'home text']]
			}
		}
		makePostRequest 'save', new SavePostCommand(text: 'some text')
		
		assertEquals 'show', redirectArgs.action
		assertEquals 2, PostPartType.count()
		assertNotNull PostPartType.findByName('work')
		assertNotNull PostPartType.findByName('home')
	}
	
	void testShouldShowErrorMessageIfUpdateEmptyPost(){
		def command = new SavePostCommand(text: '')
		def model = makePostRequest('update', command)
		
		assertEquals 'edit', renderArgs.view
		assertEquals command, model.post
		assertTrue command.hasErrors()
	}
	
	void testShouldUpdateExistingPost(){
		def user = asCurrentUser(createUser())
		
		def postType = new PostPartType(name: 'work')
		mockDomain PostPartType, [postType]
		
		def post = new Post(user: user)
		mockDomain Post, [post]
		
		mockDomain PostPart, [new PostPart(post: post, type: postType, text: 'text')]
		
		mock(PostParser){
			parse(1..10){[[type: 'home', text: 'home text']]}
		}
		makePostRequest 'update', new SavePostCommand(id: post.id, text: 'some text')
		
		assertEquals 'show', redirectArgs.action
		
		def updatedPost = checkSizeAndReturnFirst(1, Post.list())
		def updatedPostPart = checkSizeAndReturnFirst(1, updatedPost.parts)
		assertEquals 'home', updatedPostPart.type.toString()
		assertEquals 'home text', updatedPostPart.text
	}
}
