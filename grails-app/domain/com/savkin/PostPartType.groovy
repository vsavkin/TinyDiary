package com.savkin

class PostPartType {
	String name
	
	static constraints = {
		name(blank: false, unique: true)
	}

	String toString() {
		name
	}

	static type(String name){
		new PostPartType(name: name)
	}

	boolean equals(obj){
		getClass() == obj.getClass() && name == obj?.name
	}
}
