
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.http.DeezerGetRequest;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Album;

public class AlbumRequests extends DeezerRequests
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public DeezerGetRequest<Album> getById(long albumId)
		{
		return new DeezerGetRequest<>(url("album.get", albumId), Album.class);
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
