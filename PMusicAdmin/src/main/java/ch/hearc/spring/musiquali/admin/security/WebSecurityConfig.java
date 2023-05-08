
package ch.hearc.spring.musiquali.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.hearc.spring.musiquali.admin.security.jwt.AuthEntryPointJwt;
import ch.hearc.spring.musiquali.admin.security.jwt.AuthTokenFilter;
import ch.hearc.spring.musiquali.admin.security.services.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter()
		{
		return new AuthTokenFilter();
		}

	@Bean
	public DaoAuthenticationProvider authenticationProvider()
		{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(this.userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
		}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
		{
		return authConfig.getAuthenticationManager();
		}

	@Bean
	public PasswordEncoder passwordEncoder()
		{
		return new BCryptPasswordEncoder();
		}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
		{
		http.cors().and().csrf().disable()//
				.exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//
				.authorizeHttpRequests()//
				.requestMatchers("/error", "/broken_error.png", "/webjars/**").permitAll()//
				.requestMatchers(HttpMethod.GET, "/api/**").permitAll()//
				.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()//
				.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

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
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final String SPRING_JWT_TOKEN_COOKIE = "MUSIQUALI_JWT_TOKEN_COOKIE";
	}
