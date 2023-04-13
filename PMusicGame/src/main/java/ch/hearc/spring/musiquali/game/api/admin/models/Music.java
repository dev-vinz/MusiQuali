
package ch.hearc.spring.musiquali.game.api.admin.models;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Music
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Constructor building a music for the REST part
	 * @param id An ID
	 * @param title A title
	 * @param artist An artist name
	 * @param preview A MP3 link containing a preview
	 * @param difficulty A difficulty
	 * @param duration A duration in seconds
	 * @param link An external link refering to the music
	 * @param genres A set containing all the musical genres
	 * @param scores A set containing all the scores
	 */
	public Music(Long id, String title, String artist, String preview, Difficulty difficulty, Integer duration, String link, Set<MusicalGenre> genres, Set<Score> scores)
		{
		// Inputs & Outputs
			{
			this.id = id;
			this.title = title;
			this.artist = artist;
			this.preview = preview;
			this.difficulty = difficulty;
			this.duration = duration;
			this.link = link;
			this.genres = genres;
			this.scores = scores;
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
	 * Gets the title
	 * @return A title
	 */
	public String getTitle()
		{
		return this.title;
		}

	/**
	 * Gets the artist name
	 * @return An artist name
	 */
	public String getArtist()
		{
		return this.artist;
		}

	/**
	 * Gets the MP3 link of the preview
	 * @return A link of a preview
	 */
	public String getPreview()
		{
		return this.preview;
		}

	/**
	 * Gets the difficulty
	 * @return A difficulty
	 */
	public Difficulty getDifficulty()
		{
		return this.difficulty;
		}

	/**
	 * Gets the duration in seconds
	 * @return A duration
	 */
	public Integer getDuration()
		{
		return this.duration;
		}

	/**
	 * Gets the external link
	 * @return An external link refering to the music
	 */
	public String getLink()
		{
		return this.link;
		}

	/**
	 * Gets the set containing all the musical genres
	 * @return A read-only set containing musical genres
	 */
	public Set<MusicalGenre> getGenres()
		{
		return Collections.unmodifiableSet(this.genres);
		}

	/**
	 * Gets the set containing all the scores
	 * @return A read-only set containing scores
	 */
	public Set<Score> getScores()
		{
		return Collections.unmodifiableSet(this.scores);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;

	private String title;
	private String artist;
	private String preview;

	private Difficulty difficulty;
	private Integer duration;
	private String link;

	@JsonIgnoreProperties("musics")
	private Set<MusicalGenre> genres;
	@JsonIgnoreProperties("music")
	private Set<Score> scores;
	}
