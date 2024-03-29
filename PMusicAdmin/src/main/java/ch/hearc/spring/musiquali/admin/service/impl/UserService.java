
package ch.hearc.spring.musiquali.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.spring.musiquali.admin.models.Role;
import ch.hearc.spring.musiquali.admin.models.database.DbUser;
import ch.hearc.spring.musiquali.admin.repository.IUserRepository;
import ch.hearc.spring.musiquali.admin.service.IDatabaseService;

import jakarta.annotation.PostConstruct;

@Service
public class UserService implements IDatabaseService<DbUser>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	@PostConstruct
	public void postInit()
		{
		add(new DbUser("Admin", "Admin", "admin@musiquali.ch", "Admin1234", Role.ADMIN));
		add(new DbUser("Vincent", "Jeannin", "vincent.jeannin@he-arc.ch", "Password1234", Role.MODERATOR));
		add(new DbUser("Théo", "Vuilliomenet", "theo.vuilliomenet@he-arc.ch", "Password1234", Role.MODERATOR));
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void add(DbUser item)
		{
		this.userRepository.save(item);
		}

	@Override
	public void delete(DbUser item)
		{
		deleteById(item.getId());
		}

	@Override
	public void deleteById(Long id)
		{
		this.userRepository.deleteById(Long.valueOf(id));
		}

	@Override
	public void update(DbUser item)
		{
		this.userRepository.save(item);
		}

	@Override
	public List<DbUser> getAll()
		{
		List<DbUser> users = new ArrayList<DbUser>();

		this.userRepository//
				.findAll()//
				.forEach(users::add);

		return users;
		}

	public Page<DbUser> getAll(Pageable pageable)
		{
		return this.userRepository.findAll(pageable);
		}

	@Override
	public DbUser getById(Long id)
		{
		return this.userRepository//
				.findById(Long.valueOf(id))//
				.orElseGet(() -> null);
		}

	public DbUser getByEmail(String email)
		{
		Optional<DbUser> user = this.userRepository//
				.findByEmail(email);

		if (user.isPresent())
			{
			return user.get();
			}
		else
			{
			return null;
			}
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@Autowired
	private IUserRepository userRepository;
	}
