
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import reactor.core.publisher.Mono;

public class AdminPostRequest<T, U> extends AdminRequest<U>
	{

	public AdminPostRequest(String url, T object, Class<U> intoClass)
		{
		super(url, intoClass);

		// Input
			{
			this.object = object;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public AdminPostRequest<T, U> addParam(String name, String value)
		{
		return (AdminPostRequest<T, U>)super.addParam(name, value);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	@Override
	protected RequestBodyUriSpec newRequest()
		{
		return (RequestBodyUriSpec)WebClient.create()//
				.post()//
				.body(Mono.just(this.object), this.intoClass);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private T object;
	}
