/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public interface Writer {
    public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException;

    public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException;
}

