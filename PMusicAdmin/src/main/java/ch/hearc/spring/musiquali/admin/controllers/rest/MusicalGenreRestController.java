
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
import ch.hearc.spring.musiquali.admin.models.database.DbMusicalGenre;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.MusicalGenreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "Musical Genre", description = "Musical genre management APIs")
public class MusicalGenreRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	@Operation(summary = "Retrieve all the musical genres", description = "Get a list containing all the musical genres. The response is a list of MusicalGenre objects.")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MusicalGenre.class), mediaType = "application/json") })
	public ResponseEntity<List<MusicalGenre>> getAll()
		{
		return ResponseEntity.ok(this.musicalGenreService.getAll().stream()//
				.map(MusicalGenreRestController::fetchToMusicalGenre)//
				.toList());
		}

	@GetMapping("/{id}")
	@Operation(summary = "Retrieve a musical genre by id", description = "Get a MusicalGenre object by specifiying its id. The response is a MusicalGenre object.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MusicalGenre.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<MusicalGenre> get(@PathVariable Long id)
		{
		DbMusicalGenre musicalGenre = this.musicalGenreService.getById(id);

		if (musicalGenre != null)
			{
			return ResponseEntity.ok(fetchToMusicalGenre(musicalGenre));
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{id}/leaderboard")
	@Operation(summary = "Retrieve a leaderboard for a specific musical genre", description = "Get the leaderboard corresponding to a MusicalGenre by specifying its id. The response is an array containing MusicalGenre objects.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<List<User>> getLeaderboard(@PathVariable Long id, @RequestParam(required = false, defaultValue = "10") Integer limit)
		{
		DbMusicalGenre musicalGenre = this.musicalGenreService.getById(id);

		if (musicalGenre != null)
			{
			Set<Score> allScores = fetchToMusicalGenre(musicalGenre).getMusics().stream()//
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

	@GetMapping("/{id}/leaderboard/{userId}")
	@Operation(summary = "Retrieve an user position inside the leaderboard for a specific musical genre", description = "Gets the user's position inside a leaderboard from a MusicalGenre by specifying its id. The response is a long value.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Long.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<Long> getLeaderboardPosition(@PathVariable Long id, @PathVariable Long userId)
		{
		DbMusicalGenre musicalGenre = this.musicalGenreService.getById(id);

		if (musicalGenre != null)
			{
			Set<Score> allScores = fetchToMusicalGenre(musicalGenre).getMusics().stream()//
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

	@GetMapping("/{id}/musics")
	@Operation(summary = "Retrieve all musics for a specific musical genre", description = "Get the musics corresponding to a MusicalGenre by specifying its id. The response is an array containing Music objects.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Music.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<List<Music>> getMusics(@PathVariable Long id)
		{
		DbMusicalGenre musicalGenre = this.musicalGenreService.getById(id);

		if (musicalGenre != null)
			{
			// Gets all musics
			return ResponseEntity.ok(fetchToMusicalGenre(musicalGenre).getMusics()//
					.stream()//
					.toList());
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{id}/scores")
	@Operation(summary = "Retrieve all scores for a specific musical genre", description = "Get the scores corresponding to a MusicalGenre by specifying its id. The response is an array containg Score objetcs.")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Score.class), mediaType = "application/json") }), @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<Set<Score>> getScores(@PathVariable Long id)
		{
		DbMusicalGenre musicalGenre = this.musicalGenreService.getById(id);

		if (musicalGenre != null)
			{
			return ResponseEntity.ok(fetchToMusicalGenre(musicalGenre).getMusics().stream()//
					.flatMap(m -> m.getScores().stream())//
					.collect(Collectors.toCollection(HashSet<Score>::new)));
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
	 * Fetchs a musical genre coming from database to a restful musical genre model
	 * @param musicalGenre A musical genre coming from the database
	 * @return A restful musical genre model
	 */
	private static MusicalGenre fetchToMusicalGenre(DbMusicalGenre musicalGenre)
		{
		// Gets some informations with Deezer
		Genre genre = DeezerApi.genres.getById(musicalGenre.getGenreId()).execute();

		Set<Music> musics = musicalGenre.getMusics().stream()//
				.map(m -> {
				// Gets some informations with Deezer
				Track track = DeezerApi.tracks.getById(m.getTrackId()).execute();

				String title = track.getTitleShort();
				String artist = track.getArtist().getName();
				String preview = track.getPreview();
				Integer duration = track.getDuration();
				String link = track.getLink();

				Set<Score> scores = m.getScores().stream()//
						.map(s -> {
						DbUser dbUser = s.getUser();

						// Scores are null because they will be deleted by recursion
						User user = new User(dbUser.getId(), dbUser.getFirstName(), dbUser.getLastName(), dbUser.getEmail(), dbUser.getPassword(), dbUser.getRole(), null);

						// Music is null because it will be deleted by recursion
						return new Score(s.getId(), s.getArtistScore(), s.getTitleScore(), user, null);
						})//
						.collect(Collectors.toCollection(HashSet<Score>::new));

				// Musical genres are null because they will be deleted by recursion
				return new Music(m.getId(), title, artist, preview, m.getDifficulty(), duration, link, null, scores);
				})//
				.collect(Collectors.toCollection(HashSet<Music>::new));

		return new MusicalGenre(musicalGenre.getId(), genre.getName(), musics);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicalGenreService musicalGenreService;
	}
