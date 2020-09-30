/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ReaderBasedJsonParser
extends ParserBase {
    private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
    private static final int FEAT_MASK_ALLOW_MISSING;
    private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
    private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
    private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
    private static final int FEAT_MASK_LEADING_ZEROS;
    private static final int FEAT_MASK_NON_NUM_NUMBERS;
    private static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    protected boolean _bufferRecyclable;
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected int _nameStartCol;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete;

    static {
        FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
        FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
        FEAT_MASK_NON_NUM_NUMBERS = JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS.getMask();
        FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
        FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
        FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
        FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
        FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }

    public ReaderBasedJsonParser(IOContext iOContext, int n, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, n);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = true;
    }

    public ReaderBasedJsonParser(IOContext iOContext, int n, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer, char[] arrc, int n2, int n3, boolean bl) {
        super(iOContext, n);
        this._reader = reader;
        this._inputBuffer = arrc;
        this._inputPtr = n2;
        this._inputEnd = n3;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = bl;
    }

    private final void _checkMatchEnd(String string2, int n, int n2) throws IOException {
        if (!Character.isJavaIdentifierPart((char)n2)) return;
        this._reportInvalidToken(string2.substring(0, n));
    }

    private void _closeScope(int n) throws JsonParseException {
        if (n == 93) {
            this._updateLocation();
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (n != 125) return;
        this._updateLocation();
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(n, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
        this._currToken = JsonToken.END_OBJECT;
    }

    private String _handleOddName2(int n, int n2, int[] object) throws IOException {
        int n3;
        char c;
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        char[] arrc = this._textBuffer.getCurrentSegment();
        n = this._textBuffer.getCurrentSegmentSize();
        int n4 = ((int[])object).length;
        while ((this._inputPtr < this._inputEnd || this._loadMore()) && !((c = this._inputBuffer[this._inputPtr]) >= n4 ? !Character.isJavaIdentifierPart(c) : object[c] != 0)) {
            ++this._inputPtr;
            n2 = n2 * 33 + c;
            n3 = n + 1;
            arrc[n] = c;
            if (n3 >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n = 0;
                continue;
            }
            n = n3;
        }
        this._textBuffer.setCurrentLength(n);
        object = this._textBuffer;
        arrc = ((TextBuffer)object).getTextBuffer();
        n = ((TextBuffer)object).getTextOffset();
        n3 = ((TextBuffer)object).size();
        return this._symbols.findSymbol(arrc, n, n3, n2);
    }

    private final void _isNextTokenNameYes(int n) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        this._updateLocation();
        if (n == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return;
        }
        if (n == 45) {
            this._nextToken = this._parseNegNumber();
            return;
        }
        if (n == 91) {
            this._nextToken = JsonToken.START_ARRAY;
            return;
        }
        if (n == 102) {
            this._matchToken("false", 1);
            this._nextToken = JsonToken.VALUE_FALSE;
            return;
        }
        if (n == 110) {
            this._matchToken("null", 1);
            this._nextToken = JsonToken.VALUE_NULL;
            return;
        }
        if (n == 116) {
            this._matchToken("true", 1);
            this._nextToken = JsonToken.VALUE_TRUE;
            return;
        }
        if (n == 123) {
            this._nextToken = JsonToken.START_OBJECT;
            return;
        }
        switch (n) {
            default: {
                this._nextToken = this._handleOddValue(n);
                return;
            }
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: 
        }
        this._nextToken = this._parsePosNumber(n);
    }

    private final void _matchFalse() throws IOException {
        char[] arrc;
        int n = this._inputPtr;
        if (n + 4 < this._inputEnd && (arrc = this._inputBuffer)[n] == 'a' && arrc[++n] == 'l' && arrc[++n] == 's' && arrc[++n] == 'e') {
            int n2 = n + 1;
            n = arrc[n2];
            if (n < 48 || n == 93 || n == 125) {
                this._inputPtr = n2;
                return;
            }
        }
        this._matchToken("false", 1);
    }

    private final void _matchNull() throws IOException {
        char[] arrc;
        int n = this._inputPtr;
        if (n + 3 < this._inputEnd && (arrc = this._inputBuffer)[n] == 'u' && arrc[++n] == 'l' && arrc[++n] == 'l') {
            int n2 = n + 1;
            n = arrc[n2];
            if (n < 48 || n == 93 || n == 125) {
                this._inputPtr = n2;
                return;
            }
        }
        this._matchToken("null", 1);
    }

    private final void _matchToken2(String string2, int n) throws IOException {
        int n2;
        int n3 = string2.length();
        do {
            if (this._inputPtr >= this._inputEnd && !this._loadMore() || this._inputBuffer[this._inputPtr] != string2.charAt(n)) {
                this._reportInvalidToken(string2.substring(0, n));
            }
            ++this._inputPtr;
            n = n2 = n + 1;
        } while (n2 < n3);
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return;
        }
        n = this._inputBuffer[this._inputPtr];
        if (n < 48) return;
        if (n == 93) return;
        if (n == 125) return;
        this._checkMatchEnd(string2, n2, n);
    }

    private final void _matchTrue() throws IOException {
        char[] arrc;
        int n = this._inputPtr;
        if (n + 3 < this._inputEnd && (arrc = this._inputBuffer)[n] == 'r' && arrc[++n] == 'u' && arrc[++n] == 'e') {
            int n2 = n + 1;
            n = arrc[n2];
            if (n < 48 || n == 93 || n == 125) {
                this._inputPtr = n2;
                return;
            }
        }
        this._matchToken("true", 1);
    }

    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken _nextTokenNotInObject(int n) throws IOException {
        JsonToken jsonToken;
        block12 : {
            block11 : {
                if (n == 34) {
                    JsonToken jsonToken2;
                    this._tokenIncomplete = true;
                    this._currToken = jsonToken2 = JsonToken.VALUE_STRING;
                    return jsonToken2;
                }
                if (n == 44) break block11;
                if (n == 45) {
                    JsonToken jsonToken3;
                    this._currToken = jsonToken3 = this._parseNegNumber();
                    return jsonToken3;
                }
                if (n == 91) {
                    JsonToken jsonToken4;
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                    this._currToken = jsonToken4 = JsonToken.START_ARRAY;
                    return jsonToken4;
                }
                if (n == 93) break block11;
                if (n == 102) {
                    JsonToken jsonToken5;
                    this._matchToken("false", 1);
                    this._currToken = jsonToken5 = JsonToken.VALUE_FALSE;
                    return jsonToken5;
                }
                if (n == 110) {
                    JsonToken jsonToken6;
                    this._matchToken("null", 1);
                    this._currToken = jsonToken6 = JsonToken.VALUE_NULL;
                    return jsonToken6;
                }
                if (n == 116) {
                    JsonToken jsonToken7;
                    this._matchToken("true", 1);
                    this._currToken = jsonToken7 = JsonToken.VALUE_TRUE;
                    return jsonToken7;
                }
                if (n == 123) {
                    JsonToken jsonToken8;
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    this._currToken = jsonToken8 = JsonToken.START_OBJECT;
                    return jsonToken8;
                }
                switch (n) {
                    default: {
                        break block12;
                    }
                    case 48: 
                    case 49: 
                    case 50: 
                    case 51: 
                    case 52: 
                    case 53: 
                    case 54: 
                    case 55: 
                    case 56: 
                    case 57: {
                        JsonToken jsonToken9;
                        this._currToken = jsonToken9 = this._parsePosNumber(n);
                        return jsonToken9;
                    }
                }
            }
            if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                JsonToken jsonToken10;
                --this._inputPtr;
                this._currToken = jsonToken10 = JsonToken.VALUE_NULL;
                return jsonToken10;
            }
        }
        this._currToken = jsonToken = this._handleOddValue(n);
        return jsonToken;
    }

    /*
     * Unable to fully structure code
     */
    private final JsonToken _parseFloat(int var1_1, int var2_2, int var3_3, boolean var4_4, int var5_5) throws IOException {
        block12 : {
            block14 : {
                block13 : {
                    block11 : {
                        var6_6 = this._inputEnd;
                        var7_7 = 0;
                        var8_8 = 0;
                        var9_9 = 0;
                        if (var1_1 != 46) {
                            var12_12 = var3_3;
                            var3_3 = 0;
                            var10_10 = var1_1;
                            var1_1 = var12_12;
                        } else {
                            var1_1 = 0;
                            var10_10 = var3_3;
                            do {
                                if (var10_10 >= var6_6) {
                                    return this._parseNumber2(var4_4, var2_2);
                                }
                                var11_11 = this._inputBuffer;
                                var3_3 = var10_10 + 1;
                                if ((var10_10 = var11_11[var10_10]) < 48 || var10_10 > 57) break;
                                ++var1_1;
                                var10_10 = var3_3;
                            } while (true);
                            if (var1_1 == 0) {
                                this.reportUnexpectedNumberChar(var10_10, "Decimal point not followed by a digit");
                            }
                            var12_12 = var1_1;
                            var1_1 = var3_3;
                            var3_3 = var12_12;
                        }
                        if (var10_10 == 101) break block11;
                        var13_13 = var1_1;
                        var14_14 = var10_10;
                        var15_15 = var3_3;
                        if (var10_10 != 69) break block12;
                    }
                    if (var1_1 >= var6_6) {
                        this._inputPtr = var2_2;
                        return this._parseNumber2(var4_4, var2_2);
                    }
                    var11_11 = this._inputBuffer;
                    var15_15 = var1_1 + 1;
                    var8_8 = var11_11[var1_1];
                    if (var8_8 == 45) break block13;
                    var1_1 = var9_9;
                    var12_12 = var15_15;
                    var10_10 = var8_8;
                    var14_14 = var3_3;
                    if (var8_8 != 43) break block14;
                }
                if (var15_15 >= var6_6) {
                    this._inputPtr = var2_2;
                    return this._parseNumber2(var4_4, var2_2);
                }
                var11_11 = this._inputBuffer;
                var1_1 = var15_15 + 1;
                var10_10 = var11_11[var15_15];
                var12_12 = var7_7;
                ** GOTO lbl60
            }
            do {
                var3_3 = var12_12;
                var12_12 = var1_1;
                var1_1 = var3_3;
                var3_3 = var14_14;
lbl60: // 2 sources:
                if (var10_10 > 57 || var10_10 < 48) break;
                var14_14 = var12_12 + 1;
                if (var1_1 >= var6_6) {
                    this._inputPtr = var2_2;
                    return this._parseNumber2(var4_4, var2_2);
                }
                var11_11 = this._inputBuffer;
                var12_12 = var1_1 + 1;
                var10_10 = var11_11[var1_1];
                var1_1 = var14_14;
                var14_14 = var3_3;
            } while (true);
            var8_8 = var12_12;
            var13_13 = var1_1;
            var14_14 = var10_10;
            var15_15 = var3_3;
            if (var12_12 == 0) {
                this.reportUnexpectedNumberChar(var10_10, "Exponent indicator not followed by a digit");
                var15_15 = var3_3;
                var14_14 = var10_10;
                var13_13 = var1_1;
                var8_8 = var12_12;
            }
        }
        this._inputPtr = var1_1 = var13_13 - 1;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(var14_14);
        }
        this._textBuffer.resetWithShared(this._inputBuffer, var2_2, var1_1 - var2_2);
        return this.resetFloat(var4_4, var5_5, var15_15, var8_8);
    }

    private String _parseName2(int n, int n2, int n3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, n, this._inputPtr - n);
        Object object = this._textBuffer.getCurrentSegment();
        n = this._textBuffer.getCurrentSegmentSize();
        do {
            int n4;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            char[] arrc = this._inputBuffer;
            int n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            n5 = n4 = arrc[n5];
            if (n4 <= 92) {
                if (n4 == 92) {
                    n5 = this._decodeEscaped();
                } else {
                    n5 = n4;
                    if (n4 <= n3) {
                        if (n4 == n3) {
                            this._textBuffer.setCurrentLength(n);
                            object = this._textBuffer;
                            arrc = ((TextBuffer)object).getTextBuffer();
                            n = ((TextBuffer)object).getTextOffset();
                            n3 = ((TextBuffer)object).size();
                            return this._symbols.findSymbol(arrc, n, n3, n2);
                        }
                        n5 = n4;
                        if (n4 < 32) {
                            this._throwUnquotedSpace(n4, "name");
                            n5 = n4;
                        }
                    }
                }
            }
            n2 = n2 * 33 + n5;
            n4 = n + 1;
            object[n] = (char)n5;
            if (n4 >= ((char[])object).length) {
                object = this._textBuffer.finishCurrentSegment();
                n = 0;
                continue;
            }
            n = n4;
        } while (true);
    }

    private final JsonToken _parseNumber2(boolean bl, int n) throws IOException {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        block39 : {
            int n8;
            int n9;
            int n10;
            block37 : {
                char[] arrc;
                char[] arrc2;
                block41 : {
                    block40 : {
                        block38 : {
                            block36 : {
                                n9 = n;
                                if (bl) {
                                    n9 = n + 1;
                                }
                                this._inputPtr = n9;
                                arrc = this._textBuffer.emptyAndGetCurrentSegment();
                                n7 = 0;
                                if (bl) {
                                    arrc[0] = (char)45;
                                    n8 = 1;
                                } else {
                                    n8 = 0;
                                }
                                if (this._inputPtr < this._inputEnd) {
                                    arrc2 = this._inputBuffer;
                                    n = this._inputPtr;
                                    this._inputPtr = n + 1;
                                    n = arrc2[n];
                                } else {
                                    n = this.getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
                                }
                                n9 = n;
                                if (n == 48) {
                                    n9 = this._verifyNoLeadingZeroes();
                                }
                                n10 = 0;
                                while (n9 >= 48 && n9 <= 57) {
                                    ++n10;
                                    n4 = n8;
                                    arrc2 = arrc;
                                    if (n8 >= arrc.length) {
                                        arrc2 = this._textBuffer.finishCurrentSegment();
                                        n4 = 0;
                                    }
                                    n = n4 + 1;
                                    arrc2[n4] = (char)n9;
                                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                        n9 = 0;
                                        n4 = 1;
                                        n8 = n;
                                        n3 = n10;
                                        n = n4;
                                        break block36;
                                    }
                                    arrc = this._inputBuffer;
                                    n9 = this._inputPtr;
                                    this._inputPtr = n9 + 1;
                                    n9 = arrc[n9];
                                    n8 = n;
                                    arrc = arrc2;
                                }
                                n = 0;
                                arrc2 = arrc;
                                n3 = n10;
                            }
                            if (n3 == 0) {
                                return this._handleInvalidNumberStart(n9, bl);
                            }
                            if (n9 != 46) {
                                n5 = 0;
                                arrc = arrc2;
                                n10 = n9;
                            } else {
                                n10 = n8;
                                arrc = arrc2;
                                if (n8 >= arrc2.length) {
                                    arrc = this._textBuffer.finishCurrentSegment();
                                    n10 = 0;
                                }
                                arrc[n10] = (char)n9;
                                n8 = n10 + 1;
                                n4 = 0;
                                arrc2 = arrc;
                                n6 = n9;
                                n9 = n8;
                                do {
                                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                        n2 = 1;
                                        break;
                                    }
                                    arrc = this._inputBuffer;
                                    n8 = this._inputPtr;
                                    this._inputPtr = n8 + 1;
                                    n6 = n8 = arrc[n8];
                                    n2 = n;
                                    if (n8 < 48) break;
                                    if (n8 > 57) {
                                        n6 = n8;
                                        n2 = n;
                                        break;
                                    }
                                    ++n4;
                                    n10 = n9;
                                    arrc = arrc2;
                                    if (n9 >= arrc2.length) {
                                        arrc = this._textBuffer.finishCurrentSegment();
                                        n10 = 0;
                                    }
                                    arrc[n10] = (char)n8;
                                    n9 = n10 + 1;
                                    n6 = n8;
                                    arrc2 = arrc;
                                } while (true);
                                n8 = n9;
                                n10 = n6;
                                n = n2;
                                n5 = n4;
                                arrc = arrc2;
                                if (n4 == 0) {
                                    this.reportUnexpectedNumberChar(n6, "Decimal point not followed by a digit");
                                    n8 = n9;
                                    n10 = n6;
                                    n = n2;
                                    n5 = n4;
                                    arrc = arrc2;
                                }
                            }
                            if (n10 == 101) break block38;
                            n2 = n7;
                            n6 = n8;
                            n7 = n10;
                            n4 = n;
                            if (n10 != 69) break block39;
                        }
                        n9 = n8;
                        arrc2 = arrc;
                        if (n8 >= arrc.length) {
                            arrc2 = this._textBuffer.finishCurrentSegment();
                            n9 = 0;
                        }
                        n4 = n9 + 1;
                        arrc2[n9] = (char)n10;
                        if (this._inputPtr < this._inputEnd) {
                            arrc = this._inputBuffer;
                            n9 = this._inputPtr;
                            this._inputPtr = n9 + 1;
                            n8 = arrc[n9];
                        } else {
                            n8 = this.getNextChar("expected a digit for number exponent");
                        }
                        if (n8 == 45) break block40;
                        n10 = n8;
                        n9 = n4;
                        arrc = arrc2;
                        if (n8 != 43) break block41;
                    }
                    n9 = n4;
                    arrc = arrc2;
                    if (n4 >= arrc2.length) {
                        arrc = this._textBuffer.finishCurrentSegment();
                        n9 = 0;
                    }
                    arrc[n9] = (char)n8;
                    if (this._inputPtr < this._inputEnd) {
                        arrc2 = this._inputBuffer;
                        n8 = this._inputPtr;
                        this._inputPtr = n8 + 1;
                        n10 = arrc2[n8];
                    } else {
                        n10 = this.getNextChar("expected a digit for number exponent");
                    }
                    ++n9;
                }
                n8 = n10;
                n10 = 0;
                while (n8 <= 57 && n8 >= 48) {
                    n4 = n10 + 1;
                    n6 = n9;
                    arrc2 = arrc;
                    if (n9 >= arrc.length) {
                        arrc2 = this._textBuffer.finishCurrentSegment();
                        n6 = 0;
                    }
                    n10 = n6 + 1;
                    arrc2[n6] = (char)n8;
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        n = n4;
                        n9 = 1;
                        break block37;
                    }
                    arrc = this._inputBuffer;
                    n9 = this._inputPtr;
                    this._inputPtr = n9 + 1;
                    n8 = arrc[n9];
                    n9 = n10;
                    n10 = n4;
                    arrc = arrc2;
                }
                n4 = n10;
                n10 = n9;
                n9 = n;
                n = n4;
            }
            n2 = n;
            n6 = n10;
            n7 = n8;
            n4 = n9;
            if (n == 0) {
                this.reportUnexpectedNumberChar(n8, "Exponent indicator not followed by a digit");
                n4 = n9;
                n7 = n8;
                n6 = n10;
                n2 = n;
            }
        }
        if (n4 == 0) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(n7);
            }
        }
        this._textBuffer.setCurrentLength(n6);
        return this.reset(bl, n3, n5, n2);
    }

    private final int _skipAfterComma2() throws IOException {
        do {
            Object object;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected end-of-input within/between ");
                ((StringBuilder)object).append(this._parsingContext.typeDesc());
                ((StringBuilder)object).append(" entries");
                throw this._constructError(((StringBuilder)object).toString());
            }
            object = this._inputBuffer;
            Object object2 = this._inputPtr;
            this._inputPtr = object2 + 1;
            if ((object2 = (Object)object[object2]) > 32) {
                if (object2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (object2 != 35) return object2;
                if (!this._skipYAMLComment()) return object2;
                continue;
            }
            if (object2 >= 32) continue;
            if (object2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (object2 == 13) {
                this._skipCR();
                continue;
            }
            if (object2 == 9) continue;
            this._throwInvalidSpace((int)object2);
        } while (true);
    }

    private void _skipCComment() throws IOException {
        do {
            int n;
            block7 : {
                block8 : {
                    block6 : {
                        if (this._inputPtr >= this._inputEnd && !this._loadMore()) break block6;
                        char[] arrc = this._inputBuffer;
                        n = this._inputPtr;
                        this._inputPtr = n + 1;
                        if ((n = arrc[n]) > 42) continue;
                        if (n != 42) break block7;
                        if (this._inputPtr < this._inputEnd || this._loadMore()) break block8;
                    }
                    this._reportInvalidEOF(" in a comment", null);
                    return;
                }
                if (this._inputBuffer[this._inputPtr] != '/') continue;
                ++this._inputPtr;
                return;
            }
            if (n >= 32) continue;
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n == 13) {
                this._skipCR();
                continue;
            }
            if (n == 9) continue;
            this._throwInvalidSpace(n);
        } while (true);
    }

    private final int _skipColon() throws IOException {
        int n;
        char[] arrc;
        block14 : {
            block13 : {
                if (this._inputPtr + 4 >= this._inputEnd) {
                    return this._skipColon2(false);
                }
                int n2 = this._inputBuffer[this._inputPtr];
                if (n2 == 58) {
                    int n3;
                    char[] arrc2 = this._inputBuffer;
                    this._inputPtr = n3 = this._inputPtr + 1;
                    if ((n3 = arrc2[n3]) > 32) {
                        if (n3 == 47) return this._skipColon2(true);
                        if (n3 == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return n3;
                    }
                    if (n3 != 32) {
                        if (n3 != 9) return this._skipColon2(true);
                    }
                    arrc2 = this._inputBuffer;
                    this._inputPtr = n3 = this._inputPtr + 1;
                    if ((n3 = arrc2[n3]) <= 32) return this._skipColon2(true);
                    if (n3 == 47) return this._skipColon2(true);
                    if (n3 == 35) {
                        return this._skipColon2(true);
                    }
                    ++this._inputPtr;
                    return n3;
                }
                if (n2 == 32) break block13;
                n = n2;
                if (n2 != 9) break block14;
            }
            arrc = this._inputBuffer;
            this._inputPtr = n = this._inputPtr + 1;
            n = arrc[n];
        }
        if (n != 58) return this._skipColon2(false);
        arrc = this._inputBuffer;
        this._inputPtr = n = this._inputPtr + 1;
        if ((n = arrc[n]) > 32) {
            if (n == 47) return this._skipColon2(true);
            if (n == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return n;
        }
        if (n != 32) {
            if (n != 9) return this._skipColon2(true);
        }
        arrc = this._inputBuffer;
        this._inputPtr = n = this._inputPtr + 1;
        if ((n = arrc[n]) <= 32) return this._skipColon2(true);
        if (n == 47) return this._skipColon2(true);
        if (n == 35) {
            return this._skipColon2(true);
        }
        ++this._inputPtr;
        return n;
    }

    private final int _skipColon2(boolean bl) throws IOException {
        do {
            Object object;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" within/between ");
                ((StringBuilder)object).append(this._parsingContext.typeDesc());
                ((StringBuilder)object).append(" entries");
                this._reportInvalidEOF(((StringBuilder)object).toString(), null);
                return -1;
            }
            object = this._inputBuffer;
            Object object2 = this._inputPtr;
            this._inputPtr = object2 + 1;
            if ((object2 = (Object)object[object2]) > 32) {
                if (object2 == 47) {
                    this._skipComment();
                    continue;
                }
                if (object2 == 35 && this._skipYAMLComment()) continue;
                if (bl) {
                    return object2;
                }
                if (object2 != 58) {
                    this._reportUnexpectedChar((int)object2, "was expecting a colon to separate field name and value");
                }
                bl = true;
                continue;
            }
            if (object2 >= 32) continue;
            if (object2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (object2 == 13) {
                this._skipCR();
                continue;
            }
            if (object2 == 9) continue;
            this._throwInvalidSpace((int)object2);
        } while (true);
    }

    private final int _skipColonFast(int n) throws IOException {
        int n2;
        char[] arrc;
        int n3;
        block24 : {
            block23 : {
                int n4;
                block19 : {
                    block21 : {
                        int n5;
                        block22 : {
                            block20 : {
                                arrc = this._inputBuffer;
                                n2 = n + 1;
                                n4 = arrc[n];
                                if (n4 != 58) break block19;
                                n5 = n2 + 1;
                                if ((n2 = arrc[n2]) <= 32) break block20;
                                n = n5;
                                if (n2 != 47) {
                                    n = n5;
                                    if (n2 != 35) {
                                        this._inputPtr = n5;
                                        return n2;
                                    }
                                }
                                break block21;
                            }
                            if (n2 == 32) break block22;
                            n = n5;
                            if (n2 != 9) break block21;
                        }
                        arrc = this._inputBuffer;
                        n = n5 + 1;
                        if ((n5 = arrc[n5]) > 32 && n5 != 47 && n5 != 35) {
                            this._inputPtr = n;
                            return n5;
                        }
                    }
                    this._inputPtr = n - 1;
                    return this._skipColon2(true);
                }
                if (n4 == 32) break block23;
                n = n2;
                n3 = n4;
                if (n4 != 9) break block24;
            }
            n3 = this._inputBuffer[n2];
            n = n2 + 1;
        }
        boolean bl = n3 == 58;
        n3 = n;
        if (bl) {
            arrc = this._inputBuffer;
            n3 = n + 1;
            if ((n = arrc[n]) > 32) {
                if (n != 47 && n != 35) {
                    this._inputPtr = n3;
                    return n;
                }
            } else if (n == 32 || n == 9) {
                arrc = this._inputBuffer;
                n = n3 + 1;
                n2 = arrc[n3];
                n3 = n;
                if (n2 > 32) {
                    n3 = n;
                    if (n2 != 47) {
                        n3 = n;
                        if (n2 != 35) {
                            this._inputPtr = n;
                            return n2;
                        }
                    }
                }
            }
        }
        this._inputPtr = n3 - 1;
        return this._skipColon2(bl);
    }

    private final int _skipComma(int n) throws IOException {
        char[] arrc;
        if (n != 44) {
            arrc = new StringBuilder();
            arrc.append("was expecting comma to separate ");
            arrc.append(this._parsingContext.typeDesc());
            arrc.append(" entries");
            this._reportUnexpectedChar(n, arrc.toString());
        }
        while (this._inputPtr < this._inputEnd) {
            arrc = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrc[n]) > 32) {
                if (n != 47) {
                    if (n != 35) return n;
                }
                --this._inputPtr;
                return this._skipAfterComma2();
            }
            if (n >= 32) continue;
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n == 13) {
                this._skipCR();
                continue;
            }
            if (n == 9) continue;
            this._throwInvalidSpace(n);
        }
        return this._skipAfterComma2();
    }

    private void _skipComment() throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        char[] arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrc[n]) == 47) {
            this._skipLine();
            return;
        }
        if (n == 42) {
            this._skipCComment();
            return;
        }
        this._reportUnexpectedChar(n, "was expecting either '*' or '/' for a comment");
    }

    private void _skipLine() throws IOException {
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this._loadMore()) return;
            }
            char[] arrc = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrc[n]) >= 32) continue;
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                return;
            }
            if (n == 13) {
                this._skipCR();
                return;
            }
            if (n == 9) continue;
            this._throwInvalidSpace(n);
        } while (true);
    }

    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        char[] arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrc[n]) > 32) {
            if (n != 47) {
                if (n != 35) return n;
            }
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        if (n != 32) {
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
            } else if (n == 13) {
                this._skipCR();
            } else if (n != 9) {
                this._throwInvalidSpace(n);
            }
        }
        while (this._inputPtr < this._inputEnd) {
            arrc = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrc[n]) > 32) {
                if (n != 47) {
                    if (n != 35) return n;
                }
                --this._inputPtr;
                return this._skipWSOrEnd2();
            }
            if (n == 32) continue;
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n == 13) {
                this._skipCR();
                continue;
            }
            if (n == 9) continue;
            this._throwInvalidSpace(n);
        }
        return this._skipWSOrEnd2();
    }

    private int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            char[] arrc = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrc[n]) > 32) {
                if (n == 47) {
                    this._skipComment();
                    continue;
                }
                if (n != 35) return n;
                if (!this._skipYAMLComment()) return n;
                continue;
            }
            if (n == 32) continue;
            if (n == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                continue;
            }
            if (n == 13) {
                this._skipCR();
                continue;
            }
            if (n == 9) continue;
            this._throwInvalidSpace(n);
        }
        return this._eofAsNextChar();
    }

    private boolean _skipYAMLComment() throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
            return false;
        }
        this._skipLine();
        return true;
    }

    private final void _updateLocation() {
        int n = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + (long)n;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = n - this._currInputRowStart;
    }

    private final void _updateNameLocation() {
        int n = this._inputPtr;
        this._nameStartOffset = n;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = n - this._currInputRowStart;
    }

    private char _verifyNLZ2() throws IOException {
        char c;
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return '0';
        }
        char c2 = this._inputBuffer[this._inputPtr];
        if (c2 < '0') return '0';
        if (c2 > '9') {
            return '0';
        }
        if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        char c3 = c2;
        if (c2 != '0') return c3;
        do {
            if (this._inputPtr >= this._inputEnd) {
                c3 = c2;
                if (!this._loadMore()) return c3;
            }
            if ((c = this._inputBuffer[this._inputPtr]) < '0') return '0';
            if (c > '9') {
                return '0';
            }
            ++this._inputPtr;
            c2 = c;
        } while (c == '0');
        return c;
    }

    private final char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd) return this._verifyNLZ2();
        char c = this._inputBuffer[this._inputPtr];
        if (c < '0') return '0';
        if (c <= '9') return this._verifyNLZ2();
        return '0';
    }

    private final void _verifyRootSpace(int n) throws IOException {
        ++this._inputPtr;
        if (n == 9) return;
        if (n == 10) {
            ++this._currInputRow;
            this._currInputRowStart = this._inputPtr;
            return;
        }
        if (n != 13) {
            if (n == 32) return;
            this._reportMissingRootWS(n);
            return;
        }
        this._skipCR();
    }

    @Override
    protected void _closeInput() throws IOException {
        if (this._reader == null) return;
        if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._reader.close();
        }
        this._reader = null;
    }

    protected byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        Object object = this._getByteArrayBuilder();
        do {
            int n;
            int n2;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char[] arrc = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            char c = arrc[n3];
            if (c <= ' ') continue;
            n3 = n2 = base64Variant.decodeBase64Char(c);
            if (n2 < 0) {
                if (c == '\"') {
                    return ((ByteArrayBuilder)object).toByteArray();
                }
                n3 = n2 = this._decodeBase64Escape(base64Variant, c, 0);
                if (n2 < 0) continue;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            c = arrc[n2];
            n2 = n = base64Variant.decodeBase64Char(c);
            if (n < 0) {
                n2 = this._decodeBase64Escape(base64Variant, c, 1);
            }
            int n4 = n3 << 6 | n2;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c = arrc[n3];
            n2 = n = base64Variant.decodeBase64Char(c);
            if (n < 0) {
                n3 = n;
                if (n != -2) {
                    if (c == '\"') {
                        ((ByteArrayBuilder)object).append(n4 >> 4);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        --this._inputPtr;
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, c, 2);
                }
                n2 = n3;
                if (n3 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this._loadMoreGuaranteed();
                    }
                    arrc = this._inputBuffer;
                    n3 = this._inputPtr;
                    this._inputPtr = n3 + 1;
                    c = arrc[n3];
                    if (!base64Variant.usesPaddingChar(c) && this._decodeBase64Escape(base64Variant, c, 3) != -2) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("expected padding character '");
                        ((StringBuilder)object).append(base64Variant.getPaddingChar());
                        ((StringBuilder)object).append("'");
                        throw this.reportInvalidBase64Char(base64Variant, c, 3, ((StringBuilder)object).toString());
                    }
                    ((ByteArrayBuilder)object).append(n4 >> 4);
                    continue;
                }
            }
            n4 = n4 << 6 | n2;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrc = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            c = arrc[n3];
            n2 = n = base64Variant.decodeBase64Char(c);
            if (n < 0) {
                n3 = n;
                if (n != -2) {
                    if (c == '\"') {
                        ((ByteArrayBuilder)object).appendTwoBytes(n4 >> 2);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        --this._inputPtr;
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, c, 3);
                }
                n2 = n3;
                if (n3 == -2) {
                    ((ByteArrayBuilder)object).appendTwoBytes(n4 >> 2);
                    continue;
                }
            }
            ((ByteArrayBuilder)object).appendThreeBytes(n4 << 6 | n2);
        } while (true);
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        int n;
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        char[] arrc = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        int n3 = n = arrc[n2];
        if (n == 34) return (char)n3;
        n3 = n;
        if (n == 47) return (char)n3;
        n3 = n;
        if (n == 92) return (char)n3;
        if (n == 98) {
            n2 = 8;
            return (char)n2;
        }
        if (n == 102) return '\f';
        if (n == 110) return '\n';
        if (n == 114) return '\r';
        if (n == 116) return '\t';
        if (n != 117) {
            return this._handleUnrecognizedCharacterEscape((char)n);
        }
        int n4 = 0;
        n2 = 0;
        while (n4 < 4) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
            }
            arrc = this._inputBuffer;
            int n5 = this._inputPtr;
            this._inputPtr = n5 + 1;
            char c = arrc[n5];
            n5 = CharTypes.charToHex(c);
            if (n5 < 0) {
                this._reportUnexpectedChar(c, "expected a hex-digit for character escape sequence");
            }
            n2 = n2 << 4 | n5;
            ++n4;
        }
        return (char)n2;
    }

    @Override
    protected final void _finishString() throws IOException {
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        int n3 = n;
        if (n < n2) {
            int[] arrn = _icLatin1;
            int n4 = arrn.length;
            do {
                char c;
                if ((c = this._inputBuffer[n]) < n4 && arrn[c] != 0) {
                    n3 = n;
                    if (c != '\"') break;
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, n - this._inputPtr);
                    this._inputPtr = n + 1;
                    return;
                }
                n = n3 = n + 1;
            } while (n3 < n2);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, n3 - this._inputPtr);
        this._inputPtr = n3;
        this._finishString2();
    }

    protected void _finishString2() throws IOException {
        char[] arrc = this._textBuffer.getCurrentSegment();
        int n = this._textBuffer.getCurrentSegmentSize();
        int[] arrn = _icLatin1;
        int n2 = arrn.length;
        do {
            int n3;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] arrc2 = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            n4 = n3 = arrc2[n4];
            if (n3 < n2) {
                n4 = n3;
                if (arrn[n3] != 0) {
                    if (n3 == 34) {
                        this._textBuffer.setCurrentLength(n);
                        return;
                    }
                    if (n3 == 92) {
                        n4 = this._decodeEscaped();
                    } else {
                        n4 = n3;
                        if (n3 < 32) {
                            this._throwUnquotedSpace(n3, "string value");
                            n4 = n3;
                        }
                    }
                }
            }
            arrc2 = arrc;
            n3 = n;
            if (n >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n3 = 0;
            }
            arrc2[n3] = (char)n4;
            n = n3 + 1;
            arrc = arrc2;
        } while (true);
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        int n = jsonToken.id();
        if (n == 5) return this._parsingContext.getCurrentName();
        if (n == 6) return this._textBuffer.contentsAsString();
        if (n == 7) return this._textBuffer.contentsAsString();
        if (n == 8) return this._textBuffer.contentsAsString();
        return jsonToken.asString();
    }

    protected JsonToken _handleApos() throws IOException {
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n = this._textBuffer.getCurrentSegmentSize();
        do {
            int n2;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] arrc2 = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            n3 = n2 = arrc2[n3];
            if (n2 <= 92) {
                if (n2 == 92) {
                    n3 = this._decodeEscaped();
                } else {
                    n3 = n2;
                    if (n2 <= 39) {
                        if (n2 == 39) {
                            this._textBuffer.setCurrentLength(n);
                            return JsonToken.VALUE_STRING;
                        }
                        n3 = n2;
                        if (n2 < 32) {
                            this._throwUnquotedSpace(n2, "string value");
                            n3 = n2;
                        }
                    }
                }
            }
            arrc2 = arrc;
            n2 = n;
            if (n >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n2 = 0;
            }
            arrc2[n2] = (char)n3;
            n = n2 + 1;
            arrc = arrc2;
        } while (true);
    }

    protected JsonToken _handleInvalidNumberStart(int n, boolean bl) throws IOException {
        int n2 = n;
        if (n == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
            }
            Object object = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n = object[n];
            double d = Double.NEGATIVE_INFINITY;
            if (n == 78) {
                object = bl ? "-INF" : "+INF";
                this._matchToken((String)object, 3);
                if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                    if (bl) {
                        return this.resetAsNaN((String)object, d);
                    }
                    d = Double.POSITIVE_INFINITY;
                    return this.resetAsNaN((String)object, d);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Non-standard token '");
                stringBuilder.append((String)object);
                stringBuilder.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                this._reportError(stringBuilder.toString());
                n2 = n;
            } else {
                n2 = n;
                if (n == 110) {
                    object = bl ? "-Infinity" : "+Infinity";
                    this._matchToken((String)object, 3);
                    if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                        if (bl) {
                            return this.resetAsNaN((String)object, d);
                        }
                        d = Double.POSITIVE_INFINITY;
                        return this.resetAsNaN((String)object, d);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Non-standard token '");
                    stringBuilder.append((String)object);
                    stringBuilder.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    this._reportError(stringBuilder.toString());
                    n2 = n;
                }
            }
        }
        this.reportUnexpectedNumberChar(n2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    protected String _handleOddName(int n) throws IOException {
        int[] arrn;
        int n2;
        if (n == 39 && (this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._parseAposName();
        }
        if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar(n, "was expecting double-quote to start field name");
        }
        boolean bl = n < (n2 = (arrn = CharTypes.getInputCodeLatin1JsNames()).length) ? arrn[n] == 0 : Character.isJavaIdentifierPart((char)n);
        if (!bl) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int n3 = this._inputPtr;
        int n4 = this._hashSeed;
        int n5 = this._inputEnd;
        int n6 = n4;
        n = n3;
        if (n3 < n5) {
            n = n3;
            do {
                if ((n6 = this._inputBuffer[n]) < n2) {
                    if (arrn[n6] != 0) {
                        n6 = this._inputPtr - 1;
                        this._inputPtr = n;
                        return this._symbols.findSymbol(this._inputBuffer, n6, n - n6, n4);
                    }
                } else if (!Character.isJavaIdentifierPart((char)n6)) {
                    n6 = this._inputPtr - 1;
                    this._inputPtr = n;
                    return this._symbols.findSymbol(this._inputBuffer, n6, n - n6, n4);
                }
                n6 = n4 * 33 + n6;
                n3 = n + 1;
                n4 = n6;
                n = n3;
            } while (n3 < n5);
            n = n3;
        }
        n4 = this._inputPtr;
        this._inputPtr = n;
        return this._handleOddName2(n4 - 1, n6, arrn);
    }

    protected JsonToken _handleOddValue(int n) throws IOException {
        StringBuilder stringBuilder;
        block14 : {
            block8 : {
                block9 : {
                    block10 : {
                        block13 : {
                            block11 : {
                                block12 : {
                                    if (n == 39) break block8;
                                    if (n == 73) break block9;
                                    if (n == 78) break block10;
                                    if (n == 93) break block11;
                                    if (n == 43) break block12;
                                    if (n == 44) break block13;
                                    break block14;
                                }
                                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                    this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                                }
                                char[] arrc = this._inputBuffer;
                                n = this._inputPtr;
                                this._inputPtr = n + 1;
                                return this._handleInvalidNumberStart(arrc[n], false);
                            }
                            if (!this._parsingContext.inArray()) break block14;
                        }
                        if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                            --this._inputPtr;
                            return JsonToken.VALUE_NULL;
                        }
                        break block14;
                    }
                    this._matchToken("NaN", 1);
                    if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                        return this.resetAsNaN("NaN", Double.NaN);
                    }
                    this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break block14;
                }
                this._matchToken("Infinity", 1);
                if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break block14;
            }
            if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
                return this._handleApos();
            }
        }
        if (Character.isJavaIdentifierStart(n)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append((char)n);
            this._reportInvalidToken(stringBuilder.toString(), this._validJsonTokenList());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("expected a valid value ");
        stringBuilder.append(this._validJsonValueList());
        this._reportUnexpectedChar(n, stringBuilder.toString());
        return null;
    }

    /*
     * Unable to fully structure code
     */
    protected boolean _isNextTokenNameMaybe(int var1_1, String var2_2) throws IOException {
        var3_3 = var1_1 == 34 ? this._parseName() : this._handleOddName(var1_1);
        this._parsingContext.setCurrentName(var3_3);
        this._currToken = JsonToken.FIELD_NAME;
        var1_1 = this._skipColon();
        this._updateLocation();
        if (var1_1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return var2_2.equals(var3_3);
        }
        if (var1_1 != 45) {
            if (var1_1 != 91) {
                if (var1_1 != 102) {
                    if (var1_1 != 110) {
                        if (var1_1 != 116) {
                            if (var1_1 != 123) {
                                switch (var1_1) {
                                    default: {
                                        var4_4 = this._handleOddValue(var1_1);
                                        ** break;
                                    }
                                    case 48: 
                                    case 49: 
                                    case 50: 
                                    case 51: 
                                    case 52: 
                                    case 53: 
                                    case 54: 
                                    case 55: 
                                    case 56: 
                                    case 57: 
                                }
                                var4_4 = this._parsePosNumber(var1_1);
                                ** break;
lbl23: // 2 sources:
                            } else {
                                var4_4 = JsonToken.START_OBJECT;
                            }
                        } else {
                            this._matchTrue();
                            var4_4 = JsonToken.VALUE_TRUE;
                        }
                    } else {
                        this._matchNull();
                        var4_4 = JsonToken.VALUE_NULL;
                    }
                } else {
                    this._matchFalse();
                    var4_4 = JsonToken.VALUE_FALSE;
                }
            } else {
                var4_4 = JsonToken.START_ARRAY;
            }
        } else {
            var4_4 = this._parseNegNumber();
        }
        this._nextToken = var4_4;
        return var2_2.equals(var3_3);
    }

    protected boolean _loadMore() throws IOException {
        int n = this._inputEnd;
        Object object = this._reader;
        if (object == null) return false;
        char[] arrc = this._inputBuffer;
        int n2 = ((Reader)object).read(arrc, 0, arrc.length);
        if (n2 > 0) {
            this._inputPtr = 0;
            this._inputEnd = n2;
            long l = this._currInputProcessed;
            long l2 = n;
            this._currInputProcessed = l + l2;
            this._currInputRowStart -= n;
            this._nameStartOffset -= l2;
            return true;
        }
        this._closeInput();
        if (n2 != 0) {
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Reader returned 0 characters when trying to read ");
        ((StringBuilder)object).append(this._inputEnd);
        throw new IOException(((StringBuilder)object).toString());
    }

    protected void _loadMoreGuaranteed() throws IOException {
        if (this._loadMore()) return;
        this._reportInvalidEOF();
    }

    protected final void _matchToken(String string2, int n) throws IOException {
        int n2 = string2.length();
        int n3 = n;
        if (this._inputPtr + n2 >= this._inputEnd) {
            this._matchToken2(string2, n);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != string2.charAt(n3)) {
                this._reportInvalidToken(string2.substring(0, n3));
            }
            ++this._inputPtr;
            n3 = n = n3 + 1;
        } while (n < n2);
        n3 = this._inputBuffer[this._inputPtr];
        if (n3 < 48) return;
        if (n3 == 93) return;
        if (n3 == 125) return;
        this._checkMatchEnd(string2, n, n3);
    }

    protected String _parseAposName() throws IOException {
        int n;
        int n2;
        int n3;
        block3 : {
            n2 = this._inputPtr;
            int n4 = this._hashSeed;
            int n5 = this._inputEnd;
            n = n2;
            n3 = n4;
            if (n2 < n5) {
                int[] arrn = _icLatin1;
                int n6 = arrn.length;
                n3 = n4;
                n = n2;
                do {
                    if ((n2 = this._inputBuffer[n]) == 39) {
                        n2 = this._inputPtr;
                        this._inputPtr = n + 1;
                        return this._symbols.findSymbol(this._inputBuffer, n2, n - n2, n3);
                    }
                    if (n2 < n6 && arrn[n2] != 0) break block3;
                    n4 = n3 * 33 + n2;
                    n = n2 = n + 1;
                    n3 = n4;
                } while (n2 < n5);
                n3 = n4;
                n = n2;
            }
        }
        n2 = this._inputPtr;
        this._inputPtr = n;
        return this._parseName2(n2, n3, 39);
    }

    protected final String _parseName() throws IOException {
        int n;
        int n2;
        int n3 = this._hashSeed;
        int[] arrn = _icLatin1;
        for (n2 = this._inputPtr; n2 < this._inputEnd; ++n2) {
            n = this._inputBuffer[n2];
            if (n < arrn.length && arrn[n] != 0) {
                if (n != 34) break;
                n = this._inputPtr;
                this._inputPtr = n2 + 1;
                return this._symbols.findSymbol(this._inputBuffer, n, n2 - n, n3);
            }
            n3 = n3 * 33 + n;
        }
        n = this._inputPtr;
        this._inputPtr = n2;
        return this._parseName2(n, n3, 34);
    }

    protected final JsonToken _parseNegNumber() throws IOException {
        int n;
        int n2 = this._inputPtr;
        int n3 = n2 - 1;
        int n4 = this._inputEnd;
        if (n2 >= n4) {
            return this._parseNumber2(true, n3);
        }
        char[] arrc = this._inputBuffer;
        int n5 = n2 + 1;
        if ((n2 = arrc[n2]) <= 57 && n2 >= 48) {
            if (n2 == 48) {
                return this._parseNumber2(true, n3);
            }
        } else {
            this._inputPtr = n5;
            return this._handleInvalidNumberStart(n2, true);
        }
        n2 = 1;
        do {
            if (n5 >= n4) {
                return this._parseNumber2(true, n3);
            }
            arrc = this._inputBuffer;
            n = n5 + 1;
            if ((n5 = arrc[n5]) < 48 || n5 > 57) break;
            ++n2;
            n5 = n;
        } while (true);
        if (n5 != 46 && n5 != 101 && n5 != 69) {
            this._inputPtr = --n;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(n5);
            }
            this._textBuffer.resetWithShared(this._inputBuffer, n3, n - n3);
            return this.resetInt(true, n2);
        }
        this._inputPtr = n;
        return this._parseFloat(n5, n3, n, true, n2);
    }

    protected final JsonToken _parsePosNumber(int n) throws IOException {
        int n2;
        int n3 = this._inputPtr;
        int n4 = n3 - 1;
        int n5 = this._inputEnd;
        if (n == 48) {
            return this._parseNumber2(false, n4);
        }
        n = 1;
        do {
            if (n3 >= n5) {
                this._inputPtr = n4;
                return this._parseNumber2(false, n4);
            }
            char[] arrc = this._inputBuffer;
            n2 = n3 + 1;
            if ((n3 = arrc[n3]) < 48 || n3 > 57) break;
            ++n;
            n3 = n2;
        } while (true);
        if (n3 != 46 && n3 != 101 && n3 != 69) {
            this._inputPtr = --n2;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(n3);
            }
            this._textBuffer.resetWithShared(this._inputBuffer, n4, n2 - n4);
            return this.resetInt(false, n);
        }
        this._inputPtr = n2;
        return this._parseFloat(n3, n4, n2, false, n);
    }

    protected int _readBinary(Base64Variant base64Variant, OutputStream object, byte[] arrby) throws IOException {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        int n4 = 0;
        do {
            block20 : {
                block16 : {
                    int n5;
                    int n6;
                    block19 : {
                        block18 : {
                            char c;
                            char[] arrc;
                            block17 : {
                                if (this._inputPtr >= this._inputEnd) {
                                    this._loadMoreGuaranteed();
                                }
                                arrc = this._inputBuffer;
                                n = this._inputPtr;
                                this._inputPtr = n + 1;
                                c = arrc[n];
                                if (c <= ' ') break block16;
                                n5 = n = base64Variant.decodeBase64Char(c);
                                if (n >= 0) break block17;
                                if (c == '\"') break;
                                n5 = n = this._decodeBase64Escape(base64Variant, c, 0);
                                if (n < 0) break block16;
                            }
                            n6 = n3;
                            n = n4;
                            if (n3 > n2 - 3) {
                                n = n4 + n3;
                                ((OutputStream)object).write(arrby, 0, n3);
                                n6 = 0;
                            }
                            if (this._inputPtr >= this._inputEnd) {
                                this._loadMoreGuaranteed();
                            }
                            arrc = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            c = arrc[n3];
                            n3 = n4 = base64Variant.decodeBase64Char(c);
                            if (n4 < 0) {
                                n3 = this._decodeBase64Escape(base64Variant, c, 1);
                            }
                            int n7 = n5 << 6 | n3;
                            if (this._inputPtr >= this._inputEnd) {
                                this._loadMoreGuaranteed();
                            }
                            arrc = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            c = arrc[n3];
                            n5 = n4 = base64Variant.decodeBase64Char(c);
                            if (n4 < 0) {
                                n3 = n4;
                                if (n4 != -2) {
                                    if (c == '\"') {
                                        arrby[n6] = (byte)(n7 >> 4);
                                        if (base64Variant.usesPadding()) {
                                            --this._inputPtr;
                                            this._handleBase64MissingPadding(base64Variant);
                                        }
                                        n3 = n6 + 1;
                                        n4 = n;
                                        break;
                                    }
                                    n3 = this._decodeBase64Escape(base64Variant, c, 2);
                                }
                                n5 = n3;
                                if (n3 == -2) {
                                    if (this._inputPtr >= this._inputEnd) {
                                        this._loadMoreGuaranteed();
                                    }
                                    arrc = this._inputBuffer;
                                    n3 = this._inputPtr;
                                    this._inputPtr = n3 + 1;
                                    c = arrc[n3];
                                    if (!base64Variant.usesPaddingChar(c) && this._decodeBase64Escape(base64Variant, c, 3) != -2) {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("expected padding character '");
                                        ((StringBuilder)object).append(base64Variant.getPaddingChar());
                                        ((StringBuilder)object).append("'");
                                        throw this.reportInvalidBase64Char(base64Variant, c, 3, ((StringBuilder)object).toString());
                                    }
                                    arrby[n6] = (byte)(n7 >> 4);
                                    n3 = n6 + 1;
                                    n4 = n;
                                    continue;
                                }
                            }
                            n5 = n7 << 6 | n5;
                            if (this._inputPtr >= this._inputEnd) {
                                this._loadMoreGuaranteed();
                            }
                            arrc = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            c = arrc[n3];
                            n3 = base64Variant.decodeBase64Char(c);
                            if (n3 >= 0) break block18;
                            if (n3 != -2) {
                                if (c == '\"') {
                                    n3 = n5 >> 2;
                                    n4 = n6 + 1;
                                    arrby[n6] = (byte)(n3 >> 8);
                                    n6 = n4 + 1;
                                    arrby[n4] = (byte)n3;
                                    n3 = n6;
                                    n4 = n;
                                    if (!base64Variant.usesPadding()) break;
                                    --this._inputPtr;
                                    this._handleBase64MissingPadding(base64Variant);
                                    n4 = n;
                                    n3 = n6;
                                    break;
                                }
                                n3 = this._decodeBase64Escape(base64Variant, c, 3);
                            }
                            n4 = n3;
                            if (n3 != -2) break block19;
                            n4 = n6 + 1;
                            arrby[n6] = (byte)((n5 >>= 2) >> 8);
                            n3 = n4 + 1;
                            arrby[n4] = (byte)n5;
                            break block20;
                        }
                        n4 = n3;
                    }
                    n3 = n5 << 6 | n4;
                    n4 = n6 + 1;
                    arrby[n6] = (byte)(n3 >> 16);
                    n6 = n4 + 1;
                    arrby[n4] = (byte)(n3 >> 8);
                    arrby[n6] = (byte)n3;
                    n3 = n6 + 1;
                    break block20;
                }
                n = n4;
            }
            n4 = n;
        } while (true);
        this._tokenIncomplete = false;
        n = n4;
        if (n3 <= 0) return n;
        n = n4 + n3;
        ((OutputStream)object).write(arrby, 0, n3);
        return n;
    }

    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (!this._bufferRecyclable) return;
        char[] arrc = this._inputBuffer;
        if (arrc == null) return;
        this._inputBuffer = null;
        this._ioContext.releaseTokenBuffer(arrc);
    }

    protected void _reportInvalidToken(String string2) throws IOException {
        this._reportInvalidToken(string2, this._validJsonTokenList());
    }

    protected void _reportInvalidToken(String charSequence, String string2) throws IOException {
        char c;
        charSequence = new StringBuilder((String)charSequence);
        while ((this._inputPtr < this._inputEnd || this._loadMore()) && Character.isJavaIdentifierPart(c = this._inputBuffer[this._inputPtr])) {
            ++this._inputPtr;
            ((StringBuilder)charSequence).append(c);
            if (((StringBuilder)charSequence).length() < 256) continue;
            ((StringBuilder)charSequence).append("...");
            break;
        }
        this._reportError("Unrecognized token '%s': was expecting %s", charSequence, string2);
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int n = this._inputPtr;
        int n2 = this._inputEnd;
        char[] arrc = this._inputBuffer;
        do {
            int n3 = n;
            int n4 = n2;
            if (n >= n2) {
                this._inputPtr = n;
                if (!this._loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                n3 = this._inputPtr;
                n4 = this._inputEnd;
            }
            n = n3 + 1;
            n2 = arrc[n3];
            if (n2 <= 92) {
                if (n2 == 92) {
                    this._inputPtr = n;
                    this._decodeEscaped();
                    n = this._inputPtr;
                    n2 = this._inputEnd;
                    continue;
                }
                if (n2 <= 34) {
                    if (n2 == 34) {
                        this._inputPtr = n;
                        return;
                    }
                    if (n2 < 32) {
                        this._inputPtr = n;
                        this._throwUnquotedSpace(n2, "string value");
                    }
                }
            }
            n2 = n4;
        } while (true);
    }

    @Override
    public void finishToken() throws IOException {
        if (!this._tokenIncomplete) return;
        this._tokenIncomplete = false;
        this._finishString();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        Object object;
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Current token (");
            ((StringBuilder)object).append((Object)this._currToken);
            ((StringBuilder)object).append(") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
            this._reportError(((StringBuilder)object).toString());
        }
        if (!this._tokenIncomplete) {
            if (this._binaryValue != null) return this._binaryValue;
            object = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), (ByteArrayBuilder)object, base64Variant);
            this._binaryValue = ((ByteArrayBuilder)object).toByteArray();
            return this._binaryValue;
        }
        try {
            this._binaryValue = this._decodeBase64(base64Variant);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to decode VALUE_STRING as base64 (");
            ((StringBuilder)object).append(base64Variant);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(illegalArgumentException.getMessage());
            throw this._constructError(((StringBuilder)object).toString());
        }
        this._tokenIncomplete = false;
        return this._binaryValue;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        int n = this._inputPtr;
        int n2 = this._currInputRowStart;
        Object object = this._getSourceReference();
        long l = this._currInputProcessed;
        return new JsonLocation(object, -1L, (long)this._inputPtr + l, this._currInputRow, n - n2 + 1);
    }

    @Override
    public Object getInputSource() {
        return this._reader;
    }

    @Deprecated
    protected char getNextChar(String string2) throws IOException {
        return this.getNextChar(string2, null);
    }

    protected char getNextChar(String arrc, JsonToken jsonToken) throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF((String)arrc, jsonToken);
        }
        arrc = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        return arrc[n];
    }

    @Override
    public int getText(Writer writer) throws IOException {
        Object object = this._currToken;
        if (object == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsToWriter(writer);
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.contentsToWriter(writer);
        }
        if (object == JsonToken.FIELD_NAME) {
            object = this._parsingContext.getCurrentName();
            writer.write((String)object);
            return ((String)object).length();
        }
        if (object == null) return 0;
        if (object.isNumeric()) {
            return this._textBuffer.contentsToWriter(writer);
        }
        object = object.asCharArray();
        writer.write((char[])object);
        return ((Object)object).length;
    }

    @Override
    public final String getText() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_STRING) return this._getText2(jsonToken);
        if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
        this._tokenIncomplete = false;
        this._finishString();
        return this._textBuffer.contentsAsString();
    }

    @Override
    public final char[] getTextCharacters() throws IOException {
        if (this._currToken == null) return null;
        int n = this._currToken.id();
        if (n != 5) {
            if (n != 6) {
                if (n == 7) return this._textBuffer.getTextBuffer();
                if (n == 8) return this._textBuffer.getTextBuffer();
                return this._currToken.asCharArray();
            }
            if (!this._tokenIncomplete) return this._textBuffer.getTextBuffer();
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.getTextBuffer();
        }
        if (this._nameCopied) return this._nameCopyBuffer;
        String string2 = this._parsingContext.getCurrentName();
        n = string2.length();
        if (this._nameCopyBuffer == null) {
            this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(n);
        } else if (this._nameCopyBuffer.length < n) {
            this._nameCopyBuffer = new char[n];
        }
        string2.getChars(0, n, this._nameCopyBuffer, 0);
        this._nameCopied = true;
        return this._nameCopyBuffer;
    }

    @Override
    public final int getTextLength() throws IOException {
        if (this._currToken == null) return 0;
        int n = this._currToken.id();
        if (n == 5) return this._parsingContext.getCurrentName().length();
        if (n != 6) {
            if (n == 7) return this._textBuffer.size();
            if (n == 8) return this._textBuffer.size();
            return this._currToken.asCharArray().length;
        }
        if (!this._tokenIncomplete) return this._textBuffer.size();
        this._tokenIncomplete = false;
        this._finishString();
        return this._textBuffer.size();
    }

    @Override
    public final int getTextOffset() throws IOException {
        if (this._currToken == null) return 0;
        int n = this._currToken.id();
        if (n != 6) {
            if (n == 7) return this._textBuffer.getTextOffset();
            if (n == 8) return this._textBuffer.getTextOffset();
            return 0;
        }
        if (!this._tokenIncomplete) return this._textBuffer.getTextOffset();
        this._tokenIncomplete = false;
        this._finishString();
        return this._textBuffer.getTextOffset();
    }

    @Override
    public JsonLocation getTokenLocation() {
        if (this._currToken != JsonToken.FIELD_NAME) return new JsonLocation(this._getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
        long l = this._currInputProcessed;
        long l2 = this._nameStartOffset;
        return new JsonLocation(this._getSourceReference(), -1L, l + (l2 - 1L), this._nameStartRow, this._nameStartCol);
    }

    @Override
    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(null);
        return this.getCurrentName();
    }

    @Override
    public final String getValueAsString(String string2) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(string2);
        return this.getCurrentName();
    }

    @Override
    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return null;
            }
            if (jsonToken != JsonToken.START_OBJECT) return null;
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        JsonToken jsonToken = this.nextToken();
        if (jsonToken == null) return null;
        int n = jsonToken.id();
        if (n == 9) {
            return Boolean.TRUE;
        }
        if (n != 10) return null;
        return Boolean.FALSE;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public String nextFieldName() throws IOException {
        block23 : {
            block24 : {
                block25 : {
                    this._numTypesValid = 0;
                    if (this._currToken == JsonToken.FIELD_NAME) {
                        this._nextAfterName();
                        return null;
                    }
                    if (this._tokenIncomplete) {
                        this._skipString();
                    }
                    if ((var1_1 = this._skipWSOrEnd()) < 0) {
                        this.close();
                        this._currToken = null;
                        return null;
                    }
                    this._binaryValue = null;
                    if (var1_1 == 93 || var1_1 == 125) break block23;
                    var2_2 = var1_1;
                    if (!this._parsingContext.expectComma()) break block24;
                    var2_2 = var1_1 = this._skipComma(var1_1);
                    if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) == 0) break block24;
                    if (var1_1 == 93) break block25;
                    var2_2 = var1_1;
                    if (var1_1 != 125) break block24;
                }
                this._closeScope(var1_1);
                return null;
            }
            if (!this._parsingContext.inObject()) {
                this._updateLocation();
                this._nextTokenNotInObject(var2_2);
                return null;
            }
            this._updateNameLocation();
            var3_3 = var2_2 == 34 ? this._parseName() : this._handleOddName(var2_2);
            this._parsingContext.setCurrentName(var3_3);
            this._currToken = JsonToken.FIELD_NAME;
            var2_2 = this._skipColon();
            this._updateLocation();
            if (var2_2 == 34) {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return var3_3;
            }
            if (var2_2 != 45) {
                if (var2_2 != 91) {
                    if (var2_2 != 102) {
                        if (var2_2 != 110) {
                            if (var2_2 != 116) {
                                if (var2_2 != 123) {
                                    switch (var2_2) {
                                        default: {
                                            var4_4 = this._handleOddValue(var2_2);
                                            ** break;
                                        }
                                        case 48: 
                                        case 49: 
                                        case 50: 
                                        case 51: 
                                        case 52: 
                                        case 53: 
                                        case 54: 
                                        case 55: 
                                        case 56: 
                                        case 57: 
                                    }
                                    var4_4 = this._parsePosNumber(var2_2);
                                    ** break;
lbl53: // 2 sources:
                                } else {
                                    var4_4 = JsonToken.START_OBJECT;
                                }
                            } else {
                                this._matchTrue();
                                var4_4 = JsonToken.VALUE_TRUE;
                            }
                        } else {
                            this._matchNull();
                            var4_4 = JsonToken.VALUE_NULL;
                        }
                    } else {
                        this._matchFalse();
                        var4_4 = JsonToken.VALUE_FALSE;
                    }
                } else {
                    var4_4 = JsonToken.START_ARRAY;
                }
            } else {
                var4_4 = this._parseNegNumber();
            }
            this._nextToken = var4_4;
            return var3_3;
        }
        this._closeScope(var1_1);
        return null;
    }

    @Override
    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        int n;
        int n2;
        int n3;
        block14 : {
            block11 : {
                block12 : {
                    block13 : {
                        n2 = 0;
                        this._numTypesValid = 0;
                        if (this._currToken == JsonToken.FIELD_NAME) {
                            this._nextAfterName();
                            return false;
                        }
                        if (this._tokenIncomplete) {
                            this._skipString();
                        }
                        if ((n3 = this._skipWSOrEnd()) < 0) {
                            this.close();
                            this._currToken = null;
                            return false;
                        }
                        this._binaryValue = null;
                        if (n3 == 93 || n3 == 125) break block11;
                        n = n3;
                        if (!this._parsingContext.expectComma()) break block12;
                        n = n3 = this._skipComma(n3);
                        if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) break block12;
                        if (n3 == 93) break block13;
                        n = n3;
                        if (n3 != 125) break block12;
                    }
                    this._closeScope(n3);
                    return false;
                }
                if (!this._parsingContext.inObject()) {
                    this._updateLocation();
                    this._nextTokenNotInObject(n);
                    return false;
                }
                break block14;
            }
            this._closeScope(n3);
            return false;
        }
        this._updateNameLocation();
        if (n != 34) return this._isNextTokenNameMaybe(n, serializableString.getValue());
        char[] arrc = serializableString.asQuotedChars();
        n3 = arrc.length;
        if (this._inputPtr + n3 + 4 >= this._inputEnd) return this._isNextTokenNameMaybe(n, serializableString.getValue());
        int n4 = this._inputPtr + n3;
        if (this._inputBuffer[n4] != '\"') return this._isNextTokenNameMaybe(n, serializableString.getValue());
        n3 = this._inputPtr;
        do {
            if (n3 == n4) {
                this._parsingContext.setCurrentName(serializableString.getValue());
                this._isNextTokenNameYes(this._skipColonFast(n3 + 1));
                return true;
            }
            if (arrc[n2] != this._inputBuffer[n3]) {
                return this._isNextTokenNameMaybe(n, serializableString.getValue());
            }
            ++n2;
            ++n3;
        } while (true);
    }

    @Override
    public final int nextIntValue(int n) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return n;
            return this.getIntValue();
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return n;
        }
        if (jsonToken != JsonToken.START_OBJECT) return n;
        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        return n;
    }

    @Override
    public final long nextLongValue(long l) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return l;
            return this.getLongValue();
        }
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        this._currToken = jsonToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
        }
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return l;
        }
        if (jsonToken != JsonToken.START_OBJECT) return l;
        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        return l;
    }

    @Override
    public final String nextTextValue() throws IOException {
        JsonToken jsonToken = this._currToken;
        JsonToken jsonToken2 = JsonToken.FIELD_NAME;
        Object object = null;
        if (jsonToken != jsonToken2) {
            if (this.nextToken() != JsonToken.VALUE_STRING) return object;
            return this.getText();
        }
        this._nameCopied = false;
        object = this._nextToken;
        this._nextToken = null;
        this._currToken = object;
        if (object == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.contentsAsString();
        }
        if (object == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            return null;
        }
        if (object != JsonToken.START_OBJECT) return null;
        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        return null;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final JsonToken nextToken() throws IOException {
        block15 : {
            block26 : {
                block18 : {
                    block19 : {
                        block20 : {
                            block21 : {
                                block22 : {
                                    block23 : {
                                        block24 : {
                                            block25 : {
                                                block16 : {
                                                    block17 : {
                                                        if (this._currToken == JsonToken.FIELD_NAME) {
                                                            return this._nextAfterName();
                                                        }
                                                        this._numTypesValid = 0;
                                                        if (this._tokenIncomplete) {
                                                            this._skipString();
                                                        }
                                                        if ((var1_1 = this._skipWSOrEnd()) < 0) {
                                                            this.close();
                                                            this._currToken = null;
                                                            return null;
                                                        }
                                                        this._binaryValue = null;
                                                        if (var1_1 == 93 || var1_1 == 125) break block15;
                                                        var2_2 = var1_1;
                                                        if (!this._parsingContext.expectComma()) break block16;
                                                        var2_2 = var1_1 = this._skipComma(var1_1);
                                                        if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) == 0) break block16;
                                                        if (var1_1 == 93) break block17;
                                                        var2_2 = var1_1;
                                                        if (var1_1 != 125) break block16;
                                                    }
                                                    this._closeScope(var1_1);
                                                    return this._currToken;
                                                }
                                                var3_3 = this._parsingContext.inObject();
                                                var1_1 = var2_2;
                                                if (var3_3) {
                                                    this._updateNameLocation();
                                                    if (var2_2 == 34) {
                                                        var4_4 = this._parseName();
                                                    } else {
                                                        var4_5 = this._handleOddName(var2_2);
                                                    }
                                                    this._parsingContext.setCurrentName((String)var4_6);
                                                    this._currToken = JsonToken.FIELD_NAME;
                                                    var1_1 = this._skipColon();
                                                }
                                                this._updateLocation();
                                                if (var1_1 == 34) break block18;
                                                if (var1_1 == 45) break block19;
                                                if (var1_1 == 91) break block20;
                                                if (var1_1 == 102) break block21;
                                                if (var1_1 == 110) break block22;
                                                if (var1_1 == 116) break block23;
                                                if (var1_1 == 123) break block24;
                                                if (var1_1 == 125) break block25;
                                                switch (var1_1) {
                                                    default: {
                                                        var4_7 = this._handleOddValue(var1_1);
                                                        ** break;
                                                    }
                                                    case 48: 
                                                    case 49: 
                                                    case 50: 
                                                    case 51: 
                                                    case 52: 
                                                    case 53: 
                                                    case 54: 
                                                    case 55: 
                                                    case 56: 
                                                    case 57: 
                                                }
                                                var4_8 = this._parsePosNumber(var1_1);
                                                ** break;
lbl50: // 2 sources:
                                                break block26;
                                            }
                                            this._reportUnexpectedChar(var1_1, "expected a value");
                                            break block23;
                                        }
                                        if (!var3_3) {
                                            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                                        }
                                        var4_9 = JsonToken.START_OBJECT;
                                        break block26;
                                    }
                                    this._matchTrue();
                                    var4_10 = JsonToken.VALUE_TRUE;
                                    break block26;
                                }
                                this._matchNull();
                                var4_11 = JsonToken.VALUE_NULL;
                                break block26;
                            }
                            this._matchFalse();
                            var4_12 = JsonToken.VALUE_FALSE;
                            break block26;
                        }
                        if (!var3_3) {
                            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                        }
                        var4_13 = JsonToken.START_ARRAY;
                        break block26;
                    }
                    var4_14 = this._parseNegNumber();
                    break block26;
                }
                this._tokenIncomplete = true;
                var4_15 = JsonToken.VALUE_STRING;
            }
            if (var3_3) {
                this._nextToken = var4_16;
                return this._currToken;
            }
            this._currToken = var4_16;
            return var4_16;
        }
        this._closeScope(var1_1);
        return this._currToken;
    }

    @Override
    public int readBinaryValue(Base64Variant arrby, OutputStream outputStream2) throws IOException {
        if (this._tokenIncomplete && this._currToken == JsonToken.VALUE_STRING) {
            byte[] arrby2 = this._ioContext.allocBase64Buffer();
            try {
                int n = this._readBinary((Base64Variant)arrby, outputStream2, arrby2);
                return n;
            }
            finally {
                this._ioContext.releaseBase64Buffer(arrby2);
            }
        }
        arrby = this.getBinaryValue((Base64Variant)arrby);
        outputStream2.write(arrby);
        return arrby.length;
    }

    @Override
    public int releaseBuffered(Writer writer) throws IOException {
        int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        int n2 = this._inputPtr;
        writer.write(this._inputBuffer, n2, n);
        return n;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }
}

