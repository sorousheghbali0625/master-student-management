
package org.soroush.masterstudentmanagement.services;

import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.dto.CreateStudentRequest;
import org.soroush.masterstudentmanagement.dto.StudentResponse;
import org.soroush.masterstudentmanagement.model.Student;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.normalization.TextNormalizer;
import org.soroush.masterstudentmanagement.repository.StudentRepository;
import org.soroush.masterstudentmanagement.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashSet;
import java.util.List;
import org.soroush.masterstudentmanagement.exception.ResourceNotFoundException;
import org.soroush.masterstudentmanagement.exception.DuplicateResourceException;
import org.soroush.masterstudentmanagement.exception.InvalidOperationException;



@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TextNormalizer textNormalizer;



    @Transactional
    public void deleteStudent(Long studentId) {

        Student student = findStudent(studentId);

        for (Subject subject : new HashSet<>(student.getSubjects())) {
            student.removeSubject(subject);
        }

        studentRepository.delete(student);
    }


    @Transactional
    public StudentResponse createStudent(CreateStudentRequest request) {

        if (request == null
                || request.name() == null
                || request.name().isBlank()
                || request.studentNumber() == null
                || request.studentNumber().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid student data"
            );
        }

        String name = request.name().trim();
        String normalized = textNormalizer.normalize(name);
        String studentNumber = request.studentNumber().trim();

        if (studentRepository.existsByStudentNumber(studentNumber)) {
            throw new DuplicateResourceException(
                    "Student number is already registered"
            );
        }

        Student student = new Student(normalized, studentNumber);

        Student savedStudent = studentRepository.save(student);

        return new StudentResponse(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getStudentNumber()
        );
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Subject> getStudentSubjects(Long studentId) {

        Student student = findStudent(studentId);

        return List.copyOf(student.getSubjects());
    }

    @Transactional
    public void selectSubject(Long studentId, Long subjectId) {

        Student student = findStudent(studentId);
        Subject subject = findSubject(subjectId);

        if (student.getSubjects().contains(subject)) {
            throw new DuplicateResourceException(
                    "Student has already selected this subject"
            );
        }

        student.addSubject(subject);
    }

    @Transactional
    public void removeSubject(Long studentId, Long subjectId) {

        Student student = findStudent(studentId);
        Subject subject = findSubject(subjectId);

        if (!student.getSubjects().contains(subject)) {
            throw new InvalidOperationException(
                    "Student has not selected this subject"
            );
        }

        student.removeSubject(subject);
    }

    private Student findStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student with ID " + studentId + " not found"
                ));
    }

    private Subject findSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student with ID " + subjectId + " not found"
                ));
    }
}
