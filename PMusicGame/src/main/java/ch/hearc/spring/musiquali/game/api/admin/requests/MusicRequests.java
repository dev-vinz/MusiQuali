
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.Music;
import ch.hearc.spring.musiquali.game.api.admin.models.Score;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class MusicRequests extends AdminRequests
	{
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the musics
	 * @return A request that can be executed to fetch the musics
	 */
	public AdminRequest<Music[]> getAll()
		{
		return new AdminGetRequest<>(url("musics"), Music[].class);
		}

	/**
	 * Gets a music finding by its ID
	 * @param musicId A music ID
	 * @return A request that can be executed to fetch a music
	 */
	public AdminRequest<Music> getById(long musicId)
		{
		return new AdminGetRequest<>(url("music.get", musicId), Music.class);
		}

	/**
	 * Gets the leaderboard of a music
	 * @param musicId A music ID
	 * @return A request that can be executed to fetch the leaderboard
	 */
	public AdminRequest<User[]> getLeaderboard(long musicId)
		{
		return new AdminGetRequest<>(url("music.leaderboard", musicId), User[].class);
		}

	/**
	 * Gets the scores of a music
	 * @param musicId A music ID
	 * @return A request that can be executed to fetch the scores
	 */
	public AdminRequest<Score[]> getScores(long musicId)
		{
		return new AdminGetRequest<>(url("music.scores", musicId), Score[].class);
		}
	}
