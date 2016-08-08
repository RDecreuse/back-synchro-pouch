package com.synchro.pouch.transport;

import com.synchro.pouch.business.Animal;
import com.synchro.pouch.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/animals")
public class AnimalResource {

    @Autowired
    private AnimalService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Animal> findAll() {
        return this.service.getAnimals();
    }

    @RequestMapping(path = "/{animalId}", method = RequestMethod.GET)
    public ResponseEntity<Animal> findOne(@PathVariable final Long animalId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findOne(animalId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Animal> create(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(animal));
    }

    @RequestMapping(path = "/{animalId}", method = RequestMethod.PUT)
    public ResponseEntity<Animal> update(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(animal));
    }

    @RequestMapping(path = "/{animalId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long animalId) {
        service.delete(animalId);
    }

}
