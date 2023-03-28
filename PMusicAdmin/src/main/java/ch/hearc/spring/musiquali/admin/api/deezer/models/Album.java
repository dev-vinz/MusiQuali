
package ch.hearc.spring.musiquali.admin.api.deezer.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public Long getId()
		{
		return this.id;
		}

	public String getTitle()
		{
		return this.title;
		}

	public String getLink()
		{
		return this.link;
		}

	public String getCoverSmall()
		{
		return this.coverSmall;
		}

	public String getCoverMedium()
		{
		return this.coverMedium;
		}

	public String getCoverBig()
		{
		return this.coverBig;
		}

	public String getCoverXL()
		{
		return this.coverXL;
		}

	public List<Genre> getGenres()
		{
		return this.genres.getData();
		}

	public Integer getNbTracks()
		{
		return this.nbTracks;
		}

	public Integer getNbDuration()
		{
		return this.nbDuration;
		}

	public Artist getArtist()
		{
		return this.artist;
		}

	public List<Track> getTracks()
		{
		return this.tracks.getData();
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
