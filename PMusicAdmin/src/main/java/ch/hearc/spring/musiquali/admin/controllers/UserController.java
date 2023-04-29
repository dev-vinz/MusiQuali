
package ch.hearc.spring.musiquali.admin.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@Controller
@RequestMapping("/users")
public class UserController
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping(value = { "/", "index" })
	public String index(Principal principal, Model model)
		{
		// Gets all users
		List<DbUser> users = this.userService.getAll();

		model.addAttribute("users", users);
		model.addAttribute("loggedRole", Role.ADMIN); // TODO: Replace with logged user role
		model.addAttribute("roles", Role.values());

		return "user/index";
		}

	@GetMapping(value = { "/login" })
	public String showLoginView(/*Model model*/)
		{
		//model.addAttribute("request", new AuthenticationRequest());

		return "user/login";
		}

	@GetMapping(value = { "/register" })
	public String showRegisterView(Model model)
		{
		model.addAttribute("user", new DbUser());

		return "user/register";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

//	@PostMapping(value = { "/login" })
//	String registerUser(@ModelAttribute AuthenticationRequest request) throws URISyntaxException
//		{
//		System.out.println(request.getEmail());
//		System.out.println(request.getPassword());
//
//		AuthenticationResponse response = WebClient.create().post()//
//				.uri(new URI("http://localhost:8081/api/auth/login"))//
//				.contentType(MediaType.APPLICATION_JSON)//
//				.body(Mono.just(request), AuthenticationRequest.class)//
//				.retrieve()//
//				.bodyToMono(AuthenticationResponse.class)//
//				.block();
//
//		System.out.println(response);
//
//		return "redirect:/";
//		}

	@PostMapping(value = { "/register" })
	String registerUser(@ModelAttribute DbUser user, @RequestParam String confirmPassword, BindingResult result, Model model)
		{
		DbUser existingUser = this.userService.getByEmail(user.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
			{
			result.rejectValue("email", null, "Un utilisateur est déjà enregistré avec cet email");
			}

		if (!user.getPassword().equals(confirmPassword))
			{
			result.rejectValue("password", null, "Les mots de passe ne correspondent pas");
			}

		if (result.hasErrors())
			{
			model.addAttribute("user", user);

			return "user/register";
			}

		this.userService.add(user);

		return "redirect:/users/register?success";
		}

	@PostMapping(value = { "/delete" })
	public String delete(Principal principal, @RequestParam Long userId)
		{
		// TODO: Checks if logged user has rights to do this

		this.userService.deleteById(userId);

		return "redirect:/users/?deleted";
		}

	@PostMapping(value = { "/update" })
	public String update(Principal principal, @RequestParam Long userId, @RequestParam(required = false) Role userRole)
		{
		// TODO: Checks if logged user has rights to do this

		DbUser user = this.userService.getById(userId);

		if (userRole == null || userRole == user.getRole())
			{ return "redirect:/users/"; }

		user.setRole(userRole);
		this.userService.update(user);

		return "redirect:/users/?updated";
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
