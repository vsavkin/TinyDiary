package com.savkin

import grails.test.*
import grails.plugin.spock.*

@Mixin([TestUtils, ControllerTestUtils])
class MainControllerSpec extends ControllerSpec {

	def 'should show main page for anonymous users'() {
		when:
		asCurrentUser null
		makeRequest 'index'
		
		then:
		redirectArgs == [:]
	}
	
    def 'should redirect registered users to PostsController'() {
		when:
		asCurrentUser new User()
		makeRequest 'index'
		
		then:
		redirectArgs.controller == 'posts'
    }
}
