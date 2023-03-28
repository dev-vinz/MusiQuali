
package ch.hearc.spring.musiquali.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.spring.musiquali.admin.models.database.DbMusicalGenre;
import ch.hearc.spring.musiquali.admin.repository.IMusicalGenreRepository;
import ch.hearc.spring.musiquali.admin.service.IDatabaseService;

@Service
public class MusicalGenreService implements IDatabaseService<DbMusicalGenre>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void add(DbMusicalGenre item)
		{
		this.musicalGenreRepository.save(item);
		}

	@Override
	public void delete(DbMusicalGenre item)
		{
		deleteById(item.getId());
		}

	@Override
	public void deleteById(Long id)
		{
		this.musicalGenreRepository.deleteById(Long.valueOf(id));
		}

	@Override
	public void update(DbMusicalGenre item)
		{
		this.musicalGenreRepository.save(item);
		}

	@Override
	public List<DbMusicalGenre> getAll()
		{
		List<DbMusicalGenre> genres = new ArrayList<DbMusicalGenre>();

		this.musicalGenreRepository//
				.findAll()//
				.forEach(genres::add);

		return genres;
		}

	@Override
	public DbMusicalGenre getById(Long id)
		{
		return this.musicalGenreRepository//
				.findById(Long.valueOf(id))//
				.orElseGet(() -> null);
		}

	public DbMusicalGenre getByGenreId(Long id)
		{
		Optional<DbMusicalGenre> musicalGenre = this.musicalGenreRepository//
				.findByGenreId(id);

		if (musicalGenre.isPresent())
			{
			return musicalGenre.get();
			}
		else
			{
			return null;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private IMusicalGenreRepository musicalGenreRepository;
	}
