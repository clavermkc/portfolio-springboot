package com.portfolio.backend.services.implement;

import com.portfolio.backend.dtos.EducationDto;

import java.util.List;

public interface EducationServiceImplement {

    void createEducation(EducationDto educationDto);
    void deleteEducation(Long id);
    List<EducationDto> getAllEducations();
    EducationDto getEducationById(Long id);
}
