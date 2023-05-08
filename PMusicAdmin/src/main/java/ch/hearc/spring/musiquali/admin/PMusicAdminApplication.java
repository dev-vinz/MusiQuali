
package ch.hearc.spring.musiquali.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class PMusicAdminApplication
	{

	public static void main(String[] args)
		{
		SpringApplication.run(PMusicAdminApplication.class, args);
		}

	@Configuration
	public class WebConfig implements WebMvcConfigurer
		{

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry)
			{
			registry//
					.addResourceHandler("/**")//
					.addResourceLocations("classpath:/static/", "classpath:/images/", "classpath:/css/")//
					.setCachePeriod(0);
			}
		}

	@PostConstruct
	public void addSomeDatas()
		{
		userService.add(new DbUser("Admin", "Admin", "admin@musiquali.ch", "Admin1234", Role.ADMIN));
		userService.add(new DbUser("Vincent", "Jeannin", "vincent.jeannin@he-arc.ch", "Password1234", Role.MODERATOR));
		userService.add(new DbUser("Théo", "Vuilliomenet", "theo.vuilliomenet@he-arc.ch", "Password1234", Role.MODERATOR));
		}

	@Autowired
	private UserService userService;
	}
