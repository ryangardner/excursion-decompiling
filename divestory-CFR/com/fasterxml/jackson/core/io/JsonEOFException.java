/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonEOFException
extends JsonParseException {
    private static final long serialVersionUID = 1L;
    protected final JsonToken _token;

    public JsonEOFException(JsonParser jsonParser, JsonToken jsonToken, String string2) {
        super(jsonParser, string2);
        this._token = jsonToken;
    }

    public JsonToken getTokenBeingDecoded() {
        return this._token;
    }
}
