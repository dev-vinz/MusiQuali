
package ch.hearc.jee.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.jee.model.MusicalGenre;

public interface IMusicalGenreRepository extends CrudRepository<MusicalGenre, Long>
	{

	Optional<MusicalGenre> findByGenreId(Long genreId);
	}
