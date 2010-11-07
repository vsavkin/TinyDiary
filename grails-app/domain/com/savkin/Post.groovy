package com.savkin

class Post {
	
	List parts
	Date dateCreated
	
	static belongsTo = [user: User]
	static hasMany = [parts: PostPart]
	
    static constraints = {
    }
	
	def getText(){
		parts.collect{
			"!${it.type.toString()}\n${it.text}"
		}.join("\n")
	}
}
