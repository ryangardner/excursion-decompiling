package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u0000 !2\u00020\u0001:\u0002 !B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J@\u0010\u0010\u001a\u0002H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u00110\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\u00170\u0014H\u0082\b¢\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u00020\u001aH&J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH&J\b\u0010\u001d\u001a\u00020\u0015H&J\u0006\u0010\u001e\u001a\u00020\u001fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lokhttp3/ResponseBody;", "Ljava/io/Closeable;", "()V", "reader", "Ljava/io/Reader;", "byteStream", "Ljava/io/InputStream;", "byteString", "Lokio/ByteString;", "bytes", "", "charStream", "charset", "Ljava/nio/charset/Charset;", "close", "", "consumeSource", "T", "", "consumer", "Lkotlin/Function1;", "Lokio/BufferedSource;", "sizeMapper", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "source", "string", "", "BomAwareReader", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class ResponseBody implements Closeable {
   public static final ResponseBody.Companion Companion = new ResponseBody.Companion((DefaultConstructorMarker)null);
   private Reader reader;

   private final Charset charset() {
      MediaType var1 = this.contentType();
      Charset var2;
      if (var1 != null) {
         var2 = var1.charset(Charsets.UTF_8);
         if (var2 != null) {
            return var2;
         }
      }

      var2 = Charsets.UTF_8;
      return var2;
   }

   private final <T> T consumeSource(Function1<? super BufferedSource, ? extends T> var1, Function1<? super T, Integer> var2) {
      long var3 = this.contentLength();
      StringBuilder var14;
      if (var3 <= (long)Integer.MAX_VALUE) {
         Closeable var5 = (Closeable)this.source();
         Throwable var6 = (Throwable)null;

         Object var16;
         try {
            var16 = var1.invoke(var5);
         } catch (Throwable var13) {
            Throwable var15 = var13;

            try {
               throw var15;
            } finally {
               InlineMarker.finallyStart(1);
               CloseableKt.closeFinally(var5, var13);
               InlineMarker.finallyEnd(1);
            }
         }

         InlineMarker.finallyStart(1);
         CloseableKt.closeFinally(var5, var6);
         InlineMarker.finallyEnd(1);
         int var7 = ((Number)var2.invoke(var16)).intValue();
         if (var3 != -1L && var3 != (long)var7) {
            var14 = new StringBuilder();
            var14.append("Content-Length (");
            var14.append(var3);
            var14.append(") and stream length (");
            var14.append(var7);
            var14.append(") disagree");
            throw (Throwable)(new IOException(var14.toString()));
         } else {
            return var16;
         }
      } else {
         var14 = new StringBuilder();
         var14.append("Cannot buffer entire body for content length: ");
         var14.append(var3);
         throw (Throwable)(new IOException(var14.toString()));
      }
   }

   @JvmStatic
   public static final ResponseBody create(String var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.asResponseBody(contentType, contentLength)",
   imports = {"okhttp3.ResponseBody.Companion.asResponseBody"}
)
   )
   @JvmStatic
   public static final ResponseBody create(MediaType var0, long var1, BufferedSource var3) {
      return Companion.create(var0, var1, var3);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
   )
   @JvmStatic
   public static final ResponseBody create(MediaType var0, String var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
   )
   @JvmStatic
   public static final ResponseBody create(MediaType var0, ByteString var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
   )
   @JvmStatic
   public static final ResponseBody create(MediaType var0, byte[] var1) {
      return Companion.create(var0, var1);
   }

   @JvmStatic
   public static final ResponseBody create(BufferedSource var0, MediaType var1, long var2) {
      return Companion.create(var0, var1, var2);
   }

   @JvmStatic
   public static final ResponseBody create(ByteString var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   @JvmStatic
   public static final ResponseBody create(byte[] var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   public final InputStream byteStream() {
      return this.source().inputStream();
   }

   public final ByteString byteString() throws IOException {
      long var1 = this.contentLength();
      StringBuilder var3;
      if (var1 <= (long)Integer.MAX_VALUE) {
         Closeable var13 = (Closeable)this.source();
         Throwable var4 = (Throwable)null;

         ByteString var14;
         try {
            var14 = ((BufferedSource)var13).readByteString();
         } catch (Throwable var12) {
            Throwable var5 = var12;

            try {
               throw var5;
            } finally {
               CloseableKt.closeFinally(var13, var12);
            }
         }

         CloseableKt.closeFinally(var13, var4);
         int var6 = var14.size();
         if (var1 != -1L && var1 != (long)var6) {
            var3 = new StringBuilder();
            var3.append("Content-Length (");
            var3.append(var1);
            var3.append(") and stream length (");
            var3.append(var6);
            var3.append(") disagree");
            throw (Throwable)(new IOException(var3.toString()));
         } else {
            return var14;
         }
      } else {
         var3 = new StringBuilder();
         var3.append("Cannot buffer entire body for content length: ");
         var3.append(var1);
         throw (Throwable)(new IOException(var3.toString()));
      }
   }

   public final byte[] bytes() throws IOException {
      long var1 = this.contentLength();
      StringBuilder var3;
      if (var1 <= (long)Integer.MAX_VALUE) {
         Closeable var13 = (Closeable)this.source();
         Throwable var4 = (Throwable)null;

         byte[] var14;
         try {
            var14 = ((BufferedSource)var13).readByteArray();
         } catch (Throwable var12) {
            Throwable var5 = var12;

            try {
               throw var5;
            } finally {
               CloseableKt.closeFinally(var13, var12);
            }
         }

         CloseableKt.closeFinally(var13, var4);
         int var6 = var14.length;
         if (var1 != -1L && var1 != (long)var6) {
            var3 = new StringBuilder();
            var3.append("Content-Length (");
            var3.append(var1);
            var3.append(") and stream length (");
            var3.append(var6);
            var3.append(") disagree");
            throw (Throwable)(new IOException(var3.toString()));
         } else {
            return var14;
         }
      } else {
         var3 = new StringBuilder();
         var3.append("Cannot buffer entire body for content length: ");
         var3.append(var1);
         throw (Throwable)(new IOException(var3.toString()));
      }
   }

   public final Reader charStream() {
      Reader var1 = this.reader;
      if (var1 == null) {
         var1 = (Reader)(new ResponseBody.BomAwareReader(this.source(), this.charset()));
         this.reader = var1;
      }

      return var1;
   }

   public void close() {
      Util.closeQuietly((Closeable)this.source());
   }

   public abstract long contentLength();

   public abstract MediaType contentType();

   public abstract BufferedSource source();

   public final String string() throws IOException {
      Closeable var1 = (Closeable)this.source();
      Throwable var2 = (Throwable)null;

      String var10;
      try {
         BufferedSource var3 = (BufferedSource)var1;
         var10 = var3.readString(Util.readBomAsCharset(var3, this.charset()));
      } catch (Throwable var9) {
         var2 = var9;

         try {
            throw var2;
         } finally {
            CloseableKt.closeFinally(var1, var9);
         }
      }

      CloseableKt.closeFinally(var1, var2);
      return var10;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
      d2 = {"Lokhttp3/ResponseBody$BomAwareReader;", "Ljava/io/Reader;", "source", "Lokio/BufferedSource;", "charset", "Ljava/nio/charset/Charset;", "(Lokio/BufferedSource;Ljava/nio/charset/Charset;)V", "closed", "", "delegate", "close", "", "read", "", "cbuf", "", "off", "len", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class BomAwareReader extends Reader {
      private final Charset charset;
      private boolean closed;
      private Reader delegate;
      private final BufferedSource source;

      public BomAwareReader(BufferedSource var1, Charset var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Intrinsics.checkParameterIsNotNull(var2, "charset");
         super();
         this.source = var1;
         this.charset = var2;
      }

      public void close() throws IOException {
         this.closed = true;
         Reader var1 = this.delegate;
         if (var1 != null) {
            var1.close();
         } else {
            ((ResponseBody.BomAwareReader)this).source.close();
         }

      }

      public int read(char[] var1, int var2, int var3) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "cbuf");
         if (!this.closed) {
            Reader var4 = this.delegate;
            if (var4 == null) {
               var4 = (Reader)(new InputStreamReader(this.source.inputStream(), Util.readBomAsCharset(this.source, this.charset)));
               this.delegate = var4;
            }

            return var4.read(var1, var2, var3);
         } else {
            throw (Throwable)(new IOException("Stream closed"));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\"\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u000bH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\fH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\rH\u0007J'\u0010\u000e\u001a\u00020\u0004*\u00020\u000b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0007¢\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\f2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\r2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0010"},
      d2 = {"Lokhttp3/ResponseBody$Companion;", "", "()V", "create", "Lokhttp3/ResponseBody;", "contentType", "Lokhttp3/MediaType;", "content", "", "contentLength", "", "Lokio/BufferedSource;", "", "Lokio/ByteString;", "asResponseBody", "toResponseBody", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $FF: synthetic method
      public static ResponseBody create$default(ResponseBody.Companion var0, String var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      // $FF: synthetic method
      public static ResponseBody create$default(ResponseBody.Companion var0, BufferedSource var1, MediaType var2, long var3, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var2 = (MediaType)null;
         }

         if ((var5 & 2) != 0) {
            var3 = -1L;
         }

         return var0.create(var1, var2, var3);
      }

      // $FF: synthetic method
      public static ResponseBody create$default(ResponseBody.Companion var0, ByteString var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      // $FF: synthetic method
      public static ResponseBody create$default(ResponseBody.Companion var0, byte[] var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      @JvmStatic
      public final ResponseBody create(String var1, MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         Charset var3 = Charsets.UTF_8;
         MediaType var4 = var2;
         if (var2 != null) {
            Charset var5 = MediaType.charset$default(var2, (Charset)null, 1, (Object)null);
            var3 = var5;
            var4 = var2;
            if (var5 == null) {
               var3 = Charsets.UTF_8;
               MediaType.Companion var6 = MediaType.Companion;
               StringBuilder var8 = new StringBuilder();
               var8.append(var2);
               var8.append("; charset=utf-8");
               var4 = var6.parse(var8.toString());
            }
         }

         Buffer var7 = (new Buffer()).writeString(var1, var3);
         return ((ResponseBody.Companion)this).create((BufferedSource)var7, var4, var7.size());
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.asResponseBody(contentType, contentLength)",
   imports = {"okhttp3.ResponseBody.Companion.asResponseBody"}
)
      )
      @JvmStatic
      public final ResponseBody create(MediaType var1, long var2, BufferedSource var4) {
         Intrinsics.checkParameterIsNotNull(var4, "content");
         return ((ResponseBody.Companion)this).create(var4, var1, var2);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
      )
      @JvmStatic
      public final ResponseBody create(MediaType var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((ResponseBody.Companion)this).create(var2, var1);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
      )
      @JvmStatic
      public final ResponseBody create(MediaType var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((ResponseBody.Companion)this).create(var2, var1);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toResponseBody(contentType)",
   imports = {"okhttp3.ResponseBody.Companion.toResponseBody"}
)
      )
      @JvmStatic
      public final ResponseBody create(MediaType var1, byte[] var2) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((ResponseBody.Companion)this).create(var2, var1);
      }

      @JvmStatic
      public final ResponseBody create(final BufferedSource var1, final MediaType var2, final long var3) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$asResponseBody");
         return (ResponseBody)(new ResponseBody() {
            public long contentLength() {
               return var3;
            }

            public MediaType contentType() {
               return var2;
            }

            public BufferedSource source() {
               return var1;
            }
         });
      }

      @JvmStatic
      public final ResponseBody create(ByteString var1, MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         return ((ResponseBody.Companion)this).create((BufferedSource)(new Buffer()).write(var1), var2, (long)var1.size());
      }

      @JvmStatic
      public final ResponseBody create(byte[] var1, MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         return ((ResponseBody.Companion)this).create((BufferedSource)(new Buffer()).write(var1), var2, (long)var1.length);
      }
   }
}
