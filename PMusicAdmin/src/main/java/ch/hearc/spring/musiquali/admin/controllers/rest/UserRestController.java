
package ch.hearc.spring.musiquali.admin.controllers.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Album;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
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
	@ResponseStatus(value = HttpStatus.OK)
	public List<User> all()
		{
		List<DbUser> allUsers = this.userService.getAll();

		return allUsers.stream()//
				.map(u -> new User(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.getRole(), fetchScores(u)))//
				.toList();
		}

	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User get(@PathVariable Long id)
		{
		DbUser user = this.userService.getById(id);

		if (user != null)
			{
			return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), fetchScores(user));
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id \"" + id + "\" not found");
			}
		}

	@GetMapping("/{id}/scores")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Score> getScores(@PathVariable Long id)
		{
		DbUser user = this.userService.getById(id);

		if (user != null)
			{
			return fetchScores(user);
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id \"" + id + "\" not found");
			}
		}

	@GetMapping("/email/{email}")
	@ResponseStatus(value = HttpStatus.OK)
	public User get(@PathVariable String email)
		{
		DbUser user = this.userService.getByEmail(email);

		if (user != null)
			{
			return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), fetchScores(user));
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email \"" + email + "\" not found");
			}
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public User create(@RequestBody User user)
		{
		// Checks if email already exists
		DbUser existingUser = this.userService.getByEmail(user.getEmail());

		if (existingUser != null)
			{ throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email \"" + user.getEmail() + "\" already exists"); }

		DbUser newUser = new DbUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
		this.userService.add(newUser);

		return new User(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getRole(), user.getScores());
		}

	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public User update(@RequestBody User updatedUser, @PathVariable Long id)
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

			return new User(oldUser.getId(), oldUser.getFirstName(), oldUser.getLastName(), oldUser.getEmail(), oldUser.getPassword(), oldUser.getRole(), updatedUser.getScores());
			}
		else
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id \"" + id + "\" not found");
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
				.map(s -> {
				DbMusic dbMusic = s.getMusic();

				// Gets some informations with Deezer
				Track track = DeezerApi.tracks.getById(dbMusic.getId()).execute();
				Album album = DeezerApi.albums.getById(track.getAlbum().getId()).execute();

				String title = track.getTitleShort();
				String artist = track.getArtist().getName();
				String preview = track.getPreview();
				Integer duration = track.getDuration();
				String link = track.getLink();

				// Musics are null because they will be deleted by recursion
				Set<MusicalGenre> genres = album.getGenres().stream()//
						.map(g -> new MusicalGenre(g.getId(), g.getName(), null)).collect(Collectors.toCollection(HashSet<MusicalGenre>::new));

				// Scores are null because they will be deleted by recursion
				Music music = new Music(dbMusic.getId(), title, artist, preview, dbMusic.getDifficulty(), duration, link, genres, null);

				// User is null because it will be deleted by recursion
				return new Score(s.getId(), s.getArtistScore(), s.getTitleScore(), null, music);
				})//
				.toList();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private UserService userService;
	}
