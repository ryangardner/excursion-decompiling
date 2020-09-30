/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.services.drive.model.Reply;
import java.util.List;

public final class ReplyList
extends GenericJson {
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private List<Reply> replies;

    static {
        Data.nullOf(Reply.class);
    }

    @Override
    public ReplyList clone() {
        return (ReplyList)super.clone();
    }

    public String getKind() {
        return this.kind;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public List<Reply> getReplies() {
        return this.replies;
    }

    @Override
    public ReplyList set(String string2, Object object) {
        return (ReplyList)super.set(string2, object);
    }

    public ReplyList setKind(String string2) {
        this.kind = string2;
        return this;
    }

    public ReplyList setNextPageToken(String string2) {
        this.nextPageToken = string2;
        return this;
    }

    public ReplyList setReplies(List<Reply> list) {
        this.replies = list;
        return this;
    }
}

