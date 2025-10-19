package com.portfolio.backend.services.auth;

import com.portfolio.backend.dtos.AuthenticationRequest;
import com.portfolio.backend.dtos.AuthenticationResponse;
import com.portfolio.backend.dtos.SignupRequest;
import com.portfolio.backend.dtos.UserDto;

/**
 * Service interface for handling authentication and user registration.
 * <p>
 * This interface defines methods for creating a new user, authenticating an existing user,
 * and managing their session with JWT tokens.
 * </p>
 */
public interface AuthService {
    /**
     * Registers a new user based on the provided signup request.
     * <p>
     * This method handles the creation of a new user, including validation of the provided data
     * and saving the user in the system. The user's details are returned as a {@link UserDto}.
     * </p>
     *
     * @param signupRequest the data for creating the user, including email, password, first name, and last name
     * @return a {@link UserDto} representing the newly created user
     */
    UserDto createUser (SignupRequest signupRequest);
    /**
     * Authenticates a user based on the provided credentials.
     * <p>
     * This method checks the provided email and password, verifies them against the system,
     * and returns an {@link AuthenticationResponse} containing a JWT token if authentication is successful.
     * </p>
     *
     * @param authenticationRequest the credentials used for authentication (email and password)
     * @return an {@link AuthenticationResponse} containing the JWT token and user details
     */
    AuthenticationResponse authenticate( AuthenticationRequest authenticationRequest);
}