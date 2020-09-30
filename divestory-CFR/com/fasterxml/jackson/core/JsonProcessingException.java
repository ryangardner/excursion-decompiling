/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import java.io.IOException;

public class JsonProcessingException
extends IOException {
    static final long serialVersionUID = 123L;
    protected JsonLocation _location;

    protected JsonProcessingException(String string2) {
        super(string2);
    }

    protected JsonProcessingException(String string2, JsonLocation jsonLocation) {
        this(string2, jsonLocation, null);
    }

    protected JsonProcessingException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2);
        if (throwable != null) {
            this.initCause(throwable);
        }
        this._location = jsonLocation;
    }

    protected JsonProcessingException(String string2, Throwable throwable) {
        this(string2, null, throwable);
    }

    protected JsonProcessingException(Throwable throwable) {
        this(null, null, throwable);
    }

    public void clearLocation() {
        this._location = null;
    }

    public JsonLocation getLocation() {
        return this._location;
    }

    @Override
    public String getMessage() {
        CharSequence charSequence = super.getMessage();
        String string2 = charSequence;
        if (charSequence == null) {
            string2 = "N/A";
        }
        JsonLocation jsonLocation = this.getLocation();
        String string3 = this.getMessageSuffix();
        if (jsonLocation == null) {
            charSequence = string2;
            if (string3 == null) return charSequence;
        }
        charSequence = new StringBuilder(100);
        ((StringBuilder)charSequence).append(string2);
        if (string3 != null) {
            ((StringBuilder)charSequence).append(string3);
        }
        if (jsonLocation == null) return ((StringBuilder)charSequence).toString();
        ((StringBuilder)charSequence).append('\n');
        ((StringBuilder)charSequence).append(" at ");
        ((StringBuilder)charSequence).append(jsonLocation.toString());
        return ((StringBuilder)charSequence).toString();
    }

    protected String getMessageSuffix() {
        return null;
    }

    public String getOriginalMessage() {
        return super.getMessage();
    }

    public Object getProcessor() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(": ");
        stringBuilder.append(this.getMessage());
        return stringBuilder.toString();
    }
}

