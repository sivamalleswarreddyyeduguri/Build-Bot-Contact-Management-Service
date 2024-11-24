package com.buildbot.contactsmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.buildbot.contactsmanagement.constants.ContactsManagementConstants;
import com.buildbot.contactsmanagement.model.LoginDto;
import com.buildbot.contactsmanagement.model.UserDto;
import com.buildbot.contactsmanagement.responseDto.ResponseDto;
import com.buildbot.contactsmanagement.security.JwtService;
import com.buildbot.contactsmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/app/contacts-user/api/v1")
@Tag(name = "Contact User Management", description = "APIs for user management, including creation, authentication, and token generation.")
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(summary = "Create a new user", description = "This endpoint allows creating a new user in the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/new-user")
    public ResponseEntity<?> saveNewUser(@RequestBody UserDto userDto) {
        log.info("Request received to create a new user with username: {}", userDto.getUserName());
        userService.saveUser(userDto);
        log.info("User created successfully with username: {}", userDto.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED, ContactsManagementConstants.CREATED_SUCCESS));
    }

    @Operation(summary = "Authenticate and get token", description = "This endpoint allows users to authenticate with their username and password and receive a JWT token for accessing other secure endpoints.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful and token generated", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Unauthorized access due to invalid credentials", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden access, insufficient privileges", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/get-token")
    public String authenticateAndGetToken(@RequestBody LoginDto login) {
        log.info("Authentication request received for username: {}", login.getUserName());

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserName(), login.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUserName());

        if (authenticate.isAuthenticated()) {
            log.info("Authentication successful for username: {}", login.getUserName());
            return jwtService.getToken(userDetails);
        }
        log.warn("Authentication failed for username: {}", login.getUserName());
        return "wrong userName and Password";
    }

    @Operation(summary = "Get user details", description = "Fetch the details of the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User details successfully fetched", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden access, insufficient privileges", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/user-details")
    public ResponseEntity<UserDto> getUserDetails(
            @Parameter(description = "Username of the user to fetch details") @RequestParam String username) {
        log.info("Request received to fetch details for user: {}", username);
        UserDto userDto = userService.getUserDetails(username);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Update user details", description = "Update the details of an existing user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User details successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden access, insufficient privileges", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update-user/{username}")
    public ResponseEntity<?> updateUser(
            @Parameter(description = "Username of the user to update", required = true) @PathVariable String username,
            @RequestBody UserDto userDto) {
        log.info("Request to update user received with username: {} and data: {}", username, userDto);
        userService.updateUser(username, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, ContactsManagementConstants.UPDATED_SUCCESS));
    }
}
