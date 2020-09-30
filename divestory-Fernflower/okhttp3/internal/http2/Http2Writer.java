package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u0000 :2\u00020\u0001:\u0001:B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u0006\u0010\u0015\u001a\u00020\u0011J(\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0019\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u000fJ(\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\b\u0010\u001d\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u000fJ\u0006\u0010\u001e\u001a\u00020\u0011J&\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fJ\u001e\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'J$\u0010(\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u000f2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*J\u0006\u0010,\u001a\u00020\u000fJ\u001e\u0010-\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u000fJ$\u00101\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u000f2\f\u00103\u001a\b\u0012\u0004\u0012\u00020+0*J\u0016\u00104\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020%J\u000e\u00105\u001a\u00020\u00112\u0006\u00105\u001a\u00020\u0013J\u0016\u00106\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u00107\u001a\u000208J\u0018\u00109\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u000208H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"},
   d2 = {"Lokhttp3/internal/http2/Http2Writer;", "Ljava/io/Closeable;", "sink", "Lokio/BufferedSink;", "client", "", "(Lokio/BufferedSink;Z)V", "closed", "hpackBuffer", "Lokio/Buffer;", "hpackWriter", "Lokhttp3/internal/http2/Hpack$Writer;", "getHpackWriter", "()Lokhttp3/internal/http2/Hpack$Writer;", "maxFrameSize", "", "applyAndAckSettings", "", "peerSettings", "Lokhttp3/internal/http2/Settings;", "close", "connectionPreface", "data", "outFinished", "streamId", "source", "byteCount", "dataFrame", "flags", "buffer", "flush", "frameHeader", "length", "type", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "", "headers", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "maxDataLength", "ping", "ack", "payload1", "payload2", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "settings", "windowUpdate", "windowSizeIncrement", "", "writeContinuationFrames", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http2Writer implements Closeable {
   public static final Http2Writer.Companion Companion = new Http2Writer.Companion((DefaultConstructorMarker)null);
   private static final Logger logger = Logger.getLogger(Http2.class.getName());
   private final boolean client;
   private boolean closed;
   private final Buffer hpackBuffer;
   private final Hpack.Writer hpackWriter;
   private int maxFrameSize;
   private final BufferedSink sink;

   public Http2Writer(BufferedSink var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      super();
      this.sink = var1;
      this.client = var2;
      this.hpackBuffer = new Buffer();
      this.maxFrameSize = 16384;
      this.hpackWriter = new Hpack.Writer(0, false, this.hpackBuffer, 3, (DefaultConstructorMarker)null);
   }

   private final void writeContinuationFrames(int var1, long var2) throws IOException {
      while(var2 > 0L) {
         long var4 = Math.min((long)this.maxFrameSize, var2);
         var2 -= var4;
         int var6 = (int)var4;
         byte var7;
         if (var2 == 0L) {
            var7 = 4;
         } else {
            var7 = 0;
         }

         this.frameHeader(var1, var6, 9, var7);
         this.sink.write(this.hpackBuffer, var4);
      }

   }

   public final void applyAndAckSettings(Settings var1) throws IOException {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "peerSettings");
         if (this.closed) {
            IOException var4 = new IOException("closed");
            throw (Throwable)var4;
         }

         this.maxFrameSize = var1.getMaxFrameSize(this.maxFrameSize);
         if (var1.getHeaderTableSize() != -1) {
            this.hpackWriter.resizeHeaderTable(var1.getHeaderTableSize());
         }

         this.frameHeader(0, 0, 4, 1);
         this.sink.flush();
      } finally {
         ;
      }

   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         this.closed = true;
         this.sink.close();
      } finally {
         ;
      }

   }

   public final void connectionPreface() throws IOException {
      synchronized(this){}

      Throwable var10000;
      label224: {
         boolean var1;
         boolean var10001;
         label219: {
            try {
               if (!this.closed) {
                  var1 = this.client;
                  break label219;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label224;
            }

            try {
               IOException var2 = new IOException("closed");
               throw (Throwable)var2;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label224;
            }
         }

         if (!var1) {
            return;
         }

         try {
            if (logger.isLoggable(Level.FINE)) {
               Logger var24 = logger;
               StringBuilder var3 = new StringBuilder();
               var3.append(">> CONNECTION ");
               var3.append(Http2.CONNECTION_PREFACE.hex());
               var24.fine(Util.format(var3.toString()));
            }
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label224;
         }

         try {
            this.sink.write(Http2.CONNECTION_PREFACE);
            this.sink.flush();
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label224;
         }

         return;
      }

      Throwable var25 = var10000;
      throw var25;
   }

   public final void data(boolean var1, int var2, Buffer var3, int var4) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label141: {
         boolean var10001;
         label135: {
            try {
               if (!this.closed) {
                  break label135;
               }
            } catch (Throwable var17) {
               var10000 = var17;
               var10001 = false;
               break label141;
            }

            try {
               IOException var18 = new IOException("closed");
               throw (Throwable)var18;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label141;
            }
         }

         byte var5 = 0;
         if (var1) {
            var5 = 1;
         }

         try {
            this.dataFrame(var2, var5, var3, var4);
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label141;
         }

         return;
      }

      Throwable var19 = var10000;
      throw var19;
   }

   public final void dataFrame(int var1, int var2, Buffer var3, int var4) throws IOException {
      this.frameHeader(var1, var4, 0, var2);
      if (var4 > 0) {
         BufferedSink var5 = this.sink;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         var5.write(var3, (long)var4);
      }

   }

   public final void flush() throws IOException {
      synchronized(this){}

      try {
         if (this.closed) {
            IOException var1 = new IOException("closed");
            throw (Throwable)var1;
         }

         this.sink.flush();
      } finally {
         ;
      }

   }

   public final void frameHeader(int var1, int var2, int var3, int var4) throws IOException {
      if (logger.isLoggable(Level.FINE)) {
         logger.fine(Http2.INSTANCE.frameLog(false, var1, var2, var3, var4));
      }

      int var5 = this.maxFrameSize;
      boolean var6 = true;
      boolean var8;
      if (var2 <= var5) {
         var8 = true;
      } else {
         var8 = false;
      }

      StringBuilder var7;
      if (var8) {
         if (((int)2147483648L & var1) == 0) {
            var8 = var6;
         } else {
            var8 = false;
         }

         if (var8) {
            Util.writeMedium(this.sink, var2);
            this.sink.writeByte(var3 & 255);
            this.sink.writeByte(var4 & 255);
            this.sink.writeInt(var1 & Integer.MAX_VALUE);
         } else {
            var7 = new StringBuilder();
            var7.append("reserved bit set: ");
            var7.append(var1);
            throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
         }
      } else {
         var7 = new StringBuilder();
         var7.append("FRAME_SIZE_ERROR length > ");
         var7.append(this.maxFrameSize);
         var7.append(": ");
         var7.append(var2);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      }
   }

   public final Hpack.Writer getHpackWriter() {
      return this.hpackWriter;
   }

   public final void goAway(int var1, ErrorCode var2, byte[] var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label534: {
         int var4;
         boolean var10001;
         label529: {
            try {
               Intrinsics.checkParameterIsNotNull(var2, "errorCode");
               Intrinsics.checkParameterIsNotNull(var3, "debugData");
               if (!this.closed) {
                  var4 = var2.getHttpCode();
                  break label529;
               }
            } catch (Throwable var61) {
               var10000 = var61;
               var10001 = false;
               break label534;
            }

            try {
               IOException var63 = new IOException("closed");
               throw (Throwable)var63;
            } catch (Throwable var60) {
               var10000 = var60;
               var10001 = false;
               break label534;
            }
         }

         boolean var5 = false;
         boolean var66;
         if (var4 != -1) {
            var66 = true;
         } else {
            var66 = false;
         }

         if (var66) {
            label535: {
               try {
                  this.frameHeader(0, var3.length + 8, 7, 0);
                  this.sink.writeInt(var1);
                  this.sink.writeInt(var2.getHttpCode());
               } catch (Throwable var58) {
                  var10000 = var58;
                  var10001 = false;
                  break label535;
               }

               boolean var62 = var5;

               label511: {
                  try {
                     if (var3.length != 0) {
                        break label511;
                     }
                  } catch (Throwable var57) {
                     var10000 = var57;
                     var10001 = false;
                     break label535;
                  }

                  var62 = true;
               }

               if (var62 ^ true) {
                  try {
                     this.sink.write(var3);
                  } catch (Throwable var56) {
                     var10000 = var56;
                     var10001 = false;
                     break label535;
                  }
               }

               try {
                  this.sink.flush();
               } catch (Throwable var55) {
                  var10000 = var55;
                  var10001 = false;
                  break label535;
               }

               return;
            }
         } else {
            label518:
            try {
               IllegalArgumentException var65 = new IllegalArgumentException("errorCode.httpCode == -1".toString());
               throw (Throwable)var65;
            } catch (Throwable var59) {
               var10000 = var59;
               var10001 = false;
               break label518;
            }
         }
      }

      Throwable var64 = var10000;
      throw var64;
   }

   public final void headers(boolean var1, int var2, List<Header> var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label251: {
         long var4;
         long var6;
         boolean var10001;
         label245: {
            try {
               Intrinsics.checkParameterIsNotNull(var3, "headerBlock");
               if (!this.closed) {
                  this.hpackWriter.writeHeaders(var3);
                  var4 = this.hpackBuffer.size();
                  var6 = Math.min((long)this.maxFrameSize, var4);
                  break label245;
               }
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label251;
            }

            try {
               IOException var31 = new IOException("closed");
               throw (Throwable)var31;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               break label251;
            }
         }

         long var33;
         int var8 = (var33 = var4 - var6) == 0L ? 0 : (var33 < 0L ? -1 : 1);
         byte var9;
         if (var8 == 0) {
            var9 = 4;
         } else {
            var9 = 0;
         }

         int var10 = var9;
         if (var1) {
            var10 = var9 | 1;
         }

         try {
            this.frameHeader(var2, (int)var6, 1, var10);
            this.sink.write(this.hpackBuffer, var6);
         } catch (Throwable var28) {
            var10000 = var28;
            var10001 = false;
            break label251;
         }

         if (var8 > 0) {
            try {
               this.writeContinuationFrames(var2, var4 - var6);
            } catch (Throwable var27) {
               var10000 = var27;
               var10001 = false;
               break label251;
            }
         }

         return;
      }

      Throwable var32 = var10000;
      throw var32;
   }

   public final int maxDataLength() {
      return this.maxFrameSize;
   }

   public final void ping(boolean var1, int var2, int var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label136: {
         boolean var10001;
         label131: {
            try {
               if (!this.closed) {
                  break label131;
               }
            } catch (Throwable var17) {
               var10000 = var17;
               var10001 = false;
               break label136;
            }

            try {
               IOException var5 = new IOException("closed");
               throw (Throwable)var5;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label136;
            }
         }

         byte var4;
         if (var1) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         try {
            this.frameHeader(0, 8, 6, var4);
            this.sink.writeInt(var2);
            this.sink.writeInt(var3);
            this.sink.flush();
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label136;
         }

         return;
      }

      Throwable var18 = var10000;
      throw var18;
   }

   public final void pushPromise(int var1, int var2, List<Header> var3) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label215: {
         boolean var10001;
         label219: {
            long var4;
            int var6;
            try {
               Intrinsics.checkParameterIsNotNull(var3, "requestHeaders");
               if (this.closed) {
                  break label219;
               }

               this.hpackWriter.writeHeaders(var3);
               var4 = this.hpackBuffer.size();
               var6 = (int)Math.min((long)this.maxFrameSize - 4L, var4);
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label215;
            }

            long var7 = (long)var6;
            long var33;
            int var9 = (var33 = var4 - var7) == 0L ? 0 : (var33 < 0L ? -1 : 1);
            byte var10;
            if (var9 == 0) {
               var10 = 4;
            } else {
               var10 = 0;
            }

            try {
               this.frameHeader(var1, var6 + 4, 5, var10);
               this.sink.writeInt(var2 & Integer.MAX_VALUE);
               this.sink.write(this.hpackBuffer, var7);
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label215;
            }

            if (var9 > 0) {
               try {
                  this.writeContinuationFrames(var1, var4 - var7);
               } catch (Throwable var27) {
                  var10000 = var27;
                  var10001 = false;
                  break label215;
               }
            }

            return;
         }

         label208:
         try {
            IOException var32 = new IOException("closed");
            throw (Throwable)var32;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label208;
         }
      }

      Throwable var31 = var10000;
      throw var31;
   }

   public final void rstStream(int var1, ErrorCode var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label212: {
         boolean var3;
         boolean var10001;
         label211: {
            label210: {
               label209: {
                  try {
                     Intrinsics.checkParameterIsNotNull(var2, "errorCode");
                     if (this.closed) {
                        break label210;
                     }

                     if (var2.getHttpCode() != -1) {
                        break label209;
                     }
                  } catch (Throwable var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label212;
                  }

                  var3 = false;
                  break label211;
               }

               var3 = true;
               break label211;
            }

            try {
               IOException var26 = new IOException("closed");
               throw (Throwable)var26;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label212;
            }
         }

         if (var3) {
            label195: {
               try {
                  this.frameHeader(var1, 4, 3, 0);
                  this.sink.writeInt(var2.getHttpCode());
                  this.sink.flush();
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label195;
               }

               return;
            }
         } else {
            label197:
            try {
               IllegalArgumentException var24 = new IllegalArgumentException("Failed requirement.".toString());
               throw (Throwable)var24;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label197;
            }
         }
      }

      Throwable var25 = var10000;
      throw var25;
   }

   public final void settings(Settings var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label447: {
         int var2;
         boolean var10001;
         label442: {
            try {
               Intrinsics.checkParameterIsNotNull(var1, "settings");
               if (!this.closed) {
                  var2 = var1.size();
                  break label442;
               }
            } catch (Throwable var45) {
               var10000 = var45;
               var10001 = false;
               break label447;
            }

            try {
               IOException var46 = new IOException("closed");
               throw (Throwable)var46;
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label447;
            }
         }

         int var3 = 0;

         try {
            this.frameHeader(0, var2 * 6, 4, 0);
         } catch (Throwable var42) {
            var10000 = var42;
            var10001 = false;
            break label447;
         }

         while(true) {
            if (var3 >= 10) {
               try {
                  this.sink.flush();
               } catch (Throwable var40) {
                  var10000 = var40;
                  var10001 = false;
                  break;
               }

               return;
            }

            label449: {
               try {
                  if (!var1.isSet(var3)) {
                     break label449;
                  }
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break;
               }

               if (var3 != 4) {
                  if (var3 != 7) {
                     var2 = var3;
                  } else {
                     var2 = 4;
                  }
               } else {
                  var2 = 3;
               }

               try {
                  this.sink.writeShort(var2);
                  this.sink.writeInt(var1.get(var3));
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break;
               }
            }

            ++var3;
         }
      }

      Throwable var47 = var10000;
      throw var47;
   }

   public final void windowUpdate(int var1, long var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label221: {
         boolean var10001;
         label215: {
            try {
               if (!this.closed) {
                  break label215;
               }
            } catch (Throwable var26) {
               var10000 = var26;
               var10001 = false;
               break label221;
            }

            try {
               IOException var5 = new IOException("closed");
               throw (Throwable)var5;
            } catch (Throwable var25) {
               var10000 = var25;
               var10001 = false;
               break label221;
            }
         }

         boolean var4;
         if (var2 != 0L && var2 <= 2147483647L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            label204: {
               try {
                  this.frameHeader(var1, 4, 8, 0);
                  this.sink.writeInt((int)var2);
                  this.sink.flush();
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label204;
               }

               return;
            }
         } else {
            label206:
            try {
               StringBuilder var28 = new StringBuilder();
               var28.append("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: ");
               var28.append(var2);
               String var6 = var28.toString();
               IllegalArgumentException var29 = new IllegalArgumentException(var6.toString());
               throw (Throwable)var29;
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label206;
            }
         }
      }

      Throwable var27 = var10000;
      throw var27;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"},
      d2 = {"Lokhttp3/internal/http2/Http2Writer$Companion;", "", "()V", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
