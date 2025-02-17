package com.example.transcripttodiagram.repository;

import com.example.transcripttodiagram.model.SubjectCommonSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectCommonSkillsRepository extends JpaRepository<SubjectCommonSkills, Integer> {
    List<SubjectCommonSkills> findBySubjectId(int subjectId);

    // SubjectCommonSkillsRepository.java
    List<SubjectCommonSkills> findBySubjectIdIn(List<Integer> subjectIds);
}
