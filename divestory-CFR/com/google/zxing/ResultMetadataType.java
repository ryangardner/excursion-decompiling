/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

public final class ResultMetadataType
extends Enum<ResultMetadataType> {
    private static final /* synthetic */ ResultMetadataType[] $VALUES;
    public static final /* enum */ ResultMetadataType BYTE_SEGMENTS;
    public static final /* enum */ ResultMetadataType ERROR_CORRECTION_LEVEL;
    public static final /* enum */ ResultMetadataType ISSUE_NUMBER;
    public static final /* enum */ ResultMetadataType ORIENTATION;
    public static final /* enum */ ResultMetadataType OTHER;
    public static final /* enum */ ResultMetadataType PDF417_EXTRA_METADATA;
    public static final /* enum */ ResultMetadataType POSSIBLE_COUNTRY;
    public static final /* enum */ ResultMetadataType STRUCTURED_APPEND_PARITY;
    public static final /* enum */ ResultMetadataType STRUCTURED_APPEND_SEQUENCE;
    public static final /* enum */ ResultMetadataType SUGGESTED_PRICE;
    public static final /* enum */ ResultMetadataType UPC_EAN_EXTENSION;

    static {
        ResultMetadataType resultMetadataType;
        OTHER = new ResultMetadataType();
        ORIENTATION = new ResultMetadataType();
        BYTE_SEGMENTS = new ResultMetadataType();
        ERROR_CORRECTION_LEVEL = new ResultMetadataType();
        ISSUE_NUMBER = new ResultMetadataType();
        SUGGESTED_PRICE = new ResultMetadataType();
        POSSIBLE_COUNTRY = new ResultMetadataType();
        UPC_EAN_EXTENSION = new ResultMetadataType();
        PDF417_EXTRA_METADATA = new ResultMetadataType();
        STRUCTURED_APPEND_SEQUENCE = new ResultMetadataType();
        STRUCTURED_APPEND_PARITY = resultMetadataType = new ResultMetadataType();
        $VALUES = new ResultMetadataType[]{OTHER, ORIENTATION, BYTE_SEGMENTS, ERROR_CORRECTION_LEVEL, ISSUE_NUMBER, SUGGESTED_PRICE, POSSIBLE_COUNTRY, UPC_EAN_EXTENSION, PDF417_EXTRA_METADATA, STRUCTURED_APPEND_SEQUENCE, resultMetadataType};
    }

    public static ResultMetadataType valueOf(String string2) {
        return Enum.valueOf(ResultMetadataType.class, string2);
    }

    public static ResultMetadataType[] values() {
        return (ResultMetadataType[])$VALUES.clone();
    }
}

