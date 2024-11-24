package com.buildbot.contactsmanagement.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ContactResponseDto {

	private Long contactId;

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNo;

}
