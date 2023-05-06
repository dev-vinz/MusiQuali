
package ch.hearc.spring.musiquali.game.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.User;

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

	@GetMapping(value = { "/register" })
	public String register(Model model)
		{
		model.addAttribute("user", new User());

		return "user/register";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

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
	}
