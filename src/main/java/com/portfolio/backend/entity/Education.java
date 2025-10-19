package com.portfolio.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "program")
    private String program;

    @Column(name = "courses_taken")
    private String coursesTaken;

    @Column(name = "start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

//    @Column(name = "image_path")
//    private String imagePath;

    @Transient
    private List<String> courses;


    public void setCourses() {
        if (coursesTaken != null && !coursesTaken.isEmpty()) {
            String[] temp = coursesTaken.trim().split("\\.\\s+");
            this.courses = Arrays.asList(temp);
        } else{
            this.courses = Collections.emptyList();
        }
    }

    @Override
    public String toString() {
        return "Education{" +
                "universityName='" + universityName + '\'' +
                ", degree='" + degree + '\'' +
                ", program='" + program + '\'' +
                ", coursesTaken='" + coursesTaken + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
//                ", imagePath='" + imagePath + '\'' +
                ", courses=" + courses +
                '}';
    }
}
