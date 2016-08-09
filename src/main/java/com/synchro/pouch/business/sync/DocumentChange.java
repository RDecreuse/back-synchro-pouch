package com.synchro.pouch.business.sync;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentChange {
    private String id;
    private String seq;
    private String revision;
}
