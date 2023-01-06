
package ch.hearc.jee.repository;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.jee.model.Music;

public interface IMusicRepository extends CrudRepository<Music, Long>
	{
	}
