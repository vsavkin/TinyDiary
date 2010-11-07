package com.savkin

import grails.plugins.springsecurity.Secured
import groovy.lang.Mixin;

class MainController {
	AuthService authService
	
	def index = { 
		if(authService.isLoggedIn()){
			redirect controller: 'posts'
		}	
	}
	
//	@Secured(['ROLE_USER'])
}
