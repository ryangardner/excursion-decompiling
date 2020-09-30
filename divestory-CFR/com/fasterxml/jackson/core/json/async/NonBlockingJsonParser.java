/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.io.OutputStream;

public class NonBlockingJsonParser
extends NonBlockingJsonParserBase
implements ByteArrayFeeder {
    private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
    private static final int FEAT_MASK_ALLOW_MISSING;
    private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
    private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
    private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
    private static final int FEAT_MASK_LEADING_ZEROS;
    private static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    private static final int[] _icUTF8;
    protected byte[] _inputBuffer = NO_BYTES;
    protected int _origBufferLen;

    static {
        FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
        FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
        FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
        FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
        FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
        FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
        FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
        _icUTF8 = CharTypes.getInputCodeUtf8();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }

    public NonBlockingJsonParser(IOContext iOContext, int n, ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
        super(iOContext, n, byteQuadsCanonicalizer);
    }

    private final int _decodeCharEscape() throws IOException {
        if (this._inputEnd - this._inputPtr >= 5) return this._decodeFastCharEscape();
        return this._decodeSplitEscaped(0, -1);
    }

    private final int _decodeFastCharEscape() throws IOException {
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        if ((n = arrby[n]) == 34) return (char)n;
        if (n == 47) return (char)n;
        if (n == 92) return (char)n;
        if (n == 98) return 8;
        if (n == 102) return 12;
        if (n == 110) return 10;
        if (n == 114) return 13;
        if (n == 116) return 9;
        if (n != 117) {
            return this._handleUnrecognizedCharacterEscape((char)n);
        }
        arrby = this._inputBuffer;
        n = this._inputPtr;
        this._inputPtr = n + 1;
        int n2 = CharTypes.charToHex(n = arrby[n]);
        if (n2 >= 0) {
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            int n3 = CharTypes.charToHex(n = arrby[n]);
            if (n3 >= 0) {
                arrby = this._inputBuffer;
                n = this._inputPtr;
                this._inputPtr = n + 1;
                int n4 = CharTypes.charToHex(n = arrby[n]);
                if (n4 >= 0) {
                    arrby = this._inputBuffer;
                    n = this._inputPtr;
                    this._inputPtr = n + 1;
                    int n5 = CharTypes.charToHex(n = arrby[n]);
                    if (n5 >= 0) {
                        return ((n2 << 4 | n3) << 4 | n4) << 4 | n5;
                    }
                }
            }
        }
        this._reportUnexpectedChar(n & 255, "expected a hex-digit for character escape sequence");
        return -1;
    }

    private int _decodeSplitEscaped(int n, int n2) throws IOException {
        int n3;
        if (this._inputPtr >= this._inputEnd) {
            this._quoted32 = n;
            this._quotedDigits = n2;
            return -1;
        }
        byte[] arrby = this._inputBuffer;
        int n4 = this._inputPtr;
        this._inputPtr = n4 + 1;
        n4 = n3 = arrby[n4];
        int n5 = n;
        int n6 = n2;
        if (n2 == -1) {
            if (n3 == 34) return n3;
            if (n3 == 47) return n3;
            if (n3 == 92) return n3;
            if (n3 == 98) return 8;
            if (n3 == 102) return 12;
            if (n3 == 110) return 10;
            if (n3 == 114) return 13;
            if (n3 == 116) return 9;
            if (n3 != 117) {
                return this._handleUnrecognizedCharacterEscape((char)n3);
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = 0;
                this._quoted32 = 0;
                return -1;
            }
            arrby = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n4 = arrby[n2];
            n6 = 0;
            n5 = n;
        }
        do {
            if ((n2 = CharTypes.charToHex(n = n4 & 255)) < 0) {
                this._reportUnexpectedChar(n & 255, "expected a hex-digit for character escape sequence");
            }
            n5 = n5 << 4 | n2;
            if (++n6 == 4) {
                return n5;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = n6;
                this._quoted32 = n5;
                return -1;
            }
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n4 = arrby[n];
        } while (true);
    }

    private final boolean _decodeSplitMultiByte(int n, int n2, boolean bl) throws IOException {
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        if (n < 32) {
                            this._throwUnquotedSpace(n, "string value");
                        } else {
                            this._reportInvalidChar(n);
                        }
                        this._textBuffer.append((char)n);
                        return true;
                    }
                    n &= 7;
                    if (bl) {
                        byte[] arrby = this._inputBuffer;
                        n2 = this._inputPtr;
                        this._inputPtr = n2 + 1;
                        return this._decodeSplitUTF8_4(n, 1, arrby[n2]);
                    }
                    this._pending32 = n;
                    this._pendingBytes = 1;
                    this._minorState = 44;
                    return false;
                }
                n &= 15;
                if (bl) {
                    byte[] arrby = this._inputBuffer;
                    n2 = this._inputPtr;
                    this._inputPtr = n2 + 1;
                    return this._decodeSplitUTF8_3(n, 1, arrby[n2]);
                }
                this._minorState = 43;
                this._pending32 = n;
                this._pendingBytes = 1;
                return false;
            }
            if (bl) {
                byte[] arrby = this._inputBuffer;
                n2 = this._inputPtr;
                this._inputPtr = n2 + 1;
                n = this._decodeUTF8_2(n, arrby[n2]);
                this._textBuffer.append((char)n);
                return true;
            }
            this._minorState = 42;
            this._pending32 = n;
            return false;
        }
        n = this._decodeSplitEscaped(0, -1);
        if (n < 0) {
            this._minorState = 41;
            return false;
        }
        this._textBuffer.append((char)n);
        return true;
    }

    private final boolean _decodeSplitUTF8_3(int n, int n2, int n3) throws IOException {
        int n4 = n;
        int n5 = n3;
        if (n2 == 1) {
            if ((n3 & 192) != 128) {
                this._reportInvalidOther(n3 & 255, this._inputPtr);
            }
            n4 = n << 6 | n3 & 63;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 43;
                this._pending32 = n4;
                this._pendingBytes = 2;
                return false;
            }
            byte[] arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n5 = arrby[n];
        }
        if ((n5 & 192) != 128) {
            this._reportInvalidOther(n5 & 255, this._inputPtr);
        }
        this._textBuffer.append((char)(n4 << 6 | n5 & 63));
        return true;
    }

    private final boolean _decodeSplitUTF8_4(int n, int n2, int n3) throws IOException {
        byte[] arrby;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        if (n2 == 1) {
            if ((n3 & 192) != 128) {
                this._reportInvalidOther(n3 & 255, this._inputPtr);
            }
            n4 = n << 6 | n3 & 63;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = n4;
                this._pendingBytes = 2;
                return false;
            }
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n6 = arrby[n];
            n5 = 2;
        }
        n2 = n4;
        n = n6;
        if (n5 == 2) {
            if ((n6 & 192) != 128) {
                this._reportInvalidOther(n6 & 255, this._inputPtr);
            }
            n2 = n4 << 6 | n6 & 63;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = n2;
                this._pendingBytes = 3;
                return false;
            }
            arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n = arrby[n];
        }
        if ((n & 192) != 128) {
            this._reportInvalidOther(n & 255, this._inputPtr);
        }
        n = (n2 << 6 | n & 63) - 65536;
        this._textBuffer.append((char)(55296 | n >> 10));
        this._textBuffer.append((char)(n & 1023 | 56320));
        return true;
    }

    private final int _decodeUTF8_2(int n, int n2) throws IOException {
        if ((n2 & 192) == 128) return (n & 31) << 6 | n2 & 63;
        this._reportInvalidOther(n2 & 255, this._inputPtr);
        return (n & 31) << 6 | n2 & 63;
    }

    private final int _decodeUTF8_3(int n, int n2, int n3) throws IOException {
        if ((n2 & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if ((n3 & 192) == 128) return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
        this._reportInvalidOther(n3 & 255, this._inputPtr);
        return ((n & 15) << 6 | n2 & 63) << 6 | n3 & 63;
    }

    private final int _decodeUTF8_4(int n, int n2, int n3, int n4) throws IOException {
        if ((n2 & 192) != 128) {
            this._reportInvalidOther(n2 & 255, this._inputPtr);
        }
        if ((n3 & 192) != 128) {
            this._reportInvalidOther(n3 & 255, this._inputPtr);
        }
        if ((n4 & 192) == 128) return ((((n & 7) << 6 | n2 & 63) << 6 | n3 & 63) << 6 | n4 & 63) - 65536;
        this._reportInvalidOther(n4 & 255, this._inputPtr);
        return ((((n & 7) << 6 | n2 & 63) << 6 | n3 & 63) << 6 | n4 & 63) - 65536;
    }

    private final String _fastParseName() throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n = this._inputPtr;
        int n2 = n + 1;
        int n3 = arrby[n] & 255;
        if (arrn[n3] != 0) {
            if (n3 != 34) return null;
            this._inputPtr = n2;
            return "";
        }
        n = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return null;
            this._inputPtr = n;
            return this._findName(n3, 1);
        }
        n2 = n3 << 8 | n2;
        n3 = n + 1;
        if (arrn[n = arrby[n] & 255] != 0) {
            if (n != 34) return null;
            this._inputPtr = n3;
            return this._findName(n2, 2);
        }
        n2 = n2 << 8 | n;
        n = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 != 34) return null;
            this._inputPtr = n;
            return this._findName(n2, 3);
        }
        n3 = n2 << 8 | n3;
        n2 = n + 1;
        if (arrn[n = arrby[n] & 255] == 0) {
            this._quad1 = n3;
            return this._parseMediumName(n2, n);
        }
        if (n != 34) return null;
        this._inputPtr = n2;
        return this._findName(n3, 4);
    }

    private JsonToken _finishAposName(int n, int n2, int n3) throws IOException {
        Object object = this._quadBuffer;
        Object object2 = _icLatin1;
        do {
            if (this._inputPtr >= this._inputEnd) {
                this._quadLength = n;
                this._pending32 = n2;
                this._pendingBytes = n3;
                this._minorState = 9;
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            byte[] arrby = this._inputBuffer;
            int n4 = this._inputPtr;
            this._inputPtr = n4 + 1;
            int n5 = arrby[n4] & 255;
            if (n5 == 39) {
                if (n3 > 0) {
                    arrby = object;
                    if (n >= ((int[])object).length) {
                        arrby = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                        this._quadBuffer = arrby;
                    }
                    arrby[n] = NonBlockingJsonParser._padLastQuad(n2, n3);
                    n2 = n + 1;
                } else {
                    arrby = object;
                    n2 = n;
                    if (n == 0) {
                        return this._fieldComplete("");
                    }
                }
                object2 = this._symbols.findName(arrby, n2);
                object = object2;
                if (object2 != null) return this._fieldComplete((String)object);
                object = this._addName(arrby, n2, n3);
                return this._fieldComplete((String)object);
            }
            arrby = object;
            n4 = n5;
            int n6 = n;
            int n7 = n2;
            int n8 = n3;
            if (n5 != 34) {
                arrby = object;
                n4 = n5;
                n6 = n;
                n7 = n2;
                n8 = n3;
                if (object2[n5] != 0) {
                    if (n5 != 92) {
                        this._throwUnquotedSpace(n5, "name");
                    } else {
                        n5 = n4 = this._decodeCharEscape();
                        if (n4 < 0) {
                            this._minorState = 8;
                            this._minorStateAfterSplit = 9;
                            this._quadLength = n;
                            this._pending32 = n2;
                            this._pendingBytes = n3;
                            this._currToken = object = JsonToken.NOT_AVAILABLE;
                            return object;
                        }
                    }
                    arrby = object;
                    n4 = n5;
                    n6 = n;
                    n7 = n2;
                    n8 = n3;
                    if (n5 > 127) {
                        n8 = 0;
                        arrby = object;
                        n4 = n;
                        n7 = n2;
                        n6 = n3;
                        if (n3 >= 4) {
                            arrby = object;
                            if (n >= ((int[])object).length) {
                                arrby = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                                this._quadBuffer = arrby;
                            }
                            arrby[n] = n2;
                            n4 = n + 1;
                            n7 = 0;
                            n6 = 0;
                        }
                        if (n5 < 2048) {
                            n = n7 << 8 | (n5 >> 6 | 192);
                            n2 = n6 + 1;
                        } else {
                            n2 = n7 << 8 | (n5 >> 12 | 224);
                            n = n6 + 1;
                            if (n >= 4) {
                                object = arrby;
                                if (n4 >= arrby.length) {
                                    this._quadBuffer = object = NonBlockingJsonParser.growArrayBy(arrby, arrby.length);
                                }
                                object[n4] = n2;
                                ++n4;
                                n = 0;
                                n2 = n8;
                            } else {
                                object = arrby;
                            }
                            n3 = n2 << 8 | (n5 >> 6 & 63 | 128);
                            n2 = n + 1;
                            n = n3;
                            arrby = object;
                        }
                        n3 = n5 & 63 | 128;
                        n8 = n2;
                        n7 = n;
                        n6 = n4;
                        n4 = n3;
                    }
                }
            }
            if (n8 < 4) {
                n3 = n8 + 1;
                n2 = n7 << 8 | n4;
                object = arrby;
                n = n6;
                continue;
            }
            object = arrby;
            if (n6 >= arrby.length) {
                this._quadBuffer = object = NonBlockingJsonParser.growArrayBy(arrby, arrby.length);
            }
            object[n6] = n7;
            n = n6 + 1;
            n2 = n4;
            n3 = 1;
        } while (true);
    }

    private final JsonToken _finishAposString() throws IOException {
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        Object[] arrobject = this._textBuffer.getBufferWithoutReset();
        int n = this._textBuffer.getCurrentSegmentSize();
        int n2 = this._inputPtr;
        int n3 = this._inputEnd;
        block0 : do {
            Object object;
            if (n2 >= this._inputEnd) {
                this._inputPtr = n2;
                this._minorState = 45;
                this._textBuffer.setCurrentLength(n);
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            int n4 = arrobject.length;
            int n5 = 0;
            boolean bl = false;
            object = arrobject;
            int n6 = n;
            if (n >= n4) {
                object = this._textBuffer.finishCurrentSegment();
                n6 = 0;
            }
            int n7 = Math.min(this._inputEnd, ((char[])object).length - n6 + n2);
            n4 = n2;
            do {
                arrobject = object;
                n = n6;
                n2 = n4;
                if (n4 >= n7) continue block0;
                n = n4 + 1;
                n2 = arrby[n4] & 255;
                if (arrn[n2] != 0 && n2 != 34) {
                    if (n >= n3 - 5) {
                        this._inputPtr = n;
                        this._textBuffer.setCurrentLength(n6);
                        n6 = arrn[n2];
                        if (n < this._inputEnd) {
                            bl = true;
                        }
                        if (!this._decodeSplitMultiByte(n2, n6, bl)) {
                            this._minorStateAfterSplit = 45;
                            this._currToken = object = JsonToken.NOT_AVAILABLE;
                            return object;
                        }
                        arrobject = this._textBuffer.getBufferWithoutReset();
                        n = this._textBuffer.getCurrentSegmentSize();
                        n2 = this._inputPtr;
                        continue block0;
                    }
                    n4 = arrn[n2];
                    if (n4 != 1) {
                        if (n4 != 2) {
                            if (n4 != 3) {
                                if (n4 != 4) {
                                    if (n2 < 32) {
                                        this._throwUnquotedSpace(n2, "string value");
                                    } else {
                                        this._reportInvalidChar(n2);
                                    }
                                } else {
                                    arrobject = this._inputBuffer;
                                    n7 = n + 1;
                                    n = arrobject[n];
                                    n4 = n7 + 1;
                                    n2 = this._decodeUTF8_4(n2, n, arrobject[n7], arrobject[n4]);
                                    n = n6 + 1;
                                    object[n6] = (char)(55296 | n2 >> 10);
                                    if (n >= ((char[])object).length) {
                                        object = this._textBuffer.finishCurrentSegment();
                                        n6 = 0;
                                    } else {
                                        n6 = n;
                                    }
                                    n2 = n2 & 1023 | 56320;
                                    n = n4 + 1;
                                }
                            } else {
                                arrobject = this._inputBuffer;
                                n4 = n + 1;
                                n2 = this._decodeUTF8_3(n2, arrobject[n], arrobject[n4]);
                                n = n4 + 1;
                            }
                        } else {
                            n2 = this._decodeUTF8_2(n2, this._inputBuffer[n]);
                            ++n;
                        }
                    } else {
                        this._inputPtr = n;
                        n2 = this._decodeFastCharEscape();
                        n = this._inputPtr;
                    }
                    if (n6 >= ((char[])object).length) {
                        arrobject = this._textBuffer.finishCurrentSegment();
                        n6 = n5;
                    } else {
                        arrobject = object;
                    }
                    n4 = n6 + 1;
                    arrobject[n6] = (char)n2;
                    n2 = n;
                    n = n4;
                    continue block0;
                }
                if (n2 == 39) {
                    this._inputPtr = n;
                    this._textBuffer.setCurrentLength(n6);
                    return this._valueComplete(JsonToken.VALUE_STRING);
                }
                object[n6] = (char)n2;
                ++n6;
                n4 = n;
            } while (true);
            break;
        } while (true);
    }

    private final JsonToken _finishBOM(int n) throws IOException {
        do {
            Object object;
            if (this._inputPtr >= this._inputEnd) {
                this._pending32 = n;
                this._minorState = 1;
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            object = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            n2 = object[n2] & 255;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this._currInputProcessed -= 3L;
                        return this._startDocument(n2);
                    }
                } else if (n2 != 191) {
                    this._reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", n2);
                }
            } else if (n2 != 187) {
                this._reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", n2);
            }
            ++n;
        } while (true);
    }

    private final JsonToken _finishCComment(int n, boolean bl) throws IOException {
        Object object;
        int n2;
        do {
            if (this._inputPtr >= this._inputEnd) {
                if (bl) {
                    n2 = 52;
                    break;
                }
                n2 = 53;
                break;
            }
            object = this._inputBuffer;
            n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = object[n2] & 255) < 32) {
                if (n2 == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                } else if (n2 == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                } else if (n2 != 9) {
                    this._throwInvalidSpace(n2);
                }
            } else {
                if (n2 == 42) {
                    bl = true;
                    continue;
                }
                if (n2 == 47 && bl) {
                    return this._startAfterComment(n);
                }
            }
            bl = false;
        } while (true);
        this._minorState = n2;
        this._pending32 = n;
        this._currToken = object = JsonToken.NOT_AVAILABLE;
        return object;
    }

    private final JsonToken _finishCppComment(int n) throws IOException {
        do {
            Object object;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 54;
                this._pending32 = n;
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            object = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = object[n2] & 255) >= 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                return this._startAfterComment(n);
            }
            if (n2 == 13) {
                ++this._currInputRowAlt;
                this._currInputRowStart = this._inputPtr;
                return this._startAfterComment(n);
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        } while (true);
    }

    private final JsonToken _finishHashComment(int n) throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
            this._reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
        }
        do {
            Object object;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 55;
                this._pending32 = n;
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            object = this._inputBuffer;
            int n2 = this._inputPtr;
            this._inputPtr = n2 + 1;
            if ((n2 = object[n2] & 255) >= 32) continue;
            if (n2 == 10) {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
                return this._startAfterComment(n);
            }
            if (n2 == 13) {
                ++this._currInputRowAlt;
                this._currInputRowStart = this._inputPtr;
                return this._startAfterComment(n);
            }
            if (n2 == 9) continue;
            this._throwInvalidSpace(n2);
        } while (true);
    }

    private final JsonToken _finishRegularString() throws IOException {
        int[] arrn = _icUTF8;
        byte[] arrby = this._inputBuffer;
        Object[] arrobject = this._textBuffer.getBufferWithoutReset();
        int n = this._textBuffer.getCurrentSegmentSize();
        int n2 = this._inputPtr;
        int n3 = this._inputEnd;
        block0 : do {
            Object object;
            if (n2 >= this._inputEnd) {
                this._inputPtr = n2;
                this._minorState = 40;
                this._textBuffer.setCurrentLength(n);
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            int n4 = arrobject.length;
            int n5 = 0;
            boolean bl = false;
            object = arrobject;
            int n6 = n;
            if (n >= n4) {
                object = this._textBuffer.finishCurrentSegment();
                n6 = 0;
            }
            int n7 = Math.min(this._inputEnd, ((char[])object).length - n6 + n2);
            n4 = n2;
            do {
                arrobject = object;
                n = n6;
                n2 = n4;
                if (n4 >= n7) continue block0;
                n = n4 + 1;
                n2 = arrby[n4] & 255;
                if (arrn[n2] != 0) {
                    if (n2 == 34) {
                        this._inputPtr = n;
                        this._textBuffer.setCurrentLength(n6);
                        return this._valueComplete(JsonToken.VALUE_STRING);
                    }
                    if (n >= n3 - 5) {
                        this._inputPtr = n;
                        this._textBuffer.setCurrentLength(n6);
                        n6 = arrn[n2];
                        if (n < this._inputEnd) {
                            bl = true;
                        }
                        if (!this._decodeSplitMultiByte(n2, n6, bl)) {
                            this._minorStateAfterSplit = 40;
                            this._currToken = object = JsonToken.NOT_AVAILABLE;
                            return object;
                        }
                        arrobject = this._textBuffer.getBufferWithoutReset();
                        n = this._textBuffer.getCurrentSegmentSize();
                        n2 = this._inputPtr;
                        continue block0;
                    }
                    n4 = arrn[n2];
                    if (n4 != 1) {
                        if (n4 != 2) {
                            if (n4 != 3) {
                                if (n4 != 4) {
                                    if (n2 < 32) {
                                        this._throwUnquotedSpace(n2, "string value");
                                    } else {
                                        this._reportInvalidChar(n2);
                                    }
                                } else {
                                    arrobject = this._inputBuffer;
                                    n7 = n + 1;
                                    n = arrobject[n];
                                    n4 = n7 + 1;
                                    n2 = this._decodeUTF8_4(n2, n, arrobject[n7], arrobject[n4]);
                                    n = n6 + 1;
                                    object[n6] = (char)(55296 | n2 >> 10);
                                    if (n >= ((char[])object).length) {
                                        object = this._textBuffer.finishCurrentSegment();
                                        n6 = 0;
                                    } else {
                                        n6 = n;
                                    }
                                    n2 = n2 & 1023 | 56320;
                                    n = n4 + 1;
                                }
                            } else {
                                arrobject = this._inputBuffer;
                                n4 = n + 1;
                                n2 = this._decodeUTF8_3(n2, arrobject[n], arrobject[n4]);
                                n = n4 + 1;
                            }
                        } else {
                            n2 = this._decodeUTF8_2(n2, this._inputBuffer[n]);
                            ++n;
                        }
                    } else {
                        this._inputPtr = n;
                        n2 = this._decodeFastCharEscape();
                        n = this._inputPtr;
                    }
                    if (n6 >= ((char[])object).length) {
                        object = this._textBuffer.finishCurrentSegment();
                        n6 = n5;
                    }
                    n4 = n6 + 1;
                    object[n6] = (char)n2;
                    n2 = n;
                    arrobject = object;
                    n = n4;
                    continue block0;
                }
                object[n6] = (char)n2;
                ++n6;
                n4 = n;
            } while (true);
            break;
        } while (true);
    }

    private JsonToken _finishUnquotedName(int n, int n2, int n3) throws IOException {
        Object object = this._quadBuffer;
        Object object2 = CharTypes.getInputCodeUtf8JsNames();
        do {
            int[] arrn;
            if (this._inputPtr >= this._inputEnd) {
                this._quadLength = n;
                this._pending32 = n2;
                this._pendingBytes = n3;
                this._minorState = 10;
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            int n4 = this._inputBuffer[this._inputPtr] & 255;
            if (object2[n4] != 0) {
                arrn = object;
                n4 = n;
                if (n3 > 0) {
                    arrn = object;
                    if (n >= ((int[])object).length) {
                        this._quadBuffer = arrn = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                    }
                    arrn[n] = n2;
                    n4 = n + 1;
                }
                object2 = this._symbols.findName(arrn, n4);
                object = object2;
                if (object2 != null) return this._fieldComplete((String)object);
                object = this._addName(arrn, n4, n3);
                return this._fieldComplete((String)object);
            }
            ++this._inputPtr;
            if (n3 < 4) {
                ++n3;
                n2 = n2 << 8 | n4;
                continue;
            }
            arrn = object;
            if (n >= ((int[])object).length) {
                this._quadBuffer = arrn = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
            }
            arrn[n] = n2;
            ++n;
            n2 = n4;
            n3 = 1;
            object = arrn;
        } while (true);
    }

    private JsonToken _handleOddName(int n) throws IOException {
        if (n != 35) {
            if (n != 39) {
                if (n == 47) return this._startSlashComment(4);
                if (n == 93) return this._closeArrayScope();
            } else if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
                return this._finishAposName(0, 0, 0);
            }
        } else if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) != 0) {
            return this._finishHashComment(4);
        }
        if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar((char)n, "was expecting double-quote to start field name");
        }
        if (CharTypes.getInputCodeUtf8JsNames()[n] == 0) return this._finishUnquotedName(0, n, 1);
        this._reportUnexpectedChar(n, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        return this._finishUnquotedName(0, n, 1);
    }

    private final JsonToken _parseEscapedName(int n, int n2, int n3) throws IOException {
        Object object = this._quadBuffer;
        Object object2 = _icLatin1;
        do {
            int n4;
            block23 : {
                int n5;
                byte[] arrby;
                int n6;
                block24 : {
                    block22 : {
                        int n7;
                        block20 : {
                            block21 : {
                                if (this._inputPtr >= this._inputEnd) {
                                    this._quadLength = n;
                                    this._pending32 = n2;
                                    this._pendingBytes = n3;
                                    this._minorState = 7;
                                    this._currToken = object = JsonToken.NOT_AVAILABLE;
                                    return object;
                                }
                                arrby = this._inputBuffer;
                                n4 = this._inputPtr;
                                this._inputPtr = n4 + 1;
                                n7 = arrby[n4] & 255;
                                if (object2[n7] != 0) break block20;
                                if (n3 >= 4) break block21;
                                n4 = n7;
                                break block22;
                            }
                            arrby = object;
                            if (n >= ((int[])object).length) {
                                arrby = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                                this._quadBuffer = arrby;
                            }
                            n3 = n + 1;
                            arrby[n] = n2;
                            n = n3;
                            n4 = n7;
                            object = arrby;
                            break block23;
                        }
                        if (n7 == 34) {
                            if (n3 > 0) {
                                arrby = object;
                                if (n >= ((int[])object).length) {
                                    arrby = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                                    this._quadBuffer = arrby;
                                }
                                arrby[n] = NonBlockingJsonParser._padLastQuad(n2, n3);
                                n2 = n + 1;
                            } else {
                                arrby = object;
                                n2 = n;
                                if (n == 0) {
                                    return this._fieldComplete("");
                                }
                            }
                            object2 = this._symbols.findName(arrby, n2);
                            object = object2;
                            if (object2 != null) return this._fieldComplete((String)object);
                            object = this._addName(arrby, n2, n3);
                            return this._fieldComplete((String)object);
                        }
                        if (n7 != 92) {
                            this._throwUnquotedSpace(n7, "name");
                        } else {
                            n7 = n4 = this._decodeCharEscape();
                            if (n4 < 0) {
                                this._minorState = 8;
                                this._minorStateAfterSplit = 7;
                                this._quadLength = n;
                                this._pending32 = n2;
                                this._pendingBytes = n3;
                                this._currToken = object = JsonToken.NOT_AVAILABLE;
                                return object;
                            }
                        }
                        arrby = object;
                        if (n >= ((int[])object).length) {
                            arrby = NonBlockingJsonParser.growArrayBy(object, ((int[])object).length);
                            this._quadBuffer = arrby;
                        }
                        n4 = n7;
                        n6 = n;
                        n5 = n2;
                        int n8 = n3;
                        if (n7 > 127) {
                            n6 = 0;
                            n4 = n;
                            n5 = n2;
                            n8 = n3;
                            if (n3 >= 4) {
                                arrby[n] = n2;
                                n4 = n + 1;
                                n5 = 0;
                                n8 = 0;
                            }
                            if (n7 < 2048) {
                                n5 = n5 << 8 | (n7 >> 6 | 192);
                                ++n8;
                            } else {
                                n2 = n5 << 8 | (n7 >> 12 | 224);
                                n = n8 + 1;
                                if (n >= 4) {
                                    arrby[n4] = n2;
                                    ++n4;
                                    n = 0;
                                    n2 = n6;
                                }
                                n5 = n2 << 8 | (n7 >> 6 & 63 | 128);
                                n8 = n + 1;
                            }
                            n = n7 & 63 | 128;
                            n6 = n4;
                            n4 = n;
                        }
                        if (n8 >= 4) break block24;
                        object = arrby;
                        n = n6;
                        n2 = n5;
                        n3 = n8;
                    }
                    ++n3;
                    n2 = n2 << 8 | n4;
                    continue;
                }
                n = n6 + 1;
                arrby[n6] = n5;
                object = arrby;
            }
            n2 = n4;
            n3 = 1;
        } while (true);
    }

    private final String _parseMediumName(int n, int n2) throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n3 = n + 1;
        if (arrn[n = arrby[n] & 255] != 0) {
            if (n != 34) return null;
            this._inputPtr = n3;
            return this._findName(this._quad1, n2, 1);
        }
        n |= n2 << 8;
        n2 = n3 + 1;
        if (arrn[n3 = arrby[n3] & 255] != 0) {
            if (n3 != 34) return null;
            this._inputPtr = n2;
            return this._findName(this._quad1, n, 2);
        }
        n3 = n << 8 | n3;
        n = n2 + 1;
        if (arrn[n2 = arrby[n2] & 255] != 0) {
            if (n2 != 34) return null;
            this._inputPtr = n;
            return this._findName(this._quad1, n3, 3);
        }
        n3 = n3 << 8 | n2;
        n2 = n + 1;
        if (arrn[n = arrby[n] & 255] == 0) {
            return this._parseMediumName2(n2, n, n3);
        }
        if (n != 34) return null;
        this._inputPtr = n2;
        return this._findName(this._quad1, n3, 4);
    }

    private final String _parseMediumName2(int n, int n2, int n3) throws IOException {
        byte[] arrby = this._inputBuffer;
        int[] arrn = _icLatin1;
        int n4 = n + 1;
        if (arrn[n = arrby[n] & 255] != 0) {
            if (n != 34) return null;
            this._inputPtr = n4;
            return this._findName(this._quad1, n3, n2, 1);
        }
        n2 = n | n2 << 8;
        n = n4 + 1;
        if (arrn[n4 = arrby[n4] & 255] != 0) {
            if (n4 != 34) return null;
            this._inputPtr = n;
            return this._findName(this._quad1, n3, n2, 2);
        }
        n4 = n2 << 8 | n4;
        n2 = n + 1;
        if (arrn[n = arrby[n] & 255] != 0) {
            if (n != 34) return null;
            this._inputPtr = n2;
            return this._findName(this._quad1, n3, n4, 3);
        }
        if ((arrby[n2] & 255) != 34) return null;
        this._inputPtr = n2 + 1;
        return this._findName(this._quad1, n3, n4 << 8 | n, 4);
    }

    private final int _skipWS(int n) throws IOException {
        int n2;
        do {
            if (n != 32) {
                if (n == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                } else if (n == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                } else if (n != 9) {
                    this._throwInvalidSpace(n);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._currToken = JsonToken.NOT_AVAILABLE;
                return 0;
            }
            byte[] arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n = n2 = arrby[n] & 255;
        } while (n2 <= 32);
        return n2;
    }

    private final JsonToken _startAfterComment(int n) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            JsonToken jsonToken;
            this._minorState = n;
            this._currToken = jsonToken = JsonToken.NOT_AVAILABLE;
            return jsonToken;
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        n2 = arrby[n2] & 255;
        if (n == 4) return this._startFieldName(n2);
        if (n == 5) return this._startFieldNameAfterComma(n2);
        switch (n) {
            default: {
                VersionUtil.throwInternal();
                return null;
            }
            case 15: {
                return this._startValueAfterComma(n2);
            }
            case 14: {
                return this._startValueExpectColon(n2);
            }
            case 13: {
                return this._startValueExpectComma(n2);
            }
            case 12: 
        }
        return this._startValue(n2);
    }

    private final JsonToken _startDocument(int n) throws IOException {
        int n2;
        n = n2 = n & 255;
        if (n2 == 239) {
            n = n2;
            if (this._minorState != 1) {
                return this._finishBOM(1);
            }
        }
        while (n <= 32) {
            if (n != 32) {
                if (n == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                } else if (n == 13) {
                    ++this._currInputRowAlt;
                    this._currInputRowStart = this._inputPtr;
                } else if (n != 9) {
                    this._throwInvalidSpace(n);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 3;
                if (this._closed) {
                    return null;
                }
                if (!this._endOfInput) return JsonToken.NOT_AVAILABLE;
                return this._eofAsNextToken();
            }
            byte[] arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n = arrby[n] & 255;
        }
        return this._startValue(n);
    }

    private final JsonToken _startFieldName(int n) throws IOException {
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 4;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (n2 != 34) {
            if (n2 != 125) return this._handleOddName(n2);
            return this._closeObjectScope();
        }
        if (this._inputPtr + 13 > this._inputEnd) return this._parseEscapedName(0, 0, 0);
        String string2 = this._fastParseName();
        if (string2 == null) return this._parseEscapedName(0, 0, 0);
        return this._fieldComplete(string2);
    }

    private final JsonToken _startFieldNameAfterComma(int n) throws IOException {
        Object object;
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 5;
                return this._currToken;
            }
        }
        if (n2 != 44) {
            if (n2 == 125) {
                return this._closeObjectScope();
            }
            if (n2 == 35) {
                return this._finishHashComment(5);
            }
            if (n2 == 47) {
                return this._startSlashComment(5);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("was expecting comma to separate ");
            ((StringBuilder)object).append(this._parsingContext.typeDesc());
            ((StringBuilder)object).append(" entries");
            this._reportUnexpectedChar(n2, ((StringBuilder)object).toString());
        }
        if ((n = this._inputPtr) >= this._inputEnd) {
            this._minorState = 4;
            object = JsonToken.NOT_AVAILABLE;
            this._currToken = object;
            return object;
        }
        n2 = this._inputBuffer[n];
        this._inputPtr = n + 1;
        n = n2;
        if (n2 <= 32) {
            n = n2 = this._skipWS(n2);
            if (n2 <= 0) {
                this._minorState = 4;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (n != 34) {
            if (n != 125) return this._handleOddName(n);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) return this._handleOddName(n);
            return this._closeObjectScope();
        }
        if (this._inputPtr + 13 > this._inputEnd) return this._parseEscapedName(0, 0, 0);
        object = this._fastParseName();
        if (object == null) return this._parseEscapedName(0, 0, 0);
        return this._fieldComplete((String)object);
    }

    private final JsonToken _startSlashComment(int n) throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd) {
            JsonToken jsonToken;
            this._pending32 = n;
            this._minorState = 51;
            this._currToken = jsonToken = JsonToken.NOT_AVAILABLE;
            return jsonToken;
        }
        byte[] arrby = this._inputBuffer;
        int n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        if ((n2 = arrby[n2]) == 42) {
            return this._finishCComment(n, false);
        }
        if (n2 == 47) {
            return this._finishCppComment(n);
        }
        this._reportUnexpectedChar(n2 & 255, "was expecting either '*' or '/' for a comment");
        return null;
    }

    private final JsonToken _startValue(int n) throws IOException {
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 12;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        this._parsingContext.expectComma();
        if (n2 == 34) {
            return this._startString();
        }
        if (n2 == 35) return this._finishHashComment(12);
        if (n2 == 45) return this._startNegativeNumber();
        if (n2 == 91) return this._startArrayScope();
        if (n2 == 93) return this._closeArrayScope();
        if (n2 == 102) return this._startFalseToken();
        if (n2 == 110) return this._startNullToken();
        if (n2 == 116) return this._startTrueToken();
        if (n2 == 123) return this._startObjectScope();
        if (n2 == 125) return this._closeObjectScope();
        switch (n2) {
            default: {
                return this._startUnexpectedValue(false, n2);
            }
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                return this._startPositiveNumber(n2);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 47: 
        }
        return this._startSlashComment(12);
    }

    private final JsonToken _startValueAfterComma(int n) throws IOException {
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 15;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (n2 == 34) {
            return this._startString();
        }
        if (n2 == 35) return this._finishHashComment(15);
        if (n2 == 45) return this._startNegativeNumber();
        if (n2 == 91) return this._startArrayScope();
        if (n2 == 93) {
            if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) return this._startUnexpectedValue(true, n2);
            return this._closeArrayScope();
        }
        if (n2 == 102) return this._startFalseToken();
        if (n2 == 110) return this._startNullToken();
        if (n2 == 116) return this._startTrueToken();
        if (n2 == 123) return this._startObjectScope();
        if (n2 == 125) {
            if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) return this._startUnexpectedValue(true, n2);
            return this._closeObjectScope();
        }
        switch (n2) {
            default: {
                return this._startUnexpectedValue(true, n2);
            }
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                return this._startPositiveNumber(n2);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 47: 
        }
        return this._startSlashComment(15);
    }

    private final JsonToken _startValueExpectColon(int n) throws IOException {
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 14;
                return this._currToken;
            }
        }
        if (n2 != 58) {
            if (n2 == 47) {
                return this._startSlashComment(14);
            }
            if (n2 == 35) {
                return this._finishHashComment(14);
            }
            this._reportUnexpectedChar(n2, "was expecting a colon to separate field name and value");
        }
        if ((n = this._inputPtr) >= this._inputEnd) {
            JsonToken jsonToken;
            this._minorState = 12;
            this._currToken = jsonToken = JsonToken.NOT_AVAILABLE;
            return jsonToken;
        }
        n2 = this._inputBuffer[n];
        this._inputPtr = n + 1;
        n = n2;
        if (n2 <= 32) {
            n = n2 = this._skipWS(n2);
            if (n2 <= 0) {
                this._minorState = 12;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (n == 34) {
            return this._startString();
        }
        if (n == 35) return this._finishHashComment(12);
        if (n == 45) return this._startNegativeNumber();
        if (n == 91) return this._startArrayScope();
        if (n == 102) return this._startFalseToken();
        if (n == 110) return this._startNullToken();
        if (n == 116) return this._startTrueToken();
        if (n == 123) return this._startObjectScope();
        switch (n) {
            default: {
                return this._startUnexpectedValue(false, n);
            }
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                return this._startPositiveNumber(n);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 47: 
        }
        return this._startSlashComment(12);
    }

    private final JsonToken _startValueExpectComma(int n) throws IOException {
        Object object;
        int n2 = n;
        if (n <= 32) {
            n2 = n = this._skipWS(n);
            if (n <= 0) {
                this._minorState = 13;
                return this._currToken;
            }
        }
        if (n2 != 44) {
            if (n2 == 93) {
                return this._closeArrayScope();
            }
            if (n2 == 125) {
                return this._closeObjectScope();
            }
            if (n2 == 47) {
                return this._startSlashComment(13);
            }
            if (n2 == 35) {
                return this._finishHashComment(13);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("was expecting comma to separate ");
            ((StringBuilder)object).append(this._parsingContext.typeDesc());
            ((StringBuilder)object).append(" entries");
            this._reportUnexpectedChar(n2, ((StringBuilder)object).toString());
        }
        this._parsingContext.expectComma();
        n = this._inputPtr;
        if (n >= this._inputEnd) {
            this._minorState = 15;
            object = JsonToken.NOT_AVAILABLE;
            this._currToken = object;
            return object;
        }
        n2 = this._inputBuffer[n];
        this._inputPtr = n + 1;
        n = n2;
        if (n2 <= 32) {
            n = n2 = this._skipWS(n2);
            if (n2 <= 0) {
                this._minorState = 15;
                return this._currToken;
            }
        }
        this._updateTokenLocation();
        if (n == 34) {
            return this._startString();
        }
        if (n == 35) return this._finishHashComment(15);
        if (n == 45) return this._startNegativeNumber();
        if (n == 91) return this._startArrayScope();
        if (n == 93) {
            if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) return this._startUnexpectedValue(true, n);
            return this._closeArrayScope();
        }
        if (n == 102) return this._startFalseToken();
        if (n == 110) return this._startNullToken();
        if (n == 116) return this._startTrueToken();
        if (n == 123) return this._startObjectScope();
        if (n == 125) {
            if ((this._features & FEAT_MASK_TRAILING_COMMA) == 0) return this._startUnexpectedValue(true, n);
            return this._closeObjectScope();
        }
        switch (n) {
            default: {
                return this._startUnexpectedValue(true, n);
            }
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                return this._startPositiveNumber(n);
            }
            case 48: {
                return this._startNumberLeadingZero();
            }
            case 47: 
        }
        return this._startSlashComment(15);
    }

    @Override
    protected char _decodeEscaped() throws IOException {
        VersionUtil.throwInternal();
        return ' ';
    }

    protected JsonToken _finishErrorToken() throws IOException {
        do {
            byte[] arrby;
            if (this._inputPtr >= this._inputEnd) {
                this._currToken = arrby = JsonToken.NOT_AVAILABLE;
                return arrby;
            }
            arrby = this._inputBuffer;
            int n = this._inputPtr;
            this._inputPtr = n + 1;
            char c = (char)arrby[n];
            if (!Character.isJavaIdentifierPart(c)) return this._reportErrorToken(this._textBuffer.contentsAsString());
            this._textBuffer.append(c);
            if (this._textBuffer.size() >= 256) return this._reportErrorToken(this._textBuffer.contentsAsString());
        } while (true);
    }

    protected JsonToken _finishErrorTokenWithEOF() throws IOException {
        return this._reportErrorToken(this._textBuffer.contentsAsString());
    }

    protected final JsonToken _finishFieldWithEscape() throws IOException {
        int[] arrn;
        int n = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
        if (n < 0) {
            this._minorState = 8;
            return JsonToken.NOT_AVAILABLE;
        }
        if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = NonBlockingJsonParser.growArrayBy(this._quadBuffer, 32);
        }
        int n2 = this._pending32;
        int n3 = this._pendingBytes;
        int n4 = 1;
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        if (n > 127) {
            n7 = 0;
            n6 = n2;
            n5 = n3;
            if (n3 >= 4) {
                arrn = this._quadBuffer;
                n5 = this._quadLength;
                this._quadLength = n5 + 1;
                arrn[n5] = n2;
                n6 = 0;
                n5 = 0;
            }
            if (n < 2048) {
                n3 = n6 << 8;
                n2 = n >> 6 | 192;
            } else {
                n2 = n6 << 8 | (n >> 12 | 224);
                if (++n5 >= 4) {
                    arrn = this._quadBuffer;
                    n5 = this._quadLength;
                    this._quadLength = n5 + 1;
                    arrn[n5] = n2;
                    n5 = 0;
                    n2 = n7;
                }
                n3 = n2 << 8;
                n2 = n >> 6 & 63 | 128;
            }
            n6 = n3 | n2;
            n7 = n5 + 1;
            n5 = n & 63 | 128;
        }
        if (n7 < 4) {
            n2 = 1 + n7;
            n5 |= n6 << 8;
        } else {
            arrn = this._quadBuffer;
            n2 = this._quadLength;
            this._quadLength = n2 + 1;
            arrn[n2] = n6;
            n2 = n4;
        }
        if (this._minorStateAfterSplit != 9) return this._parseEscapedName(this._quadLength, n5, n2);
        return this._finishAposName(this._quadLength, n5, n2);
    }

    protected JsonToken _finishFloatExponent(boolean bl, int n) throws IOException {
        Object[] arrobject;
        int n2;
        block8 : {
            block9 : {
                n2 = n;
                if (!bl) break block8;
                this._minorState = 32;
                if (n == 45) break block9;
                n2 = n;
                if (n != 43) break block8;
            }
            this._textBuffer.append((char)n);
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 32;
                this._expLength = 0;
                return JsonToken.NOT_AVAILABLE;
            }
            arrobject = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n2 = arrobject[n];
        }
        Object[] arrobject2 = this._textBuffer.getBufferWithoutReset();
        n = this._textBuffer.getCurrentSegmentSize();
        int n3 = this._expLength;
        while (n2 >= 48 && n2 <= 57) {
            ++n3;
            arrobject = arrobject2;
            if (n >= arrobject2.length) {
                arrobject = this._textBuffer.expandCurrentSegment();
            }
            int n4 = n + 1;
            arrobject[n] = (char)n2;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(n4);
                this._expLength = n3;
                return JsonToken.NOT_AVAILABLE;
            }
            arrobject2 = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            n2 = arrobject2[n];
            n = n4;
            arrobject2 = arrobject;
        }
        if (n3 == 0) {
            this.reportUnexpectedNumberChar(n2 & 255, "Exponent indicator not followed by a digit");
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(n);
        this._expLength = n3;
        return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    protected JsonToken _finishFloatFraction() throws IOException {
        byte by;
        int n = this._fractLength;
        Object[] arrobject = this._textBuffer.getBufferWithoutReset();
        int n2 = this._textBuffer.getCurrentSegmentSize();
        do {
            Object[] arrobject2 = this._inputBuffer;
            int n3 = this._inputPtr;
            this._inputPtr = n3 + 1;
            by = arrobject2[n3];
            if (by < 48 || by > 57) break;
            ++n;
            arrobject2 = arrobject;
            if (n2 >= arrobject.length) {
                arrobject2 = this._textBuffer.expandCurrentSegment();
            }
            n3 = n2 + 1;
            arrobject2[n2] = (char)by;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(n3);
                this._fractLength = n;
                return JsonToken.NOT_AVAILABLE;
            }
            n2 = n3;
            arrobject = arrobject2;
        } while (true);
        if (n == 0) {
            this.reportUnexpectedNumberChar(by, "Decimal point not followed by a digit");
        }
        this._fractLength = n;
        this._textBuffer.setCurrentLength(n2);
        if (by != 101 && by != 69) {
            --this._inputPtr;
            this._textBuffer.setCurrentLength(n2);
            this._expLength = 0;
            return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
        }
        this._textBuffer.append((char)by);
        this._expLength = 0;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 31;
            return JsonToken.NOT_AVAILABLE;
        }
        this._minorState = 32;
        arrobject = this._inputBuffer;
        n2 = this._inputPtr;
        this._inputPtr = n2 + 1;
        return this._finishFloatExponent(true, arrobject[n2] & 255);
    }

    protected JsonToken _finishKeywordToken(String object, int n, JsonToken jsonToken) throws IOException {
        int n2 = ((String)object).length();
        do {
            block9 : {
                block8 : {
                    byte by;
                    block7 : {
                        if (this._inputPtr >= this._inputEnd) {
                            this._pending32 = n;
                            object = JsonToken.NOT_AVAILABLE;
                            this._currToken = object;
                            return object;
                        }
                        by = this._inputBuffer[this._inputPtr];
                        if (n != n2) break block7;
                        if (by < 48) return this._valueComplete(jsonToken);
                        if (by == 93) return this._valueComplete(jsonToken);
                        if (by == 125) {
                            return this._valueComplete(jsonToken);
                        }
                        break block8;
                    }
                    if (by == ((String)object).charAt(n)) break block9;
                }
                this._minorState = 50;
                this._textBuffer.resetWithCopy((String)object, 0, n);
                return this._finishErrorToken();
            }
            ++n;
            ++this._inputPtr;
        } while (true);
    }

    protected JsonToken _finishKeywordTokenWithEOF(String string2, int n, JsonToken jsonToken) throws IOException {
        if (n == string2.length()) {
            this._currToken = jsonToken;
            return jsonToken;
        }
        this._textBuffer.resetWithCopy(string2, 0, n);
        return this._finishErrorTokenWithEOF();
    }

    protected JsonToken _finishNonStdToken(int n, int n2) throws IOException {
        Object object = this._nonStdToken(n);
        int n3 = ((String)object).length();
        do {
            block9 : {
                block8 : {
                    byte by;
                    block7 : {
                        if (this._inputPtr >= this._inputEnd) {
                            this._nonStdTokenType = n;
                            this._pending32 = n2;
                            this._minorState = 19;
                            object = JsonToken.NOT_AVAILABLE;
                            this._currToken = object;
                            return object;
                        }
                        by = this._inputBuffer[this._inputPtr];
                        if (n2 != n3) break block7;
                        if (by < 48) return this._valueNonStdNumberComplete(n);
                        if (by == 93) return this._valueNonStdNumberComplete(n);
                        if (by == 125) {
                            return this._valueNonStdNumberComplete(n);
                        }
                        break block8;
                    }
                    if (by == ((String)object).charAt(n2)) break block9;
                }
                this._minorState = 50;
                this._textBuffer.resetWithCopy((String)object, 0, n2);
                return this._finishErrorToken();
            }
            ++n2;
            ++this._inputPtr;
        } while (true);
    }

    protected JsonToken _finishNonStdTokenWithEOF(int n, int n2) throws IOException {
        String string2 = this._nonStdToken(n);
        if (n2 == string2.length()) {
            return this._valueNonStdNumberComplete(n);
        }
        this._textBuffer.resetWithCopy(string2, 0, n2);
        return this._finishErrorTokenWithEOF();
    }

    protected JsonToken _finishNumberIntegralPart(char[] object, int n) throws IOException {
        char[] arrc;
        int n2;
        if (this._numberNegative) {
            n2 = -1;
            arrc = object;
        } else {
            n2 = 0;
            arrc = object;
        }
        do {
            int n3;
            block12 : {
                block13 : {
                    block11 : {
                        block10 : {
                            if (this._inputPtr >= this._inputEnd) {
                                this._minorState = 26;
                                this._textBuffer.setCurrentLength(n);
                                this._currToken = object = JsonToken.NOT_AVAILABLE;
                                return object;
                            }
                            n3 = this._inputBuffer[this._inputPtr] & 255;
                            if (n3 >= 48) break block10;
                            if (n3 == 46) {
                                this._intLength = n2 + n;
                                ++this._inputPtr;
                                return this._startFloat(arrc, n, n3);
                            }
                            break block11;
                        }
                        if (n3 <= 57) break block12;
                        if (n3 == 101 || n3 == 69) break block13;
                    }
                    this._intLength = n2 + n;
                    this._textBuffer.setCurrentLength(n);
                    return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
                }
                this._intLength = n2 + n;
                ++this._inputPtr;
                return this._startFloat(arrc, n, n3);
            }
            ++this._inputPtr;
            object = arrc;
            if (n >= arrc.length) {
                object = this._textBuffer.expandCurrentSegment();
            }
            object[n] = (char)n3;
            ++n;
            arrc = object;
        } while (true);
    }

    protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
        Object object;
        int n;
        do {
            block10 : {
                block11 : {
                    block9 : {
                        block8 : {
                            if (this._inputPtr >= this._inputEnd) {
                                this._minorState = 25;
                                this._currToken = object = JsonToken.NOT_AVAILABLE;
                                return object;
                            }
                            object = this._inputBuffer;
                            n = this._inputPtr;
                            this._inputPtr = n + 1;
                            if ((n = object[n] & 255) >= 48) break block8;
                            if (n == 46) {
                                object = this._textBuffer.emptyAndGetCurrentSegment();
                                object[0] = (char)45;
                                object[1] = (char)48;
                                this._intLength = 1;
                                return this._startFloat((char[])object, 2, n);
                            }
                            break block9;
                        }
                        if (n <= 57) break block10;
                        if (n == 101 || n == 69) break block11;
                        if (n != 93 && n != 125) {
                            this.reportUnexpectedNumberChar(n, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                        }
                    }
                    --this._inputPtr;
                    return this._valueCompleteInt(0, "0");
                }
                object = this._textBuffer.emptyAndGetCurrentSegment();
                object[0] = (char)45;
                object[1] = (char)48;
                this._intLength = 1;
                return this._startFloat((char[])object, 2, n);
            }
            if ((this._features & FEAT_MASK_LEADING_ZEROS) != 0) continue;
            this.reportInvalidNumber("Leading zeroes not allowed");
        } while (n == 48);
        object = this._textBuffer.emptyAndGetCurrentSegment();
        object[0] = (char)45;
        object[1] = (char)n;
        this._intLength = 1;
        return this._finishNumberIntegralPart((char[])object, 2);
    }

    protected JsonToken _finishNumberLeadingZeroes() throws IOException {
        Object object;
        int n;
        do {
            block10 : {
                block11 : {
                    block9 : {
                        block8 : {
                            if (this._inputPtr >= this._inputEnd) {
                                this._minorState = 24;
                                this._currToken = object = JsonToken.NOT_AVAILABLE;
                                return object;
                            }
                            object = this._inputBuffer;
                            n = this._inputPtr;
                            this._inputPtr = n + 1;
                            if ((n = object[n] & 255) >= 48) break block8;
                            if (n == 46) {
                                object = this._textBuffer.emptyAndGetCurrentSegment();
                                object[0] = (char)48;
                                this._intLength = 1;
                                return this._startFloat((char[])object, 1, n);
                            }
                            break block9;
                        }
                        if (n <= 57) break block10;
                        if (n == 101 || n == 69) break block11;
                        if (n != 93 && n != 125) {
                            this.reportUnexpectedNumberChar(n, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                        }
                    }
                    --this._inputPtr;
                    return this._valueCompleteInt(0, "0");
                }
                object = this._textBuffer.emptyAndGetCurrentSegment();
                object[0] = (char)48;
                this._intLength = 1;
                return this._startFloat((char[])object, 1, n);
            }
            if ((this._features & FEAT_MASK_LEADING_ZEROS) != 0) continue;
            this.reportInvalidNumber("Leading zeroes not allowed");
        } while (n == 48);
        object = this._textBuffer.emptyAndGetCurrentSegment();
        object[0] = (char)n;
        this._intLength = 1;
        return this._finishNumberIntegralPart((char[])object, 1);
    }

    protected JsonToken _finishNumberMinus(int n) throws IOException {
        if (n <= 48) {
            if (n == 48) {
                return this._finishNumberLeadingNegZeroes();
            }
            this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        } else if (n > 57) {
            if (n == 73) {
                return this._finishNonStdToken(3, 2);
            }
            this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        arrc[0] = (char)45;
        arrc[1] = (char)n;
        this._intLength = 1;
        return this._finishNumberIntegralPart(arrc, 2);
    }

    protected final JsonToken _finishToken() throws IOException {
        int n = this._minorState;
        if (n == 1) return this._finishBOM(this._pending32);
        if (n == 4) {
            byte[] arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            return this._startFieldName(arrby[n] & 255);
        }
        if (n == 5) {
            byte[] arrby = this._inputBuffer;
            n = this._inputPtr;
            this._inputPtr = n + 1;
            return this._startFieldNameAfterComma(arrby[n] & 255);
        }
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        switch (n) {
                                            default: {
                                                switch (n) {
                                                    default: {
                                                        VersionUtil.throwInternal();
                                                        return null;
                                                    }
                                                    case 55: {
                                                        return this._finishHashComment(this._pending32);
                                                    }
                                                    case 54: {
                                                        return this._finishCppComment(this._pending32);
                                                    }
                                                    case 53: {
                                                        return this._finishCComment(this._pending32, false);
                                                    }
                                                    case 52: {
                                                        return this._finishCComment(this._pending32, true);
                                                    }
                                                    case 51: {
                                                        return this._startSlashComment(this._pending32);
                                                    }
                                                    case 50: 
                                                }
                                                return this._finishErrorToken();
                                            }
                                            case 45: {
                                                return this._finishAposString();
                                            }
                                            case 44: {
                                                n = this._pending32;
                                                int n2 = this._pendingBytes;
                                                byte[] arrby = this._inputBuffer;
                                                int n3 = this._inputPtr;
                                                this._inputPtr = n3 + 1;
                                                if (!this._decodeSplitUTF8_4(n, n2, arrby[n3])) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                if (this._minorStateAfterSplit != 45) return this._finishRegularString();
                                                return this._finishAposString();
                                            }
                                            case 43: {
                                                int n4 = this._pending32;
                                                int n5 = this._pendingBytes;
                                                byte[] arrby = this._inputBuffer;
                                                n = this._inputPtr;
                                                this._inputPtr = n + 1;
                                                if (!this._decodeSplitUTF8_3(n4, n5, arrby[n])) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                if (this._minorStateAfterSplit != 45) return this._finishRegularString();
                                                return this._finishAposString();
                                            }
                                            case 42: {
                                                TextBuffer textBuffer = this._textBuffer;
                                                n = this._pending32;
                                                byte[] arrby = this._inputBuffer;
                                                int n6 = this._inputPtr;
                                                this._inputPtr = n6 + 1;
                                                textBuffer.append((char)this._decodeUTF8_2(n, arrby[n6]));
                                                if (this._minorStateAfterSplit != 45) return this._finishRegularString();
                                                return this._finishAposString();
                                            }
                                            case 41: {
                                                n = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
                                                if (n < 0) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                this._textBuffer.append((char)n);
                                                if (this._minorStateAfterSplit != 45) return this._finishRegularString();
                                                return this._finishAposString();
                                            }
                                            case 40: 
                                        }
                                        return this._finishRegularString();
                                    }
                                    case 32: {
                                        byte[] arrby = this._inputBuffer;
                                        n = this._inputPtr;
                                        this._inputPtr = n + 1;
                                        return this._finishFloatExponent(false, arrby[n] & 255);
                                    }
                                    case 31: {
                                        byte[] arrby = this._inputBuffer;
                                        n = this._inputPtr;
                                        this._inputPtr = n + 1;
                                        return this._finishFloatExponent(true, arrby[n] & 255);
                                    }
                                    case 30: 
                                }
                                return this._finishFloatFraction();
                            }
                            case 26: {
                                return this._finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
                            }
                            case 25: {
                                return this._finishNumberLeadingNegZeroes();
                            }
                            case 24: {
                                return this._finishNumberLeadingZeroes();
                            }
                            case 23: 
                        }
                        byte[] arrby = this._inputBuffer;
                        n = this._inputPtr;
                        this._inputPtr = n + 1;
                        return this._finishNumberMinus(arrby[n] & 255);
                    }
                    case 19: {
                        return this._finishNonStdToken(this._nonStdTokenType, this._pending32);
                    }
                    case 18: {
                        return this._finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
                    }
                    case 17: {
                        return this._finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
                    }
                    case 16: {
                        return this._finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
                    }
                    case 15: {
                        byte[] arrby = this._inputBuffer;
                        n = this._inputPtr;
                        this._inputPtr = n + 1;
                        return this._startValueAfterComma(arrby[n] & 255);
                    }
                    case 14: {
                        byte[] arrby = this._inputBuffer;
                        n = this._inputPtr;
                        this._inputPtr = n + 1;
                        return this._startValueExpectColon(arrby[n] & 255);
                    }
                    case 13: {
                        byte[] arrby = this._inputBuffer;
                        n = this._inputPtr;
                        this._inputPtr = n + 1;
                        return this._startValueExpectComma(arrby[n] & 255);
                    }
                    case 12: 
                }
                byte[] arrby = this._inputBuffer;
                n = this._inputPtr;
                this._inputPtr = n + 1;
                return this._startValue(arrby[n] & 255);
            }
            case 10: {
                return this._finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
            }
            case 9: {
                return this._finishAposName(this._quadLength, this._pending32, this._pendingBytes);
            }
            case 8: {
                return this._finishFieldWithEscape();
            }
            case 7: 
        }
        return this._parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
    }

    /*
     * Exception decompiling
     */
    protected final JsonToken _finishTokenWithEOF() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [11[CASE]], but top level block is 15[SWITCH]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    protected JsonToken _reportErrorToken(String string2) throws IOException {
        this._reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), this._validJsonTokenList());
        return JsonToken.NOT_AVAILABLE;
    }

    protected JsonToken _startAposString() throws IOException {
        int n;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n2 = Math.min(this._inputEnd, arrc.length + n);
        byte[] arrby = this._inputBuffer;
        int n3 = 0;
        for (n = this._inputPtr; n < n2; ++n, ++n3) {
            int n4 = arrby[n] & 255;
            if (n4 == 39) {
                this._inputPtr = n + 1;
                this._textBuffer.setCurrentLength(n3);
                return this._valueComplete(JsonToken.VALUE_STRING);
            }
            if (arrn[n4] != 0) break;
            arrc[n3] = (char)n4;
        }
        this._textBuffer.setCurrentLength(n3);
        this._inputPtr = n;
        return this._finishAposString();
    }

    protected JsonToken _startFalseToken() throws IOException {
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
                            return this._valueComplete(JsonToken.VALUE_FALSE);
                        }
                    }
                }
            }
        }
        this._minorState = 18;
        return this._finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
    }

    /*
     * Unable to fully structure code
     */
    protected JsonToken _startFloat(char[] var1_1, int var2_2, int var3_3) throws IOException {
        block16 : {
            block17 : {
                block15 : {
                    var4_22 = 0;
                    var5_23 = 0;
                    var6_24 = 0;
                    if (var3_21 != 46) {
                        var11_50 = 0;
                        var7_35 /* !! */  = var1_1 /* !! */ ;
                        var10_49 = var3_21;
                    } else {
                        var7_25 /* !! */  = var1_1 /* !! */ ;
                        if (var2_20 >= var1_1 /* !! */ .length) {
                            var7_26 = this._textBuffer.expandCurrentSegment();
                        }
                        var7_27[var2_20] = (char)46;
                        var3_21 = var2_20 + true;
                        var8_47 = 0;
                        var1_2 = var7_27;
                        do {
                            if (this._inputPtr >= this._inputEnd) {
                                this._textBuffer.setCurrentLength(var3_21);
                                this._minorState = 30;
                                this._fractLength = var8_47;
                                this._currToken = var1_4 = JsonToken.NOT_AVAILABLE;
                                return var1_4;
                            }
                            var7_29 = this._inputBuffer;
                            var2_20 = this._inputPtr;
                            this._inputPtr = var2_20 + 1;
                            if ((var2_20 = var7_29[var2_20]) < 48 || var2_20 > 57) break;
                            var7_30 = var1_3;
                            if (var3_21 >= ((void)var1_3).length) {
                                var7_31 = this._textBuffer.expandCurrentSegment();
                            }
                            var7_32[var3_21] = (char)var2_20;
                            ++var8_47;
                            ++var3_21;
                            var1_5 = var7_32;
                        } while (true);
                        var10_49 = var9_48 = var2_20 & 255;
                        var7_33 = var1_3;
                        var2_20 = var3_21;
                        var11_50 = var8_47;
                        if (var8_47 == 0) {
                            this.reportUnexpectedNumberChar(var9_48, "Decimal point not followed by a digit");
                            var10_49 = var9_48;
                            var7_34 = var1_3;
                            var2_20 = var3_21;
                            var11_50 = var8_47;
                        }
                    }
                    this._fractLength = var11_50;
                    if (var10_49 == 101) break block15;
                    var3_21 = var5_23;
                    var8_47 = var2_20;
                    if (var10_49 != 69) break block16;
                }
                var1_7 = var7_36;
                if (var2_20 >= ((void)var7_36).length) {
                    var1_8 = this._textBuffer.expandCurrentSegment();
                }
                var11_50 = var2_20 + true;
                var1_9[var2_20] = (char)var10_49;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(var11_50);
                    this._minorState = 31;
                    this._expLength = 0;
                    this._currToken = var1_10 = JsonToken.NOT_AVAILABLE;
                    return var1_10;
                }
                var7_37 = this._inputBuffer;
                var2_20 = this._inputPtr;
                this._inputPtr = var2_20 + 1;
                var10_49 = var7_37[var2_20];
                if (var10_49 == 45) break block17;
                var3_21 = var4_22;
                var7_38 = var1_9;
                var8_47 = var10_49;
                var2_20 = var11_50;
                if (var10_49 != 43) ** GOTO lbl92
            }
            var7_40 = var1_9;
            if (var11_50 >= ((void)var1_9).length) {
                var7_41 = this._textBuffer.expandCurrentSegment();
            }
            var2_20 = var11_50 + 1;
            var7_42[var11_50] = (char)var10_49;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(var2_20);
                this._minorState = 32;
                this._expLength = 0;
                this._currToken = var1_11 = JsonToken.NOT_AVAILABLE;
                return var1_11;
            }
            var1_12 = this._inputBuffer;
            var3_21 = this._inputPtr;
            this._inputPtr = var3_21 + 1;
            var8_47 = var1_12[var3_21];
            var1_13 = var7_42;
            var3_21 = var6_24;
            do {
                var7_44 = var1_14;
lbl92: // 2 sources:
                if (var8_47 < 48 || var8_47 > 57) break;
                ++var3_21;
                var1_16 = var7_45;
                if (var2_20 >= ((void)var7_45).length) {
                    var1_17 = this._textBuffer.expandCurrentSegment();
                }
                var10_49 = var2_20 + 1;
                var1_18[var2_20] = (char)var8_47;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(var10_49);
                    this._minorState = 32;
                    this._expLength = var3_21;
                    this._currToken = var1_19 = JsonToken.NOT_AVAILABLE;
                    return var1_19;
                }
                var7_46 = this._inputBuffer;
                var2_20 = this._inputPtr;
                this._inputPtr = var2_20 + 1;
                var8_47 = var7_46[var2_20];
                var2_20 = var10_49;
            } while (true);
            if (var3_21 == 0) {
                this.reportUnexpectedNumberChar(var8_47 & 255, "Exponent indicator not followed by a digit");
            }
            var8_47 = var2_20;
        }
        --this._inputPtr;
        this._textBuffer.setCurrentLength(var8_47);
        this._expLength = var3_21;
        return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    protected JsonToken _startNegativeNumber() throws IOException {
        this._numberNegative = true;
        if (this._inputPtr >= this._inputEnd) {
            JsonToken jsonToken;
            this._minorState = 23;
            this._currToken = jsonToken = JsonToken.NOT_AVAILABLE;
            return jsonToken;
        }
        Object object = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        n = object[n] & 255;
        int n2 = 2;
        if (n <= 48) {
            if (n == 48) {
                return this._finishNumberLeadingNegZeroes();
            }
            this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        } else if (n > 57) {
            if (n == 73) {
                return this._finishNonStdToken(3, 2);
            }
            this.reportUnexpectedNumberChar(n, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        object = this._textBuffer.emptyAndGetCurrentSegment();
        object[0] = (char)45;
        object[1] = (char)n;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(2);
            this._intLength = 1;
            this._currToken = object = JsonToken.NOT_AVAILABLE;
            return object;
        }
        n = this._inputBuffer[this._inputPtr];
        do {
            if (n < 48) {
                if (n != 46) break;
                this._intLength = n2 - 1;
                ++this._inputPtr;
                return this._startFloat((char[])object, n2, n);
            }
            if (n > 57) {
                if (n != 101 && n != 69) break;
                this._intLength = n2 - 1;
                ++this._inputPtr;
                return this._startFloat((char[])object, n2, n);
            }
            Object object2 = object;
            if (n2 >= ((byte[])object).length) {
                object2 = this._textBuffer.expandCurrentSegment();
            }
            int n3 = n2 + 1;
            object2[n2] = (char)n;
            this._inputPtr = n = this._inputPtr + 1;
            if (n >= this._inputEnd) {
                this._minorState = 26;
                this._textBuffer.setCurrentLength(n3);
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            n = this._inputBuffer[this._inputPtr] & 255;
            n2 = n3;
            object = object2;
        } while (true);
        this._intLength = n2 - 1;
        this._textBuffer.setCurrentLength(n2);
        return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
    }

    protected JsonToken _startNullToken() throws IOException {
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
                        return this._valueComplete(JsonToken.VALUE_NULL);
                    }
                }
            }
        }
        this._minorState = 16;
        return this._finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
    }

    protected JsonToken _startNumberLeadingZero() throws IOException {
        int n = this._inputPtr;
        if (n >= this._inputEnd) {
            JsonToken jsonToken;
            this._minorState = 24;
            this._currToken = jsonToken = JsonToken.NOT_AVAILABLE;
            return jsonToken;
        }
        Object[] arrobject = this._inputBuffer;
        int n2 = n + 1;
        if ((n = arrobject[n] & 255) < 48) {
            if (n != 46) return this._valueCompleteInt(0, "0");
            this._inputPtr = n2;
            this._intLength = 1;
            arrobject = this._textBuffer.emptyAndGetCurrentSegment();
            arrobject[0] = (char)48;
            return this._startFloat((char[])arrobject, 1, n);
        }
        if (n <= 57) return this._finishNumberLeadingZeroes();
        if (n != 101 && n != 69) {
            if (n == 93) return this._valueCompleteInt(0, "0");
            if (n == 125) return this._valueCompleteInt(0, "0");
            this.reportUnexpectedNumberChar(n, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            return this._valueCompleteInt(0, "0");
        }
        this._inputPtr = n2;
        this._intLength = 1;
        arrobject = this._textBuffer.emptyAndGetCurrentSegment();
        arrobject[0] = (char)48;
        return this._startFloat((char[])arrobject, 1, n);
    }

    protected JsonToken _startPositiveNumber(int n) throws IOException {
        this._numberNegative = false;
        Object object = this._textBuffer.emptyAndGetCurrentSegment();
        object[0] = (char)n;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(1);
            this._currToken = object = JsonToken.NOT_AVAILABLE;
            return object;
        }
        int n2 = this._inputBuffer[this._inputPtr] & 255;
        n = 1;
        do {
            if (n2 < 48) {
                if (n2 != 46) break;
                this._intLength = n;
                ++this._inputPtr;
                return this._startFloat((char[])object, n, n2);
            }
            if (n2 > 57) {
                if (n2 != 101 && n2 != 69) break;
                this._intLength = n;
                ++this._inputPtr;
                return this._startFloat((char[])object, n, n2);
            }
            Object object2 = object;
            if (n >= ((char[])object).length) {
                object2 = this._textBuffer.expandCurrentSegment();
            }
            int n3 = n + 1;
            object2[n] = (char)n2;
            this._inputPtr = n = this._inputPtr + 1;
            if (n >= this._inputEnd) {
                this._minorState = 26;
                this._textBuffer.setCurrentLength(n3);
                this._currToken = object = JsonToken.NOT_AVAILABLE;
                return object;
            }
            n2 = this._inputBuffer[this._inputPtr] & 255;
            n = n3;
            object = object2;
        } while (true);
        this._intLength = n;
        this._textBuffer.setCurrentLength(n);
        return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
    }

    protected JsonToken _startString() throws IOException {
        int n;
        char[] arrc = this._textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = _icUTF8;
        int n2 = Math.min(this._inputEnd, arrc.length + n);
        byte[] arrby = this._inputBuffer;
        int n3 = 0;
        for (n = this._inputPtr; n < n2; ++n, ++n3) {
            int n4 = arrby[n] & 255;
            if (arrn[n4] != 0) {
                if (n4 != 34) break;
                this._inputPtr = n + 1;
                this._textBuffer.setCurrentLength(n3);
                return this._valueComplete(JsonToken.VALUE_STRING);
            }
            arrc[n3] = (char)n4;
        }
        this._textBuffer.setCurrentLength(n3);
        this._inputPtr = n;
        return this._finishRegularString();
    }

    protected JsonToken _startTrueToken() throws IOException {
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
                        return this._valueComplete(JsonToken.VALUE_TRUE);
                    }
                }
            }
        }
        this._minorState = 17;
        return this._finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
    }

    protected JsonToken _startUnexpectedValue(boolean bl, int n) throws IOException {
        block6 : {
            block4 : {
                block7 : {
                    block5 : {
                        if (n == 39) break block4;
                        if (n == 73) return this._finishNonStdToken(1, 1);
                        if (n == 78) return this._finishNonStdToken(0, 1);
                        if (n == 93) break block5;
                        if (n == 125) break block6;
                        if (n == 43) return this._finishNonStdToken(2, 1);
                        if (n == 44) break block7;
                        break block6;
                    }
                    if (!this._parsingContext.inArray()) break block6;
                }
                if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                    --this._inputPtr;
                    return this._valueComplete(JsonToken.VALUE_NULL);
                }
                break block6;
            }
            if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
                return this._startAposString();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected a valid value ");
        stringBuilder.append(this._validJsonValueList());
        this._reportUnexpectedChar(n, stringBuilder.toString());
        return null;
    }

    @Override
    public void endOfInput() {
        this._endOfInput = true;
    }

    @Override
    public void feedInput(byte[] arrby, int n, int n2) throws IOException {
        if (this._inputPtr < this._inputEnd) {
            this._reportError("Still have %d undecoded bytes, should not call 'feedInput'", this._inputEnd - this._inputPtr);
        }
        if (n2 < n) {
            this._reportError("Input end (%d) may not be before start (%d)", n2, n);
        }
        if (this._endOfInput) {
            this._reportError("Already closed, can not feed more input");
        }
        this._currInputProcessed += (long)this._origBufferLen;
        this._currInputRowStart = n - (this._inputEnd - this._currInputRowStart);
        this._currBufferStart = n;
        this._inputBuffer = arrby;
        this._inputPtr = n;
        this._inputEnd = n2;
        this._origBufferLen = n2 - n;
    }

    @Override
    public ByteArrayFeeder getNonBlockingInputFeeder() {
        return this;
    }

    @Override
    public final boolean needMoreInput() {
        if (this._inputPtr < this._inputEnd) return false;
        if (this._endOfInput) return false;
        return true;
    }

    @Override
    public JsonToken nextToken() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            if (this._closed) {
                return null;
            }
            if (!this._endOfInput) return JsonToken.NOT_AVAILABLE;
            if (this._currToken != JsonToken.NOT_AVAILABLE) return this._eofAsNextToken();
            return this._finishTokenWithEOF();
        }
        if (this._currToken == JsonToken.NOT_AVAILABLE) {
            return this._finishToken();
        }
        this._numTypesValid = 0;
        this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr;
        this._binaryValue = null;
        byte[] arrby = this._inputBuffer;
        int n = this._inputPtr;
        this._inputPtr = n + 1;
        n = arrby[n] & 255;
        switch (this._majorState) {
            default: {
                VersionUtil.throwInternal();
                return null;
            }
            case 6: {
                return this._startValueExpectComma(n);
            }
            case 5: {
                return this._startValue(n);
            }
            case 4: {
                return this._startValueExpectColon(n);
            }
            case 3: {
                return this._startFieldNameAfterComma(n);
            }
            case 2: {
                return this._startFieldName(n);
            }
            case 1: {
                return this._startValue(n);
            }
            case 0: 
        }
        return this._startDocument(n);
    }

    @Override
    public int releaseBuffered(OutputStream outputStream2) throws IOException {
        int n = this._inputEnd - this._inputPtr;
        if (n <= 0) return n;
        outputStream2.write(this._inputBuffer, this._inputPtr, n);
        return n;
    }
}

