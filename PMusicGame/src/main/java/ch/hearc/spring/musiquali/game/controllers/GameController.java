
package ch.hearc.spring.musiquali.game.controllers;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import ch.hearc.spring.musiquali.game.api.admin.MusicAdminAPI;
import ch.hearc.spring.musiquali.game.api.admin.models.Difficulty;
import ch.hearc.spring.musiquali.game.api.admin.models.Music;
import ch.hearc.spring.musiquali.game.api.admin.models.Score;
import ch.hearc.spring.musiquali.game.api.admin.models.User;
import ch.hearc.spring.musiquali.game.security.PrincipalService;
import ch.hearc.spring.musiquali.game.utils.Levenshtein;

@Controller
@RequestMapping("/game")
public class GameController
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

	@GetMapping(value = { "/", "/index" })
	public String index(Principal principal, Model model)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		Difficulty[] difficulties = MusicAdminAPI.difficulties.getAll().execute();

		model.addAttribute("difficulties", difficulties);

		return "game/index";
		}

	@GetMapping(value = { "/leaderboard" })
	public String leaderboard(Principal principal)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		return "game/leaderboard";
		}

	@GetMapping(value = { "/play" })
	public String play(Principal principal, HttpServletRequest request, Model model, Difficulty difficulty)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Makes sure difficulty is recovered
		if (difficulty == null && request == null)
			{
			return "redirect:/game/";
			}
		else if (difficulty == null)
			{
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

			if (inputFlashMap == null)
				{
				return "redirect:/game/";
				}
			else
				{
				difficulty = (Difficulty)inputFlashMap.get("difficulty");
				}
			}

		// Gets all musics
		Music[] musics = MusicAdminAPI.difficulties.getMusics(difficulty.getId()).execute();
		int size = musics.length;

		// Gets a random music
		Music music = null;

		if (size > 0)
			{
			int rnd = new Random().nextInt(size);
			music = musics[rnd];
			}

		model.addAttribute("music", music);
		model.addAttribute("isEmpty", size < 1);

		return "game/play";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/play" })
	public String play(Principal principal, Model model, @RequestParam Long musicId, @RequestParam String artist, @RequestParam String title)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		// Gets music
		Music music = MusicAdminAPI.musics.getById(musicId).execute();

		// Verifies artist
		double artistScore = Levenshtein.getRatio(artist.toLowerCase(), music.getArtist().toLowerCase());

		// Verifies title
		double titleScore = Levenshtein.getRatio(title.toLowerCase(), music.getTitle().toLowerCase());

		// Creates score
		Score score = new Score((int)Math.round(artistScore * 100), (int)Math.round(titleScore * 100), loggedUser, music);

		// Saves score
		MusicAdminAPI.scores.add(score).execute();

		// Saves data to model
		model.addAttribute("score", score);

		return play(principal, null, model, music.getDifficulty());
		}

	@PostMapping(value = { "/start" })
	public String play(Principal principal, RedirectAttributes redirectAttributes, @RequestParam Difficulty difficulty)
		{
		// Gets logged user
		User loggedUser = PrincipalService.parseFromPrincipal(principal);

		if (loggedUser == null)
			{ return "redirect:/login"; }

		redirectAttributes.addFlashAttribute("difficulty", difficulty);

		return "redirect:/game/play";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
