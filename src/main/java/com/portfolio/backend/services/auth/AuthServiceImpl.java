package com.portfolio.backend.services.auth;

import java.util.List;


import com.portfolio.backend.dtos.AuthenticationRequest;
import com.portfolio.backend.dtos.AuthenticationResponse;
import com.portfolio.backend.dtos.SignupRequest;
import com.portfolio.backend.dtos.UserDto;
import com.portfolio.backend.entity.Role;
import com.portfolio.backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import com.portfolio.backend.repositories.UserRepository;
import com.portfolio.backend.services.userService.UserService;
import com.portfolio.backend.utils.JwtUtil;

/**
 * Service implementation for handling user authentication and registration.
 * <p>
 * This service provides methods to register new users, authenticate existing users,
 * and generate JWT tokens for authorized users.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    /**
     * Creates a new user based on the provided signup request.
     * <p>
     * This method validates the signup request to check if a user with the provided
     * email already exists. If the user exists and their password matches, an
     * exception is thrown. Otherwise, a new user is created, assigned a role based
     * on their email domain, and saved in the repository.
     * </p>
     *
     * @param signupRequest the signup request containing the user's email, password, first name, and last name
     * @return a {@link UserDto} representing the newly created user
     * @throws EntityExistsException if a user with the same email already exists and the password matches
     */
    public UserDto createUser (SignupRequest signupRequest){
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            if(new BCryptPasswordEncoder().matches(signupRequest.getPassword(),userRepository.findFirstByEmail(signupRequest.getEmail()).get().getPassword())){
                throw new EntityExistsException("User Already Present With email" + signupRequest.getEmail());
            }
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        if(signupRequest.getEmail().endsWith("@djanguicore.com")){
            logger.info("User with email: " + signupRequest.getEmail() + " is an admin");
            user.setRole(Role.ADMIN);
        }else {
            logger.info("User with email: " + signupRequest.getEmail() + " is a simple user");
            user.setRole(Role.USER);
        }
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();

    }


    /**
     * Authenticates a user using the provided authentication request.
     * <p>
     * This method validates the provided email and password. If successful, it generates a JWT token
     * and returns an {@link AuthenticationResponse} containing the token along with the user's details.
     * </p>
     *
     * @param authenticationRequest the user's email and password
     * @return an {@link AuthenticationResponse} containing the JWT token and user details
     * @throws BadCredentialsException if the authentication fails due to incorrect username or password
     */
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest){

        User theUser = new User();

        try{
            List<User> users = userRepository.findByEmail(authenticationRequest.getEmail());
            if (users.isEmpty()) {
                throw new BadCredentialsException("User not found.");
            }
            for (User user : users) {
                if (new BCryptPasswordEncoder().matches(authenticationRequest.getPassword(), user.getPassword())) {
                    theUser = user;
                }
            }
            userRepository.findByEmailAndPassword(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(theUser.getEmail());

        User optionalUser = theUser;
        final String jwt = jwtUtil.generateToken(userDetails);
        //TODO : create a builder ? :) es ist viel besser
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationResponse.setJwt(jwt);
        authenticationResponse.setUserId(optionalUser.getId());
        authenticationResponse.setFirstName(optionalUser.getFirstName());
        authenticationResponse.setLastName(optionalUser.getLastName());

        return authenticationResponse;
    }
}