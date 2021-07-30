package com.authentication.AuditAuthentication.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class is used by {@link GlobalErrorHandler} as a response to users
 * in case of any error
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
	LocalDateTime timestamp;
	HttpStatus status;
	String reason;
	String message;
}
