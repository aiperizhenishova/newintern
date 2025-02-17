package com.example.transcripttodiagram.service;

import com.example.transcripttodiagram.model.SubjectCommonSkills;
import com.example.transcripttodiagram.repository.SubjectCommonSkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectCommonSkillsService {
    private final SubjectCommonSkillsRepository repository;

    public SubjectCommonSkillsService(SubjectCommonSkillsRepository repository) {
        this.repository = repository;
    }

    public List<SubjectCommonSkills> getAll() {
        return repository.findAll();
    }

    public Optional<SubjectCommonSkills> getById(int id) {
        return repository.findById(id);
    }

    public List<SubjectCommonSkills> getBySubjectId(int subjectId) {
        return repository.findBySubjectId(subjectId);
    }

    public List<SubjectCommonSkills> getBySubjectIds(List<Integer> subjectIds) { // Правильное расположение метода
        return repository.findBySubjectIdIn(subjectIds);
    }

    public SubjectCommonSkills create(SubjectCommonSkills skill) {
        return repository.save(skill);
    }

    public SubjectCommonSkills update(int id, SubjectCommonSkills updatedSkill) {
        return repository.findById(id)
                .map(skill -> {
                    skill.setSubject(updatedSkill.getSubject());
                    skill.setCommonSkills(updatedSkill.getCommonSkills());
                    return repository.save(skill);
                })
                .orElseThrow(() -> new RuntimeException("SubjectCommonSkills not found"));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}