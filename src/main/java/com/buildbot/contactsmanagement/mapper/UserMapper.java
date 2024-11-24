package com.buildbot.contactsmanagement.mapper;

import com.buildbot.contactsmanagement.entity.User;
import com.buildbot.contactsmanagement.model.UserDto;

public class UserMapper {

	public static User mapToUser(UserDto userDto) {
		User user = new User();

		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRoles(userDto.getRoles());

		user.setFirstName(replaceAndTrim(userDto.getFirstName()));
		user.setLastName(replaceAndTrim(userDto.getLastName()));

		return user;
	}

	private static String replaceAndTrim(String input) {
		if (input != null) {
			String doubleSpaces = input.replaceAll("\\s+", " ");
			return doubleSpaces.trim().toUpperCase();
		}
		return null;
	}
}
