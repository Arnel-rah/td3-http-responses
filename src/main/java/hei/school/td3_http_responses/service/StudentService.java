package hei.school.td3_http_responses.service;

import hei.school.td3_http_responses.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<StudentEntity> studentList = new ArrayList<>();

    public List<StudentEntity> addStudents(List<StudentEntity> students) {
        studentList.addAll(students);
        return students;
    }

    public List<StudentEntity> getStudents() {
        return studentList;
    }
}
