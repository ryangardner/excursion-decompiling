/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.util.RequestPayload;

public class JsonParseException
extends StreamReadException {
    private static final long serialVersionUID = 2L;

    public JsonParseException(JsonParser jsonParser, String string2) {
        super(jsonParser, string2);
    }

    public JsonParseException(JsonParser jsonParser, String string2, JsonLocation jsonLocation) {
        super(jsonParser, string2, jsonLocation);
    }

    public JsonParseException(JsonParser jsonParser, String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2, jsonLocation, throwable);
    }

    public JsonParseException(JsonParser jsonParser, String string2, Throwable throwable) {
        super(jsonParser, string2, throwable);
    }

    @Deprecated
    public JsonParseException(String string2, JsonLocation jsonLocation) {
        super(string2, jsonLocation, null);
    }

    @Deprecated
    public JsonParseException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2, jsonLocation, throwable);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public JsonParser getProcessor() {
        return super.getProcessor();
    }

    @Override
    public RequestPayload getRequestPayload() {
        return super.getRequestPayload();
    }

    @Override
    public String getRequestPayloadAsString() {
        return super.getRequestPayloadAsString();
    }

    @Override
    public JsonParseException withParser(JsonParser jsonParser) {
        this._processor = jsonParser;
        return this;
    }

    @Override
    public JsonParseException withRequestPayload(RequestPayload requestPayload) {
        this._requestPayload = requestPayload;
        return this;
    }
}

