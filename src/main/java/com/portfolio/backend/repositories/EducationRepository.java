package com.portfolio.backend.repositories;

import com.portfolio.backend.entity.Education;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationRepository {
    void deleteById(@NonNull Long id);
    @NonNull
    Optional<Education> findById(@NonNull Long id);

    @NonNull
    Page<Education> findAll(@NonNull Pageable pageable);

}
