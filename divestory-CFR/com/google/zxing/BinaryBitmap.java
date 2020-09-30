/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public final class BinaryBitmap {
    private final Binarizer binarizer;
    private BitMatrix matrix;

    public BinaryBitmap(Binarizer binarizer) {
        if (binarizer == null) throw new IllegalArgumentException("Binarizer must be non-null.");
        this.binarizer = binarizer;
    }

    public BinaryBitmap crop(int n, int n2, int n3, int n4) {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().crop(n, n2, n3, n4);
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) return this.matrix;
        this.matrix = this.binarizer.getBlackMatrix();
        return this.matrix;
    }

    public BitArray getBlackRow(int n, BitArray bitArray) throws NotFoundException {
        return this.binarizer.getBlackRow(n, bitArray);
    }

    public int getHeight() {
        return this.binarizer.getHeight();
    }

    public int getWidth() {
        return this.binarizer.getWidth();
    }

    public boolean isCropSupported() {
        return this.binarizer.getLuminanceSource().isCropSupported();
    }

    public boolean isRotateSupported() {
        return this.binarizer.getLuminanceSource().isRotateSupported();
    }

    public BinaryBitmap rotateCounterClockwise() {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().rotateCounterClockwise();
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public BinaryBitmap rotateCounterClockwise45() {
        LuminanceSource luminanceSource = this.binarizer.getLuminanceSource().rotateCounterClockwise45();
        return new BinaryBitmap(this.binarizer.createBinarizer(luminanceSource));
    }

    public String toString() {
        try {
            return this.getBlackMatrix().toString();
        }
        catch (NotFoundException notFoundException) {
            return "";
        }
    }
}

