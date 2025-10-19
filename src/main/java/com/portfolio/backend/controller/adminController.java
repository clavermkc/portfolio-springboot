package com.portfolio.backend.controller;

import com.portfolio.backend.dtos.EducationDto;
import com.portfolio.backend.dtos.SkillDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.portfolio.backend.services.EducationService;
import com.portfolio.backend.services.SkillService;

import java.util.List;


@RestController
//@RequestMapping("api/admin")
@RequestMapping("api/v0")
  //uniquement avec authentification admin
@RequiredArgsConstructor
public class adminController {
    private final SkillService skillService;

    private static final Logger log = LoggerFactory.getLogger(adminController.class);
    /*++++++++++skills++++++++++*/


    @PostMapping("/post-skill")
    public ResponseEntity<SkillDto> postSkill( @RequestBody SkillDto skillDto) {
        skillService.createSkill(skillDto);
        log.info("skill{} created", skillDto);
        return ResponseEntity.ok().body(skillDto);
    }
    @GetMapping("/getSkills")
    public List<SkillDto> getSkills() {
        //was passiert wenn das schiefgeht?
        return skillService.getAllSkills();


    }
    @GetMapping("/get-skill/{id}")
    public ResponseEntity<SkillDto> getSkill(@PathVariable Long id) {

        return ResponseEntity.ok().body( skillService.getSkillDtoById(id));
    }

    /*++++++++++Education++++++++++*/
    EducationService educationService;
    @PostMapping("/post-education")
    public ResponseEntity<EducationDto> postSkill( @RequestBody EducationDto educationDto) {
        educationService.createEducation(educationDto);
        return ResponseEntity.ok().body(educationDto);
    }

}
