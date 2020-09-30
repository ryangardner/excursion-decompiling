package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0018H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J \u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"},
   d2 = {"Lokio/GzipSource;", "Lokio/Source;", "source", "(Lokio/Source;)V", "crc", "Ljava/util/zip/CRC32;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "section", "", "Lokio/RealBufferedSource;", "checkEqual", "", "name", "", "expected", "", "actual", "close", "consumeHeader", "consumeTrailer", "read", "", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "updateCrc", "buffer", "offset", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class GzipSource implements Source {
   private final CRC32 crc;
   private final Inflater inflater;
   private final InflaterSource inflaterSource;
   private byte section;
   private final RealBufferedSource source;

   public GzipSource(Source var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      super();
      this.source = new RealBufferedSource(var1);
      this.inflater = new Inflater(true);
      this.inflaterSource = new InflaterSource((BufferedSource)this.source, this.inflater);
      this.crc = new CRC32();
   }

   private final void checkEqual(String var1, int var2, int var3) {
      if (var3 != var2) {
         var1 = String.format("%s: actual 0x%08x != expected 0x%08x", Arrays.copyOf(new Object[]{var1, var3, var2}, 3));
         Intrinsics.checkExpressionValueIsNotNull(var1, "java.lang.String.format(this, *args)");
         throw (Throwable)(new IOException(var1));
      }
   }

   private final void consumeHeader() throws IOException {
      this.source.require(10L);
      byte var1 = this.source.bufferField.getByte(3L);
      boolean var2 = true;
      boolean var3;
      if ((var1 >> 1 & 1) == 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         this.updateCrc(this.source.bufferField, 0L, 10L);
      }

      this.checkEqual("ID1ID2", 8075, this.source.readShort());
      this.source.skip(8L);
      boolean var4;
      if ((var1 >> 2 & 1) == 1) {
         var4 = true;
      } else {
         var4 = false;
      }

      long var5;
      if (var4) {
         this.source.require(2L);
         if (var3) {
            this.updateCrc(this.source.bufferField, 0L, 2L);
         }

         var5 = (long)this.source.bufferField.readShortLe();
         this.source.require(var5);
         if (var3) {
            this.updateCrc(this.source.bufferField, 0L, var5);
         }

         this.source.skip(var5);
      }

      if ((var1 >> 3 & 1) == 1) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var5 = this.source.indexOf((byte)0);
         if (var5 == -1L) {
            throw (Throwable)(new EOFException());
         }

         if (var3) {
            this.updateCrc(this.source.bufferField, 0L, var5 + 1L);
         }

         this.source.skip(var5 + 1L);
      }

      if ((var1 >> 4 & 1) == 1) {
         var4 = var2;
      } else {
         var4 = false;
      }

      if (var4) {
         var5 = this.source.indexOf((byte)0);
         if (var5 == -1L) {
            throw (Throwable)(new EOFException());
         }

         if (var3) {
            this.updateCrc(this.source.bufferField, 0L, var5 + 1L);
         }

         this.source.skip(var5 + 1L);
      }

      if (var3) {
         this.checkEqual("FHCRC", this.source.readShortLe(), (short)((int)this.crc.getValue()));
         this.crc.reset();
      }

   }

   private final void consumeTrailer() throws IOException {
      this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
      this.checkEqual("ISIZE", this.source.readIntLe(), (int)this.inflater.getBytesWritten());
   }

   private final void updateCrc(Buffer var1, long var2, long var4) {
      Segment var6 = var1.head;
      Segment var11 = var6;
      long var7 = var2;
      if (var6 == null) {
         Intrinsics.throwNpe();
         var7 = var2;
         var11 = var6;
      }

      while(var7 >= (long)(var11.limit - var11.pos)) {
         var2 = var7 - (long)(var11.limit - var11.pos);
         var6 = var11.next;
         var11 = var6;
         var7 = var2;
         if (var6 == null) {
            Intrinsics.throwNpe();
            var11 = var6;
            var7 = var2;
         }
      }

      for(; var4 > 0L; var7 = 0L) {
         int var9 = (int)((long)var11.pos + var7);
         int var10 = (int)Math.min((long)(var11.limit - var9), var4);
         this.crc.update(var11.data, var9, var10);
         var4 -= (long)var10;
         var11 = var11.next;
         if (var11 == null) {
            Intrinsics.throwNpe();
         }
      }

   }

   public void close() throws IOException {
      this.inflaterSource.close();
   }

   public long read(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var9;
      int var4 = (var9 = var2 - 0L) == 0L ? 0 : (var9 < 0L ? -1 : 1);
      boolean var5;
      if (var4 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         if (var4 == 0) {
            return 0L;
         } else {
            if (this.section == 0) {
               this.consumeHeader();
               this.section = (byte)1;
            }

            if (this.section == 1) {
               long var6 = var1.size();
               var2 = this.inflaterSource.read(var1, var2);
               if (var2 != -1L) {
                  this.updateCrc(var1, var6, var2);
                  return var2;
               }

               this.section = (byte)2;
            }

            if (this.section == 2) {
               this.consumeTrailer();
               this.section = (byte)3;
               if (!this.source.exhausted()) {
                  throw (Throwable)(new IOException("gzip finished without exhausting source"));
               }
            }

            return -1L;
         }
      } else {
         StringBuilder var8 = new StringBuilder();
         var8.append("byteCount < 0: ");
         var8.append(var2);
         throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
      }
   }

   public Timeout timeout() {
      return this.source.timeout();
   }
}
