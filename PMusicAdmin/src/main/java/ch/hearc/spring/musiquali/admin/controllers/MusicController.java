
package ch.hearc.spring.musiquali.admin.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.spring.musiquali.admin.api.deezer.DeezerApi;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Album;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Genre;
import ch.hearc.spring.musiquali.admin.api.deezer.models.Track;
import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.models.database.DbMusicalGenre;
import ch.hearc.spring.musiquali.admin.models.rest.Music;
import ch.hearc.spring.musiquali.admin.models.rest.MusicalGenre;
import ch.hearc.spring.musiquali.admin.service.impl.MusicService;
import ch.hearc.spring.musiquali.admin.service.impl.MusicalGenreService;

@Controller
@RequestMapping("/admin/musics")
public class MusicController
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping(value = { "/", "index" })
	public String index(Model model)
		{
		// Gets all musics
		List<DbMusic> dbMusics = this.musicService.getAll();

		// Transforms into displayable musics
		Music[] musics = dbMusics.stream()//
				.parallel()//
				.map(db -> {
				// Recovers some datas from deezer
				Track track = DeezerApi.tracks.getById(db.getTrackId()).execute();

				Set<MusicalGenre> genres = db.getGenres()//
						.stream()//
						.map(g -> {
						Genre genre = DeezerApi.genres.getById(g.getGenreId()).execute();

						return new MusicalGenre(g.getId(), genre.getName(), null);
						})//
						.collect(Collectors.toSet());

				return new Music(db.getId(), track.getTitleShort(), track.getArtist().getName(), track.getPreview(), db.getDifficulty(), track.getDuration(), track.getLink(), genres, null);
				})//
				.toArray(Music[]::new);

		model.addAttribute("musics", musics);

		return "music/index";
		}

	@GetMapping(value = { "/search" })
	public String search(Model model, @RequestParam(name = "q", required = false) String query)
		{
		if (query == null)
			{ return "music/search"; }

		Track[] tracks = DeezerApi.searchs.track(query).execute()//
				.getData()//
				.toArray(Track[]::new);

		model.addAttribute("tracks", tracks);
		model.addAttribute("difficulties", Difficulty.values());

		return "music/search";
		}

	/*------------------------------*\
	|*				Post			*|
	\*------------------------------*/

	@PostMapping(value = { "/add" })
	public String add(Principal principal, @RequestParam Long trackId, @RequestParam Difficulty difficulty)
		{
		// TODO: Checks if logged user has rights to do this

		// Gest informations
		Track track = DeezerApi.tracks.getById(trackId).execute();
		Album album = DeezerApi.albums.getById(track.getAlbum().getId()).execute();

		// Checks if musi already exists
		Boolean isMusic = this.musicService.getAll()//
				.stream()//
				.anyMatch(m -> m.getTrackId().longValue() == trackId.longValue());

		if (!isMusic)
			{
			// Creates and adds music
			DbMusic music = new DbMusic(trackId, difficulty);
			this.musicService.add(music);

			// Creates all genres
			List<DbMusicalGenre> dbGenres = album.getGenres()//
					.stream()//
					.map(this::parseMusicalGenreToDbMusicalGenre)//
					.collect(Collectors.toList());

			// Saves genres and adds them to music
			dbGenres//
					.stream()//
					.peek(musicalGenreService::add)//
					.forEach(music::addGenre);

			// Updates music
			this.musicService.update(music);

			return "redirect:/admin/musics/search?added";
			}
		else
			{
			return "redirect:/admin/musics/search?error";
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private DbMusicalGenre parseMusicalGenreToDbMusicalGenre(Genre genre)
		{
		// Checks if musical genre already exists
		DbMusicalGenre mGenre = this.musicalGenreService.getByGenreId(genre.getId());

		if (mGenre == null)
			{
			mGenre = new DbMusicalGenre(genre.getId());
			}

		return mGenre;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private MusicService musicService;

	@Autowired
	private MusicalGenreService musicalGenreService;
	}
