package com.example.transcripttodiagram.controller;


import com.example.transcripttodiagram.model.CommonSkills;
import com.example.transcripttodiagram.service.CommonSkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/common-skills")
public class CommonSkillsController {
    private final CommonSkillsService commonSkillsService;

    public CommonSkillsController(CommonSkillsService commonSkillsService) {
        this.commonSkillsService = commonSkillsService;
    }

    @GetMapping
    public List<CommonSkills> getAllCommonSkills() {
        return commonSkillsService.getAllCommonSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonSkills> getCommonSkillsById(@PathVariable int id) {
        Optional<CommonSkills> skill = commonSkillsService.getCommonSkillsById(id);
        return skill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CommonSkills createCommonSkills(@RequestBody CommonSkills skill) {
        return commonSkillsService.createCommonSkills(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonSkills> updateCommonSkills(@PathVariable int id, @RequestBody CommonSkills skillDetails) {
        try {
            return ResponseEntity.ok(commonSkillsService.updateCommonSkills(id, skillDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommonSkills(@PathVariable int id) {
        commonSkillsService.deleteCommonSkills(id);
        return ResponseEntity.noContent().build();
    }
}
