
package org.soroush.masterstudentmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.dto.SubjectResponse;
import org.soroush.masterstudentmanagement.dto.UpdateSubjectRequest;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.services.SubjectService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @Operation(
            summary = "Update a subject",
            description = "Updates the title, units, and assigned master"
    )
    @PutMapping("/{subjectId}")
    public SubjectResponse updateSubject(
            @PathVariable Long subjectId,
            @RequestBody UpdateSubjectRequest request) {

        return service.updateSubject(subjectId, request);
    }


    @GetMapping
    public List<Subject> getAllSubjects() {
        return service.getAllSubjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject addSubject(@RequestBody Subject subject) {
        return service.addSubject(subject);
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(
            @PathVariable Long subjectId) {

        service.deleteSubject(subjectId);
    }
}

