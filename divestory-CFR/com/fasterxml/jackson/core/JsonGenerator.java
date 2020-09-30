/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class JsonGenerator
implements Closeable,
Flushable,
Versioned {
    protected PrettyPrinter _cfgPrettyPrinter;

    protected JsonGenerator() {
    }

    /*
     * Unable to fully structure code
     */
    protected void _copyCurrentContents(JsonParser var1_1) throws IOException {
        var2_2 = 1;
        block14 : while ((var3_3 = var1_1.nextToken()) != null) {
            switch (var3_3.id()) {
                case 12: {
                    this.writeObject(var1_1.getEmbeddedObject());
                    continue block14;
                }
                case 11: {
                    this.writeNull();
                    continue block14;
                }
                case 10: {
                    this.writeBoolean(false);
                    continue block14;
                }
                case 9: {
                    this.writeBoolean(true);
                    continue block14;
                }
                case 8: {
                    var3_3 = var1_1.getNumberType();
                    if (var3_3 == JsonParser.NumberType.BIG_DECIMAL) {
                        this.writeNumber(var1_1.getDecimalValue());
                        continue block14;
                    }
                    if (var3_3 == JsonParser.NumberType.FLOAT) {
                        this.writeNumber(var1_1.getFloatValue());
                        continue block14;
                    }
                    this.writeNumber(var1_1.getDoubleValue());
                    continue block14;
                }
                case 7: {
                    var3_3 = var1_1.getNumberType();
                    if (var3_3 == JsonParser.NumberType.INT) {
                        this.writeNumber(var1_1.getIntValue());
                        continue block14;
                    }
                    if (var3_3 == JsonParser.NumberType.BIG_INTEGER) {
                        this.writeNumber(var1_1.getBigIntegerValue());
                        continue block14;
                    }
                    this.writeNumber(var1_1.getLongValue());
                    continue block14;
                }
                case 6: {
                    if (var1_1.hasTextCharacters()) {
                        this.writeString(var1_1.getTextCharacters(), var1_1.getTextOffset(), var1_1.getTextLength());
                        continue block14;
                    }
                    this.writeString(var1_1.getText());
                    continue block14;
                }
                case 5: {
                    this.writeFieldName(var1_1.getCurrentName());
                    continue block14;
                }
                case 4: {
                    this.writeEndArray();
                    var2_2 = var4_4 = var2_2 - 1;
                    if (var4_4 != 0) continue block14;
                    return;
                }
                case 3: {
                    this.writeStartArray();
                    ** break;
                }
                case 2: {
                    this.writeEndObject();
                    var2_2 = var4_4 = var2_2 - 1;
                    if (var4_4 != 0) continue block14;
                    return;
                }
                case 1: {
                    this.writeStartObject();
lbl60: // 2 sources:
                    ++var2_2;
                    continue block14;
                }
            }
        }
        return;
        var1_1 = new StringBuilder();
        var1_1.append("Internal error: unknown current token, ");
        var1_1.append(var3_3);
        throw new IllegalStateException(var1_1.toString());
    }

    protected void _reportError(String string2) throws JsonGenerationException {
        throw new JsonGenerationException(string2, this);
    }

    protected void _reportUnsupportedOperation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Operation not supported by generator of type ");
        stringBuilder.append(this.getClass().getName());
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }

    protected final void _verifyOffsets(int n, int n2, int n3) {
        if (n2 < 0 || n2 + n3 > n) throw new IllegalArgumentException(String.format("invalid argument(s) (offset=%d, length=%d) for input array of %d element", n2, n3, n));
    }

    protected void _writeSimpleObject(Object object) throws IOException {
        Serializable serializable;
        if (object == null) {
            this.writeNull();
            return;
        }
        if (object instanceof String) {
            this.writeString((String)object);
            return;
        }
        if (object instanceof Number) {
            serializable = (Number)object;
            if (serializable instanceof Integer) {
                this.writeNumber(((Number)serializable).intValue());
                return;
            }
            if (serializable instanceof Long) {
                this.writeNumber(((Number)serializable).longValue());
                return;
            }
            if (serializable instanceof Double) {
                this.writeNumber(((Number)serializable).doubleValue());
                return;
            }
            if (serializable instanceof Float) {
                this.writeNumber(((Number)serializable).floatValue());
                return;
            }
            if (serializable instanceof Short) {
                this.writeNumber(((Number)serializable).shortValue());
                return;
            }
            if (serializable instanceof Byte) {
                this.writeNumber(((Number)serializable).byteValue());
                return;
            }
            if (serializable instanceof BigInteger) {
                this.writeNumber((BigInteger)serializable);
                return;
            }
            if (serializable instanceof BigDecimal) {
                this.writeNumber((BigDecimal)serializable);
                return;
            }
            if (serializable instanceof AtomicInteger) {
                this.writeNumber(((AtomicInteger)serializable).get());
                return;
            }
            if (serializable instanceof AtomicLong) {
                this.writeNumber(((AtomicLong)serializable).get());
                return;
            }
        } else {
            if (object instanceof byte[]) {
                this.writeBinary((byte[])object);
                return;
            }
            if (object instanceof Boolean) {
                this.writeBoolean((Boolean)object);
                return;
            }
            if (object instanceof AtomicBoolean) {
                this.writeBoolean(((AtomicBoolean)object).get());
                return;
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed ");
        ((StringBuilder)serializable).append(object.getClass().getName());
        ((StringBuilder)serializable).append(")");
        throw new IllegalStateException(((StringBuilder)serializable).toString());
    }

    public boolean canOmitFields() {
        return true;
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        return false;
    }

    public boolean canWriteBinaryNatively() {
        return false;
    }

    public boolean canWriteFormattedNumbers() {
        return false;
    }

    public boolean canWriteObjectId() {
        return false;
    }

    public boolean canWriteTypeId() {
        return false;
    }

    @Override
    public abstract void close() throws IOException;

    public final JsonGenerator configure(Feature feature, boolean bl) {
        if (bl) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public void copyCurrentEvent(JsonParser object) throws IOException {
        Enum enum_ = ((JsonParser)object).currentToken();
        int n = enum_ == null ? -1 : ((JsonToken)enum_).id();
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Internal error: unknown current token, ");
                ((StringBuilder)object).append(enum_);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            case 12: {
                this.writeObject(((JsonParser)object).getEmbeddedObject());
                return;
            }
            case 11: {
                this.writeNull();
                return;
            }
            case 10: {
                this.writeBoolean(false);
                return;
            }
            case 9: {
                this.writeBoolean(true);
                return;
            }
            case 8: {
                enum_ = ((JsonParser)object).getNumberType();
                if (enum_ == JsonParser.NumberType.BIG_DECIMAL) {
                    this.writeNumber(((JsonParser)object).getDecimalValue());
                    return;
                }
                if (enum_ == JsonParser.NumberType.FLOAT) {
                    this.writeNumber(((JsonParser)object).getFloatValue());
                    return;
                }
                this.writeNumber(((JsonParser)object).getDoubleValue());
                return;
            }
            case 7: {
                enum_ = ((JsonParser)object).getNumberType();
                if (enum_ == JsonParser.NumberType.INT) {
                    this.writeNumber(((JsonParser)object).getIntValue());
                    return;
                }
                if (enum_ == JsonParser.NumberType.BIG_INTEGER) {
                    this.writeNumber(((JsonParser)object).getBigIntegerValue());
                    return;
                }
                this.writeNumber(((JsonParser)object).getLongValue());
                return;
            }
            case 6: {
                if (((JsonParser)object).hasTextCharacters()) {
                    this.writeString(((JsonParser)object).getTextCharacters(), ((JsonParser)object).getTextOffset(), ((JsonParser)object).getTextLength());
                    return;
                }
                this.writeString(((JsonParser)object).getText());
                return;
            }
            case 5: {
                this.writeFieldName(((JsonParser)object).getCurrentName());
                return;
            }
            case 4: {
                this.writeEndArray();
                return;
            }
            case 3: {
                this.writeStartArray();
                return;
            }
            case 2: {
                this.writeEndObject();
                return;
            }
            case 1: {
                this.writeStartObject();
                return;
            }
            case -1: 
        }
        this._reportError("No current event to copy");
    }

    public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
        JsonToken jsonToken = jsonParser.currentToken();
        int n = -1;
        int n2 = jsonToken == null ? -1 : jsonToken.id();
        int n3 = n2;
        if (n2 == 5) {
            this.writeFieldName(jsonParser.getCurrentName());
            jsonToken = jsonParser.nextToken();
            n2 = jsonToken == null ? n : jsonToken.id();
            n3 = n2;
        }
        if (n3 == 1) {
            this.writeStartObject();
            this._copyCurrentContents(jsonParser);
            return;
        }
        if (n3 != 3) {
            this.copyCurrentEvent(jsonParser);
            return;
        }
        this.writeStartArray();
        this._copyCurrentContents(jsonParser);
    }

    public abstract JsonGenerator disable(Feature var1);

    public abstract JsonGenerator enable(Feature var1);

    @Override
    public abstract void flush() throws IOException;

    public CharacterEscapes getCharacterEscapes() {
        return null;
    }

    public abstract ObjectCodec getCodec();

    public Object getCurrentValue() {
        JsonStreamContext jsonStreamContext = this.getOutputContext();
        if (jsonStreamContext != null) return jsonStreamContext.getCurrentValue();
        return null;
    }

    public abstract int getFeatureMask();

    public int getFormatFeatures() {
        return 0;
    }

    public int getHighestEscapedChar() {
        return 0;
    }

    public int getOutputBuffered() {
        return -1;
    }

    public abstract JsonStreamContext getOutputContext();

    public Object getOutputTarget() {
        return null;
    }

    public PrettyPrinter getPrettyPrinter() {
        return this._cfgPrettyPrinter;
    }

    public FormatSchema getSchema() {
        return null;
    }

    public abstract boolean isClosed();

    public abstract boolean isEnabled(Feature var1);

    public boolean isEnabled(StreamWriteFeature streamWriteFeature) {
        return this.isEnabled(streamWriteFeature.mappedFeature());
    }

    public JsonGenerator overrideFormatFeatures(int n, int n2) {
        return this;
    }

    public JsonGenerator overrideStdFeatures(int n, int n2) {
        return this.setFeatureMask(n & n2 | this.getFeatureMask() & n2);
    }

    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        return this;
    }

    public abstract JsonGenerator setCodec(ObjectCodec var1);

    public void setCurrentValue(Object object) {
        JsonStreamContext jsonStreamContext = this.getOutputContext();
        if (jsonStreamContext == null) return;
        jsonStreamContext.setCurrentValue(object);
    }

    @Deprecated
    public abstract JsonGenerator setFeatureMask(int var1);

    public JsonGenerator setHighestNonEscapedChar(int n) {
        return this;
    }

    public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
        this._cfgPrettyPrinter = prettyPrinter;
        return this;
    }

    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        throw new UnsupportedOperationException();
    }

    public void setSchema(FormatSchema formatSchema) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Generator of type ");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" does not support schema of type '");
        stringBuilder.append(formatSchema.getSchemaType());
        stringBuilder.append("'");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public abstract JsonGenerator useDefaultPrettyPrinter();

    @Override
    public abstract Version version();

    public void writeArray(double[] arrd, int n, int n2) throws IOException {
        if (arrd == null) throw new IllegalArgumentException("null array");
        this._verifyOffsets(arrd.length, n, n2);
        this.writeStartArray(arrd, n2);
        int n3 = n;
        do {
            if (n3 >= n2 + n) {
                this.writeEndArray();
                return;
            }
            this.writeNumber(arrd[n3]);
            ++n3;
        } while (true);
    }

    public void writeArray(int[] arrn, int n, int n2) throws IOException {
        if (arrn == null) throw new IllegalArgumentException("null array");
        this._verifyOffsets(arrn.length, n, n2);
        this.writeStartArray(arrn, n2);
        int n3 = n;
        do {
            if (n3 >= n2 + n) {
                this.writeEndArray();
                return;
            }
            this.writeNumber(arrn[n3]);
            ++n3;
        } while (true);
    }

    public void writeArray(long[] arrl, int n, int n2) throws IOException {
        if (arrl == null) throw new IllegalArgumentException("null array");
        this._verifyOffsets(arrl.length, n, n2);
        this.writeStartArray(arrl, n2);
        int n3 = n;
        do {
            if (n3 >= n2 + n) {
                this.writeEndArray();
                return;
            }
            this.writeNumber(arrl[n3]);
            ++n3;
        } while (true);
    }

    public final void writeArrayFieldStart(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeStartArray();
    }

    public abstract int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException;

    public int writeBinary(InputStream inputStream2, int n) throws IOException {
        return this.writeBinary(Base64Variants.getDefaultVariant(), inputStream2, n);
    }

    public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException;

    public void writeBinary(byte[] arrby) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, 0, arrby.length);
    }

    public void writeBinary(byte[] arrby, int n, int n2) throws IOException {
        this.writeBinary(Base64Variants.getDefaultVariant(), arrby, n, n2);
    }

    public final void writeBinaryField(String string2, byte[] arrby) throws IOException {
        this.writeFieldName(string2);
        this.writeBinary(arrby);
    }

    public abstract void writeBoolean(boolean var1) throws IOException;

    public final void writeBooleanField(String string2, boolean bl) throws IOException {
        this.writeFieldName(string2);
        this.writeBoolean(bl);
    }

    public void writeEmbeddedObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (object instanceof byte[]) {
            this.writeBinary((byte[])object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No native support for writing embedded objects of type ");
        stringBuilder.append(object.getClass().getName());
        throw new JsonGenerationException(stringBuilder.toString(), this);
    }

    public abstract void writeEndArray() throws IOException;

    public abstract void writeEndObject() throws IOException;

    public void writeFieldId(long l) throws IOException {
        this.writeFieldName(Long.toString(l));
    }

    public abstract void writeFieldName(SerializableString var1) throws IOException;

    public abstract void writeFieldName(String var1) throws IOException;

    public abstract void writeNull() throws IOException;

    public final void writeNullField(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeNull();
    }

    public abstract void writeNumber(double var1) throws IOException;

    public abstract void writeNumber(float var1) throws IOException;

    public abstract void writeNumber(int var1) throws IOException;

    public abstract void writeNumber(long var1) throws IOException;

    public abstract void writeNumber(String var1) throws IOException;

    public abstract void writeNumber(BigDecimal var1) throws IOException;

    public abstract void writeNumber(BigInteger var1) throws IOException;

    public void writeNumber(short s) throws IOException {
        this.writeNumber((int)s);
    }

    public final void writeNumberField(String string2, double d) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(d);
    }

    public final void writeNumberField(String string2, float f) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(f);
    }

    public final void writeNumberField(String string2, int n) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(n);
    }

    public final void writeNumberField(String string2, long l) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(l);
    }

    public final void writeNumberField(String string2, BigDecimal bigDecimal) throws IOException {
        this.writeFieldName(string2);
        this.writeNumber(bigDecimal);
    }

    public abstract void writeObject(Object var1) throws IOException;

    public final void writeObjectField(String string2, Object object) throws IOException {
        this.writeFieldName(string2);
        this.writeObject(object);
    }

    public final void writeObjectFieldStart(String string2) throws IOException {
        this.writeFieldName(string2);
        this.writeStartObject();
    }

    public void writeObjectId(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids", this);
    }

    public void writeObjectRef(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Object Ids", this);
    }

    public void writeOmittedField(String string2) throws IOException {
    }

    public abstract void writeRaw(char var1) throws IOException;

    public void writeRaw(SerializableString serializableString) throws IOException {
        this.writeRaw(serializableString.getValue());
    }

    public abstract void writeRaw(String var1) throws IOException;

    public abstract void writeRaw(String var1, int var2, int var3) throws IOException;

    public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException;

    public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException;

    public void writeRawValue(SerializableString serializableString) throws IOException {
        this.writeRawValue(serializableString.getValue());
    }

    public abstract void writeRawValue(String var1) throws IOException;

    public abstract void writeRawValue(String var1, int var2, int var3) throws IOException;

    public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException;

    public abstract void writeStartArray() throws IOException;

    public void writeStartArray(int n) throws IOException {
        this.writeStartArray();
    }

    public void writeStartArray(Object object) throws IOException {
        this.writeStartArray();
        this.setCurrentValue(object);
    }

    public void writeStartArray(Object object, int n) throws IOException {
        this.writeStartArray(n);
        this.setCurrentValue(object);
    }

    public abstract void writeStartObject() throws IOException;

    public void writeStartObject(Object object) throws IOException {
        this.writeStartObject();
        this.setCurrentValue(object);
    }

    public void writeStartObject(Object object, int n) throws IOException {
        this.writeStartObject();
        this.setCurrentValue(object);
    }

    public abstract void writeString(SerializableString var1) throws IOException;

    public void writeString(Reader reader, int n) throws IOException {
        this._reportUnsupportedOperation();
    }

    public abstract void writeString(String var1) throws IOException;

    public abstract void writeString(char[] var1, int var2, int var3) throws IOException;

    public void writeStringField(String string2, String string3) throws IOException {
        this.writeFieldName(string2);
        this.writeString(string3);
    }

    public abstract void writeTree(TreeNode var1) throws IOException;

    public void writeTypeId(Object object) throws IOException {
        throw new JsonGenerationException("No native support for writing Type Ids", this);
    }

    public WritableTypeId writeTypePrefix(WritableTypeId writableTypeId) throws IOException {
        Object object = writableTypeId.id;
        JsonToken jsonToken = writableTypeId.valueShape;
        if (this.canWriteTypeId()) {
            writableTypeId.wrapperWritten = false;
            this.writeTypeId(object);
        } else {
            WritableTypeId.Inclusion inclusion;
            int n;
            object = object instanceof String ? (String)object : String.valueOf(object);
            writableTypeId.wrapperWritten = true;
            WritableTypeId.Inclusion inclusion2 = inclusion = writableTypeId.include;
            if (jsonToken != JsonToken.START_OBJECT) {
                inclusion2 = inclusion;
                if (inclusion.requiresObjectContext()) {
                    writableTypeId.include = inclusion2 = WritableTypeId.Inclusion.WRAPPER_ARRAY;
                }
            }
            if ((n = 1.$SwitchMap$com$fasterxml$jackson$core$type$WritableTypeId$Inclusion[inclusion2.ordinal()]) != 1 && n != 2) {
                if (n == 3) {
                    this.writeStartObject(writableTypeId.forValue);
                    this.writeStringField(writableTypeId.asProperty, (String)object);
                    return writableTypeId;
                }
                if (n != 4) {
                    this.writeStartArray();
                    this.writeString((String)object);
                } else {
                    this.writeStartObject();
                    this.writeFieldName((String)object);
                }
            }
        }
        if (jsonToken == JsonToken.START_OBJECT) {
            this.writeStartObject(writableTypeId.forValue);
            return writableTypeId;
        }
        if (jsonToken != JsonToken.START_ARRAY) return writableTypeId;
        this.writeStartArray();
        return writableTypeId;
    }

    public WritableTypeId writeTypeSuffix(WritableTypeId writableTypeId) throws IOException {
        Object object = writableTypeId.valueShape;
        if (object == JsonToken.START_OBJECT) {
            this.writeEndObject();
        } else if (object == JsonToken.START_ARRAY) {
            this.writeEndArray();
        }
        if (!writableTypeId.wrapperWritten) return writableTypeId;
        int n = 1.$SwitchMap$com$fasterxml$jackson$core$type$WritableTypeId$Inclusion[writableTypeId.include.ordinal()];
        if (n != 1) {
            if (n == 2) return writableTypeId;
            if (n == 3) return writableTypeId;
            if (n != 5) {
                this.writeEndObject();
                return writableTypeId;
            }
            this.writeEndArray();
            return writableTypeId;
        }
        object = writableTypeId.id;
        object = object instanceof String ? (String)object : String.valueOf(object);
        this.writeStringField(writableTypeId.asProperty, (String)object);
        return writableTypeId;
    }

    public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException;

    public static final class Feature
    extends Enum<Feature> {
        private static final /* synthetic */ Feature[] $VALUES;
        public static final /* enum */ Feature AUTO_CLOSE_JSON_CONTENT;
        public static final /* enum */ Feature AUTO_CLOSE_TARGET;
        @Deprecated
        public static final /* enum */ Feature ESCAPE_NON_ASCII;
        public static final /* enum */ Feature FLUSH_PASSED_TO_STREAM;
        public static final /* enum */ Feature IGNORE_UNKNOWN;
        @Deprecated
        public static final /* enum */ Feature QUOTE_FIELD_NAMES;
        @Deprecated
        public static final /* enum */ Feature QUOTE_NON_NUMERIC_NUMBERS;
        public static final /* enum */ Feature STRICT_DUPLICATE_DETECTION;
        public static final /* enum */ Feature WRITE_BIGDECIMAL_AS_PLAIN;
        @Deprecated
        public static final /* enum */ Feature WRITE_NUMBERS_AS_STRINGS;
        private final boolean _defaultState;
        private final int _mask;

        static {
            Feature feature;
            AUTO_CLOSE_TARGET = new Feature(true);
            AUTO_CLOSE_JSON_CONTENT = new Feature(true);
            FLUSH_PASSED_TO_STREAM = new Feature(true);
            QUOTE_FIELD_NAMES = new Feature(true);
            QUOTE_NON_NUMERIC_NUMBERS = new Feature(true);
            ESCAPE_NON_ASCII = new Feature(false);
            WRITE_NUMBERS_AS_STRINGS = new Feature(false);
            WRITE_BIGDECIMAL_AS_PLAIN = new Feature(false);
            STRICT_DUPLICATE_DETECTION = new Feature(false);
            IGNORE_UNKNOWN = feature = new Feature(false);
            $VALUES = new Feature[]{AUTO_CLOSE_TARGET, AUTO_CLOSE_JSON_CONTENT, FLUSH_PASSED_TO_STREAM, QUOTE_FIELD_NAMES, QUOTE_NON_NUMERIC_NUMBERS, ESCAPE_NON_ASCII, WRITE_NUMBERS_AS_STRINGS, WRITE_BIGDECIMAL_AS_PLAIN, STRICT_DUPLICATE_DETECTION, feature};
        }

        private Feature(boolean bl) {
            this._defaultState = bl;
            this._mask = 1 << this.ordinal();
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

}

