package com.synchro.pouch.repository;

import com.synchro.pouch.business.sync.SyncParams;
import org.springframework.data.repository.CrudRepository;

public interface SyncParamsDao extends CrudRepository<SyncParams, Long> {

    SyncParams findByBucket(String bucket);

}
