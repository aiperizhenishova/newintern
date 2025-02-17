package com.example.transcripttodiagram.controller;

import com.example.transcripttodiagram.model.SubjectSingleSkills;
import com.example.transcripttodiagram.service.SubjectSingleSkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject-single-skills")
public class SubjectSingleSkillsController {
    private final SubjectSingleSkillsService service;

    public SubjectSingleSkillsController(SubjectSingleSkillsService service) {
        this.service = service;
    }

    @GetMapping
    public List<SubjectSingleSkills> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectSingleSkills> getById(@PathVariable int id) {
        Optional<SubjectSingleSkills> item = service.getById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SubjectSingleSkills create(@RequestBody SubjectSingleSkills item) {
        return service.create(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectSingleSkills> update(@PathVariable int id, @RequestBody SubjectSingleSkills details) {
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
