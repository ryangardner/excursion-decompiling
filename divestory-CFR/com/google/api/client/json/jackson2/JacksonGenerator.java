/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class JacksonGenerator
extends JsonGenerator {
    private final JacksonFactory factory;
    private final com.fasterxml.jackson.core.JsonGenerator generator;

    JacksonGenerator(JacksonFactory jacksonFactory, com.fasterxml.jackson.core.JsonGenerator jsonGenerator) {
        this.factory = jacksonFactory;
        this.generator = jsonGenerator;
    }

    @Override
    public void close() throws IOException {
        this.generator.close();
    }

    @Override
    public void enablePrettyPrint() throws IOException {
        this.generator.useDefaultPrettyPrinter();
    }

    @Override
    public void flush() throws IOException {
        this.generator.flush();
    }

    @Override
    public JacksonFactory getFactory() {
        return this.factory;
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.generator.writeBoolean(bl);
    }

    @Override
    public void writeEndArray() throws IOException {
        this.generator.writeEndArray();
    }

    @Override
    public void writeEndObject() throws IOException {
        this.generator.writeEndObject();
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
        this.generator.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException {
        this.generator.writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        this.generator.writeNumber(d);
    }

    @Override
    public void writeNumber(float f) throws IOException {
        this.generator.writeNumber(f);
    }

    @Override
    public void writeNumber(int n) throws IOException {
        this.generator.writeNumber(n);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        this.generator.writeNumber(l);
    }

    @Override
    public void writeNumber(String string2) throws IOException {
        this.generator.writeNumber(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this.generator.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this.generator.writeNumber(bigInteger);
    }

    @Override
    public void writeStartArray() throws IOException {
        this.generator.writeStartArray();
    }

    @Override
    public void writeStartObject() throws IOException {
        this.generator.writeStartObject();
    }

    @Override
    public void writeString(String string2) throws IOException {
        this.generator.writeString(string2);
    }
}

