package com.example.cars_and_persons.controllers;

import com.alibaba.fastjson.JSONObject;
import com.example.cars_and_persons.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/person", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createPerson(@RequestBody JSONObject jsonObject){
        return personService.createPerson(jsonObject);
    }
    @RequestMapping(value = "/car", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createCar(@RequestBody JSONObject jsonObject){
        return personService.createCar(jsonObject);
    }

    @RequestMapping(value = "/personwithcars", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getPersonsWithCars(@RequestParam(value = "personid", required = false) Long id){
        return personService.getPersonsWithCars(id);
    }
    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic(){
        return personService.statistic();
    }
    @GetMapping("/clear")
    public ResponseEntity<?> clear(){
        return personService.clear();
    }

    @GetMapping("/start")
    public void start(){
        personService.start();
    }

}
