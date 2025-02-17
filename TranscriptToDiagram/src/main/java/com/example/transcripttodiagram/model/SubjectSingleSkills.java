package com.example.transcripttodiagram.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "subject_single_skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSingleSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "single_skill_id", nullable = false)
    private SingleSkills singleSkills;
}


