/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TSFBuilder;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

public class JsonFactoryBuilder
extends TSFBuilder<JsonFactory, JsonFactoryBuilder> {
    protected CharacterEscapes _characterEscapes;
    protected int _maximumNonEscapedChar;
    protected char _quoteChar = (char)34;
    protected SerializableString _rootValueSeparator;

    public JsonFactoryBuilder() {
        this._rootValueSeparator = JsonFactory.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._maximumNonEscapedChar = 0;
    }

    public JsonFactoryBuilder(JsonFactory jsonFactory) {
        super(jsonFactory);
        this._characterEscapes = jsonFactory.getCharacterEscapes();
        this._rootValueSeparator = jsonFactory._rootValueSeparator;
        this._maximumNonEscapedChar = jsonFactory._maximumNonEscapedChar;
    }

    @Override
    public JsonFactory build() {
        return new JsonFactory(this);
    }

    public JsonFactoryBuilder characterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        return this;
    }

    public CharacterEscapes characterEscapes() {
        return this._characterEscapes;
    }

    @Override
    public JsonFactoryBuilder configure(JsonReadFeature object, boolean bl) {
        if (!bl) return this.disable((JsonReadFeature)object);
        return this.enable((JsonReadFeature)object);
    }

    @Override
    public JsonFactoryBuilder configure(JsonWriteFeature object, boolean bl) {
        if (!bl) return this.disable((JsonWriteFeature)object);
        return this.enable((JsonWriteFeature)object);
    }

    @Override
    public JsonFactoryBuilder disable(JsonReadFeature jsonReadFeature) {
        this._legacyDisable(jsonReadFeature.mappedFeature());
        return this;
    }

    @Override
    public JsonFactoryBuilder disable(JsonReadFeature jsonReadFeature, JsonReadFeature ... arrjsonReadFeature) {
        this._legacyDisable(jsonReadFeature.mappedFeature());
        int n = arrjsonReadFeature.length;
        int n2 = 0;
        while (n2 < n) {
            this._legacyEnable(arrjsonReadFeature[n2].mappedFeature());
            ++n2;
        }
        return this;
    }

    @Override
    public JsonFactoryBuilder disable(JsonWriteFeature jsonWriteFeature) {
        this._legacyDisable(jsonWriteFeature.mappedFeature());
        return this;
    }

    @Override
    public JsonFactoryBuilder disable(JsonWriteFeature jsonWriteFeature, JsonWriteFeature ... arrjsonWriteFeature) {
        this._legacyDisable(jsonWriteFeature.mappedFeature());
        int n = arrjsonWriteFeature.length;
        int n2 = 0;
        while (n2 < n) {
            this._legacyDisable(arrjsonWriteFeature[n2].mappedFeature());
            ++n2;
        }
        return this;
    }

    @Override
    public JsonFactoryBuilder enable(JsonReadFeature jsonReadFeature) {
        this._legacyEnable(jsonReadFeature.mappedFeature());
        return this;
    }

    @Override
    public JsonFactoryBuilder enable(JsonReadFeature jsonReadFeature, JsonReadFeature ... arrjsonReadFeature) {
        this._legacyEnable(jsonReadFeature.mappedFeature());
        this.enable(jsonReadFeature);
        int n = arrjsonReadFeature.length;
        int n2 = 0;
        while (n2 < n) {
            this._legacyEnable(arrjsonReadFeature[n2].mappedFeature());
            ++n2;
        }
        return this;
    }

    @Override
    public JsonFactoryBuilder enable(JsonWriteFeature enum_) {
        if ((enum_ = ((JsonWriteFeature)enum_).mappedFeature()) == null) return this;
        this._legacyEnable((JsonGenerator.Feature)enum_);
        return this;
    }

    @Override
    public JsonFactoryBuilder enable(JsonWriteFeature jsonWriteFeature, JsonWriteFeature ... arrjsonWriteFeature) {
        this._legacyEnable(jsonWriteFeature.mappedFeature());
        int n = arrjsonWriteFeature.length;
        int n2 = 0;
        while (n2 < n) {
            this._legacyEnable(arrjsonWriteFeature[n2].mappedFeature());
            ++n2;
        }
        return this;
    }

    public int highestNonEscapedChar() {
        return this._maximumNonEscapedChar;
    }

    public JsonFactoryBuilder highestNonEscapedChar(int n) {
        n = n <= 0 ? 0 : Math.max(127, n);
        this._maximumNonEscapedChar = n;
        return this;
    }

    public char quoteChar() {
        return this._quoteChar;
    }

    public JsonFactoryBuilder quoteChar(char c) {
        if (c > '') throw new IllegalArgumentException("Can only use Unicode characters up to 0x7F as quote characters");
        this._quoteChar = c;
        return this;
    }

    public JsonFactoryBuilder rootValueSeparator(SerializableString serializableString) {
        this._rootValueSeparator = serializableString;
        return this;
    }

    public JsonFactoryBuilder rootValueSeparator(String object) {
        object = object == null ? null : new SerializedString((String)object);
        this._rootValueSeparator = object;
        return this;
    }

    public SerializableString rootValueSeparator() {
        return this._rootValueSeparator;
    }
}

