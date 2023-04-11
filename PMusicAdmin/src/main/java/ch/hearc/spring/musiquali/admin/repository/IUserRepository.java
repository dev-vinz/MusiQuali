
package ch.hearc.spring.musiquali.admin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.spring.musiquali.admin.models.database.User;

public interface IUserRepository extends CrudRepository<User, Long>
	{

	Optional<User> findByEmail(String email);
	}
