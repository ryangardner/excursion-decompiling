package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.io.OutputStream;

public class NonBlockingJsonParser extends NonBlockingJsonParserBase implements ByteArrayFeeder {
   private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS;
   private static final int FEAT_MASK_ALLOW_MISSING;
   private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES;
   private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES;
   private static final int FEAT_MASK_ALLOW_YAML_COMMENTS;
   private static final int FEAT_MASK_LEADING_ZEROS;
   private static final int FEAT_MASK_TRAILING_COMMA;
   protected static final int[] _icLatin1;
   private static final int[] _icUTF8;
   protected byte[] _inputBuffer;
   protected int _origBufferLen;

   static {
      FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
      FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
      FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
      FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
      FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
      FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
      FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
      _icUTF8 = CharTypes.getInputCodeUtf8();
      _icLatin1 = CharTypes.getInputCodeLatin1();
   }

   public NonBlockingJsonParser(IOContext var1, int var2, ByteQuadsCanonicalizer var3) {
      super(var1, var2, var3);
      this._inputBuffer = NO_BYTES;
   }

   private final int _decodeCharEscape() throws IOException {
      return this._inputEnd - this._inputPtr < 5 ? this._decodeSplitEscaped(0, -1) : this._decodeFastCharEscape();
   }

   private final int _decodeFastCharEscape() throws IOException {
      byte[] var1 = this._inputBuffer;
      int var2 = this._inputPtr++;
      byte var7 = var1[var2];
      if (var7 != 34 && var7 != 47 && var7 != 92) {
         if (var7 != 98) {
            if (var7 != 102) {
               if (var7 != 110) {
                  if (var7 != 114) {
                     if (var7 != 116) {
                        if (var7 != 117) {
                           return this._handleUnrecognizedCharacterEscape((char)var7);
                        } else {
                           var1 = this._inputBuffer;
                           var2 = this._inputPtr++;
                           var7 = var1[var2];
                           int var3 = CharTypes.charToHex(var7);
                           if (var3 >= 0) {
                              var1 = this._inputBuffer;
                              var2 = this._inputPtr++;
                              var7 = var1[var2];
                              int var4 = CharTypes.charToHex(var7);
                              if (var4 >= 0) {
                                 var1 = this._inputBuffer;
                                 var2 = this._inputPtr++;
                                 var7 = var1[var2];
                                 int var5 = CharTypes.charToHex(var7);
                                 if (var5 >= 0) {
                                    var1 = this._inputBuffer;
                                    var2 = this._inputPtr++;
                                    var7 = var1[var2];
                                    int var6 = CharTypes.charToHex(var7);
                                    if (var6 >= 0) {
                                       return ((var3 << 4 | var4) << 4 | var5) << 4 | var6;
                                    }
                                 }
                              }
                           }

                           this._reportUnexpectedChar(var7 & 255, "expected a hex-digit for character escape sequence");
                           return -1;
                        }
                     } else {
                        return 9;
                     }
                  } else {
                     return 13;
                  }
               } else {
                  return 10;
               }
            } else {
               return 12;
            }
         } else {
            return 8;
         }
      } else {
         return (char)var7;
      }
   }

   private int _decodeSplitEscaped(int var1, int var2) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._quoted32 = var1;
         this._quotedDigits = var2;
         return -1;
      } else {
         byte[] var3 = this._inputBuffer;
         int var4 = this._inputPtr++;
         byte var5 = var3[var4];
         byte var8 = var5;
         int var6 = var1;
         int var7 = var2;
         if (var2 == -1) {
            if (var5 == 34 || var5 == 47 || var5 == 92) {
               return var5;
            }

            if (var5 == 98) {
               return 8;
            }

            if (var5 == 102) {
               return 12;
            }

            if (var5 == 110) {
               return 10;
            }

            if (var5 == 114) {
               return 13;
            }

            if (var5 == 116) {
               return 9;
            }

            if (var5 != 117) {
               return this._handleUnrecognizedCharacterEscape((char)var5);
            }

            if (this._inputPtr >= this._inputEnd) {
               this._quotedDigits = 0;
               this._quoted32 = 0;
               return -1;
            }

            var3 = this._inputBuffer;
            var2 = this._inputPtr++;
            var8 = var3[var2];
            var7 = 0;
            var6 = var1;
         }

         while(true) {
            var1 = var8 & 255;
            var2 = CharTypes.charToHex(var1);
            if (var2 < 0) {
               this._reportUnexpectedChar(var1 & 255, "expected a hex-digit for character escape sequence");
            }

            var6 = var6 << 4 | var2;
            ++var7;
            if (var7 == 4) {
               return var6;
            }

            if (this._inputPtr >= this._inputEnd) {
               this._quotedDigits = var7;
               this._quoted32 = var6;
               return -1;
            }

            var3 = this._inputBuffer;
            var1 = this._inputPtr++;
            var8 = var3[var1];
         }
      }
   }

   private final boolean _decodeSplitMultiByte(int var1, int var2, boolean var3) throws IOException {
      if (var2 != 1) {
         byte[] var4;
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var1 < 32) {
                     this._throwUnquotedSpace(var1, "string value");
                  } else {
                     this._reportInvalidChar(var1);
                  }

                  this._textBuffer.append((char)var1);
                  return true;
               } else {
                  var1 &= 7;
                  if (var3) {
                     var4 = this._inputBuffer;
                     var2 = this._inputPtr++;
                     return this._decodeSplitUTF8_4(var1, 1, var4[var2]);
                  } else {
                     this._pending32 = var1;
                     this._pendingBytes = 1;
                     this._minorState = 44;
                     return false;
                  }
               }
            } else {
               var1 &= 15;
               if (var3) {
                  var4 = this._inputBuffer;
                  var2 = this._inputPtr++;
                  return this._decodeSplitUTF8_3(var1, 1, var4[var2]);
               } else {
                  this._minorState = 43;
                  this._pending32 = var1;
                  this._pendingBytes = 1;
                  return false;
               }
            }
         } else if (var3) {
            var4 = this._inputBuffer;
            var2 = this._inputPtr++;
            var1 = this._decodeUTF8_2(var1, var4[var2]);
            this._textBuffer.append((char)var1);
            return true;
         } else {
            this._minorState = 42;
            this._pending32 = var1;
            return false;
         }
      } else {
         var1 = this._decodeSplitEscaped(0, -1);
         if (var1 < 0) {
            this._minorState = 41;
            return false;
         } else {
            this._textBuffer.append((char)var1);
            return true;
         }
      }
   }

   private final boolean _decodeSplitUTF8_3(int var1, int var2, int var3) throws IOException {
      int var4 = var1;
      int var5 = var3;
      if (var2 == 1) {
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255, this._inputPtr);
         }

         var4 = var1 << 6 | var3 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 43;
            this._pending32 = var4;
            this._pendingBytes = 2;
            return false;
         }

         byte[] var6 = this._inputBuffer;
         var1 = this._inputPtr++;
         var5 = var6[var1];
      }

      if ((var5 & 192) != 128) {
         this._reportInvalidOther(var5 & 255, this._inputPtr);
      }

      this._textBuffer.append((char)(var4 << 6 | var5 & 63));
      return true;
   }

   private final boolean _decodeSplitUTF8_4(int var1, int var2, int var3) throws IOException {
      int var4 = var1;
      int var5 = var2;
      int var6 = var3;
      byte[] var7;
      if (var2 == 1) {
         if ((var3 & 192) != 128) {
            this._reportInvalidOther(var3 & 255, this._inputPtr);
         }

         var4 = var1 << 6 | var3 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 44;
            this._pending32 = var4;
            this._pendingBytes = 2;
            return false;
         }

         var7 = this._inputBuffer;
         var1 = this._inputPtr++;
         var6 = var7[var1];
         var5 = 2;
      }

      var2 = var4;
      var1 = var6;
      if (var5 == 2) {
         if ((var6 & 192) != 128) {
            this._reportInvalidOther(var6 & 255, this._inputPtr);
         }

         var2 = var4 << 6 | var6 & 63;
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 44;
            this._pending32 = var2;
            this._pendingBytes = 3;
            return false;
         }

         var7 = this._inputBuffer;
         var1 = this._inputPtr++;
         var1 = var7[var1];
      }

      if ((var1 & 192) != 128) {
         this._reportInvalidOther(var1 & 255, this._inputPtr);
      }

      var1 = (var2 << 6 | var1 & 63) - 65536;
      this._textBuffer.append((char)('\ud800' | var1 >> 10));
      this._textBuffer.append((char)(var1 & 1023 | '\udc00'));
      return true;
   }

   private final int _decodeUTF8_2(int var1, int var2) throws IOException {
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      return (var1 & 31) << 6 | var2 & 63;
   }

   private final int _decodeUTF8_3(int var1, int var2, int var3) throws IOException {
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      return ((var1 & 15) << 6 | var2 & 63) << 6 | var3 & 63;
   }

   private final int _decodeUTF8_4(int var1, int var2, int var3, int var4) throws IOException {
      if ((var2 & 192) != 128) {
         this._reportInvalidOther(var2 & 255, this._inputPtr);
      }

      if ((var3 & 192) != 128) {
         this._reportInvalidOther(var3 & 255, this._inputPtr);
      }

      if ((var4 & 192) != 128) {
         this._reportInvalidOther(var4 & 255, this._inputPtr);
      }

      return ((((var1 & 7) << 6 | var2 & 63) << 6 | var3 & 63) << 6 | var4 & 63) - 65536;
   }

   private final String _fastParseName() throws IOException {
      byte[] var1 = this._inputBuffer;
      int[] var2 = _icLatin1;
      int var3 = this._inputPtr;
      int var4 = var3 + 1;
      int var5 = var1[var3] & 255;
      if (var2[var5] == 0) {
         var3 = var4 + 1;
         var4 = var1[var4] & 255;
         if (var2[var4] == 0) {
            var4 |= var5 << 8;
            var5 = var3 + 1;
            var3 = var1[var3] & 255;
            if (var2[var3] == 0) {
               var4 = var4 << 8 | var3;
               var3 = var5 + 1;
               var5 = var1[var5] & 255;
               if (var2[var5] == 0) {
                  var5 |= var4 << 8;
                  var4 = var3 + 1;
                  var3 = var1[var3] & 255;
                  if (var2[var3] == 0) {
                     this._quad1 = var5;
                     return this._parseMediumName(var4, var3);
                  } else if (var3 == 34) {
                     this._inputPtr = var4;
                     return this._findName(var5, 4);
                  } else {
                     return null;
                  }
               } else if (var5 == 34) {
                  this._inputPtr = var3;
                  return this._findName(var4, 3);
               } else {
                  return null;
               }
            } else if (var3 == 34) {
               this._inputPtr = var5;
               return this._findName(var4, 2);
            } else {
               return null;
            }
         } else if (var4 == 34) {
            this._inputPtr = var3;
            return this._findName(var5, 1);
         } else {
            return null;
         }
      } else if (var5 == 34) {
         this._inputPtr = var4;
         return "";
      } else {
         return null;
      }
   }

   private JsonToken _finishAposName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = _icLatin1;

      JsonToken var14;
      while(this._inputPtr < this._inputEnd) {
         byte[] var6 = this._inputBuffer;
         int var7 = this._inputPtr++;
         int var8 = var6[var7] & 255;
         int[] var13;
         if (var8 == 39) {
            if (var3 > 0) {
               var13 = var4;
               if (var1 >= var4.length) {
                  var13 = growArrayBy(var4, var4.length);
                  this._quadBuffer = var13;
               }

               var13[var1] = _padLastQuad(var2, var3);
               var2 = var1 + 1;
            } else {
               var13 = var4;
               var2 = var1;
               if (var1 == 0) {
                  return this._fieldComplete("");
               }
            }

            String var12 = this._symbols.findName(var13, var2);
            String var15 = var12;
            if (var12 == null) {
               var15 = this._addName(var13, var2, var3);
            }

            return this._fieldComplete(var15);
         }

         var13 = var4;
         var7 = var8;
         int var9 = var1;
         int var10 = var2;
         int var11 = var3;
         if (var8 != 34) {
            var13 = var4;
            var7 = var8;
            var9 = var1;
            var10 = var2;
            var11 = var3;
            if (var5[var8] != 0) {
               if (var8 != 92) {
                  this._throwUnquotedSpace(var8, "name");
               } else {
                  var7 = this._decodeCharEscape();
                  var8 = var7;
                  if (var7 < 0) {
                     this._minorState = 8;
                     this._minorStateAfterSplit = 9;
                     this._quadLength = var1;
                     this._pending32 = var2;
                     this._pendingBytes = var3;
                     var14 = JsonToken.NOT_AVAILABLE;
                     this._currToken = var14;
                     return var14;
                  }
               }

               var13 = var4;
               var7 = var8;
               var9 = var1;
               var10 = var2;
               var11 = var3;
               if (var8 > 127) {
                  byte var16 = 0;
                  var13 = var4;
                  var7 = var1;
                  var10 = var2;
                  var9 = var3;
                  if (var3 >= 4) {
                     var13 = var4;
                     if (var1 >= var4.length) {
                        var13 = growArrayBy(var4, var4.length);
                        this._quadBuffer = var13;
                     }

                     var13[var1] = var2;
                     var7 = var1 + 1;
                     var10 = 0;
                     var9 = 0;
                  }

                  if (var8 < 2048) {
                     var1 = var10 << 8 | var8 >> 6 | 192;
                     var2 = var9 + 1;
                  } else {
                     var2 = var10 << 8 | var8 >> 12 | 224;
                     var1 = var9 + 1;
                     if (var1 >= 4) {
                        var4 = var13;
                        if (var7 >= var13.length) {
                           var4 = growArrayBy(var13, var13.length);
                           this._quadBuffer = var4;
                        }

                        var4[var7] = var2;
                        ++var7;
                        var1 = 0;
                        var2 = var16;
                     } else {
                        var4 = var13;
                     }

                     var3 = var2 << 8 | var8 >> 6 & 63 | 128;
                     var2 = var1 + 1;
                     var1 = var3;
                     var13 = var4;
                  }

                  var3 = var8 & 63 | 128;
                  var11 = var2;
                  var10 = var1;
                  var9 = var7;
                  var7 = var3;
               }
            }
         }

         if (var11 < 4) {
            var3 = var11 + 1;
            var2 = var10 << 8 | var7;
            var4 = var13;
            var1 = var9;
         } else {
            var4 = var13;
            if (var9 >= var13.length) {
               var4 = growArrayBy(var13, var13.length);
               this._quadBuffer = var4;
            }

            var4[var9] = var10;
            var1 = var9 + 1;
            var2 = var7;
            var3 = 1;
         }
      }

      this._quadLength = var1;
      this._pending32 = var2;
      this._pendingBytes = var3;
      this._minorState = 9;
      var14 = JsonToken.NOT_AVAILABLE;
      this._currToken = var14;
      return var14;
   }

   private final JsonToken _finishAposString() throws IOException {
      int[] var1 = _icUTF8;
      byte[] var2 = this._inputBuffer;
      char[] var3 = this._textBuffer.getBufferWithoutReset();
      int var4 = this._textBuffer.getCurrentSegmentSize();
      int var5 = this._inputPtr;
      int var6 = this._inputEnd;

      while(true) {
         JsonToken var15;
         while(var5 < this._inputEnd) {
            int var8 = var3.length;
            byte var9 = 0;
            boolean var10 = false;
            char[] var7 = var3;
            int var11 = var4;
            if (var4 >= var8) {
               var7 = this._textBuffer.finishCurrentSegment();
               var11 = 0;
            }

            int var12 = Math.min(this._inputEnd, var7.length - var11 + var5);
            var8 = var5;

            while(true) {
               var3 = var7;
               var4 = var11;
               var5 = var8;
               if (var8 >= var12) {
                  break;
               }

               var4 = var8 + 1;
               var5 = var2[var8] & 255;
               if (var1[var5] != 0 && var5 != 34) {
                  if (var4 >= var6 - 5) {
                     this._inputPtr = var4;
                     this._textBuffer.setCurrentLength(var11);
                     var11 = var1[var5];
                     if (var4 < this._inputEnd) {
                        var10 = true;
                     }

                     if (!this._decodeSplitMultiByte(var5, var11, var10)) {
                        this._minorStateAfterSplit = 45;
                        var15 = JsonToken.NOT_AVAILABLE;
                        this._currToken = var15;
                        return var15;
                     }

                     var3 = this._textBuffer.getBufferWithoutReset();
                     var4 = this._textBuffer.getCurrentSegmentSize();
                     var5 = this._inputPtr;
                  } else {
                     var8 = var1[var5];
                     if (var8 != 1) {
                        if (var8 != 2) {
                           byte[] var13;
                           if (var8 != 3) {
                              if (var8 != 4) {
                                 if (var5 < 32) {
                                    this._throwUnquotedSpace(var5, "string value");
                                 } else {
                                    this._reportInvalidChar(var5);
                                 }
                              } else {
                                 var13 = this._inputBuffer;
                                 var12 = var4 + 1;
                                 byte var14 = var13[var4];
                                 var8 = var12 + 1;
                                 var5 = this._decodeUTF8_4(var5, var14, var13[var12], var13[var8]);
                                 var4 = var11 + 1;
                                 var7[var11] = (char)((char)('\ud800' | var5 >> 10));
                                 if (var4 >= var7.length) {
                                    var7 = this._textBuffer.finishCurrentSegment();
                                    var11 = 0;
                                 } else {
                                    var11 = var4;
                                 }

                                 var5 = var5 & 1023 | '\udc00';
                                 var4 = var8 + 1;
                              }
                           } else {
                              var13 = this._inputBuffer;
                              var8 = var4 + 1;
                              var5 = this._decodeUTF8_3(var5, var13[var4], var13[var8]);
                              var4 = var8 + 1;
                           }
                        } else {
                           var5 = this._decodeUTF8_2(var5, this._inputBuffer[var4]);
                           ++var4;
                        }
                     } else {
                        this._inputPtr = var4;
                        var5 = this._decodeFastCharEscape();
                        var4 = this._inputPtr;
                     }

                     if (var11 >= var7.length) {
                        var3 = this._textBuffer.finishCurrentSegment();
                        var11 = var9;
                     } else {
                        var3 = var7;
                     }

                     var8 = var11 + 1;
                     var3[var11] = (char)((char)var5);
                     var5 = var4;
                     var4 = var8;
                  }
                  break;
               }

               if (var5 == 39) {
                  this._inputPtr = var4;
                  this._textBuffer.setCurrentLength(var11);
                  return this._valueComplete(JsonToken.VALUE_STRING);
               }

               var7[var11] = (char)((char)var5);
               ++var11;
               var8 = var4;
            }
         }

         this._inputPtr = var5;
         this._minorState = 45;
         this._textBuffer.setCurrentLength(var4);
         var15 = JsonToken.NOT_AVAILABLE;
         this._currToken = var15;
         return var15;
      }
   }

   private final JsonToken _finishBOM(int var1) throws IOException {
      for(; this._inputPtr < this._inputEnd; ++var1) {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         var3 = var2[var3] & 255;
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 == 3) {
                  this._currInputProcessed -= 3L;
                  return this._startDocument(var3);
               }
            } else if (var3 != 191) {
               this._reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", var3);
            }
         } else if (var3 != 187) {
            this._reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", var3);
         }
      }

      this._pending32 = var1;
      this._minorState = 1;
      JsonToken var4 = JsonToken.NOT_AVAILABLE;
      this._currToken = var4;
      return var4;
   }

   private final JsonToken _finishCComment(int var1, boolean var2) throws IOException {
      while(this._inputPtr < this._inputEnd) {
         byte[] var4 = this._inputBuffer;
         int var3 = this._inputPtr++;
         var3 = var4[var3] & 255;
         if (var3 < 32) {
            if (var3 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 == 13) {
               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            } else if (var3 != 9) {
               this._throwInvalidSpace(var3);
            }
         } else {
            if (var3 == 42) {
               var2 = true;
               continue;
            }

            if (var3 == 47 && var2) {
               return this._startAfterComment(var1);
            }
         }

         var2 = false;
      }

      byte var5;
      if (var2) {
         var5 = 52;
      } else {
         var5 = 53;
      }

      this._minorState = var5;
      this._pending32 = var1;
      JsonToken var6 = JsonToken.NOT_AVAILABLE;
      this._currToken = var6;
      return var6;
   }

   private final JsonToken _finishCppComment(int var1) throws IOException {
      while(this._inputPtr < this._inputEnd) {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         var3 = var2[var3] & 255;
         if (var3 < 32) {
            if (var3 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else {
               if (var3 != 13) {
                  if (var3 != 9) {
                     this._throwInvalidSpace(var3);
                  }
                  continue;
               }

               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            }

            return this._startAfterComment(var1);
         }
      }

      this._minorState = 54;
      this._pending32 = var1;
      JsonToken var4 = JsonToken.NOT_AVAILABLE;
      this._currToken = var4;
      return var4;
   }

   private final JsonToken _finishHashComment(int var1) throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
         this._reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
      }

      while(true) {
         int var3;
         do {
            if (this._inputPtr >= this._inputEnd) {
               this._minorState = 55;
               this._pending32 = var1;
               JsonToken var4 = JsonToken.NOT_AVAILABLE;
               this._currToken = var4;
               return var4;
            }

            byte[] var2 = this._inputBuffer;
            var3 = this._inputPtr++;
            var3 = var2[var3] & 255;
         } while(var3 >= 32);

         if (var3 == 10) {
            ++this._currInputRow;
            this._currInputRowStart = this._inputPtr;
            break;
         }

         if (var3 == 13) {
            ++this._currInputRowAlt;
            this._currInputRowStart = this._inputPtr;
            break;
         }

         if (var3 != 9) {
            this._throwInvalidSpace(var3);
         }
      }

      return this._startAfterComment(var1);
   }

   private final JsonToken _finishRegularString() throws IOException {
      int[] var1 = _icUTF8;
      byte[] var2 = this._inputBuffer;
      char[] var3 = this._textBuffer.getBufferWithoutReset();
      int var4 = this._textBuffer.getCurrentSegmentSize();
      int var5 = this._inputPtr;
      int var6 = this._inputEnd;

      while(true) {
         JsonToken var15;
         while(var5 < this._inputEnd) {
            int var8 = var3.length;
            byte var9 = 0;
            boolean var10 = false;
            char[] var7 = var3;
            int var11 = var4;
            if (var4 >= var8) {
               var7 = this._textBuffer.finishCurrentSegment();
               var11 = 0;
            }

            int var12 = Math.min(this._inputEnd, var7.length - var11 + var5);
            var8 = var5;

            while(true) {
               var3 = var7;
               var4 = var11;
               var5 = var8;
               if (var8 >= var12) {
                  break;
               }

               var4 = var8 + 1;
               var5 = var2[var8] & 255;
               if (var1[var5] != 0) {
                  if (var5 == 34) {
                     this._inputPtr = var4;
                     this._textBuffer.setCurrentLength(var11);
                     return this._valueComplete(JsonToken.VALUE_STRING);
                  }

                  if (var4 >= var6 - 5) {
                     this._inputPtr = var4;
                     this._textBuffer.setCurrentLength(var11);
                     var11 = var1[var5];
                     if (var4 < this._inputEnd) {
                        var10 = true;
                     }

                     if (!this._decodeSplitMultiByte(var5, var11, var10)) {
                        this._minorStateAfterSplit = 40;
                        var15 = JsonToken.NOT_AVAILABLE;
                        this._currToken = var15;
                        return var15;
                     }

                     var3 = this._textBuffer.getBufferWithoutReset();
                     var4 = this._textBuffer.getCurrentSegmentSize();
                     var5 = this._inputPtr;
                  } else {
                     var8 = var1[var5];
                     if (var8 != 1) {
                        if (var8 != 2) {
                           byte[] var13;
                           if (var8 != 3) {
                              if (var8 != 4) {
                                 if (var5 < 32) {
                                    this._throwUnquotedSpace(var5, "string value");
                                 } else {
                                    this._reportInvalidChar(var5);
                                 }
                              } else {
                                 var13 = this._inputBuffer;
                                 var12 = var4 + 1;
                                 byte var14 = var13[var4];
                                 var8 = var12 + 1;
                                 var5 = this._decodeUTF8_4(var5, var14, var13[var12], var13[var8]);
                                 var4 = var11 + 1;
                                 var7[var11] = (char)((char)('\ud800' | var5 >> 10));
                                 if (var4 >= var7.length) {
                                    var7 = this._textBuffer.finishCurrentSegment();
                                    var11 = 0;
                                 } else {
                                    var11 = var4;
                                 }

                                 var5 = var5 & 1023 | '\udc00';
                                 var4 = var8 + 1;
                              }
                           } else {
                              var13 = this._inputBuffer;
                              var8 = var4 + 1;
                              var5 = this._decodeUTF8_3(var5, var13[var4], var13[var8]);
                              var4 = var8 + 1;
                           }
                        } else {
                           var5 = this._decodeUTF8_2(var5, this._inputBuffer[var4]);
                           ++var4;
                        }
                     } else {
                        this._inputPtr = var4;
                        var5 = this._decodeFastCharEscape();
                        var4 = this._inputPtr;
                     }

                     if (var11 >= var7.length) {
                        var7 = this._textBuffer.finishCurrentSegment();
                        var11 = var9;
                     }

                     var8 = var11 + 1;
                     var7[var11] = (char)((char)var5);
                     var5 = var4;
                     var3 = var7;
                     var4 = var8;
                  }
                  break;
               }

               var7[var11] = (char)((char)var5);
               ++var11;
               var8 = var4;
            }
         }

         this._inputPtr = var5;
         this._minorState = 40;
         this._textBuffer.setCurrentLength(var4);
         var15 = JsonToken.NOT_AVAILABLE;
         this._currToken = var15;
         return var15;
      }
   }

   private JsonToken _finishUnquotedName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = CharTypes.getInputCodeUtf8JsNames();

      while(this._inputPtr < this._inputEnd) {
         int var6 = this._inputBuffer[this._inputPtr] & 255;
         int[] var7;
         if (var5[var6] != 0) {
            var7 = var4;
            var6 = var1;
            if (var3 > 0) {
               var7 = var4;
               if (var1 >= var4.length) {
                  var7 = growArrayBy(var4, var4.length);
                  this._quadBuffer = var7;
               }

               var7[var1] = var2;
               var6 = var1 + 1;
            }

            String var9 = this._symbols.findName(var7, var6);
            String var8 = var9;
            if (var9 == null) {
               var8 = this._addName(var7, var6, var3);
            }

            return this._fieldComplete(var8);
         }

         ++this._inputPtr;
         if (var3 < 4) {
            ++var3;
            var2 = var2 << 8 | var6;
         } else {
            var7 = var4;
            if (var1 >= var4.length) {
               var7 = growArrayBy(var4, var4.length);
               this._quadBuffer = var7;
            }

            var7[var1] = var2;
            ++var1;
            var2 = var6;
            var3 = 1;
            var4 = var7;
         }
      }

      this._quadLength = var1;
      this._pending32 = var2;
      this._pendingBytes = var3;
      this._minorState = 10;
      JsonToken var10 = JsonToken.NOT_AVAILABLE;
      this._currToken = var10;
      return var10;
   }

   private JsonToken _handleOddName(int var1) throws IOException {
      if (var1 != 35) {
         if (var1 != 39) {
            if (var1 == 47) {
               return this._startSlashComment(4);
            }

            if (var1 == 93) {
               return this._closeArrayScope();
            }
         } else if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
            return this._finishAposName(0, 0, 0);
         }
      } else if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) != 0) {
         return this._finishHashComment(4);
      }

      if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
         this._reportUnexpectedChar((char)var1, "was expecting double-quote to start field name");
      }

      if (CharTypes.getInputCodeUtf8JsNames()[var1] != 0) {
         this._reportUnexpectedChar(var1, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
      }

      return this._finishUnquotedName(0, var1, 1);
   }

   private final JsonToken _parseEscapedName(int var1, int var2, int var3) throws IOException {
      int[] var4 = this._quadBuffer;
      int[] var5 = _icLatin1;

      while(true) {
         int var7;
         while(true) {
            JsonToken var13;
            if (this._inputPtr >= this._inputEnd) {
               this._quadLength = var1;
               this._pending32 = var2;
               this._pendingBytes = var3;
               this._minorState = 7;
               var13 = JsonToken.NOT_AVAILABLE;
               this._currToken = var13;
               return var13;
            }

            byte[] var6 = this._inputBuffer;
            var7 = this._inputPtr++;
            int var8 = var6[var7] & 255;
            int[] var15;
            if (var5[var8] == 0) {
               if (var3 < 4) {
                  var7 = var8;
                  break;
               }

               var15 = var4;
               if (var1 >= var4.length) {
                  var15 = growArrayBy(var4, var4.length);
                  this._quadBuffer = var15;
               }

               var3 = var1 + 1;
               var15[var1] = var2;
               var1 = var3;
               var7 = var8;
               var4 = var15;
            } else {
               if (var8 == 34) {
                  if (var3 > 0) {
                     var15 = var4;
                     if (var1 >= var4.length) {
                        var15 = growArrayBy(var4, var4.length);
                        this._quadBuffer = var15;
                     }

                     var15[var1] = _padLastQuad(var2, var3);
                     var2 = var1 + 1;
                  } else {
                     var15 = var4;
                     var2 = var1;
                     if (var1 == 0) {
                        return this._fieldComplete("");
                     }
                  }

                  String var12 = this._symbols.findName(var15, var2);
                  String var14 = var12;
                  if (var12 == null) {
                     var14 = this._addName(var15, var2, var3);
                  }

                  return this._fieldComplete(var14);
               }

               if (var8 != 92) {
                  this._throwUnquotedSpace(var8, "name");
               } else {
                  var7 = this._decodeCharEscape();
                  var8 = var7;
                  if (var7 < 0) {
                     this._minorState = 8;
                     this._minorStateAfterSplit = 7;
                     this._quadLength = var1;
                     this._pending32 = var2;
                     this._pendingBytes = var3;
                     var13 = JsonToken.NOT_AVAILABLE;
                     this._currToken = var13;
                     return var13;
                  }
               }

               var15 = var4;
               if (var1 >= var4.length) {
                  var15 = growArrayBy(var4, var4.length);
                  this._quadBuffer = var15;
               }

               var7 = var8;
               int var9 = var1;
               int var10 = var2;
               int var11 = var3;
               if (var8 > 127) {
                  byte var16 = 0;
                  var7 = var1;
                  var10 = var2;
                  var11 = var3;
                  if (var3 >= 4) {
                     var15[var1] = var2;
                     var7 = var1 + 1;
                     var10 = 0;
                     var11 = 0;
                  }

                  if (var8 < 2048) {
                     var10 = var10 << 8 | var8 >> 6 | 192;
                     ++var11;
                  } else {
                     var2 = var10 << 8 | var8 >> 12 | 224;
                     var1 = var11 + 1;
                     if (var1 >= 4) {
                        var15[var7] = var2;
                        ++var7;
                        var1 = 0;
                        var2 = var16;
                     }

                     var10 = var2 << 8 | var8 >> 6 & 63 | 128;
                     var11 = var1 + 1;
                  }

                  var1 = var8 & 63 | 128;
                  var9 = var7;
                  var7 = var1;
               }

               if (var11 < 4) {
                  var4 = var15;
                  var1 = var9;
                  var2 = var10;
                  var3 = var11;
                  break;
               }

               var1 = var9 + 1;
               var15[var9] = var10;
               var4 = var15;
            }

            var2 = var7;
            var3 = 1;
         }

         ++var3;
         var2 = var2 << 8 | var7;
      }
   }

   private final String _parseMediumName(int var1, int var2) throws IOException {
      byte[] var3 = this._inputBuffer;
      int[] var4 = _icLatin1;
      int var5 = var1 + 1;
      var1 = var3[var1] & 255;
      if (var4[var1] == 0) {
         var1 |= var2 << 8;
         var2 = var5 + 1;
         var5 = var3[var5] & 255;
         if (var4[var5] == 0) {
            var5 |= var1 << 8;
            var1 = var2 + 1;
            var2 = var3[var2] & 255;
            if (var4[var2] == 0) {
               var5 = var5 << 8 | var2;
               var2 = var1 + 1;
               var1 = var3[var1] & 255;
               if (var4[var1] == 0) {
                  return this._parseMediumName2(var2, var1, var5);
               } else if (var1 == 34) {
                  this._inputPtr = var2;
                  return this._findName(this._quad1, var5, 4);
               } else {
                  return null;
               }
            } else if (var2 == 34) {
               this._inputPtr = var1;
               return this._findName(this._quad1, var5, 3);
            } else {
               return null;
            }
         } else if (var5 == 34) {
            this._inputPtr = var2;
            return this._findName(this._quad1, var1, 2);
         } else {
            return null;
         }
      } else if (var1 == 34) {
         this._inputPtr = var5;
         return this._findName(this._quad1, var2, 1);
      } else {
         return null;
      }
   }

   private final String _parseMediumName2(int var1, int var2, int var3) throws IOException {
      byte[] var4 = this._inputBuffer;
      int[] var5 = _icLatin1;
      int var6 = var1 + 1;
      var1 = var4[var1] & 255;
      if (var5[var1] != 0) {
         if (var1 == 34) {
            this._inputPtr = var6;
            return this._findName(this._quad1, var3, var2, 1);
         } else {
            return null;
         }
      } else {
         var2 = var1 | var2 << 8;
         var1 = var6 + 1;
         var6 = var4[var6] & 255;
         if (var5[var6] != 0) {
            if (var6 == 34) {
               this._inputPtr = var1;
               return this._findName(this._quad1, var3, var2, 2);
            } else {
               return null;
            }
         } else {
            var6 |= var2 << 8;
            var2 = var1 + 1;
            var1 = var4[var1] & 255;
            if (var5[var1] != 0) {
               if (var1 == 34) {
                  this._inputPtr = var2;
                  return this._findName(this._quad1, var3, var6, 3);
               } else {
                  return null;
               }
            } else if ((var4[var2] & 255) == 34) {
               this._inputPtr = var2 + 1;
               return this._findName(this._quad1, var3, var6 << 8 | var1, 4);
            } else {
               return null;
            }
         }
      }
   }

   private final int _skipWS(int var1) throws IOException {
      int var3;
      do {
         if (var1 != 32) {
            if (var1 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 == 13) {
               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }

         if (this._inputPtr >= this._inputEnd) {
            this._currToken = JsonToken.NOT_AVAILABLE;
            return 0;
         }

         byte[] var2 = this._inputBuffer;
         var1 = this._inputPtr++;
         var3 = var2[var1] & 255;
         var1 = var3;
      } while(var3 <= 32);

      return var3;
   }

   private final JsonToken _startAfterComment(int var1) throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = var1;
         JsonToken var4 = JsonToken.NOT_AVAILABLE;
         this._currToken = var4;
         return var4;
      } else {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         var3 = var2[var3] & 255;
         if (var1 != 4) {
            if (var1 != 5) {
               switch(var1) {
               case 12:
                  return this._startValue(var3);
               case 13:
                  return this._startValueExpectComma(var3);
               case 14:
                  return this._startValueExpectColon(var3);
               case 15:
                  return this._startValueAfterComma(var3);
               default:
                  VersionUtil.throwInternal();
                  return null;
               }
            } else {
               return this._startFieldNameAfterComma(var3);
            }
         } else {
            return this._startFieldName(var3);
         }
      }
   }

   private final JsonToken _startDocument(int var1) throws IOException {
      int var2 = var1 & 255;
      var1 = var2;
      if (var2 == 239) {
         var1 = var2;
         if (this._minorState != 1) {
            return this._finishBOM(1);
         }
      }

      while(var1 <= 32) {
         if (var1 != 32) {
            if (var1 == 10) {
               ++this._currInputRow;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 == 13) {
               ++this._currInputRowAlt;
               this._currInputRowStart = this._inputPtr;
            } else if (var1 != 9) {
               this._throwInvalidSpace(var1);
            }
         }

         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 3;
            if (this._closed) {
               return null;
            }

            if (this._endOfInput) {
               return this._eofAsNextToken();
            }

            return JsonToken.NOT_AVAILABLE;
         }

         byte[] var3 = this._inputBuffer;
         var1 = this._inputPtr++;
         var1 = var3[var1] & 255;
      }

      return this._startValue(var1);
   }

   private final JsonToken _startFieldName(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 4;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      if (var2 != 34) {
         return var2 == 125 ? this._closeObjectScope() : this._handleOddName(var2);
      } else {
         if (this._inputPtr + 13 <= this._inputEnd) {
            String var3 = this._fastParseName();
            if (var3 != null) {
               return this._fieldComplete(var3);
            }
         }

         return this._parseEscapedName(0, 0, 0);
      }
   }

   private final JsonToken _startFieldNameAfterComma(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 5;
            return this._currToken;
         }
      }

      if (var2 != 44) {
         if (var2 == 125) {
            return this._closeObjectScope();
         }

         if (var2 == 35) {
            return this._finishHashComment(5);
         }

         if (var2 == 47) {
            return this._startSlashComment(5);
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("was expecting comma to separate ");
         var3.append(this._parsingContext.typeDesc());
         var3.append(" entries");
         this._reportUnexpectedChar(var2, var3.toString());
      }

      var1 = this._inputPtr;
      if (var1 >= this._inputEnd) {
         this._minorState = 4;
         JsonToken var6 = JsonToken.NOT_AVAILABLE;
         this._currToken = var6;
         return var6;
      } else {
         byte var4 = this._inputBuffer[var1];
         this._inputPtr = var1 + 1;
         var1 = var4;
         if (var4 <= 32) {
            var2 = this._skipWS(var4);
            var1 = var2;
            if (var2 <= 0) {
               this._minorState = 4;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 != 34) {
            return var1 == 125 && (this._features & FEAT_MASK_TRAILING_COMMA) != 0 ? this._closeObjectScope() : this._handleOddName(var1);
         } else {
            if (this._inputPtr + 13 <= this._inputEnd) {
               String var5 = this._fastParseName();
               if (var5 != null) {
                  return this._fieldComplete(var5);
               }
            }

            return this._parseEscapedName(0, 0, 0);
         }
      }
   }

   private final JsonToken _startSlashComment(int var1) throws IOException {
      if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
      }

      if (this._inputPtr >= this._inputEnd) {
         this._pending32 = var1;
         this._minorState = 51;
         JsonToken var4 = JsonToken.NOT_AVAILABLE;
         this._currToken = var4;
         return var4;
      } else {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr++;
         byte var5 = var2[var3];
         if (var5 == 42) {
            return this._finishCComment(var1, false);
         } else if (var5 == 47) {
            return this._finishCppComment(var1);
         } else {
            this._reportUnexpectedChar(var5 & 255, "was expecting either '*' or '/' for a comment");
            return null;
         }
      }
   }

   private final JsonToken _startValue(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 12;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      this._parsingContext.expectComma();
      if (var2 == 34) {
         return this._startString();
      } else if (var2 != 35) {
         if (var2 != 45) {
            if (var2 != 91) {
               if (var2 != 93) {
                  if (var2 != 102) {
                     if (var2 != 110) {
                        if (var2 != 116) {
                           if (var2 != 123) {
                              if (var2 != 125) {
                                 switch(var2) {
                                 case 47:
                                    return this._startSlashComment(12);
                                 case 48:
                                    return this._startNumberLeadingZero();
                                 case 49:
                                 case 50:
                                 case 51:
                                 case 52:
                                 case 53:
                                 case 54:
                                 case 55:
                                 case 56:
                                 case 57:
                                    return this._startPositiveNumber(var2);
                                 default:
                                    return this._startUnexpectedValue(false, var2);
                                 }
                              } else {
                                 return this._closeObjectScope();
                              }
                           } else {
                              return this._startObjectScope();
                           }
                        } else {
                           return this._startTrueToken();
                        }
                     } else {
                        return this._startNullToken();
                     }
                  } else {
                     return this._startFalseToken();
                  }
               } else {
                  return this._closeArrayScope();
               }
            } else {
               return this._startArrayScope();
            }
         } else {
            return this._startNegativeNumber();
         }
      } else {
         return this._finishHashComment(12);
      }
   }

   private final JsonToken _startValueAfterComma(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 15;
            return this._currToken;
         }
      }

      this._updateTokenLocation();
      if (var2 == 34) {
         return this._startString();
      } else if (var2 != 35) {
         if (var2 != 45) {
            if (var2 != 91) {
               if (var2 != 93) {
                  if (var2 == 102) {
                     return this._startFalseToken();
                  }

                  if (var2 == 110) {
                     return this._startNullToken();
                  }

                  if (var2 == 116) {
                     return this._startTrueToken();
                  }

                  if (var2 == 123) {
                     return this._startObjectScope();
                  }

                  if (var2 != 125) {
                     switch(var2) {
                     case 47:
                        return this._startSlashComment(15);
                     case 48:
                        return this._startNumberLeadingZero();
                     case 49:
                     case 50:
                     case 51:
                     case 52:
                     case 53:
                     case 54:
                     case 55:
                     case 56:
                     case 57:
                        return this._startPositiveNumber(var2);
                     }
                  } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     return this._closeObjectScope();
                  }
               } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                  return this._closeArrayScope();
               }

               return this._startUnexpectedValue(true, var2);
            } else {
               return this._startArrayScope();
            }
         } else {
            return this._startNegativeNumber();
         }
      } else {
         return this._finishHashComment(15);
      }
   }

   private final JsonToken _startValueExpectColon(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 14;
            return this._currToken;
         }
      }

      if (var2 != 58) {
         if (var2 == 47) {
            return this._startSlashComment(14);
         }

         if (var2 == 35) {
            return this._finishHashComment(14);
         }

         this._reportUnexpectedChar(var2, "was expecting a colon to separate field name and value");
      }

      var1 = this._inputPtr;
      if (var1 >= this._inputEnd) {
         this._minorState = 12;
         JsonToken var3 = JsonToken.NOT_AVAILABLE;
         this._currToken = var3;
         return var3;
      } else {
         byte var4 = this._inputBuffer[var1];
         this._inputPtr = var1 + 1;
         var1 = var4;
         if (var4 <= 32) {
            var2 = this._skipWS(var4);
            var1 = var2;
            if (var2 <= 0) {
               this._minorState = 12;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 == 34) {
            return this._startString();
         } else if (var1 != 35) {
            if (var1 != 45) {
               if (var1 != 91) {
                  if (var1 != 102) {
                     if (var1 != 110) {
                        if (var1 != 116) {
                           if (var1 != 123) {
                              switch(var1) {
                              case 47:
                                 return this._startSlashComment(12);
                              case 48:
                                 return this._startNumberLeadingZero();
                              case 49:
                              case 50:
                              case 51:
                              case 52:
                              case 53:
                              case 54:
                              case 55:
                              case 56:
                              case 57:
                                 return this._startPositiveNumber(var1);
                              default:
                                 return this._startUnexpectedValue(false, var1);
                              }
                           } else {
                              return this._startObjectScope();
                           }
                        } else {
                           return this._startTrueToken();
                        }
                     } else {
                        return this._startNullToken();
                     }
                  } else {
                     return this._startFalseToken();
                  }
               } else {
                  return this._startArrayScope();
               }
            } else {
               return this._startNegativeNumber();
            }
         } else {
            return this._finishHashComment(12);
         }
      }
   }

   private final JsonToken _startValueExpectComma(int var1) throws IOException {
      int var2 = var1;
      if (var1 <= 32) {
         var1 = this._skipWS(var1);
         var2 = var1;
         if (var1 <= 0) {
            this._minorState = 13;
            return this._currToken;
         }
      }

      if (var2 != 44) {
         if (var2 == 93) {
            return this._closeArrayScope();
         }

         if (var2 == 125) {
            return this._closeObjectScope();
         }

         if (var2 == 47) {
            return this._startSlashComment(13);
         }

         if (var2 == 35) {
            return this._finishHashComment(13);
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("was expecting comma to separate ");
         var3.append(this._parsingContext.typeDesc());
         var3.append(" entries");
         this._reportUnexpectedChar(var2, var3.toString());
      }

      this._parsingContext.expectComma();
      var1 = this._inputPtr;
      if (var1 >= this._inputEnd) {
         this._minorState = 15;
         JsonToken var5 = JsonToken.NOT_AVAILABLE;
         this._currToken = var5;
         return var5;
      } else {
         byte var4 = this._inputBuffer[var1];
         this._inputPtr = var1 + 1;
         var1 = var4;
         if (var4 <= 32) {
            var2 = this._skipWS(var4);
            var1 = var2;
            if (var2 <= 0) {
               this._minorState = 15;
               return this._currToken;
            }
         }

         this._updateTokenLocation();
         if (var1 == 34) {
            return this._startString();
         } else if (var1 != 35) {
            if (var1 != 45) {
               if (var1 != 91) {
                  if (var1 != 93) {
                     if (var1 == 102) {
                        return this._startFalseToken();
                     }

                     if (var1 == 110) {
                        return this._startNullToken();
                     }

                     if (var1 == 116) {
                        return this._startTrueToken();
                     }

                     if (var1 == 123) {
                        return this._startObjectScope();
                     }

                     if (var1 != 125) {
                        switch(var1) {
                        case 47:
                           return this._startSlashComment(15);
                        case 48:
                           return this._startNumberLeadingZero();
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                           return this._startPositiveNumber(var1);
                        }
                     } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                        return this._closeObjectScope();
                     }
                  } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                     return this._closeArrayScope();
                  }

                  return this._startUnexpectedValue(true, var1);
               } else {
                  return this._startArrayScope();
               }
            } else {
               return this._startNegativeNumber();
            }
         } else {
            return this._finishHashComment(15);
         }
      }
   }

   protected char _decodeEscaped() throws IOException {
      VersionUtil.throwInternal();
      return ' ';
   }

   protected JsonToken _finishErrorToken() throws IOException {
      while(true) {
         if (this._inputPtr < this._inputEnd) {
            byte[] var4 = this._inputBuffer;
            int var2 = this._inputPtr++;
            char var3 = (char)var4[var2];
            if (Character.isJavaIdentifierPart(var3)) {
               this._textBuffer.append(var3);
               if (this._textBuffer.size() < 256) {
                  continue;
               }
            }

            return this._reportErrorToken(this._textBuffer.contentsAsString());
         }

         JsonToken var1 = JsonToken.NOT_AVAILABLE;
         this._currToken = var1;
         return var1;
      }
   }

   protected JsonToken _finishErrorTokenWithEOF() throws IOException {
      return this._reportErrorToken(this._textBuffer.contentsAsString());
   }

   protected final JsonToken _finishFieldWithEscape() throws IOException {
      int var1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
      if (var1 < 0) {
         this._minorState = 8;
         return JsonToken.NOT_AVAILABLE;
      } else {
         if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = growArrayBy(this._quadBuffer, 32);
         }

         int var2 = this._pending32;
         int var3 = this._pendingBytes;
         byte var4 = 1;
         int var5 = var1;
         int var6 = var2;
         int var7 = var3;
         int[] var8;
         if (var1 > 127) {
            byte var9 = 0;
            var6 = var2;
            var5 = var3;
            if (var3 >= 4) {
               var8 = this._quadBuffer;
               var5 = this._quadLength++;
               var8[var5] = var2;
               var6 = 0;
               var5 = 0;
            }

            if (var1 < 2048) {
               var3 = var6 << 8;
               var2 = var1 >> 6 | 192;
            } else {
               var2 = var6 << 8 | var1 >> 12 | 224;
               ++var5;
               if (var5 >= 4) {
                  var8 = this._quadBuffer;
                  var5 = this._quadLength++;
                  var8[var5] = var2;
                  var5 = 0;
                  var2 = var9;
               }

               var3 = var2 << 8;
               var2 = var1 >> 6 & 63 | 128;
            }

            var6 = var3 | var2;
            var7 = var5 + 1;
            var5 = var1 & 63 | 128;
         }

         if (var7 < 4) {
            var2 = 1 + var7;
            var5 |= var6 << 8;
         } else {
            var8 = this._quadBuffer;
            var2 = this._quadLength++;
            var8[var2] = var6;
            var2 = var4;
         }

         return this._minorStateAfterSplit == 9 ? this._finishAposName(this._quadLength, var5, var2) : this._parseEscapedName(this._quadLength, var5, var2);
      }
   }

   protected JsonToken _finishFloatExponent(boolean var1, int var2) throws IOException {
      int var3 = var2;
      if (var1) {
         label44: {
            this._minorState = 32;
            if (var2 != 45) {
               var3 = var2;
               if (var2 != 43) {
                  break label44;
               }
            }

            this._textBuffer.append((char)var2);
            if (this._inputPtr >= this._inputEnd) {
               this._minorState = 32;
               this._expLength = 0;
               return JsonToken.NOT_AVAILABLE;
            }

            byte[] var4 = this._inputBuffer;
            var2 = this._inputPtr++;
            var3 = var4[var2];
         }
      }

      char[] var5 = this._textBuffer.getBufferWithoutReset();
      var2 = this._textBuffer.getCurrentSegmentSize();

      int var6;
      char[] var8;
      for(var6 = this._expLength; var3 >= 48 && var3 <= 57; var5 = var8) {
         ++var6;
         var8 = var5;
         if (var2 >= var5.length) {
            var8 = this._textBuffer.expandCurrentSegment();
         }

         int var7 = var2 + 1;
         var8[var2] = (char)((char)var3);
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var7);
            this._expLength = var6;
            return JsonToken.NOT_AVAILABLE;
         }

         byte[] var9 = this._inputBuffer;
         var2 = this._inputPtr++;
         var3 = var9[var2];
         var2 = var7;
      }

      if (var6 == 0) {
         this.reportUnexpectedNumberChar(var3 & 255, "Exponent indicator not followed by a digit");
      }

      --this._inputPtr;
      this._textBuffer.setCurrentLength(var2);
      this._expLength = var6;
      return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
   }

   protected JsonToken _finishFloatFraction() throws IOException {
      int var1 = this._fractLength;
      char[] var2 = this._textBuffer.getBufferWithoutReset();
      int var3 = this._textBuffer.getCurrentSegmentSize();

      while(true) {
         byte[] var4 = this._inputBuffer;
         int var5 = this._inputPtr++;
         byte var6 = var4[var5];
         if (var6 < 48 || var6 > 57) {
            if (var1 == 0) {
               this.reportUnexpectedNumberChar(var6, "Decimal point not followed by a digit");
            }

            this._fractLength = var1;
            this._textBuffer.setCurrentLength(var3);
            if (var6 != 101 && var6 != 69) {
               --this._inputPtr;
               this._textBuffer.setCurrentLength(var3);
               this._expLength = 0;
               return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
            } else {
               this._textBuffer.append((char)var6);
               this._expLength = 0;
               if (this._inputPtr >= this._inputEnd) {
                  this._minorState = 31;
                  return JsonToken.NOT_AVAILABLE;
               } else {
                  this._minorState = 32;
                  byte[] var7 = this._inputBuffer;
                  var3 = this._inputPtr++;
                  return this._finishFloatExponent(true, var7[var3] & 255);
               }
            }
         }

         ++var1;
         char[] var8 = var2;
         if (var3 >= var2.length) {
            var8 = this._textBuffer.expandCurrentSegment();
         }

         var5 = var3 + 1;
         var8[var3] = (char)((char)var6);
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var5);
            this._fractLength = var1;
            return JsonToken.NOT_AVAILABLE;
         }

         var3 = var5;
         var2 = var8;
      }
   }

   protected JsonToken _finishKeywordToken(String var1, int var2, JsonToken var3) throws IOException {
      int var4 = var1.length();

      while(this._inputPtr < this._inputEnd) {
         byte var5 = this._inputBuffer[this._inputPtr];
         if (var2 == var4) {
            if (var5 < 48 || var5 == 93 || var5 == 125) {
               return this._valueComplete(var3);
            }
         } else if (var5 == var1.charAt(var2)) {
            ++var2;
            ++this._inputPtr;
            continue;
         }

         this._minorState = 50;
         this._textBuffer.resetWithCopy((String)var1, 0, var2);
         return this._finishErrorToken();
      }

      this._pending32 = var2;
      JsonToken var6 = JsonToken.NOT_AVAILABLE;
      this._currToken = var6;
      return var6;
   }

   protected JsonToken _finishKeywordTokenWithEOF(String var1, int var2, JsonToken var3) throws IOException {
      if (var2 == var1.length()) {
         this._currToken = var3;
         return var3;
      } else {
         this._textBuffer.resetWithCopy((String)var1, 0, var2);
         return this._finishErrorTokenWithEOF();
      }
   }

   protected JsonToken _finishNonStdToken(int var1, int var2) throws IOException {
      String var3 = this._nonStdToken(var1);
      int var4 = var3.length();

      while(this._inputPtr < this._inputEnd) {
         byte var5 = this._inputBuffer[this._inputPtr];
         if (var2 == var4) {
            if (var5 < 48 || var5 == 93 || var5 == 125) {
               return this._valueNonStdNumberComplete(var1);
            }
         } else if (var5 == var3.charAt(var2)) {
            ++var2;
            ++this._inputPtr;
            continue;
         }

         this._minorState = 50;
         this._textBuffer.resetWithCopy((String)var3, 0, var2);
         return this._finishErrorToken();
      }

      this._nonStdTokenType = var1;
      this._pending32 = var2;
      this._minorState = 19;
      JsonToken var6 = JsonToken.NOT_AVAILABLE;
      this._currToken = var6;
      return var6;
   }

   protected JsonToken _finishNonStdTokenWithEOF(int var1, int var2) throws IOException {
      String var3 = this._nonStdToken(var1);
      if (var2 == var3.length()) {
         return this._valueNonStdNumberComplete(var1);
      } else {
         this._textBuffer.resetWithCopy((String)var3, 0, var2);
         return this._finishErrorTokenWithEOF();
      }
   }

   protected JsonToken _finishNumberIntegralPart(char[] var1, int var2) throws IOException {
      byte var3;
      char[] var4;
      if (this._numberNegative) {
         var3 = -1;
         var4 = var1;
      } else {
         var3 = 0;
         var4 = var1;
      }

      while(this._inputPtr < this._inputEnd) {
         int var5 = this._inputBuffer[this._inputPtr] & 255;
         if (var5 < 48) {
            if (var5 == 46) {
               this._intLength = var3 + var2;
               ++this._inputPtr;
               return this._startFloat(var4, var2, var5);
            }
         } else {
            if (var5 <= 57) {
               ++this._inputPtr;
               var1 = var4;
               if (var2 >= var4.length) {
                  var1 = this._textBuffer.expandCurrentSegment();
               }

               var1[var2] = (char)((char)var5);
               ++var2;
               var4 = var1;
               continue;
            }

            if (var5 == 101 || var5 == 69) {
               this._intLength = var3 + var2;
               ++this._inputPtr;
               return this._startFloat(var4, var2, var5);
            }
         }

         this._intLength = var3 + var2;
         this._textBuffer.setCurrentLength(var2);
         return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
      }

      this._minorState = 26;
      this._textBuffer.setCurrentLength(var2);
      JsonToken var6 = JsonToken.NOT_AVAILABLE;
      this._currToken = var6;
      return var6;
   }

   protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
      while(this._inputPtr < this._inputEnd) {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         char[] var3;
         if (var2 < 48) {
            if (var2 == 46) {
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)45;
               var3[1] = (char)48;
               this._intLength = 1;
               return this._startFloat(var3, 2, var2);
            }
         } else {
            if (var2 <= 57) {
               if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
                  this.reportInvalidNumber("Leading zeroes not allowed");
               }

               if (var2 == 48) {
                  continue;
               }

               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)45;
               var3[1] = (char)((char)var2);
               this._intLength = 1;
               return this._finishNumberIntegralPart(var3, 2);
            }

            if (var2 == 101 || var2 == 69) {
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)45;
               var3[1] = (char)48;
               this._intLength = 1;
               return this._startFloat(var3, 2, var2);
            }

            if (var2 != 93 && var2 != 125) {
               this.reportUnexpectedNumberChar(var2, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         --this._inputPtr;
         return this._valueCompleteInt(0, "0");
      }

      this._minorState = 25;
      JsonToken var4 = JsonToken.NOT_AVAILABLE;
      this._currToken = var4;
      return var4;
   }

   protected JsonToken _finishNumberLeadingZeroes() throws IOException {
      while(this._inputPtr < this._inputEnd) {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         char[] var3;
         if (var2 < 48) {
            if (var2 == 46) {
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)48;
               this._intLength = 1;
               return this._startFloat(var3, 1, var2);
            }
         } else {
            if (var2 <= 57) {
               if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
                  this.reportInvalidNumber("Leading zeroes not allowed");
               }

               if (var2 == 48) {
                  continue;
               }

               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)((char)var2);
               this._intLength = 1;
               return this._finishNumberIntegralPart(var3, 1);
            }

            if (var2 == 101 || var2 == 69) {
               var3 = this._textBuffer.emptyAndGetCurrentSegment();
               var3[0] = (char)48;
               this._intLength = 1;
               return this._startFloat(var3, 1, var2);
            }

            if (var2 != 93 && var2 != 125) {
               this.reportUnexpectedNumberChar(var2, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         --this._inputPtr;
         return this._valueCompleteInt(0, "0");
      }

      this._minorState = 24;
      JsonToken var4 = JsonToken.NOT_AVAILABLE;
      this._currToken = var4;
      return var4;
   }

   protected JsonToken _finishNumberMinus(int var1) throws IOException {
      if (var1 <= 48) {
         if (var1 == 48) {
            return this._finishNumberLeadingNegZeroes();
         }

         this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
      } else if (var1 > 57) {
         if (var1 == 73) {
            return this._finishNonStdToken(3, 2);
         }

         this.reportUnexpectedNumberChar(var1, "expected digit (0-9) to follow minus sign, for valid numeric value");
      }

      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      var2[0] = (char)45;
      var2[1] = (char)((char)var1);
      this._intLength = 1;
      return this._finishNumberIntegralPart(var2, 2);
   }

   protected final JsonToken _finishToken() throws IOException {
      int var1 = this._minorState;
      if (var1 != 1) {
         byte[] var3;
         if (var1 != 4) {
            if (var1 != 5) {
               switch(var1) {
               case 7:
                  return this._parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
               case 8:
                  return this._finishFieldWithEscape();
               case 9:
                  return this._finishAposName(this._quadLength, this._pending32, this._pendingBytes);
               case 10:
                  return this._finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
               default:
                  switch(var1) {
                  case 12:
                     var3 = this._inputBuffer;
                     var1 = this._inputPtr++;
                     return this._startValue(var3[var1] & 255);
                  case 13:
                     var3 = this._inputBuffer;
                     var1 = this._inputPtr++;
                     return this._startValueExpectComma(var3[var1] & 255);
                  case 14:
                     var3 = this._inputBuffer;
                     var1 = this._inputPtr++;
                     return this._startValueExpectColon(var3[var1] & 255);
                  case 15:
                     var3 = this._inputBuffer;
                     var1 = this._inputPtr++;
                     return this._startValueAfterComma(var3[var1] & 255);
                  case 16:
                     return this._finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
                  case 17:
                     return this._finishKeywordToken("true", this._pending32, JsonToken.VALUE_TRUE);
                  case 18:
                     return this._finishKeywordToken("false", this._pending32, JsonToken.VALUE_FALSE);
                  case 19:
                     return this._finishNonStdToken(this._nonStdTokenType, this._pending32);
                  default:
                     switch(var1) {
                     case 23:
                        var3 = this._inputBuffer;
                        var1 = this._inputPtr++;
                        return this._finishNumberMinus(var3[var1] & 255);
                     case 24:
                        return this._finishNumberLeadingZeroes();
                     case 25:
                        return this._finishNumberLeadingNegZeroes();
                     case 26:
                        return this._finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
                     default:
                        switch(var1) {
                        case 30:
                           return this._finishFloatFraction();
                        case 31:
                           var3 = this._inputBuffer;
                           var1 = this._inputPtr++;
                           return this._finishFloatExponent(true, var3[var1] & 255);
                        case 32:
                           var3 = this._inputBuffer;
                           var1 = this._inputPtr++;
                           return this._finishFloatExponent(false, var3[var1] & 255);
                        default:
                           int var2;
                           int var4;
                           switch(var1) {
                           case 40:
                              return this._finishRegularString();
                           case 41:
                              var1 = this._decodeSplitEscaped(this._quoted32, this._quotedDigits);
                              if (var1 < 0) {
                                 return JsonToken.NOT_AVAILABLE;
                              } else {
                                 this._textBuffer.append((char)var1);
                                 if (this._minorStateAfterSplit == 45) {
                                    return this._finishAposString();
                                 }

                                 return this._finishRegularString();
                              }
                           case 42:
                              TextBuffer var6 = this._textBuffer;
                              var1 = this._pending32;
                              byte[] var5 = this._inputBuffer;
                              var2 = this._inputPtr++;
                              var6.append((char)this._decodeUTF8_2(var1, var5[var2]));
                              if (this._minorStateAfterSplit == 45) {
                                 return this._finishAposString();
                              }

                              return this._finishRegularString();
                           case 43:
                              var2 = this._pending32;
                              var4 = this._pendingBytes;
                              var3 = this._inputBuffer;
                              var1 = this._inputPtr++;
                              if (!this._decodeSplitUTF8_3(var2, var4, var3[var1])) {
                                 return JsonToken.NOT_AVAILABLE;
                              } else {
                                 if (this._minorStateAfterSplit == 45) {
                                    return this._finishAposString();
                                 }

                                 return this._finishRegularString();
                              }
                           case 44:
                              var1 = this._pending32;
                              var2 = this._pendingBytes;
                              var3 = this._inputBuffer;
                              var4 = this._inputPtr++;
                              if (!this._decodeSplitUTF8_4(var1, var2, var3[var4])) {
                                 return JsonToken.NOT_AVAILABLE;
                              } else {
                                 if (this._minorStateAfterSplit == 45) {
                                    return this._finishAposString();
                                 }

                                 return this._finishRegularString();
                              }
                           case 45:
                              return this._finishAposString();
                           default:
                              switch(var1) {
                              case 50:
                                 return this._finishErrorToken();
                              case 51:
                                 return this._startSlashComment(this._pending32);
                              case 52:
                                 return this._finishCComment(this._pending32, true);
                              case 53:
                                 return this._finishCComment(this._pending32, false);
                              case 54:
                                 return this._finishCppComment(this._pending32);
                              case 55:
                                 return this._finishHashComment(this._pending32);
                              default:
                                 VersionUtil.throwInternal();
                                 return null;
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               var3 = this._inputBuffer;
               var1 = this._inputPtr++;
               return this._startFieldNameAfterComma(var3[var1] & 255);
            }
         } else {
            var3 = this._inputBuffer;
            var1 = this._inputPtr++;
            return this._startFieldName(var3[var1] & 255);
         }
      } else {
         return this._finishBOM(this._pending32);
      }
   }

   protected final JsonToken _finishTokenWithEOF() throws IOException {
      JsonToken var1 = this._currToken;
      int var2 = this._minorState;
      if (var2 == 3) {
         return this._eofAsNextToken();
      } else if (var2 != 12) {
         if (var2 == 50) {
            return this._finishErrorTokenWithEOF();
         } else {
            switch(var2) {
            case 16:
               return this._finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
            case 17:
               return this._finishKeywordTokenWithEOF("true", this._pending32, JsonToken.VALUE_TRUE);
            case 18:
               return this._finishKeywordTokenWithEOF("false", this._pending32, JsonToken.VALUE_FALSE);
            case 19:
               return this._finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
            default:
               switch(var2) {
               case 24:
               case 25:
                  return this._valueCompleteInt(0, "0");
               case 26:
                  int var4 = this._textBuffer.getCurrentSegmentSize();
                  var2 = var4;
                  if (this._numberNegative) {
                     var2 = var4 - 1;
                  }

                  this._intLength = var2;
                  return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
               default:
                  switch(var2) {
                  case 30:
                     this._expLength = 0;
                  case 32:
                     return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
                  case 31:
                     this._reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
                     break;
                  default:
                     switch(var2) {
                     case 52:
                     case 53:
                        break;
                     case 54:
                     case 55:
                        return this._eofAsNextToken();
                     default:
                        StringBuilder var3 = new StringBuilder();
                        var3.append(": was expecting rest of token (internal state: ");
                        var3.append(this._minorState);
                        var3.append(")");
                        this._reportInvalidEOF(var3.toString(), this._currToken);
                        return var1;
                     }
                  }

                  this._reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
                  return this._eofAsNextToken();
               }
            }
         }
      } else {
         return this._eofAsNextToken();
      }
   }

   protected JsonToken _reportErrorToken(String var1) throws IOException {
      this._reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), this._validJsonTokenList());
      return JsonToken.NOT_AVAILABLE;
   }

   protected JsonToken _startAposString() throws IOException {
      int var1 = this._inputPtr;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var3 = _icUTF8;
      int var4 = Math.min(this._inputEnd, var2.length + var1);
      byte[] var5 = this._inputBuffer;

      int var6;
      for(var6 = 0; var1 < var4; ++var6) {
         int var7 = var5[var1] & 255;
         if (var7 == 39) {
            this._inputPtr = var1 + 1;
            this._textBuffer.setCurrentLength(var6);
            return this._valueComplete(JsonToken.VALUE_STRING);
         }

         if (var3[var7] != 0) {
            break;
         }

         ++var1;
         var2[var6] = (char)((char)var7);
      }

      this._textBuffer.setCurrentLength(var6);
      this._inputPtr = var1;
      return this._finishAposString();
   }

   protected JsonToken _startFalseToken() throws IOException {
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
                        return this._valueComplete(JsonToken.VALUE_FALSE);
                     }
                  }
               }
            }
         }
      }

      this._minorState = 18;
      return this._finishKeywordToken("false", 1, JsonToken.VALUE_FALSE);
   }

   protected JsonToken _startFloat(char[] var1, int var2, int var3) throws IOException {
      byte var4 = 0;
      byte var5 = 0;
      byte var6 = 0;
      char[] var7;
      int var8;
      int var10;
      int var11;
      JsonToken var12;
      byte[] var15;
      if (var3 == 46) {
         var7 = var1;
         if (var2 >= var1.length) {
            var7 = this._textBuffer.expandCurrentSegment();
         }

         var7[var2] = (char)46;
         var3 = var2 + 1;
         var8 = 0;
         var1 = var7;

         while(true) {
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var3);
               this._minorState = 30;
               this._fractLength = var8;
               var12 = JsonToken.NOT_AVAILABLE;
               this._currToken = var12;
               return var12;
            }

            var15 = this._inputBuffer;
            var2 = this._inputPtr++;
            byte var13 = var15[var2];
            if (var13 < 48 || var13 > 57) {
               int var9 = var13 & 255;
               var10 = var9;
               var7 = var1;
               var2 = var3;
               var11 = var8;
               if (var8 == 0) {
                  this.reportUnexpectedNumberChar(var9, "Decimal point not followed by a digit");
                  var10 = var9;
                  var7 = var1;
                  var2 = var3;
                  var11 = var8;
               }
               break;
            }

            var7 = var1;
            if (var3 >= var1.length) {
               var7 = this._textBuffer.expandCurrentSegment();
            }

            var7[var3] = (char)((char)var13);
            ++var8;
            ++var3;
            var1 = var7;
         }
      } else {
         var11 = 0;
         var7 = var1;
         var10 = var3;
      }

      label99: {
         this._fractLength = var11;
         if (var10 != 101) {
            var3 = var5;
            var8 = var2;
            if (var10 != 69) {
               break label99;
            }
         }

         var1 = var7;
         if (var2 >= var7.length) {
            var1 = this._textBuffer.expandCurrentSegment();
         }

         var11 = var2 + 1;
         var1[var2] = (char)((char)var10);
         if (this._inputPtr >= this._inputEnd) {
            this._textBuffer.setCurrentLength(var11);
            this._minorState = 31;
            this._expLength = 0;
            var12 = JsonToken.NOT_AVAILABLE;
            this._currToken = var12;
            return var12;
         }

         byte var16;
         label100: {
            var15 = this._inputBuffer;
            var2 = this._inputPtr++;
            byte var17 = var15[var2];
            if (var17 != 45) {
               var3 = var4;
               var7 = var1;
               var16 = var17;
               var2 = var11;
               if (var17 != 43) {
                  break label100;
               }
            }

            var7 = var1;
            if (var11 >= var1.length) {
               var7 = this._textBuffer.expandCurrentSegment();
            }

            var2 = var11 + 1;
            var7[var11] = (char)((char)var17);
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var2);
               this._minorState = 32;
               this._expLength = 0;
               var12 = JsonToken.NOT_AVAILABLE;
               this._currToken = var12;
               return var12;
            }

            byte[] var14 = this._inputBuffer;
            var3 = this._inputPtr++;
            var16 = var14[var3];
            var3 = var6;
         }

         for(var7 = var7; var16 >= 48 && var16 <= 57; var7 = var1) {
            ++var3;
            var1 = var7;
            if (var2 >= var7.length) {
               var1 = this._textBuffer.expandCurrentSegment();
            }

            var10 = var2 + 1;
            var1[var2] = (char)((char)var16);
            if (this._inputPtr >= this._inputEnd) {
               this._textBuffer.setCurrentLength(var10);
               this._minorState = 32;
               this._expLength = var3;
               var12 = JsonToken.NOT_AVAILABLE;
               this._currToken = var12;
               return var12;
            }

            var15 = this._inputBuffer;
            var2 = this._inputPtr++;
            var16 = var15[var2];
            var2 = var10;
         }

         if (var3 == 0) {
            this.reportUnexpectedNumberChar(var16 & 255, "Exponent indicator not followed by a digit");
         }

         var8 = var2;
      }

      --this._inputPtr;
      this._textBuffer.setCurrentLength(var8);
      this._expLength = var3;
      return this._valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
   }

   protected JsonToken _startNegativeNumber() throws IOException {
      this._numberNegative = true;
      JsonToken var7;
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = 23;
         var7 = JsonToken.NOT_AVAILABLE;
         this._currToken = var7;
         return var7;
      } else {
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         int var3 = 2;
         if (var2 <= 48) {
            if (var2 == 48) {
               return this._finishNumberLeadingNegZeroes();
            }

            this.reportUnexpectedNumberChar(var2, "expected digit (0-9) to follow minus sign, for valid numeric value");
         } else if (var2 > 57) {
            if (var2 == 73) {
               return this._finishNonStdToken(3, 2);
            }

            this.reportUnexpectedNumberChar(var2, "expected digit (0-9) to follow minus sign, for valid numeric value");
         }

         char[] var6 = this._textBuffer.emptyAndGetCurrentSegment();
         var6[0] = (char)45;
         var6[1] = (char)((char)var2);
         if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(2);
            this._intLength = 1;
            var7 = JsonToken.NOT_AVAILABLE;
            this._currToken = var7;
            return var7;
         } else {
            var2 = this._inputBuffer[this._inputPtr];

            while(true) {
               if (var2 < 48) {
                  if (var2 == 46) {
                     this._intLength = var3 - 1;
                     ++this._inputPtr;
                     return this._startFloat(var6, var3, var2);
                  }
                  break;
               }

               if (var2 > 57) {
                  if (var2 != 101 && var2 != 69) {
                     break;
                  }

                  this._intLength = var3 - 1;
                  ++this._inputPtr;
                  return this._startFloat(var6, var3, var2);
               }

               char[] var4 = var6;
               if (var3 >= var6.length) {
                  var4 = this._textBuffer.expandCurrentSegment();
               }

               int var5 = var3 + 1;
               var4[var3] = (char)((char)var2);
               var2 = this._inputPtr + 1;
               this._inputPtr = var2;
               if (var2 >= this._inputEnd) {
                  this._minorState = 26;
                  this._textBuffer.setCurrentLength(var5);
                  var7 = JsonToken.NOT_AVAILABLE;
                  this._currToken = var7;
                  return var7;
               }

               var2 = this._inputBuffer[this._inputPtr] & 255;
               var3 = var5;
               var6 = var4;
            }

            this._intLength = var3 - 1;
            this._textBuffer.setCurrentLength(var3);
            return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
         }
      }
   }

   protected JsonToken _startNullToken() throws IOException {
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
                     return this._valueComplete(JsonToken.VALUE_NULL);
                  }
               }
            }
         }
      }

      this._minorState = 16;
      return this._finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
   }

   protected JsonToken _startNumberLeadingZero() throws IOException {
      int var1 = this._inputPtr;
      if (var1 >= this._inputEnd) {
         this._minorState = 24;
         JsonToken var5 = JsonToken.NOT_AVAILABLE;
         this._currToken = var5;
         return var5;
      } else {
         byte[] var2 = this._inputBuffer;
         int var3 = var1 + 1;
         var1 = var2[var1] & 255;
         char[] var4;
         if (var1 < 48) {
            if (var1 == 46) {
               this._inputPtr = var3;
               this._intLength = 1;
               var4 = this._textBuffer.emptyAndGetCurrentSegment();
               var4[0] = (char)48;
               return this._startFloat(var4, 1, var1);
            }
         } else {
            if (var1 <= 57) {
               return this._finishNumberLeadingZeroes();
            }

            if (var1 == 101 || var1 == 69) {
               this._inputPtr = var3;
               this._intLength = 1;
               var4 = this._textBuffer.emptyAndGetCurrentSegment();
               var4[0] = (char)48;
               return this._startFloat(var4, 1, var1);
            }

            if (var1 != 93 && var1 != 125) {
               this.reportUnexpectedNumberChar(var1, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
         }

         return this._valueCompleteInt(0, "0");
      }
   }

   protected JsonToken _startPositiveNumber(int var1) throws IOException {
      this._numberNegative = false;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      var2[0] = (char)((char)var1);
      JsonToken var6;
      if (this._inputPtr >= this._inputEnd) {
         this._minorState = 26;
         this._textBuffer.setCurrentLength(1);
         var6 = JsonToken.NOT_AVAILABLE;
         this._currToken = var6;
         return var6;
      } else {
         int var3 = this._inputBuffer[this._inputPtr] & 255;
         var1 = 1;

         while(true) {
            if (var3 < 48) {
               if (var3 == 46) {
                  this._intLength = var1;
                  ++this._inputPtr;
                  return this._startFloat(var2, var1, var3);
               }
               break;
            }

            if (var3 > 57) {
               if (var3 != 101 && var3 != 69) {
                  break;
               }

               this._intLength = var1;
               ++this._inputPtr;
               return this._startFloat(var2, var1, var3);
            }

            char[] var4 = var2;
            if (var1 >= var2.length) {
               var4 = this._textBuffer.expandCurrentSegment();
            }

            int var5 = var1 + 1;
            var4[var1] = (char)((char)var3);
            var1 = this._inputPtr + 1;
            this._inputPtr = var1;
            if (var1 >= this._inputEnd) {
               this._minorState = 26;
               this._textBuffer.setCurrentLength(var5);
               var6 = JsonToken.NOT_AVAILABLE;
               this._currToken = var6;
               return var6;
            }

            var3 = this._inputBuffer[this._inputPtr] & 255;
            var1 = var5;
            var2 = var4;
         }

         this._intLength = var1;
         this._textBuffer.setCurrentLength(var1);
         return this._valueComplete(JsonToken.VALUE_NUMBER_INT);
      }
   }

   protected JsonToken _startString() throws IOException {
      int var1 = this._inputPtr;
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var3 = _icUTF8;
      int var4 = Math.min(this._inputEnd, var2.length + var1);
      byte[] var5 = this._inputBuffer;

      int var6;
      for(var6 = 0; var1 < var4; ++var6) {
         int var7 = var5[var1] & 255;
         if (var3[var7] != 0) {
            if (var7 == 34) {
               this._inputPtr = var1 + 1;
               this._textBuffer.setCurrentLength(var6);
               return this._valueComplete(JsonToken.VALUE_STRING);
            }
            break;
         }

         ++var1;
         var2[var6] = (char)((char)var7);
      }

      this._textBuffer.setCurrentLength(var6);
      this._inputPtr = var1;
      return this._finishRegularString();
   }

   protected JsonToken _startTrueToken() throws IOException {
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
                     return this._valueComplete(JsonToken.VALUE_TRUE);
                  }
               }
            }
         }
      }

      this._minorState = 17;
      return this._finishKeywordToken("true", 1, JsonToken.VALUE_TRUE);
   }

   protected JsonToken _startUnexpectedValue(boolean var1, int var2) throws IOException {
      if (var2 != 39) {
         label39: {
            if (var2 == 73) {
               return this._finishNonStdToken(1, 1);
            }

            if (var2 == 78) {
               return this._finishNonStdToken(0, 1);
            }

            if (var2 != 93) {
               if (var2 == 125) {
                  break label39;
               }

               if (var2 == 43) {
                  return this._finishNonStdToken(2, 1);
               }

               if (var2 != 44) {
                  break label39;
               }
            } else if (!this._parsingContext.inArray()) {
               break label39;
            }

            if ((this._features & FEAT_MASK_ALLOW_MISSING) != 0) {
               --this._inputPtr;
               return this._valueComplete(JsonToken.VALUE_NULL);
            }
         }
      } else if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
         return this._startAposString();
      }

      StringBuilder var3 = new StringBuilder();
      var3.append("expected a valid value ");
      var3.append(this._validJsonValueList());
      this._reportUnexpectedChar(var2, var3.toString());
      return null;
   }

   public void endOfInput() {
      this._endOfInput = true;
   }

   public void feedInput(byte[] var1, int var2, int var3) throws IOException {
      if (this._inputPtr < this._inputEnd) {
         this._reportError("Still have %d undecoded bytes, should not call 'feedInput'", this._inputEnd - this._inputPtr);
      }

      if (var3 < var2) {
         this._reportError("Input end (%d) may not be before start (%d)", var3, var2);
      }

      if (this._endOfInput) {
         this._reportError("Already closed, can not feed more input");
      }

      this._currInputProcessed += (long)this._origBufferLen;
      this._currInputRowStart = var2 - (this._inputEnd - this._currInputRowStart);
      this._currBufferStart = var2;
      this._inputBuffer = var1;
      this._inputPtr = var2;
      this._inputEnd = var3;
      this._origBufferLen = var3 - var2;
   }

   public ByteArrayFeeder getNonBlockingInputFeeder() {
      return this;
   }

   public final boolean needMoreInput() {
      boolean var1;
      if (this._inputPtr >= this._inputEnd && !this._endOfInput) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public JsonToken nextToken() throws IOException {
      if (this._inputPtr >= this._inputEnd) {
         if (this._closed) {
            return null;
         } else if (this._endOfInput) {
            return this._currToken == JsonToken.NOT_AVAILABLE ? this._finishTokenWithEOF() : this._eofAsNextToken();
         } else {
            return JsonToken.NOT_AVAILABLE;
         }
      } else if (this._currToken == JsonToken.NOT_AVAILABLE) {
         return this._finishToken();
      } else {
         this._numTypesValid = 0;
         this._tokenInputTotal = this._currInputProcessed + (long)this._inputPtr;
         this._binaryValue = null;
         byte[] var1 = this._inputBuffer;
         int var2 = this._inputPtr++;
         var2 = var1[var2] & 255;
         switch(this._majorState) {
         case 0:
            return this._startDocument(var2);
         case 1:
            return this._startValue(var2);
         case 2:
            return this._startFieldName(var2);
         case 3:
            return this._startFieldNameAfterComma(var2);
         case 4:
            return this._startValueExpectColon(var2);
         case 5:
            return this._startValue(var2);
         case 6:
            return this._startValueExpectComma(var2);
         default:
            VersionUtil.throwInternal();
            return null;
         }
      }
   }

   public int releaseBuffered(OutputStream var1) throws IOException {
      int var2 = this._inputEnd - this._inputPtr;
      if (var2 > 0) {
         var1.write(this._inputBuffer, this._inputPtr, var2);
      }

      return var2;
   }
}
