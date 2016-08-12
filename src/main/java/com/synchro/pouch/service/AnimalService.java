package com.synchro.pouch.service;

import com.synchro.pouch.business.Animal;

import java.util.List;

public interface AnimalService {

    List<Animal> getAnimals();

    Animal findOne(Long animalId);

    Animal createOrUpdate(Animal animal);

    Animal update(Animal animal);

    void delete(Long animalId);

}
