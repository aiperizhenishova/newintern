package com.example.transcripttodiagram.service;

import com.example.transcripttodiagram.model.SingleSkills;
import com.example.transcripttodiagram.repository.SingleSkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingleSkillsService {
    private final SingleSkillsRepository singleSkillsRepository;

    public SingleSkillsService(SingleSkillsRepository singleSkillsRepository) {
        this.singleSkillsRepository = singleSkillsRepository;
    }

    public List<SingleSkills> getAllSingleSkills() {
        return singleSkillsRepository.findAll();
    }

    public Optional<SingleSkills> getSingleSkillsById(int id) {
        return singleSkillsRepository.findById(id);
    }

    public SingleSkills createSingleSkills(SingleSkills skill) {
        return singleSkillsRepository.save(skill);
    }

    public SingleSkills updateSingleSkills(int id, SingleSkills skillDetails) {
        return singleSkillsRepository.findById(id)
                .map(skill -> {
                    skill.setName(skillDetails.getName());
                    return singleSkillsRepository.save(skill);
                })
                .orElseThrow(() -> new RuntimeException("SingleSkill not found"));
    }

    public void deleteSingleSkills(int id) {
        singleSkillsRepository.deleteById(id);
    }
}
