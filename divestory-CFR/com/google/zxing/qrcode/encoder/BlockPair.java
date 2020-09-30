/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.encoder;

final class BlockPair {
    private final byte[] dataBytes;
    private final byte[] errorCorrectionBytes;

    BlockPair(byte[] arrby, byte[] arrby2) {
        this.dataBytes = arrby;
        this.errorCorrectionBytes = arrby2;
    }

    public byte[] getDataBytes() {
        return this.dataBytes;
    }

    public byte[] getErrorCorrectionBytes() {
        return this.errorCorrectionBytes;
    }
}

