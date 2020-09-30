/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI013103decoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01320xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01392xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01393xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0x1xDecoder;
import com.google.zxing.oned.rss.expanded.decoders.AI01AndOtherAIs;
import com.google.zxing.oned.rss.expanded.decoders.AnyAIDecoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

public abstract class AbstractExpandedDecoder {
    private final GeneralAppIdDecoder generalDecoder;
    private final BitArray information;

    AbstractExpandedDecoder(BitArray bitArray) {
        this.information = bitArray;
        this.generalDecoder = new GeneralAppIdDecoder(bitArray);
    }

    public static AbstractExpandedDecoder createDecoder(BitArray bitArray) {
        if (bitArray.get(1)) {
            return new AI01AndOtherAIs(bitArray);
        }
        if (!bitArray.get(2)) {
            return new AnyAIDecoder(bitArray);
        }
        int n = GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 4);
        if (n == 4) return new AI013103decoder(bitArray);
        if (n == 5) return new AI01320xDecoder(bitArray);
        n = GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 5);
        if (n == 12) return new AI01392xDecoder(bitArray);
        if (n == 13) return new AI01393xDecoder(bitArray);
        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 7)) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown decoder: ");
                stringBuilder.append(bitArray);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 63: {
                return new AI013x0x1xDecoder(bitArray, "320", "17");
            }
            case 62: {
                return new AI013x0x1xDecoder(bitArray, "310", "17");
            }
            case 61: {
                return new AI013x0x1xDecoder(bitArray, "320", "15");
            }
            case 60: {
                return new AI013x0x1xDecoder(bitArray, "310", "15");
            }
            case 59: {
                return new AI013x0x1xDecoder(bitArray, "320", "13");
            }
            case 58: {
                return new AI013x0x1xDecoder(bitArray, "310", "13");
            }
            case 57: {
                return new AI013x0x1xDecoder(bitArray, "320", "11");
            }
            case 56: 
        }
        return new AI013x0x1xDecoder(bitArray, "310", "11");
    }

    protected final GeneralAppIdDecoder getGeneralDecoder() {
        return this.generalDecoder;
    }

    protected final BitArray getInformation() {
        return this.information;
    }

    public abstract String parseInformation() throws NotFoundException, FormatException;
}

