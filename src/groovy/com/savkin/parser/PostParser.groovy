package com.savkin.parser

class PostParser {
	static final DEFAULT_TYPE = '~~ DEFAULT_TYPE ~~'
	
	def parse(String text){
		def result = [[type: DEFAULT_TYPE, textLines: []]]
		
		for(line in text.readLines()){
			if(isNewType(line)){
				result << [type: canonicalTypeFormat(line), textLines: []]
			}else{
				result.last().textLines << line
			}
		}
		removeEmptyTypes joinTextLines(result)
	} 
	
	private isNewType(String line){
		line.trim().startsWith('!')
	}
	
	private removeEmptyTypes(result){
		result.findAll{!it.text.isEmpty()}
	}
	
	private joinTextLines(result){
		result.collect{
			[type: it.type, text: it.textLines.join("\n").trim()]
		}
	}
	
	private canonicalTypeFormat(String line){
		line.trim()[1..-1].toLowerCase()
	}
}