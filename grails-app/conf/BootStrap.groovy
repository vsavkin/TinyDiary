import com.savkin.*
import grails.plugins.springsecurity.SpringSecurityService;
import grails.util.Environment

class BootStrap {
	
	SpringSecurityService springSecurityService

    def init = { servletContext ->
		if(Environment.current == Environment.DEVELOPMENT){			
			def user = new User(username: 'avix1000', password: 
				springSecurityService.encodePassword('password'), enabled: true)
			user.save()
			
			def work = new PostPartType(name: 'work').save()
			def home = new PostPartType(name: 'home').save()
			
			def post = new Post()
			post.addToParts new PostPart(type: work, text: 'Sitting at work')
			post.addToParts new PostPart(type: home, text: 'Sitting at home')
			
			user.addToPosts post
			user.save()		

			user = new User(username: 'john', password:
				springSecurityService.encodePassword('password'), enabled: true)
			user.save()
			
			post = new Post()
			post.addToParts new PostPart(type: work, text: 'Sitting at work')
			post.addToParts new PostPart(type: home, text: 'Sitting at home')
			
			user.addToPosts post
			user.save(flush: true)
			println post.id
		}
	}
	
    def destroy = {
    }
}
