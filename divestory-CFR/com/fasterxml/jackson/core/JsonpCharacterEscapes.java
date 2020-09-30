/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

public class JsonpCharacterEscapes
extends CharacterEscapes {
    private static final int[] asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
    private static final SerializedString escapeFor2028 = new SerializedString("\\u2028");
    private static final SerializedString escapeFor2029 = new SerializedString("\\u2029");
    private static final JsonpCharacterEscapes sInstance = new JsonpCharacterEscapes();
    private static final long serialVersionUID = 1L;

    public static JsonpCharacterEscapes instance() {
        return sInstance;
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int n) {
        if (n == 8232) return escapeFor2028;
        if (n == 8233) return escapeFor2029;
        return null;
    }
}

