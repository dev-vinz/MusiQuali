
package ch.hearc.spring.musiquali.admin.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.hearc.spring.musiquali.admin.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
		{
		try
			{
			String jwt = parseJwt(request);

			Cookie[] tabCookies = request.getCookies() != null ? request.getCookies() : new Cookie[] {};

			Optional<Cookie> cookie = Arrays.stream(tabCookies)//
					.filter(c -> c.getName().contentEquals("SPRING_JWT_TOKEN_COOKIE"))//
					.findFirst();

			Cookie sessionCookie = cookie.orElse(null);

			// Gets JWT token in session
			if (jwt == null && sessionCookie != null)
				{
				jwt = sessionCookie.getValue();
				}

			if (jwt != null && jwtUtils.validateJwtToken(jwt))
				{
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		catch (Exception e)
			{
			logger.error("Cannot set user authentication: {}", e);
			}

		filterChain.doFilter(request, response);
		}

	private String parseJwt(HttpServletRequest request)
		{
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
			{ return headerAuth.substring(7, headerAuth.length()); }

		return null;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	}
