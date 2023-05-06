
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import reactor.core.publisher.Mono;

public class AdminPostRequest<T> extends AdminRequest<T>
	{

	public AdminPostRequest(String url, T object, Class<T> intoClass)
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

	@Override
	public AdminPostRequest<T> addParam(String name, Object value)
		{
		return (AdminPostRequest<T>)super.addParam(name, value);
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
