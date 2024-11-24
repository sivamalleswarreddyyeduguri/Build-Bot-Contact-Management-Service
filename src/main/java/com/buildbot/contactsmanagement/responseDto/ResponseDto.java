package com.buildbot.contactsmanagement.responseDto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto {
		
	private HttpStatus statusCode;
	private String statusMsg;
}