/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.oned.OneDimensionalCodeWriter;
import com.google.zxing.oned.UPCEANReader;

public abstract class UPCEANWriter
extends OneDimensionalCodeWriter {
    @Override
    public int getDefaultMargin() {
        return UPCEANReader.START_END_PATTERN.length;
    }
}

