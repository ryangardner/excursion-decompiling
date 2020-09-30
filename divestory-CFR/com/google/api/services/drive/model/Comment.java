/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Reply;
import com.google.api.services.drive.model.User;
import java.util.List;

public final class Comment
extends GenericJson {
    @Key
    private String anchor;
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
    @Key
    private QuotedFileContent quotedFileContent;
    @Key
    private List<Reply> replies;
    @Key
    private Boolean resolved;

    @Override
    public Comment clone() {
        return (Comment)super.clone();
    }

    public String getAnchor() {
        return this.anchor;
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

    public QuotedFileContent getQuotedFileContent() {
        return this.quotedFileContent;
    }

    public List<Reply> getReplies() {
        return this.replies;
    }

    public Boolean getResolved() {
        return this.resolved;
    }

    @Override
    public Comment set(String string2, Object object) {
        return (Comment)super.set(string2, object);
    }

    public Comment setAnchor(String string2) {
        this.anchor = string2;
        return this;
    }

    public Comment setAuthor(User user) {
        this.author = user;
        return this;
    }

    public Comment setContent(String string2) {
        this.content = string2;
        return this;
    }

    public Comment setCreatedTime(DateTime dateTime) {
        this.createdTime = dateTime;
        return this;
    }

    public Comment setDeleted(Boolean bl) {
        this.deleted = bl;
        return this;
    }

    public Comment setHtmlContent(String string2) {
        this.htmlContent = string2;
        return this;
    }

    public Comment setId(String string2) {
        this.id = string2;
        return this;
    }

    public Comment setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public Comment setModifiedTime(DateTime dateTime) {
        this.modifiedTime = dateTime;
        return this;
    }

    public Comment setQuotedFileContent(QuotedFileContent quotedFileContent) {
        this.quotedFileContent = quotedFileContent;
        return this;
    }

    public Comment setReplies(List<Reply> list) {
        this.replies = list;
        return this;
    }

    public Comment setResolved(Boolean bl) {
        this.resolved = bl;
        return this;
    }

    public static final class QuotedFileContent
    extends GenericJson {
        @Key
        private String mimeType;
        @Key
        private String value;

        @Override
        public QuotedFileContent clone() {
            return (QuotedFileContent)super.clone();
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public QuotedFileContent set(String string2, Object object) {
            return (QuotedFileContent)super.set(string2, object);
        }

        public QuotedFileContent setMimeType(String string2) {
            this.mimeType = string2;
            return this;
        }

        public QuotedFileContent setValue(String string2) {
            this.value = string2;
            return this;
        }
    }

}

