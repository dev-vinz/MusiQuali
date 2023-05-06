
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.Score;
import ch.hearc.spring.musiquali.admin.models.rest.User;
import ch.hearc.spring.musiquali.admin.service.impl.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping
	public ResponseEntity<List<User>> all()
		{
		List<DbUser> allUsers = this.userService.getAll();

		return ResponseEntity.ok(allUsers.stream()//
				.map(u -> new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.getRole(), fetchScores(u)))//
				.toList());
		}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable Long id)
		{
		DbUser user = this.userService.getById(id);

		if (user != null)
			{
			return ResponseEntity.ok(new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), fetchScores(user)));
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/leaderboard")
	public ResponseEntity<List<User>> getLeaderboard()
		{
		return ResponseEntity.ok(this.userService.getAll()//
				.stream()//
				.flatMap(u -> fetchScores(u).stream())//
				.collect(Collectors.groupingBy(Score::getUser, Collectors.summingLong(s -> s.getArtistValue() + s.getTitleValue())))//
				.entrySet()//
				.stream()//
				.sorted(Collections.reverseOrder(Entry.comparingByValue()))//
				.map(Entry::getKey)//
				.distinct()//
				.toList());
		}

	@GetMapping("/{id}/leaderboard")
	public ResponseEntity<Long> getLeaderboardPosition(@PathVariable Long id)
		{
		List<User> leaderboard = this.userService.getAll()//
				.stream()//
				.flatMap(u -> fetchScores(u).stream())//
				.collect(Collectors.groupingBy(Score::getUser, Collectors.summingLong(s -> s.getArtistValue() + s.getTitleValue())))//
				.entrySet()//
				.stream()//
				.sorted(Collections.reverseOrder(Entry.comparingByValue()))//
				.map(Entry::getKey)//
				.distinct()//
				.toList();

		OptionalLong result = LongStream.range(0, leaderboard.size())//
				.filter(i -> leaderboard.get((int)i).getId() == id)//
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

	@GetMapping("/{id}/scores")
	public ResponseEntity<List<Score>> getScores(@PathVariable Long id, @RequestParam(name = "difficulty", required = false) String diff)
		{
		DbUser user = this.userService.getById(id);

		if (user != null)
			{
			try
				{
				Difficulty difficulty = Difficulty.valueOf(diff);

				return ResponseEntity.ok(fetchScores(user).stream()//
						.filter(s -> s.getMusic().getDifficulty() == difficulty)//
						.toList());
				}
			catch (Exception e)
				{
				return ResponseEntity.ok(fetchScores(user));
				}
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> get(@PathVariable String email)
		{
		DbUser user = this.userService.getByEmail(email);

		if (user != null)
			{
			return ResponseEntity.ok(new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), fetchScores(user)));
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
	public ResponseEntity<User> create(@RequestBody User user)
		{
		// Checks if email already exists
		DbUser existingUser = this.userService.getByEmail(user.getEmail());

		if (existingUser != null)
			{ return new ResponseEntity<>(null, HttpStatus.CONFLICT); }

		DbUser newUser = new DbUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
		this.userService.add(newUser);

		return new ResponseEntity<>(new User(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getRole(), user.getScores()), HttpStatus.CREATED);
		}

	/*------------------------------*\
	|*				Put				*|
	\*------------------------------*/

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@RequestBody User updatedUser, @PathVariable Long id)
		{
		DbUser oldUser = this.userService.getById(id);

		if (oldUser != null)
			{
			String firstName = Optional.ofNullable(updatedUser.getFirstName()).orElse(oldUser.getFirstName());
			String lastName = Optional.ofNullable(updatedUser.getLastName()).orElse(oldUser.getLastName());
			String email = Optional.ofNullable(updatedUser.getEmail()).orElse(oldUser.getEmail());
			String password = Optional.ofNullable(updatedUser.getPassword()).orElse(oldUser.getPassword());
			Role role = Optional.ofNullable(updatedUser.getRole()).orElse(oldUser.getRole());

			oldUser.setFirstName(firstName);
			oldUser.setLastName(lastName);
			oldUser.setEmail(email);
			oldUser.setPassword(password);
			oldUser.setRole(role);

			this.userService.update(oldUser);

			return ResponseEntity.ok(new User(oldUser.getId(), oldUser.getFirstName(), oldUser.getLastName(), oldUser.getEmail(), oldUser.getPassword(), oldUser.getRole(), updatedUser.getScores()));
			}
		else
			{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}

	/*------------------------------*\
	|*				Delete			*|
	\*------------------------------*/

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id)
		{
		DbUser user = this.userService.getById(id);

		if (user != null)
			{
			this.userService.delete(user);

			return ResponseEntity.ok(true);
			}
		else
			{
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * Gets all the scores associated at this user instance
	 * @param user A user from the database
	 * @return A list containing all the user scores
	 */
	private static List<Score> fetchScores(DbUser user)
		{
		return user.getScores().stream()//
				.parallel()//
				.map(s -> {
				DbMusic dbMusic = s.getMusic();

				// Gets some informations with Deezer
				Track track = DeezerApi.tracks.getById(dbMusic.getTrackId()).execute();

				String title = track.getTitleShort();
				String artist = track.getArtist().getName();
				String preview = track.getPreview();
				Integer duration = track.getDuration();
				String link = track.getLink();

				// Scores are null because they will be deleted by recursion
				Music music = new Music(dbMusic.getId(), title, artist, preview, dbMusic.getDifficulty(), duration, link, null, null);

				User scoreUser = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), null);

				return new Score(s.getId(), s.getArtistScore(), s.getTitleScore(), scoreUser, music);
				})//
				.toList();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private UserService userService;
	}
