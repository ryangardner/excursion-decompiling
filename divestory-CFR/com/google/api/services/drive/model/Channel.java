/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Channel
extends GenericJson {
    @Key
    private String address;
    @JsonString
    @Key
    private Long expiration;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private Map<String, String> params;
    @Key
    private Boolean payload;
    @Key
    private String resourceId;
    @Key
    private String resourceUri;
    @Key
    private String token;
    @Key
    private String type;

    @Override
    public Channel clone() {
        return (Channel)super.clone();
    }

    public String getAddress() {
        return this.address;
    }

    public Long getExpiration() {
        return this.expiration;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public Boolean getPayload() {
        return this.payload;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public String getResourceUri() {
        return this.resourceUri;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public Channel set(String string2, Object object) {
        return (Channel)super.set(string2, object);
    }

    public Channel setAddress(String string2) {
        this.address = string2;
        return this;
    }

    public Channel setExpiration(Long l) {
        this.expiration = l;
        return this;
    }

    public Channel setId(String string2) {
        this.id = string2;
        return this;
    }

    public Channel setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Channel setParams(Map<String, String> map) {
        this.params = map;
        return this;
    }

    public Channel setPayload(Boolean bl) {
        this.payload = bl;
        return this;
    }

    public Channel setResourceId(String string2) {
        this.resourceId = string2;
        return this;
    }

    public Channel setResourceUri(String string2) {
        this.resourceUri = string2;
        return this;
    }

    public Channel setToken(String string2) {
        this.token = string2;
        return this;
    }

    public Channel setType(String string2) {
        this.type = string2;
        return this;
    }
}

