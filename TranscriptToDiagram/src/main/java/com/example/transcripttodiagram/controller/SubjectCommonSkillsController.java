package com.example.transcripttodiagram.controller;

import com.example.transcripttodiagram.model.SubjectCommonSkills;
import com.example.transcripttodiagram.service.SubjectCommonSkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject-common-skills")
public class SubjectCommonSkillsController {
    private final SubjectCommonSkillsService service;

    public SubjectCommonSkillsController(SubjectCommonSkillsService service) {
        this.service = service;
    }

    @GetMapping
    public List<SubjectCommonSkills> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectCommonSkills> getById(@PathVariable int id) {
        Optional<SubjectCommonSkills> item = service.getById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SubjectCommonSkills create(@RequestBody SubjectCommonSkills item) {
        return service.create(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectCommonSkills> update(@PathVariable int id, @RequestBody SubjectCommonSkills details) {
        try {
            return ResponseEntity.ok(service.update(id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

