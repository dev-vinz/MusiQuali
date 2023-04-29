
package ch.hearc.spring.musiquali.admin.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public String getUserNameFromJwtToken(String token)
		{
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		}

	public boolean validateJwtToken(String authToken)
		{
		try
			{
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
			}
		catch (SignatureException e)
			{
			logger.error("Invalid JWT signature: {}", e.getMessage());
			}
		catch (MalformedJwtException e)
			{
			logger.error("Invalid JWT token: {}", e.getMessage());
			}
		catch (ExpiredJwtException e)
			{
			logger.error("JWT token is expired: {}", e.getMessage());
			}
		catch (UnsupportedJwtException e)
			{
			logger.error("JWT token is unsupported: {}", e.getMessage());
			}
		catch (IllegalArgumentException e)
			{
			logger.error("JWT claims string is empty: {}", e.getMessage());
			}

		return false;
		}

	public String generateTokenFromUsername(String username)
		{
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${musiquali.app.secret_key}")
	private String jwtSecret;

	@Value("${musiquali.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${musiquali.app.jwtCookieName}")
	private String jwtCookie;
	}
