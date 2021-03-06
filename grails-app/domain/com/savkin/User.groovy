package com.savkin

class User {
	
	String username
	String password
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	static hasMany = [posts: Post]
	
	static constraints = {
		username blank: false, unique: true
		password minSize: 6
	}
	
	static mapping = { password column: '`password`' }
	
	List<Post> lastPosts(int count){
		Post.findAllByUser(this, [max: count, sort: 'dateCreated', order: 'desc'])
	}
	
	List<Post> postsBetween(Date from, Date to){
		Post.findAllByUserAndDateCreatedBetween(this, from, to + 1, 
			[sort: 'dateCreated', order: 'desc'])
	}
	
	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def postById(long id){
		def post = Post.get(id)
		post?.user == this ? post : null
	}

	def prevPost(long id){
		def post = postById(id)
		if (post != null) {
			Post.findByUserAndDateCreatedLessThan(this, post.dateCreated, [sort: 'dateCreated', order: 'desc'])
		}
	}

	def nextPost(long id){
		def post = postById(id)
		if (post != null) {
			Post.findByUserAndDateCreatedGreaterThan(this, post.dateCreated, [sort: 'dateCreated', order: 'asc'])
		}
	}
}
