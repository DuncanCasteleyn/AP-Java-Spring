package com.infosupport.ap.exercise.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClassRegistration {
    @Id
    @GeneratedValue
    private Long id;
    @Length(min = 3)
    private String classname;
    private String lector;
    private String topic;
    private Boolean closed;
    @OneToMany
    @JoinColumn(name="classId", referencedColumnName="id")
    private List<Presence> presentStudents;

    public ClassRegistration(String classname, String lector, String topic, List<Presence> presentStudents) {
        this.classname = classname;
        this.lector = lector;
        this.topic = topic;
        this.presentStudents = presentStudents;
        closed = false;
    }

    public ClassRegistration() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getClosed() {
        return closed;
    }

    public String getClassname() {
        return classname;
    }

    public String getLector() {
        return lector;
    }

    public String getTopic() {
        return topic;
    }

    public List<Presence> getPresentStudents() {
        return presentStudents;
    }

    public void setPresentStudents(List<Presence> presentStudents) {
        this.presentStudents = presentStudents;
    }
}
