/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.ResultPointCallback;
import java.util.List;

public final class DecodeHintType
extends Enum<DecodeHintType> {
    private static final /* synthetic */ DecodeHintType[] $VALUES;
    public static final /* enum */ DecodeHintType ALLOWED_EAN_EXTENSIONS;
    public static final /* enum */ DecodeHintType ALLOWED_LENGTHS;
    public static final /* enum */ DecodeHintType ASSUME_CODE_39_CHECK_DIGIT;
    public static final /* enum */ DecodeHintType ASSUME_GS1;
    public static final /* enum */ DecodeHintType CHARACTER_SET;
    public static final /* enum */ DecodeHintType NEED_RESULT_POINT_CALLBACK;
    public static final /* enum */ DecodeHintType OTHER;
    public static final /* enum */ DecodeHintType POSSIBLE_FORMATS;
    public static final /* enum */ DecodeHintType PURE_BARCODE;
    public static final /* enum */ DecodeHintType RETURN_CODABAR_START_END;
    public static final /* enum */ DecodeHintType TRY_HARDER;
    private final Class<?> valueType;

    static {
        DecodeHintType decodeHintType;
        OTHER = new DecodeHintType(Object.class);
        PURE_BARCODE = new DecodeHintType(Void.class);
        POSSIBLE_FORMATS = new DecodeHintType(List.class);
        TRY_HARDER = new DecodeHintType(Void.class);
        CHARACTER_SET = new DecodeHintType(String.class);
        ALLOWED_LENGTHS = new DecodeHintType(int[].class);
        ASSUME_CODE_39_CHECK_DIGIT = new DecodeHintType(Void.class);
        ASSUME_GS1 = new DecodeHintType(Void.class);
        RETURN_CODABAR_START_END = new DecodeHintType(Void.class);
        NEED_RESULT_POINT_CALLBACK = new DecodeHintType(ResultPointCallback.class);
        ALLOWED_EAN_EXTENSIONS = decodeHintType = new DecodeHintType(int[].class);
        $VALUES = new DecodeHintType[]{OTHER, PURE_BARCODE, POSSIBLE_FORMATS, TRY_HARDER, CHARACTER_SET, ALLOWED_LENGTHS, ASSUME_CODE_39_CHECK_DIGIT, ASSUME_GS1, RETURN_CODABAR_START_END, NEED_RESULT_POINT_CALLBACK, decodeHintType};
    }

    private DecodeHintType(Class<?> class_) {
        this.valueType = class_;
    }

    public static DecodeHintType valueOf(String string2) {
        return Enum.valueOf(DecodeHintType.class, string2);
    }

    public static DecodeHintType[] values() {
        return (DecodeHintType[])$VALUES.clone();
    }

    public Class<?> getValueType() {
        return this.valueType;
    }
}

