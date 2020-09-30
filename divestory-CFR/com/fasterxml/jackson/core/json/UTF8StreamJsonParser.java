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
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class UTF8StreamJsonParser
extends ParserBase {
    static final byte BYTE_LF = 10;
    private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
    private static final int FEAT_MASK_ALLOW_MISSING;
    private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
    private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
    private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
    private static final int FEAT_MASK_LEADING_ZEROS;
    private static final int FEAT_MASK_NON_NUM_NUMBERS;
    private static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    private static final int[] _icUTF8;
    protected boolean _bufferRecyclable;
    protected byte[] _inputBuffer;
    protected InputStream _inputStream;
    protected int _nameStartCol;
    protected int _nameStartOffset;
    protected int _nameStartRow;
    protected ObjectCodec _objectCodec;
    private int _quad1;
    protected int[] _quadBuffer = new int[16];
    protected final ByteQuadsCanonicalizer _symbols;
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
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }

    public UTF8StreamJsonParser(IOContext iOContext, int n, InputStream inputStream2, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, byte[] arrby, int n2, int n3, int n4, boolean bl) {
        super(iOContext, n);
        this._inputStream = inputStream2;
        this._objectCodec = objectCodec;
        this._symbols = byteQuadsCanonicalizer;
        this._inputBuffer = arrby;
        this._inputPtr = n2;
        this._inputEnd = n3;
        this._currInputRowStart = n2 - n4;
        this._currInputProcessed = -n2 + n4;
        this._bufferRecyclable = bl;
    }

    @Deprecated
    public UTF8StreamJsonParser(IOContext iOContext, int n, InputStream inputStream2, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, byte[] arrby, int n2, int n3, boolean bl) {
        this(iOContext, n, inputStream2, objectCodec, byteQuadsCanonicalizer, arrby, n2, n3, 0, bl);
    }

    private final void _checkMatchEnd(String string2, int n, int n2) throws IOException {
        if (!Character.isJavaIdentifierPart((char)this._decodeCharForError(n2))) return;
        this._reportInvalidToken(string2.substring(0, n));
    }

    private final void _closeArrayScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }

    private final void _closeObjectScope() throws JsonParseException {
        this._updateLocation();
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
    }

    private final JsonToken _closeScope(int n) throws JsonParseException {
        JsonToken jsonToken;
        if (n == 125) {
            JsonToken jsonToken2;
            this._closeObjectScope();
            this._currToken = jsonToken2 = JsonToken.END_OBJECT;
            return jsonToken2;
        }
        this._closeArrayScope();
        this._currToken = jsonToken = JsonToken.END_ARRAY;
        return jsonToken;
    }

    private final int _decodeUtf8_2(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) == 128) return (n & 31) << 6 | n2 & 63;
        this._reportInvalidOther(n2 & 255, this._inputPtr);
        return (n & 31) << 6 | n2 & 63;
    }

    private final int _decodeUtf8_3(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) == 128) return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
        this._reportInvalidOther(n3 & 255, this._inputPtr);
        return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
    }

    private final int _decodeUtf8_3fast(int n) throws IOException {
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) == 128) return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
        this._reportInvalidOther(n3 & 255, this._inputPtr);
        return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
    }

    private final int _decodeUtf8_4(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (((n2 = arrby[n2]) & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (((n3 = arrby[n3]) & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        if (((n4 = arrby[n4]) & 192) == 128) return ((((n & 7) << 6 | n2 & 63) << 6 | n3 & 63) << 6 | n4 & 63) - 65536;
        this._reportInvalidOther(n4 & 255, this._inputPtr);
        return ((((n & 7) << 6 | n2 & 63) << 6 | n3 & 63) << 6 | n4 & 63) - 65536;
    }

    private final void _finishString2(char[] arrc, int n) throws IOException {
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        block0 : do {
            int n2;
            int n3 = n2 = this._inputPtr;
            if (n2 >= this._inputEnd) {
                this._loadMoreGuaranteed();
                n3 = this._inputPtr;
            }
            int n4 = arrc.length;
            int n5 = 0;
            n2 = n;
            if (n >= n4) {
                arrc = this._textBuffer.finishCurrentSegment();
                n2 = 0;
            }
            int n6 = Math.min(this._inputEnd, arrc.length - n2 + n3);
            while (n3 < n6) {
                n4 = n3 + 1;
                n = arrby[n3] & 255;
                if (arrn[n] != 0) {
                    this._inputPtr = n4;
                    if (n == 34) {
                        this._textBuffer.setCurrentLength(n2);
                        return;
                    }
                    n3 = arrn[n];
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                if (n3 != 4) {
                                    if (n < 32) {
                                        this._throwUnquotedSpace(n, "string value");
                                    } else {
                                        this._reportInvalidChar(n);
                                    }
                                } else {
                                    n3 = this._decodeUtf8_4(n);
                                    n = n2 + 1;
                                    arrc[n2] = (char)(55296 | n3 >> 10);
                                    if (n >= arrc.length) {
                                        arrc = this._textBuffer.finishCurrentSegment();
                                        n2 = 0;
                                    } else {
                                        n2 = n;
                                    }
                                    n = n3 & 1023 | 56320;
                                }
                            } else {
                                n = this._inputEnd - this._inputPtr >= 2 ? this._decodeUtf8_3fast(n) : this._decodeUtf8_3(n);
                            }
                        } else {
                            n = this._decodeUtf8_2(n);
                        }
                    } else {
                        n = this._decodeEscaped();
                    }
                    if (n2 >= arrc.length) {
                        arrc = this._textBuffer.finishCurrentSegment();
                        n2 = n5;
                    }
                    n3 = n2 + 1;
                    arrc[n2] = (char)n;
                    n = n3;
                    continue block0;
                }
                arrc[n2] = (char)n;
                n3 = n4;
                ++n2;
            }
            this._inputPtr = n3;
            n = n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    private final boolean _isNextTokenNameMaybe(int var1_1, SerializableString var2_2) throws IOException {
        var3_12 = this._parseName(var1_1);
        this._parsingContext.setCurrentName(var3_12);
        var4_13 = var3_12.equals(var2_2.getValue());
        this._currToken = JsonToken.FIELD_NAME;
        var1_1 = this._skipColon();
        this._updateLocation();
        if (var1_1 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return var4_13;
        }
        if (var1_1 != 45) {
            if (var1_1 != 91) {
                if (var1_1 != 102) {
                    if (var1_1 != 110) {
                        if (var1_1 != 116) {
                            if (var1_1 != 123) {
                                switch (var1_1) {
                                    default: {
                                        var2_3 = this._handleUnexpectedValue(var1_1);
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
                                var2_4 = this._parsePosNumber(var1_1);
                                ** break;
lbl24: // 2 sources:
                            } else {
                                var2_5 = JsonToken.START_OBJECT;
                            }
                        } else {
                            this._matchTrue();
                            var2_6 = JsonToken.VALUE_TRUE;
                        }
                    } else {
                        this._matchNull();
                        var2_7 = JsonToken.VALUE_NULL;
                    }
                } else {
                    this._matchFalse();
                    var2_8 = JsonToken.VALUE_FALSE;
                }
            } else {
                var2_9 = JsonToken.START_ARRAY;
            }
        } else {
            var2_10 = this._parseNegNumber();
        }
        this._nextToken = var2_11;
        return var4_13;
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
            this._matchFalse();
            this._nextToken = JsonToken.VALUE_FALSE;
            return;
        }
        if (n == 110) {
            this._matchNull();
            this._nextToken = JsonToken.VALUE_NULL;
            return;
        }
        if (n == 116) {
            this._matchTrue();
            this._nextToken = JsonToken.VALUE_TRUE;
            return;
        }
        if (n == 123) {
            this._nextToken = JsonToken.START_OBJECT;
            return;
        }
        switch (n) {
            default: {
                this._nextToken = this._handleUnexpectedValue(n);
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
        n = this._inputBuffer[this._inputPtr] & 255;
        if (n < 48) return;
        if (n == 93) return;
        if (n == 125) return;
        this._checkMatchEnd(string2, n2, n);
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
        if (n == 34) {
            JsonToken jsonToken2;
            this._tokenIncomplete = true;
            this._currToken = jsonToken2 = JsonToken.VALUE_STRING;
            return jsonToken2;
        }
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
        if (n == 102) {
            JsonToken jsonToken5;
            this._matchFalse();
            this._currToken = jsonToken5 = JsonToken.VALUE_FALSE;
            return jsonToken5;
        }
        if (n == 110) {
            JsonToken jsonToken6;
            this._matchNull();
            this._currToken = jsonToken6 = JsonToken.VALUE_NULL;
            return jsonToken6;
        }
        if (n == 116) {
            JsonToken jsonToken7;
            this._matchTrue();
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
                JsonToken jsonToken9;
                this._currToken = jsonToken9 = this._handleUnexpectedValue(n);
                return jsonToken9;
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
        this._currToken = jsonToken = this._parsePosNumber(n);
        return jsonToken;
    }

    private static final int _padLastQuad(int n, int n2) {
        if (n2 == 4) {
            return n;
        }
        n |= -1 << (n2 << 3);
        return n;
    }

    private final JsonToken _parseFloat(char[] arrobject, int n, int n2, boolean bl, int n3) throws IOException {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        block25 : {
            int n9;
            int n10;
            block23 : {
                Object[] arrobject2;
                block27 : {
                    block26 : {
                        block24 : {
                            n8 = 0;
                            if (n2 != 46) {
                                n5 = 0;
                                n6 = 0;
                                n9 = n2;
                                n10 = n;
                                arrobject2 = arrobject;
                                n = n6;
                            } else {
                                block22 : {
                                    arrobject2 = arrobject;
                                    n10 = n;
                                    if (n >= arrobject.length) {
                                        arrobject2 = this._textBuffer.finishCurrentSegment();
                                        n10 = 0;
                                    }
                                    arrobject2[n10] = (char)n2;
                                    n = n10 + 1;
                                    n6 = 0;
                                    n4 = n2;
                                    n2 = n;
                                    arrobject = arrobject2;
                                    do {
                                        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                            n7 = 1;
                                            break block22;
                                        }
                                        arrobject2 = this._inputBuffer;
                                        n = this._inputPtr;
                                        this._inputPtr = n + 1;
                                        n4 = arrobject2[n] & 255;
                                        if (n4 < 48 || n4 > 57) break;
                                        ++n6;
                                        arrobject2 = arrobject;
                                        n = n2;
                                        if (n2 >= arrobject.length) {
                                            arrobject2 = this._textBuffer.finishCurrentSegment();
                                            n = 0;
                                        }
                                        arrobject2[n] = (char)n4;
                                        n2 = n + 1;
                                        arrobject = arrobject2;
                                    } while (true);
                                    n7 = 0;
                                }
                                n5 = n6;
                                n = n7;
                                arrobject2 = arrobject;
                                n10 = n2;
                                n9 = n4;
                                if (n6 == 0) {
                                    this.reportUnexpectedNumberChar(n4, "Decimal point not followed by a digit");
                                    n5 = n6;
                                    n = n7;
                                    arrobject2 = arrobject;
                                    n10 = n2;
                                    n9 = n4;
                                }
                            }
                            if (n9 == 101) break block24;
                            n6 = n;
                            n4 = n10;
                            n7 = n9;
                            if (n9 != 69) break block25;
                        }
                        arrobject = arrobject2;
                        n2 = n10;
                        if (n10 >= arrobject2.length) {
                            arrobject = this._textBuffer.finishCurrentSegment();
                            n2 = 0;
                        }
                        n6 = n2 + 1;
                        arrobject[n2] = (char)n9;
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        arrobject2 = this._inputBuffer;
                        n2 = this._inputPtr;
                        this._inputPtr = n2 + 1;
                        n9 = arrobject2[n2] & 255;
                        if (n9 == 45) break block26;
                        n2 = n6;
                        arrobject2 = arrobject;
                        n10 = n9;
                        if (n9 != 43) break block27;
                    }
                    n2 = n6;
                    arrobject2 = arrobject;
                    if (n6 >= arrobject.length) {
                        arrobject2 = this._textBuffer.finishCurrentSegment();
                        n2 = 0;
                    }
                    arrobject2[n2] = (char)n9;
                    if (this._inputPtr >= this._inputEnd) {
                        this._loadMoreGuaranteed();
                    }
                    arrobject = this._inputBuffer;
                    n10 = this._inputPtr;
                    this._inputPtr = n10 + 1;
                    n10 = arrobject[n10] & 255;
                    ++n2;
                }
                n9 = 0;
                while (n10 >= 48 && n10 <= 57) {
                    ++n9;
                    n6 = n2;
                    arrobject = arrobject2;
                    if (n2 >= arrobject2.length) {
                        arrobject = this._textBuffer.finishCurrentSegment();
                        n6 = 0;
                    }
                    n2 = n6 + 1;
                    arrobject[n6] = (char)n10;
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        n = n9;
                        n9 = 1;
                        break block23;
                    }
                    arrobject2 = this._inputBuffer;
                    n10 = this._inputPtr;
                    this._inputPtr = n10 + 1;
                    n10 = arrobject2[n10] & 255;
                    arrobject2 = arrobject;
                }
                n6 = n9;
                n9 = n;
                n = n6;
            }
            n8 = n;
            n6 = n9;
            n4 = n2;
            n7 = n10;
            if (n == 0) {
                this.reportUnexpectedNumberChar(n10, "Exponent indicator not followed by a digit");
                n7 = n10;
                n4 = n2;
                n6 = n9;
                n8 = n;
            }
        }
        if (n6 == 0) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(n7);
            }
        }
        this._textBuffer.setCurrentLength(n4);
        return this.resetFloat(bl, n3, n5, n8);
    }

    private final JsonToken _parseNumber2(char[] arrobject, int n, boolean bl, int n2) throws IOException {
        int n3;
        do {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._textBuffer.setCurrentLength(n);
                return this.resetInt(bl, n2);
            }
            Object[] arrobject2 = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            n3 = arrobject2[n4] & 255;
            if (n3 > 57 || n3 < 48) break;
            arrobject2 = arrobject;
            n4 = n;
            if (n >= arrobject.length) {
                arrobject2 = this._textBuffer.finishCurrentSegment();
                n4 = 0;
            }
            arrobject2[n4] = (char)n3;
            ++n2;
            n = n4 + 1;
            arrobject = arrobject2;
        } while (true);
        if (n3 == 46) return this._parseFloat((char[])arrobject, n, n3, bl, n2);
        if (n3 == 101) return this._parseFloat((char[])arrobject, n, n3, bl, n2);
        if (n3 == 69) {
            return this._parseFloat((char[])arrobject, n, n3, bl, n2);
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n);
        if (!this._parsingContext.inRoot()) return this.resetInt(bl, n2);
        this._verifyRootSpace(this._inputBuffer[this._inputPtr] & 255);
        return this.resetInt(bl, n2);
    }

    private final void _skipCComment() throws IOException {
        int[] arrn = CharTypes.getInputCodeComment();
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = arrby[n] & 255;
            n = arrn[n2];
            if (n == 0) continue;
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 10) {
                            if (n != 13) {
                                if (n != 42) {
                                    this._reportInvalidChar(n2);
                                    continue;
                                }
                                if (this._inputPtr >= this._inputEnd && !this._loadMore()) break;
                                if (this._inputBuffer[this._inputPtr] != 47) continue;
                                ++this._inputPtr;
                                return;
                            }
                            this._skipCR();
                            continue;
                        }
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                        continue;
                    }
                    this._skipUtf8_4(n2);
                    continue;
                }
                this._skipUtf8_3();
                continue;
            }
            this._skipUtf8_2();
        }
        this._reportInvalidEOF(" in a comment", null);
    }

    private final int _skipColon() throws IOException {
        int n;
        byte[] arrby;
        block14 : {
            block13 : {
                if (this._inputPtr + 4 >= this._inputEnd) {
                    return this._skipColon2(false);
                }
                int n2 = this._inputBuffer[this._inputPtr];
                if (n2 == 58) {
                    int n3;
                    byte[] arrby2 = this._inputBuffer;
                    this._inputPtr = n3 = this._inputPtr + 1;
                    if ((n3 = arrby2[n3]) > 32) {
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
                    arrby2 = this._inputBuffer;
                    this._inputPtr = n3 = this._inputPtr + 1;
                    if ((n3 = arrby2[n3]) <= 32) return this._skipColon2(true);
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
            arrby = this._inputBuffer;
            this._inputPtr = n = this._inputPtr + 1;
            n = arrby[n];
        }
        if (n != 58) return this._skipColon2(false);
        arrby = this._inputBuffer;
        this._inputPtr = n = this._inputPtr + 1;
        if ((n = arrby[n]) > 32) {
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
        arrby = this._inputBuffer;
        this._inputPtr = n = this._inputPtr + 1;
        if ((n = arrby[n]) <= 32) return this._skipColon2(true);
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
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = object[n] & 255) > 32) {
                if (n == 47) {
                    this._skipComment();
                    continue;
                }
                if (n == 35 && this._skipYAMLComment()) continue;
                if (bl) {
                    return n;
                }
                if (n != 58) {
                    this._reportUnexpectedChar(n, "was expecting a colon to separate field name and value");
                }
                bl = true;
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
        } while (true);
    }

    private final int _skipColonFast(int n) throws IOException {
        block26 : {
            byte[] arrby;
            int n2;
            block27 : {
                int n3;
                block25 : {
                    block24 : {
                        block23 : {
                            int n4;
                            block19 : {
                                block21 : {
                                    int n5;
                                    block22 : {
                                        block20 : {
                                            arrby = this._inputBuffer;
                                            n3 = n + 1;
                                            n4 = arrby[n];
                                            if (n4 != 58) break block19;
                                            n5 = n3 + 1;
                                            if ((n3 = arrby[n3]) <= 32) break block20;
                                            n = n5;
                                            if (n3 != 47) {
                                                n = n5;
                                                if (n3 != 35) {
                                                    this._inputPtr = n5;
                                                    return n3;
                                                }
                                            }
                                            break block21;
                                        }
                                        if (n3 == 32) break block22;
                                        n = n5;
                                        if (n3 != 9) break block21;
                                    }
                                    arrby = this._inputBuffer;
                                    n = n5 + 1;
                                    if ((n5 = arrby[n5]) > 32 && n5 != 47 && n5 != 35) {
                                        this._inputPtr = n;
                                        return n5;
                                    }
                                }
                                this._inputPtr = n - 1;
                                return this._skipColon2(true);
                            }
                            if (n4 == 32) break block23;
                            n = n3;
                            n2 = n4;
                            if (n4 != 9) break block24;
                        }
                        n2 = this._inputBuffer[n3];
                        n = n3 + 1;
                    }
                    if (n2 != 58) {
                        this._inputPtr = n - 1;
                        return this._skipColon2(false);
                    }
                    arrby = this._inputBuffer;
                    n2 = n + 1;
                    n3 = arrby[n];
                    if (n3 <= 32) break block25;
                    n = n2;
                    if (n3 != 47) {
                        n = n2;
                        if (n3 != 35) {
                            this._inputPtr = n2;
                            return n3;
                        }
                    }
                    break block26;
                }
                if (n3 == 32) break block27;
                n = n2;
                if (n3 != 9) break block26;
            }
            arrby = this._inputBuffer;
            n = n2 + 1;
            if ((n2 = arrby[n2]) > 32 && n2 != 47 && n2 != 35) {
                this._inputPtr = n;
                return n2;
            }
        }
        this._inputPtr = n - 1;
        return this._skipColon2(true);
    }

    private final void _skipComment() throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrby[n] & 255) == 47) {
            this._skipLine();
            return;
        }
        if (n == 42) {
            this._skipCComment();
            return;
        }
        this._reportUnexpectedChar(n, "was expecting either '*' or '/' for a comment");
    }

    private final void _skipLine() throws IOException {
        int[] arrn = CharTypes.getInputCodeComment();
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this._loadMore()) return;
            }
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            int n2 = arrby[n] & 255;
            n = arrn[n2];
            if (n == 0) continue;
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n == 10) {
                            ++this._currInputRow;
                            this._currInputRowStart = this._inputPtr;
                            return;
                        }
                        if (n == 13) {
                            this._skipCR();
                            return;
                        }
                        if (n == 42 || n >= 0) continue;
                        this._reportInvalidChar(n2);
                        continue;
                    }
                    this._skipUtf8_4(n2);
                    continue;
                }
                this._skipUtf8_3();
                continue;
            }
            this._skipUtf8_2();
        } while (true);
    }

    private final void _skipUtf8_2() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) == 128) return;
        this._reportInvalidOther(n & 255, this._inputPtr);
    }

    private final void _skipUtf8_3() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) != 128) {
            this._reportInvalidOther(n & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) == 128) return;
        this._reportInvalidOther(n & 255, this._inputPtr);
    }

    private final void _skipUtf8_4(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) != 128) {
            this._reportInvalidOther(n & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) != 128) {
            this._reportInvalidOther(n & 255, this._inputPtr);
        }
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        arrby = this._inputBuffer;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        if (((n = arrby[n]) & 192) == 128) return;
        this._reportInvalidOther(n & 255, this._inputPtr);
    }

    private final int _skipWS() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrby[n] & 255) > 32) {
                if (n != 47) {
                    if (n != 35) return n;
                }
                --this._inputPtr;
                return this._skipWS2();
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
        return this._skipWS2();
    }

    private final int _skipWS2() throws IOException {
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
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = object[n] & 255) > 32) {
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
        } while (true);
    }

    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrby[n] & 255) > 32) {
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
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrby[n] & 255) > 32) {
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

    private final int _skipWSOrEnd2() throws IOException {
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this._loadMore()) return this._eofAsNextChar();
            }
            byte[] arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = arrby[n] & 255) > 32) {
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
        } while (true);
    }

    private final boolean _skipYAMLComment() throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
            return false;
        }
        this._skipLine();
        return true;
    }

    private final void _updateLocation() {
        this._tokenInputRow = this._currInputRow;
        int n = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + (long)n;
        this._tokenInputCol = n - this._currInputRowStart;
    }

    private final void _updateNameLocation() {
        int n;
        this._nameStartRow = this._currInputRow;
        this._nameStartOffset = n = this._inputPtr;
        this._nameStartCol = n - this._currInputRowStart;
    }

    private final int _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return 48;
        }
        int n = this._inputBuffer[this._inputPtr] & 255;
        if (n < 48) return 48;
        if (n > 57) {
            return 48;
        }
        if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        int n2 = n;
        if (n != 48) return n2;
        n2 = n;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (!this._loadMore()) return n2;
            }
            if ((n = this._inputBuffer[this._inputPtr] & 255) < 48) return 48;
            if (n > 57) {
                return 48;
            }
            ++this._inputPtr;
            n2 = n;
        } while (n == 48);
        return n;
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

    /*
     * Unable to fully structure code
     */
    private final String addName(int[] var1_1, int var2_2, int var3_3) throws JsonParseException {
        var4_4 = (var2_2 << 2) - 4 + var3_3;
        if (var3_3 < 4) {
            var5_5 = var2_2 - 1;
            var6_6 = var1_1[var5_5];
            var1_1[var5_5] = var6_6 << (4 - var3_3 << 3);
        } else {
            var6_6 = 0;
        }
        var7_7 = this._textBuffer.emptyAndGetCurrentSegment();
        var8_13 = 0;
        var5_5 = 0;
        do {
            block18 : {
                block19 : {
                    if (var8_13 >= var4_4) {
                        var7_12 = new String((char[])var7_8, 0, var5_5);
                        if (var3_3 >= 4) return this._symbols.addName(var7_12, var1_1, var2_2);
                        var1_1[var2_2 - 1] = var6_6;
                        return this._symbols.addName(var7_12, var1_1, var2_2);
                    }
                    var9_14 = var1_1[var8_13 >> 2] >> (3 - (var8_13 & 3) << 3) & 255;
                    var10_15 = var8_13 + 1;
                    var11_17 = var7_8;
                    var8_13 = var10_15;
                    var12_22 = var5_5;
                    var13_23 = var9_14;
                    if (var9_14 <= 127) break block18;
                    if ((var9_14 & 224) != 192) break block19;
                    var14_24 = var9_14 & 31;
                    ** GOTO lbl38
                }
                if ((var9_14 & 240) == 224) {
                    var14_24 = var9_14 & 15;
                    var9_14 = 2;
                } else if ((var9_14 & 248) == 240) {
                    var14_24 = var9_14 & 7;
                    var9_14 = 3;
                } else {
                    this._reportInvalidInitial(var9_14);
                    var14_24 = 1;
lbl38: // 2 sources:
                    var9_14 = 1;
                }
                if (var10_15 + var9_14 > var4_4) {
                    this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
                }
                var13_23 = var1_1[var10_15 >> 2] >> (3 - (var10_15 & 3) << 3);
                var8_13 = var10_15 + 1;
                if ((var13_23 & 192) != 128) {
                    this._reportInvalidOther(var13_23);
                }
                var10_15 = var13_23 & 63 | var14_24 << 6;
                if (var9_14 > 1) {
                    var14_24 = var1_1[var8_13 >> 2] >> (3 - (var8_13 & 3) << 3);
                    var13_23 = var8_13 + 1;
                    if ((var14_24 & 192) != 128) {
                        this._reportInvalidOther(var14_24);
                    }
                    var8_13 = var10_15 = var14_24 & 63 | var10_15 << 6;
                    var14_24 = var13_23;
                    if (var9_14 > 2) {
                        var8_13 = var1_1[var13_23 >> 2] >> (3 - (var13_23 & 3) << 3);
                        var14_24 = var13_23 + 1;
                        if ((var8_13 & 192) != 128) {
                            this._reportInvalidOther(var8_13 & 255);
                        }
                        var8_13 = var10_15 << 6 | var8_13 & 63;
                    }
                    var10_15 = var8_13;
                } else {
                    var14_24 = var8_13;
                }
                var11_18 = var7_8;
                var8_13 = var14_24;
                var12_22 = var5_5;
                var13_23 = var10_15;
                if (var9_14 > 2) {
                    var9_14 = var10_15 - 65536;
                    var11_19 = var7_8;
                    if (var5_5 >= ((void)var7_8).length) {
                        var11_20 = this._textBuffer.expandCurrentSegment();
                    }
                    var11_21[var5_5] = (char)((var9_14 >> 10) + 55296);
                    var13_23 = var9_14 & 1023 | 56320;
                    var12_22 = var5_5 + 1;
                    var8_13 = var14_24;
                }
            }
            var7_9 = var11_16;
            if (var12_22 >= ((void)var11_16).length) {
                var7_10 = this._textBuffer.expandCurrentSegment();
            }
            var7_11[var12_22] = (char)var13_23;
            var5_5 = var12_22 + 1;
        } while (true);
    }

    private final String findName(int n, int n2) throws JsonParseException {
        int[] arrn = this._symbols.findName(n = UTF8StreamJsonParser._padLastQuad(n, n2));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        return this.addName(arrn, 1, n2);
    }

    private final String findName(int n, int n2, int n3) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2 = UTF8StreamJsonParser._padLastQuad(n2, n3));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        return this.addName(arrn, 2, n3);
    }

    private final String findName(int n, int n2, int n3, int n4) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2, n3 = UTF8StreamJsonParser._padLastQuad(n3, n4));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        arrn[2] = UTF8StreamJsonParser._padLastQuad(n3, n4);
        return this.addName(arrn, 3, n4);
    }

    private final String findName(int[] object, int n, int n2, int n3) throws JsonParseException {
        int[] arrn = object;
        if (n >= ((int[])object).length) {
            arrn = UTF8StreamJsonParser.growArrayBy(object, ((int[])object).length);
            this._quadBuffer = arrn;
        }
        int n4 = n + 1;
        arrn[n] = UTF8StreamJsonParser._padLastQuad(n2, n3);
        object = this._symbols.findName(arrn, n4);
        if (object != null) return object;
        return this.addName(arrn, n4, n3);
    }

    private int nextByte() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        return arrby[n] & 255;
    }

    private final String parseName(int n, int n2, int n3) throws IOException {
        return this.parseEscapedName(this._quadBuffer, 0, n, n2, n3);
    }

    private final String parseName(int n, int n2, int n3, int n4) throws IOException {
        int[] arrn = this._quadBuffer;
        arrn[0] = n;
        return this.parseEscapedName(arrn, 1, n2, n3, n4);
    }

    private final String parseName(int n, int n2, int n3, int n4, int n5) throws IOException {
        int[] arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        return this.parseEscapedName(arrn, 2, n3, n4, n5);
    }

    @Override
    protected void _closeInput() throws IOException {
        if (this._inputStream == null) return;
        if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._inputStream.close();
        }
        this._inputStream = null;
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        Object object = this._getByteArrayBuilder();
        do {
            int n;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            byte[] arrby = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n3 = arrby[n2] & 255;
            if (n3 <= 32) continue;
            n2 = n = base64Variant.decodeBase64Char(n3);
            if (n < 0) {
                if (n3 == 34) {
                    return ((ByteArrayBuilder)object).toByteArray();
                }
                n2 = n = this._decodeBase64Escape(base64Variant, n3, 0);
                if (n < 0) continue;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            int n4 = arrby[n] & 255;
            n = n3 = base64Variant.decodeBase64Char(n4);
            if (n3 < 0) {
                n = this._decodeBase64Escape(base64Variant, n4, 1);
            }
            n4 = n2 << 6 | n;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            int n5 = arrby[n2] & 255;
            n = n3 = base64Variant.decodeBase64Char(n5);
            if (n3 < 0) {
                n2 = n3;
                if (n3 != -2) {
                    if (n5 == 34) {
                        ((ByteArrayBuilder)object).append(n4 >> 4);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        --this._inputPtr;
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n2 = this._decodeBase64Escape(base64Variant, n5, 2);
                }
                n = n2;
                if (n2 == -2) {
                    if (this._inputPtr >= this._inputEnd) {
                        this._loadMoreGuaranteed();
                    }
                    arrby = this._inputBuffer;
                    n2 = this._inputPtr;
                    this._inputPtr = n2 + 1;
                    if (!base64Variant.usesPaddingChar(n2 = arrby[n2] & 255) && this._decodeBase64Escape(base64Variant, n2, 3) != -2) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("expected padding character '");
                        ((StringBuilder)object).append(base64Variant.getPaddingChar());
                        ((StringBuilder)object).append("'");
                        throw this.reportInvalidBase64Char(base64Variant, n2, 3, ((StringBuilder)object).toString());
                    }
                    ((ByteArrayBuilder)object).append(n4 >> 4);
                    continue;
                }
            }
            n4 = n4 << 6 | n;
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n5 = arrby[n2] & 255;
            n = n3 = base64Variant.decodeBase64Char(n5);
            if (n3 < 0) {
                n2 = n3;
                if (n3 != -2) {
                    if (n5 == 34) {
                        ((ByteArrayBuilder)object).appendTwoBytes(n4 >> 2);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        --this._inputPtr;
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n2 = this._decodeBase64Escape(base64Variant, n5, 3);
                }
                n = n2;
                if (n2 == -2) {
                    ((ByteArrayBuilder)object).appendTwoBytes(n4 >> 2);
                    continue;
                }
            }
            ((ByteArrayBuilder)object).appendThreeBytes(n4 << 6 | n);
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    protected int _decodeCharForError(int var1_1) throws IOException {
        block6 : {
            var1_1 = var2_2 = var1_1 & 255;
            if (var2_2 <= 127) return var1_1;
            if ((var2_2 & 224) != 192) break block6;
            var1_1 = var2_2 & 31;
            ** GOTO lbl17
        }
        if ((var2_2 & 240) == 224) {
            var1_1 = var2_2 & 15;
            var2_2 = 2;
        } else if ((var2_2 & 248) == 240) {
            var1_1 = var2_2 & 7;
            var2_2 = 3;
        } else {
            this._reportInvalidInitial(var2_2 & 255);
            var1_1 = var2_2;
lbl17: // 2 sources:
            var2_2 = 1;
        }
        var3_3 = this.nextByte();
        if ((var3_3 & 192) != 128) {
            this._reportInvalidOther(var3_3 & 255);
        }
        var1_1 = var3_3 = var1_1 << 6 | var3_3 & 63;
        if (var2_2 <= 1) return var1_1;
        var1_1 = this.nextByte();
        if ((var1_1 & 192) != 128) {
            this._reportInvalidOther(var1_1 & 255);
        }
        var1_1 = var3_3 = var3_3 << 6 | var1_1 & 63;
        if (var2_2 <= 2) return var1_1;
        var1_1 = this.nextByte();
        if ((var1_1 & 192) == 128) return var3_3 << 6 | var1_1 & 63;
        this._reportInvalidOther(var1_1 & 255);
        return var3_3 << 6 | var1_1 & 63;
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrby[n]) == 34) return (char)n;
        if (n == 47) return (char)n;
        if (n == 92) return (char)n;
        if (n == 98) return '\b';
        if (n == 102) return '\f';
        if (n == 110) return '\n';
        if (n == 114) return '\r';
        if (n == 116) return '\t';
        if (n != 117) {
            return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(n));
        }
        n = 0;
        int n2 = 0;
        while (n < 4) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
            }
            arrby = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            int n4 = CharTypes.charToHex(n3 = arrby[n3]);
            if (n4 < 0) {
                this._reportUnexpectedChar(n3 & 255, "expected a hex-digit for character escape sequence");
            }
            n2 = n2 << 4 | n4;
            ++n;
        }
        return (char)n2;
    }

    protected String _finishAndReturnString() throws IOException {
        int n;
        int n2 = n = this._inputPtr;
        if (n >= this._inputEnd) {
            this._loadMoreGuaranteed();
            n2 = this._inputPtr;
        }
        n = 0;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n3 = Math.min(this._inputEnd, arrc.length + n2);
        byte[] arrby = this._inputBuffer;
        while (n2 < n3) {
            int n4 = arrby[n2] & 255;
            if (arrn[n4] != 0) {
                if (n4 != 34) break;
                this._inputPtr = n2 + 1;
                return this._textBuffer.setCurrentAndReturn(n);
            }
            ++n2;
            arrc[n] = (char)n4;
            ++n;
        }
        this._inputPtr = n2;
        this._finishString2(arrc, n);
        return this._textBuffer.contentsAsString();
    }

    @Override
    protected void _finishString() throws IOException {
        int n;
        int n2 = n = this._inputPtr;
        if (n >= this._inputEnd) {
            this._loadMoreGuaranteed();
            n2 = this._inputPtr;
        }
        n = 0;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n3 = Math.min(this._inputEnd, arrc.length + n2);
        byte[] arrby = this._inputBuffer;
        while (n2 < n3) {
            int n4 = arrby[n2] & 255;
            if (arrn[n4] != 0) {
                if (n4 != 34) break;
                this._inputPtr = n2 + 1;
                this._textBuffer.setCurrentLength(n);
                return;
            }
            ++n2;
            arrc[n] = (char)n4;
            ++n;
        }
        this._inputPtr = n2;
        this._finishString2(arrc, n);
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
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        int n = 0;
        block0 : do {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char[] arrc2 = arrc;
            int n2 = n;
            if (n >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n2 = 0;
            }
            int n3 = this._inputEnd;
            n = this._inputPtr + (arrc2.length - n2);
            int n4 = n2;
            int n5 = n3;
            if (n < n3) {
                n5 = n;
                n4 = n2;
            }
            do {
                arrc = arrc2;
                n = n4;
                if (this._inputPtr >= n5) continue block0;
                n = this._inputPtr;
                this._inputPtr = n + 1;
                if ((n = arrby[n] & 255) == 39 || arrn[n] != 0) break;
                arrc2[n4] = (char)n;
                ++n4;
            } while (true);
            if (n == 39) {
                this._textBuffer.setCurrentLength(n4);
                return JsonToken.VALUE_STRING;
            }
            n2 = arrn[n];
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n < 32) {
                                this._throwUnquotedSpace(n, "string value");
                            }
                            this._reportInvalidChar(n);
                        } else {
                            n2 = this._decodeUtf8_4(n);
                            n = n4 + 1;
                            arrc2[n4] = (char)(55296 | n2 >> 10);
                            if (n >= arrc2.length) {
                                arrc2 = this._textBuffer.finishCurrentSegment();
                                n4 = 0;
                            } else {
                                n4 = n;
                            }
                            n = 56320 | n2 & 1023;
                        }
                    } else {
                        n = this._inputEnd - this._inputPtr >= 2 ? this._decodeUtf8_3fast(n) : this._decodeUtf8_3(n);
                    }
                } else {
                    n = this._decodeUtf8_2(n);
                }
            } else {
                n = this._decodeEscaped();
            }
            arrc = arrc2;
            n2 = n4;
            if (n4 >= arrc2.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n2 = 0;
            }
            arrc[n2] = (char)n;
            n = n2 + 1;
        } while (true);
    }

    protected JsonToken _handleInvalidNumberStart(int n, boolean bl) throws IOException {
        int n2;
        do {
            n2 = n;
            if (n != 73) break;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_FLOAT);
            }
            Object object = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            if ((n = object[n]) == 78) {
                object = bl ? "-INF" : "+INF";
            } else {
                n2 = n;
                if (n != 110) break;
                object = bl ? "-Infinity" : "+Infinity";
            }
            this._matchToken((String)object, 3);
            if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                double d;
                if (bl) {
                    d = Double.NEGATIVE_INFINITY;
                    return this.resetAsNaN((String)object, d);
                }
                d = Double.POSITIVE_INFINITY;
                return this.resetAsNaN((String)object, d);
            }
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", object);
        } while (true);
        this.reportUnexpectedNumberChar(n2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    protected String _handleOddName(int n) throws IOException {
        Object object;
        if (n == 39 && (this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._parseAposName();
        }
        if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar((char)this._decodeCharForError(n), "was expecting double-quote to start field name");
        }
        if ((object = CharTypes.getInputCodeUtf8JsNames())[n] != 0) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] arrn = this._quadBuffer;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        do {
            Object object2;
            int n5;
            if (n2 < 4) {
                n |= n4 << 8;
                object2 = arrn;
                n4 = ++n2;
            } else {
                object2 = arrn;
                if (n3 >= arrn.length) {
                    this._quadBuffer = object2 = UTF8StreamJsonParser.growArrayBy(arrn, arrn.length);
                }
                object2[n3] = n4;
                ++n3;
                n4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            if (object[n5 = this._inputBuffer[this._inputPtr] & 255] != 0) {
                arrn = object2;
                n2 = n3;
                if (n4 > 0) {
                    arrn = object2;
                    if (n3 >= ((int[])object2).length) {
                        this._quadBuffer = arrn = UTF8StreamJsonParser.growArrayBy(object2, ((int[])object2).length);
                    }
                    arrn[n3] = n;
                    n2 = n3 + 1;
                }
                object = this._symbols.findName(arrn, n2);
                object2 = object;
                if (object != null) return object2;
                return this.addName(arrn, n2, n4);
            }
            ++this._inputPtr;
            arrn = object2;
            n2 = n4;
            n4 = n;
            n = n5;
        } while (true);
    }

    protected JsonToken _handleUnexpectedValue(int n) throws IOException {
        StringBuilder stringBuilder;
        block15 : {
            block8 : {
                block9 : {
                    block10 : {
                        block12 : {
                            block14 : {
                                block11 : {
                                    block13 : {
                                        if (n == 39) break block8;
                                        if (n == 73) break block9;
                                        if (n == 78) break block10;
                                        if (n == 93) break block11;
                                        if (n == 125) break block12;
                                        if (n == 43) break block13;
                                        if (n == 44) break block14;
                                        break block15;
                                    }
                                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                        this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                                    }
                                    byte[] arrby = this._inputBuffer;
                                    n = this._inputPtr;
                                    this._inputPtr = n + 1;
                                    return this._handleInvalidNumberStart(arrby[n] & 255, false);
                                }
                                if (!this._parsingContext.inArray()) break block15;
                            }
                            if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                                --this._inputPtr;
                                return JsonToken.VALUE_NULL;
                            }
                        }
                        this._reportUnexpectedChar(n, "expected a value");
                        break block8;
                    }
                    this._matchToken("NaN", 1);
                    if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                        return this.resetAsNaN("NaN", Double.NaN);
                    }
                    this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break block15;
                }
                this._matchToken("Infinity", 1);
                if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break block15;
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

    protected final boolean _loadMore() throws IOException {
        int n = this._inputEnd;
        InputStream inputStream2 = this._inputStream;
        if (inputStream2 == null) return false;
        Object object = this._inputBuffer;
        int n2 = ((byte[])object).length;
        if (n2 == 0) {
            return false;
        }
        if ((n2 = inputStream2.read((byte[])object, 0, n2)) > 0) {
            this._inputPtr = 0;
            this._inputEnd = n2;
            this._currInputProcessed += (long)this._inputEnd;
            this._currInputRowStart -= this._inputEnd;
            this._nameStartOffset -= n;
            return true;
        }
        this._closeInput();
        if (n2 != 0) {
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("InputStream.read() returned 0 characters when trying to read ");
        ((StringBuilder)object).append(this._inputBuffer.length);
        ((StringBuilder)object).append(" bytes");
        throw new IOException(((StringBuilder)object).toString());
    }

    protected void _loadMoreGuaranteed() throws IOException {
        if (this._loadMore()) return;
        this._reportInvalidEOF();
    }

    protected final void _matchFalse() throws IOException {
        int n = this._inputPtr;
        if (n + 4 < this._inputEnd) {
            byte[] arrby = this._inputBuffer;
            int n2 = n + 1;
            if (arrby[n] == 97) {
                n = n2 + 1;
                if (arrby[n2] == 108) {
                    n2 = n + 1;
                    if (arrby[n] == 115) {
                        n = n2 + 1;
                        if (arrby[n2] == 101 && ((n2 = arrby[n] & 255) < 48 || n2 == 93 || n2 == 125)) {
                            this._inputPtr = n;
                            return;
                        }
                    }
                }
            }
        }
        this._matchToken2("false", 1);
    }

    protected final void _matchNull() throws IOException {
        int n = this._inputPtr;
        if (n + 3 < this._inputEnd) {
            byte[] arrby = this._inputBuffer;
            int n2 = n + 1;
            if (arrby[n] == 117) {
                n = n2 + 1;
                if (arrby[n2] == 108) {
                    n2 = n + 1;
                    if (arrby[n] == 108 && ((n = arrby[n2] & 255) < 48 || n == 93 || n == 125)) {
                        this._inputPtr = n2;
                        return;
                    }
                }
            }
        }
        this._matchToken2("null", 1);
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
        n3 = this._inputBuffer[this._inputPtr] & 255;
        if (n3 < 48) return;
        if (n3 == 93) return;
        if (n3 == 125) return;
        this._checkMatchEnd(string2, n, n3);
    }

    protected final void _matchTrue() throws IOException {
        int n = this._inputPtr;
        if (n + 3 < this._inputEnd) {
            byte[] arrby = this._inputBuffer;
            int n2 = n + 1;
            if (arrby[n] == 114) {
                n = n2 + 1;
                if (arrby[n2] == 117) {
                    n2 = n + 1;
                    if (arrby[n] == 101 && ((n = arrby[n2] & 255) < 48 || n == 93 || n == 125)) {
                        this._inputPtr = n2;
                        return;
                    }
                }
            }
        }
        this._matchToken2("true", 1);
    }

    protected String _parseAposName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
        }
        Object object = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = object[n] & 255;
        if (n2 == 39) {
            return "";
        }
        object = this._quadBuffer;
        Object object2 = _icLatin1;
        int n3 = 0;
        n = 0;
        int n4 = 0;
        do {
            byte[] arrby;
            if (n2 == 39) {
                arrby = object;
                n2 = n;
                if (n3 > 0) {
                    arrby = object;
                    if (n >= ((byte[])object).length) {
                        arrby = UTF8StreamJsonParser.growArrayBy(object, ((byte[])object).length);
                        this._quadBuffer = arrby;
                    }
                    arrby[n] = UTF8StreamJsonParser._padLastQuad(n4, n3);
                    n2 = n + 1;
                }
                object2 = this._symbols.findName(arrby, n2);
                object = object2;
                if (object2 != null) return object;
                return this.addName(arrby, n2, n3);
            }
            int n5 = n2;
            arrby = object;
            int n6 = n3;
            int n7 = n;
            int n8 = n4;
            if (object2[n2] != 0) {
                n5 = n2;
                arrby = object;
                n6 = n3;
                n7 = n;
                n8 = n4;
                if (n2 != 34) {
                    int n9;
                    if (n2 != 92) {
                        this._throwUnquotedSpace(n2, "name");
                        n9 = n2;
                    } else {
                        n9 = this._decodeEscaped();
                    }
                    n5 = n9;
                    arrby = object;
                    n6 = n3;
                    n7 = n;
                    n8 = n4;
                    if (n9 > 127) {
                        arrby = object;
                        n5 = n3;
                        n2 = n;
                        n7 = n4;
                        if (n3 >= 4) {
                            arrby = object;
                            if (n >= ((byte[])object).length) {
                                arrby = UTF8StreamJsonParser.growArrayBy(object, ((byte[])object).length);
                                this._quadBuffer = arrby;
                            }
                            arrby[n] = n4;
                            n2 = n + 1;
                            n5 = 0;
                            n7 = 0;
                        }
                        if (n9 < 2048) {
                            n3 = n7 << 8 | (n9 >> 6 | 192);
                            n = n5 + 1;
                            object = arrby;
                        } else {
                            n7 = n7 << 8 | (n9 >> 12 | 224);
                            object = arrby;
                            n4 = ++n5;
                            n = n2;
                            n3 = n7;
                            if (n5 >= 4) {
                                object = arrby;
                                if (n2 >= arrby.length) {
                                    object = UTF8StreamJsonParser.growArrayBy(arrby, arrby.length);
                                    this._quadBuffer = object;
                                }
                                object[n2] = n7;
                                n = n2 + 1;
                                n4 = 0;
                                n3 = 0;
                            }
                            n3 = n3 << 8 | (n9 >> 6 & 63 | 128);
                            n2 = n;
                            n = ++n4;
                        }
                        n5 = n9 & 63 | 128;
                        n8 = n3;
                        n7 = n2;
                        n6 = n;
                        arrby = object;
                    }
                }
            }
            if (n6 < 4) {
                n3 = n6 + 1;
                n4 = n5 | n8 << 8;
                object = arrby;
                n = n7;
            } else {
                object = arrby;
                if (n7 >= arrby.length) {
                    object = UTF8StreamJsonParser.growArrayBy(arrby, arrby.length);
                    this._quadBuffer = object;
                }
                object[n7] = n8;
                n4 = n5;
                n = n7 + 1;
                n3 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n2 = arrby[n2] & 255;
        } while (true);
    }

    protected final String _parseName(int n) throws IOException {
        if (n != 34) {
            return this._handleOddName(n);
        }
        if (this._inputPtr + 13 > this._inputEnd) {
            return this.slowParseName();
        }
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        if (arrn[n = arrby[n] & 255] != 0) {
            if (n != 34) return this.parseName(0, n, 0);
            return "";
        }
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(n, n2, 1);
            return this.findName(n, 1);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(n, n2, 2);
            return this.findName(n, 2);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(n, n2, 3);
            return this.findName(n, 3);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] == 0) {
            this._quad1 = n;
            return this.parseMediumName(n2);
        }
        if (n2 != 34) return this.parseName(n, n2, 4);
        return this.findName(n, 4);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        arrc[0] = (char)45;
        if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = arrby[n] & 255;
        if (n2 <= 48) {
            if (n2 != 48) {
                return this._handleInvalidNumberStart(n2, true);
            }
            n = this._verifyNoLeadingZeroes();
        } else {
            n = n2;
            if (n2 > 57) {
                return this._handleInvalidNumberStart(n2, true);
            }
        }
        int n3 = 2;
        arrc[1] = (char)n;
        int n4 = Math.min(this._inputEnd, this._inputPtr + arrc.length - 2);
        n2 = 1;
        n = n3;
        do {
            if (this._inputPtr >= n4) {
                return this._parseNumber2(arrc, n, true, n2);
            }
            arrby = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if ((n3 = arrby[n3] & 255) < 48 || n3 > 57) break;
            ++n2;
            arrc[n] = (char)n3;
            ++n;
        } while (true);
        if (n3 == 46) return this._parseFloat(arrc, n, n3, true, n2);
        if (n3 == 101) return this._parseFloat(arrc, n, n3, true, n2);
        if (n3 == 69) {
            return this._parseFloat(arrc, n, n3, true, n2);
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n);
        if (!this._parsingContext.inRoot()) return this.resetInt(true, n2);
        this._verifyRootSpace(n3);
        return this.resetInt(true, n2);
    }

    protected JsonToken _parsePosNumber(int n) throws IOException {
        int n2;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n3 = n;
        if (n == 48) {
            n3 = this._verifyNoLeadingZeroes();
        }
        arrc[0] = (char)n3;
        int n4 = Math.min(this._inputEnd, this._inputPtr + arrc.length - 1);
        n3 = 1;
        n = 1;
        do {
            if (this._inputPtr >= n4) {
                return this._parseNumber2(arrc, n3, false, n);
            }
            byte[] arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = arrby[n2] & 255) < 48 || n2 > 57) break;
            ++n;
            arrc[n3] = (char)n2;
            ++n3;
        } while (true);
        if (n2 == 46) return this._parseFloat(arrc, n3, n2, false, n);
        if (n2 == 101) return this._parseFloat(arrc, n3, n2, false, n);
        if (n2 == 69) {
            return this._parseFloat(arrc, n3, n2, false, n);
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n3);
        if (!this._parsingContext.inRoot()) return this.resetInt(false, n);
        this._verifyRootSpace(n2);
        return this.resetInt(false, n);
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
                            byte[] arrby2;
                            block17 : {
                                if (this._inputPtr >= this._inputEnd) {
                                    this._loadMoreGuaranteed();
                                }
                                arrby2 = this._inputBuffer;
                                n = this._inputPtr;
                                this._inputPtr = n + 1;
                                n6 = arrby2[n] & 255;
                                if (n6 <= 32) break block16;
                                n5 = n = base64Variant.decodeBase64Char(n6);
                                if (n >= 0) break block17;
                                if (n6 == 34) break;
                                n5 = n = this._decodeBase64Escape(base64Variant, n6, 0);
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
                            arrby2 = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            int n7 = arrby2[n3] & 255;
                            n3 = n4 = base64Variant.decodeBase64Char(n7);
                            if (n4 < 0) {
                                n3 = this._decodeBase64Escape(base64Variant, n7, 1);
                            }
                            n7 = n5 << 6 | n3;
                            if (this._inputPtr >= this._inputEnd) {
                                this._loadMoreGuaranteed();
                            }
                            arrby2 = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            int n8 = arrby2[n3] & 255;
                            n5 = n4 = base64Variant.decodeBase64Char(n8);
                            if (n4 < 0) {
                                n3 = n4;
                                if (n4 != -2) {
                                    if (n8 == 34) {
                                        arrby[n6] = (byte)(n7 >> 4);
                                        if (base64Variant.usesPadding()) {
                                            --this._inputPtr;
                                            this._handleBase64MissingPadding(base64Variant);
                                        }
                                        n3 = n6 + 1;
                                        n4 = n;
                                        break;
                                    }
                                    n3 = this._decodeBase64Escape(base64Variant, n8, 2);
                                }
                                n5 = n3;
                                if (n3 == -2) {
                                    if (this._inputPtr >= this._inputEnd) {
                                        this._loadMoreGuaranteed();
                                    }
                                    arrby2 = this._inputBuffer;
                                    n3 = this._inputPtr;
                                    this._inputPtr = n3 + 1;
                                    if (!base64Variant.usesPaddingChar(n3 = arrby2[n3] & 255) && this._decodeBase64Escape(base64Variant, n3, 3) != -2) {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("expected padding character '");
                                        ((StringBuilder)object).append(base64Variant.getPaddingChar());
                                        ((StringBuilder)object).append("'");
                                        throw this.reportInvalidBase64Char(base64Variant, n3, 3, ((StringBuilder)object).toString());
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
                            arrby2 = this._inputBuffer;
                            n3 = this._inputPtr;
                            this._inputPtr = n3 + 1;
                            n4 = arrby2[n3] & 255;
                            n3 = base64Variant.decodeBase64Char(n4);
                            if (n3 >= 0) break block18;
                            if (n3 != -2) {
                                if (n4 == 34) {
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
                                n3 = this._decodeBase64Escape(base64Variant, n4, 3);
                            }
                            n4 = n3;
                            if (n3 != -2) break block19;
                            n4 = n5 >> 2;
                            n5 = n6 + 1;
                            arrby[n6] = (byte)(n4 >> 8);
                            n3 = n5 + 1;
                            arrby[n5] = (byte)n4;
                            break block20;
                        }
                        n4 = n3;
                    }
                    n4 = n5 << 6 | n4;
                    n3 = n6 + 1;
                    arrby[n6] = (byte)(n4 >> 16);
                    n6 = n3 + 1;
                    arrby[n3] = (byte)(n4 >> 8);
                    arrby[n6] = (byte)n4;
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
        byte[] arrby = this._inputBuffer;
        if (arrby == null) return;
        this._inputBuffer = NO_BYTES;
        this._ioContext.releaseReadIOBuffer(arrby);
    }

    protected void _reportInvalidChar(int n) throws JsonParseException {
        if (n < 32) {
            this._throwInvalidSpace(n);
        }
        this._reportInvalidInitial(n);
    }

    protected void _reportInvalidInitial(int n) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid UTF-8 start byte 0x");
        stringBuilder.append(Integer.toHexString(n));
        this._reportError(stringBuilder.toString());
    }

    protected void _reportInvalidOther(int n) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid UTF-8 middle byte 0x");
        stringBuilder.append(Integer.toHexString(n));
        this._reportError(stringBuilder.toString());
    }

    protected void _reportInvalidOther(int n, int n2) throws JsonParseException {
        this._inputPtr = n2;
        this._reportInvalidOther(n);
    }

    protected void _reportInvalidToken(String string2) throws IOException {
        this._reportInvalidToken(string2, this._validJsonTokenList());
    }

    protected void _reportInvalidToken(String string2, int n) throws IOException {
        this._inputPtr = n;
        this._reportInvalidToken(string2, this._validJsonTokenList());
    }

    protected void _reportInvalidToken(String arrby, String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder((String)arrby);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            char c = (char)this._decodeCharForError(arrby[n]);
            if (!Character.isJavaIdentifierPart(c)) break;
            stringBuilder.append(c);
            if (stringBuilder.length() < 256) continue;
            stringBuilder.append("...");
            break;
        }
        this._reportError("Unrecognized token '%s': was expecting %s", stringBuilder, string2);
    }

    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        block0 : do {
            int n = this._inputPtr;
            int n2 = this._inputEnd;
            int n3 = n;
            int n4 = n2;
            if (n >= n2) {
                this._loadMoreGuaranteed();
                n3 = this._inputPtr;
                n4 = this._inputEnd;
            }
            while (n3 < n4) {
                n2 = n3 + 1;
                if (arrn[n3 = arrby[n3] & 255] != 0) {
                    this._inputPtr = n2;
                    if (n3 == 34) {
                        return;
                    }
                    n4 = arrn[n3];
                    if (n4 != 1) {
                        if (n4 != 2) {
                            if (n4 != 3) {
                                if (n4 != 4) {
                                    if (n3 < 32) {
                                        this._throwUnquotedSpace(n3, "string value");
                                        continue block0;
                                    }
                                    this._reportInvalidChar(n3);
                                    continue block0;
                                }
                                this._skipUtf8_4(n3);
                                continue block0;
                            }
                            this._skipUtf8_3();
                            continue block0;
                        }
                        this._skipUtf8_2();
                        continue block0;
                    }
                    this._decodeEscaped();
                    continue block0;
                }
                n3 = n2;
            }
            this._inputPtr = n3;
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
        if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
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
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (long)this._inputPtr, -1L, this._currInputRow, n - n2 + 1);
    }

    @Override
    public Object getInputSource() {
        return this._inputStream;
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
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) return this._getText2(this._currToken);
        if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
        this._tokenIncomplete = false;
        return this._finishAndReturnString();
    }

    @Override
    public char[] getTextCharacters() throws IOException {
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
    public int getTextLength() throws IOException {
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
    public int getTextOffset() throws IOException {
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
        if (this._currToken != JsonToken.FIELD_NAME) return new JsonLocation(this._getSourceReference(), this._tokenInputTotal - 1L, -1L, this._tokenInputRow, this._tokenInputCol);
        long l = this._currInputProcessed;
        long l2 = this._nameStartOffset - 1;
        return new JsonLocation(this._getSourceReference(), l + l2, -1L, this._nameStartRow, this._nameStartCol);
    }

    @Override
    public int getValueAsInt() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT) {
            if (jsonToken != JsonToken.VALUE_NUMBER_FLOAT) return super.getValueAsInt(0);
        }
        if ((this._numTypesValid & 1) != 0) return this._numberInt;
        if (this._numTypesValid == 0) {
            return this._parseIntValue();
        }
        if ((this._numTypesValid & 1) != 0) return this._numberInt;
        this.convertNumberToInt();
        return this._numberInt;
    }

    @Override
    public int getValueAsInt(int n) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_NUMBER_INT) {
            if (jsonToken != JsonToken.VALUE_NUMBER_FLOAT) return super.getValueAsInt(n);
        }
        if ((this._numTypesValid & 1) != 0) return this._numberInt;
        if (this._numTypesValid == 0) {
            return this._parseIntValue();
        }
        if ((this._numTypesValid & 1) != 0) return this._numberInt;
        this.convertNumberToInt();
        return this._numberInt;
    }

    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(null);
        return this.getCurrentName();
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.contentsAsString();
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(string2);
        return this.getCurrentName();
    }

    @Override
    public Boolean nextBooleanValue() throws IOException {
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
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (jsonToken != JsonToken.VALUE_FALSE) return null;
        return Boolean.FALSE;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public String nextFieldName() throws IOException {
        block26 : {
            block27 : {
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
                if (var1_1 == 93) {
                    this._closeArrayScope();
                    this._currToken = JsonToken.END_ARRAY;
                    return null;
                }
                if (var1_1 == 125) {
                    this._closeObjectScope();
                    this._currToken = JsonToken.END_OBJECT;
                    return null;
                }
                var2_2 = var1_1;
                if (!this._parsingContext.expectComma()) break block26;
                if (var1_1 != 44) {
                    var3_3 = new StringBuilder();
                    var3_3.append("was expecting comma to separate ");
                    var3_3.append(this._parsingContext.typeDesc());
                    var3_3.append(" entries");
                    this._reportUnexpectedChar(var1_1, var3_3.toString());
                }
                var2_2 = var1_1 = this._skipWS();
                if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) == 0) break block26;
                if (var1_1 == 93) break block27;
                var2_2 = var1_1;
                if (var1_1 != 125) break block26;
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
        var4_13 = this._parseName(var2_2);
        this._parsingContext.setCurrentName(var4_13);
        this._currToken = JsonToken.FIELD_NAME;
        var2_2 = this._skipColon();
        this._updateLocation();
        if (var2_2 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return var4_13;
        }
        if (var2_2 != 45) {
            if (var2_2 != 91) {
                if (var2_2 != 102) {
                    if (var2_2 != 110) {
                        if (var2_2 != 116) {
                            if (var2_2 != 123) {
                                switch (var2_2) {
                                    default: {
                                        var3_4 = this._handleUnexpectedValue(var2_2);
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
                                var3_5 = this._parsePosNumber(var2_2);
                                ** break;
lbl70: // 2 sources:
                            } else {
                                var3_6 = JsonToken.START_OBJECT;
                            }
                        } else {
                            this._matchTrue();
                            var3_7 = JsonToken.VALUE_TRUE;
                        }
                    } else {
                        this._matchNull();
                        var3_8 = JsonToken.VALUE_NULL;
                    }
                } else {
                    this._matchFalse();
                    var3_9 = JsonToken.VALUE_FALSE;
                }
            } else {
                var3_10 = JsonToken.START_ARRAY;
            }
        } else {
            var3_11 = this._parseNegNumber();
        }
        this._nextToken = var3_12;
        return var4_13;
    }

    @Override
    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        int n;
        int n2;
        byte[] arrby;
        int n3;
        block13 : {
            block14 : {
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
                if (n3 == 93) {
                    this._closeArrayScope();
                    this._currToken = JsonToken.END_ARRAY;
                    return false;
                }
                if (n3 == 125) {
                    this._closeObjectScope();
                    this._currToken = JsonToken.END_OBJECT;
                    return false;
                }
                n = n3;
                if (!this._parsingContext.expectComma()) break block13;
                if (n3 != 44) {
                    arrby = new StringBuilder();
                    arrby.append("was expecting comma to separate ");
                    arrby.append(this._parsingContext.typeDesc());
                    arrby.append(" entries");
                    this._reportUnexpectedChar(n3, arrby.toString());
                }
                n = n3 = this._skipWS();
                if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) break block13;
                if (n3 == 93) break block14;
                n = n3;
                if (n3 != 125) break block13;
            }
            this._closeScope(n3);
            return false;
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(n);
            return false;
        }
        this._updateNameLocation();
        if (n != 34) return this._isNextTokenNameMaybe(n, serializableString);
        arrby = serializableString.asQuotedUTF8();
        n3 = arrby.length;
        if (this._inputPtr + n3 + 4 >= this._inputEnd) return this._isNextTokenNameMaybe(n, serializableString);
        int n4 = this._inputPtr + n3;
        if (this._inputBuffer[n4] != 34) return this._isNextTokenNameMaybe(n, serializableString);
        n3 = this._inputPtr;
        do {
            if (n3 == n4) {
                this._parsingContext.setCurrentName(serializableString.getValue());
                this._isNextTokenNameYes(this._skipColonFast(n3 + 1));
                return true;
            }
            if (arrby[n2] != this._inputBuffer[n3]) {
                return this._isNextTokenNameMaybe(n, serializableString);
            }
            ++n2;
            ++n3;
        } while (true);
    }

    @Override
    public int nextIntValue(int n) throws IOException {
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
    public long nextLongValue(long l) throws IOException {
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
    public String nextTextValue() throws IOException {
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
            return this._finishAndReturnString();
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
    public JsonToken nextToken() throws IOException {
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
        if (var1_1 == 93) {
            this._closeArrayScope();
            this._currToken = var2_2 = JsonToken.END_ARRAY;
            return var2_2;
        }
        if (var1_1 == 125) {
            this._closeObjectScope();
            this._currToken = var2_3 = JsonToken.END_OBJECT;
            return var2_3;
        }
        var3_15 = var1_1;
        if (this._parsingContext.expectComma()) {
            if (var1_1 != 44) {
                var2_4 = new StringBuilder();
                var2_4.append("was expecting comma to separate ");
                var2_4.append(this._parsingContext.typeDesc());
                var2_4.append(" entries");
                this._reportUnexpectedChar(var1_1, var2_4.toString());
            }
            var3_15 = var1_1 = this._skipWS();
            if ((this._features & UTF8StreamJsonParser.FEAT_MASK_TRAILING_COMMA) != 0) {
                if (var1_1 == 93) return this._closeScope(var1_1);
                var3_15 = var1_1;
                if (var1_1 == 125) {
                    return this._closeScope(var1_1);
                }
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            return this._nextTokenNotInObject(var3_15);
        }
        this._updateNameLocation();
        var2_5 = this._parseName(var3_15);
        this._parsingContext.setCurrentName(var2_5);
        this._currToken = JsonToken.FIELD_NAME;
        var3_15 = this._skipColon();
        this._updateLocation();
        if (var3_15 == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return this._currToken;
        }
        if (var3_15 != 45) {
            if (var3_15 != 91) {
                if (var3_15 != 102) {
                    if (var3_15 != 110) {
                        if (var3_15 != 116) {
                            if (var3_15 != 123) {
                                switch (var3_15) {
                                    default: {
                                        var2_6 = this._handleUnexpectedValue(var3_15);
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
                                var2_7 = this._parsePosNumber(var3_15);
                                ** break;
lbl62: // 2 sources:
                            } else {
                                var2_8 = JsonToken.START_OBJECT;
                            }
                        } else {
                            this._matchTrue();
                            var2_9 = JsonToken.VALUE_TRUE;
                        }
                    } else {
                        this._matchNull();
                        var2_10 = JsonToken.VALUE_NULL;
                    }
                } else {
                    this._matchFalse();
                    var2_11 = JsonToken.VALUE_FALSE;
                }
            } else {
                var2_12 = JsonToken.START_ARRAY;
            }
        } else {
            var2_13 = this._parseNegNumber();
        }
        this._nextToken = var2_14;
        return this._currToken;
    }

    protected final String parseEscapedName(int[] object, int n, int n2, int n3, int n4) throws IOException {
        Object object2 = _icLatin1;
        int n5 = n3;
        do {
            int[] arrn = object;
            int n6 = n;
            int n7 = n2;
            n3 = n5;
            int n8 = n4;
            if (object2[n5] != 0) {
                if (n5 == 34) {
                    arrn = object;
                    n3 = n;
                    if (n4 > 0) {
                        arrn = object;
                        if (n >= ((int[])object).length) {
                            arrn = UTF8StreamJsonParser.growArrayBy(object, ((int[])object).length);
                            this._quadBuffer = arrn;
                        }
                        arrn[n] = UTF8StreamJsonParser._padLastQuad(n2, n4);
                        n3 = n + 1;
                    }
                    object2 = this._symbols.findName(arrn, n3);
                    object = object2;
                    if (object2 != null) return object;
                    return this.addName(arrn, n3, n4);
                }
                if (n5 != 92) {
                    this._throwUnquotedSpace(n5, "name");
                } else {
                    n5 = this._decodeEscaped();
                }
                arrn = object;
                n6 = n;
                n7 = n2;
                n3 = n5;
                n8 = n4;
                if (n5 > 127) {
                    n7 = 0;
                    arrn = object;
                    n3 = n;
                    n8 = n2;
                    n6 = n4;
                    if (n4 >= 4) {
                        arrn = object;
                        if (n >= ((int[])object).length) {
                            arrn = UTF8StreamJsonParser.growArrayBy(object, ((int[])object).length);
                            this._quadBuffer = arrn;
                        }
                        arrn[n] = n2;
                        n3 = n + 1;
                        n8 = 0;
                        n6 = 0;
                    }
                    if (n5 < 2048) {
                        n = n8 << 8 | (n5 >> 6 | 192);
                        n2 = n6 + 1;
                        n6 = n3;
                    } else {
                        n2 = n8 << 8 | (n5 >> 12 | 224);
                        n = n6 + 1;
                        if (n >= 4) {
                            object = arrn;
                            if (n3 >= arrn.length) {
                                object = UTF8StreamJsonParser.growArrayBy(arrn, arrn.length);
                                this._quadBuffer = object;
                            }
                            object[n3] = n2;
                            ++n3;
                            n = 0;
                            n2 = n7;
                        } else {
                            object = arrn;
                        }
                        n4 = n2 << 8 | (n5 >> 6 & 63 | 128);
                        n2 = n + 1;
                        n = n4;
                        n6 = n3;
                        arrn = object;
                    }
                    n3 = n5 & 63 | 128;
                    n8 = n2;
                    n7 = n;
                }
            }
            if (n8 < 4) {
                n4 = n8 + 1;
                n2 = n7 << 8 | n3;
                object = arrn;
                n = n6;
            } else {
                object = arrn;
                if (n6 >= arrn.length) {
                    object = UTF8StreamJsonParser.growArrayBy(arrn, arrn.length);
                    this._quadBuffer = object;
                }
                object[n6] = n7;
                n2 = n3;
                n = n6 + 1;
                n4 = 1;
            }
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            arrn = this._inputBuffer;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            n5 = arrn[n3] & 255;
        } while (true);
    }

    protected final String parseLongName(int n, int n2, int n3) throws IOException {
        int[] arrn = this._quadBuffer;
        arrn[0] = this._quad1;
        arrn[1] = n2;
        arrn[2] = n3;
        byte[] arrby = this._inputBuffer;
        arrn = _icLatin1;
        n3 = 3;
        n2 = n;
        n = n3;
        while (this._inputPtr + 4 <= this._inputEnd) {
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if (arrn[n3 = arrby[n3] & 255] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 1);
                return this.findName(this._quadBuffer, n, n2, 1);
            }
            n2 = n2 << 8 | n3;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if (arrn[n3 = arrby[n3] & 255] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 2);
                return this.findName(this._quadBuffer, n, n2, 2);
            }
            n2 = n2 << 8 | n3;
            n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            if (arrn[n3 = arrby[n3] & 255] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 3);
                return this.findName(this._quadBuffer, n, n2, 3);
            }
            n3 = n2 << 8 | n3;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if (arrn[n2 = arrby[n2] & 255] != 0) {
                if (n2 != 34) return this.parseEscapedName(this._quadBuffer, n, n3, n2, 4);
                return this.findName(this._quadBuffer, n, n3, 4);
            }
            int[] arrn2 = this._quadBuffer;
            if (n >= arrn2.length) {
                this._quadBuffer = UTF8StreamJsonParser.growArrayBy(arrn2, n);
            }
            this._quadBuffer[n] = n3;
            ++n;
        }
        return this.parseEscapedName(this._quadBuffer, n, 0, n2, 0);
    }

    protected final String parseMediumName(int n) throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(this._quad1, n, n2, 1);
            return this.findName(this._quad1, n, 1);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(this._quad1, n, n2, 2);
            return this.findName(this._quad1, n, 2);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return this.parseName(this._quad1, n, n2, 3);
            return this.findName(this._quad1, n, 3);
        }
        n = n << 8 | n2;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] == 0) return this.parseMediumName2(n2, n);
        if (n2 != 34) return this.parseName(this._quad1, n, n2, 4);
        return this.findName(this._quad1, n, 4);
    }

    protected final String parseMediumName2(int n, int n2) throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 1);
            return this.findName(this._quad1, n2, n, 1);
        }
        n = n << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 2);
            return this.findName(this._quad1, n2, n, 2);
        }
        n = n << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 3);
            return this.findName(this._quad1, n2, n, 3);
        }
        n = n << 8 | n3;
        n3 = this._inputPtr;
        this._inputPtr = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] == 0) return this.parseLongName(n3, n2, n);
        if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 4);
        return this.findName(this._quad1, n2, n, 4);
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
    public int releaseBuffered(OutputStream outputStream2) throws IOException {
        int n = this._inputEnd - this._inputPtr;
        if (n < 1) {
            return 0;
        }
        int n2 = this._inputPtr;
        outputStream2.write(this._inputBuffer, n2, n);
        return n;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    protected String slowParseName() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
        }
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrby[n] & 255) != 34) return this.parseEscapedName(this._quadBuffer, 0, 0, n, 0);
        return "";
    }
}

