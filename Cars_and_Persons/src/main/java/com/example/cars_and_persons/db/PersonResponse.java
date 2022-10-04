package com.example.cars_and_persons.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PersonResponse {
    private Long id;
    private String name;
    private String date;
    private List<Car> list;

    public PersonResponse() {
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

    public String getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public List<Car> getList() {
        return list;
    }

    public void setList(List<Car> list) {
        this.list = list;
    }
}
