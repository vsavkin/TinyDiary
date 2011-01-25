package com.savkin

class PostPart {
	String text
	PostPartType type	
	static belongsTo = [post: Post]
	
	static constraints = {
		text(blank: false, maxSize: 10000)
	}

	def getTextAsHtml(){
		text.encodeAsHTML().replaceAll("\n", '<br>')
	}
	
	static mappying = {
		type(lazy: false)
	}

	boolean equals(String text, String type){
		this.text == text && this.type.toString() == type
	}
}
