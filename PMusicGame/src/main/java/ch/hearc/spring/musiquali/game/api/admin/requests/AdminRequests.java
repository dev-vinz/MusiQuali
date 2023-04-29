
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.utils.AdminProperties;

public abstract class AdminRequests
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * Formats a string by completing datas with arguments
	 * @param property A property key
	 * @param args All the arguments to complete a string
	 * @return A formatted URL
	 */
	protected String url(String property, Object... args)
		{
		return String.format(AdminProperties.getURL(property), args);
		}

	}
