/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;

public final class StreamWriteFeature
extends Enum<StreamWriteFeature> {
    private static final /* synthetic */ StreamWriteFeature[] $VALUES;
    public static final /* enum */ StreamWriteFeature AUTO_CLOSE_CONTENT;
    public static final /* enum */ StreamWriteFeature AUTO_CLOSE_TARGET;
    public static final /* enum */ StreamWriteFeature FLUSH_PASSED_TO_STREAM;
    public static final /* enum */ StreamWriteFeature IGNORE_UNKNOWN;
    public static final /* enum */ StreamWriteFeature STRICT_DUPLICATE_DETECTION;
    public static final /* enum */ StreamWriteFeature WRITE_BIGDECIMAL_AS_PLAIN;
    private final boolean _defaultState;
    private final JsonGenerator.Feature _mappedFeature;
    private final int _mask;

    static {
        StreamWriteFeature streamWriteFeature;
        AUTO_CLOSE_TARGET = new StreamWriteFeature(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        AUTO_CLOSE_CONTENT = new StreamWriteFeature(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        FLUSH_PASSED_TO_STREAM = new StreamWriteFeature(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM);
        WRITE_BIGDECIMAL_AS_PLAIN = new StreamWriteFeature(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        STRICT_DUPLICATE_DETECTION = new StreamWriteFeature(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION);
        IGNORE_UNKNOWN = streamWriteFeature = new StreamWriteFeature(JsonGenerator.Feature.IGNORE_UNKNOWN);
        $VALUES = new StreamWriteFeature[]{AUTO_CLOSE_TARGET, AUTO_CLOSE_CONTENT, FLUSH_PASSED_TO_STREAM, WRITE_BIGDECIMAL_AS_PLAIN, STRICT_DUPLICATE_DETECTION, streamWriteFeature};
    }

    private StreamWriteFeature(JsonGenerator.Feature feature) {
        this._mappedFeature = feature;
        this._mask = feature.getMask();
        this._defaultState = feature.enabledByDefault();
    }

    public static int collectDefaults() {
        StreamWriteFeature[] arrstreamWriteFeature = StreamWriteFeature.values();
        int n = arrstreamWriteFeature.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            StreamWriteFeature streamWriteFeature = arrstreamWriteFeature[n2];
            int n4 = n3;
            if (streamWriteFeature.enabledByDefault()) {
                n4 = n3 | streamWriteFeature.getMask();
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static StreamWriteFeature valueOf(String string2) {
        return Enum.valueOf(StreamWriteFeature.class, string2);
    }

    public static StreamWriteFeature[] values() {
        return (StreamWriteFeature[])$VALUES.clone();
    }

    public boolean enabledByDefault() {
        return this._defaultState;
    }

    public boolean enabledIn(int n) {
        if ((n & this._mask) == 0) return false;
        return true;
    }

    public int getMask() {
        return this._mask;
    }

    public JsonGenerator.Feature mappedFeature() {
        return this._mappedFeature;
    }
}

