
package ch.hearc.jee.api.deezer.model.minimal;

public class MinimalGenre
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MinimalGenre(long id, String name, String picture)
		{
		// Inputs
			{
			this.id = id;
			this.name = name;
			this.picture = picture;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public long getId()
		{
		return this.id;
		}

	public String getName()
		{
		return this.name;
		}

	public String getPicture()
		{
		return this.picture;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private long id;

	private String name;
	private String picture;
	}
