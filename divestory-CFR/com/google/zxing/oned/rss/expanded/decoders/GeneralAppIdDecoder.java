/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult;
import com.google.zxing.oned.rss.expanded.decoders.CurrentParsingState;
import com.google.zxing.oned.rss.expanded.decoders.DecodedChar;
import com.google.zxing.oned.rss.expanded.decoders.DecodedInformation;
import com.google.zxing.oned.rss.expanded.decoders.DecodedNumeric;
import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;
import com.google.zxing.oned.rss.expanded.decoders.FieldParser;

final class GeneralAppIdDecoder {
    private final StringBuilder buffer = new StringBuilder();
    private final CurrentParsingState current = new CurrentParsingState();
    private final BitArray information;

    GeneralAppIdDecoder(BitArray bitArray) {
        this.information = bitArray;
    }

    private DecodedChar decodeAlphanumeric(int n) {
        int n2;
        int n3 = this.extractNumericValueFromBitArray(n, 5);
        if (n3 == 15) {
            return new DecodedChar(n + 5, '$');
        }
        if (n3 >= 5 && n3 < 15) {
            return new DecodedChar(n + 5, (char)(n3 + 48 - 5));
        }
        n3 = this.extractNumericValueFromBitArray(n, 6);
        if (n3 >= 32 && n3 < 58) {
            return new DecodedChar(n + 6, (char)(n3 + 33));
        }
        switch (n3) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Decoding invalid alphanumeric value: ");
                stringBuilder.append(n3);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 62: {
                n2 = n3 = 47;
                return new DecodedChar(n + 6, (char)n2);
            }
            case 61: {
                n2 = n3 = 46;
                return new DecodedChar(n + 6, (char)n2);
            }
            case 60: {
                n2 = n3 = 45;
                return new DecodedChar(n + 6, (char)n2);
            }
            case 59: {
                n2 = n3 = 44;
                return new DecodedChar(n + 6, (char)n2);
            }
            case 58: 
        }
        n2 = n3 = 42;
        return new DecodedChar(n + 6, (char)n2);
    }

    private DecodedChar decodeIsoIec646(int n) throws FormatException {
        int n2;
        int n3 = this.extractNumericValueFromBitArray(n, 5);
        if (n3 == 15) {
            return new DecodedChar(n + 5, '$');
        }
        if (n3 >= 5 && n3 < 15) {
            return new DecodedChar(n + 5, (char)(n3 + 48 - 5));
        }
        n3 = this.extractNumericValueFromBitArray(n, 7);
        if (n3 >= 64 && n3 < 90) {
            return new DecodedChar(n + 7, (char)(n3 + 1));
        }
        if (n3 >= 90 && n3 < 116) {
            return new DecodedChar(n + 7, (char)(n3 + 7));
        }
        switch (this.extractNumericValueFromBitArray(n, 8)) {
            default: {
                throw FormatException.getFormatInstance();
            }
            case 252: {
                n2 = n3 = 32;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 251: {
                n2 = n3 = 95;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 250: {
                n2 = n3 = 63;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 249: {
                n2 = n3 = 62;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 248: {
                n2 = n3 = 61;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 247: {
                n2 = n3 = 60;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 246: {
                n2 = n3 = 59;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 245: {
                n2 = n3 = 58;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 244: {
                n2 = n3 = 47;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 243: {
                n2 = n3 = 46;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 242: {
                n2 = n3 = 45;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 241: {
                n2 = n3 = 44;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 240: {
                n2 = n3 = 43;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 239: {
                n2 = n3 = 42;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 238: {
                n2 = n3 = 41;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 237: {
                n2 = n3 = 40;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 236: {
                n2 = n3 = 39;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 235: {
                n2 = n3 = 38;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 234: {
                n2 = n3 = 37;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 233: {
                n2 = n3 = 34;
                return new DecodedChar(n + 8, (char)n2);
            }
            case 232: 
        }
        n2 = n3 = 33;
        return new DecodedChar(n + 8, (char)n2);
    }

    private DecodedNumeric decodeNumeric(int n) throws FormatException {
        int n2 = n + 7;
        if (n2 > this.information.getSize()) {
            if ((n = this.extractNumericValueFromBitArray(n, 4)) != 0) return new DecodedNumeric(this.information.getSize(), n - 1, 10);
            return new DecodedNumeric(this.information.getSize(), 10, 10);
        }
        n = this.extractNumericValueFromBitArray(n, 7) - 8;
        return new DecodedNumeric(n2, n / 11, n % 11);
    }

    static int extractNumericValueFromBitArray(BitArray bitArray, int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            int n5 = n4;
            if (bitArray.get(n + n3)) {
                n5 = n4 | 1 << n2 - n3 - 1;
            }
            ++n3;
            n4 = n5;
        }
        return n4;
    }

    private boolean isAlphaOr646ToNumericLatch(int n) {
        int n2 = n + 3;
        if (n2 > this.information.getSize()) {
            return false;
        }
        while (n < n2) {
            if (this.information.get(n)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private boolean isAlphaTo646ToAlphaLatch(int n) {
        if (n + 1 > this.information.getSize()) {
            return false;
        }
        int n2 = 0;
        while (n2 < 5) {
            int n3 = n2 + n;
            if (n3 >= this.information.getSize()) return true;
            if (n2 == 2 ? !this.information.get(n + 2) : this.information.get(n3)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    private boolean isNumericToAlphaNumericLatch(int n) {
        if (n + 1 > this.information.getSize()) {
            return false;
        }
        int n2 = 0;
        while (n2 < 4) {
            int n3 = n2 + n;
            if (n3 >= this.information.getSize()) return true;
            if (this.information.get(n3)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    private boolean isStillAlpha(int n) {
        int n2 = this.information.getSize();
        boolean bl = false;
        if (n + 5 > n2) {
            return false;
        }
        n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 >= 5 && n2 < 16) {
            return true;
        }
        if (n + 6 > this.information.getSize()) {
            return false;
        }
        n = this.extractNumericValueFromBitArray(n, 6);
        boolean bl2 = bl;
        if (n < 16) return bl2;
        bl2 = bl;
        if (n >= 63) return bl2;
        return true;
    }

    private boolean isStillIsoIec646(int n) {
        int n2 = this.information.getSize();
        boolean bl = false;
        if (n + 5 > n2) {
            return false;
        }
        n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 >= 5 && n2 < 16) {
            return true;
        }
        if (n + 7 > this.information.getSize()) {
            return false;
        }
        n2 = this.extractNumericValueFromBitArray(n, 7);
        if (n2 >= 64 && n2 < 116) {
            return true;
        }
        if (n + 8 > this.information.getSize()) {
            return false;
        }
        n = this.extractNumericValueFromBitArray(n, 8);
        boolean bl2 = bl;
        if (n < 232) return bl2;
        bl2 = bl;
        if (n >= 253) return bl2;
        return true;
    }

    private boolean isStillNumeric(int n) {
        int n2;
        int n3 = this.information.getSize();
        boolean bl = true;
        if (n + 7 > n3) {
            if (n + 4 > this.information.getSize()) return false;
            return bl;
        }
        n3 = n;
        while (n3 < (n2 = n + 3)) {
            if (this.information.get(n3)) {
                return true;
            }
            ++n3;
        }
        return this.information.get(n2);
    }

    private BlockParsedResult parseAlphaBlock() {
        while (this.isStillAlpha(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeAlphanumeric(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
            return new BlockParsedResult(false);
        }
        if (!this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) return new BlockParsedResult(false);
        if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
        } else {
            this.current.setPosition(this.information.getSize());
        }
        this.current.setIsoIec646();
        return new BlockParsedResult(false);
    }

    private DecodedInformation parseBlocks() throws FormatException {
        BlockParsedResult blockParsedResult;
        boolean bl;
        do {
            int n = this.current.getPosition();
            if (this.current.isAlpha()) {
                blockParsedResult = this.parseAlphaBlock();
                bl = blockParsedResult.isFinished();
            } else if (this.current.isIsoIec646()) {
                blockParsedResult = this.parseIsoIec646Block();
                bl = blockParsedResult.isFinished();
            } else {
                blockParsedResult = this.parseNumericBlock();
                bl = blockParsedResult.isFinished();
            }
            n = n != this.current.getPosition() ? 1 : 0;
            if (n != 0 || bl) continue;
            return blockParsedResult.getDecodedInformation();
        } while (!bl);
        return blockParsedResult.getDecodedInformation();
    }

    private BlockParsedResult parseIsoIec646Block() throws FormatException {
        while (this.isStillIsoIec646(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeIsoIec646(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
            return new BlockParsedResult(false);
        }
        if (!this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) return new BlockParsedResult(false);
        if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
        } else {
            this.current.setPosition(this.information.getSize());
        }
        this.current.setAlpha();
        return new BlockParsedResult(false);
    }

    private BlockParsedResult parseNumericBlock() throws FormatException {
        DecodedObject decodedObject;
        do {
            if (!this.isStillNumeric(this.current.getPosition())) {
                if (!this.isNumericToAlphaNumericLatch(this.current.getPosition())) return new BlockParsedResult(false);
                this.current.setAlpha();
                this.current.incrementPosition(4);
                return new BlockParsedResult(false);
            }
            decodedObject = this.decodeNumeric(this.current.getPosition());
            this.current.setPosition(decodedObject.getNewPosition());
            if (decodedObject.isFirstDigitFNC1()) {
                if (!decodedObject.isSecondDigitFNC1()) break;
                decodedObject = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
                return new BlockParsedResult((DecodedInformation)decodedObject, true);
            }
            this.buffer.append(decodedObject.getFirstDigit());
            if (decodedObject.isSecondDigitFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedObject.getSecondDigit());
        } while (true);
        decodedObject = new DecodedInformation(this.current.getPosition(), this.buffer.toString(), decodedObject.getSecondDigit());
        return new BlockParsedResult((DecodedInformation)decodedObject, true);
    }

    String decodeAllCodes(StringBuilder stringBuilder, int n) throws NotFoundException, FormatException {
        String string2 = null;
        do {
            DecodedInformation decodedInformation = this.decodeGeneralPurposeField(n, string2);
            string2 = FieldParser.parseFieldsInGeneralPurpose(decodedInformation.getNewString());
            if (string2 != null) {
                stringBuilder.append(string2);
            }
            string2 = decodedInformation.isRemaining() ? String.valueOf(decodedInformation.getRemainingValue()) : null;
            if (n == decodedInformation.getNewPosition()) {
                return stringBuilder.toString();
            }
            n = decodedInformation.getNewPosition();
        } while (true);
    }

    DecodedInformation decodeGeneralPurposeField(int n, String object) throws FormatException {
        this.buffer.setLength(0);
        if (object != null) {
            this.buffer.append((String)object);
        }
        this.current.setPosition(n);
        object = this.parseBlocks();
        if (object == null) return new DecodedInformation(this.current.getPosition(), this.buffer.toString());
        if (!((DecodedInformation)object).isRemaining()) return new DecodedInformation(this.current.getPosition(), this.buffer.toString());
        return new DecodedInformation(this.current.getPosition(), this.buffer.toString(), ((DecodedInformation)object).getRemainingValue());
    }

    int extractNumericValueFromBitArray(int n, int n2) {
        return GeneralAppIdDecoder.extractNumericValueFromBitArray(this.information, n, n2);
    }
}

