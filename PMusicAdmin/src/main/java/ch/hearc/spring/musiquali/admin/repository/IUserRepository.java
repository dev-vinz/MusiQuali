
package ch.hearc.spring.musiquali.admin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.spring.musiquali.admin.models.database.DbUser;

public interface IUserRepository extends CrudRepository<DbUser, Long>
	{

	Optional<DbUser> findByEmail(String email);
	}
