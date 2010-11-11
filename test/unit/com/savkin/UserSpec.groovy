package com.savkin

import grails.plugin.spock.*


@Mixin(TestUtils)
class UserSpec extends UnitSpec {
	def setup(){
		mockDomain User
		mockDomain Post
	}
	
    def 'checking user constraints'() {
		setup:
		mockForConstraintsTests(User, [new User(username: 'John', password: 'password')])
		def user
		
		when: 'username and password are not specified'
		user = new User()
		
		then:
		! user.validate()
		user.errors['username'] == 'nullable'
		user.errors['password'] == 'nullable'
		
		when: 'username is empty'
		user = new User(username: '')
		
		then:
		! user.validate()
		user.errors['username'] == 'blank'
		
		when: 'password is too short'
		user = new User(password: '123')
		
		then:
		! user.validate()
		user.errors['password']	== 'minSize'
			
		when: 'user with such a username is already exist'
		user = new User(username: 'John')
		
		then:
		! user.validate()
		user.errors['username'] == 'unique'
    }
	
	def 'should return X posts'(){
		setup:
		def user = new User(username: 'John', password: 'password').save()
		def post1 = addPost(user, new Post())
		def post2 = addPost(user, new Post())
		def post3 = addPost(user, new Post())
		def posts
		
		when: 'returning n last posts where n < posts.size'
		posts = user.lastPosts(2)
		
		then:
		posts == [post3, post2]
		
		when: 'returning n last posts where n > posts.size'
		posts = user.lastPosts(4)
		
		then:
		posts == [post3, post2, post1]
	}
	
	def 'should return all posts from specified date interval'(){
		setup:
		def user = new User(username: 'John', password: 'password').save()
		def post1 = addPost(user, new Post(), dateCreated: today - 7)
		def post2 = addPost(user, new Post(), dateCreated: today)
		
		def date = today + 1
		date.minutes = 1
		def post3 = addPost(user, new Post(), dateCreated: date)
		def post4 = addPost(user, new Post(), dateCreated: today + 7)
		
		when:
		def posts = user.postsBetween(today, today + 1)
		
		then:
		posts = [post3, post2]
	}
}
