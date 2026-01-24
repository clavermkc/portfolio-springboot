package com.portfolio.backend.repositories;
import com.portfolio.backend.entity.ContactUsFormular;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.Optional;
public interface ContactUsRepository extends JpaRepository <ContactUsFormular, Long>{


    Optional<ContactUsFormular> findByPhoneNumberAndEmail(String phoneNumber,String email);

    Page <ContactUsFormular> findAllByPhoneNumberIsNotEmpty(Pageable pageable);

    Page <ContactUsFormular> findAllByMessageIsNotEmpty(Pageable pageable);



}
