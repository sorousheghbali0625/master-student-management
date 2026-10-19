
package org.soroush.masterstudentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.model.Master;
import org.soroush.masterstudentmanagement.model.Student;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.services.MasterService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService service;



    @GetMapping
    public List<Master> getAllMasters() {
        return service.getAllMasters();
    }

    @GetMapping("/{masterId}/students")
    public List<Student> getMastersStudents(
            @PathVariable Long masterId
    ) {
        return service.getMastersStudents(masterId);
    }

    @GetMapping("/{masterId}/subjects")
    public List<Subject> getMastersSubjects(
            @PathVariable Long masterId
    ) {
        return service.getMastersSubjects(masterId);
    }
}
