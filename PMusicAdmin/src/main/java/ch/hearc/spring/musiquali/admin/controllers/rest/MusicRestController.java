
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
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;

@RestController
@RequestMapping("/api/musics")
public class MusicRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	public ResponseEntity<List<Music>> getAll()
		{
		return ResponseEntity.ok(this.musicService.getAll().stream()//
				.map(MusicRestController::fetchToMusic)//
				.toList());
		}

	@GetMapping("/{id}")
	public ResponseEntity<Music> get(@PathVariable Long id)
		{
		DbMusic music = this.musicService.getById(id);

		if (music != null)
			{
			return ResponseEntity.ok(fetchToMusic(music));
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/{id}/leaderboard")
	public ResponseEntity<List<User>> getLeaderboard(@PathVariable Long id, @RequestParam(required = false, defaultValue = "10") Integer limit)
		{
		DbMusic music = this.musicService.getById(id);

		if (music != null)
			{
			Set<Score> allScores = fetchToMusic(music).getScores();

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
		DbMusic music = this.musicService.getById(id);

		if (music != null)
			{
			List<User> leaderboard = fetchToMusic(music).getScores().stream()//
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

	@GetMapping("/{id}/scores")
	public ResponseEntity<Set<Score>> getScores(@PathVariable Long id)
		{
		DbMusic music = this.musicService.getById(id);

		if (music != null)
			{
			return ResponseEntity.ok(fetchToMusic(music).getScores());
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
