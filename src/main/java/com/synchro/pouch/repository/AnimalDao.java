package com.synchro.pouch.repository;

import com.synchro.pouch.business.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalDao extends CrudRepository<Animal, Long> {

}
