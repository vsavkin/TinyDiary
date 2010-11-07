package com.savkin

class PostPart {
	String text
	PostPartType type	
	static belongsTo = [post: Post]
	
	static constraints = {
		text(blank: false, maxSize: 10000)
	}
	
	static mappying = {
		type(lazy: false)
	}
}
