package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

public abstract class ParserBase extends ParserMinimalBase {
   protected byte[] _binaryValue;
   protected ByteArrayBuilder _byteArrayBuilder;
   protected boolean _closed;
   protected long _currInputProcessed;
   protected int _currInputRow = 1;
   protected int _currInputRowStart;
   protected int _expLength;
   protected int _fractLength;
   protected int _inputEnd;
   protected int _inputPtr;
   protected int _intLength;
   protected final IOContext _ioContext;
   protected boolean _nameCopied;
   protected char[] _nameCopyBuffer;
   protected JsonToken _nextToken;
   protected int _numTypesValid = 0;
   protected BigDecimal _numberBigDecimal;
   protected BigInteger _numberBigInt;
   protected double _numberDouble;
   protected int _numberInt;
   protected long _numberLong;
   protected boolean _numberNegative;
   protected JsonReadContext _parsingContext;
   protected final TextBuffer _textBuffer;
   protected int _tokenInputCol;
   protected int _tokenInputRow = 1;
   protected long _tokenInputTotal;

   protected ParserBase(IOContext var1, int var2) {
      super(var2);
      this._ioContext = var1;
      this._textBuffer = var1.constructTextBuffer();
      DupDetector var3;
      if (JsonParser.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(var2)) {
         var3 = DupDetector.rootDetector((JsonParser)this);
      } else {
         var3 = null;
      }

      this._parsingContext = JsonReadContext.createRootContext(var3);
   }

   private void _parseSlowFloat(int var1) throws IOException {
      NumberFormatException var10000;
      boolean var10001;
      if (var1 == 16) {
         try {
            this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
            this._numTypesValid = 16;
            return;
         } catch (NumberFormatException var4) {
            var10000 = var4;
            var10001 = false;
         }
      } else {
         try {
            this._numberDouble = this._textBuffer.contentsAsDouble();
            this._numTypesValid = 8;
            return;
         } catch (NumberFormatException var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      NumberFormatException var2 = var10000;
      StringBuilder var3 = new StringBuilder();
      var3.append("Malformed numeric value (");
      var3.append(this._longNumberDesc(this._textBuffer.contentsAsString()));
      var3.append(")");
      this._wrapError(var3.toString(), var2);
   }

   private void _parseSlowInt(int var1) throws IOException {
      String var2 = this._textBuffer.contentsAsString();

      NumberFormatException var10000;
      label72: {
         int var3;
         char[] var4;
         int var5;
         boolean var10001;
         try {
            var3 = this._intLength;
            var4 = this._textBuffer.getTextBuffer();
            var5 = this._textBuffer.getTextOffset();
         } catch (NumberFormatException var13) {
            var10000 = var13;
            var10001 = false;
            break label72;
         }

         int var6 = var5;

         label61: {
            try {
               if (!this._numberNegative) {
                  break label61;
               }
            } catch (NumberFormatException var12) {
               var10000 = var12;
               var10001 = false;
               break label72;
            }

            var6 = var5 + 1;
         }

         try {
            if (NumberInput.inLongRange(var4, var6, var3, this._numberNegative)) {
               this._numberLong = Long.parseLong(var2);
               this._numTypesValid = 2;
               return;
            }
         } catch (NumberFormatException var11) {
            var10000 = var11;
            var10001 = false;
            break label72;
         }

         if (var1 == 1 || var1 == 2) {
            try {
               this._reportTooLongIntegral(var1, var2);
            } catch (NumberFormatException var10) {
               var10000 = var10;
               var10001 = false;
               break label72;
            }
         }

         if (var1 != 8 && var1 != 32) {
            try {
               BigInteger var15 = new BigInteger(var2);
               this._numberBigInt = var15;
               this._numTypesValid = 4;
               return;
            } catch (NumberFormatException var8) {
               var10000 = var8;
               var10001 = false;
            }
         } else {
            try {
               this._numberDouble = NumberInput.parseDouble(var2);
               this._numTypesValid = 8;
               return;
            } catch (NumberFormatException var9) {
               var10000 = var9;
               var10001 = false;
            }
         }
      }

      NumberFormatException var7 = var10000;
      StringBuilder var14 = new StringBuilder();
      var14.append("Malformed numeric value (");
      var14.append(this._longNumberDesc(var2));
      var14.append(")");
      this._wrapError(var14.toString(), var7);
   }

   protected static int[] growArrayBy(int[] var0, int var1) {
      return var0 == null ? new int[var1] : Arrays.copyOf(var0, var0.length + var1);
   }

   protected void _checkStdFeatureChanges(int var1, int var2) {
      int var3 = JsonParser.Feature.STRICT_DUPLICATE_DETECTION.getMask();
      if ((var2 & var3) != 0 && (var1 & var3) != 0) {
         if (this._parsingContext.getDupDetector() == null) {
            this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector((JsonParser)this));
         } else {
            this._parsingContext = this._parsingContext.withDupDetector((DupDetector)null);
         }
      }

   }

   protected abstract void _closeInput() throws IOException;

   protected final int _decodeBase64Escape(Base64Variant var1, char var2, int var3) throws IOException {
      if (var2 == '\\') {
         char var4 = this._decodeEscaped();
         if (var4 <= ' ' && var3 == 0) {
            return -1;
         } else {
            int var5 = var1.decodeBase64Char(var4);
            if (var5 >= 0 || var5 == -2 && var3 >= 2) {
               return var5;
            } else {
               throw this.reportInvalidBase64Char(var1, var4, var3);
            }
         }
      } else {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      }
   }

   protected final int _decodeBase64Escape(Base64Variant var1, int var2, int var3) throws IOException {
      if (var2 == 92) {
         char var4 = this._decodeEscaped();
         if (var4 <= ' ' && var3 == 0) {
            return -1;
         } else {
            var2 = var1.decodeBase64Char((int)var4);
            if (var2 < 0 && var2 != -2) {
               throw this.reportInvalidBase64Char(var1, var4, var3);
            } else {
               return var2;
            }
         }
      } else {
         throw this.reportInvalidBase64Char(var1, var2, var3);
      }
   }

   protected char _decodeEscaped() throws IOException {
      throw new UnsupportedOperationException();
   }

   protected final int _eofAsNextChar() throws JsonParseException {
      this._handleEOF();
      return -1;
   }

   protected void _finishString() throws IOException {
   }

   public ByteArrayBuilder _getByteArrayBuilder() {
      ByteArrayBuilder var1 = this._byteArrayBuilder;
      if (var1 == null) {
         this._byteArrayBuilder = new ByteArrayBuilder();
      } else {
         var1.reset();
      }

      return this._byteArrayBuilder;
   }

   protected Object _getSourceReference() {
      return JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION.enabledIn(this._features) ? this._ioContext.getSourceReference() : null;
   }

   protected void _handleBase64MissingPadding(Base64Variant var1) throws IOException {
      this._reportError(var1.missingPaddingMessage());
   }

   protected void _handleEOF() throws JsonParseException {
      if (!this._parsingContext.inRoot()) {
         String var1;
         if (this._parsingContext.inArray()) {
            var1 = "Array";
         } else {
            var1 = "Object";
         }

         this._reportInvalidEOF(String.format(": expected close marker for %s (start marker at %s)", var1, this._parsingContext.getStartLocation(this._getSourceReference())), (JsonToken)null);
      }

   }

   protected char _handleUnrecognizedCharacterEscape(char var1) throws JsonProcessingException {
      if (this.isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
         return var1;
      } else if (var1 == '\'' && this.isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES)) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unrecognized character escape ");
         var2.append(_getCharDesc(var1));
         this._reportError(var2.toString());
         return var1;
      }
   }

   protected int _parseIntValue() throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT && this._intLength <= 9) {
         int var1 = this._textBuffer.contentsAsInt(this._numberNegative);
         this._numberInt = var1;
         this._numTypesValid = 1;
         return var1;
      } else {
         this._parseNumericValue(1);
         if ((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
         }

         return this._numberInt;
      }
   }

   protected void _parseNumericValue(int var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         int var2 = this._intLength;
         if (var2 <= 9) {
            this._numberInt = this._textBuffer.contentsAsInt(this._numberNegative);
            this._numTypesValid = 1;
         } else if (var2 <= 18) {
            long var3 = this._textBuffer.contentsAsLong(this._numberNegative);
            if (var2 == 10) {
               if (this._numberNegative) {
                  if (var3 >= -2147483648L) {
                     this._numberInt = (int)var3;
                     this._numTypesValid = 1;
                     return;
                  }
               } else if (var3 <= 2147483647L) {
                  this._numberInt = (int)var3;
                  this._numTypesValid = 1;
                  return;
               }
            }

            this._numberLong = var3;
            this._numTypesValid = 2;
         } else {
            this._parseSlowInt(var1);
         }
      } else if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
         this._parseSlowFloat(var1);
      } else {
         this._reportError("Current token (%s) not numeric, can not use numeric value accessors", this._currToken);
      }
   }

   protected void _releaseBuffers() throws IOException {
      this._textBuffer.releaseBuffers();
      char[] var1 = this._nameCopyBuffer;
      if (var1 != null) {
         this._nameCopyBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }

   }

   protected void _reportMismatchedEndMarker(int var1, char var2) throws JsonParseException {
      JsonReadContext var3 = this.getParsingContext();
      this._reportError(String.format("Unexpected close marker '%s': expected '%c' (for %s starting at %s)", (char)var1, var2, var3.typeDesc(), var3.getStartLocation(this._getSourceReference())));
   }

   protected void _reportTooLongIntegral(int var1, String var2) throws IOException {
      if (var1 == 1) {
         this.reportOverflowInt(var2);
      } else {
         this.reportOverflowLong(var2);
      }

   }

   protected void _throwUnquotedSpace(int var1, String var2) throws JsonParseException {
      if (!this.isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || var1 > 32) {
         char var4 = (char)var1;
         StringBuilder var3 = new StringBuilder();
         var3.append("Illegal unquoted character (");
         var3.append(_getCharDesc(var4));
         var3.append("): has to be escaped using backslash to be included in ");
         var3.append(var2);
         this._reportError(var3.toString());
      }

   }

   protected String _validJsonTokenList() throws IOException {
      return this._validJsonValueList();
   }

   protected String _validJsonValueList() throws IOException {
      return this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS) ? "(JSON String, Number (or 'NaN'/'INF'/'+INF'), Array, Object or token 'null', 'true' or 'false')" : "(JSON String, Number, Array, Object or token 'null', 'true' or 'false')";
   }

   public void close() throws IOException {
      if (!this._closed) {
         this._inputPtr = Math.max(this._inputPtr, this._inputEnd);
         this._closed = true;

         try {
            this._closeInput();
         } finally {
            this._releaseBuffers();
         }
      }

   }

   protected void convertNumberToBigDecimal() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 8) != 0) {
         this._numberBigDecimal = NumberInput.parseBigDecimal(this.getText());
      } else if ((var1 & 4) != 0) {
         this._numberBigDecimal = new BigDecimal(this._numberBigInt);
      } else if ((var1 & 2) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
      } else if ((var1 & 1) != 0) {
         this._numberBigDecimal = BigDecimal.valueOf((long)this._numberInt);
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 16;
   }

   protected void convertNumberToBigInteger() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 16) != 0) {
         this._numberBigInt = this._numberBigDecimal.toBigInteger();
      } else if ((var1 & 2) != 0) {
         this._numberBigInt = BigInteger.valueOf(this._numberLong);
      } else if ((var1 & 1) != 0) {
         this._numberBigInt = BigInteger.valueOf((long)this._numberInt);
      } else if ((var1 & 8) != 0) {
         this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 4;
   }

   protected void convertNumberToDouble() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 16) != 0) {
         this._numberDouble = this._numberBigDecimal.doubleValue();
      } else if ((var1 & 4) != 0) {
         this._numberDouble = this._numberBigInt.doubleValue();
      } else if ((var1 & 2) != 0) {
         this._numberDouble = (double)this._numberLong;
      } else if ((var1 & 1) != 0) {
         this._numberDouble = (double)this._numberInt;
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 8;
   }

   protected void convertNumberToInt() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 2) != 0) {
         long var2 = this._numberLong;
         var1 = (int)var2;
         if ((long)var1 != var2) {
            this.reportOverflowInt(this.getText(), this.currentToken());
         }

         this._numberInt = var1;
      } else if ((var1 & 4) != 0) {
         if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigInt.intValue();
      } else if ((var1 & 8) != 0) {
         double var4 = this._numberDouble;
         if (var4 < -2.147483648E9D || var4 > 2.147483647E9D) {
            this.reportOverflowInt();
         }

         this._numberInt = (int)this._numberDouble;
      } else if ((var1 & 16) != 0) {
         if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowInt();
         }

         this._numberInt = this._numberBigDecimal.intValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 1;
   }

   protected void convertNumberToLong() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 1) != 0) {
         this._numberLong = (long)this._numberInt;
      } else if ((var1 & 4) != 0) {
         if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigInt.longValue();
      } else if ((var1 & 8) != 0) {
         double var2 = this._numberDouble;
         if (var2 < -9.223372036854776E18D || var2 > 9.223372036854776E18D) {
            this.reportOverflowLong();
         }

         this._numberLong = (long)this._numberDouble;
      } else if ((var1 & 16) != 0) {
         if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
            this.reportOverflowLong();
         }

         this._numberLong = this._numberBigDecimal.longValue();
      } else {
         this._throwInternal();
      }

      this._numTypesValid |= 2;
   }

   public JsonParser disable(JsonParser.Feature var1) {
      this._features &= var1.getMask();
      if (var1 == JsonParser.Feature.STRICT_DUPLICATE_DETECTION) {
         this._parsingContext = this._parsingContext.withDupDetector((DupDetector)null);
      }

      return this;
   }

   public JsonParser enable(JsonParser.Feature var1) {
      this._features |= var1.getMask();
      if (var1 == JsonParser.Feature.STRICT_DUPLICATE_DETECTION && this._parsingContext.getDupDetector() == null) {
         this._parsingContext = this._parsingContext.withDupDetector(DupDetector.rootDetector((JsonParser)this));
      }

      return this;
   }

   public BigInteger getBigIntegerValue() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 4) == 0) {
         if (var1 == 0) {
            this._parseNumericValue(4);
         }

         if ((this._numTypesValid & 4) == 0) {
            this.convertNumberToBigInteger();
         }
      }

      return this._numberBigInt;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      if (this._binaryValue == null) {
         if (this._currToken != JsonToken.VALUE_STRING) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Current token (");
            var2.append(this._currToken);
            var2.append(") not VALUE_STRING, can not access as binary");
            this._reportError(var2.toString());
         }

         ByteArrayBuilder var3 = this._getByteArrayBuilder();
         this._decodeBase64(this.getText(), var3, var1);
         this._binaryValue = var3.toByteArray();
      }

      return this._binaryValue;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      Object var3 = this._getSourceReference();
      long var4 = this._currInputProcessed;
      return new JsonLocation(var3, -1L, (long)this._inputPtr + var4, this._currInputRow, var1 - var2 + 1);
   }

   public String getCurrentName() throws IOException {
      if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
         JsonReadContext var1 = this._parsingContext.getParent();
         if (var1 != null) {
            return var1.getCurrentName();
         }
      }

      return this._parsingContext.getCurrentName();
   }

   public Object getCurrentValue() {
      return this._parsingContext.getCurrentValue();
   }

   public BigDecimal getDecimalValue() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 16) == 0) {
         if (var1 == 0) {
            this._parseNumericValue(16);
         }

         if ((this._numTypesValid & 16) == 0) {
            this.convertNumberToBigDecimal();
         }
      }

      return this._numberBigDecimal;
   }

   public double getDoubleValue() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 8) == 0) {
         if (var1 == 0) {
            this._parseNumericValue(8);
         }

         if ((this._numTypesValid & 8) == 0) {
            this.convertNumberToDouble();
         }
      }

      return this._numberDouble;
   }

   public float getFloatValue() throws IOException {
      return (float)this.getDoubleValue();
   }

   public int getIntValue() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 1) == 0) {
         if (var1 == 0) {
            return this._parseIntValue();
         }

         if ((var1 & 1) == 0) {
            this.convertNumberToInt();
         }
      }

      return this._numberInt;
   }

   public long getLongValue() throws IOException {
      int var1 = this._numTypesValid;
      if ((var1 & 2) == 0) {
         if (var1 == 0) {
            this._parseNumericValue(2);
         }

         if ((this._numTypesValid & 2) == 0) {
            this.convertNumberToLong();
         }
      }

      return this._numberLong;
   }

   public JsonParser.NumberType getNumberType() throws IOException {
      if (this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         int var1 = this._numTypesValid;
         if ((var1 & 1) != 0) {
            return JsonParser.NumberType.INT;
         } else {
            return (var1 & 2) != 0 ? JsonParser.NumberType.LONG : JsonParser.NumberType.BIG_INTEGER;
         }
      } else {
         return (this._numTypesValid & 16) != 0 ? JsonParser.NumberType.BIG_DECIMAL : JsonParser.NumberType.DOUBLE;
      }
   }

   public Number getNumberValue() throws IOException {
      if (this._numTypesValid == 0) {
         this._parseNumericValue(0);
      }

      int var1;
      if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
         var1 = this._numTypesValid;
         if ((var1 & 1) != 0) {
            return this._numberInt;
         } else if ((var1 & 2) != 0) {
            return this._numberLong;
         } else {
            return (Number)((var1 & 4) != 0 ? this._numberBigInt : this._numberBigDecimal);
         }
      } else {
         var1 = this._numTypesValid;
         if ((var1 & 16) != 0) {
            return this._numberBigDecimal;
         } else {
            if ((var1 & 8) == 0) {
               this._throwInternal();
            }

            return this._numberDouble;
         }
      }
   }

   public JsonReadContext getParsingContext() {
      return this._parsingContext;
   }

   public long getTokenCharacterOffset() {
      return this._tokenInputTotal;
   }

   public int getTokenColumnNr() {
      int var1 = this._tokenInputCol;
      if (var1 >= 0) {
         ++var1;
      }

      return var1;
   }

   public int getTokenLineNr() {
      return this._tokenInputRow;
   }

   public JsonLocation getTokenLocation() {
      return new JsonLocation(this._getSourceReference(), -1L, this.getTokenCharacterOffset(), this.getTokenLineNr(), this.getTokenColumnNr());
   }

   public boolean hasTextCharacters() {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return true;
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this._nameCopied : false;
      }
   }

   public boolean isClosed() {
      return this._closed;
   }

   public boolean isNaN() {
      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.VALUE_NUMBER_FLOAT;
      boolean var3 = false;
      boolean var4 = var3;
      if (var1 == var2) {
         var4 = var3;
         if ((this._numTypesValid & 8) != 0) {
            double var5 = this._numberDouble;
            if (!Double.isNaN(var5)) {
               var4 = var3;
               if (!Double.isInfinite(var5)) {
                  return var4;
               }
            }

            var4 = true;
         }
      }

      return var4;
   }

   @Deprecated
   protected boolean loadMore() throws IOException {
      return false;
   }

   @Deprecated
   protected void loadMoreGuaranteed() throws IOException {
      if (!this.loadMore()) {
         this._reportInvalidEOF();
      }

   }

   public void overrideCurrentName(String var1) {
      JsonReadContext var3;
      label16: {
         JsonReadContext var2 = this._parsingContext;
         if (this._currToken != JsonToken.START_OBJECT) {
            var3 = var2;
            if (this._currToken != JsonToken.START_ARRAY) {
               break label16;
            }
         }

         var3 = var2.getParent();
      }

      try {
         var3.setCurrentName(var1);
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public JsonParser overrideStdFeatures(int var1, int var2) {
      int var3 = this._features;
      var1 = var1 & var2 | var2 & var3;
      var2 = var3 ^ var1;
      if (var2 != 0) {
         this._features = var1;
         this._checkStdFeatureChanges(var1, var2);
      }

      return this;
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3) throws IllegalArgumentException {
      return this.reportInvalidBase64Char(var1, var2, var3, (String)null);
   }

   protected IllegalArgumentException reportInvalidBase64Char(Base64Variant var1, int var2, int var3, String var4) throws IllegalArgumentException {
      StringBuilder var5;
      String var6;
      if (var2 <= 32) {
         var6 = String.format("Illegal white space character (code 0x%s) as character #%d of 4-char base64 unit: can only used between units", Integer.toHexString(var2), var3 + 1);
      } else if (var1.usesPaddingChar(var2)) {
         var5 = new StringBuilder();
         var5.append("Unexpected padding character ('");
         var5.append(var1.getPaddingChar());
         var5.append("') as character #");
         var5.append(var3 + 1);
         var5.append(" of 4-char base64 unit: padding only legal as 3rd or 4th character");
         var6 = var5.toString();
      } else {
         StringBuilder var7;
         if (Character.isDefined(var2) && !Character.isISOControl(var2)) {
            var7 = new StringBuilder();
            var7.append("Illegal character '");
            var7.append((char)var2);
            var7.append("' (code 0x");
            var7.append(Integer.toHexString(var2));
            var7.append(") in base64 content");
            var6 = var7.toString();
         } else {
            var7 = new StringBuilder();
            var7.append("Illegal character (code 0x");
            var7.append(Integer.toHexString(var2));
            var7.append(") in base64 content");
            var6 = var7.toString();
         }
      }

      String var8 = var6;
      if (var4 != null) {
         var5 = new StringBuilder();
         var5.append(var6);
         var5.append(": ");
         var5.append(var4);
         var8 = var5.toString();
      }

      return new IllegalArgumentException(var8);
   }

   protected final JsonToken reset(boolean var1, int var2, int var3, int var4) {
      return var3 < 1 && var4 < 1 ? this.resetInt(var1, var2) : this.resetFloat(var1, var2, var3, var4);
   }

   protected final JsonToken resetAsNaN(String var1, double var2) {
      this._textBuffer.resetWithString(var1);
      this._numberDouble = var2;
      this._numTypesValid = 8;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   protected final JsonToken resetFloat(boolean var1, int var2, int var3, int var4) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = var3;
      this._expLength = var4;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_FLOAT;
   }

   protected final JsonToken resetInt(boolean var1, int var2) {
      this._numberNegative = var1;
      this._intLength = var2;
      this._fractLength = 0;
      this._expLength = 0;
      this._numTypesValid = 0;
      return JsonToken.VALUE_NUMBER_INT;
   }

   public void setCurrentValue(Object var1) {
      this._parsingContext.setCurrentValue(var1);
   }

   @Deprecated
   public JsonParser setFeatureMask(int var1) {
      int var2 = this._features ^ var1;
      if (var2 != 0) {
         this._features = var1;
         this._checkStdFeatureChanges(var1, var2);
      }

      return this;
   }

   public Version version() {
      return PackageVersion.VERSION;
   }
}
