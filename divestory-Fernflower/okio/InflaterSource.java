package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u0016\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0014\u001a\u00020\u000bJ\b\u0010\u0015\u001a\u00020\rH\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokio/InflaterSource;", "Lokio/Source;", "source", "inflater", "Ljava/util/zip/Inflater;", "(Lokio/Source;Ljava/util/zip/Inflater;)V", "Lokio/BufferedSource;", "(Lokio/BufferedSource;Ljava/util/zip/Inflater;)V", "bufferBytesHeldByInflater", "", "closed", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "readOrInflate", "refill", "releaseBytesAfterInflate", "timeout", "Lokio/Timeout;", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class InflaterSource implements Source {
   private int bufferBytesHeldByInflater;
   private boolean closed;
   private final Inflater inflater;
   private final BufferedSource source;

   public InflaterSource(BufferedSource var1, Inflater var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "inflater");
      super();
      this.source = var1;
      this.inflater = var2;
   }

   public InflaterSource(Source var1, Inflater var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "inflater");
      this(Okio.buffer(var1), var2);
   }

   private final void releaseBytesAfterInflate() {
      int var1 = this.bufferBytesHeldByInflater;
      if (var1 != 0) {
         var1 -= this.inflater.getRemaining();
         this.bufferBytesHeldByInflater -= var1;
         this.source.skip((long)var1);
      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.inflater.end();
         this.closed = true;
         this.source.close();
      }
   }

   public long read(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      do {
         long var4 = this.readOrInflate(var1, var2);
         if (var4 > 0L) {
            return var4;
         }

         if (this.inflater.finished() || this.inflater.needsDictionary()) {
            return -1L;
         }
      } while(!this.source.exhausted());

      throw (Throwable)(new EOFException("source exhausted prematurely"));
   }

   public final long readOrInflate(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var16;
      int var4 = (var16 = var2 - 0L) == 0L ? 0 : (var16 < 0L ? -1 : 1);
      boolean var5;
      if (var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         if (!(this.closed ^ true)) {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         } else if (var4 == 0) {
            return 0L;
         } else {
            DataFormatException var10000;
            label48: {
               Segment var6;
               int var15;
               boolean var10001;
               try {
                  var6 = var1.writableSegment$okio(1);
                  var15 = (int)Math.min(var2, (long)(8192 - var6.limit));
                  this.refill();
                  var15 = this.inflater.inflate(var6.data, var6.limit, var15);
                  this.releaseBytesAfterInflate();
               } catch (DataFormatException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label48;
               }

               if (var15 > 0) {
                  label58: {
                     long var7;
                     try {
                        var6.limit += var15;
                        var7 = var1.size();
                     } catch (DataFormatException var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label58;
                     }

                     var2 = (long)var15;

                     try {
                        var1.setSize$okio(var7 + var2);
                        return var2;
                     } catch (DataFormatException var9) {
                        var10000 = var9;
                        var10001 = false;
                     }
                  }
               } else {
                  try {
                     if (var6.pos == var6.limit) {
                        var1.head = var6.pop();
                        SegmentPool.recycle(var6);
                     }

                     return 0L;
                  } catch (DataFormatException var11) {
                     var10000 = var11;
                     var10001 = false;
                  }
               }
            }

            DataFormatException var14 = var10000;
            throw (Throwable)(new IOException((Throwable)var14));
         }
      } else {
         StringBuilder var13 = new StringBuilder();
         var13.append("byteCount < 0: ");
         var13.append(var2);
         throw (Throwable)(new IllegalArgumentException(var13.toString().toString()));
      }
   }

   public final boolean refill() throws IOException {
      if (!this.inflater.needsInput()) {
         return false;
      } else if (this.source.exhausted()) {
         return true;
      } else {
         Segment var1 = this.source.getBuffer().head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         this.bufferBytesHeldByInflater = var1.limit - var1.pos;
         this.inflater.setInput(var1.data, var1.pos, this.bufferBytesHeldByInflater);
         return false;
      }
   }

   public Timeout timeout() {
      return this.source.timeout();
   }
}
