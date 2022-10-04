package com.example.cars_and_persons.repository;

import com.example.cars_and_persons.db.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRep extends CrudRepository<Car, Long> {

}
