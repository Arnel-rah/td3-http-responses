package hei.school.td3_http_responses.controller;

import hei.school.td3_http_responses.entity.StudentEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private final List<StudentEntity> studentList = new ArrayList<>();

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
            return new ResponseEntity<>("Le paramètre 'name' est requis.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Bienvenue, " + name + " !", HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<List<StudentEntity>> addStudents(@RequestBody List<StudentEntity> students) {
        try {
            studentList.addAll(students);
            return new ResponseEntity<>(students, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(@RequestHeader(required = false) String accept) {
        if (accept == null || accept.isEmpty()) {
            return new ResponseEntity<>("L'en-tête 'Accept' est requis.", HttpStatus.BAD_REQUEST);
        }

        try {
            if (accept.equals("application/json")) {
                return new ResponseEntity<>(studentList, HttpStatus.OK);
            }

            if (accept.equals("text/plain")) {
                StringBuilder body = new StringBuilder();
                for (StudentEntity s : studentList) {
                    body.append(s.toString()).append("\n");
                }
                return new ResponseEntity<>(body.toString(), HttpStatus.OK);
            }

            return new ResponseEntity<>("Type 'Accept' non supporté : " + accept, HttpStatus.NOT_IMPLEMENTED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}