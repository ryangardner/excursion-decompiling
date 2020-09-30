/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;

public abstract class StreamReadException
extends JsonProcessingException {
    static final long serialVersionUID = 1L;
    protected transient JsonParser _processor;
    protected RequestPayload _requestPayload;

    public StreamReadException(JsonParser jsonParser, String string2) {
        JsonLocation jsonLocation = jsonParser == null ? null : jsonParser.getCurrentLocation();
        super(string2, jsonLocation);
        this._processor = jsonParser;
    }

    public StreamReadException(JsonParser jsonParser, String string2, JsonLocation jsonLocation) {
        super(string2, jsonLocation, null);
        this._processor = jsonParser;
    }

    public StreamReadException(JsonParser jsonParser, String string2, Throwable throwable) {
        JsonLocation jsonLocation = jsonParser == null ? null : jsonParser.getCurrentLocation();
        super(string2, jsonLocation, throwable);
        this._processor = jsonParser;
    }

    protected StreamReadException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2);
        if (throwable != null) {
            this.initCause(throwable);
        }
        this._location = jsonLocation;
    }

    @Override
    public String getMessage() {
        String string2 = super.getMessage();
        CharSequence charSequence = string2;
        if (this._requestPayload == null) return charSequence;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("\nRequest payload : ");
        ((StringBuilder)charSequence).append(this._requestPayload.toString());
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public JsonParser getProcessor() {
        return this._processor;
    }

    public RequestPayload getRequestPayload() {
        return this._requestPayload;
    }

    public String getRequestPayloadAsString() {
        RequestPayload requestPayload = this._requestPayload;
        if (requestPayload == null) return null;
        return requestPayload.toString();
    }

    public abstract StreamReadException withParser(JsonParser var1);

    public abstract StreamReadException withRequestPayload(RequestPayload var1);
}

