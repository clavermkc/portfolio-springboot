package com.portfolio.backend.services.userService;

import com.portfolio.backend.dtos.UserDto;
import com.portfolio.backend.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;



import java.util.List;

/**
 * Service interface for managing user-related operations.
 * <p>
 * This interface defines the methods related to user management, such as retrieving
 * a list of users, retrieving a user by their ID, and providing a {@link UserDetailsService}
 * for user authentication purposes.
 * </p>
 */

public interface UserService {

    /**
     * Retrieves the {@link UserDetailsService} to load user-specific data for authentication.
     * <p>
     * This method provides the service for loading user details, used for the authentication process.
     * </p>
     *
     * @return the {@link UserDetailsService} instance for loading user details
     */
    UserDetailsService userDetailsService();

    /**
     * Retrieves a list of all users.
     * <p>
     * This method fetches all users from the system. The returned response is typically a
     * list or collection of users wrapped in a {@link ResponseEntity}.
     * </p>
     *
     * @return a {@link ResponseEntity} containing the list of users
     */
    ResponseEntity<?> getUsers();

    /**
     * Retrieves a user by their unique ID.
     * <p>
     * This method fetches a specific user from the system by their ID and returns a corresponding
     * {@link UserDto} with the user's details.
     * </p>
     *
     * @param id the unique ID of the user to be retrieved
     * @return the {@link UserDto} of the user with the given ID
     */
    UserDto getUserById(Long id);

    List<UserDto> getUserByRole(Role role);

    Page<UserDto> getFirstByRole(Role role,int pageNumber, int pageSize);
}