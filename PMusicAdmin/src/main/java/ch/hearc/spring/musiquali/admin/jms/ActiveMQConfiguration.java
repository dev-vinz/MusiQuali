
package ch.hearc.spring.musiquali.admin.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;

@Configuration
@EnableJms
public class ActiveMQConfiguration
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Connection factory fournissant les connections vers ActiveMQ
	 * @return un instance de connectionFactory
	 */
	@Bean
	public ConnectionFactory connectionFactory()
		{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setTrustAllPackages(true);
		connectionFactory.setBrokerURL(BROKER_URL);
		connectionFactory.setPassword(BROKER_USERNAME);
		connectionFactory.setUserName(BROKER_PASSWORD);
		return connectionFactory;
		}

	/**
	 * Conversion de message en mode texte pour json
	 * @return une instance de converter
	 */
	@Bean
	public MessageConverter messageConverter()
		{
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setObjectMapper(objectMapper());
		return converter;
		}

	/**
	 * Instance de mapper pour sérailisation, Paramétrage du type date
	 * @return une instance de objectMapper
	 */
	@Bean
	public ObjectMapper objectMapper()
		{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
		}

	/**
	 * Template jms pour l'enmvoi de message
	 * @return une instance de jmsTemplate
	 */
	@Bean
	public JmsTemplate jmsTemplate()
		{
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setMessageConverter(messageConverter());
		template.setPubSubDomain(true);
		template.setDestinationResolver(destinationResolver());
		template.setDeliveryPersistent(true);
		return template;
		}

	/**
	 * Composant de gestion des routes jms
	 * @return une instance de DynamicDestinationResolver
	 */
	@Bean
	DynamicDestinationResolver destinationResolver()
		{
		return new DynamicDestinationResolver()
			{

			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException
				{
				if (destinationName.endsWith("-t"))
					{
					pubSubDomain = true;
					}
				else
					{
					pubSubDomain = false;
					}
				return super.resolveDestinationName(session, destinationName, pubSubDomain);
				}
			};
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	@Value("${spring.activemq.broker-url}")
	String BROKER_URL;
	@Value("${spring.activemq.user}")
	String BROKER_USERNAME;
	@Value("${spring.activemq.password}")
	String BROKER_PASSWORD;

	}
