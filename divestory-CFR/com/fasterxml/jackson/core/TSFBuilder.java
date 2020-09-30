/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;

public abstract class TSFBuilder<F extends JsonFactory, B extends TSFBuilder<F, B>> {
    protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = JsonFactory.Feature.collectDefaults();
    protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected static final int DEFAULT_PARSER_FEATURE_FLAGS;
    protected int _factoryFeatures;
    protected InputDecorator _inputDecorator;
    protected OutputDecorator _outputDecorator;
    protected int _streamReadFeatures;
    protected int _streamWriteFeatures;

    static {
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
    }

    protected TSFBuilder() {
        this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
        this._streamReadFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
        this._streamWriteFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
        this._inputDecorator = null;
        this._outputDecorator = null;
    }

    protected TSFBuilder(int n, int n2, int n3) {
        this._factoryFeatures = n;
        this._streamReadFeatures = n2;
        this._streamWriteFeatures = n3;
    }

    protected TSFBuilder(JsonFactory jsonFactory) {
        this(jsonFactory._factoryFeatures, jsonFactory._parserFeatures, jsonFactory._generatorFeatures);
    }

    private B _failNonJSON(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Feature ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append("#");
        stringBuilder.append(object.toString());
        stringBuilder.append(" not supported for non-JSON backend");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected void _legacyDisable(JsonGenerator.Feature feature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = feature.getMask() & n;
    }

    protected void _legacyDisable(JsonParser.Feature feature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = feature.getMask() & n;
    }

    protected void _legacyEnable(JsonGenerator.Feature feature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = feature.getMask() | n;
    }

    protected void _legacyEnable(JsonParser.Feature feature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = feature.getMask() | n;
    }

    protected final B _this() {
        return (B)this;
    }

    public abstract F build();

    public B configure(JsonFactory.Feature feature, boolean bl) {
        if (bl) {
            feature = this.enable(feature);
            return (B)((Object)feature);
        }
        feature = this.disable(feature);
        return (B)((Object)feature);
    }

    public B configure(StreamReadFeature streamReadFeature, boolean bl) {
        if (bl) {
            streamReadFeature = this.enable(streamReadFeature);
            return (B)((Object)streamReadFeature);
        }
        streamReadFeature = this.disable(streamReadFeature);
        return (B)((Object)streamReadFeature);
    }

    public B configure(StreamWriteFeature streamWriteFeature, boolean bl) {
        if (bl) {
            streamWriteFeature = this.enable(streamWriteFeature);
            return (B)((Object)streamWriteFeature);
        }
        streamWriteFeature = this.disable(streamWriteFeature);
        return (B)((Object)streamWriteFeature);
    }

    public B configure(JsonReadFeature jsonReadFeature, boolean bl) {
        return this._failNonJSON(jsonReadFeature);
    }

    public B configure(JsonWriteFeature jsonWriteFeature, boolean bl) {
        return this._failNonJSON(jsonWriteFeature);
    }

    public B disable(JsonFactory.Feature feature) {
        int n = this._factoryFeatures;
        this._factoryFeatures = feature.getMask() & n;
        return this._this();
    }

    public B disable(StreamReadFeature streamReadFeature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() & n;
        return this._this();
    }

    public B disable(StreamReadFeature streamReadFeature, StreamReadFeature ... arrstreamReadFeature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() & n;
        int n2 = arrstreamReadFeature.length;
        n = 0;
        while (n < n2) {
            streamReadFeature = arrstreamReadFeature[n];
            int n3 = this._streamReadFeatures;
            this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() & n3;
            ++n;
        }
        return this._this();
    }

    public B disable(StreamWriteFeature streamWriteFeature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() & n;
        return this._this();
    }

    public B disable(StreamWriteFeature streamWriteFeature, StreamWriteFeature ... arrstreamWriteFeature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() & n;
        int n2 = arrstreamWriteFeature.length;
        n = 0;
        while (n < n2) {
            streamWriteFeature = arrstreamWriteFeature[n];
            int n3 = this._streamWriteFeatures;
            this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() & n3;
            ++n;
        }
        return this._this();
    }

    public B disable(JsonReadFeature jsonReadFeature) {
        return this._failNonJSON(jsonReadFeature);
    }

    public B disable(JsonReadFeature jsonReadFeature, JsonReadFeature ... arrjsonReadFeature) {
        return this._failNonJSON(jsonReadFeature);
    }

    public B disable(JsonWriteFeature jsonWriteFeature) {
        return this._failNonJSON(jsonWriteFeature);
    }

    public B disable(JsonWriteFeature jsonWriteFeature, JsonWriteFeature ... arrjsonWriteFeature) {
        return this._failNonJSON(jsonWriteFeature);
    }

    public B enable(JsonFactory.Feature feature) {
        int n = this._factoryFeatures;
        this._factoryFeatures = feature.getMask() | n;
        return this._this();
    }

    public B enable(StreamReadFeature streamReadFeature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() | n;
        return this._this();
    }

    public B enable(StreamReadFeature streamReadFeature, StreamReadFeature ... arrstreamReadFeature) {
        int n = this._streamReadFeatures;
        this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() | n;
        int n2 = arrstreamReadFeature.length;
        n = 0;
        while (n < n2) {
            streamReadFeature = arrstreamReadFeature[n];
            int n3 = this._streamReadFeatures;
            this._streamReadFeatures = streamReadFeature.mappedFeature().getMask() | n3;
            ++n;
        }
        return this._this();
    }

    public B enable(StreamWriteFeature streamWriteFeature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() | n;
        return this._this();
    }

    public B enable(StreamWriteFeature streamWriteFeature, StreamWriteFeature ... arrstreamWriteFeature) {
        int n = this._streamWriteFeatures;
        this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() | n;
        int n2 = arrstreamWriteFeature.length;
        n = 0;
        while (n < n2) {
            streamWriteFeature = arrstreamWriteFeature[n];
            int n3 = this._streamWriteFeatures;
            this._streamWriteFeatures = streamWriteFeature.mappedFeature().getMask() | n3;
            ++n;
        }
        return this._this();
    }

    public B enable(JsonReadFeature jsonReadFeature) {
        return this._failNonJSON(jsonReadFeature);
    }

    public B enable(JsonReadFeature jsonReadFeature, JsonReadFeature ... arrjsonReadFeature) {
        return this._failNonJSON(jsonReadFeature);
    }

    public B enable(JsonWriteFeature jsonWriteFeature) {
        return this._failNonJSON(jsonWriteFeature);
    }

    public B enable(JsonWriteFeature jsonWriteFeature, JsonWriteFeature ... arrjsonWriteFeature) {
        return this._failNonJSON(jsonWriteFeature);
    }

    public int factoryFeaturesMask() {
        return this._factoryFeatures;
    }

    public B inputDecorator(InputDecorator inputDecorator) {
        this._inputDecorator = inputDecorator;
        return this._this();
    }

    public InputDecorator inputDecorator() {
        return this._inputDecorator;
    }

    public B outputDecorator(OutputDecorator outputDecorator) {
        this._outputDecorator = outputDecorator;
        return this._this();
    }

    public OutputDecorator outputDecorator() {
        return this._outputDecorator;
    }

    public int streamReadFeatures() {
        return this._streamReadFeatures;
    }

    public int streamWriteFeatures() {
        return this._streamWriteFeatures;
    }
}

