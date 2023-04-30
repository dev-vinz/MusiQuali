
package ch.hearc.spring.musiquali.admin.models.rest;

public enum MusicOrder
	{

ID, //
ARTIST, //
TITLE, //
DIFFICULTY;

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public int getComparator(Music m1, Music m2)
		{
		switch(this)
			{
			case ARTIST:
				return m1.getArtist().compareTo(m2.getArtist());
			case DIFFICULTY:
				return m1.getDifficulty().compareTo(m2.getDifficulty());
			case ID:
				return m1.getId().compareTo(m2.getId());
			case TITLE:
				return m1.getTitle().compareTo(m2.getTitle());
			default:
				throw new IllegalArgumentException("Unexpected value: " + this);
			}
		}
	}
