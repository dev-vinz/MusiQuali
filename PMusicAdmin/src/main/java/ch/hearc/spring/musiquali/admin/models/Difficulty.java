
package ch.hearc.spring.musiquali.admin.models;

public enum Difficulty
	{

EASY(0, "Facile"), //
MEDIUM(1, "Medium"), //
ADVANCED(2, "Avancé"), //
EXTREME(3, "Extrême");

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private Difficulty(Integer id, String frenchName)
		{
		// Input
			{
			this.id = Integer.valueOf(id);
			this.frenchName = frenchName;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Integer getId()
		{
		return this.id;
		}

	public String getFrenchName()
		{
		return this.frenchName;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private Integer id;
	private String frenchName;
	}
