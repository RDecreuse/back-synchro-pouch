package com.synchro.pouch.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synchro.pouch.business.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimalSync {

    private String name;

    private Integer age;

    private List<String> channels;

    @JsonProperty("_id")
    private String documentId;

    @JsonProperty("_rev")
    private String revision;

    public AnimalSync(){
    }

    public AnimalSync(Animal animal){
        this.name = animal.getName();
        this.age = animal.getAge();
        this.documentId = animal.getDocumentId();
    }

    public Animal toAnimal(){
        Animal animal = new Animal();
        animal.setName(this.name);
        animal.setAge(this.age);
        animal.setDocumentId(this.documentId);
        return animal;
    }
}
