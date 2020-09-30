package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import okio.internal.BufferKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\rH\u0016J\b\u0010\u001e\u001a\u00020\u0001H\u0016J\u0018\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J(\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020'H\u0016J \u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020'2\u0006\u0010 \u001a\u00020\"2\u0006\u0010#\u001a\u00020\"H\u0016J\u0018\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010%\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\u0014H\u0016J\b\u0010+\u001a\u00020'H\u0016J\u0010\u0010+\u001a\u00020'2\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010,\u001a\u00020\u0018H\u0016J\u0010\u0010,\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010-\u001a\u00020\u0012H\u0016J\u0010\u0010.\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020'H\u0016J\u0018\u0010.\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010/\u001a\u00020\u0012H\u0016J\b\u00100\u001a\u00020\"H\u0016J\b\u00101\u001a\u00020\"H\u0016J\b\u00102\u001a\u00020\u0012H\u0016J\b\u00103\u001a\u00020\u0012H\u0016J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000205H\u0016J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0016J\u0018\u00107\u001a\u0002082\u0006\u0010#\u001a\u00020\u00122\u0006\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u000208H\u0016J\u0010\u0010;\u001a\u0002082\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010<\u001a\u00020\"H\u0016J\n\u0010=\u001a\u0004\u0018\u000108H\u0016J\b\u0010>\u001a\u000208H\u0016J\u0010\u0010>\u001a\u0002082\u0006\u0010?\u001a\u00020\u0012H\u0016J\u0010\u0010@\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010A\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0012H\u0016J\u0010\u0010B\u001a\u00020\"2\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0012H\u0016J\b\u0010F\u001a\u00020GH\u0016J\b\u0010H\u001a\u000208H\u0016R\u001b\u0010\u0005\u001a\u00020\u00068Ö\u0002X\u0096\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006I"},
   d2 = {"Lokio/RealBufferedSource;", "Lokio/BufferedSource;", "source", "Lokio/Source;", "(Lokio/Source;)V", "buffer", "Lokio/Buffer;", "buffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "exhausted", "indexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "peek", "rangeEquals", "offset", "bytesOffset", "", "byteCount", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "", "charset", "Ljava/nio/charset/Charset;", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "skip", "timeout", "Lokio/Timeout;", "toString", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealBufferedSource implements BufferedSource {
   public final Buffer bufferField;
   public boolean closed;
   public final Source source;

   public RealBufferedSource(Source var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      super();
      this.source = var1;
      this.bufferField = new Buffer();
   }

   // $FF: synthetic method
   public static void buffer$annotations() {
   }

   public Buffer buffer() {
      return this.bufferField;
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         this.source.close();
         this.bufferField.clear();
      }

   }

   public boolean exhausted() {
      boolean var1 = this.closed;
      boolean var2 = true;
      if (!(var1 ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         if (!this.bufferField.exhausted() || this.source.read(this.bufferField, (long)8192) != -1L) {
            var2 = false;
         }

         return var2;
      }
   }

   public Buffer getBuffer() {
      return this.bufferField;
   }

   public long indexOf(byte var1) {
      return this.indexOf(var1, 0L, Long.MAX_VALUE);
   }

   public long indexOf(byte var1, long var2) {
      return this.indexOf(var1, var2, Long.MAX_VALUE);
   }

   public long indexOf(byte var1, long var2, long var4) {
      boolean var6 = this.closed;
      boolean var7 = true;
      if (!(var6 ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         if (0L > var2 || var4 < var2) {
            var7 = false;
         }

         if (!var7) {
            StringBuilder var14 = new StringBuilder();
            var14.append("fromIndex=");
            var14.append(var2);
            var14.append(" toIndex=");
            var14.append(var4);
            throw (Throwable)(new IllegalArgumentException(var14.toString().toString()));
         } else {
            long var10;
            while(true) {
               long var8 = -1L;
               var10 = var8;
               if (var2 >= var4) {
                  break;
               }

               var10 = this.bufferField.indexOf(var1, var2, var4);
               if (var10 != -1L) {
                  break;
               }

               long var12 = this.bufferField.size();
               var10 = var8;
               if (var12 >= var4) {
                  break;
               }

               if (this.source.read(this.bufferField, (long)8192) == -1L) {
                  var10 = var8;
                  break;
               }

               var2 = Math.max(var2, var12);
            }

            return var10;
         }
      }
   }

   public long indexOf(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      return this.indexOf(var1, 0L);
   }

   public long indexOf(ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      if (!(this.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         while(true) {
            long var4 = this.bufferField.indexOf(var1, var2);
            if (var4 != -1L) {
               var2 = var4;
               break;
            }

            var4 = this.bufferField.size();
            if (this.source.read(this.bufferField, (long)8192) == -1L) {
               var2 = -1L;
               break;
            }

            var2 = Math.max(var2, var4 - (long)var1.size() + 1L);
         }

         return var2;
      }
   }

   public long indexOfElement(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      return this.indexOfElement(var1, 0L);
   }

   public long indexOfElement(ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      if (!(this.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         while(true) {
            long var4 = this.bufferField.indexOfElement(var1, var2);
            if (var4 != -1L) {
               var2 = var4;
               break;
            }

            var4 = this.bufferField.size();
            if (this.source.read(this.bufferField, (long)8192) == -1L) {
               var2 = -1L;
               break;
            }

            var2 = Math.max(var2, var4);
         }

         return var2;
      }
   }

   public InputStream inputStream() {
      return (InputStream)(new InputStream() {
         public int available() {
            if (!RealBufferedSource.this.closed) {
               return (int)Math.min(RealBufferedSource.this.bufferField.size(), (long)Integer.MAX_VALUE);
            } else {
               throw (Throwable)(new IOException("closed"));
            }
         }

         public void close() {
            RealBufferedSource.this.close();
         }

         public int read() {
            if (!RealBufferedSource.this.closed) {
               return RealBufferedSource.this.bufferField.size() == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.bufferField, (long)8192) == -1L ? -1 : RealBufferedSource.this.bufferField.readByte() & 255;
            } else {
               throw (Throwable)(new IOException("closed"));
            }
         }

         public int read(byte[] var1, int var2, int var3) {
            Intrinsics.checkParameterIsNotNull(var1, "data");
            if (!RealBufferedSource.this.closed) {
               _Util.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
               return RealBufferedSource.this.bufferField.size() == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.bufferField, (long)8192) == -1L ? -1 : RealBufferedSource.this.bufferField.read(var1, var2, var3);
            } else {
               throw (Throwable)(new IOException("closed"));
            }
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(RealBufferedSource.this);
            var1.append(".inputStream()");
            return var1.toString();
         }
      });
   }

   public boolean isOpen() {
      return this.closed ^ true;
   }

   public BufferedSource peek() {
      return Okio.buffer((Source)(new PeekSource((BufferedSource)this)));
   }

   public boolean rangeEquals(long var1, ByteString var3) {
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      return this.rangeEquals(var1, var3, 0, var3.size());
   }

   public boolean rangeEquals(long var1, ByteString var3, int var4, int var5) {
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      boolean var6 = this.closed;
      boolean var7 = true;
      if (!(var6 ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         if (var1 >= 0L && var4 >= 0 && var5 >= 0 && var3.size() - var4 >= var5) {
            int var8 = 0;

            while(true) {
               var6 = var7;
               if (var8 >= var5) {
                  return var6;
               }

               long var9 = (long)var8 + var1;
               if (!this.request(1L + var9) || this.bufferField.getByte(var9) != var3.getByte(var4 + var8)) {
                  break;
               }

               ++var8;
            }
         }

         var6 = false;
         return var6;
      }
   }

   public int read(ByteBuffer var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return this.bufferField.size() == 0L && this.source.read(this.bufferField, (long)8192) == -1L ? -1 : this.bufferField.read(var1);
   }

   public int read(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var4 = (long)var1.length;
      long var6 = (long)var2;
      long var8 = (long)var3;
      _Util.checkOffsetAndCount(var4, var6, var8);
      if (this.bufferField.size() == 0L && this.source.read(this.bufferField, (long)8192) == -1L) {
         var2 = -1;
      } else {
         var3 = (int)Math.min(var8, this.bufferField.size());
         var2 = this.bufferField.read(var1, var2, var3);
      }

      return var2;
   }

   public long read(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (!(true ^ this.closed)) {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         } else {
            long var5 = this.bufferField.size();
            long var7 = -1L;
            if (var5 == 0L && this.source.read(this.bufferField, (long)8192) == -1L) {
               var2 = var7;
            } else {
               var2 = Math.min(var2, this.bufferField.size());
               var2 = this.bufferField.read(var1, var2);
            }

            return var2;
         }
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append("byteCount < 0: ");
         var9.append(var2);
         throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
      }
   }

   public long readAll(Sink var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var2 = 0L;

      long var4;
      while(this.source.read(this.bufferField, (long)8192) != -1L) {
         var4 = this.bufferField.completeSegmentByteCount();
         if (var4 > 0L) {
            var2 += var4;
            var1.write(this.bufferField, var4);
         }
      }

      var4 = var2;
      if (this.bufferField.size() > 0L) {
         var4 = var2 + this.bufferField.size();
         Buffer var6 = this.bufferField;
         var1.write(var6, var6.size());
      }

      return var4;
   }

   public byte readByte() {
      this.require(1L);
      return this.bufferField.readByte();
   }

   public byte[] readByteArray() {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readByteArray();
   }

   public byte[] readByteArray(long var1) {
      this.require(var1);
      return this.bufferField.readByteArray(var1);
   }

   public ByteString readByteString() {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readByteString();
   }

   public ByteString readByteString(long var1) {
      this.require(var1);
      return this.bufferField.readByteString(var1);
   }

   public long readDecimalLong() {
      this.require(1L);
      long var1 = 0L;

      while(true) {
         long var3 = var1 + 1L;
         if (!this.request(var3)) {
            break;
         }

         byte var5 = this.bufferField.getByte(var1);
         if (var5 < (byte)48 || var5 > (byte)57) {
            long var9;
            int var6 = (var9 = var1 - 0L) == 0L ? 0 : (var9 < 0L ? -1 : 1);
            if (var6 != 0 || var5 != (byte)45) {
               if (var6 == 0) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("Expected leading [0-9] or '-' character but was 0x");
                  String var8 = Integer.toString(var5, CharsKt.checkRadix(CharsKt.checkRadix(16)));
                  Intrinsics.checkExpressionValueIsNotNull(var8, "java.lang.Integer.toStri…(this, checkRadix(radix))");
                  var7.append(var8);
                  throw (Throwable)(new NumberFormatException(var7.toString()));
               }
               break;
            }
         }

         var1 = var3;
      }

      return this.bufferField.readDecimalLong();
   }

   public void readFully(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      try {
         this.require(var2);
      } catch (EOFException var5) {
         var1.writeAll((Source)this.bufferField);
         throw (Throwable)var5;
      }

      this.bufferField.readFully(var1, var2);
   }

   public void readFully(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      try {
         this.require((long)var1.length);
      } catch (EOFException var6) {
         int var5;
         for(int var3 = 0; this.bufferField.size() > 0L; var3 += var5) {
            Buffer var4 = this.bufferField;
            var5 = var4.read(var1, var3, (int)var4.size());
            if (var5 == -1) {
               throw (Throwable)(new AssertionError());
            }
         }

         throw (Throwable)var6;
      }

      this.bufferField.readFully(var1);
   }

   public long readHexadecimalUnsignedLong() {
      this.require(1L);
      int var1 = 0;

      while(true) {
         int var2 = var1 + 1;
         if (!this.request((long)var2)) {
            break;
         }

         byte var3 = this.bufferField.getByte((long)var1);
         if ((var3 < (byte)48 || var3 > (byte)57) && (var3 < (byte)97 || var3 > (byte)102) && (var3 < (byte)65 || var3 > (byte)70)) {
            if (var1 == 0) {
               StringBuilder var4 = new StringBuilder();
               var4.append("Expected leading [0-9a-fA-F] character but was 0x");
               String var5 = Integer.toString(var3, CharsKt.checkRadix(CharsKt.checkRadix(16)));
               Intrinsics.checkExpressionValueIsNotNull(var5, "java.lang.Integer.toStri…(this, checkRadix(radix))");
               var4.append(var5);
               throw (Throwable)(new NumberFormatException(var4.toString()));
            }
            break;
         }

         var1 = var2;
      }

      return this.bufferField.readHexadecimalUnsignedLong();
   }

   public int readInt() {
      this.require(4L);
      return this.bufferField.readInt();
   }

   public int readIntLe() {
      this.require(4L);
      return this.bufferField.readIntLe();
   }

   public long readLong() {
      this.require(8L);
      return this.bufferField.readLong();
   }

   public long readLongLe() {
      this.require(8L);
      return this.bufferField.readLongLe();
   }

   public short readShort() {
      this.require(2L);
      return this.bufferField.readShort();
   }

   public short readShortLe() {
      this.require(2L);
      return this.bufferField.readShortLe();
   }

   public String readString(long var1, Charset var3) {
      Intrinsics.checkParameterIsNotNull(var3, "charset");
      this.require(var1);
      return this.bufferField.readString(var1, var3);
   }

   public String readString(Charset var1) {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      this.bufferField.writeAll(this.source);
      return this.bufferField.readString(var1);
   }

   public String readUtf8() {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readUtf8();
   }

   public String readUtf8(long var1) {
      this.require(var1);
      return this.bufferField.readUtf8(var1);
   }

   public int readUtf8CodePoint() {
      this.require(1L);
      byte var1 = this.bufferField.getByte(0L);
      if ((var1 & 224) == 192) {
         this.require(2L);
      } else if ((var1 & 240) == 224) {
         this.require(3L);
      } else if ((var1 & 248) == 240) {
         this.require(4L);
      }

      return this.bufferField.readUtf8CodePoint();
   }

   public String readUtf8Line() {
      long var1 = this.indexOf((byte)10);
      String var3;
      if (var1 == -1L) {
         if (this.bufferField.size() != 0L) {
            var3 = this.readUtf8(this.bufferField.size());
         } else {
            var3 = null;
         }
      } else {
         var3 = BufferKt.readUtf8Line(this.bufferField, var1);
      }

      return var3;
   }

   public String readUtf8LineStrict() {
      return this.readUtf8LineStrict(Long.MAX_VALUE);
   }

   public String readUtf8LineStrict(long var1) {
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
         long var7 = this.indexOf(var6, 0L, var4);
         String var12;
         if (var7 != -1L) {
            var12 = BufferKt.readUtf8Line(this.bufferField, var7);
         } else {
            if (var4 >= Long.MAX_VALUE || !this.request(var4) || this.bufferField.getByte(var4 - 1L) != (byte)13 || !this.request(1L + var4) || this.bufferField.getByte(var4) != var6) {
               Buffer var13 = new Buffer();
               Buffer var10 = this.bufferField;
               var4 = var10.size();
               var10.copyTo(var13, 0L, Math.min((long)32, var4));
               StringBuilder var11 = new StringBuilder();
               var11.append("\\n not found: limit=");
               var11.append(Math.min(this.bufferField.size(), var1));
               var11.append(" content=");
               var11.append(var13.readByteString().hex());
               var11.append("…");
               throw (Throwable)(new EOFException(var11.toString()));
            }

            var12 = BufferKt.readUtf8Line(this.bufferField, var4);
         }

         return var12;
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append("limit < 0: ");
         var9.append(var1);
         throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
      }
   }

   public boolean request(long var1) {
      boolean var3 = false;
      boolean var4;
      if (var1 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         StringBuilder var5 = new StringBuilder();
         var5.append("byteCount < 0: ");
         var5.append(var1);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      } else if (!(this.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         do {
            if (this.bufferField.size() >= var1) {
               var3 = true;
               break;
            }
         } while(this.source.read(this.bufferField, (long)8192) != -1L);

         return var3;
      }
   }

   public void require(long var1) {
      if (!this.request(var1)) {
         throw (Throwable)(new EOFException());
      }
   }

   public int select(Options var1) {
      Intrinsics.checkParameterIsNotNull(var1, "options");
      if (!(this.closed ^ true)) {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      } else {
         int var2;
         while(true) {
            var2 = BufferKt.selectPrefix(this.bufferField, var1, true);
            if (var2 != -2) {
               if (var2 != -1) {
                  int var3 = var1.getByteStrings$okio()[var2].size();
                  this.bufferField.skip((long)var3);
                  break;
               }
            } else if (this.source.read(this.bufferField, (long)8192) != -1L) {
               continue;
            }

            var2 = -1;
            break;
         }

         return var2;
      }
   }

   public void skip(long var1) {
      if (this.closed ^ true) {
         while(var1 > 0L) {
            if (this.bufferField.size() == 0L && this.source.read(this.bufferField, (long)8192) == -1L) {
               throw (Throwable)(new EOFException());
            }

            long var3 = Math.min(var1, this.bufferField.size());
            this.bufferField.skip(var3);
            var1 -= var3;
         }

      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public Timeout timeout() {
      return this.source.timeout();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("buffer(");
      var1.append(this.source);
      var1.append(')');
      return var1.toString();
   }
}
