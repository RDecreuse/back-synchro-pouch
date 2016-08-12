package com.synchro.pouch.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synchro.pouch.business.Animal;
import lombok.Getter;
import lombok.Setter;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimalChanges {

    private String name;

    private Integer age;

    private List<String> channels;

    @JsonProperty("_id")
    private String documentId;

    @JsonProperty("_rev")
    private String revision;

    @JsonProperty("_deleted")
    private Boolean deleted;

    public AnimalChanges(){
    }

    public Animal toAnimal(){
        if(this.deleted){
            throw new InvalidStateException("Animal has been deleted, you should not convert this object to an animal");
        }
        Animal a = new Animal();
        a.setName(this.name);
        a.setAge(this.age);
        a.setDocumentId(this.documentId);
        return a;
    }

    @Override
    public String toString() {
        return "AnimalSync{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", channels=" + channels +
                ", documentId='" + documentId + '\'' +
                ", revision='" + revision + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}
