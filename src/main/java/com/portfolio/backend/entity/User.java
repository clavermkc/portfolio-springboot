package com.portfolio.backend.entity;


import com.portfolio.backend.dtos.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The User class represents a user entity in the system.
 * It is mapped to the "users" table in the database.
 * <p>
 * A user has a first name, last name, email, password, and a list of notebooks.
 * The User class implements the {@link UserDetails} interface to integrate with Spring Security.
 * </p>
 * <p>
 * The class provides a method to convert the User entity into a Data Transfer Object (DTO) for external use.
 * </p>
 */
@Data
@Entity
@Table(name = "users")

public class User  implements UserDetails {

    /**
     * The unique identifier for the user.
     * This is the primary key and is generated automatically by the database.
     */
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The first name of the user.
     */
    @NotNull(message = "First name is required")
    private String firstName;

    /**
     * The last name of the user.
     */
    @NotNull(message = "Last name is required")
    private String lastName;

    /**
     * The email of the user. This serves as the username for authentication.
     */
    @NotNull(message = "Email is required")
    private String email;

    /**
     * The password of the user, used for authentication.
     */

    private String password;

    /**
     * Returns the email of the user as the username.
     * This is used by Spring Security for authentication.
     *
     * @return the email of the user
     */

    //Todo discuss if this is useful
    /**
     * The role assigned to the user.
     * This represents the user's role or level of access within the system.
     */
    //Todo is it neccessary to have eager = true to avoid lazy loading exceptions???
    @Enumerated(EnumType.STRING)
    private Role role;

    private Set<Role> roles = new HashSet<>();
    // RBAC

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return true since the account is never expired in this model
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return true since the account is never locked in this model
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are expired.
     *
     * @return true since the credentials are never expired in this model
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true since the user is always enabled in this model
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Converts this entity object into a Data Transfer Object (DTO).
     * The DTO is used for transferring user data without exposing entity details.
     *
     * @return a UserDto object containing the user's data.
     */
    public UserDto getUserDto() {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);

        return dto;
    }

    /**
     * Returns the authorities granted to the user.
     * Since no specific roles or authorities are defined here, it returns an empty list.
     *
     * @return an empty list of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }
}
