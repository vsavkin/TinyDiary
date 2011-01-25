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
		
		expect:
		user.postsBetween(today, today + 1) == [post3, post2]
	}

	def 'should return a post by id'(){
		setup:
		def user = new User(username: 'John', password: 'password').save()
		def post = addPost(user, new Post(), dateCreated: today)

		expect:
		user.postById(post.id) == post
	}

	def 'should return null if post exists by belong to another user'(){
		setup:
		def user1 = new User(username: 'John', password: 'password').save()
		def user2 = new User(username: 'Piter', password: 'password').save()
		def post = addPost(user1, new Post(), dateCreated: today)

		expect:
		user2.postById(post.id) == null
	}

	def 'should return the previous post'(){
		setup:
		def user = new User(username: 'John', password: 'password').save()
		def post1 = addPost(user, new Post(), dateCreated: today - 7)
		def post2 = addPost(user, new Post(), dateCreated: today)
		def post3 = addPost(user, new Post(), dateCreated: today + 7)

		expect:
		user.prevPost(post3.id) == post2
		user.prevPost(post2.id) == post1
		user.prevPost(post1.id) == null
	}

	def 'should return the next post'(){
		setup:
		def user = new User(username: 'John', password: 'password').save()
		def post1 = addPost(user, new Post(), dateCreated: today - 7)
		def post2 = addPost(user, new Post(), dateCreated: today)
		def post3 = addPost(user, new Post(), dateCreated: today + 7)

		expect:
		user.nextPost(post3.id) == null
		user.nextPost(post2.id) == post3
		user.nextPost(post1.id) == post2
	}
}
