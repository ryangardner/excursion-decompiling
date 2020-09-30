package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class BaseEncoding {
   private static final BaseEncoding BASE16;
   private static final BaseEncoding BASE32;
   private static final BaseEncoding BASE32_HEX;
   private static final BaseEncoding BASE64;
   private static final BaseEncoding BASE64_URL;

   static {
      Character var0 = '=';
      BASE64 = new BaseEncoding.Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", var0);
      BASE64_URL = new BaseEncoding.Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", var0);
      BASE32 = new BaseEncoding.StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", var0);
      BASE32_HEX = new BaseEncoding.StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", var0);
      BASE16 = new BaseEncoding.Base16Encoding("base16()", "0123456789ABCDEF");
   }

   BaseEncoding() {
   }

   public static BaseEncoding base16() {
      return BASE16;
   }

   public static BaseEncoding base32() {
      return BASE32;
   }

   public static BaseEncoding base32Hex() {
      return BASE32_HEX;
   }

   public static BaseEncoding base64() {
      return BASE64;
   }

   public static BaseEncoding base64Url() {
      return BASE64_URL;
   }

   private static byte[] extract(byte[] var0, int var1) {
      if (var1 == var0.length) {
         return var0;
      } else {
         byte[] var2 = new byte[var1];
         System.arraycopy(var0, 0, var2, 0, var1);
         return var2;
      }
   }

   static Reader ignoringReader(final Reader var0, final String var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Reader() {
         public void close() throws IOException {
            var0.close();
         }

         public int read() throws IOException {
            int var1x;
            do {
               var1x = var0.read();
            } while(var1x != -1 && var1.indexOf((char)var1x) >= 0);

            return var1x;
         }

         public int read(char[] var1x, int var2, int var3) throws IOException {
            throw new UnsupportedOperationException();
         }
      };
   }

   static Appendable separatingAppendable(final Appendable var0, final String var1, final int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      boolean var3;
      if (var2 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      return new Appendable() {
         int charsUntilSeparator = var2;

         public Appendable append(char var1x) throws IOException {
            if (this.charsUntilSeparator == 0) {
               var0.append(var1);
               this.charsUntilSeparator = var2;
            }

            var0.append(var1x);
            --this.charsUntilSeparator;
            return this;
         }

         public Appendable append(@NullableDecl CharSequence var1x) throws IOException {
            throw new UnsupportedOperationException();
         }

         public Appendable append(@NullableDecl CharSequence var1x, int var2x, int var3) throws IOException {
            throw new UnsupportedOperationException();
         }
      };
   }

   static Writer separatingWriter(final Writer var0, String var1, int var2) {
      return new Writer(separatingAppendable(var0, var1, var2)) {
         // $FF: synthetic field
         final Appendable val$seperatingAppendable;

         {
            this.val$seperatingAppendable = var1;
         }

         public void close() throws IOException {
            var0.close();
         }

         public void flush() throws IOException {
            var0.flush();
         }

         public void write(int var1) throws IOException {
            this.val$seperatingAppendable.append((char)var1);
         }

         public void write(char[] var1, int var2, int var3) throws IOException {
            throw new UnsupportedOperationException();
         }
      };
   }

   public abstract boolean canDecode(CharSequence var1);

   public final byte[] decode(CharSequence var1) {
      try {
         byte[] var3 = this.decodeChecked(var1);
         return var3;
      } catch (BaseEncoding.DecodingException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   final byte[] decodeChecked(CharSequence var1) throws BaseEncoding.DecodingException {
      var1 = this.trimTrailingPadding(var1);
      byte[] var2 = new byte[this.maxDecodedSize(var1.length())];
      return extract(var2, this.decodeTo(var2, var1));
   }

   abstract int decodeTo(byte[] var1, CharSequence var2) throws BaseEncoding.DecodingException;

   public final ByteSource decodingSource(final CharSource var1) {
      Preconditions.checkNotNull(var1);
      return new ByteSource() {
         public InputStream openStream() throws IOException {
            return BaseEncoding.this.decodingStream(var1.openStream());
         }
      };
   }

   public abstract InputStream decodingStream(Reader var1);

   public String encode(byte[] var1) {
      return this.encode(var1, 0, var1.length);
   }

   public final String encode(byte[] var1, int var2, int var3) {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      StringBuilder var4 = new StringBuilder(this.maxEncodedSize(var3));

      try {
         this.encodeTo(var4, var1, var2, var3);
      } catch (IOException var5) {
         throw new AssertionError(var5);
      }

      return var4.toString();
   }

   abstract void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException;

   public final ByteSink encodingSink(final CharSink var1) {
      Preconditions.checkNotNull(var1);
      return new ByteSink() {
         public OutputStream openStream() throws IOException {
            return BaseEncoding.this.encodingStream(var1.openStream());
         }
      };
   }

   public abstract OutputStream encodingStream(Writer var1);

   public abstract BaseEncoding lowerCase();

   abstract int maxDecodedSize(int var1);

   abstract int maxEncodedSize(int var1);

   public abstract BaseEncoding omitPadding();

   CharSequence trimTrailingPadding(CharSequence var1) {
      return (CharSequence)Preconditions.checkNotNull(var1);
   }

   public abstract BaseEncoding upperCase();

   public abstract BaseEncoding withPadChar(char var1);

   public abstract BaseEncoding withSeparator(String var1, int var2);

   private static final class Alphabet {
      final int bitsPerChar;
      final int bytesPerChunk;
      private final char[] chars;
      final int charsPerChunk;
      private final byte[] decodabet;
      final int mask;
      private final String name;
      private final boolean[] validPadding;

      Alphabet(String var1, char[] var2) {
         this.name = (String)Preconditions.checkNotNull(var1);
         this.chars = (char[])Preconditions.checkNotNull(var2);

         int var3;
         StringBuilder var10;
         try {
            var3 = IntMath.log2(var2.length, RoundingMode.UNNECESSARY);
            this.bitsPerChar = var3;
         } catch (ArithmeticException var9) {
            var10 = new StringBuilder();
            var10.append("Illegal alphabet length ");
            var10.append(var2.length);
            throw new IllegalArgumentException(var10.toString(), var9);
         }

         var3 = Math.min(8, Integer.lowestOneBit(var3));

         try {
            this.charsPerChunk = 8 / var3;
            this.bytesPerChunk = this.bitsPerChar / var3;
         } catch (ArithmeticException var8) {
            var10 = new StringBuilder();
            var10.append("Illegal alphabet ");
            var10.append(new String(var2));
            throw new IllegalArgumentException(var10.toString(), var8);
         }

         this.mask = var2.length - 1;
         byte[] var11 = new byte[128];
         Arrays.fill(var11, (byte)-1);
         byte var4 = 0;

         for(var3 = 0; var3 < var2.length; ++var3) {
            char var5 = var2[var3];
            boolean var6;
            if (var5 < 128) {
               var6 = true;
            } else {
               var6 = false;
            }

            Preconditions.checkArgument(var6, "Non-ASCII character: %s", var5);
            if (var11[var5] == -1) {
               var6 = true;
            } else {
               var6 = false;
            }

            Preconditions.checkArgument(var6, "Duplicate character: %s", var5);
            var11[var5] = (byte)((byte)var3);
         }

         this.decodabet = var11;
         boolean[] var12 = new boolean[this.charsPerChunk];

         for(var3 = var4; var3 < this.bytesPerChunk; ++var3) {
            var12[IntMath.divide(var3 * 8, this.bitsPerChar, RoundingMode.CEILING)] = true;
         }

         this.validPadding = var12;
      }

      private boolean hasLowerCase() {
         char[] var1 = this.chars;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            if (Ascii.isLowerCase(var1[var3])) {
               return true;
            }
         }

         return false;
      }

      private boolean hasUpperCase() {
         char[] var1 = this.chars;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            if (Ascii.isUpperCase(var1[var3])) {
               return true;
            }
         }

         return false;
      }

      boolean canDecode(char var1) {
         boolean var2;
         if (var1 <= 127 && this.decodabet[var1] != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      int decode(char var1) throws BaseEncoding.DecodingException {
         StringBuilder var3;
         if (var1 <= 127) {
            byte var2 = this.decodabet[var1];
            if (var2 == -1) {
               if (var1 > ' ' && var1 != 127) {
                  var3 = new StringBuilder();
                  var3.append("Unrecognized character: ");
                  var3.append(var1);
                  throw new BaseEncoding.DecodingException(var3.toString());
               } else {
                  var3 = new StringBuilder();
                  var3.append("Unrecognized character: 0x");
                  var3.append(Integer.toHexString(var1));
                  throw new BaseEncoding.DecodingException(var3.toString());
               }
            } else {
               return var2;
            }
         } else {
            var3 = new StringBuilder();
            var3.append("Unrecognized character: 0x");
            var3.append(Integer.toHexString(var1));
            throw new BaseEncoding.DecodingException(var3.toString());
         }
      }

      char encode(int var1) {
         return this.chars[var1];
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof BaseEncoding.Alphabet) {
            BaseEncoding.Alphabet var2 = (BaseEncoding.Alphabet)var1;
            return Arrays.equals(this.chars, var2.chars);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Arrays.hashCode(this.chars);
      }

      boolean isValidPaddingStartPosition(int var1) {
         return this.validPadding[var1 % this.charsPerChunk];
      }

      BaseEncoding.Alphabet lowerCase() {
         if (!this.hasUpperCase()) {
            return this;
         } else {
            Preconditions.checkState(this.hasLowerCase() ^ true, "Cannot call lowerCase() on a mixed-case alphabet");
            char[] var1 = new char[this.chars.length];
            int var2 = 0;

            while(true) {
               char[] var3 = this.chars;
               if (var2 >= var3.length) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append(this.name);
                  var4.append(".lowerCase()");
                  return new BaseEncoding.Alphabet(var4.toString(), var1);
               }

               var1[var2] = Ascii.toLowerCase(var3[var2]);
               ++var2;
            }
         }
      }

      public boolean matches(char var1) {
         byte[] var2 = this.decodabet;
         boolean var3;
         if (var1 < var2.length && var2[var1] != -1) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public String toString() {
         return this.name;
      }

      BaseEncoding.Alphabet upperCase() {
         if (!this.hasLowerCase()) {
            return this;
         } else {
            Preconditions.checkState(this.hasUpperCase() ^ true, "Cannot call upperCase() on a mixed-case alphabet");
            char[] var1 = new char[this.chars.length];
            int var2 = 0;

            while(true) {
               char[] var3 = this.chars;
               if (var2 >= var3.length) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append(this.name);
                  var4.append(".upperCase()");
                  return new BaseEncoding.Alphabet(var4.toString(), var1);
               }

               var1[var2] = Ascii.toUpperCase(var3[var2]);
               ++var2;
            }
         }
      }
   }

   static final class Base16Encoding extends BaseEncoding.StandardBaseEncoding {
      final char[] encoding;

      private Base16Encoding(BaseEncoding.Alphabet var1) {
         super(var1, (Character)null);
         this.encoding = new char[512];
         int var2 = var1.chars.length;
         int var3 = 0;
         boolean var4;
         if (var2 == 16) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);

         while(var3 < 256) {
            this.encoding[var3] = var1.encode(var3 >>> 4);
            this.encoding[var3 | 256] = var1.encode(var3 & 15);
            ++var3;
         }

      }

      Base16Encoding(String var1, String var2) {
         this(new BaseEncoding.Alphabet(var1, var2.toCharArray()));
      }

      int decodeTo(byte[] var1, CharSequence var2) throws BaseEncoding.DecodingException {
         Preconditions.checkNotNull(var1);
         if (var2.length() % 2 == 1) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Invalid input length ");
            var5.append(var2.length());
            throw new BaseEncoding.DecodingException(var5.toString());
         } else {
            int var3 = 0;

            int var4;
            for(var4 = 0; var3 < var2.length(); ++var4) {
               var1[var4] = (byte)((byte)(this.alphabet.decode(var2.charAt(var3)) << 4 | this.alphabet.decode(var2.charAt(var3 + 1))));
               var3 += 2;
            }

            return var4;
         }
      }

      void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException {
         Preconditions.checkNotNull(var1);
         Preconditions.checkPositionIndexes(var3, var3 + var4, var2.length);

         for(int var5 = 0; var5 < var4; ++var5) {
            int var6 = var2[var3 + var5] & 255;
            var1.append(this.encoding[var6]);
            var1.append(this.encoding[var6 | 256]);
         }

      }

      BaseEncoding newInstance(BaseEncoding.Alphabet var1, @NullableDecl Character var2) {
         return new BaseEncoding.Base16Encoding(var1);
      }
   }

   static final class Base64Encoding extends BaseEncoding.StandardBaseEncoding {
      private Base64Encoding(BaseEncoding.Alphabet var1, @NullableDecl Character var2) {
         super(var1, var2);
         boolean var3;
         if (var1.chars.length == 64) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
      }

      Base64Encoding(String var1, String var2, @NullableDecl Character var3) {
         this(new BaseEncoding.Alphabet(var1, var2.toCharArray()), var3);
      }

      int decodeTo(byte[] var1, CharSequence var2) throws BaseEncoding.DecodingException {
         Preconditions.checkNotNull(var1);
         var2 = this.trimTrailingPadding(var2);
         if (!this.alphabet.isValidPaddingStartPosition(var2.length())) {
            StringBuilder var9 = new StringBuilder();
            var9.append("Invalid input length ");
            var9.append(var2.length());
            throw new BaseEncoding.DecodingException(var9.toString());
         } else {
            int var3 = 0;
            int var4 = 0;

            while(true) {
               int var7;
               while(true) {
                  if (var3 >= var2.length()) {
                     return var4;
                  }

                  BaseEncoding.Alphabet var5 = this.alphabet;
                  int var6 = var3 + 1;
                  var3 = var5.decode(var2.charAt(var3));
                  var5 = this.alphabet;
                  var7 = var6 + 1;
                  int var8 = var3 << 18 | var5.decode(var2.charAt(var6)) << 12;
                  var6 = var4 + 1;
                  var1[var4] = (byte)((byte)(var8 >>> 16));
                  var3 = var6;
                  var4 = var7;
                  if (var7 >= var2.length()) {
                     break;
                  }

                  var5 = this.alphabet;
                  var3 = var7 + 1;
                  var8 |= var5.decode(var2.charAt(var7)) << 6;
                  var4 = var6 + 1;
                  var1[var6] = (byte)((byte)(var8 >>> 8 & 255));
                  if (var3 < var2.length()) {
                     var5 = this.alphabet;
                     var7 = var3 + 1;
                     var6 = var5.decode(var2.charAt(var3));
                     var3 = var4 + 1;
                     var1[var4] = (byte)((byte)((var8 | var6) & 255));
                     var4 = var7;
                     break;
                  }
               }

               var7 = var3;
               var3 = var4;
               var4 = var7;
            }
         }
      }

      void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException {
         Preconditions.checkNotNull(var1);
         int var5 = var3 + var4;
         Preconditions.checkPositionIndexes(var3, var5, var2.length);

         while(var4 >= 3) {
            int var6 = var3 + 1;
            byte var7 = var2[var3];
            var3 = var6 + 1;
            var6 = (var7 & 255) << 16 | (var2[var6] & 255) << 8 | var2[var3] & 255;
            var1.append(this.alphabet.encode(var6 >>> 18));
            var1.append(this.alphabet.encode(var6 >>> 12 & 63));
            var1.append(this.alphabet.encode(var6 >>> 6 & 63));
            var1.append(this.alphabet.encode(var6 & 63));
            var4 -= 3;
            ++var3;
         }

         if (var3 < var5) {
            this.encodeChunkTo(var1, var2, var3, var5 - var3);
         }

      }

      BaseEncoding newInstance(BaseEncoding.Alphabet var1, @NullableDecl Character var2) {
         return new BaseEncoding.Base64Encoding(var1, var2);
      }
   }

   public static final class DecodingException extends IOException {
      DecodingException(String var1) {
         super(var1);
      }

      DecodingException(Throwable var1) {
         super(var1);
      }
   }

   static final class SeparatedBaseEncoding extends BaseEncoding {
      private final int afterEveryChars;
      private final BaseEncoding delegate;
      private final String separator;

      SeparatedBaseEncoding(BaseEncoding var1, String var2, int var3) {
         this.delegate = (BaseEncoding)Preconditions.checkNotNull(var1);
         this.separator = (String)Preconditions.checkNotNull(var2);
         this.afterEveryChars = var3;
         boolean var4;
         if (var3 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "Cannot add a separator after every %s chars", var3);
      }

      public boolean canDecode(CharSequence var1) {
         StringBuilder var2 = new StringBuilder();

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            char var4 = var1.charAt(var3);
            if (this.separator.indexOf(var4) < 0) {
               var2.append(var4);
            }
         }

         return this.delegate.canDecode(var2);
      }

      int decodeTo(byte[] var1, CharSequence var2) throws BaseEncoding.DecodingException {
         StringBuilder var3 = new StringBuilder(var2.length());

         for(int var4 = 0; var4 < var2.length(); ++var4) {
            char var5 = var2.charAt(var4);
            if (this.separator.indexOf(var5) < 0) {
               var3.append(var5);
            }
         }

         return this.delegate.decodeTo(var1, var3);
      }

      public InputStream decodingStream(Reader var1) {
         return this.delegate.decodingStream(ignoringReader(var1, this.separator));
      }

      void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException {
         this.delegate.encodeTo(separatingAppendable(var1, this.separator, this.afterEveryChars), var2, var3, var4);
      }

      public OutputStream encodingStream(Writer var1) {
         return this.delegate.encodingStream(separatingWriter(var1, this.separator, this.afterEveryChars));
      }

      public BaseEncoding lowerCase() {
         return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
      }

      int maxDecodedSize(int var1) {
         return this.delegate.maxDecodedSize(var1);
      }

      int maxEncodedSize(int var1) {
         var1 = this.delegate.maxEncodedSize(var1);
         return var1 + this.separator.length() * IntMath.divide(Math.max(0, var1 - 1), this.afterEveryChars, RoundingMode.FLOOR);
      }

      public BaseEncoding omitPadding() {
         return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.delegate);
         var1.append(".withSeparator(\"");
         var1.append(this.separator);
         var1.append("\", ");
         var1.append(this.afterEveryChars);
         var1.append(")");
         return var1.toString();
      }

      CharSequence trimTrailingPadding(CharSequence var1) {
         return this.delegate.trimTrailingPadding(var1);
      }

      public BaseEncoding upperCase() {
         return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
      }

      public BaseEncoding withPadChar(char var1) {
         return this.delegate.withPadChar(var1).withSeparator(this.separator, this.afterEveryChars);
      }

      public BaseEncoding withSeparator(String var1, int var2) {
         throw new UnsupportedOperationException("Already have a separator");
      }
   }

   static class StandardBaseEncoding extends BaseEncoding {
      final BaseEncoding.Alphabet alphabet;
      @MonotonicNonNullDecl
      private transient BaseEncoding lowerCase;
      @NullableDecl
      final Character paddingChar;
      @MonotonicNonNullDecl
      private transient BaseEncoding upperCase;

      StandardBaseEncoding(BaseEncoding.Alphabet var1, @NullableDecl Character var2) {
         this.alphabet = (BaseEncoding.Alphabet)Preconditions.checkNotNull(var1);
         boolean var3;
         if (var2 != null && var1.matches(var2)) {
            var3 = false;
         } else {
            var3 = true;
         }

         Preconditions.checkArgument(var3, "Padding character %s was already in alphabet", (Object)var2);
         this.paddingChar = var2;
      }

      StandardBaseEncoding(String var1, String var2, @NullableDecl Character var3) {
         this(new BaseEncoding.Alphabet(var1, var2.toCharArray()), var3);
      }

      public boolean canDecode(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         var1 = this.trimTrailingPadding(var1);
         if (!this.alphabet.isValidPaddingStartPosition(var1.length())) {
            return false;
         } else {
            for(int var2 = 0; var2 < var1.length(); ++var2) {
               if (!this.alphabet.canDecode(var1.charAt(var2))) {
                  return false;
               }
            }

            return true;
         }
      }

      int decodeTo(byte[] var1, CharSequence var2) throws BaseEncoding.DecodingException {
         Preconditions.checkNotNull(var1);
         var2 = this.trimTrailingPadding(var2);
         if (!this.alphabet.isValidPaddingStartPosition(var2.length())) {
            StringBuilder var13 = new StringBuilder();
            var13.append("Invalid input length ");
            var13.append(var2.length());
            throw new BaseEncoding.DecodingException(var13.toString());
         } else {
            int var3 = 0;

            int var4;
            for(var4 = 0; var3 < var2.length(); var3 += this.alphabet.charsPerChunk) {
               long var5 = 0L;
               int var7 = 0;

               int var8;
               int var11;
               for(var8 = 0; var7 < this.alphabet.charsPerChunk; var8 = var11) {
                  long var9 = var5 << this.alphabet.bitsPerChar;
                  var5 = var9;
                  var11 = var8;
                  if (var3 + var7 < var2.length()) {
                     var5 = var9 | (long)this.alphabet.decode(var2.charAt(var8 + var3));
                     var11 = var8 + 1;
                  }

                  ++var7;
               }

               var7 = this.alphabet.bytesPerChunk;
               int var12 = this.alphabet.bitsPerChar;

               for(var11 = (this.alphabet.bytesPerChunk - 1) * 8; var11 >= var7 * 8 - var8 * var12; ++var4) {
                  var1[var4] = (byte)((byte)((int)(var5 >>> var11 & 255L)));
                  var11 -= 8;
               }
            }

            return var4;
         }
      }

      public InputStream decodingStream(final Reader var1) {
         Preconditions.checkNotNull(var1);
         return new InputStream() {
            int bitBuffer = 0;
            int bitBufferLength = 0;
            boolean hitPadding = false;
            int readChars = 0;

            public void close() throws IOException {
               var1.close();
            }

            public int read() throws IOException {
               while(true) {
                  int var1x = var1.read();
                  StringBuilder var2;
                  if (var1x == -1) {
                     if (!this.hitPadding && !StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars)) {
                        var2 = new StringBuilder();
                        var2.append("Invalid input length ");
                        var2.append(this.readChars);
                        throw new BaseEncoding.DecodingException(var2.toString());
                     }

                     return -1;
                  }

                  ++this.readChars;
                  char var3 = (char)var1x;
                  if (StandardBaseEncoding.this.paddingChar != null && StandardBaseEncoding.this.paddingChar == var3) {
                     if (!this.hitPadding && (this.readChars == 1 || !StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars - 1))) {
                        var2 = new StringBuilder();
                        var2.append("Padding cannot start at index ");
                        var2.append(this.readChars);
                        throw new BaseEncoding.DecodingException(var2.toString());
                     }

                     this.hitPadding = true;
                  } else {
                     if (!this.hitPadding) {
                        var1x = this.bitBuffer << StandardBaseEncoding.this.alphabet.bitsPerChar;
                        this.bitBuffer = var1x;
                        this.bitBuffer = StandardBaseEncoding.this.alphabet.decode(var3) | var1x;
                        var1x = this.bitBufferLength + StandardBaseEncoding.this.alphabet.bitsPerChar;
                        this.bitBufferLength = var1x;
                        if (var1x < 8) {
                           continue;
                        }

                        var1x -= 8;
                        this.bitBufferLength = var1x;
                        return this.bitBuffer >> var1x & 255;
                     }

                     var2 = new StringBuilder();
                     var2.append("Expected padding character but found '");
                     var2.append(var3);
                     var2.append("' at index ");
                     var2.append(this.readChars);
                     throw new BaseEncoding.DecodingException(var2.toString());
                  }
               }
            }

            public int read(byte[] var1x, int var2, int var3) throws IOException {
               int var4 = var3 + var2;
               Preconditions.checkPositionIndexes(var2, var4, var1x.length);

               for(var3 = var2; var3 < var4; ++var3) {
                  int var5 = this.read();
                  byte var6 = -1;
                  if (var5 == -1) {
                     var2 = var3 - var2;
                     if (var2 == 0) {
                        var2 = var6;
                     }

                     return var2;
                  }

                  var1x[var3] = (byte)((byte)var5);
               }

               return var3 - var2;
            }
         };
      }

      void encodeChunkTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException {
         Preconditions.checkNotNull(var1);
         Preconditions.checkPositionIndexes(var3, var3 + var4, var2.length);
         int var5 = this.alphabet.bytesPerChunk;
         byte var6 = 0;
         boolean var7;
         if (var4 <= var5) {
            var7 = true;
         } else {
            var7 = false;
         }

         Preconditions.checkArgument(var7);
         long var8 = 0L;

         for(var5 = 0; var5 < var4; ++var5) {
            var8 = (var8 | (long)(var2[var3 + var5] & 255)) << 8;
         }

         var5 = this.alphabet.bitsPerChar;

         for(var3 = var6; var3 < var4 * 8; var3 += this.alphabet.bitsPerChar) {
            int var10 = (int)(var8 >>> (var4 + 1) * 8 - var5 - var3);
            int var11 = this.alphabet.mask;
            var1.append(this.alphabet.encode(var10 & var11));
         }

         if (this.paddingChar != null) {
            while(var3 < this.alphabet.bytesPerChunk * 8) {
               var1.append(this.paddingChar);
               var3 += this.alphabet.bitsPerChar;
            }
         }

      }

      void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException {
         Preconditions.checkNotNull(var1);
         Preconditions.checkPositionIndexes(var3, var3 + var4, var2.length);

         for(int var5 = 0; var5 < var4; var5 += this.alphabet.bytesPerChunk) {
            this.encodeChunkTo(var1, var2, var3 + var5, Math.min(this.alphabet.bytesPerChunk, var4 - var5));
         }

      }

      public OutputStream encodingStream(final Writer var1) {
         Preconditions.checkNotNull(var1);
         return new OutputStream() {
            int bitBuffer = 0;
            int bitBufferLength = 0;
            int writtenChars = 0;

            public void close() throws IOException {
               if (this.bitBufferLength > 0) {
                  int var1x = this.bitBuffer;
                  int var2 = StandardBaseEncoding.this.alphabet.bitsPerChar;
                  int var3 = this.bitBufferLength;
                  int var4 = StandardBaseEncoding.this.alphabet.mask;
                  var1.write(StandardBaseEncoding.this.alphabet.encode(var1x << var2 - var3 & var4));
                  ++this.writtenChars;
                  if (StandardBaseEncoding.this.paddingChar != null) {
                     while(this.writtenChars % StandardBaseEncoding.this.alphabet.charsPerChunk != 0) {
                        var1.write(StandardBaseEncoding.this.paddingChar);
                        ++this.writtenChars;
                     }
                  }
               }

               var1.close();
            }

            public void flush() throws IOException {
               var1.flush();
            }

            public void write(int var1x) throws IOException {
               int var2 = this.bitBuffer << 8;
               this.bitBuffer = var2;
               this.bitBuffer = var1x & 255 | var2;

               for(this.bitBufferLength += 8; this.bitBufferLength >= StandardBaseEncoding.this.alphabet.bitsPerChar; this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar) {
                  var1x = this.bitBuffer;
                  int var3 = this.bitBufferLength;
                  var2 = StandardBaseEncoding.this.alphabet.bitsPerChar;
                  int var4 = StandardBaseEncoding.this.alphabet.mask;
                  var1.write(StandardBaseEncoding.this.alphabet.encode(var1x >> var3 - var2 & var4));
                  ++this.writtenChars;
               }

            }
         };
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof BaseEncoding.StandardBaseEncoding;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            BaseEncoding.StandardBaseEncoding var5 = (BaseEncoding.StandardBaseEncoding)var1;
            var4 = var3;
            if (this.alphabet.equals(var5.alphabet)) {
               var4 = var3;
               if (Objects.equal(this.paddingChar, var5.paddingChar)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
      }

      public BaseEncoding lowerCase() {
         BaseEncoding var1 = this.lowerCase;
         Object var2 = var1;
         if (var1 == null) {
            BaseEncoding.Alphabet var3 = this.alphabet.lowerCase();
            if (var3 == this.alphabet) {
               var2 = this;
            } else {
               var2 = this.newInstance(var3, this.paddingChar);
            }

            this.lowerCase = (BaseEncoding)var2;
         }

         return (BaseEncoding)var2;
      }

      int maxDecodedSize(int var1) {
         return (int)(((long)this.alphabet.bitsPerChar * (long)var1 + 7L) / 8L);
      }

      int maxEncodedSize(int var1) {
         return this.alphabet.charsPerChunk * IntMath.divide(var1, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
      }

      BaseEncoding newInstance(BaseEncoding.Alphabet var1, @NullableDecl Character var2) {
         return new BaseEncoding.StandardBaseEncoding(var1, var2);
      }

      public BaseEncoding omitPadding() {
         Object var1;
         if (this.paddingChar == null) {
            var1 = this;
         } else {
            var1 = this.newInstance(this.alphabet, (Character)null);
         }

         return (BaseEncoding)var1;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder("BaseEncoding.");
         var1.append(this.alphabet.toString());
         if (8 % this.alphabet.bitsPerChar != 0) {
            if (this.paddingChar == null) {
               var1.append(".omitPadding()");
            } else {
               var1.append(".withPadChar('");
               var1.append(this.paddingChar);
               var1.append("')");
            }
         }

         return var1.toString();
      }

      CharSequence trimTrailingPadding(CharSequence var1) {
         Preconditions.checkNotNull(var1);
         Character var2 = this.paddingChar;
         if (var2 == null) {
            return var1;
         } else {
            char var3 = var2;

            int var4;
            for(var4 = var1.length() - 1; var4 >= 0 && var1.charAt(var4) == var3; --var4) {
            }

            return var1.subSequence(0, var4 + 1);
         }
      }

      public BaseEncoding upperCase() {
         BaseEncoding var1 = this.upperCase;
         Object var2 = var1;
         if (var1 == null) {
            BaseEncoding.Alphabet var3 = this.alphabet.upperCase();
            if (var3 == this.alphabet) {
               var2 = this;
            } else {
               var2 = this.newInstance(var3, this.paddingChar);
            }

            this.upperCase = (BaseEncoding)var2;
         }

         return (BaseEncoding)var2;
      }

      public BaseEncoding withPadChar(char var1) {
         if (8 % this.alphabet.bitsPerChar != 0) {
            Character var2 = this.paddingChar;
            if (var2 == null || var2 != var1) {
               return this.newInstance(this.alphabet, var1);
            }
         }

         return this;
      }

      public BaseEncoding withSeparator(String var1, int var2) {
         boolean var3 = false;

         for(int var4 = 0; var4 < var1.length(); ++var4) {
            Preconditions.checkArgument(this.alphabet.matches(var1.charAt(var4)) ^ true, "Separator (%s) cannot contain alphabet characters", (Object)var1);
         }

         Character var5 = this.paddingChar;
         if (var5 != null) {
            if (var1.indexOf(var5) < 0) {
               var3 = true;
            }

            Preconditions.checkArgument(var3, "Separator (%s) cannot contain padding character", (Object)var1);
         }

         return new BaseEncoding.SeparatedBaseEncoding(this, var1, var2);
      }
   }
}
