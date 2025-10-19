package com.portfolio.backend.dtos;



import lombok.Data;

/**
 * The UserDto class is a data transfer object (DTO) that represents a user
 * along with their associated notebooks.
 * <p>
 * This DTO is used to transfer user-related data, such as the user's personal
 * information (first name, last name, email) as well as a list of notebooks
 * associated with the user.
 * </p>
 */
@Data
public class UserDto {

    /**
     * The unique identifier for the user.
     * This field is used to represent the user in the system.
     */
    private Long id;

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

    /**
     * The email address of the user.
     * This field uniquely identifies the user and is used for login purposes.
     */
    private String email;


}
