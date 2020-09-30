/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public abstract class NonBlockingJsonParserBase
extends ParserBase {
    protected static final int MAJOR_ARRAY_ELEMENT_FIRST = 5;
    protected static final int MAJOR_ARRAY_ELEMENT_NEXT = 6;
    protected static final int MAJOR_CLOSED = 7;
    protected static final int MAJOR_INITIAL = 0;
    protected static final int MAJOR_OBJECT_FIELD_FIRST = 2;
    protected static final int MAJOR_OBJECT_FIELD_NEXT = 3;
    protected static final int MAJOR_OBJECT_VALUE = 4;
    protected static final int MAJOR_ROOT = 1;
    protected static final int MINOR_COMMENT_C = 53;
    protected static final int MINOR_COMMENT_CLOSING_ASTERISK = 52;
    protected static final int MINOR_COMMENT_CPP = 54;
    protected static final int MINOR_COMMENT_LEADING_SLASH = 51;
    protected static final int MINOR_COMMENT_YAML = 55;
    protected static final int MINOR_FIELD_APOS_NAME = 9;
    protected static final int MINOR_FIELD_LEADING_COMMA = 5;
    protected static final int MINOR_FIELD_LEADING_WS = 4;
    protected static final int MINOR_FIELD_NAME = 7;
    protected static final int MINOR_FIELD_NAME_ESCAPE = 8;
    protected static final int MINOR_FIELD_UNQUOTED_NAME = 10;
    protected static final int MINOR_NUMBER_EXPONENT_DIGITS = 32;
    protected static final int MINOR_NUMBER_EXPONENT_MARKER = 31;
    protected static final int MINOR_NUMBER_FRACTION_DIGITS = 30;
    protected static final int MINOR_NUMBER_INTEGER_DIGITS = 26;
    protected static final int MINOR_NUMBER_MINUS = 23;
    protected static final int MINOR_NUMBER_MINUSZERO = 25;
    protected static final int MINOR_NUMBER_ZERO = 24;
    protected static final int MINOR_ROOT_BOM = 1;
    protected static final int MINOR_ROOT_GOT_SEPARATOR = 3;
    protected static final int MINOR_ROOT_NEED_SEPARATOR = 2;
    protected static final int MINOR_VALUE_APOS_STRING = 45;
    protected static final int MINOR_VALUE_EXPECTING_COLON = 14;
    protected static final int MINOR_VALUE_EXPECTING_COMMA = 13;
    protected static final int MINOR_VALUE_LEADING_WS = 12;
    protected static final int MINOR_VALUE_STRING = 40;
    protected static final int MINOR_VALUE_STRING_ESCAPE = 41;
    protected static final int MINOR_VALUE_STRING_UTF8_2 = 42;
    protected static final int MINOR_VALUE_STRING_UTF8_3 = 43;
    protected static final int MINOR_VALUE_STRING_UTF8_4 = 44;
    protected static final int MINOR_VALUE_TOKEN_ERROR = 50;
    protected static final int MINOR_VALUE_TOKEN_FALSE = 18;
    protected static final int MINOR_VALUE_TOKEN_NON_STD = 19;
    protected static final int MINOR_VALUE_TOKEN_NULL = 16;
    protected static final int MINOR_VALUE_TOKEN_TRUE = 17;
    protected static final int MINOR_VALUE_WS_AFTER_COMMA = 15;
    protected static final String[] NON_STD_TOKENS = new String[]{"NaN", "Infinity", "+Infinity", "-Infinity"};
    protected static final int NON_STD_TOKEN_INFINITY = 1;
    protected static final int NON_STD_TOKEN_MINUS_INFINITY = 3;
    protected static final int NON_STD_TOKEN_NAN = 0;
    protected static final int NON_STD_TOKEN_PLUS_INFINITY = 2;
    protected static final double[] NON_STD_TOKEN_VALUES = new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
    protected int _currBufferStart = 0;
    protected int _currInputRowAlt = 1;
    protected boolean _endOfInput = false;
    protected int _majorState;
    protected int _majorStateAfterValue;
    protected int _minorState;
    protected int _minorStateAfterSplit;
    protected int _nonStdTokenType;
    protected int _pending32;
    protected int _pendingBytes;
    protected int _quad1;
    protected int[] _quadBuffer = new int[8];
    protected int _quadLength;
    protected int _quoted32;
    protected int _quotedDigits;
    protected final ByteQuadsCanonicalizer _symbols;

    public NonBlockingJsonParserBase(IOContext iOContext, int n, ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
        super(iOContext, n);
        this._symbols = byteQuadsCanonicalizer;
        this._currToken = null;
        this._majorState = 0;
        this._majorStateAfterValue = 1;
    }

    protected static final int _padLastQuad(int n, int n2) {
        if (n2 == 4) {
            return n;
        }
        n |= -1 << (n2 << 3);
        return n;
    }

    /*
     * Unable to fully structure code
     */
    protected final String _addName(int[] var1_1, int var2_2, int var3_3) throws JsonParseException {
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
                var8_13 = var1_1[var10_15 >> 2] >> (3 - (var10_15 & 3) << 3);
                var13_23 = var10_15 + 1;
                if ((var8_13 & 192) != 128) {
                    this._reportInvalidOther(var8_13);
                }
                var10_15 = var8_13 & 63 | var14_24 << 6;
                if (var9_14 > 1) {
                    var14_24 = var1_1[var13_23 >> 2] >> (3 - (var13_23 & 3) << 3);
                    var8_13 = var13_23 + 1;
                    if ((var14_24 & 192) != 128) {
                        this._reportInvalidOther(var14_24);
                    }
                    var13_23 = var10_15 = var14_24 & 63 | var10_15 << 6;
                    var14_24 = var8_13;
                    if (var9_14 > 2) {
                        var13_23 = var1_1[var8_13 >> 2] >> (3 - (var8_13 & 3) << 3);
                        var14_24 = var8_13 + 1;
                        if ((var13_23 & 192) != 128) {
                            this._reportInvalidOther(var13_23 & 255);
                        }
                        var13_23 = var10_15 << 6 | var13_23 & 63;
                    }
                    var10_15 = var13_23;
                } else {
                    var14_24 = var13_23;
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

    protected final JsonToken _closeArrayScope() throws IOException {
        if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(93, '}');
        }
        Object object = this._parsingContext.getParent();
        this._parsingContext = object;
        int n = object.inObject() ? 3 : (object.inArray() ? 6 : 1);
        this._majorState = n;
        this._majorStateAfterValue = n;
        object = JsonToken.END_ARRAY;
        this._currToken = object;
        return object;
    }

    @Override
    protected void _closeInput() throws IOException {
        this._currBufferStart = 0;
        this._inputEnd = 0;
    }

    protected final JsonToken _closeObjectScope() throws IOException {
        if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(125, ']');
        }
        Object object = this._parsingContext.getParent();
        this._parsingContext = object;
        int n = object.inObject() ? 3 : (object.inArray() ? 6 : 1);
        this._majorState = n;
        this._majorStateAfterValue = n;
        object = JsonToken.END_OBJECT;
        this._currToken = object;
        return object;
    }

    protected final JsonToken _eofAsNextToken() throws IOException {
        this._majorState = 7;
        if (!this._parsingContext.inRoot()) {
            this._handleEOF();
        }
        this.close();
        this._currToken = null;
        return null;
    }

    protected final JsonToken _fieldComplete(String object) throws IOException {
        this._majorState = 4;
        this._parsingContext.setCurrentName((String)object);
        object = JsonToken.FIELD_NAME;
        this._currToken = object;
        return object;
    }

    protected final String _findName(int n, int n2) throws JsonParseException {
        int[] arrn = this._symbols.findName(n = NonBlockingJsonParserBase._padLastQuad(n, n2));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        return this._addName(arrn, 1, n2);
    }

    protected final String _findName(int n, int n2, int n3) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2 = NonBlockingJsonParserBase._padLastQuad(n2, n3));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        return this._addName(arrn, 2, n3);
    }

    protected final String _findName(int n, int n2, int n3, int n4) throws JsonParseException {
        int[] arrn = this._symbols.findName(n, n2, n3 = NonBlockingJsonParserBase._padLastQuad(n3, n4));
        if (arrn != null) {
            return arrn;
        }
        arrn = this._quadBuffer;
        arrn[0] = n;
        arrn[1] = n2;
        arrn[2] = NonBlockingJsonParserBase._padLastQuad(n3, n4);
        return this._addName(arrn, 3, n4);
    }

    protected final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        int n = jsonToken.id();
        if (n == -1) return null;
        if (n == 5) return this._parsingContext.getCurrentName();
        if (n == 6) return this._textBuffer.contentsAsString();
        if (n == 7) return this._textBuffer.contentsAsString();
        if (n == 8) return this._textBuffer.contentsAsString();
        return jsonToken.asString();
    }

    protected final String _nonStdToken(int n) {
        return NON_STD_TOKENS[n];
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

    protected final JsonToken _startArrayScope() throws IOException {
        JsonToken jsonToken;
        this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
        this._majorState = 5;
        this._majorStateAfterValue = 6;
        this._currToken = jsonToken = JsonToken.START_ARRAY;
        return jsonToken;
    }

    protected final JsonToken _startObjectScope() throws IOException {
        JsonToken jsonToken;
        this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
        this._majorState = 2;
        this._majorStateAfterValue = 3;
        this._currToken = jsonToken = JsonToken.START_OBJECT;
        return jsonToken;
    }

    protected final void _updateTokenLocation() {
        this._tokenInputRow = Math.max(this._currInputRow, this._currInputRowAlt);
        int n = this._inputPtr;
        this._tokenInputCol = n - this._currInputRowStart;
        this._tokenInputTotal = this._currInputProcessed + (long)(n - this._currBufferStart);
    }

    protected final JsonToken _valueComplete(JsonToken jsonToken) throws IOException {
        this._majorState = this._majorStateAfterValue;
        this._currToken = jsonToken;
        return jsonToken;
    }

    protected final JsonToken _valueCompleteInt(int n, String object) throws IOException {
        this._textBuffer.resetWithString((String)object);
        this._intLength = object.length();
        this._numTypesValid = 1;
        this._numberInt = n;
        this._majorState = this._majorStateAfterValue;
        object = JsonToken.VALUE_NUMBER_INT;
        this._currToken = object;
        return object;
    }

    protected final JsonToken _valueNonStdNumberComplete(int n) throws IOException {
        Object object = NON_STD_TOKENS[n];
        this._textBuffer.resetWithString((String)object);
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
            this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", object);
        }
        this._intLength = 0;
        this._numTypesValid = 8;
        this._numberDouble = NON_STD_TOKEN_VALUES[n];
        this._majorState = this._majorStateAfterValue;
        object = JsonToken.VALUE_NUMBER_FLOAT;
        this._currToken = object;
        return object;
    }

    @Override
    public boolean canParseAsync() {
        return true;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (%s) not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary", (Object)this._currToken);
        }
        if (this._binaryValue != null) return this._binaryValue;
        ByteArrayBuilder byteArrayBuilder = this._getByteArrayBuilder();
        this._decodeBase64(this.getText(), byteArrayBuilder, base64Variant);
        this._binaryValue = byteArrayBuilder.toByteArray();
        return this._binaryValue;
    }

    @Override
    public ObjectCodec getCodec() {
        return null;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        int n = this._inputPtr;
        int n2 = this._currInputRowStart;
        int n3 = Math.max(this._currInputRow, this._currInputRowAlt);
        return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (long)(this._inputPtr - this._currBufferStart), -1L, n3, n - n2 + 1);
    }

    @Override
    public Object getEmbeddedObject() throws IOException {
        if (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT) return null;
        return this._binaryValue;
    }

    @Override
    public Object getInputSource() {
        return null;
    }

    @Override
    public int getText(Writer writer) throws IOException {
        Object object = this._currToken;
        if (object == JsonToken.VALUE_STRING) {
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
        if (object == JsonToken.NOT_AVAILABLE) {
            this._reportError("Current token not available: can not call this method");
        }
        object = object.asCharArray();
        writer.write((char[])object);
        return ((Object)object).length;
    }

    @Override
    public String getText() throws IOException {
        if (this._currToken != JsonToken.VALUE_STRING) return this._getText2(this._currToken);
        return this._textBuffer.contentsAsString();
    }

    @Override
    public char[] getTextCharacters() throws IOException {
        if (this._currToken == null) return null;
        int n = this._currToken.id();
        if (n != 5) {
            if (n == 6) return this._textBuffer.getTextBuffer();
            if (n == 7) return this._textBuffer.getTextBuffer();
            if (n == 8) return this._textBuffer.getTextBuffer();
            return this._currToken.asCharArray();
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
        if (n == 6) return this._textBuffer.size();
        if (n == 7) return this._textBuffer.size();
        if (n == 8) return this._textBuffer.size();
        return this._currToken.asCharArray().length;
    }

    @Override
    public int getTextOffset() throws IOException {
        if (this._currToken == null) return 0;
        int n = this._currToken.id();
        if (n == 6) return this._textBuffer.getTextOffset();
        if (n == 7) return this._textBuffer.getTextOffset();
        if (n == 8) return this._textBuffer.getTextOffset();
        return 0;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._getSourceReference(), this._tokenInputTotal, -1L, this._tokenInputRow, this._tokenInputCol);
    }

    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(null);
        return this.getCurrentName();
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return super.getValueAsString(string2);
        return this.getCurrentName();
    }

    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this._textBuffer.hasTextAsCharacters();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return false;
        return this._nameCopied;
    }

    @Override
    public int readBinaryValue(Base64Variant arrby, OutputStream outputStream2) throws IOException {
        arrby = this.getBinaryValue((Base64Variant)arrby);
        outputStream2.write(arrby);
        return arrby.length;
    }

    @Override
    public abstract int releaseBuffered(OutputStream var1) throws IOException;

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        throw new UnsupportedOperationException("Can not use ObjectMapper with non-blocking parser");
    }

    protected ByteQuadsCanonicalizer symbolTableForTests() {
        return this._symbols;
    }
}

