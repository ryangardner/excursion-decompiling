/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANExtension2Support;
import com.google.zxing.oned.UPCEANExtension5Support;
import com.google.zxing.oned.UPCEANReader;

final class UPCEANExtensionSupport {
    private static final int[] EXTENSION_START_PATTERN = new int[]{1, 1, 2};
    private final UPCEANExtension5Support fiveSupport = new UPCEANExtension5Support();
    private final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();

    UPCEANExtensionSupport() {
    }

    Result decodeRow(int n, BitArray bitArray, int n2) throws NotFoundException {
        int[] arrn = UPCEANReader.findGuardPattern(bitArray, n2, false, EXTENSION_START_PATTERN);
        try {
            return this.fiveSupport.decodeRow(n, bitArray, arrn);
        }
        catch (ReaderException readerException) {
            return this.twoSupport.decodeRow(n, bitArray, arrn);
        }
    }
}

