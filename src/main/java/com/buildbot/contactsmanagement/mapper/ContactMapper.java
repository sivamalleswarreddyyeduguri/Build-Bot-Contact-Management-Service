package com.buildbot.contactsmanagement.mapper;

import com.buildbot.contactsmanagement.entity.Contact;
import com.buildbot.contactsmanagement.model.ContactDto;
import com.buildbot.contactsmanagement.responseDto.ContactResponseDto;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper {

	public Contact mapToContact(ContactDto contactDto) {
		if (contactDto == null) {
			return null;
		}

		Contact contact = new Contact();
		contact.setFirstName(replaceAndTrim(contactDto.getFirstName()));
		contact.setLastName(replaceAndTrim(contactDto.getLastName()));
		contact.setEmail(contactDto.getEmail());
		contact.setPhoneNo(contactDto.getPhoneNo());

		return contact;
	}

	public ContactResponseDto mapToContactResponseDto(Contact contact) {
		if (contact == null) {
			return null;
		}

		ContactResponseDto responseDto = new ContactResponseDto();
		responseDto.setContactId(contact.getContactId());
		responseDto.setFirstName(replaceAndTrim(contact.getFirstName()));
		responseDto.setLastName(replaceAndTrim(contact.getLastName()));
		responseDto.setEmail(contact.getEmail());
		responseDto.setPhoneNo(contact.getPhoneNo());

		return responseDto;
	}

	public List<ContactResponseDto> listMapToContactResponseDto(List<Contact> contacts) {
		if (contacts == null) {
			return null;
		}

		return contacts.stream().map(this::mapToContactResponseDto).collect(Collectors.toList());
	}

	public Contact updateMapToContact(ContactDto contactDto, Contact contact) {
		if (contactDto == null || contact == null) {
			return null;
		}

		contact.setFirstName(replaceAndTrim(contactDto.getFirstName()));
		contact.setLastName(replaceAndTrim(contactDto.getLastName()));
		contact.setEmail(contactDto.getEmail());
		contact.setPhoneNo(contactDto.getPhoneNo());

		return contact;
	}

	private String replaceAndTrim(String input) {
		if (input != null) {
			String doubleSpaces = input.replaceAll("\\s+", " ");
			return doubleSpaces.trim().toUpperCase();
		}
		return null;
	}
}
