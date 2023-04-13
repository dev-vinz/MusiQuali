
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient;

public class AdminGetRequest<T> extends AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminGetRequest(String url, Class<T> intoClass)
		{
		super(url, intoClass);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public T execute()
		{
		return WebClient.create().get()//
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

