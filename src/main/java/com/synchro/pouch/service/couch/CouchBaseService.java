package com.synchro.pouch.service.couch;

import com.google.gson.JsonObject;
import com.synchro.pouch.business.sync.SyncChanges;

import java.util.Optional;

public interface CouchBaseService {

    boolean insertOrUpdate(Object obj, String documentId, String bucketName);

    Optional<JsonObject> findAsGsonObject(String documentId, String bucketName);

    <T> T findDocumentAs(String documentId, String bucketName, Class<T> clazz);

    SyncChanges getChanges(String bucketName, String sinceRevision, String channel);
}
