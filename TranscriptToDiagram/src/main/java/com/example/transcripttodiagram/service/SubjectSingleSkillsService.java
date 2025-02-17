package com.example.transcripttodiagram.service;

import com.example.transcripttodiagram.model.SubjectSingleSkills;
import com.example.transcripttodiagram.repository.SubjectSingleSkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectSingleSkillsService {
    private final SubjectSingleSkillsRepository repository;

    public SubjectSingleSkillsService(SubjectSingleSkillsRepository repository) {
        this.repository = repository;
    }

    public List<SubjectSingleSkills> getAll() {
        return repository.findAll();
    }

    public Optional<SubjectSingleSkills> getById(int id) {
        return repository.findById(id);
    }

    public List<SubjectSingleSkills> getBySubjectId(int subjectId) {
        return repository.findBySubjectId(subjectId);
    }

    public List<SubjectSingleSkills> getBySubjectIds(List<Integer> subjectIds) { //  Добавлен метод!
        return repository.findBySubjectIdIn(subjectIds);
    }

    public SubjectSingleSkills create(SubjectSingleSkills skill) {
        return repository.save(skill);
    }

    public SubjectSingleSkills update(int id, SubjectSingleSkills updatedSkill) {
        return repository.findById(id)
                .map(skill -> {
                    skill.setSubject(updatedSkill.getSubject());
                    skill.setSingleSkills(updatedSkill.getSingleSkills());
                    return repository.save(skill);
                })
                .orElseThrow(() -> new RuntimeException("SubjectSingleSkills not found"));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}