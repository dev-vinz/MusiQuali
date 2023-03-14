
package ch.hearc.jee.model;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.hearc.jee.api.deezer.DeezerApi;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "musics")
public class Music
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Music()
		{
		// Outputs
			{
			this.genres = new HashSet<MusicalGenre>();
			this.scores = new HashSet<Score>();
			}
		}

	public Music(Long trackId, String author, String cover, String title, String preview)
		{
		// Inputs
			{
			this.trackId = trackId;
			this.author = author;
			this.cover = cover;
			this.title = title;
			this.preview = preview;
			}

		// Outputs
			{
			this.genres = new HashSet<MusicalGenre>();
			this.scores = new HashSet<Score>();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void addGenre(MusicalGenre genre)
		{
		this.genres.add(genre);
		}

	public void addScore(Score score)
		{
		this.scores.add(score);
		}

	/*------------------------------*\
	|*				equals			*|
	\*------------------------------*/

	public boolean isEquals(Music music)
		{
		if (this == music)
			{
			return true;
			}
		else
			{
			return this.id.longValue() == music.id.longValue();
			}
		}

	@Override
	public boolean equals(Object object2)
		{
		if (object2.getClass().equals(this.getClass()))
			{
			return isEquals((Music)object2);
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

	public Long getId()
		{
		return this.id;
		}

	public Long getTrackId()
		{
		return this.trackId;
		}

	public String getTrackLink()
		{
		return DeezerApi.trackById(this.trackId).getLink();
		}

	public String getAuthor()
		{
		return this.author;
		}

	public String getCover()
		{
		return this.cover;
		}

	public String getTitle()
		{
		return this.title;
		}

	public String getPreview()
		{
		return this.preview;
		}

	public Set<MusicalGenre> getGenres()
		{
		return this.genres;
		}

	public Set<Score> getScores()
		{
		return this.scores;
		}

	public User getUser()
		{
		return this.user;
		}

	@JsonIgnore
	public String getSuccessRateArtist()
		{

		double length = this.scores.size();

		if (length == 0)
			{ return "-"; }

		int somme = this.scores//
				.stream()//
				.mapToInt(Score::getArtistScore)//
				.sum();

		double successRate = somme / length;

		return TWO_DECIMAL_FORMAT.format(successRate) + "%";
		}

	@JsonIgnore
	public String getSuccessDegArtist()
		{
		// Have to be between 0 and 180deg

		double length = this.scores.size();

		if (length == 0)
			{ return "0deg"; }

		int somme = this.scores//
				.stream()//
				.mapToInt(Score::getArtistScore)//
				.sum();

		double rate = somme / length / 100 * 180;

		return rate + "deg";
		}

	@JsonIgnore
	public String getSuccessRateTitle()
		{

		double length = this.scores.size();

		if (length == 0)
			{ return "-"; }

		int somme = this.scores//
				.stream()//
				.mapToInt(Score::getTitleScore)//
				.sum();

		double successRate = somme / length;

		return TWO_DECIMAL_FORMAT.format(successRate) + "%";
		}

	@JsonIgnore
	public String getSuccessDegTitle()
		{
		// Have to be between 0 and 180deg

		double length = this.scores.size();

		if (length == 0)
			{ return "0deg"; }

		int somme = this.scores//
				.stream()//
				.mapToInt(Score::getTitleScore)//
				.sum();

		double rate = somme / length / 100 * 180;

		return rate + "deg";
		}

	@JsonIgnore
	public String getSuccessRateGlobal()
		{
		double length = this.scores.size();

		if (length == 0)
			{ return "-"; }

		int sommeArtist = this.scores//
				.stream()//
				.mapToInt(Score::getArtistScore)//
				.sum();

		int sommeTitle = this.scores//
				.stream()//
				.mapToInt(Score::getTitleScore)//
				.sum();

		int somme = sommeArtist + sommeTitle;

		double successRate = somme / (2 * length);

		return INTEGER_FORMAT.format(successRate) + "%";
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setUser(User user)
		{
		this.user = user;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long trackId;

	private String author;
	private String cover;
	private String title;
	private String preview;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "musics_genres", joinColumns = { @JoinColumn(name = "music_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@JsonIgnoreProperties("musics")
	private Set<MusicalGenre> genres;

	@OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnoreProperties("music")
	private Set<Score> scores;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnoreProperties("musics")
	private User user;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final DecimalFormat TWO_DECIMAL_FORMAT = new DecimalFormat("0.00");
	private static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("0");
	}
