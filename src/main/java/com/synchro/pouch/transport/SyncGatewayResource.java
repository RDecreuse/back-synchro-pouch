package com.synchro.pouch.transport;

import com.synchro.pouch.business.dto.AnimalDto;
import com.synchro.pouch.service.couch.CouchBaseService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/sync/animal")
public class SyncGatewayResource {

    public static final String ANIMAL_BUCKET_NAME = "r_animal";

    @Autowired
    private CouchBaseService service;

    @RequestMapping(method = RequestMethod.GET)
    public AnimalDto findOne(@RequestParam String docId) {
        return this.service.findDocumentAs(docId, ANIMAL_BUCKET_NAME, AnimalDto.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insert(@RequestBody AnimalDto animal, HttpServletResponse response) {
        if(null == animal.getDocumentId()){
            throw new IllegalArgumentException("Document id must not be null.");
        }
        boolean updated = this.service.insertOrUpdate(animal, animal.getDocumentId(), ANIMAL_BUCKET_NAME);
        if(!updated){
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
