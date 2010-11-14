package com.savkin

import grails.test.*
import grails.plugin.spock.*

@Mixin([TestUtils, ControllerTestUtils])
class RegistrationControllerSpec extends ControllerSpec {
	def setup(){
		mockDomain User
		mockCommandObject UserRegistrationCommand
	}
	
	def 'should return view for get request'(){
		when:
		makeRequest 'index'
		
		then:
		controller.renderArgs.isEmpty()
	}
	
    def 'should show error message for different passwords'() {
		setup:
		def urc = new UserRegistrationCommand(username: 'victor', 
			password: 'password', passwordRepeat: 'password1')
		controller.authService = Mock(AuthService)
		
		when:
		def model = makePostRequest('index', urc)
		
		then:
		model.urc?.hasErrors()	
		controller.renderArgs.isEmpty()
    }
	
	def 'should show error message if name is not unique'() {
		setup:
		new User(username: 'victor', password: 'password').save()
		def urc = new UserRegistrationCommand(username: 'victor',
			password: 'password', passwordRepeat: 'password')
		controller.authService = Mock(AuthService)
		
		when:
		def model = makePostRequest('index', urc)
		
		then:
		model.urc?.hasErrors()
		controller.renderArgs.isEmpty()
	}
	
	def 'should create new enabled user and redirect to main page'() {
		setup:
		def urc = new UserRegistrationCommand(username: 'victor',
			password: 'password', passwordRepeat: 'password')
		controller.authService = Mock(AuthService)
		
		when:
		def model = makePostRequest('index', urc)
		
		then:
		1 * controller.authService.encodePassword('password') >> 'encoded_password'
		controller.redirectArgs == [controller: 'main']
		assertFlash 'Welcome'
		
		def user = User.findByUsername('victor')
		user != null
		user.enabled == true
		user.password == 'encoded_password'
	}
}
