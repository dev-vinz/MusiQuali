
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminPostRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

public class AuthRequests extends AdminRequests
	{

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	/**
	 * Loggs an user to the ADMIN section, in order to get an access token
	 * @param email An email
	 * @param password A password
	 * @return A request that can be executed to login an user
	 */
	public AdminRequest<User> login(String email, String password)
		{
		return new AdminPostRequest<>(url("auth.login"), new User(email, password), User.class);
		}
	}
