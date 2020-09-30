package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.InflaterSource;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lokhttp3/internal/ws/MessageInflater;", "Ljava/io/Closeable;", "noContextTakeover", "", "(Z)V", "deflatedBytes", "Lokio/Buffer;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "close", "", "inflate", "buffer", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MessageInflater implements Closeable {
   private final Buffer deflatedBytes;
   private final Inflater inflater;
   private final InflaterSource inflaterSource;
   private final boolean noContextTakeover;

   public MessageInflater(boolean var1) {
      this.noContextTakeover = var1;
      this.deflatedBytes = new Buffer();
      this.inflater = new Inflater(true);
      this.inflaterSource = new InflaterSource((Source)this.deflatedBytes, this.inflater);
   }

   public void close() throws IOException {
      this.inflaterSource.close();
   }

   public final void inflate(Buffer var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      boolean var2;
      if (this.deflatedBytes.size() == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      } else {
         if (this.noContextTakeover) {
            this.inflater.reset();
         }

         this.deflatedBytes.writeAll((Source)var1);
         this.deflatedBytes.writeInt(65535);
         long var3 = this.inflater.getBytesRead();
         long var5 = this.deflatedBytes.size();

         do {
            this.inflaterSource.readOrInflate(var1, Long.MAX_VALUE);
         } while(this.inflater.getBytesRead() < var3 + var5);

      }
   }
}
