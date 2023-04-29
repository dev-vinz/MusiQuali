
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.Difficulty;
import ch.hearc.spring.musiquali.game.api.admin.models.Music;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class DifficultyRequests extends AdminRequests
	{
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the difficulties
	 * @return A request that can be executed to fetch the difficulties
	 */
	public AdminRequest<Difficulty[]> getAll()
		{
		return new AdminGetRequest<>(url("difficulties"), Difficulty[].class);
		}

	/**
	 * Gets a difficulty finding by its ID
	 * @param difficultyId A difficulty ID
	 * @return A request that can be executed to fetch a difficulty
	 */
	public AdminRequest<Difficulty> getById(long difficultyId)
		{
		return new AdminGetRequest<>(url("difficulty.get", difficultyId), Difficulty.class);
		}

	/**
	 * Gets the leaderboard of a difficulty
	 * @param difficultyId A difficulty ID
	 * @return A request that can be executed to fetch the leaderboard
	 */
	public AdminRequest<User[]> getLeaderboard(long difficultyId)
		{
		return new AdminGetRequest<>(url("difficulty.leaderboard", difficultyId), User[].class);
		}

	/**
	 * Gets the musics of a difficulty
	 * @param difficultyId A difficulty ID
	 * @return A request that can be executed to fetch the musics
	 */
	public AdminRequest<Music[]> getMusics(long difficultyId)
		{
		return new AdminGetRequest<>(url("difficulty.musics", difficultyId), Music[].class);
		}

	}
