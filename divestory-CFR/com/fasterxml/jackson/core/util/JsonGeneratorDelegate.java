/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate
extends JsonGenerator {
    protected JsonGenerator delegate;
    protected boolean delegateCopyMethods;

    public JsonGeneratorDelegate(JsonGenerator jsonGenerator) {
        this(jsonGenerator, true);
    }

    public JsonGeneratorDelegate(JsonGenerator jsonGenerator, boolean bl) {
        this.delegate = jsonGenerator;
        this.delegateCopyMethods = bl;
    }

    @Override
    public boolean canOmitFields() {
        return this.delegate.canOmitFields();
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    @Override
    public boolean canWriteBinaryNatively() {
        return this.delegate.canWriteBinaryNatively();
    }

    @Override
    public boolean canWriteFormattedNumbers() {
        return this.delegate.canWriteFormattedNumbers();
    }

    @Override
    public boolean canWriteObjectId() {
        return this.delegate.canWriteObjectId();
    }

    @Override
    public boolean canWriteTypeId() {
        return this.delegate.canWriteTypeId();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentEvent(jsonParser);
            return;
        }
        super.copyCurrentEvent(jsonParser);
    }

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentStructure(jsonParser);
            return;
        }
        super.copyCurrentStructure(jsonParser);
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this.delegate.disable(feature);
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this.delegate.enable(feature);
        return this;
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this.delegate.getCharacterEscapes();
    }

    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }

    public JsonGenerator getDelegate() {
        return this.delegate;
    }

    @Override
    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }

    @Override
    public int getHighestEscapedChar() {
        return this.delegate.getHighestEscapedChar();
    }

    @Override
    public int getOutputBuffered() {
        return this.delegate.getOutputBuffered();
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }

    @Override
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }

    @Override
    public PrettyPrinter getPrettyPrinter() {
        return this.delegate.getPrettyPrinter();
    }

    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }

    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override
    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    @Override
    public JsonGenerator overrideFormatFeatures(int n, int n2) {
        this.delegate.overrideFormatFeatures(n, n2);
        return this;
    }

    @Override
    public JsonGenerator overrideStdFeatures(int n, int n2) {
        this.delegate.overrideStdFeatures(n, n2);
        return this;
    }

    @Override
    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        this.delegate.setCharacterEscapes(characterEscapes);
        return this;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
        return this;
    }

    @Override
    public void setCurrentValue(Object object) {
        this.delegate.setCurrentValue(object);
    }

    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(int n) {
        this.delegate.setFeatureMask(n);
        return this;
    }

    @Override
    public JsonGenerator setHighestNonEscapedChar(int n) {
        this.delegate.setHighestNonEscapedChar(n);
        return this;
    }

    @Override
    public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
        this.delegate.setPrettyPrinter(prettyPrinter);
        return this;
    }

    @Override
    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        this.delegate.setRootValueSeparator(serializableString);
        return this;
    }

    @Override
    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }

    @Override
    public Version version() {
        return this.delegate.version();
    }

    @Override
    public void writeArray(double[] arrd, int n, int n2) throws IOException {
        this.delegate.writeArray(arrd, n, n2);
    }

    @Override
    public void writeArray(int[] arrn, int n, int n2) throws IOException {
        this.delegate.writeArray(arrn, n, n2);
    }

    @Override
    public void writeArray(long[] arrl, int n, int n2) throws IOException {
        this.delegate.writeArray(arrl, n, n2);
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream2, int n) throws IOException {
        return this.delegate.writeBinary(base64Variant, inputStream2, n);
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException {
        this.delegate.writeBinary(base64Variant, arrby, n, n2);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.delegate.writeBoolean(bl);
    }

    @Override
    public void writeEmbeddedObject(Object object) throws IOException {
        this.delegate.writeEmbeddedObject(object);
    }

    @Override
    public void writeEndArray() throws IOException {
        this.delegate.writeEndArray();
    }

    @Override
    public void writeEndObject() throws IOException {
        this.delegate.writeEndObject();
    }

    @Override
    public void writeFieldId(long l) throws IOException {
        this.delegate.writeFieldId(l);
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        this.delegate.writeFieldName(serializableString);
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
        this.delegate.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        this.delegate.writeNumber(d);
    }

    @Override
    public void writeNumber(float f) throws IOException {
        this.delegate.writeNumber(f);
    }

    @Override
    public void writeNumber(int n) throws IOException {
        this.delegate.writeNumber(n);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        this.delegate.writeNumber(l);
    }

    @Override
    public void writeNumber(String string2) throws IOException, UnsupportedOperationException {
        this.delegate.writeNumber(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this.delegate.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this.delegate.writeNumber(bigInteger);
    }

    @Override
    public void writeNumber(short s) throws IOException {
        this.delegate.writeNumber(s);
    }

    @Override
    public void writeObject(Object object) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeObject(object);
            return;
        }
        if (object == null) {
            this.writeNull();
            return;
        }
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec != null) {
            objectCodec.writeValue(this, object);
            return;
        }
        this._writeSimpleObject(object);
    }

    @Override
    public void writeObjectId(Object object) throws IOException {
        this.delegate.writeObjectId(object);
    }

    @Override
    public void writeObjectRef(Object object) throws IOException {
        this.delegate.writeObjectRef(object);
    }

    @Override
    public void writeOmittedField(String string2) throws IOException {
        this.delegate.writeOmittedField(string2);
    }

    @Override
    public void writeRaw(char c) throws IOException {
        this.delegate.writeRaw(c);
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        this.delegate.writeRaw(serializableString);
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        this.delegate.writeRaw(string2);
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException {
        this.delegate.writeRaw(string2, n, n2);
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException {
        this.delegate.writeRaw(arrc, n, n2);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this.delegate.writeRawUTF8String(arrby, n, n2);
    }

    @Override
    public void writeRawValue(String string2) throws IOException {
        this.delegate.writeRawValue(string2);
    }

    @Override
    public void writeRawValue(String string2, int n, int n2) throws IOException {
        this.delegate.writeRawValue(string2, n, n2);
    }

    @Override
    public void writeRawValue(char[] arrc, int n, int n2) throws IOException {
        this.delegate.writeRawValue(arrc, n, n2);
    }

    @Override
    public void writeStartArray() throws IOException {
        this.delegate.writeStartArray();
    }

    @Override
    public void writeStartArray(int n) throws IOException {
        this.delegate.writeStartArray(n);
    }

    @Override
    public void writeStartArray(Object object) throws IOException {
        this.delegate.writeStartArray(object);
    }

    @Override
    public void writeStartArray(Object object, int n) throws IOException {
        this.delegate.writeStartArray(object, n);
    }

    @Override
    public void writeStartObject() throws IOException {
        this.delegate.writeStartObject();
    }

    @Override
    public void writeStartObject(Object object) throws IOException {
        this.delegate.writeStartObject(object);
    }

    @Override
    public void writeStartObject(Object object, int n) throws IOException {
        this.delegate.writeStartObject(object, n);
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        this.delegate.writeString(serializableString);
    }

    @Override
    public void writeString(Reader reader, int n) throws IOException {
        this.delegate.writeString(reader, n);
    }

    @Override
    public void writeString(String string2) throws IOException {
        this.delegate.writeString(string2);
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException {
        this.delegate.writeString(arrc, n, n2);
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeTree(treeNode);
            return;
        }
        if (treeNode == null) {
            this.writeNull();
            return;
        }
        ObjectCodec objectCodec = this.getCodec();
        if (objectCodec == null) throw new IllegalStateException("No ObjectCodec defined");
        objectCodec.writeTree(this, treeNode);
    }

    @Override
    public void writeTypeId(Object object) throws IOException {
        this.delegate.writeTypeId(object);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this.delegate.writeUTF8String(arrby, n, n2);
    }
}

