
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.http.DeezerGetRequest;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Artist;
import ch.hearc.spring.musiquali.admin.api.deezer.models.data.AlbumData;

public class ArtistRequests extends DeezerRequests
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Gets an artist finding by its ID in Deezer
	 * @param artistId An artist ID
	 * @return A request that can be executed to fetch an artist
	 */
	public DeezerGetRequest<Artist> getById(long artistId)
		{
		return new DeezerGetRequest<>(url("artist.get", artistId), Artist.class);
		}

	/**
	 * Gets all the albums of an artist
	 * @param artistId An artist ID
	 * @return A request that can be executed to fetch all the albums of an artist
	 */
	public DeezerGetRequest<AlbumData> getAlbums(long artistId)
		{
		return new DeezerGetRequest<>(url("artist.albums", artistId), AlbumData.class);
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
