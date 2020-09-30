/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.json;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.util.Preconditions;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class JsonHttpContent
extends AbstractHttpContent {
    private final Object data;
    private final JsonFactory jsonFactory;
    private String wrapperKey;

    public JsonHttpContent(JsonFactory jsonFactory, Object object) {
        super("application/json; charset=UTF-8");
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        this.data = Preconditions.checkNotNull(object);
    }

    public final Object getData() {
        return this.data;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getWrapperKey() {
        return this.wrapperKey;
    }

    @Override
    public JsonHttpContent setMediaType(HttpMediaType httpMediaType) {
        super.setMediaType(httpMediaType);
        return this;
    }

    public JsonHttpContent setWrapperKey(String string2) {
        this.wrapperKey = string2;
        return this;
    }

    @Override
    public void writeTo(OutputStream closeable) throws IOException {
        closeable = this.jsonFactory.createJsonGenerator((OutputStream)closeable, this.getCharset());
        if (this.wrapperKey != null) {
            ((JsonGenerator)closeable).writeStartObject();
            ((JsonGenerator)closeable).writeFieldName(this.wrapperKey);
        }
        ((JsonGenerator)closeable).serialize(this.data);
        if (this.wrapperKey != null) {
            ((JsonGenerator)closeable).writeEndObject();
        }
        ((JsonGenerator)closeable).flush();
    }
}

