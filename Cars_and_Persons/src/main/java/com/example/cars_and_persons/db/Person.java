package com.example.cars_and_persons.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.time.LocalDate;

@Entity
@Table(name = "PERSONS")
public class Person {
    @Id
    @Column(name = "person_id", nullable = false)
    private Long id;

    @Column(name = "person_name", nullable = false)
    private  String name;

    @Column(name = "person_birthday_date")
    private LocalDate birthdate;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdayDate() {
        return birthdate;
    }

    public void setBirthdayDate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
