
package ch.hearc.spring.musiquali.admin.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();

		DbUser user = userService.getByEmail(email);

		if (user != null)
			{
			if (password.equals(user.getPassword()))
				{
				return new UsernamePasswordAuthenticationToken(user.getFirstName(), password, new ArrayList<>());
				}
			}

		return null;
		}

	@Override
	public boolean supports(Class<?> authentication)
		{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private UserService userService;

	}
