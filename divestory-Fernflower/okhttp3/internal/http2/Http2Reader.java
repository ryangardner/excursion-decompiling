package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 #2\u00020\u0001:\u0003#$%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010J(\u0010\u0012\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u0017\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J.\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001c\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001d\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J\u0018\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001f\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010 \u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010!\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\"\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"},
   d2 = {"Lokhttp3/internal/http2/Http2Reader;", "Ljava/io/Closeable;", "source", "Lokio/BufferedSource;", "client", "", "(Lokio/BufferedSource;Z)V", "continuation", "Lokhttp3/internal/http2/Http2Reader$ContinuationSource;", "hpackReader", "Lokhttp3/internal/http2/Hpack$Reader;", "close", "", "nextFrame", "requireSettings", "handler", "Lokhttp3/internal/http2/Http2Reader$Handler;", "readConnectionPreface", "readData", "length", "", "flags", "streamId", "readGoAway", "readHeaderBlock", "", "Lokhttp3/internal/http2/Header;", "padding", "readHeaders", "readPing", "readPriority", "readPushPromise", "readRstStream", "readSettings", "readWindowUpdate", "Companion", "ContinuationSource", "Handler", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http2Reader implements Closeable {
   public static final Http2Reader.Companion Companion = new Http2Reader.Companion((DefaultConstructorMarker)null);
   private static final Logger logger;
   private final boolean client;
   private final Http2Reader.ContinuationSource continuation;
   private final Hpack.Reader hpackReader;
   private final BufferedSource source;

   static {
      Logger var0 = Logger.getLogger(Http2.class.getName());
      Intrinsics.checkExpressionValueIsNotNull(var0, "Logger.getLogger(Http2::class.java.name)");
      logger = var0;
   }

   public Http2Reader(BufferedSource var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      super();
      this.source = var1;
      this.client = var2;
      this.continuation = new Http2Reader.ContinuationSource(this.source);
      this.hpackReader = new Hpack.Reader((Source)this.continuation, 4096, 0, 4, (DefaultConstructorMarker)null);
   }

   private final void readData(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var4 != 0) {
         byte var5 = 0;
         boolean var6 = true;
         boolean var7;
         if ((var3 & 1) != 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if ((var3 & 32) == 0) {
            var6 = false;
         }

         if (!var6) {
            int var8 = var5;
            if ((var3 & 8) != 0) {
               var8 = Util.and((byte)this.source.readByte(), 255);
            }

            var2 = Companion.lengthWithoutPadding(var2, var3, var8);
            var1.data(var7, var4, this.source, var2);
            this.source.skip((long)var8);
         } else {
            throw (Throwable)(new IOException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA"));
         }
      } else {
         throw (Throwable)(new IOException("PROTOCOL_ERROR: TYPE_DATA streamId == 0"));
      }
   }

   private final void readGoAway(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      StringBuilder var7;
      if (var2 >= 8) {
         if (var4 == 0) {
            var3 = this.source.readInt();
            var4 = this.source.readInt();
            var2 -= 8;
            ErrorCode var5 = ErrorCode.Companion.fromHttp2(var4);
            if (var5 != null) {
               ByteString var6 = ByteString.EMPTY;
               if (var2 > 0) {
                  var6 = this.source.readByteString((long)var2);
               }

               var1.goAway(var3, var5, var6);
            } else {
               var7 = new StringBuilder();
               var7.append("TYPE_GOAWAY unexpected error code: ");
               var7.append(var4);
               throw (Throwable)(new IOException(var7.toString()));
            }
         } else {
            throw (Throwable)(new IOException("TYPE_GOAWAY streamId != 0"));
         }
      } else {
         var7 = new StringBuilder();
         var7.append("TYPE_GOAWAY length < 8: ");
         var7.append(var2);
         throw (Throwable)(new IOException(var7.toString()));
      }
   }

   private final List<Header> readHeaderBlock(int var1, int var2, int var3, int var4) throws IOException {
      this.continuation.setLeft(var1);
      Http2Reader.ContinuationSource var5 = this.continuation;
      var5.setLength(var5.getLeft());
      this.continuation.setPadding(var2);
      this.continuation.setFlags(var3);
      this.continuation.setStreamId(var4);
      this.hpackReader.readHeaders();
      return this.hpackReader.getAndResetHeaderList();
   }

   private final void readHeaders(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var4 != 0) {
         int var5 = 0;
         boolean var6;
         if ((var3 & 1) != 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if ((var3 & 8) != 0) {
            var5 = Util.and((byte)this.source.readByte(), 255);
         }

         int var7 = var2;
         if ((var3 & 32) != 0) {
            this.readPriority(var1, var4);
            var7 = var2 - 5;
         }

         var1.headers(var6, var4, -1, this.readHeaderBlock(Companion.lengthWithoutPadding(var7, var3, var5), var5, var3, var4));
      } else {
         throw (Throwable)(new IOException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0"));
      }
   }

   private final void readPing(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var2 == 8) {
         if (var4 == 0) {
            var4 = this.source.readInt();
            var2 = this.source.readInt();
            boolean var5 = true;
            if ((var3 & 1) == 0) {
               var5 = false;
            }

            var1.ping(var5, var4, var2);
         } else {
            throw (Throwable)(new IOException("TYPE_PING streamId != 0"));
         }
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("TYPE_PING length != 8: ");
         var6.append(var2);
         throw (Throwable)(new IOException(var6.toString()));
      }
   }

   private final void readPriority(Http2Reader.Handler var1, int var2) throws IOException {
      int var3 = this.source.readInt();
      boolean var4;
      if ((var3 & (int)2147483648L) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.priority(var2, var3 & Integer.MAX_VALUE, Util.and((byte)this.source.readByte(), 255) + 1, var4);
   }

   private final void readPriority(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var2 == 5) {
         if (var4 != 0) {
            this.readPriority(var1, var4);
         } else {
            throw (Throwable)(new IOException("TYPE_PRIORITY streamId == 0"));
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("TYPE_PRIORITY length: ");
         var5.append(var2);
         var5.append(" != 5");
         throw (Throwable)(new IOException(var5.toString()));
      }
   }

   private final void readPushPromise(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var4 != 0) {
         int var5;
         if ((var3 & 8) != 0) {
            var5 = Util.and((byte)this.source.readByte(), 255);
         } else {
            var5 = 0;
         }

         var1.pushPromise(var4, this.source.readInt() & Integer.MAX_VALUE, this.readHeaderBlock(Companion.lengthWithoutPadding(var2 - 4, var3, var5), var5, var3, var4));
      } else {
         throw (Throwable)(new IOException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0"));
      }
   }

   private final void readRstStream(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      StringBuilder var6;
      if (var2 == 4) {
         if (var4 != 0) {
            var2 = this.source.readInt();
            ErrorCode var5 = ErrorCode.Companion.fromHttp2(var2);
            if (var5 != null) {
               var1.rstStream(var4, var5);
            } else {
               var6 = new StringBuilder();
               var6.append("TYPE_RST_STREAM unexpected error code: ");
               var6.append(var2);
               throw (Throwable)(new IOException(var6.toString()));
            }
         } else {
            throw (Throwable)(new IOException("TYPE_RST_STREAM streamId == 0"));
         }
      } else {
         var6 = new StringBuilder();
         var6.append("TYPE_RST_STREAM length: ");
         var6.append(var2);
         var6.append(" != 4");
         throw (Throwable)(new IOException(var6.toString()));
      }
   }

   private final void readSettings(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var4 != 0) {
         throw (Throwable)(new IOException("TYPE_SETTINGS streamId != 0"));
      } else if ((var3 & 1) != 0) {
         if (var2 == 0) {
            var1.ackSettings();
         } else {
            throw (Throwable)(new IOException("FRAME_SIZE_ERROR ack frame should be empty!"));
         }
      } else {
         StringBuilder var10;
         if (var2 % 6 != 0) {
            var10 = new StringBuilder();
            var10.append("TYPE_SETTINGS length % 6 != 0: ");
            var10.append(var2);
            throw (Throwable)(new IOException(var10.toString()));
         } else {
            Settings var5;
            label63: {
               var5 = new Settings();
               IntProgression var6 = RangesKt.step((IntProgression)RangesKt.until(0, var2), 6);
               var3 = var6.getFirst();
               int var7 = var6.getLast();
               int var8 = var6.getStep();
               if (var8 >= 0) {
                  if (var3 > var7) {
                     break label63;
                  }
               } else if (var3 < var7) {
                  break label63;
               }

               while(true) {
                  var4 = Util.and((short)this.source.readShort(), 65535);
                  int var9 = this.source.readInt();
                  if (var4 != 2) {
                     if (var4 != 3) {
                        if (var4 != 4) {
                           if (var4 != 5) {
                              var2 = var4;
                           } else {
                              if (var9 < 16384 || var9 > 16777215) {
                                 var10 = new StringBuilder();
                                 var10.append("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: ");
                                 var10.append(var9);
                                 throw (Throwable)(new IOException(var10.toString()));
                              }

                              var2 = var4;
                           }
                        } else {
                           var2 = 7;
                           if (var9 < 0) {
                              throw (Throwable)(new IOException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1"));
                           }
                        }
                     } else {
                        var2 = 4;
                     }
                  } else {
                     var2 = var4;
                     if (var9 != 0) {
                        if (var9 != 1) {
                           throw (Throwable)(new IOException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1"));
                        }

                        var2 = var4;
                     }
                  }

                  var5.set(var2, var9);
                  if (var3 == var7) {
                     break;
                  }

                  var3 += var8;
               }
            }

            var1.settings(false, var5);
         }
      }
   }

   private final void readWindowUpdate(Http2Reader.Handler var1, int var2, int var3, int var4) throws IOException {
      if (var2 == 4) {
         long var5 = Util.and(this.source.readInt(), 2147483647L);
         if (var5 != 0L) {
            var1.windowUpdate(var4, var5);
         } else {
            throw (Throwable)(new IOException("windowSizeIncrement was 0"));
         }
      } else {
         StringBuilder var7 = new StringBuilder();
         var7.append("TYPE_WINDOW_UPDATE length !=4: ");
         var7.append(var2);
         throw (Throwable)(new IOException(var7.toString()));
      }
   }

   public void close() throws IOException {
      this.source.close();
   }

   public final boolean nextFrame(boolean var1, Http2Reader.Handler var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var2, "handler");

      try {
         this.source.require(9L);
      } catch (EOFException var7) {
         return false;
      }

      int var3 = Util.readMedium(this.source);
      StringBuilder var8;
      if (var3 <= 16384) {
         int var4 = Util.and((byte)this.source.readByte(), 255);
         int var5 = Util.and((byte)this.source.readByte(), 255);
         int var6 = this.source.readInt() & Integer.MAX_VALUE;
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(Http2.INSTANCE.frameLog(true, var6, var3, var4, var5));
         }

         if (var1 && var4 != 4) {
            var8 = new StringBuilder();
            var8.append("Expected a SETTINGS frame but was ");
            var8.append(Http2.INSTANCE.formattedType$okhttp(var4));
            throw (Throwable)(new IOException(var8.toString()));
         } else {
            switch(var4) {
            case 0:
               this.readData(var2, var3, var5, var6);
               break;
            case 1:
               this.readHeaders(var2, var3, var5, var6);
               break;
            case 2:
               this.readPriority(var2, var3, var5, var6);
               break;
            case 3:
               this.readRstStream(var2, var3, var5, var6);
               break;
            case 4:
               this.readSettings(var2, var3, var5, var6);
               break;
            case 5:
               this.readPushPromise(var2, var3, var5, var6);
               break;
            case 6:
               this.readPing(var2, var3, var5, var6);
               break;
            case 7:
               this.readGoAway(var2, var3, var5, var6);
               break;
            case 8:
               this.readWindowUpdate(var2, var3, var5, var6);
               break;
            default:
               this.source.skip((long)var3);
            }

            return true;
         }
      } else {
         var8 = new StringBuilder();
         var8.append("FRAME_SIZE_ERROR: ");
         var8.append(var3);
         throw (Throwable)(new IOException(var8.toString()));
      }
   }

   public final void readConnectionPreface(Http2Reader.Handler var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "handler");
      if (this.client) {
         if (!this.nextFrame(true, var1)) {
            throw (Throwable)(new IOException("Required SETTINGS preface not received"));
         }
      } else {
         ByteString var4 = this.source.readByteString((long)Http2.CONNECTION_PREFACE.size());
         if (logger.isLoggable(Level.FINE)) {
            Logger var2 = logger;
            StringBuilder var3 = new StringBuilder();
            var3.append("<< CONNECTION ");
            var3.append(var4.hex());
            var2.fine(Util.format(var3.toString()));
         }

         if (Intrinsics.areEqual((Object)Http2.CONNECTION_PREFACE, (Object)var4) ^ true) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Expected a connection header but was ");
            var5.append(var4.utf8());
            throw (Throwable)(new IOException(var5.toString()));
         }
      }

   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"},
      d2 = {"Lokhttp3/internal/http2/Http2Reader$Companion;", "", "()V", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "lengthWithoutPadding", "", "length", "flags", "padding", "okhttp"},
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

      public final Logger getLogger() {
         return Http2Reader.logger;
      }

      public final int lengthWithoutPadding(int var1, int var2, int var3) throws IOException {
         int var4 = var1;
         if ((var2 & 8) != 0) {
            var4 = var1 - 1;
         }

         if (var3 <= var4) {
            return var4 - var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("PROTOCOL_ERROR padding ");
            var5.append(var3);
            var5.append(" > remaining length ");
            var5.append(var4);
            throw (Throwable)(new IOException(var5.toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001aH\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\n¨\u0006!"},
      d2 = {"Lokhttp3/internal/http2/Http2Reader$ContinuationSource;", "Lokio/Source;", "source", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "flags", "", "getFlags", "()I", "setFlags", "(I)V", "left", "getLeft", "setLeft", "length", "getLength", "setLength", "padding", "getPadding", "setPadding", "streamId", "getStreamId", "setStreamId", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "readContinuationHeader", "timeout", "Lokio/Timeout;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class ContinuationSource implements Source {
      private int flags;
      private int left;
      private int length;
      private int padding;
      private final BufferedSource source;
      private int streamId;

      public ContinuationSource(BufferedSource var1) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         super();
         this.source = var1;
      }

      private final void readContinuationHeader() throws IOException {
         int var1 = this.streamId;
         int var2 = Util.readMedium(this.source);
         this.left = var2;
         this.length = var2;
         var2 = Util.and((byte)this.source.readByte(), 255);
         this.flags = Util.and((byte)this.source.readByte(), 255);
         if (Http2Reader.Companion.getLogger().isLoggable(Level.FINE)) {
            Http2Reader.Companion.getLogger().fine(Http2.INSTANCE.frameLog(true, this.streamId, this.length, var2, this.flags));
         }

         int var3 = this.source.readInt() & Integer.MAX_VALUE;
         this.streamId = var3;
         if (var2 == 9) {
            if (var3 != var1) {
               throw (Throwable)(new IOException("TYPE_CONTINUATION streamId changed"));
            }
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" != TYPE_CONTINUATION");
            throw (Throwable)(new IOException(var4.toString()));
         }
      }

      public void close() throws IOException {
      }

      public final int getFlags() {
         return this.flags;
      }

      public final int getLeft() {
         return this.left;
      }

      public final int getLength() {
         return this.length;
      }

      public final int getPadding() {
         return this.padding;
      }

      public final int getStreamId() {
         return this.streamId;
      }

      public long read(Buffer var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "sink");

         while(true) {
            int var4 = this.left;
            if (var4 != 0) {
               var2 = this.source.read(var1, Math.min(var2, (long)var4));
               if (var2 == -1L) {
                  return -1L;
               }

               this.left -= (int)var2;
               return var2;
            }

            this.source.skip((long)this.padding);
            this.padding = 0;
            if ((this.flags & 4) != 0) {
               return -1L;
            }

            this.readContinuationHeader();
         }
      }

      public final void setFlags(int var1) {
         this.flags = var1;
      }

      public final void setLeft(int var1) {
         this.left = var1;
      }

      public final void setLength(int var1) {
         this.length = var1;
      }

      public final void setPadding(int var1) {
         this.padding = var1;
      }

      public final void setStreamId(int var1) {
         this.streamId = var1;
      }

      public Timeout timeout() {
         return this.source.timeout();
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J8\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH&J(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0006H&J \u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH&J.\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH&J \u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H&J(\u0010#\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u0011H&J&\u0010'\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH&J\u0018\u0010*\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0018\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u00112\u0006\u0010+\u001a\u00020-H&J\u0018\u0010.\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u000eH&¨\u00060"},
      d2 = {"Lokhttp3/internal/http2/Http2Reader$Handler;", "", "ackSettings", "", "alternateService", "streamId", "", "origin", "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "data", "inFinished", "", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "settings", "clearPrevious", "Lokhttp3/internal/http2/Settings;", "windowUpdate", "windowSizeIncrement", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public interface Handler {
      void ackSettings();

      void alternateService(int var1, String var2, ByteString var3, String var4, int var5, long var6);

      void data(boolean var1, int var2, BufferedSource var3, int var4) throws IOException;

      void goAway(int var1, ErrorCode var2, ByteString var3);

      void headers(boolean var1, int var2, int var3, List<Header> var4);

      void ping(boolean var1, int var2, int var3);

      void priority(int var1, int var2, int var3, boolean var4);

      void pushPromise(int var1, int var2, List<Header> var3) throws IOException;

      void rstStream(int var1, ErrorCode var2);

      void settings(boolean var1, Settings var2);

      void windowUpdate(int var1, long var2);
   }
}
