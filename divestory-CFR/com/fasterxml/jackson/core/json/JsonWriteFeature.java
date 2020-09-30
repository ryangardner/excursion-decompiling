/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonGenerator;

public final class JsonWriteFeature
extends Enum<JsonWriteFeature>
implements FormatFeature {
    private static final /* synthetic */ JsonWriteFeature[] $VALUES;
    public static final /* enum */ JsonWriteFeature ESCAPE_NON_ASCII;
    public static final /* enum */ JsonWriteFeature QUOTE_FIELD_NAMES;
    public static final /* enum */ JsonWriteFeature WRITE_NAN_AS_STRINGS;
    public static final /* enum */ JsonWriteFeature WRITE_NUMBERS_AS_STRINGS;
    private final boolean _defaultState;
    private final JsonGenerator.Feature _mappedFeature;
    private final int _mask;

    static {
        JsonWriteFeature jsonWriteFeature;
        QUOTE_FIELD_NAMES = new JsonWriteFeature(true, JsonGenerator.Feature.QUOTE_FIELD_NAMES);
        WRITE_NAN_AS_STRINGS = new JsonWriteFeature(true, JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS);
        WRITE_NUMBERS_AS_STRINGS = new JsonWriteFeature(false, JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
        ESCAPE_NON_ASCII = jsonWriteFeature = new JsonWriteFeature(false, JsonGenerator.Feature.ESCAPE_NON_ASCII);
        $VALUES = new JsonWriteFeature[]{QUOTE_FIELD_NAMES, WRITE_NAN_AS_STRINGS, WRITE_NUMBERS_AS_STRINGS, jsonWriteFeature};
    }

    private JsonWriteFeature(boolean bl, JsonGenerator.Feature feature) {
        this._defaultState = bl;
        this._mask = 1 << this.ordinal();
        this._mappedFeature = feature;
    }

    public static int collectDefaults() {
        JsonWriteFeature[] arrjsonWriteFeature = JsonWriteFeature.values();
        int n = arrjsonWriteFeature.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            JsonWriteFeature jsonWriteFeature = arrjsonWriteFeature[n2];
            int n4 = n3;
            if (jsonWriteFeature.enabledByDefault()) {
                n4 = n3 | jsonWriteFeature.getMask();
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static JsonWriteFeature valueOf(String string2) {
        return Enum.valueOf(JsonWriteFeature.class, string2);
    }

    public static JsonWriteFeature[] values() {
        return (JsonWriteFeature[])$VALUES.clone();
    }

    @Override
    public boolean enabledByDefault() {
        return this._defaultState;
    }

    @Override
    public boolean enabledIn(int n) {
        if ((n & this._mask) == 0) return false;
        return true;
    }

    @Override
    public int getMask() {
        return this._mask;
    }

    public JsonGenerator.Feature mappedFeature() {
        return this._mappedFeature;
    }
}

