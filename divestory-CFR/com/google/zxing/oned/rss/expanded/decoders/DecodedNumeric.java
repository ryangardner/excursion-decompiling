/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedNumeric
extends DecodedObject {
    static final int FNC1 = 10;
    private final int firstDigit;
    private final int secondDigit;

    DecodedNumeric(int n, int n2, int n3) throws FormatException {
        super(n);
        if (n2 < 0) throw FormatException.getFormatInstance();
        if (n2 > 10) throw FormatException.getFormatInstance();
        if (n3 < 0) throw FormatException.getFormatInstance();
        if (n3 > 10) throw FormatException.getFormatInstance();
        this.firstDigit = n2;
        this.secondDigit = n3;
    }

    int getFirstDigit() {
        return this.firstDigit;
    }

    int getSecondDigit() {
        return this.secondDigit;
    }

    int getValue() {
        return this.firstDigit * 10 + this.secondDigit;
    }

    boolean isAnyFNC1() {
        if (this.firstDigit == 10) return true;
        if (this.secondDigit == 10) return true;
        return false;
    }

    boolean isFirstDigitFNC1() {
        if (this.firstDigit != 10) return false;
        return true;
    }

    boolean isSecondDigitFNC1() {
        if (this.secondDigit != 10) return false;
        return true;
    }
}

