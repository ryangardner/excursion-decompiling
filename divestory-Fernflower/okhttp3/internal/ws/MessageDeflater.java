package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Sink;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0006J\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lokhttp3/internal/ws/MessageDeflater;", "Ljava/io/Closeable;", "noContextTakeover", "", "(Z)V", "deflatedBytes", "Lokio/Buffer;", "deflater", "Ljava/util/zip/Deflater;", "deflaterSink", "Lokio/DeflaterSink;", "close", "", "deflate", "buffer", "endsWith", "suffix", "Lokio/ByteString;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MessageDeflater implements Closeable {
   private final Buffer deflatedBytes;
   private final Deflater deflater;
   private final DeflaterSink deflaterSink;
   private final boolean noContextTakeover;

   public MessageDeflater(boolean var1) {
      this.noContextTakeover = var1;
      this.deflatedBytes = new Buffer();
      this.deflater = new Deflater(-1, true);
      this.deflaterSink = new DeflaterSink((Sink)this.deflatedBytes, this.deflater);
   }

   private final boolean endsWith(Buffer var1, ByteString var2) {
      return var1.rangeEquals(var1.size() - (long)var2.size(), var2);
   }

   public void close() throws IOException {
      this.deflaterSink.close();
   }

   public final void deflate(Buffer var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      boolean var2;
      if (this.deflatedBytes.size() == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         if (this.noContextTakeover) {
            this.deflater.reset();
         }

         this.deflaterSink.write(var1, var1.size());
         this.deflaterSink.flush();
         if (this.endsWith(this.deflatedBytes, MessageDeflaterKt.access$getEMPTY_DEFLATE_BLOCK$p())) {
            long var3 = this.deflatedBytes.size();
            long var5 = (long)4;
            Closeable var7 = (Closeable)Buffer.readAndWriteUnsafe$default(this.deflatedBytes, (Buffer.UnsafeCursor)null, 1, (Object)null);
            Throwable var8 = (Throwable)null;

            try {
               ((Buffer.UnsafeCursor)var7).resizeBuffer(var3 - var5);
            } catch (Throwable var14) {
               Throwable var15 = var14;

               try {
                  throw var15;
               } finally {
                  CloseableKt.closeFinally(var7, var14);
               }
            }

            CloseableKt.closeFinally(var7, var8);
         } else {
            this.deflatedBytes.writeByte(0);
         }

         Buffer var16 = this.deflatedBytes;
         var1.write(var16, var16.size());
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }
}
