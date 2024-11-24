package com.buildbot.contactsmanagement.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buildbot.contactsmanagement.entity.Contact;
import com.buildbot.contactsmanagement.exception.ResourceNotFoundException;
import com.buildbot.contactsmanagement.mapper.ContactMapper;
import com.buildbot.contactsmanagement.model.ContactDto;
import com.buildbot.contactsmanagement.repository.ContactRepository;
import com.buildbot.contactsmanagement.responseDto.ContactResponseDto;
import com.buildbot.contactsmanagement.responseDto.ResponseDto;
import com.buildbot.contactsmanagement.service.ContactService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private  ContactRepository contactRepository;
    private  ContactMapper contactMapper; 

    @Override
    public ResponseDto createNewContact(ContactDto contactDto) {
        log.info("Creating new contact with email: {}", contactDto.getEmail());

        Optional<Contact> existEmail = contactRepository.findByEmail(contactDto.getEmail());
        Optional<Contact> existPhoneNo = contactRepository.findByPhoneNo(contactDto.getPhoneNo());

        if (existEmail.isPresent()) {
            log.error("Email already exists: {}", contactDto.getEmail());
            return new ResponseDto("Email Already Exist");
        }

        if (existPhoneNo.isPresent()) {
            log.error("Phone number already exists: {}", contactDto.getPhoneNo());
            return new ResponseDto("PhoneNo Already Exist");
        }

        Contact contact = contactMapper.mapToContact(contactDto); 
        Contact savedContact = contactRepository.save(contact);
        log.info("Contact created successfully with ID: {}", savedContact.getContactId());

        return new ResponseDto("Contact Created Successfully");
    }

    @Override
    public ResponseDto updateContact(String contactId, ContactDto contactDto) {
        log.info("Updating contact with ID: {}", contactId);

        Long id = Long.parseLong(contactId);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "ContactId", id));

        Optional<Contact> existEmail = contactRepository.findByEmail(contactDto.getEmail());
        if (existEmail.isPresent() && !existEmail.get().getContactId().equals(id)) {
            log.error("Email already exists for another contact: {}", contactDto.getEmail());
            return new ResponseDto("Email already exists for another contact");
        }

        Optional<Contact> existPhoneNo = contactRepository.findByPhoneNo(contactDto.getPhoneNo());
        if (existPhoneNo.isPresent() && !existPhoneNo.get().getContactId().equals(id)) {
            log.error("Phone number already exists for another contact: {}", contactDto.getPhoneNo());
            return new ResponseDto("Phone number already exists for another contact");
        }

        Contact updatedContact = contactMapper.updateMapToContact(contactDto, contact); 
        contactRepository.save(updatedContact);
        log.info("Contact updated successfully with ID: {}", updatedContact.getContactId());

        return new ResponseDto("Contact Updated Successfully");
    }

    @Override
    public List<ContactResponseDto> fetchContactsByField(String phoneNo, String email) {
        if (phoneNo == null && email == null) {
            return fetchAllContacts();
        }

        if (phoneNo != null && email == null) {
            return List.of(fetchContactByPhoneNo(phoneNo));
        }

        if (phoneNo == null && email != null) {
            return List.of(fetchContactByEmail(email));
        }

        throw new IllegalArgumentException("Please provide only one of either phoneNo or email, not both.");
    }

    @Override
    public ResponseDto deleteContact(Long contactId) {
        contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "ContactId", contactId));

        contactRepository.deleteById(contactId);
        log.info("Contact deleted successfully with ID: {}", contactId);

        return new ResponseDto("Contact deleted successfully");
    }

    @Override
    @Transactional
    public ContactResponseDto mergeContacts(Long primaryContactId, List<Long> duplicateContactIds) {
        Contact primaryContact = contactRepository.findById(primaryContactId).orElseThrow(() -> {
            return new ResourceNotFoundException("Contact", "ContactId", primaryContactId);
        });

        List<Contact> duplicates = contactRepository.findAllById(duplicateContactIds);

        for (Contact duplicate : duplicates) {
            if (primaryContact.getFirstName() == null && duplicate.getFirstName() != null) {
                primaryContact.setFirstName(duplicate.getFirstName());
            }
            if (primaryContact.getLastName() == null && duplicate.getLastName() != null) {
                primaryContact.setLastName(duplicate.getLastName());
            }
            if (primaryContact.getEmail() == null && duplicate.getEmail() != null) {
                primaryContact.setEmail(duplicate.getEmail());
            }
            if (primaryContact.getPhoneNo() == null && duplicate.getPhoneNo() != null) {
                primaryContact.setPhoneNo(duplicate.getPhoneNo());
            }
            if (primaryContact.getAddress() == null && duplicate.getAddress() != null) {
                primaryContact.setAddress(duplicate.getAddress());
            }
        }

        Contact save = contactRepository.save(primaryContact);
        contactRepository.deleteAll(duplicates);

        return contactMapper.mapToContactResponseDto(save); 
    }

    @Override
    @Cacheable(value = "contactCache", key = "'allContacts'")
    public List<ContactResponseDto> fetchAllContacts() {
        List<Contact> allContacts = contactRepository.findAll();
        return allContacts.stream()
                .map(contactMapper::mapToContactResponseDto) 
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "contactCache", key = "#phoneNo")
    public ContactResponseDto fetchContactByPhoneNo(String phoneNo) {
        Contact contact = contactRepository.findByPhoneNo(phoneNo)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "PhoneNo", phoneNo));
        return contactMapper.mapToContactResponseDto(contact); 
    }

    @Override
    @Cacheable(value = "contactCache", key = "#email")
    public ContactResponseDto fetchContactByEmail(String email) {
        Contact contact = contactRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "Email", email));
        return contactMapper.mapToContactResponseDto(contact); 
    }
}
