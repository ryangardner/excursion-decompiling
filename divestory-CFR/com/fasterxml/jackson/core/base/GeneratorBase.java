/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public abstract class GeneratorBase
extends JsonGenerator {
    protected static final int DERIVED_FEATURES_MASK = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | JsonGenerator.Feature.ESCAPE_NON_ASCII.getMask() | JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.getMask();
    protected static final int MAX_BIG_DECIMAL_SCALE = 9999;
    public static final int SURR1_FIRST = 55296;
    public static final int SURR1_LAST = 56319;
    public static final int SURR2_FIRST = 56320;
    public static final int SURR2_LAST = 57343;
    protected static final String WRITE_BINARY = "write a binary value";
    protected static final String WRITE_BOOLEAN = "write a boolean value";
    protected static final String WRITE_NULL = "write a null";
    protected static final String WRITE_NUMBER = "write a number";
    protected static final String WRITE_RAW = "write a raw (unencoded) value";
    protected static final String WRITE_STRING = "write a string";
    protected boolean _cfgNumbersAsStrings;
    protected boolean _closed;
    protected int _features;
    protected ObjectCodec _objectCodec;
    protected JsonWriteContext _writeContext;

    protected GeneratorBase(int n, ObjectCodec object) {
        this._features = n;
        this._objectCodec = object;
        object = JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n) ? DupDetector.rootDetector(this) : null;
        this._writeContext = JsonWriteContext.createRootContext((DupDetector)object);
        this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(n);
    }

    protected GeneratorBase(int n, ObjectCodec objectCodec, JsonWriteContext jsonWriteContext) {
        this._features = n;
        this._objectCodec = objectCodec;
        this._writeContext = jsonWriteContext;
        this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(n);
    }

    protected String _asString(BigDecimal bigDecimal) throws IOException {
        if (!JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) return bigDecimal.toString();
        int n = bigDecimal.scale();
        if (n >= -9999) {
            if (n <= 9999) return bigDecimal.toPlainString();
        }
        this._reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", n, 9999, 9999));
        return bigDecimal.toPlainString();
    }

    protected void _checkStdFeatureChanges(int n, int n2) {
        if ((DERIVED_FEATURES_MASK & n2) == 0) {
            return;
        }
        this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(n);
        if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(n2)) {
            if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(n)) {
                this.setHighestNonEscapedChar(127);
            } else {
                this.setHighestNonEscapedChar(0);
            }
        }
        if (!JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n2)) return;
        if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n)) {
            if (this._writeContext.getDupDetector() != null) return;
            this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
            return;
        }
        this._writeContext = this._writeContext.withDupDetector(null);
    }

    protected PrettyPrinter _constructDefaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }

    protected final int _decodeSurrogate(int n, int n2) throws IOException {
        if (n2 >= 56320) {
            if (n2 <= 57343) return (n - 55296 << 10) + 65536 + (n2 - 56320);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Incomplete surrogate pair: first char 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(", second 0x");
        stringBuilder.append(Integer.toHexString(n2));
        this._reportError(stringBuilder.toString());
        return (n - 55296 << 10) + 65536 + (n2 - 56320);
    }

    protected abstract void _releaseBuffers();

    protected abstract void _verifyValueWrite(String var1) throws IOException;

    @Override
    public void close() throws IOException {
        this._closed = true;
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        int n = feature.getMask();
        this._features &= n;
        if ((n & DERIVED_FEATURES_MASK) == 0) return this;
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = false;
            return this;
        }
        if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(0);
            return this;
        }
        if (feature != JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION) return this;
        this._writeContext = this._writeContext.withDupDetector(null);
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        int n = feature.getMask();
        this._features |= n;
        if ((n & DERIVED_FEATURES_MASK) == 0) return this;
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = true;
            return this;
        }
        if (feature == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(127);
            return this;
        }
        if (feature != JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION) return this;
        if (this._writeContext.getDupDetector() != null) return this;
        this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
        return this;
    }

    @Override
    public abstract void flush() throws IOException;

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public Object getCurrentValue() {
        return this._writeContext.getCurrentValue();
    }

    @Override
    public int getFeatureMask() {
        return this._features;
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return this._writeContext;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    @Override
    public final boolean isEnabled(JsonGenerator.Feature feature) {
        int n = this._features;
        if ((feature.getMask() & n) == 0) return false;
        return true;
    }

    @Override
    public JsonGenerator overrideStdFeatures(int n, int n2) {
        int n3 = this._features;
        n = n & n2 | n2 & n3;
        n2 = n3 ^ n;
        if (n2 == 0) return this;
        this._features = n;
        this._checkStdFeatureChanges(n, n2);
        return this;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Override
    public void setCurrentValue(Object object) {
        JsonWriteContext jsonWriteContext = this._writeContext;
        if (jsonWriteContext == null) return;
        jsonWriteContext.setCurrentValue(object);
    }

    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(int n) {
        int n2 = this._features ^ n;
        this._features = n;
        if (n2 == 0) return this;
        this._checkStdFeatureChanges(n, n2);
        return this;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() == null) return this.setPrettyPrinter(this._constructDefaultPrettyPrinter());
        return this;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream2, int n) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        this.writeFieldName(serializableString.getValue());
    }

    @Override
    public void writeObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
            return;
        }
        ObjectCodec objectCodec = this._objectCodec;
        if (objectCodec != null) {
            objectCodec.writeValue(this, object);
            return;
        }
        this._writeSimpleObject(object);
    }

    @Override
    public void writeRawValue(SerializableString serializableString) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(serializableString);
    }

    @Override
    public void writeRawValue(String string2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string2);
    }

    @Override
    public void writeRawValue(String string2, int n, int n2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string2, n, n2);
    }

    @Override
    public void writeRawValue(char[] arrc, int n, int n2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(arrc, n, n2);
    }

    @Override
    public void writeStartObject(Object object) throws IOException {
        this.writeStartObject();
        if (object == null) return;
        this.setCurrentValue(object);
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        this.writeString(serializableString.getValue());
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        if (treeNode == null) {
            this.writeNull();
            return;
        }
        ObjectCodec objectCodec = this._objectCodec;
        if (objectCodec == null) throw new IllegalStateException("No ObjectCodec defined");
        objectCodec.writeValue(this, treeNode);
    }
}

