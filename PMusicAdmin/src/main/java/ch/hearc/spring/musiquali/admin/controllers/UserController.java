
package ch.hearc.spring.musiquali.admin.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@Controller
@RequestMapping("/admin/users")
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

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/delete" })
	public String delete(Principal principal, @RequestParam Long userId)
		{
		// TODO: Checks if logged user has rights to do this

		this.userService.deleteById(userId);

		return "redirect:/admin/users/?deleted";
		}

	@PostMapping(value = { "/update" })
	public String update(Principal principal, @RequestParam Long userId, @RequestParam(required = false) Role userRole)
		{
		// TODO: Checks if logged user has rights to do this

		DbUser user = this.userService.getById(userId);

		if (userRole == null || userRole == user.getRole())
			{ return "redirect:/admin/users/"; }

		user.setRole(userRole);
		this.userService.update(user);

		return "redirect:/admin/users/?updated";
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
