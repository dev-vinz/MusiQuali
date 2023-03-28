
package ch.hearc.spring.musiquali.admin.api.deezer.http;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

public class DeezerGetRequest<T>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public DeezerGetRequest(String url, Class<T> intoClass)
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

	public T execute()
		{
		return WebClient.create().get()//
				.uri(this.uriBuilder.build())//
				.retrieve()//
				.bodyToMono(this.intoClass)//
				.block();
		}

	public DeezerGetRequest<T> addParam(String name, String value)
		{
		this.uriBuilder.queryParam(name, value);
		return this;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Class<T> intoClass;
	private UriBuilder uriBuilder;
	}
