package com.example.transcripttodiagram.controller;

import com.example.transcripttodiagram.model.SingleSkills;
import com.example.transcripttodiagram.service.SingleSkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/single-skills")
public class SingleSkillsController {
    private final SingleSkillsService service;

    public SingleSkillsController(SingleSkillsService service) {
        this.service = service;
    }

    @GetMapping
    public List<SingleSkills> getAll() {
        return service.getAllSingleSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSkills> getById(@PathVariable int id) {
        Optional<SingleSkills> skill = service.getSingleSkillsById(id);
        return skill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SingleSkills create(@RequestBody SingleSkills skill) {
        return service.createSingleSkills(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleSkills> update(@PathVariable int id, @RequestBody SingleSkills skillDetails) {
        try {
            return ResponseEntity.ok(service.updateSingleSkills(id, skillDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteSingleSkills(id);
        return ResponseEntity.noContent().build();
    }
}
