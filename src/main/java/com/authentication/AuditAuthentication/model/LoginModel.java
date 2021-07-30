package com.authentication.AuditAuthentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class used to authenticate tokens
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginModel {
	String username;
	String token;
}
