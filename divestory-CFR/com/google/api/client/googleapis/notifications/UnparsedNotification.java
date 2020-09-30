/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.googleapis.notifications.AbstractNotification;
import com.google.api.client.util.Objects;
import java.io.InputStream;

public class UnparsedNotification
extends AbstractNotification {
    private InputStream contentStream;
    private String contentType;

    public UnparsedNotification(long l, String string2, String string3, String string4, String string5) {
        super(l, string2, string3, string4, string5);
    }

    public final InputStream getContentStream() {
        return this.contentStream;
    }

    public final String getContentType() {
        return this.contentType;
    }

    @Override
    public UnparsedNotification setChanged(String string2) {
        return (UnparsedNotification)super.setChanged(string2);
    }

    @Override
    public UnparsedNotification setChannelExpiration(String string2) {
        return (UnparsedNotification)super.setChannelExpiration(string2);
    }

    @Override
    public UnparsedNotification setChannelId(String string2) {
        return (UnparsedNotification)super.setChannelId(string2);
    }

    @Override
    public UnparsedNotification setChannelToken(String string2) {
        return (UnparsedNotification)super.setChannelToken(string2);
    }

    public UnparsedNotification setContentStream(InputStream inputStream2) {
        this.contentStream = inputStream2;
        return this;
    }

    public UnparsedNotification setContentType(String string2) {
        this.contentType = string2;
        return this;
    }

    @Override
    public UnparsedNotification setMessageNumber(long l) {
        return (UnparsedNotification)super.setMessageNumber(l);
    }

    @Override
    public UnparsedNotification setResourceId(String string2) {
        return (UnparsedNotification)super.setResourceId(string2);
    }

    @Override
    public UnparsedNotification setResourceState(String string2) {
        return (UnparsedNotification)super.setResourceState(string2);
    }

    @Override
    public UnparsedNotification setResourceUri(String string2) {
        return (UnparsedNotification)super.setResourceUri(string2);
    }

    @Override
    public String toString() {
        return super.toStringHelper().add("contentType", this.contentType).toString();
    }
}

