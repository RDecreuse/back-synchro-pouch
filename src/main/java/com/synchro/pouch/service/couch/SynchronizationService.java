package com.synchro.pouch.service.couch;

public interface SynchronizationService {
    void synchronizeSyncGateway();

    void persistSyncGateway(String bucketName);
}
