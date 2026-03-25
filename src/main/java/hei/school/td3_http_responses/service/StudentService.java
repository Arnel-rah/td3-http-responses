package hei.school.td3_http_responses.service;

import hei.school.td3_http_responses.entity.StudentEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<StudentEntity> studentsInMemory = new ArrayList<>();

    public List<StudentEntity> addStudents(List<StudentEntity> students) {
        studentsInMemory.addAll(students);
        return studentsInMemory;
    }

    public List<StudentEntity> getStudents() {
        return studentsInMemory;
    }
}