
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.Music;
import ch.hearc.spring.musiquali.game.api.admin.models.MusicalGenre;
import ch.hearc.spring.musiquali.game.api.admin.models.Score;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class MusicalGenreRequests extends AdminRequests
	{
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the musical genres
	 * @return A request that can be executed to fetch the musical genres
	 */
	public AdminRequest<MusicalGenre[]> getAll()
		{
		return new AdminGetRequest<>(url("genres"), MusicalGenre[].class);
		}

	/**
	 * Gets a musical genre finding by its ID
	 * @param genreId A musical genre ID
	 * @return A request that can be executed to fetch a musical genre
	 */
	public AdminRequest<MusicalGenre> getById(long genreId)
		{
		return new AdminGetRequest<>(url("genre.get", genreId), MusicalGenre.class);
		}

	/**
	 * Gets the leaderboard of a musical genre
	 * @param genreId A musical genre ID
	 * @return A request that can be executed to fetch the leaderboard
	 */
	public AdminRequest<User[]> getLeaderboard(long genreId)
		{
		return new AdminGetRequest<>(url("genre.leaderboard", genreId), User[].class);
		}

	/**
	 * Gets the user's position in the leaderboard
	 * @param genreId A musical genre ID
	 * @param userId An user ID
	 * @return A request that can be executed to fetch user's position
	 */
	public AdminRequest<Long> getLeaderboardPosition(long genreId, long userId)
		{
		return new AdminGetRequest<>(url("genre.leaderboard.position", genreId, userId), Long.class);
		}

	/**
	 * Gets the musics of a musical genre
	 * @param genreId A musical genre ID
	 * @return A request that can be executed to fetch the musics
	 */
	public AdminRequest<Music[]> getMusics(long genreId)
		{
		return new AdminGetRequest<>(url("genre.musics", genreId), Music[].class);
		}

	/**
	 * Gets the scores of a musical genre
	 * @param genreId A musical genre ID
	 * @return A request that can be executed to fetch the scores
	 */
	public AdminRequest<Score[]> getScores(long genreId)
		{
		return new AdminGetRequest<>(url("genre.scores", genreId), Score[].class);
		}

	}
