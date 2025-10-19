package com.portfolio.backend.dtos;

import lombok.Data;

/**
 * The AuthenticationRequest class is used to capture the details provided by a user
 * when attempting to authenticate (log in) to the system.
 * <p>
 * It contains the user's email and password for authentication purposes.
 * </p>
 */
@Data
public class AuthenticationRequest {

    /**
     * The email address of the user attempting to authenticate.
     */
    private String email;

    /**
     * The password of the user attempting to authenticate.
     */
    private String password;
}