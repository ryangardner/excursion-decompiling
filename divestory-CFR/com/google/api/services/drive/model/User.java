/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public final class User
extends GenericJson {
    @Key
    private String displayName;
    @Key
    private String emailAddress;
    @Key
    private String kind;
    @Key
    private Boolean me;
    @Key
    private String permissionId;
    @Key
    private String photoLink;

    @Override
    public User clone() {
        return (User)super.clone();
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getKind() {
        return this.kind;
    }

    public Boolean getMe() {
        return this.me;
    }

    public String getPermissionId() {
        return this.permissionId;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    @Override
    public User set(String string2, Object object) {
        return (User)super.set(string2, object);
    }

    public User setDisplayName(String string2) {
        this.displayName = string2;
        return this;
    }

    public User setEmailAddress(String string2) {
        this.emailAddress = string2;
        return this;
    }

    public User setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public User setMe(Boolean bl) {
        this.me = bl;
        return this;
    }

    public User setPermissionId(String string2) {
        this.permissionId = string2;
        return this;
    }

    public User setPhotoLink(String string2) {
        this.photoLink = string2;
        return this;
    }
}

