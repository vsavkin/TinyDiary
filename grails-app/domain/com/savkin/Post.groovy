package com.savkin

class Post {
	
	List parts
	Date dateCreated
	
	static belongsTo = [user: User]
	static hasMany = [parts: PostPart]
	
    static constraints = {
    }
	
	static mapping = {
		parts cascade: "all-delete-orphan"
	}
	
	def getText(){
		parts.collect{
			"!${it.type.toString()}\n${it.text}"
		}.join("\n\n")
	}
	
	def updateContent(List postParts){
		deleteAllParts()
		postParts.each {
			addToParts it
		}
	}
	
	private deleteAllParts(){
		def clonedParts = parts.collect{it}
		clonedParts.each{
			removeFromParts it
		}
	}

	def hasPartType(PostPartType type){
		parts.find {it.type == type}
	}
}
