package com.synchro.pouch.service.couch;

import com.google.gson.JsonObject;

public interface CouchBaseService {

    boolean insertOrUpdate(Object obj, String documentId, String bucketName);

    JsonObject findAsGsonObject(String documentId, String bucketName);

    <T> T findDocumentAs(String documentId, String bucketName, Class<T> clazz);
}
