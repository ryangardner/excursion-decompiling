/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

public final class JsonToken
extends Enum<JsonToken> {
    private static final /* synthetic */ JsonToken[] $VALUES;
    public static final /* enum */ JsonToken END_ARRAY;
    public static final /* enum */ JsonToken END_OBJECT;
    public static final /* enum */ JsonToken FIELD_NAME;
    public static final /* enum */ JsonToken NOT_AVAILABLE;
    public static final /* enum */ JsonToken START_ARRAY;
    public static final /* enum */ JsonToken START_OBJECT;
    public static final /* enum */ JsonToken VALUE_FALSE;
    public static final /* enum */ JsonToken VALUE_NULL;
    public static final /* enum */ JsonToken VALUE_NUMBER_FLOAT;
    public static final /* enum */ JsonToken VALUE_NUMBER_INT;
    public static final /* enum */ JsonToken VALUE_STRING;
    public static final /* enum */ JsonToken VALUE_TRUE;

    static {
        JsonToken jsonToken;
        START_ARRAY = new JsonToken();
        END_ARRAY = new JsonToken();
        START_OBJECT = new JsonToken();
        END_OBJECT = new JsonToken();
        FIELD_NAME = new JsonToken();
        VALUE_STRING = new JsonToken();
        VALUE_NUMBER_INT = new JsonToken();
        VALUE_NUMBER_FLOAT = new JsonToken();
        VALUE_TRUE = new JsonToken();
        VALUE_FALSE = new JsonToken();
        VALUE_NULL = new JsonToken();
        NOT_AVAILABLE = jsonToken = new JsonToken();
        $VALUES = new JsonToken[]{START_ARRAY, END_ARRAY, START_OBJECT, END_OBJECT, FIELD_NAME, VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE, VALUE_NULL, jsonToken};
    }

    public static JsonToken valueOf(String string2) {
        return Enum.valueOf(JsonToken.class, string2);
    }

    public static JsonToken[] values() {
        return (JsonToken[])$VALUES.clone();
    }
}

