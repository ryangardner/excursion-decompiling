/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.User;

public final class Reply
extends GenericJson {
    @Key
    private String action;
    @Key
    private User author;
    @Key
    private String content;
    @Key
    private DateTime createdTime;
    @Key
    private Boolean deleted;
    @Key
    private String htmlContent;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private DateTime modifiedTime;

    @Override
    public Reply clone() {
        return (Reply)super.clone();
    }

    public String getAction() {
        return this.action;
    }

    public User getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public DateTime getCreatedTime() {
        return this.createdTime;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public DateTime getModifiedTime() {
        return this.modifiedTime;
    }

    @Override
    public Reply set(String string2, Object object) {
        return (Reply)super.set(string2, object);
    }

    public Reply setAction(String string2) {
        this.action = string2;
        return this;
    }

    public Reply setAuthor(User user) {
        this.author = user;
        return this;
    }

    public Reply setContent(String string2) {
        this.content = string2;
        return this;
    }

    public Reply setCreatedTime(DateTime dateTime) {
        this.createdTime = dateTime;
        return this;
    }

    public Reply setDeleted(Boolean bl) {
        this.deleted = bl;
        return this;
    }

    public Reply setHtmlContent(String string2) {
        this.htmlContent = string2;
        return this;
    }

    public Reply setId(String string2) {
        this.id = string2;
        return this;
    }

    public Reply setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Reply setModifiedTime(DateTime dateTime) {
        this.modifiedTime = dateTime;
        return this;
    }
}

