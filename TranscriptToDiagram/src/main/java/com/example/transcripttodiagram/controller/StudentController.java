package com.example.transcripttodiagram.controller;


import com.example.transcripttodiagram.model.Student;
import com.example.transcripttodiagram.security.JwtUtil;
import com.example.transcripttodiagram.service.AuthService;
import com.example.transcripttodiagram.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        if (studentService.findByEmail(student.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setLastLogin(LocalDateTime.now());
        Student savedStudent = studentService.save(student);

        UserDetails userDetails = authService.loadUserByUsername(savedStudent.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.get("email"),
                            credentials.get("password")
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        UserDetails userDetails = authService.loadUserByUsername(credentials.get("email"));
        String token = jwtUtil.generateToken(userDetails.getUsername());

        Student student = studentService.findByEmail(credentials.get("email"));
        student.setLastLogin(LocalDateTime.now());
        studentService.save(student);

        return ResponseEntity.ok(Map.of("token", token));
    }

//    @GetMapping
//    public ResponseEntity<List<Student>> getAllStudents() {
//        return ResponseEntity.ok(studentService.findAll());
//    }
//
//    // Получить студента по ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(student);
//    }
//
//    // Получить студента по email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getStudentByEmail(@PathVariable String email) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(student);
//    }

    // Получить текущего студента (по токену)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentStudent(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    // Получить всех студентов (например, для администратора)
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    // Обновить данные текущего студента
    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(Authentication authentication, @RequestBody Student updatedStudent) {
        String email = authentication.getName();
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedStudent.getEmail() != null && !updatedStudent.getEmail().equals(student.getEmail())) {
            student.setEmail(updatedStudent.getEmail());
        }
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
        }

        studentService.save(student);

        // Генерация нового токена после обновления данных
        String newToken = jwtUtil.generateToken(student.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", newToken);
        response.put("email", student.getEmail());

        return ResponseEntity.ok(response);
    }

    // Удалить текущего студента
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStudent(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.delete(student);
        return ResponseEntity.ok().build();
    }

}
//    // Обновить студента по ID
//    @PutMapping()
//    public ResponseEntity<?> updateStudentById(@PathVariable Long id, @RequestBody Student updatedStudent) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (updatedStudent.getEmail() != null) {
//            student.setEmail(updatedStudent.getEmail());
//        }
//        if (updatedStudent.getPassword() != null) {
//            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
//        }
//
//        studentService.save(student);
//        return ResponseEntity.ok(student);
//    }
//
//    // Обновить студента по email
//    @PutMapping("/email/{email}")
//    public ResponseEntity<?> updateStudentByEmail(@PathVariable String email, @RequestBody Student updatedStudent) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (updatedStudent.getEmail() != null) {
//            student.setEmail(updatedStudent.getEmail());
//        }
//        if (updatedStudent.getPassword() != null) {
//            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
//        }
//
//        studentService.save(student);
//        return ResponseEntity.ok(student);
//    }
//
//    // Удалить студента по ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        studentService.delete(student);
//        return ResponseEntity.ok().build();
//    }
//
//    // Удалить студента по email
//    @DeleteMapping("/email/{email}")
//    public ResponseEntity<?> deleteStudentByEmail(@PathVariable String email) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        studentService.delete(student);
//        return ResponseEntity.ok().build();
//    }





//
//import com.example.transcripttodiagram.model.Student;
//
//import com.example.transcripttodiagram.security.JwtUtil;
//import com.example.transcripttodiagram.service.AuthService;
//import com.example.transcripttodiagram.service.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
//
//@RestController
//@RequestMapping("/profile")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentService studentService;
//    private final AuthService authService;
//    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//
//
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Student student) {
//        if (studentService.findByEmail(student.getEmail()) != null) {
//            return ResponseEntity.badRequest().body("Email already exists");
//        }
//
//        student.setPassword(passwordEncoder.encode(student.getPassword()));
//        student.setLastLogin(LocalDateTime.now());
//        Student savedStudent = studentService.save(student);
//
//        UserDetails userDetails = authService.loadUserByUsername(savedStudent.getEmail());
//        String token = jwtUtil.generateToken(userDetails.getUsername());
//
//        return ResponseEntity.ok(Map.of("token", token, "message", "Registration successful. Please login."));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            credentials.get("email"),
//                            credentials.get("password")
//                    )
//            );
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid credentials");
//        }
//
//        UserDetails userDetails = authService.loadUserByUsername(credentials.get("email"));
//        String token = jwtUtil.generateToken(userDetails.getUsername());
//
//        Student student = studentService.findByEmail(credentials.get("email"));
//        student.setLastLogin(LocalDateTime.now());
//        studentService.save(student);
//
//        return ResponseEntity.ok(Map.of("token", token, "message", "Login successful. Welcome to Home Page."));
//    }
//
//
//    // Получить всех студентов (только для администратора)
//    @GetMapping
//    public ResponseEntity<?> getAllStudents() {
//        List<Student> students = studentService.findAll();
//        return ResponseEntity.ok(students);
//    }
//
//
//    // Получить студента по ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(student);
//    }
//
//    // Получить студента по email
//    @GetMapping()
//    public ResponseEntity<?> getStudentByEmail(@PathVariable String email) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(student);
//    }
//
//    // Обновить студента по ID
//    @PutMapping()
//    public ResponseEntity<?> updateStudentById(@PathVariable Long id, @RequestBody Student updatedStudent) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (updatedStudent.getEmail() != null) {
//            student.setEmail(updatedStudent.getEmail());
//        }
//        if (updatedStudent.getPassword() != null) {
//            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
//        }
//
//        studentService.save(student);
//        return ResponseEntity.ok(student);
//    }
//
//    // Обновить студента по email
//    @PutMapping()
//    public ResponseEntity<?> updateStudentByEmail(@PathVariable String email, @RequestBody Student updatedStudent) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (updatedStudent.getEmail() != null) {
//            student.setEmail(updatedStudent.getEmail());
//        }
//        if (updatedStudent.getPassword() != null) {
//            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
//        }
//
//        studentService.save(student);
//        return ResponseEntity.ok(student);
//    }
//
//    // Удалить студента по ID
//    @DeleteMapping()
//    public ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
//        Student student = studentService.findById(id);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        studentService.delete(student);
//        return ResponseEntity.ok().build();
//    }
//
//    // Удалить студента по email
//    @DeleteMapping()
//    public ResponseEntity<?> deleteStudentByEmail(@PathVariable String email) {
//        Student student = studentService.findByEmail(email);
//        if (student == null) {
//            return ResponseEntity.notFound().build();
//        }
//        studentService.delete(student);
//        return ResponseEntity.ok().build();
//    }
//}





//
//
//@RestController
//@RequestMapping("/profile")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentService studentService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthService authService;
//    private final JwtUtil jwtUtil;
//
//    private final AuthenticationManager authenticationManager;
//
//    // Получить профиль текущего пользователя
//    @GetMapping
//    public ResponseEntity<?> getProfile(Authentication authentication) {
//        // Извлекаем текущего аутентифицированного пользователя
//        Student currentStudent = (Student) authentication.getPrincipal();
//        return ResponseEntity.ok(currentStudent);
//    }
//
//    // Обновить профиль текущего пользователя
//    @PutMapping
//    public ResponseEntity<?> updateProfile(@RequestBody Student updatedStudent, Authentication authentication) {
//        // Извлекаем текущего аутентифицированного пользователя
//        Student currentStudent = (Student) authentication.getPrincipal();
//
//        // Проверяем, что обновление производится только для своего аккаунта
//        if (updatedStudent.getId() != null && !updatedStudent.getId().equals(currentStudent.getId())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Ошибка, если id не совпадают
//        }
//
//        if (updatedStudent.getEmail() != null) {
//            currentStudent.setEmail(updatedStudent.getEmail());
//        }
//        if (updatedStudent.getPassword() != null) {
//            currentStudent.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
//        }
//
//        studentService.save(currentStudent);
//        return ResponseEntity.ok(currentStudent);
//    }
//
//    // Удалить профиль текущего пользователя
//    @DeleteMapping
//    public ResponseEntity<?> deleteProfile(Authentication authentication) {
//        // Извлекаем текущего аутентифицированного пользователя
//        Student currentStudent = (Student) authentication.getPrincipal();
//
//        studentService.delete(currentStudent);
//        return ResponseEntity.ok().build();
//    }
//
//
//        @PostMapping("/register")
//        public ResponseEntity<?> register(@RequestBody Student student) {
//            if (studentService.findByEmail(student.getEmail()) != null) {
//                return ResponseEntity.badRequest().body("Email already exists");
//            }
//
//            student.setPassword(passwordEncoder.encode(student.getPassword()));
//            student.setLastLogin(LocalDateTime.now());
//            Student savedStudent = studentService.save(student);
//
//            UserDetails userDetails = authService.loadUserByUsername(savedStudent.getEmail());
//            String token = jwtUtil.generateToken(userDetails.getUsername());
//
//            return ResponseEntity.ok(Map.of("token", token, "message", "Registration successful. Please login."));
//        }
//
//        @PostMapping("/login")
//        public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//            try {
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                credentials.get("email"),
//                                credentials.get("password")
//                        )
//                );
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body("Invalid credentials");
//            }
//
//            UserDetails userDetails = authService.loadUserByUsername(credentials.get("email"));
//            String token = jwtUtil.generateToken(userDetails.getUsername());
//
//            Student student = studentService.findByEmail(credentials.get("email"));
//            student.setLastLogin(LocalDateTime.now());
//            studentService.save(student);
//
//            return ResponseEntity.ok(Map.of("token", token, "message", "Login successful. Welcome to Home Page."));
//        }
//
//
//        @PostMapping("/refresh-token")
//        public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
//            String refreshToken = request.get("refreshToken");
//            if (refreshToken == null || !jwtUtil.isTokenExpired(refreshToken)) {
//                return ResponseEntity.badRequest().body("Invalid or expired refresh token");
//            }
//
//            String email = jwtUtil.getEmailFromToken(refreshToken);
//            UserDetails userDetails = authService.loadUserByUsername(email);
//
//            if (userDetails != null) {
//                String newAccessToken = jwtUtil.generateToken(email);
//                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
//            }
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
//        }
//
//
//
//}
