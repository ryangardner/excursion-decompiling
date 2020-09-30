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
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class UTF8DataInputJsonParser
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
    protected DataInput _inputData;
    protected int _nextByte = -1;
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

    public UTF8DataInputJsonParser(IOContext iOContext, int n, DataInput dataInput, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, int n2) {
        super(iOContext, n);
        this._objectCodec = objectCodec;
        this._symbols = byteQuadsCanonicalizer;
        this._inputData = dataInput;
        this._nextByte = n2;
    }

    private final void _checkMatchEnd(String string2, int n, int n2) throws IOException {
        char c = (char)this._decodeCharForError(n2);
        if (!Character.isJavaIdentifierPart(c)) return;
        this._reportInvalidToken(c, string2.substring(0, n));
    }

    private void _closeScope(int n) throws JsonParseException {
        if (n == 93) {
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(n, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (n != 125) return;
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(n, ']');
        }
        this._parsingContext = this._parsingContext.clearAndGetParent();
        this._currToken = JsonToken.END_OBJECT;
    }

    private final int _decodeUtf8_2(int n) throws IOException {
        int n2 = this._inputData.readUnsignedByte();
        if ((n2 & 192) == 128) return (n & 31) << 6 | n2 & 63;
        this._reportInvalidOther(n2 & 255);
        return (n & 31) << 6 | n2 & 63;
    }

    private final int _decodeUtf8_3(int n) throws IOException {
        int n2;
        int n3 = this._inputData.readUnsignedByte();
        if ((n3 & 192) != 128) {
            this._reportInvalidOther(n3 & 255);
        }
        if (((n2 = this._inputData.readUnsignedByte()) & 192) == 128) return ((n & 15) << 6 | n3 & 63) << 6 | n2 & 63;
        this._reportInvalidOther(n2 & 255);
        return ((n & 15) << 6 | n3 & 63) << 6 | n2 & 63;
    }

    private final int _decodeUtf8_4(int n) throws IOException {
        int n2;
        int n3;
        int n4 = this._inputData.readUnsignedByte();
        if ((n4 & 192) != 128) {
            this._reportInvalidOther(n4 & 255);
        }
        if (((n3 = this._inputData.readUnsignedByte()) & 192) != 128) {
            this._reportInvalidOther(n3 & 255);
        }
        if (((n2 = this._inputData.readUnsignedByte()) & 192) == 128) return ((((n & 7) << 6 | n4 & 63) << 6 | n3 & 63) << 6 | n2 & 63) - 65536;
        this._reportInvalidOther(n2 & 255);
        return ((((n & 7) << 6 | n4 & 63) << 6 | n3 & 63) << 6 | n2 & 63) - 65536;
    }

    private String _finishAndReturnString() throws IOException {
        int n;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n2 = arrc.length;
        int n3 = 0;
        do {
            if (arrn[n = this._inputData.readUnsignedByte()] != 0) {
                if (n != 34) break;
                return this._textBuffer.setCurrentAndReturn(n3);
            }
            int n4 = n3 + 1;
            arrc[n3] = (char)n;
            if (n4 >= n2) {
                this._finishString2(arrc, n4, this._inputData.readUnsignedByte());
                return this._textBuffer.contentsAsString();
            }
            n3 = n4;
        } while (true);
        this._finishString2(arrc, n3, n);
        return this._textBuffer.contentsAsString();
    }

    private final void _finishString2(char[] arrc, int n, int n2) throws IOException {
        int[] arrn = _icUTF8;
        int n3 = arrc.length;
        int n4 = n2;
        n2 = n3;
        do {
            int n5 = arrn[n4];
            n3 = 0;
            if (n5 == 0) {
                n3 = n2;
                n5 = n;
                if (n >= n2) {
                    arrc = this._textBuffer.finishCurrentSegment();
                    n3 = arrc.length;
                    n5 = 0;
                }
                arrc[n5] = (char)n4;
                n4 = this._inputData.readUnsignedByte();
                n = n5 + 1;
                n2 = n3;
                continue;
            }
            if (n4 == 34) {
                this._textBuffer.setCurrentLength(n);
                return;
            }
            n5 = arrn[n4];
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            if (n4 < 32) {
                                this._throwUnquotedSpace(n4, "string value");
                            } else {
                                this._reportInvalidChar(n4);
                            }
                        } else {
                            n5 = this._decodeUtf8_4(n4);
                            char[] arrc2 = arrc;
                            n4 = n;
                            if (n >= arrc.length) {
                                arrc2 = this._textBuffer.finishCurrentSegment();
                                n2 = arrc2.length;
                                n4 = 0;
                            }
                            arrc2[n4] = (char)(55296 | n5 >> 10);
                            n5 = n5 & 1023 | 56320;
                            n = n4 + 1;
                            arrc = arrc2;
                            n4 = n5;
                        }
                    } else {
                        n4 = this._decodeUtf8_3(n4);
                    }
                } else {
                    n4 = this._decodeUtf8_2(n4);
                }
            } else {
                n4 = this._decodeEscaped();
            }
            if (n >= arrc.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n2 = arrc.length;
                n = n3;
            }
            n3 = n + 1;
            arrc[n] = (char)n4;
            n4 = this._inputData.readUnsignedByte();
            n = n3;
        } while (true);
    }

    private static int[] _growArrayBy(int[] arrn, int n) {
        if (arrn != null) return Arrays.copyOf(arrn, arrn.length + n);
        return new int[n];
    }

    private final int _handleLeadingZeroes() throws IOException {
        int n;
        int n2 = n = this._inputData.readUnsignedByte();
        if (n < 48) return n2;
        if (n > 57) {
            return n;
        }
        int n3 = n;
        if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
            this.reportInvalidNumber("Leading zeroes not allowed");
            n3 = n;
        }
        do {
            n2 = n3;
            if (n3 != 48) return n2;
            n3 = this._inputData.readUnsignedByte();
        } while (true);
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

    private final JsonToken _parseFloat(char[] arrc, int n, int n2, boolean bl, int n3) throws IOException {
        int n4;
        int n5;
        int n6;
        int n7;
        block18 : {
            char[] arrc2;
            block17 : {
                n6 = 0;
                if (n2 != 46) {
                    n7 = 0;
                    arrc2 = arrc;
                } else {
                    arrc[n] = (char)n2;
                    ++n;
                    n2 = 0;
                    while ((n5 = this._inputData.readUnsignedByte()) >= 48 && n5 <= 57) {
                        n7 = n2 + 1;
                        arrc2 = arrc;
                        n2 = n;
                        if (n >= arrc.length) {
                            arrc2 = this._textBuffer.finishCurrentSegment();
                            n2 = 0;
                        }
                        arrc2[n2] = (char)n5;
                        n = n2 + 1;
                        arrc = arrc2;
                        n2 = n7;
                    }
                    if (n2 == 0) {
                        this.reportUnexpectedNumberChar(n5, "Decimal point not followed by a digit");
                    }
                    n7 = n2;
                    n2 = n5;
                    arrc2 = arrc;
                }
                if (n2 == 101) break block17;
                n4 = n;
                n5 = n2;
                if (n2 != 69) break block18;
            }
            arrc = arrc2;
            n5 = n;
            if (n >= arrc2.length) {
                arrc = this._textBuffer.finishCurrentSegment();
                n5 = 0;
            }
            n = n5 + 1;
            arrc[n5] = (char)n2;
            n5 = this._inputData.readUnsignedByte();
            if (n5 != 45 && n5 != 43) {
                n2 = n5;
                n5 = 0;
            } else {
                n2 = n;
                arrc2 = arrc;
                if (n >= arrc.length) {
                    arrc2 = this._textBuffer.finishCurrentSegment();
                    n2 = 0;
                }
                arrc2[n2] = (char)n5;
                n = this._inputData.readUnsignedByte();
                n5 = 0;
                n4 = n2 + 1;
                n2 = n;
                n = n4;
                arrc = arrc2;
            }
            while (n2 <= 57 && n2 >= 48) {
                n4 = n5 + 1;
                arrc2 = arrc;
                n5 = n;
                if (n >= arrc.length) {
                    arrc2 = this._textBuffer.finishCurrentSegment();
                    n5 = 0;
                }
                arrc2[n5] = (char)n2;
                n2 = this._inputData.readUnsignedByte();
                n = n5 + 1;
                n5 = n4;
                arrc = arrc2;
            }
            if (n5 == 0) {
                this.reportUnexpectedNumberChar(n2, "Exponent indicator not followed by a digit");
            }
            n6 = n5;
            n5 = n2;
            n4 = n;
        }
        this._nextByte = n5;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
        }
        this._textBuffer.setCurrentLength(n4);
        return this.resetFloat(bl, n3, n7, n6);
    }

    private final String _parseLongName(int n, int n2, int n3) throws IOException {
        int[] arrn = this._quadBuffer;
        arrn[0] = this._quad1;
        arrn[1] = n2;
        arrn[2] = n3;
        arrn = _icLatin1;
        n2 = n;
        n = 3;
        do {
            if (arrn[n3 = this._inputData.readUnsignedByte()] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 1);
                return this.findName(this._quadBuffer, n, n2, 1);
            }
            n2 = n2 << 8 | n3;
            n3 = this._inputData.readUnsignedByte();
            if (arrn[n3] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 2);
                return this.findName(this._quadBuffer, n, n2, 2);
            }
            n2 = n2 << 8 | n3;
            n3 = this._inputData.readUnsignedByte();
            if (arrn[n3] != 0) {
                if (n3 != 34) return this.parseEscapedName(this._quadBuffer, n, n2, n3, 3);
                return this.findName(this._quadBuffer, n, n2, 3);
            }
            n3 = n2 << 8 | n3;
            n2 = this._inputData.readUnsignedByte();
            if (arrn[n2] != 0) {
                if (n2 != 34) return this.parseEscapedName(this._quadBuffer, n, n3, n2, 4);
                return this.findName(this._quadBuffer, n, n3, 4);
            }
            int[] arrn2 = this._quadBuffer;
            if (n >= arrn2.length) {
                this._quadBuffer = UTF8DataInputJsonParser._growArrayBy(arrn2, n);
            }
            this._quadBuffer[n] = n3;
            ++n;
        } while (true);
    }

    private final String _parseMediumName(int n) throws IOException {
        int[] arrn = _icLatin1;
        int n2 = this._inputData.readUnsignedByte();
        if (arrn[n2] != 0) {
            if (n2 != 34) return this.parseName(this._quad1, n, n2, 1);
            return this.findName(this._quad1, n, 1);
        }
        n2 = n << 8 | n2;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(this._quad1, n2, n, 2);
            return this.findName(this._quad1, n2, 2);
        }
        n2 = n2 << 8 | n;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(this._quad1, n2, n, 3);
            return this.findName(this._quad1, n2, 3);
        }
        n = n2 << 8 | n;
        n2 = this._inputData.readUnsignedByte();
        if (arrn[n2] == 0) return this._parseMediumName2(n2, n);
        if (n2 != 34) return this.parseName(this._quad1, n, n2, 4);
        return this.findName(this._quad1, n, 4);
    }

    private final String _parseMediumName2(int n, int n2) throws IOException {
        int[] arrn = _icLatin1;
        int n3 = this._inputData.readUnsignedByte();
        if (arrn[n3] != 0) {
            if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 1);
            return this.findName(this._quad1, n2, n, 1);
        }
        n3 = n << 8 | n3;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(this._quad1, n2, n3, n, 2);
            return this.findName(this._quad1, n2, n3, 2);
        }
        n = n3 << 8 | n;
        n3 = this._inputData.readUnsignedByte();
        if (arrn[n3] != 0) {
            if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 3);
            return this.findName(this._quad1, n2, n, 3);
        }
        n = n << 8 | n3;
        n3 = this._inputData.readUnsignedByte();
        if (arrn[n3] == 0) return this._parseLongName(n3, n2, n);
        if (n3 != 34) return this.parseName(this._quad1, n2, n, n3, 4);
        return this.findName(this._quad1, n2, n, 4);
    }

    private void _reportInvalidOther(int n) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid UTF-8 middle byte 0x");
        stringBuilder.append(Integer.toHexString(n));
        this._reportError(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     */
    private final void _skipCComment() throws IOException {
        var1_1 = CharTypes.getInputCodeComment();
        var2_2 = this._inputData.readUnsignedByte();
        do {
            block3 : {
                block4 : {
                    block5 : {
                        block6 : {
                            if ((var3_3 = var1_1[var2_2]) == 0) break block3;
                            if (var3_3 == 2) break block4;
                            if (var3_3 == 3) break block5;
                            if (var3_3 == 4) break block6;
                            if (var3_3 == 10 || var3_3 == 13) ** GOTO lbl15
                            if (var3_3 != 42) {
                                this._reportInvalidChar(var2_2);
                            } else {
                                var2_2 = var3_3 = this._inputData.readUnsignedByte();
                                if (var3_3 != 47) continue;
                                return;
lbl15: // 1 sources:
                                ++this._currInputRow;
                            }
                            break block3;
                        }
                        this._skipUtf8_4();
                        break block3;
                    }
                    this._skipUtf8_3();
                    break block3;
                }
                this._skipUtf8_2();
            }
            var2_2 = this._inputData.readUnsignedByte();
        } while (true);
    }

    private final int _skipColon() throws IOException {
        int n;
        int n2;
        block11 : {
            block10 : {
                n = this._nextByte;
                if (n < 0) {
                    n = this._inputData.readUnsignedByte();
                } else {
                    this._nextByte = -1;
                }
                if (n == 58) {
                    int n3 = this._inputData.readUnsignedByte();
                    if (n3 > 32) {
                        if (n3 == 47) return this._skipColon2(n3, true);
                        if (n3 != 35) return n3;
                        return this._skipColon2(n3, true);
                    }
                    if (n3 != 32) {
                        n = n3;
                        if (n3 != 9) return this._skipColon2(n, true);
                    }
                    n = n3 = this._inputData.readUnsignedByte();
                    if (n3 <= 32) return this._skipColon2(n, true);
                    if (n3 == 47) return this._skipColon2(n3, true);
                    if (n3 != 35) return n3;
                    return this._skipColon2(n3, true);
                }
                if (n == 32) break block10;
                n2 = n;
                if (n != 9) break block11;
            }
            n2 = this._inputData.readUnsignedByte();
        }
        if (n2 != 58) return this._skipColon2(n2, false);
        n2 = this._inputData.readUnsignedByte();
        if (n2 > 32) {
            if (n2 == 47) return this._skipColon2(n2, true);
            if (n2 != 35) return n2;
            return this._skipColon2(n2, true);
        }
        if (n2 != 32) {
            n = n2;
            if (n2 != 9) return this._skipColon2(n, true);
        }
        n = n2 = this._inputData.readUnsignedByte();
        if (n2 <= 32) return this._skipColon2(n, true);
        if (n2 == 47) return this._skipColon2(n2, true);
        if (n2 != 35) return n2;
        return this._skipColon2(n2, true);
    }

    private final int _skipColon2(int n, boolean bl) throws IOException {
        do {
            boolean bl2;
            block11 : {
                block12 : {
                    block10 : {
                        if (n <= 32) break block10;
                        if (n == 47) {
                            this._skipComment();
                            bl2 = bl;
                        } else if (n == 35 && this._skipYAMLComment()) {
                            bl2 = bl;
                        } else {
                            if (bl) {
                                return n;
                            }
                            if (n != 58) {
                                this._reportUnexpectedChar(n, "was expecting a colon to separate field name and value");
                            }
                            bl2 = true;
                        }
                        break block11;
                    }
                    if (n == 13) break block12;
                    bl2 = bl;
                    if (n != 10) break block11;
                }
                ++this._currInputRow;
                bl2 = bl;
            }
            n = this._inputData.readUnsignedByte();
            bl = bl2;
        } while (true);
    }

    private final void _skipComment() throws IOException {
        int n;
        if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if ((n = this._inputData.readUnsignedByte()) == 47) {
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
            int n;
            int n2;
            if ((n2 = arrn[n = this._inputData.readUnsignedByte()]) == 0) {
                continue;
            }
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        if (n2 == 10 || n2 == 13) break;
                        if (n2 == 42 || n2 >= 0) continue;
                        this._reportInvalidChar(n);
                        continue;
                    }
                    this._skipUtf8_4();
                    continue;
                }
                this._skipUtf8_3();
                continue;
            }
            this._skipUtf8_2();
        } while (true);
        ++this._currInputRow;
    }

    private final void _skipUtf8_2() throws IOException {
        int n = this._inputData.readUnsignedByte();
        if ((n & 192) == 128) return;
        this._reportInvalidOther(n & 255);
    }

    private final void _skipUtf8_3() throws IOException {
        int n = this._inputData.readUnsignedByte();
        if ((n & 192) != 128) {
            this._reportInvalidOther(n & 255);
        }
        if (((n = this._inputData.readUnsignedByte()) & 192) == 128) return;
        this._reportInvalidOther(n & 255);
    }

    private final void _skipUtf8_4() throws IOException {
        int n = this._inputData.readUnsignedByte();
        if ((n & 192) != 128) {
            this._reportInvalidOther(n & 255);
        }
        if (((n = this._inputData.readUnsignedByte()) & 192) != 128) {
            this._reportInvalidOther(n & 255);
        }
        if (((n = this._inputData.readUnsignedByte()) & 192) == 128) return;
        this._reportInvalidOther(n & 255);
    }

    private final int _skipWS() throws IOException {
        int n = this._nextByte;
        if (n < 0) {
            n = this._inputData.readUnsignedByte();
        } else {
            this._nextByte = -1;
        }
        do {
            if (n > 32) {
                if (n == 47) return this._skipWSComment(n);
                if (n != 35) return n;
                return this._skipWSComment(n);
            }
            if (n == 13 || n == 10) {
                ++this._currInputRow;
            }
            n = this._inputData.readUnsignedByte();
        } while (true);
    }

    private final int _skipWSComment(int n) throws IOException {
        do {
            if (n > 32) {
                if (n == 47) {
                    this._skipComment();
                } else {
                    if (n != 35) return n;
                    if (!this._skipYAMLComment()) {
                        return n;
                    }
                }
            } else if (n == 13 || n == 10) {
                ++this._currInputRow;
            }
            n = this._inputData.readUnsignedByte();
        } while (true);
    }

    private final int _skipWSOrEnd() throws IOException {
        int n = this._nextByte;
        if (n < 0) {
            try {
                n = this._inputData.readUnsignedByte();
            }
            catch (EOFException eOFException) {
                return this._eofAsNextChar();
            }
        } else {
            this._nextByte = -1;
        }
        do {
            if (n > 32) {
                if (n == 47) return this._skipWSComment(n);
                if (n != 35) return n;
                return this._skipWSComment(n);
            }
            if (n == 13 || n == 10) {
                ++this._currInputRow;
            }
            try {
                n = this._inputData.readUnsignedByte();
            }
            catch (EOFException eOFException) {
                return this._eofAsNextChar();
            }
        } while (true);
    }

    private final boolean _skipYAMLComment() throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
            return false;
        }
        this._skipLine();
        return true;
    }

    private final void _verifyRootSpace() throws IOException {
        int n = this._nextByte;
        if (n > 32) {
            this._reportMissingRootWS(n);
            return;
        }
        this._nextByte = -1;
        if (n != 13) {
            if (n != 10) return;
        }
        ++this._currInputRow;
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
        int[] arrn = this._symbols.findName(n = UTF8DataInputJsonParser.pad(n, n2));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        return this.addName(arrn, 1, n2);
    }

    private final String findName(int n, int n2, int n3) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2 = UTF8DataInputJsonParser.pad(n2, n3));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        return this.addName(arrn, 2, n3);
    }

    private final String findName(int n, int n2, int n3, int n4) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2, n3 = UTF8DataInputJsonParser.pad(n3, n4));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        arrn[2] = UTF8DataInputJsonParser.pad(n3, n4);
        return this.addName(arrn, 3, n4);
    }

    private final String findName(int[] object, int n, int n2, int n3) throws JsonParseException {
        int[] arrn = object;
        if (n >= ((int[])object).length) {
            arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
            this._quadBuffer = arrn;
        }
        int n4 = n + 1;
        arrn[n] = UTF8DataInputJsonParser.pad(n2, n3);
        object = this._symbols.findName(arrn, n4);
        if (object != null) return object;
        return this.addName(arrn, n4, n3);
    }

    private static final int pad(int n, int n2) {
        if (n2 == 4) {
            return n;
        }
        n |= -1 << (n2 << 3);
        return n;
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
    }

    protected final byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        Object object = this._getByteArrayBuilder();
        do {
            int n;
            int n2;
            if ((n2 = this._inputData.readUnsignedByte()) <= 32) {
                continue;
            }
            int n3 = n = base64Variant.decodeBase64Char(n2);
            if (n < 0) {
                if (n2 == 34) {
                    return ((ByteArrayBuilder)object).toByteArray();
                }
                n3 = n = this._decodeBase64Escape(base64Variant, n2, 0);
                if (n < 0) continue;
            }
            int n4 = this._inputData.readUnsignedByte();
            n = n2 = base64Variant.decodeBase64Char(n4);
            if (n2 < 0) {
                n = this._decodeBase64Escape(base64Variant, n4, 1);
            }
            n4 = n3 << 6 | n;
            int n5 = this._inputData.readUnsignedByte();
            n = n2 = base64Variant.decodeBase64Char(n5);
            if (n2 < 0) {
                n3 = n2;
                if (n2 != -2) {
                    if (n5 == 34) {
                        ((ByteArrayBuilder)object).append(n4 >> 4);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n5, 2);
                }
                n = n3;
                if (n3 == -2) {
                    n3 = this._inputData.readUnsignedByte();
                    if (!(base64Variant.usesPaddingChar(n3) || n3 == 92 && this._decodeBase64Escape(base64Variant, n3, 3) == -2)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("expected padding character '");
                        ((StringBuilder)object).append(base64Variant.getPaddingChar());
                        ((StringBuilder)object).append("'");
                        throw this.reportInvalidBase64Char(base64Variant, n3, 3, ((StringBuilder)object).toString());
                    }
                    ((ByteArrayBuilder)object).append(n4 >> 4);
                    continue;
                }
            }
            n4 = n4 << 6 | n;
            n5 = this._inputData.readUnsignedByte();
            n2 = n = base64Variant.decodeBase64Char(n5);
            if (n < 0) {
                n3 = n;
                if (n != -2) {
                    if (n5 == 34) {
                        ((ByteArrayBuilder)object).appendTwoBytes(n4 >> 2);
                        if (!base64Variant.usesPadding()) return ((ByteArrayBuilder)object).toByteArray();
                        this._handleBase64MissingPadding(base64Variant);
                        return ((ByteArrayBuilder)object).toByteArray();
                    }
                    n3 = this._decodeBase64Escape(base64Variant, n5, 3);
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
        var3_3 = this._inputData.readUnsignedByte();
        if ((var3_3 & 192) != 128) {
            this._reportInvalidOther(var3_3 & 255);
        }
        var1_1 = var3_3 = var1_1 << 6 | var3_3 & 63;
        if (var2_2 <= 1) return var1_1;
        var1_1 = this._inputData.readUnsignedByte();
        if ((var1_1 & 192) != 128) {
            this._reportInvalidOther(var1_1 & 255);
        }
        var1_1 = var3_3 = var3_3 << 6 | var1_1 & 63;
        if (var2_2 <= 2) return var1_1;
        var1_1 = this._inputData.readUnsignedByte();
        if ((var1_1 & 192) == 128) return var3_3 << 6 | var1_1 & 63;
        this._reportInvalidOther(var1_1 & 255);
        return var3_3 << 6 | var1_1 & 63;
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        int n = this._inputData.readUnsignedByte();
        if (n == 34) return (char)n;
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
            int n3 = this._inputData.readUnsignedByte();
            int n4 = CharTypes.charToHex(n3);
            if (n4 < 0) {
                this._reportUnexpectedChar(n3, "expected a hex-digit for character escape sequence");
            }
            n2 = n2 << 4 | n4;
            ++n;
        }
        return (char)n2;
    }

    @Override
    protected void _finishString() throws IOException {
        int n;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n2 = arrc.length;
        int n3 = 0;
        do {
            if (arrn[n = this._inputData.readUnsignedByte()] != 0) {
                if (n != 34) break;
                this._textBuffer.setCurrentLength(n3);
                return;
            }
            int n4 = n3 + 1;
            arrc[n3] = (char)n;
            if (n4 >= n2) {
                this._finishString2(arrc, n4, this._inputData.readUnsignedByte());
                return;
            }
            n3 = n4;
        } while (true);
        this._finishString2(arrc, n3, n);
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
        int n = 0;
        block0 : do {
            int n2;
            int n3 = arrc.length;
            char[] arrc2 = arrc;
            int n4 = n;
            if (n >= arrc.length) {
                arrc2 = this._textBuffer.finishCurrentSegment();
                n3 = arrc2.length;
                n4 = 0;
            }
            do {
                if ((n = this._inputData.readUnsignedByte()) == 39) {
                    this._textBuffer.setCurrentLength(n4);
                    return JsonToken.VALUE_STRING;
                }
                if (arrn[n] != 0) {
                    n3 = arrn[n];
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                if (n3 != 4) {
                                    if (n < 32) {
                                        this._throwUnquotedSpace(n, "string value");
                                    }
                                    this._reportInvalidChar(n);
                                } else {
                                    n3 = this._decodeUtf8_4(n);
                                    n = n4 + 1;
                                    arrc2[n4] = (char)(55296 | n3 >> 10);
                                    if (n >= arrc2.length) {
                                        arrc2 = this._textBuffer.finishCurrentSegment();
                                        n4 = 0;
                                    } else {
                                        n4 = n;
                                    }
                                    n = 56320 | n3 & 1023;
                                }
                            } else {
                                n = this._decodeUtf8_3(n);
                            }
                        } else {
                            n = this._decodeUtf8_2(n);
                        }
                    } else {
                        n = this._decodeEscaped();
                    }
                    arrc = arrc2;
                    n3 = n4;
                    if (n4 >= arrc2.length) {
                        arrc = this._textBuffer.finishCurrentSegment();
                        n3 = 0;
                    }
                    arrc[n3] = (char)n;
                    n = n3 + 1;
                    continue block0;
                }
                n2 = n4 + 1;
                arrc2[n4] = (char)n;
                n4 = n = n2;
            } while (n2 < n3);
            arrc = arrc2;
        } while (true);
    }

    protected JsonToken _handleInvalidNumberStart(int n, boolean bl) throws IOException {
        int n2;
        do {
            String string2;
            n2 = n;
            if (n != 73) break;
            n = this._inputData.readUnsignedByte();
            if (n == 78) {
                string2 = bl ? "-INF" : "+INF";
            } else {
                n2 = n;
                if (n != 110) break;
                string2 = bl ? "-Infinity" : "+Infinity";
            }
            this._matchToken(string2, 3);
            if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                double d;
                if (bl) {
                    d = Double.NEGATIVE_INFINITY;
                    return this.resetAsNaN(string2, d);
                }
                d = Double.POSITIVE_INFINITY;
                return this.resetAsNaN(string2, d);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Non-standard token '");
            stringBuilder.append(string2);
            stringBuilder.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            this._reportError(stringBuilder.toString());
        } while (true);
        this.reportUnexpectedNumberChar(n2, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }

    protected String _handleOddName(int n) throws IOException {
        int n2;
        int n3;
        Object object;
        Object object2;
        int n4;
        int n5;
        if (n == 39 && (this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._parseAposName();
        }
        if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar((char)this._decodeCharForError(n), "was expecting double-quote to start field name");
        }
        if ((object2 = CharTypes.getInputCodeUtf8JsNames())[n] != 0) {
            this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int[] arrn = this._quadBuffer;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        do {
            if (n6 < 4) {
                n3 = n6 + 1;
                n5 = n | n8 << 8;
                object = arrn;
                n2 = n7;
            } else {
                object = arrn;
                if (n7 >= arrn.length) {
                    this._quadBuffer = object = UTF8DataInputJsonParser._growArrayBy(arrn, arrn.length);
                }
                object[n7] = n8;
                n2 = n7 + 1;
                n3 = 1;
                n5 = n;
            }
            n4 = this._inputData.readUnsignedByte();
            arrn = object;
            n6 = n3;
            n7 = n2;
            n8 = n5;
            n = n4;
        } while (object2[n4] == 0);
        this._nextByte = n4;
        arrn = object;
        n = n2;
        if (n3 > 0) {
            arrn = object;
            if (n2 >= ((int[])object).length) {
                this._quadBuffer = arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
            }
            arrn[n2] = n5;
            n = n2 + 1;
        }
        object2 = this._symbols.findName(arrn, n);
        object = object2;
        if (object2 != null) return object;
        return this.addName(arrn, n, n3);
    }

    protected JsonToken _handleUnexpectedValue(int n) throws IOException {
        StringBuilder stringBuilder;
        block13 : {
            block7 : {
                block8 : {
                    block9 : {
                        block11 : {
                            block12 : {
                                block10 : {
                                    if (n == 39) break block7;
                                    if (n == 73) break block8;
                                    if (n == 78) break block9;
                                    if (n == 93) break block10;
                                    if (n == 125) break block11;
                                    if (n == 43) return this._handleInvalidNumberStart(this._inputData.readUnsignedByte(), false);
                                    if (n == 44) break block12;
                                    break block13;
                                }
                                if (!this._parsingContext.inArray()) break block13;
                            }
                            if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                                this._nextByte = n;
                                return JsonToken.VALUE_NULL;
                            }
                        }
                        this._reportUnexpectedChar(n, "expected a value");
                        break block7;
                    }
                    this._matchToken("NaN", 1);
                    if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                        return this.resetAsNaN("NaN", Double.NaN);
                    }
                    this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                    break block13;
                }
                this._matchToken("Infinity", 1);
                if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break block13;
            }
            if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
                return this._handleApos();
            }
        }
        if (Character.isJavaIdentifierStart(n)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append((char)n);
            this._reportInvalidToken(n, stringBuilder.toString(), this._validJsonTokenList());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("expected a valid value ");
        stringBuilder.append(this._validJsonValueList());
        this._reportUnexpectedChar(n, stringBuilder.toString());
        return null;
    }

    protected final void _matchToken(String string2, int n) throws IOException {
        int n2;
        int n3 = string2.length();
        do {
            if ((n2 = this._inputData.readUnsignedByte()) != string2.charAt(n)) {
                this._reportInvalidToken(n2, string2.substring(0, n));
            }
            n = n2 = n + 1;
        } while (n2 < n3);
        n = this._inputData.readUnsignedByte();
        if (n >= 48 && n != 93 && n != 125) {
            this._checkMatchEnd(string2, n2, n);
        }
        this._nextByte = n;
    }

    protected String _parseAposName() throws IOException {
        int n = this._inputData.readUnsignedByte();
        if (n == 39) {
            return "";
        }
        Object object = this._quadBuffer;
        Object object2 = _icLatin1;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        do {
            int[] arrn;
            if (n == 39) {
                arrn = object;
                n = n3;
                if (n2 > 0) {
                    arrn = object;
                    if (n3 >= ((int[])object).length) {
                        this._quadBuffer = arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
                    }
                    arrn[n3] = UTF8DataInputJsonParser.pad(n4, n2);
                    n = n3 + 1;
                }
                object2 = this._symbols.findName(arrn, n);
                object = object2;
                if (object2 != null) return object;
                return this.addName(arrn, n, n2);
            }
            int n5 = n;
            arrn = object;
            int n6 = n2;
            int n7 = n3;
            int n8 = n4;
            if (n != 34) {
                n5 = n;
                arrn = object;
                n6 = n2;
                n7 = n3;
                n8 = n4;
                if (object2[n] != 0) {
                    int n9;
                    if (n != 92) {
                        this._throwUnquotedSpace(n, "name");
                        n9 = n;
                    } else {
                        n9 = this._decodeEscaped();
                    }
                    n5 = n9;
                    arrn = object;
                    n6 = n2;
                    n7 = n3;
                    n8 = n4;
                    if (n9 > 127) {
                        arrn = object;
                        n5 = n2;
                        n = n3;
                        n7 = n4;
                        if (n2 >= 4) {
                            arrn = object;
                            if (n3 >= ((int[])object).length) {
                                this._quadBuffer = arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
                            }
                            arrn[n3] = n4;
                            n = n3 + 1;
                            n5 = 0;
                            n7 = 0;
                        }
                        if (n9 < 2048) {
                            n2 = n7 << 8 | (n9 >> 6 | 192);
                            n3 = n5 + 1;
                            object = arrn;
                        } else {
                            n7 = n7 << 8 | (n9 >> 12 | 224);
                            object = arrn;
                            n4 = ++n5;
                            n3 = n;
                            n2 = n7;
                            if (n5 >= 4) {
                                object = arrn;
                                if (n >= arrn.length) {
                                    this._quadBuffer = object = UTF8DataInputJsonParser._growArrayBy(arrn, arrn.length);
                                }
                                object[n] = n7;
                                n3 = n + 1;
                                n4 = 0;
                                n2 = 0;
                            }
                            n2 = n2 << 8 | (n9 >> 6 & 63 | 128);
                            n = n3;
                            n3 = ++n4;
                        }
                        n5 = n9 & 63 | 128;
                        n8 = n2;
                        n7 = n;
                        n6 = n3;
                        arrn = object;
                    }
                }
            }
            if (n6 < 4) {
                n2 = n6 + 1;
                n4 = n5 | n8 << 8;
                object = arrn;
                n3 = n7;
            } else {
                object = arrn;
                if (n7 >= arrn.length) {
                    this._quadBuffer = object = UTF8DataInputJsonParser._growArrayBy(arrn, arrn.length);
                }
                object[n7] = n8;
                n4 = n5;
                n3 = n7 + 1;
                n2 = 1;
            }
            n = this._inputData.readUnsignedByte();
        } while (true);
    }

    protected final String _parseName(int n) throws IOException {
        if (n != 34) {
            return this._handleOddName(n);
        }
        int[] arrn = _icLatin1;
        int n2 = this._inputData.readUnsignedByte();
        if (arrn[n2] != 0) {
            if (n2 != 34) return this.parseName(0, n2, 0);
            return "";
        }
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(n2, n, 1);
            return this.findName(n2, 1);
        }
        n2 = n2 << 8 | n;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(n2, n, 2);
            return this.findName(n2, 2);
        }
        n2 = n2 << 8 | n;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] != 0) {
            if (n != 34) return this.parseName(n2, n, 3);
            return this.findName(n2, 3);
        }
        n2 = n2 << 8 | n;
        n = this._inputData.readUnsignedByte();
        if (arrn[n] == 0) {
            this._quad1 = n2;
            return this._parseMediumName(n);
        }
        if (n != 34) return this.parseName(n2, n, 4);
        return this.findName(n2, 4);
    }

    protected JsonToken _parseNegNumber() throws IOException {
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        arrc[0] = (char)45;
        int n = this._inputData.readUnsignedByte();
        arrc[1] = (char)n;
        if (n <= 48) {
            if (n != 48) return this._handleInvalidNumberStart(n, true);
            n = this._handleLeadingZeroes();
        } else {
            if (n > 57) {
                return this._handleInvalidNumberStart(n, true);
            }
            n = this._inputData.readUnsignedByte();
        }
        int n2 = 2;
        int n3 = 1;
        while (n <= 57 && n >= 48) {
            ++n3;
            arrc[n2] = (char)n;
            n = this._inputData.readUnsignedByte();
            ++n2;
        }
        if (n == 46) return this._parseFloat(arrc, n2, n, true, n3);
        if (n == 101) return this._parseFloat(arrc, n2, n, true, n3);
        if (n == 69) {
            return this._parseFloat(arrc, n2, n, true, n3);
        }
        this._textBuffer.setCurrentLength(n2);
        this._nextByte = n;
        if (!this._parsingContext.inRoot()) return this.resetInt(true, n3);
        this._verifyRootSpace();
        return this.resetInt(true, n3);
    }

    protected JsonToken _parsePosNumber(int n) throws IOException {
        int n2;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int n3 = 1;
        if (n == 48) {
            n2 = this._handleLeadingZeroes();
            if (n2 <= 57 && n2 >= 48) {
                n = 0;
            } else {
                arrc[0] = (char)48;
                n = n3;
            }
        } else {
            arrc[0] = (char)n;
            n2 = this._inputData.readUnsignedByte();
            n = n3;
        }
        n3 = n;
        while (n2 <= 57 && n2 >= 48) {
            ++n3;
            arrc[n] = (char)n2;
            n2 = this._inputData.readUnsignedByte();
            ++n;
        }
        if (n2 == 46) return this._parseFloat(arrc, n, n2, false, n3);
        if (n2 == 101) return this._parseFloat(arrc, n, n2, false, n3);
        if (n2 == 69) {
            return this._parseFloat(arrc, n, n2, false, n3);
        }
        this._textBuffer.setCurrentLength(n);
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
            return this.resetInt(false, n3);
        }
        this._nextByte = n2;
        return this.resetInt(false, n3);
    }

    /*
     * Unable to fully structure code
     */
    protected int _readBinary(Base64Variant var1_1, OutputStream var2_2, byte[] var3_3) throws IOException {
        var4_4 = var3_3.length;
        var5_5 = 0;
        var6_6 = 0;
        do {
            if ((var7_7 = this._inputData.readUnsignedByte()) <= 32) {
                continue;
            }
            var9_9 = var8_8 = var1_1.decodeBase64Char(var7_7);
            if (var8_8 < 0) {
                if (var7_7 == 34) break;
                var9_9 = var8_8 = this._decodeBase64Escape(var1_1, var7_7, 0);
                if (var8_8 < 0) continue;
            }
            var7_7 = var5_5;
            var8_8 = var6_6;
            if (var5_5 > var4_4 - 3) {
                var8_8 = var6_6 + var5_5;
                var2_2.write(var3_3, 0, var5_5);
                var7_7 = 0;
            }
            var10_10 = this._inputData.readUnsignedByte();
            var5_5 = var6_6 = var1_1.decodeBase64Char(var10_10);
            if (var6_6 < 0) {
                var5_5 = this._decodeBase64Escape(var1_1, var10_10, 1);
            }
            var10_10 = var9_9 << 6 | var5_5;
            var11_11 = this._inputData.readUnsignedByte();
            var6_6 = var9_9 = var1_1.decodeBase64Char(var11_11);
            if (var9_9 >= 0) ** GOTO lbl-1000
            var5_5 = var9_9;
            if (var9_9 != -2) {
                if (var11_11 == 34) {
                    var3_3[var7_7] = (byte)(var10_10 >> 4);
                    if (var1_1.usesPadding()) {
                        this._handleBase64MissingPadding(var1_1);
                    }
                    var5_5 = var7_7 + 1;
                    var6_6 = var8_8;
                    break;
                }
                var5_5 = this._decodeBase64Escape(var1_1, var11_11, 2);
            }
            var6_6 = var5_5;
            if (var5_5 == -2) {
                var5_5 = this._inputData.readUnsignedByte();
                if (!(var1_1.usesPaddingChar(var5_5) || var5_5 == 92 && this._decodeBase64Escape(var1_1, var5_5, 3) == -2)) {
                    var2_2 = new StringBuilder();
                    var2_2.append("expected padding character '");
                    var2_2.append(var1_1.getPaddingChar());
                    var2_2.append("'");
                    throw this.reportInvalidBase64Char(var1_1, var5_5, 3, var2_2.toString());
                }
                var5_5 = var7_7 + 1;
                var3_3[var7_7] = (byte)(var10_10 >> 4);
            } else lbl-1000: // 2 sources:
            {
                var10_10 = var10_10 << 6 | var6_6;
                var11_11 = this._inputData.readUnsignedByte();
                var6_6 = var9_9 = var1_1.decodeBase64Char(var11_11);
                if (var9_9 < 0) {
                    var5_5 = var9_9;
                    if (var9_9 != -2) {
                        if (var11_11 == 34) {
                            var5_5 = var10_10 >> 2;
                            var6_6 = var7_7 + 1;
                            var3_3[var7_7] = (byte)(var5_5 >> 8);
                            var7_7 = var6_6 + 1;
                            var3_3[var6_6] = (byte)var5_5;
                            var5_5 = var7_7;
                            var6_6 = var8_8;
                            if (!var1_1.usesPadding()) break;
                            this._handleBase64MissingPadding(var1_1);
                            var6_6 = var8_8;
                            var5_5 = var7_7;
                            break;
                        }
                        var5_5 = this._decodeBase64Escape(var1_1, var11_11, 3);
                    }
                    var6_6 = var5_5;
                    if (var5_5 == -2) {
                        var9_9 = var10_10 >> 2;
                        var6_6 = var7_7 + 1;
                        var3_3[var7_7] = (byte)(var9_9 >> 8);
                        var5_5 = var6_6 + 1;
                        var3_3[var6_6] = (byte)var9_9;
                        var6_6 = var8_8;
                        continue;
                    }
                }
                var6_6 = var10_10 << 6 | var6_6;
                var5_5 = var7_7 + 1;
                var3_3[var7_7] = (byte)(var6_6 >> 16);
                var7_7 = var5_5 + 1;
                var3_3[var5_5] = (byte)(var6_6 >> 8);
                var5_5 = var7_7 + 1;
                var3_3[var7_7] = (byte)var6_6;
            }
            var6_6 = var8_8;
        } while (true);
        this._tokenIncomplete = false;
        var8_8 = var6_6;
        if (var5_5 <= 0) return var8_8;
        var8_8 = var6_6 + var5_5;
        var2_2.write(var3_3, 0, var5_5);
        return var8_8;
    }

    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
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

    protected void _reportInvalidToken(int n, String string2) throws IOException {
        this._reportInvalidToken(n, string2, this._validJsonTokenList());
    }

    protected void _reportInvalidToken(int n, String charSequence, String string2) throws IOException {
        charSequence = new StringBuilder((String)charSequence);
        do {
            char c;
            if (!Character.isJavaIdentifierPart(c = (char)this._decodeCharForError(n))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unrecognized token '");
                stringBuilder.append(((StringBuilder)charSequence).toString());
                stringBuilder.append("': was expecting ");
                stringBuilder.append(string2);
                this._reportError(stringBuilder.toString());
                return;
            }
            ((StringBuilder)charSequence).append(c);
            n = this._inputData.readUnsignedByte();
        } while (true);
    }

    protected void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int[] arrn = _icUTF8;
        do {
            int n;
            if (arrn[n = this._inputData.readUnsignedByte()] == 0) {
                continue;
            }
            if (n == 34) {
                return;
            }
            int n2 = arrn[n];
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n < 32) {
                                this._throwUnquotedSpace(n, "string value");
                                continue;
                            }
                            this._reportInvalidChar(n);
                            continue;
                        }
                        this._skipUtf8_4();
                        continue;
                    }
                    this._skipUtf8_3();
                    continue;
                }
                this._skipUtf8_2();
                continue;
            }
            this._decodeEscaped();
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
        return new JsonLocation(this._getSourceReference(), -1L, -1L, this._currInputRow, -1);
    }

    @Override
    public Object getInputSource() {
        return this._inputData;
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
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (!this._tokenIncomplete) return this._textBuffer.size();
            this._tokenIncomplete = false;
            this._finishString();
            return this._textBuffer.size();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._parsingContext.getCurrentName().length();
        }
        if (this._currToken == null) return 0;
        if (!this._currToken.isNumeric()) return this._currToken.asCharArray().length;
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
        return new JsonLocation(this._getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
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
                    var1_1 = this._skipWS();
                    this._binaryValue = null;
                    this._tokenInputRow = this._currInputRow;
                    if (var1_1 == 93 || var1_1 == 125) break block23;
                    var2_2 = var1_1;
                    if (!this._parsingContext.expectComma()) break block24;
                    if (var1_1 != 44) {
                        var3_3 = new StringBuilder();
                        var3_3.append("was expecting comma to separate ");
                        var3_3.append(this._parsingContext.typeDesc());
                        var3_3.append(" entries");
                        this._reportUnexpectedChar(var1_1, var3_3.toString());
                    }
                    var2_2 = var1_1 = this._skipWS();
                    if ((this._features & UTF8DataInputJsonParser.FEAT_MASK_TRAILING_COMMA) == 0) break block24;
                    if (var1_1 == 93) break block25;
                    var2_2 = var1_1;
                    if (var1_1 != 125) break block24;
                }
                this._closeScope(var1_1);
                return null;
            }
            if (!this._parsingContext.inObject()) {
                this._nextTokenNotInObject(var2_2);
                return null;
            }
            var4_13 = this._parseName(var2_2);
            this._parsingContext.setCurrentName(var4_13);
            this._currToken = JsonToken.FIELD_NAME;
            var2_2 = this._skipColon();
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
lbl57: // 2 sources:
                                } else {
                                    var3_6 = JsonToken.START_OBJECT;
                                }
                            } else {
                                this._matchToken("true", 1);
                                var3_7 = JsonToken.VALUE_TRUE;
                            }
                        } else {
                            this._matchToken("null", 1);
                            var3_8 = JsonToken.VALUE_NULL;
                        }
                    } else {
                        this._matchToken("false", 1);
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
        this._closeScope(var1_1);
        return null;
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
        block25 : {
            block26 : {
                block27 : {
                    if (this._closed) {
                        return null;
                    }
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
                    this._tokenInputRow = this._currInputRow;
                    if (var1_1 == 93 || var1_1 == 125) break block25;
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
                    if ((this._features & UTF8DataInputJsonParser.FEAT_MASK_TRAILING_COMMA) == 0) break block26;
                    if (var1_1 == 93) break block27;
                    var2_2 = var1_1;
                    if (var1_1 != 125) break block26;
                }
                this._closeScope(var1_1);
                return this._currToken;
            }
            if (!this._parsingContext.inObject()) {
                return this._nextTokenNotInObject(var2_2);
            }
            var3_4 = this._parseName(var2_2);
            this._parsingContext.setCurrentName(var3_4);
            this._currToken = JsonToken.FIELD_NAME;
            var2_2 = this._skipColon();
            if (var2_2 == 34) {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
                return this._currToken;
            }
            if (var2_2 != 45) {
                if (var2_2 != 91) {
                    if (var2_2 != 102) {
                        if (var2_2 != 110) {
                            if (var2_2 != 116) {
                                if (var2_2 != 123) {
                                    switch (var2_2) {
                                        default: {
                                            var3_5 = this._handleUnexpectedValue(var2_2);
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
                                    var3_6 = this._parsePosNumber(var2_2);
                                    ** break;
lbl58: // 2 sources:
                                } else {
                                    var3_7 = JsonToken.START_OBJECT;
                                }
                            } else {
                                this._matchToken("true", 1);
                                var3_8 = JsonToken.VALUE_TRUE;
                            }
                        } else {
                            this._matchToken("null", 1);
                            var3_9 = JsonToken.VALUE_NULL;
                        }
                    } else {
                        this._matchToken("false", 1);
                        var3_10 = JsonToken.VALUE_FALSE;
                    }
                } else {
                    var3_11 = JsonToken.START_ARRAY;
                }
            } else {
                var3_12 = this._parseNegNumber();
            }
            this._nextToken = var3_13;
            return this._currToken;
        }
        this._closeScope(var1_1);
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
                            arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
                            this._quadBuffer = arrn;
                        }
                        arrn[n] = UTF8DataInputJsonParser.pad(n2, n4);
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
                            arrn = UTF8DataInputJsonParser._growArrayBy(object, ((int[])object).length);
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
                                object = UTF8DataInputJsonParser._growArrayBy(arrn, arrn.length);
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
                    object = UTF8DataInputJsonParser._growArrayBy(arrn, arrn.length);
                    this._quadBuffer = object;
                }
                object[n6] = n7;
                n2 = n3;
                n = n6 + 1;
                n4 = 1;
            }
            n5 = this._inputData.readUnsignedByte();
        } while (true);
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
        return 0;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }
}

