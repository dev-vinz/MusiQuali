
package ch.hearc.spring.musiquali.admin.models.rest;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.hearc.spring.musiquali.admin.models.Role;

public class User
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

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
			this.role = role;
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
		return Collections.unmodifiableList(this.scores);
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
	}
