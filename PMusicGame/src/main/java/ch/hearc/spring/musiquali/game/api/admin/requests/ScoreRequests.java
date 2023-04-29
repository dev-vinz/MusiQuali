
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminPostRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.Score;

public class ScoreRequests extends AdminRequests
	{
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the scores
	 * @return A request that can be executed to fetch the scores
	 */
	public AdminRequest<Score[]> getAll()
		{
		return new AdminGetRequest<>(url("scores"), Score[].class);
		}

	/**
	 * Gets a score finding by its ID
	 * @param scoreId A score ID
	 * @return A request that can be executed to fetch a score
	 */
	public AdminRequest<Score> getById(long scoreId)
		{
		return new AdminGetRequest<>(url("score.get", scoreId), Score.class);
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	/**
	 * Adds a new score in the ADMIN database
	 * @param score A score
	 * @return A request that can be executed to save a score
	 */
	public AdminRequest<Score> add(Score score)
		{
		return new AdminPostRequest<>("score.save", score, Score.class);
		}

	}
