package com.portfolio.backend.controller;

import com.portfolio.backend.dtos.AuthenticationRequest;
import com.portfolio.backend.dtos.AuthenticationResponse;
import com.portfolio.backend.dtos.SignupRequest;
import com.portfolio.backend.dtos.UserDto;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import com.portfolio.backend.services.auth.AuthService;

/**
 * The AuthController class handles authentication-related HTTP requests, such as user sign-up and login.
 * It defines two primary endpoints:
 * <ul>
 *   <li><b>/signup</b>: Registers a new user in the system by accepting a signup request.</li>
 *   <li><b>/login</b>: Authenticates a user by accepting login credentials and returns an authentication token.</li>
 * </ul>
 * The controller interacts with the {@link AuthService} to perform user registration and authentication logic.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Handles user sign-up requests. This method takes the user's sign-up details and attempts to create a new user in the system.
     * If the user already exists, an {@link EntityExistsException} is caught and a 406 status code is returned.
     * If an error occurs during the process, a 400 status code is returned.
     *
     * @param signupRequest the sign-up request containing the user's details
     * @return a {@link ResponseEntity} with a created user and a 200 status code if successful,
     *         or an error message with an appropriate HTTP status code if unsuccessful
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            logger.info("New user created and store in DB with id: {}with status:{}", createdUser.getId(), HttpStatus.OK);

            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch(EntityExistsException entityExistsException){
            logger.info("User already exists with the same email: {}with status:{}", signupRequest.getEmail(), HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<>("User already exists with the same password ", HttpStatus.NOT_ACCEPTABLE);
        }catch(Exception e){
            logger.info("User not created, come again later: {}with status:{}", e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("User not created, come again later", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles user login requests. This method takes the user's login credentials and authenticates them.
     * If authentication is successful, it returns an authentication token in the form of an {@link AuthenticationResponse}.
     *
     * @param authenticationRequest the login request containing the user's credentials
     * @return an {@link AuthenticationResponse} containing the authentication token
     */
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        logger.info("Trying to authenticate user with email: ");
        return authService.authenticate(authenticationRequest);
    }
}