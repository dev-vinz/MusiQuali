
package ch.hearc.spring.musiquali.game.api.http;

import org.springframework.web.reactive.function.client.WebClient;

public class AdminPutRequest<T> extends AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminPutRequest(String url, Class<T> intoClass)
		{
		super(url, intoClass);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public T execute()
		{
		// TODO : Improve return necessities

		return WebClient.create().put()//
				.uri(this.uriBuilder.build())//
				.retrieve()//
				.bodyToMono(this.intoClass)//
				.block();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
