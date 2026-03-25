package hei.school.td3_http_responses.validator;

import hei.school.td3_http_responses.entity.StudentEntity;
import hei.school.td3_http_responses.exception.BadRequestException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StudentValidator {
    public void validate(List<StudentEntity> students) {
        for (StudentEntity student : students) {
            if (student.getReference() == null || student.getReference().isBlank()) {
                throw new BadRequestException("NewStudent.reference cannot be null");
            }
            if (student.getFirstName() == null || student.getFirstName().isBlank()) {
                throw new BadRequestException("NewStudent.firstName cannot be null");
            }
            if (student.getLastName() == null || student.getLastName().isBlank()) {
                throw new BadRequestException("NewStudent.lastName cannot be null");
            }
        }
    }
}