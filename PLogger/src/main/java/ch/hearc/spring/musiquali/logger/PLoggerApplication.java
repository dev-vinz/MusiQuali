
package ch.hearc.spring.musiquali.logger;

import javax.jms.JMSException;
import javax.jms.Message;
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
	public void readInprogressLog(final Message jsonLog) throws JMSException
		{
		String messageData = null;

		if (jsonLog instanceof TextMessage)
			{
			TextMessage textMessage = (TextMessage)jsonLog;
			messageData = textMessage.getText();

			try
				{
				Log log = mapper.readValue(messageData, Log.class);
				LoggerService.getInstance().log(log);
				}
			catch (Exception e)
				{
				System.out.println("error processing log");
				}
			}
		}
	}