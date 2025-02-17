package com.example.transcripttodiagram.repository;

import com.example.transcripttodiagram.model.SubjectSingleSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectSingleSkillsRepository extends JpaRepository<SubjectSingleSkills, Integer> {
    List<SubjectSingleSkills> findBySubjectId(int subjectId);

    List<SubjectSingleSkills> findBySubjectIdIn(List<Integer> subjectIds);
}
