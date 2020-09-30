/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.User;

public final class Revision
extends GenericJson {
    @Key
    private String id;
    @Key
    private Boolean keepForever;
    @Key
    private String kind;
    @Key
    private User lastModifyingUser;
    @Key
    private String md5Checksum;
    @Key
    private String mimeType;
    @Key
    private DateTime modifiedTime;
    @Key
    private String originalFilename;
    @Key
    private Boolean publishAuto;
    @Key
    private Boolean published;
    @Key
    private Boolean publishedOutsideDomain;
    @JsonString
    @Key
    private Long size;

    @Override
    public Revision clone() {
        return (Revision)super.clone();
    }

    public String getId() {
        return this.id;
    }

    public Boolean getKeepForever() {
        return this.keepForever;
    }

    public String getKind() {
        return this.kind;
    }

    public User getLastModifyingUser() {
        return this.lastModifyingUser;
    }

    public String getMd5Checksum() {
        return this.md5Checksum;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public DateTime getModifiedTime() {
        return this.modifiedTime;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public Boolean getPublishAuto() {
        return this.publishAuto;
    }

    public Boolean getPublished() {
        return this.published;
    }

    public Boolean getPublishedOutsideDomain() {
        return this.publishedOutsideDomain;
    }

    public Long getSize() {
        return this.size;
    }

    @Override
    public Revision set(String string2, Object object) {
        return (Revision)super.set(string2, object);
    }

    public Revision setId(String string2) {
        this.id = string2;
        return this;
    }

    public Revision setKeepForever(Boolean bl) {
        this.keepForever = bl;
        return this;
    }

    public Revision setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Revision setLastModifyingUser(User user) {
        this.lastModifyingUser = user;
        return this;
    }

    public Revision setMd5Checksum(String string2) {
        this.md5Checksum = string2;
        return this;
    }

    public Revision setMimeType(String string2) {
        this.mimeType = string2;
        return this;
    }

    public Revision setModifiedTime(DateTime dateTime) {
        this.modifiedTime = dateTime;
        return this;
    }

    public Revision setOriginalFilename(String string2) {
        this.originalFilename = string2;
        return this;
    }

    public Revision setPublishAuto(Boolean bl) {
        this.publishAuto = bl;
        return this;
    }

    public Revision setPublished(Boolean bl) {
        this.published = bl;
        return this;
    }

    public Revision setPublishedOutsideDomain(Boolean bl) {
        this.publishedOutsideDomain = bl;
        return this;
    }

    public Revision setSize(Long l) {
        this.size = l;
        return this;
    }
}

