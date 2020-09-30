package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
   d2 = {"Lokio/PeekSource;", "Lokio/Source;", "upstream", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "buffer", "Lokio/Buffer;", "closed", "", "expectedPos", "", "expectedSegment", "Lokio/Segment;", "pos", "", "close", "", "read", "sink", "byteCount", "timeout", "Lokio/Timeout;", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class PeekSource implements Source {
   private final Buffer buffer;
   private boolean closed;
   private int expectedPos;
   private Segment expectedSegment;
   private long pos;
   private final BufferedSource upstream;

   public PeekSource(BufferedSource var1) {
      Intrinsics.checkParameterIsNotNull(var1, "upstream");
      super();
      this.upstream = var1;
      Buffer var3 = var1.getBuffer();
      this.buffer = var3;
      this.expectedSegment = var3.head;
      Segment var4 = this.buffer.head;
      int var2;
      if (var4 != null) {
         var2 = var4.pos;
      } else {
         var2 = -1;
      }

      this.expectedPos = var2;
   }

   public void close() {
      this.closed = true;
   }

   public long read(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      boolean var4 = false;
      long var10;
      int var5 = (var10 = var2 - 0L) == 0L ? 0 : (var10 < 0L ? -1 : 1);
      boolean var6;
      if (var5 >= 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         if (!(this.closed ^ true)) {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         } else {
            Segment var7;
            label49: {
               var7 = this.expectedSegment;
               if (var7 != null) {
                  var6 = var4;
                  if (var7 != this.buffer.head) {
                     break label49;
                  }

                  int var8 = this.expectedPos;
                  var7 = this.buffer.head;
                  if (var7 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var4;
                  if (var8 != var7.pos) {
                     break label49;
                  }
               }

               var6 = true;
            }

            if (var6) {
               if (var5 == 0) {
                  return 0L;
               } else if (!this.upstream.request(this.pos + 1L)) {
                  return -1L;
               } else {
                  if (this.expectedSegment == null && this.buffer.head != null) {
                     this.expectedSegment = this.buffer.head;
                     var7 = this.buffer.head;
                     if (var7 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.expectedPos = var7.pos;
                  }

                  var2 = Math.min(var2, this.buffer.size() - this.pos);
                  this.buffer.copyTo(var1, this.pos, var2);
                  this.pos += var2;
                  return var2;
               }
            } else {
               throw (Throwable)(new IllegalStateException("Peek source is invalid because upstream source was used".toString()));
            }
         }
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append("byteCount < 0: ");
         var9.append(var2);
         throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
      }
   }

   public Timeout timeout() {
      return this.upstream.timeout();
   }
}
