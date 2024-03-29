
package ch.hearc.spring.musiquali.admin.models.database;

import java.util.HashSet;
import java.util.Set;

import ch.hearc.spring.musiquali.admin.models.Difficulty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "musics")
public class DbMusic
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	public DbMusic()
		{
		// Outputs
			{
			this.genres = new HashSet<DbMusicalGenre>();
			}
		}

	/**
	 * Constructor building a music with an external track ID and a difficulty
	 * @param trackId An external track ID
	 * @param difficulty A difficulty
	 */
	public DbMusic(Long trackId, Difficulty difficulty)
		{
		// Inputs
			{
			this.trackId = trackId;
			this.difficulty = difficulty;
			}

		// Outputs
			{
			this.genres = new HashSet<DbMusicalGenre>();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Adds a genre to the music
	 * @param genre A musical genre
	 */
	public void addGenre(DbMusicalGenre genre)
		{
		this.genres.add(genre);
		}

	/*------------------------------*\
	|*				equals			*|
	\*------------------------------*/

	/**
	 * Custom equals override
	 * @param music A music
	 * @return True if musics are equals; False otherwise
	 */
	public boolean isEquals(DbMusic music)
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
			return isEquals((DbMusic)object2);
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
	 * Gets the external track ID
	 * @return An external ID
	 */
	public Long getTrackId()
		{
		return this.trackId;
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
	 * Gets all the musical genres associated
	 * @return A set containing all the genres
	 */
	public Set<DbMusicalGenre> getGenres()
		{
		return this.genres;
		}

	/**
	 * Gets all the scores associat3ed
	 * @return A set containing all the scores
	 */
	public Set<DbScore> getScores()
		{
		return this.scores;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * Sets a new difficulty
	 * @param difficulty A difficulty
	 */
	public void setDifficulty(Difficulty difficulty)
		{
		this.difficulty = difficulty;
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

	@Enumerated(EnumType.ORDINAL)
	private Difficulty difficulty;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "musics_genres", joinColumns = { @JoinColumn(name = "music_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private Set<DbMusicalGenre> genres;

	@OneToMany(mappedBy = "music", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<DbScore> scores;
	}
