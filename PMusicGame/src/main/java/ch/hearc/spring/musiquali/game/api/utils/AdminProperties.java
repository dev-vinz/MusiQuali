
package ch.hearc.spring.musiquali.game.api.utils;

import java.util.HashMap;
import java.util.Map;

public class AdminProperties
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static String getURL(String key)
		{
		return ADMIN_URLS.get(key);
		}

	private static final Map<String, String> ADMIN_URLS = new HashMap<>()
		{
			{
			put("users.get", "http://localhost:8080/users/%d");
			put("users.all", "http://localhost:8080/users/");
			}

		};
	}
