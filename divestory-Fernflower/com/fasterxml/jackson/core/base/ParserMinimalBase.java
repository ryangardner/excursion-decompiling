package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ParserMinimalBase extends JsonParser {
   protected static final BigDecimal BD_MAX_INT;
   protected static final BigDecimal BD_MAX_LONG;
   protected static final BigDecimal BD_MIN_INT;
   protected static final BigDecimal BD_MIN_LONG;
   protected static final BigInteger BI_MAX_INT = BigInteger.valueOf(2147483647L);
   protected static final BigInteger BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
   protected static final BigInteger BI_MIN_INT = BigInteger.valueOf(-2147483648L);
   protected static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
   protected static final char CHAR_NULL = '\u0000';
   protected static final int INT_0 = 48;
   protected static final int INT_9 = 57;
   protected static final int INT_APOS = 39;
   protected static final int INT_ASTERISK = 42;
   protected static final int INT_BACKSLASH = 92;
   protected static final int INT_COLON = 58;
   protected static final int INT_COMMA = 44;
   protected static final int INT_CR = 13;
   protected static final int INT_E = 69;
   protected static final int INT_HASH = 35;
   protected static final int INT_LBRACKET = 91;
   protected static final int INT_LCURLY = 123;
   protected static final int INT_LF = 10;
   protected static final int INT_MINUS = 45;
   protected static final int INT_PERIOD = 46;
   protected static final int INT_PLUS = 43;
   protected static final int INT_QUOTE = 34;
   protected static final int INT_RBRACKET = 93;
   protected static final int INT_RCURLY = 125;
   protected static final int INT_SLASH = 47;
   protected static final int INT_SPACE = 32;
   protected static final int INT_TAB = 9;
   protected static final int INT_e = 101;
   protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
   protected static final double MAX_INT_D = 2.147483647E9D;
   protected static final long MAX_INT_L = 2147483647L;
   protected static final double MAX_LONG_D = 9.223372036854776E18D;
   protected static final double MIN_INT_D = -2.147483648E9D;
   protected static final long MIN_INT_L = -2147483648L;
   protected static final double MIN_LONG_D = -9.223372036854776E18D;
   protected static final byte[] NO_BYTES = new byte[0];
   protected static final int[] NO_INTS = new int[0];
   protected static final int NR_BIGDECIMAL = 16;
   protected static final int NR_BIGINT = 4;
   protected static final int NR_DOUBLE = 8;
   protected static final int NR_FLOAT = 32;
   protected static final int NR_INT = 1;
   protected static final int NR_LONG = 2;
   protected static final int NR_UNKNOWN = 0;
   protected JsonToken _currToken;
   protected JsonToken _lastClearedToken;

   static {
      BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
      BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
      BD_MIN_INT = new BigDecimal(BI_MIN_INT);
      BD_MAX_INT = new BigDecimal(BI_MAX_INT);
   }

   protected ParserMinimalBase() {
   }

   protected ParserMinimalBase(int var1) {
      super(var1);
   }

   protected static String _ascii(byte[] var0) {
      try {
         String var2 = new String(var0, "US-ASCII");
         return var2;
      } catch (IOException var1) {
         throw new RuntimeException(var1);
      }
   }

   protected static byte[] _asciiBytes(String var0) {
      byte[] var1 = new byte[var0.length()];
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3] = (byte)((byte)var0.charAt(var3));
      }

      return var1;
   }

   protected static final String _getCharDesc(int var0) {
      char var1 = (char)var0;
      StringBuilder var2;
      if (Character.isISOControl(var1)) {
         var2 = new StringBuilder();
         var2.append("(CTRL-CHAR, code ");
         var2.append(var0);
         var2.append(")");
         return var2.toString();
      } else if (var0 > 255) {
         var2 = new StringBuilder();
         var2.append("'");
         var2.append(var1);
         var2.append("' (code ");
         var2.append(var0);
         var2.append(" / 0x");
         var2.append(Integer.toHexString(var0));
         var2.append(")");
         return var2.toString();
      } else {
         var2 = new StringBuilder();
         var2.append("'");
         var2.append(var1);
         var2.append("' (code ");
         var2.append(var0);
         var2.append(")");
         return var2.toString();
      }
   }

   protected final JsonParseException _constructError(String var1, Throwable var2) {
      return new JsonParseException(this, var1, var2);
   }

   protected void _decodeBase64(String var1, ByteArrayBuilder var2, Base64Variant var3) throws IOException {
      try {
         var3.decode(var1, var2);
      } catch (IllegalArgumentException var4) {
         this._reportError(var4.getMessage());
      }

   }

   protected abstract void _handleEOF() throws JsonParseException;

   protected boolean _hasTextualNull(String var1) {
      return "null".equals(var1);
   }

   protected String _longIntegerDesc(String var1) {
      int var2 = var1.length();
      if (var2 < 1000) {
         return var1;
      } else {
         int var3 = var2;
         if (var1.startsWith("-")) {
            var3 = var2 - 1;
         }

         return String.format("[Integer with %d digits]", var3);
      }
   }

   protected String _longNumberDesc(String var1) {
      int var2 = var1.length();
      if (var2 < 1000) {
         return var1;
      } else {
         int var3 = var2;
         if (var1.startsWith("-")) {
            var3 = var2 - 1;
         }

         return String.format("[number with %d characters]", var3);
      }
   }

   protected final void _reportError(String var1) throws JsonParseException {
      throw this._constructError(var1);
   }

   protected final void _reportError(String var1, Object var2) throws JsonParseException {
      throw this._constructError(String.format(var1, var2));
   }

   protected final void _reportError(String var1, Object var2, Object var3) throws JsonParseException {
      throw this._constructError(String.format(var1, var2, var3));
   }

   protected void _reportInputCoercion(String var1, JsonToken var2, Class<?> var3) throws InputCoercionException {
      throw new InputCoercionException(this, var1, var2, var3);
   }

   protected void _reportInvalidEOF() throws JsonParseException {
      StringBuilder var1 = new StringBuilder();
      var1.append(" in ");
      var1.append(this._currToken);
      this._reportInvalidEOF(var1.toString(), this._currToken);
   }

   @Deprecated
   protected void _reportInvalidEOF(String var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Unexpected end-of-input");
      var2.append(var1);
      throw new JsonEOFException(this, (JsonToken)null, var2.toString());
   }

   protected void _reportInvalidEOF(String var1, JsonToken var2) throws JsonParseException {
      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected end-of-input");
      var3.append(var1);
      throw new JsonEOFException(this, var2, var3.toString());
   }

   @Deprecated
   protected void _reportInvalidEOFInValue() throws JsonParseException {
      this._reportInvalidEOF(" in a value");
   }

   protected void _reportInvalidEOFInValue(JsonToken var1) throws JsonParseException {
      String var2;
      if (var1 == JsonToken.VALUE_STRING) {
         var2 = " in a String value";
      } else if (var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT) {
         var2 = " in a value";
      } else {
         var2 = " in a Number value";
      }

      this._reportInvalidEOF(var2, var1);
   }

   protected void _reportMissingRootWS(int var1) throws JsonParseException {
      this._reportUnexpectedChar(var1, "Expected space separating root-level values");
   }

   protected void _reportUnexpectedChar(int var1, String var2) throws JsonParseException {
      if (var1 < 0) {
         this._reportInvalidEOF();
      }

      String var3 = String.format("Unexpected character (%s)", _getCharDesc(var1));
      String var4 = var3;
      if (var2 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var3);
         var5.append(": ");
         var5.append(var2);
         var4 = var5.toString();
      }

      this._reportError(var4);
   }

   protected final void _throwInternal() {
      VersionUtil.throwInternal();
   }

   protected void _throwInvalidSpace(int var1) throws JsonParseException {
      char var3 = (char)var1;
      StringBuilder var2 = new StringBuilder();
      var2.append("Illegal character (");
      var2.append(_getCharDesc(var3));
      var2.append("): only regular white space (\\r, \\n, \\t) is allowed between tokens");
      this._reportError(var2.toString());
   }

   protected final void _wrapError(String var1, Throwable var2) throws JsonParseException {
      throw this._constructError(var1, var2);
   }

   public void clearCurrentToken() {
      JsonToken var1 = this._currToken;
      if (var1 != null) {
         this._lastClearedToken = var1;
         this._currToken = null;
      }

   }

   public abstract void close() throws IOException;

   public JsonToken currentToken() {
      return this._currToken;
   }

   public int currentTokenId() {
      JsonToken var1 = this._currToken;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.id();
      }

      return var2;
   }

   public abstract byte[] getBinaryValue(Base64Variant var1) throws IOException;

   public abstract String getCurrentName() throws IOException;

   public JsonToken getCurrentToken() {
      return this._currToken;
   }

   public int getCurrentTokenId() {
      JsonToken var1 = this._currToken;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.id();
      }

      return var2;
   }

   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   public abstract JsonStreamContext getParsingContext();

   public abstract String getText() throws IOException;

   public abstract char[] getTextCharacters() throws IOException;

   public abstract int getTextLength() throws IOException;

   public abstract int getTextOffset() throws IOException;

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != null) {
         int var3 = var2.id();
         boolean var4 = true;
         switch(var3) {
         case 6:
            String var6 = this.getText().trim();
            if ("true".equals(var6)) {
               return true;
            }

            if ("false".equals(var6)) {
               return false;
            }

            if (this._hasTextualNull(var6)) {
               return false;
            }
            break;
         case 7:
            if (this.getIntValue() != 0) {
               var1 = var4;
            } else {
               var1 = false;
            }

            return var1;
         case 8:
         default:
            break;
         case 9:
            return true;
         case 10:
         case 11:
            return false;
         case 12:
            Object var5 = this.getEmbeddedObject();
            if (var5 instanceof Boolean) {
               return (Boolean)var5;
            }
         }
      }

      return var1;
   }

   public double getValueAsDouble(double var1) throws IOException {
      JsonToken var3 = this._currToken;
      double var4 = var1;
      if (var3 != null) {
         switch(var3.id()) {
         case 6:
            String var7 = this.getText();
            if (this._hasTextualNull(var7)) {
               return 0.0D;
            }

            var4 = NumberInput.parseAsDouble(var7, var1);
            break;
         case 7:
         case 8:
            return this.getDoubleValue();
         case 9:
            return 1.0D;
         case 10:
         case 11:
            return 0.0D;
         case 12:
            Object var6 = this.getEmbeddedObject();
            var4 = var1;
            if (var6 instanceof Number) {
               return ((Number)var6).doubleValue();
            }
            break;
         default:
            var4 = var1;
         }
      }

      return var4;
   }

   public int getValueAsInt() throws IOException {
      JsonToken var1 = this._currToken;
      return var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT ? this.getValueAsInt(0) : this.getIntValue();
   }

   public int getValueAsInt(int var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != JsonToken.VALUE_NUMBER_INT && var2 != JsonToken.VALUE_NUMBER_FLOAT) {
         int var3 = var1;
         if (var2 != null) {
            var3 = var2.id();
            if (var3 != 6) {
               switch(var3) {
               case 9:
                  return 1;
               case 10:
               case 11:
                  return 0;
               case 12:
                  Object var4 = this.getEmbeddedObject();
                  var3 = var1;
                  if (var4 instanceof Number) {
                     return ((Number)var4).intValue();
                  }
                  break;
               default:
                  var3 = var1;
               }
            } else {
               String var5 = this.getText();
               if (this._hasTextualNull(var5)) {
                  return 0;
               }

               var3 = NumberInput.parseAsInt(var5, var1);
            }
         }

         return var3;
      } else {
         return this.getIntValue();
      }
   }

   public long getValueAsLong() throws IOException {
      JsonToken var1 = this._currToken;
      return var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT ? this.getValueAsLong(0L) : this.getLongValue();
   }

   public long getValueAsLong(long var1) throws IOException {
      JsonToken var3 = this._currToken;
      if (var3 != JsonToken.VALUE_NUMBER_INT && var3 != JsonToken.VALUE_NUMBER_FLOAT) {
         long var4 = var1;
         if (var3 != null) {
            int var6 = var3.id();
            if (var6 != 6) {
               switch(var6) {
               case 9:
                  return 1L;
               case 10:
               case 11:
                  return 0L;
               case 12:
                  Object var7 = this.getEmbeddedObject();
                  var4 = var1;
                  if (var7 instanceof Number) {
                     return ((Number)var7).longValue();
                  }
                  break;
               default:
                  var4 = var1;
               }
            } else {
               String var8 = this.getText();
               if (this._hasTextualNull(var8)) {
                  return 0L;
               }

               var4 = NumberInput.parseAsLong(var8, var1);
            }
         }

         return var4;
      } else {
         return this.getLongValue();
      }
   }

   public String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this.getText();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : this.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this.getText();
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this.getCurrentName();
      } else {
         JsonToken var2 = this._currToken;
         String var3 = var1;
         if (var2 != null) {
            var3 = var1;
            if (var2 != JsonToken.VALUE_NULL) {
               if (!this._currToken.isScalarValue()) {
                  var3 = var1;
               } else {
                  var3 = this.getText();
               }
            }
         }

         return var3;
      }
   }

   public boolean hasCurrentToken() {
      boolean var1;
      if (this._currToken != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract boolean hasTextCharacters();

   public boolean hasToken(JsonToken var1) {
      boolean var2;
      if (this._currToken == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hasTokenId(int var1) {
      JsonToken var2 = this._currToken;
      boolean var3 = true;
      boolean var4 = true;
      if (var2 == null) {
         if (var1 != 0) {
            var4 = false;
         }

         return var4;
      } else {
         if (var2.id() == var1) {
            var4 = var3;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   public abstract boolean isClosed();

   public boolean isExpectedStartArrayToken() {
      boolean var1;
      if (this._currToken == JsonToken.START_ARRAY) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isExpectedStartObjectToken() {
      boolean var1;
      if (this._currToken == JsonToken.START_OBJECT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract JsonToken nextToken() throws IOException;

   public JsonToken nextValue() throws IOException {
      JsonToken var1 = this.nextToken();
      JsonToken var2 = var1;
      if (var1 == JsonToken.FIELD_NAME) {
         var2 = this.nextToken();
      }

      return var2;
   }

   public abstract void overrideCurrentName(String var1);

   protected void reportInvalidNumber(String var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid numeric value: ");
      var2.append(var1);
      this._reportError(var2.toString());
   }

   protected void reportOverflowInt() throws IOException {
      this.reportOverflowInt(this.getText());
   }

   protected void reportOverflowInt(String var1) throws IOException {
      this.reportOverflowInt(var1, JsonToken.VALUE_NUMBER_INT);
   }

   protected void reportOverflowInt(String var1, JsonToken var2) throws IOException {
      this._reportInputCoercion(String.format("Numeric value (%s) out of range of int (%d - %s)", this._longIntegerDesc(var1), Integer.MIN_VALUE, Integer.MAX_VALUE), var2, Integer.TYPE);
   }

   protected void reportOverflowLong() throws IOException {
      this.reportOverflowLong(this.getText());
   }

   protected void reportOverflowLong(String var1) throws IOException {
      this.reportOverflowLong(var1, JsonToken.VALUE_NUMBER_INT);
   }

   protected void reportOverflowLong(String var1, JsonToken var2) throws IOException {
      this._reportInputCoercion(String.format("Numeric value (%s) out of range of long (%d - %s)", this._longIntegerDesc(var1), Long.MIN_VALUE, Long.MAX_VALUE), var2, Long.TYPE);
   }

   protected void reportUnexpectedNumberChar(int var1, String var2) throws JsonParseException {
      String var3 = String.format("Unexpected character (%s) in numeric value", _getCharDesc(var1));
      String var4 = var3;
      if (var2 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var3);
         var5.append(": ");
         var5.append(var2);
         var4 = var5.toString();
      }

      this._reportError(var4);
   }

   public JsonParser skipChildren() throws IOException {
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return this;
      } else {
         int var1 = 1;

         while(true) {
            JsonToken var2 = this.nextToken();
            if (var2 == null) {
               this._handleEOF();
               return this;
            }

            if (var2.isStructStart()) {
               ++var1;
            } else if (var2.isStructEnd()) {
               int var3 = var1 - 1;
               var1 = var3;
               if (var3 == 0) {
                  return this;
               }
            } else if (var2 == JsonToken.NOT_AVAILABLE) {
               this._reportError("Not enough content available for `skipChildren()`: non-blocking parser? (%s)", this.getClass().getName());
            }
         }
      }
   }
}
