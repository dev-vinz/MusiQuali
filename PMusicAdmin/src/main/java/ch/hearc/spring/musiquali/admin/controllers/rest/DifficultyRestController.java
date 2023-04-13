
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Album;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;

@RestController
@RequestMapping("/api/difficulties")
public class DifficultyRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	private List<Difficulty> getAll()
		{
		return List.of(Difficulty.values());
		}

	@GetMapping("/{index}")
	@ResponseStatus(value = HttpStatus.OK)
	private Difficulty get(@PathVariable Integer index)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id \"" + index + "\" is not possible, it musts be positive");
			}
		else if (index < difficulties.length)
			{
			return difficulties[index];
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Difficulty with id \"" + index + "\" not found");
			}
		}

	@GetMapping("/{index}/leaderboard")
	@ResponseStatus(value = HttpStatus.OK)
	public Set<User> getLeaderboard(@PathVariable Integer index, @RequestParam(required = false, defaultValue = "10") Integer limit)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id \"" + index + "\" is not possible, it musts be positive");
			}
		else if (index < difficulties.length)
			{
			Difficulty difficulty = difficulties[index];

			Set<Score> allScores = this.musicService.getAll().stream()//
					.map(DifficultyRestController::fetchToMusic)//
					.filter(m -> m.getDifficulty() == difficulty)//
					.flatMap(m -> m.getScores().stream())//
					.collect(Collectors.toCollection(HashSet<Score>::new));

			return allScores.stream()//
					.collect(Collectors.groupingBy(Score::getUser, Collectors.summingLong(s -> s.getArtistValue() + s.getTitleValue())))//
					.entrySet()//
					.stream()//
					.sorted(Entry.comparingByValue())// Min score is on top
					.sorted(Collections.reverseOrder())// Max score is on top
					.map(Entry::getKey)//
					.limit(limit)//
					.collect(Collectors.toCollection(HashSet<User>::new));
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Difficulty with id \"" + index + "\" not found");
			}
		}

	@GetMapping("/{index}/musics")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Music> getMusics(@PathVariable Integer index)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id \"" + index + "\" is not possible, it musts be positive");
			}
		else if (index < difficulties.length)
			{
			Difficulty difficulty = difficulties[index];

			return this.musicService.getAll().stream()//
					.map(DifficultyRestController::fetchToMusic)//
					.filter(m -> m.getDifficulty() == difficulty)//
					.toList();
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Difficulty with id \"" + index + "\" not found");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * Fetchs a music coming from database to a restful music model
	 * @param music A music coming from the database
	 * @return A restful music model
	 */
	private static Music fetchToMusic(DbMusic music)
		{
		// Gets some informations with Deezer
		Track track = DeezerApi.tracks.getById(music.getId()).execute();
		Album album = DeezerApi.albums.getById(track.getAlbum().getId()).execute();

		String title = track.getTitleShort();
		String artist = track.getArtist().getName();
		String preview = track.getPreview();
		Integer duration = track.getDuration();
		String link = track.getLink();

		// Musics are null because they will be deleted by recursion
		Set<MusicalGenre> genres = album.getGenres().stream()//
				.map(g -> new MusicalGenre(g.getId(), g.getName(), null))//
				.collect(Collectors.toCollection(HashSet<MusicalGenre>::new));

		Set<Score> scores = music.getScores().stream()//
				.map(s -> {
				DbUser dbUser = s.getUser();

				// Scores are null because they will be deleted by recursion
				User user = new User(dbUser.getId(), dbUser.getFirstName(), dbUser.getLastName(), dbUser.getEmail(), dbUser.getPassword(), dbUser.getRole(), null);

				// Music is null because it will be deleted by recursion
				return new Score(s.getId(), s.getArtistScore(), s.getTitleScore(), user, null);
				})//
				.collect(Collectors.toCollection(HashSet<Score>::new));

		return new Music(music.getId(), title, artist, preview, music.getDifficulty(), duration, link, genres, scores);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicService musicService;
	}
