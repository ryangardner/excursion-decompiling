package okio;

import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lokio/OutputStreamSink;", "Lokio/Sink;", "out", "Ljava/io/OutputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/OutputStream;Lokio/Timeout;)V", "close", "", "flush", "toString", "", "write", "source", "Lokio/Buffer;", "byteCount", "", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
final class OutputStreamSink implements Sink {
   private final OutputStream out;
   private final Timeout timeout;

   public OutputStreamSink(OutputStream var1, Timeout var2) {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      Intrinsics.checkParameterIsNotNull(var2, "timeout");
      super();
      this.out = var1;
      this.timeout = var2;
   }

   public void close() {
      this.out.close();
   }

   public void flush() {
      this.out.flush();
   }

   public Timeout timeout() {
      return this.timeout;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("sink(");
      var1.append(this.out);
      var1.append(')');
      return var1.toString();
   }

   public void write(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      _Util.checkOffsetAndCount(var1.size(), 0L, var2);

      while(var2 > 0L) {
         this.timeout.throwIfReached();
         Segment var4 = var1.head;
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         int var5 = (int)Math.min(var2, (long)(var4.limit - var4.pos));
         this.out.write(var4.data, var4.pos, var5);
         var4.pos += var5;
         long var6 = (long)var5;
         long var8 = var2 - var6;
         var1.setSize$okio(var1.size() - var6);
         var2 = var8;
         if (var4.pos == var4.limit) {
            var1.head = var4.pop();
            SegmentPool.recycle(var4);
            var2 = var8;
         }
      }

   }
}
