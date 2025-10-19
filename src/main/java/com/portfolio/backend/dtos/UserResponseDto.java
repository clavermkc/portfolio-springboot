package com.portfolio.backend.dtos;

import java.util.List;

import lombok.Data;

/**
 * The UserResponseDto class is a data transfer object (DTO) used to represent
 * a response containing a list of users.
 * <p>
 * This DTO is typically used in situations where multiple users need to be
 * returned in a single response. It encapsulates a list of `UserDto` objects
 * that represent the details of each user.
 * </p>
 */
@Data
public class UserResponseDto {

    /**
     * A list of `UserDto` objects representing the users in the response.
     * This list contains user data for each user being returned in the response.
     */
    private List<UserDto> userDtoList;
}