package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ReaderBasedJsonParser extends ParserBase {
   private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
   private static final int FEAT_MASK_ALLOW_MISSING;
   private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
   private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
   private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
   private static final int FEAT_MASK_LEADING_ZEROS;
   private static final int FEAT_MASK_NON_NUM_NUMBERS;
   private static final int FEAT_MASK_TRAILING_COMMA;
   protected static final int[] _icLatin1;
   protected boolean _bufferRecyclable;
   protected final int _hashSeed;
   protected char[] _inputBuffer;
   protected int _nameStartCol;
   protected long _nameStartOffset;
   protected int _nameStartRow;
   protected ObjectCodec _objectCodec;
   protected Reader _reader;
   protected final CharsToNameCanonicalizer _symbols;
   protected boolean _tokenIncomplete;

   static {
      FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
      FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
      FEAT_MASK_NON_NUM_NUMBERS = JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS.getMask();
      FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
      FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
      FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
      FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
      FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
      _icLatin1 = CharTypes.getInputCodeLatin1();
   }

   public ReaderBasedJsonParser(IOContext var1, int var2, Reader var3, ObjectCodec var4, CharsToNameCanonicalizer var5) {
      super(var1, var2);
      this._reader = var3;
      this._inputBuffer = var1.allocTokenBuffer();
      this._inputPtr = 0;
      this._inputEnd = 0;
      this._objectCodec = var4;
      this._symbols = var5;
      this._hashSeed = var5.hashSeed();
      this._bufferRecyclable = true;
   }

   public ReaderBasedJsonParser(IOContext var1, int var2, Reader var3, ObjectCodec var4, CharsToNameCanonicalizer var5, char[] var6, int var7, int var8, boolean var9) {
      super(var1, var2);
      this._reader = var3;
      this._inputBuffer = var6;
      this._inputPtr = var7;
      this._inputEnd = var8;
      this._objectCodec = var4;
      this._symbols = var5;
      this._hashSeed = var5.hashSeed();
      this._bufferRecyclable = var9;
   }

   private final void _checkMatchEnd(String var1, int var2, int var3) throws IOException {
      if (Character.isJavaIdentifierPart((char)var3)) {
         this._reportInvalidToken(var1.substring(0, var2));
      }

   }

   private void _closeScope(int var1) throws JsonParseException {
      if (var1 == 93) {
         this._updateLocation();
         if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(var1, '}');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_ARRAY;
      }

      if (var1 == 125) {
         this._updateLocation();
         if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(var1, ']');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_OBJECT;
      }

   }

   private String _handleOddName2(int var1, int var2, int[] var3) throws IOException {
      this._textBuffer.resetWithShared(this._inputBuffer, var1, this._inputPtr - var1);
      char[] var4 = this._textBuffer.getCurrentSegment();
      var1 = this._textBuffer.getCurrentSegmentSize();
      int var5 = var3.length;

      int var7;
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char var6 = this._inputBuffer[this._inputPtr];
         if (var6 < var5) {
            if (var3[var6] != 0) {
               break;
            }
         } else if (!Character.isJavaIdentifierPart(var6)) {
            break;
         }

         ++this._inputPtr;
         var2 = var2 * 33 + var6;
         var7 = var1 + 1;
         var4[var1] = var6;
         if (var7 >= var4.length) {
            var4 = this._textBuffer.finishCurrentSegment();
            var1 = 0;
         } else {
            var1 = var7;
         }
      }

      this._textBuffer.setCurrentLength(var1);
      TextBuffer var8 = this._textBuffer;
      var4 = var8.getTextBuffer();
      var1 = var8.getTextOffset();
      var7 = var8.size();
      return this._symbols.findSymbol(var4, var1, var7, var2);
   }

   private final void _isNextTokenNameYes(int var1) throws IOException {
      this._currToken = JsonToken.FIELD_NAME;
      this._updateLocation();
      if (var1 != 34) {
         if (var1 != 45) {
            if (var1 != 91) {
               if (var1 != 102) {
                  if (var1 != 110) {
                     if (var1 != 116) {
                        if (var1 != 123) {
                           switch(var1) {
                           case 48:
                           case 49:
                           case 50:
                           case 51:
                           case 52:
                           case 53:
                           case 54:
                           case 55:
                           case 56:
                           case 57:
                              this._nextToken = this._parsePosNumber(var1);
                              return;
                           default:
                              this._nextToken = this._handleOddValue(var1);
                           }
                        } else {
                           this._nextToken = JsonToken.START_OBJECT;
                        }
                     } else {
                        this._matchToken("true", 1);
                        this._nextToken = JsonToken.VALUE_TRUE;
                     }
                  } else {
                     this._matchToken("null", 1);
                     this._nextToken = JsonToken.VALUE_NULL;
                  }
               } else {
                  this._matchToken("false", 1);
                  this._nextToken = JsonToken.VALUE_FALSE;
               }
            } else {
               this._nextToken = JsonToken.START_ARRAY;
            }
         } else {
            this._nextToken = this._parseNegNumber();
         }
      } else {
         this._tokenIncomplete = true;
         this._nextToken = JsonToken.VALUE_STRING;
      }
   }

   private final void _matchFalse() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 4 < this._inputEnd) {
         char[] var2 = this._inputBuffer;
         if (var2[var1] == 'a') {
            ++var1;
            if (var2[var1] == 'l') {
               ++var1;
               if (var2[var1] == 's') {
                  ++var1;
                  if (var2[var1] == 'e') {
                     int var3 = var1 + 1;
                     char var4 = var2[var3];
                     if (var4 < '0' || var4 == ']' || var4 == '}') {
                        this._inputPtr = var3;
                        return;
                     }
                  }
               }
            }
         }
      }

      this._matchToken("false", 1);
   }

   private final void _matchNull() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         char[] var2 = this._inputBuffer;
         if (var2[var1] == 'u') {
            ++var1;
            if (var2[var1] == 'l') {
               ++var1;
               if (var2[var1] == 'l') {
                  int var3 = var1 + 1;
                  char var4 = var2[var3];
                  if (var4 < '0' || var4 == ']' || var4 == '}') {
                     this._inputPtr = var3;
                     return;
                  }
               }
            }
         }
      }

      this._matchToken("null", 1);
   }

   private final void _matchToken2(String var1, int var2) throws IOException {
      int var3 = var1.length();

      int var4;
      do {
         if (this._inputPtr >= this._inputEnd && !this._loadMore() || this._inputBuffer[this._inputPtr] != var1.charAt(var2)) {
            this._reportInvalidToken(var1.substring(0, var2));
         }

         ++this._inputPtr;
         var4 = var2 + 1;
         var2 = var4;
      } while(var4 < var3);

      if (this._inputPtr < this._inputEnd || this._loadMore()) {
         char var5 = this._inputBuffer[this._inputPtr];
         if (var5 >= '0' && var5 != ']' && var5 != '}') {
            this._checkMatchEnd(var1, var4, var5);
         }

      }
   }

   private final void _matchTrue() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         char[] var2 = this._inputBuffer;
         if (var2[var1] == 'r') {
            ++var1;
            if (var2[var1] == 'u') {
               ++var1;
               if (var2[var1] == 'e') {
                  int var3 = var1 + 1;
                  char var4 = var2[var3];
                  if (var4 < '0' || var4 == ']' || var4 == '}') {
                     this._inputPtr = var3;
                     return;
                  }
               }
            }
         }
      }

      this._matchToken("true", 1);
   }

   private final JsonToken _nextAfterName() {
      this._nameCopied = false;
      JsonToken var1 = this._nextToken;
      this._nextToken = null;
      if (var1 == JsonToken.START_ARRAY) {
         this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
      } else if (var1 == JsonToken.START_OBJECT) {
         this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
      }

      this._currToken = var1;
      return var1;
   }

   private final JsonToken _nextTokenNotInObject(int var1) throws IOException {
      JsonToken var2;
      if (var1 == 34) {
         this._tokenIncomplete = true;
         var2 = JsonToken.VALUE_STRING;
         this._currToken = var2;
         return var2;
      } else {
         label48: {
            if (var1 != 44) {
               if (var1 == 45) {
                  var2 = this._parseNegNumber();
                  this._currToken = var2;
                  return var2;
               }

               if (var1 == 91) {
                  this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                  var2 = JsonToken.START_ARRAY;
                  this._currToken = var2;
                  return var2;
               }

               if (var1 != 93) {
                  if (var1 == 102) {
                     this._matchToken("false", 1);
                     var2 = JsonToken.VALUE_FALSE;
                     this._currToken = var2;
                     return var2;
                  }

                  if (var1 == 110) {
                     this._matchToken("null", 1);
                     var2 = JsonToken.VALUE_NULL;
                     this._currToken = var2;
                     return var2;
                  }

                  if (var1 == 116) {
                     this._matchToken("true", 1);
                     var2 = JsonToken.VALUE_TRUE;
                     this._currToken = var2;
                     return var2;
                  }

                  if (var1 == 123) {
                     this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                     var2 = JsonToken.START_OBJECT;
                     this._currToken = var2;
                     return var2;
                  }

                  switch(var1) {
                  case 48:
                  case 49:
                  case 50:
                  case 51:
                  case 52:
                  case 53:
                  case 54:
                  case 55:
                  case 56:
                  case 57:
                     var2 = this._parsePosNumber(var1);
                     this._currToken = var2;
                     return var2;
                  default:
                     break label48;
                  }
               }
            }

            if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
               --this._inputPtr;
               var2 = JsonToken.VALUE_NULL;
               this._currToken = var2;
               return var2;
            }
         }

         var2 = this._handleOddValue(var1);
         this._currToken = var2;
         return var2;
      }
   }

   private final JsonToken _parseFloat(int var1, int var2, int var3, boolean var4, int var5) throws IOException {
      int var6 = this._inputEnd;
      byte var7 = 0;
      int var8 = 0;
      byte var9 = 0;
      int var10;
      char[] var11;
      int var12;
      if (var1 == 46) {
         var1 = 0;
         var10 = var3;

         while(true) {
            if (var10 >= var6) {
               return this._parseNumber2(var4, var2);
            }

            var11 = this._inputBuffer;
            var3 = var10 + 1;
            var10 = var11[var10];
            if (var10 < 48 || var10 > 57) {
               if (var1 == 0) {
                  this.reportUnexpectedNumberChar(var10, "Decimal point not followed by a digit");
               }

               var12 = var1;
               var1 = var3;
               var3 = var12;
               break;
            }

            ++var1;
            var10 = var3;
         }
      } else {
         var12 = var3;
         var3 = 0;
         var10 = var1;
         var1 = var12;
      }

      int var13;
      int var14;
      int var15;
      label83: {
         if (var10 != 101) {
            var13 = var1;
            var14 = var10;
            var15 = var3;
            if (var10 != 69) {
               break label83;
            }
         }

         if (var1 >= var6) {
            this._inputPtr = var2;
            return this._parseNumber2(var4, var2);
         }

         char var17;
         label84: {
            var11 = this._inputBuffer;
            var15 = var1 + 1;
            char var16 = var11[var1];
            if (var16 != '-') {
               var17 = var16;
               if (var16 != '+') {
                  var12 = var9;
                  var1 = var15;
                  var3 = var3;
                  break label84;
               }
            }

            if (var15 >= var6) {
               this._inputPtr = var2;
               return this._parseNumber2(var4, var2);
            }

            var11 = this._inputBuffer;
            var1 = var15 + 1;
            var17 = var11[var15];
         }

         for(var12 = var7; var17 <= '9' && var17 >= '0'; var3 = var14) {
            var14 = var12 + 1;
            if (var1 >= var6) {
               this._inputPtr = var2;
               return this._parseNumber2(var4, var2);
            }

            var11 = this._inputBuffer;
            var12 = var1 + 1;
            var17 = var11[var1];
            var1 = var14;
            var14 = var3;
            var3 = var12;
            var12 = var1;
            var1 = var3;
         }

         var8 = var12;
         var13 = var1;
         var14 = var17;
         var15 = var3;
         if (var12 == 0) {
            this.reportUnexpectedNumberChar(var17, "Exponent indicator not followed by a digit");
            var15 = var3;
            var14 = var17;
            var13 = var1;
            var8 = var12;
         }
      }

      var1 = var13 - 1;
      this._inputPtr = var1;
      if (this._parsingContext.inRoot()) {
         this._verifyRootSpace(var14);
      }

      this._textBuffer.resetWithShared(this._inputBuffer, var2, var1 - var2);
      return this.resetFloat(var4, var5, var15, var8);
   }

   private String _parseName2(int var1, int var2, int var3) throws IOException {
      this._textBuffer.resetWithShared(this._inputBuffer, var1, this._inputPtr - var1);
      char[] var4 = this._textBuffer.getCurrentSegment();
      var1 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
         }

         char[] var5 = this._inputBuffer;
         int var6 = this._inputPtr++;
         char var7 = var5[var6];
         char var9 = var7;
         if (var7 <= '\\') {
            if (var7 == '\\') {
               var9 = this._decodeEscaped();
            } else {
               var9 = var7;
               if (var7 <= var3) {
                  if (var7 == var3) {
                     this._textBuffer.setCurrentLength(var1);
                     TextBuffer var8 = this._textBuffer;
                     var5 = var8.getTextBuffer();
                     var1 = var8.getTextOffset();
                     var3 = var8.size();
                     return this._symbols.findSymbol(var5, var1, var3, var2);
                  }

                  var9 = var7;
                  if (var7 < ' ') {
                     this._throwUnquotedSpace(var7, "name");
                     var9 = var7;
                  }
               }
            }
         }

         var2 = var2 * 33 + var9;
         int var10 = var1 + 1;
         var4[var1] = (char)var9;
         if (var10 >= var4.length) {
            var4 = this._textBuffer.finishCurrentSegment();
            var1 = 0;
         } else {
            var1 = var10;
         }
      }
   }

   private final JsonToken _parseNumber2(boolean var1, int var2) throws IOException {
      int var3 = var2;
      if (var1) {
         var3 = var2 + 1;
      }

      this._inputPtr = var3;
      char[] var4 = this._textBuffer.emptyAndGetCurrentSegment();
      byte var5 = 0;
      int var6;
      if (var1) {
         var4[0] = (char)45;
         var6 = 1;
      } else {
         var6 = 0;
      }

      char[] var7;
      char var14;
      if (this._inputPtr < this._inputEnd) {
         var7 = this._inputBuffer;
         var2 = this._inputPtr++;
         var14 = var7[var2];
      } else {
         var14 = this.getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
      }

      char var15 = var14;
      if (var14 == '0') {
         var15 = this._verifyNoLeadingZeroes();
      }

      int var8 = 0;

      int var9;
      int var10;
      boolean var16;
      boolean var19;
      while(true) {
         if (var15 < '0' || var15 > '9') {
            var16 = false;
            var7 = var4;
            var10 = var8;
            break;
         }

         ++var8;
         var9 = var6;
         var7 = var4;
         if (var6 >= var4.length) {
            var7 = this._textBuffer.finishCurrentSegment();
            var9 = 0;
         }

         var2 = var9 + 1;
         var7[var9] = (char)var15;
         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            var15 = 0;
            var19 = true;
            var6 = var2;
            var10 = var8;
            var16 = var19;
            break;
         }

         var4 = this._inputBuffer;
         var3 = this._inputPtr++;
         var15 = var4[var3];
         var6 = var2;
         var4 = var7;
      }

      if (var10 == 0) {
         return this._handleInvalidNumberStart(var15, var1);
      } else {
         int var13;
         char var18;
         char var20;
         if (var15 == '.') {
            var8 = var6;
            var4 = var7;
            if (var6 >= var7.length) {
               var4 = this._textBuffer.finishCurrentSegment();
               var8 = 0;
            }

            var4[var8] = (char)var15;
            var6 = var8 + 1;
            var9 = 0;
            var7 = var4;
            char var11 = var15;
            var3 = var6;

            boolean var12;
            while(true) {
               if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                  var12 = true;
                  break;
               }

               var4 = this._inputBuffer;
               var6 = this._inputPtr++;
               var18 = var4[var6];
               var11 = var18;
               var12 = var16;
               if (var18 < '0') {
                  break;
               }

               if (var18 > '9') {
                  var11 = var18;
                  var12 = var16;
                  break;
               }

               ++var9;
               var8 = var3;
               var4 = var7;
               if (var3 >= var7.length) {
                  var4 = this._textBuffer.finishCurrentSegment();
                  var8 = 0;
               }

               var4[var8] = (char)var18;
               var3 = var8 + 1;
               var11 = var18;
               var7 = var4;
            }

            var6 = var3;
            var20 = var11;
            var16 = var12;
            var13 = var9;
            var4 = var7;
            if (var9 == 0) {
               this.reportUnexpectedNumberChar(var11, "Decimal point not followed by a digit");
               var6 = var3;
               var20 = var11;
               var16 = var12;
               var13 = var9;
               var4 = var7;
            }
         } else {
            var13 = 0;
            var4 = var7;
            var20 = var15;
         }

         char var17;
         int var22;
         int var23;
         label155: {
            if (var20 != 'e') {
               var23 = var5;
               var22 = var6;
               var17 = var20;
               var19 = var16;
               if (var20 != 'E') {
                  break label155;
               }
            }

            var3 = var6;
            var7 = var4;
            if (var6 >= var4.length) {
               var7 = this._textBuffer.finishCurrentSegment();
               var3 = 0;
            }

            var9 = var3 + 1;
            var7[var3] = (char)var20;
            if (this._inputPtr < this._inputEnd) {
               var4 = this._inputBuffer;
               var3 = this._inputPtr++;
               var18 = var4[var3];
            } else {
               var18 = this.getNextChar("expected a digit for number exponent");
            }

            label156: {
               if (var18 != '-') {
                  var20 = var18;
                  var3 = var9;
                  var4 = var7;
                  if (var18 != '+') {
                     break label156;
                  }
               }

               var3 = var9;
               var4 = var7;
               if (var9 >= var7.length) {
                  var4 = this._textBuffer.finishCurrentSegment();
                  var3 = 0;
               }

               var4[var3] = (char)var18;
               if (this._inputPtr < this._inputEnd) {
                  var7 = this._inputBuffer;
                  var6 = this._inputPtr++;
                  var20 = var7[var6];
               } else {
                  var20 = this.getNextChar("expected a digit for number exponent");
               }

               ++var3;
            }

            var18 = var20;

            boolean var21;
            label110: {
               for(var8 = 0; var18 <= '9' && var18 >= '0'; var4 = var7) {
                  var9 = var8 + 1;
                  var22 = var3;
                  var7 = var4;
                  if (var3 >= var4.length) {
                     var7 = this._textBuffer.finishCurrentSegment();
                     var22 = 0;
                  }

                  var8 = var22 + 1;
                  var7[var22] = (char)var18;
                  if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                     var2 = var9;
                     var21 = true;
                     break label110;
                  }

                  var4 = this._inputBuffer;
                  var3 = this._inputPtr++;
                  var18 = var4[var3];
                  var3 = var8;
                  var8 = var9;
               }

               var9 = var8;
               var8 = var3;
               var21 = var16;
               var2 = var9;
            }

            var23 = var2;
            var22 = var8;
            var17 = var18;
            var19 = var21;
            if (var2 == 0) {
               this.reportUnexpectedNumberChar(var18, "Exponent indicator not followed by a digit");
               var19 = var21;
               var17 = var18;
               var22 = var8;
               var23 = var2;
            }
         }

         if (!var19) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
               this._verifyRootSpace(var17);
            }
         }

         this._textBuffer.setCurrentLength(var22);
         return this.reset(var1, var10, var13, var23);
      }
   }

   private final int _skipAfterComma2() throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         char var4 = var1[var2];
         if (var4 > ' ') {
            if (var4 == '/') {
               this._skipComment();
            } else if (var4 != '#' || !this._skipYAMLComment()) {
               return var4;
            }
         } else if (var4 < ' ') {
            if (var4 == '\n') {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var4 == '\r') {
               this._skipCR();
            } else if (var4 != '\t') {
               this._throwInvalidSpace(var4);
            }
         }
      }

      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected end-of-input within/between ");
      var3.append(this._parsingContext.typeDesc());
      var3.append(" entries");
      throw this._constructError(var3.toString());
   }

   private void _skipCComment() throws IOException {
      label45:
      do {
         while(this._inputPtr < this._inputEnd || this._loadMore()) {
            char[] var1 = this._inputBuffer;
            int var2 = this._inputPtr++;
            char var3 = var1[var2];
            if (var3 <= '*') {
               if (var3 == '*') {
                  if (this._inputPtr < this._inputEnd || this._loadMore()) {
                     continue label45;
                  }
                  break;
               }

               if (var3 < ' ') {
                  if (var3 == '\n') {
                     ++this._currInputRow;
                     this._currInputRowStart = this._inputPtr;
                  } else if (var3 == '\r') {
                     this._skipCR();
                  } else if (var3 != '\t') {
                     this._throwInvalidSpace(var3);
                  }
               }
            }
         }

         this._reportInvalidEOF(" in a comment", (JsonToken)null);
         return;
      } while(this._inputBuffer[this._inputPtr] != '/');

      ++this._inputPtr;
   }

   private final int _skipColon() throws IOException {
      if (this._inputPtr + 4 >= this._inputEnd) {
         return this._skipColon2(false);
      } else {
         char var1 = this._inputBuffer[this._inputPtr];
         char[] var2;
         char var3;
         int var4;
         if (var1 == ':') {
            var2 = this._inputBuffer;
            var4 = this._inputPtr + 1;
            this._inputPtr = var4;
            var3 = var2[var4];
            if (var3 > ' ') {
               if (var3 != '/' && var3 != '#') {
                  ++this._inputPtr;
                  return var3;
               } else {
                  return this._skipColon2(true);
               }
            } else {
               if (var3 == ' ' || var3 == '\t') {
                  var2 = this._inputBuffer;
                  var4 = this._inputPtr + 1;
                  this._inputPtr = var4;
                  var3 = var2[var4];
                  if (var3 > ' ') {
                     if (var3 != '/' && var3 != '#') {
                        ++this._inputPtr;
                        return var3;
                     }

                     return this._skipColon2(true);
                  }
               }

               return this._skipColon2(true);
            }
         } else {
            label78: {
               if (var1 != ' ') {
                  var3 = var1;
                  if (var1 != '\t') {
                     break label78;
                  }
               }

               var2 = this._inputBuffer;
               var4 = this._inputPtr + 1;
               this._inputPtr = var4;
               var3 = var2[var4];
            }

            if (var3 == ':') {
               var2 = this._inputBuffer;
               var4 = this._inputPtr + 1;
               this._inputPtr = var4;
               var3 = var2[var4];
               if (var3 > ' ') {
                  if (var3 != '/' && var3 != '#') {
                     ++this._inputPtr;
                     return var3;
                  } else {
                     return this._skipColon2(true);
                  }
               } else {
                  if (var3 == ' ' || var3 == '\t') {
                     var2 = this._inputBuffer;
                     var4 = this._inputPtr + 1;
                     this._inputPtr = var4;
                     var3 = var2[var4];
                     if (var3 > ' ') {
                        if (var3 != '/' && var3 != '#') {
                           ++this._inputPtr;
                           return var3;
                        }

                        return this._skipColon2(true);
                     }
                  }

                  return this._skipColon2(true);
               }
            } else {
               return this._skipColon2(false);
            }
         }
      }
   }

   private final int _skipColon2(boolean var1) throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         char var5 = var2[var3];
         if (var5 > ' ') {
            if (var5 == '/') {
               this._skipComment();
            } else if (var5 != '#' || !this._skipYAMLComment()) {
               if (var1) {
                  return var5;
               }

               if (var5 != ':') {
                  this._reportUnexpectedChar(var5, "was expecting a colon to separate field name and value");
               }

               var1 = true;
            }
         } else if (var5 < ' ') {
            if (var5 == '\n') {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var5 == '\r') {
               this._skipCR();
            } else if (var5 != '\t') {
               this._throwInvalidSpace(var5);
            }
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(" within/between ");
      var4.append(this._parsingContext.typeDesc());
      var4.append(" entries");
      this._reportInvalidEOF(var4.toString(), (JsonToken)null);
      return -1;
   }

   private final int _skipColonFast(int var1) throws IOException {
      char[] var2 = this._inputBuffer;
      int var3 = var1 + 1;
      char var4 = var2[var1];
      char var5;
      char var8;
      int var9;
      if (var4 == ':') {
         var9 = var3 + 1;
         var8 = var2[var3];
         if (var8 > ' ') {
            var1 = var9;
            if (var8 != '/') {
               var1 = var9;
               if (var8 != '#') {
                  this._inputPtr = var9;
                  return var8;
               }
            }
         } else {
            label50: {
               if (var8 != ' ') {
                  var1 = var9;
                  if (var8 != '\t') {
                     break label50;
                  }
               }

               var2 = this._inputBuffer;
               var1 = var9 + 1;
               var5 = var2[var9];
               if (var5 > ' ' && var5 != '/' && var5 != '#') {
                  this._inputPtr = var1;
                  return var5;
               }
            }
         }

         this._inputPtr = var1 - 1;
         return this._skipColon2(true);
      } else {
         label65: {
            if (var4 != ' ') {
               var1 = var3;
               var5 = var4;
               if (var4 != '\t') {
                  break label65;
               }
            }

            var5 = this._inputBuffer[var3];
            var1 = var3 + 1;
         }

         boolean var6;
         if (var5 == ':') {
            var6 = true;
         } else {
            var6 = false;
         }

         var9 = var1;
         if (var6) {
            var2 = this._inputBuffer;
            var9 = var1 + 1;
            char var7 = var2[var1];
            if (var7 > ' ') {
               if (var7 != '/' && var7 != '#') {
                  this._inputPtr = var9;
                  return var7;
               }
            } else if (var7 == ' ' || var7 == '\t') {
               var2 = this._inputBuffer;
               var1 = var9 + 1;
               var8 = var2[var9];
               var9 = var1;
               if (var8 > ' ') {
                  var9 = var1;
                  if (var8 != '/') {
                     var9 = var1;
                     if (var8 != '#') {
                        this._inputPtr = var1;
                        return var8;
                     }
                  }
               }
            }
         }

         this._inputPtr = var9 - 1;
         return this._skipColon2(var6);
      }
   }

   private final int _skipComma(int var1) throws IOException {
      if (var1 != 44) {
         StringBuilder var2 = new StringBuilder();
         var2.append("was expecting comma to separate ");
         var2.append(this._parsingContext.typeDesc());
         var2.append(" entries");
         this._reportUnexpectedChar(var1, var2.toString());
      }

      while(this._inputPtr < this._inputEnd) {
         char[] var4 = this._inputBuffer;
         var1 = this._inputPtr++;
         char var3 = var4[var1];
         if (var3 > ' ') {
            if (var3 != '/' && var3 != '#') {
               return var3;
            }

            --this._inputPtr;
            return this._skipAfterComma2();
         }

         if (var3 < ' ') {
            if (var3 == '\n') {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 == '\r') {
               this._skipCR();
            } else if (var3 != '\t') {
               this._throwInvalidSpace(var3);
            }
         }
      }

      return this._skipAfterComma2();
   }

   private void _skipComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(" in a comment", (JsonToken)null);
      }

      char[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      char var3 = var1[var2];
      if (var3 == '/') {
         this._skipLine();
      } else if (var3 == '*') {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var3, "was expecting either '*' or '/' for a comment");
      }

   }

   private void _skipLine() throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         char var3 = var1[var2];
         if (var3 < ' ') {
            if (var3 == '\n') {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
               break;
            } else if (var3 != '\r') {
               if (var3 != '\t') {
                  this._throwInvalidSpace(var3);
               }
            } else {
               this._skipCR();
               break;
            }
         }
      }

   }

   private final int _skipWSOrEnd() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         return this._eofAsNextChar();
      } else {
         char[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         char var3 = var1[var2];
         if (var3 > ' ') {
            if (var3 != '/' && var3 != '#') {
               return var3;
            } else {
               --this._inputPtr;
               return this._skipWSOrEnd2();
            }
         } else {
            if (var3 != ' ') {
               if (var3 == '\n') {
                  ++this._currInputRow;
                  this._currInputRowStart = this._inputPtr;
               } else if (var3 == '\r') {
                  this._skipCR();
               } else if (var3 != '\t') {
                  this._throwInvalidSpace(var3);
               }
            }

            while(this._inputPtr < this._inputEnd) {
               var1 = this._inputBuffer;
               var2 = this._inputPtr++;
               var3 = var1[var2];
               if (var3 > ' ') {
                  if (var3 != '/' && var3 != '#') {
                     return var3;
                  }

                  --this._inputPtr;
                  return this._skipWSOrEnd2();
               }

               if (var3 != ' ') {
                  if (var3 == '\n') {
                     ++this._currInputRow;
                     this._currInputRowStart = this._inputPtr;
                  } else if (var3 == '\r') {
                     this._skipCR();
                  } else if (var3 != '\t') {
                     this._throwInvalidSpace(var3);
                  }
               }
            }

            return this._skipWSOrEnd2();
         }
      }
   }

   private int _skipWSOrEnd2() throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         char var3 = var1[var2];
         if (var3 > ' ') {
            if (var3 == '/') {
               this._skipComment();
            } else if (var3 != '#' || !this._skipYAMLComment()) {
               return var3;
            }
         } else if (var3 != ' ') {
            if (var3 == '\n') {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 == '\r') {
               this._skipCR();
            } else if (var3 != '\t') {
               this._throwInvalidSpace(var3);
            }
         }
      }

      return this._eofAsNextChar();
   }

   private boolean _skipYAMLComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
         return false;
      } else {
         this._skipLine();
         return true;
      }
   }

   private final void _updateLocation() {
      int var1 = this._inputPtr;
      this._tokenInputTotal = this._currInputProcessed + (long)var1;
      this._tokenInputRow = this._currInputRow;
      this._tokenInputCol = var1 - this._currInputRowStart;
   }

   private final void _updateNameLocation() {
      int var1 = this._inputPtr;
      this._nameStartOffset = (long)var1;
      this._nameStartRow = this._currInputRow;
      this._nameStartCol = var1 - this._currInputRowStart;
   }

   private char _verifyNLZ2() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         return '0';
      } else {
         char var1 = this._inputBuffer[this._inputPtr];
         if (var1 >= '0' && var1 <= '9') {
            if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }

            ++this._inputPtr;
            char var2 = var1;
            if (var1 == '0') {
               while(true) {
                  if (this._inputPtr >= this._inputEnd) {
                     var2 = var1;
                     if (!this._loadMore()) {
                        break;
                     }
                  }

                  char var3 = this._inputBuffer[this._inputPtr];
                  if (var3 < '0' || var3 > '9') {
                     return '0';
                  }

                  ++this._inputPtr;
                  var1 = var3;
                  if (var3 != '0') {
                     var2 = var3;
                     break;
                  }
               }
            }

            return var2;
         } else {
            return '0';
         }
      }
   }

   private final char _verifyNoLeadingZeroes() throws IOException {
      if (this._inputPtr < this._inputEnd) {
         char var1 = this._inputBuffer[this._inputPtr];
         if (var1 < '0' || var1 > '9') {
            return '0';
         }
      }

      return this._verifyNLZ2();
   }

   private final void _verifyRootSpace(int var1) throws IOException {
      ++this._inputPtr;
      if (var1 != 9) {
         if (var1 != 10) {
            if (var1 == 13) {
               this._skipCR();
               return;
            }

            if (var1 != 32) {
               this._reportMissingRootWS(var1);
               return;
            }
         } else {
            ++this._currInputRow;
            this._currInputRowStart = this._inputPtr;
         }
      }

   }

   protected void _closeInput() throws IOException {
      if (this._reader != null) {
         if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._reader.close();
         }

         this._reader = null;
      }

   }

   protected byte[] _decodeBase64(Base64Variant var1) throws IOException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         while(true) {
            char[] var3;
            int var4;
            char var5;
            int var6;
            do {
               do {
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var3 = this._inputBuffer;
                  var4 = this._inputPtr++;
                  var5 = var3[var4];
               } while(var5 <= ' ');

               var6 = var1.decodeBase64Char(var5);
               var4 = var6;
               if (var6 >= 0) {
                  break;
               }

               if (var5 == '"') {
                  return var2.toByteArray();
               }

               var6 = this._decodeBase64Escape(var1, var5, 0);
               var4 = var6;
            } while(var6 < 0);

            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            var3 = this._inputBuffer;
            var6 = this._inputPtr++;
            var5 = var3[var6];
            int var7 = var1.decodeBase64Char(var5);
            var6 = var7;
            if (var7 < 0) {
               var6 = this._decodeBase64Escape(var1, var5, 1);
            }

            int var8 = var4 << 6 | var6;
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            var3 = this._inputBuffer;
            var4 = this._inputPtr++;
            var5 = var3[var4];
            var7 = var1.decodeBase64Char(var5);
            var6 = var7;
            if (var7 < 0) {
               var4 = var7;
               if (var7 != -2) {
                  if (var5 == '"') {
                     var2.append(var8 >> 4);
                     if (var1.usesPadding()) {
                        --this._inputPtr;
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var5, 2);
               }

               var6 = var4;
               if (var4 == -2) {
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var3 = this._inputBuffer;
                  var4 = this._inputPtr++;
                  var5 = var3[var4];
                  if (!var1.usesPaddingChar(var5) && this._decodeBase64Escape(var1, var5, 3) != -2) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("expected padding character '");
                     var9.append(var1.getPaddingChar());
                     var9.append("'");
                     throw this.reportInvalidBase64Char(var1, var5, 3, var9.toString());
                  }

                  var2.append(var8 >> 4);
                  continue;
               }
            }

            var8 = var8 << 6 | var6;
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            var3 = this._inputBuffer;
            var4 = this._inputPtr++;
            var5 = var3[var4];
            var7 = var1.decodeBase64Char(var5);
            var6 = var7;
            if (var7 < 0) {
               var4 = var7;
               if (var7 != -2) {
                  if (var5 == '"') {
                     var2.appendTwoBytes(var8 >> 2);
                     if (var1.usesPadding()) {
                        --this._inputPtr;
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var5, 3);
               }

               var6 = var4;
               if (var4 == -2) {
                  var2.appendTwoBytes(var8 >> 2);
                  continue;
               }
            }

            var2.appendThreeBytes(var8 << 6 | var6);
         }
      }
   }

   protected char _decodeEscaped() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
      }

      char[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      char var3 = var1[var2];
      char var4 = var3;
      if (var3 != '"') {
         var4 = var3;
         if (var3 != '/') {
            var4 = var3;
            if (var3 != '\\') {
               if (var3 != 'b') {
                  if (var3 != 'f') {
                     if (var3 != 'n') {
                        if (var3 != 'r') {
                           if (var3 != 't') {
                              if (var3 != 'u') {
                                 return this._handleUnrecognizedCharacterEscape(var3);
                              }

                              int var5 = 0;

                              for(var2 = 0; var5 < 4; ++var5) {
                                 if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                    this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                                 }

                                 var1 = this._inputBuffer;
                                 int var6 = this._inputPtr++;
                                 char var7 = var1[var6];
                                 var6 = CharTypes.charToHex(var7);
                                 if (var6 < 0) {
                                    this._reportUnexpectedChar(var7, "expected a hex-digit for character escape sequence");
                                 }

                                 var2 = var2 << 4 | var6;
                              }

                              return (char)var2;
                           }

                           return '\t';
                        }

                        return '\r';
                     }

                     return '\n';
                  }

                  return '\f';
               }

               byte var8 = 8;
               var4 = (char)var8;
            }
         }
      }

      return var4;
   }

   protected final void _finishString() throws IOException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      int var3 = var1;
      if (var1 < var2) {
         int[] var4 = _icLatin1;
         int var5 = var4.length;

         do {
            char var6 = this._inputBuffer[var1];
            if (var6 < var5 && var4[var6] != 0) {
               var3 = var1;
               if (var6 == '"') {
                  this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, var1 - this._inputPtr);
                  this._inputPtr = var1 + 1;
                  return;
               }
               break;
            }

            var3 = var1 + 1;
            var1 = var3;
         } while(var3 < var2);
      }

      this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, var3 - this._inputPtr);
      this._inputPtr = var3;
      this._finishString2();
   }

   protected void _finishString2() throws IOException {
      char[] var1 = this._textBuffer.getCurrentSegment();
      int var2 = this._textBuffer.getCurrentSegmentSize();
      int[] var3 = _icLatin1;
      int var4 = var3.length;

      while(true) {
         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
         }

         char[] var5 = this._inputBuffer;
         int var6 = this._inputPtr++;
         char var7 = var5[var6];
         char var8 = var7;
         if (var7 < var4) {
            var8 = var7;
            if (var3[var7] != 0) {
               if (var7 == '"') {
                  this._textBuffer.setCurrentLength(var2);
                  return;
               }

               if (var7 == '\\') {
                  var8 = this._decodeEscaped();
               } else {
                  var8 = var7;
                  if (var7 < ' ') {
                     this._throwUnquotedSpace(var7, "string value");
                     var8 = var7;
                  }
               }
            }
         }

         var5 = var1;
         int var9 = var2;
         if (var2 >= var1.length) {
            var5 = this._textBuffer.finishCurrentSegment();
            var9 = 0;
         }

         var5[var9] = (char)var8;
         var2 = var9 + 1;
         var1 = var5;
      }
   }

   protected final String _getText2(JsonToken var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.id();
         if (var2 != 5) {
            return var2 != 6 && var2 != 7 && var2 != 8 ? var1.asString() : this._textBuffer.contentsAsString();
         } else {
            return this._parsingContext.getCurrentName();
         }
      }
   }

   protected JsonToken _handleApos() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      int var2 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
         }

         char[] var3 = this._inputBuffer;
         int var4 = this._inputPtr++;
         char var5 = var3[var4];
         char var6 = var5;
         if (var5 <= '\\') {
            if (var5 == '\\') {
               var6 = this._decodeEscaped();
            } else {
               var6 = var5;
               if (var5 <= '\'') {
                  if (var5 == '\'') {
                     this._textBuffer.setCurrentLength(var2);
                     return JsonToken.VALUE_STRING;
                  }

                  var6 = var5;
                  if (var5 < ' ') {
                     this._throwUnquotedSpace(var5, "string value");
                     var6 = var5;
                  }
               }
            }
         }

         var3 = var1;
         int var7 = var2;
         if (var2 >= var1.length) {
            var3 = this._textBuffer.finishCurrentSegment();
            var7 = 0;
         }

         var3[var7] = (char)var6;
         var2 = var7 + 1;
         var1 = var3;
      }
   }

   protected JsonToken _handleInvalidNumberStart(int var1, boolean var2) throws IOException {
      int var3 = var1;
      if (var1 == 73) {
         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
         }

         char[] var4 = this._inputBuffer;
         var1 = this._inputPtr++;
         char var8 = var4[var1];
         double var5 = Double.NEGATIVE_INFINITY;
         StringBuilder var7;
         String var9;
         if (var8 == 'N') {
            if (var2) {
               var9 = "-INF";
            } else {
               var9 = "+INF";
            }

            this._matchToken(var9, 3);
            if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
               if (!var2) {
                  var5 = Double.POSITIVE_INFINITY;
               }

               return this.resetAsNaN(var9, var5);
            }

            var7 = new StringBuilder();
            var7.append("Non-standard token '");
            var7.append(var9);
            var7.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            this._reportError(var7.toString());
            var3 = var8;
         } else {
            var3 = var8;
            if (var8 == 'n') {
               if (var2) {
                  var9 = "-Infinity";
               } else {
                  var9 = "+Infinity";
               }

               this._matchToken(var9, 3);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  if (!var2) {
                     var5 = Double.POSITIVE_INFINITY;
                  }

                  return this.resetAsNaN(var9, var5);
               }

               var7 = new StringBuilder();
               var7.append("Non-standard token '");
               var7.append(var9);
               var7.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               this._reportError(var7.toString());
               var3 = var8;
            }
         }
      }

      this.reportUnexpectedNumberChar(var3, "expected digit (0-9) to follow minus sign, for valid numeric value");
      return null;
   }

   protected String _handleOddName(int var1) throws IOException {
      if (var1 == 39 && (this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
         return this._parseAposName();
      } else {
         if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar(var1, "was expecting double-quote to start field name");
         }

         int[] var2 = CharTypes.getInputCodeLatin1JsNames();
         int var3 = var2.length;
         boolean var4;
         if (var1 < var3) {
            if (var2[var1] == 0) {
               var4 = true;
            } else {
               var4 = false;
            }
         } else {
            var4 = Character.isJavaIdentifierPart((char)var1);
         }

         if (!var4) {
            this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
         }

         int var5 = this._inputPtr;
         int var6 = this._hashSeed;
         int var7 = this._inputEnd;
         int var8 = var6;
         var1 = var5;
         if (var5 < var7) {
            var1 = var5;

            do {
               char var9 = this._inputBuffer[var1];
               if (var9 < var3) {
                  if (var2[var9] != 0) {
                     var8 = this._inputPtr - 1;
                     this._inputPtr = var1;
                     return this._symbols.findSymbol(this._inputBuffer, var8, var1 - var8, var6);
                  }
               } else if (!Character.isJavaIdentifierPart((char)var9)) {
                  var8 = this._inputPtr - 1;
                  this._inputPtr = var1;
                  return this._symbols.findSymbol(this._inputBuffer, var8, var1 - var8, var6);
               }

               var8 = var6 * 33 + var9;
               var5 = var1 + 1;
               var6 = var8;
               var1 = var5;
            } while(var5 < var7);

            var1 = var5;
         }

         var6 = this._inputPtr;
         this._inputPtr = var1;
         return this._handleOddName2(var6 - 1, var8, var2);
      }
   }

   protected JsonToken _handleOddValue(int var1) throws IOException {
      if (var1 != 39) {
         if (var1 != 73) {
            if (var1 != 78) {
               label47: {
                  if (var1 != 93) {
                     if (var1 == 43) {
                        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                           this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                        }

                        char[] var2 = this._inputBuffer;
                        var1 = this._inputPtr++;
                        return this._handleInvalidNumberStart(var2[var1], false);
                     }

                     if (var1 != 44) {
                        break label47;
                     }
                  } else if (!this._parsingContext.inArray()) {
                     break label47;
                  }

                  if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                     --this._inputPtr;
                     return JsonToken.VALUE_NULL;
                  }
               }
            } else {
               this._matchToken("NaN", 1);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  return this.resetAsNaN("NaN", Double.NaN);
               }

               this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
         } else {
            this._matchToken("Infinity", 1);
            if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
               return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
            }

            this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
         }
      } else if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
         return this._handleApos();
      }

      StringBuilder var3;
      if (Character.isJavaIdentifierStart(var1)) {
         var3 = new StringBuilder();
         var3.append("");
         var3.append((char)var1);
         this._reportInvalidToken(var3.toString(), this._validJsonTokenList());
      }

      var3 = new StringBuilder();
      var3.append("expected a valid value ");
      var3.append(this._validJsonValueList());
      this._reportUnexpectedChar(var1, var3.toString());
      return null;
   }

   protected boolean _isNextTokenNameMaybe(int var1, String var2) throws IOException {
      String var3;
      if (var1 == 34) {
         var3 = this._parseName();
      } else {
         var3 = this._handleOddName(var1);
      }

      this._parsingContext.setCurrentName(var3);
      this._currToken = JsonToken.FIELD_NAME;
      var1 = this._skipColon();
      this._updateLocation();
      if (var1 == 34) {
         this._tokenIncomplete = true;
         this._nextToken = JsonToken.VALUE_STRING;
         return var2.equals(var3);
      } else {
         JsonToken var4;
         if (var1 != 45) {
            if (var1 != 91) {
               if (var1 != 102) {
                  if (var1 != 110) {
                     if (var1 != 116) {
                        if (var1 != 123) {
                           switch(var1) {
                           case 48:
                           case 49:
                           case 50:
                           case 51:
                           case 52:
                           case 53:
                           case 54:
                           case 55:
                           case 56:
                           case 57:
                              var4 = this._parsePosNumber(var1);
                              break;
                           default:
                              var4 = this._handleOddValue(var1);
                           }
                        } else {
                           var4 = JsonToken.START_OBJECT;
                        }
                     } else {
                        this._matchTrue();
                        var4 = JsonToken.VALUE_TRUE;
                     }
                  } else {
                     this._matchNull();
                     var4 = JsonToken.VALUE_NULL;
                  }
               } else {
                  this._matchFalse();
                  var4 = JsonToken.VALUE_FALSE;
               }
            } else {
               var4 = JsonToken.START_ARRAY;
            }
         } else {
            var4 = this._parseNegNumber();
         }

         this._nextToken = var4;
         return var2.equals(var3);
      }
   }

   protected boolean _loadMore() throws IOException {
      int var1 = this._inputEnd;
      Reader var2 = this._reader;
      if (var2 != null) {
         char[] var3 = this._inputBuffer;
         int var4 = var2.read(var3, 0, var3.length);
         if (var4 > 0) {
            this._inputPtr = 0;
            this._inputEnd = var4;
            long var5 = this._currInputProcessed;
            long var7 = (long)var1;
            this._currInputProcessed = var5 + var7;
            this._currInputRowStart -= var1;
            this._nameStartOffset -= var7;
            return true;
         }

         this._closeInput();
         if (var4 == 0) {
            StringBuilder var9 = new StringBuilder();
            var9.append("Reader returned 0 characters when trying to read ");
            var9.append(this._inputEnd);
            throw new IOException(var9.toString());
         }
      }

      return false;
   }

   protected void _loadMoreGuaranteed() throws IOException {
      if (!this._loadMore()) {
         this._reportInvalidEOF();
      }

   }

   protected final void _matchToken(String var1, int var2) throws IOException {
      int var3 = var1.length();
      int var4 = var2;
      if (this._inputPtr + var3 >= this._inputEnd) {
         this._matchToken2(var1, var2);
      } else {
         do {
            if (this._inputBuffer[this._inputPtr] != var1.charAt(var4)) {
               this._reportInvalidToken(var1.substring(0, var4));
            }

            ++this._inputPtr;
            var2 = var4 + 1;
            var4 = var2;
         } while(var2 < var3);

         char var5 = this._inputBuffer[this._inputPtr];
         if (var5 >= '0' && var5 != ']' && var5 != '}') {
            this._checkMatchEnd(var1, var2, var5);
         }

      }
   }

   protected String _parseAposName() throws IOException {
      int var1 = this._inputPtr;
      int var2 = this._hashSeed;
      int var3 = this._inputEnd;
      int var4 = var1;
      int var5 = var2;
      if (var1 < var3) {
         int[] var6 = _icLatin1;
         int var7 = var6.length;
         var5 = var2;
         var4 = var1;

         while(true) {
            char var8 = this._inputBuffer[var4];
            if (var8 == '\'') {
               var1 = this._inputPtr;
               this._inputPtr = var4 + 1;
               return this._symbols.findSymbol(this._inputBuffer, var1, var4 - var1, var5);
            }

            if (var8 < var7 && var6[var8] != 0) {
               break;
            }

            var2 = var5 * 33 + var8;
            var1 = var4 + 1;
            var4 = var1;
            var5 = var2;
            if (var1 >= var3) {
               var5 = var2;
               var4 = var1;
               break;
            }
         }
      }

      var1 = this._inputPtr;
      this._inputPtr = var4;
      return this._parseName2(var1, var5, 39);
   }

   protected final String _parseName() throws IOException {
      int var1 = this._inputPtr;
      int var2 = this._hashSeed;

      int var5;
      for(int[] var3 = _icLatin1; var1 < this._inputEnd; ++var1) {
         char var4 = this._inputBuffer[var1];
         if (var4 < var3.length && var3[var4] != 0) {
            if (var4 == '"') {
               var5 = this._inputPtr;
               this._inputPtr = var1 + 1;
               return this._symbols.findSymbol(this._inputBuffer, var5, var1 - var5, var2);
            }
            break;
         }

         var2 = var2 * 33 + var4;
      }

      var5 = this._inputPtr;
      this._inputPtr = var1;
      return this._parseName2(var5, var2, 34);
   }

   protected final JsonToken _parseNegNumber() throws IOException {
      int var1 = this._inputPtr;
      int var2 = var1 - 1;
      int var3 = this._inputEnd;
      if (var1 >= var3) {
         return this._parseNumber2(true, var2);
      } else {
         char[] var4 = this._inputBuffer;
         int var5 = var1 + 1;
         char var7 = var4[var1];
         if (var7 <= '9' && var7 >= '0') {
            if (var7 == '0') {
               return this._parseNumber2(true, var2);
            } else {
               int var6;
               for(var1 = 1; var5 < var3; var5 = var6) {
                  var4 = this._inputBuffer;
                  var6 = var5 + 1;
                  char var8 = var4[var5];
                  if (var8 < '0' || var8 > '9') {
                     if (var8 != '.' && var8 != 'e' && var8 != 'E') {
                        --var6;
                        this._inputPtr = var6;
                        if (this._parsingContext.inRoot()) {
                           this._verifyRootSpace(var8);
                        }

                        this._textBuffer.resetWithShared(this._inputBuffer, var2, var6 - var2);
                        return this.resetInt(true, var1);
                     } else {
                        this._inputPtr = var6;
                        return this._parseFloat(var8, var2, var6, true, var1);
                     }
                  }

                  ++var1;
               }

               return this._parseNumber2(true, var2);
            }
         } else {
            this._inputPtr = var5;
            return this._handleInvalidNumberStart(var7, true);
         }
      }
   }

   protected final JsonToken _parsePosNumber(int var1) throws IOException {
      int var2 = this._inputPtr;
      int var3 = var2 - 1;
      int var4 = this._inputEnd;
      if (var1 == 48) {
         return this._parseNumber2(false, var3);
      } else {
         int var6;
         for(var1 = 1; var2 < var4; var2 = var6) {
            char[] var5 = this._inputBuffer;
            var6 = var2 + 1;
            char var7 = var5[var2];
            if (var7 < '0' || var7 > '9') {
               if (var7 != '.' && var7 != 'e' && var7 != 'E') {
                  --var6;
                  this._inputPtr = var6;
                  if (this._parsingContext.inRoot()) {
                     this._verifyRootSpace(var7);
                  }

                  this._textBuffer.resetWithShared(this._inputBuffer, var3, var6 - var3);
                  return this.resetInt(false, var1);
               } else {
                  this._inputPtr = var6;
                  return this._parseFloat(var7, var3, var6, false, var1);
               }
            }

            ++var1;
         }

         this._inputPtr = var3;
         return this._parseNumber2(false, var3);
      }
   }

   protected int _readBinary(Base64Variant var1, OutputStream var2, byte[] var3) throws IOException {
      int var4 = var3.length;
      int var5 = 0;
      int var6 = 0;

      int var8;
      while(true) {
         if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
         }

         label110: {
            char[] var7 = this._inputBuffer;
            var8 = this._inputPtr++;
            char var9 = var7[var8];
            if (var9 > ' ') {
               label109: {
                  var8 = var1.decodeBase64Char(var9);
                  int var10 = var8;
                  if (var8 < 0) {
                     if (var9 == '"') {
                        break;
                     }

                     var8 = this._decodeBase64Escape(var1, var9, 0);
                     var10 = var8;
                     if (var8 < 0) {
                        break label109;
                     }
                  }

                  int var11 = var5;
                  var8 = var6;
                  if (var5 > var4 - 3) {
                     var8 = var6 + var5;
                     var2.write(var3, 0, var5);
                     var11 = 0;
                  }

                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  var9 = var7[var5];
                  var6 = var1.decodeBase64Char(var9);
                  var5 = var6;
                  if (var6 < 0) {
                     var5 = this._decodeBase64Escape(var1, var9, 1);
                  }

                  int var12 = var10 << 6 | var5;
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  var9 = var7[var5];
                  var6 = var1.decodeBase64Char(var9);
                  var10 = var6;
                  if (var6 < 0) {
                     var5 = var6;
                     if (var6 != -2) {
                        if (var9 == '"') {
                           var3[var11] = (byte)((byte)(var12 >> 4));
                           if (var1.usesPadding()) {
                              --this._inputPtr;
                              this._handleBase64MissingPadding(var1);
                           }

                           var5 = var11 + 1;
                           var6 = var8;
                           break;
                        }

                        var5 = this._decodeBase64Escape(var1, var9, 2);
                     }

                     var10 = var5;
                     if (var5 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                           this._loadMoreGuaranteed();
                        }

                        var7 = this._inputBuffer;
                        var5 = this._inputPtr++;
                        var9 = var7[var5];
                        if (!var1.usesPaddingChar(var9) && this._decodeBase64Escape(var1, var9, 3) != -2) {
                           StringBuilder var13 = new StringBuilder();
                           var13.append("expected padding character '");
                           var13.append(var1.getPaddingChar());
                           var13.append("'");
                           throw this.reportInvalidBase64Char(var1, var9, 3, var13.toString());
                        }

                        var3[var11] = (byte)((byte)(var12 >> 4));
                        var5 = var11 + 1;
                        var6 = var8;
                        continue;
                     }
                  }

                  var10 |= var12 << 6;
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  var9 = var7[var5];
                  var5 = var1.decodeBase64Char(var9);
                  if (var5 < 0) {
                     if (var5 != -2) {
                        if (var9 == '"') {
                           var5 = var10 >> 2;
                           var6 = var11 + 1;
                           var3[var11] = (byte)((byte)(var5 >> 8));
                           var11 = var6 + 1;
                           var3[var6] = (byte)((byte)var5);
                           var5 = var11;
                           var6 = var8;
                           if (var1.usesPadding()) {
                              --this._inputPtr;
                              this._handleBase64MissingPadding(var1);
                              var6 = var8;
                              var5 = var11;
                           }
                           break;
                        }

                        var5 = this._decodeBase64Escape(var1, var9, 3);
                     }

                     var6 = var5;
                     if (var5 == -2) {
                        var10 >>= 2;
                        var6 = var11 + 1;
                        var3[var11] = (byte)((byte)(var10 >> 8));
                        var5 = var6 + 1;
                        var3[var6] = (byte)((byte)var10);
                        break label110;
                     }
                  } else {
                     var6 = var5;
                  }

                  var5 = var10 << 6 | var6;
                  var6 = var11 + 1;
                  var3[var11] = (byte)((byte)(var5 >> 16));
                  var11 = var6 + 1;
                  var3[var6] = (byte)((byte)(var5 >> 8));
                  var3[var11] = (byte)((byte)var5);
                  var5 = var11 + 1;
                  break label110;
               }
            }

            var8 = var6;
         }

         var6 = var8;
      }

      this._tokenIncomplete = false;
      var8 = var6;
      if (var5 > 0) {
         var8 = var6 + var5;
         var2.write(var3, 0, var5);
      }

      return var8;
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      this._symbols.release();
      if (this._bufferRecyclable) {
         char[] var1 = this._inputBuffer;
         if (var1 != null) {
            this._inputBuffer = null;
            this._ioContext.releaseTokenBuffer(var1);
         }
      }

   }

   protected void _reportInvalidToken(String var1) throws IOException {
      this._reportInvalidToken(var1, this._validJsonTokenList());
   }

   protected void _reportInvalidToken(String var1, String var2) throws IOException {
      StringBuilder var4 = new StringBuilder(var1);

      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         char var3 = this._inputBuffer[this._inputPtr];
         if (!Character.isJavaIdentifierPart(var3)) {
            break;
         }

         ++this._inputPtr;
         var4.append(var3);
         if (var4.length() >= 256) {
            var4.append("...");
            break;
         }
      }

      this._reportError("Unrecognized token '%s': was expecting %s", var4, var2);
   }

   protected final void _skipCR() throws IOException {
      if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
         ++this._inputPtr;
      }

      ++this._currInputRow;
      this._currInputRowStart = this._inputPtr;
   }

   protected final void _skipString() throws IOException {
      this._tokenIncomplete = false;
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      char[] var3 = this._inputBuffer;

      while(true) {
         while(true) {
            int var4 = var1;
            int var5 = var2;
            if (var1 >= var2) {
               this._inputPtr = var1;
               if (!this._loadMore()) {
                  this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
               }

               var4 = this._inputPtr;
               var5 = this._inputEnd;
            }

            var1 = var4 + 1;
            char var6 = var3[var4];
            if (var6 <= '\\') {
               if (var6 == '\\') {
                  this._inputPtr = var1;
                  this._decodeEscaped();
                  var1 = this._inputPtr;
                  var2 = this._inputEnd;
                  continue;
               }

               if (var6 <= '"') {
                  if (var6 == '"') {
                     this._inputPtr = var1;
                     return;
                  }

                  if (var6 < ' ') {
                     this._inputPtr = var1;
                     this._throwUnquotedSpace(var6, "string value");
                  }
               }
            }

            var2 = var5;
         }
      }
   }

   public void finishToken() throws IOException {
      if (this._tokenIncomplete) {
         this._tokenIncomplete = false;
         this._finishString();
      }

   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
         return this._binaryValue;
      } else {
         StringBuilder var2;
         if (this._currToken != JsonToken.VALUE_STRING) {
            var2 = new StringBuilder();
            var2.append("Current token (");
            var2.append(this._currToken);
            var2.append(") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
            this._reportError(var2.toString());
         }

         if (this._tokenIncomplete) {
            try {
               this._binaryValue = this._decodeBase64(var1);
            } catch (IllegalArgumentException var4) {
               var2 = new StringBuilder();
               var2.append("Failed to decode VALUE_STRING as base64 (");
               var2.append(var1);
               var2.append("): ");
               var2.append(var4.getMessage());
               throw this._constructError(var2.toString());
            }

            this._tokenIncomplete = false;
         } else if (this._binaryValue == null) {
            ByteArrayBuilder var5 = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), var5, var1);
            this._binaryValue = var5.toByteArray();
         }

         return this._binaryValue;
      }
   }

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      Object var3 = this._getSourceReference();
      long var4 = this._currInputProcessed;
      return new JsonLocation(var3, -1L, (long)this._inputPtr + var4, this._currInputRow, var1 - var2 + 1);
   }

   public Object getInputSource() {
      return this._reader;
   }

   @Deprecated
   protected char getNextChar(String var1) throws IOException {
      return this.getNextChar(var1, (JsonToken)null);
   }

   protected char getNextChar(String var1, JsonToken var2) throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(var1, var2);
      }

      char[] var4 = this._inputBuffer;
      int var3 = this._inputPtr++;
      return var4[var3];
   }

   public int getText(Writer var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsToWriter(var1);
      } else if (var2 == JsonToken.FIELD_NAME) {
         String var4 = this._parsingContext.getCurrentName();
         var1.write(var4);
         return var4.length();
      } else if (var2 != null) {
         if (var2.isNumeric()) {
            return this._textBuffer.contentsToWriter(var1);
         } else {
            char[] var3 = var2.asCharArray();
            var1.write(var3);
            return var3.length;
         }
      } else {
         return 0;
      }
   }

   public final String getText() throws IOException {
      JsonToken var1 = this._currToken;
      if (var1 == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return this._getText2(var1);
      }
   }

   public final char[] getTextCharacters() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 != 5) {
            if (var1 != 6) {
               if (var1 != 7 && var1 != 8) {
                  return this._currToken.asCharArray();
               }
            } else if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }

            return this._textBuffer.getTextBuffer();
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

   public final int getTextLength() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 != 5) {
            if (var1 != 6) {
               if (var1 != 7 && var1 != 8) {
                  return this._currToken.asCharArray().length;
               }
            } else if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }

            return this._textBuffer.size();
         } else {
            return this._parsingContext.getCurrentName().length();
         }
      } else {
         return 0;
      }
   }

   public final int getTextOffset() throws IOException {
      if (this._currToken != null) {
         int var1 = this._currToken.id();
         if (var1 == 6) {
            if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }

            return this._textBuffer.getTextOffset();
         }

         if (var1 == 7 || var1 == 8) {
            return this._textBuffer.getTextOffset();
         }
      }

      return 0;
   }

   public JsonLocation getTokenLocation() {
      if (this._currToken == JsonToken.FIELD_NAME) {
         long var1 = this._currInputProcessed;
         long var3 = this._nameStartOffset;
         return new JsonLocation(this._getSourceReference(), -1L, var1 + (var3 - 1L), this._nameStartRow, this._nameStartCol);
      } else {
         return new JsonLocation(this._getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
      }
   }

   public final String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString((String)null);
      }
   }

   public final String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.contentsAsString();
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString(var1);
      }
   }

   public final Boolean nextBooleanValue() throws IOException {
      JsonToken var1;
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         var1 = this._nextToken;
         this._nextToken = null;
         this._currToken = var1;
         if (var1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
         } else if (var1 == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
         } else {
            if (var1 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var1 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         var1 = this.nextToken();
         if (var1 != null) {
            int var2 = var1.id();
            if (var2 == 9) {
               return Boolean.TRUE;
            }

            if (var2 == 10) {
               return Boolean.FALSE;
            }
         }

         return null;
      }
   }

   public String nextFieldName() throws IOException {
      this._numTypesValid = 0;
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nextAfterName();
         return null;
      } else {
         if (this._tokenIncomplete) {
            this._skipString();
         }

         int var1 = this._skipWSOrEnd();
         if (var1 < 0) {
            this.close();
            this._currToken = null;
            return null;
         } else {
            this._binaryValue = null;
            if (var1 != 93 && var1 != 125) {
               int var2 = var1;
               if (this._parsingContext.expectComma()) {
                  var1 = this._skipComma(var1);
                  var2 = var1;
                  if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     label81: {
                        if (var1 != 93) {
                           var2 = var1;
                           if (var1 != 125) {
                              break label81;
                           }
                        }

                        this._closeScope(var1);
                        return null;
                     }
                  }
               }

               if (!this._parsingContext.inObject()) {
                  this._updateLocation();
                  this._nextTokenNotInObject(var2);
                  return null;
               } else {
                  this._updateNameLocation();
                  String var3;
                  if (var2 == 34) {
                     var3 = this._parseName();
                  } else {
                     var3 = this._handleOddName(var2);
                  }

                  this._parsingContext.setCurrentName(var3);
                  this._currToken = JsonToken.FIELD_NAME;
                  var2 = this._skipColon();
                  this._updateLocation();
                  if (var2 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return var3;
                  } else {
                     JsonToken var4;
                     if (var2 != 45) {
                        if (var2 != 91) {
                           if (var2 != 102) {
                              if (var2 != 110) {
                                 if (var2 != 116) {
                                    if (var2 != 123) {
                                       switch(var2) {
                                       case 48:
                                       case 49:
                                       case 50:
                                       case 51:
                                       case 52:
                                       case 53:
                                       case 54:
                                       case 55:
                                       case 56:
                                       case 57:
                                          var4 = this._parsePosNumber(var2);
                                          break;
                                       default:
                                          var4 = this._handleOddValue(var2);
                                       }
                                    } else {
                                       var4 = JsonToken.START_OBJECT;
                                    }
                                 } else {
                                    this._matchTrue();
                                    var4 = JsonToken.VALUE_TRUE;
                                 }
                              } else {
                                 this._matchNull();
                                 var4 = JsonToken.VALUE_NULL;
                              }
                           } else {
                              this._matchFalse();
                              var4 = JsonToken.VALUE_FALSE;
                           }
                        } else {
                           var4 = JsonToken.START_ARRAY;
                        }
                     } else {
                        var4 = this._parseNegNumber();
                     }

                     this._nextToken = var4;
                     return var3;
                  }
               }
            } else {
               this._closeScope(var1);
               return null;
            }
         }
      }
   }

   public boolean nextFieldName(SerializableString var1) throws IOException {
      int var2 = 0;
      this._numTypesValid = 0;
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nextAfterName();
         return false;
      } else {
         if (this._tokenIncomplete) {
            this._skipString();
         }

         int var3 = this._skipWSOrEnd();
         if (var3 < 0) {
            this.close();
            this._currToken = null;
            return false;
         } else {
            this._binaryValue = null;
            if (var3 != 93 && var3 != 125) {
               int var4 = var3;
               if (this._parsingContext.expectComma()) {
                  var3 = this._skipComma(var3);
                  var4 = var3;
                  if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     label70: {
                        if (var3 != 93) {
                           var4 = var3;
                           if (var3 != 125) {
                              break label70;
                           }
                        }

                        this._closeScope(var3);
                        return false;
                     }
                  }
               }

               if (!this._parsingContext.inObject()) {
                  this._updateLocation();
                  this._nextTokenNotInObject(var4);
                  return false;
               } else {
                  this._updateNameLocation();
                  if (var4 == 34) {
                     char[] var5 = var1.asQuotedChars();
                     var3 = var5.length;
                     if (this._inputPtr + var3 + 4 < this._inputEnd) {
                        int var6 = this._inputPtr + var3;
                        if (this._inputBuffer[var6] == '"') {
                           var3 = this._inputPtr;

                           while(true) {
                              if (var3 == var6) {
                                 this._parsingContext.setCurrentName(var1.getValue());
                                 this._isNextTokenNameYes(this._skipColonFast(var3 + 1));
                                 return true;
                              }

                              if (var5[var2] != this._inputBuffer[var3]) {
                                 break;
                              }

                              ++var2;
                              ++var3;
                           }
                        }
                     }
                  }

                  return this._isNextTokenNameMaybe(var4, var1.getValue());
               }
            } else {
               this._closeScope(var3);
               return false;
            }
         }
      }
   }

   public final int nextIntValue(int var1) throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var2 = this._nextToken;
         this._nextToken = null;
         this._currToken = var2;
         if (var2 == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
         } else {
            if (var2 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var2 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            var1 = this.getIntValue();
         }

         return var1;
      }
   }

   public final long nextLongValue(long var1) throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         this._nameCopied = false;
         JsonToken var3 = this._nextToken;
         this._nextToken = null;
         this._currToken = var3;
         if (var3 == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
         } else {
            if (var3 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var3 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return var1;
         }
      } else {
         if (this.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            var1 = this.getLongValue();
         }

         return var1;
      }
   }

   public final String nextTextValue() throws IOException {
      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.FIELD_NAME;
      String var3 = null;
      if (var1 == var2) {
         this._nameCopied = false;
         JsonToken var4 = this._nextToken;
         this._nextToken = null;
         this._currToken = var4;
         if (var4 == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
               this._tokenIncomplete = false;
               this._finishString();
            }

            return this._textBuffer.contentsAsString();
         } else {
            if (var4 == JsonToken.START_ARRAY) {
               this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (var4 == JsonToken.START_OBJECT) {
               this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }

            return null;
         }
      } else {
         if (this.nextToken() == JsonToken.VALUE_STRING) {
            var3 = this.getText();
         }

         return var3;
      }
   }

   public final JsonToken nextToken() throws IOException {
      if (this._currToken == JsonToken.FIELD_NAME) {
         return this._nextAfterName();
      } else {
         this._numTypesValid = 0;
         if (this._tokenIncomplete) {
            this._skipString();
         }

         int var1 = this._skipWSOrEnd();
         if (var1 < 0) {
            this.close();
            this._currToken = null;
            return null;
         } else {
            this._binaryValue = null;
            if (var1 != 93 && var1 != 125) {
               int var2 = var1;
               if (this._parsingContext.expectComma()) {
                  var1 = this._skipComma(var1);
                  var2 = var1;
                  if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     label100: {
                        if (var1 != 93) {
                           var2 = var1;
                           if (var1 != 125) {
                              break label100;
                           }
                        }

                        this._closeScope(var1);
                        return this._currToken;
                     }
                  }
               }

               boolean var3 = this._parsingContext.inObject();
               var1 = var2;
               if (var3) {
                  this._updateNameLocation();
                  String var4;
                  if (var2 == 34) {
                     var4 = this._parseName();
                  } else {
                     var4 = this._handleOddName(var2);
                  }

                  this._parsingContext.setCurrentName(var4);
                  this._currToken = JsonToken.FIELD_NAME;
                  var1 = this._skipColon();
               }

               this._updateLocation();
               JsonToken var5;
               if (var1 != 34) {
                  if (var1 != 45) {
                     if (var1 != 91) {
                        if (var1 != 102) {
                           if (var1 != 110) {
                              label68: {
                                 if (var1 != 116) {
                                    if (var1 == 123) {
                                       if (!var3) {
                                          this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                                       }

                                       var5 = JsonToken.START_OBJECT;
                                       break label68;
                                    }

                                    if (var1 != 125) {
                                       switch(var1) {
                                       case 48:
                                       case 49:
                                       case 50:
                                       case 51:
                                       case 52:
                                       case 53:
                                       case 54:
                                       case 55:
                                       case 56:
                                       case 57:
                                          var5 = this._parsePosNumber(var1);
                                          break label68;
                                       default:
                                          var5 = this._handleOddValue(var1);
                                          break label68;
                                       }
                                    }

                                    this._reportUnexpectedChar(var1, "expected a value");
                                 }

                                 this._matchTrue();
                                 var5 = JsonToken.VALUE_TRUE;
                              }
                           } else {
                              this._matchNull();
                              var5 = JsonToken.VALUE_NULL;
                           }
                        } else {
                           this._matchFalse();
                           var5 = JsonToken.VALUE_FALSE;
                        }
                     } else {
                        if (!var3) {
                           this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                        }

                        var5 = JsonToken.START_ARRAY;
                     }
                  } else {
                     var5 = this._parseNegNumber();
                  }
               } else {
                  this._tokenIncomplete = true;
                  var5 = JsonToken.VALUE_STRING;
               }

               if (var3) {
                  this._nextToken = var5;
                  return this._currToken;
               } else {
                  this._currToken = var5;
                  return var5;
               }
            } else {
               this._closeScope(var1);
               return this._currToken;
            }
         }
      }
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      if (this._tokenIncomplete && this._currToken == JsonToken.VALUE_STRING) {
         byte[] var3 = this._ioContext.allocBase64Buffer();

         int var4;
         try {
            var4 = this._readBinary(var1, var2, var3);
         } finally {
            this._ioContext.releaseBase64Buffer(var3);
         }

         return var4;
      } else {
         byte[] var7 = this.getBinaryValue(var1);
         var2.write(var7);
         return var7.length;
      }
   }

   public int releaseBuffered(Writer var1) throws IOException {
      int var2 = this._inputEnd - this._inputPtr;
      if (var2 < 1) {
         return 0;
      } else {
         int var3 = this._inputPtr;
         var1.write(this._inputBuffer, var3, var2);
         return var2;
      }
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }
}
