
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;

@RestController
@RequestMapping("/musics")
public class MusicRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@GetMapping("/difficulties")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Difficulty> difficulties()
		{
		return Arrays.stream(Difficulty.values()).toList();
		}

	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Music> musicsByDifficulty(@RequestBody Difficulty difficulty, @RequestBody Integer nbMusics)
		{
		List<Long> listIdTracks = this.musicService.getAllByDifficulty(difficulty, nbMusics).stream()//
				.map(DbMusic::getTrackId)//
				.toList();

		// TODO : call Deezer api to request the trackId's and send the deezer Musics

		return null;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicService musicService;
	}
