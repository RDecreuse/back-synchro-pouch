package com.synchro.pouch.transport;

import com.synchro.pouch.business.dto.AnimalDto;
import com.synchro.pouch.service.couch.CouchBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
}
