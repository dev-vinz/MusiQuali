
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.http.DeezerGetRequest;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Album;
import ch.hearc.spring.musiquali.admin.api.deezer.models.data.TrackData;

public class AlbumRequests extends DeezerRequests
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Gets an album finding by its ID in Deezer
	 * @param albumId An album ID
	 * @return A request that can be executed to fetch an album
	 */
	public DeezerGetRequest<Album> getById(long albumId)
		{
		return new DeezerGetRequest<>(url("album.get", albumId), Album.class);
		}

	/**
	 * Gets all the tracks of an album
	 * @param albumId An album ID
	 * @return A request that can be executed to fetch all the tracks of an album
	 */
	public DeezerGetRequest<TrackData> getTracks(long albumId)
		{
		return new DeezerGetRequest<>(url("album.tracks", albumId), TrackData.class);
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
