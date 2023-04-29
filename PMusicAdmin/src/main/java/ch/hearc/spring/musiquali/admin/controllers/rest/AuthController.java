
package ch.hearc.spring.musiquali.admin.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.security.JwtUtils;
import ch.hearc.spring.musiquali.admin.security.model.AuthenticationResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(String email, String password/*@RequestBody AuthenticationRequest authenticationRequest*/)
		{
		logger.info("Entering authenticateUser method");

//		String email = authenticationRequest.getEmail();
//		String password = authenticationRequest.getPassword();

//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateTokenFromUsername(email);

		return ResponseEntity.ok(new AuthenticationResponse(null));
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	}
