package com.portfolio.backend.services;

import com.portfolio.backend.dtos.SkillDto;
import com.portfolio.backend.entity.Skill;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.portfolio.backend.services.implement.SkillServiceImplment;
import com.portfolio.backend.repositories.SkillRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class SkillService implements SkillServiceImplment {
    private final SkillRepository skillRepository;
    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public void createSkill(SkillDto skillDto) {
        Skill skill = Skill.builder().category(skillDto.getCategory()).
                skill_name(skillDto.getSkill_name()).
                framework(skillDto.getFramework()).
                icon(skillDto.getIcon()).
                build();
        skillRepository.save(skill);
        log.info("Created skill with id {}", skill.getId());
    }

    //we assume that skill doesn't have a dependencies with another entities and is not transactional
    @Override
    public void deleteSkill(Long id) {
        log.info("Attempting to delete skill with ID: {}", id);

        // Vérifier si le skill existe
        skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Skill with id {} not found for deletion", id);
                    return new EntityNotFoundException(String.format("Skill with id %d not found", id));
                });
    }


    @Override
    public List<SkillDto> getAllSkills() {
        if (!skillRepository.findAll().isEmpty()) {
            List<Skill> skills = skillRepository.findAll();
           return skills.stream().map(this::mapToSkillDto).collect(Collectors.toList());
        }
        return List.of();
    }

    private SkillDto mapToSkillDto(Skill skill) {
       return SkillDto.builder().
                skill_name(skill.getSkill_name()).
                framework(skill.getFramework()).
                icon(skill.getIcon()).
                build();
    }

    @Override
    public SkillDto getSkillDtoById(Long id) {
        Skill skill= skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Skill with id " + id + " not found"));
       return  mapToSkillDto(skill);
    }

}
