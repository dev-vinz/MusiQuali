
package ch.hearc.spring.musiquali.game.api.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Score
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Constructor building a score for the REST part
	 * @param id An ID
	 * @param artistValue A score value between 0 and 100
	 * @param titleValue A score value between 0 and 100
	 * @param user An user
	 * @param music A music
	 */
	public Score(Long id, Integer artistValue, Integer titleValue, User user, Music music)
		{
		// Inputs & Outputs
			{
			this.id = id;
			this.artistValue = artistValue;
			this.titleValue = titleValue;
			this.user = user;
			this.music = music;
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
	 * Gets the score value corresponding to the artist
	 * @return A score value between 0 and 100
	 */
	public Integer getArtistValue()
		{
		return this.artistValue;
		}

	/**
	 * Gets the score value corresponding to the title
	 * @return A score value between 0 and 100
	 */
	public Integer getTitleValue()
		{
		return this.titleValue;
		}

	/**
	 * Gets the user who is associated with this instance
	 * @return An user
	 */
	public User getUser()
		{
		return this.user;
		}

	/**
	 * Gets the music that is associated with this instance
	 * @return A music
	 */
	public Music getMusic()
		{
		return this.music;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;

	private Integer artistValue;
	private Integer titleValue;

	@JsonIgnoreProperties("scores")
	private User user;
	@JsonIgnoreProperties("scores")
	private Music music;
	}
