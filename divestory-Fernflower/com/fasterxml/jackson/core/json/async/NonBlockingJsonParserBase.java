package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public abstract class NonBlockingJsonParserBase extends ParserBase {
   protected static final int MAJOR_ARRAY_ELEMENT_FIRST = 5;
   protected static final int MAJOR_ARRAY_ELEMENT_NEXT = 6;
   protected static final int MAJOR_CLOSED = 7;
   protected static final int MAJOR_INITIAL = 0;
   protected static final int MAJOR_OBJECT_FIELD_FIRST = 2;
   protected static final int MAJOR_OBJECT_FIELD_NEXT = 3;
   protected static final int MAJOR_OBJECT_VALUE = 4;
   protected static final int MAJOR_ROOT = 1;
   protected static final int MINOR_COMMENT_C = 53;
   protected static final int MINOR_COMMENT_CLOSING_ASTERISK = 52;
   protected static final int MINOR_COMMENT_CPP = 54;
   protected static final int MINOR_COMMENT_LEADING_SLASH = 51;
   protected static final int MINOR_COMMENT_YAML = 55;
   protected static final int MINOR_FIELD_APOS_NAME = 9;
   protected static final int MINOR_FIELD_LEADING_COMMA = 5;
   protected static final int MINOR_FIELD_LEADING_WS = 4;
   protected static final int MINOR_FIELD_NAME = 7;
   protected static final int MINOR_FIELD_NAME_ESCAPE = 8;
   protected static final int MINOR_FIELD_UNQUOTED_NAME = 10;
   protected static final int MINOR_NUMBER_EXPONENT_DIGITS = 32;
   protected static final int MINOR_NUMBER_EXPONENT_MARKER = 31;
   protected static final int MINOR_NUMBER_FRACTION_DIGITS = 30;
   protected static final int MINOR_NUMBER_INTEGER_DIGITS = 26;
   protected static final int MINOR_NUMBER_MINUS = 23;
   protected static final int MINOR_NUMBER_MINUSZERO = 25;
   protected static final int MINOR_NUMBER_ZERO = 24;
   protected static final int MINOR_ROOT_BOM = 1;
   protected static final int MINOR_ROOT_GOT_SEPARATOR = 3;
   protected static final int MINOR_ROOT_NEED_SEPARATOR = 2;
   protected static final int MINOR_VALUE_APOS_STRING = 45;
   protected static final int MINOR_VALUE_EXPECTING_COLON = 14;
   protected static final int MINOR_VALUE_EXPECTING_COMMA = 13;
   protected static final int MINOR_VALUE_LEADING_WS = 12;
   protected static final int MINOR_VALUE_STRING = 40;
   protected static final int MINOR_VALUE_STRING_ESCAPE = 41;
   protected static final int MINOR_VALUE_STRING_UTF8_2 = 42;
   protected static final int MINOR_VALUE_STRING_UTF8_3 = 43;
   protected static final int MINOR_VALUE_STRING_UTF8_4 = 44;
   protected static final int MINOR_VALUE_TOKEN_ERROR = 50;
   protected static final int MINOR_VALUE_TOKEN_FALSE = 18;
   protected static final int MINOR_VALUE_TOKEN_NON_STD = 19;
   protected static final int MINOR_VALUE_TOKEN_NULL = 16;
   protected static final int MINOR_VALUE_TOKEN_TRUE = 17;
   protected static final int MINOR_VALUE_WS_AFTER_COMMA = 15;
   protected static final String[] NON_STD_TOKENS = new String[]{"NaN", "Infinity", "+Infinity", "-Infinity"};
   protected static final int NON_STD_TOKEN_INFINITY = 1;
   protected static final int NON_STD_TOKEN_MINUS_INFINITY = 3;
   protected static final int NON_STD_TOKEN_NAN = 0;
   protected static final int NON_STD_TOKEN_PLUS_INFINITY = 2;
   protected static final double[] NON_STD_TOKEN_VALUES = new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
   protected int _currBufferStart = 0;
   protected int _currInputRowAlt = 1;
   protected boolean _endOfInput = false;
   protected int _majorState;
   protected int _majorStateAfterValue;
   protected int _minorState;
   protected int _minorStateAfterSplit;
   protected int _nonStdTokenType;
   protected int _pending32;
   protected int _pendingBytes;
   protected int _quad1;
   protected int[] _quadBuffer = new int[8];
   protected int _quadLength;
   protected int _quoted32;
   protected int _quotedDigits;
   protected final ByteQuadsCanonicalizer _symbols;

   public NonBlockingJsonParserBase(IOContext var1, int var2, ByteQuadsCanonicalizer var3) {
      super(var1, var2);
      this._symbols = var3;
      this._currToken = null;
      this._majorState = 0;
      this._majorStateAfterValue = 1;
   }

   protected static final int _padLastQuad(int var0, int var1) {
      if (var1 != 4) {
         var0 |= -1 << (var1 << 3);
      }

      return var0;
   }

   protected final String _addName(int[] var1, int var2, int var3) throws JsonParseException {
      int var4 = (var2 << 2) - 4 + var3;
      int var5;
      int var6;
      if (var3 < 4) {
         var5 = var2 - 1;
         var6 = var1[var5];
         var1[var5] = var6 << (4 - var3 << 3);
      } else {
         var6 = 0;
      }

      char[] var7 = this._textBuffer.emptyAndGetCurrentSegment();
      int var8 = 0;

      int var12;
      for(var5 = 0; var8 < var4; var5 = var12 + 1) {
         int var9 = var1[var8 >> 2] >> (3 - (var8 & 3) << 3) & 255;
         int var10 = var8 + 1;
         char[] var11 = var7;
         var8 = var10;
         var12 = var5;
         int var13 = var9;
         if (var9 > 127) {
            int var14;
            byte var16;
            label72: {
               if ((var9 & 224) == 192) {
                  var14 = var9 & 31;
               } else {
                  if ((var9 & 240) == 224) {
                     var14 = var9 & 15;
                     var16 = 2;
                     break label72;
                  }

                  if ((var9 & 248) == 240) {
                     var14 = var9 & 7;
                     var16 = 3;
                     break label72;
                  }

                  this._reportInvalidInitial(var9);
                  var14 = 1;
               }

               var16 = 1;
            }

            if (var10 + var16 > var4) {
               this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }

            var8 = var1[var10 >> 2] >> (3 - (var10 & 3) << 3);
            var13 = var10 + 1;
            if ((var8 & 192) != 128) {
               this._reportInvalidOther(var8);
            }

            var10 = var8 & 63 | var14 << 6;
            if (var16 > 1) {
               var14 = var1[var13 >> 2] >> (3 - (var13 & 3) << 3);
               var8 = var13 + 1;
               if ((var14 & 192) != 128) {
                  this._reportInvalidOther(var14);
               }

               var10 = var14 & 63 | var10 << 6;
               var13 = var10;
               var14 = var8;
               if (var16 > 2) {
                  var13 = var1[var8 >> 2] >> (3 - (var8 & 3) << 3);
                  var14 = var8 + 1;
                  if ((var13 & 192) != 128) {
                     this._reportInvalidOther(var13 & 255);
                  }

                  var13 = var10 << 6 | var13 & 63;
               }

               var10 = var13;
            } else {
               var14 = var13;
            }

            var11 = var7;
            var8 = var14;
            var12 = var5;
            var13 = var10;
            if (var16 > 2) {
               var9 = var10 - 65536;
               var11 = var7;
               if (var5 >= var7.length) {
                  var11 = this._textBuffer.expandCurrentSegment();
               }

               var11[var5] = (char)((char)((var9 >> 10) + '\ud800'));
               var13 = var9 & 1023 | '\udc00';
               var12 = var5 + 1;
               var8 = var14;
            }
         }

         var7 = var11;
         if (var12 >= var11.length) {
            var7 = this._textBuffer.expandCurrentSegment();
         }

         var7[var12] = (char)((char)var13);
      }

      String var15 = new String(var7, 0, var5);
      if (var3 < 4) {
         var1[var2 - 1] = var6;
      }

      return this._symbols.addName(var15, var1, var2);
   }

   protected final JsonToken _closeArrayScope() throws IOException {
      if (!this._parsingContext.inArray()) {
         this._reportMismatchedEndMarker(93, '}');
      }

      JsonReadContext var1 = this._parsingContext.getParent();
      this._parsingContext = var1;
      byte var2;
      if (var1.inObject()) {
         var2 = 3;
      } else if (var1.inArray()) {
         var2 = 6;
      } else {
         var2 = 1;
      }

      this._majorState = var2;
      this._majorStateAfterValue = var2;
      JsonToken var3 = JsonToken.END_ARRAY;
      this._currToken = var3;
      return var3;
   }

   protected void _closeInput() throws IOException {
      this._currBufferStart = 0;
      this._inputEnd = 0;
   }

   protected final JsonToken _closeObjectScope() throws IOException {
      if (!this._parsingContext.inObject()) {
         this._reportMismatchedEndMarker(125, ']');
      }

      JsonReadContext var1 = this._parsingContext.getParent();
      this._parsingContext = var1;
      byte var2;
      if (var1.inObject()) {
         var2 = 3;
      } else if (var1.inArray()) {
         var2 = 6;
      } else {
         var2 = 1;
      }

      this._majorState = var2;
      this._majorStateAfterValue = var2;
      JsonToken var3 = JsonToken.END_OBJECT;
      this._currToken = var3;
      return var3;
   }

   protected final JsonToken _eofAsNextToken() throws IOException {
      this._majorState = 7;
      if (!this._parsingContext.inRoot()) {
         this._handleEOF();
      }

      this.close();
      this._currToken = null;
      return null;
   }

   protected final JsonToken _fieldComplete(String var1) throws IOException {
      this._majorState = 4;
      this._parsingContext.setCurrentName(var1);
      JsonToken var2 = JsonToken.FIELD_NAME;
      this._currToken = var2;
      return var2;
   }

   protected final String _findName(int var1, int var2) throws JsonParseException {
      var1 = _padLastQuad(var1, var2);
      String var3 = this._symbols.findName(var1);
      if (var3 != null) {
         return var3;
      } else {
         int[] var4 = this._quadBuffer;
         var4[0] = var1;
         return this._addName(var4, 1, var2);
      }
   }

   protected final String _findName(int var1, int var2, int var3) throws JsonParseException {
      var2 = _padLastQuad(var2, var3);
      String var4 = this._symbols.findName(var1, var2);
      if (var4 != null) {
         return var4;
      } else {
         int[] var5 = this._quadBuffer;
         var5[0] = var1;
         var5[1] = var2;
         return this._addName(var5, 2, var3);
      }
   }

   protected final String _findName(int var1, int var2, int var3, int var4) throws JsonParseException {
      var3 = _padLastQuad(var3, var4);
      String var5 = this._symbols.findName(var1, var2, var3);
      if (var5 != null) {
         return var5;
      } else {
         int[] var6 = this._quadBuffer;
         var6[0] = var1;
         var6[1] = var2;
         var6[2] = _padLastQuad(var3, var4);
         return this._addName(var6, 3, var4);
      }
   }

   protected final String _getText2(JsonToken var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.id();
         if (var2 != -1) {
            if (var2 != 5) {
               return var2 != 6 && var2 != 7 && var2 != 8 ? var1.asString() : this._textBuffer.contentsAsString();
            } else {
               return this._parsingContext.getCurrentName();
            }
         } else {
            return null;
         }
      }
   }

   protected final String _nonStdToken(int var1) {
      return NON_STD_TOKENS[var1];
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      this._symbols.release();
   }

   protected void _reportInvalidChar(int var1) throws JsonParseException {
      if (var1 < 32) {
         this._throwInvalidSpace(var1);
      }

      this._reportInvalidInitial(var1);
   }

   protected void _reportInvalidInitial(int var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid UTF-8 start byte 0x");
      var2.append(Integer.toHexString(var1));
      this._reportError(var2.toString());
   }

   protected void _reportInvalidOther(int var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid UTF-8 middle byte 0x");
      var2.append(Integer.toHexString(var1));
      this._reportError(var2.toString());
   }

   protected void _reportInvalidOther(int var1, int var2) throws JsonParseException {
      this._inputPtr = var2;
      this._reportInvalidOther(var1);
   }

   protected final JsonToken _startArrayScope() throws IOException {
      this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
      this._majorState = 5;
      this._majorStateAfterValue = 6;
      JsonToken var1 = JsonToken.START_ARRAY;
      this._currToken = var1;
      return var1;
   }

   protected final JsonToken _startObjectScope() throws IOException {
      this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
      this._majorState = 2;
      this._majorStateAfterValue = 3;
      JsonToken var1 = JsonToken.START_OBJECT;
      this._currToken = var1;
      return var1;
   }

   protected final void _updateTokenLocation() {
      this._tokenInputRow = Math.max(this._currInputRow, this._currInputRowAlt);
      int var1 = this._inputPtr;
      this._tokenInputCol = var1 - this._currInputRowStart;
      this._tokenInputTotal = this._currInputProcessed + (long)(var1 - this._currBufferStart);
   }

   protected final JsonToken _valueComplete(JsonToken var1) throws IOException {
      this._majorState = this._majorStateAfterValue;
      this._currToken = var1;
      return var1;
   }

   protected final JsonToken _valueCompleteInt(int var1, String var2) throws IOException {
      this._textBuffer.resetWithString(var2);
      this._intLength = var2.length();
      this._numTypesValid = 1;
      this._numberInt = var1;
      this._majorState = this._majorStateAfterValue;
      JsonToken var3 = JsonToken.VALUE_NUMBER_INT;
      this._currToken = var3;
      return var3;
   }

   protected final JsonToken _valueNonStdNumberComplete(int var1) throws IOException {
      String var2 = NON_STD_TOKENS[var1];
      this._textBuffer.resetWithString(var2);
      if (!this.isEnabled(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
         this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", var2);
      }

      this._intLength = 0;
      this._numTypesValid = 8;
      this._numberDouble = NON_STD_TOKEN_VALUES[var1];
      this._majorState = this._majorStateAfterValue;
      JsonToken var3 = JsonToken.VALUE_NUMBER_FLOAT;
      this._currToken = var3;
      return var3;
   }

   public boolean canParseAsync() {
      return true;
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      if (this._currToken != JsonToken.VALUE_STRING) {
         this._reportError("Current token (%s) not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary", this._currToken);
      }

      if (this._binaryValue == null) {
         ByteArrayBuilder var2 = this._getByteArrayBuilder();
         this._decodeBase64(this.getText(), var2, var1);
         this._binaryValue = var2.toByteArray();
      }

      return this._binaryValue;
   }

   public ObjectCodec getCodec() {
      return null;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      int var3 = Math.max(this._currInputRow, this._currInputRowAlt);
      return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (long)(this._inputPtr - this._currBufferStart), -1L, var3, var1 - var2 + 1);
   }

   public Object getEmbeddedObject() throws IOException {
      return this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT ? this._binaryValue : null;
   }

   public Object getInputSource() {
      return null;
   }

   public int getText(Writer var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 == JsonToken.VALUE_STRING) {
         return this._textBuffer.contentsToWriter(var1);
      } else if (var2 == JsonToken.FIELD_NAME) {
         String var4 = this._parsingContext.getCurrentName();
         var1.write(var4);
         return var4.length();
      } else if (var2 != null) {
         if (var2.isNumeric()) {
            return this._textBuffer.contentsToWriter(var1);
         } else {
            if (var2 == JsonToken.NOT_AVAILABLE) {
               this._reportError("Current token not available: can not call this method");
            }

            char[] var3 = var2.asCharArray();
            var1.write(var3);
            return var3.length;
         }
      } else {
         return 0;
      }
   }

   public String getText() throws IOException {
      return this._currToken == JsonToken.VALUE_STRING ? this._textBuffer.contentsAsString() : this._getText2(this._currToken);
   }

   public char[] getTextCharacters() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 != 5) {
            return var1 != 6 && var1 != 7 && var1 != 8 ? this._currToken.asCharArray() : this._textBuffer.getTextBuffer();
         } else {
            if (!this._nameCopied) {
               String var2 = this._parsingContext.getCurrentName();
               var1 = var2.length();
               if (this._nameCopyBuffer == null) {
                  this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(var1);
               } else if (this._nameCopyBuffer.length < var1) {
                  this._nameCopyBuffer = new char[var1];
               }

               var2.getChars(0, var1, this._nameCopyBuffer, 0);
               this._nameCopied = true;
            }

            return this._nameCopyBuffer;
         }
      } else {
         return null;
      }
   }

   public int getTextLength() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 != 5) {
            return var1 != 6 && var1 != 7 && var1 != 8 ? this._currToken.asCharArray().length : this._textBuffer.size();
         } else {
            return this._parsingContext.getCurrentName().length();
         }
      } else {
         return 0;
      }
   }

   public int getTextOffset() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 == 6 || var1 == 7 || var1 == 8) {
            return this._textBuffer.getTextOffset();
         }
      }

      return 0;
   }

   public JsonLocation getTokenLocation() {
      return new JsonLocation(this._getSourceReference(), this._tokenInputTotal, -1L, this._tokenInputRow, this._tokenInputCol);
   }

   public String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this._textBuffer.contentsAsString();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this._textBuffer.contentsAsString();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString(var1);
      }
   }

   public boolean hasTextCharacters() {
      if (this._currToken == JsonToken.VALUE_STRING) {
         return this._textBuffer.hasTextAsCharacters();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this._nameCopied : false;
      }
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      byte[] var3 = this.getBinaryValue(var1);
      var2.write(var3);
      return var3.length;
   }

   public abstract int releaseBuffered(OutputStream var1) throws IOException;

   public void setCodec(ObjectCodec var1) {
      throw new UnsupportedOperationException("Can not use ObjectMapper with non-blocking parser");
   }

   protected ByteQuadsCanonicalizer symbolTableForTests() {
      return this._symbols;
   }
}
