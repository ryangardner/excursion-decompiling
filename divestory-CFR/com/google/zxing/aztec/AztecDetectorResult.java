/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;

public final class AztecDetectorResult
extends DetectorResult {
    private final boolean compact;
    private final int nbDatablocks;
    private final int nbLayers;

    public AztecDetectorResult(BitMatrix bitMatrix, ResultPoint[] arrresultPoint, boolean bl, int n, int n2) {
        super(bitMatrix, arrresultPoint);
        this.compact = bl;
        this.nbDatablocks = n;
        this.nbLayers = n2;
    }

    public int getNbDatablocks() {
        return this.nbDatablocks;
    }

    public int getNbLayers() {
        return this.nbLayers;
    }

    public boolean isCompact() {
        return this.compact;
    }
}

