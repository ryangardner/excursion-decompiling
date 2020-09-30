/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

public final class JsonToken
extends Enum<JsonToken> {
    private static final /* synthetic */ JsonToken[] $VALUES;
    public static final /* enum */ JsonToken END_ARRAY;
    public static final /* enum */ JsonToken END_OBJECT;
    public static final /* enum */ JsonToken FIELD_NAME;
    public static final /* enum */ JsonToken NOT_AVAILABLE;
    public static final /* enum */ JsonToken START_ARRAY;
    public static final /* enum */ JsonToken START_OBJECT;
    public static final /* enum */ JsonToken VALUE_EMBEDDED_OBJECT;
    public static final /* enum */ JsonToken VALUE_FALSE;
    public static final /* enum */ JsonToken VALUE_NULL;
    public static final /* enum */ JsonToken VALUE_NUMBER_FLOAT;
    public static final /* enum */ JsonToken VALUE_NUMBER_INT;
    public static final /* enum */ JsonToken VALUE_STRING;
    public static final /* enum */ JsonToken VALUE_TRUE;
    final int _id;
    final boolean _isBoolean;
    final boolean _isNumber;
    final boolean _isScalar;
    final boolean _isStructEnd;
    final boolean _isStructStart;
    final String _serialized;
    final byte[] _serializedBytes;
    final char[] _serializedChars;

    static {
        JsonToken jsonToken;
        NOT_AVAILABLE = new JsonToken(null, -1);
        START_OBJECT = new JsonToken("{", 1);
        END_OBJECT = new JsonToken("}", 2);
        START_ARRAY = new JsonToken("[", 3);
        END_ARRAY = new JsonToken("]", 4);
        FIELD_NAME = new JsonToken(null, 5);
        VALUE_EMBEDDED_OBJECT = new JsonToken(null, 12);
        VALUE_STRING = new JsonToken(null, 6);
        VALUE_NUMBER_INT = new JsonToken(null, 7);
        VALUE_NUMBER_FLOAT = new JsonToken(null, 8);
        VALUE_TRUE = new JsonToken("true", 9);
        VALUE_FALSE = new JsonToken("false", 10);
        VALUE_NULL = jsonToken = new JsonToken("null", 11);
        $VALUES = new JsonToken[]{NOT_AVAILABLE, START_OBJECT, END_OBJECT, START_ARRAY, END_ARRAY, FIELD_NAME, VALUE_EMBEDDED_OBJECT, VALUE_STRING, VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT, VALUE_TRUE, VALUE_FALSE, jsonToken};
    }

    private JsonToken(String string2, int n2) {
        boolean bl = false;
        if (string2 == null) {
            this._serialized = null;
            this._serializedChars = null;
            this._serializedBytes = null;
        } else {
            this._serialized = string2;
            arrc = string2.toCharArray();
            this._serializedChars = arrc;
            int n3 = arrc.length;
            this._serializedBytes = new byte[n3];
            for (n = 0; n < n3; ++n) {
                this._serializedBytes[n] = (byte)this._serializedChars[n];
            }
        }
        this._id = n2;
        boolean bl2 = n2 == 10 || n2 == 9;
        this._isBoolean = bl2;
        bl2 = n2 == 7 || n2 == 8;
        this._isNumber = bl2;
        bl2 = n2 == 1 || n2 == 3;
        this._isStructStart = bl2;
        boolean bl3 = n2 == 2 || n2 == 4;
        this._isStructEnd = bl3;
        bl2 = bl;
        if (!this._isStructStart) {
            bl2 = bl;
            if (!bl3) {
                bl2 = bl;
                if (n2 != 5) {
                    bl2 = bl;
                    if (n2 != -1) {
                        bl2 = true;
                    }
                }
            }
        }
        this._isScalar = bl2;
    }

    public static JsonToken valueOf(String string2) {
        return Enum.valueOf(JsonToken.class, string2);
    }

    public static JsonToken[] values() {
        return (JsonToken[])$VALUES.clone();
    }

    public final byte[] asByteArray() {
        return this._serializedBytes;
    }

    public final char[] asCharArray() {
        return this._serializedChars;
    }

    public final String asString() {
        return this._serialized;
    }

    public final int id() {
        return this._id;
    }

    public final boolean isBoolean() {
        return this._isBoolean;
    }

    public final boolean isNumeric() {
        return this._isNumber;
    }

    public final boolean isScalarValue() {
        return this._isScalar;
    }

    public final boolean isStructEnd() {
        return this._isStructEnd;
    }

    public final boolean isStructStart() {
        return this._isStructStart;
    }
}

