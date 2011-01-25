package com.savkin

class OwnerFilters {
	AuthService authService
	
    def filters = {
        all(controller:'post', action:'show|edit|update') {
            before = {
                def user = authService.currentUser()
				if(!user.postById(params.id.toLong())){
					redirect controller: 'main'	
					return false
				}
				return true
            }
        }
    }
    
}
