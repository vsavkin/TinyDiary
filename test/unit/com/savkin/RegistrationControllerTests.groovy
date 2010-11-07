package com.savkin

import grails.test.*

@Mixin([TestUtils, ControllerTestUtils])
class RegistrationControllerTests extends ControllerUnitTestCase {
	protected void setUp(){
		super.setUp()
		mockDomain User
		mockCommandObject UserRegistrationCommand
	}
	
	void testShouldReturnViewForGetRequest(){
		makeRequest 'index'
		assertTrue controller.renderArgs.isEmpty()
	}
	
    void testShouldShowErrorMessageForDifferentPasswords() {
		def urc = new UserRegistrationCommand(username: 'victor', 
			password: 'password', passwordRepeat: 'password1')
		
		def model = makePostRequest('index', urc)
		assertTrue model.urc?.hasErrors()	
		assertTrue controller.renderArgs.isEmpty()
    }
	
	void testShouldShowErrorMessageIfNameIsNotUnique() {
		new User(username: 'victor', password: 'password').save()

		def urc = new UserRegistrationCommand(username: 'victor',
			password: 'password', passwordRepeat: 'password')
		
		def model = makePostRequest('index', urc)
		assertTrue model.urc?.hasErrors()
		assertTrue controller.renderArgs.isEmpty()
	}
	
	void testShouldCreateNewUserAndRedirectToMainPage() {
		def urc = new UserRegistrationCommand(username: 'victor',
			password: 'password', passwordRepeat: 'password')
		
		def model = makePostRequest('index', urc)
		assertEquals 'main', controller.redirectArgs['controller']
		assertFlash 'Welcome'
		assertNotNull User.findByUsername('victor')
	}
}
