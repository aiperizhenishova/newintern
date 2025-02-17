package com.example.transcripttodiagram.DTO;

import lombok.Data;
import java.util.Map;

@Data
public class SaveDataRequest {
    private Map<Integer, Integer> selectedCourses; // key: boxIndex, value: subjectId
    private Map<Integer, Integer> courseScores;   // key: boxIndex, value: score
}
