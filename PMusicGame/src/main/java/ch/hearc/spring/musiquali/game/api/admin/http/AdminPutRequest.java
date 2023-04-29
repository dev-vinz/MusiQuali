
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import reactor.core.publisher.Mono;

public class AdminPutRequest<T> extends AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminPutRequest(String url, T object, Class<T> intoClass)
		{
		super(url, intoClass);

		// Input
			{
			this.object = object;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public AdminPutRequest<T> addParam(String name, String value)
		{
		return (AdminPutRequest<T>)super.addParam(name, value);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	@Override
	protected RequestBodyUriSpec newRequest()
		{
		return (RequestBodyUriSpec)WebClient.create()//
				.put()//
				.body(Mono.just(this.object), this.intoClass);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private T object;
	}
