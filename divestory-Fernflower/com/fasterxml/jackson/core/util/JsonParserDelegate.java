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

public class JsonParserDelegate extends JsonParser {
   protected JsonParser delegate;

   public JsonParserDelegate(JsonParser var1) {
      this.delegate = var1;
   }

   public boolean canReadObjectId() {
      return this.delegate.canReadObjectId();
   }

   public boolean canReadTypeId() {
      return this.delegate.canReadTypeId();
   }

   public boolean canUseSchema(FormatSchema var1) {
      return this.delegate.canUseSchema(var1);
   }

   public void clearCurrentToken() {
      this.delegate.clearCurrentToken();
   }

   public void close() throws IOException {
      this.delegate.close();
   }

   public JsonToken currentToken() {
      return this.delegate.currentToken();
   }

   public int currentTokenId() {
      return this.delegate.currentTokenId();
   }

   public JsonParser delegate() {
      return this.delegate;
   }

   public JsonParser disable(JsonParser.Feature var1) {
      this.delegate.disable(var1);
      return this;
   }

   public JsonParser enable(JsonParser.Feature var1) {
      this.delegate.enable(var1);
      return this;
   }

   public void finishToken() throws IOException {
      this.delegate.finishToken();
   }

   public BigInteger getBigIntegerValue() throws IOException {
      return this.delegate.getBigIntegerValue();
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      return this.delegate.getBinaryValue(var1);
   }

   public boolean getBooleanValue() throws IOException {
      return this.delegate.getBooleanValue();
   }

   public byte getByteValue() throws IOException {
      return this.delegate.getByteValue();
   }

   public ObjectCodec getCodec() {
      return this.delegate.getCodec();
   }

   public JsonLocation getCurrentLocation() {
      return this.delegate.getCurrentLocation();
   }

   public String getCurrentName() throws IOException {
      return this.delegate.getCurrentName();
   }

   public JsonToken getCurrentToken() {
      return this.delegate.getCurrentToken();
   }

   public int getCurrentTokenId() {
      return this.delegate.getCurrentTokenId();
   }

   public Object getCurrentValue() {
      return this.delegate.getCurrentValue();
   }

   public BigDecimal getDecimalValue() throws IOException {
      return this.delegate.getDecimalValue();
   }

   public double getDoubleValue() throws IOException {
      return this.delegate.getDoubleValue();
   }

   public Object getEmbeddedObject() throws IOException {
      return this.delegate.getEmbeddedObject();
   }

   public int getFeatureMask() {
      return this.delegate.getFeatureMask();
   }

   public float getFloatValue() throws IOException {
      return this.delegate.getFloatValue();
   }

   public Object getInputSource() {
      return this.delegate.getInputSource();
   }

   public int getIntValue() throws IOException {
      return this.delegate.getIntValue();
   }

   public JsonToken getLastClearedToken() {
      return this.delegate.getLastClearedToken();
   }

   public long getLongValue() throws IOException {
      return this.delegate.getLongValue();
   }

   public JsonParser.NumberType getNumberType() throws IOException {
      return this.delegate.getNumberType();
   }

   public Number getNumberValue() throws IOException {
      return this.delegate.getNumberValue();
   }

   public Object getObjectId() throws IOException {
      return this.delegate.getObjectId();
   }

   public JsonStreamContext getParsingContext() {
      return this.delegate.getParsingContext();
   }

   public FormatSchema getSchema() {
      return this.delegate.getSchema();
   }

   public short getShortValue() throws IOException {
      return this.delegate.getShortValue();
   }

   public int getText(Writer var1) throws IOException, UnsupportedOperationException {
      return this.delegate.getText(var1);
   }

   public String getText() throws IOException {
      return this.delegate.getText();
   }

   public char[] getTextCharacters() throws IOException {
      return this.delegate.getTextCharacters();
   }

   public int getTextLength() throws IOException {
      return this.delegate.getTextLength();
   }

   public int getTextOffset() throws IOException {
      return this.delegate.getTextOffset();
   }

   public JsonLocation getTokenLocation() {
      return this.delegate.getTokenLocation();
   }

   public Object getTypeId() throws IOException {
      return this.delegate.getTypeId();
   }

   public boolean getValueAsBoolean() throws IOException {
      return this.delegate.getValueAsBoolean();
   }

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      return this.delegate.getValueAsBoolean(var1);
   }

   public double getValueAsDouble() throws IOException {
      return this.delegate.getValueAsDouble();
   }

   public double getValueAsDouble(double var1) throws IOException {
      return this.delegate.getValueAsDouble(var1);
   }

   public int getValueAsInt() throws IOException {
      return this.delegate.getValueAsInt();
   }

   public int getValueAsInt(int var1) throws IOException {
      return this.delegate.getValueAsInt(var1);
   }

   public long getValueAsLong() throws IOException {
      return this.delegate.getValueAsLong();
   }

   public long getValueAsLong(long var1) throws IOException {
      return this.delegate.getValueAsLong(var1);
   }

   public String getValueAsString() throws IOException {
      return this.delegate.getValueAsString();
   }

   public String getValueAsString(String var1) throws IOException {
      return this.delegate.getValueAsString(var1);
   }

   public boolean hasCurrentToken() {
      return this.delegate.hasCurrentToken();
   }

   public boolean hasTextCharacters() {
      return this.delegate.hasTextCharacters();
   }

   public boolean hasToken(JsonToken var1) {
      return this.delegate.hasToken(var1);
   }

   public boolean hasTokenId(int var1) {
      return this.delegate.hasTokenId(var1);
   }

   public boolean isClosed() {
      return this.delegate.isClosed();
   }

   public boolean isEnabled(JsonParser.Feature var1) {
      return this.delegate.isEnabled(var1);
   }

   public boolean isExpectedStartArrayToken() {
      return this.delegate.isExpectedStartArrayToken();
   }

   public boolean isExpectedStartObjectToken() {
      return this.delegate.isExpectedStartObjectToken();
   }

   public boolean isNaN() throws IOException {
      return this.delegate.isNaN();
   }

   public JsonToken nextToken() throws IOException {
      return this.delegate.nextToken();
   }

   public JsonToken nextValue() throws IOException {
      return this.delegate.nextValue();
   }

   public void overrideCurrentName(String var1) {
      this.delegate.overrideCurrentName(var1);
   }

   public JsonParser overrideFormatFeatures(int var1, int var2) {
      this.delegate.overrideFormatFeatures(var1, var2);
      return this;
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      this.delegate.overrideStdFeatures(var1, var2);
      return this;
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      return this.delegate.readBinaryValue(var1, var2);
   }

   public boolean requiresCustomCodec() {
      return this.delegate.requiresCustomCodec();
   }

   public void setCodec(ObjectCodec var1) {
      this.delegate.setCodec(var1);
   }

   public void setCurrentValue(Object var1) {
      this.delegate.setCurrentValue(var1);
   }

   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      this.delegate.setFeatureMask(var1);
      return this;
   }

   public void setSchema(FormatSchema var1) {
      this.delegate.setSchema(var1);
   }

   public JsonParser skipChildren() throws IOException {
      this.delegate.skipChildren();
      return this;
   }

   public Version version() {
      return this.delegate.version();
   }
}
