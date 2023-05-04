
package ch.hearc.spring.musiquali.game.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception
		{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.authenticationProvider(this.authProvider);

		return authenticationManagerBuilder.build();
		}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
		{
		http.csrf().disable()//
				.authorizeHttpRequests(authorize -> authorize//
						.antMatchers("/register/**", "/webjars/**", "/favicon.ico").permitAll()//
						.antMatchers("/**").hasAnyRole("USER", "MODERATOR", "ADMIN"))//
				.formLogin(form -> form//
						.loginPage("/login")//
						.loginProcessingUrl("/login")//
						.permitAll())//
				.logout(logout -> logout//
						.deleteCookies(SPRING_JWT_TOKEN_COOKIE)//
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//
						.permitAll());

		return http.build();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private CustomAuthenticationProvider authProvider;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final String SPRING_JWT_TOKEN_COOKIE = "MUSIQUALI_JWT_TOKEN_COOKIE";
	}
