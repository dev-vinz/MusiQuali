
package ch.hearc.spring.musiquali.game.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.Difficulty;
import ch.hearc.spring.musiquali.game.api.admin.models.Music;
import ch.hearc.spring.musiquali.game.api.admin.models.MusicalGenre;
import ch.hearc.spring.musiquali.game.api.admin.models.User;
import ch.hearc.spring.musiquali.game.security.PrincipalService;

@Controller
public class HomeController
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

	@GetMapping(value = { "/about" })
	public String about(Principal principal)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		return "home/about";
		}

	@GetMapping(value = { "/explore" })
	public String explore(Principal principal, Model model)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Gets some datas
		Difficulty[] difficulties = MusicAdminAPI.difficulties.getAll().execute();
		MusicalGenre[] genres = MusicAdminAPI.musicalGenres.getAll().execute();
		Music[] musics = MusicAdminAPI.musics.getAll().execute();

		model.addAttribute("difficulties", difficulties);
		model.addAttribute("genres", genres);
		model.addAttribute("musics", musics);

		return "home/explore";
		}

	@GetMapping(value = { "/", "/index" })
	public String index(Principal principal)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		return "index";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
