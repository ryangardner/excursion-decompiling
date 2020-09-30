/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Revision;
import java.util.List;

public final class RevisionList
extends GenericJson {
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private List<Revision> revisions;

    static {
        Data.nullOf(Revision.class);
    }

    @Override
    public RevisionList clone() {
        return (RevisionList)super.clone();
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<Revision> getRevisions() {
        return this.revisions;
    }

    @Override
    public RevisionList set(String string2, Object object) {
        return (RevisionList)super.set(string2, object);
    }

    public RevisionList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public RevisionList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }

    public RevisionList setRevisions(List<Revision> list) {
        this.revisions = list;
        return this;
    }
}

