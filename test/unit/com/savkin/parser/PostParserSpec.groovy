package com.savkin.parser

import spock.lang.*

class PostParserSpec extends Specification{
	PostParser parser
	
	def setup(){
		parser = new PostParser()	
	}
	
	def 'should return empty list for empty string'(){
		expect:
		parser.parse(emptyInput).isEmpty()
		
		where:
		emptyInput << ["", "\t\t   \t\t"]
	}
	
	def 'should return list with types and texts'(){
		setup:
		def str = """
			!Work
			${workNotes}
			
			!Home
			${homeNotes}
		"""
		
		when:
		def result = parser.parse(str)

		then:
		result.size() == 2
		result[0] == [type: 'work', text: workNotes]
		result[1] == [type: 'home', text: homeNotes]
		
		where:
		workNotes = 'Some notes about work'
		homeNotes = 'Some notes about home'
	}
		
	def 'should return default type if type is not specified'(){
		setup:
		def str = """
			${defaultNotes}
			
			!Home
			${homeNotes}
		"""
				
		when:
		def result = parser.parse(str)
		
		then:
		result.size() == 2
		result[0] == [type: PostParser.DEFAULT_TYPE, text: defaultNotes]
		result[1] == [type: 'home', text: homeNotes]
		
		where:
		defaultNotes = 'Some notes about work'
		homeNotes = 'Some notes about home'
	}
}
