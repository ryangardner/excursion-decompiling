/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonParser;

public final class StreamReadFeature
extends Enum<StreamReadFeature> {
    private static final /* synthetic */ StreamReadFeature[] $VALUES;
    public static final /* enum */ StreamReadFeature AUTO_CLOSE_SOURCE;
    public static final /* enum */ StreamReadFeature IGNORE_UNDEFINED;
    public static final /* enum */ StreamReadFeature INCLUDE_SOURCE_IN_LOCATION;
    public static final /* enum */ StreamReadFeature STRICT_DUPLICATE_DETECTION;
    private final boolean _defaultState;
    private final JsonParser.Feature _mappedFeature;
    private final int _mask;

    static {
        StreamReadFeature streamReadFeature;
        AUTO_CLOSE_SOURCE = new StreamReadFeature(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        STRICT_DUPLICATE_DETECTION = new StreamReadFeature(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        IGNORE_UNDEFINED = new StreamReadFeature(JsonParser.Feature.IGNORE_UNDEFINED);
        INCLUDE_SOURCE_IN_LOCATION = streamReadFeature = new StreamReadFeature(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);
        $VALUES = new StreamReadFeature[]{AUTO_CLOSE_SOURCE, STRICT_DUPLICATE_DETECTION, IGNORE_UNDEFINED, streamReadFeature};
    }

    private StreamReadFeature(JsonParser.Feature feature) {
        this._mappedFeature = feature;
        this._mask = feature.getMask();
        this._defaultState = feature.enabledByDefault();
    }

    public static int collectDefaults() {
        StreamReadFeature[] arrstreamReadFeature = StreamReadFeature.values();
        int n = arrstreamReadFeature.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            StreamReadFeature streamReadFeature = arrstreamReadFeature[n2];
            int n4 = n3;
            if (streamReadFeature.enabledByDefault()) {
                n4 = n3 | streamReadFeature.getMask();
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static StreamReadFeature valueOf(String string2) {
        return Enum.valueOf(StreamReadFeature.class, string2);
    }

    public static StreamReadFeature[] values() {
        return (StreamReadFeature[])$VALUES.clone();
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

    public JsonParser.Feature mappedFeature() {
        return this._mappedFeature;
    }
}

