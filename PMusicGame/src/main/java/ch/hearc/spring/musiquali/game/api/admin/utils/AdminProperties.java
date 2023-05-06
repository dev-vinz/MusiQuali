
package ch.hearc.spring.musiquali.game.api.admin.utils;

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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String PORT = "8081";
	private static final String HOST = "localhost";
	private static final String BASE_URL = "http://" + HOST + ":" + PORT + "/api"; // --> http://localhost:8081/api

	private static final Map<String, String> ADMIN_URLS = new HashMap<>()
		{

			{
			put("auth.login", BASE_URL + "/auth/login");

			put("difficulties", BASE_URL + "/difficulties");
			put("difficulty.get", BASE_URL + "/difficulties/%d");
			put("difficulty.leaderboard", BASE_URL + "/difficulties/%d/leaderboard");
			put("difficulty.leaderboard.position", BASE_URL + "/difficulties/%d/leaderboard/%d");
			put("difficulty.musics", BASE_URL + "/difficulties/%d/musics");

			put("genres", BASE_URL + "/genres");
			put("genre.get", BASE_URL + "/genres/%d");
			put("genre.leaderboard", BASE_URL + "/genres/%d/leaderboard");
			put("genre.leaderboard.position", BASE_URL + "/genres/%d/leaderboard/%d");
			put("genre.musics", BASE_URL + "/genres/%d/musics");
			put("genre.scores", BASE_URL + "/genres/%d/scores");

			put("musics", BASE_URL + "/musics");
			put("music.get", BASE_URL + "/musics/%d");
			put("music.leaderboard", BASE_URL + "/musics/%d/leaderboard");
			put("music.leaderboard.position", BASE_URL + "/musics/%d/leaderboard/%d");
			put("music.scores", BASE_URL + "/musics/%d/scores");

			put("scores", BASE_URL + "/scores");
			put("score.get", BASE_URL + "/scores/%d");
			put("score.save", BASE_URL + "/scores");

			put("users", BASE_URL + "/users");
			put("user.get.id", BASE_URL + "/users/%d");
			put("user.get.email", BASE_URL + "/users/email/%s");
			put("user.leaderboard", BASE_URL + "/users/leaderboard");
			put("user.leaderboard.position", BASE_URL + "/users/%d/leaderboard");
			put("user.scores", BASE_URL + "/users/%d/scores");
			put("user.save", BASE_URL + "/users");
			put("user.update", BASE_URL + "/users/%d");
			put("user.delete", BASE_URL + "/users/%d");
			}

		};
	}
