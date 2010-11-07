import com.savkin.*
import grails.plugins.springsecurity.SpringSecurityService;
import grails.util.Environment

class BootStrap {
	
	SpringSecurityService springSecurityService

    def init = { servletContext ->
		if(Environment.current == Environment.DEVELOPMENT){
			def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save()
			def adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save()
			
			def user = new User(username: 'avix1000', password: springSecurityService.encodePassword('password'), enabled: true)
			user.save()
			
			UserRole.create user, userRole
			
			def work = new PostPartType(name: 'work').save()
			def home = new PostPartType(name: 'home').save()
			
			def post = new Post()
			post.addToParts new PostPart(type: work, text: 'Sitting at work')
			post.addToParts new PostPart(type: home, text: 'Sitting at home')
			
			user.addToPosts post
			user.save()		
			
		}
	}
	
    def destroy = {
    }
}
