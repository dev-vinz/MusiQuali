
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.utils.DeezerProperties;

public abstract class DeezerRequests
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

	protected String url(String property, Object... args)
		{
		return String.format(DeezerProperties.getURL(property), args);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
