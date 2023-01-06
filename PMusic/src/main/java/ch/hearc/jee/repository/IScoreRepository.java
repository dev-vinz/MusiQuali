
package ch.hearc.jee.repository;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.jee.model.Score;

public interface IScoreRepository extends CrudRepository<Score, Long>
	{
	}
