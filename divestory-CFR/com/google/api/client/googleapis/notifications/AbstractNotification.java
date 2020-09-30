/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;

public abstract class AbstractNotification {
    private String changed;
    private String channelExpiration;
    private String channelId;
    private String channelToken;
    private long messageNumber;
    private String resourceId;
    private String resourceState;
    private String resourceUri;

    protected AbstractNotification(long l, String string2, String string3, String string4, String string5) {
        this.setMessageNumber(l);
        this.setResourceState(string2);
        this.setResourceId(string3);
        this.setResourceUri(string4);
        this.setChannelId(string5);
    }

    protected AbstractNotification(AbstractNotification abstractNotification) {
        this(abstractNotification.getMessageNumber(), abstractNotification.getResourceState(), abstractNotification.getResourceId(), abstractNotification.getResourceUri(), abstractNotification.getChannelId());
        this.setChannelExpiration(abstractNotification.getChannelExpiration());
        this.setChannelToken(abstractNotification.getChannelToken());
        this.setChanged(abstractNotification.getChanged());
    }

    public final String getChanged() {
        return this.changed;
    }

    public final String getChannelExpiration() {
        return this.channelExpiration;
    }

    public final String getChannelId() {
        return this.channelId;
    }

    public final String getChannelToken() {
        return this.channelToken;
    }

    public final long getMessageNumber() {
        return this.messageNumber;
    }

    public final String getResourceId() {
        return this.resourceId;
    }

    public final String getResourceState() {
        return this.resourceState;
    }

    public final String getResourceUri() {
        return this.resourceUri;
    }

    public AbstractNotification setChanged(String string2) {
        this.changed = string2;
        return this;
    }

    public AbstractNotification setChannelExpiration(String string2) {
        this.channelExpiration = string2;
        return this;
    }

    public AbstractNotification setChannelId(String string2) {
        this.channelId = Preconditions.checkNotNull(string2);
        return this;
    }

    public AbstractNotification setChannelToken(String string2) {
        this.channelToken = string2;
        return this;
    }

    public AbstractNotification setMessageNumber(long l) {
        boolean bl = l >= 1L;
        Preconditions.checkArgument(bl);
        this.messageNumber = l;
        return this;
    }

    public AbstractNotification setResourceId(String string2) {
        this.resourceId = Preconditions.checkNotNull(string2);
        return this;
    }

    public AbstractNotification setResourceState(String string2) {
        this.resourceState = Preconditions.checkNotNull(string2);
        return this;
    }

    public AbstractNotification setResourceUri(String string2) {
        this.resourceUri = Preconditions.checkNotNull(string2);
        return this;
    }

    public String toString() {
        return this.toStringHelper().toString();
    }

    protected Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this).add("messageNumber", this.messageNumber).add("resourceState", this.resourceState).add("resourceId", this.resourceId).add("resourceUri", this.resourceUri).add("channelId", this.channelId).add("channelExpiration", this.channelExpiration).add("channelToken", this.channelToken).add("changed", this.changed);
    }
}

