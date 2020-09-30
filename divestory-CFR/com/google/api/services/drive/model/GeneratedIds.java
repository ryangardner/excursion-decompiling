/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.util.List;

public final class GeneratedIds
extends GenericJson {
    @Key
    private List<String> ids;
    @Key
    private String kind;
    @Key
    private String space;

    @Override
    public GeneratedIds clone() {
        return (GeneratedIds)super.clone();
    }

    public List<String> getIds() {
        return this.ids;
    }

    public String getKind() {
        return this.kind;
    }

    public String getSpace() {
        return this.space;
    }

    @Override
    public GeneratedIds set(String string2, Object object) {
        return (GeneratedIds)super.set(string2, object);
    }

    public GeneratedIds setIds(List<String> list) {
        this.ids = list;
        return this;
    }

    public GeneratedIds setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public GeneratedIds setSpace(String string2) {
        this.space = string2;
        return this;
    }
}

