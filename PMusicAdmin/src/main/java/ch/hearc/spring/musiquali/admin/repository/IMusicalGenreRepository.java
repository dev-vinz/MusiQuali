
package ch.hearc.spring.musiquali.admin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.spring.musiquali.admin.models.database.DbMusicalGenre;

public interface IMusicalGenreRepository extends CrudRepository<DbMusicalGenre, Long>
	{

	Optional<DbMusicalGenre> findByGenreId(Long genreId);
	}
