package com.synchro.pouch.service.couch;

public interface SynchronizationService {
    void synchronizeSyncGateway(String bucketName);

    void persistSyncGateway(String bucketName);
}
