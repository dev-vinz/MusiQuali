
package ch.hearc.spring.musiquali.game.security;

import java.security.Principal;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class PrincipalService
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static User parseFromPrincipal(Principal principal)
		{
		// Some security
		if (principal == null)
			{
			return null;
			}
		else
			{
			return MusicAdminAPI.users.getByEmail(principal.getName()).execute();
			}
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
