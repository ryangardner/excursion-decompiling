/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.testing.json.MockJsonGenerator;
import com.google.api.client.testing.json.MockJsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class MockJsonFactory
extends JsonFactory {
    @Override
    public JsonGenerator createJsonGenerator(OutputStream outputStream2, Charset charset) throws IOException {
        return new MockJsonGenerator(this);
    }

    @Override
    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return new MockJsonGenerator(this);
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2) throws IOException {
        return new MockJsonParser(this);
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2, Charset charset) throws IOException {
        return new MockJsonParser(this);
    }

    @Override
    public JsonParser createJsonParser(Reader reader) throws IOException {
        return new MockJsonParser(this);
    }

    @Override
    public JsonParser createJsonParser(String string2) throws IOException {
        return new MockJsonParser(this);
    }
}

