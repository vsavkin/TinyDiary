package com.savkin

class AuthService {
	def springSecurityService

	def currentUser(){
		User.get(springSecurityService.principal?.id)
	}
	
	def isLoggedIn(){
		springSecurityService.isLoggedIn() 
	}
}
