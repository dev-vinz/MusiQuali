
package ch.hearc.spring.musiquali.admin.api.deezer.requests;

import ch.hearc.spring.musiquali.admin.api.deezer.http.DeezerGetRequest;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;

public class TrackRequests extends DeezerRequests
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public DeezerGetRequest<Track> getById(long trackId)
		{
		return new DeezerGetRequest<>(url("track.id", trackId), Track.class);
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
