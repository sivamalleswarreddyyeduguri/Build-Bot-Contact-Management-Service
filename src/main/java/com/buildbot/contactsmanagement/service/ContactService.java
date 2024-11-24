package com.buildbot.contactsmanagement.service;

import com.buildbot.contactsmanagement.model.ContactDto;
import com.buildbot.contactsmanagement.responseDto.ContactResponseDto;
import com.buildbot.contactsmanagement.responseDto.ResponseDto;

import java.util.List;

public interface ContactService {
    ResponseDto createNewContact(ContactDto contactDto);
    ResponseDto updateContact(String contactId, ContactDto contactDto);
    List<ContactResponseDto> fetchContactsByField(String phoneNo, String email);
    ResponseDto deleteContact(Long contactId);
    ContactResponseDto mergeContacts(Long primaryContactId, List<Long> duplicateContactIds);
    List<ContactResponseDto> fetchAllContacts();
    ContactResponseDto fetchContactByPhoneNo(String phoneNo);
    ContactResponseDto fetchContactByEmail(String email);
}
