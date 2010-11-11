package com.savkin

import grails.plugin.spock.*

class PostSpec extends UnitSpec {
	def 'post should generate text using its parts'(){
		setup:
		mockDomain Post
		def type1 = new PostPartType(name: 'type1')
		def type2 = new PostPartType(name: 'type2')
		
		when:
		def post = new Post()
		post.addToParts new PostPart(post: post, type: type1, text: 'text1')
		post.addToParts new PostPart(post: post, type: type2, text: 'text2')
		
		then:
		post.text == "!type1\ntext1\n!type2\ntext2"
	}
}
