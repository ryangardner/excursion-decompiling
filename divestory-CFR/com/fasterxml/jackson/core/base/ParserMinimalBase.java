/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ParserMinimalBase
extends JsonParser {
    protected static final BigDecimal BD_MAX_INT;
    protected static final BigDecimal BD_MAX_LONG;
    protected static final BigDecimal BD_MIN_INT;
    protected static final BigDecimal BD_MIN_LONG;
    protected static final BigInteger BI_MAX_INT;
    protected static final BigInteger BI_MAX_LONG;
    protected static final BigInteger BI_MIN_INT;
    protected static final BigInteger BI_MIN_LONG;
    protected static final char CHAR_NULL = '\u0000';
    protected static final int INT_0 = 48;
    protected static final int INT_9 = 57;
    protected static final int INT_APOS = 39;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_CR = 13;
    protected static final int INT_E = 69;
    protected static final int INT_HASH = 35;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_LF = 10;
    protected static final int INT_MINUS = 45;
    protected static final int INT_PERIOD = 46;
    protected static final int INT_PLUS = 43;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_SLASH = 47;
    protected static final int INT_SPACE = 32;
    protected static final int INT_TAB = 9;
    protected static final int INT_e = 101;
    protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
    protected static final double MAX_INT_D = 2.147483647E9;
    protected static final long MAX_INT_L = Integer.MAX_VALUE;
    protected static final double MAX_LONG_D = 9.223372036854776E18;
    protected static final double MIN_INT_D = -2.147483648E9;
    protected static final long MIN_INT_L = Integer.MIN_VALUE;
    protected static final double MIN_LONG_D = -9.223372036854776E18;
    protected static final byte[] NO_BYTES;
    protected static final int[] NO_INTS;
    protected static final int NR_BIGDECIMAL = 16;
    protected static final int NR_BIGINT = 4;
    protected static final int NR_DOUBLE = 8;
    protected static final int NR_FLOAT = 32;
    protected static final int NR_INT = 1;
    protected static final int NR_LONG = 2;
    protected static final int NR_UNKNOWN = 0;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;

    static {
        NO_BYTES = new byte[0];
        NO_INTS = new int[0];
        BI_MIN_INT = BigInteger.valueOf(Integer.MIN_VALUE);
        BI_MAX_INT = BigInteger.valueOf(Integer.MAX_VALUE);
        BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
        BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
        BD_MIN_INT = new BigDecimal(BI_MIN_INT);
        BD_MAX_INT = new BigDecimal(BI_MAX_INT);
    }

    protected ParserMinimalBase() {
    }

    protected ParserMinimalBase(int n) {
        super(n);
    }

    protected static String _ascii(byte[] object) {
        try {
            return new String((byte[])object, "US-ASCII");
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    protected static byte[] _asciiBytes(String string2) {
        byte[] arrby = new byte[string2.length()];
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            arrby[n2] = (byte)string2.charAt(n2);
            ++n2;
        }
        return arrby;
    }

    protected static final String _getCharDesc(int n) {
        char c = (char)n;
        if (Character.isISOControl(c)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(CTRL-CHAR, code ");
            stringBuilder.append(n);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        if (n > 255) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(c);
            stringBuilder.append("' (code ");
            stringBuilder.append(n);
            stringBuilder.append(" / 0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(c);
        stringBuilder.append("' (code ");
        stringBuilder.append(n);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    protected final JsonParseException _constructError(String string2, Throwable throwable) {
        return new JsonParseException((JsonParser)this, string2, throwable);
    }

    protected void _decodeBase64(String string2, ByteArrayBuilder byteArrayBuilder, Base64Variant base64Variant) throws IOException {
        try {
            base64Variant.decode(string2, byteArrayBuilder);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this._reportError(illegalArgumentException.getMessage());
        }
    }

    protected abstract void _handleEOF() throws JsonParseException;

    protected boolean _hasTextualNull(String string2) {
        return "null".equals(string2);
    }

    protected String _longIntegerDesc(String string2) {
        int n = string2.length();
        if (n < 1000) {
            return string2;
        }
        int n2 = n;
        if (!string2.startsWith("-")) return String.format("[Integer with %d digits]", n2);
        n2 = n - 1;
        return String.format("[Integer with %d digits]", n2);
    }

    protected String _longNumberDesc(String string2) {
        int n = string2.length();
        if (n < 1000) {
            return string2;
        }
        int n2 = n;
        if (!string2.startsWith("-")) return String.format("[number with %d characters]", n2);
        n2 = n - 1;
        return String.format("[number with %d characters]", n2);
    }

    protected final void _reportError(String string2) throws JsonParseException {
        throw this._constructError(string2);
    }

    protected final void _reportError(String string2, Object object) throws JsonParseException {
        throw this._constructError(String.format(string2, object));
    }

    protected final void _reportError(String string2, Object object, Object object2) throws JsonParseException {
        throw this._constructError(String.format(string2, object, object2));
    }

    protected void _reportInputCoercion(String string2, JsonToken jsonToken, Class<?> class_) throws InputCoercionException {
        throw new InputCoercionException(this, string2, jsonToken, class_);
    }

    protected void _reportInvalidEOF() throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" in ");
        stringBuilder.append((Object)((Object)this._currToken));
        this._reportInvalidEOF(stringBuilder.toString(), this._currToken);
    }

    @Deprecated
    protected void _reportInvalidEOF(String string2) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected end-of-input");
        stringBuilder.append(string2);
        throw new JsonEOFException(this, null, stringBuilder.toString());
    }

    protected void _reportInvalidEOF(String string2, JsonToken jsonToken) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected end-of-input");
        stringBuilder.append(string2);
        throw new JsonEOFException(this, jsonToken, stringBuilder.toString());
    }

    @Deprecated
    protected void _reportInvalidEOFInValue() throws JsonParseException {
        this._reportInvalidEOF(" in a value");
    }

    protected void _reportInvalidEOFInValue(JsonToken jsonToken) throws JsonParseException {
        String string2 = jsonToken == JsonToken.VALUE_STRING ? " in a String value" : (jsonToken != JsonToken.VALUE_NUMBER_INT && jsonToken != JsonToken.VALUE_NUMBER_FLOAT ? " in a value" : " in a Number value");
        this._reportInvalidEOF(string2, jsonToken);
    }

    protected void _reportMissingRootWS(int n) throws JsonParseException {
        this._reportUnexpectedChar(n, "Expected space separating root-level values");
    }

    protected void _reportUnexpectedChar(int n, String string2) throws JsonParseException {
        if (n < 0) {
            this._reportInvalidEOF();
        }
        String string3 = String.format("Unexpected character (%s)", ParserMinimalBase._getCharDesc(n));
        CharSequence charSequence = string3;
        if (string2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        this._reportError((String)charSequence);
    }

    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }

    protected void _throwInvalidSpace(int n) throws JsonParseException {
        n = (char)n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal character (");
        stringBuilder.append(ParserMinimalBase._getCharDesc(n));
        stringBuilder.append("): only regular white space (\\r, \\n, \\t) is allowed between tokens");
        this._reportError(stringBuilder.toString());
    }

    protected final void _wrapError(String string2, Throwable throwable) throws JsonParseException {
        throw this._constructError(string2, throwable);
    }

    @Override
    public void clearCurrentToken() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == null) return;
        this._lastClearedToken = jsonToken;
        this._currToken = null;
    }

    @Override
    public abstract void close() throws IOException;

    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }

    @Override
    public int currentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) return jsonToken.id();
        return 0;
    }

    @Override
    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

    @Override
    public abstract String getCurrentName() throws IOException;

    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    @Override
    public int getCurrentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) return jsonToken.id();
        return 0;
    }

    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    @Override
    public abstract JsonStreamContext getParsingContext();

    @Override
    public abstract String getText() throws IOException;

    @Override
    public abstract char[] getTextCharacters() throws IOException;

    @Override
    public abstract int getTextLength() throws IOException;

    @Override
    public abstract int getTextOffset() throws IOException;

    @Override
    public boolean getValueAsBoolean(boolean bl) throws IOException {
        Object object = this._currToken;
        if (object == null) return bl;
        int n = object.id();
        boolean bl2 = true;
        switch (n) {
            default: {
                return bl;
            }
            case 12: {
                object = this.getEmbeddedObject();
                if (!(object instanceof Boolean)) return bl;
                return (Boolean)object;
            }
            case 10: 
            case 11: {
                return false;
            }
            case 9: {
                return true;
            }
            case 7: {
                if (this.getIntValue() == 0) return false;
                return bl2;
            }
            case 6: 
        }
        object = this.getText().trim();
        if ("true".equals(object)) {
            return true;
        }
        if ("false".equals(object)) {
            return false;
        }
        if (!this._hasTextualNull((String)object)) return bl;
        return false;
    }

    @Override
    public double getValueAsDouble(double d) throws IOException {
        Object object = this._currToken;
        double d2 = d;
        if (object == null) return d2;
        switch (object.id()) {
            default: {
                return d;
            }
            case 12: {
                object = this.getEmbeddedObject();
                d2 = d;
                if (!(object instanceof Number)) return d2;
                return ((Number)object).doubleValue();
            }
            case 10: 
            case 11: {
                return 0.0;
            }
            case 9: {
                return 1.0;
            }
            case 7: 
            case 8: {
                return this.getDoubleValue();
            }
            case 6: 
        }
        object = this.getText();
        if (!this._hasTextualNull((String)object)) return NumberInput.parseAsDouble((String)object, d);
        return 0.0;
    }

    @Override
    public int getValueAsInt() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return this.getIntValue();
        if (jsonToken != JsonToken.VALUE_NUMBER_FLOAT) return this.getValueAsInt(0);
        return this.getIntValue();
    }

    @Override
    public int getValueAsInt(int n) throws IOException {
        Object object = this._currToken;
        if (object == JsonToken.VALUE_NUMBER_INT) return this.getIntValue();
        if (object == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getIntValue();
        }
        int n2 = n;
        if (object == null) return n2;
        n2 = object.id();
        if (n2 != 6) {
            switch (n2) {
                default: {
                    return n;
                }
                case 12: {
                    object = this.getEmbeddedObject();
                    n2 = n;
                    if (!(object instanceof Number)) return n2;
                    return ((Number)object).intValue();
                }
                case 10: 
                case 11: {
                    return 0;
                }
                case 9: 
            }
            return 1;
        }
        object = this.getText();
        if (!this._hasTextualNull((String)object)) return NumberInput.parseAsInt((String)object, n);
        return 0;
    }

    @Override
    public long getValueAsLong() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return this.getLongValue();
        if (jsonToken != JsonToken.VALUE_NUMBER_FLOAT) return this.getValueAsLong(0L);
        return this.getLongValue();
    }

    @Override
    public long getValueAsLong(long l) throws IOException {
        Object object = this._currToken;
        if (object == JsonToken.VALUE_NUMBER_INT) return this.getLongValue();
        if (object == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getLongValue();
        }
        long l2 = l;
        if (object == null) return l2;
        int n = object.id();
        if (n != 6) {
            switch (n) {
                default: {
                    return l;
                }
                case 12: {
                    object = this.getEmbeddedObject();
                    l2 = l;
                    if (!(object instanceof Number)) return l2;
                    return ((Number)object).longValue();
                }
                case 10: 
                case 11: {
                    return 0L;
                }
                case 9: 
            }
            return 1L;
        }
        object = this.getText();
        if (!this._hasTextualNull((String)object)) return NumberInput.parseAsLong((String)object, l);
        return 0L;
    }

    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken != JsonToken.FIELD_NAME) return this.getValueAsString(null);
        return this.getCurrentName();
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        JsonToken jsonToken = this._currToken;
        String string3 = string2;
        if (jsonToken == null) return string3;
        string3 = string2;
        if (jsonToken == JsonToken.VALUE_NULL) return string3;
        if (this._currToken.isScalarValue()) return this.getText();
        return string2;
    }

    @Override
    public boolean hasCurrentToken() {
        if (this._currToken == null) return false;
        return true;
    }

    @Override
    public abstract boolean hasTextCharacters();

    @Override
    public boolean hasToken(JsonToken jsonToken) {
        if (this._currToken != jsonToken) return false;
        return true;
    }

    @Override
    public boolean hasTokenId(int n) {
        JsonToken jsonToken = this._currToken;
        boolean bl = true;
        boolean bl2 = true;
        if (jsonToken == null) {
            if (n != 0) return false;
            return bl2;
        }
        if (jsonToken.id() != n) return false;
        return bl;
    }

    @Override
    public abstract boolean isClosed();

    @Override
    public boolean isExpectedStartArrayToken() {
        if (this._currToken != JsonToken.START_ARRAY) return false;
        return true;
    }

    @Override
    public boolean isExpectedStartObjectToken() {
        if (this._currToken != JsonToken.START_OBJECT) return false;
        return true;
    }

    @Override
    public abstract JsonToken nextToken() throws IOException;

    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = this.nextToken();
        if (jsonToken != JsonToken.FIELD_NAME) return jsonToken2;
        return this.nextToken();
    }

    @Override
    public abstract void overrideCurrentName(String var1);

    protected void reportInvalidNumber(String string2) throws JsonParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid numeric value: ");
        stringBuilder.append(string2);
        this._reportError(stringBuilder.toString());
    }

    protected void reportOverflowInt() throws IOException {
        this.reportOverflowInt(this.getText());
    }

    protected void reportOverflowInt(String string2) throws IOException {
        this.reportOverflowInt(string2, JsonToken.VALUE_NUMBER_INT);
    }

    protected void reportOverflowInt(String string2, JsonToken jsonToken) throws IOException {
        this._reportInputCoercion(String.format("Numeric value (%s) out of range of int (%d - %s)", this._longIntegerDesc(string2), Integer.MIN_VALUE, Integer.MAX_VALUE), jsonToken, Integer.TYPE);
    }

    protected void reportOverflowLong() throws IOException {
        this.reportOverflowLong(this.getText());
    }

    protected void reportOverflowLong(String string2) throws IOException {
        this.reportOverflowLong(string2, JsonToken.VALUE_NUMBER_INT);
    }

    protected void reportOverflowLong(String string2, JsonToken jsonToken) throws IOException {
        this._reportInputCoercion(String.format("Numeric value (%s) out of range of long (%d - %s)", this._longIntegerDesc(string2), Long.MIN_VALUE, Long.MAX_VALUE), jsonToken, Long.TYPE);
    }

    protected void reportUnexpectedNumberChar(int n, String string2) throws JsonParseException {
        String string3 = String.format("Unexpected character (%s) in numeric value", ParserMinimalBase._getCharDesc(n));
        CharSequence charSequence = string3;
        if (string2 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        this._reportError((String)charSequence);
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int n = 1;
        do {
            JsonToken jsonToken;
            if ((jsonToken = this.nextToken()) == null) {
                this._handleEOF();
                return this;
            }
            if (jsonToken.isStructStart()) {
                ++n;
                continue;
            }
            if (jsonToken.isStructEnd()) {
                int n2;
                n = n2 = n - 1;
                if (n2 != 0) continue;
                return this;
            }
            if (jsonToken != JsonToken.NOT_AVAILABLE) continue;
            this._reportError("Not enough content available for `skipChildren()`: non-blocking parser? (%s)", this.getClass().getName());
        } while (true);
    }
}

