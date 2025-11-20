package com.hirehub.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hirehub.entity.User;

import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {
	
	private final Long id;
	private final String email;
	private final String password;
	private final Set<String> roles;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
		this.authorities = this.roles.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
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
