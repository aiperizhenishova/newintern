package com.example.transcripttodiagram.service;

import com.example.transcripttodiagram.model.Subject;
import com.example.transcripttodiagram.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(int id) {
        return subjectRepository.findById(id);
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(int id, Subject subjectDetails) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDetails.getName());
                    subject.setGrade(subjectDetails.getGrade());
                    return subjectRepository.save(subject);
                })
                .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }
}
