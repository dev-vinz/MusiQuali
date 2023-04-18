
package ch.hearc.spring.musiquali.game.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import ch.hearc.spring.musiquali.game.jms.model.Log;
import ch.hearc.spring.musiquali.game.jms.model.LogType;

@Component
public class TestLogProducer implements CommandLineRunner
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run(String... args) throws Exception
		{
		System.out.println("Starting test log production...");

		while(true)
			{
			Thread.sleep(1000);

			Log log = new Log("Test réussi", LogType.INFO);
			System.out.println(log);

			jmsTemplate.convertAndSend("log-q", log);
			System.out.println("New Log produced.");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// rien

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	JmsTemplate jmsTemplate;
	}
