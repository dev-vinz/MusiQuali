
package ch.hearc.spring.musiquali.game.api.admin.models;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class MusicalGenre
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

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
			this.musics = musics;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

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
		return Collections.unmodifiableSet(this.musics);
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

	@JsonIgnoreProperties("genres")
	private Set<Music> musics;
	}