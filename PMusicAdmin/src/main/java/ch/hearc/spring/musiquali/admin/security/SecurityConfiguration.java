
package ch.hearc.spring.musiquali.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter()
//		{
//		return new AuthTokenFilter();
//		}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception
		{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
		}

	@Bean
	public PasswordEncoder passwordEncoder()
		{
		return new BCryptPasswordEncoder();
		}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
		{
		http.csrf().disable()//
				.authorizeRequests()//
				.antMatchers("/users/login").permitAll()//
				.antMatchers("/users/register").permitAll()//
				.antMatchers("/api/auth/login").permitAll()//
				.antMatchers("/webjars/**").permitAll()//
				.anyRequest().authenticated();

		http//
		.formLogin(form -> form//
						.loginPage("/users/login")//
						.loginProcessingUrl("/api/auth/login")//
						.defaultSuccessUrl("/", true)//
						.permitAll())//
				.logout(logout -> logout//
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//
						.permitAll());

		return http.build();

		//		http.cors().and().csrf().disable()//
		//				.exceptionHandling()//
		//				.authenticationEntryPoint(unauthorizedHandler)//
		//				.and()//
		//				.sessionManagement()//
		//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
		//				.and()//
		//				.authorizeRequests()//
		//				.antMatchers(HttpMethod.GET, "/").permitAll()//
		//				.antMatchers("/api/auth/**").permitAll()//
		//				.antMatchers("/webjars/**").permitAll()//
		//				.anyRequest().authenticated();
		//
		//		http//
		//				.formLogin(form -> form//
		//						.loginPage("/users/login")//
		//						.loginProcessingUrl("/api/auth/login")//
		//						.defaultSuccessUrl("/", true)//
		//						.permitAll())//
		//				.logout(logout -> logout//
		//						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//
		//						.permitAll());
		//
		//		http.authenticationProvider(authProvider);
		//
		//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//
		//		return http.build();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

//	@Autowired
//	private CustomAuthenticationProvider authProvider;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;
	}
