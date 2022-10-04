package com.example.cars_and_persons.services;

import com.alibaba.fastjson.JSONObject;
import com.example.cars_and_persons.db.Car;
import com.example.cars_and_persons.db.Person;
import com.example.cars_and_persons.db.PersonResponse;
import com.example.cars_and_persons.db.Statistics;
import com.example.cars_and_persons.repository.CarRep;
import com.example.cars_and_persons.repository.PersonRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Service
public class PersonService {
    @Autowired
    private PersonRep personRep;
    @Autowired
    private CarRep carRep;

    public ResponseEntity<String> createPerson(JSONObject jsonObject){

        Long id;
        try {
            id = jsonObject.getLong("id");
            boolean exist = personRep.findById(id).isPresent();
            if (exist){
                return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        LocalDate date;
        try{
            date = LocalDate.parse(jsonObject.getString("birthdate"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            if (date.isAfter(LocalDate.now())){
                return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }

        String name;
        try{
            name = jsonObject.getString("name");
            if ("".equals(name)){
                return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        System.out.println(id + name + date);
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setBirthdayDate(date);
        personRep.save(person);
        return new ResponseEntity<>("Запись добавлена", HttpStatus.OK);
    }

    public ResponseEntity<String> createCar(JSONObject jsonObject){
        Long id;
        try {
            id = jsonObject.getLong("id");
            boolean exist = carRep.findById(id).isPresent();
            if (exist){
                return new ResponseEntity<>("Такой id уже есть в базе", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        String model;
        try{
            model = jsonObject.getString("model");
            if ("".equals(model)){
                return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
            }
            String[] list = model.split("-");
            if (list.length!=2){
                return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        int horsePower;
        try{
            horsePower = jsonObject.getIntValue("horsepower");
            if (horsePower<1){
                return new ResponseEntity<>("Отрицательная мощность", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        Long ownerId;
        Person owner;
        try{
            ownerId = jsonObject.getLong("ownerId");
            Optional<Person> person = personRep.findById(ownerId);
            if (person.isPresent()){
                owner = person.get();
            }else{
                return new ResponseEntity<>("Нет такого владельца", HttpStatus.BAD_REQUEST);
            }
            if (Period.between(owner.getBirthdayDate(), LocalDate.now()).getYears() < 18){
                return new ResponseEntity<>("Владелец моложе 18", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>("Ошибка в теле запроса", HttpStatus.BAD_REQUEST);
        }
        Car car = new Car();
        car.setId(id);
        car.setModel(model);
        car.setHorsepower(horsePower);
        car.setOwnerId(ownerId);
        carRep.save(car);
        System.out.println(car);
        return new ResponseEntity<>("Запись создана", HttpStatus.OK);
    }

    public void start() {
        Person person = new Person();
        person.setId(1l);
        person.setName("Bob");
        LocalDate date = LocalDate.of(2000,01,28);
        System.out.println(date);
        person.setBirthdayDate(date);
        System.out.println(person);
        personRep.save(person);
    }

    public ResponseEntity<?> getPersonsWithCars(Long id) {
        if (id == null){
            return new ResponseEntity<>("Пустой запрос", HttpStatus.BAD_REQUEST);
        }
        PersonResponse personResponse = new PersonResponse();
        Optional<Person> person = personRep.findById(id);
        if (person.isPresent()){
            personResponse.setId(id);
            personResponse.setName(person.get().getName());
            personResponse.setDate(person.get().getBirthdayDate());
            Iterable<Car> allCars = carRep.findAll();
            List<Car> list = new LinkedList<>();
            allCars.forEach((p) -> {
                if (p.getOwnerId()==id){
                    list.add(p);
                }
            });
            personResponse.setList(list);
        }else{
            return new ResponseEntity<>("Такого id нет в базе Person", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> statistic() {
        Statistics statisticResponse = new Statistics();
        statisticResponse.setCarCount(carRep.count());
        statisticResponse.setPersonCount(personRep.count());
        Iterable<Car> allCars = carRep.findAll();
        Set<String> set = new HashSet<>();
        allCars.forEach((p) -> {
            String[] strings = p.getModel().split("-");
            set.add(strings[0]);
        });
        statisticResponse.setUniqueVendorCount((long) set.size());
        return new ResponseEntity<>(statisticResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> clear() {
        carRep.deleteAll();
        personRep.deleteAll();
        return new ResponseEntity<>("База очищена", HttpStatus.OK);
    }
}
