package com.example.transcripttodiagram.repository;

import com.example.transcripttodiagram.model.SingleSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleSkillsRepository extends JpaRepository<SingleSkills, Integer> {
}
