package com.buildbot.contactsmanagement.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buildbot.contactsmanagement.entity.User;
import com.buildbot.contactsmanagement.exception.ResourceAlreadyExistException;
import com.buildbot.contactsmanagement.mapper.UserMapper;
import com.buildbot.contactsmanagement.model.UserDto;
import com.buildbot.contactsmanagement.repository.UserRepository;
import com.buildbot.contactsmanagement.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	@Override
	public void saveUser(UserDto userDto) {

		Optional<User> byUserName = userRepository.findByUserName(userDto.getUserName());
		if (byUserName.isPresent()) {
			log.error("Username already taken: {}", userDto.getUserName());
			throw new ResourceAlreadyExistException("Username already taken: " + userDto.getUserName());
		}

		Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
		if (byEmail.isPresent()) {
			log.error("Email already exists: {}", userDto.getEmail());
			throw new ResourceAlreadyExistException("Email already exists.");
		}

		User user = UserMapper.mapToUser(userDto);
		user.setPassword(encoder.encode(userDto.getPassword()));

		log.info("User saved successfully with username: {}", userDto.getUserName());
		userRepository.save(user);
	}
}
