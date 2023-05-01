
package ch.hearc.spring.musiquali.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PMusicGameApplication
	{

	public static void main(String[] args)
		{
		SpringApplication.run(PMusicGameApplication.class, args);
		}

	@Configuration
	public class WebConfig implements WebMvcConfigurer
		{

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry)
			{
			registry//
					.addResourceHandler("/**")//
					.addResourceLocations("classpath:/static/", "classpath:/images/", "classpath:/css/", "classpath:/js/")//
					.setCachePeriod(0);
			}
		}

	}
