
package ch.hearc.spring.musiquali.admin.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class PrincipalService
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static DbUser parseFromPrincipal(Principal principal)
		{
		// Some security
		if (principal == null)
			{
			return null;
			}
		else
			{
			return userService.getByEmail(principal.getName());
			}
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	@PostConstruct
	private void initPostConstruct()
		{
		userService = this._uService;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private UserService _uService;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static UserService userService;
	}
