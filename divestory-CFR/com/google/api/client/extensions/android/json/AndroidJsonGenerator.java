/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.JsonWriter
 */
package com.google.api.client.extensions.android.json;

import android.util.JsonWriter;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

class AndroidJsonGenerator
extends JsonGenerator {
    private final AndroidJsonFactory factory;
    private final JsonWriter writer;

    AndroidJsonGenerator(AndroidJsonFactory androidJsonFactory, JsonWriter jsonWriter) {
        this.factory = androidJsonFactory;
        this.writer = jsonWriter;
        jsonWriter.setLenient(true);
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    @Override
    public void enablePrettyPrint() throws IOException {
        this.writer.setIndent("  ");
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.writer.value(bl);
    }

    @Override
    public void writeEndArray() throws IOException {
        this.writer.endArray();
    }

    @Override
    public void writeEndObject() throws IOException {
        this.writer.endObject();
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
        this.writer.name(string2);
    }

    @Override
    public void writeNull() throws IOException {
        this.writer.nullValue();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        this.writer.value(d);
    }

    @Override
    public void writeNumber(float f) throws IOException {
        this.writer.value((double)f);
    }

    @Override
    public void writeNumber(int n) throws IOException {
        this.writer.value((long)n);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        this.writer.value(l);
    }

    @Override
    public void writeNumber(String string2) throws IOException {
        this.writer.value((Number)new StringNumber(string2));
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this.writer.value((Number)bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this.writer.value((Number)bigInteger);
    }

    @Override
    public void writeStartArray() throws IOException {
        this.writer.beginArray();
    }

    @Override
    public void writeStartObject() throws IOException {
        this.writer.beginObject();
    }

    @Override
    public void writeString(String string2) throws IOException {
        this.writer.value(string2);
    }

    static final class StringNumber
    extends Number {
        private static final long serialVersionUID = 1L;
        private final String encodedValue;

        StringNumber(String string2) {
            this.encodedValue = string2;
        }

        @Override
        public double doubleValue() {
            return 0.0;
        }

        @Override
        public float floatValue() {
            return 0.0f;
        }

        @Override
        public int intValue() {
            return 0;
        }

        @Override
        public long longValue() {
            return 0L;
        }

        public String toString() {
            return this.encodedValue;
        }
    }

}

