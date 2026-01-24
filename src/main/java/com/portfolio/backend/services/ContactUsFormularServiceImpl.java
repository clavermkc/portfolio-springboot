package com.portfolio.backend.services;

import com.portfolio.backend.dtos.ContactUsFormularDto;
import com.portfolio.backend.entity.ContactUsFormular;
import com.portfolio.backend.repositories.ContactUsRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactUsFormularServiceImpl implements ContactUsFormularService{

    private final ContactUsRepository contactUsRepository;

    @Override
    public void createContactFormular(ContactUsFormularDto contactUsFormularDto) {
        ContactUsFormular contactUsFormular = getContactUsFormular(contactUsFormularDto);


        //Todo USE BUILDER ONLY IF WE DONT HAVE MANDATORY FIELDS
//        ContactUsFormular contactUsFormular1 = ContactUsFormular.builder()
//                .firstName(contactUsFormularDto.getFirstName())
//                .lastName(contactUsFormularDto.getLastName())
//                .email(contactUsFormularDto.getEmail())
//                .phoneNumber(contactUsFormularDto.getPhoneNumber())
//                .message(contactUsFormularDto.getMessage()).build();
        contactUsRepository.save(contactUsFormular);
    }

    private static @NonNull ContactUsFormular getContactUsFormular(ContactUsFormularDto contactUsFormularDto) {
        ContactUsFormular contactUsFormular= new ContactUsFormular();
        contactUsFormular.setFirstName(contactUsFormularDto.getFirstName());
        contactUsFormular.setEmail(contactUsFormularDto.getEmail());
        contactUsFormular.setMessage(contactUsFormularDto.getMessage());
        if (contactUsFormularDto.getLastName() != null && !contactUsFormularDto.getLastName().trim().isEmpty()) {
            contactUsFormular.setLastName(contactUsFormularDto.getLastName());
        }
        if (contactUsFormularDto.getEmail() != null && !contactUsFormularDto.getEmail().trim().isEmpty()) {
            contactUsFormular.setEmail(contactUsFormularDto.getEmail());
        }
        return contactUsFormular;
    }

    @Override
    public List<ContactUsFormular>findAll() {
        return contactUsRepository.findAll();

    }


    @Override
    public void deleteContactUsDto(Long id) {

    }
    @Override
    public void createFormularContactUs(ContactUsFormularDto contactUsFormularDto) {

    }
}