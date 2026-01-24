package com.portfolio.backend.services;
import com.portfolio.backend.dtos.ContactUsFormularDto;
import com.portfolio.backend.entity.ContactUsFormular;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ContactUsFormularService {

    void deleteContactUsDto(Long id);

    void createFormularContactUs(ContactUsFormularDto contactUsFormularDto);

    void createContactFormular(ContactUsFormularDto contactUsFormularDto);
    List<ContactUsFormular> findAll();
}