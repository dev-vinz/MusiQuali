
package ch.hearc.spring.musiquali.game.api.admin;

import ch.hearc.spring.musiquali.game.api.admin.requests.DifficultyRequests;
import ch.hearc.spring.musiquali.game.api.admin.requests.MusicRequests;
import ch.hearc.spring.musiquali.game.api.admin.requests.MusicalGenreRequests;
import ch.hearc.spring.musiquali.game.api.admin.requests.ScoreRequests;
import ch.hearc.spring.musiquali.game.api.admin.requests.UserRequests;

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

	public static final DifficultyRequests difficulties = new DifficultyRequests();
	public static final MusicRequests musics = new MusicRequests();
	public static final MusicalGenreRequests musicalGenres = new MusicalGenreRequests();
	public static final UserRequests users = new UserRequests();
	public static final ScoreRequests scores = new ScoreRequests();

	}
