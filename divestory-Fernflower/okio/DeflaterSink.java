package okio;

import java.io.IOException;
import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\tH\u0003J\r\u0010\u000e\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lokio/DeflaterSink;", "Lokio/Sink;", "sink", "deflater", "Ljava/util/zip/Deflater;", "(Lokio/Sink;Ljava/util/zip/Deflater;)V", "Lokio/BufferedSink;", "(Lokio/BufferedSink;Ljava/util/zip/Deflater;)V", "closed", "", "close", "", "deflate", "syncFlush", "finishDeflate", "finishDeflate$okio", "flush", "timeout", "Lokio/Timeout;", "toString", "", "write", "source", "Lokio/Buffer;", "byteCount", "", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class DeflaterSink implements Sink {
   private boolean closed;
   private final Deflater deflater;
   private final BufferedSink sink;

   public DeflaterSink(BufferedSink var1, Deflater var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      Intrinsics.checkParameterIsNotNull(var2, "deflater");
      super();
      this.sink = var1;
      this.deflater = var2;
   }

   public DeflaterSink(Sink var1, Deflater var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      Intrinsics.checkParameterIsNotNull(var2, "deflater");
      this(Okio.buffer(var1), var2);
   }

   private final void deflate(boolean var1) {
      Buffer var2 = this.sink.getBuffer();

      while(true) {
         Segment var3 = var2.writableSegment$okio(1);
         int var4;
         if (var1) {
            var4 = this.deflater.deflate(var3.data, var3.limit, 8192 - var3.limit, 2);
         } else {
            var4 = this.deflater.deflate(var3.data, var3.limit, 8192 - var3.limit);
         }

         if (var4 > 0) {
            var3.limit += var4;
            var2.setSize$okio(var2.size() + (long)var4);
            this.sink.emitCompleteSegments();
         } else if (this.deflater.needsInput()) {
            if (var3.pos == var3.limit) {
               var2.head = var3.pop();
               SegmentPool.recycle(var3);
            }

            return;
         }
      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         Throwable var1 = (Throwable)null;

         label168:
         try {
            this.finishDeflate$okio();
         } finally {
            break label168;
         }

         Throwable var2;
         label165: {
            try {
               this.deflater.end();
            } catch (Throwable var14) {
               var2 = var1;
               if (var1 == null) {
                  var2 = var14;
               }
               break label165;
            }

            var2 = var1;
         }

         label158: {
            try {
               this.sink.close();
            } catch (Throwable var13) {
               var1 = var2;
               if (var2 == null) {
                  var1 = var13;
               }
               break label158;
            }

            var1 = var2;
         }

         this.closed = true;
         if (var1 != null) {
            throw var1;
         }
      }
   }

   public final void finishDeflate$okio() {
      this.deflater.finish();
      this.deflate(false);
   }

   public void flush() throws IOException {
      this.deflate(true);
      this.sink.flush();
   }

   public Timeout timeout() {
      return this.sink.timeout();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("DeflaterSink(");
      var1.append(this.sink);
      var1.append(')');
      return var1.toString();
   }

   public void write(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      _Util.checkOffsetAndCount(var1.size(), 0L, var2);

      long var8;
      for(; var2 > 0L; var2 -= var8) {
         Segment var4 = var1.head;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         int var5 = (int)Math.min(var2, (long)(var4.limit - var4.pos));
         this.deflater.setInput(var4.data, var4.pos, var5);
         this.deflate(false);
         long var6 = var1.size();
         var8 = (long)var5;
         var1.setSize$okio(var6 - var8);
         var4.pos += var5;
         if (var4.pos == var4.limit) {
            var1.head = var4.pop();
            SegmentPool.recycle(var4);
         }
      }

   }
}
