
package ch.hearc.spring.musiquali.admin.security.model;

public class AuthenticationRequest
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AuthenticationRequest()
		{
		}

	public AuthenticationRequest(String username, String password)
		{
		this.username = username;
		this.password = password;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getUsername()
		{
		return this.username;
		}

	public String getPassword()
		{
		return this.password;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setUsername(String username)
		{
		this.username = username;
		}

	public void setPassword(String password)
		{
		this.password = password;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String username;
	private String password;
	}
