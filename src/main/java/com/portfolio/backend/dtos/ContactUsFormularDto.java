package com.portfolio.backend.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
    /**
     * The FormDataDto class is a data transfer object (DTO) representing the data
     * submitted through a form. It is used for transferring form-related data
     * between the layers of the application.
     *
     * This DTO encapsulates details such as a user's first name, last name,
     * email address, and message content submitted via a form.
     */
    @Data
    @RequiredArgsConstructor
    public class ContactUsFormularDto {
        @NotBlank(message = "Le prénom est requis")
        @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
        private String firstName;

        // Optionnel - peut être null ou vide
        @Size(max = 50, message = "Le nom ne peut pas dépasser 50 caractères")
        private String lastName;

        // Optionnel - peut être null ou vide
        @Size(min = 8, max = 20, message = "Le numéro doit contenir entre 8 et 20 chiffres")
        private String phoneNumber;

        @NotBlank(message = "L'email est requis")
        @Email(message = "Format d'email invalide")
        @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
        private String email;

        @NotBlank(message = "Le message est requis")
        @Size(min = 10, max = 2000, message = "Le message doit contenir entre 10 et 2000 caractères")
        private String message;
    }