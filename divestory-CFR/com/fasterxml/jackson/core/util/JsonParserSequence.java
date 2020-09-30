/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserSequence
extends JsonParserDelegate {
    protected final boolean _checkForExistingToken;
    protected boolean _hasToken;
    protected int _nextParserIndex;
    protected final JsonParser[] _parsers;

    protected JsonParserSequence(boolean bl, JsonParser[] arrjsonParser) {
        boolean bl2 = false;
        super(arrjsonParser[0]);
        this._checkForExistingToken = bl;
        boolean bl3 = bl2;
        if (bl) {
            bl3 = bl2;
            if (this.delegate.hasCurrentToken()) {
                bl3 = true;
            }
        }
        this._hasToken = bl3;
        this._parsers = arrjsonParser;
        this._nextParserIndex = 1;
    }

    @Deprecated
    protected JsonParserSequence(JsonParser[] arrjsonParser) {
        this(false, arrjsonParser);
    }

    @Deprecated
    public static JsonParserSequence createFlattened(JsonParser jsonParser, JsonParser jsonParser2) {
        return JsonParserSequence.createFlattened(false, jsonParser, jsonParser2);
    }

    public static JsonParserSequence createFlattened(boolean bl, JsonParser jsonParser, JsonParser jsonParser2) {
        boolean bl2 = jsonParser instanceof JsonParserSequence;
        if (!bl2 && !(jsonParser2 instanceof JsonParserSequence)) {
            return new JsonParserSequence(bl, new JsonParser[]{jsonParser, jsonParser2});
        }
        ArrayList<JsonParser> arrayList = new ArrayList<JsonParser>();
        if (bl2) {
            ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(arrayList);
        } else {
            arrayList.add(jsonParser);
        }
        if (jsonParser2 instanceof JsonParserSequence) {
            ((JsonParserSequence)jsonParser2).addFlattenedActiveParsers(arrayList);
            return new JsonParserSequence(bl, arrayList.toArray(new JsonParser[arrayList.size()]));
        }
        arrayList.add(jsonParser2);
        return new JsonParserSequence(bl, arrayList.toArray(new JsonParser[arrayList.size()]));
    }

    protected void addFlattenedActiveParsers(List<JsonParser> list) {
        int n = this._nextParserIndex - 1;
        int n2 = this._parsers.length;
        while (n < n2) {
            JsonParser jsonParser = this._parsers[n];
            if (jsonParser instanceof JsonParserSequence) {
                ((JsonParserSequence)jsonParser).addFlattenedActiveParsers(list);
            } else {
                list.add(jsonParser);
            }
            ++n;
        }
    }

    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }

    public int containedParsersCount() {
        return this._parsers.length;
    }

    @Override
    public JsonToken nextToken() throws IOException {
        JsonToken jsonToken;
        if (this.delegate == null) {
            return null;
        }
        if (this._hasToken) {
            this._hasToken = false;
            return this.delegate.currentToken();
        }
        JsonToken jsonToken2 = jsonToken = this.delegate.nextToken();
        if (jsonToken != null) return jsonToken2;
        return this.switchAndReturnNext();
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        if (this.delegate.currentToken() != JsonToken.START_OBJECT && this.delegate.currentToken() != JsonToken.START_ARRAY) {
            return this;
        }
        int n = 1;
        JsonToken jsonToken;
        while ((jsonToken = this.nextToken()) != null) {
            int n2;
            if (jsonToken.isStructStart()) {
                ++n;
                continue;
            }
            if (!jsonToken.isStructEnd()) continue;
            n = n2 = n - 1;
            if (n2 == 0) return this;
        }
        return this;
    }

    protected JsonToken switchAndReturnNext() throws IOException {
        Object object;
        do {
            int n;
            if ((n = this._nextParserIndex) >= ((JsonParser[])(object = this._parsers)).length) return null;
            this._nextParserIndex = n + 1;
            this.delegate = object[n];
            if (!this._checkForExistingToken || !this.delegate.hasCurrentToken()) continue;
            return this.delegate.getCurrentToken();
        } while ((object = this.delegate.nextToken()) == null);
        return object;
    }

    protected boolean switchToNext() {
        int n = this._nextParserIndex;
        JsonParser[] arrjsonParser = this._parsers;
        if (n >= arrjsonParser.length) return false;
        this._nextParserIndex = n + 1;
        this.delegate = arrjsonParser[n];
        return true;
    }
}

