
package ch.hearc.spring.musiquali.game.api.http;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class AdminPutRequest<T> extends AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminPutRequest(String url, T object, Class<T> intoClass)
		{
		super(url, intoClass);

		this.object = object;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public T execute()
		{
		return WebClient.create().put()//
				.uri(this.uriBuilder.build())//
				.body(Mono.just(object), this.intoClass)//
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

	// Input
	private T object;
	}
