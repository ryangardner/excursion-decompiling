package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!J\u0018\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020!H\u0002J\u0016\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020!J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020!J\u000e\u0010)\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"},
   d2 = {"Lokhttp3/internal/ws/WebSocketWriter;", "Ljava/io/Closeable;", "isClient", "", "sink", "Lokio/BufferedSink;", "random", "Ljava/util/Random;", "perMessageDeflate", "noContextTakeover", "minimumDeflateSize", "", "(ZLokio/BufferedSink;Ljava/util/Random;ZZJ)V", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageBuffer", "Lokio/Buffer;", "messageDeflater", "Lokhttp3/internal/ws/MessageDeflater;", "getRandom", "()Ljava/util/Random;", "getSink", "()Lokio/BufferedSink;", "sinkBuffer", "writerClosed", "close", "", "writeClose", "code", "", "reason", "Lokio/ByteString;", "writeControlFrame", "opcode", "payload", "writeMessageFrame", "formatOpcode", "data", "writePing", "writePong", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class WebSocketWriter implements Closeable {
   private final boolean isClient;
   private final Buffer.UnsafeCursor maskCursor;
   private final byte[] maskKey;
   private final Buffer messageBuffer;
   private MessageDeflater messageDeflater;
   private final long minimumDeflateSize;
   private final boolean noContextTakeover;
   private final boolean perMessageDeflate;
   private final Random random;
   private final BufferedSink sink;
   private final Buffer sinkBuffer;
   private boolean writerClosed;

   public WebSocketWriter(boolean var1, BufferedSink var2, Random var3, boolean var4, boolean var5, long var6) {
      Intrinsics.checkParameterIsNotNull(var2, "sink");
      Intrinsics.checkParameterIsNotNull(var3, "random");
      super();
      this.isClient = var1;
      this.sink = var2;
      this.random = var3;
      this.perMessageDeflate = var4;
      this.noContextTakeover = var5;
      this.minimumDeflateSize = var6;
      this.messageBuffer = new Buffer();
      this.sinkBuffer = this.sink.getBuffer();
      var1 = this.isClient;
      var3 = null;
      byte[] var8;
      if (var1) {
         var8 = new byte[4];
      } else {
         var8 = null;
      }

      this.maskKey = var8;
      Buffer.UnsafeCursor var9 = var3;
      if (this.isClient) {
         var9 = new Buffer.UnsafeCursor();
      }

      this.maskCursor = var9;
   }

   private final void writeControlFrame(int var1, ByteString var2) throws IOException {
      if (!this.writerClosed) {
         int var3 = var2.size();
         boolean var4;
         if ((long)var3 <= 125L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            this.sinkBuffer.writeByte(var1 | 128);
            if (this.isClient) {
               this.sinkBuffer.writeByte(var3 | 128);
               Random var5 = this.random;
               byte[] var6 = this.maskKey;
               if (var6 == null) {
                  Intrinsics.throwNpe();
               }

               var5.nextBytes(var6);
               this.sinkBuffer.write(this.maskKey);
               if (var3 > 0) {
                  long var7 = this.sinkBuffer.size();
                  this.sinkBuffer.write(var2);
                  Buffer var9 = this.sinkBuffer;
                  Buffer.UnsafeCursor var10 = this.maskCursor;
                  if (var10 == null) {
                     Intrinsics.throwNpe();
                  }

                  var9.readAndWriteUnsafe(var10);
                  this.maskCursor.seek(var7);
                  WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
                  this.maskCursor.close();
               }
            } else {
               this.sinkBuffer.writeByte(var3);
               this.sinkBuffer.write(var2);
            }

            this.sink.flush();
         } else {
            throw (Throwable)(new IllegalArgumentException("Payload size must be less than or equal to 125".toString()));
         }
      } else {
         throw (Throwable)(new IOException("closed"));
      }
   }

   public void close() {
      MessageDeflater var1 = this.messageDeflater;
      if (var1 != null) {
         var1.close();
      }

   }

   public final Random getRandom() {
      return this.random;
   }

   public final BufferedSink getSink() {
      return this.sink;
   }

   public final void writeClose(int var1, ByteString var2) throws IOException {
      ByteString var3 = ByteString.EMPTY;
      if (var1 != 0 || var2 != null) {
         if (var1 != 0) {
            WebSocketProtocol.INSTANCE.validateCloseCode(var1);
         }

         Buffer var6 = new Buffer();
         var6.writeShort(var1);
         if (var2 != null) {
            var6.write(var2);
         }

         var3 = var6.readByteString();
      }

      try {
         this.writeControlFrame(8, var3);
      } finally {
         this.writerClosed = true;
      }

   }

   public final void writeMessageFrame(int var1, ByteString var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var2, "data");
      if (!this.writerClosed) {
         this.messageBuffer.write(var2);
         short var3 = 128;
         int var4 = var1 | 128;
         var1 = var4;
         if (this.perMessageDeflate) {
            var1 = var4;
            if ((long)var2.size() >= this.minimumDeflateSize) {
               MessageDeflater var8 = this.messageDeflater;
               if (var8 == null) {
                  var8 = new MessageDeflater(this.noContextTakeover);
                  this.messageDeflater = var8;
               }

               var8.deflate(this.messageBuffer);
               var1 = var4 | 64;
            }
         }

         long var5 = this.messageBuffer.size();
         this.sinkBuffer.writeByte(var1);
         short var9;
         if (this.isClient) {
            var9 = var3;
         } else {
            var9 = 0;
         }

         if (var5 <= 125L) {
            int var10 = (int)var5;
            this.sinkBuffer.writeByte(var10 | var9);
         } else if (var5 <= 65535L) {
            this.sinkBuffer.writeByte(var9 | 126);
            this.sinkBuffer.writeShort((int)var5);
         } else {
            this.sinkBuffer.writeByte(var9 | 127);
            this.sinkBuffer.writeLong(var5);
         }

         if (this.isClient) {
            Random var11 = this.random;
            byte[] var7 = this.maskKey;
            if (var7 == null) {
               Intrinsics.throwNpe();
            }

            var11.nextBytes(var7);
            this.sinkBuffer.write(this.maskKey);
            if (var5 > 0L) {
               Buffer var12 = this.messageBuffer;
               Buffer.UnsafeCursor var13 = this.maskCursor;
               if (var13 == null) {
                  Intrinsics.throwNpe();
               }

               var12.readAndWriteUnsafe(var13);
               this.maskCursor.seek(0L);
               WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
               this.maskCursor.close();
            }
         }

         this.sinkBuffer.write(this.messageBuffer, var5);
         this.sink.emit();
      } else {
         throw (Throwable)(new IOException("closed"));
      }
   }

   public final void writePing(ByteString var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "payload");
      this.writeControlFrame(9, var1);
   }

   public final void writePong(ByteString var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "payload");
      this.writeControlFrame(10, var1);
   }
}
