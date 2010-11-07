package com.savkin

import grails.test.*

class PostTests extends GrailsUnitTestCase {

	void testShouldGenerateText() {
		mockDomain Post
		
		def (type1, type2) = [
			new PostPartType(name: 'type1'), 
			new PostPartType(name: 'type2')]
		
		def post = new Post()
		post.addToParts new PostPart(post: post, type: type1, text: 'text1')
		post.addToParts new PostPart(post: post, type: type2, text: 'text2')
				
		assertEquals "!type1\ntext1\n!type2\ntext2", post.text
    }
}
