
package ch.hearc.spring.musiquali.admin.api.deezer.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Track
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
	 * Get the ID
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
	 * Gets the title short version
	 * @return A short title
	 */
	public String getTitleShort()
		{
		return this.titleShort;
		}

	/**
	 * Gets the title version
	 * @return A title version
	 */
	public String getTitleVersion()
		{
		return this.titleVersion;
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
	 * Gets the duration in seconds
	 * @return A duration
	 */
	public Integer getDuration()
		{
		return this.duration;
		}

	/**
	 * Gets the track position
	 * @return A position
	 */
	public Integer getTrackPosition()
		{
		return this.trackPosition;
		}

	/**
	 * Gets the disk number
	 * @return A disk number
	 */
	public Integer getDiskNumber()
		{
		return this.diskNumber;
		}

	/**
	 * Gets the rank
	 * @return A rank
	 */
	public Integer getRank()
		{
		return this.rank;
		}

	/**
	 * Gets the preview of 30 seconds
	 * @return A preview
	 */
	public String getPreview()
		{
		return this.preview;
		}

	/**
	 * Gets the BPM
	 * @return A BPM
	 */
	public Float getBpm()
		{
		return this.bpm;
		}

	/**
	 * Gets the available countries
	 * @return A list containing the countries where the track is available
	 */
	public List<String> getAvailableCountries()
		{
		return this.availableCountries;
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
	 * Gets the album
	 * @return An album
	 */
	public Album getAlbum()
		{
		return this.album;
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

	@JsonProperty("title_short")
	private String titleShort;

	@JsonProperty("title_version")
	private String titleVersion;

	@JsonProperty("link")
	private String link;

	@JsonProperty("duration")
	private Integer duration;

	@JsonProperty("track_position")
	private Integer trackPosition;

	@JsonProperty("disk_number")
	private Integer diskNumber;

	@JsonProperty("rank")
	private Integer rank;

	@JsonProperty("preview")
	private String preview;

	@JsonProperty("bpm")
	private Float bpm;

	@JsonProperty("available_countries")
	private List<String> availableCountries;

	@JsonProperty("artist")
	private Artist artist;

	@JsonProperty("album")
	private Album album;
	}
