
package ch.hearc.spring.musiquali.admin.security.payload.request;

public class LoginRequest
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public LoginRequest(String email, String password)
		{
		// Inputs & Outputs
			{
			this.email = email;
			this.password = password;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getEmail()
		{
		return this.email;
		}

	public String getPassword()
		{
		return this.password;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private String email;
	private String password;
	}
