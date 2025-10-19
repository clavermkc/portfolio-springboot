package com.portfolio.backend.services.userService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.portfolio.backend.dtos.UserDto;
import com.portfolio.backend.dtos.UserResponseDto;
import com.portfolio.backend.entity.Role;
import com.portfolio.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import com.portfolio.backend.repositories.UserRepository;


/**
 * Service implementation for managing user-related operations.
 * <p>
 * This service provides implementations for managing user data, including retrieving a list of all users,
 * retrieving a specific user by their ID, and loading user details for authentication.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Provides the {@link UserDetailsService} to load user details by username (email).
     * <p>
     * This method is used for user authentication, where the user's details are loaded by their username (email).
     * </p>
     *
     * @return a {@link UserDetailsService} instance that loads user details by username
     */
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
            }
        };
    }

    /**
     * Retrieves a list of all users in the system.
     * <p>
     * This method fetches all users from the database and returns a {@link ResponseEntity} containing a
     * {@link UserResponseDto}, which is a list of {@link UserDto} objects representing the users.
     * </p>
     *
     * @return a {@link ResponseEntity} containing the list of all users wrapped in a {@link UserResponseDto}
     */
    public ResponseEntity<?> getUsers() {

        List<User> userList = userRepository.findAll();

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserDtoList(userList.stream().map(User::getUserDto).collect(Collectors.toList()));

        return ResponseEntity.ok(userResponseDto);
    }

    /**
     * Retrieves a user by their unique ID.
     * <p>
     * This method fetches a user from the database using their ID and returns a {@link UserDto} with the user's details.
     * If the user is not found, it returns {@code null}.
     * </p>
     *
     * @param id the unique ID of the user to be retrieved
     * @return a {@link UserDto} representing the user, or {@code null} if the user is not found
     */

    //https://blogs.oracle.com/javamagazine/post/12-recipes-for-using-the-optional-class-as-its-meant-to-be-used
    public UserDto getUserById(Long id) {

        Optional<User> user = userRepository.findById(id);
        //UserDto userDto ;
        //userDto = user.get().getUserDto();
        //Note tu peux utiliser optional comme stream c est plus lisible et ca evite les erreurs
        //orElseThrow () fais deja isPresent et get .
        return user.map(User::getUserDto).orElseThrow(() -> new UsernameNotFoundException("User with " + id + " not Found"));

        //Todo risk de nullpointerException  pourquoi retourner null?
        //return null;
    }

    /**
     * Retrieves a list of users filtered by their assigned role.
     *
     * @param role the role of the users to be retrieved
     * @return a list of {@link UserDto} objects representing users with the specified role
     */
    @Override
    public List<UserDto> getUserByRole(Role role) {
        List<User> userList = userRepository.findByRole(role);
        return userList.stream().map(User::getUserDto).collect(Collectors.toList());
    }
    @Override
    public Page<UserDto> getFirstByRole(Role role,int pageNumber, int pageSize) {

        //typeSort erstellen
        Sort.TypedSort<UserDto> userDtoTypedSort= Sort.sort(UserDto.class);
        Sort sort = userDtoTypedSort.by(UserDto::getFirstName).ascending();
        //
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userRepository.findAllByRole(role,pageable).map(User::getUserDto);
    }
}