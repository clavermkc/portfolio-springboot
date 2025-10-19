package com.portfolio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


    @Entity
    @Data
    @Table(name = "work_experience")
    public class Experience {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "company_name", nullable = false)
        private String companyName;

        @Column(name = "job_title", nullable = false)
        private String jobTitle;

        @Column(name = "start_date", nullable = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @Column(name = "end_date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        @Column(name = "description")
        private String description;

        @Transient
        List<String> descPoints;
        public String toString() {
            return "Experience{" +
                    "companyName='" + companyName + '\'' +
                    ", jobTitle='" + jobTitle + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

