package com.synchro.pouch.service.couch;

import com.synchro.pouch.business.Animal;
import com.synchro.pouch.business.dto.AnimalSync;
import com.synchro.pouch.business.sync.DocumentChange;
import com.synchro.pouch.business.sync.SyncChanges;
import com.synchro.pouch.business.sync.SyncParams;
import com.synchro.pouch.repository.SyncParamsDao;
import com.synchro.pouch.service.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SynchronizationServiceImpl implements SynchronizationService {

    @Autowired
    private CouchBaseService couchBaseService;

    @Value("${url.syncgateway.animal.channel}")
    private String animalChannel;

    @Autowired
    private AnimalService animalService;


    @Autowired
    private SyncParamsDao syncParamsDao;

    @Override
    public void synchronizeSyncGateway() {
        //WIP
    }

    @Override
    public void persistSyncGateway(String bucketName) {
        LOGGER.info("Synchronization start for bucket {} and channel {}", bucketName, animalChannel);

        SyncParams syncParams = syncParamsDao.findByBucket(bucketName);
        if(syncParams == null){
            throw new InvalidStateException("Synchronization params must be initialized for each bucket.");
        }
        LOGGER.info("Synchronization params for bucket {} : last revision={} and date={}",
                bucketName, syncParams.getRev(), syncParams.getDate());

        SyncChanges changes = couchBaseService.getChanges(bucketName, syncParams.getRev(), animalChannel);

        List<DocumentChange> documentChanges = changes.getDocumentChanges();
        LOGGER.info("New animal found={}", documentChanges.size());
        for (DocumentChange documentChange : documentChanges) {
            AnimalSync newAnimalSync = couchBaseService.findDocumentAs(documentChange.getId(), bucketName, AnimalSync.class);
            Animal newAnimal = newAnimalSync.toAnimal();
            animalService.create(newAnimal);
            LOGGER.info("New animal created with id={}", newAnimal.getId());
        }

        syncParams.setRev(changes.getLastRevision());
        syncParams.setDate(new Date());
        syncParamsDao.save(syncParams);

        LOGGER.info("Synchronization ending for bucket {} and channel {}. Last revision={}",
                bucketName, animalChannel, syncParams.getRev());
    }
}
