
package org.soroush.masterstudentmanagement.services;

import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.model.Master;
import org.soroush.masterstudentmanagement.model.Student;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.repository.MasterRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterRepository masterRepository;


    @Transactional(readOnly = true)
    public List<Master> getAllMasters() {
        return masterRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Subject> getMastersSubjects(Long masterId) {

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Master not found: " + masterId
                ));

        return List.copyOf(master.getSubjects());
    }

    @Transactional(readOnly = true)
    public List<Student> getMastersStudents(Long masterId) {

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Master not found: " + masterId
                ));

        Set<Student> students = master.getSubjects()
                .stream()
                .flatMap(subject -> subject.getStudents().stream())
                .collect(Collectors.toSet());

        return List.copyOf(students);
    }
}
