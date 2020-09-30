package kotlin.io;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\t\u0010\u0015\u001a\u00020\nH\u0087\b\u001a\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u001a\u001a\u00020\r*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\n*\u00020\u001dH\u0002\u001a\u0018\u0010\u001e\u001a\u00020\n*\u00020\u001b2\n\u0010\u001f\u001a\u00060 j\u0002`!H\u0002\u001a$\u0010\"\u001a\u00020\r*\u00020\u00042\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006'"},
   d2 = {"BUFFER_SIZE", "", "LINE_SEPARATOR_MAX_LENGTH", "decoder", "Ljava/nio/charset/CharsetDecoder;", "getDecoder", "()Ljava/nio/charset/CharsetDecoder;", "decoder$delegate", "Lkotlin/Lazy;", "print", "", "message", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "inputStream", "Ljava/io/InputStream;", "endsWithLineSeparator", "Ljava/nio/CharBuffer;", "flipBack", "Ljava/nio/Buffer;", "offloadPrefixTo", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "tryDecode", "byteBuffer", "Ljava/nio/ByteBuffer;", "charBuffer", "isEndOfStream", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ConsoleKt {
   private static final int BUFFER_SIZE = 32;
   private static final int LINE_SEPARATOR_MAX_LENGTH = 2;
   private static final Lazy decoder$delegate;

   static {
      decoder$delegate = LazyKt.lazy((Function0)null.INSTANCE);
   }

   private static final boolean endsWithLineSeparator(CharBuffer var0) {
      int var1 = var0.position();
      boolean var2 = true;
      if (var1 <= 0 || var0.get(var1 - 1) != '\n') {
         var2 = false;
      }

      return var2;
   }

   private static final void flipBack(Buffer var0) {
      var0.position(var0.limit());
      var0.limit(var0.capacity());
   }

   private static final CharsetDecoder getDecoder() {
      return (CharsetDecoder)decoder$delegate.getValue();
   }

   private static final void offloadPrefixTo(CharBuffer var0, StringBuilder var1) {
      var0.flip();
      int var2 = var0.limit();

      for(int var3 = 0; var3 < var2 - 1; ++var3) {
         var1.append(var0.get());
      }

      var0.compact();
   }

   private static final void print(byte var0) {
      System.out.print(var0);
   }

   private static final void print(char var0) {
      System.out.print(var0);
   }

   private static final void print(double var0) {
      System.out.print(var0);
   }

   private static final void print(float var0) {
      System.out.print(var0);
   }

   private static final void print(int var0) {
      System.out.print(var0);
   }

   private static final void print(long var0) {
      System.out.print(var0);
   }

   private static final void print(Object var0) {
      System.out.print(var0);
   }

   private static final void print(short var0) {
      System.out.print(var0);
   }

   private static final void print(boolean var0) {
      System.out.print(var0);
   }

   private static final void print(char[] var0) {
      System.out.print(var0);
   }

   private static final void println() {
      System.out.println();
   }

   private static final void println(byte var0) {
      System.out.println(var0);
   }

   private static final void println(char var0) {
      System.out.println(var0);
   }

   private static final void println(double var0) {
      System.out.println(var0);
   }

   private static final void println(float var0) {
      System.out.println(var0);
   }

   private static final void println(int var0) {
      System.out.println(var0);
   }

   private static final void println(long var0) {
      System.out.println(var0);
   }

   private static final void println(Object var0) {
      System.out.println(var0);
   }

   private static final void println(short var0) {
      System.out.println(var0);
   }

   private static final void println(boolean var0) {
      System.out.println(var0);
   }

   private static final void println(char[] var0) {
      System.out.println(var0);
   }

   public static final String readLine() {
      InputStream var0 = System.in;
      Intrinsics.checkExpressionValueIsNotNull(var0, "System.`in`");
      return readLine(var0, getDecoder());
   }

   public static final String readLine(InputStream var0, CharsetDecoder var1) {
      Intrinsics.checkParameterIsNotNull(var0, "inputStream");
      Intrinsics.checkParameterIsNotNull(var1, "decoder");
      float var2 = var1.maxCharsPerByte();
      float var3 = (float)1;
      int var4 = 0;
      boolean var5;
      if (var2 <= var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (!var5) {
         throw (Throwable)(new IllegalArgumentException("Encodings with multiple chars per byte are not supported".toString()));
      } else {
         ByteBuffer var6 = ByteBuffer.allocate(32);
         CharBuffer var7 = CharBuffer.allocate(4);
         StringBuilder var8 = new StringBuilder();
         int var9 = var0.read();
         int var10 = var9;
         if (var9 == -1) {
            return null;
         } else {
            do {
               var6.put((byte)var10);
               Intrinsics.checkExpressionValueIsNotNull(var6, "byteBuffer");
               Intrinsics.checkExpressionValueIsNotNull(var7, "charBuffer");
               if (tryDecode(var1, var6, var7, false)) {
                  if (endsWithLineSeparator(var7)) {
                     break;
                  }

                  if (var7.remaining() < 2) {
                     offloadPrefixTo(var7, var8);
                  }
               }

               var9 = var0.read();
               var10 = var9;
            } while(var9 != -1);

            tryDecode(var1, var6, var7, true);
            var1.reset();
            var9 = var7.position();
            var10 = var9;
            if (var9 > 0) {
               var10 = var9;
               if (var7.get(var9 - 1) == '\n') {
                  --var9;
                  var10 = var9;
                  if (var9 > 0) {
                     var10 = var9;
                     if (var7.get(var9 - 1) == '\r') {
                        var10 = var9 - 1;
                     }
                  }
               }
            }

            var7.flip();

            while(var4 < var10) {
               var8.append(var7.get());
               ++var4;
            }

            return var8.toString();
         }
      }
   }

   private static final boolean tryDecode(CharsetDecoder var0, ByteBuffer var1, CharBuffer var2, boolean var3) {
      int var4 = var2.position();
      var1.flip();
      CoderResult var5 = var0.decode(var1, var2, var3);
      if (var5.isError()) {
         var5.throwException();
      }

      if (var2.position() > var4) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var1.clear();
      } else {
         flipBack((Buffer)var1);
      }

      return var3;
   }
}
