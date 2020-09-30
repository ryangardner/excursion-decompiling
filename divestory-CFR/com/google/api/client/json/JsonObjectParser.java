/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonObjectParser
implements ObjectParser {
    private final JsonFactory jsonFactory;
    private final Set<String> wrapperKeys;

    public JsonObjectParser(JsonFactory jsonFactory) {
        this(new Builder(jsonFactory));
    }

    protected JsonObjectParser(Builder builder) {
        this.jsonFactory = builder.jsonFactory;
        this.wrapperKeys = new HashSet<String>(builder.wrapperKeys);
    }

    private void initializeParser(JsonParser jsonParser) throws IOException {
        if (this.wrapperKeys.isEmpty()) {
            return;
        }
        try {
            boolean bl = jsonParser.skipToKey(this.wrapperKeys) != null && jsonParser.getCurrentToken() != JsonToken.END_OBJECT;
            Preconditions.checkArgument(bl, "wrapper key(s) not found: %s", this.wrapperKeys);
            return;
        }
        catch (Throwable throwable) {
            jsonParser.close();
            throw throwable;
        }
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public Set<String> getWrapperKeys() {
        return Collections.unmodifiableSet(this.wrapperKeys);
    }

    @Override
    public <T> T parseAndClose(InputStream inputStream2, Charset charset, Class<T> class_) throws IOException {
        return (T)this.parseAndClose(inputStream2, charset, (Type)class_);
    }

    @Override
    public Object parseAndClose(InputStream closeable, Charset charset, Type type) throws IOException {
        closeable = this.jsonFactory.createJsonParser((InputStream)closeable, charset);
        this.initializeParser((JsonParser)closeable);
        return ((JsonParser)closeable).parse(type, true);
    }

    @Override
    public <T> T parseAndClose(Reader reader, Class<T> class_) throws IOException {
        return (T)this.parseAndClose(reader, (Type)class_);
    }

    @Override
    public Object parseAndClose(Reader closeable, Type type) throws IOException {
        closeable = this.jsonFactory.createJsonParser((Reader)closeable);
        this.initializeParser((JsonParser)closeable);
        return ((JsonParser)closeable).parse(type, true);
    }

    public static class Builder {
        final JsonFactory jsonFactory;
        Collection<String> wrapperKeys = Sets.newHashSet();

        public Builder(JsonFactory jsonFactory) {
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }

        public JsonObjectParser build() {
            return new JsonObjectParser(this);
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final Collection<String> getWrapperKeys() {
            return this.wrapperKeys;
        }

        public Builder setWrapperKeys(Collection<String> collection) {
            this.wrapperKeys = collection;
            return this;
        }
    }

}

