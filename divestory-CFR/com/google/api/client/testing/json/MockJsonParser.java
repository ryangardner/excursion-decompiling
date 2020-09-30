/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MockJsonParser
extends JsonParser {
    private final JsonFactory factory;
    private boolean isClosed;

    MockJsonParser(JsonFactory jsonFactory) {
        this.factory = jsonFactory;
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return null;
    }

    @Override
    public byte getByteValue() throws IOException {
        return 0;
    }

    @Override
    public String getCurrentName() throws IOException {
        return null;
    }

    @Override
    public JsonToken getCurrentToken() {
        return null;
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return null;
    }

    @Override
    public double getDoubleValue() throws IOException {
        return 0.0;
    }

    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getFloatValue() throws IOException {
        return 0.0f;
    }

    @Override
    public int getIntValue() throws IOException {
        return 0;
    }

    @Override
    public long getLongValue() throws IOException {
        return 0L;
    }

    @Override
    public short getShortValue() throws IOException {
        return 0;
    }

    @Override
    public String getText() throws IOException {
        return null;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    @Override
    public JsonToken nextToken() throws IOException {
        return null;
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        return null;
    }
}

