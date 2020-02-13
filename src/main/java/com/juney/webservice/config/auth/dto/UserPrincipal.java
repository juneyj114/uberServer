package com.juney.webservice.config.auth.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	
	public UserPrincipal(Long id, String firstName, String lastName, String email, String password,Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	 public static UserPrincipal create(User user) {
	        List<GrantedAuthority> authorities = Collections.
	                singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));

	        return new UserPrincipal(
	                user.getId(),
	                user.getFirstName(),
	                user.getLastName(),
	                user.getEmail(),
	                user.getPassword(),
	                authorities
	        );
	    }
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return String.valueOf(id);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return firstName + " " + lastName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
