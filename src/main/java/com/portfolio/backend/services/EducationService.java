package com.portfolio.backend.services;

import com.portfolio.backend.dtos.EducationDto;
import com.portfolio.backend.services.implement.EducationServiceImplement;

import java.util.List;

public class EducationService implements EducationServiceImplement {
    @Override
    public void createEducation(EducationDto educationDto) {

    }

    @Override
    public void deleteEducation(Long id) {

    }

    @Override
    public List<EducationDto> getAllEducations() {
        return List.of();
    }

    @Override
    public EducationDto getEducationById(Long id) {
        return null;
    }
}
