package com.portfolio.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silentsoft.simpleicons.SimpleIcons;


import java.util.Collection;
import java.util.HashSet;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "skill_name")
    private String skill_name;
    @Column(name = "framework")
    private String framework;
    /**
     *icon's name :client will match the iconame with slugname from simpleicons <a href="https://github.com/simple-icons/simple-icons/tree/develop">...</a>
     */
    @Column (name="icon")
    private String icon;




}
