package com.synchro.pouch.transport;

import com.synchro.pouch.business.dto.AnimalSync;
import com.synchro.pouch.service.couch.CouchBaseService;
import com.synchro.pouch.service.couch.SynchronizationService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/sync/animal")
public class SyncGatewayResource {

    @Value("${url.syncgateway.animal.bucket}")
    private String animalBucket;

    @Autowired
    private CouchBaseService service;

    @Autowired
    private SynchronizationService synchronizationService;

    @RequestMapping(method = RequestMethod.GET)
    public AnimalSync findOne(@RequestParam String docId) {
        return this.service.findDocumentAs(docId, animalBucket, AnimalSync.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insert(@RequestBody AnimalSync animal, HttpServletResponse response) {
        if(null == animal.getDocumentId()){
            throw new IllegalArgumentException("Document id must not be null.");
        }
        boolean updated = this.service.insertOrUpdate(animal, animal.getDocumentId(), animalBucket);
        if(!updated){
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/persist")
    public void persistSyncGateway() {
        this.synchronizationService.persistSyncGateway(animalBucket);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/synchronize")
    public void synchronizeSyncGateway() {
        this.synchronizationService.synchronizeSyncGateway(animalBucket);
    }
}
