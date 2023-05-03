
package ch.hearc.spring.musiquali.game.security;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.JwtResponse;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();

		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();

		JwtResponse response = MusicAdminAPI.auth.signIn(email, password).execute();

		if (response != null)
			{
			Cookie cookie = new Cookie("SPRING_JWT_TOKEN_COOKIE", response.getAccessToken());
			attributes.getResponse().addCookie(cookie);

			User user = MusicAdminAPI.users.getById(response.getId()).execute();

			List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

			return new UsernamePasswordAuthenticationToken(email, password, authorities);
			}
		else
			{
			return null;
			}
		}

	@Override
	public boolean supports(Class<?> authentication)
		{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
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
	}
