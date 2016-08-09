package com.synchro.pouch.service;

import com.google.common.collect.Lists;
import com.synchro.pouch.business.Animal;
import com.synchro.pouch.repository.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    AnimalDao dao;

    @Override
    public List<Animal> getAnimals() {
        return Lists.newArrayList(dao.findAll());
    }

    @Override
    public Animal findOne(Long animalId) {
        return dao.findOne(animalId);
    }

    @Override
    public Animal create(Animal animal) {
        return dao.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        if (StringUtils.isEmpty(animal.getId())) {
            throw new IllegalArgumentException("Product must have an id.");
        }
        return dao.save(animal);
    }

    @Override
    public void delete(Long animalId) {
        this.dao.delete(animalId);
    }
}
