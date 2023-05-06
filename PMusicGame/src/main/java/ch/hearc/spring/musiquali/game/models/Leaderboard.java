
package ch.hearc.spring.musiquali.game.models;

public class Leaderboard
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Constructor building a new leaderboard
	 * @param users All the users
	 * @param userPosition The logged user's position
	 */
	public Leaderboard(LeaderboardUser[] users, long userPosition)
		{
		// Inputs & Outputs
			{
			this.users = users;
			this.userPosition = userPosition;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets all the users
	 * @return An array containing all the users
	 */
	public LeaderboardUser[] getUsers()
		{
		return this.users;
		}

	/**
	 * Gets the logged user's position
	 * @return An user position
	 */
	public long getUserPosition()
		{
		return this.userPosition;
		}

	/**
	 * Tells wether the leaderboard is empty
	 * @return True if there's no users; False otherwise
	 */
	public Boolean isEmpty()
		{
		return this.users.length < 1;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private LeaderboardUser[] users;
	private long userPosition;
	}
