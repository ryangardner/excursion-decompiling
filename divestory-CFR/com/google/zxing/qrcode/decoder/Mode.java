/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

public final class Mode
extends Enum<Mode> {
    private static final /* synthetic */ Mode[] $VALUES;
    public static final /* enum */ Mode ALPHANUMERIC;
    public static final /* enum */ Mode BYTE;
    public static final /* enum */ Mode ECI;
    public static final /* enum */ Mode FNC1_FIRST_POSITION;
    public static final /* enum */ Mode FNC1_SECOND_POSITION;
    public static final /* enum */ Mode HANZI;
    public static final /* enum */ Mode KANJI;
    public static final /* enum */ Mode NUMERIC;
    public static final /* enum */ Mode STRUCTURED_APPEND;
    public static final /* enum */ Mode TERMINATOR;
    private final int bits;
    private final int[] characterCountBitsForVersions;

    static {
        Mode mode;
        TERMINATOR = new Mode(new int[]{0, 0, 0}, 0);
        NUMERIC = new Mode(new int[]{10, 12, 14}, 1);
        ALPHANUMERIC = new Mode(new int[]{9, 11, 13}, 2);
        STRUCTURED_APPEND = new Mode(new int[]{0, 0, 0}, 3);
        BYTE = new Mode(new int[]{8, 16, 16}, 4);
        ECI = new Mode(new int[]{0, 0, 0}, 7);
        KANJI = new Mode(new int[]{8, 10, 12}, 8);
        FNC1_FIRST_POSITION = new Mode(new int[]{0, 0, 0}, 5);
        FNC1_SECOND_POSITION = new Mode(new int[]{0, 0, 0}, 9);
        HANZI = mode = new Mode(new int[]{8, 10, 12}, 13);
        $VALUES = new Mode[]{TERMINATOR, NUMERIC, ALPHANUMERIC, STRUCTURED_APPEND, BYTE, ECI, KANJI, FNC1_FIRST_POSITION, FNC1_SECOND_POSITION, mode};
    }

    private Mode(int[] arrn, int n2) {
        this.characterCountBitsForVersions = arrn;
        this.bits = n2;
    }

    public static Mode forBits(int n) {
        if (n == 0) return TERMINATOR;
        if (n == 1) return NUMERIC;
        if (n == 2) return ALPHANUMERIC;
        if (n == 3) return STRUCTURED_APPEND;
        if (n == 4) return BYTE;
        if (n == 5) return FNC1_FIRST_POSITION;
        if (n == 7) return ECI;
        if (n == 8) return KANJI;
        if (n == 9) return FNC1_SECOND_POSITION;
        if (n != 13) throw new IllegalArgumentException();
        return HANZI;
    }

    public static Mode valueOf(String string2) {
        return Enum.valueOf(Mode.class, string2);
    }

    public static Mode[] values() {
        return (Mode[])$VALUES.clone();
    }

    public int getBits() {
        return this.bits;
    }

    public int getCharacterCountBits(Version version) {
        int n = version.getVersionNumber();
        if (n <= 9) {
            n = 0;
            return this.characterCountBitsForVersions[n];
        }
        if (n <= 26) {
            n = 1;
            return this.characterCountBitsForVersions[n];
        }
        n = 2;
        return this.characterCountBitsForVersions[n];
    }
}

