
package ch.hearc.spring.musiquali.game.controllers;

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
	public String index(Model model)
		{
		Difficulty[] difficulties = MusicAdminAPI.difficulties.getAll().execute();

		model.addAttribute("difficulties", difficulties);

		return "game/index";
		}

	@GetMapping(value = { "/leaderboard" })
	public String leaderboard()
		{
		return "game/leaderboard";
		}

	@GetMapping(value = { "/play" })
	public String play(HttpServletRequest request, Model model, Difficulty difficulty)
		{
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
	public String play(Model model, @RequestParam Long musicId, @RequestParam String artist, @RequestParam String title)
		{
		// Gets music
		Music music = MusicAdminAPI.musics.getById(musicId).execute();

		// Verifies artist
		// TODO

		// Verifies title
		// TODO

		// Creates score
		// TODO

		// Saves score
		// TODO

		return play(null, model, music.getDifficulty());
		}

	@PostMapping(value = { "/start" })
	public String play(RedirectAttributes redirectAttributes, @RequestParam Difficulty difficulty)
		{
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
