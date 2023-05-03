
package ch.hearc.spring.musiquali.admin.models.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.hearc.spring.musiquali.admin.models.Role;

public class User
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Default constructor
	 */
	protected User()
		{
		}

	/**
	 * Constructor building an user for the REST part
	 * @param id An ID
	 * @param firstName A first name
	 * @param lastName A last name
	 * @param email An email
	 * @param password A hashed password
	 * @param role A role
	 * @param scores A list containing all user' scores
	 */
	public User(Long id, String firstName, String lastName, String email, String password, Role role, List<Score> scores)
		{
		// Inputs & Outputs
			{
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			this.role = Optional.ofNullable(role).orElse(Role.USER);
			this.scores = Optional.ofNullable(scores).orElse(new ArrayList<>());
			this.accessToken = DEFAULT_ACCESS_TOKEN;
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
	 * @param user An user
	 * @return True if users are equals; False otherwise
	 */
	public boolean isEquals(User user)
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
			return isEquals((User)object2);
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
	 * Gets the password
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
	 * Gets the list containing all the scores
	 * @return A read-only list containing scores
	 */
	public List<Score> getScores()
		{
		return this.scores;
		}

	/**
	 * Gets the access token
	 * @return A JWT access token
	 */
	public String getAccessToken()
		{
		return this.accessToken;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * Sets a JWT access token to this instance
	 * @param accessToken A JWT access token
	 */
	public void setAccessToken(String accessToken)
		{
		this.accessToken = accessToken;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;

	private String firstName;
	private String lastName;
	private String email;

	private String password;

	private Role role;

	@JsonIgnoreProperties("user")
	private List<Score> scores;

	@JsonInclude(Include.NON_EMPTY)
	private String accessToken;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String DEFAULT_ACCESS_TOKEN = Strings.EMPTY;
	}
