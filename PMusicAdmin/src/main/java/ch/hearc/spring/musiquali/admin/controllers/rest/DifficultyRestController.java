
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;

public class DifficultyRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private List<Music> musicsByDifficulty(@RequestBody Difficulty difficulty, @RequestBody Integer nbMusics)
		{
		List<Long> listIdTracks = this.musicService.getAllByDifficulty(difficulty, nbMusics).stream()//
				.map(DbMusic::getTrackId)//
				.toList();

		// TODO : call Deezer api to request the trackId's and send the deezer Musics

		return null;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicService musicService;
	}
