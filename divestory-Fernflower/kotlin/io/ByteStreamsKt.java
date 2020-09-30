package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ByteIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b¨\u0006\u001f"},
   d2 = {"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ByteStreamsKt {
   private static final BufferedInputStream buffered(InputStream var0, int var1) {
      BufferedInputStream var2;
      if (var0 instanceof BufferedInputStream) {
         var2 = (BufferedInputStream)var0;
      } else {
         var2 = new BufferedInputStream(var0, var1);
      }

      return var2;
   }

   private static final BufferedOutputStream buffered(OutputStream var0, int var1) {
      BufferedOutputStream var2;
      if (var0 instanceof BufferedOutputStream) {
         var2 = (BufferedOutputStream)var0;
      } else {
         var2 = new BufferedOutputStream(var0, var1);
      }

      return var2;
   }

   // $FF: synthetic method
   static BufferedInputStream buffered$default(InputStream var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      BufferedInputStream var4;
      if (var0 instanceof BufferedInputStream) {
         var4 = (BufferedInputStream)var0;
      } else {
         var4 = new BufferedInputStream(var0, var1);
      }

      return var4;
   }

   // $FF: synthetic method
   static BufferedOutputStream buffered$default(OutputStream var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      BufferedOutputStream var4;
      if (var0 instanceof BufferedOutputStream) {
         var4 = (BufferedOutputStream)var0;
      } else {
         var4 = new BufferedOutputStream(var0, var1);
      }

      return var4;
   }

   private static final BufferedReader bufferedReader(InputStream var0, Charset var1) {
      Reader var2 = (Reader)(new InputStreamReader(var0, var1));
      BufferedReader var3;
      if (var2 instanceof BufferedReader) {
         var3 = (BufferedReader)var2;
      } else {
         var3 = new BufferedReader(var2, 8192);
      }

      return var3;
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(InputStream var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      Reader var4 = (Reader)(new InputStreamReader(var0, var1));
      BufferedReader var5;
      if (var4 instanceof BufferedReader) {
         var5 = (BufferedReader)var4;
      } else {
         var5 = new BufferedReader(var4, 8192);
      }

      return var5;
   }

   private static final BufferedWriter bufferedWriter(OutputStream var0, Charset var1) {
      Writer var2 = (Writer)(new OutputStreamWriter(var0, var1));
      BufferedWriter var3;
      if (var2 instanceof BufferedWriter) {
         var3 = (BufferedWriter)var2;
      } else {
         var3 = new BufferedWriter(var2, 8192);
      }

      return var3;
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(OutputStream var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      Writer var4 = (Writer)(new OutputStreamWriter(var0, var1));
      BufferedWriter var5;
      if (var4 instanceof BufferedWriter) {
         var5 = (BufferedWriter)var4;
      } else {
         var5 = new BufferedWriter(var4, 8192);
      }

      return var5;
   }

   private static final ByteArrayInputStream byteInputStream(String var0, Charset var1) {
      if (var0 != null) {
         byte[] var2 = var0.getBytes(var1);
         Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).getBytes(charset)");
         return new ByteArrayInputStream(var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   // $FF: synthetic method
   static ByteArrayInputStream byteInputStream$default(String var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if (var0 != null) {
         byte[] var4 = var0.getBytes(var1);
         Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.String).getBytes(charset)");
         return new ByteArrayInputStream(var4);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
   }

   public static final long copyTo(InputStream var0, OutputStream var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(var1, "out");
      byte[] var3 = new byte[var2];
      var2 = var0.read(var3);

      long var4;
      for(var4 = 0L; var2 >= 0; var2 = var0.read(var3)) {
         var1.write(var3, 0, var2);
         var4 += (long)var2;
      }

      return var4;
   }

   // $FF: synthetic method
   public static long copyTo$default(InputStream var0, OutputStream var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   private static final ByteArrayInputStream inputStream(byte[] var0) {
      return new ByteArrayInputStream(var0);
   }

   private static final ByteArrayInputStream inputStream(byte[] var0, int var1, int var2) {
      return new ByteArrayInputStream(var0, var1, var2);
   }

   public static final ByteIterator iterator(final BufferedInputStream var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$iterator");
      return (ByteIterator)(new ByteIterator() {
         private boolean finished;
         private int nextByte = -1;
         private boolean nextPrepared;

         private final void prepareNext() {
            if (!this.nextPrepared && !this.finished) {
               int var1 = var0.read();
               this.nextByte = var1;
               boolean var2 = true;
               this.nextPrepared = true;
               if (var1 != -1) {
                  var2 = false;
               }

               this.finished = var2;
            }

         }

         public final boolean getFinished() {
            return this.finished;
         }

         public final int getNextByte() {
            return this.nextByte;
         }

         public final boolean getNextPrepared() {
            return this.nextPrepared;
         }

         public boolean hasNext() {
            this.prepareNext();
            return this.finished ^ true;
         }

         public byte nextByte() {
            this.prepareNext();
            if (!this.finished) {
               byte var1 = (byte)this.nextByte;
               this.nextPrepared = false;
               return var1;
            } else {
               throw (Throwable)(new NoSuchElementException("Input stream is over."));
            }
         }

         public final void setFinished(boolean var1) {
            this.finished = var1;
         }

         public final void setNextByte(int var1) {
            this.nextByte = var1;
         }

         public final void setNextPrepared(boolean var1) {
            this.nextPrepared = var1;
         }
      });
   }

   public static final byte[] readBytes(InputStream var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readBytes");
      ByteArrayOutputStream var1 = new ByteArrayOutputStream(Math.max(8192, var0.available()));
      copyTo$default(var0, (OutputStream)var1, 0, 2, (Object)null);
      byte[] var2 = var1.toByteArray();
      Intrinsics.checkExpressionValueIsNotNull(var2, "buffer.toByteArray()");
      return var2;
   }

   @Deprecated(
      message = "Use readBytes() overload without estimatedSize parameter",
      replaceWith = @ReplaceWith(
   expression = "readBytes()",
   imports = {}
)
   )
   public static final byte[] readBytes(InputStream var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readBytes");
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(Math.max(var1, var0.available()));
      copyTo$default(var0, (OutputStream)var2, 0, 2, (Object)null);
      byte[] var3 = var2.toByteArray();
      Intrinsics.checkExpressionValueIsNotNull(var3, "buffer.toByteArray()");
      return var3;
   }

   // $FF: synthetic method
   public static byte[] readBytes$default(InputStream var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      return readBytes(var0, var1);
   }

   private static final InputStreamReader reader(InputStream var0, Charset var1) {
      return new InputStreamReader(var0, var1);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(InputStream var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return new InputStreamReader(var0, var1);
   }

   private static final OutputStreamWriter writer(OutputStream var0, Charset var1) {
      return new OutputStreamWriter(var0, var1);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(OutputStream var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return new OutputStreamWriter(var0, var1);
   }
}
