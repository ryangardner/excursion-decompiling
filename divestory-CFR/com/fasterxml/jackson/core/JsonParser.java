/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.RequestPayload;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public abstract class JsonParser
implements Closeable,
Versioned {
    private static final int MAX_BYTE_I = 255;
    private static final int MAX_SHORT_I = 32767;
    private static final int MIN_BYTE_I = -128;
    private static final int MIN_SHORT_I = -32768;
    protected int _features;
    protected transient RequestPayload _requestPayload;

    protected JsonParser() {
    }

    protected JsonParser(int n) {
        this._features = n;
    }

    protected ObjectCodec _codec() {
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
        return objectCodec;
    }

    protected JsonParseException _constructError(String string2) {
        return new JsonParseException(this, string2).withRequestPayload(this._requestPayload);
    }

    protected void _reportUnsupportedOperation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Operation not supported by parser of type ");
        stringBuilder.append(this.getClass().getName());
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public boolean canParseAsync() {
        return false;
    }

    public boolean canReadObjectId() {
        return false;
    }

    public boolean canReadTypeId() {
        return false;
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public abstract void clearCurrentToken();

    @Override
    public abstract void close() throws IOException;

    public JsonParser configure(Feature feature, boolean bl) {
        if (bl) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public String currentName() throws IOException {
        return this.getCurrentName();
    }

    public JsonToken currentToken() {
        return this.getCurrentToken();
    }

    public int currentTokenId() {
        return this.getCurrentTokenId();
    }

    public JsonParser disable(Feature feature) {
        int n = this._features;
        this._features = feature.getMask() & n;
        return this;
    }

    public JsonParser enable(Feature feature) {
        int n = this._features;
        this._features = feature.getMask() | n;
        return this;
    }

    public void finishToken() throws IOException {
    }

    public abstract BigInteger getBigIntegerValue() throws IOException;

    public byte[] getBinaryValue() throws IOException {
        return this.getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

    public boolean getBooleanValue() throws IOException {
        JsonToken jsonToken = this.currentToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (jsonToken != JsonToken.VALUE_FALSE) throw new JsonParseException(this, String.format("Current token (%s) not of boolean type", new Object[]{jsonToken})).withRequestPayload(this._requestPayload);
        return false;
    }

    public byte getByteValue() throws IOException {
        int n = this.getIntValue();
        if (n >= -128 && n <= 255) {
            return (byte)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Numeric value (");
        stringBuilder.append(this.getText());
        stringBuilder.append(") out of range of Java byte");
        throw this._constructError(stringBuilder.toString());
    }

    public abstract ObjectCodec getCodec();

    public abstract JsonLocation getCurrentLocation();

    public abstract String getCurrentName() throws IOException;

    public abstract JsonToken getCurrentToken();

    public abstract int getCurrentTokenId();

    public Object getCurrentValue() {
        JsonStreamContext jsonStreamContext = this.getParsingContext();
        if (jsonStreamContext != null) return jsonStreamContext.getCurrentValue();
        return null;
    }

    public abstract BigDecimal getDecimalValue() throws IOException;

    public abstract double getDoubleValue() throws IOException;

    public Object getEmbeddedObject() throws IOException {
        return null;
    }

    public int getFeatureMask() {
        return this._features;
    }

    public abstract float getFloatValue() throws IOException;

    public int getFormatFeatures() {
        return 0;
    }

    public Object getInputSource() {
        return null;
    }

    public abstract int getIntValue() throws IOException;

    public abstract JsonToken getLastClearedToken();

    public abstract long getLongValue() throws IOException;

    public NonBlockingInputFeeder getNonBlockingInputFeeder() {
        return null;
    }

    public abstract NumberType getNumberType() throws IOException;

    public abstract Number getNumberValue() throws IOException;

    public Object getObjectId() throws IOException {
        return null;
    }

    public abstract JsonStreamContext getParsingContext();

    public FormatSchema getSchema() {
        return null;
    }

    public short getShortValue() throws IOException {
        int n = this.getIntValue();
        if (n >= -32768 && n <= 32767) {
            return (short)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Numeric value (");
        stringBuilder.append(this.getText());
        stringBuilder.append(") out of range of Java short");
        throw this._constructError(stringBuilder.toString());
    }

    public int getText(Writer writer) throws IOException, UnsupportedOperationException {
        String string2 = this.getText();
        if (string2 == null) {
            return 0;
        }
        writer.write(string2);
        return string2.length();
    }

    public abstract String getText() throws IOException;

    public abstract char[] getTextCharacters() throws IOException;

    public abstract int getTextLength() throws IOException;

    public abstract int getTextOffset() throws IOException;

    public abstract JsonLocation getTokenLocation();

    public Object getTypeId() throws IOException {
        return null;
    }

    public boolean getValueAsBoolean() throws IOException {
        return this.getValueAsBoolean(false);
    }

    public boolean getValueAsBoolean(boolean bl) throws IOException {
        return bl;
    }

    public double getValueAsDouble() throws IOException {
        return this.getValueAsDouble(0.0);
    }

    public double getValueAsDouble(double d) throws IOException {
        return d;
    }

    public int getValueAsInt() throws IOException {
        return this.getValueAsInt(0);
    }

    public int getValueAsInt(int n) throws IOException {
        return n;
    }

    public long getValueAsLong() throws IOException {
        return this.getValueAsLong(0L);
    }

    public long getValueAsLong(long l) throws IOException {
        return l;
    }

    public String getValueAsString() throws IOException {
        return this.getValueAsString(null);
    }

    public abstract String getValueAsString(String var1) throws IOException;

    public abstract boolean hasCurrentToken();

    public abstract boolean hasTextCharacters();

    public abstract boolean hasToken(JsonToken var1);

    public abstract boolean hasTokenId(int var1);

    public abstract boolean isClosed();

    public boolean isEnabled(Feature feature) {
        return feature.enabledIn(this._features);
    }

    public boolean isEnabled(StreamReadFeature streamReadFeature) {
        return streamReadFeature.mappedFeature().enabledIn(this._features);
    }

    public boolean isExpectedStartArrayToken() {
        if (this.currentToken() != JsonToken.START_ARRAY) return false;
        return true;
    }

    public boolean isExpectedStartObjectToken() {
        if (this.currentToken() != JsonToken.START_OBJECT) return false;
        return true;
    }

    public boolean isNaN() throws IOException {
        return false;
    }

    public Boolean nextBooleanValue() throws IOException {
        JsonToken jsonToken = this.nextToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (jsonToken != JsonToken.VALUE_FALSE) return null;
        return Boolean.FALSE;
    }

    public String nextFieldName() throws IOException {
        if (this.nextToken() != JsonToken.FIELD_NAME) return null;
        return this.getCurrentName();
    }

    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        if (this.nextToken() != JsonToken.FIELD_NAME) return false;
        if (!serializableString.getValue().equals(this.getCurrentName())) return false;
        return true;
    }

    public int nextIntValue(int n) throws IOException {
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return n;
        return this.getIntValue();
    }

    public long nextLongValue(long l) throws IOException {
        if (this.nextToken() != JsonToken.VALUE_NUMBER_INT) return l;
        return this.getLongValue();
    }

    public String nextTextValue() throws IOException {
        if (this.nextToken() != JsonToken.VALUE_STRING) return null;
        return this.getText();
    }

    public abstract JsonToken nextToken() throws IOException;

    public abstract JsonToken nextValue() throws IOException;

    public abstract void overrideCurrentName(String var1);

    public JsonParser overrideFormatFeatures(int n, int n2) {
        return this;
    }

    public JsonParser overrideStdFeatures(int n, int n2) {
        return this.setFeatureMask(n & n2 | this._features & n2);
    }

    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream2) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }

    public int readBinaryValue(OutputStream outputStream2) throws IOException {
        return this.readBinaryValue(Base64Variants.getDefaultVariant(), outputStream2);
    }

    public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
        return (T)this._codec().readValue(this, typeReference);
    }

    public <T> T readValueAs(Class<T> class_) throws IOException {
        return this._codec().readValue(this, class_);
    }

    public <T extends TreeNode> T readValueAsTree() throws IOException {
        return this._codec().readTree(this);
    }

    public <T> Iterator<T> readValuesAs(TypeReference<T> typeReference) throws IOException {
        return this._codec().readValues(this, typeReference);
    }

    public <T> Iterator<T> readValuesAs(Class<T> class_) throws IOException {
        return this._codec().readValues(this, class_);
    }

    public int releaseBuffered(OutputStream outputStream2) throws IOException {
        return -1;
    }

    public int releaseBuffered(Writer writer) throws IOException {
        return -1;
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public abstract void setCodec(ObjectCodec var1);

    public void setCurrentValue(Object object) {
        JsonStreamContext jsonStreamContext = this.getParsingContext();
        if (jsonStreamContext == null) return;
        jsonStreamContext.setCurrentValue(object);
    }

    @Deprecated
    public JsonParser setFeatureMask(int n) {
        this._features = n;
        return this;
    }

    public void setRequestPayloadOnError(RequestPayload requestPayload) {
        this._requestPayload = requestPayload;
    }

    public void setRequestPayloadOnError(String object) {
        object = object == null ? null : new RequestPayload((CharSequence)object);
        this._requestPayload = object;
    }

    public void setRequestPayloadOnError(byte[] object, String string2) {
        object = object == null ? null : new RequestPayload((byte[])object, string2);
        this._requestPayload = object;
    }

    public void setSchema(FormatSchema formatSchema) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parser of type ");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" does not support schema of type '");
        stringBuilder.append(formatSchema.getSchemaType());
        stringBuilder.append("'");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract JsonParser skipChildren() throws IOException;

    @Override
    public abstract Version version();

    public static final class Feature
    extends Enum<Feature> {
        private static final /* synthetic */ Feature[] $VALUES;
        @Deprecated
        public static final /* enum */ Feature ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;
        public static final /* enum */ Feature ALLOW_COMMENTS;
        @Deprecated
        public static final /* enum */ Feature ALLOW_MISSING_VALUES;
        @Deprecated
        public static final /* enum */ Feature ALLOW_NON_NUMERIC_NUMBERS;
        @Deprecated
        public static final /* enum */ Feature ALLOW_NUMERIC_LEADING_ZEROS;
        public static final /* enum */ Feature ALLOW_SINGLE_QUOTES;
        @Deprecated
        public static final /* enum */ Feature ALLOW_TRAILING_COMMA;
        @Deprecated
        public static final /* enum */ Feature ALLOW_UNQUOTED_CONTROL_CHARS;
        public static final /* enum */ Feature ALLOW_UNQUOTED_FIELD_NAMES;
        public static final /* enum */ Feature ALLOW_YAML_COMMENTS;
        public static final /* enum */ Feature AUTO_CLOSE_SOURCE;
        public static final /* enum */ Feature IGNORE_UNDEFINED;
        public static final /* enum */ Feature INCLUDE_SOURCE_IN_LOCATION;
        public static final /* enum */ Feature STRICT_DUPLICATE_DETECTION;
        private final boolean _defaultState;
        private final int _mask = 1 << this.ordinal();

        static {
            Feature feature;
            AUTO_CLOSE_SOURCE = new Feature(true);
            ALLOW_COMMENTS = new Feature(false);
            ALLOW_YAML_COMMENTS = new Feature(false);
            ALLOW_UNQUOTED_FIELD_NAMES = new Feature(false);
            ALLOW_SINGLE_QUOTES = new Feature(false);
            ALLOW_UNQUOTED_CONTROL_CHARS = new Feature(false);
            ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER = new Feature(false);
            ALLOW_NUMERIC_LEADING_ZEROS = new Feature(false);
            ALLOW_NON_NUMERIC_NUMBERS = new Feature(false);
            ALLOW_MISSING_VALUES = new Feature(false);
            ALLOW_TRAILING_COMMA = new Feature(false);
            STRICT_DUPLICATE_DETECTION = new Feature(false);
            IGNORE_UNDEFINED = new Feature(false);
            INCLUDE_SOURCE_IN_LOCATION = feature = new Feature(true);
            $VALUES = new Feature[]{AUTO_CLOSE_SOURCE, ALLOW_COMMENTS, ALLOW_YAML_COMMENTS, ALLOW_UNQUOTED_FIELD_NAMES, ALLOW_SINGLE_QUOTES, ALLOW_UNQUOTED_CONTROL_CHARS, ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, ALLOW_NUMERIC_LEADING_ZEROS, ALLOW_NON_NUMERIC_NUMBERS, ALLOW_MISSING_VALUES, ALLOW_TRAILING_COMMA, STRICT_DUPLICATE_DETECTION, IGNORE_UNDEFINED, feature};
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
        }

        public static int collectDefaults() {
            Feature[] arrfeature = Feature.values();
            int n = arrfeature.length;
            int n2 = 0;
            int n3 = 0;
            while (n2 < n) {
                Feature feature = arrfeature[n2];
                int n4 = n3;
                if (feature.enabledByDefault()) {
                    n4 = n3 | feature.getMask();
                }
                ++n2;
                n3 = n4;
            }
            return n3;
        }

        public static Feature valueOf(String string2) {
            return Enum.valueOf(Feature.class, string2);
        }

        public static Feature[] values() {
            return (Feature[])$VALUES.clone();
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int n) {
            if ((n & this._mask) == 0) return false;
            return true;
        }

        public int getMask() {
            return this._mask;
        }
    }

    public static final class NumberType
    extends Enum<NumberType> {
        private static final /* synthetic */ NumberType[] $VALUES;
        public static final /* enum */ NumberType BIG_DECIMAL;
        public static final /* enum */ NumberType BIG_INTEGER;
        public static final /* enum */ NumberType DOUBLE;
        public static final /* enum */ NumberType FLOAT;
        public static final /* enum */ NumberType INT;
        public static final /* enum */ NumberType LONG;

        static {
            NumberType numberType;
            INT = new NumberType();
            LONG = new NumberType();
            BIG_INTEGER = new NumberType();
            FLOAT = new NumberType();
            DOUBLE = new NumberType();
            BIG_DECIMAL = numberType = new NumberType();
            $VALUES = new NumberType[]{INT, LONG, BIG_INTEGER, FLOAT, DOUBLE, numberType};
        }

        public static NumberType valueOf(String string2) {
            return Enum.valueOf(NumberType.class, string2);
        }

        public static NumberType[] values() {
            return (NumberType[])$VALUES.clone();
        }
    }

}

