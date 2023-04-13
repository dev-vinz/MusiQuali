
package ch.hearc.spring.musiquali.game.api.admin.utils;

import java.util.HashMap;
import java.util.Map;

public class AdminProperties
	{

	private static final String PORT = "8080";
	private static final String HOST = "localhost";
	private static final String BASE_URL = "http://" + HOST + ":" + PORT; // --> http://localhost:8080

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
			put("users.id.get", BASE_URL + "/users/%d");
			put("users.email.get", BASE_URL + "/users/email/%s");
			put("users.save", BASE_URL + "/users/");
			put("users.update", BASE_URL + "/users/%d}");
			put("users.all", BASE_URL + "/users/");
			}

		};
	}
