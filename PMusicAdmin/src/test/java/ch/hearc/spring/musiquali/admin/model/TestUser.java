
package ch.hearc.spring.musiquali.admin.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.hearc.spring.musiquali.admin.models.database.DbUser;

public class TestUser
	{

	/*------------------------------------------------------------------*\
	 |*							Methodes Public							*|
	 \*-----------------------------------------------------------------*/

	@Test
	public void givenAUser_whenChangeFirstName_thenVerify()
		{
		DbUser user = new DbUser();
		String firstName = "Toto";

		user.setFirstName(firstName);

		Assertions.assertEquals(user.getFirstName(), firstName);
		}

	@Test
	public void givenAUser_whenChangeLastName_thenVerify()
		{
		DbUser user = new DbUser();
		String lastName = "Dupond";

		user.setLastName(lastName);

		Assertions.assertEquals(user.getLastName(), lastName);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
