
package ch.hearc.spring.musiquali.game.api.requests;

import ch.hearc.spring.musiquali.game.api.utils.AdminProperties;

public abstract class AdminRequest
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	protected String url(String property, Object... args)
		{
		return String.format(AdminProperties.getURL(property), args);
		}

	protected String url(String property)
		{
		return AdminProperties.getURL(property);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}

