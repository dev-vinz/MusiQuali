
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.hearc.spring.musiquali.admin.models.database.User;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@GetMapping("")
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> all()
		{
		return userService.getAll();
		}

	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User get(@PathVariable Long id)
		{
		User user = userService.getById(id);

		if (user != null)
			{
			return user;
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id \"" + id + "\" not found");
			}
		}

	@GetMapping("/email/{email}")
	@ResponseStatus(value = HttpStatus.OK)
	public User get(@PathVariable String email)
		{
		User user = userService.getByEmail(email);

		if (user != null)
			{
			return user;
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email \"" + email + "\" not found");
			}
		}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public User create(@RequestBody User user)
		{
		// Checks if email already exists
		User existingUser = this.userService.getByEmail(user.getEmail());

		if (existingUser != null)
			{ throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email \"" + user.getEmail() + "\" already exists"); }

		this.userService.add(user);

		return user;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private UserService userService;
	}

