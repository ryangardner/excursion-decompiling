/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public final class StartPageToken
extends GenericJson {
    @Key
    private String kind;
    @Key
    private String startPageToken;

    @Override
    public StartPageToken clone() {
        return (StartPageToken)super.clone();
    }

    public String getKind() {
        return this.kind;
    }

    public String getStartPageToken() {
        return this.startPageToken;
    }

    @Override
    public StartPageToken set(String string2, Object object) {
        return (StartPageToken)super.set(string2, object);
    }

    public StartPageToken setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public StartPageToken setStartPageToken(String string2) {
        this.startPageToken = string2;
        return this;
    }
}

