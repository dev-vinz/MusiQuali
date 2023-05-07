
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Genre;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/difficulties")
@Tag(name = "Difficulty", description = "Difficulty management APIs")
public class DifficultyRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	@Operation(summary = "Retrieve all the difficulties", description = "Get a list containing all the difficulties. The response is a list of Difficulty objects.")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Difficulty.class), mediaType = "application/json") })
	private ResponseEntity<List<Difficulty>> getAll()
		{
		return ResponseEntity.ok(List.of(Difficulty.values()));
		}

	@GetMapping("/{index}")
	@Operation(summary = "Retrieve a difficulty by index", description = "Get a Difficulty object by specifying its index. The response is a Difficulty object.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Difficulty.class), mediaType = "application/json") }), @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	private ResponseEntity<Difficulty> get(@PathVariable Integer index)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		else if (index < difficulties.length)
			{
			return ResponseEntity.ok(difficulties[index]);
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{index}/leaderboard")
	@Operation(summary = "Retrieve a leaderboard for a specific difficulty", description = "Get the leaderboard corresponding to a Difficulty by specifying its index. The response is an array containing User objects.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }), @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<List<User>> getLeaderboard(@PathVariable Integer index, @RequestParam(required = false, defaultValue = "10") Integer limit)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		else if (index < difficulties.length)
			{
			Difficulty difficulty = difficulties[index];

			Set<Score> allScores = this.musicService.getAll().stream()//
					.map(DifficultyRestController::fetchToMusic)//
					.filter(m -> m.getDifficulty() == difficulty)//
					.flatMap(m -> m.getScores().stream())//
					.collect(Collectors.toCollection(HashSet<Score>::new));

			return ResponseEntity.ok(allScores.stream()//
					.collect(Collectors.groupingBy(Score::getUser, Collectors.summingLong(s -> s.getArtistValue() + s.getTitleValue())))//
					.entrySet()//
					.stream()//
					.sorted(Collections.reverseOrder(Entry.comparingByValue()))// Max score is on top
					.map(Entry::getKey)//
					.distinct()//
					.limit(limit)//
					.toList());
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{index}/leaderboard/{userId}")
	@Operation(summary = "Retrieve an user position inside the leaderboard for a specific difficulty", description = "Get the user's position inside a leaderboard from a specific Difficulty by specifying its index. The response is a long value.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Long.class), mediaType = "application/json") }), @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<Long> getLeaderboardPosition(@PathVariable Integer index, @PathVariable Long userId)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		else if (index < difficulties.length)
			{
			Difficulty difficulty = difficulties[index];

			Set<Score> allScores = this.musicService.getAll().stream()//
					.map(DifficultyRestController::fetchToMusic)//
					.filter(m -> m.getDifficulty() == difficulty)//
					.flatMap(m -> m.getScores().stream())//
					.collect(Collectors.toCollection(HashSet<Score>::new));

			List<User> leaderboard = allScores.stream()//
					.collect(Collectors.groupingBy(Score::getUser, Collectors.summingLong(s -> s.getArtistValue() + s.getTitleValue())))//
					.entrySet()//
					.stream()//
					.sorted(Collections.reverseOrder(Entry.comparingByValue()))// Max score is on top
					.map(Entry::getKey)//
					.distinct()//
					.toList();

			OptionalLong result = LongStream.range(0, leaderboard.size())//
					.filter(i -> leaderboard.get((int)i).getId() == userId)//
					.findFirst();

			if (result.isPresent())
				{
				return ResponseEntity.ok(result.getAsLong());
				}
			else
				{
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{index}/musics")
	@Operation(summary = "Retrieve all musics for a specific difficulty", description = "Get the musics corresponding to a Difficulty level by specifying its index. The response is an array containing Music objects.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Music.class), mediaType = "application/json") }), @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<List<Music>> getMusics(@PathVariable Integer index)
		{
		Difficulty[] difficulties = Difficulty.values();

		if (index < 0)
			{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		else if (index < difficulties.length)
			{
			Difficulty difficulty = difficulties[index];

			// Gets all musics
			return ResponseEntity.ok(this.musicService.getAll().stream()//
					.map(DifficultyRestController::fetchToMusic)//
					.filter(m -> m.getDifficulty() == difficulty)//
					.toList());
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
		Track track = DeezerApi.tracks.getById(music.getTrackId()).execute();

		String title = track.getTitleShort();
		String artist = track.getArtist().getName();
		String preview = track.getPreview();
		Integer duration = track.getDuration();
		String link = track.getLink();

		Set<MusicalGenre> genres = music.getGenres().stream()//
				.map(g -> {
				Genre genre = DeezerApi.genres.getById(g.getGenreId()).execute();
				Set<Music> musics = g.getMusics().stream()//
						.map(m -> {
						try
							{
							Thread.sleep(100);
							}
						catch (InterruptedException e)
							{
							}

						// Gets some informations with Deezer
						Track mTrack = DeezerApi.tracks.getById(music.getTrackId()).execute();

						String mTitle = mTrack.getTitleShort();
						String mArtist = mTrack.getArtist().getName();
						String mPreview = mTrack.getPreview();
						Integer mDuration = mTrack.getDuration();
						String mLink = mTrack.getLink();

						// Genres and scores are null because they will be deleted by recursion
						return new Music(m.getId(), mTitle, mArtist, mPreview, m.getDifficulty(), mDuration, mLink, null, null);
						})//
						.collect(Collectors.toCollection(HashSet<Music>::new));

				return new MusicalGenre(g.getId(), genre.getName(), musics);
				})//
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
