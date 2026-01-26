package com.portfolio.backend.controller;

import com.portfolio.backend.dtos.ContactUsFormularDto;
import com.portfolio.backend.entity.ContactUsFormular;
import com.portfolio.backend.services.ContactUsFormularService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://https://portfolio-springboot-production-39de.up.railway.app/"})
public class ContactUsController {
    private final ContactUsFormularService contactUsFormularService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ContactUsController.class);
    @PostMapping("/contact-us")
    public ResponseEntity<?> postContactUs(@RequestBody ContactUsFormularDto contactUsFormularDto){
        contactUsFormularService.createContactFormular(contactUsFormularDto);
        return new ResponseEntity<>(contactUsFormularDto, HttpStatus.CREATED);
    }

    /**
     *Get all contact-us formular in the database
     */
    @GetMapping("/contact-us")
    public ResponseEntity<?> getContactUs(){
        if(contactUsFormularService.findAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(contactUsFormularService.findAll(), HttpStatus.OK);
    }
}