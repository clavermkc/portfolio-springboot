package com.portfolio.backend.dtos;

import lombok.Data;

/**
 * The AuthenticationResponse class is used to encapsulate the response
 * returned after successful authentication of a user.
 * <p>
 * It contains the JWT (JSON Web Token) along with the user's identifying
 * information, such as their user ID, first name, and last name.
 * </p>
 */

@Data
public class AuthenticationResponse {

    /**
     * The JWT (JSON Web Token) generated upon successful authentication.
     * This token is used to authorize subsequent requests.
     */
    private String jwt;
    /**
     * The unique identifier for the authenticated user.
     */
    private Long userId;
    /**
     * The first name of the authenticated user.
     */
    private String firstName;
    /**
     * The last name of the authenticated user.
     */
    private String lastName;
}