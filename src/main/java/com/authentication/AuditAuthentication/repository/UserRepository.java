package com.authentication.AuditAuthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.AuditAuthentication.model.UserCredentials;

/**
 * JPA Repository to connect with the database and perform CRUD operations
 */
@Repository
public interface UserRepository extends JpaRepository<UserCredentials, String> {

}
