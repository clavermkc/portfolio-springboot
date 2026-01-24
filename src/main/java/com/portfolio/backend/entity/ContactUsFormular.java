package com.portfolio.backend.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//HACK: prototyp1 This form will be save in the Db by click on send on formular
/**
 * The FormData class represents a form submission entity within the system.
 * It is mapped to the "form_data" table in the database.
 *
 * This class is designed to capture and store information submitted
 * through a form, such as personal details (first name, last name, etc.)
 * and a message from the user.
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "form_data")
public class ContactUsFormular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    // OPTIONNEL - Peut être null dans la base de données
    @Column(nullable = true)  // Optionnel - nullable par défaut
    private String lastName;

    // OPTIONNEL - Peut être null dans la base de données
    @Column(nullable = true)  // Optionnel - nullable par défaut
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(columnDefinition ="TEXT" ,nullable = false)
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

}