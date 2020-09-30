/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Comment;
import java.util.List;

public final class CommentList
extends GenericJson {
    @Key
    private List<Comment> comments;
    @Key
    private String kind;
    @Key
    private String nextPageToken;

    static {
        Data.nullOf(Comment.class);
    }

    @Override
    public CommentList clone() {
        return (CommentList)super.clone();
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    @Override
    public CommentList set(String string2, Object object) {
        return (CommentList)super.set(string2, object);
    }

    public CommentList setComments(List<Comment> list) {
        this.comments = list;
        return this;
    }

    public CommentList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public CommentList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }
}

