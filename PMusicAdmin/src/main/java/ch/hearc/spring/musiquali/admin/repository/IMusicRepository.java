
package ch.hearc.spring.musiquali.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.spring.musiquali.admin.models.Difficulty;
import ch.hearc.spring.musiquali.admin.models.database.DbMusic;

public interface IMusicRepository extends CrudRepository<DbMusic, Long> ,PagingAndSortingRepository<DbMusic, Long>
	{

	public List<DbMusic> findAllByDifficulty(Difficulty difficulty);
	}
