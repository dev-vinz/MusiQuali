
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient;

public class AdminDeleteRequest<T> extends AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminDeleteRequest(String url, Class<T> intoClass)
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

		return WebClient.create().delete()//
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
