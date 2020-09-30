/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

public final class BarcodeFormat
extends Enum<BarcodeFormat> {
    private static final /* synthetic */ BarcodeFormat[] $VALUES;
    public static final /* enum */ BarcodeFormat AZTEC;
    public static final /* enum */ BarcodeFormat CODABAR;
    public static final /* enum */ BarcodeFormat CODE_128;
    public static final /* enum */ BarcodeFormat CODE_39;
    public static final /* enum */ BarcodeFormat CODE_93;
    public static final /* enum */ BarcodeFormat DATA_MATRIX;
    public static final /* enum */ BarcodeFormat EAN_13;
    public static final /* enum */ BarcodeFormat EAN_8;
    public static final /* enum */ BarcodeFormat ITF;
    public static final /* enum */ BarcodeFormat MAXICODE;
    public static final /* enum */ BarcodeFormat PDF_417;
    public static final /* enum */ BarcodeFormat QR_CODE;
    public static final /* enum */ BarcodeFormat RSS_14;
    public static final /* enum */ BarcodeFormat RSS_EXPANDED;
    public static final /* enum */ BarcodeFormat UPC_A;
    public static final /* enum */ BarcodeFormat UPC_E;
    public static final /* enum */ BarcodeFormat UPC_EAN_EXTENSION;

    static {
        BarcodeFormat barcodeFormat;
        AZTEC = new BarcodeFormat();
        CODABAR = new BarcodeFormat();
        CODE_39 = new BarcodeFormat();
        CODE_93 = new BarcodeFormat();
        CODE_128 = new BarcodeFormat();
        DATA_MATRIX = new BarcodeFormat();
        EAN_8 = new BarcodeFormat();
        EAN_13 = new BarcodeFormat();
        ITF = new BarcodeFormat();
        MAXICODE = new BarcodeFormat();
        PDF_417 = new BarcodeFormat();
        QR_CODE = new BarcodeFormat();
        RSS_14 = new BarcodeFormat();
        RSS_EXPANDED = new BarcodeFormat();
        UPC_A = new BarcodeFormat();
        UPC_E = new BarcodeFormat();
        UPC_EAN_EXTENSION = barcodeFormat = new BarcodeFormat();
        $VALUES = new BarcodeFormat[]{AZTEC, CODABAR, CODE_39, CODE_93, CODE_128, DATA_MATRIX, EAN_8, EAN_13, ITF, MAXICODE, PDF_417, QR_CODE, RSS_14, RSS_EXPANDED, UPC_A, UPC_E, barcodeFormat};
    }

    public static BarcodeFormat valueOf(String string2) {
        return Enum.valueOf(BarcodeFormat.class, string2);
    }

    public static BarcodeFormat[] values() {
        return (BarcodeFormat[])$VALUES.clone();
    }
}

