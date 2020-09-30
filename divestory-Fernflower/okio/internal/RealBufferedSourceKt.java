package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.RealBufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import okio._Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0080\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0080\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\u0080\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\u0080\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\u0080\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\u0080\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\u0080\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\u0080\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\u0080\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\u0080\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\u0080\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\u0080\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\u0080\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\u0080\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\u0080\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\u0080\b¨\u00068"},
   d2 = {"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class RealBufferedSourceKt {
   public static final void commonClose(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonClose");
      if (!var0.closed) {
         var0.closed = true;
         var0.source.close();
         var0.bufferField.clear();
      }
   }

   public static final boolean commonExhausted(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonExhausted");
      boolean var1 = var0.closed;
      boolean var2 = true;
      if (!(var1 ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         if (!var0.bufferField.exhausted() || var0.source.read(var0.bufferField, (long)8192) != -1L) {
            var2 = false;
         }

         return var2;
      }
   }

   public static final long commonIndexOf(RealBufferedSource var0, byte var1, long var2, long var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOf");
      boolean var6 = var0.closed;
      boolean var7 = true;
      if (!(var6 ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         if (0L > var2 || var4 < var2) {
            var7 = false;
         }

         if (!var7) {
            StringBuilder var10 = new StringBuilder();
            var10.append("fromIndex=");
            var10.append(var2);
            var10.append(" toIndex=");
            var10.append(var4);
            throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
         } else {
            while(var2 < var4) {
               long var8 = var0.bufferField.indexOf(var1, var2, var4);
               if (var8 != -1L) {
                  return var8;
               }

               var8 = var0.bufferField.size();
               if (var8 >= var4 || var0.source.read(var0.bufferField, (long)8192) == -1L) {
                  break;
               }

               var2 = Math.max(var2, var8);
            }

            return -1L;
         }
      }
   }

   public static final long commonIndexOf(RealBufferedSource var0, ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOf");
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      if (!(var0.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         while(true) {
            long var4 = var0.bufferField.indexOf(var1, var2);
            if (var4 != -1L) {
               return var4;
            }

            var4 = var0.bufferField.size();
            if (var0.source.read(var0.bufferField, (long)8192) == -1L) {
               return -1L;
            }

            var2 = Math.max(var2, var4 - (long)var1.size() + 1L);
         }
      }
   }

   public static final long commonIndexOfElement(RealBufferedSource var0, ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOfElement");
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      if (!(var0.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         while(true) {
            long var4 = var0.bufferField.indexOfElement(var1, var2);
            if (var4 != -1L) {
               return var4;
            }

            var4 = var0.bufferField.size();
            if (var0.source.read(var0.bufferField, (long)8192) == -1L) {
               return -1L;
            }

            var2 = Math.max(var2, var4);
         }
      }
   }

   public static final BufferedSource commonPeek(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonPeek");
      return Okio.buffer((Source)(new PeekSource((BufferedSource)var0)));
   }

   public static final boolean commonRangeEquals(RealBufferedSource var0, long var1, ByteString var3, int var4, int var5) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      if (var0.closed ^ true) {
         if (var1 >= 0L && var4 >= 0 && var5 >= 0 && var3.size() - var4 >= var5) {
            for(int var6 = 0; var6 < var5; ++var6) {
               long var7 = (long)var6 + var1;
               if (!var0.request(1L + var7)) {
                  return false;
               }

               if (var0.bufferField.getByte(var7) != var3.getByte(var4 + var6)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final int commonRead(RealBufferedSource var0, byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRead");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var4 = (long)var1.length;
      long var6 = (long)var2;
      long var8 = (long)var3;
      _Util.checkOffsetAndCount(var4, var6, var8);
      if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, (long)8192) == -1L) {
         return -1;
      } else {
         var3 = (int)Math.min(var8, var0.bufferField.size());
         return var0.bufferField.read(var1, var2, var3);
      }
   }

   public static final long commonRead(RealBufferedSource var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRead");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (true ^ var0.closed) {
            if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, (long)8192) == -1L) {
               return -1L;
            } else {
               var2 = Math.min(var2, var0.bufferField.size());
               return var0.bufferField.read(var1, var2);
            }
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("byteCount < 0: ");
         var5.append(var2);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public static final long commonReadAll(RealBufferedSource var0, Sink var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadAll");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var2 = 0L;

      long var4;
      while(var0.source.read(var0.bufferField, (long)8192) != -1L) {
         var4 = var0.bufferField.completeSegmentByteCount();
         if (var4 > 0L) {
            var2 += var4;
            var1.write(var0.bufferField, var4);
         }
      }

      var4 = var2;
      if (var0.bufferField.size() > 0L) {
         var4 = var2 + var0.bufferField.size();
         var1.write(var0.bufferField, var0.bufferField.size());
      }

      return var4;
   }

   public static final byte commonReadByte(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByte");
      var0.require(1L);
      return var0.bufferField.readByte();
   }

   public static final byte[] commonReadByteArray(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteArray");
      var0.bufferField.writeAll(var0.source);
      return var0.bufferField.readByteArray();
   }

   public static final byte[] commonReadByteArray(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteArray");
      var0.require(var1);
      return var0.bufferField.readByteArray(var1);
   }

   public static final ByteString commonReadByteString(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteString");
      var0.bufferField.writeAll(var0.source);
      return var0.bufferField.readByteString();
   }

   public static final ByteString commonReadByteString(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteString");
      var0.require(var1);
      return var0.bufferField.readByteString(var1);
   }

   public static final long commonReadDecimalLong(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadDecimalLong");
      var0.require(1L);
      long var1 = 0L;

      while(true) {
         long var3 = var1 + 1L;
         if (!var0.request(var3)) {
            break;
         }

         byte var5 = var0.bufferField.getByte(var1);
         if (var5 < (byte)48 || var5 > (byte)57) {
            long var9;
            int var6 = (var9 = var1 - 0L) == 0L ? 0 : (var9 < 0L ? -1 : 1);
            if (var6 != 0 || var5 != (byte)45) {
               if (var6 == 0) {
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Expected leading [0-9] or '-' character but was 0x");
                  String var7 = Integer.toString(var5, CharsKt.checkRadix(CharsKt.checkRadix(16)));
                  Intrinsics.checkExpressionValueIsNotNull(var7, "java.lang.Integer.toStri…(this, checkRadix(radix))");
                  var8.append(var7);
                  throw (Throwable)(new NumberFormatException(var8.toString()));
               }
               break;
            }
         }

         var1 = var3;
      }

      return var0.bufferField.readDecimalLong();
   }

   public static final void commonReadFully(RealBufferedSource var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadFully");
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      try {
         var0.require(var2);
      } catch (EOFException var5) {
         var1.writeAll((Source)var0.bufferField);
         throw (Throwable)var5;
      }

      var0.bufferField.readFully(var1, var2);
   }

   public static final void commonReadFully(RealBufferedSource var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadFully");
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      try {
         var0.require((long)var1.length);
      } catch (EOFException var5) {
         int var4;
         for(int var3 = 0; var0.bufferField.size() > 0L; var3 += var4) {
            var4 = var0.bufferField.read(var1, var3, (int)var0.bufferField.size());
            if (var4 == -1) {
               throw (Throwable)(new AssertionError());
            }
         }

         throw (Throwable)var5;
      }

      var0.bufferField.readFully(var1);
   }

   public static final long commonReadHexadecimalUnsignedLong(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadHexadecimalUnsignedLong");
      var0.require(1L);
      int var1 = 0;

      while(true) {
         int var2 = var1 + 1;
         if (!var0.request((long)var2)) {
            break;
         }

         byte var3 = var0.bufferField.getByte((long)var1);
         if ((var3 < (byte)48 || var3 > (byte)57) && (var3 < (byte)97 || var3 > (byte)102) && (var3 < (byte)65 || var3 > (byte)70)) {
            if (var1 == 0) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Expected leading [0-9a-fA-F] character but was 0x");
               String var4 = Integer.toString(var3, CharsKt.checkRadix(CharsKt.checkRadix(16)));
               Intrinsics.checkExpressionValueIsNotNull(var4, "java.lang.Integer.toStri…(this, checkRadix(radix))");
               var5.append(var4);
               throw (Throwable)(new NumberFormatException(var5.toString()));
            }
            break;
         }

         var1 = var2;
      }

      return var0.bufferField.readHexadecimalUnsignedLong();
   }

   public static final int commonReadInt(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadInt");
      var0.require(4L);
      return var0.bufferField.readInt();
   }

   public static final int commonReadIntLe(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadIntLe");
      var0.require(4L);
      return var0.bufferField.readIntLe();
   }

   public static final long commonReadLong(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadLong");
      var0.require(8L);
      return var0.bufferField.readLong();
   }

   public static final long commonReadLongLe(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadLongLe");
      var0.require(8L);
      return var0.bufferField.readLongLe();
   }

   public static final short commonReadShort(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadShort");
      var0.require(2L);
      return var0.bufferField.readShort();
   }

   public static final short commonReadShortLe(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadShortLe");
      var0.require(2L);
      return var0.bufferField.readShortLe();
   }

   public static final String commonReadUtf8(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8");
      var0.bufferField.writeAll(var0.source);
      return var0.bufferField.readUtf8();
   }

   public static final String commonReadUtf8(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8");
      var0.require(var1);
      return var0.bufferField.readUtf8(var1);
   }

   public static final int commonReadUtf8CodePoint(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8CodePoint");
      var0.require(1L);
      byte var1 = var0.bufferField.getByte(0L);
      if ((var1 & 224) == 192) {
         var0.require(2L);
      } else if ((var1 & 240) == 224) {
         var0.require(3L);
      } else if ((var1 & 248) == 240) {
         var0.require(4L);
      }

      return var0.bufferField.readUtf8CodePoint();
   }

   public static final String commonReadUtf8Line(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8Line");
      long var1 = var0.indexOf((byte)10);
      String var3;
      if (var1 == -1L) {
         if (var0.bufferField.size() != 0L) {
            var3 = var0.readUtf8(var0.bufferField.size());
         } else {
            var3 = null;
         }
      } else {
         var3 = BufferKt.readUtf8Line(var0.bufferField, var1);
      }

      return var3;
   }

   public static final String commonReadUtf8LineStrict(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8LineStrict");
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         long var4;
         if (var1 == Long.MAX_VALUE) {
            var4 = Long.MAX_VALUE;
         } else {
            var4 = var1 + 1L;
         }

         byte var6 = (byte)10;
         long var7 = var0.indexOf(var6, 0L, var4);
         if (var7 != -1L) {
            return BufferKt.readUtf8Line(var0.bufferField, var7);
         } else if (var4 < Long.MAX_VALUE && var0.request(var4) && var0.bufferField.getByte(var4 - 1L) == (byte)13 && var0.request(1L + var4) && var0.bufferField.getByte(var4) == var6) {
            return BufferKt.readUtf8Line(var0.bufferField, var4);
         } else {
            Buffer var9 = new Buffer();
            Buffer var10 = var0.bufferField;
            var4 = var0.bufferField.size();
            var10.copyTo(var9, 0L, Math.min((long)32, var4));
            StringBuilder var11 = new StringBuilder();
            var11.append("\\n not found: limit=");
            var11.append(Math.min(var0.bufferField.size(), var1));
            var11.append(" content=");
            var11.append(var9.readByteString().hex());
            var11.append("…");
            throw (Throwable)(new EOFException(var11.toString()));
         }
      } else {
         StringBuilder var12 = new StringBuilder();
         var12.append("limit < 0: ");
         var12.append(var1);
         throw (Throwable)(new IllegalArgumentException(var12.toString().toString()));
      }
   }

   public static final boolean commonRequest(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRequest");
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         if (var0.closed ^ true) {
            do {
               if (var0.bufferField.size() >= var1) {
                  return true;
               }
            } while(var0.source.read(var0.bufferField, (long)8192) != -1L);

            return false;
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("byteCount < 0: ");
         var4.append(var1);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   public static final void commonRequire(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRequire");
      if (!var0.request(var1)) {
         throw (Throwable)(new EOFException());
      }
   }

   public static final int commonSelect(RealBufferedSource var0, Options var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSelect");
      Intrinsics.checkParameterIsNotNull(var1, "options");
      if (!(var0.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         do {
            int var2 = BufferKt.selectPrefix(var0.bufferField, var1, true);
            if (var2 != -2) {
               if (var2 != -1) {
                  int var3 = var1.getByteStrings$okio()[var2].size();
                  var0.bufferField.skip((long)var3);
                  return var2;
               } else {
                  return -1;
               }
            }
         } while(var0.source.read(var0.bufferField, (long)8192) != -1L);

         return -1;
      }
   }

   public static final void commonSkip(RealBufferedSource var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSkip");
      if (var0.closed ^ true) {
         while(var1 > 0L) {
            if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, (long)8192) == -1L) {
               throw (Throwable)(new EOFException());
            }

            long var3 = Math.min(var1, var0.bufferField.size());
            var0.bufferField.skip(var3);
            var1 -= var3;
         }

      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public static final Timeout commonTimeout(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonTimeout");
      return var0.source.timeout();
   }

   public static final String commonToString(RealBufferedSource var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonToString");
      StringBuilder var1 = new StringBuilder();
      var1.append("buffer(");
      var1.append(var0.source);
      var1.append(')');
      return var1.toString();
   }
}
