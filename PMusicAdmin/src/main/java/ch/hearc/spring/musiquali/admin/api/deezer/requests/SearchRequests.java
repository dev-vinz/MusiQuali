
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.http.DeezerGetRequest;
import ch.hearc.spring.musiquali.admin.api.deezer.models.data.AlbumData;
import ch.hearc.spring.musiquali.admin.api.deezer.models.data.ArtistData;
import ch.hearc.spring.musiquali.admin.api.deezer.models.data.TrackData;

public class SearchRequests extends DeezerRequests
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public DeezerGetRequest<AlbumData> album(String query)
		{
		return new DeezerGetRequest<>(url("search.album"), AlbumData.class)//
				.addParam("q", query);
		}

	public DeezerGetRequest<ArtistData> artist(String query)
		{
		return new DeezerGetRequest<>(url("search.artist"), ArtistData.class)//
				.addParam("q", query);
		}

	public DeezerGetRequest<TrackData> track(String query)
		{
		return new DeezerGetRequest<>(url("search.track"), TrackData.class)//
				.addParam("q", query);
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
