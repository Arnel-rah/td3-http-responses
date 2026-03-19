package hei.school.td3_http_responses.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
@GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam String name){
    return ResponseEntity.status(HttpStatus.OK).
            header("Content-Type","text/plain").
            body("Welcome "+name+"!");
}

}
