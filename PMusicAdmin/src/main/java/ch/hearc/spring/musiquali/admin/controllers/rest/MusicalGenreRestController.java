
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

@RestController
@RequestMapping("/api/genres")
public class MusicalGenreRestController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	public ResponseEntity<List<MusicalGenre>> getAll()
		{
		return ResponseEntity.ok(this.musicalGenreService.getAll().stream()//
				.map(MusicalGenreRestController::fetchToMusicalGenre)//
				.toList());
		}

	@GetMapping("/{id}")
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
