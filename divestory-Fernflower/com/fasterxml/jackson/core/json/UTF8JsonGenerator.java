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
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTF8JsonGenerator extends JsonGeneratorImpl {
   private static final byte BYTE_0 = 48;
   private static final byte BYTE_BACKSLASH = 92;
   private static final byte BYTE_COLON = 58;
   private static final byte BYTE_COMMA = 44;
   private static final byte BYTE_LBRACKET = 91;
   private static final byte BYTE_LCURLY = 123;
   private static final byte BYTE_RBRACKET = 93;
   private static final byte BYTE_RCURLY = 125;
   private static final byte BYTE_u = 117;
   private static final byte[] FALSE_BYTES = new byte[]{102, 97, 108, 115, 101};
   private static final byte[] HEX_CHARS = CharTypes.copyHexBytes();
   private static final int MAX_BYTES_TO_BUFFER = 512;
   private static final byte[] NULL_BYTES = new byte[]{110, 117, 108, 108};
   private static final byte[] TRUE_BYTES = new byte[]{116, 114, 117, 101};
   protected boolean _bufferRecyclable;
   protected char[] _charBuffer;
   protected final int _charBufferLength;
   protected byte[] _entityBuffer;
   protected byte[] _outputBuffer;
   protected final int _outputEnd;
   protected final int _outputMaxContiguous;
   protected final OutputStream _outputStream;
   protected int _outputTail;
   protected byte _quoteChar;

   @Deprecated
   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4) {
      this(var1, var2, var3, var4, '"');
   }

   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4, char var5) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._quoteChar = (byte)((byte)var5);
      if (var5 != '"') {
         this._outputEscapes = CharTypes.get7BitOutputEscapes(var5);
      }

      this._bufferRecyclable = true;
      byte[] var7 = var1.allocWriteEncodingBuffer();
      this._outputBuffer = var7;
      var2 = var7.length;
      this._outputEnd = var2;
      this._outputMaxContiguous = var2 >> 3;
      char[] var6 = var1.allocConcatBuffer();
      this._charBuffer = var6;
      this._charBufferLength = var6.length;
      if (this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
         this.setHighestNonEscapedChar(127);
      }

   }

   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4, char var5, byte[] var6, int var7, boolean var8) {
      super(var1, var2, var3);
      this._outputStream = var4;
      this._quoteChar = (byte)((byte)var5);
      if (var5 != '"') {
         this._outputEscapes = CharTypes.get7BitOutputEscapes(var5);
      }

      this._bufferRecyclable = var8;
      this._outputTail = var7;
      this._outputBuffer = var6;
      var2 = var6.length;
      this._outputEnd = var2;
      this._outputMaxContiguous = var2 >> 3;
      char[] var9 = var1.allocConcatBuffer();
      this._charBuffer = var9;
      this._charBufferLength = var9.length;
   }

   @Deprecated
   public UTF8JsonGenerator(IOContext var1, int var2, ObjectCodec var3, OutputStream var4, byte[] var5, int var6, boolean var7) {
      this(var1, var2, var3, var4, '"', var5, var6, var7);
   }

   private final int _handleLongCustomEscape(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IOException, JsonGenerationException {
      int var6 = var4.length;
      int var7 = var2;
      if (var2 + var6 > var3) {
         this._outputTail = var2;
         this._flushBuffer();
         var2 = this._outputTail;
         var7 = var2;
         if (var6 > var1.length) {
            this._outputStream.write(var4, 0, var6);
            return var2;
         }
      }

      System.arraycopy(var4, 0, var1, var7, var6);
      var2 = var7 + var6;
      if (var5 * 6 + var2 > var3) {
         this._outputTail = var2;
         this._flushBuffer();
         return this._outputTail;
      } else {
         return var2;
      }
   }

   private final int _outputMultiByteChar(int var1, int var2) throws IOException {
      byte[] var3 = this._outputBuffer;
      int var4;
      if (var1 >= 55296 && var1 <= 57343) {
         var4 = var2 + 1;
         var3[var2] = (byte)92;
         var2 = var4 + 1;
         var3[var4] = (byte)117;
         var4 = var2 + 1;
         byte[] var5 = HEX_CHARS;
         var3[var2] = (byte)var5[var1 >> 12 & 15];
         var2 = var4 + 1;
         var3[var4] = (byte)var5[var1 >> 8 & 15];
         var4 = var2 + 1;
         var3[var2] = (byte)var5[var1 >> 4 & 15];
         var2 = var4 + 1;
         var3[var4] = (byte)var5[var1 & 15];
         var1 = var2;
      } else {
         var4 = var2 + 1;
         var3[var2] = (byte)((byte)(var1 >> 12 | 224));
         var2 = var4 + 1;
         var3[var4] = (byte)((byte)(var1 >> 6 & 63 | 128));
         var3[var2] = (byte)((byte)(var1 & 63 | 128));
         var1 = var2 + 1;
      }

      return var1;
   }

   private final int _outputRawMultiByteChar(int var1, char[] var2, int var3, int var4) throws IOException {
      if (var1 >= 55296 && var1 <= 57343) {
         if (var3 >= var4 || var2 == null) {
            this._reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", var1));
         }

         this._outputSurrogates(var1, var2[var3]);
         return var3 + 1;
      } else {
         byte[] var6 = this._outputBuffer;
         int var5 = this._outputTail;
         var4 = var5 + 1;
         this._outputTail = var4;
         var6[var5] = (byte)((byte)(var1 >> 12 | 224));
         var5 = var4 + 1;
         this._outputTail = var5;
         var6[var4] = (byte)((byte)(var1 >> 6 & 63 | 128));
         this._outputTail = var5 + 1;
         var6[var5] = (byte)((byte)(var1 & 63 | 128));
         return var3;
      }
   }

   private final int _readMore(InputStream var1, byte[] var2, int var3, int var4, int var5) throws IOException {
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

   private final void _writeBytes(byte[] var1) throws IOException {
      int var2 = var1.length;
      if (this._outputTail + var2 > this._outputEnd) {
         this._flushBuffer();
         if (var2 > 512) {
            this._outputStream.write(var1, 0, var2);
            return;
         }
      }

      System.arraycopy(var1, 0, this._outputBuffer, this._outputTail, var2);
      this._outputTail += var2;
   }

   private final void _writeBytes(byte[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + var3 > this._outputEnd) {
         this._flushBuffer();
         if (var3 > 512) {
            this._outputStream.write(var1, var2, var3);
            return;
         }
      }

      System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   private final int _writeCustomEscape(byte[] var1, int var2, SerializableString var3, int var4) throws IOException, JsonGenerationException {
      byte[] var6 = var3.asUnquotedUTF8();
      int var5 = var6.length;
      if (var5 > 6) {
         return this._handleLongCustomEscape(var1, var2, this._outputEnd, var6, var4);
      } else {
         System.arraycopy(var6, 0, var1, var2, var5);
         return var2 + var5;
      }
   }

   private final void _writeCustomStringSegment2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7;
      if (this._maximumNonEscapedChar <= 0) {
         var7 = 65535;
      } else {
         var7 = this._maximumNonEscapedChar;
      }

      CharacterEscapes var8 = this._characterEscapes;
      int var9 = var2;
      var2 = var4;

      while(true) {
         while(var9 < var3) {
            var4 = var9 + 1;
            char var10 = var1.charAt(var9);
            if (var10 <= 127) {
               if (var6[var10] == 0) {
                  var5[var2] = (byte)((byte)var10);
                  var9 = var4;
                  ++var2;
                  continue;
               }

               var9 = var6[var10];
               if (var9 > 0) {
                  int var13 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var13 + 1;
                  var5[var13] = (byte)((byte)var9);
               } else if (var9 == -2) {
                  SerializableString var11 = var8.getEscapeSequence(var10);
                  if (var11 == null) {
                     StringBuilder var12 = new StringBuilder();
                     var12.append("Invalid custom escape definitions; custom escape not found for character code 0x");
                     var12.append(Integer.toHexString(var10));
                     var12.append(", although was supposed to have one");
                     this._reportError(var12.toString());
                  }

                  var2 = this._writeCustomEscape(var5, var2, var11, var3 - var4);
               } else {
                  var2 = this._writeGenericEscape(var10, var2);
               }
            } else if (var10 > var7) {
               var2 = this._writeGenericEscape(var10, var2);
            } else {
               SerializableString var14 = var8.getEscapeSequence(var10);
               if (var14 != null) {
                  var2 = this._writeCustomEscape(var5, var2, var14, var3 - var4);
               } else if (var10 <= 2047) {
                  var9 = var2 + 1;
                  var5[var2] = (byte)((byte)(var10 >> 6 | 192));
                  var2 = var9 + 1;
                  var5[var9] = (byte)((byte)(var10 & 63 | 128));
               } else {
                  var2 = this._outputMultiByteChar(var10, var2);
               }
            }

            var9 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private final void _writeCustomStringSegment2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7;
      if (this._maximumNonEscapedChar <= 0) {
         var7 = 65535;
      } else {
         var7 = this._maximumNonEscapedChar;
      }

      CharacterEscapes var8 = this._characterEscapes;
      int var9 = var2;
      var2 = var4;

      while(true) {
         while(var9 < var3) {
            var4 = var9 + 1;
            char var13 = var1[var9];
            int var10;
            if (var13 <= 127) {
               if (var6[var13] == 0) {
                  var5[var2] = (byte)((byte)var13);
                  var9 = var4;
                  ++var2;
                  continue;
               }

               var10 = var6[var13];
               if (var10 > 0) {
                  var9 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var9 + 1;
                  var5[var9] = (byte)((byte)var10);
               } else if (var10 == -2) {
                  SerializableString var11 = var8.getEscapeSequence(var13);
                  if (var11 == null) {
                     StringBuilder var12 = new StringBuilder();
                     var12.append("Invalid custom escape definitions; custom escape not found for character code 0x");
                     var12.append(Integer.toHexString(var13));
                     var12.append(", although was supposed to have one");
                     this._reportError(var12.toString());
                  }

                  var2 = this._writeCustomEscape(var5, var2, var11, var3 - var4);
               } else {
                  var2 = this._writeGenericEscape(var13, var2);
               }
            } else if (var13 > var7) {
               var2 = this._writeGenericEscape(var13, var2);
            } else {
               SerializableString var14 = var8.getEscapeSequence(var13);
               if (var14 != null) {
                  var2 = this._writeCustomEscape(var5, var2, var14, var3 - var4);
               } else if (var13 <= 2047) {
                  var10 = var2 + 1;
                  var5[var2] = (byte)((byte)(var13 >> 6 | 192));
                  var2 = var10 + 1;
                  var5[var10] = (byte)((byte)(var13 & 63 | 128));
               } else {
                  var2 = this._outputMultiByteChar(var13, var2);
               }
            }

            var9 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private int _writeGenericEscape(int var1, int var2) throws IOException {
      byte[] var3 = this._outputBuffer;
      int var4 = var2 + 1;
      var3[var2] = (byte)92;
      var2 = var4 + 1;
      var3[var4] = (byte)117;
      byte[] var6;
      if (var1 > 255) {
         var4 = 255 & var1 >> 8;
         int var5 = var2 + 1;
         var6 = HEX_CHARS;
         var3[var2] = (byte)var6[var4 >> 4];
         var2 = var5 + 1;
         var3[var5] = (byte)var6[var4 & 15];
         var1 &= 255;
      } else {
         var4 = var2 + 1;
         var3[var2] = (byte)48;
         var2 = var4 + 1;
         var3[var4] = (byte)48;
      }

      var4 = var2 + 1;
      var6 = HEX_CHARS;
      var3[var2] = (byte)var6[var1 >> 4];
      var3[var4] = (byte)var6[var1 & 15];
      return var4 + 1;
   }

   private final void _writeNull() throws IOException {
      if (this._outputTail + 4 >= this._outputEnd) {
         this._flushBuffer();
      }

      System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
      this._outputTail += 4;
   }

   private final void _writeQuotedInt(int var1) throws IOException {
      if (this._outputTail + 13 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (byte)this._quoteChar;
      var1 = NumberOutput.outputInt(var1, var2, var4);
      this._outputTail = var1;
      var2 = this._outputBuffer;
      this._outputTail = var1 + 1;
      var2[var1] = (byte)this._quoteChar;
   }

   private final void _writeQuotedLong(long var1) throws IOException {
      if (this._outputTail + 23 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = (byte)this._quoteChar;
      var5 = NumberOutput.outputLong(var1, var3, var5);
      this._outputTail = var5;
      var3 = this._outputBuffer;
      this._outputTail = var5 + 1;
      var3[var5] = (byte)this._quoteChar;
   }

   private final void _writeQuotedRaw(String var1) throws IOException {
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      int var3 = this._outputTail++;
      var2[var3] = (byte)this._quoteChar;
      this.writeRaw(var1);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      var3 = this._outputTail++;
      var4[var3] = (byte)this._quoteChar;
   }

   private final void _writeQuotedShort(short var1) throws IOException {
      if (this._outputTail + 8 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (byte)this._quoteChar;
      int var5 = NumberOutput.outputInt(var1, (byte[])var2, var4);
      this._outputTail = var5;
      var2 = this._outputBuffer;
      this._outputTail = var5 + 1;
      var2[var5] = (byte)this._quoteChar;
   }

   private void _writeRawSegment(char[] var1, int var2, int var3) throws IOException {
      label20:
      while(true) {
         int var9;
         if (var2 < var3) {
            do {
               char var4 = var1[var2];
               byte[] var5;
               int var7;
               if (var4 > 127) {
                  var9 = var2 + 1;
                  char var8 = var1[var2];
                  if (var8 < 2048) {
                     var5 = this._outputBuffer;
                     int var6 = this._outputTail;
                     var7 = var6 + 1;
                     this._outputTail = var7;
                     var5[var6] = (byte)((byte)(var8 >> 6 | 192));
                     this._outputTail = var7 + 1;
                     var5[var7] = (byte)((byte)(var8 & 63 | 128));
                     var2 = var9;
                     continue label20;
                  }

                  var2 = this._outputRawMultiByteChar(var8, var1, var9, var3);
                  continue label20;
               }

               var5 = this._outputBuffer;
               var7 = this._outputTail++;
               var5[var7] = (byte)((byte)var4);
               var9 = var2 + 1;
               var2 = var9;
            } while(var9 < var3);
         }

         return;
      }
   }

   private final void _writeSegmentedRaw(char[] var1, int var2, int var3) throws IOException {
      int var4 = this._outputEnd;
      byte[] var5 = this._outputBuffer;
      int var6 = var3 + var2;

      while(var2 < var6) {
         while(true) {
            char var7 = var1[var2];
            if (var7 >= 128) {
               if (this._outputTail + 3 >= this._outputEnd) {
                  this._flushBuffer();
               }

               var3 = var2 + 1;
               char var8 = var1[var2];
               if (var8 < 2048) {
                  int var9 = this._outputTail;
                  var2 = var9 + 1;
                  this._outputTail = var2;
                  var5[var9] = (byte)((byte)(var8 >> 6 | 192));
                  this._outputTail = var2 + 1;
                  var5[var2] = (byte)((byte)(var8 & 63 | 128));
                  var2 = var3;
               } else {
                  var2 = this._outputRawMultiByteChar(var8, var1, var3, var6);
               }
            } else {
               if (this._outputTail >= var4) {
                  this._flushBuffer();
               }

               var3 = this._outputTail++;
               var5[var3] = (byte)((byte)var7);
               var3 = var2 + 1;
               var2 = var3;
               if (var3 >= var6) {
                  return;
               }
            }
         }
      }

   }

   private final void _writeStringSegment(String var1, int var2, int var3) throws IOException {
      int var4 = var3 + var2;
      var3 = this._outputTail;
      byte[] var5 = this._outputBuffer;

      for(int[] var6 = this._outputEscapes; var2 < var4; ++var3) {
         char var7 = var1.charAt(var2);
         if (var7 > 127 || var6[var7] != 0) {
            break;
         }

         var5[var3] = (byte)((byte)var7);
         ++var2;
      }

      this._outputTail = var3;
      if (var2 < var4) {
         if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(var1, var2, var4);
         } else if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(var1, var2, var4);
         } else {
            this._writeStringSegmentASCII2(var1, var2, var4);
         }
      }

   }

   private final void _writeStringSegment(char[] var1, int var2, int var3) throws IOException {
      int var4 = var3 + var2;
      var3 = this._outputTail;
      byte[] var5 = this._outputBuffer;

      for(int[] var6 = this._outputEscapes; var2 < var4; ++var3) {
         char var7 = var1[var2];
         if (var7 > 127 || var6[var7] != 0) {
            break;
         }

         var5[var3] = (byte)((byte)var7);
         ++var2;
      }

      this._outputTail = var3;
      if (var2 < var4) {
         if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(var1, var2, var4);
         } else if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(var1, var2, var4);
         } else {
            this._writeStringSegmentASCII2(var1, var2, var4);
         }
      }

   }

   private final void _writeStringSegment2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = var2;
      var2 = var4;

      while(true) {
         while(var7 < var3) {
            var4 = var7 + 1;
            char var8 = var1.charAt(var7);
            if (var8 <= 127) {
               if (var6[var8] == 0) {
                  var5[var2] = (byte)((byte)var8);
                  var7 = var4;
                  ++var2;
                  continue;
               }

               var7 = var6[var8];
               if (var7 > 0) {
                  int var9 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var9 + 1;
                  var5[var9] = (byte)((byte)var7);
               } else {
                  var2 = this._writeGenericEscape(var8, var2);
               }
            } else if (var8 <= 2047) {
               var7 = var2 + 1;
               var5[var2] = (byte)((byte)(var8 >> 6 | 192));
               var2 = var7 + 1;
               var5[var7] = (byte)((byte)(var8 & 63 | 128));
            } else {
               var2 = this._outputMultiByteChar(var8, var2);
            }

            var7 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private final void _writeStringSegment2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = var2;
      var2 = var4;

      while(true) {
         while(var7 < var3) {
            var4 = var7 + 1;
            char var8 = var1[var7];
            if (var8 <= 127) {
               if (var6[var8] == 0) {
                  var5[var2] = (byte)((byte)var8);
                  var7 = var4;
                  ++var2;
                  continue;
               }

               var7 = var6[var8];
               if (var7 > 0) {
                  int var9 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var9 + 1;
                  var5[var9] = (byte)((byte)var7);
               } else {
                  var2 = this._writeGenericEscape(var8, var2);
               }
            } else if (var8 <= 2047) {
               var7 = var2 + 1;
               var5[var2] = (byte)((byte)(var8 >> 6 | 192));
               var2 = var7 + 1;
               var5[var7] = (byte)((byte)(var8 & 63 | 128));
            } else {
               var2 = this._outputMultiByteChar(var8, var2);
            }

            var7 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private final void _writeStringSegmentASCII2(String var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar;
      int var8 = var2;
      var2 = var4;

      while(true) {
         while(var8 < var3) {
            var4 = var8 + 1;
            char var9 = var1.charAt(var8);
            if (var9 <= 127) {
               if (var6[var9] == 0) {
                  var5[var2] = (byte)((byte)var9);
                  var8 = var4;
                  ++var2;
                  continue;
               }

               var8 = var6[var9];
               if (var8 > 0) {
                  int var10 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var10 + 1;
                  var5[var10] = (byte)((byte)var8);
               } else {
                  var2 = this._writeGenericEscape(var9, var2);
               }
            } else if (var9 > var7) {
               var2 = this._writeGenericEscape(var9, var2);
            } else if (var9 <= 2047) {
               var8 = var2 + 1;
               var5[var2] = (byte)((byte)(var9 >> 6 | 192));
               var2 = var8 + 1;
               var5[var8] = (byte)((byte)(var9 & 63 | 128));
            } else {
               var2 = this._outputMultiByteChar(var9, var2);
            }

            var8 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private final void _writeStringSegmentASCII2(char[] var1, int var2, int var3) throws IOException {
      if (this._outputTail + (var3 - var2) * 6 > this._outputEnd) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      byte[] var5 = this._outputBuffer;
      int[] var6 = this._outputEscapes;
      int var7 = this._maximumNonEscapedChar;
      int var8 = var2;
      var2 = var4;

      while(true) {
         while(var8 < var3) {
            var4 = var8 + 1;
            char var10 = var1[var8];
            int var9;
            if (var10 <= 127) {
               if (var6[var10] == 0) {
                  var5[var2] = (byte)((byte)var10);
                  var8 = var4;
                  ++var2;
                  continue;
               }

               var9 = var6[var10];
               if (var9 > 0) {
                  var8 = var2 + 1;
                  var5[var2] = (byte)92;
                  var2 = var8 + 1;
                  var5[var8] = (byte)((byte)var9);
               } else {
                  var2 = this._writeGenericEscape(var10, var2);
               }
            } else if (var10 > var7) {
               var2 = this._writeGenericEscape(var10, var2);
            } else if (var10 <= 2047) {
               var9 = var2 + 1;
               var5[var2] = (byte)((byte)(var10 >> 6 | 192));
               var2 = var9 + 1;
               var5[var9] = (byte)((byte)(var10 & 63 | 128));
            } else {
               var2 = this._outputMultiByteChar(var10, var2);
            }

            var8 = var4;
         }

         this._outputTail = var2;
         return;
      }
   }

   private final void _writeStringSegments(String var1, int var2, int var3) throws IOException {
      int var4;
      do {
         var4 = Math.min(this._outputMaxContiguous, var3);
         if (this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var4);
         var2 += var4;
         var4 = var3 - var4;
         var3 = var4;
      } while(var4 > 0);

   }

   private final void _writeStringSegments(String var1, boolean var2) throws IOException {
      int var4;
      if (var2) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         var4 = this._outputTail++;
         var3[var4] = (byte)this._quoteChar;
      }

      int var5 = var1.length();

      int var6;
      for(var4 = 0; var5 > 0; var5 -= var6) {
         var6 = Math.min(this._outputMaxContiguous, var5);
         if (this._outputTail + var6 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var4, var6);
         var4 += var6;
      }

      if (var2) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var7 = this._outputBuffer;
         var4 = this._outputTail++;
         var7[var4] = (byte)this._quoteChar;
      }

   }

   private final void _writeStringSegments(char[] var1, int var2, int var3) throws IOException {
      int var4;
      do {
         var4 = Math.min(this._outputMaxContiguous, var3);
         if (this._outputTail + var4 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var4);
         var2 += var4;
         var4 = var3 - var4;
         var3 = var4;
      } while(var4 > 0);

   }

   private final void _writeUTF8Segment(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int[] var4 = this._outputEscapes;

      for(int var5 = var2; var5 < var2 + var3; ++var5) {
         byte var6 = var1[var5];
         if (var6 >= 0 && var4[var6] != 0) {
            this._writeUTF8Segment2(var1, var2, var3);
            return;
         }
      }

      if (this._outputTail + var3 > this._outputEnd) {
         this._flushBuffer();
      }

      System.arraycopy(var1, var2, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   private final void _writeUTF8Segment2(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = this._outputTail;
      int var5 = var4;
      if (var3 * 6 + var4 > this._outputEnd) {
         this._flushBuffer();
         var5 = this._outputTail;
      }

      byte[] var6 = this._outputBuffer;
      int[] var7 = this._outputEscapes;
      var4 = var2;

      while(true) {
         while(true) {
            int var8 = var4;
            if (var4 >= var3 + var2) {
               this._outputTail = var5;
               return;
            }

            ++var4;
            byte var9 = var1[var8];
            if (var9 >= 0 && var7[var9] != 0) {
               var8 = var7[var9];
               if (var8 > 0) {
                  int var10 = var5 + 1;
                  var6[var5] = (byte)92;
                  var5 = var10 + 1;
                  var6[var10] = (byte)((byte)var8);
               } else {
                  var5 = this._writeGenericEscape(var9, var5);
               }
            } else {
               var6[var5] = (byte)var9;
               ++var5;
            }
         }
      }
   }

   private final void _writeUTF8Segments(byte[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4;
      do {
         var4 = Math.min(this._outputMaxContiguous, var3);
         this._writeUTF8Segment(var1, var2, var4);
         var2 += var4;
         var4 = var3 - var4;
         var3 = var4;
      } while(var4 > 0);

   }

   private final void _writeUnq(SerializableString var1) throws IOException {
      int var2 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var2;
      }

   }

   protected final void _flushBuffer() throws IOException {
      int var1 = this._outputTail;
      if (var1 > 0) {
         this._outputTail = 0;
         this._outputStream.write(this._outputBuffer, 0, var1);
      }

   }

   protected final void _outputSurrogates(int var1, int var2) throws IOException {
      var1 = this._decodeSurrogate(var1, var2);
      if (this._outputTail + 4 > this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var3 = this._outputBuffer;
      var2 = this._outputTail;
      int var4 = var2 + 1;
      this._outputTail = var4;
      var3[var2] = (byte)((byte)(var1 >> 18 | 240));
      var2 = var4 + 1;
      this._outputTail = var2;
      var3[var4] = (byte)((byte)(var1 >> 12 & 63 | 128));
      var4 = var2 + 1;
      this._outputTail = var4;
      var3[var2] = (byte)((byte)(var1 >> 6 & 63 | 128));
      this._outputTail = var4 + 1;
      var3[var4] = (byte)((byte)(var1 & 63 | 128));
   }

   protected void _releaseBuffers() {
      byte[] var1 = this._outputBuffer;
      if (var1 != null && this._bufferRecyclable) {
         this._outputBuffer = null;
         this._ioContext.releaseWriteEncodingBuffer(var1);
      }

      char[] var2 = this._charBuffer;
      if (var2 != null) {
         this._charBuffer = null;
         this._ioContext.releaseConcatBuffer(var2);
      }

   }

   protected final void _verifyValueWrite(String var1) throws IOException {
      int var2 = this._writeContext.writeValue();
      if (this._cfgPrettyPrinter != null) {
         this._verifyPrettyValueWrite(var1, var2);
      } else {
         byte[] var4;
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
                  var4 = this._rootValueSeparator.asUnquotedUTF8();
                  if (var4.length > 0) {
                     this._writeBytes(var4);
                  }
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

         var4 = this._outputBuffer;
         int var3 = this._outputTail++;
         var4[var3] = (byte)var5;
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
                  this._outputTail = var1.encodeBase64Partial(var9, var18, (byte[])this._outputBuffer, this._outputTail);
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
            byte[] var17 = this._outputBuffer;
            var5 = var15 + 1;
            this._outputTail = var5;
            var17[var15] = (byte)92;
            this._outputTail = var5 + 1;
            var17[var5] = (byte)110;
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
            byte[] var17 = this._outputBuffer;
            var4 = var15 + 1;
            this._outputTail = var4;
            var17[var15] = (byte)92;
            this._outputTail = var4 + 1;
            var17[var4] = (byte)110;
            var8 = var1.getMaxLineLength() >> 2;
            var9 = var12;
            var4 = var13;
            var11 = var21;
            var6 = var14;
         }
      }

      var4 = var6;
      if (var6 > 0) {
         var11 = this._readMore(var2, var3, var21, var12, var6);
         var4 = var6;
         if (var11 > 0) {
            if (this._outputTail > var5) {
               this._flushBuffer();
            }

            var8 = var3[0] << 16;
            byte var19;
            if (1 < var11) {
               var8 |= (var3[1] & 255) << 8;
               var19 = var7;
            } else {
               var19 = 1;
            }

            this._outputTail = var1.encodeBase64Partial(var8, var19, (byte[])this._outputBuffer, this._outputTail);
            var4 = var6 - var19;
         }
      }

      return var4;
   }

   protected final void _writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var5 = this._outputEnd - 6;
      int var6 = var1.getMaxLineLength() >> 2;
      int var7 = var3;

      for(var3 = var6; var7 <= var4 - 3; var7 = var6 + 1) {
         if (this._outputTail > var5) {
            this._flushBuffer();
         }

         int var8 = var7 + 1;
         byte var10 = var2[var7];
         var6 = var8 + 1;
         var8 = var1.encodeBase64Chunk((var10 << 8 | var2[var8] & 255) << 8 | var2[var6] & 255, this._outputBuffer, this._outputTail);
         this._outputTail = var8;
         var7 = var3 - 1;
         var3 = var7;
         if (var7 <= 0) {
            byte[] var9 = this._outputBuffer;
            var3 = var8 + 1;
            this._outputTail = var3;
            var9[var8] = (byte)92;
            this._outputTail = var3 + 1;
            var9[var3] = (byte)110;
            var3 = var1.getMaxLineLength() >> 2;
         }
      }

      var6 = var4 - var7;
      if (var6 > 0) {
         if (this._outputTail > var5) {
            this._flushBuffer();
         }

         var4 = var2[var7] << 16;
         var3 = var4;
         if (var6 == 2) {
            var3 = var4 | (var2[var7 + 1] & 255) << 8;
         }

         this._outputTail = var1.encodeBase64Partial(var3, var6, this._outputBuffer, this._outputTail);
      }

   }

   protected final void _writePPFieldName(SerializableString var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1.getValue());
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      if (var2 == 1) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      boolean var6 = this._cfgUnqNames ^ true;
      int var4;
      if (var6) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var3 = this._outputBuffer;
         var4 = this._outputTail++;
         var3[var4] = (byte)this._quoteChar;
      }

      var4 = var1.appendQuotedUTF8(this._outputBuffer, this._outputTail);
      if (var4 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var4;
      }

      if (var6) {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var5 = this._outputBuffer;
         var2 = this._outputTail++;
         var5[var2] = (byte)this._quoteChar;
      }

   }

   protected final void _writePPFieldName(String var1) throws IOException {
      int var2 = this._writeContext.writeFieldName(var1);
      if (var2 == 4) {
         this._reportError("Can not write a field name, expecting a value");
      }

      if (var2 == 1) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      if (this._cfgUnqNames) {
         this._writeStringSegments(var1, false);
      } else {
         var2 = var1.length();
         if (var2 > this._charBufferLength) {
            this._writeStringSegments(var1, true);
         } else {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var3 = this._outputBuffer;
            int var4 = this._outputTail++;
            var3[var4] = (byte)this._quoteChar;
            var1.getChars(0, var2, this._charBuffer, 0);
            if (var2 <= this._outputMaxContiguous) {
               if (this._outputTail + var2 > this._outputEnd) {
                  this._flushBuffer();
               }

               this._writeStringSegment((char[])this._charBuffer, 0, var2);
            } else {
               this._writeStringSegments((char[])this._charBuffer, 0, var2);
            }

            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var5 = this._outputBuffer;
            var2 = this._outputTail++;
            var5[var2] = (byte)this._quoteChar;
         }
      }
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
      this._outputTail = 0;
      if (this._outputStream != null) {
         if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
            if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
               this._outputStream.flush();
            }
         } else {
            this._outputStream.close();
         }
      }

      this._releaseBuffers();
   }

   public void flush() throws IOException {
      this._flushBuffer();
      if (this._outputStream != null && this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
         this._outputStream.flush();
      }

   }

   public int getOutputBuffered() {
      return this._outputTail;
   }

   public Object getOutputTarget() {
      return this._outputStream;
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4;
      int var5;
      label153: {
         var4 = this._outputBuffer;
         var5 = this._outputTail++;
         var4[var5] = (byte)this._quoteChar;
         var4 = this._ioContext.allocBase64Buffer();
         Throwable var10000;
         boolean var10001;
         if (var3 < 0) {
            label144:
            try {
               var5 = this._writeBinary(var1, var2, var4);
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
                  var6 = this._writeBinary(var1, var2, var4, var3);
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
         this._ioContext.releaseBase64Buffer(var4);
         throw var19;
      }

      this._ioContext.releaseBase64Buffer(var4);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var21 = this._outputBuffer;
      var3 = this._outputTail++;
      var21[var3] = (byte)this._quoteChar;
      return var5;
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write a binary value");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      int var6 = this._outputTail++;
      var5[var6] = (byte)this._quoteChar;
      this._writeBinary(var1, var2, var3, var4 + var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var7 = this._outputBuffer;
      var3 = this._outputTail++;
      var7[var3] = (byte)this._quoteChar;
   }

   public void writeBoolean(boolean var1) throws IOException {
      this._verifyValueWrite("write a boolean value");
      if (this._outputTail + 5 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2;
      if (var1) {
         var2 = TRUE_BYTES;
      } else {
         var2 = FALSE_BYTES;
      }

      int var3 = var2.length;
      System.arraycopy(var2, 0, this._outputBuffer, this._outputTail, var3);
      this._outputTail += var3;
   }

   public final void writeEndArray() throws IOException {
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

         byte[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (byte)93;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   public final void writeEndObject() throws IOException {
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

         byte[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (byte)125;
      }

      this._writeContext = this._writeContext.clearAndGetParent();
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1);
      } else {
         int var2 = this._writeContext.writeFieldName(var1.getValue());
         if (var2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
         }

         byte[] var3;
         if (var2 == 1) {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            var3 = this._outputBuffer;
            var2 = this._outputTail++;
            var3[var2] = (byte)44;
         }

         if (this._cfgUnqNames) {
            this._writeUnq(var1);
         } else {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            var3 = this._outputBuffer;
            var2 = this._outputTail;
            int var4 = var2 + 1;
            this._outputTail = var4;
            var3[var2] = (byte)this._quoteChar;
            var2 = var1.appendQuotedUTF8(var3, var4);
            if (var2 < 0) {
               this._writeBytes(var1.asQuotedUTF8());
            } else {
               this._outputTail += var2;
            }

            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var5 = this._outputBuffer;
            var2 = this._outputTail++;
            var5[var2] = (byte)this._quoteChar;
         }
      }
   }

   public void writeFieldName(String var1) throws IOException {
      if (this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1);
      } else {
         int var2 = this._writeContext.writeFieldName(var1);
         if (var2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
         }

         byte[] var3;
         if (var2 == 1) {
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            var3 = this._outputBuffer;
            var2 = this._outputTail++;
            var3[var2] = (byte)44;
         }

         if (this._cfgUnqNames) {
            this._writeStringSegments(var1, false);
         } else {
            int var4 = var1.length();
            if (var4 > this._charBufferLength) {
               this._writeStringSegments(var1, true);
            } else {
               if (this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               var3 = this._outputBuffer;
               var2 = this._outputTail;
               int var5 = var2 + 1;
               this._outputTail = var5;
               var3[var2] = (byte)this._quoteChar;
               if (var4 <= this._outputMaxContiguous) {
                  if (var5 + var4 > this._outputEnd) {
                     this._flushBuffer();
                  }

                  this._writeStringSegment((String)var1, 0, var4);
               } else {
                  this._writeStringSegments((String)var1, 0, var4);
               }

               if (this._outputTail >= this._outputEnd) {
                  this._flushBuffer();
               }

               byte[] var6 = this._outputBuffer;
               var2 = this._outputTail++;
               var6[var2] = (byte)this._quoteChar;
            }
         }
      }
   }

   public void writeNull() throws IOException {
      this._verifyValueWrite("write a null");
      this._writeNull();
   }

   public void writeNumber(double var1) throws IOException {
      if (this._cfgNumbersAsStrings || NumberOutput.notFinite(var1) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features)) {
         this.writeString(String.valueOf(var1));
      } else {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      }
   }

   public void writeNumber(float var1) throws IOException {
      if (this._cfgNumbersAsStrings || NumberOutput.notFinite(var1) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features)) {
         this.writeString(String.valueOf(var1));
      } else {
         this._verifyValueWrite("write a number");
         this.writeRaw(String.valueOf(var1));
      }
   }

   public void writeNumber(int var1) throws IOException {
      this._verifyValueWrite("write a number");
      if (this._outputTail + 11 >= this._outputEnd) {
         this._flushBuffer();
      }

      if (this._cfgNumbersAsStrings) {
         this._writeQuotedInt(var1);
      } else {
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
      if (this._outputTail + 6 >= this._outputEnd) {
         this._flushBuffer();
      }

      if (this._cfgNumbersAsStrings) {
         this._writeQuotedShort(var1);
      } else {
         this._outputTail = NumberOutput.outputInt(var1, (byte[])this._outputBuffer, this._outputTail);
      }
   }

   public void writeRaw(char var1) throws IOException {
      if (this._outputTail + 3 >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      int var3;
      if (var1 <= 127) {
         var3 = this._outputTail++;
         var2[var3] = (byte)((byte)var1);
      } else if (var1 < 2048) {
         var3 = this._outputTail;
         int var4 = var3 + 1;
         this._outputTail = var4;
         var2[var3] = (byte)((byte)(var1 >> 6 | 192));
         this._outputTail = var4 + 1;
         var2[var4] = (byte)((byte)(var1 & 63 | 128));
      } else {
         this._outputRawMultiByteChar(var1, (char[])null, 0, 0);
      }

   }

   public void writeRaw(SerializableString var1) throws IOException {
      int var2 = var1.appendUnquotedUTF8(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this._writeBytes(var1.asUnquotedUTF8());
      } else {
         this._outputTail += var2;
      }

   }

   public void writeRaw(String var1) throws IOException {
      int var2 = var1.length();
      char[] var3 = this._charBuffer;
      if (var2 <= var3.length) {
         var1.getChars(0, var2, var3, 0);
         this.writeRaw((char[])var3, 0, var2);
      } else {
         this.writeRaw((String)var1, 0, var2);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      char[] var4 = this._charBuffer;
      int var5 = var4.length;
      if (var3 <= var5) {
         var1.getChars(var2, var2 + var3, var4, 0);
         this.writeRaw((char[])var4, 0, var3);
      } else {
         int var6 = this._outputEnd;

         for(int var7 = Math.min(var5, (var6 >> 2) + (var6 >> 4)); var3 > 0; var3 -= var6) {
            var5 = Math.min(var7, var3);
            var1.getChars(var2, var2 + var5, var4, 0);
            if (this._outputTail + var7 * 3 > this._outputEnd) {
               this._flushBuffer();
            }

            var6 = var5;
            if (var5 > 1) {
               char var8 = var4[var5 - 1];
               var6 = var5;
               if (var8 >= '\ud800') {
                  var6 = var5;
                  if (var8 <= '\udbff') {
                     var6 = var5 - 1;
                  }
               }
            }

            this._writeRawSegment(var4, 0, var6);
            var2 += var6;
         }

      }
   }

   public final void writeRaw(char[] var1, int var2, int var3) throws IOException {
      int var4 = var3 + var3 + var3;
      int var5 = this._outputTail;
      int var6 = this._outputEnd;
      if (var5 + var4 > var6) {
         if (var6 < var4) {
            this._writeSegmentedRaw(var1, var2, var3);
            return;
         }

         this._flushBuffer();
      }

      var5 = var3 + var2;

      while(var2 < var5) {
         while(true) {
            char var9 = var1[var2];
            byte[] var7;
            if (var9 > 127) {
               var3 = var2 + 1;
               char var8 = var1[var2];
               if (var8 < 2048) {
                  var7 = this._outputBuffer;
                  var6 = this._outputTail;
                  var2 = var6 + 1;
                  this._outputTail = var2;
                  var7[var6] = (byte)((byte)(var8 >> 6 | 192));
                  this._outputTail = var2 + 1;
                  var7[var2] = (byte)((byte)(var8 & 63 | 128));
                  var2 = var3;
               } else {
                  var2 = this._outputRawMultiByteChar(var8, var1, var3, var5);
               }
            } else {
               var7 = this._outputBuffer;
               var3 = this._outputTail++;
               var7[var3] = (byte)((byte)var9);
               var3 = var2 + 1;
               var2 = var3;
               if (var3 >= var5) {
                  return;
               }
            }
         }
      }

   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      int var5 = this._outputTail++;
      var4[var5] = (byte)this._quoteChar;
      this._writeBytes(var1, var2, var3);
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail++;
      var1[var2] = (byte)this._quoteChar;
   }

   public void writeRawValue(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a raw (unencoded) value");
      int var2 = var1.appendUnquotedUTF8(this._outputBuffer, this._outputTail);
      if (var2 < 0) {
         this._writeBytes(var1.asUnquotedUTF8());
      } else {
         this._outputTail += var2;
      }

   }

   public final void writeStartArray() throws IOException {
      this._verifyValueWrite("start an array");
      this._writeContext = this._writeContext.createChildArrayContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartArray(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var1 = this._outputBuffer;
         int var2 = this._outputTail++;
         var1[var2] = (byte)91;
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

         byte[] var2 = this._outputBuffer;
         var1 = this._outputTail++;
         var2[var1] = (byte)91;
      }

   }

   public final void writeStartObject() throws IOException {
      this._verifyValueWrite("start an object");
      this._writeContext = this._writeContext.createChildObjectContext();
      if (this._cfgPrettyPrinter != null) {
         this._cfgPrettyPrinter.writeStartObject(this);
      } else {
         if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
         }

         byte[] var1 = this._outputBuffer;
         int var2 = this._outputTail++;
         var1[var2] = (byte)123;
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

         byte[] var3 = this._outputBuffer;
         int var2 = this._outputTail++;
         var3[var2] = (byte)123;
      }

   }

   public final void writeString(SerializableString var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var2 = this._outputBuffer;
      int var3 = this._outputTail;
      int var4 = var3 + 1;
      this._outputTail = var4;
      var2[var3] = (byte)this._quoteChar;
      var4 = var1.appendQuotedUTF8(var2, var4);
      if (var4 < 0) {
         this._writeBytes(var1.asQuotedUTF8());
      } else {
         this._outputTail += var4;
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      var4 = this._outputTail++;
      var5[var4] = (byte)this._quoteChar;
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

      char[] var4 = this._charBuffer;
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var5 = this._outputBuffer;
      int var6 = this._outputTail++;

      for(var5[var6] = (byte)this._quoteChar; var3 > 0; var3 -= var6) {
         var6 = var1.read(var4, 0, Math.min(var3, var4.length));
         if (var6 <= 0) {
            break;
         }

         if (this._outputTail + var2 >= this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegments((char[])var4, 0, var6);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var7 = this._outputBuffer;
      var6 = this._outputTail++;
      var7[var6] = (byte)this._quoteChar;
      if (var3 > 0 && var2 >= 0) {
         this._reportError("Didn't read enough from reader");
      }

   }

   public void writeString(String var1) throws IOException {
      this._verifyValueWrite("write a string");
      if (var1 == null) {
         this._writeNull();
      } else {
         int var2 = var1.length();
         if (var2 > this._outputMaxContiguous) {
            this._writeStringSegments(var1, true);
         } else {
            if (this._outputTail + var2 >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var3 = this._outputBuffer;
            int var4 = this._outputTail++;
            var3[var4] = (byte)this._quoteChar;
            this._writeStringSegment((String)var1, 0, var2);
            if (this._outputTail >= this._outputEnd) {
               this._flushBuffer();
            }

            byte[] var5 = this._outputBuffer;
            var4 = this._outputTail++;
            var5[var4] = (byte)this._quoteChar;
         }
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      int var5 = this._outputTail;
      int var6 = var5 + 1;
      this._outputTail = var6;
      var4[var5] = (byte)this._quoteChar;
      if (var3 <= this._outputMaxContiguous) {
         if (var6 + var3 > this._outputEnd) {
            this._flushBuffer();
         }

         this._writeStringSegment(var1, var2, var3);
      } else {
         this._writeStringSegments(var1, var2, var3);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var7 = this._outputBuffer;
      var2 = this._outputTail++;
      var7[var2] = (byte)this._quoteChar;
   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      this._verifyValueWrite("write a string");
      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      byte[] var4 = this._outputBuffer;
      int var5 = this._outputTail++;
      var4[var5] = (byte)this._quoteChar;
      if (var3 <= this._outputMaxContiguous) {
         this._writeUTF8Segment(var1, var2, var3);
      } else {
         this._writeUTF8Segments(var1, var2, var3);
      }

      if (this._outputTail >= this._outputEnd) {
         this._flushBuffer();
      }

      var1 = this._outputBuffer;
      var2 = this._outputTail++;
      var1[var2] = (byte)this._quoteChar;
   }
}
