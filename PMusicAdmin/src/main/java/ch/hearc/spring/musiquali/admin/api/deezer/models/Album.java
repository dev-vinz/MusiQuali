
package ch.hearc.spring.musiquali.admin.api.deezer.models;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an album on Deezer
 */
public class Album
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

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
	 * Gets the link
	 * @return A link
	 */
	public String getLink()
		{
		return this.link;
		}

	/**
	 * Gets the cover in small size
	 * @return A small cover
	 */
	public String getCoverSmall()
		{
		return this.coverSmall;
		}

	/**
	 * Gets the cover in medium size
	 * @return A medium cover
	 */
	public String getCoverMedium()
		{
		return this.coverMedium;
		}

	/**
	 * Gets the cover in big size
	 * @return A big cover
	 */
	public String getCoverBig()
		{
		return this.coverBig;
		}

	/**
	 * Gets the cover in XL size
	 * @return A XL size
	 */
	public String getCoverXL()
		{
		return this.coverXL;
		}

	/**
	 * Gets all the genres
	 * @return A read-only list containing all the genres
	 */
	public List<Genre> getGenres()
		{
		return Collections.unmodifiableList(this.genres.getData());
		}

	/**
	 * Gets the number of tracks
	 * @return A number of tracks
	 */
	public Integer getNbTracks()
		{
		return this.nbTracks;
		}

	/**
	 * Gets the duration in seconds
	 * @return A duration
	 */
	public Integer getNbDuration()
		{
		return this.nbDuration;
		}

	/**
	 * Gets the artist
	 * @return An artist
	 */
	public Artist getArtist()
		{
		return this.artist;
		}

	/**
	 * Gets the tracks
	 * @return A read-only list containing all the tracks
	 */
	public List<Track> getTracks()
		{
		return Collections.unmodifiableList(this.tracks.getData());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@JsonProperty("id")
	private Long id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("link")
	private String link;

	@JsonProperty("cover_small")
	private String coverSmall;

	@JsonProperty("cover_medium")
	private String coverMedium;

	@JsonProperty("cover_big")
	private String coverBig;

	@JsonProperty("cover_xl")
	private String coverXL;

	@JsonProperty("genres")
	private DeezerData<Genre> genres;

	@JsonProperty("nb_tracks")
	private Integer nbTracks;

	@JsonProperty("duration")
	private Integer nbDuration;

	@JsonProperty("artist")
	private Artist artist;

	@JsonProperty("tracks")
	private DeezerData<Track> tracks;
	}
