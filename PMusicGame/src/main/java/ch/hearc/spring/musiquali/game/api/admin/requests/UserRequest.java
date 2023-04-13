
package ch.hearc.spring.musiquali.game.api.requests;

import ch.hearc.spring.musiquali.game.api.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.http.AdminPostRequest;
import ch.hearc.spring.musiquali.game.api.http.AdminPutRequest;
import ch.hearc.spring.musiquali.game.models.User;

public class UserRequest extends AdminRequest
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public AdminGetRequest<User> getById(long userId)
		{
		return new AdminGetRequest<>(url("users.id.get", userId), User.class);
		}

	public AdminGetRequest<User> getByEmail(String userEmail)
		{
		return new AdminGetRequest<>(url("users.email.get", userEmail), User.class);
		}

	public AdminPostRequest<User> save(User user)
		{
		return new AdminPostRequest<>(url("users.save"), user, User.class);
		}

	public AdminPutRequest<User> update(User user, long userId)
		{
		return new AdminPutRequest<>(url("users.update", userId), user, User.class);
		}
	}
