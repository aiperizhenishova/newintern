package com.example.transcripttodiagram.repository;

import com.example.transcripttodiagram.model.CommonSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonSkillsRepository extends JpaRepository<CommonSkills, Integer> {
}
