package com.savkin.parser

class PostParserTests extends GroovyTestCase{
	PostParser parser
	
	protected void setUp(){
		parser = new PostParser()	
	}
	
	void testShouldReturnEmptyResultForEmptyString(){
		assertTrue parser.parse("").isEmpty()
		assertTrue parser.parse("\t\t   \t\t").isEmpty()
	}
	
	void testShouldReturnListOfTypesAndText(){
		def workNotes = 'Some notes about work'
		def homeNotes = 'Some notes about home'
		def str = """
			!Work
			${workNotes}
			
			!Home
			${homeNotes}
		"""
		def result = parser.parse(str)
		println result.inspect()
		assertEquals 2, result.size()

		assertEquals 'work', result[0].type
		assertEquals workNotes, result[0].text
		
		assertEquals 'home', result[1].type
		assertEquals homeNotes, result[1].text		
	}
		
	void testShouldReturnDefaultTypeIfIsNotSpecified(){
		def defaultNotes = 'Some notes about work'
		def homeNotes = 'Some notes about home'
		def str = """
			${defaultNotes}
			
			!Home
			${homeNotes}
		"""
		def result = parser.parse(str)
		assertEquals 2, result.size()

		assertEquals PostParser.DEFAULT_TYPE, result[0].type
		assertEquals defaultNotes, result[0].text
		
		assertEquals 'home', result[1].type
		assertEquals homeNotes, result[1].text		
	}
}
