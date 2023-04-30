
package ch.hearc.spring.musiquali.admin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.spring.musiquali.admin.models.database.DbUser;

public interface IUserRepository extends CrudRepository<DbUser, Long> ,PagingAndSortingRepository<DbUser, Long>
	{

	Optional<DbUser> findByEmail(String email);

	@Override
	Page<DbUser> findAll(Pageable pageable);
	}
