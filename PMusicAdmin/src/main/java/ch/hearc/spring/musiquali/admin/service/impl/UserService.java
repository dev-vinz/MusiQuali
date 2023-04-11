
package ch.hearc.spring.musiquali.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.spring.musiquali.admin.models.database.User;
import ch.hearc.spring.musiquali.admin.repository.IUserRepository;
import ch.hearc.spring.musiquali.admin.service.IDatabaseService;

@Service
public class UserService implements IDatabaseService<User>
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void add(User item)
		{
		//String hashPassword = this.passwordEncoder.encode(item.getPassword());
		//item.setPassword(hashPassword);

		this.userRepository.save(item);
		}

	@Override
	public void delete(User item)
		{
		deleteById(item.getId());
		}

	@Override
	public void deleteById(Long id)
		{
		this.userRepository.deleteById(Long.valueOf(id));
		}

	@Override
	public void update(User item)
		{
		this.userRepository.save(item);
		}

	@Override
	public List<User> getAll()
		{
		List<User> users = new ArrayList<User>();

		this.userRepository//
				.findAll()//
				.forEach(users::add);

		return users;
		}

	@Override
	public User getById(Long id)
		{
		return this.userRepository//
				.findById(Long.valueOf(id))//
				.orElseGet(() -> null);
		}

	public User getByEmail(String email)
		{
		Optional<User> user = this.userRepository//
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

	//@Autowired
	//private PasswordEncoder passwordEncoder;
	}
