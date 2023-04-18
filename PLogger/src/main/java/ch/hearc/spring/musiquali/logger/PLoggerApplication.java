
package ch.hearc.spring.musiquali.logger;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.spring.musiquali.logger.model.Log;

@SpringBootApplication
public class PLoggerApplication
	{

	@Autowired
	ObjectMapper mapper;

	public static void main(String[] args)
		{
		SpringApplication.run(PLoggerApplication.class, args);
		}

	/**
	 * Listener jms avec conversion json pour log
	 * @param jsonMessage
	 * @throws JMSException
	 */
	@JmsListener(destination = "${spring.activemq.log-queue}")
	public void readInprogressLog(final Log jsonLog) throws JMSException
		{

		String messageData = null;

		System.out.println("Received log-q message " + jsonLog);

		if (jsonLog instanceof TextMessage)
			{

			Log log = null;

			TextMessage textMessage = (TextMessage)jsonLog;
			messageData = textMessage.getText();

			try
				{
				log = mapper.readValue(messageData, Log.class);

				System.out.println(log);
				}
			catch (Exception e)
				{
				System.out.println("error converting to log");
				}

			System.out.println("messageData:" + messageData);
			}
		}

	}
