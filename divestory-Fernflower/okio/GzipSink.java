package okio;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\u0010J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\b\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lokio/GzipSink;", "Lokio/Sink;", "sink", "(Lokio/Sink;)V", "closed", "", "crc", "Ljava/util/zip/CRC32;", "deflater", "Ljava/util/zip/Deflater;", "()Ljava/util/zip/Deflater;", "deflaterSink", "Lokio/DeflaterSink;", "Lokio/RealBufferedSink;", "close", "", "-deprecated_deflater", "flush", "timeout", "Lokio/Timeout;", "updateCrc", "buffer", "Lokio/Buffer;", "byteCount", "", "write", "source", "writeFooter", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class GzipSink implements Sink {
   private boolean closed;
   private final CRC32 crc;
   private final Deflater deflater;
   private final DeflaterSink deflaterSink;
   private final RealBufferedSink sink;

   public GzipSink(Sink var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      super();
      this.sink = new RealBufferedSink(var1);
      this.deflater = new Deflater(-1, true);
      this.deflaterSink = new DeflaterSink((BufferedSink)this.sink, this.deflater);
      this.crc = new CRC32();
      Buffer var2 = this.sink.bufferField;
      var2.writeShort(8075);
      var2.writeByte(8);
      var2.writeByte(0);
      var2.writeInt(0);
      var2.writeByte(0);
      var2.writeByte(0);
   }

   private final void updateCrc(Buffer var1, long var2) {
      Segment var4 = var1.head;
      Segment var8 = var4;
      long var5 = var2;
      if (var4 == null) {
         Intrinsics.throwNpe();
         var5 = var2;
         var8 = var4;
      }

      while(var5 > 0L) {
         int var7 = (int)Math.min(var5, (long)(var8.limit - var8.pos));
         this.crc.update(var8.data, var8.pos, var7);
         var2 = var5 - (long)var7;
         var4 = var8.next;
         var8 = var4;
         var5 = var2;
         if (var4 == null) {
            Intrinsics.throwNpe();
            var8 = var4;
            var5 = var2;
         }
      }

   }

   private final void writeFooter() {
      this.sink.writeIntLe((int)this.crc.getValue());
      this.sink.writeIntLe((int)this.deflater.getBytesRead());
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "deflater",
   imports = {}
)
   )
   public final Deflater _deprecated_deflater/* $FF was: -deprecated_deflater*/() {
      return this.deflater;
   }

   public void close() throws IOException {
      if (!this.closed) {
         Throwable var1 = (Throwable)null;

         label168:
         try {
            this.deflaterSink.finishDeflate$okio();
            this.writeFooter();
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

   public final Deflater deflater() {
      return this.deflater;
   }

   public void flush() throws IOException {
      this.deflaterSink.flush();
   }

   public Timeout timeout() {
      return this.sink.timeout();
   }

   public void write(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var7;
      int var4 = (var7 = var2 - 0L) == 0L ? 0 : (var7 < 0L ? -1 : 1);
      boolean var5;
      if (var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         if (var4 != 0) {
            this.updateCrc(var1, var2);
            this.deflaterSink.write(var1, var2);
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("byteCount < 0: ");
         var6.append(var2);
         throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
      }
   }
}
