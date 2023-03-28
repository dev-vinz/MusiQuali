
package ch.hearc.spring.musiquali.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.spring.musiquali.admin.models.database.DbScore;
import ch.hearc.spring.musiquali.admin.repository.IScoreRepository;
import ch.hearc.spring.musiquali.admin.service.IDatabaseService;

@Service
public class ScoreService implements IDatabaseService<DbScore>
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void add(DbScore item)
		{
		this.scoreRepository.save(item);
		}

	@Override
	public void delete(DbScore item)
		{
		deleteById(item.getId());
		}

	@Override
	public void deleteById(Long id)
		{
		this.scoreRepository.deleteById(Long.valueOf(id));
		}

	@Override
	public void update(DbScore item)
		{
		this.scoreRepository.save(item);
		}

	@Override
	public List<DbScore> getAll()
		{
		List<DbScore> scores = new ArrayList<DbScore>();

		this.scoreRepository//
				.findAll()//
				.forEach(scores::add);

		return scores;
		}

	@Override
	public DbScore getById(Long id)
		{
		return this.scoreRepository//
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
	private IScoreRepository scoreRepository;
	}
