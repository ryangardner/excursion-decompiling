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
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class UTF8StreamJsonParser extends ParserBase {
   static final byte BYTE_LF = 10;
   private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
   private static final int FEAT_MASK_ALLOW_MISSING;
   private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
   private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
   private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
   private static final int FEAT_MASK_LEADING_ZEROS;
   private static final int FEAT_MASK_NON_NUM_NUMBERS;
   private static final int FEAT_MASK_TRAILING_COMMA;
   protected static final int[] _icLatin1;
   private static final int[] _icUTF8;
   protected boolean _bufferRecyclable;
   protected byte[] _inputBuffer;
   protected InputStream _inputStream;
   protected int _nameStartCol;
   protected int _nameStartOffset;
   protected int _nameStartRow;
   protected ObjectCodec _objectCodec;
   private int _quad1;
   protected int[] _quadBuffer;
   protected final ByteQuadsCanonicalizer _symbols;
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
      _icUTF8 = CharTypes.getInputCodeUtf8();
      _icLatin1 = CharTypes.getInputCodeLatin1();
   }

   public UTF8StreamJsonParser(IOContext var1, int var2, InputStream var3, ObjectCodec var4, ByteQuadsCanonicalizer var5, byte[] var6, int var7, int var8, int var9, boolean var10) {
      super(var1, var2);
      this._quadBuffer = new int[16];
      this._inputStream = var3;
      this._objectCodec = var4;
      this._symbols = var5;
      this._inputBuffer = var6;
      this._inputPtr = var7;
      this._inputEnd = var8;
      this._currInputRowStart = var7 - var9;
      this._currInputProcessed = (long)(-var7 + var9);
      this._bufferRecyclable = var10;
   }

   @Deprecated
   public UTF8StreamJsonParser(IOContext var1, int var2, InputStream var3, ObjectCodec var4, ByteQuadsCanonicalizer var5, byte[] var6, int var7, int var8, boolean var9) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, 0, var9);
   }

   private final void _checkMatchEnd(String var1, int var2, int var3) throws IOException {
      if (Character.isJavaIdentifierPart((char)this._decodeCharForError(var3))) {
         this._reportInvalidToken(var1.substring(0, var2));
      }

   }

   private final void _closeArrayScope() throws JsonParseException {
      this._updateLocation();
      if (!this._parsingContext.inArray()) {
         this._reportMismatchedEndMarker(93, '}');
      }

      this._parsingContext = this._parsingContext.clearAndGetParent();
   }

   private final void _closeObjectScope() throws JsonParseException {
      this._updateLocation();
      if (!this._parsingContext.inObject()) {
         this._reportMismatchedEndMarker(125, ']');
      }

      this._parsingContext = this._parsingContext.clearAndGetParent();
   }

   private final JsonToken _closeScope(int var1) throws JsonParseException {
      JsonToken var2;
      if (var1 == 125) {
         this._closeObjectScope();
         var2 = JsonToken.END_OBJECT;
         this._currToken = var2;
         return var2;
      } else {
         this._closeArrayScope();
         var2 = JsonToken.END_ARRAY;
         this._currToken = var2;
         return var2;
      }
   }

   private final int _decodeUtf8_2(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var3 = this._inputPtr++;
      byte var4 = var2[var3];
      if ((var4 & 192) != 128) {
         this._reportInvalidOther(var4 & 255, this._inputPtr);
      }

      return (var1 & 31) << 6 | var4 & 63;
   }

   private final int _decodeUtf8_3(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var3 = this._inputPtr++;
      byte var5 = var2[var3];
      if ((var5 & 192) != 128) {
         this._reportInvalidOther(var5 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      int var4 = this._inputPtr++;
      byte var6 = var2[var4];
      if ((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      return ((var1 & 15) << 6 | var5 & 63) << 6 | var6 & 63;
   }

   private final int _decodeUtf8_3fast(int var1) throws IOException {
      byte[] var2 = this._inputBuffer;
      int var3 = this._inputPtr++;
      byte var5 = var2[var3];
      if ((var5 & 192) != 128) {
         this._reportInvalidOther(var5 & 255, this._inputPtr);
      }

      var2 = this._inputBuffer;
      int var4 = this._inputPtr++;
      byte var6 = var2[var4];
      if ((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      return ((var1 & 15) << 6 | var5 & 63) << 6 | var6 & 63;
   }

   private final int _decodeUtf8_4(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var3 = this._inputPtr++;
      byte var6 = var2[var3];
      if ((var6 & 192) != 128) {
         this._reportInvalidOther(var6 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      int var4 = this._inputPtr++;
      byte var7 = var2[var4];
      if ((var7 & 192) != 128) {
         this._reportInvalidOther(var7 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      int var5 = this._inputPtr++;
      byte var8 = var2[var5];
      if ((var8 & 192) != 128) {
         this._reportInvalidOther(var8 & 255, this._inputPtr);
      }

      return ((((var1 & 7) << 6 | var6 & 63) << 6 | var7 & 63) << 6 | var8 & 63) - 65536;
   }

   private final void _finishString2(char[] var1, int var2) throws IOException {
      int[] var3 = _icUTF8;
      byte[] var4 = this._inputBuffer;

      while(true) {
         label59:
         while(true) {
            int var5 = this._inputPtr;
            int var6 = var5;
            if (var5 >= this._inputEnd) {
               this._loadMoreGuaranteed();
               var6 = this._inputPtr;
            }

            int var7 = var1.length;
            byte var8 = 0;
            var5 = var2;
            if (var2 >= var7) {
               var1 = this._textBuffer.finishCurrentSegment();
               var5 = 0;
            }

            for(int var9 = Math.min(this._inputEnd, var1.length - var5 + var6); var6 < var9; ++var5) {
               var7 = var6 + 1;
               var2 = var4[var6] & 255;
               if (var3[var2] != 0) {
                  this._inputPtr = var7;
                  if (var2 == 34) {
                     this._textBuffer.setCurrentLength(var5);
                     return;
                  }

                  var6 = var3[var2];
                  if (var6 != 1) {
                     if (var6 != 2) {
                        if (var6 != 3) {
                           if (var6 != 4) {
                              if (var2 < 32) {
                                 this._throwUnquotedSpace(var2, "string value");
                              } else {
                                 this._reportInvalidChar(var2);
                              }
                           } else {
                              var6 = this._decodeUtf8_4(var2);
                              var2 = var5 + 1;
                              var1[var5] = (char)((char)('\ud800' | var6 >> 10));
                              if (var2 >= var1.length) {
                                 var1 = this._textBuffer.finishCurrentSegment();
                                 var5 = 0;
                              } else {
                                 var5 = var2;
                              }

                              var2 = var6 & 1023 | '\udc00';
                           }
                        } else if (this._inputEnd - this._inputPtr >= 2) {
                           var2 = this._decodeUtf8_3fast(var2);
                        } else {
                           var2 = this._decodeUtf8_3(var2);
                        }
                     } else {
                        var2 = this._decodeUtf8_2(var2);
                     }
                  } else {
                     var2 = this._decodeEscaped();
                  }

                  if (var5 >= var1.length) {
                     var1 = this._textBuffer.finishCurrentSegment();
                     var5 = var8;
                  }

                  var6 = var5 + 1;
                  var1[var5] = (char)((char)var2);
                  var2 = var6;
                  continue label59;
               }

               var1[var5] = (char)((char)var2);
               var6 = var7;
            }

            this._inputPtr = var6;
            var2 = var5;
         }
      }
   }

   private final boolean _isNextTokenNameMaybe(int var1, SerializableString var2) throws IOException {
      String var3 = this._parseName(var1);
      this._parsingContext.setCurrentName(var3);
      boolean var4 = var3.equals(var2.getValue());
      this._currToken = JsonToken.FIELD_NAME;
      var1 = this._skipColon();
      this._updateLocation();
      if (var1 == 34) {
         this._tokenIncomplete = true;
         this._nextToken = JsonToken.VALUE_STRING;
         return var4;
      } else {
         JsonToken var5;
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
                              var5 = this._parsePosNumber(var1);
                              break;
                           default:
                              var5 = this._handleUnexpectedValue(var1);
                           }
                        } else {
                           var5 = JsonToken.START_OBJECT;
                        }
                     } else {
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
               var5 = JsonToken.START_ARRAY;
            }
         } else {
            var5 = this._parseNegNumber();
         }

         this._nextToken = var5;
         return var4;
      }
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
                              this._nextToken = this._handleUnexpectedValue(var1);
                           }
                        } else {
                           this._nextToken = JsonToken.START_OBJECT;
                        }
                     } else {
                        this._matchTrue();
                        this._nextToken = JsonToken.VALUE_TRUE;
                     }
                  } else {
                     this._matchNull();
                     this._nextToken = JsonToken.VALUE_NULL;
                  }
               } else {
                  this._matchFalse();
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
         var2 = this._inputBuffer[this._inputPtr] & 255;
         if (var2 >= 48 && var2 != 93 && var2 != 125) {
            this._checkMatchEnd(var1, var4, var2);
         }

      }
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
      } else if (var1 != 45) {
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
                           var2 = this._parsePosNumber(var1);
                           this._currToken = var2;
                           return var2;
                        default:
                           var2 = this._handleUnexpectedValue(var1);
                           this._currToken = var2;
                           return var2;
                        }
                     } else {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                        var2 = JsonToken.START_OBJECT;
                        this._currToken = var2;
                        return var2;
                     }
                  } else {
                     this._matchTrue();
                     var2 = JsonToken.VALUE_TRUE;
                     this._currToken = var2;
                     return var2;
                  }
               } else {
                  this._matchNull();
                  var2 = JsonToken.VALUE_NULL;
                  this._currToken = var2;
                  return var2;
               }
            } else {
               this._matchFalse();
               var2 = JsonToken.VALUE_FALSE;
               this._currToken = var2;
               return var2;
            }
         } else {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            var2 = JsonToken.START_ARRAY;
            this._currToken = var2;
            return var2;
         }
      } else {
         var2 = this._parseNegNumber();
         this._currToken = var2;
         return var2;
      }
   }

   private static final int _padLastQuad(int var0, int var1) {
      if (var1 != 4) {
         var0 |= -1 << (var1 << 3);
      }

      return var0;
   }

   private final JsonToken _parseFloat(char[] var1, int var2, int var3, boolean var4, int var5) throws IOException {
      int var6 = 0;
      char[] var7;
      int var8;
      int var9;
      int var10;
      int var12;
      int var13;
      boolean var15;
      byte[] var16;
      boolean var17;
      if (var3 == 46) {
         var7 = var1;
         var8 = var2;
         if (var2 >= var1.length) {
            var7 = this._textBuffer.finishCurrentSegment();
            var8 = 0;
         }

         var7[var8] = (char)((char)var3);
         var2 = var8 + 1;
         var9 = 0;
         var10 = var3;
         var3 = var2;
         var1 = var7;

         boolean var11;
         while(true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
               var11 = true;
               break;
            }

            var16 = this._inputBuffer;
            var2 = this._inputPtr++;
            var10 = var16[var2] & 255;
            if (var10 < 48 || var10 > 57) {
               var11 = false;
               break;
            }

            ++var9;
            var7 = var1;
            var2 = var3;
            if (var3 >= var1.length) {
               var7 = this._textBuffer.finishCurrentSegment();
               var2 = 0;
            }

            var7[var2] = (char)((char)var10);
            var3 = var2 + 1;
            var1 = var7;
         }

         var12 = var9;
         var15 = var11;
         var7 = var1;
         var8 = var3;
         var13 = var10;
         if (var9 == 0) {
            this.reportUnexpectedNumberChar(var10, "Decimal point not followed by a digit");
            var12 = var9;
            var15 = var11;
            var7 = var1;
            var8 = var3;
            var13 = var10;
         }
      } else {
         var12 = 0;
         var17 = false;
         var13 = var3;
         var8 = var2;
         var7 = var1;
         var15 = var17;
      }

      int var18;
      label111: {
         if (var13 != 101) {
            var17 = var15;
            var10 = var8;
            var18 = var13;
            if (var13 != 69) {
               break label111;
            }
         }

         var1 = var7;
         var3 = var8;
         if (var8 >= var7.length) {
            var1 = this._textBuffer.finishCurrentSegment();
            var3 = 0;
         }

         var9 = var3 + 1;
         var1[var3] = (char)((char)var13);
         if (this._inputPtr >= this._inputEnd) {
            this._loadMoreGuaranteed();
         }

         label112: {
            var16 = this._inputBuffer;
            var3 = this._inputPtr++;
            var13 = var16[var3] & 255;
            if (var13 != 45) {
               var3 = var9;
               var7 = var1;
               var8 = var13;
               if (var13 != 43) {
                  break label112;
               }
            }

            var3 = var9;
            var7 = var1;
            if (var9 >= var1.length) {
               var7 = this._textBuffer.finishCurrentSegment();
               var3 = 0;
            }

            var7[var3] = (char)((char)var13);
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            byte[] var14 = this._inputBuffer;
            var8 = this._inputPtr++;
            var8 = var14[var8] & 255;
            ++var3;
         }

         boolean var19;
         label77: {
            for(var13 = 0; var8 >= 48 && var8 <= 57; var7 = var1) {
               ++var13;
               var9 = var3;
               var1 = var7;
               if (var3 >= var7.length) {
                  var1 = this._textBuffer.finishCurrentSegment();
                  var9 = 0;
               }

               var3 = var9 + 1;
               var1[var9] = (char)((char)var8);
               if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                  var2 = var13;
                  var19 = true;
                  break label77;
               }

               var16 = this._inputBuffer;
               var8 = this._inputPtr++;
               var8 = var16[var8] & 255;
            }

            var19 = var15;
            var2 = var13;
         }

         var6 = var2;
         var17 = var19;
         var10 = var3;
         var18 = var8;
         if (var2 == 0) {
            this.reportUnexpectedNumberChar(var8, "Exponent indicator not followed by a digit");
            var18 = var8;
            var10 = var3;
            var17 = var19;
            var6 = var2;
         }
      }

      if (!var17) {
         --this._inputPtr;
         if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(var18);
         }
      }

      this._textBuffer.setCurrentLength(var10);
      return this.resetFloat(var4, var5, var12, var6);
   }

   private final JsonToken _parseNumber2(char[] var1, int var2, boolean var3, int var4) throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var5 = this._inputBuffer;
         int var6 = this._inputPtr++;
         int var7 = var5[var6] & 255;
         if (var7 > 57 || var7 < 48) {
            if (var7 != 46 && var7 != 101 && var7 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var2);
               if (this._parsingContext.inRoot()) {
                  this._verifyRootSpace(this._inputBuffer[this._inputPtr] & 255);
               }

               return this.resetInt(var3, var4);
            }

            return this._parseFloat(var1, var2, var7, var3, var4);
         }

         char[] var8 = var1;
         var6 = var2;
         if (var2 >= var1.length) {
            var8 = this._textBuffer.finishCurrentSegment();
            var6 = 0;
         }

         var8[var6] = (char)((char)var7);
         ++var4;
         var2 = var6 + 1;
         var1 = var8;
      }

      this._textBuffer.setCurrentLength(var2);
      return this.resetInt(var3, var4);
   }

   private final void _skipCComment() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         int var4 = var2[var3] & 255;
         var3 = var1[var4];
         if (var3 != 0) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 != 10) {
                        if (var3 != 13) {
                           if (var3 != 42) {
                              this._reportInvalidChar(var4);
                           } else {
                              if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                 break;
                              }

                              if (this._inputBuffer[this._inputPtr] == 47) {
                                 ++this._inputPtr;
                                 return;
                              }
                           }
                        } else {
                           this._skipCR();
                        }
                     } else {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                     }
                  } else {
                     this._skipUtf8_4(var4);
                  }
               } else {
                  this._skipUtf8_3();
               }
            } else {
               this._skipUtf8_2();
            }
         }
      }

      this._reportInvalidEOF(" in a comment", (JsonToken)null);
   }

   private final int _skipColon() throws IOException {
      if (this._inputPtr + 4 >= this._inputEnd) {
         return this._skipColon2(false);
      } else {
         byte var1 = this._inputBuffer[this._inputPtr];
         byte[] var2;
         byte var3;
         int var4;
         if (var1 == 58) {
            var2 = this._inputBuffer;
            var4 = this._inputPtr + 1;
            this._inputPtr = var4;
            var3 = var2[var4];
            if (var3 > 32) {
               if (var3 != 47 && var3 != 35) {
                  ++this._inputPtr;
                  return var3;
               } else {
                  return this._skipColon2(true);
               }
            } else {
               if (var3 == 32 || var3 == 9) {
                  var2 = this._inputBuffer;
                  var4 = this._inputPtr + 1;
                  this._inputPtr = var4;
                  var3 = var2[var4];
                  if (var3 > 32) {
                     if (var3 != 47 && var3 != 35) {
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
               if (var1 != 32) {
                  var3 = var1;
                  if (var1 != 9) {
                     break label78;
                  }
               }

               var2 = this._inputBuffer;
               var4 = this._inputPtr + 1;
               this._inputPtr = var4;
               var3 = var2[var4];
            }

            if (var3 == 58) {
               var2 = this._inputBuffer;
               var4 = this._inputPtr + 1;
               this._inputPtr = var4;
               var3 = var2[var4];
               if (var3 > 32) {
                  if (var3 != 47 && var3 != 35) {
                     ++this._inputPtr;
                     return var3;
                  } else {
                     return this._skipColon2(true);
                  }
               } else {
                  if (var3 == 32 || var3 == 9) {
                     var2 = this._inputBuffer;
                     var4 = this._inputPtr + 1;
                     this._inputPtr = var4;
                     var3 = var2[var4];
                     if (var3 > 32) {
                        if (var3 != 47 && var3 != 35) {
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
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         var3 = var2[var3] & 255;
         if (var3 > 32) {
            if (var3 == 47) {
               this._skipComment();
            } else if (var3 != 35 || !this._skipYAMLComment()) {
               if (var1) {
                  return var3;
               }

               if (var3 != 58) {
                  this._reportUnexpectedChar(var3, "was expecting a colon to separate field name and value");
               }

               var1 = true;
            }
         } else if (var3 != 32) {
            if (var3 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 == 13) {
               this._skipCR();
            } else if (var3 != 9) {
               this._throwInvalidSpace(var3);
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
      byte[] var2 = this._inputBuffer;
      int var3 = var1 + 1;
      byte var4 = var2[var1];
      byte var5;
      byte var6;
      int var7;
      if (var4 == 58) {
         var7 = var3 + 1;
         var6 = var2[var3];
         if (var6 > 32) {
            var1 = var7;
            if (var6 != 47) {
               var1 = var7;
               if (var6 != 35) {
                  this._inputPtr = var7;
                  return var6;
               }
            }
         } else {
            label46: {
               if (var6 != 32) {
                  var1 = var7;
                  if (var6 != 9) {
                     break label46;
                  }
               }

               var2 = this._inputBuffer;
               var1 = var7 + 1;
               var5 = var2[var7];
               if (var5 > 32 && var5 != 47 && var5 != 35) {
                  this._inputPtr = var1;
                  return var5;
               }
            }
         }

         this._inputPtr = var1 - 1;
         return this._skipColon2(true);
      } else {
         label60: {
            if (var4 != 32) {
               var1 = var3;
               var5 = var4;
               if (var4 != 9) {
                  break label60;
               }
            }

            var5 = this._inputBuffer[var3];
            var1 = var3 + 1;
         }

         if (var5 != 58) {
            this._inputPtr = var1 - 1;
            return this._skipColon2(false);
         } else {
            var2 = this._inputBuffer;
            var7 = var1 + 1;
            var6 = var2[var1];
            if (var6 > 32) {
               var1 = var7;
               if (var6 != 47) {
                  var1 = var7;
                  if (var6 != 35) {
                     this._inputPtr = var7;
                     return var6;
                  }
               }
            } else {
               label53: {
                  if (var6 != 32) {
                     var1 = var7;
                     if (var6 != 9) {
                        break label53;
                     }
                  }

                  var2 = this._inputBuffer;
                  var1 = var7 + 1;
                  var5 = var2[var7];
                  if (var5 > 32 && var5 != 47 && var5 != 35) {
                     this._inputPtr = var1;
                     return var5;
                  }
               }
            }

            this._inputPtr = var1 - 1;
            return this._skipColon2(true);
         }
      }
   }

   private final void _skipComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(" in a comment", (JsonToken)null);
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      var2 = var1[var2] & 255;
      if (var2 == 47) {
         this._skipLine();
      } else if (var2 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var2, "was expecting either '*' or '/' for a comment");
      }

   }

   private final void _skipLine() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         int var4 = var2[var3] & 255;
         var3 = var1[var4];
         if (var3 != 0) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                        return;
                     }

                     if (var3 == 13) {
                        this._skipCR();
                        return;
                     }

                     if (var3 != 42 && var3 < 0) {
                        this._reportInvalidChar(var4);
                     }
                  } else {
                     this._skipUtf8_4(var4);
                  }
               } else {
                  this._skipUtf8_3();
               }
            } else {
               this._skipUtf8_2();
            }
         }
      }

   }

   private final void _skipUtf8_2() throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      byte var3 = var1[var2];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private final void _skipUtf8_3() throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      byte var3 = var1[var2];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var1 = this._inputBuffer;
      var2 = this._inputPtr++;
      var3 = var1[var2];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private final void _skipUtf8_4(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      var1 = this._inputPtr++;
      byte var3 = var2[var1];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      var1 = this._inputPtr++;
      var3 = var2[var1];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      var2 = this._inputBuffer;
      var1 = this._inputPtr++;
      var3 = var2[var1];
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

   }

   private final int _skipWS() throws IOException {
      while(this._inputPtr < this._inputEnd) {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         if (var2 > 32) {
            if (var2 != 47 && var2 != 35) {
               return var2;
            }

            --this._inputPtr;
            return this._skipWS2();
         }

         if (var2 != 32) {
            if (var2 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var2 == 13) {
               this._skipCR();
            } else if (var2 != 9) {
               this._throwInvalidSpace(var2);
            }
         }
      }

      return this._skipWS2();
   }

   private final int _skipWS2() throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         if (var2 > 32) {
            if (var2 == 47) {
               this._skipComment();
            } else if (var2 != 35 || !this._skipYAMLComment()) {
               return var2;
            }
         } else if (var2 != 32) {
            if (var2 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var2 == 13) {
               this._skipCR();
            } else if (var2 != 9) {
               this._throwInvalidSpace(var2);
            }
         }
      }

      StringBuilder var3 = new StringBuilder();
      var3.append("Unexpected end-of-input within/between ");
      var3.append(this._parsingContext.typeDesc());
      var3.append(" entries");
      throw this._constructError(var3.toString());
   }

   private final int _skipWSOrEnd() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         return this._eofAsNextChar();
      } else {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         if (var2 > 32) {
            if (var2 != 47 && var2 != 35) {
               return var2;
            } else {
               --this._inputPtr;
               return this._skipWSOrEnd2();
            }
         } else {
            if (var2 != 32) {
               if (var2 == 10) {
                  ++this._currInputRow;
                  this._currInputRowStart = this._inputPtr;
               } else if (var2 == 13) {
                  this._skipCR();
               } else if (var2 != 9) {
                  this._throwInvalidSpace(var2);
               }
            }

            while(this._inputPtr < this._inputEnd) {
               var1 = this._inputBuffer;
               var2 = this._inputPtr++;
               var2 = var1[var2] & 255;
               if (var2 > 32) {
                  if (var2 != 47 && var2 != 35) {
                     return var2;
                  }

                  --this._inputPtr;
                  return this._skipWSOrEnd2();
               }

               if (var2 != 32) {
                  if (var2 == 10) {
                     ++this._currInputRow;
                     this._currInputRowStart = this._inputPtr;
                  } else if (var2 == 13) {
                     this._skipCR();
                  } else if (var2 != 9) {
                     this._throwInvalidSpace(var2);
                  }
               }
            }

            return this._skipWSOrEnd2();
         }
      }
   }

   private final int _skipWSOrEnd2() throws IOException {
      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         if (var2 > 32) {
            if (var2 == 47) {
               this._skipComment();
            } else if (var2 != 35 || !this._skipYAMLComment()) {
               return var2;
            }
         } else if (var2 != 32) {
            if (var2 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var2 == 13) {
               this._skipCR();
            } else if (var2 != 9) {
               this._throwInvalidSpace(var2);
            }
         }
      }

      return this._eofAsNextChar();
   }

   private final boolean _skipYAMLComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
         return false;
      } else {
         this._skipLine();
         return true;
      }
   }

   private final void _updateLocation() {
      this._tokenInputRow = this._currInputRow;
      int var1 = this._inputPtr;
      this._tokenInputTotal = this._currInputProcessed + (long)var1;
      this._tokenInputCol = var1 - this._currInputRowStart;
   }

   private final void _updateNameLocation() {
      this._nameStartRow = this._currInputRow;
      int var1 = this._inputPtr;
      this._nameStartOffset = var1;
      this._nameStartCol = var1 - this._currInputRowStart;
   }

   private final int _verifyNoLeadingZeroes() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         return 48;
      } else {
         int var1 = this._inputBuffer[this._inputPtr] & 255;
         if (var1 >= 48 && var1 <= 57) {
            if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }

            ++this._inputPtr;
            int var2 = var1;
            if (var1 == 48) {
               var2 = var1;

               while(this._inputPtr < this._inputEnd || this._loadMore()) {
                  var1 = this._inputBuffer[this._inputPtr] & 255;
                  if (var1 < 48 || var1 > 57) {
                     return 48;
                  }

                  ++this._inputPtr;
                  var2 = var1;
                  if (var1 != 48) {
                     var2 = var1;
                     break;
                  }
               }
            }

            return var2;
         } else {
            return 48;
         }
      }
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

   private final String addName(int[] var1, int var2, int var3) throws JsonParseException {
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

            var13 = var1[var10 >> 2] >> (3 - (var10 & 3) << 3);
            var8 = var10 + 1;
            if ((var13 & 192) != 128) {
               this._reportInvalidOther(var13);
            }

            var10 = var13 & 63 | var14 << 6;
            if (var16 > 1) {
               var14 = var1[var8 >> 2] >> (3 - (var8 & 3) << 3);
               var13 = var8 + 1;
               if ((var14 & 192) != 128) {
                  this._reportInvalidOther(var14);
               }

               var10 = var14 & 63 | var10 << 6;
               var8 = var10;
               var14 = var13;
               if (var16 > 2) {
                  var8 = var1[var13 >> 2] >> (3 - (var13 & 3) << 3);
                  var14 = var13 + 1;
                  if ((var8 & 192) != 128) {
                     this._reportInvalidOther(var8 & 255);
                  }

                  var8 = var10 << 6 | var8 & 63;
               }

               var10 = var8;
            } else {
               var14 = var8;
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

   private final String findName(int var1, int var2) throws JsonParseException {
      var1 = _padLastQuad(var1, var2);
      String var3 = this._symbols.findName(var1);
      if (var3 != null) {
         return var3;
      } else {
         int[] var4 = this._quadBuffer;
         var4[0] = var1;
         return this.addName(var4, 1, var2);
      }
   }

   private final String findName(int var1, int var2, int var3) throws JsonParseException {
      var2 = _padLastQuad(var2, var3);
      String var4 = this._symbols.findName(var1, var2);
      if (var4 != null) {
         return var4;
      } else {
         int[] var5 = this._quadBuffer;
         var5[0] = var1;
         var5[1] = var2;
         return this.addName(var5, 2, var3);
      }
   }

   private final String findName(int var1, int var2, int var3, int var4) throws JsonParseException {
      var3 = _padLastQuad(var3, var4);
      String var5 = this._symbols.findName(var1, var2, var3);
      if (var5 != null) {
         return var5;
      } else {
         int[] var6 = this._quadBuffer;
         var6[0] = var1;
         var6[1] = var2;
         var6[2] = _padLastQuad(var3, var4);
         return this.addName(var6, 3, var4);
      }
   }

   private final String findName(int[] var1, int var2, int var3, int var4) throws JsonParseException {
      int[] var5 = var1;
      if (var2 >= var1.length) {
         var5 = growArrayBy(var1, var1.length);
         this._quadBuffer = var5;
      }

      int var6 = var2 + 1;
      var5[var2] = _padLastQuad(var3, var4);
      String var7 = this._symbols.findName(var5, var6);
      return var7 == null ? this.addName(var5, var6, var4) : var7;
   }

   private int nextByte() throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      return var1[var2] & 255;
   }

   private final String parseName(int var1, int var2, int var3) throws IOException {
      return this.parseEscapedName(this._quadBuffer, 0, var1, var2, var3);
   }

   private final String parseName(int var1, int var2, int var3, int var4) throws IOException {
      int[] var5 = this._quadBuffer;
      var5[0] = var1;
      return this.parseEscapedName(var5, 1, var2, var3, var4);
   }

   private final String parseName(int var1, int var2, int var3, int var4, int var5) throws IOException {
      int[] var6 = this._quadBuffer;
      var6[0] = var1;
      var6[1] = var2;
      return this.parseEscapedName(var6, 2, var3, var4, var5);
   }

   protected void _closeInput() throws IOException {
      if (this._inputStream != null) {
         if (this._ioContext.isResourceManaged() || this.isEnabled(JsonParser.Feature.AUTO_CLOSE_SOURCE)) {
            this._inputStream.close();
         }

         this._inputStream = null;
      }

   }

   protected final byte[] _decodeBase64(Base64Variant var1) throws IOException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         while(true) {
            byte[] var3;
            int var4;
            int var5;
            int var6;
            do {
               do {
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var3 = this._inputBuffer;
                  var4 = this._inputPtr++;
                  var5 = var3[var4] & 255;
               } while(var5 <= 32);

               var6 = var1.decodeBase64Char(var5);
               var4 = var6;
               if (var6 >= 0) {
                  break;
               }

               if (var5 == 34) {
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
            int var7 = var3[var6] & 255;
            var5 = var1.decodeBase64Char(var7);
            var6 = var5;
            if (var5 < 0) {
               var6 = this._decodeBase64Escape(var1, var7, 1);
            }

            var7 = var4 << 6 | var6;
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            var3 = this._inputBuffer;
            var4 = this._inputPtr++;
            int var8 = var3[var4] & 255;
            var5 = var1.decodeBase64Char(var8);
            var6 = var5;
            if (var5 < 0) {
               var4 = var5;
               if (var5 != -2) {
                  if (var8 == 34) {
                     var2.append(var7 >> 4);
                     if (var1.usesPadding()) {
                        --this._inputPtr;
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var8, 2);
               }

               var6 = var4;
               if (var4 == -2) {
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var3 = this._inputBuffer;
                  var4 = this._inputPtr++;
                  var4 = var3[var4] & 255;
                  if (!var1.usesPaddingChar(var4) && this._decodeBase64Escape(var1, var4, 3) != -2) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("expected padding character '");
                     var9.append(var1.getPaddingChar());
                     var9.append("'");
                     throw this.reportInvalidBase64Char(var1, var4, 3, var9.toString());
                  }

                  var2.append(var7 >> 4);
                  continue;
               }
            }

            var7 = var7 << 6 | var6;
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            var3 = this._inputBuffer;
            var4 = this._inputPtr++;
            var8 = var3[var4] & 255;
            var5 = var1.decodeBase64Char(var8);
            var6 = var5;
            if (var5 < 0) {
               var4 = var5;
               if (var5 != -2) {
                  if (var8 == 34) {
                     var2.appendTwoBytes(var7 >> 2);
                     if (var1.usesPadding()) {
                        --this._inputPtr;
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var4 = this._decodeBase64Escape(var1, var8, 3);
               }

               var6 = var4;
               if (var4 == -2) {
                  var2.appendTwoBytes(var7 >> 2);
                  continue;
               }
            }

            var2.appendThreeBytes(var7 << 6 | var6);
         }
      }
   }

   protected int _decodeCharForError(int var1) throws IOException {
      int var2 = var1 & 255;
      var1 = var2;
      if (var2 > 127) {
         byte var4;
         label37: {
            if ((var2 & 224) == 192) {
               var1 = var2 & 31;
            } else {
               if ((var2 & 240) == 224) {
                  var1 = var2 & 15;
                  var4 = 2;
                  break label37;
               }

               if ((var2 & 248) == 240) {
                  var1 = var2 & 7;
                  var4 = 3;
                  break label37;
               }

               this._reportInvalidInitial(var2 & 255);
               var1 = var2;
            }

            var4 = 1;
         }

         int var3 = this.nextByte();
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255);
         }

         var3 = var1 << 6 | var3 & 63;
         var1 = var3;
         if (var4 > 1) {
            var1 = this.nextByte();
            if ((var1 & 192) != 128) {
               this._reportInvalidOther(var1 & 255);
            }

            var3 = var3 << 6 | var1 & 63;
            var1 = var3;
            if (var4 > 2) {
               var1 = this.nextByte();
               if ((var1 & 192) != 128) {
                  this._reportInvalidOther(var1 & 255);
               }

               var1 = var3 << 6 | var1 & 63;
            }
         }
      }

      return var1;
   }

   protected char _decodeEscaped() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      byte var6 = var1[var2];
      if (var6 != 34 && var6 != 47 && var6 != 92) {
         if (var6 != 98) {
            if (var6 != 102) {
               if (var6 != 110) {
                  if (var6 != 114) {
                     if (var6 != 116) {
                        if (var6 != 117) {
                           return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(var6));
                        } else {
                           var2 = 0;

                           int var3;
                           for(var3 = 0; var2 < 4; ++var2) {
                              if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                                 this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                              }

                              var1 = this._inputBuffer;
                              int var4 = this._inputPtr++;
                              byte var7 = var1[var4];
                              int var5 = CharTypes.charToHex(var7);
                              if (var5 < 0) {
                                 this._reportUnexpectedChar(var7 & 255, "expected a hex-digit for character escape sequence");
                              }

                              var3 = var3 << 4 | var5;
                           }

                           return (char)var3;
                        }
                     } else {
                        return '\t';
                     }
                  } else {
                     return '\r';
                  }
               } else {
                  return '\n';
               }
            } else {
               return '\f';
            }
         } else {
            return '\b';
         }
      } else {
         return (char)var6;
      }
   }

   protected String _finishAndReturnString() throws IOException {
      int var1 = this._inputPtr;
      int var2 = var1;
      if (var1 >= this._inputEnd) {
         this._loadMoreGuaranteed();
         var2 = this._inputPtr;
      }

      var1 = 0;
      char[] var3 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var4 = _icUTF8;
      int var5 = Math.min(this._inputEnd, var3.length + var2);

      for(byte[] var6 = this._inputBuffer; var2 < var5; ++var1) {
         int var7 = var6[var2] & 255;
         if (var4[var7] != 0) {
            if (var7 == 34) {
               this._inputPtr = var2 + 1;
               return this._textBuffer.setCurrentAndReturn(var1);
            }
            break;
         }

         ++var2;
         var3[var1] = (char)((char)var7);
      }

      this._inputPtr = var2;
      this._finishString2(var3, var1);
      return this._textBuffer.contentsAsString();
   }

   protected void _finishString() throws IOException {
      int var1 = this._inputPtr;
      int var2 = var1;
      if (var1 >= this._inputEnd) {
         this._loadMoreGuaranteed();
         var2 = this._inputPtr;
      }

      var1 = 0;
      char[] var3 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var4 = _icUTF8;
      int var5 = Math.min(this._inputEnd, var3.length + var2);

      for(byte[] var6 = this._inputBuffer; var2 < var5; ++var1) {
         int var7 = var6[var2] & 255;
         if (var4[var7] != 0) {
            if (var7 == 34) {
               this._inputPtr = var2 + 1;
               this._textBuffer.setCurrentLength(var1);
               return;
            }
            break;
         }

         ++var2;
         var3[var1] = (char)((char)var7);
      }

      this._inputPtr = var2;
      this._finishString2(var3, var1);
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
      int[] var2 = _icUTF8;
      byte[] var3 = this._inputBuffer;
      int var4 = 0;

      while(true) {
         while(true) {
            if (this._inputPtr >= this._inputEnd) {
               this._loadMoreGuaranteed();
            }

            char[] var5 = var1;
            int var6 = var4;
            if (var4 >= var1.length) {
               var5 = this._textBuffer.finishCurrentSegment();
               var6 = 0;
            }

            int var7 = this._inputEnd;
            var4 = this._inputPtr + (var5.length - var6);
            int var8 = var6;
            int var9 = var7;
            if (var4 < var7) {
               var9 = var4;
               var8 = var6;
            }

            while(true) {
               var1 = var5;
               var4 = var8;
               if (this._inputPtr >= var9) {
                  break;
               }

               var4 = this._inputPtr++;
               var4 = var3[var4] & 255;
               if (var4 == 39 || var2[var4] != 0) {
                  if (var4 == 39) {
                     this._textBuffer.setCurrentLength(var8);
                     return JsonToken.VALUE_STRING;
                  }

                  var6 = var2[var4];
                  if (var6 != 1) {
                     if (var6 != 2) {
                        if (var6 != 3) {
                           if (var6 != 4) {
                              if (var4 < 32) {
                                 this._throwUnquotedSpace(var4, "string value");
                              }

                              this._reportInvalidChar(var4);
                           } else {
                              var6 = this._decodeUtf8_4(var4);
                              var4 = var8 + 1;
                              var5[var8] = (char)((char)('\ud800' | var6 >> 10));
                              if (var4 >= var5.length) {
                                 var5 = this._textBuffer.finishCurrentSegment();
                                 var8 = 0;
                              } else {
                                 var8 = var4;
                              }

                              var4 = '\udc00' | var6 & 1023;
                           }
                        } else if (this._inputEnd - this._inputPtr >= 2) {
                           var4 = this._decodeUtf8_3fast(var4);
                        } else {
                           var4 = this._decodeUtf8_3(var4);
                        }
                     } else {
                        var4 = this._decodeUtf8_2(var4);
                     }
                  } else {
                     var4 = this._decodeEscaped();
                  }

                  var1 = var5;
                  var6 = var8;
                  if (var8 >= var5.length) {
                     var1 = this._textBuffer.finishCurrentSegment();
                     var6 = 0;
                  }

                  var1[var6] = (char)((char)var4);
                  var4 = var6 + 1;
                  break;
               }

               var5[var8] = (char)((char)var4);
               ++var8;
            }
         }
      }
   }

   protected JsonToken _handleInvalidNumberStart(int var1, boolean var2) throws IOException {
      int var3;
      while(true) {
         var3 = var1;
         if (var1 != 73) {
            break;
         }

         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_FLOAT);
         }

         byte[] var4 = this._inputBuffer;
         var1 = this._inputPtr++;
         var1 = var4[var1];
         String var7;
         if (var1 == 78) {
            if (var2) {
               var7 = "-INF";
            } else {
               var7 = "+INF";
            }
         } else {
            var3 = var1;
            if (var1 != 110) {
               break;
            }

            if (var2) {
               var7 = "-Infinity";
            } else {
               var7 = "+Infinity";
            }
         }

         this._matchToken(var7, 3);
         if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
            double var5;
            if (var2) {
               var5 = Double.NEGATIVE_INFINITY;
            } else {
               var5 = Double.POSITIVE_INFINITY;
            }

            return this.resetAsNaN(var7, var5);
         }

         this._reportError("Non-standard token '%s': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow", var7);
      }

      this.reportUnexpectedNumberChar(var3, "expected digit (0-9) to follow minus sign, for valid numeric value");
      return null;
   }

   protected String _handleOddName(int var1) throws IOException {
      if (var1 == 39 && (this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
         return this._parseAposName();
      } else {
         if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            this._reportUnexpectedChar((char)this._decodeCharForError(var1), "was expecting double-quote to start field name");
         }

         int[] var2 = CharTypes.getInputCodeUtf8JsNames();
         if (var2[var1] != 0) {
            this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
         }

         int[] var3 = this._quadBuffer;
         int var4 = 0;
         int var5 = 0;
         int var6 = 0;

         while(true) {
            int[] var7;
            if (var4 < 4) {
               ++var4;
               var1 |= var6 << 8;
               var7 = var3;
               var6 = var4;
            } else {
               var7 = var3;
               if (var5 >= var3.length) {
                  var7 = growArrayBy(var3, var3.length);
                  this._quadBuffer = var7;
               }

               var7[var5] = var6;
               ++var5;
               var6 = 1;
            }

            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
               this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }

            int var8 = this._inputBuffer[this._inputPtr] & 255;
            if (var2[var8] != 0) {
               var3 = var7;
               var4 = var5;
               if (var6 > 0) {
                  var3 = var7;
                  if (var5 >= var7.length) {
                     var3 = growArrayBy(var7, var7.length);
                     this._quadBuffer = var3;
                  }

                  var3[var5] = var1;
                  var4 = var5 + 1;
               }

               String var9 = this._symbols.findName(var3, var4);
               String var10 = var9;
               if (var9 == null) {
                  var10 = this.addName(var3, var4, var6);
               }

               return var10;
            }

            ++this._inputPtr;
            var3 = var7;
            var4 = var6;
            var6 = var1;
            var1 = var8;
         }
      }
   }

   protected JsonToken _handleUnexpectedValue(int var1) throws IOException {
      label59: {
         if (var1 != 39) {
            if (var1 == 73) {
               this._matchToken("Infinity", 1);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
               }

               this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               break label59;
            }

            if (var1 == 78) {
               this._matchToken("NaN", 1);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  return this.resetAsNaN("NaN", Double.NaN);
               }

               this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               break label59;
            }

            label53: {
               if (var1 != 93) {
                  if (var1 == 125) {
                     break label53;
                  }

                  if (var1 == 43) {
                     if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                     }

                     byte[] var2 = this._inputBuffer;
                     var1 = this._inputPtr++;
                     return this._handleInvalidNumberStart(var2[var1] & 255, false);
                  }

                  if (var1 != 44) {
                     break label59;
                  }
               } else if (!this._parsingContext.inArray()) {
                  break label59;
               }

               if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                  --this._inputPtr;
                  return JsonToken.VALUE_NULL;
               }
            }

            this._reportUnexpectedChar(var1, "expected a value");
         }

         if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._handleApos();
         }
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

   protected final boolean _loadMore() throws IOException {
      int var1 = this._inputEnd;
      InputStream var2 = this._inputStream;
      if (var2 != null) {
         byte[] var3 = this._inputBuffer;
         int var4 = var3.length;
         if (var4 == 0) {
            return false;
         }

         var4 = var2.read(var3, 0, var4);
         if (var4 > 0) {
            this._inputPtr = 0;
            this._inputEnd = var4;
            this._currInputProcessed += (long)this._inputEnd;
            this._currInputRowStart -= this._inputEnd;
            this._nameStartOffset -= var1;
            return true;
         }

         this._closeInput();
         if (var4 == 0) {
            StringBuilder var5 = new StringBuilder();
            var5.append("InputStream.read() returned 0 characters when trying to read ");
            var5.append(this._inputBuffer.length);
            var5.append(" bytes");
            throw new IOException(var5.toString());
         }
      }

      return false;
   }

   protected void _loadMoreGuaranteed() throws IOException {
      if (!this._loadMore()) {
         this._reportInvalidEOF();
      }

   }

   protected final void _matchFalse() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 4 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         int var3 = var1 + 1;
         if (var2[var1] == 97) {
            var1 = var3 + 1;
            if (var2[var3] == 108) {
               var3 = var1 + 1;
               if (var2[var1] == 115) {
                  var1 = var3 + 1;
                  if (var2[var3] == 101) {
                     var3 = var2[var1] & 255;
                     if (var3 < 48 || var3 == 93 || var3 == 125) {
                        this._inputPtr = var1;
                        return;
                     }
                  }
               }
            }
         }
      }

      this._matchToken2("false", 1);
   }

   protected final void _matchNull() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         int var3 = var1 + 1;
         if (var2[var1] == 117) {
            var1 = var3 + 1;
            if (var2[var3] == 108) {
               var3 = var1 + 1;
               if (var2[var1] == 108) {
                  var1 = var2[var3] & 255;
                  if (var1 < 48 || var1 == 93 || var1 == 125) {
                     this._inputPtr = var3;
                     return;
                  }
               }
            }
         }
      }

      this._matchToken2("null", 1);
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

         var4 = this._inputBuffer[this._inputPtr] & 255;
         if (var4 >= 48 && var4 != 93 && var4 != 125) {
            this._checkMatchEnd(var1, var2, var4);
         }

      }
   }

   protected final void _matchTrue() throws IOException {
      int var1 = this._inputPtr;
      if (var1 + 3 < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         int var3 = var1 + 1;
         if (var2[var1] == 114) {
            var1 = var3 + 1;
            if (var2[var3] == 117) {
               var3 = var1 + 1;
               if (var2[var1] == 101) {
                  var1 = var2[var3] & 255;
                  if (var1 < 48 || var1 == 93 || var1 == 125) {
                     this._inputPtr = var3;
                     return;
                  }
               }
            }
         }
      }

      this._matchToken2("true", 1);
   }

   protected String _parseAposName() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(": was expecting closing ''' for field name", JsonToken.FIELD_NAME);
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      int var3 = var1[var2] & 255;
      if (var3 == 39) {
         return "";
      } else {
         int[] var13 = this._quadBuffer;
         int[] var4 = _icLatin1;
         int var5 = 0;
         var2 = 0;

         int var6;
         int[] var7;
         byte[] var16;
         for(var6 = 0; var3 != 39; var3 = var16[var3] & 255) {
            int var8 = var3;
            var7 = var13;
            int var9 = var5;
            int var10 = var2;
            int var11 = var6;
            if (var4[var3] != 0) {
               var8 = var3;
               var7 = var13;
               var9 = var5;
               var10 = var2;
               var11 = var6;
               if (var3 != 34) {
                  int var12;
                  if (var3 != 92) {
                     this._throwUnquotedSpace(var3, "name");
                     var12 = var3;
                  } else {
                     var12 = this._decodeEscaped();
                  }

                  var8 = var12;
                  var7 = var13;
                  var9 = var5;
                  var10 = var2;
                  var11 = var6;
                  if (var12 > 127) {
                     var7 = var13;
                     var8 = var5;
                     var3 = var2;
                     var10 = var6;
                     if (var5 >= 4) {
                        var7 = var13;
                        if (var2 >= var13.length) {
                           var7 = growArrayBy(var13, var13.length);
                           this._quadBuffer = var7;
                        }

                        var7[var2] = var6;
                        var3 = var2 + 1;
                        var8 = 0;
                        var10 = 0;
                     }

                     if (var12 < 2048) {
                        var5 = var10 << 8 | var12 >> 6 | 192;
                        var2 = var8 + 1;
                        var13 = var7;
                     } else {
                        var10 = var10 << 8 | var12 >> 12 | 224;
                        ++var8;
                        var13 = var7;
                        var6 = var8;
                        var2 = var3;
                        var5 = var10;
                        if (var8 >= 4) {
                           var13 = var7;
                           if (var3 >= var7.length) {
                              var13 = growArrayBy(var7, var7.length);
                              this._quadBuffer = var13;
                           }

                           var13[var3] = var10;
                           var2 = var3 + 1;
                           var6 = 0;
                           var5 = 0;
                        }

                        var5 = var5 << 8 | var12 >> 6 & 63 | 128;
                        ++var6;
                        var3 = var2;
                        var2 = var6;
                     }

                     var8 = var12 & 63 | 128;
                     var11 = var5;
                     var10 = var3;
                     var9 = var2;
                     var7 = var13;
                  }
               }
            }

            if (var9 < 4) {
               var5 = var9 + 1;
               var6 = var8 | var11 << 8;
               var13 = var7;
               var2 = var10;
            } else {
               var13 = var7;
               if (var10 >= var7.length) {
                  var13 = growArrayBy(var7, var7.length);
                  this._quadBuffer = var13;
               }

               var13[var10] = var11;
               var6 = var8;
               var2 = var10 + 1;
               var5 = 1;
            }

            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
               this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }

            var16 = this._inputBuffer;
            var3 = this._inputPtr++;
         }

         var7 = var13;
         var3 = var2;
         if (var5 > 0) {
            var7 = var13;
            if (var2 >= var13.length) {
               var7 = growArrayBy(var13, var13.length);
               this._quadBuffer = var7;
            }

            var7[var2] = _padLastQuad(var6, var5);
            var3 = var2 + 1;
         }

         String var14 = this._symbols.findName(var7, var3);
         String var15 = var14;
         if (var14 == null) {
            var15 = this.addName(var7, var3, var5);
         }

         return var15;
      }
   }

   protected final String _parseName(int var1) throws IOException {
      if (var1 != 34) {
         return this._handleOddName(var1);
      } else if (this._inputPtr + 13 > this._inputEnd) {
         return this.slowParseName();
      } else {
         byte[] var2 = this._inputBuffer;
         int[] var3 = _icLatin1;
         var1 = this._inputPtr++;
         var1 = var2[var1] & 255;
         if (var3[var1] == 0) {
            int var4 = this._inputPtr++;
            var4 = var2[var4] & 255;
            if (var3[var4] == 0) {
               var1 = var1 << 8 | var4;
               var4 = this._inputPtr++;
               var4 = var2[var4] & 255;
               if (var3[var4] == 0) {
                  var1 = var1 << 8 | var4;
                  var4 = this._inputPtr++;
                  var4 = var2[var4] & 255;
                  if (var3[var4] == 0) {
                     var1 = var1 << 8 | var4;
                     var4 = this._inputPtr++;
                     var4 = var2[var4] & 255;
                     if (var3[var4] == 0) {
                        this._quad1 = var1;
                        return this.parseMediumName(var4);
                     } else {
                        return var4 == 34 ? this.findName(var1, 4) : this.parseName(var1, var4, 4);
                     }
                  } else {
                     return var4 == 34 ? this.findName(var1, 3) : this.parseName(var1, var4, 3);
                  }
               } else {
                  return var4 == 34 ? this.findName(var1, 2) : this.parseName(var1, var4, 2);
               }
            } else {
               return var4 == 34 ? this.findName(var1, 1) : this.parseName(var1, var4, 1);
            }
         } else {
            return var1 == 34 ? "" : this.parseName(0, var1, 0);
         }
      }
   }

   protected JsonToken _parseNegNumber() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      var1[0] = (char)45;
      if (this._inputPtr >= this._inputEnd) {
         this._loadMoreGuaranteed();
      }

      byte[] var2 = this._inputBuffer;
      int var3 = this._inputPtr++;
      int var4 = var2[var3] & 255;
      if (var4 <= 48) {
         if (var4 != 48) {
            return this._handleInvalidNumberStart(var4, true);
         }

         var3 = this._verifyNoLeadingZeroes();
      } else {
         var3 = var4;
         if (var4 > 57) {
            return this._handleInvalidNumberStart(var4, true);
         }
      }

      byte var5 = 2;
      var1[1] = (char)((char)var3);
      int var6 = Math.min(this._inputEnd, this._inputPtr + var1.length - 2);
      var4 = 1;

      for(var3 = var5; this._inputPtr < var6; ++var3) {
         var2 = this._inputBuffer;
         int var7 = this._inputPtr++;
         var7 = var2[var7] & 255;
         if (var7 < 48 || var7 > 57) {
            if (var7 != 46 && var7 != 101 && var7 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var3);
               if (this._parsingContext.inRoot()) {
                  this._verifyRootSpace(var7);
               }

               return this.resetInt(true, var4);
            } else {
               return this._parseFloat(var1, var3, var7, true, var4);
            }
         }

         ++var4;
         var1[var3] = (char)((char)var7);
      }

      return this._parseNumber2(var1, var3, true, var4);
   }

   protected JsonToken _parsePosNumber(int var1) throws IOException {
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int var3 = var1;
      if (var1 == 48) {
         var3 = this._verifyNoLeadingZeroes();
      }

      var2[0] = (char)((char)var3);
      int var4 = Math.min(this._inputEnd, this._inputPtr + var2.length - 1);
      var3 = 1;

      for(var1 = 1; this._inputPtr < var4; ++var3) {
         byte[] var5 = this._inputBuffer;
         int var6 = this._inputPtr++;
         var6 = var5[var6] & 255;
         if (var6 < 48 || var6 > 57) {
            if (var6 != 46 && var6 != 101 && var6 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var3);
               if (this._parsingContext.inRoot()) {
                  this._verifyRootSpace(var6);
               }

               return this.resetInt(false, var1);
            } else {
               return this._parseFloat(var2, var3, var6, false, var1);
            }
         }

         ++var1;
         var2[var3] = (char)((char)var6);
      }

      return this._parseNumber2(var2, var3, false, var1);
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
            byte[] var7 = this._inputBuffer;
            var8 = this._inputPtr++;
            int var9 = var7[var8] & 255;
            if (var9 > 32) {
               label109: {
                  var8 = var1.decodeBase64Char(var9);
                  int var10 = var8;
                  if (var8 < 0) {
                     if (var9 == 34) {
                        break;
                     }

                     var8 = this._decodeBase64Escape(var1, var9, 0);
                     var10 = var8;
                     if (var8 < 0) {
                        break label109;
                     }
                  }

                  var9 = var5;
                  var8 = var6;
                  if (var5 > var4 - 3) {
                     var8 = var6 + var5;
                     var2.write(var3, 0, var5);
                     var9 = 0;
                  }

                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  int var11 = var7[var5] & 255;
                  var6 = var1.decodeBase64Char(var11);
                  var5 = var6;
                  if (var6 < 0) {
                     var5 = this._decodeBase64Escape(var1, var11, 1);
                  }

                  var11 = var10 << 6 | var5;
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  int var12 = var7[var5] & 255;
                  var6 = var1.decodeBase64Char(var12);
                  var10 = var6;
                  if (var6 < 0) {
                     var5 = var6;
                     if (var6 != -2) {
                        if (var12 == 34) {
                           var3[var9] = (byte)((byte)(var11 >> 4));
                           if (var1.usesPadding()) {
                              --this._inputPtr;
                              this._handleBase64MissingPadding(var1);
                           }

                           var5 = var9 + 1;
                           var6 = var8;
                           break;
                        }

                        var5 = this._decodeBase64Escape(var1, var12, 2);
                     }

                     var10 = var5;
                     if (var5 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                           this._loadMoreGuaranteed();
                        }

                        var7 = this._inputBuffer;
                        var5 = this._inputPtr++;
                        var5 = var7[var5] & 255;
                        if (!var1.usesPaddingChar(var5) && this._decodeBase64Escape(var1, var5, 3) != -2) {
                           StringBuilder var13 = new StringBuilder();
                           var13.append("expected padding character '");
                           var13.append(var1.getPaddingChar());
                           var13.append("'");
                           throw this.reportInvalidBase64Char(var1, var5, 3, var13.toString());
                        }

                        var3[var9] = (byte)((byte)(var11 >> 4));
                        var5 = var9 + 1;
                        var6 = var8;
                        continue;
                     }
                  }

                  var10 |= var11 << 6;
                  if (this._inputPtr >= this._inputEnd) {
                     this._loadMoreGuaranteed();
                  }

                  var7 = this._inputBuffer;
                  var5 = this._inputPtr++;
                  var6 = var7[var5] & 255;
                  var5 = var1.decodeBase64Char(var6);
                  if (var5 < 0) {
                     if (var5 != -2) {
                        if (var6 == 34) {
                           var5 = var10 >> 2;
                           var6 = var9 + 1;
                           var3[var9] = (byte)((byte)(var5 >> 8));
                           var9 = var6 + 1;
                           var3[var6] = (byte)((byte)var5);
                           var5 = var9;
                           var6 = var8;
                           if (var1.usesPadding()) {
                              --this._inputPtr;
                              this._handleBase64MissingPadding(var1);
                              var6 = var8;
                              var5 = var9;
                           }
                           break;
                        }

                        var5 = this._decodeBase64Escape(var1, var6, 3);
                     }

                     var6 = var5;
                     if (var5 == -2) {
                        var6 = var10 >> 2;
                        var10 = var9 + 1;
                        var3[var9] = (byte)((byte)(var6 >> 8));
                        var5 = var10 + 1;
                        var3[var10] = (byte)((byte)var6);
                        break label110;
                     }
                  } else {
                     var6 = var5;
                  }

                  var6 |= var10 << 6;
                  var5 = var9 + 1;
                  var3[var9] = (byte)((byte)(var6 >> 16));
                  var9 = var5 + 1;
                  var3[var5] = (byte)((byte)(var6 >> 8));
                  var3[var9] = (byte)((byte)var6);
                  var5 = var9 + 1;
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
         byte[] var1 = this._inputBuffer;
         if (var1 != null) {
            this._inputBuffer = NO_BYTES;
            this._ioContext.releaseReadIOBuffer(var1);
         }
      }

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

   protected void _reportInvalidToken(String var1) throws IOException {
      this._reportInvalidToken(var1, this._validJsonTokenList());
   }

   protected void _reportInvalidToken(String var1, int var2) throws IOException {
      this._inputPtr = var2;
      this._reportInvalidToken(var1, this._validJsonTokenList());
   }

   protected void _reportInvalidToken(String var1, String var2) throws IOException {
      StringBuilder var3 = new StringBuilder(var1);

      while(this._inputPtr < this._inputEnd || this._loadMore()) {
         byte[] var6 = this._inputBuffer;
         int var4 = this._inputPtr++;
         char var5 = (char)this._decodeCharForError(var6[var4]);
         if (!Character.isJavaIdentifierPart(var5)) {
            break;
         }

         var3.append(var5);
         if (var3.length() >= 256) {
            var3.append("...");
            break;
         }
      }

      this._reportError("Unrecognized token '%s': was expecting %s", var3, var2);
   }

   protected final void _skipCR() throws IOException {
      if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
         ++this._inputPtr;
      }

      ++this._currInputRow;
      this._currInputRowStart = this._inputPtr;
   }

   protected void _skipString() throws IOException {
      this._tokenIncomplete = false;
      int[] var1 = _icUTF8;
      byte[] var2 = this._inputBuffer;

      while(true) {
         label39:
         while(true) {
            int var3 = this._inputPtr;
            int var4 = this._inputEnd;
            int var5 = var3;
            int var6 = var4;
            if (var3 >= var4) {
               this._loadMoreGuaranteed();
               var5 = this._inputPtr;
               var6 = this._inputEnd;
            }

            while(var5 < var6) {
               var4 = var5 + 1;
               var5 = var2[var5] & 255;
               if (var1[var5] != 0) {
                  this._inputPtr = var4;
                  if (var5 == 34) {
                     return;
                  }

                  var6 = var1[var5];
                  if (var6 != 1) {
                     if (var6 != 2) {
                        if (var6 != 3) {
                           if (var6 != 4) {
                              if (var5 < 32) {
                                 this._throwUnquotedSpace(var5, "string value");
                              } else {
                                 this._reportInvalidChar(var5);
                              }
                           } else {
                              this._skipUtf8_4(var5);
                           }
                        } else {
                           this._skipUtf8_3();
                        }
                     } else {
                        this._skipUtf8_2();
                     }
                  } else {
                     this._decodeEscaped();
                  }
                  continue label39;
               }

               var5 = var4;
            }

            this._inputPtr = var5;
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
      StringBuilder var2;
      if (this._currToken != JsonToken.VALUE_STRING && (this._currToken != JsonToken.VALUE_EMBEDDED_OBJECT || this._binaryValue == null)) {
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

   public ObjectCodec getCodec() {
      return this._objectCodec;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      return new JsonLocation(this._getSourceReference(), this._currInputProcessed + (long)this._inputPtr, -1L, this._currInputRow, var1 - var2 + 1);
   }

   public Object getInputSource() {
      return this._inputStream;
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

   public String getText() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._getText2(this._currToken);
      }
   }

   public char[] getTextCharacters() throws IOException {
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

   public int getTextLength() throws IOException {
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

   public int getTextOffset() throws IOException {
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
         long var3 = (long)(this._nameStartOffset - 1);
         return new JsonLocation(this._getSourceReference(), var1 + var3, -1L, this._nameStartRow, this._nameStartCol);
      } else {
         return new JsonLocation(this._getSourceReference(), this._tokenInputTotal - 1L, -1L, this._tokenInputRow, this._tokenInputCol);
      }
   }

   public int getValueAsInt() throws IOException {
      JsonToken var1 = this._currToken;
      if (var1 != JsonToken.VALUE_NUMBER_INT && var1 != JsonToken.VALUE_NUMBER_FLOAT) {
         return super.getValueAsInt(0);
      } else {
         if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
               return this._parseIntValue();
            }

            if ((this._numTypesValid & 1) == 0) {
               this.convertNumberToInt();
            }
         }

         return this._numberInt;
      }
   }

   public int getValueAsInt(int var1) throws IOException {
      JsonToken var2 = this._currToken;
      if (var2 != JsonToken.VALUE_NUMBER_INT && var2 != JsonToken.VALUE_NUMBER_FLOAT) {
         return super.getValueAsInt(var1);
      } else {
         if ((this._numTypesValid & 1) == 0) {
            if (this._numTypesValid == 0) {
               return this._parseIntValue();
            }

            if ((this._numTypesValid & 1) == 0) {
               this.convertNumberToInt();
            }
         }

         return this._numberInt;
      }
   }

   public String getValueAsString() throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString((String)null);
      }
   }

   public String getValueAsString(String var1) throws IOException {
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            return this._finishAndReturnString();
         } else {
            return this._textBuffer.contentsAsString();
         }
      } else {
         return this._currToken == JsonToken.FIELD_NAME ? this.getCurrentName() : super.getValueAsString(var1);
      }
   }

   public Boolean nextBooleanValue() throws IOException {
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
         if (var1 == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
         } else {
            return var1 == JsonToken.VALUE_FALSE ? Boolean.FALSE : null;
         }
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
            if (var1 == 93) {
               this._closeArrayScope();
               this._currToken = JsonToken.END_ARRAY;
               return null;
            } else if (var1 == 125) {
               this._closeObjectScope();
               this._currToken = JsonToken.END_OBJECT;
               return null;
            } else {
               label75: {
                  int var2 = var1;
                  if (this._parsingContext.expectComma()) {
                     if (var1 != 44) {
                        StringBuilder var3 = new StringBuilder();
                        var3.append("was expecting comma to separate ");
                        var3.append(this._parsingContext.typeDesc());
                        var3.append(" entries");
                        this._reportUnexpectedChar(var1, var3.toString());
                     }

                     var1 = this._skipWS();
                     var2 = var1;
                     if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                        if (var1 == 93) {
                           break label75;
                        }

                        var2 = var1;
                        if (var1 == 125) {
                           break label75;
                        }
                     }
                  }

                  if (!this._parsingContext.inObject()) {
                     this._updateLocation();
                     this._nextTokenNotInObject(var2);
                     return null;
                  }

                  this._updateNameLocation();
                  String var4 = this._parseName(var2);
                  this._parsingContext.setCurrentName(var4);
                  this._currToken = JsonToken.FIELD_NAME;
                  var2 = this._skipColon();
                  this._updateLocation();
                  if (var2 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return var4;
                  }

                  JsonToken var5;
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
                                       var5 = this._parsePosNumber(var2);
                                       break;
                                    default:
                                       var5 = this._handleUnexpectedValue(var2);
                                    }
                                 } else {
                                    var5 = JsonToken.START_OBJECT;
                                 }
                              } else {
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
                        var5 = JsonToken.START_ARRAY;
                     }
                  } else {
                     var5 = this._parseNegNumber();
                  }

                  this._nextToken = var5;
                  return var4;
               }

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
            if (var3 == 93) {
               this._closeArrayScope();
               this._currToken = JsonToken.END_ARRAY;
               return false;
            } else if (var3 == 125) {
               this._closeObjectScope();
               this._currToken = JsonToken.END_OBJECT;
               return false;
            } else {
               int var4 = var3;
               if (this._parsingContext.expectComma()) {
                  if (var3 != 44) {
                     StringBuilder var5 = new StringBuilder();
                     var5.append("was expecting comma to separate ");
                     var5.append(this._parsingContext.typeDesc());
                     var5.append(" entries");
                     this._reportUnexpectedChar(var3, var5.toString());
                  }

                  var3 = this._skipWS();
                  var4 = var3;
                  if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     label76: {
                        if (var3 != 93) {
                           var4 = var3;
                           if (var3 != 125) {
                              break label76;
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
                     byte[] var7 = var1.asQuotedUTF8();
                     var3 = var7.length;
                     if (this._inputPtr + var3 + 4 < this._inputEnd) {
                        int var6 = this._inputPtr + var3;
                        if (this._inputBuffer[var6] == 34) {
                           var3 = this._inputPtr;

                           while(true) {
                              if (var3 == var6) {
                                 this._parsingContext.setCurrentName(var1.getValue());
                                 this._isNextTokenNameYes(this._skipColonFast(var3 + 1));
                                 return true;
                              }

                              if (var7[var2] != this._inputBuffer[var3]) {
                                 break;
                              }

                              ++var2;
                              ++var3;
                           }
                        }
                     }
                  }

                  return this._isNextTokenNameMaybe(var4, var1);
               }
            }
         }
      }
   }

   public int nextIntValue(int var1) throws IOException {
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

   public long nextLongValue(long var1) throws IOException {
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

   public String nextTextValue() throws IOException {
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
               return this._finishAndReturnString();
            } else {
               return this._textBuffer.contentsAsString();
            }
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

   public JsonToken nextToken() throws IOException {
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
            JsonToken var5;
            if (var1 == 93) {
               this._closeArrayScope();
               var5 = JsonToken.END_ARRAY;
               this._currToken = var5;
               return var5;
            } else if (var1 == 125) {
               this._closeObjectScope();
               var5 = JsonToken.END_OBJECT;
               this._currToken = var5;
               return var5;
            } else {
               int var3 = var1;
               if (this._parsingContext.expectComma()) {
                  if (var1 != 44) {
                     StringBuilder var2 = new StringBuilder();
                     var2.append("was expecting comma to separate ");
                     var2.append(this._parsingContext.typeDesc());
                     var2.append(" entries");
                     this._reportUnexpectedChar(var1, var2.toString());
                  }

                  var1 = this._skipWS();
                  var3 = var1;
                  if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     if (var1 == 93) {
                        return this._closeScope(var1);
                     }

                     var3 = var1;
                     if (var1 == 125) {
                        return this._closeScope(var1);
                     }
                  }
               }

               if (!this._parsingContext.inObject()) {
                  this._updateLocation();
                  return this._nextTokenNotInObject(var3);
               } else {
                  this._updateNameLocation();
                  String var4 = this._parseName(var3);
                  this._parsingContext.setCurrentName(var4);
                  this._currToken = JsonToken.FIELD_NAME;
                  var3 = this._skipColon();
                  this._updateLocation();
                  if (var3 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return this._currToken;
                  } else {
                     if (var3 != 45) {
                        if (var3 != 91) {
                           if (var3 != 102) {
                              if (var3 != 110) {
                                 if (var3 != 116) {
                                    if (var3 != 123) {
                                       switch(var3) {
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
                                          var5 = this._parsePosNumber(var3);
                                          break;
                                       default:
                                          var5 = this._handleUnexpectedValue(var3);
                                       }
                                    } else {
                                       var5 = JsonToken.START_OBJECT;
                                    }
                                 } else {
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
                           var5 = JsonToken.START_ARRAY;
                        }
                     } else {
                        var5 = this._parseNegNumber();
                     }

                     this._nextToken = var5;
                     return this._currToken;
                  }
               }
            }
         }
      }
   }

   protected final String parseEscapedName(int[] var1, int var2, int var3, int var4, int var5) throws IOException {
      int[] var6 = _icLatin1;
      int var7 = var4;

      while(true) {
         int[] var8 = var1;
         int var9 = var2;
         int var10 = var3;
         var4 = var7;
         int var11 = var5;
         if (var6[var7] != 0) {
            if (var7 == 34) {
               var8 = var1;
               var4 = var2;
               if (var5 > 0) {
                  var8 = var1;
                  if (var2 >= var1.length) {
                     var8 = growArrayBy(var1, var1.length);
                     this._quadBuffer = var8;
                  }

                  var8[var2] = _padLastQuad(var3, var5);
                  var4 = var2 + 1;
               }

               String var13 = this._symbols.findName(var8, var4);
               String var12 = var13;
               if (var13 == null) {
                  var12 = this.addName(var8, var4, var5);
               }

               return var12;
            }

            if (var7 != 92) {
               this._throwUnquotedSpace(var7, "name");
            } else {
               var7 = this._decodeEscaped();
            }

            var8 = var1;
            var9 = var2;
            var10 = var3;
            var4 = var7;
            var11 = var5;
            if (var7 > 127) {
               byte var14 = 0;
               var8 = var1;
               var4 = var2;
               var11 = var3;
               var9 = var5;
               if (var5 >= 4) {
                  var8 = var1;
                  if (var2 >= var1.length) {
                     var8 = growArrayBy(var1, var1.length);
                     this._quadBuffer = var8;
                  }

                  var8[var2] = var3;
                  var4 = var2 + 1;
                  var11 = 0;
                  var9 = 0;
               }

               if (var7 < 2048) {
                  var2 = var11 << 8 | var7 >> 6 | 192;
                  var3 = var9 + 1;
                  var9 = var4;
               } else {
                  var3 = var11 << 8 | var7 >> 12 | 224;
                  var2 = var9 + 1;
                  if (var2 >= 4) {
                     var1 = var8;
                     if (var4 >= var8.length) {
                        var1 = growArrayBy(var8, var8.length);
                        this._quadBuffer = var1;
                     }

                     var1[var4] = var3;
                     ++var4;
                     var2 = 0;
                     var3 = var14;
                  } else {
                     var1 = var8;
                  }

                  var5 = var3 << 8 | var7 >> 6 & 63 | 128;
                  var3 = var2 + 1;
                  var2 = var5;
                  var9 = var4;
                  var8 = var1;
               }

               var4 = var7 & 63 | 128;
               var11 = var3;
               var10 = var2;
            }
         }

         if (var11 < 4) {
            var5 = var11 + 1;
            var3 = var10 << 8 | var4;
            var1 = var8;
            var2 = var9;
         } else {
            var1 = var8;
            if (var9 >= var8.length) {
               var1 = growArrayBy(var8, var8.length);
               this._quadBuffer = var1;
            }

            var1[var9] = var10;
            var3 = var4;
            var2 = var9 + 1;
            var5 = 1;
         }

         if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
         }

         byte[] var15 = this._inputBuffer;
         var4 = this._inputPtr++;
         var7 = var15[var4] & 255;
      }
   }

   protected final String parseLongName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      var4[0] = this._quad1;
      var4[1] = var2;
      var4[2] = var3;
      byte[] var5 = this._inputBuffer;
      var4 = _icLatin1;
      byte var7 = 3;
      var2 = var1;

      for(var1 = var7; this._inputPtr + 4 <= this._inputEnd; ++var1) {
         var3 = this._inputPtr++;
         var3 = var5[var3] & 255;
         if (var4[var3] != 0) {
            if (var3 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 1);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var2, var3, 1);
         }

         var2 = var2 << 8 | var3;
         var3 = this._inputPtr++;
         var3 = var5[var3] & 255;
         if (var4[var3] != 0) {
            if (var3 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 2);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var2, var3, 2);
         }

         var2 = var2 << 8 | var3;
         var3 = this._inputPtr++;
         var3 = var5[var3] & 255;
         if (var4[var3] != 0) {
            if (var3 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 3);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var2, var3, 3);
         }

         var3 |= var2 << 8;
         var2 = this._inputPtr++;
         var2 = var5[var2] & 255;
         if (var4[var2] != 0) {
            if (var2 == 34) {
               return this.findName(this._quadBuffer, var1, var3, 4);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var3, var2, 4);
         }

         int[] var6 = this._quadBuffer;
         if (var1 >= var6.length) {
            this._quadBuffer = growArrayBy(var6, var1);
         }

         this._quadBuffer[var1] = var3;
      }

      return this.parseEscapedName(this._quadBuffer, var1, 0, var2, 0);
   }

   protected final String parseMediumName(int var1) throws IOException {
      byte[] var2 = this._inputBuffer;
      int[] var3 = _icLatin1;
      int var4 = this._inputPtr++;
      var4 = var2[var4] & 255;
      if (var3[var4] != 0) {
         return var4 == 34 ? this.findName(this._quad1, var1, 1) : this.parseName(this._quad1, var1, var4, 1);
      } else {
         var1 = var1 << 8 | var4;
         var4 = this._inputPtr++;
         var4 = var2[var4] & 255;
         if (var3[var4] != 0) {
            return var4 == 34 ? this.findName(this._quad1, var1, 2) : this.parseName(this._quad1, var1, var4, 2);
         } else {
            var1 = var1 << 8 | var4;
            var4 = this._inputPtr++;
            var4 = var2[var4] & 255;
            if (var3[var4] != 0) {
               return var4 == 34 ? this.findName(this._quad1, var1, 3) : this.parseName(this._quad1, var1, var4, 3);
            } else {
               var1 = var1 << 8 | var4;
               var4 = this._inputPtr++;
               var4 = var2[var4] & 255;
               if (var3[var4] != 0) {
                  return var4 == 34 ? this.findName(this._quad1, var1, 4) : this.parseName(this._quad1, var1, var4, 4);
               } else {
                  return this.parseMediumName2(var4, var1);
               }
            }
         }
      }
   }

   protected final String parseMediumName2(int var1, int var2) throws IOException {
      byte[] var3 = this._inputBuffer;
      int[] var4 = _icLatin1;
      int var5 = this._inputPtr++;
      var5 = var3[var5] & 255;
      if (var4[var5] != 0) {
         return var5 == 34 ? this.findName(this._quad1, var2, var1, 1) : this.parseName(this._quad1, var2, var1, var5, 1);
      } else {
         var1 = var1 << 8 | var5;
         var5 = this._inputPtr++;
         var5 = var3[var5] & 255;
         if (var4[var5] != 0) {
            return var5 == 34 ? this.findName(this._quad1, var2, var1, 2) : this.parseName(this._quad1, var2, var1, var5, 2);
         } else {
            var1 = var1 << 8 | var5;
            var5 = this._inputPtr++;
            var5 = var3[var5] & 255;
            if (var4[var5] != 0) {
               return var5 == 34 ? this.findName(this._quad1, var2, var1, 3) : this.parseName(this._quad1, var2, var1, var5, 3);
            } else {
               var1 = var1 << 8 | var5;
               var5 = this._inputPtr++;
               var5 = var3[var5] & 255;
               if (var4[var5] != 0) {
                  return var5 == 34 ? this.findName(this._quad1, var2, var1, 4) : this.parseName(this._quad1, var2, var1, var5, 4);
               } else {
                  return this.parseLongName(var5, var2, var1);
               }
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

   public int releaseBuffered(OutputStream var1) throws IOException {
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

   protected String slowParseName() throws IOException {
      if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
         this._reportInvalidEOF(": was expecting closing '\"' for name", JsonToken.FIELD_NAME);
      }

      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      var2 = var1[var2] & 255;
      return var2 == 34 ? "" : this.parseEscapedName(this._quadBuffer, 0, 0, var2, 0);
   }
}
