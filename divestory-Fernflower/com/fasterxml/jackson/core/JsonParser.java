package com.fasterxml.jackson.core;

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

public abstract class JsonParser implements Closeable, Versioned {
   private static final int MAX_BYTE_I = 255;
   private static final int MAX_SHORT_I = 32767;
   private static final int MIN_BYTE_I = -128;
   private static final int MIN_SHORT_I = -32768;
   protected int _features;
   protected transient RequestPayload _requestPayload;

   protected JsonParser() {
   }

   protected JsonParser(int var1) {
      this._features = var1;
   }

   protected ObjectCodec _codec() {
      ObjectCodec var1 = this.getCodec();
      if (var1 != null) {
         return var1;
      } else {
         throw new IllegalStateException("No ObjectCodec defined for parser, needed for deserialization");
      }
   }

   protected JsonParseException _constructError(String var1) {
      return (new JsonParseException(this, var1)).withRequestPayload(this._requestPayload);
   }

   protected void _reportUnsupportedOperation() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Operation not supported by parser of type ");
      var1.append(this.getClass().getName());
      throw new UnsupportedOperationException(var1.toString());
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

   public boolean canUseSchema(FormatSchema var1) {
      return false;
   }

   public abstract void clearCurrentToken();

   public abstract void close() throws IOException;

   public JsonParser configure(JsonParser.Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

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

   public JsonParser disable(JsonParser.Feature var1) {
      int var2 = this._features;
      this._features = var1.getMask() & var2;
      return this;
   }

   public JsonParser enable(JsonParser.Feature var1) {
      int var2 = this._features;
      this._features = var1.getMask() | var2;
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
      JsonToken var1 = this.currentToken();
      if (var1 == JsonToken.VALUE_TRUE) {
         return true;
      } else if (var1 == JsonToken.VALUE_FALSE) {
         return false;
      } else {
         throw (new JsonParseException(this, String.format("Current token (%s) not of boolean type", var1))).withRequestPayload(this._requestPayload);
      }
   }

   public byte getByteValue() throws IOException {
      int var1 = this.getIntValue();
      if (var1 >= -128 && var1 <= 255) {
         return (byte)var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Numeric value (");
         var2.append(this.getText());
         var2.append(") out of range of Java byte");
         throw this._constructError(var2.toString());
      }
   }

   public abstract ObjectCodec getCodec();

   public abstract JsonLocation getCurrentLocation();

   public abstract String getCurrentName() throws IOException;

   public abstract JsonToken getCurrentToken();

   public abstract int getCurrentTokenId();

   public Object getCurrentValue() {
      JsonStreamContext var1 = this.getParsingContext();
      Object var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getCurrentValue();
      }

      return var2;
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

   public abstract JsonParser.NumberType getNumberType() throws IOException;

   public abstract Number getNumberValue() throws IOException;

   public Object getObjectId() throws IOException {
      return null;
   }

   public abstract JsonStreamContext getParsingContext();

   public FormatSchema getSchema() {
      return null;
   }

   public short getShortValue() throws IOException {
      int var1 = this.getIntValue();
      if (var1 >= -32768 && var1 <= 32767) {
         return (short)var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Numeric value (");
         var2.append(this.getText());
         var2.append(") out of range of Java short");
         throw this._constructError(var2.toString());
      }
   }

   public int getText(Writer var1) throws IOException, UnsupportedOperationException {
      String var2 = this.getText();
      if (var2 == null) {
         return 0;
      } else {
         var1.write(var2);
         return var2.length();
      }
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

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      return var1;
   }

   public double getValueAsDouble() throws IOException {
      return this.getValueAsDouble(0.0D);
   }

   public double getValueAsDouble(double var1) throws IOException {
      return var1;
   }

   public int getValueAsInt() throws IOException {
      return this.getValueAsInt(0);
   }

   public int getValueAsInt(int var1) throws IOException {
      return var1;
   }

   public long getValueAsLong() throws IOException {
      return this.getValueAsLong(0L);
   }

   public long getValueAsLong(long var1) throws IOException {
      return var1;
   }

   public String getValueAsString() throws IOException {
      return this.getValueAsString((String)null);
   }

   public abstract String getValueAsString(String var1) throws IOException;

   public abstract boolean hasCurrentToken();

   public abstract boolean hasTextCharacters();

   public abstract boolean hasToken(JsonToken var1);

   public abstract boolean hasTokenId(int var1);

   public abstract boolean isClosed();

   public boolean isEnabled(JsonParser.Feature var1) {
      return var1.enabledIn(this._features);
   }

   public boolean isEnabled(StreamReadFeature var1) {
      return var1.mappedFeature().enabledIn(this._features);
   }

   public boolean isExpectedStartArrayToken() {
      boolean var1;
      if (this.currentToken() == JsonToken.START_ARRAY) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isExpectedStartObjectToken() {
      boolean var1;
      if (this.currentToken() == JsonToken.START_OBJECT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNaN() throws IOException {
      return false;
   }

   public Boolean nextBooleanValue() throws IOException {
      JsonToken var1 = this.nextToken();
      if (var1 == JsonToken.VALUE_TRUE) {
         return Boolean.TRUE;
      } else {
         return var1 == JsonToken.VALUE_FALSE ? Boolean.FALSE : null;
      }
   }

   public String nextFieldName() throws IOException {
      String var1;
      if (this.nextToken() == JsonToken.FIELD_NAME) {
         var1 = this.getCurrentName();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean nextFieldName(SerializableString var1) throws IOException {
      boolean var2;
      if (this.nextToken() == JsonToken.FIELD_NAME && var1.getValue().equals(this.getCurrentName())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int nextIntValue(int var1) throws IOException {
      if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
         var1 = this.getIntValue();
      }

      return var1;
   }

   public long nextLongValue(long var1) throws IOException {
      if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
         var1 = this.getLongValue();
      }

      return var1;
   }

   public String nextTextValue() throws IOException {
      String var1;
      if (this.nextToken() == JsonToken.VALUE_STRING) {
         var1 = this.getText();
      } else {
         var1 = null;
      }

      return var1;
   }

   public abstract JsonToken nextToken() throws IOException;

   public abstract JsonToken nextValue() throws IOException;

   public abstract void overrideCurrentName(String var1);

   public JsonParser overrideFormatFeatures(int var1, int var2) {
      return this;
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      return this.setFeatureMask(var1 & var2 | this._features & var2);
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      this._reportUnsupportedOperation();
      return 0;
   }

   public int readBinaryValue(OutputStream var1) throws IOException {
      return this.readBinaryValue(Base64Variants.getDefaultVariant(), var1);
   }

   public <T> T readValueAs(TypeReference<?> var1) throws IOException {
      return this._codec().readValue(this, var1);
   }

   public <T> T readValueAs(Class<T> var1) throws IOException {
      return this._codec().readValue(this, var1);
   }

   public <T extends TreeNode> T readValueAsTree() throws IOException {
      return this._codec().readTree(this);
   }

   public <T> Iterator<T> readValuesAs(TypeReference<T> var1) throws IOException {
      return this._codec().readValues(this, var1);
   }

   public <T> Iterator<T> readValuesAs(Class<T> var1) throws IOException {
      return this._codec().readValues(this, var1);
   }

   public int releaseBuffered(OutputStream var1) throws IOException {
      return -1;
   }

   public int releaseBuffered(Writer var1) throws IOException {
      return -1;
   }

   public boolean requiresCustomCodec() {
      return false;
   }

   public abstract void setCodec(ObjectCodec var1);

   public void setCurrentValue(Object var1) {
      JsonStreamContext var2 = this.getParsingContext();
      if (var2 != null) {
         var2.setCurrentValue(var1);
      }

   }

   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      this._features = var1;
      return this;
   }

   public void setRequestPayloadOnError(RequestPayload var1) {
      this._requestPayload = var1;
   }

   public void setRequestPayloadOnError(String var1) {
      RequestPayload var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new RequestPayload(var1);
      }

      this._requestPayload = var2;
   }

   public void setRequestPayloadOnError(byte[] var1, String var2) {
      RequestPayload var3;
      if (var1 == null) {
         var3 = null;
      } else {
         var3 = new RequestPayload(var1, var2);
      }

      this._requestPayload = var3;
   }

   public void setSchema(FormatSchema var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Parser of type ");
      var2.append(this.getClass().getName());
      var2.append(" does not support schema of type '");
      var2.append(var1.getSchemaType());
      var2.append("'");
      throw new UnsupportedOperationException(var2.toString());
   }

   public abstract JsonParser skipChildren() throws IOException;

   public abstract Version version();

   public static enum Feature {
      @Deprecated
      ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),
      ALLOW_COMMENTS(false),
      @Deprecated
      ALLOW_MISSING_VALUES(false),
      @Deprecated
      ALLOW_NON_NUMERIC_NUMBERS(false),
      @Deprecated
      ALLOW_NUMERIC_LEADING_ZEROS(false),
      ALLOW_SINGLE_QUOTES(false),
      @Deprecated
      ALLOW_TRAILING_COMMA(false),
      @Deprecated
      ALLOW_UNQUOTED_CONTROL_CHARS(false),
      ALLOW_UNQUOTED_FIELD_NAMES(false),
      ALLOW_YAML_COMMENTS(false),
      AUTO_CLOSE_SOURCE(true),
      IGNORE_UNDEFINED(false),
      INCLUDE_SOURCE_IN_LOCATION,
      STRICT_DUPLICATE_DETECTION(false);

      private final boolean _defaultState;
      private final int _mask = 1 << this.ordinal();

      static {
         JsonParser.Feature var0 = new JsonParser.Feature("INCLUDE_SOURCE_IN_LOCATION", 13, true);
         INCLUDE_SOURCE_IN_LOCATION = var0;
      }

      private Feature(boolean var3) {
         this._defaultState = var3;
      }

      public static int collectDefaults() {
         JsonParser.Feature[] var0 = values();
         int var1 = var0.length;
         int var2 = 0;

         int var3;
         int var5;
         for(var3 = 0; var2 < var1; var3 = var5) {
            JsonParser.Feature var4 = var0[var2];
            var5 = var3;
            if (var4.enabledByDefault()) {
               var5 = var3 | var4.getMask();
            }

            ++var2;
         }

         return var3;
      }

      public boolean enabledByDefault() {
         return this._defaultState;
      }

      public boolean enabledIn(int var1) {
         boolean var2;
         if ((var1 & this._mask) != 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int getMask() {
         return this._mask;
      }
   }

   public static enum NumberType {
      BIG_DECIMAL,
      BIG_INTEGER,
      DOUBLE,
      FLOAT,
      INT,
      LONG;

      static {
         JsonParser.NumberType var0 = new JsonParser.NumberType("BIG_DECIMAL", 5);
         BIG_DECIMAL = var0;
      }
   }
}
