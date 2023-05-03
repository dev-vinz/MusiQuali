
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminDeleteRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminPostRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminPutRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.Score;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class UserRequests extends AdminRequests
	{
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the users
	 * @return A request that can be executed to fetch the users
	 */
	public AdminRequest<User[]> getAll()
		{
		return new AdminGetRequest<>(url("users"), User[].class);
		}

	/**
	 * Gets an user finding by its ID
	 * @param userId An user ID
	 * @return A request that can be executed to fetch an user
	 */
	public AdminRequest<User> getById(long userId)
		{
		return new AdminGetRequest<>(url("user.get.id", userId), User.class);
		}

	/**
	 * Gets an user finding by its email
	 * @param email An user email
	 * @return A request that can be executed to fetch an user
	 */
	public AdminRequest<User> getByEmail(String email)
		{
		return new AdminGetRequest<>(url("user.get.email", email), User.class);
		}

	/**
	 * Gets the scores of an user
	 * @param userId An user ID
	 * @return A request that can be executed to fetch the scores
	 */
	public AdminRequest<Score[]> getScores(long userId)
		{
		return new AdminGetRequest<>(url("user.scores", userId), Score[].class);
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	/**
	 * Adds a new user in the ADMIN database
	 * @param user An user
	 * @return A request that can be executed to save an user
	 */
	public AdminRequest<User> add(User user)
		{
		return new AdminPostRequest<>(url("user.save"), user, User.class);
		}

	/*------------------------------*\
	|*				Put				*|
	\*------------------------------*/

	/**
	 * Updates an user in the ADMIN database
	 * @param user An user
	 * @return A request that can be executed to update an user
	 */
	public AdminRequest<User> update(User user)
		{
		return new AdminPutRequest<>(url("user.update", user.getId()), user, User.class);
		}

	/*------------------------------*\
	|*				Delete			*|
	\*------------------------------*/

	/**
	 * Deletes an user from the ADMIN database
	 * @param userId An user ID
	 * @return A request that can be executed to delete an user
	 */
	public AdminRequest<Boolean> delete(long userId)
		{
		return new AdminDeleteRequest<>(url("user.delete", userId), Boolean.class);
		}
	}
