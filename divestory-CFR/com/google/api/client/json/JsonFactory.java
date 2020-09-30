/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class JsonFactory {
    private ByteArrayOutputStream toByteStream(Object object, boolean bl) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = this.createJsonGenerator(byteArrayOutputStream, Charsets.UTF_8);
        if (bl) {
            jsonGenerator.enablePrettyPrint();
        }
        jsonGenerator.serialize(object);
        jsonGenerator.flush();
        return byteArrayOutputStream;
    }

    private String toString(Object object, boolean bl) throws IOException {
        return this.toByteStream(object, bl).toString("UTF-8");
    }

    public abstract JsonGenerator createJsonGenerator(OutputStream var1, Charset var2) throws IOException;

    public abstract JsonGenerator createJsonGenerator(Writer var1) throws IOException;

    public final JsonObjectParser createJsonObjectParser() {
        return new JsonObjectParser(this);
    }

    public abstract JsonParser createJsonParser(InputStream var1) throws IOException;

    public abstract JsonParser createJsonParser(InputStream var1, Charset var2) throws IOException;

    public abstract JsonParser createJsonParser(Reader var1) throws IOException;

    public abstract JsonParser createJsonParser(String var1) throws IOException;

    public final <T> T fromInputStream(InputStream inputStream2, Class<T> class_) throws IOException {
        return this.createJsonParser(inputStream2).parseAndClose(class_);
    }

    public final <T> T fromInputStream(InputStream inputStream2, Charset charset, Class<T> class_) throws IOException {
        return this.createJsonParser(inputStream2, charset).parseAndClose(class_);
    }

    public final <T> T fromReader(Reader reader, Class<T> class_) throws IOException {
        return this.createJsonParser(reader).parseAndClose(class_);
    }

    public final <T> T fromString(String string2, Class<T> class_) throws IOException {
        return this.createJsonParser(string2).parse(class_);
    }

    public final byte[] toByteArray(Object object) throws IOException {
        return this.toByteStream(object, false).toByteArray();
    }

    public final String toPrettyString(Object object) throws IOException {
        return this.toString(object, true);
    }

    public final String toString(Object object) throws IOException {
        return this.toString(object, false);
    }
}

