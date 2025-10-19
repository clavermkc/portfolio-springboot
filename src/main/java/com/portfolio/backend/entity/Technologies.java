package com.portfolio.backend.entity;

import java.util.List;
public class Technologies{
    public record Technology(
            String id,          // "java", "react"
            String displayName, // "Java", "React"
            String iconSlug,    // "java", "react"
            String category,    // "Backend", "Frontend"
            String type         // "Language", "Framework"
    ) {}
    public static final Technology JAVA =
            new Technology("java", "Java", "java", "Backend", "Language");

    public static final Technology SPRING_BOOT =
            new Technology("spring-boot", "Spring Boot", "springboot", "Backend", "Framework");

    public static final Technology REACT =
            new Technology("react", "React", "react", "Frontend", "Framework");

    // Liste de toutes les technologies prédéfinies
    public static final List<Technology> ALL = List.of(
            JAVA, SPRING_BOOT, REACT
    );
}
