package hei.school.td3_http_responses.controller;

import hei.school.td3_http_responses.entity.StudentEntity;
import hei.school.td3_http_responses.exception.BadRequestException;
import hei.school.td3_http_responses.service.StudentService;
import hei.school.td3_http_responses.validator.StudentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;
    private final StudentValidator studentValidator;

    public StudentController(StudentService studentService, StudentValidator studentValidator) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok()
                .header("X-Custom-Header", "my custom value")
                .body("Hello world!");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().body("le paramètre est requis.");
        }
        return ResponseEntity.ok("Welcome, " + name + " !");
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudents(@RequestBody List<StudentEntity> students) {
        try {
            studentValidator.validate(students);
            List<StudentEntity> saved = studentService.addStudents(students);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(name = "Accept", required = false) String accept) {
        if (accept == null || accept.isEmpty()) {
            return ResponseEntity.badRequest().body("l'en-tête est requis.");
        }

        List<StudentEntity> students = studentService.getStudents();

        if (accept.equals("application/json")) {
            return ResponseEntity.ok(students);
        }

        if (accept.equals("text/plain")) {
            StringBuilder body = new StringBuilder();
            for (StudentEntity s : students) {
                body.append(s.toString()).append("\n");
            }
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(body.toString());
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("type non supporté : " + accept);
    }
}