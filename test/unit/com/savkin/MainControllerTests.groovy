package com.savkin

import grails.test.*
@Mixin([TestUtils, ControllerTestUtils])
class MainControllerTests extends ControllerUnitTestCase {

	void testShouldShowMainPageForAnonymous() {
		asCurrentUser null
		makeRequest 'index'
		assertEquals [:], redirectArgs
	}
	
    void testShouldRedirectToPostsControllerRegisteredUser() {
		asCurrentUser 'user'
		makeRequest 'index'
		assertEquals 'posts', redirectArgs.controller
    }
}
