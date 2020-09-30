package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class UTF8DataInputJsonParser extends ParserBase {
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
   protected DataInput _inputData;
   protected int _nextByte = -1;
   protected ObjectCodec _objectCodec;
   private int _quad1;
   protected int[] _quadBuffer = new int[16];
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

   public UTF8DataInputJsonParser(IOContext var1, int var2, DataInput var3, ObjectCodec var4, ByteQuadsCanonicalizer var5, int var6) {
      super(var1, var2);
      this._objectCodec = var4;
      this._symbols = var5;
      this._inputData = var3;
      this._nextByte = var6;
   }

   private final void _checkMatchEnd(String var1, int var2, int var3) throws IOException {
      char var4 = (char)this._decodeCharForError(var3);
      if (Character.isJavaIdentifierPart(var4)) {
         this._reportInvalidToken(var4, var1.substring(0, var2));
      }

   }

   private void _closeScope(int var1) throws JsonParseException {
      if (var1 == 93) {
         if (!this._parsingContext.inArray()) {
            this._reportMismatchedEndMarker(var1, '}');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_ARRAY;
      }

      if (var1 == 125) {
         if (!this._parsingContext.inObject()) {
            this._reportMismatchedEndMarker(var1, ']');
         }

         this._parsingContext = this._parsingContext.clearAndGetParent();
         this._currToken = JsonToken.END_OBJECT;
      }

   }

   private final int _decodeUtf8_2(int var1) throws IOException {
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      return (var1 & 31) << 6 | var2 & 63;
   }

   private final int _decodeUtf8_3(int var1) throws IOException {
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      int var3 = this._inputData.readUnsignedByte();
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255);
      }

      return ((var1 & 15) << 6 | var2 & 63) << 6 | var3 & 63;
   }

   private final int _decodeUtf8_4(int var1) throws IOException {
      int var2 = this._inputData.readUnsignedByte();
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255);
      }

      int var3 = this._inputData.readUnsignedByte();
      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255);
      }

      int var4 = this._inputData.readUnsignedByte();
      if ((var4 & 192) != 128) {
         this._reportInvalidOther(var4 & 255);
      }

      return ((((var1 & 7) << 6 | var2 & 63) << 6 | var3 & 63) << 6 | var4 & 63) - 65536;
   }

   private String _finishAndReturnString() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var2 = _icUTF8;
      int var3 = var1.length;
      int var4 = 0;

      while(true) {
         int var5 = this._inputData.readUnsignedByte();
         if (var2[var5] != 0) {
            if (var5 == 34) {
               return this._textBuffer.setCurrentAndReturn(var4);
            } else {
               this._finishString2(var1, var4, var5);
               return this._textBuffer.contentsAsString();
            }
         }

         int var6 = var4 + 1;
         var1[var4] = (char)((char)var5);
         if (var6 >= var3) {
            this._finishString2(var1, var6, this._inputData.readUnsignedByte());
            return this._textBuffer.contentsAsString();
         }

         var4 = var6;
      }
   }

   private final void _finishString2(char[] var1, int var2, int var3) throws IOException {
      int[] var4 = _icUTF8;
      int var5 = var1.length;
      int var6 = var3;
      var3 = var5;

      while(true) {
         while(true) {
            int var7 = var4[var6];
            byte var9 = 0;
            if (var7 == 0) {
               var5 = var3;
               var7 = var2;
               if (var2 >= var3) {
                  var1 = this._textBuffer.finishCurrentSegment();
                  var5 = var1.length;
                  var7 = 0;
               }

               var1[var7] = (char)((char)var6);
               var6 = this._inputData.readUnsignedByte();
               var2 = var7 + 1;
               var3 = var5;
            } else {
               if (var6 == 34) {
                  this._textBuffer.setCurrentLength(var2);
                  return;
               }

               var7 = var4[var6];
               if (var7 != 1) {
                  if (var7 != 2) {
                     if (var7 != 3) {
                        if (var7 != 4) {
                           if (var6 < 32) {
                              this._throwUnquotedSpace(var6, "string value");
                           } else {
                              this._reportInvalidChar(var6);
                           }
                        } else {
                           var7 = this._decodeUtf8_4(var6);
                           char[] var8 = var1;
                           var6 = var2;
                           if (var2 >= var1.length) {
                              var8 = this._textBuffer.finishCurrentSegment();
                              var3 = var8.length;
                              var6 = 0;
                           }

                           var8[var6] = (char)((char)('\ud800' | var7 >> 10));
                           var7 = var7 & 1023 | '\udc00';
                           var2 = var6 + 1;
                           var1 = var8;
                           var6 = var7;
                        }
                     } else {
                        var6 = this._decodeUtf8_3(var6);
                     }
                  } else {
                     var6 = this._decodeUtf8_2(var6);
                  }
               } else {
                  var6 = this._decodeEscaped();
               }

               if (var2 >= var1.length) {
                  var1 = this._textBuffer.finishCurrentSegment();
                  var3 = var1.length;
                  var2 = var9;
               }

               var5 = var2 + 1;
               var1[var2] = (char)((char)var6);
               var6 = this._inputData.readUnsignedByte();
               var2 = var5;
            }
         }
      }
   }

   private static int[] _growArrayBy(int[] var0, int var1) {
      return var0 == null ? new int[var1] : Arrays.copyOf(var0, var0.length + var1);
   }

   private final int _handleLeadingZeroes() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      int var2 = var1;
      if (var1 >= 48) {
         if (var1 > 57) {
            var2 = var1;
         } else {
            int var3 = var1;
            if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
               this.reportInvalidNumber("Leading zeroes not allowed");
               var3 = var1;
            }

            while(true) {
               var2 = var3;
               if (var3 != 48) {
                  break;
               }

               var3 = this._inputData.readUnsignedByte();
            }
         }
      }

      return var2;
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
                     this._matchToken("true", 1);
                     var2 = JsonToken.VALUE_TRUE;
                     this._currToken = var2;
                     return var2;
                  }
               } else {
                  this._matchToken("null", 1);
                  var2 = JsonToken.VALUE_NULL;
                  this._currToken = var2;
                  return var2;
               }
            } else {
               this._matchToken("false", 1);
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

   private final JsonToken _parseFloat(char[] var1, int var2, int var3, boolean var4, int var5) throws IOException {
      int var6 = 0;
      int var7;
      int var8;
      char[] var9;
      if (var3 == 46) {
         var1[var2] = (char)((char)var3);
         ++var2;
         var3 = 0;

         while(true) {
            var7 = this._inputData.readUnsignedByte();
            if (var7 < 48 || var7 > 57) {
               if (var3 == 0) {
                  this.reportUnexpectedNumberChar(var7, "Decimal point not followed by a digit");
               }

               var8 = var3;
               var3 = var7;
               var9 = var1;
               break;
            }

            var8 = var3 + 1;
            var9 = var1;
            var3 = var2;
            if (var2 >= var1.length) {
               var9 = this._textBuffer.finishCurrentSegment();
               var3 = 0;
            }

            var9[var3] = (char)((char)var7);
            var2 = var3 + 1;
            var1 = var9;
            var3 = var8;
         }
      } else {
         var8 = 0;
         var9 = var1;
      }

      int var10;
      label86: {
         if (var3 != 101) {
            var10 = var2;
            var7 = var3;
            if (var3 != 69) {
               break label86;
            }
         }

         var1 = var9;
         var7 = var2;
         if (var2 >= var9.length) {
            var1 = this._textBuffer.finishCurrentSegment();
            var7 = 0;
         }

         var2 = var7 + 1;
         var1[var7] = (char)((char)var3);
         var7 = this._inputData.readUnsignedByte();
         if (var7 != 45 && var7 != 43) {
            var3 = var7;
            var7 = 0;
         } else {
            var3 = var2;
            var9 = var1;
            if (var2 >= var1.length) {
               var9 = this._textBuffer.finishCurrentSegment();
               var3 = 0;
            }

            var9[var3] = (char)((char)var7);
            var2 = this._inputData.readUnsignedByte();
            var7 = 0;
            var10 = var3 + 1;
            var3 = var2;
            var2 = var10;
            var1 = var9;
         }

         while(var3 <= 57 && var3 >= 48) {
            var10 = var7 + 1;
            var9 = var1;
            var7 = var2;
            if (var2 >= var1.length) {
               var9 = this._textBuffer.finishCurrentSegment();
               var7 = 0;
            }

            var9[var7] = (char)((char)var3);
            var3 = this._inputData.readUnsignedByte();
            var2 = var7 + 1;
            var7 = var10;
            var1 = var9;
         }

         if (var7 == 0) {
            this.reportUnexpectedNumberChar(var3, "Exponent indicator not followed by a digit");
         }

         var6 = var7;
         var7 = var3;
         var10 = var2;
      }

      this._nextByte = var7;
      if (this._parsingContext.inRoot()) {
         this._verifyRootSpace();
      }

      this._textBuffer.setCurrentLength(var10);
      return this.resetFloat(var4, var5, var8, var6);
   }

   private final String _parseLongName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      var4[0] = this._quad1;
      var4[1] = var2;
      var4[2] = var3;
      var4 = _icLatin1;
      var2 = var1;
      var1 = 3;

      while(true) {
         var3 = this._inputData.readUnsignedByte();
         if (var4[var3] != 0) {
            return var3 == 34 ? this.findName(this._quadBuffer, var1, var2, 1) : this.parseEscapedName(this._quadBuffer, var1, var2, var3, 1);
         }

         var2 = var2 << 8 | var3;
         var3 = this._inputData.readUnsignedByte();
         if (var4[var3] != 0) {
            if (var3 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 2);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var2, var3, 2);
         }

         var2 = var2 << 8 | var3;
         var3 = this._inputData.readUnsignedByte();
         if (var4[var3] != 0) {
            if (var3 == 34) {
               return this.findName(this._quadBuffer, var1, var2, 3);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var2, var3, 3);
         }

         var3 |= var2 << 8;
         var2 = this._inputData.readUnsignedByte();
         if (var4[var2] != 0) {
            if (var2 == 34) {
               return this.findName(this._quadBuffer, var1, var3, 4);
            }

            return this.parseEscapedName(this._quadBuffer, var1, var3, var2, 4);
         }

         int[] var5 = this._quadBuffer;
         if (var1 >= var5.length) {
            this._quadBuffer = _growArrayBy(var5, var1);
         }

         this._quadBuffer[var1] = var3;
         ++var1;
      }
   }

   private final String _parseMediumName(int var1) throws IOException {
      int[] var2 = _icLatin1;
      int var3 = this._inputData.readUnsignedByte();
      if (var2[var3] != 0) {
         return var3 == 34 ? this.findName(this._quad1, var1, 1) : this.parseName(this._quad1, var1, var3, 1);
      } else {
         var3 |= var1 << 8;
         var1 = this._inputData.readUnsignedByte();
         if (var2[var1] != 0) {
            return var1 == 34 ? this.findName(this._quad1, var3, 2) : this.parseName(this._quad1, var3, var1, 2);
         } else {
            var3 = var3 << 8 | var1;
            var1 = this._inputData.readUnsignedByte();
            if (var2[var1] != 0) {
               return var1 == 34 ? this.findName(this._quad1, var3, 3) : this.parseName(this._quad1, var3, var1, 3);
            } else {
               var1 |= var3 << 8;
               var3 = this._inputData.readUnsignedByte();
               if (var2[var3] != 0) {
                  return var3 == 34 ? this.findName(this._quad1, var1, 4) : this.parseName(this._quad1, var1, var3, 4);
               } else {
                  return this._parseMediumName2(var3, var1);
               }
            }
         }
      }
   }

   private final String _parseMediumName2(int var1, int var2) throws IOException {
      int[] var3 = _icLatin1;
      int var4 = this._inputData.readUnsignedByte();
      if (var3[var4] != 0) {
         return var4 == 34 ? this.findName(this._quad1, var2, var1, 1) : this.parseName(this._quad1, var2, var1, var4, 1);
      } else {
         var4 |= var1 << 8;
         var1 = this._inputData.readUnsignedByte();
         if (var3[var1] != 0) {
            return var1 == 34 ? this.findName(this._quad1, var2, var4, 2) : this.parseName(this._quad1, var2, var4, var1, 2);
         } else {
            var1 |= var4 << 8;
            var4 = this._inputData.readUnsignedByte();
            if (var3[var4] != 0) {
               return var4 == 34 ? this.findName(this._quad1, var2, var1, 3) : this.parseName(this._quad1, var2, var1, var4, 3);
            } else {
               var1 = var1 << 8 | var4;
               var4 = this._inputData.readUnsignedByte();
               if (var3[var4] != 0) {
                  return var4 == 34 ? this.findName(this._quad1, var2, var1, 4) : this.parseName(this._quad1, var2, var1, var4, 4);
               } else {
                  return this._parseLongName(var4, var2, var1);
               }
            }
         }
      }
   }

   private void _reportInvalidOther(int var1) throws JsonParseException {
      StringBuilder var2 = new StringBuilder();
      var2.append("Invalid UTF-8 middle byte 0x");
      var2.append(Integer.toHexString(var1));
      this._reportError(var2.toString());
   }

   private final void _skipCComment() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();
      int var2 = this._inputData.readUnsignedByte();

      int var3;
      do {
         while(true) {
            var3 = var1[var2];
            if (var3 != 0) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 != 10 && var3 != 13) {
                           if (var3 == 42) {
                              var3 = this._inputData.readUnsignedByte();
                              var2 = var3;
                              break;
                           }

                           this._reportInvalidChar(var2);
                        } else {
                           ++this._currInputRow;
                        }
                     } else {
                        this._skipUtf8_4();
                     }
                  } else {
                     this._skipUtf8_3();
                  }
               } else {
                  this._skipUtf8_2();
               }
            }

            var2 = this._inputData.readUnsignedByte();
         }
      } while(var3 != 47);

   }

   private final int _skipColon() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         var1 = this._inputData.readUnsignedByte();
      } else {
         this._nextByte = -1;
      }

      int var2;
      if (var1 == 58) {
         var2 = this._inputData.readUnsignedByte();
         if (var2 > 32) {
            return var2 != 47 && var2 != 35 ? var2 : this._skipColon2(var2, true);
         } else {
            if (var2 != 32) {
               var1 = var2;
               if (var2 != 9) {
                  return this._skipColon2(var1, true);
               }
            }

            var2 = this._inputData.readUnsignedByte();
            var1 = var2;
            if (var2 > 32) {
               if (var2 != 47 && var2 != 35) {
                  return var2;
               } else {
                  return this._skipColon2(var2, true);
               }
            } else {
               return this._skipColon2(var1, true);
            }
         }
      } else {
         label79: {
            if (var1 != 32) {
               var2 = var1;
               if (var1 != 9) {
                  break label79;
               }
            }

            var2 = this._inputData.readUnsignedByte();
         }

         if (var2 == 58) {
            var2 = this._inputData.readUnsignedByte();
            if (var2 > 32) {
               return var2 != 47 && var2 != 35 ? var2 : this._skipColon2(var2, true);
            } else {
               if (var2 != 32) {
                  var1 = var2;
                  if (var2 != 9) {
                     return this._skipColon2(var1, true);
                  }
               }

               var2 = this._inputData.readUnsignedByte();
               var1 = var2;
               if (var2 > 32) {
                  if (var2 != 47 && var2 != 35) {
                     return var2;
                  } else {
                     return this._skipColon2(var2, true);
                  }
               } else {
                  return this._skipColon2(var1, true);
               }
            }
         } else {
            return this._skipColon2(var2, false);
         }
      }
   }

   private final int _skipColon2(int var1, boolean var2) throws IOException {
      while(true) {
         boolean var3;
         if (var1 > 32) {
            if (var1 == 47) {
               this._skipComment();
               var3 = var2;
            } else if (var1 == 35 && this._skipYAMLComment()) {
               var3 = var2;
            } else {
               if (var2) {
                  return var1;
               }

               if (var1 != 58) {
                  this._reportUnexpectedChar(var1, "was expecting a colon to separate field name and value");
               }

               var3 = true;
            }
         } else {
            label26: {
               if (var1 != 13) {
                  var3 = var2;
                  if (var1 != 10) {
                     break label26;
                  }
               }

               ++this._currInputRow;
               var3 = var2;
            }
         }

         var1 = this._inputData.readUnsignedByte();
         var2 = var3;
      }
   }

   private final void _skipComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      int var1 = this._inputData.readUnsignedByte();
      if (var1 == 47) {
         this._skipLine();
      } else if (var1 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var1, "was expecting either '*' or '/' for a comment");
      }

   }

   private final void _skipLine() throws IOException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(true) {
         while(true) {
            while(true) {
               while(true) {
                  int var2;
                  int var3;
                  do {
                     var2 = this._inputData.readUnsignedByte();
                     var3 = var1[var2];
                  } while(var3 == 0);

                  if (var3 != 2) {
                     if (var3 != 3) {
                        if (var3 != 4) {
                           if (var3 == 10 || var3 == 13) {
                              ++this._currInputRow;
                              return;
                           }

                           if (var3 != 42 && var3 < 0) {
                              this._reportInvalidChar(var2);
                           }
                        } else {
                           this._skipUtf8_4();
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
      }
   }

   private final void _skipUtf8_2() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   private final void _skipUtf8_3() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   private final void _skipUtf8_4() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

      var1 = this._inputData.readUnsignedByte();
      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255);
      }

   }

   private final int _skipWS() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         var1 = this._inputData.readUnsignedByte();
      } else {
         this._nextByte = -1;
      }

      for(; var1 <= 32; var1 = this._inputData.readUnsignedByte()) {
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }
      }

      if (var1 != 47 && var1 != 35) {
         return var1;
      } else {
         return this._skipWSComment(var1);
      }
   }

   private final int _skipWSComment(int var1) throws IOException {
      while(true) {
         if (var1 > 32) {
            if (var1 == 47) {
               this._skipComment();
            } else if (var1 != 35 || !this._skipYAMLComment()) {
               return var1;
            }
         } else if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

         var1 = this._inputData.readUnsignedByte();
      }
   }

   private final int _skipWSOrEnd() throws IOException {
      int var1 = this._nextByte;
      if (var1 < 0) {
         try {
            var1 = this._inputData.readUnsignedByte();
         } catch (EOFException var4) {
            return this._eofAsNextChar();
         }
      } else {
         this._nextByte = -1;
      }

      while(var1 <= 32) {
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
         }

         try {
            var1 = this._inputData.readUnsignedByte();
         } catch (EOFException var3) {
            return this._eofAsNextChar();
         }
      }

      if (var1 != 47 && var1 != 35) {
         return var1;
      } else {
         return this._skipWSComment(var1);
      }
   }

   private final boolean _skipYAMLComment() throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
         return false;
      } else {
         this._skipLine();
         return true;
      }
   }

   private final void _verifyRootSpace() throws IOException {
      int var1 = this._nextByte;
      if (var1 > 32) {
         this._reportMissingRootWS(var1);
      } else {
         this._nextByte = -1;
         if (var1 == 13 || var1 == 10) {
            ++this._currInputRow;
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
      var1 = pad(var1, var2);
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
      var2 = pad(var2, var3);
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
      var3 = pad(var3, var4);
      String var5 = this._symbols.findName(var1, var2, var3);
      if (var5 != null) {
         return var5;
      } else {
         int[] var6 = this._quadBuffer;
         var6[0] = var1;
         var6[1] = var2;
         var6[2] = pad(var3, var4);
         return this.addName(var6, 3, var4);
      }
   }

   private final String findName(int[] var1, int var2, int var3, int var4) throws JsonParseException {
      int[] var5 = var1;
      if (var2 >= var1.length) {
         var5 = _growArrayBy(var1, var1.length);
         this._quadBuffer = var5;
      }

      int var6 = var2 + 1;
      var5[var2] = pad(var3, var4);
      String var7 = this._symbols.findName(var5, var6);
      return var7 == null ? this.addName(var5, var6, var4) : var7;
   }

   private static final int pad(int var0, int var1) {
      if (var1 != 4) {
         var0 |= -1 << (var1 << 3);
      }

      return var0;
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
   }

   protected final byte[] _decodeBase64(Base64Variant var1) throws IOException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         while(true) {
            int var3;
            int var4;
            int var5;
            do {
               do {
                  var3 = this._inputData.readUnsignedByte();
               } while(var3 <= 32);

               var4 = var1.decodeBase64Char(var3);
               var5 = var4;
               if (var4 >= 0) {
                  break;
               }

               if (var3 == 34) {
                  return var2.toByteArray();
               }

               var4 = this._decodeBase64Escape(var1, var3, 0);
               var5 = var4;
            } while(var4 < 0);

            int var6 = this._inputData.readUnsignedByte();
            var3 = var1.decodeBase64Char(var6);
            var4 = var3;
            if (var3 < 0) {
               var4 = this._decodeBase64Escape(var1, var6, 1);
            }

            var6 = var5 << 6 | var4;
            int var7 = this._inputData.readUnsignedByte();
            var3 = var1.decodeBase64Char(var7);
            var4 = var3;
            if (var3 < 0) {
               var5 = var3;
               if (var3 != -2) {
                  if (var7 == 34) {
                     var2.append(var6 >> 4);
                     if (var1.usesPadding()) {
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var5 = this._decodeBase64Escape(var1, var7, 2);
               }

               var4 = var5;
               if (var5 == -2) {
                  var5 = this._inputData.readUnsignedByte();
                  if (!var1.usesPaddingChar(var5) && (var5 != 92 || this._decodeBase64Escape(var1, var5, 3) != -2)) {
                     StringBuilder var8 = new StringBuilder();
                     var8.append("expected padding character '");
                     var8.append(var1.getPaddingChar());
                     var8.append("'");
                     throw this.reportInvalidBase64Char(var1, var5, 3, var8.toString());
                  }

                  var2.append(var6 >> 4);
                  continue;
               }
            }

            var6 = var6 << 6 | var4;
            var7 = this._inputData.readUnsignedByte();
            var4 = var1.decodeBase64Char(var7);
            var3 = var4;
            if (var4 < 0) {
               var5 = var4;
               if (var4 != -2) {
                  if (var7 == 34) {
                     var2.appendTwoBytes(var6 >> 2);
                     if (var1.usesPadding()) {
                        this._handleBase64MissingPadding(var1);
                     }

                     return var2.toByteArray();
                  }

                  var5 = this._decodeBase64Escape(var1, var7, 3);
               }

               var3 = var5;
               if (var5 == -2) {
                  var2.appendTwoBytes(var6 >> 2);
                  continue;
               }
            }

            var2.appendThreeBytes(var6 << 6 | var3);
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

         int var3 = this._inputData.readUnsignedByte();
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255);
         }

         var3 = var1 << 6 | var3 & 63;
         var1 = var3;
         if (var4 > 1) {
            var1 = this._inputData.readUnsignedByte();
            if ((var1 & 192) != 128) {
               this._reportInvalidOther(var1 & 255);
            }

            var3 = var3 << 6 | var1 & 63;
            var1 = var3;
            if (var4 > 2) {
               var1 = this._inputData.readUnsignedByte();
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
      int var1 = this._inputData.readUnsignedByte();
      if (var1 != 34 && var1 != 47 && var1 != 92) {
         if (var1 != 98) {
            if (var1 != 102) {
               if (var1 != 110) {
                  if (var1 != 114) {
                     if (var1 != 116) {
                        if (var1 != 117) {
                           return this._handleUnrecognizedCharacterEscape((char)this._decodeCharForError(var1));
                        } else {
                           var1 = 0;

                           int var2;
                           for(var2 = 0; var1 < 4; ++var1) {
                              int var3 = this._inputData.readUnsignedByte();
                              int var4 = CharTypes.charToHex(var3);
                              if (var4 < 0) {
                                 this._reportUnexpectedChar(var3, "expected a hex-digit for character escape sequence");
                              }

                              var2 = var2 << 4 | var4;
                           }

                           return (char)var2;
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
         return (char)var1;
      }
   }

   protected void _finishString() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var2 = _icUTF8;
      int var3 = var1.length;
      int var4 = 0;

      while(true) {
         int var5 = this._inputData.readUnsignedByte();
         if (var2[var5] != 0) {
            if (var5 == 34) {
               this._textBuffer.setCurrentLength(var4);
               return;
            } else {
               this._finishString2(var1, var4, var5);
               return;
            }
         }

         int var6 = var4 + 1;
         var1[var4] = (char)((char)var5);
         if (var6 >= var3) {
            this._finishString2(var1, var6, this._inputData.readUnsignedByte());
            return;
         }

         var4 = var6;
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
      int[] var2 = _icUTF8;
      int var3 = 0;

      while(true) {
         label49:
         while(true) {
            int var4 = var1.length;
            char[] var5 = var1;
            int var6 = var3;
            if (var3 >= var1.length) {
               var5 = this._textBuffer.finishCurrentSegment();
               var4 = var5.length;
               var6 = 0;
            }

            int var7;
            do {
               var3 = this._inputData.readUnsignedByte();
               if (var3 == 39) {
                  this._textBuffer.setCurrentLength(var6);
                  return JsonToken.VALUE_STRING;
               }

               if (var2[var3] != 0) {
                  var4 = var2[var3];
                  if (var4 != 1) {
                     if (var4 != 2) {
                        if (var4 != 3) {
                           if (var4 != 4) {
                              if (var3 < 32) {
                                 this._throwUnquotedSpace(var3, "string value");
                              }

                              this._reportInvalidChar(var3);
                           } else {
                              var4 = this._decodeUtf8_4(var3);
                              var3 = var6 + 1;
                              var5[var6] = (char)((char)('\ud800' | var4 >> 10));
                              if (var3 >= var5.length) {
                                 var5 = this._textBuffer.finishCurrentSegment();
                                 var6 = 0;
                              } else {
                                 var6 = var3;
                              }

                              var3 = '\udc00' | var4 & 1023;
                           }
                        } else {
                           var3 = this._decodeUtf8_3(var3);
                        }
                     } else {
                        var3 = this._decodeUtf8_2(var3);
                     }
                  } else {
                     var3 = this._decodeEscaped();
                  }

                  var1 = var5;
                  var4 = var6;
                  if (var6 >= var5.length) {
                     var1 = this._textBuffer.finishCurrentSegment();
                     var4 = 0;
                  }

                  var1[var4] = (char)((char)var3);
                  var3 = var4 + 1;
                  continue label49;
               }

               var7 = var6 + 1;
               var5[var6] = (char)((char)var3);
               var3 = var7;
               var6 = var7;
            } while(var7 < var4);

            var1 = var5;
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

         var1 = this._inputData.readUnsignedByte();
         String var4;
         if (var1 == 78) {
            if (var2) {
               var4 = "-INF";
            } else {
               var4 = "+INF";
            }
         } else {
            var3 = var1;
            if (var1 != 110) {
               break;
            }

            if (var2) {
               var4 = "-Infinity";
            } else {
               var4 = "+Infinity";
            }
         }

         this._matchToken(var4, 3);
         if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
            double var5;
            if (var2) {
               var5 = Double.NEGATIVE_INFINITY;
            } else {
               var5 = Double.POSITIVE_INFINITY;
            }

            return this.resetAsNaN(var4, var5);
         }

         StringBuilder var7 = new StringBuilder();
         var7.append("Non-standard token '");
         var7.append(var4);
         var7.append("': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
         this._reportError(var7.toString());
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

         int var7;
         int var8;
         int[] var9;
         int var10;
         int var11;
         do {
            if (var4 < 4) {
               var7 = var4 + 1;
               var8 = var1 | var6 << 8;
               var9 = var3;
               var10 = var5;
            } else {
               var9 = var3;
               if (var5 >= var3.length) {
                  var9 = _growArrayBy(var3, var3.length);
                  this._quadBuffer = var9;
               }

               var9[var5] = var6;
               var10 = var5 + 1;
               var7 = 1;
               var8 = var1;
            }

            var11 = this._inputData.readUnsignedByte();
            var3 = var9;
            var4 = var7;
            var5 = var10;
            var6 = var8;
            var1 = var11;
         } while(var2[var11] == 0);

         this._nextByte = var11;
         var3 = var9;
         var1 = var10;
         if (var7 > 0) {
            var3 = var9;
            if (var10 >= var9.length) {
               var3 = _growArrayBy(var9, var9.length);
               this._quadBuffer = var3;
            }

            var3[var10] = var8;
            var1 = var10 + 1;
         }

         String var12 = this._symbols.findName(var3, var1);
         String var13 = var12;
         if (var12 == null) {
            var13 = this.addName(var3, var1, var7);
         }

         return var13;
      }
   }

   protected JsonToken _handleUnexpectedValue(int var1) throws IOException {
      label53: {
         if (var1 != 39) {
            if (var1 == 73) {
               this._matchToken("Infinity", 1);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
               }

               this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               break label53;
            }

            if (var1 == 78) {
               this._matchToken("NaN", 1);
               if ((this._features & FEAT_MASK_NON_NUM_NUMBERS) != 0) {
                  return this.resetAsNaN("NaN", Double.NaN);
               }

               this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
               break label53;
            }

            label47: {
               if (var1 != 93) {
                  if (var1 == 125) {
                     break label47;
                  }

                  if (var1 == 43) {
                     return this._handleInvalidNumberStart(this._inputData.readUnsignedByte(), false);
                  }

                  if (var1 != 44) {
                     break label53;
                  }
               } else if (!this._parsingContext.inArray()) {
                  break label53;
               }

               if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
                  this._nextByte = var1;
                  return JsonToken.VALUE_NULL;
               }
            }

            this._reportUnexpectedChar(var1, "expected a value");
         }

         if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._handleApos();
         }
      }

      StringBuilder var2;
      if (Character.isJavaIdentifierStart(var1)) {
         var2 = new StringBuilder();
         var2.append("");
         var2.append((char)var1);
         this._reportInvalidToken(var1, var2.toString(), this._validJsonTokenList());
      }

      var2 = new StringBuilder();
      var2.append("expected a valid value ");
      var2.append(this._validJsonValueList());
      this._reportUnexpectedChar(var1, var2.toString());
      return null;
   }

   protected final void _matchToken(String var1, int var2) throws IOException {
      int var3 = var1.length();

      int var4;
      do {
         var4 = this._inputData.readUnsignedByte();
         if (var4 != var1.charAt(var2)) {
            this._reportInvalidToken(var4, var1.substring(0, var2));
         }

         var4 = var2 + 1;
         var2 = var4;
      } while(var4 < var3);

      var2 = this._inputData.readUnsignedByte();
      if (var2 >= 48 && var2 != 93 && var2 != 125) {
         this._checkMatchEnd(var1, var4, var2);
      }

      this._nextByte = var2;
   }

   protected String _parseAposName() throws IOException {
      int var1 = this._inputData.readUnsignedByte();
      if (var1 == 39) {
         return "";
      } else {
         int[] var2 = this._quadBuffer;
         int[] var3 = _icLatin1;
         int var4 = 0;
         int var5 = 0;

         int var6;
         int[] var7;
         for(var6 = 0; var1 != 39; var1 = this._inputData.readUnsignedByte()) {
            int var8 = var1;
            var7 = var2;
            int var9 = var4;
            int var10 = var5;
            int var11 = var6;
            if (var1 != 34) {
               var8 = var1;
               var7 = var2;
               var9 = var4;
               var10 = var5;
               var11 = var6;
               if (var3[var1] != 0) {
                  int var12;
                  if (var1 != 92) {
                     this._throwUnquotedSpace(var1, "name");
                     var12 = var1;
                  } else {
                     var12 = this._decodeEscaped();
                  }

                  var8 = var12;
                  var7 = var2;
                  var9 = var4;
                  var10 = var5;
                  var11 = var6;
                  if (var12 > 127) {
                     var7 = var2;
                     var8 = var4;
                     var1 = var5;
                     var10 = var6;
                     if (var4 >= 4) {
                        var7 = var2;
                        if (var5 >= var2.length) {
                           var7 = _growArrayBy(var2, var2.length);
                           this._quadBuffer = var7;
                        }

                        var7[var5] = var6;
                        var1 = var5 + 1;
                        var8 = 0;
                        var10 = 0;
                     }

                     if (var12 < 2048) {
                        var4 = var10 << 8 | var12 >> 6 | 192;
                        var5 = var8 + 1;
                        var2 = var7;
                     } else {
                        var10 = var10 << 8 | var12 >> 12 | 224;
                        ++var8;
                        var2 = var7;
                        var6 = var8;
                        var5 = var1;
                        var4 = var10;
                        if (var8 >= 4) {
                           var2 = var7;
                           if (var1 >= var7.length) {
                              var2 = _growArrayBy(var7, var7.length);
                              this._quadBuffer = var2;
                           }

                           var2[var1] = var10;
                           var5 = var1 + 1;
                           var6 = 0;
                           var4 = 0;
                        }

                        var4 = var4 << 8 | var12 >> 6 & 63 | 128;
                        ++var6;
                        var1 = var5;
                        var5 = var6;
                     }

                     var8 = var12 & 63 | 128;
                     var11 = var4;
                     var10 = var1;
                     var9 = var5;
                     var7 = var2;
                  }
               }
            }

            if (var9 < 4) {
               var4 = var9 + 1;
               var6 = var8 | var11 << 8;
               var2 = var7;
               var5 = var10;
            } else {
               var2 = var7;
               if (var10 >= var7.length) {
                  var2 = _growArrayBy(var7, var7.length);
                  this._quadBuffer = var2;
               }

               var2[var10] = var11;
               var6 = var8;
               var5 = var10 + 1;
               var4 = 1;
            }
         }

         var7 = var2;
         var1 = var5;
         if (var4 > 0) {
            var7 = var2;
            if (var5 >= var2.length) {
               var7 = _growArrayBy(var2, var2.length);
               this._quadBuffer = var7;
            }

            var7[var5] = pad(var6, var4);
            var1 = var5 + 1;
         }

         String var13 = this._symbols.findName(var7, var1);
         String var14 = var13;
         if (var13 == null) {
            var14 = this.addName(var7, var1, var4);
         }

         return var14;
      }
   }

   protected final String _parseName(int var1) throws IOException {
      if (var1 != 34) {
         return this._handleOddName(var1);
      } else {
         int[] var2 = _icLatin1;
         int var3 = this._inputData.readUnsignedByte();
         if (var2[var3] == 0) {
            var1 = this._inputData.readUnsignedByte();
            if (var2[var1] == 0) {
               var3 = var3 << 8 | var1;
               var1 = this._inputData.readUnsignedByte();
               if (var2[var1] == 0) {
                  var3 = var3 << 8 | var1;
                  var1 = this._inputData.readUnsignedByte();
                  if (var2[var1] == 0) {
                     var3 = var3 << 8 | var1;
                     var1 = this._inputData.readUnsignedByte();
                     if (var2[var1] == 0) {
                        this._quad1 = var3;
                        return this._parseMediumName(var1);
                     } else {
                        return var1 == 34 ? this.findName(var3, 4) : this.parseName(var3, var1, 4);
                     }
                  } else {
                     return var1 == 34 ? this.findName(var3, 3) : this.parseName(var3, var1, 3);
                  }
               } else {
                  return var1 == 34 ? this.findName(var3, 2) : this.parseName(var3, var1, 2);
               }
            } else {
               return var1 == 34 ? this.findName(var3, 1) : this.parseName(var3, var1, 1);
            }
         } else {
            return var3 == 34 ? "" : this.parseName(0, var3, 0);
         }
      }
   }

   protected JsonToken _parseNegNumber() throws IOException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      var1[0] = (char)45;
      int var2 = this._inputData.readUnsignedByte();
      var1[1] = (char)((char)var2);
      if (var2 <= 48) {
         if (var2 != 48) {
            return this._handleInvalidNumberStart(var2, true);
         }

         var2 = this._handleLeadingZeroes();
      } else {
         if (var2 > 57) {
            return this._handleInvalidNumberStart(var2, true);
         }

         var2 = this._inputData.readUnsignedByte();
      }

      int var3 = 2;

      int var4;
      for(var4 = 1; var2 <= 57 && var2 >= 48; ++var3) {
         ++var4;
         var1[var3] = (char)((char)var2);
         var2 = this._inputData.readUnsignedByte();
      }

      if (var2 != 46 && var2 != 101 && var2 != 69) {
         this._textBuffer.setCurrentLength(var3);
         this._nextByte = var2;
         if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
         }

         return this.resetInt(true, var4);
      } else {
         return this._parseFloat(var1, var3, var2, true, var4);
      }
   }

   protected JsonToken _parsePosNumber(int var1) throws IOException {
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      byte var3 = 1;
      int var4;
      if (var1 == 48) {
         var4 = this._handleLeadingZeroes();
         if (var4 <= 57 && var4 >= 48) {
            var1 = 0;
         } else {
            var2[0] = (char)48;
            var1 = var3;
         }
      } else {
         var2[0] = (char)((char)var1);
         var4 = this._inputData.readUnsignedByte();
         var1 = var3;
      }

      int var5;
      for(var5 = var1; var4 <= 57 && var4 >= 48; ++var1) {
         ++var5;
         var2[var1] = (char)((char)var4);
         var4 = this._inputData.readUnsignedByte();
      }

      if (var4 != 46 && var4 != 101 && var4 != 69) {
         this._textBuffer.setCurrentLength(var1);
         if (this._parsingContext.inRoot()) {
            this._verifyRootSpace();
         } else {
            this._nextByte = var4;
         }

         return this.resetInt(false, var5);
      } else {
         return this._parseFloat(var2, var1, var4, false, var5);
      }
   }

   protected int _readBinary(Base64Variant var1, OutputStream var2, byte[] var3) throws IOException {
      int var4 = var3.length;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         int var7;
         do {
            var7 = this._inputData.readUnsignedByte();
         } while(var7 <= 32);

         int var8;
         label89: {
            int var10;
            label95: {
               label96: {
                  var8 = var1.decodeBase64Char(var7);
                  int var9 = var8;
                  if (var8 < 0) {
                     if (var7 == 34) {
                        break label96;
                     }

                     var8 = this._decodeBase64Escape(var1, var7, 0);
                     var9 = var8;
                     if (var8 < 0) {
                        continue;
                     }
                  }

                  var7 = var5;
                  var8 = var6;
                  if (var5 > var4 - 3) {
                     var8 = var6 + var5;
                     var2.write(var3, 0, var5);
                     var7 = 0;
                  }

                  var10 = this._inputData.readUnsignedByte();
                  var6 = var1.decodeBase64Char(var10);
                  var5 = var6;
                  if (var6 < 0) {
                     var5 = this._decodeBase64Escape(var1, var10, 1);
                  }

                  var10 = var9 << 6 | var5;
                  int var11 = this._inputData.readUnsignedByte();
                  var9 = var1.decodeBase64Char(var11);
                  var6 = var9;
                  if (var9 < 0) {
                     var5 = var9;
                     if (var9 != -2) {
                        if (var11 == 34) {
                           var3[var7] = (byte)((byte)(var10 >> 4));
                           if (var1.usesPadding()) {
                              this._handleBase64MissingPadding(var1);
                           }

                           var5 = var7 + 1;
                           var6 = var8;
                           break label96;
                        }

                        var5 = this._decodeBase64Escape(var1, var11, 2);
                     }

                     var6 = var5;
                     if (var5 == -2) {
                        var5 = this._inputData.readUnsignedByte();
                        if (var1.usesPaddingChar(var5) || var5 == 92 && this._decodeBase64Escape(var1, var5, 3) == -2) {
                           var5 = var7 + 1;
                           var3[var7] = (byte)((byte)(var10 >> 4));
                           break label89;
                        }

                        StringBuilder var12 = new StringBuilder();
                        var12.append("expected padding character '");
                        var12.append(var1.getPaddingChar());
                        var12.append("'");
                        throw this.reportInvalidBase64Char(var1, var5, 3, var12.toString());
                     }
                  }

                  var10 = var10 << 6 | var6;
                  var11 = this._inputData.readUnsignedByte();
                  var9 = var1.decodeBase64Char(var11);
                  var6 = var9;
                  if (var9 >= 0) {
                     break label95;
                  }

                  var5 = var9;
                  if (var9 != -2) {
                     if (var11 == 34) {
                        var5 = var10 >> 2;
                        var6 = var7 + 1;
                        var3[var7] = (byte)((byte)(var5 >> 8));
                        var7 = var6 + 1;
                        var3[var6] = (byte)((byte)var5);
                        var5 = var7;
                        var6 = var8;
                        if (var1.usesPadding()) {
                           this._handleBase64MissingPadding(var1);
                           var6 = var8;
                           var5 = var7;
                        }
                        break label96;
                     }

                     var5 = this._decodeBase64Escape(var1, var11, 3);
                  }

                  var6 = var5;
                  if (var5 == -2) {
                     var9 = var10 >> 2;
                     var6 = var7 + 1;
                     var3[var7] = (byte)((byte)(var9 >> 8));
                     var5 = var6 + 1;
                     var3[var6] = (byte)((byte)var9);
                     var6 = var8;
                     continue;
                  }
                  break label95;
               }

               this._tokenIncomplete = false;
               var8 = var6;
               if (var5 > 0) {
                  var8 = var6 + var5;
                  var2.write(var3, 0, var5);
               }

               return var8;
            }

            var6 |= var10 << 6;
            var5 = var7 + 1;
            var3[var7] = (byte)((byte)(var6 >> 16));
            var7 = var5 + 1;
            var3[var5] = (byte)((byte)(var6 >> 8));
            var5 = var7 + 1;
            var3[var7] = (byte)((byte)var6);
         }

         var6 = var8;
      }
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

   protected void _reportInvalidToken(int var1, String var2) throws IOException {
      this._reportInvalidToken(var1, var2, this._validJsonTokenList());
   }

   protected void _reportInvalidToken(int var1, String var2, String var3) throws IOException {
      StringBuilder var6 = new StringBuilder(var2);

      while(true) {
         char var4 = (char)this._decodeCharForError(var1);
         if (!Character.isJavaIdentifierPart(var4)) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Unrecognized token '");
            var5.append(var6.toString());
            var5.append("': was expecting ");
            var5.append(var3);
            this._reportError(var5.toString());
            return;
         }

         var6.append(var4);
         var1 = this._inputData.readUnsignedByte();
      }
   }

   protected void _skipString() throws IOException {
      this._tokenIncomplete = false;
      int[] var1 = _icUTF8;

      while(true) {
         int var2;
         do {
            var2 = this._inputData.readUnsignedByte();
         } while(var1[var2] == 0);

         if (var2 == 34) {
            return;
         }

         int var3 = var1[var2];
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var2 < 32) {
                        this._throwUnquotedSpace(var2, "string value");
                     } else {
                        this._reportInvalidChar(var2);
                     }
                  } else {
                     this._skipUtf8_4();
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
      return new JsonLocation(this._getSourceReference(), -1L, -1L, this._currInputRow, -1);
   }

   public Object getInputSource() {
      return this._inputData;
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
      if (this._currToken == JsonToken.VALUE_STRING) {
         if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
         }

         return this._textBuffer.size();
      } else if (this._currToken == JsonToken.FIELD_NAME) {
         return this._parsingContext.getCurrentName().length();
      } else if (this._currToken != null) {
         return this._currToken.isNumeric() ? this._textBuffer.size() : this._currToken.asCharArray().length;
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
      return new JsonLocation(this._getSourceReference(), -1L, -1L, this._tokenInputRow, -1);
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

         int var1 = this._skipWS();
         this._binaryValue = null;
         this._tokenInputRow = this._currInputRow;
         if (var1 != 93 && var1 != 125) {
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
                  label75: {
                     if (var1 != 93) {
                        var2 = var1;
                        if (var1 != 125) {
                           break label75;
                        }
                     }

                     this._closeScope(var1);
                     return null;
                  }
               }
            }

            if (!this._parsingContext.inObject()) {
               this._nextTokenNotInObject(var2);
               return null;
            } else {
               String var4 = this._parseName(var2);
               this._parsingContext.setCurrentName(var4);
               this._currToken = JsonToken.FIELD_NAME;
               var2 = this._skipColon();
               if (var2 == 34) {
                  this._tokenIncomplete = true;
                  this._nextToken = JsonToken.VALUE_STRING;
                  return var4;
               } else {
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
                                 this._matchToken("true", 1);
                                 var5 = JsonToken.VALUE_TRUE;
                              }
                           } else {
                              this._matchToken("null", 1);
                              var5 = JsonToken.VALUE_NULL;
                           }
                        } else {
                           this._matchToken("false", 1);
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
         } else {
            this._closeScope(var1);
            return null;
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
      if (this._closed) {
         return null;
      } else if (this._currToken == JsonToken.FIELD_NAME) {
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
            this._tokenInputRow = this._currInputRow;
            if (var1 != 93 && var1 != 125) {
               label78: {
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
                           break label78;
                        }

                        var2 = var1;
                        if (var1 == 125) {
                           break label78;
                        }
                     }
                  }

                  if (!this._parsingContext.inObject()) {
                     return this._nextTokenNotInObject(var2);
                  }

                  String var4 = this._parseName(var2);
                  this._parsingContext.setCurrentName(var4);
                  this._currToken = JsonToken.FIELD_NAME;
                  var2 = this._skipColon();
                  if (var2 == 34) {
                     this._tokenIncomplete = true;
                     this._nextToken = JsonToken.VALUE_STRING;
                     return this._currToken;
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
                                 this._matchToken("true", 1);
                                 var5 = JsonToken.VALUE_TRUE;
                              }
                           } else {
                              this._matchToken("null", 1);
                              var5 = JsonToken.VALUE_NULL;
                           }
                        } else {
                           this._matchToken("false", 1);
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

               this._closeScope(var1);
               return this._currToken;
            } else {
               this._closeScope(var1);
               return this._currToken;
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
                     var8 = _growArrayBy(var1, var1.length);
                     this._quadBuffer = var8;
                  }

                  var8[var2] = pad(var3, var5);
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
                     var8 = _growArrayBy(var1, var1.length);
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
                        var1 = _growArrayBy(var8, var8.length);
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
               var1 = _growArrayBy(var8, var8.length);
               this._quadBuffer = var1;
            }

            var1[var9] = var10;
            var3 = var4;
            var2 = var9 + 1;
            var5 = 1;
         }

         var7 = this._inputData.readUnsignedByte();
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
      return 0;
   }

   public void setCodec(ObjectCodec var1) {
      this._objectCodec = var1;
   }
}
