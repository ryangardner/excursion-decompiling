package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000f"},
   d2 = {"Lokhttp3/RequestBody;", "", "()V", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "isDuplex", "", "isOneShot", "writeTo", "", "sink", "Lokio/BufferedSink;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class RequestBody {
   public static final RequestBody.Companion Companion = new RequestBody.Companion((DefaultConstructorMarker)null);

   @JvmStatic
   public static final RequestBody create(File var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   @JvmStatic
   public static final RequestBody create(String var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'file' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "file.asRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.asRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, File var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, String var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, ByteString var1) {
      return Companion.create(var0, var1);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, byte[] var1) {
      return RequestBody.Companion.create$default(Companion, (MediaType)var0, (byte[])var1, 0, 0, 12, (Object)null);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, byte[] var1, int var2) {
      return RequestBody.Companion.create$default(Companion, (MediaType)var0, (byte[])var1, var2, 0, 8, (Object)null);
   }

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "Moved to extension function. Put the 'content' argument first to fix Java",
      replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
   )
   @JvmStatic
   public static final RequestBody create(MediaType var0, byte[] var1, int var2, int var3) {
      return Companion.create(var0, var1, var2, var3);
   }

   @JvmStatic
   public static final RequestBody create(ByteString var0, MediaType var1) {
      return Companion.create(var0, var1);
   }

   @JvmStatic
   public static final RequestBody create(byte[] var0) {
      return RequestBody.Companion.create$default(Companion, (byte[])var0, (MediaType)null, 0, 0, 7, (Object)null);
   }

   @JvmStatic
   public static final RequestBody create(byte[] var0, MediaType var1) {
      return RequestBody.Companion.create$default(Companion, (byte[])var0, (MediaType)var1, 0, 0, 6, (Object)null);
   }

   @JvmStatic
   public static final RequestBody create(byte[] var0, MediaType var1, int var2) {
      return RequestBody.Companion.create$default(Companion, (byte[])var0, (MediaType)var1, var2, 0, 4, (Object)null);
   }

   @JvmStatic
   public static final RequestBody create(byte[] var0, MediaType var1, int var2, int var3) {
      return Companion.create(var0, var1, var2, var3);
   }

   public long contentLength() throws IOException {
      return -1L;
   }

   public abstract MediaType contentType();

   public boolean isDuplex() {
      return false;
   }

   public boolean isOneShot() {
      return false;
   }

   public abstract void writeTo(BufferedSink var1) throws IOException;

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J.\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u000eH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u000fH\u0007J\u001d\u0010\u0010\u001a\u00020\u0004*\u00020\b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003J1\u0010\u0011\u001a\u00020\u0004*\u00020\n2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0007¢\u0006\u0002\b\u0003J\u001d\u0010\u0011\u001a\u00020\u0004*\u00020\u000e2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003J\u001d\u0010\u0011\u001a\u00020\u0004*\u00020\u000f2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\b\u0003¨\u0006\u0012"},
      d2 = {"Lokhttp3/RequestBody$Companion;", "", "()V", "create", "Lokhttp3/RequestBody;", "contentType", "Lokhttp3/MediaType;", "file", "Ljava/io/File;", "content", "", "offset", "", "byteCount", "", "Lokio/ByteString;", "asRequestBody", "toRequestBody", "okhttp"},
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
      public static RequestBody create$default(RequestBody.Companion var0, File var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      // $FF: synthetic method
      public static RequestBody create$default(RequestBody.Companion var0, String var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      // $FF: synthetic method
      public static RequestBody create$default(RequestBody.Companion var0, MediaType var1, byte[] var2, int var3, int var4, int var5, Object var6) {
         if ((var5 & 4) != 0) {
            var3 = 0;
         }

         if ((var5 & 8) != 0) {
            var4 = var2.length;
         }

         return var0.create(var1, var2, var3, var4);
      }

      // $FF: synthetic method
      public static RequestBody create$default(RequestBody.Companion var0, ByteString var1, MediaType var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = (MediaType)null;
         }

         return var0.create(var1, var2);
      }

      // $FF: synthetic method
      public static RequestBody create$default(RequestBody.Companion var0, byte[] var1, MediaType var2, int var3, int var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var2 = (MediaType)null;
         }

         if ((var5 & 2) != 0) {
            var3 = 0;
         }

         if ((var5 & 4) != 0) {
            var4 = var1.length;
         }

         return var0.create(var1, var2, var3, var4);
      }

      @JvmStatic
      public final RequestBody create(final File var1, final MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$asRequestBody");
         return (RequestBody)(new RequestBody() {
            public long contentLength() {
               return var1.length();
            }

            public MediaType contentType() {
               return var2;
            }

            public void writeTo(BufferedSink var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "sink");
               Closeable var2x = (Closeable)Okio.source(var1);
               Throwable var3 = (Throwable)null;

               try {
                  var1x.writeAll((Source)var2x);
               } catch (Throwable var9) {
                  var3 = var9;

                  try {
                     throw var3;
                  } finally {
                     CloseableKt.closeFinally(var2x, var9);
                  }
               }

               CloseableKt.closeFinally(var2x, var3);
            }
         });
      }

      @JvmStatic
      public final RequestBody create(String var1, MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
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

         byte[] var7 = var1.getBytes(var3);
         Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.String).getBytes(charset)");
         return ((RequestBody.Companion)this).create((byte[])var7, (MediaType)var4, 0, var7.length);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'file' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "file.asRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.asRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, File var2) {
         Intrinsics.checkParameterIsNotNull(var2, "file");
         return ((RequestBody.Companion)this).create(var2, var1);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((RequestBody.Companion)this).create(var2, var1);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((RequestBody.Companion)this).create(var2, var1);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, byte[] var2) {
         return create$default(this, (MediaType)var1, (byte[])var2, 0, 0, 12, (Object)null);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, byte[] var2, int var3) {
         return create$default(this, (MediaType)var1, (byte[])var2, var3, 0, 8, (Object)null);
      }

      @Deprecated(
         level = DeprecationLevel.WARNING,
         message = "Moved to extension function. Put the 'content' argument first to fix Java",
         replaceWith = @ReplaceWith(
   expression = "content.toRequestBody(contentType, offset, byteCount)",
   imports = {"okhttp3.RequestBody.Companion.toRequestBody"}
)
      )
      @JvmStatic
      public final RequestBody create(MediaType var1, byte[] var2, int var3, int var4) {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         return ((RequestBody.Companion)this).create(var2, var1, var3, var4);
      }

      @JvmStatic
      public final RequestBody create(final ByteString var1, final MediaType var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
         return (RequestBody)(new RequestBody() {
            public long contentLength() {
               return (long)var1.size();
            }

            public MediaType contentType() {
               return var2;
            }

            public void writeTo(BufferedSink var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "sink");
               var1x.write(var1);
            }
         });
      }

      @JvmStatic
      public final RequestBody create(byte[] var1) {
         return create$default(this, (byte[])var1, (MediaType)null, 0, 0, 7, (Object)null);
      }

      @JvmStatic
      public final RequestBody create(byte[] var1, MediaType var2) {
         return create$default(this, (byte[])var1, (MediaType)var2, 0, 0, 6, (Object)null);
      }

      @JvmStatic
      public final RequestBody create(byte[] var1, MediaType var2, int var3) {
         return create$default(this, (byte[])var1, (MediaType)var2, var3, 0, 4, (Object)null);
      }

      @JvmStatic
      public final RequestBody create(final byte[] var1, final MediaType var2, final int var3, final int var4) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
         Util.checkOffsetAndCount((long)var1.length, (long)var3, (long)var4);
         return (RequestBody)(new RequestBody() {
            public long contentLength() {
               return (long)var4;
            }

            public MediaType contentType() {
               return var2;
            }

            public void writeTo(BufferedSink var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "sink");
               var1x.write(var1, var3, var4);
            }
         });
      }
   }
}
