package com.savkin

import grails.test.*
@Mixin([TestUtils, ControllerTestUtils])
class MainControllerTests extends ControllerUnitTestCase {

	void testShouldShowMainPageForAnonymous() {
		setCurrentUser null
		makeRequest 'index'
		assertEquals [:], redirectArgs
	}
	
    void testShouldRedirectToPostsControllerRegisteredUser() {
		setCurrentUser 'user'
		makeRequest 'index'
		assertEquals 'posts', redirectArgs.controller
    }
}
