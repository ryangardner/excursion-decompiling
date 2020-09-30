/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.notifications;

import com.google.api.client.googleapis.notifications.AbstractNotification;
import com.google.api.client.googleapis.notifications.UnparsedNotification;
import com.google.api.client.util.Objects;

public class TypedNotification<T>
extends AbstractNotification {
    private T content;

    public TypedNotification(long l, String string2, String string3, String string4, String string5) {
        super(l, string2, string3, string4, string5);
    }

    public TypedNotification(UnparsedNotification unparsedNotification) {
        super(unparsedNotification);
    }

    public final T getContent() {
        return this.content;
    }

    @Override
    public TypedNotification<T> setChanged(String string2) {
        return (TypedNotification)super.setChanged(string2);
    }

    @Override
    public TypedNotification<T> setChannelExpiration(String string2) {
        return (TypedNotification)super.setChannelExpiration(string2);
    }

    @Override
    public TypedNotification<T> setChannelId(String string2) {
        return (TypedNotification)super.setChannelId(string2);
    }

    @Override
    public TypedNotification<T> setChannelToken(String string2) {
        return (TypedNotification)super.setChannelToken(string2);
    }

    public TypedNotification<T> setContent(T t) {
        this.content = t;
        return this;
    }

    @Override
    public TypedNotification<T> setMessageNumber(long l) {
        return (TypedNotification)super.setMessageNumber(l);
    }

    @Override
    public TypedNotification<T> setResourceId(String string2) {
        return (TypedNotification)super.setResourceId(string2);
    }

    @Override
    public TypedNotification<T> setResourceState(String string2) {
        return (TypedNotification)super.setResourceState(string2);
    }

    @Override
    public TypedNotification<T> setResourceUri(String string2) {
        return (TypedNotification)super.setResourceUri(string2);
    }

    @Override
    public String toString() {
        return super.toStringHelper().add("content", this.content).toString();
    }
}

