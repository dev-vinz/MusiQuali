
package ch.hearc.spring.musiquali.game.api.admin.requests;

import ch.hearc.spring.musiquali.game.api.admin.http.AdminPostRequest;
import ch.hearc.spring.musiquali.game.api.admin.http.AdminRequest;
import ch.hearc.spring.musiquali.game.api.admin.models.JwtResponse;
import ch.hearc.spring.musiquali.game.api.admin.models.SignInRequest;

public class AuthRequests extends AdminRequests
	{

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	public AdminRequest<JwtResponse> signIn(String email, String password)
		{
		SignInRequest request = new SignInRequest(email, password);

		return new AdminPostRequest<>(url("auth.signin"), request, JwtResponse.class);
		}
	}
