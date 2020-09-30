package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.RealBufferedSink;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a\r\u0010\u0005\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0007\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\t\u001a\u00020\n*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0080\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0080\b\u001a\u001d\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0080\b\u001a\u001d\u0010\u000b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u0016\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\f\u001a\u00020\u0015H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u0019\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u001b\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010\u001f\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0012H\u0080\b\u001a\u0015\u0010!\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010#\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\nH\u0080\b\u001a%\u0010$\u001a\u00020\u0004*\u00020\u00022\u0006\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0080\b\u001a\u0015\u0010(\u001a\u00020\u0004*\u00020\u00022\u0006\u0010)\u001a\u00020\u000fH\u0080\b¨\u0006*"},
   d2 = {"commonClose", "", "Lokio/RealBufferedSink;", "commonEmit", "Lokio/BufferedSink;", "commonEmitCompleteSegments", "commonFlush", "commonTimeout", "Lokio/Timeout;", "commonToString", "", "commonWrite", "source", "", "offset", "", "byteCount", "Lokio/Buffer;", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "b", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteIntLe", "commonWriteLong", "commonWriteLongLe", "commonWriteShort", "s", "commonWriteShortLe", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class RealBufferedSinkKt {
   public static final void commonClose(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonClose");
      if (!var0.closed) {
         Throwable var1 = (Throwable)null;
         Throwable var2 = var1;

         label106: {
            try {
               if (var0.bufferField.size() <= 0L) {
                  break label106;
               }

               var0.sink.write(var0.bufferField, var0.bufferField.size());
            } finally {
               break label106;
            }

         }

         label100: {
            try {
               var0.sink.close();
            } catch (Throwable var8) {
               var1 = var1;
               if (var2 == null) {
                  var1 = var8;
               }
               break label100;
            }

            var1 = var1;
         }

         var0.closed = true;
         if (var1 != null) {
            throw var1;
         }
      }
   }

   public static final BufferedSink commonEmit(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEmit");
      if (var0.closed ^ true) {
         long var1 = var0.bufferField.size();
         if (var1 > 0L) {
            var0.sink.write(var0.bufferField, var1);
         }

         return (BufferedSink)var0;
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonEmitCompleteSegments(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEmitCompleteSegments");
      if (var0.closed ^ true) {
         long var1 = var0.bufferField.completeSegmentByteCount();
         if (var1 > 0L) {
            var0.sink.write(var0.bufferField, var1);
         }

         return (BufferedSink)var0;
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final void commonFlush(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonFlush");
      if (var0.closed ^ true) {
         if (var0.bufferField.size() > 0L) {
            var0.sink.write(var0.bufferField, var0.bufferField.size());
         }

         var0.sink.flush();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final Timeout commonTimeout(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonTimeout");
      return var0.sink.timeout();
   }

   public static final String commonToString(RealBufferedSink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToString");
      StringBuilder var1 = new StringBuilder();
      var1.append("buffer(");
      var1.append(var0.sink);
      var1.append(')');
      return var1.toString();
   }

   public static final BufferedSink commonWrite(RealBufferedSink var0, ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      if (var0.closed ^ true) {
         var0.bufferField.write(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWrite(RealBufferedSink var0, ByteString var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      if (var0.closed ^ true) {
         var0.bufferField.write(var1, var2, var3);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWrite(RealBufferedSink var0, Source var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");

      while(var2 > 0L) {
         long var4 = var1.read(var0.bufferField, var2);
         if (var4 == -1L) {
            throw (Throwable)(new EOFException());
         }

         var2 -= var4;
         var0.emitCompleteSegments();
      }

      return (BufferedSink)var0;
   }

   public static final BufferedSink commonWrite(RealBufferedSink var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (var0.closed ^ true) {
         var0.bufferField.write(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWrite(RealBufferedSink var0, byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (var0.closed ^ true) {
         var0.bufferField.write(var1, var2, var3);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final void commonWrite(RealBufferedSink var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (var0.closed ^ true) {
         var0.bufferField.write(var1, var2);
         var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final long commonWriteAll(RealBufferedSink var0, Source var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteAll");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var2 = 0L;

      while(true) {
         long var4 = var1.read(var0.bufferField, (long)8192);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
         var0.emitCompleteSegments();
      }
   }

   public static final BufferedSink commonWriteByte(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteByte");
      if (var0.closed ^ true) {
         var0.bufferField.writeByte(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteDecimalLong(RealBufferedSink var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteDecimalLong");
      if (var0.closed ^ true) {
         var0.bufferField.writeDecimalLong(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteHexadecimalUnsignedLong(RealBufferedSink var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteHexadecimalUnsignedLong");
      if (var0.closed ^ true) {
         var0.bufferField.writeHexadecimalUnsignedLong(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteInt(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteInt");
      if (var0.closed ^ true) {
         var0.bufferField.writeInt(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteIntLe(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteIntLe");
      if (var0.closed ^ true) {
         var0.bufferField.writeIntLe(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteLong(RealBufferedSink var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteLong");
      if (var0.closed ^ true) {
         var0.bufferField.writeLong(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteLongLe(RealBufferedSink var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteLongLe");
      if (var0.closed ^ true) {
         var0.bufferField.writeLongLe(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteShort(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteShort");
      if (var0.closed ^ true) {
         var0.bufferField.writeShort(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteShortLe(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteShortLe");
      if (var0.closed ^ true) {
         var0.bufferField.writeShortLe(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteUtf8(RealBufferedSink var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteUtf8");
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (var0.closed ^ true) {
         var0.bufferField.writeUtf8(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteUtf8(RealBufferedSink var0, String var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteUtf8");
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (var0.closed ^ true) {
         var0.bufferField.writeUtf8(var1, var2, var3);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final BufferedSink commonWriteUtf8CodePoint(RealBufferedSink var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteUtf8CodePoint");
      if (var0.closed ^ true) {
         var0.bufferField.writeUtf8CodePoint(var1);
         return var0.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }
}
