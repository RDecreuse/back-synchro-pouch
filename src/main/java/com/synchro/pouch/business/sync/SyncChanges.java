package com.synchro.pouch.business.sync;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SyncChanges {
    List<DocumentChange> documentChanges;
    String lastRevision;

    public SyncChanges(String lastRevision) {
        this.lastRevision = lastRevision;
        this.documentChanges = Lists.newArrayList();
    }
}
