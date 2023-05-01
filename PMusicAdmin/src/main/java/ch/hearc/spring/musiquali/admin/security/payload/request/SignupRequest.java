
package ch.hearc.spring.musiquali.admin.security.payload.request;

public class SignupRequest
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public SignupRequest(String firstName, String lastName, String email, String password)
		{
		// Inputs & Outputs
			{
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			// Role ?
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getFirstName()
		{
		return this.firstName;
		}

	public String getLastName()
		{
		return this.lastName;
		}

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
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	}
