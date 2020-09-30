package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class WriterBasedJsonGenerator extends JsonGeneratorImpl {
   protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
   protected static final int SHORT_WRITE = 32;
   protected char[] _copyBuffer;
   protected SerializableString _currentEscape;
   protected char[] _entityBuffer;
   protected char[] _outputBuffer;
   protected int _outputEnd;
   protected int _outputHead;
   protected int _outputTail;
   protected char _quoteChar;
   protected final Writer _writer;

   @Deprecated
   public WriterBasedJsonGenerator(IOContext var1, int var2, ObjectCodec var3, Writer var4) {
      this(var1, var2, var3, var4, '"');
   }

   public WriterBasedJsonGenerator(IOContext var1, int var2, ObjectCodec var3, Writer var4, char var5) {
      super(var1, var2, var3);
      this._writer = var4;
      char[] var6 = var1.allocConcatBuffer();
      this._outputBuffer = var6;
      this._outputEnd = var6.length;
      this._quoteChar = (char)var5;
      if (var5 != '"') {
         this._outputEscapes = CharTypes.get7BitOutputEscapes(var5);
      }

   }

   private char[] _allocateCopyBuffer() {
      if (this._copyBuffer == null) {
         this._copyBuffer = this._ioContext.allocNameCopyBuffer(2000);
      }

      return this._copyBuffer;
   }

   private char[] _allocateEntityBuffer() {
      char[] var1 = new char[14];
      var1[0] = (char)92;
      var1[2] = (char)92;
      var1[3] = (char)117;
      var1[4] = (char)48;
      var1[5] = (char)48;
      var1[8] = (char)92;
      var1[9] = (char)117;
      this._entityBuffer = var1;
      return var1;
   }

   private void _appendCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      int var4;
      int var7;
      char[] var9;
      if (var2 >= 0) {
         if (this._outputTail + 2 > this._outputEnd) {
            this._flushBuffer();
         }

         var9 = this._outputBuffer;
         var7 = this._outputTail;
         var4 = var7 + 1;
         this._outputTail = var4;
         var9[var7] = (char)92;
         this._outputTail = var4 + 1;
         var9[var4] = (char)((char)var2);
      } else if (var2 != -2) {
         if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
         }

         var2 = this._outputTail;
         var9 = this._outputBuffer;
         var4 = var2 + 1;
         var9[var2] = (char)92;
         var2 = var4 + 1;
         var9[var4] = (char)117;
         char[] var6;
         if (var1 > 255) {
            int var5 = 255 & var1 >> 8;
            var4 = var2 + 1;
            var6 = HEX_CHARS;
            var9[var2] = (char)var6[var5 >> 4];
            var2 = var4 + 1;
            var9[var4] = (char)var6[var5 & 15];
            var1 = (char)(var1 & 255);
         } else {
            var4 = var2 + 1;
            var9[var2] = (char)48;
            var2 = var4 + 1;
            var9[var4] = (char)48;
         }

         var4 = var2 + 1;
         var6 = HEX_CHARS;
         var9[var2] = (char)var6[var1 >> 4];
         var9[var4] = (char)var6[var1 & 15];
         this._outputTail = var4 + 1;
      } else {
         SerializableString var3 = this._currentEscape;
         String var8;
         if (var3 == null) {
            var8 = this._characterEscapes.getEscapeSequence(var1).getValue();
         } else {
            var8 = var3.getValue();
            this._currentEscape = null;
         }

         var7 = var8.length();
         if (this._outputTail + var7 > this._outputEnd) {
            this._flushBuffer();
            if (var7 > this._outputEnd) {
               this._writer.write(var8);
               return;
            }
         }

         var8.getChars(0, var7, this._outputBuffer, this._outputTail);
         this._outputTail += var7;
      }
   }

   private int _prependOrWriteCharacterEscape(char[] var1, int var2, int var3, char var4, int var5) throws IOException, JsonGenerationException {
      char[] var9;
      if (var5 >= 0) {
         if (var2 > 1 && var2 < var3) {
            var2 -= 2;
            var1[var2] = (char)92;
            var1[var2 + 1] = (char)((char)var5);
         } else {
            var9 = this._entityBuffer;
            var1 = var9;
            if (var9 == null) {
               var1 = this._allocateEntityBuffer();
            }

            var1[1] = (char)((char)var5);
            this._writer.write(var1, 0, 2);
         }

         return var2;
      } else {
         int var7;
         if (var5 != -2) {
            if (var2 > 5 && var2 < var3) {
               var2 -= 6;
               var3 = var2 + 1;
               var1[var2] = (char)92;
               var2 = var3 + 1;
               var1[var3] = (char)117;
               if (var4 > 255) {
                  var5 = var4 >> 8 & 255;
                  var3 = var2 + 1;
                  var9 = HEX_CHARS;
                  var1[var2] = (char)var9[var5 >> 4];
                  var2 = var3 + 1;
                  var1[var3] = (char)var9[var5 & 15];
                  var4 = (char)(var4 & 255);
               } else {
                  var3 = var2 + 1;
                  var1[var2] = (char)48;
                  var2 = var3 + 1;
                  var1[var3] = (char)48;
               }

               var3 = var2 + 1;
               var9 = HEX_CHARS;
               var1[var2] = (char)var9[var4 >> 4];
               var1[var3] = (char)var9[var4 & 15];
               var2 = var3 - 5;
            } else {
               var9 = this._entityBuffer;
               var1 = var9;
               if (var9 == null) {
                  var1 = this._allocateEntityBuffer();
               }

               this._outputHead = this._outputTail;
               if (var4 > 255) {
                  var3 = var4 >> 8 & 255;
                  var7 = var4 & 255;
                  var9 = HEX_CHARS;
                  var1[10] = (char)var9[var3 >> 4];
                  var1[11] = (char)var9[var3 & 15];
                  var1[12] = (char)var9[var7 >> 4];
                  var1[13] = (char)var9[var7 & 15];
                  this._writer.write(var1, 8, 6);
               } else {
                  var9 = HEX_CHARS;
                  var1[6] = (char)var9[var4 >> 4];
                  var1[7] = (char)var9[var4 & 15];
                  this._writer.write(var1, 2, 6);
               }
            }

            return var2;
         } else {
            SerializableString var6 = this._currentEscape;
            String var8;
            if (var6 == null) {
               var8 = this._characterEscapes.getEscapeSequence(var4).getValue();
            } else {
               var8 = var6.getValue();
               this._currentEscape = null;
            }

            var7 = var8.length();
            if (var2 >= var7 && var2 < var3) {
               var2 -= var7;
               var8.getChars(0, var7, var1, var2);
            } else {
               this._writer.write(var8);
            }

            return var2;
         }
      }
   }

   private void _prependOrWriteCharacterEscape(char var1, int var2) throws IOException, JsonGenerationException {
      char[] var4;
      int var6;
      char[] var8;
      if (var2 >= 0) {
         var6 = this._outputTail;
         if (var6 >= 2) {
            var6 -= 2;
            this._outputHead = var6;
            var8 = this._outputBuffer;
            var8[var6] = (char)92;
            var8[var6 + 1] = (char)((char)var2);
         } else {
            var4 = this._entityBuffer;
            var8 = var4;
            if (var4 == null) {
               var8 = this._allocateEntityBuffer();
            }

            this._outputHead = this._outputTail;
            var8[1] = (char)((char)var2);
            this._writer.write(var8, 0, 2);
         }
      } else if (var2 != -2) {
         var2 = this._outputTail;
         if (var2 >= 6) {
            var8 = this._outputBuffer;
            var2 -= 6;
            this._outputHead = var2;
            var8[var2] = (char)92;
            ++var2;
            var8[var2] = (char)117;
            if (var1 > 255) {
               int var5 = var1 >> 8 & 255;
               ++var2;
               var4 = HEX_CHARS;
               var8[var2] = (char)var4[var5 >> 4];
               ++var2;
               var8[var2] = (char)var4[var5 & 15];
               var1 = (char)(var1 & 255);
            } else {
               ++var2;
               var8[var2] = (char)48;
               ++var2;
               var8[var2] = (char)48;
            }

            ++var2;
            var4 = HEX_CHARS;
            var8[var2] = (char)var4[var1 >> 4];
            var8[var2 + 1] = (char)var4[var1 & 15];
         } else {
            var4 = this._entityBuffer;
            var8 = var4;
            if (var4 == null) {
               var8 = this._allocateEntityBuffer();
            }

            this._outputHead = this._outputTail;
            if (var1 > 255) {
               var2 = var1 >> 8 & 255;
               var6 = var1 & 255;
               var4 = HEX_CHARS;
               var8[10] = (char)var4[var2 >> 4];
               var8[11] = (char)var4[var2 & 15];
               var8[12] = (char)var4[var6 >> 4];
               var8[13] = (char)var4[var6 & 15];
               this._writer.write(var8, 8, 6);
            } else {
               var4 = HEX_CHARS;
               var8[6] = (char)var4[var1 >> 4];
               var8[7] = (char)var4[var1 & 15];
               this._writer.write(var8, 2, 6);
            }

         }
      } else {
         SerializableString var3 = this._currentEscape;
         String var7;
         if (var3 == null) {
            var7 = this._characterEscapes.getEscapeSequence(var1).getValue();
         } else {
            var7 = var3.getValue();
            this._currentEscape = null;
         }

         var6 = var7.length();
         var2 = this._outputTail;
         if (var2 >= var6) {
            var2 -= var6;
            this._outputHead = var2;
            var7.getChars(0, var6, this._outputBuffer, var2);
         } else {
            this._outputHead = var2;
            this._writer.write(var7);
         }
      }
   }

   private int _readMore(InputStream var1, byte[] var2, int var3, int var4, int var5) throws IOException {
      byte var6 = 0;
      int var7 = var3;

      for(var3 = var6; var7 < var4; ++var7) {
         var2[var3] = (byte)var2[var7];
         ++var3;
      }

      var5 = Math.min(var5, var2.length);

      while(true) {
         var4 = var5 - var3;
         if (var4 == 0) {
            break;
         }

         var4 = var1.read(var2, var3, var4);
         if (var4 < 0) {
            return var3;
         }

         var4 += var3;
         var3 = var4;
         if (var4 >= 3) {
            var3 = var4;
            break;
         }
      }

      return var3;
   }

   private final void _writeFieldNameTail(SerializableString var1) throws IOException {
      char[] var3 = var1.asQuotedChars();
      this.writeRaw((char[])var3, 0, var3.length);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var3 = this._outputBuffer;
      int var2 = this._outputTail++;
      var3[var2] = (char)this._quoteChar;
   }

   private void _writeLongString(String var1) throws IOException {
      this._flushBuffer();
      int var2 = var1.length();
      int var3 = 0;

      while(true) {
         int var4 = this._outputEnd;
         int var5 = var4;
         if (var3 + var4 > var2) {
            var5 = var2 - var3;
         }

         var4 = var3 + var5;
         var1.getChars(var3, var4, this._outputBuffer, 0);
         if (this._characterEscapes != null) {
            this._writeSegmentCustom(var5);
         } else if (this._maximumNonEscapedChar != 0) {
            this._writeSegmentASCII(var5, this._maximumNonEscapedChar);
         } else {
            this._writeSegment(var5);
         }

         if (var4 >= var2) {
            return;
         }

         var3 = var4;
      }
   }

   private final void _writeNull() throws IOException {
      if (this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var1 = this._outputTail;
      char[] var2 = this._outputBuffer;
      var2[var1] = (char)110;
      ++var1;
      var2[var1] = (char)117;
      ++var1;
      var2[var1] = (char)108;
      ++var1;
      var2[var1] = (char)108;
      this._outputTail = var1 + 1;
   }

   private void _writeQuotedInt(int var1) throws IOException {
      if (this._outputTail + 13 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (char)this._quoteChar;
      var1 = NumberOutput.outputInt(var1, var2, var4);
      this._outputTail = var1;
      var2 = this._outputBuffer;
      this._outputTail = var1 + 1;
      var2[var1] = (char)this._quoteChar;
   }

   private void _writeQuotedLong(long var1) throws IOException {
      if (this._outputTail + 23 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = (char)this._quoteChar;
      var5 = NumberOutput.outputLong(var1, var3, var5);
      this._outputTail = var5;
      var3 = this._outputBuffer;
      this._outputTail = var5 + 1;
      var3[var5] = (char)this._quoteChar;
   }

   private void _writeQuotedRaw(String var1) throws IOException {
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var2 = this._outputBuffer;
      int var3 = this._outputTail++;
      var2[var3] = (char)this._quoteChar;
      this.writeRaw(var1);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      var3 = this._outputTail++;
      var4[var3] = (char)this._quoteChar;
   }

   private void _writeQuotedShort(short var1) throws IOException {
      if (this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (char)this._quoteChar;
      int var5 = NumberOutput.outputInt(var1, (char[])var2, var4);
      this._outputTail = var5;
      var2 = this._outputBuffer;
      this._outputTail = var5 + 1;
      var2[var5] = (char)this._quoteChar;
   }

   private void _writeSegment(int var1) throws IOException {
      int[] var2 = this._outputEscapes;
      int var3 = var2.length;
      int var4 = 0;

      char var6;
      for(int var5 = 0; var4 < var1; var5 = this._prependOrWriteCharacterEscape(this._outputBuffer, var4, var1, var6, var2[var6])) {
         int var7;
         while(true) {
            var6 = this._outputBuffer[var4];
            if (var6 < var3 && var2[var6] != 0) {
               break;
            }

            var7 = var4 + 1;
            var4 = var7;
            if (var7 >= var1) {
               var4 = var7;
               break;
            }
         }

         var7 = var4 - var5;
         if (var7 > 0) {
            this._writer.write(this._outputBuffer, var5, var7);
            if (var4 >= var1) {
               break;
            }
         }

         ++var4;
      }

   }

   private void _writeSegmentASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int[] var3 = this._outputEscapes;
      int var4 = Math.min(var3.length, var2 + 1);
      int var5 = 0;
      int var6 = 0;

      char var9;
      for(int var7 = 0; var5 < var1; var6 = this._prependOrWriteCharacterEscape(this._outputBuffer, var5, var1, var9, var7)) {
         int var8 = var7;
         var7 = var5;

         int var10;
         while(true) {
            var9 = this._outputBuffer[var7];
            if (var9 < var4) {
               var8 = var3[var9];
               var5 = var8;
               if (var8 != 0) {
                  var10 = var7;
                  var7 = var8;
                  break;
               }
            } else {
               var5 = var8;
               if (var9 > var2) {
                  byte var11 = -1;
                  var10 = var7;
                  var7 = var11;
                  break;
               }
            }

            var10 = var7 + 1;
            var7 = var10;
            var8 = var5;
            if (var10 >= var1) {
               var7 = var5;
               break;
            }
         }

         var5 = var10 - var6;
         if (var5 > 0) {
            this._writer.write(this._outputBuffer, var6, var5);
            if (var10 >= var1) {
               break;
            }
         }

         var5 = var10 + 1;
      }

   }

   private void _writeSegmentCustom(int var1) throws IOException, JsonGenerationException {
      int[] var2 = this._outputEscapes;
      int var3;
      if (this._maximumNonEscapedChar < 1) {
         var3 = 65535;
      } else {
         var3 = this._maximumNonEscapedChar;
      }

      int var4 = Math.min(var2.length, var3 + 1);
      CharacterEscapes var5 = this._characterEscapes;
      int var6 = 0;
      int var7 = 0;

      char var10;
      for(int var8 = 0; var6 < var1; var7 = this._prependOrWriteCharacterEscape(this._outputBuffer, var6, var1, var10, var8)) {
         int var9 = var8;
         var8 = var6;

         while(true) {
            var10 = this._outputBuffer[var8];
            if (var10 < var4) {
               var9 = var2[var10];
               var6 = var9;
               if (var9 != 0) {
                  var6 = var8;
                  var8 = var9;
                  break;
               }
            } else {
               byte var13;
               if (var10 > var3) {
                  var13 = -1;
                  var6 = var8;
                  var8 = var13;
                  break;
               }

               SerializableString var11 = var5.getEscapeSequence(var10);
               this._currentEscape = var11;
               var6 = var9;
               if (var11 != null) {
                  var13 = -2;
                  var6 = var8;
                  var8 = var13;
                  break;
               }
            }

            int var12 = var8 + 1;
            var8 = var12;
            var9 = var6;
            if (var12 >= var1) {
               var8 = var6;
               var6 = var12;
               break;
            }
         }

         var9 = var6 - var7;
         if (var9 > 0) {
            this._writer.write(this._outputBuffer, var7, var9);
            if (var6 >= var1) {
               break;
            }
         }

         ++var6;
      }

   }

   private void _writeString(String var1) throws IOException {
      int var2 = var1.length();
      int var3 = this._outputEnd;
      if (var2 > var3) {
         this._writeLongString(var1);
      } else {
         if (this._outputTail + var2 > var3) {
            this._flushBuffer();
         }

         var1.getChars(0, var2, this._outputBuffer, this._outputTail);
         if (this._characterEscapes != null) {
            this._writeStringCustom(var2);
         } else if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(var2, this._maximumNonEscapedChar);
         } else {
            this._writeString2(var2);
         }

      }
   }

   private void _writeString(char[] var1, int var2, int var3) throws IOException {
      if (this._characterEscapes != null) {
         this._writeStringCustom(var1, var2, var3);
      } else if (this._maximumNonEscapedChar != 0) {
         this._writeStringASCII(var1, var2, var3, this._maximumNonEscapedChar);
      } else {
         int var4 = var3 + var2;
         int[] var5 = this._outputEscapes;
         int var6 = var5.length;

         while(var2 < var4) {
            var3 = var2;

            int var9;
            while(true) {
               char var7 = var1[var3];
               if (var7 < var6 && var5[var7] != 0) {
                  break;
               }

               var9 = var3 + 1;
               var3 = var9;
               if (var9 >= var4) {
                  var3 = var9;
                  break;
               }
            }

            var9 = var3 - var2;
            if (var9 < 32) {
               if (this._outputTail + var9 > this._outputEnd) {
                  this._flushBuffer();
               }

               if (var9 > 0) {
                  System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var9);
                  this._outputTail += var9;
               }
            } else {
               this._flushBuffer();
               this._writer.write(var1, var2, var9);
            }

            if (var3 >= var4) {
               break;
            }

            var2 = var3 + 1;
            char var8 = var1[var3];
            this._appendCharacterEscape(var8, var5[var8]);
         }

      }
   }

   private void _writeString2(int var1) throws IOException {
      int var2 = this._outputTail + var1;
      int[] var3 = this._outputEscapes;
      var1 = var3.length;

      while(this._outputTail < var2) {
         while(true) {
            char[] var4 = this._outputBuffer;
            int var5 = this._outputTail;
            char var6 = var4[var5];
            if (var6 < var1 && var3[var6] != 0) {
               int var8 = this._outputHead;
               var5 -= var8;
               if (var5 > 0) {
                  this._writer.write(var4, var8, var5);
               }

               var4 = this._outputBuffer;
               var5 = this._outputTail++;
               char var7 = var4[var5];
               this._prependOrWriteCharacterEscape(var7, var3[var7]);
            } else {
               var5 = this._outputTail + 1;
               this._outputTail = var5;
               if (var5 >= var2) {
                  return;
               }
            }
         }
      }

   }

   private void _writeString2(SerializableString var1) throws IOException {
      char[] var3 = var1.asQuotedChars();
      int var2 = var3.length;
      if (var2 < 32) {
         if (var2 > this._outputEnd - this._outputTail) {
            this._flushBuffer();
         }

         System.arraycopy(var3, 0, this._outputBuffer, this._outputTail, var2);
         this._outputTail += var2;
      } else {
         this._flushBuffer();
         this._writer.write(var3, 0, var2);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var3 = this._outputBuffer;
      var2 = this._outputTail++;
      var3[var2] = (char)this._quoteChar;
   }

   private void _writeStringASCII(int var1, int var2) throws IOException, JsonGenerationException {
      int var3 = this._outputTail + var1;
      int[] var4 = this._outputEscapes;
      int var5 = Math.min(var4.length, var2 + 1);

      while(this._outputTail < var3) {
         char var6;
         while(true) {
            var6 = this._outputBuffer[this._outputTail];
            if (var6 < var5) {
               var1 = var4[var6];
               if (var1 != 0) {
                  break;
               }
            } else if (var6 > var2) {
               var1 = -1;
               break;
            }

            var1 = this._outputTail + 1;
            this._outputTail = var1;
            if (var1 >= var3) {
               return;
            }
         }

         int var7 = this._outputTail;
         int var8 = this._outputHead;
         var7 -= var8;
         if (var7 > 0) {
            this._writer.write(this._outputBuffer, var8, var7);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var6, var1);
      }

   }

   private void _writeStringASCII(char[] var1, int var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var5 = var3 + var2;
      int[] var6 = this._outputEscapes;
      int var7 = Math.min(var6.length, var4 + 1);
      byte var8 = 0;
      var3 = var2;
      var2 = var8;

      while(var3 < var5) {
         int var12 = var3;
         int var9 = var2;

         char var10;
         int var11;
         while(true) {
            var10 = var1[var12];
            if (var10 < var7) {
               var11 = var6[var10];
               var2 = var11;
               if (var11 != 0) {
                  var2 = var11;
                  break;
               }
            } else {
               var2 = var9;
               if (var10 > var4) {
                  var2 = -1;
                  break;
               }
            }

            var11 = var12 + 1;
            var9 = var2;
            var12 = var11;
            if (var11 >= var5) {
               var12 = var11;
               break;
            }
         }

         var11 = var12 - var3;
         if (var11 < 32) {
            if (this._outputTail + var11 > this._outputEnd) {
               this._flushBuffer();
            }

            if (var11 > 0) {
               System.arraycopy(var1, var3, this._outputBuffer, this._outputTail, var11);
               this._outputTail += var11;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var3, var11);
         }

         if (var12 >= var5) {
            break;
         }

         var3 = var12 + 1;
         this._appendCharacterEscape(var10, var2);
      }

   }

   private void _writeStringCustom(int var1) throws IOException, JsonGenerationException {
      int var2 = this._outputTail + var1;
      int[] var3 = this._outputEscapes;
      int var4;
      if (this._maximumNonEscapedChar < 1) {
         var4 = 65535;
      } else {
         var4 = this._maximumNonEscapedChar;
      }

      int var5 = Math.min(var3.length, var4 + 1);
      CharacterEscapes var6 = this._characterEscapes;

      while(this._outputTail < var2) {
         char var7;
         while(true) {
            var7 = this._outputBuffer[this._outputTail];
            if (var7 < var5) {
               var1 = var3[var7];
               if (var1 != 0) {
                  break;
               }
            } else {
               if (var7 > var4) {
                  var1 = -1;
                  break;
               }

               SerializableString var8 = var6.getEscapeSequence(var7);
               this._currentEscape = var8;
               if (var8 != null) {
                  var1 = -2;
                  break;
               }
            }

            var1 = this._outputTail + 1;
            this._outputTail = var1;
            if (var1 >= var2) {
               return;
            }
         }

         int var9 = this._outputTail;
         int var10 = this._outputHead;
         var9 -= var10;
         if (var9 > 0) {
            this._writer.write(this._outputBuffer, var10, var9);
         }

         ++this._outputTail;
         this._prependOrWriteCharacterEscape(var7, var1);
      }

   }

   private void _writeStringCustom(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = var3 + var2;
      int[] var5 = this._outputEscapes;
      int var6;
      if (this._maximumNonEscapedChar < 1) {
         var6 = 65535;
      } else {
         var6 = this._maximumNonEscapedChar;
      }

      int var7 = Math.min(var5.length, var6 + 1);
      CharacterEscapes var8 = this._characterEscapes;
      byte var9 = 0;
      var3 = var2;
      var2 = var9;

      while(var3 < var4) {
         int var14 = var3;
         int var10 = var2;

         char var11;
         int var12;
         while(true) {
            var11 = var1[var14];
            if (var11 < var7) {
               var12 = var5[var11];
               var2 = var12;
               if (var12 != 0) {
                  var2 = var12;
                  break;
               }
            } else {
               if (var11 > var6) {
                  var2 = -1;
                  break;
               }

               SerializableString var13 = var8.getEscapeSequence(var11);
               this._currentEscape = var13;
               var2 = var10;
               if (var13 != null) {
                  var2 = -2;
                  break;
               }
            }

            var12 = var14 + 1;
            var10 = var2;
            var14 = var12;
            if (var12 >= var4) {
               var14 = var12;
               break;
            }
         }

         var12 = var14 - var3;
         if (var12 < 32) {
            if (this._outputTail + var12 > this._outputEnd) {
               this._flushBuffer();
            }

            if (var12 > 0) {
               System.arraycopy(var1, var3, this._outputBuffer, this._outputTail, var12);
               this._outputTail += var12;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var3, var12);
         }

         if (var14 >= var4) {
            break;
         }

         var3 = var14 + 1;
         this._appendCharacterEscape(var11, var2);
      }

   }

   private void writeRawLong(String var1) throws IOException {
      int var2 = this._outputEnd;
      int var3 = this._outputTail;
      var2 -= var3;
      var1.getChars(0, var2, this._outputBuffer, var3);
      this._outputTail += var2;
      this._flushBuffer();
      var3 = var1.length() - var2;

      while(true) {
         int var4 = this._outputEnd;
         if (var3 <= var4) {
            var1.getChars(var2, var2 + var3, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = var3;
            return;
         }

         int var5 = var2 + var4;
         var1.getChars(var2, var5, this._outputBuffer, 0);
         this._outputHead = 0;
         this._outputTail = var4;
         this._flushBuffer();
         var3 -= var4;
         var2 = var5;
      }
   }

   protected void _flushBuffer() throws IOException {
      int var1 = this._outputTail;
      int var2 = this._outputHead;
      var1 -= var2;
      if (var1 > 0) {
         this._outputHead = 0;
         this._outputTail = 0;
         this._writer.write(this._outputBuffer, var2, var1);
      }

   }

   protected void _releaseBuffers() {
      char[] var1 = this._outputBuffer;
      if (var1 != null) {
         this._outputBuffer = null;
         this._ioContext.releaseConcatBuffer(var1);
      }

      var1 = this._copyBuffer;
      if (var1 != null) {
         this._copyBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }

   }

   protected final void _verifyValueWrite(String var1) throws IOException {
      int var2 = this._writeContext.writeValue();
      if (this._cfgPrettyPrinter != null) {
         this._verifyPrettyValueWrite(var1, var2);
      } else {
         byte var5;
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  if (var2 != 5) {
                     return;
                  }

                  this._reportCantWriteValueExpectName(var1);
                  return;
               }

               if (this._rootValueSeparator != null) {
                  this.writeRaw(this._rootValueSeparator.getValue());
               }

               return;
            }

            var5 = 58;
         } else {
            var5 = 44;
         }

         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var4 = this._outputBuffer;
         int var3 = this._outputTail++;
         var4[var3] = (char)var5;
      }
   }

   protected final int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3) throws IOException, JsonGenerationException {
      int var4 = this._outputEnd - 6;
      int var5 = var1.getMaxLineLength();
      byte var6 = 2;
      int var7 = var5 >> 2;
      int var8 = -3;
      int var9 = 0;
      int var10 = 0;
      var5 = 0;

      while(true) {
         int var11 = var8;
         int var12 = var9;
         int var13 = var10;
         if (var9 > var8) {
            var13 = this._readMore(var2, var3, var9, var10, var3.length);
            if (var13 < 3) {
               var7 = var5;
               if (var13 > 0) {
                  if (this._outputTail > var4) {
                     this._flushBuffer();
                  }

                  var9 = var3[0] << 16;
                  byte var18;
                  if (1 < var13) {
                     var9 |= (var3[1] & 255) << 8;
                     var18 = var6;
                  } else {
                     var18 = 1;
                  }

                  var5 += var18;
                  this._outputTail = var1.encodeBase64Partial(var9, var18, (char[])this._outputBuffer, this._outputTail);
                  var7 = var5;
               }

               return var7;
            }

            var11 = var13 - 3;
            var12 = 0;
         }

         if (this._outputTail > var4) {
            this._flushBuffer();
         }

         var10 = var12 + 1;
         byte var20 = var3[var12];
         var8 = var10 + 1;
         byte var21 = var3[var10];
         var12 = var8 + 1;
         byte var19 = var3[var8];
         int var14 = var5 + 3;
         int var15 = var1.encodeBase64Chunk((var21 & 255 | var20 << 8) << 8 | var19 & 255, this._outputBuffer, this._outputTail);
         this._outputTail = var15;
         int var16 = var7 - 1;
         var7 = var16;
         var8 = var11;
         var9 = var12;
         var10 = var13;
         var5 = var14;
         if (var16 <= 0) {
            char[] var17 = this._outputBuffer;
            var5 = var15 + 1;
            this._outputTail = var5;
            var17[var15] = (char)92;
            this._outputTail = var5 + 1;
            var17[var5] = (char)110;
            var7 = var1.getMaxLineLength() >> 2;
            var8 = var11;
            var9 = var12;
            var10 = var13;
            var5 = var14;
         }
      }
   }

   protected final int _writeBinary(Base64Variant var1, InputStream var2, byte[] var3, int var4) throws IOException, JsonGenerationException {
      int var5 = this._outputEnd - 6;
      int var6 = var1.getMaxLineLength();
      byte var7 = 2;
      int var8 = var6 >> 2;
      int var9 = -3;
      byte var10 = 0;
      int var11 = 0;
      var6 = var4;
      var4 = var10;

      int var12;
      int var21;
      while(true) {
         var21 = var4;
         var12 = var11;
         if (var6 <= 2) {
            break;
         }

         var12 = var9;
         int var13 = var4;
         var21 = var11;
         if (var4 > var9) {
            var12 = this._readMore(var2, var3, var4, var11, var6);
            if (var12 < 3) {
               var21 = 0;
               break;
            }

            var4 = var12 - 3;
            var13 = 0;
            var21 = var12;
            var12 = var4;
         }

         if (this._outputTail > var5) {
            this._flushBuffer();
         }

         var11 = var13 + 1;
         byte var18 = var3[var13];
         var9 = var11 + 1;
         byte var22 = var3[var11];
         var13 = var9 + 1;
         byte var20 = var3[var9];
         int var14 = var6 - 3;
         int var15 = var1.encodeBase64Chunk((var22 & 255 | var18 << 8) << 8 | var20 & 255, this._outputBuffer, this._outputTail);
         this._outputTail = var15;
         int var16 = var8 - 1;
         var8 = var16;
         var9 = var12;
         var4 = var13;
         var11 = var21;
         var6 = var14;
         if (var16 <= 0) {
            char[] var17 = this._outputBuffer;
            var4 = var15 + 1;
            this._outputTail = var4;
            var17[var15] = (char)92;
            this._outputTail = var4 + 1;
            var17[var4] = (char)110;
            var8 = var1.getMaxLineLength() >> 2;
            var9 = var12;
            var4 = var13;
            var11 = var21;
            var6 = var14;
         }
      }

      var4 = var6;
      if (var6 > 0) {
         var8 = this._readMore(var2, var3, var21, var12, var6);
         var4 = var6;
         if (var8 > 0) {
            if (this._outputTail > var5) {
               this._flushBuffer();
            }

            var4 = var3[0] << 16;
            byte var19;
            if (1 < var8) {
               var4 |= (var3[1] & 255) << 8;
               var19 = var7;
            } else {
               var19 = 1;
            }

            this._outputTail = var1.encodeBase64Partial(var4, var19, (char[])this._outputBuffer, this._outputTail);
            var4 = var6 - var19;
         }
      }

      return var4;
   }

   protected final void _writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var5 = this._outputEnd - 6;

      int var6;
      int var8;
      for(var6 = var1.getMaxLineLength() >> 2; var3 <= var4 - 3; var3 = var8) {
         if (this._outputTail > var5) {
            this._flushBuffer();
         }

         int var7 = var3 + 1;
         byte var10 = var2[var3];
         var8 = var7 + 1;
         var7 = var1.encodeBase64Chunk((var10 << 8 | var2[var7] & 255) << 8 | var2[var8] & 255, this._outputBuffer, this._outputTail);
         this._outputTail = var7;
         --var6;
         var3 = var6;
         if (var6 <= 0) {
            char[] var9 = this._outputBuffer;
            var3 = var7 + 1;
            this._outputTail = var3;
            var9[var7] = (char)92;
            this._outputTail = var3 + 1;
            var9[var3] = (char)110;
            var3 = var1.getMaxLineLength() >> 2;
         }

         ++var8;
         var6 = var3;
      }

      var8 = var4 - var3;
      if (var8 > 0) {
         if (this._outputTail > var5) {
            this._flushBuffer();
         }

         var6 = var2[var3] << 16;
         var4 = var6;
         if (var8 == 2) {
            var4 = var6 | (var2[var3 + 1] & 255) << 8;
         }

         this._outputTail = var1.encodeBase64Partial(var4, var8, this._outputBuffer, this._outputTail);
      }

   }

   protected final void _writeFieldName(SerializableString var1, boolean var2) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3;
         int var4;
         if (var2) {
            var3 = this._outputBuffer;
            var4 = this._outputTail++;
            var3[var4] = (char)44;
         }

         char[] var6;
         if (this._cfgUnqNames) {
            var6 = var1.asQuotedChars();
            this.writeRaw((char[])var6, 0, var6.length);
         } else {
            var3 = this._outputBuffer;
            var4 = this._outputTail;
            int var5 = var4 + 1;
            this._outputTail = var5;
            var3[var4] = (char)this._quoteChar;
            var4 = var1.appendQuoted(var3, var5);
            if (var4 < 0) {
               this._writeFieldNameTail(var1);
            } else {
               var4 += this._outputTail;
               this._outputTail = var4;
               if (var4 >= this._outputEnd) {
                  this._flushBuffer();
               }

               var6 = this._outputBuffer;
               var4 = this._outputTail++;
               var6[var4] = (char)this._quoteChar;
            }
         }
      }
   }

   protected final void _writeFieldName(String var1, boolean var2) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3;
         int var4;
         if (var2) {
            var3 = this._outputBuffer;
            var4 = this._outputTail++;
            var3[var4] = (char)44;
         }

         if (this._cfgUnqNames) {
            this._writeString(var1);
         } else {
            var3 = this._outputBuffer;
            var4 = this._outputTail++;
            var3[var4] = (char)this._quoteChar;
            this._writeString(var1);
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            char[] var5 = this._outputBuffer;
            var4 = this._outputTail++;
            var5[var4] = (char)this._quoteChar;
         }
      }
   }

   protected final void _writePPFieldName(SerializableString var1, boolean var2) throws IOException {
      if (var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      char[] var5 = var1.asQuotedChars();
      if (this._cfgUnqNames) {
         this.writeRaw((char[])var5, 0, var5.length);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var4 = this._outputTail++;
         var3[var4] = (char)this._quoteChar;
         this.writeRaw((char[])var5, 0, var5.length);
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         var5 = this._outputBuffer;
         var4 = this._outputTail++;
         var5[var4] = (char)this._quoteChar;
      }

   }

   protected final void _writePPFieldName(String var1, boolean var2) throws IOException {
      if (var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if (this._cfgUnqNames) {
         this._writeString(var1);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var4 = this._outputTail++;
         var3[var4] = (char)this._quoteChar;
         this._writeString(var1);
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var5 = this._outputBuffer;
         var4 = this._outputTail++;
         var5[var4] = (char)this._quoteChar;
      }

   }

   public boolean canWriteFormattedNumbers() {
      return true;
   }

   public void close() throws IOException {
      super.close();
      if (this._outputBuffer != null && this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
         label34:
         while(true) {
            while(true) {
               JsonStreamContext var1 = this.getOutputContext();
               if (var1.inArray()) {
                  this.writeEndArray();
               } else {
                  if (!var1.inObject()) {
                     break label34;
                  }

                  this.writeEndObject();
               }
            }
         }
      }

      this._flushBuffer();
      this._outputHead = 0;
      this._outputTail = 0;
      if (this._writer != null) {
         if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
            if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
               this._writer.flush();
            }
         } else {
            this._writer.close();
         }
      }

      this._releaseBuffers();
   }

   public void flush() throws IOException {
      this._flushBuffer();
      if (this._writer != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
         this._writer.flush();
      }

   }

   public int getOutputBuffered() {
      return Math.max(0, this._outputTail - this._outputHead);
   }

   public Object getOutputTarget() {
      return this._writer;
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      int var5;
      byte[] var22;
      label153: {
         char[] var4 = this._outputBuffer;
         var5 = this._outputTail++;
         var4[var5] = (char)this._quoteChar;
         var22 = this._ioContext.allocBase64Buffer();
         Throwable var10000;
         boolean var10001;
         if (var3 < 0) {
            label144:
            try {
               var5 = this._writeBinary(var1, var2, var22);
               break label153;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label144;
            }
         } else {
            label157: {
               int var6;
               try {
                  var6 = this._writeBinary(var1, var2, var22, var3);
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label157;
               }

               var5 = var3;
               if (var6 <= 0) {
                  break label153;
               }

               try {
                  StringBuilder var20 = new StringBuilder();
                  var20.append("Too few bytes available: missing ");
                  var20.append(var6);
                  var20.append(" bytes (out of ");
                  var20.append(var3);
                  var20.append(")");
                  this._reportError(var20.toString());
               } catch (Throwable var17) {
                  var10000 = var17;
                  var10001 = false;
                  break label157;
               }

               var5 = var3;
               break label153;
            }
         }

         Throwable var19 = var10000;
         this._ioContext.releaseBase64Buffer(var22);
         throw var19;
      }

      this._ioContext.releaseBase64Buffer(var22);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var21 = this._outputBuffer;
      var3 = this._outputTail++;
      var21[var3] = (char)this._quoteChar;
      return var5;
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var5 = this._outputBuffer;
      int var6 = this._outputTail++;
      var5[var6] = (char)this._quoteChar;
      this._writeBinary(var1, var2, var3, var4 + var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var7 = this._outputBuffer;
      var3 = this._outputTail++;
      var7[var3] = (char)this._quoteChar;
   }

   public void writeBoolean(boolean var1) throws IOException {
      this._verifyValueWrite("write a boolean value");
      if (this._outputTail + 5 >= this._outputEnd) {
         this._flushBuffer();
      }

      int var2 = this._outputTail;
      char[] var3 = this._outputBuffer;
      if (var1) {
         var3[var2] = (char)116;
         ++var2;
         var3[var2] = (char)114;
         ++var2;
         var3[var2] = (char)117;
         ++var2;
         var3[var2] = (char)101;
      } else {
         var3[var2] = (char)102;
         ++var2;
         var3[var2] = (char)97;
         ++var2;
         var3[var2] = (char)108;
         ++var2;
         var3[var2] = (char)115;
         ++var2;
         var3[var2] = (char)101;
      }

      this._outputTail = var2 + 1;
   }

   public void writeEndArray() throws IOException {
      if (!this._writeContext.inArray()) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Current context not Array but ");
         var1.append(this._writeContext.typeDesc());
         this._reportError(var1.toString());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (char)93;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   public void writeEndObject() throws IOException {
      if (!this._writeContext.inObject()) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Current context not Object but ");
         var1.append(this._writeContext.typeDesc());
         this._reportError(var1.toString());
      }

      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (char)125;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      boolean var3 = true;
      if (var2 != 1) {
         var3 = false;
      }

      this._writeFieldName(var1, var3);
   }

   public void writeFieldName(String var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1);
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      boolean var3 = true;
      if (var2 != 1) {
         var3 = false;
      }

      this._writeFieldName(var1, var3);
   }

   public void writeNull() throws IOException {
      this._verifyValueWrite("write a null");
      this._writeNull();
   }

   public void writeNumber(double var1) throws IOException {
      if (this._cfgNumbersAsStrings || NumberOutput.notFinite(var1) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
         this.writeString(String.valueOf(var1));
      } else {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      }
   }

   public void writeNumber(float var1) throws IOException {
      if (this._cfgNumbersAsStrings || NumberOutput.notFinite(var1) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS)) {
         this.writeString(String.valueOf(var1));
      } else {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      }
   }

   public void writeNumber(int var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
         if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputInt(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeNumber(long var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedLong(var1);
      } else {
         if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputLong(var1, this._outputBuffer, this._outputTail);
      }
   }

   public void writeNumber(String var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1);
      } else {
         this.writeRaw(var1);
      }

   }

   public void writeNumber(BigDecimal var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (var1 == null) {
         this._writeNull();
      } else if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(this._asString(var1));
      } else {
         this.writeRaw(this._asString(var1));
      }

   }

   public void writeNumber(BigInteger var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (var1 == null) {
         this._writeNull();
      } else if (this._cfgNumbersAsStrings) {
         this._writeQuotedRaw(var1.toString());
      } else {
         this.writeRaw(var1.toString());
      }

   }

   public void writeNumber(short var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._outputTail = NumberOutput.outputInt(var1, (char[])this._outputBuffer, this._outputTail);
      }
   }

   public void writeRaw(char var1) throws IOException {
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var2 = this._outputBuffer;
      int var3 = this._outputTail++;
      var2[var3] = (char)var1;
   }

   public void writeRaw(SerializableString var1) throws IOException {
      int var2 = var1.appendUnquoted(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this.writeRaw(var1.getValue());
      } else {
         this._outputTail += var2;
      }
   }

   public void writeRaw(String var1) throws IOException {
      int var2 = var1.length();
      int var3 = this._outputEnd - this._outputTail;
      int var4 = var3;
      if (var3 == 0) {
         this._flushBuffer();
         var4 = this._outputEnd - this._outputTail;
      }

      if (var4 >= var2) {
         var1.getChars(0, var2, this._outputBuffer, this._outputTail);
         this._outputTail += var2;
      } else {
         this.writeRawLong(var1);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      int var4 = this._outputEnd - this._outputTail;
      int var5 = var4;
      if (var4 < var3) {
         this._flushBuffer();
         var5 = this._outputEnd - this._outputTail;
      }

      if (var5 >= var3) {
         var1.getChars(var2, var2 + var3, this._outputBuffer, this._outputTail);
         this._outputTail += var3;
      } else {
         this.writeRawLong(var1.substring(var2, var3 + var2));
      }

   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      if (var3 < 32) {
         if (var3 > this._outputEnd - this._outputTail) {
            this._flushBuffer();
         }

         System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
         this._outputTail += var3;
      } else {
         this._flushBuffer();
         this._writer.write(var1, var2, var3);
      }
   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }

   public void writeStartArray() throws IOException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var1 = this._outputBuffer;
         int var2 = this._outputTail++;
         var1[var2] = (char)91;
      }

   }

   public void writeStartArray(int var1) throws IOException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var2 = this._outputBuffer;
         var1 = this._outputTail++;
         var2[var1] = (char)91;
      }

   }

   public void writeStartObject() throws IOException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var1 = this._outputBuffer;
         int var2 = this._outputTail++;
         var1[var2] = (char)123;
      }

   }

   public void writeStartObject(Object var1) throws IOException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext(var1);
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (char)123;
      }

   }

   public void writeString(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (char)this._quoteChar;
      var3 = var1.appendQuoted(var2, var4);
      if (var3 < 0) {
         this._writeString2(var1);
      } else {
         var3 += this._outputTail;
         this._outputTail = var3;
         if (var3 >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var5 = this._outputBuffer;
         var3 = this._outputTail++;
         var5[var3] = (char)this._quoteChar;
      }
   }

   public void writeString(Reader var1, int var2) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._reportError("null reader");
      }

      int var3;
      if (var2 >= 0) {
         var3 = var2;
      } else {
         var3 = Integer.MAX_VALUE;
      }

      if (this._outputTail + var2 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      int var5 = this._outputTail++;
      var4[var5] = (char)this._quoteChar;

      for(var4 = this._allocateCopyBuffer(); var3 > 0; var3 -= var5) {
         var5 = var1.read(var4, 0, Math.min(var3, var4.length));
         if (var5 <= 0) {
            break;
         }

         if (this._outputTail + var2 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._writeString(var4, 0, var5);
      }

      if (this._outputTail + var2 >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var6 = this._outputBuffer;
      var5 = this._outputTail++;
      var6[var5] = (char)this._quoteChar;
      if (var3 > 0 && var2 >= 0) {
         this._reportError("Didn't read enough from reader");
      }

   }

   public void writeString(String var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._writeNull();
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var2 = this._outputBuffer;
         int var3 = this._outputTail++;
         var2[var3] = (char)this._quoteChar;
         this._writeString(var1);
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         char[] var4 = this._outputBuffer;
         var3 = this._outputTail++;
         var4[var3] = (char)this._quoteChar;
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      int var5 = this._outputTail++;
      var4[var5] = (char)this._quoteChar;
      this._writeString(var1, var2, var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail++;
      var1[var2] = (char)this._quoteChar;
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._reportUnsupportedOperation();
   }
}
