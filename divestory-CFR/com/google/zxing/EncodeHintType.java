/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

public final class EncodeHintType
extends Enum<EncodeHintType> {
    private static final /* synthetic */ EncodeHintType[] $VALUES;
    public static final /* enum */ EncodeHintType AZTEC_LAYERS;
    public static final /* enum */ EncodeHintType CHARACTER_SET;
    public static final /* enum */ EncodeHintType DATA_MATRIX_SHAPE;
    public static final /* enum */ EncodeHintType ERROR_CORRECTION;
    public static final /* enum */ EncodeHintType MARGIN;
    @Deprecated
    public static final /* enum */ EncodeHintType MAX_SIZE;
    @Deprecated
    public static final /* enum */ EncodeHintType MIN_SIZE;
    public static final /* enum */ EncodeHintType PDF417_COMPACT;
    public static final /* enum */ EncodeHintType PDF417_COMPACTION;
    public static final /* enum */ EncodeHintType PDF417_DIMENSIONS;

    static {
        EncodeHintType encodeHintType;
        ERROR_CORRECTION = new EncodeHintType();
        CHARACTER_SET = new EncodeHintType();
        DATA_MATRIX_SHAPE = new EncodeHintType();
        MIN_SIZE = new EncodeHintType();
        MAX_SIZE = new EncodeHintType();
        MARGIN = new EncodeHintType();
        PDF417_COMPACT = new EncodeHintType();
        PDF417_COMPACTION = new EncodeHintType();
        PDF417_DIMENSIONS = new EncodeHintType();
        AZTEC_LAYERS = encodeHintType = new EncodeHintType();
        $VALUES = new EncodeHintType[]{ERROR_CORRECTION, CHARACTER_SET, DATA_MATRIX_SHAPE, MIN_SIZE, MAX_SIZE, MARGIN, PDF417_COMPACT, PDF417_COMPACTION, PDF417_DIMENSIONS, encodeHintType};
    }

    public static EncodeHintType valueOf(String string2) {
        return Enum.valueOf(EncodeHintType.class, string2);
    }

    public static EncodeHintType[] values() {
        return (EncodeHintType[])$VALUES.clone();
    }
}

