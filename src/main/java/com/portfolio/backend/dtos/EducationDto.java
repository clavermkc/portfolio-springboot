package com.portfolio.backend.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private String universityName;

    private String degree;

    private String program;

    private String coursesTaken;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<String> courses;

}
