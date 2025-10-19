package com.portfolio.backend.dtos;

import lombok.Data;
/**
 * The SignupRequest class is a data transfer object (DTO) used for capturing
 * user registration details during the signup process.
 * <p>
 * This DTO encapsulates the necessary information required to create a new user
 * account, including email, password, first name, and last name.
 * </p>
 */
@Data
public class SignupRequest {

    /**
     * The email address of the user.
     * This field is used to uniquely identify the user for authentication.
     */
    private String email;

    /**
     * The password chosen by the user.
     * This field is used for authenticating the user during the login process.
     */
    private String password;

    /**
     * The first name of the user.
     * This field holds the user's given name.
     */
    private String firstName;

    /**
     * The last name of the user.
     * This field holds the user's family or surname.
     */
    private String lastName;
}