package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class JsonGenerator implements Closeable, Flushable, Versioned {
   protected PrettyPrinter _cfgPrettyPrinter;

   protected JsonGenerator() {
   }

   protected void _copyCurrentContents(JsonParser var1) throws IOException {
      int var2 = 1;

      while(true) {
         JsonToken var3 = var1.nextToken();
         if (var3 == null) {
            return;
         }

         int var4;
         JsonParser.NumberType var6;
         switch(var3.id()) {
         case 1:
            this.writeStartObject();
            break;
         case 2:
            this.writeEndObject();
            var4 = var2 - 1;
            var2 = var4;
            if (var4 == 0) {
               return;
            }
            continue;
         case 3:
            this.writeStartArray();
            break;
         case 4:
            this.writeEndArray();
            var4 = var2 - 1;
            var2 = var4;
            if (var4 == 0) {
               return;
            }
            continue;
         case 5:
            this.writeFieldName(var1.getCurrentName());
            continue;
         case 6:
            if (var1.hasTextCharacters()) {
               this.writeString(var1.getTextCharacters(), var1.getTextOffset(), var1.getTextLength());
            } else {
               this.writeString(var1.getText());
            }
            continue;
         case 7:
            var6 = var1.getNumberType();
            if (var6 == JsonParser.NumberType.INT) {
               this.writeNumber(var1.getIntValue());
            } else if (var6 == JsonParser.NumberType.BIG_INTEGER) {
               this.writeNumber(var1.getBigIntegerValue());
            } else {
               this.writeNumber(var1.getLongValue());
            }
            continue;
         case 8:
            var6 = var1.getNumberType();
            if (var6 == JsonParser.NumberType.BIG_DECIMAL) {
               this.writeNumber(var1.getDecimalValue());
            } else if (var6 == JsonParser.NumberType.FLOAT) {
               this.writeNumber(var1.getFloatValue());
            } else {
               this.writeNumber(var1.getDoubleValue());
            }
            continue;
         case 9:
            this.writeBoolean(true);
            continue;
         case 10:
            this.writeBoolean(false);
            continue;
         case 11:
            this.writeNull();
            continue;
         case 12:
            this.writeObject(var1.getEmbeddedObject());
            continue;
         default:
            StringBuilder var5 = new StringBuilder();
            var5.append("Internal error: unknown current token, ");
            var5.append(var3);
            throw new IllegalStateException(var5.toString());
         }

         ++var2;
      }
   }

   protected void _reportError(String var1) throws JsonGenerationException {
      throw new JsonGenerationException(var1, this);
   }

   protected void _reportUnsupportedOperation() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Operation not supported by generator of type ");
      var1.append(this.getClass().getName());
      throw new UnsupportedOperationException(var1.toString());
   }

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   protected final void _verifyOffsets(int var1, int var2, int var3) {
      if (var2 < 0 || var2 + var3 > var1) {
         throw new IllegalArgumentException(String.format("invalid argument(s) (offset=%d, length=%d) for input array of %d element", var2, var3, var1));
      }
   }

   protected void _writeSimpleObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else if (var1 instanceof String) {
         this.writeString((String)var1);
      } else {
         if (var1 instanceof Number) {
            Number var2 = (Number)var1;
            if (var2 instanceof Integer) {
               this.writeNumber(var2.intValue());
               return;
            }

            if (var2 instanceof Long) {
               this.writeNumber(var2.longValue());
               return;
            }

            if (var2 instanceof Double) {
               this.writeNumber(var2.doubleValue());
               return;
            }

            if (var2 instanceof Float) {
               this.writeNumber(var2.floatValue());
               return;
            }

            if (var2 instanceof Short) {
               this.writeNumber(var2.shortValue());
               return;
            }

            if (var2 instanceof Byte) {
               this.writeNumber((short)var2.byteValue());
               return;
            }

            if (var2 instanceof BigInteger) {
               this.writeNumber((BigInteger)var2);
               return;
            }

            if (var2 instanceof BigDecimal) {
               this.writeNumber((BigDecimal)var2);
               return;
            }

            if (var2 instanceof AtomicInteger) {
               this.writeNumber(((AtomicInteger)var2).get());
               return;
            }

            if (var2 instanceof AtomicLong) {
               this.writeNumber(((AtomicLong)var2).get());
               return;
            }
         } else {
            if (var1 instanceof byte[]) {
               this.writeBinary((byte[])var1);
               return;
            }

            if (var1 instanceof Boolean) {
               this.writeBoolean((Boolean)var1);
               return;
            }

            if (var1 instanceof AtomicBoolean) {
               this.writeBoolean(((AtomicBoolean)var1).get());
               return;
            }
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("No ObjectCodec defined for the generator, can only serialize simple wrapper types (type passed ");
         var3.append(var1.getClass().getName());
         var3.append(")");
         throw new IllegalStateException(var3.toString());
      }
   }

   public boolean canOmitFields() {
      return true;
   }

   public boolean canUseSchema(FormatSchema var1) {
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

   public abstract void close() throws IOException;

   public final JsonGenerator configure(JsonGenerator.Feature var1, boolean var2) {
      if (var2) {
         this.enable(var1);
      } else {
         this.disable(var1);
      }

      return this;
   }

   public void copyCurrentEvent(JsonParser var1) throws IOException {
      JsonToken var2 = var1.currentToken();
      int var3;
      if (var2 == null) {
         var3 = -1;
      } else {
         var3 = var2.id();
      }

      JsonParser.NumberType var5;
      switch(var3) {
      case -1:
         this._reportError("No current event to copy");
         break;
      case 0:
      default:
         StringBuilder var4 = new StringBuilder();
         var4.append("Internal error: unknown current token, ");
         var4.append(var2);
         throw new IllegalStateException(var4.toString());
      case 1:
         this.writeStartObject();
         break;
      case 2:
         this.writeEndObject();
         break;
      case 3:
         this.writeStartArray();
         break;
      case 4:
         this.writeEndArray();
         break;
      case 5:
         this.writeFieldName(var1.getCurrentName());
         break;
      case 6:
         if (var1.hasTextCharacters()) {
            this.writeString(var1.getTextCharacters(), var1.getTextOffset(), var1.getTextLength());
         } else {
            this.writeString(var1.getText());
         }
         break;
      case 7:
         var5 = var1.getNumberType();
         if (var5 == JsonParser.NumberType.INT) {
            this.writeNumber(var1.getIntValue());
         } else if (var5 == JsonParser.NumberType.BIG_INTEGER) {
            this.writeNumber(var1.getBigIntegerValue());
         } else {
            this.writeNumber(var1.getLongValue());
         }
         break;
      case 8:
         var5 = var1.getNumberType();
         if (var5 == JsonParser.NumberType.BIG_DECIMAL) {
            this.writeNumber(var1.getDecimalValue());
         } else if (var5 == JsonParser.NumberType.FLOAT) {
            this.writeNumber(var1.getFloatValue());
         } else {
            this.writeNumber(var1.getDoubleValue());
         }
         break;
      case 9:
         this.writeBoolean(true);
         break;
      case 10:
         this.writeBoolean(false);
         break;
      case 11:
         this.writeNull();
         break;
      case 12:
         this.writeObject(var1.getEmbeddedObject());
      }

   }

   public void copyCurrentStructure(JsonParser var1) throws IOException {
      JsonToken var2 = var1.currentToken();
      byte var3 = -1;
      int var4;
      if (var2 == null) {
         var4 = -1;
      } else {
         var4 = var2.id();
      }

      int var5 = var4;
      if (var4 == 5) {
         this.writeFieldName(var1.getCurrentName());
         var2 = var1.nextToken();
         if (var2 == null) {
            var4 = var3;
         } else {
            var4 = var2.id();
         }

         var5 = var4;
      }

      if (var5 != 1) {
         if (var5 != 3) {
            this.copyCurrentEvent(var1);
         } else {
            this.writeStartArray();
            this._copyCurrentContents(var1);
         }
      } else {
         this.writeStartObject();
         this._copyCurrentContents(var1);
      }
   }

   public abstract JsonGenerator disable(JsonGenerator.Feature var1);

   public abstract JsonGenerator enable(JsonGenerator.Feature var1);

   public abstract void flush() throws IOException;

   public CharacterEscapes getCharacterEscapes() {
      return null;
   }

   public abstract ObjectCodec getCodec();

   public Object getCurrentValue() {
      JsonStreamContext var1 = this.getOutputContext();
      Object var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getCurrentValue();
      }

      return var2;
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

   public abstract boolean isEnabled(JsonGenerator.Feature var1);

   public boolean isEnabled(StreamWriteFeature var1) {
      return this.isEnabled(var1.mappedFeature());
   }

   public JsonGenerator overrideFormatFeatures(int var1, int var2) {
      return this;
   }

   public JsonGenerator overrideStdFeatures(int var1, int var2) {
      return this.setFeatureMask(var1 & var2 | this.getFeatureMask() & var2);
   }

   public JsonGenerator setCharacterEscapes(CharacterEscapes var1) {
      return this;
   }

   public abstract JsonGenerator setCodec(ObjectCodec var1);

   public void setCurrentValue(Object var1) {
      JsonStreamContext var2 = this.getOutputContext();
      if (var2 != null) {
         var2.setCurrentValue(var1);
      }

   }

   @Deprecated
   public abstract JsonGenerator setFeatureMask(int var1);

   public JsonGenerator setHighestNonEscapedChar(int var1) {
      return this;
   }

   public JsonGenerator setPrettyPrinter(PrettyPrinter var1) {
      this._cfgPrettyPrinter = var1;
      return this;
   }

   public JsonGenerator setRootValueSeparator(SerializableString var1) {
      throw new UnsupportedOperationException();
   }

   public void setSchema(FormatSchema var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Generator of type ");
      var2.append(this.getClass().getName());
      var2.append(" does not support schema of type '");
      var2.append(var1.getSchemaType());
      var2.append("'");
      throw new UnsupportedOperationException(var2.toString());
   }

   public abstract JsonGenerator useDefaultPrettyPrinter();

   public abstract Version version();

   public void writeArray(double[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray(var1, var3);

         for(int var4 = var2; var4 < var3 + var2; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public void writeArray(int[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray(var1, var3);

         for(int var4 = var2; var4 < var3 + var2; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public void writeArray(long[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("null array");
      } else {
         this._verifyOffsets(var1.length, var2, var3);
         this.writeStartArray(var1, var3);

         for(int var4 = var2; var4 < var3 + var2; ++var4) {
            this.writeNumber(var1[var4]);
         }

         this.writeEndArray();
      }
   }

   public final void writeArrayFieldStart(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeStartArray();
   }

   public abstract int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException;

   public int writeBinary(InputStream var1, int var2) throws IOException {
      return this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2);
   }

   public abstract void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException;

   public void writeBinary(byte[] var1) throws IOException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, 0, var1.length);
   }

   public void writeBinary(byte[] var1, int var2, int var3) throws IOException {
      this.writeBinary(Base64Variants.getDefaultVariant(), var1, var2, var3);
   }

   public final void writeBinaryField(String var1, byte[] var2) throws IOException {
      this.writeFieldName(var1);
      this.writeBinary(var2);
   }

   public abstract void writeBoolean(boolean var1) throws IOException;

   public final void writeBooleanField(String var1, boolean var2) throws IOException {
      this.writeFieldName(var1);
      this.writeBoolean(var2);
   }

   public void writeEmbeddedObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else if (var1 instanceof byte[]) {
         this.writeBinary((byte[])var1);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("No native support for writing embedded objects of type ");
         var2.append(var1.getClass().getName());
         throw new JsonGenerationException(var2.toString(), this);
      }
   }

   public abstract void writeEndArray() throws IOException;

   public abstract void writeEndObject() throws IOException;

   public void writeFieldId(long var1) throws IOException {
      this.writeFieldName(Long.toString(var1));
   }

   public abstract void writeFieldName(SerializableString var1) throws IOException;

   public abstract void writeFieldName(String var1) throws IOException;

   public abstract void writeNull() throws IOException;

   public final void writeNullField(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeNull();
   }

   public abstract void writeNumber(double var1) throws IOException;

   public abstract void writeNumber(float var1) throws IOException;

   public abstract void writeNumber(int var1) throws IOException;

   public abstract void writeNumber(long var1) throws IOException;

   public abstract void writeNumber(String var1) throws IOException;

   public abstract void writeNumber(BigDecimal var1) throws IOException;

   public abstract void writeNumber(BigInteger var1) throws IOException;

   public void writeNumber(short var1) throws IOException {
      this.writeNumber((int)var1);
   }

   public final void writeNumberField(String var1, double var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, float var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, int var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, long var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public final void writeNumberField(String var1, BigDecimal var2) throws IOException {
      this.writeFieldName(var1);
      this.writeNumber(var2);
   }

   public abstract void writeObject(Object var1) throws IOException;

   public final void writeObjectField(String var1, Object var2) throws IOException {
      this.writeFieldName(var1);
      this.writeObject(var2);
   }

   public final void writeObjectFieldStart(String var1) throws IOException {
      this.writeFieldName(var1);
      this.writeStartObject();
   }

   public void writeObjectId(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Object Ids", this);
   }

   public void writeObjectRef(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Object Ids", this);
   }

   public void writeOmittedField(String var1) throws IOException {
   }

   public abstract void writeRaw(char var1) throws IOException;

   public void writeRaw(SerializableString var1) throws IOException {
      this.writeRaw(var1.getValue());
   }

   public abstract void writeRaw(String var1) throws IOException;

   public abstract void writeRaw(String var1, int var2, int var3) throws IOException;

   public abstract void writeRaw(char[] var1, int var2, int var3) throws IOException;

   public abstract void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException;

   public void writeRawValue(SerializableString var1) throws IOException {
      this.writeRawValue(var1.getValue());
   }

   public abstract void writeRawValue(String var1) throws IOException;

   public abstract void writeRawValue(String var1, int var2, int var3) throws IOException;

   public abstract void writeRawValue(char[] var1, int var2, int var3) throws IOException;

   public abstract void writeStartArray() throws IOException;

   public void writeStartArray(int var1) throws IOException {
      this.writeStartArray();
   }

   public void writeStartArray(Object var1) throws IOException {
      this.writeStartArray();
      this.setCurrentValue(var1);
   }

   public void writeStartArray(Object var1, int var2) throws IOException {
      this.writeStartArray(var2);
      this.setCurrentValue(var1);
   }

   public abstract void writeStartObject() throws IOException;

   public void writeStartObject(Object var1) throws IOException {
      this.writeStartObject();
      this.setCurrentValue(var1);
   }

   public void writeStartObject(Object var1, int var2) throws IOException {
      this.writeStartObject();
      this.setCurrentValue(var1);
   }

   public abstract void writeString(SerializableString var1) throws IOException;

   public void writeString(Reader var1, int var2) throws IOException {
      this._reportUnsupportedOperation();
   }

   public abstract void writeString(String var1) throws IOException;

   public abstract void writeString(char[] var1, int var2, int var3) throws IOException;

   public void writeStringField(String var1, String var2) throws IOException {
      this.writeFieldName(var1);
      this.writeString(var2);
   }

   public abstract void writeTree(TreeNode var1) throws IOException;

   public void writeTypeId(Object var1) throws IOException {
      throw new JsonGenerationException("No native support for writing Type Ids", this);
   }

   public WritableTypeId writeTypePrefix(WritableTypeId var1) throws IOException {
      Object var2 = var1.id;
      JsonToken var3 = var1.valueShape;
      if (this.canWriteTypeId()) {
         var1.wrapperWritten = false;
         this.writeTypeId(var2);
      } else {
         String var7;
         if (var2 instanceof String) {
            var7 = (String)var2;
         } else {
            var7 = String.valueOf(var2);
         }

         var1.wrapperWritten = true;
         WritableTypeId.Inclusion var4 = var1.include;
         WritableTypeId.Inclusion var5 = var4;
         if (var3 != JsonToken.START_OBJECT) {
            var5 = var4;
            if (var4.requiresObjectContext()) {
               var5 = WritableTypeId.Inclusion.WRAPPER_ARRAY;
               var1.include = var5;
            }
         }

         int var6 = null.$SwitchMap$com$fasterxml$jackson$core$type$WritableTypeId$Inclusion[var5.ordinal()];
         if (var6 != 1 && var6 != 2) {
            if (var6 == 3) {
               this.writeStartObject(var1.forValue);
               this.writeStringField(var1.asProperty, var7);
               return var1;
            }

            if (var6 != 4) {
               this.writeStartArray();
               this.writeString(var7);
            } else {
               this.writeStartObject();
               this.writeFieldName(var7);
            }
         }
      }

      if (var3 == JsonToken.START_OBJECT) {
         this.writeStartObject(var1.forValue);
      } else if (var3 == JsonToken.START_ARRAY) {
         this.writeStartArray();
      }

      return var1;
   }

   public WritableTypeId writeTypeSuffix(WritableTypeId var1) throws IOException {
      JsonToken var2 = var1.valueShape;
      if (var2 == JsonToken.START_OBJECT) {
         this.writeEndObject();
      } else if (var2 == JsonToken.START_ARRAY) {
         this.writeEndArray();
      }

      if (var1.wrapperWritten) {
         int var3 = null.$SwitchMap$com$fasterxml$jackson$core$type$WritableTypeId$Inclusion[var1.include.ordinal()];
         if (var3 != 1) {
            if (var3 != 2 && var3 != 3) {
               if (var3 != 5) {
                  this.writeEndObject();
               } else {
                  this.writeEndArray();
               }
            }
         } else {
            Object var4 = var1.id;
            String var5;
            if (var4 instanceof String) {
               var5 = (String)var4;
            } else {
               var5 = String.valueOf(var4);
            }

            this.writeStringField(var1.asProperty, var5);
         }
      }

      return var1;
   }

   public abstract void writeUTF8String(byte[] var1, int var2, int var3) throws IOException;

   public static enum Feature {
      AUTO_CLOSE_JSON_CONTENT(true),
      AUTO_CLOSE_TARGET(true),
      @Deprecated
      ESCAPE_NON_ASCII(false),
      FLUSH_PASSED_TO_STREAM(true),
      IGNORE_UNKNOWN,
      @Deprecated
      QUOTE_FIELD_NAMES(true),
      @Deprecated
      QUOTE_NON_NUMERIC_NUMBERS(true),
      STRICT_DUPLICATE_DETECTION(false),
      WRITE_BIGDECIMAL_AS_PLAIN(false),
      @Deprecated
      WRITE_NUMBERS_AS_STRINGS(false);

      private final boolean _defaultState;
      private final int _mask;

      static {
         JsonGenerator.Feature var0 = new JsonGenerator.Feature("IGNORE_UNKNOWN", 9, false);
         IGNORE_UNKNOWN = var0;
      }

      private Feature(boolean var3) {
         this._defaultState = var3;
         this._mask = 1 << this.ordinal();
      }

      public static int collectDefaults() {
         JsonGenerator.Feature[] var0 = values();
         int var1 = var0.length;
         int var2 = 0;

         int var3;
         int var5;
         for(var3 = 0; var2 < var1; var3 = var5) {
            JsonGenerator.Feature var4 = var0[var2];
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
}
