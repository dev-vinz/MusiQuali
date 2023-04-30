
package ch.hearc.spring.musiquali.admin.repository;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.spring.musiquali.admin.models.database.DbMusic;

public interface IMusicRepository extends CrudRepository<DbMusic, Long>
	{

	}
