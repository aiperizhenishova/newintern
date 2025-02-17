package com.example.transcripttodiagram.service;

import com.example.transcripttodiagram.model.CommonSkills;
import com.example.transcripttodiagram.repository.CommonSkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommonSkillsService {
    private final CommonSkillsRepository commonSkillsRepository;

    public CommonSkillsService(CommonSkillsRepository commonSkillsRepository) {
        this.commonSkillsRepository = commonSkillsRepository;
    }

    public List<CommonSkills> getAllCommonSkills() {
        return commonSkillsRepository.findAll();
    }

    public Optional<CommonSkills> getCommonSkillsById(int id) {
        return commonSkillsRepository.findById(id);
    }

    public CommonSkills createCommonSkills(CommonSkills skill) {
        return commonSkillsRepository.save(skill);
    }

    public CommonSkills updateCommonSkills(int id, CommonSkills skillDetails) {
        return commonSkillsRepository.findById(id)
                .map(skill -> {
                    skill.setName(skillDetails.getName());
                    return commonSkillsRepository.save(skill);
                })
                .orElseThrow(() -> new RuntimeException("CommonSkill not found"));
    }

    public void deleteCommonSkills(int id) {
        commonSkillsRepository.deleteById(id);
    }
}
