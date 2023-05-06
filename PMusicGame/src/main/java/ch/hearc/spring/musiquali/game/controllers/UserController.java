
package ch.hearc.spring.musiquali.game.controllers;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.User;
import ch.hearc.spring.musiquali.game.security.PrincipalService;

@Controller
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

	@GetMapping(value = { "/login" })
	public String login()
		{
		return "user/login";
		}

	@GetMapping(value = { "/profile" })
	public String profile(Principal principal, Model model)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		model.addAttribute("user", loggedUser);

		return "user/profile";
		}

	@GetMapping(value = { "/register" })
	public String register(Model model)
		{
		model.addAttribute("user", new User());

		return "user/register";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/profile/delete" })
	public String profileDelete(HttpServletRequest request, Principal principal)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Logged out user
		try
			{
			request.logout();
			}
		catch (ServletException e)
			{
			return "redirect:/profile";
			}

		// Deletes user
		MusicAdminAPI.users.delete(loggedUser.getId()).execute();

		// Redirects to login
		return "redirect:/login";
		}

	@PostMapping(value = { "/profile/informations" })
	public String profileInformations(Principal principal, @ModelAttribute User user)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Verifies email
		User existingUser = MusicAdminAPI.users.getByEmail(user.getEmail()).execute();

		if (existingUser != null && !existingUser.isEquals(loggedUser) && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
			{ return "redirect:/profile?error_email"; }

		// Updates user
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		loggedUser.setEmail(user.getEmail());

		// Saves
		MusicAdminAPI.users.update(loggedUser).execute();

		return "redirect:/profile?success_informations";
		}

	@PostMapping(value = { "/profile/password" })
	public String profilePassword(Principal principal, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Checks if password is correct
		if (!PASSWORD_ENCODER.matches(oldPassword, loggedUser.getPassword()))
			{ return "redirect:/profile?error_password_incorrect"; }

		// Checks if password are the same
		if (!newPassword.equals(confirmPassword))
			{ return "redirect:/profile?error_password_not_match"; }

		// Updates and saves modification
		loggedUser.setPassword(newPassword);
		MusicAdminAPI.users.update(loggedUser).execute();

		return "redirect:/profile?succes_password";
		}

	@PostMapping(value = { "/register" })
	public String register(HttpServletRequest request, @ModelAttribute User user, @RequestParam String confirmPassword, BindingResult result, Model model)
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

		MusicAdminAPI.users.add(user).execute();

		try
			{
			request.login(user.getEmail(), user.getPassword());
			}
		catch (ServletException e)
			{
			return "redirect:/register?success";
			}

		return "redirect:/?success";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	}
