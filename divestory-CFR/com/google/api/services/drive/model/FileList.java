/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.File;
import java.util.List;

public final class FileList
extends GenericJson {
    @Key
    private List<File> files;
    @Key
    private Boolean incompleteSearch;
    @Key
    private String kind;
    @Key
    private String nextPageToken;

    static {
        Data.nullOf(File.class);
    }

    @Override
    public FileList clone() {
        return (FileList)super.clone();
    }

    public List<File> getFiles() {
        return this.files;
    }

    public Boolean getIncompleteSearch() {
        return this.incompleteSearch;
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    @Override
    public FileList set(String string2, Object object) {
        return (FileList)super.set(string2, object);
    }

    public FileList setFiles(List<File> list) {
        this.files = list;
        return this;
    }

    public FileList setIncompleteSearch(Boolean bl) {
        this.incompleteSearch = bl;
        return this;
    }

    public FileList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public FileList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }
}

