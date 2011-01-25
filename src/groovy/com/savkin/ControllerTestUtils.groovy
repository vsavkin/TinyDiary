package com.savkin

import groovy.mock.interceptor.*


class ControllerTestUtils {
	User asCurrentUser(user){
		def stub = new StubFor(AuthService)
		stub.demand.isLoggedIn(0..100){user}
		stub.demand.currentUser(0..100){user}
		controller.authService = stub.proxyInstance()
		user
	}
	
	def makeRequest(Map params = [:], String methodName){
		params.each {k,v->
			mockParams[k] = v
		}
		controller."${methodName}"()
	}
	
	def makeRequest(String methodName, commandObject){
		controller."${methodName}"(commandObject)
	}
	
	def makePostRequest(String methodName, commandObject){
		mockRequest.method = 'POST'
		commandObject.validate()
		makeRequest methodName, commandObject
	}
	
	def assertFlash(pattern){
		mockFlash.message =~ pattern
	}
}
