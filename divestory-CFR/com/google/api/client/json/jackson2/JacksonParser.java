/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class JacksonParser
extends JsonParser {
    private final JacksonFactory factory;
    private final com.fasterxml.jackson.core.JsonParser parser;

    JacksonParser(JacksonFactory jacksonFactory, com.fasterxml.jackson.core.JsonParser jsonParser) {
        this.factory = jacksonFactory;
        this.parser = jsonParser;
    }

    @Override
    public void close() throws IOException {
        this.parser.close();
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.parser.getBigIntegerValue();
    }

    @Override
    public byte getByteValue() throws IOException {
        return this.parser.getByteValue();
    }

    @Override
    public String getCurrentName() throws IOException {
        return this.parser.getCurrentName();
    }

    @Override
    public JsonToken getCurrentToken() {
        return JacksonFactory.convert(this.parser.getCurrentToken());
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.parser.getDecimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException {
        return this.parser.getDoubleValue();
    }

    @Override
    public JacksonFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getFloatValue() throws IOException {
        return this.parser.getFloatValue();
    }

    @Override
    public int getIntValue() throws IOException {
        return this.parser.getIntValue();
    }

    @Override
    public long getLongValue() throws IOException {
        return this.parser.getLongValue();
    }

    @Override
    public short getShortValue() throws IOException {
        return this.parser.getShortValue();
    }

    @Override
    public String getText() throws IOException {
        return this.parser.getText();
    }

    @Override
    public JsonToken nextToken() throws IOException {
        return JacksonFactory.convert(this.parser.nextToken());
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        this.parser.skipChildren();
        return this;
    }
}

