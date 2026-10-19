package org.soroush.masterstudentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
public class Student {

        @Id
        @GeneratedValue(strategy =GenerationType.IDENTITY)
        @Setter(AccessLevel.NONE)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String studentNumber;

        @ManyToMany
        @JoinTable(
                name = "student_subjects",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "subject_id"),
                uniqueConstraints = @UniqueConstraint(
                        columnNames = {"student_id", "subject_id"}
                )
        )
        @JsonIgnore
        private Set<Subject> subjects = new HashSet<>();



        public Student(String name, String studentNumber) {
            this.name = name;
            this.studentNumber = studentNumber;
        }
/*
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public Set<Subject> getSubjects() {
            return subjects;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }
*/
        public void addSubject(Subject subject) {
            subjects.add(subject);
            subject.getStudents().add(this);
        }

        public void removeSubject(Subject subject) {
            subjects.remove(subject);
            subject.getStudents().remove(this);
        }

    }


