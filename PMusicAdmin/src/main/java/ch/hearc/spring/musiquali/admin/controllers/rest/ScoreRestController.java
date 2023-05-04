
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Genre;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbScore;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;
import ch.hearc.spring.musiquali.admin.service.impl.ScoreService;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@RestController
@RequestMapping("/api/scores")
public class ScoreRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	public ResponseEntity<List<Score>> getAll()
		{
		return ResponseEntity.ok(this.scoreService.getAll().stream()//
				.map(ScoreRestController::fetchToScore)//
				.toList());
		}

	@GetMapping("/{id}")
	public ResponseEntity<Score> get(@PathVariable Long id)
		{
		DbScore score = this.scoreService.getById(id);

		if (score != null)
			{
			return ResponseEntity.ok(fetchToScore(score));
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping
	public ResponseEntity<Score> create(@RequestBody Score score)
		{
		DbScore newScore = new DbScore(score.getArtistValue(), score.getTitleValue());
		newScore.setMusic(this.musicService.getById(score.getMusic().getId()));
		newScore.setUser(this.userService.getById(score.getUser().getId()));

		this.scoreService.add(newScore);

		return new ResponseEntity<>(get(newScore.getId()).getBody(), HttpStatus.CREATED);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * Fetchs a score coming from database to a restful score model
	 * @param score A score coming from the database
	 * @return A restful score model
	 */
	private static Score fetchToScore(DbScore score)
		{
		DbUser u = score.getUser();
		DbMusic m = score.getMusic();

		// Gets some informations with Deezer
		Track track = DeezerApi.tracks.getById(m.getTrackId()).execute();

		String title = track.getTitleShort();
		String artist = track.getArtist().getName();
		String preview = track.getPreview();
		Integer duration = track.getDuration();
		String link = track.getLink();

		Set<MusicalGenre> genres = m.getGenres().stream()//
				.map(g -> {
				Genre genre = DeezerApi.genres.getById(g.getGenreId()).execute();

				// Musics are null because they will be deleted by recursion
				return new MusicalGenre(g.getId(), genre.getName(), null);
				})//
				.collect(Collectors.toCollection(HashSet<MusicalGenre>::new));

		// Scores are null because they will be deleted by recursion
		User user = new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.getRole(), null);
		Music music = new Music(m.getId(), title, artist, preview, m.getDifficulty(), duration, link, genres, null);

		return new Score(score.getId(), score.getArtistScore(), score.getTitleScore(), user, music);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicService musicService;

	@Autowired
	private UserService userService;

	@Autowired
	private ScoreService scoreService;
	}
