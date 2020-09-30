/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

public final class ErrorCorrectionLevel
extends Enum<ErrorCorrectionLevel> {
    private static final /* synthetic */ ErrorCorrectionLevel[] $VALUES;
    private static final ErrorCorrectionLevel[] FOR_BITS;
    public static final /* enum */ ErrorCorrectionLevel H;
    public static final /* enum */ ErrorCorrectionLevel L;
    public static final /* enum */ ErrorCorrectionLevel M;
    public static final /* enum */ ErrorCorrectionLevel Q;
    private final int bits;

    static {
        ErrorCorrectionLevel errorCorrectionLevel;
        L = new ErrorCorrectionLevel(1);
        M = new ErrorCorrectionLevel(0);
        Q = new ErrorCorrectionLevel(3);
        H = errorCorrectionLevel = new ErrorCorrectionLevel(2);
        ErrorCorrectionLevel errorCorrectionLevel2 = L;
        ErrorCorrectionLevel errorCorrectionLevel3 = M;
        ErrorCorrectionLevel errorCorrectionLevel4 = Q;
        $VALUES = new ErrorCorrectionLevel[]{errorCorrectionLevel2, errorCorrectionLevel3, errorCorrectionLevel4, errorCorrectionLevel};
        FOR_BITS = new ErrorCorrectionLevel[]{errorCorrectionLevel3, errorCorrectionLevel2, errorCorrectionLevel, errorCorrectionLevel4};
    }

    private ErrorCorrectionLevel(int n2) {
        this.bits = n2;
    }

    public static ErrorCorrectionLevel forBits(int n) {
        if (n < 0) throw new IllegalArgumentException();
        ErrorCorrectionLevel[] arrerrorCorrectionLevel = FOR_BITS;
        if (n >= arrerrorCorrectionLevel.length) throw new IllegalArgumentException();
        return arrerrorCorrectionLevel[n];
    }

    public static ErrorCorrectionLevel valueOf(String string2) {
        return Enum.valueOf(ErrorCorrectionLevel.class, string2);
    }

    public static ErrorCorrectionLevel[] values() {
        return (ErrorCorrectionLevel[])$VALUES.clone();
    }

    public int getBits() {
        return this.bits;
    }
}

