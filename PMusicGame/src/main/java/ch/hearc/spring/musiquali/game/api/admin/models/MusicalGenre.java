
package ch.hearc.spring.musiquali.game.api.admin.models;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class MusicalGenre
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	protected MusicalGenre()
		{
		}

	/**
	 * Constructor building a musical genre for the REST part
	 * @param id An ID
	 * @param name A name
	 * @param musics A set containing all the musics that has this genre
	 */
	public MusicalGenre(Long id, String name, Set<Music> musics)
		{
		// Inputs & Outputs
			{
			this.id = id;
			this.name = name;
			this.musics = Optional.ofNullable(musics).orElse(new HashSet<>());
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				equals			*|
	\*------------------------------*/

	/**
	 * Custom equals override
	 * @param musicalGenre A musical genre
	 * @return True if musical genres are equals; False otherwise
	 */
	public boolean isEquals(MusicalGenre musicalGenre)
		{
		if (this == musicalGenre)
			{
			return true;
			}
		else
			{
			return this.id.longValue() == musicalGenre.id.longValue();
			}
		}

	@Override
	public boolean equals(Object object2)
		{
		if (object2.getClass().equals(this.getClass()))
			{
			return isEquals((MusicalGenre)object2);
			}
		else
			{
			return false;
			}
		}

	@Override
	public int hashCode()
		{
		return Long.hashCode(this.id);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets the ID
	 * @return An ID
	 */
	public Long getId()
		{
		return this.id;
		}

	/**
	 * Gets the name
	 * @return A name
	 */
	public String getName()
		{
		return this.name;
		}

	/**
	 * Gets the set containing all the music that has this musical genre
	 * @return A read-only set containing musics
	 */
	public Set<Music> getMusics()
		{
		return this.musics;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;

	private String name;

	@JsonIgnoreProperties(value = "genres", allowSetters = true)
	private Set<Music> musics;
	}
