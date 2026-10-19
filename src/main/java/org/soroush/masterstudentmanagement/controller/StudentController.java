
package org.soroush.masterstudentmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.dto.CreateStudentRequest;
import org.soroush.masterstudentmanagement.dto.StudentResponse;
import org.soroush.masterstudentmanagement.model.Student;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.services.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Student Management",
        description = "APIs for students and course selection"
)
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @Operation(
            summary = "Create a student",
            description = "Registers a new student with a unique student number"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(
            @RequestBody CreateStudentRequest request) {

        return service.createStudent(request);
    }


    @Operation(
            summary = "Delete a student",
            description = "Deletes the student and their course selections"
    )
    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long studentId) {

        service.deleteStudent(studentId);
    }

    @Operation(
            summary = "Get all students",
            description = "Returns a list of registered students"
    )
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{studentId}/subjects")
    public List<Subject> getStudentSubjects(
            @PathVariable Long studentId) {

        return service.getStudentSubjects(studentId);
    }
    @Operation(
            summary = "Select a subject",
            description = "Assigns a subject to a student"
    )
    @PostMapping("/{studentId}/subjects/{subjectId}")
    public void selectSubject(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {

        service.selectSubject(studentId, subjectId);
    }

    @DeleteMapping("/{studentId}/subjects/{subjectId}")
    public void removeSubject(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {

        service.removeSubject(studentId, subjectId);
    }
}
