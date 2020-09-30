/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.JsonReader
 *  android.util.JsonWriter
 */
package com.google.api.client.extensions.android.json;

import android.util.JsonReader;
import android.util.JsonWriter;
import com.google.api.client.extensions.android.AndroidUtils;
import com.google.api.client.extensions.android.json.AndroidJsonGenerator;
import com.google.api.client.extensions.android.json.AndroidJsonParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Charsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

public class AndroidJsonFactory
extends JsonFactory {
    public AndroidJsonFactory() {
        AndroidUtils.checkMinimumSdkLevel(11);
    }

    public static AndroidJsonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public JsonGenerator createJsonGenerator(OutputStream outputStream2, Charset charset) {
        return this.createJsonGenerator(new OutputStreamWriter(outputStream2, charset));
    }

    @Override
    public JsonGenerator createJsonGenerator(Writer writer) {
        return new AndroidJsonGenerator(this, new JsonWriter(writer));
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2) {
        return this.createJsonParser(new InputStreamReader(inputStream2, Charsets.UTF_8));
    }

    @Override
    public JsonParser createJsonParser(InputStream inputStream2, Charset charset) {
        if (charset != null) return this.createJsonParser(new InputStreamReader(inputStream2, charset));
        return this.createJsonParser(inputStream2);
    }

    @Override
    public JsonParser createJsonParser(Reader reader) {
        return new AndroidJsonParser(this, new JsonReader(reader));
    }

    @Override
    public JsonParser createJsonParser(String string2) {
        return this.createJsonParser(new StringReader(string2));
    }

    static class InstanceHolder {
        static final AndroidJsonFactory INSTANCE = new AndroidJsonFactory();

        InstanceHolder() {
        }
    }

}

