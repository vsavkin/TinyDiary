package com.savkin

class OwnerFilters {
	AuthService authService
	
    def filters = {
        all(controller:'post', action:'show|save|update') {
            before = {
                def user = authService.currentUser()
				def post = Post.get(params.id)
				if(!post || post.user != user){
					redirect controller: 'main'	
					return false
				}
				return true
            }
        }
    }
    
}
