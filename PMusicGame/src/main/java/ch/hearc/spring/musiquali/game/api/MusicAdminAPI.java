
package ch.hearc.spring.musiquali.game.api;

import ch.hearc.spring.musiquali.game.api.requests.MusicRequest;
import ch.hearc.spring.musiquali.game.api.requests.UserRequest;

public final class MusicAdminAPI
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private MusicAdminAPI()
		{
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final UserRequest users = new UserRequest();
	public static final MusicRequest musics = new MusicRequest();
	}
