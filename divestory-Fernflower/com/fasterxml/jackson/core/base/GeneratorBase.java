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

public abstract class GeneratorBase extends JsonGenerator {
   protected static final int DERIVED_FEATURES_MASK;
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

   static {
      DERIVED_FEATURES_MASK = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | JsonGenerator.Feature.ESCAPE_NON_ASCII.getMask() | JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.getMask();
   }

   protected GeneratorBase(int var1, ObjectCodec var2) {
      this._features = var1;
      this._objectCodec = var2;
      DupDetector var3;
      if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(var1)) {
         var3 = DupDetector.rootDetector((JsonGenerator)this);
      } else {
         var3 = null;
      }

      this._writeContext = JsonWriteContext.createRootContext(var3);
      this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(var1);
   }

   protected GeneratorBase(int var1, ObjectCodec var2, JsonWriteContext var3) {
      this._features = var1;
      this._objectCodec = var2;
      this._writeContext = var3;
      this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(var1);
   }

   protected String _asString(BigDecimal var1) throws IOException {
      if (!JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) {
         return var1.toString();
      } else {
         int var2 = var1.scale();
         if (var2 < -9999 || var2 > 9999) {
            this._reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", var2, 9999, 9999));
         }

         return var1.toPlainString();
      }
   }

   protected void _checkStdFeatureChanges(int var1, int var2) {
      if ((DERIVED_FEATURES_MASK & var2) != 0) {
         this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(var1);
         if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(var2)) {
            if (JsonGenerator.Feature.ESCAPE_NON_ASCII.enabledIn(var1)) {
               this.setHighestNonEscapedChar(127);
            } else {
               this.setHighestNonEscapedChar(0);
            }
         }

         if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(var2)) {
            if (JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(var1)) {
               if (this._writeContext.getDupDetector() == null) {
                  this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector((JsonGenerator)this));
               }
            } else {
               this._writeContext = this._writeContext.withDupDetector((DupDetector)null);
            }
         }

      }
   }

   protected PrettyPrinter _constructDefaultPrettyPrinter() {
      return new DefaultPrettyPrinter();
   }

   protected final int _decodeSurrogate(int var1, int var2) throws IOException {
      if (var2 < 56320 || var2 > 57343) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Incomplete surrogate pair: first char 0x");
         var3.append(Integer.toHexString(var1));
         var3.append(", second 0x");
         var3.append(Integer.toHexString(var2));
         this._reportError(var3.toString());
      }

      return (var1 - '\ud800' << 10) + 65536 + (var2 - '\udc00');
   }

   protected abstract void _releaseBuffers();

   protected abstract void _verifyValueWrite(String var1) throws IOException;

   public void close() throws IOException {
      this._closed = true;
   }

   public JsonGenerator disable(JsonGenerator.Feature var1) {
      int var2 = var1.getMask();
      this._features &= var2;
      if ((var2 & DERIVED_FEATURES_MASK) != 0) {
         if (var1 == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = false;
         } else if (var1 == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(0);
         } else if (var1 == JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION) {
            this._writeContext = this._writeContext.withDupDetector((DupDetector)null);
         }
      }

      return this;
   }

   public JsonGenerator enable(JsonGenerator.Feature var1) {
      int var2 = var1.getMask();
      this._features |= var2;
      if ((var2 & DERIVED_FEATURES_MASK) != 0) {
         if (var1 == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = true;
         } else if (var1 == JsonGenerator.Feature.ESCAPE_NON_ASCII) {
            this.setHighestNonEscapedChar(127);
         } else if (var1 == JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION && this._writeContext.getDupDetector() == null) {
            this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector((JsonGenerator)this));
         }
      }

      return this;
   }

   public abstract void flush() throws IOException;

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public Object getCurrentValue() {
      return this._writeContext.getCurrentValue();
   }

   public int getFeatureMask() {
      return this._features;
   }

   public JsonStreamContext getOutputContext() {
      return this._writeContext;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public final boolean isEnabled(JsonGenerator.Feature var1) {
      int var2 = this._features;
      boolean var3;
      if ((var1.getMask() & var2) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public JsonGenerator overrideStdFeatures(int var1, int var2) {
      int var3 = this._features;
      var1 = var1 & var2 | var2 & var3;
      var2 = var3 ^ var1;
      if (var2 != 0) {
         this._features = var1;
         this._checkStdFeatureChanges(var1, var2);
      }

      return this;
   }

   public JsonGenerator setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
      return this;
   }

   public void setCurrentValue(Object var1) {
      JsonWriteContext var2 = this._writeContext;
      if (var2 != null) {
         var2.setCurrentValue(var1);
      }

   }

   @Deprecated
   public JsonGenerator setFeatureMask(int var1) {
      int var2 = this._features ^ var1;
      this._features = var1;
      if (var2 != 0) {
         this._checkStdFeatureChanges(var1, var2);
      }

      return this;
   }

   public JsonGenerator useDefaultPrettyPrinter() {
      return (JsonGenerator)(this.getPrettyPrinter() != null ? this : this.setPrettyPrinter(this._constructDefaultPrettyPrinter()));
   }

   public Version version() {
      return PackageVersion.VERSION;
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
      return 0;
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      this.writeFieldName(var1.getValue());
   }

   public void writeObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         ObjectCodec var2 = this._objectCodec;
         if (var2 != null) {
            var2.writeValue(this, var1);
            return;
         }

         this._writeSimpleObject(var1);
      }

   }

   public void writeRawValue(SerializableString var1) throws IOException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1);
   }

   public void writeRawValue(String var1) throws IOException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1);
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeStartObject(Object var1) throws IOException {
      this.writeStartObject();
      if (var1 != null) {
         this.setCurrentValue(var1);
      }

   }

   public void writeString(SerializableString var1) throws IOException {
      this.writeString(var1.getValue());
   }

   public void writeTree(TreeNode var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else {
         ObjectCodec var2 = this._objectCodec;
         if (var2 == null) {
            throw new IllegalStateException("No ObjectCodec defined");
         }

         var2.writeValue(this, var1);
      }

   }
}
