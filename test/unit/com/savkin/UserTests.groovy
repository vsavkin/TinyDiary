package com.savkin

import grails.test.*

@Mixin(TestUtils)
class UserTests extends GrailsUnitTestCase {
	protected void setUp(){
		super.setUp()
		mockDomain User
		mockDomain Post
	}
	
    void testConstraints() {
		def goodUser = new User(username: 'John', password: 'password')
		mockForConstraintsTests(User, [goodUser])
		
		def blankUser = new User()
		assertFalse blankUser.validate()
		assertEquals 'nullable', blankUser.errors['username']
		assertEquals 'nullable', blankUser.errors['password']
		
		def emptyUsername = new User(username: '')
		assertFalse emptyUsername.validate()
		assertEquals 'blank', emptyUsername.errors['username']
		
		def shortPassword = new User(password: '123')
		assertFalse shortPassword.validate()
		assertEquals 'minSize', shortPassword.errors['password']	
			
		def duplicateUsername = new User(username: 'John')
		assertFalse duplicateUsername.validate()
		assertEquals 'unique', duplicateUsername.errors['username']
    }
	
	void testShouldReturnXPosts(){
		def user = new User(username: 'John', password: 'password').save()
		def post1 = addPost(user, new Post())
		def post2 = addPost(user, new Post())
		def post3 = addPost(user, new Post())
		
		def posts = user.lastPosts(2)
		assertEquals([post3, post2], posts)
	}
	
	void testShouldReturnAllPostsIfThereAreNotEnought(){
		def user = new User(username: 'John', password: 'password').save()
		def post = addPost(user, new Post())
		
		def posts = user.lastPosts(2)
		assertEquals([post], posts)
	}
	
	void testShouldReturnAllPostsFromDateInterval(){
		def user = new User(username: 'John', password: 'password').save()

		def post1 = addPost(user, new Post(), dateCreated: today - 7)
		def post2 = addPost(user, new Post(), dateCreated: today)
		
		def date = today + 1
		date.minutes = 1
		def post3 = addPost(user, new Post(), dateCreated: date)
		def post4 = addPost(user, new Post(), dateCreated: today + 7)
		
		def posts = user.postsBetween(today, today + 1)
		assertEquals([post3, post2], posts)
	}
}
