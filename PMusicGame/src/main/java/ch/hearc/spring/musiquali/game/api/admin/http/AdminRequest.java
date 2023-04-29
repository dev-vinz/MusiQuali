
package ch.hearc.spring.musiquali.game.api.admin.http;

import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

public abstract class AdminRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AdminRequest(String url, Class<T> intoClass)
		{
		// Inputs
			{
			this.intoClass = intoClass;
			this.uriBuilder = new DefaultUriBuilderFactory(url).builder();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public AdminRequest<T> addParam(String name, String value)
		{
		this.uriBuilder.queryParam(name, value);
		return this;
		}

	public T execute()
		{
		return newRequest().uri(this.uriBuilder.build())//
				.retrieve()//
				.bodyToMono(this.intoClass)//
				.block();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	protected abstract RequestBodyUriSpec newRequest();

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	protected Class<T> intoClass;
	protected UriBuilder uriBuilder;
	}
