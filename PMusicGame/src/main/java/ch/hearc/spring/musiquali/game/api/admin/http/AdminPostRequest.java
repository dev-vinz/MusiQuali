
package ch.hearc.spring.musiquali.game.api.http;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class AdminPostRequest<T> extends AdminRequest<T>
	{

	public AdminPostRequest(String url, T object, Class<T> intoClass)
		{
		super(url, intoClass);

		this.object = object;
		}

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public T execute()
		{
		return WebClient.create().post()//
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

	// Inputs
	private T object;
	}

