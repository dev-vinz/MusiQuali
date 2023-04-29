
package ch.hearc.spring.musiquali.admin.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
		{
		try
			{
			String jwt = null;

			final String requestTokenHeader = request.getHeader("Authorization");

			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
				{
				jwt = requestTokenHeader.substring(7);
				}
			else
				{
				logger.warn("JWT Token does not begin with Bearer String");
				}

			if (jwt != null && jwtUtils.validateJwtToken(jwt))
				{
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	}
