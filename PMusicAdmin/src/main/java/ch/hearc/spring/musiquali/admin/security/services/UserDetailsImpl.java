
package ch.hearc.spring.musiquali.admin.security.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.hearc.spring.musiquali.admin.models.rest.User;

public class UserDetailsImpl implements UserDetails
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public UserDetailsImpl(Long id, String firstName, String lastName, String email, String password, Collection<? extends GrantedAuthority> authorities)
		{
		// Inputs & Outputs
			{
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
			this.authorities = authorities;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public boolean isAccountNonExpired()
		{
		return true;
		}

	@Override
	public boolean isAccountNonLocked()
		{
		return true;
		}

	@Override
	public boolean isCredentialsNonExpired()
		{
		return true;
		}

	@Override
	public boolean isEnabled()
		{
		return true;
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static UserDetailsImpl build(User user)
		{
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

		return new UserDetailsImpl(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), authorities);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Long getId()
		{
		return this.id;
		}

	@Override
	public String getUsername()
		{
		return this.email;
		}

	public String getFirstName()
		{
		return this.firstName;
		}

	public String getLastName()
		{
		return this.lastName;
		}

	public String getEmail()
		{
		return this.email;
		}

	@Override
	public String getPassword()
		{
		return this.password;
		}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
		{
		return this.authorities;
		}

	/*------------------------------*\
	|*				equals			*|
	\*------------------------------*/

	public boolean isEquals(UserDetailsImpl userDetails)
		{
		if (this == userDetails)
			{
			return true;
			}
		else
			{
			return this.id.longValue() == userDetails.id.longValue();
			}
		}

	@Override
	public boolean equals(Object object2)
		{
		if (object2.getClass().equals(this.getClass()))
			{
			return isEquals((UserDetailsImpl)object2);
			}
		else
			{
			return false;
			}
		}

	@Override
	public int hashCode()
		{
		return Long.hashCode(this.id);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs & Outputs
	private Long id;

	private String firstName;
	private String lastName;
	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	}
