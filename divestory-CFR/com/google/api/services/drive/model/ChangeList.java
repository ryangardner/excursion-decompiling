/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Change;
import java.util.List;

public final class ChangeList
extends GenericJson {
    @Key
    private List<Change> changes;
    @Key
    private String kind;
    @Key
    private String newStartPageToken;
    @Key
    private String nextPageToken;

    static {
        Data.nullOf(Change.class);
    }

    @Override
    public ChangeList clone() {
        return (ChangeList)super.clone();
    }

    public List<Change> getChanges() {
        return this.changes;
    }

    public String getKind() {
        return this.kind;
    }

    public String getNewStartPageToken() {
        return this.newStartPageToken;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    @Override
    public ChangeList set(String string2, Object object) {
        return (ChangeList)super.set(string2, object);
    }

    public ChangeList setChanges(List<Change> list) {
        this.changes = list;
        return this;
    }

    public ChangeList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public ChangeList setNewStartPageToken(String string2) {
        this.newStartPageToken = string2;
        return this;
    }

    public ChangeList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }
}

