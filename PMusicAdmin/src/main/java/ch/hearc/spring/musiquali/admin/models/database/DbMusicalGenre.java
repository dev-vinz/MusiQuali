
package ch.hearc.spring.musiquali.admin.models.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "genres")
public class DbMusicalGenre
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	public DbMusicalGenre()
		{
		// Outputs
			{
			this.musics = new HashSet<DbMusic>();
			}
		}

	/**
	 * Constructor building a musical genre with an external genre ID
	 * @param genreId An external genre ID
	 */
	public DbMusicalGenre(Long genreId)
		{
		// Inputs
			{
			this.genreId = genreId;
			}

		// Outputs
			{
			this.musics = new HashSet<DbMusic>();
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
	 * @return True if genres are equals; False otherwise
	 */
	public boolean isEquals(DbMusicalGenre musicalGenre)
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
			return isEquals((DbMusicalGenre)object2);
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
	 * Gets the external genre ID
	 * @return An external ID
	 */
	public Long getGenreId()
		{
		return this.genreId;
		}

	/**
	 * Gets all the musics associated
	 * @return A set containing all the musics
	 */
	public Set<DbMusic> getMusics()
		{
		return this.musics;
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

	private Long genreId;

	@ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
	private Set<DbMusic> musics;
	}
