package okhttp3.internal.cache2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lokhttp3/internal/cache2/FileOperator;", "", "fileChannel", "Ljava/nio/channels/FileChannel;", "(Ljava/nio/channels/FileChannel;)V", "read", "", "pos", "", "sink", "Lokio/Buffer;", "byteCount", "write", "source", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FileOperator {
   private final FileChannel fileChannel;

   public FileOperator(FileChannel var1) {
      Intrinsics.checkParameterIsNotNull(var1, "fileChannel");
      super();
      this.fileChannel = var1;
   }

   public final void read(long var1, Buffer var3, long var4) {
      Intrinsics.checkParameterIsNotNull(var3, "sink");
      if (var4 < 0L) {
         throw (Throwable)(new IndexOutOfBoundsException());
      } else {
         while(var4 > 0L) {
            long var6 = this.fileChannel.transferTo(var1, var4, (WritableByteChannel)var3);
            var1 += var6;
            var4 -= var6;
         }

      }
   }

   public final void write(long var1, Buffer var3, long var4) throws IOException {
      Intrinsics.checkParameterIsNotNull(var3, "source");
      if (var4 >= 0L && var4 <= var3.size()) {
         long var6 = var1;

         for(var1 = var4; var1 > 0L; var1 -= var4) {
            var4 = this.fileChannel.transferFrom((ReadableByteChannel)var3, var6, var1);
            var6 += var4;
         }

      } else {
         throw (Throwable)(new IndexOutOfBoundsException());
      }
   }
}
