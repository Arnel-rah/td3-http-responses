package hei.school.td3_http_responses.controller;

import hei.school.td3_http_responses.entity.StudentEntity;
import hei.school.td3_http_responses.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "text/plain")
                .header("X-Custom-Header", "my custom value")
                .body("Hello world!");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>("le paramètre est requis.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Welcome, " + name + " !", HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<List<StudentEntity>> addStudents(@RequestBody List<StudentEntity> students) {
        try {
            return new ResponseEntity<>(studentService.addStudents(students), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(required = false) String accept) {
        if (accept == null || accept.isEmpty()) {
            return new ResponseEntity<>("l'en-tête est requis.", HttpStatus.BAD_REQUEST);
        }

        try {
            if (accept.equals("application/json")) {
                return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
            }

            if (accept.equals("text/plain")) {
                StringBuilder body = new StringBuilder();
                for (StudentEntity s : studentService.getStudents()) {
                    body.append(s.toString()).append("\n");
                }
                return new ResponseEntity<>(body.toString(), HttpStatus.OK);
            }

            return new ResponseEntity<>("type non supporté : " + accept, HttpStatus.NOT_IMPLEMENTED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
