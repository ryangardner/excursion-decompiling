/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonParser;

public final class JsonReadFeature
extends Enum<JsonReadFeature>
implements FormatFeature {
    private static final /* synthetic */ JsonReadFeature[] $VALUES;
    public static final /* enum */ JsonReadFeature ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;
    public static final /* enum */ JsonReadFeature ALLOW_JAVA_COMMENTS;
    public static final /* enum */ JsonReadFeature ALLOW_LEADING_ZEROS_FOR_NUMBERS;
    public static final /* enum */ JsonReadFeature ALLOW_MISSING_VALUES;
    public static final /* enum */ JsonReadFeature ALLOW_NON_NUMERIC_NUMBERS;
    public static final /* enum */ JsonReadFeature ALLOW_SINGLE_QUOTES;
    public static final /* enum */ JsonReadFeature ALLOW_TRAILING_COMMA;
    public static final /* enum */ JsonReadFeature ALLOW_UNESCAPED_CONTROL_CHARS;
    public static final /* enum */ JsonReadFeature ALLOW_UNQUOTED_FIELD_NAMES;
    public static final /* enum */ JsonReadFeature ALLOW_YAML_COMMENTS;
    private final boolean _defaultState;
    private final JsonParser.Feature _mappedFeature;
    private final int _mask;

    static {
        JsonReadFeature jsonReadFeature;
        ALLOW_JAVA_COMMENTS = new JsonReadFeature(false, JsonParser.Feature.ALLOW_COMMENTS);
        ALLOW_YAML_COMMENTS = new JsonReadFeature(false, JsonParser.Feature.ALLOW_YAML_COMMENTS);
        ALLOW_SINGLE_QUOTES = new JsonReadFeature(false, JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        ALLOW_UNQUOTED_FIELD_NAMES = new JsonReadFeature(false, JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        ALLOW_UNESCAPED_CONTROL_CHARS = new JsonReadFeature(false, JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER = new JsonReadFeature(false, JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
        ALLOW_LEADING_ZEROS_FOR_NUMBERS = new JsonReadFeature(false, JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
        ALLOW_NON_NUMERIC_NUMBERS = new JsonReadFeature(false, JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        ALLOW_MISSING_VALUES = new JsonReadFeature(false, JsonParser.Feature.ALLOW_MISSING_VALUES);
        ALLOW_TRAILING_COMMA = jsonReadFeature = new JsonReadFeature(false, JsonParser.Feature.ALLOW_TRAILING_COMMA);
        $VALUES = new JsonReadFeature[]{ALLOW_JAVA_COMMENTS, ALLOW_YAML_COMMENTS, ALLOW_SINGLE_QUOTES, ALLOW_UNQUOTED_FIELD_NAMES, ALLOW_UNESCAPED_CONTROL_CHARS, ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, ALLOW_LEADING_ZEROS_FOR_NUMBERS, ALLOW_NON_NUMERIC_NUMBERS, ALLOW_MISSING_VALUES, jsonReadFeature};
    }

    private JsonReadFeature(boolean bl, JsonParser.Feature feature) {
        this._defaultState = bl;
        this._mask = 1 << this.ordinal();
        this._mappedFeature = feature;
    }

    public static int collectDefaults() {
        JsonReadFeature[] arrjsonReadFeature = JsonReadFeature.values();
        int n = arrjsonReadFeature.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            JsonReadFeature jsonReadFeature = arrjsonReadFeature[n2];
            int n4 = n3;
            if (jsonReadFeature.enabledByDefault()) {
                n4 = n3 | jsonReadFeature.getMask();
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static JsonReadFeature valueOf(String string2) {
        return Enum.valueOf(JsonReadFeature.class, string2);
    }

    public static JsonReadFeature[] values() {
        return (JsonReadFeature[])$VALUES.clone();
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

    public JsonParser.Feature mappedFeature() {
        return this._mappedFeature;
    }
}

