/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonGenerationException
extends JsonProcessingException {
    private static final long serialVersionUID = 123L;
    protected transient JsonGenerator _processor;

    @Deprecated
    public JsonGenerationException(String string2) {
        super(string2, (JsonLocation)null);
    }

    public JsonGenerationException(String string2, JsonGenerator jsonGenerator) {
        super(string2, (JsonLocation)null);
        this._processor = jsonGenerator;
    }

    @Deprecated
    public JsonGenerationException(String string2, Throwable throwable) {
        super(string2, null, throwable);
    }

    public JsonGenerationException(String string2, Throwable throwable, JsonGenerator jsonGenerator) {
        super(string2, null, throwable);
        this._processor = jsonGenerator;
    }

    @Deprecated
    public JsonGenerationException(Throwable throwable) {
        super(throwable);
    }

    public JsonGenerationException(Throwable throwable, JsonGenerator jsonGenerator) {
        super(throwable);
        this._processor = jsonGenerator;
    }

    @Override
    public JsonGenerator getProcessor() {
        return this._processor;
    }

    public JsonGenerationException withGenerator(JsonGenerator jsonGenerator) {
        this._processor = jsonGenerator;
        return this;
    }
}

