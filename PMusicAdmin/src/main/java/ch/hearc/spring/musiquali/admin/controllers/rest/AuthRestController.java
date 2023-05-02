
package ch.hearc.spring.musiquali.admin.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.security.jwt.JwtUtils;
import ch.hearc.spring.musiquali.admin.security.payload.request.LoginRequest;
import ch.hearc.spring.musiquali.admin.security.payload.request.SignupRequest;
import ch.hearc.spring.musiquali.admin.security.payload.response.JwtResponse;
import ch.hearc.spring.musiquali.admin.security.payload.response.MessageResponse;
import ch.hearc.spring.musiquali.admin.security.services.UserDetailsImpl;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/signin" })
	public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
		{
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtUtils.generateJwtToken(authentication);

		// Stores JWT in session
		request.getSession().setAttribute(JWT_SESSION_KEY, jwt);

		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));
		}

	@PostMapping(value = { "/signout" })
	public ResponseEntity<?> signout(HttpServletRequest request)
		{
		// Removes JWT from session
		request.getSession().removeAttribute(JWT_SESSION_KEY);

		return ResponseEntity.ok(new MessageResponse("User logout successfully"));
		}

	@PostMapping(value = { "/signup" })
	public ResponseEntity<?> signup(@RequestBody SignupRequest signUpRequest)
		{
		DbUser anyUser = this.userService.getByEmail(signUpRequest.getEmail());

		if (anyUser != null)
			{
			return ResponseEntity//
					.badRequest()//
					.body(new MessageResponse("Error: Email is already taken"));
			}

		DbUser user = new DbUser(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(), signUpRequest.getPassword(), Role.USER);

		this.userService.add(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
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
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final String JWT_SESSION_KEY = "jwt_token_session";
	}
