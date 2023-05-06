
package ch.hearc.spring.musiquali.game.models;

import java.util.Arrays;

import ch.hearc.spring.musiquali.game.api.admin.models.Score;

public class LeaderboardUser
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Constructor builder an user of leaderboard
	 * @param position A position indide a leaderboard
	 * @param firstName A first name
	 * @param lastName A last name
	 * @param scores An array containing all the scores
	 */
	public LeaderboardUser(long position, String firstName, String lastName, Score[] scores)
		{
		// Inputs & Outputs
			{
			this.position = position;
			this.firstName = firstName;
			this.lastName = lastName;
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
	 * Gets the user position inside a leaderboard
	 * @return A position
	 */
	public long getPosition()
		{
		return this.position;
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
	 * Gets the scores
	 * @return An array containing all the scores
	 */
	public Score[] getScores()
		{
		return this.scores;
		}

	/**
	 * Gets the total of all scores
	 * @return A total of all scores
	 */
	public Long getTotalScore()
		{
		return Arrays.stream(this.scores)//
				.mapToLong(s -> s.getArtistValue() + s.getTitleValue())//
				.sum();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private long position;
	private String firstName;
	private String lastName;
	private Score[] scores;
	}
