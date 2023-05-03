
package ch.hearc.spring.musiquali.admin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.security.jwt.JwtUtils;
import ch.hearc.spring.musiquali.admin.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/login" })
	@ResponseStatus(HttpStatus.OK)
	public User login(@RequestBody User user)
		{
		// Creates authentication
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

		// Generates JWT access token
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtUtils.generateJwtToken(authentication);

		// Gets logged user details
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		// Formats to known user and sets the access token
		User loggedUser = new User(userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(), userDetails.getPassword(), userDetails.getRole(), null);
		loggedUser.setAccessToken(jwt);

		return loggedUser;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;
	}
