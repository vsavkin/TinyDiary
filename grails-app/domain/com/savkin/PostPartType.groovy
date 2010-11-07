package com.savkin

class PostPartType {
	String name
	
	static constraints = {
		name(blank: false, unique: true)
	}

	String toString() {
		name
	}		
}
