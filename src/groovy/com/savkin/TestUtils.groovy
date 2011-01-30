package com.savkin

import java.util.Map;

import groovy.mock.interceptor.MockFor;
import groovy.time.TimeCategory

class TestUtils {
	User createUser(Map override = [:], List posts = []){
		def properties = [username: 'victor', password: 'password']
		def user = new User(properties + override).save()
		posts.each{
			it.user = user
			it.save()
			
			user.addToPosts it
		}
		return user
	}
	
	def bunchOfPosts(){
		(1..5).collect{new Post()}
	}

	def createPostFromParts(Map parts){
		def p = new Post()
		parts.each {typeName, text->
			def type = PostPartType.findByName(typeName)
			p.addToParts new PostPart(type: type, text: text)
		}
		p
	}
	
	def mock(Class clazz, Closure s){
		def mock = mockFor(clazz, true)
		s.delegate = mock.demand
		s()
		mock.createMock()
	}
	
	def mockStatic(Class clazz, Closure s){
		def mock = mockFor(clazz, true)
		s.delegate = mock.demand.static
		s()
		mock.createMock()
	}
	
	def addPost(Map override = [:], user, post){	
		post.user = user
		post.save()
		
		post.properties = override
		post.save()
		
		user.addToPosts(post)
		return post
	}
		
	def dateTimeSyntaxSugar(){
		Integer.metaClass.mixin TimeCategory
		Date.metaClass.mixin TimeCategory
	}
	
	def getToday(){
		def date = new Date()
		date.clearTime()
		date
	}
	
	def checkSizeAndReturnList(int size, list){
		list.size() == size
		list
	}
	
	def checkSizeAndReturnFirst(int size, list){
		list.size() == size
		list.toList().first()
	}
}
