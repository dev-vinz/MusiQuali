
package ch.hearc.spring.musiquali.admin.models.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "scores")
public class DbScore
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	public DbScore()
		{
		}

	/**
	 * Constructor building a core with a score for artist and title
	 * @param artistScore A score between 0 and 100
	 * @param titleScore A score between 0 and 100
	 */
	public DbScore(Integer artistScore, Integer titleScore)
		{
		// Inputs
			{
			this.artistScore = artistScore;
			this.titleScore = titleScore;
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
	 * @param score A score
	 * @return True if scores are equals; False otherwise
	 */
	public boolean isEquals(DbScore score)
		{
		if (this == score)
			{
			return true;
			}
		else
			{
			return this.id.longValue() == score.id.longValue();
			}
		}

	@Override
	public boolean equals(Object object2)
		{
		if (object2.getClass().equals(this.getClass()))
			{
			return isEquals((DbScore)object2);
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
	 * Gets the score corresponding to the artist
	 * @return A score between 0 and 100
	 */
	public Integer getArtistScore()
		{
		return this.artistScore;
		}

	/**
	 * Gets the score corresponding to the title
	 * @return A score between 0 and 100
	 */
	public Integer getTitleScore()
		{
		return this.titleScore;
		}

	/**
	 * Gets the user who the score belongs to
	 * @return An user
	 */
	public User getUser()
		{
		return this.user;
		}

	/**
	 * Gets the music that the score belongs to
	 * @return A music
	 */
	public DbMusic getMusic()
		{
		return this.music;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * Sets the user to the score
	 * @param user An user
	 */
	public void setUser(User user)
		{
		this.user = user;
		}

	/**
	 * Sets the music to the score
	 * @param music A music
	 */
	public void setMusic(DbMusic music)
		{
		this.music = music;
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

	private Integer artistScore;
	private Integer titleScore;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "music_id", nullable = false)
	private DbMusic music;
	}
