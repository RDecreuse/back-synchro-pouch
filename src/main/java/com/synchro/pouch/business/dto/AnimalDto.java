package com.synchro.pouch.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synchro.pouch.business.Animal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimalDto extends Animal {

    private List<String> channels;

    @JsonProperty("_id")
    private String documentId;

    @JsonProperty("_rev")
    private String revision;
}
