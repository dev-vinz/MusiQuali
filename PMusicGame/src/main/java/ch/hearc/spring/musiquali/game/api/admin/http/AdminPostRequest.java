
package ch.hearc.spring.musiquali.game.api.admin.http;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import ch.hearc.spring.musiquali.game.security.WebSecurityConfig;

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
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Cookie[] tabCookies = request.getCookies() != null ? request.getCookies() : new Cookie[] {};

		Optional<Cookie> cookie = Arrays.stream(tabCookies)//
				.filter(c -> c.getName().contentEquals(WebSecurityConfig.SPRING_JWT_TOKEN_COOKIE))//
				.findFirst();

		Cookie sessionCookie = cookie.orElse(null);
		String jwtToken = sessionCookie == null ? "" : sessionCookie.getValue();

		return (RequestBodyUriSpec)WebClient.create()//
				.post()//
				.body(Mono.just(this.object), this.intoClass)//
				.header("Authorization", "Bearer " + jwtToken);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private T object;
	}
