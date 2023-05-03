
package ch.hearc.spring.musiquali.game.api.admin.models;

public class JwtResponse
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	protected JwtResponse()
		{
		}

	public JwtResponse(String accessToken, Long id, String email)
		{
		// Inputs & Outputs
			{
			this.accessToken = accessToken;
			this.id = id;
			this.email = email;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Long getId()
		{
		return this.id;
		}

	public String getEmail()
		{
		return this.email;
		}

	public String getAccessToken()
		{
		return this.accessToken;
		}

	public String getTokenType()
		{
		return TYPE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;
	private String email;

	private String accessToken;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Output
	private static final String TYPE = "Bearer";
	}
