package com.example.cars_and_persons.repository;

import com.example.cars_and_persons.db.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRep extends CrudRepository<Person, Long> {


}
