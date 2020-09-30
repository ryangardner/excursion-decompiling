/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonParserDelegate
extends JsonParser {
    protected JsonParser delegate;

    public JsonParserDelegate(JsonParser jsonParser) {
        this.delegate = jsonParser;
    }

    @Override
    public boolean canReadObjectId() {
        return this.delegate.canReadObjectId();
    }

    @Override
    public boolean canReadTypeId() {
        return this.delegate.canReadTypeId();
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    @Override
    public void clearCurrentToken() {
        this.delegate.clearCurrentToken();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public JsonToken currentToken() {
        return this.delegate.currentToken();
    }

    @Override
    public int currentTokenId() {
        return this.delegate.currentTokenId();
    }

    public JsonParser delegate() {
        return this.delegate;
    }

    @Override
    public JsonParser disable(JsonParser.Feature feature) {
        this.delegate.disable(feature);
        return this;
    }

    @Override
    public JsonParser enable(JsonParser.Feature feature) {
        this.delegate.enable(feature);
        return this;
    }

    @Override
    public void finishToken() throws IOException {
        this.delegate.finishToken();
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        return this.delegate.getBinaryValue(base64Variant);
    }

    @Override
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }

    @Override
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }

    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }

    @Override
    public String getCurrentName() throws IOException {
        return this.delegate.getCurrentName();
    }

    @Override
    public JsonToken getCurrentToken() {
        return this.delegate.getCurrentToken();
    }

    @Override
    public int getCurrentTokenId() {
        return this.delegate.getCurrentTokenId();
    }

    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }

    @Override
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }

    @Override
    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }

    @Override
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }

    @Override
    public Object getInputSource() {
        return this.delegate.getInputSource();
    }

    @Override
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }

    @Override
    public JsonToken getLastClearedToken() {
        return this.delegate.getLastClearedToken();
    }

    @Override
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }

    @Override
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }

    @Override
    public Object getObjectId() throws IOException {
        return this.delegate.getObjectId();
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return this.delegate.getParsingContext();
    }

    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }

    @Override
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }

    @Override
    public int getText(Writer writer) throws IOException, UnsupportedOperationException {
        return this.delegate.getText(writer);
    }

    @Override
    public String getText() throws IOException {
        return this.delegate.getText();
    }

    @Override
    public char[] getTextCharacters() throws IOException {
        return this.delegate.getTextCharacters();
    }

    @Override
    public int getTextLength() throws IOException {
        return this.delegate.getTextLength();
    }

    @Override
    public int getTextOffset() throws IOException {
        return this.delegate.getTextOffset();
    }

    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }

    @Override
    public Object getTypeId() throws IOException {
        return this.delegate.getTypeId();
    }

    @Override
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }

    @Override
    public boolean getValueAsBoolean(boolean bl) throws IOException {
        return this.delegate.getValueAsBoolean(bl);
    }

    @Override
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }

    @Override
    public double getValueAsDouble(double d) throws IOException {
        return this.delegate.getValueAsDouble(d);
    }

    @Override
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }

    @Override
    public int getValueAsInt(int n) throws IOException {
        return this.delegate.getValueAsInt(n);
    }

    @Override
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }

    @Override
    public long getValueAsLong(long l) throws IOException {
        return this.delegate.getValueAsLong(l);
    }

    @Override
    public String getValueAsString() throws IOException {
        return this.delegate.getValueAsString();
    }

    @Override
    public String getValueAsString(String string2) throws IOException {
        return this.delegate.getValueAsString(string2);
    }

    @Override
    public boolean hasCurrentToken() {
        return this.delegate.hasCurrentToken();
    }

    @Override
    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }

    @Override
    public boolean hasToken(JsonToken jsonToken) {
        return this.delegate.hasToken(jsonToken);
    }

    @Override
    public boolean hasTokenId(int n) {
        return this.delegate.hasTokenId(n);
    }

    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override
    public boolean isEnabled(JsonParser.Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    @Override
    public boolean isExpectedStartArrayToken() {
        return this.delegate.isExpectedStartArrayToken();
    }

    @Override
    public boolean isExpectedStartObjectToken() {
        return this.delegate.isExpectedStartObjectToken();
    }

    @Override
    public boolean isNaN() throws IOException {
        return this.delegate.isNaN();
    }

    @Override
    public JsonToken nextToken() throws IOException {
        return this.delegate.nextToken();
    }

    @Override
    public JsonToken nextValue() throws IOException {
        return this.delegate.nextValue();
    }

    @Override
    public void overrideCurrentName(String string2) {
        this.delegate.overrideCurrentName(string2);
    }

    @Override
    public JsonParser overrideFormatFeatures(int n, int n2) {
        this.delegate.overrideFormatFeatures(n, n2);
        return this;
    }

    @Override
    public JsonParser overrideStdFeatures(int n, int n2) {
        this.delegate.overrideStdFeatures(n, n2);
        return this;
    }

    @Override
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream2) throws IOException {
        return this.delegate.readBinaryValue(base64Variant, outputStream2);
    }

    @Override
    public boolean requiresCustomCodec() {
        return this.delegate.requiresCustomCodec();
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
    }

    @Override
    public void setCurrentValue(Object object) {
        this.delegate.setCurrentValue(object);
    }

    @Deprecated
    @Override
    public JsonParser setFeatureMask(int n) {
        this.delegate.setFeatureMask(n);
        return this;
    }

    @Override
    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        this.delegate.skipChildren();
        return this;
    }

    @Override
    public Version version() {
        return this.delegate.version();
    }
}

