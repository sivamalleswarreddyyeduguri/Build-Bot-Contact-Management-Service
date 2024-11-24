package com.buildbot.contactsmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.buildbot.contactsmanagement.constants.ContactsManagementConstants;
import com.buildbot.contactsmanagement.model.ContactDto;
import com.buildbot.contactsmanagement.responseDto.ContactResponseDto;
import com.buildbot.contactsmanagement.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/app/contacts-management/api/v1")
@OpenAPIDefinition(info = @Info(title = "Contacts Management API", version = "1.0", description = "APIs for managing contacts"))
@Tag(name = "Contacts Management APIs", description = "APIs to perform various operations on contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "Create a new contact", description = "Creates a new contact with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contact successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/create-contact")
    public ResponseEntity<?> createNewContact(@RequestBody @Valid ContactDto contactDto) {
        log.info("Request to create a new contact received with data: {}", contactDto);
        return contactService.createNewContact(contactDto);
    }

    @Operation(summary = "Update an existing contact", description = "Updates the details of an existing contact")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact successfully updated", content = {@Content(schema = @Schema(implementation = ContactResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/update-contact/{contactId}")
    public ResponseEntity<?> updateContact(
            @Parameter(description = "ID of the contact to update", required = true) @PathVariable String contactId,
            @RequestBody @Valid ContactDto contactDto) {
        log.info("Request to update contact received with ID: {} and data: {}", contactId, contactDto);
        return contactService.updateContact(contactId, contactDto);
    }

    @Operation(summary = "Fetch contacts by field", description = "Fetches contacts by phone number or email, or retrieves all contacts if no parameters are provided")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacts successfully fetched", content = {@Content(schema = @Schema(implementation = ContactResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "No contacts found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(path = {"/fetchByField", "/fetch-all-contacts"})
    public ResponseEntity<?> fetchByRequiredField(
            @Parameter(description = "Phone number to filter by") @RequestParam(required = false) String phoneNo,
            @Parameter(description = "Email to filter by") @RequestParam(required = false) String email) {
        log.info("Request to fetch contacts with phoneNo: {} or email: {}", phoneNo, email);
        return contactService.fetchContactsByField(phoneNo, email);
    }

    @Operation(summary = "Delete a contact", description = "Deletes a contact by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Contact not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> deleteContact(
            @Parameter(description = "ID of the contact to delete", required = true) @RequestParam Long contactId) {
        log.info("Request to delete contact with ID: {}", contactId);
        return contactService.deleteContact(contactId);
    }

    @Operation(summary = "Merge contact", description = "Merge the duplicate contact")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contacts successfully merged", content = {@Content(schema = @Schema(implementation = ContactResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Contacts not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/merge-contacts")
    public ResponseEntity<ContactResponseDto> mergeContacts(
            @Parameter(description = "ID of the primary contact", required = true) @RequestParam Long primaryContactId,
            @Parameter(description = "List of duplicate contact IDs", required = true) @RequestParam List<Long> duplicateContactIds) {
        log.info("Request to merge contacts received with primaryContactId: {} and duplicateContactIds: {}", primaryContactId, duplicateContactIds);
        return contactService.mergeContacts(primaryContactId, duplicateContactIds);
    }
}
