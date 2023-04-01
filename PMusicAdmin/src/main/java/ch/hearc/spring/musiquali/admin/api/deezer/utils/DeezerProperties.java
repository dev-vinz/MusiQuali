
package ch.hearc.spring.musiquali.admin.api.deezer.utils;

import java.util.HashMap;
import java.util.Map;

public final class DeezerProperties
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static String getURL(String key)
		{
		return DEEZER_URLS.get(key);
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

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final Map<String, String> DEEZER_URLS = new HashMap<>()
		{

			{
			put("album.get", "https://api.deezer.com/album/%d");
			put("album.tracks", "https://api.deezer.com/album/%d/tracks");
			put("artist.get", "https://api.deezer.com/artist/%d");
			put("artist.albums", "https://api.deezer.com/artist/%d/albums");
			put("search.album", "https://api.deezer.com/search/album");
			put("search.artist", "https://api.deezer.com/search/artist");
			put("search.track", "https://api.deezer.com/search/track");
			put("track.get", "https://api.deezer.com/track/%d");
			}
		};
	}
