/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.jackson2;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.json.jackson2.JacksonGenerator;
import com.google.api.client.json.jackson2.JacksonParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public final class JacksonFactory
extends JsonFactory {
    private final com.fasterxml.jackson.core.JsonFactory factory;

    public JacksonFactory() {
        com.fasterxml.jackson.core.JsonFactory jsonFactory;
        this.factory = jsonFactory = new com.fasterxml.jackson.core.JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
    }

    static JsonToken convert(com.fasterxml.jackson.core.JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        switch (1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
            default: {
                return JsonToken.NOT_AVAILABLE;
            }
            case 11: {
                return JsonToken.FIELD_NAME;
            }
            case 10: {
                return JsonToken.VALUE_NUMBER_INT;
            }
            case 9: {
                return JsonToken.VALUE_NUMBER_FLOAT;
            }
            case 8: {
                return JsonToken.VALUE_STRING;
            }
            case 7: {
                return JsonToken.VALUE_NULL;
            }
            case 6: {
                return JsonToken.VALUE_TRUE;
            }
            case 5: {
                return JsonToken.VALUE_FALSE;
            }
            case 4: {
                return JsonToken.START_OBJECT;
            }
            case 3: {
                return JsonToken.END_OBJECT;
            }
            case 2: {
                return JsonToken.START_ARRAY;
            }
            case 1: 
        }
        return JsonToken.END_ARRAY;
    }

    public static JacksonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public JsonGenerator createJsonGenerator(OutputStream outputStream2, Charset charset) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(outputStream2, JsonEncoding.UTF8));
    }

    @Override
    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(writer));
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        return new JacksonParser(this, this.factory.createJsonParser(inputStream2));
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2, Charset charset) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        return new JacksonParser(this, this.factory.createJsonParser(inputStream2));
    }

    @Override
    public JsonParser createJsonParser(Reader reader) throws IOException {
        Preconditions.checkNotNull(reader);
        return new JacksonParser(this, this.factory.createJsonParser(reader));
    }

    @Override
    public JsonParser createJsonParser(String string2) throws IOException {
        Preconditions.checkNotNull(string2);
        return new JacksonParser(this, this.factory.createJsonParser(string2));
    }

    static class InstanceHolder {
        static final JacksonFactory INSTANCE = new JacksonFactory();

        InstanceHolder() {
        }
    }

}

