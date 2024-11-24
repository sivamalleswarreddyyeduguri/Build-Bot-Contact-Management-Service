package com.buildbot.contactsmanagement.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class AddressDto {
	
    @NotBlank(message = "Street name is required and cannot be blank")
    @Size(max = 100, message = "Street name cannot exceed 100 characters")
    private String streetName;

    @NotBlank(message = "City is required and cannot be blank")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "City can only contain letters")
    private String city;

    @NotBlank(message = "State is required and cannot be blank")
    @Size(max = 100, message = "State cannot exceed 100 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "State can only contain letters")
    private String state;

    @NotBlank(message = "PIN code is required and cannot be blank")
    @Pattern(regexp = "(\\d{6})|(\\d{6}(-\\d{4})?)", message = "PIN code must match the format: (\\d{6})|(\\d{6}(-\\d{4})?)")
    private String pinCode;

    @NotBlank(message = "Country is required and cannot be blank")
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Country can only contain letters")
    private String country;

    @NotBlank(message = "Contact ID is required and cannot be blank")
    private Integer contactId;
}
