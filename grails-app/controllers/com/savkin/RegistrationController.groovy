package com.savkin

class RegistrationController {		
	AuthService authService
	
    def index = {UserRegistrationCommand urc ->	
		if(request.method == "POST"){
			def user = new User(urc.properties)
			user.password = authService.encodePassword(urc.password)
			
			if(urc.hasErrors()){
				[urc: urc]
			}else if (!user.save()){
				[urc: user]
			}else{
				flash.message = "Welcome ${user.username}!"
				redirect controller: 'main'
			}
		}
	}
}

class UserRegistrationCommand {
	String username
	String password
	String passwordRepeat
	
	static constraints = {
		username(nullable: false, blank: false)
		password(minSize: 6)
		passwordRepeat(nullable: false,
			  validator: { passwd2, urc ->
				 return passwd2 == urc.password
			  })		
	}
}
