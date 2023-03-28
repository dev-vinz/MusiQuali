
package ch.hearc.spring.musiquali.admin.repository;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.spring.musiquali.admin.models.database.DbScore;

public interface IScoreRepository extends CrudRepository<DbScore, Long>
	{
	}
