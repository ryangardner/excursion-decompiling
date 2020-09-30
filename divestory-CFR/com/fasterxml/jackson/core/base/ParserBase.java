/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public abstract class ParserBase
extends ParserMinimalBase {
    protected byte[] _binaryValue;
    protected ByteArrayBuilder _byteArrayBuilder;
    protected boolean _closed;
    protected long _currInputProcessed;
    protected int _currInputRow = 1;
    protected int _currInputRowStart;
    protected int _expLength;
    protected int _fractLength;
    protected int _inputEnd;
    protected int _inputPtr;
    protected int _intLength;
    protected final IOContext _ioContext;
    protected boolean _nameCopied;
    protected char[] _nameCopyBuffer;
    protected JsonToken _nextToken;
    protected int _numTypesValid = 0;
    protected BigDecimal _numberBigDecimal;
    protected BigInteger _numberBigInt;
    protected double _numberDouble;
    protected int _numberInt;
    protected long _numberLong;
    protected boolean _numberNegative;
    protected JsonReadContext _parsingContext;
    protected final TextBuffer _textBuffer;
    protected int _tokenInputCol;
    protected int _tokenInputRow = 1;
    protected long _tokenInputTotal;

    protected ParserBase(IOContext object, int n) {
        super(n);
        this._ioContext = object;
        this._textBuffer = ((IOContext)object).constructTextBuffer();
        object = JsonParser.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n) ? DupDetector.rootDetector(this) : null;
        this._parsingContext = JsonReadContext.createRootContext((DupDetector)object);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void _parseSlowFloat(int var1_1) throws IOException {
        if (var1_1 != 16) ** GOTO lbl6
        try {
            this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
            this._numTypesValid = 16;
            return;
lbl6: // 1 sources:
            this._numberDouble = this._textBuffer.contentsAsDouble();
            this._numTypesValid = 8;
            return;
        }
        catch (NumberFormatException var2_2) {
            var3_3 = new StringBuilder();
            var3_3.append("Malformed numeric value (");
            var3_3.append(this._longNumberDesc(this._textBuffer.contentsAsString()));
            var3_3.append(")");
            this._wrapError(var3_3.toString(), var2_2);
        }
    }

    private void _parseSlowInt(int n) throws IOException {
        String string2 = this._textBuffer.contentsAsString();
        try {
            int n2;
            int n3 = this._intLength;
            Object object = this._textBuffer.getTextBuffer();
            int n4 = n2 = this._textBuffer.getTextOffset();
            if (this._numberNegative) {
                n4 = n2 + 1;
            }
            if (NumberInput.inLongRange(object, n4, n3, this._numberNegative)) {
                this._numberLong = Long.parseLong(string2);
                this._numTypesValid = 2;
                return;
            }
            if (n == 1 || n == 2) {
                this._reportTooLongIntegral(n, string2);
            }
            if (n != 8 && n != 32) {
                this._numberBigInt = object = new BigInteger(string2);
                this._numTypesValid = 4;
                return;
            }
            this._numberDouble = NumberInput.parseDouble(string2);
            this._numTypesValid = 8;
            return;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed numeric value (");
            stringBuilder.append(this._longNumberDesc(string2));
            stringBuilder.append(")");
            this._wrapError(stringBuilder.toString(), numberFormatException);
        }
    }

    protected static int[] growArrayBy(int[] arrn, int n) {
        if (arrn != null) return Arrays.copyOf(arrn, arrn.length + n);
        return new int[n];
    }

    protected void _checkStdFeatureChanges(int n, int n2) {
        int n3 = JsonParser.Feature.STRICT_DUPLICATE_DETECTION.getMask();
        if ((n2 & n3) == 0) return;
        if ((n & n3) == 0) return;
        if (this._parsingContext.getDupDetector() == null) {
            this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
            return;
        }
        this._parsingContext = this._parsingContext.withDupDetector(null);
    }

    protected abstract void _closeInput() throws IOException;

    protected final int _decodeBase64Escape(Base64Variant base64Variant, char c, int n) throws IOException {
        if (c != '\\') throw this.reportInvalidBase64Char(base64Variant, c, n);
        char c2 = this._decodeEscaped();
        if (c2 <= ' ' && n == 0) {
            return -1;
        }
        c = (char)base64Variant.decodeBase64Char(c2);
        if (c >= '\u0000') return c;
        if (c != '\ufffffffe') throw this.reportInvalidBase64Char(base64Variant, c2, n);
        if (n < 2) throw this.reportInvalidBase64Char(base64Variant, c2, n);
        return c;
    }

    protected final int _decodeBase64Escape(Base64Variant base64Variant, int n, int n2) throws IOException {
        if (n != 92) throw this.reportInvalidBase64Char(base64Variant, n, n2);
        char c = this._decodeEscaped();
        if (c <= ' ' && n2 == 0) {
            return -1;
        }
        n = base64Variant.decodeBase64Char((int)c);
        if (n >= 0) return n;
        if (n != -2) throw this.reportInvalidBase64Char(base64Variant, c, n2);
        return n;
    }

    protected char _decodeEscaped() throws IOException {
        throw new UnsupportedOperationException();
    }

    protected final int _eofAsNextChar() throws JsonParseException {
        this._handleEOF();
        return -1;
    }

    protected void _finishString() throws IOException {
    }

    public ByteArrayBuilder _getByteArrayBuilder() {
        ByteArrayBuilder byteArrayBuilder = this._byteArrayBuilder;
        if (byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
            return this._byteArrayBuilder;
        }
        byteArrayBuilder.reset();
        return this._byteArrayBuilder;
    }

    protected Object _getSourceReference() {
        if (!JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION.enabledIn(this._features)) return null;
        return this._ioContext.getSourceReference();
    }

    protected void _handleBase64MissingPadding(Base64Variant base64Variant) throws IOException {
        this._reportError(base64Variant.missingPaddingMessage());
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        if (this._parsingContext.inRoot()) return;
        String string2 = this._parsingContext.inArray() ? "Array" : "Object";
        this._reportInvalidEOF(String.format(": expected close marker for %s (start marker at %s)", string2, this._parsingContext.getStartLocation(this._getSourceReference())), null);
    }

    protected char _handleUnrecognizedCharacterEscape(char c) throws JsonProcessingException {
        if (this.isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
            return c;
        }
        if (c == '\'' && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
            return c;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized character escape ");
        stringBuilder.append(ParserBase._getCharDesc(c));
        this._reportError(stringBuilder.toString());
        return c;
    }

    protected int _parseIntValue() throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT && this._intLength <= 9) {
            int n;
            this._numberInt = n = this._textBuffer.contentsAsInt(this._numberNegative);
            this._numTypesValid = 1;
            return n;
        }
        this._parseNumericValue(1);
        if ((this._numTypesValid & 1) != 0) return this._numberInt;
        this.convertNumberToInt();
        return this._numberInt;
    }

    protected void _parseNumericValue(int n) throws IOException {
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            int n2 = this._intLength;
            if (n2 <= 9) {
                this._numberInt = this._textBuffer.contentsAsInt(this._numberNegative);
                this._numTypesValid = 1;
                return;
            }
            if (n2 > 18) {
                this._parseSlowInt(n);
                return;
            }
            long l = this._textBuffer.contentsAsLong(this._numberNegative);
            if (n2 == 10) {
                if (this._numberNegative) {
                    if (l >= Integer.MIN_VALUE) {
                        this._numberInt = (int)l;
                        this._numTypesValid = 1;
                        return;
                    }
                } else if (l <= Integer.MAX_VALUE) {
                    this._numberInt = (int)l;
                    this._numTypesValid = 1;
                    return;
                }
            }
            this._numberLong = l;
            this._numTypesValid = 2;
            return;
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
            this._parseSlowFloat(n);
            return;
        }
        this._reportError("Current token (%s) not numeric, can not use numeric value accessors", (Object)((Object)this._currToken));
    }

    protected void _releaseBuffers() throws IOException {
        this._textBuffer.releaseBuffers();
        char[] arrc = this._nameCopyBuffer;
        if (arrc == null) return;
        this._nameCopyBuffer = null;
        this._ioContext.releaseNameCopyBuffer(arrc);
    }

    protected void _reportMismatchedEndMarker(int n, char c) throws JsonParseException {
        JsonStreamContext jsonStreamContext = this.getParsingContext();
        this._reportError(String.format("Unexpected close marker '%s': expected '%c' (for %s starting at %s)", Character.valueOf((char)n), Character.valueOf(c), jsonStreamContext.typeDesc(), ((JsonReadContext)jsonStreamContext).getStartLocation(this._getSourceReference())));
    }

    protected void _reportTooLongIntegral(int n, String string2) throws IOException {
        if (n == 1) {
            this.reportOverflowInt(string2);
            return;
        }
        this.reportOverflowLong(string2);
    }

    protected void _throwUnquotedSpace(int n, String string2) throws JsonParseException {
        if (this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS)) {
            if (n <= 32) return;
        }
        n = (char)n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal unquoted character (");
        stringBuilder.append(ParserBase._getCharDesc(n));
        stringBuilder.append("): has to be escaped using backslash to be included in ");
        stringBuilder.append(string2);
        this._reportError(stringBuilder.toString());
    }

    protected String _validJsonTokenList() throws IOException {
        return this._validJsonValueList();
    }

    protected String _validJsonValueList() throws IOException {
        if (!this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) return "(JSON String, Number, Array, Object or token 'null', 'true' or 'false')";
        return "(JSON String, Number (or 'NaN'/'INF'/'+INF'), Array, Object or token 'null', 'true' or 'false')";
    }

    @Override
    public void close() throws IOException {
        if (this._closed) return;
        this._inputPtr = Math.max(this._inputPtr, this._inputEnd);
        this._closed = true;
        try {
            this._closeInput();
            return;
        }
        finally {
            this._releaseBuffers();
        }
    }

    protected void convertNumberToBigDecimal() throws IOException {
        int n = this._numTypesValid;
        if ((n & 8) != 0) {
            this._numberBigDecimal = NumberInput.parseBigDecimal(this.getText());
        } else if ((n & 4) != 0) {
            this._numberBigDecimal = new BigDecimal(this._numberBigInt);
        } else if ((n & 2) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
        } else if ((n & 1) != 0) {
            this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 16;
    }

    protected void convertNumberToBigInteger() throws IOException {
        int n = this._numTypesValid;
        if ((n & 16) != 0) {
            this._numberBigInt = this._numberBigDecimal.toBigInteger();
        } else if ((n & 2) != 0) {
            this._numberBigInt = BigInteger.valueOf(this._numberLong);
        } else if ((n & 1) != 0) {
            this._numberBigInt = BigInteger.valueOf(this._numberInt);
        } else if ((n & 8) != 0) {
            this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 4;
    }

    protected void convertNumberToDouble() throws IOException {
        int n = this._numTypesValid;
        if ((n & 16) != 0) {
            this._numberDouble = this._numberBigDecimal.doubleValue();
        } else if ((n & 4) != 0) {
            this._numberDouble = this._numberBigInt.doubleValue();
        } else if ((n & 2) != 0) {
            this._numberDouble = this._numberLong;
        } else if ((n & 1) != 0) {
            this._numberDouble = this._numberInt;
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 8;
    }

    protected void convertNumberToInt() throws IOException {
        int n = this._numTypesValid;
        if ((n & 2) != 0) {
            long l = this._numberLong;
            n = (int)l;
            if ((long)n != l) {
                this.reportOverflowInt(this.getText(), this.currentToken());
            }
            this._numberInt = n;
        } else if ((n & 4) != 0) {
            if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigInt.intValue();
        } else if ((n & 8) != 0) {
            double d = this._numberDouble;
            if (d < -2.147483648E9 || d > 2.147483647E9) {
                this.reportOverflowInt();
            }
            this._numberInt = (int)this._numberDouble;
        } else if ((n & 16) != 0) {
            if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowInt();
            }
            this._numberInt = this._numberBigDecimal.intValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 1;
    }

    protected void convertNumberToLong() throws IOException {
        int n = this._numTypesValid;
        if ((n & 1) != 0) {
            this._numberLong = this._numberInt;
        } else if ((n & 4) != 0) {
            if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigInt.longValue();
        } else if ((n & 8) != 0) {
            double d = this._numberDouble;
            if (d < -9.223372036854776E18 || d > 9.223372036854776E18) {
                this.reportOverflowLong();
            }
            this._numberLong = (long)this._numberDouble;
        } else if ((n & 16) != 0) {
            if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
                this.reportOverflowLong();
            }
            this._numberLong = this._numberBigDecimal.longValue();
        } else {
            this._throwInternal();
        }
        this._numTypesValid |= 2;
    }

    @Override
    public JsonParser disable(JsonParser.Feature feature) {
        this._features &= feature.getMask();
        if (feature != JsonParser.Feature.STRICT_DUPLICATE_DETECTION) return this;
        this._parsingContext = this._parsingContext.withDupDetector(null);
        return this;
    }

    @Override
    public JsonParser enable(JsonParser.Feature feature) {
        this._features |= feature.getMask();
        if (feature != JsonParser.Feature.STRICT_DUPLICATE_DETECTION) return this;
        if (this._parsingContext.getDupDetector() != null) return this;
        this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector(this));
        return this;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        int n = this._numTypesValid;
        if ((n & 4) != 0) return this._numberBigInt;
        if (n == 0) {
            this._parseNumericValue(4);
        }
        if ((this._numTypesValid & 4) != 0) return this._numberBigInt;
        this.convertNumberToBigInteger();
        return this._numberBigInt;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        Object object;
        if (this._binaryValue != null) return this._binaryValue;
        if (this._currToken != JsonToken.VALUE_STRING) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Current token (");
            ((StringBuilder)object).append((Object)((Object)this._currToken));
            ((StringBuilder)object).append(") not VALUE_STRING, can not access as binary");
            this._reportError(((StringBuilder)object).toString());
        }
        object = this._getByteArrayBuilder();
        this._decodeBase64(this.getText(), (ByteArrayBuilder)object, base64Variant);
        this._binaryValue = ((ByteArrayBuilder)object).toByteArray();
        return this._binaryValue;
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
    public String getCurrentName() throws IOException {
        JsonStreamContext jsonStreamContext;
        if (this._currToken != JsonToken.START_OBJECT) {
            if (this._currToken != JsonToken.START_ARRAY) return this._parsingContext.getCurrentName();
        }
        if ((jsonStreamContext = this._parsingContext.getParent()) == null) return this._parsingContext.getCurrentName();
        return ((JsonReadContext)jsonStreamContext).getCurrentName();
    }

    @Override
    public Object getCurrentValue() {
        return this._parsingContext.getCurrentValue();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        int n = this._numTypesValid;
        if ((n & 16) != 0) return this._numberBigDecimal;
        if (n == 0) {
            this._parseNumericValue(16);
        }
        if ((this._numTypesValid & 16) != 0) return this._numberBigDecimal;
        this.convertNumberToBigDecimal();
        return this._numberBigDecimal;
    }

    @Override
    public double getDoubleValue() throws IOException {
        int n = this._numTypesValid;
        if ((n & 8) != 0) return this._numberDouble;
        if (n == 0) {
            this._parseNumericValue(8);
        }
        if ((this._numTypesValid & 8) != 0) return this._numberDouble;
        this.convertNumberToDouble();
        return this._numberDouble;
    }

    @Override
    public float getFloatValue() throws IOException {
        return (float)this.getDoubleValue();
    }

    @Override
    public int getIntValue() throws IOException {
        int n = this._numTypesValid;
        if ((n & 1) != 0) return this._numberInt;
        if (n == 0) {
            return this._parseIntValue();
        }
        if ((n & 1) != 0) return this._numberInt;
        this.convertNumberToInt();
        return this._numberInt;
    }

    @Override
    public long getLongValue() throws IOException {
        int n = this._numTypesValid;
        if ((n & 2) != 0) return this._numberLong;
        if (n == 0) {
            this._parseNumericValue(2);
        }
        if ((this._numTypesValid & 2) != 0) return this._numberLong;
        this.convertNumberToLong();
        return this._numberLong;
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken != JsonToken.VALUE_NUMBER_INT) {
            if ((this._numTypesValid & 16) == 0) return JsonParser.NumberType.DOUBLE;
            return JsonParser.NumberType.BIG_DECIMAL;
        }
        int n = this._numTypesValid;
        if ((n & 1) != 0) {
            return JsonParser.NumberType.INT;
        }
        if ((n & 2) == 0) return JsonParser.NumberType.BIG_INTEGER;
        return JsonParser.NumberType.LONG;
    }

    @Override
    public Number getNumberValue() throws IOException {
        if (this._numTypesValid == 0) {
            this._parseNumericValue(0);
        }
        if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
            int n = this._numTypesValid;
            if ((n & 1) != 0) {
                return this._numberInt;
            }
            if ((n & 2) != 0) {
                return this._numberLong;
            }
            if ((n & 4) == 0) return this._numberBigDecimal;
            return this._numberBigInt;
        }
        int n = this._numTypesValid;
        if ((n & 16) != 0) {
            return this._numberBigDecimal;
        }
        if ((n & 8) != 0) return this._numberDouble;
        this._throwInternal();
        return this._numberDouble;
    }

    @Override
    public JsonReadContext getParsingContext() {
        return this._parsingContext;
    }

    public long getTokenCharacterOffset() {
        return this._tokenInputTotal;
    }

    public int getTokenColumnNr() {
        int n = this._tokenInputCol;
        if (n < 0) {
            return n;
        }
        ++n;
        return n;
    }

    public int getTokenLineNr() {
        return this._tokenInputRow;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return new JsonLocation(this._getSourceReference(), -1L, this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
    }

    @Override
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return true;
        }
        if (this._currToken != JsonToken.FIELD_NAME) return false;
        return this._nameCopied;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    @Override
    public boolean isNaN() {
        boolean bl;
        JsonToken jsonToken = this._currToken;
        JsonToken jsonToken2 = JsonToken.VALUE_NUMBER_FLOAT;
        boolean bl2 = bl = false;
        if (jsonToken != jsonToken2) return bl2;
        bl2 = bl;
        if ((this._numTypesValid & 8) == 0) return bl2;
        double d = this._numberDouble;
        if (Double.isNaN(d)) return true;
        bl2 = bl;
        if (!Double.isInfinite(d)) return bl2;
        return true;
    }

    @Deprecated
    protected boolean loadMore() throws IOException {
        return false;
    }

    @Deprecated
    protected void loadMoreGuaranteed() throws IOException {
        if (this.loadMore()) return;
        this._reportInvalidEOF();
    }

    @Override
    public void overrideCurrentName(String string2) {
        JsonStreamContext jsonStreamContext;
        block5 : {
            JsonReadContext jsonReadContext;
            block4 : {
                jsonReadContext = this._parsingContext;
                if (this._currToken == JsonToken.START_OBJECT) break block4;
                jsonStreamContext = jsonReadContext;
                if (this._currToken != JsonToken.START_ARRAY) break block5;
            }
            jsonStreamContext = jsonReadContext.getParent();
        }
        try {
            ((JsonReadContext)jsonStreamContext).setCurrentName(string2);
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    @Override
    public JsonParser overrideStdFeatures(int n, int n2) {
        int n3 = this._features;
        n = n & n2 | n2 & n3;
        n2 = n3 ^ n;
        if (n2 == 0) return this;
        this._features = n;
        this._checkStdFeatureChanges(n, n2);
        return this;
    }

    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int n, int n2) throws IllegalArgumentException {
        return this.reportInvalidBase64Char(base64Variant, n, n2, null);
    }

    protected IllegalArgumentException reportInvalidBase64Char(Base64Variant object, int n, int n2, String string2) throws IllegalArgumentException {
        Object object2;
        if (n <= 32) {
            object = String.format("Illegal white space character (code 0x%s) as character #%d of 4-char base64 unit: can only used between units", Integer.toHexString(n), n2 + 1);
        } else if (((Base64Variant)object).usesPaddingChar(n)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unexpected padding character ('");
            ((StringBuilder)object2).append(((Base64Variant)object).getPaddingChar());
            ((StringBuilder)object2).append("') as character #");
            ((StringBuilder)object2).append(n2 + 1);
            ((StringBuilder)object2).append(" of 4-char base64 unit: padding only legal as 3rd or 4th character");
            object = ((StringBuilder)object2).toString();
        } else if (Character.isDefined(n) && !Character.isISOControl(n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal character '");
            ((StringBuilder)object).append((char)n);
            ((StringBuilder)object).append("' (code 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(") in base64 content");
            object = ((StringBuilder)object).toString();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal character (code 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(") in base64 content");
            object = ((StringBuilder)object).toString();
        }
        object2 = object;
        if (string2 == null) return new IllegalArgumentException((String)object2);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(": ");
        ((StringBuilder)object2).append(string2);
        object2 = ((StringBuilder)object2).toString();
        return new IllegalArgumentException((String)object2);
    }

    protected final JsonToken reset(boolean bl, int n, int n2, int n3) {
        if (n2 >= 1) return this.resetFloat(bl, n, n2, n3);
        if (n3 >= 1) return this.resetFloat(bl, n, n2, n3);
        return this.resetInt(bl, n);
    }

    protected final JsonToken resetAsNaN(String string2, double d) {
        this._textBuffer.resetWithString(string2);
        this._numberDouble = d;
        this._numTypesValid = 8;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetFloat(boolean bl, int n, int n2, int n3) {
        this._numberNegative = bl;
        this._intLength = n;
        this._fractLength = n2;
        this._expLength = n3;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    protected final JsonToken resetInt(boolean bl, int n) {
        this._numberNegative = bl;
        this._intLength = n;
        this._fractLength = 0;
        this._expLength = 0;
        this._numTypesValid = 0;
        return JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public void setCurrentValue(Object object) {
        this._parsingContext.setCurrentValue(object);
    }

    @Deprecated
    @Override
    public JsonParser setFeatureMask(int n) {
        int n2 = this._features ^ n;
        if (n2 == 0) return this;
        this._features = n;
        this._checkStdFeatureChanges(n, n2);
        return this;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
}

