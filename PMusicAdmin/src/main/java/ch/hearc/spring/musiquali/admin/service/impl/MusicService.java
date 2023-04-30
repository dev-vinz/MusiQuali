
package ch.hearc.spring.musiquali.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.spring.musiquali.admin.models.database.DbMusic;
import ch.hearc.spring.musiquali.admin.repository.IMusicRepository;
import ch.hearc.spring.musiquali.admin.service.IDatabaseService;

@Service
public class MusicService implements IDatabaseService<DbMusic>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void add(DbMusic item)
		{
		this.musicRepository.save(item);
		}

	@Override
	public void delete(DbMusic item)
		{
		deleteById(item.getId());
		}

	@Override
	public void deleteById(Long id)
		{
		this.musicRepository.deleteById(Long.valueOf(id));
		}

	@Override
	public void update(DbMusic item)
		{
		this.musicRepository.save(item);
		}

	@Override
	public List<DbMusic> getAll()
		{
		List<DbMusic> musics = new ArrayList<DbMusic>();

		this.musicRepository//
				.findAll()//
				.forEach(musics::add);

		return musics;
		}

	@Override
	public DbMusic getById(Long id)
		{
		return this.musicRepository//
				.findById(Long.valueOf(id))//
				.orElseGet(() -> null);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private IMusicRepository musicRepository;
	}
