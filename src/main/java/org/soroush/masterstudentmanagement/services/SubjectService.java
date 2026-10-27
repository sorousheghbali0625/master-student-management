
package org.soroush.masterstudentmanagement.services;

import lombok.RequiredArgsConstructor;
import org.soroush.masterstudentmanagement.dto.SubjectResponse;
import org.soroush.masterstudentmanagement.dto.UpdateSubjectRequest;
import org.soroush.masterstudentmanagement.model.Master;
import org.soroush.masterstudentmanagement.model.Student;
import org.soroush.masterstudentmanagement.model.Subject;
import org.soroush.masterstudentmanagement.exception.ResourceNotFoundException;
import org.soroush.masterstudentmanagement.exception.DuplicateResourceException;
import org.soroush.masterstudentmanagement.exception.InvalidOperationException;
import org.soroush.masterstudentmanagement.normalization.TextNormalizer;
import org.soroush.masterstudentmanagement.repository.MasterRepository;
import org.soroush.masterstudentmanagement.repository.SubjectRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final MasterRepository masterRepository;
    private final TextNormalizer textNormalizer;



    @Transactional
    public SubjectResponse updateSubject(
            Long subjectId,
            UpdateSubjectRequest request) {

        // 1. Find existing subject
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with ID " + subjectId + " not found"
                ));

        // 2. Validate request
        if (request == null
                || request.title() == null
                || request.units() == null
                || request.units() <= 0
                || request.masterId() == null) {

            throw new InvalidOperationException(

                    "Invalid subject data"
            );
        }

        // 3. Normalize title
        String normalizedTitle =
                textNormalizer.normalize(subject.getTitle());

        if (normalizedTitle == null || normalizedTitle.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject title cannot be blank"
            );
        }

        // 4. Find master
        Master master = masterRepository.findById(request.masterId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Master not found: " + request.masterId()
                ));

        // 5. Update managed entity
        subject.setTitle(normalizedTitle);
        subject.setUnits(request.units());
        subject.setMaster(master);

        // 6. Return response
        return new SubjectResponse(
                subject.getId(),
                subject.getTitle(),
                subject.getUnits(),
                subject.getMaster().getId()
        );
    }


    @Transactional(readOnly = true)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject addSubject(Subject subject) {

        if (subject == null ||
                subject.getTitle() == null ||
                subject.getTitle().isBlank() ||
                subject.getUnits() == null ||
                subject.getUnits() <= 0 ||
                subject.getMaster() == null ||
                subject.getMaster().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid subject data"
            );
        }

        Long masterId = subject.getMaster().getId();

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Master with ID " + masterId + " not found"
                ));
        String normalizedTitle = textNormalizer.normalize(subject.getTitle());
        Subject newSubject = new Subject(
                normalizedTitle,
                subject.getUnits(),
                master
        );

        return subjectRepository.save(newSubject);
    }

    @Transactional
    public void deleteSubject(Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with ID " + subjectId + " not found"
                ));

        for (Student student : new HashSet<>(subject.getStudents())) {
            student.removeSubject(subject);
        }

        subjectRepository.delete(subject);
    }
}
