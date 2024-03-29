
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Genre;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Difficulty;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/scores")
@Tag(name = "Score", description = "Score management APIs")
public class ScoreRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	@Operation(summary = "Retrieve all the scores", description = "Get a list containing all the scores. The response is a list of Score objects.")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Score.class), mediaType = "application/json") })
	public ResponseEntity<List<Score>> getAll(@RequestParam(name = "difficulty", required = false) String diff)
		{
		try
			{
			Difficulty difficulty = Difficulty.valueOf(diff);

			return ResponseEntity.ok(this.scoreService.getAll().stream()//
					.map(ScoreRestController::fetchToScore)//
					.filter(s -> s.getMusic().getDifficulty() == difficulty)//
					.toList());
			}
		catch (Exception e)
			{
			return ResponseEntity.ok(this.scoreService.getAll().stream()//
					.map(ScoreRestController::fetchToScore)//
					.toList());
			}

		}

	@GetMapping("/{id}")
	@Operation(summary = "Retrieve a score by id", description = "Get a Score object by specifiying its id. The response is a Score object.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Score.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
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
	@Operation(summary = "Create a new score", description = "Create a whole new Score object. The response is a Score object newly added to the database.")
	@ApiResponses({ @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Score.class), mediaType = "application/json") }), @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
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
