
package ch.hearc.spring.musiquali.game.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.game.api.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.models.User;

@Controller
public class UserController
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@GetMapping(value = { "/profile" })
	public String showProfileView(Principal principal, Model model)
		{
		// Gets logged in user email - and user
		String email = principal.getName();
		User user = MusicAdminAPI.users.getByEmail(email).execute();

		model.addAttribute("user", user);

		return "user/profile";
		}

	@PostMapping(value = { "/profile/save" })
	public String updateProfile(Principal principal, @ModelAttribute User user, @RequestParam(required = false) String confirmPassword, BindingResult result, Model model)
		{
		// Gets logged in user email - and user
		String email = principal.getName();
		User logged = MusicAdminAPI.users.getByEmail(email).execute();

		User existingUser = MusicAdminAPI.users.getByEmail(user.getEmail()).execute();

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty() && existingUser.getId() != logged.getId())
			{
			result.rejectValue("email", null, "Un utilisateur est déjà enregistré avec cet email");
			}

		if (!user.getPassword().equals(confirmPassword) && (user.getPassword() != null || confirmPassword != null))
			{
			result.rejectValue("password", null, "Les mots de passe ne correspondent pas");
			}

		if (result.hasErrors())
			{
			model.addAttribute("user", user);

			return "user/profile";
			}

		Boolean emailChanged = !user.getEmail().equals(logged.getEmail());
		Boolean passwordChanged = user.getPassword() != null && !user.getPassword().isEmpty() && !this.passwordEncoder.matches(user.getPassword(), logged.getPassword());

		logged.setEmail(user.getEmail());
		logged.setFirstName(user.getFirstName());
		logged.setLastName(user.getLastName());

		if (passwordChanged)
			{
			logged.setPassword(this.passwordEncoder.encode(user.getPassword()));
			}

		//this.userService.update(logged);
		MusicAdminAPI.users.update(logged, logged.getId()).execute();

		if (emailChanged)
			{
			return "redirect:/login";
			}
		else
			{
			return "redirect:/profile?success";
			}
		}

	@GetMapping(value = { "/login" })
	public String showLoginView()
		{
		return "user/login";
		}

	@GetMapping(value = { "/register" })
	public String showRegisterView(Model model)
		{
		model.addAttribute("user", new User());

		return "user/register";
		}

	@PostMapping(value = { "register/save" })
	String registerUser(@ModelAttribute User user, @RequestParam String confirmPassword, BindingResult result, Model model)
		{
		User existingUser = MusicAdminAPI.users.getByEmail(user.getEmail()).execute();

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

		//this.userService.add(user);
		MusicAdminAPI.users.save(user).execute();

		return "redirect:/register?success";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private PasswordEncoder passwordEncoder;
	}
