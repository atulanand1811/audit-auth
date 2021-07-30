package com.authentication.AuditAuthentication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * JPA Entity Class used to keep a database of usernames and passwords
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="USERS")
public class UserCredentials {

	@Id
	@NotBlank(message = "Username is mandatory")
	@Column
	String username;

	@NotNull
	@Size(min = 8)
	@NotBlank(message = "Password is mandatory!")
	@Column
	String password;

}
