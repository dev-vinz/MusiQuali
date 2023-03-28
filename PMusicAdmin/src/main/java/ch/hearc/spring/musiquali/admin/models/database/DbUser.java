
package ch.hearc.spring.musiquali.admin.models.database;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.spring.musiquali.admin.models.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class DbUser
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	public DbUser()
		{
		// Outputs
			{
			this.scores = new ArrayList<DbScore>();
			}
		}

	/**
	 * Constructor building an user with his personal informations
	 * @param firstName A first name
	 * @param lastName A last name
	 * @param email An email
	 * @param password A hashed password
	 * @param role A role
	 */
	public DbUser(String firstName, String lastName, String email, String password, Role role)
		{
		// Inputs
			{
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;//PASSWORD_ENCODER.encode(password);
			this.role = role;
			}

		// Outputs
			{
			this.scores = new ArrayList<DbScore>();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Adds a score to the user
	 * @param score A score
	 */
	public void addScore(DbScore score)
		{
		this.scores.add(score);
		}

	/*------------------------------*\
	|*				equals			*|
	\*------------------------------*/

	/**
	 * Custom equals override
	 * @param user An user
	 * @return True if users are equals; False otherwise
	 */
	public boolean isEquals(DbUser user)
		{
		if (this == user)
			{
			return true;
			}
		else
			{
			return this.id.longValue() == user.id.longValue();
			}
		}

	@Override
	public boolean equals(Object object2)
		{
		if (object2.getClass().equals(this.getClass()))
			{
			return isEquals((DbUser)object2);
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
	 * Gets the first name
	 * @return A first name
	 */
	public String getFirstName()
		{
		return this.firstName;
		}

	/**
	 * Gets the last name
	 * @return A last name
	 */
	public String getLastName()
		{
		return this.lastName;
		}

	/**
	 * Gets the email
	 * @return An email
	 */
	public String getEmail()
		{
		return this.email;
		}

	/**
	 * Gets the hashed password
	 * @return A hashed password
	 */
	public String getPassword()
		{
		return this.password;
		}

	/**
	 * Gets the role
	 * @return A role
	 */
	public Role getRole()
		{
		return this.role;
		}

	/**
	 * Gets all the scores
	 * @return A list containing all the scores
	 */
	public List<DbScore> getScores()
		{
		return this.scores;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * Sets the first name
	 * @param firstName A first name
	 */
	public void setFirstName(String firstName)
		{
		this.firstName = firstName;
		}

	/**
	 * Sets the last name
	 * @param lastName A last name
	 */
	public void setLastName(String lastName)
		{
		this.lastName = lastName;
		}

	/**
	 * Sets the email
	 * @param email An email
	 */
	public void setEmail(String email)
		{
		this.email = email;
		}

	/**
	 * Hashes and sets the password
	 * @param password A clear password
	 */
	public void setPassword(String password)
		{
		this.password = password; //PASSWORD_ENCODER.encode(password);
		}

	/**
	 * Sets the role
	 * @param role A role
	 */
	public void setRole(Role role)
		{
		this.role = role;
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

	private String firstName;
	private String lastName;
	private String email;

	private String password;

	@Enumerated(EnumType.ORDINAL)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<DbScore> scores;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	//private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	}
