package com.buildbot.contactsmanagement.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.buildbot.contactsmanagement.entity.User;
import com.buildbot.contactsmanagement.repository.UserRepository;

@Component
public class UserInfoDetailesService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		System.out.println(user.get().getRoles());
		return user.map(o->new UserInfoDetails(o))
				   .orElseThrow(()->new UsernameNotFoundException(username));
		
		
		
	}

}