package com.portfolio.backend.repositories;

import com.portfolio.backend.entity.Skill;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    void deleteById(@NonNull Long id);
    @NonNull
    Optional<Skill> findById(@NonNull Long id);

    @NonNull
    Page<Skill> findAll(@NonNull Pageable pageable);

}
