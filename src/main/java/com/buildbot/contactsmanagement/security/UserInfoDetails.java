package com.buildbot.contactsmanagement.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.buildbot.contactsmanagement.entity.User;

public class UserInfoDetails implements UserDetails {

	private String userName;
	private String password;
	private List<GrantedAuthority> authontication;

	public UserInfoDetails(User user) {
		this.userName = user.getEmail();
		this.password = user.getPassword();
		this.authontication = Arrays.stream(user.getRoles().split(",")).map(o -> new SimpleGrantedAuthority(o))
				.collect(Collectors.toList());

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authontication;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
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
