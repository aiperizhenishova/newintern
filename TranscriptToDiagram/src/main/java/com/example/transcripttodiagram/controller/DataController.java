package com.example.transcripttodiagram.controller;

import com.example.transcripttodiagram.DTO.SaveDataRequest;
import com.example.transcripttodiagram.model.*;
import com.example.transcripttodiagram.repository.*;
import com.example.transcripttodiagram.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectCommonSkillsRepository subjectCommonSkillsRepository;

    @Autowired
    private SubjectSingleSkillsRepository subjectSingleSkillsRepository;

    @Autowired
    private CommonSkillsRepository commonSkillsRepository;

    @Autowired
    private SingleSkillsRepository singleSkillsRepository;

    @Autowired
    private StudentService studentService;

    @PostMapping("/save-data")
    public ResponseEntity<?> saveData(@RequestBody SaveDataRequest request, Authentication authentication) {
        try {
            // ... (Ваш существующий код для сохранения данных)

            // ВОТ ИСПРАВЛЕНИЕ:
            return ResponseEntity.ok(new MessageResponse("Data saved successfully")); // Возвращаем JSON!

        } catch (Exception e) {
            // Обработка ошибок (также возвращаем JSON):
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Ошибка сохранения данных: " + e.getMessage()));
        }
    }

    // Класс для JSON-ответа об успехе:
    static class MessageResponse { // static, чтобы можно было использовать внутри DataController
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() { // Геттер ОБЯЗАТЕЛЕН!
            return message;
        }
    }

    // Класс для JSON-ответа об ошибке (уже был в предыдущих примерах):
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}