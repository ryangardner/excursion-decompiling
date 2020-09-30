package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

final class ReaderInputStream extends InputStream {
   private ByteBuffer byteBuffer;
   private CharBuffer charBuffer;
   private boolean doneFlushing;
   private boolean draining;
   private final CharsetEncoder encoder;
   private boolean endOfInput;
   private final Reader reader;
   private final byte[] singleByte;

   ReaderInputStream(Reader var1, Charset var2, int var3) {
      this(var1, var2.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), var3);
   }

   ReaderInputStream(Reader var1, CharsetEncoder var2, int var3) {
      boolean var4 = true;
      this.singleByte = new byte[1];
      this.reader = (Reader)Preconditions.checkNotNull(var1);
      this.encoder = (CharsetEncoder)Preconditions.checkNotNull(var2);
      if (var3 <= 0) {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "bufferSize must be positive: %s", var3);
      var2.reset();
      CharBuffer var5 = CharBuffer.allocate(var3);
      this.charBuffer = var5;
      var5.flip();
      this.byteBuffer = ByteBuffer.allocate(var3);
   }

   private static int availableCapacity(Buffer var0) {
      return var0.capacity() - var0.limit();
   }

   private int drain(byte[] var1, int var2, int var3) {
      var3 = Math.min(var3, this.byteBuffer.remaining());
      this.byteBuffer.get(var1, var2, var3);
      return var3;
   }

   private static CharBuffer grow(CharBuffer var0) {
      CharBuffer var1 = CharBuffer.wrap(Arrays.copyOf(var0.array(), var0.capacity() * 2));
      var1.position(var0.position());
      var1.limit(var0.limit());
      return var1;
   }

   private void readMoreChars() throws IOException {
      if (availableCapacity(this.charBuffer) == 0) {
         if (this.charBuffer.position() > 0) {
            this.charBuffer.compact().flip();
         } else {
            this.charBuffer = grow(this.charBuffer);
         }
      }

      int var1 = this.charBuffer.limit();
      int var2 = this.reader.read(this.charBuffer.array(), var1, availableCapacity(this.charBuffer));
      if (var2 == -1) {
         this.endOfInput = true;
      } else {
         this.charBuffer.limit(var1 + var2);
      }

   }

   private void startDraining(boolean var1) {
      this.byteBuffer.flip();
      if (var1 && this.byteBuffer.remaining() == 0) {
         this.byteBuffer = ByteBuffer.allocate(this.byteBuffer.capacity() * 2);
      } else {
         this.draining = true;
      }

   }

   public void close() throws IOException {
      this.reader.close();
   }

   public int read() throws IOException {
      int var1;
      if (this.read(this.singleByte) == 1) {
         var1 = UnsignedBytes.toInt(this.singleByte[0]);
      } else {
         var1 = -1;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      if (var3 == 0) {
         return 0;
      } else {
         boolean var4 = this.endOfInput;
         int var5 = 0;

         while(true) {
            boolean var6 = var4;
            int var7 = var5;
            if (this.draining) {
               var7 = var5 + this.drain(var1, var2 + var5, var3 - var5);
               if (var7 == var3 || this.doneFlushing) {
                  if (var7 <= 0) {
                     var7 = -1;
                  }

                  return var7;
               }

               this.draining = false;
               this.byteBuffer.clear();
               var6 = var4;
            }

            while(true) {
               CoderResult var8;
               if (this.doneFlushing) {
                  var8 = CoderResult.UNDERFLOW;
               } else if (var6) {
                  var8 = this.encoder.flush(this.byteBuffer);
               } else {
                  var8 = this.encoder.encode(this.charBuffer, this.byteBuffer, this.endOfInput);
               }

               if (var8.isOverflow()) {
                  this.startDraining(true);
                  var4 = var6;
                  var5 = var7;
                  break;
               }

               if (var8.isUnderflow()) {
                  if (var6) {
                     this.doneFlushing = true;
                     this.startDraining(false);
                     var4 = var6;
                     var5 = var7;
                     break;
                  }

                  if (this.endOfInput) {
                     var6 = true;
                  } else {
                     this.readMoreChars();
                  }
               } else if (var8.isError()) {
                  var8.throwException();
                  return 0;
               }
            }
         }
      }
   }
}
