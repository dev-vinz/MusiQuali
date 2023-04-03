
package ch.hearc.spring.musiquali.game.api.requests;

import ch.hearc.spring.musiquali.game.api.http.AdminGetRequest;
import ch.hearc.spring.musiquali.game.api.models.User;

public class UserRequest extends AdminRequest
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public AdminGetRequest<User> getById(long userId)
		{
		return new AdminGetRequest<>(url("users.get", userId), User.class);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
