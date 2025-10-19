package com.portfolio.backend.repositories;

import java.util.List;
import java.util.Optional;
import com.portfolio.backend.entity.User;
import com.portfolio.backend.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations
 * along with custom query methods for retrieving users by their email and password.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    /**
     * Finds the first user by their email address.
     *
     * @param email the email address of the user to be retrieved
     * @return an {@link Optional} containing the user if found, or empty if no user with the provided email exists
     */
    Optional<User> findFirstByEmail(String email);

    /**
     * Finds all users by their email address.
     *
     * @param email the email address of the users to be retrieved
     * @return a list of users associated with the provided email
     */
    List <User> findByEmail(String email);

    /**
     * Finds a user by their password.
     *
     * @param password the password of the user to be retrieved
     * @return an {@link Optional} containing the user if found, or empty if no user with the provided password exists
     */
    Optional<User> findByPassword(String password);

    /**
     * Finds a user by both email and password.
     *
     * @param email the email address of the user to be retrieved
     * @param password the password of the user to be retrieved
     * @return an {@link Optional} containing the user if found, or empty if no user with the provided email and password exists
     */
    Optional<User> findByEmailAndPassword(String email, String password);

    List <User> findByRole(Role role);
    Page<User> queryFirst10ByRole(Role role, Pageable pageable);
    Page<User> findAllByRole(Role role, Pageable pageable);
}