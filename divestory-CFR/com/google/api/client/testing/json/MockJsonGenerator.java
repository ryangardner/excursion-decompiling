/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MockJsonGenerator
extends JsonGenerator {
    private final JsonFactory factory;

    MockJsonGenerator(JsonFactory jsonFactory) {
        this.factory = jsonFactory;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
    }

    @Override
    public void writeEndArray() throws IOException {
    }

    @Override
    public void writeEndObject() throws IOException {
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
    }

    @Override
    public void writeNull() throws IOException {
    }

    @Override
    public void writeNumber(double d) throws IOException {
    }

    @Override
    public void writeNumber(float f) throws IOException {
    }

    @Override
    public void writeNumber(int n) throws IOException {
    }

    @Override
    public void writeNumber(long l) throws IOException {
    }

    @Override
    public void writeNumber(String string2) throws IOException {
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
    }

    @Override
    public void writeStartArray() throws IOException {
    }

    @Override
    public void writeStartObject() throws IOException {
    }

    @Override
    public void writeString(String string2) throws IOException {
    }
}

