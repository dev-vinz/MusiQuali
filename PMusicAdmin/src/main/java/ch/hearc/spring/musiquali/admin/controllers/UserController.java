
package ch.hearc.spring.musiquali.admin.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.security.PrincipalService;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public class UserController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping(value = { "/", "index", "/{pageNo}", "/index/{pageNo}" })
	public String index(Principal principal, Model model, @PathVariable(required = false) Integer pageNo, @RequestParam(defaultValue = "id") String sortBy)
		{
		pageNo = pageNo == null ? 0 : pageNo;

		// Gets logged user
		DbUser loggedUser = PrincipalService.parseFromPrincipal(principal);

		// Gets users
		Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(sortBy));
		Page<DbUser> users = this.userService.getAll(pageable);

		// Gets roles, without last
		Role[] allRoles = Role.values();
		Role[] roles = Arrays.copyOf(allRoles, allRoles.length - 1);

		// Gets list of indexes for pagination
		int[] indexes = IntStream.range(0, users.getTotalPages())//
				.toArray();

		model.addAttribute("pageNo", pageNo);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("indexes", indexes);

		model.addAttribute("users", users);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("roles", roles);

		return "user/index";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/delete" })
	public String delete(Principal principal, @RequestParam Long userId)
		{
		// Gets logged user
		DbUser loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null || loggedUser.getRole().getId() < Role.MODERATOR.getId())
			{ return "redirect:/users/?error"; }

		this.userService.deleteById(userId);

		return "redirect:/users/?deleted";
		}

	@PostMapping(value = { "/update" })
	public String update(Principal principal, @RequestParam Long userId, @RequestParam(required = false) Role userRole)
		{
		// Gets logged user
		DbUser loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null || loggedUser.getRole().getId() < Role.MODERATOR.getId())
			{ return "redirect:/users/?error"; }

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
