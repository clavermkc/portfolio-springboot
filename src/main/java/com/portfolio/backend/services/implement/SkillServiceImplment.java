package com.portfolio.backend.services.implement;

import com.portfolio.backend.dtos.SkillDto;

import java.util.List;

public interface SkillServiceImplment {
void createSkill(SkillDto skillDto);
void deleteSkill(Long id);
List<SkillDto> getAllSkills();
SkillDto getSkillDtoById(Long id);
}
