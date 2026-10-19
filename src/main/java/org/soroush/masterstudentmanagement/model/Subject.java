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
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private Integer units;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "master_id", nullable = false)
        private Master master;

        @ManyToMany(mappedBy = "subjects")
        @JsonIgnore
        private Set<Student> students = new HashSet<>();


        public Subject(String title, Integer units, Master master) {
            this.title = title;
            this.units = units;
            this.master = master;
        }
    /*
        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Integer getUnits() {
            return units;
        }

        public Master getMaster() {
            return master;
        }

        public Set<Student> getStudents() {
            return students;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUnits(Integer units) {
            this.units = units;
        }

        public void setMaster(Master master) {
            this.master = master;

     */
        }



