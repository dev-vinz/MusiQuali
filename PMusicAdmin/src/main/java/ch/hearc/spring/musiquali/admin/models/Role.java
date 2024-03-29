
package ch.hearc.spring.musiquali.admin.models;

public enum Role
	{

USER(0), //
MODERATOR(1), //
ADMIN(2);

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private Role(int id)
		{
		// Input & Output
			{
			this.id = id;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int getId()
		{
		return this.id;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input & Output
	private int id;
	}
