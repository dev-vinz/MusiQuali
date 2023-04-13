
package ch.hearc.spring.musiquali.admin.api.deezer;

import ch.hearc.spring.musiquali.admin.api.deezer.requests.AlbumRequests;
import ch.hearc.spring.musiquali.admin.api.deezer.requests.ArtistRequests;
import ch.hearc.spring.musiquali.admin.api.deezer.requests.GenreRequests;
import ch.hearc.spring.musiquali.admin.api.deezer.requests.SearchRequests;
import ch.hearc.spring.musiquali.admin.api.deezer.requests.TrackRequests;

public final class DeezerApi
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private DeezerApi()
		{
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final AlbumRequests albums = new AlbumRequests();
	public static final ArtistRequests artists = new ArtistRequests();
	public static final GenreRequests genres = new GenreRequests();
	public static final SearchRequests searchs = new SearchRequests();
	public static final TrackRequests tracks = new TrackRequests();
	}
