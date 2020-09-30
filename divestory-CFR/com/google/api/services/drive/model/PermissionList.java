/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Permission;
import java.util.List;

public final class PermissionList
extends GenericJson {
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private List<Permission> permissions;

    static {
        Data.nullOf(Permission.class);
    }

    @Override
    public PermissionList clone() {
        return (PermissionList)super.clone();
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    @Override
    public PermissionList set(String string2, Object object) {
        return (PermissionList)super.set(string2, object);
    }

    public PermissionList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public PermissionList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }

    public PermissionList setPermissions(List<Permission> list) {
        this.permissions = list;
        return this;
    }
}

