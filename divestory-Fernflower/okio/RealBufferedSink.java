package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0001H\u0016J\b\u0010\u0011\u001a\u00020\u0001H\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\b\u0010\u0013\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001eH\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#H\u0016J \u0010\u001a\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020$2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010%\u001a\u00020!2\u0006\u0010\u001c\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u001bH\u0016J\u0010\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010*\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010-\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001bH\u0016J\u0010\u0010.\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u0010/\u001a\u00020\u00012\u0006\u0010)\u001a\u00020!H\u0016J\u0010\u00100\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0010\u00102\u001a\u00020\u00012\u0006\u00101\u001a\u00020\u001bH\u0016J\u0018\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u000206H\u0016J(\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001b2\u0006\u00105\u001a\u000206H\u0016J\u0010\u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u0019H\u0016J \u00109\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u00192\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u001bH\u0016J\u0010\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u001bH\u0016R\u001b\u0010\u0005\u001a\u00020\u00068Ö\u0002X\u0096\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006<"},
   d2 = {"Lokio/RealBufferedSink;", "Lokio/BufferedSink;", "sink", "Lokio/Sink;", "(Lokio/Sink;)V", "buffer", "Lokio/Buffer;", "buffer$annotations", "()V", "getBuffer", "()Lokio/Buffer;", "bufferField", "closed", "", "close", "", "emit", "emitCompleteSegments", "flush", "isOpen", "outputStream", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "toString", "", "write", "", "source", "Ljava/nio/ByteBuffer;", "", "offset", "byteCount", "", "byteString", "Lokio/ByteString;", "Lokio/Source;", "writeAll", "writeByte", "b", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "charset", "Ljava/nio/charset/Charset;", "beginIndex", "endIndex", "writeUtf8", "writeUtf8CodePoint", "codePoint", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealBufferedSink implements BufferedSink {
   public final Buffer bufferField;
   public boolean closed;
   public final Sink sink;

   public RealBufferedSink(Sink var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      super();
      this.sink = var1;
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
         Throwable var1 = (Throwable)null;
         Throwable var2 = var1;

         label98: {
            try {
               if (this.bufferField.size() <= 0L) {
                  break label98;
               }

               this.sink.write(this.bufferField, this.bufferField.size());
            } finally {
               break label98;
            }

         }

         label92: {
            try {
               this.sink.close();
            } catch (Throwable var8) {
               var1 = var1;
               if (var2 == null) {
                  var1 = var8;
               }
               break label92;
            }

            var1 = var1;
         }

         this.closed = true;
         if (var1 != null) {
            throw var1;
         }
      }

   }

   public BufferedSink emit() {
      if (this.closed ^ true) {
         long var1 = this.bufferField.size();
         if (var1 > 0L) {
            this.sink.write(this.bufferField, var1);
         }

         return (BufferedSink)this;
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink emitCompleteSegments() {
      if (this.closed ^ true) {
         long var1 = this.bufferField.completeSegmentByteCount();
         if (var1 > 0L) {
            this.sink.write(this.bufferField, var1);
         }

         return (BufferedSink)this;
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public void flush() {
      if (this.closed ^ true) {
         if (this.bufferField.size() > 0L) {
            Sink var1 = this.sink;
            Buffer var2 = this.bufferField;
            var1.write(var2, var2.size());
         }

         this.sink.flush();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public Buffer getBuffer() {
      return this.bufferField;
   }

   public boolean isOpen() {
      return this.closed ^ true;
   }

   public OutputStream outputStream() {
      return (OutputStream)(new OutputStream() {
         public void close() {
            RealBufferedSink.this.close();
         }

         public void flush() {
            if (!RealBufferedSink.this.closed) {
               RealBufferedSink.this.flush();
            }

         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(RealBufferedSink.this);
            var1.append(".outputStream()");
            return var1.toString();
         }

         public void write(int var1) {
            if (!RealBufferedSink.this.closed) {
               RealBufferedSink.this.bufferField.writeByte((byte)var1);
               RealBufferedSink.this.emitCompleteSegments();
            } else {
               throw (Throwable)(new IOException("closed"));
            }
         }

         public void write(byte[] var1, int var2, int var3) {
            Intrinsics.checkParameterIsNotNull(var1, "data");
            if (!RealBufferedSink.this.closed) {
               RealBufferedSink.this.bufferField.write(var1, var2, var3);
               RealBufferedSink.this.emitCompleteSegments();
            } else {
               throw (Throwable)(new IOException("closed"));
            }
         }
      });
   }

   public Timeout timeout() {
      return this.sink.timeout();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("buffer(");
      var1.append(this.sink);
      var1.append(')');
      return var1.toString();
   }

   public int write(ByteBuffer var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (this.closed ^ true) {
         int var2 = this.bufferField.write(var1);
         this.emitCompleteSegments();
         return var2;
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink write(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      if (this.closed ^ true) {
         this.bufferField.write(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink write(ByteString var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      if (this.closed ^ true) {
         this.bufferField.write(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink write(Source var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");

      while(var2 > 0L) {
         long var4 = var1.read(this.bufferField, var2);
         if (var4 == -1L) {
            throw (Throwable)(new EOFException());
         }

         var2 -= var4;
         this.emitCompleteSegments();
      }

      return (BufferedSink)this;
   }

   public BufferedSink write(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (this.closed ^ true) {
         this.bufferField.write(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink write(byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (this.closed ^ true) {
         this.bufferField.write(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public void write(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (this.closed ^ true) {
         this.bufferField.write(var1, var2);
         this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public long writeAll(Source var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var2 = 0L;

      while(true) {
         long var4 = var1.read(this.bufferField, (long)8192);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
         this.emitCompleteSegments();
      }
   }

   public BufferedSink writeByte(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeByte(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeDecimalLong(long var1) {
      if (this.closed ^ true) {
         this.bufferField.writeDecimalLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeHexadecimalUnsignedLong(long var1) {
      if (this.closed ^ true) {
         this.bufferField.writeHexadecimalUnsignedLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeInt(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeInt(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeIntLe(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeIntLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeLong(long var1) {
      if (this.closed ^ true) {
         this.bufferField.writeLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeLongLe(long var1) {
      if (this.closed ^ true) {
         this.bufferField.writeLongLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeShort(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeShort(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeShortLe(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeShortLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeString(String var1, int var2, int var3, Charset var4) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      Intrinsics.checkParameterIsNotNull(var4, "charset");
      if (this.closed ^ true) {
         this.bufferField.writeString(var1, var2, var3, var4);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeString(String var1, Charset var2) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      if (this.closed ^ true) {
         this.bufferField.writeString(var1, var2);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeUtf8(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (this.closed ^ true) {
         this.bufferField.writeUtf8(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeUtf8(String var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      if (this.closed ^ true) {
         this.bufferField.writeUtf8(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   public BufferedSink writeUtf8CodePoint(int var1) {
      if (this.closed ^ true) {
         this.bufferField.writeUtf8CodePoint(var1);
         return this.emitCompleteSegments();
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }
}
