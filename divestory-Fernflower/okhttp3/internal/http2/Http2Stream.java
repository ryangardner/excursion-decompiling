package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 _2\u00020\u0001:\u0004_`abB1\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u000e\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020#J\r\u0010C\u001a\u00020AH\u0000¢\u0006\u0002\bDJ\r\u0010E\u001a\u00020AH\u0000¢\u0006\u0002\bFJ\u0018\u0010G\u001a\u00020A2\u0006\u0010H\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001a\u0010I\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u000e\u0010J\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010K\u001a\u00020A2\u0006\u0010L\u001a\u00020\nJ\u0006\u0010M\u001a\u00020NJ\u0006\u0010O\u001a\u00020PJ\u0006\u0010,\u001a\u00020QJ\u0016\u0010R\u001a\u00020A2\u0006\u00104\u001a\u00020S2\u0006\u0010T\u001a\u00020\u0003J\u0016\u0010U\u001a\u00020A2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010V\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010W\u001a\u00020\nJ\u0006\u0010L\u001a\u00020\nJ\r\u0010X\u001a\u00020AH\u0000¢\u0006\u0002\bYJ$\u0010Z\u001a\u00020A2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010^\u001a\u00020\u0007J\u0006\u0010>\u001a\u00020QR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000f8@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R$\u0010)\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u0018\u0010,\u001a\u00060-R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0018\u00100\u001a\u000601R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0018\u00104\u001a\u000605R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R$\u00108\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010&\"\u0004\b:\u0010(R$\u0010;\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010&\"\u0004\b=\u0010(R\u0018\u0010>\u001a\u00060-R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010/¨\u0006c"},
   d2 = {"Lokhttp3/internal/http2/Http2Stream;", "", "id", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "outFinished", "", "inFinished", "headers", "Lokhttp3/Headers;", "(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V", "getConnection", "()Lokhttp3/internal/http2/Http2Connection;", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "getErrorCode$okhttp", "()Lokhttp3/internal/http2/ErrorCode;", "setErrorCode$okhttp", "(Lokhttp3/internal/http2/ErrorCode;)V", "errorException", "Ljava/io/IOException;", "getErrorException$okhttp", "()Ljava/io/IOException;", "setErrorException$okhttp", "(Ljava/io/IOException;)V", "hasResponseHeaders", "headersQueue", "Ljava/util/ArrayDeque;", "getId", "()I", "isLocallyInitiated", "()Z", "isOpen", "<set-?>", "", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "setReadBytesAcknowledged$okhttp", "(J)V", "readBytesTotal", "getReadBytesTotal", "setReadBytesTotal$okhttp", "readTimeout", "Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "getReadTimeout$okhttp", "()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "sink", "Lokhttp3/internal/http2/Http2Stream$FramingSink;", "getSink$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSink;", "source", "Lokhttp3/internal/http2/Http2Stream$FramingSource;", "getSource$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSource;", "writeBytesMaximum", "getWriteBytesMaximum", "setWriteBytesMaximum$okhttp", "writeBytesTotal", "getWriteBytesTotal", "setWriteBytesTotal$okhttp", "writeTimeout", "getWriteTimeout$okhttp", "addBytesToWriteWindow", "", "delta", "cancelStreamIfNecessary", "cancelStreamIfNecessary$okhttp", "checkOutNotClosed", "checkOutNotClosed$okhttp", "close", "rstStatusCode", "closeInternal", "closeLater", "enqueueTrailers", "trailers", "getSink", "Lokio/Sink;", "getSource", "Lokio/Source;", "Lokio/Timeout;", "receiveData", "Lokio/BufferedSource;", "length", "receiveHeaders", "receiveRstStream", "takeHeaders", "waitForIo", "waitForIo$okhttp", "writeHeaders", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "flushHeaders", "Companion", "FramingSink", "FramingSource", "StreamTimeout", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http2Stream {
   public static final Http2Stream.Companion Companion = new Http2Stream.Companion((DefaultConstructorMarker)null);
   public static final long EMIT_BUFFER_SIZE = 16384L;
   private final Http2Connection connection;
   private ErrorCode errorCode;
   private IOException errorException;
   private boolean hasResponseHeaders;
   private final ArrayDeque<Headers> headersQueue;
   private final int id;
   private long readBytesAcknowledged;
   private long readBytesTotal;
   private final Http2Stream.StreamTimeout readTimeout;
   private final Http2Stream.FramingSink sink;
   private final Http2Stream.FramingSource source;
   private long writeBytesMaximum;
   private long writeBytesTotal;
   private final Http2Stream.StreamTimeout writeTimeout;

   public Http2Stream(int var1, Http2Connection var2, boolean var3, boolean var4, Headers var5) {
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      super();
      this.id = var1;
      this.connection = var2;
      this.writeBytesMaximum = (long)var2.getPeerSettings().getInitialWindowSize();
      this.headersQueue = new ArrayDeque();
      this.source = new Http2Stream.FramingSource((long)this.connection.getOkHttpSettings().getInitialWindowSize(), var4);
      this.sink = new Http2Stream.FramingSink(var3);
      this.readTimeout = new Http2Stream.StreamTimeout();
      this.writeTimeout = new Http2Stream.StreamTimeout();
      if (var5 != null) {
         if (!(this.isLocallyInitiated() ^ true)) {
            throw (Throwable)(new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString()));
         }

         ((Collection)this.headersQueue).add(var5);
      } else if (!this.isLocallyInitiated()) {
         throw (Throwable)(new IllegalStateException("remotely-initiated streams should have headers".toString()));
      }

   }

   private final boolean closeInternal(ErrorCode var1, IOException var2) {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var19 = new StringBuilder();
         var19.append("Thread ");
         Thread var20 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var20, "Thread.currentThread()");
         var19.append(var20.getName());
         var19.append(" MUST NOT hold lock on ");
         var19.append(this);
         throw (Throwable)(new AssertionError(var19.toString()));
      } else {
         synchronized(this){}

         label175: {
            Throwable var10000;
            label181: {
               ErrorCode var3;
               boolean var10001;
               try {
                  var3 = this.errorCode;
               } catch (Throwable var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label181;
               }

               if (var3 != null) {
                  return false;
               }

               label169: {
                  boolean var4;
                  try {
                     if (!this.source.getFinished$okhttp()) {
                        break label169;
                     }

                     var4 = this.sink.getFinished();
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label181;
                  }

                  if (var4) {
                     return false;
                  }
               }

               label163:
               try {
                  this.errorCode = var1;
                  this.errorException = var2;
                  ((Object)this).notifyAll();
                  Unit var18 = Unit.INSTANCE;
                  break label175;
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label163;
               }
            }

            Throwable var17 = var10000;
            throw var17;
         }

         this.connection.removeStream$okhttp(this.id);
         return true;
      }
   }

   public final void addBytesToWriteWindow(long var1) {
      this.writeBytesMaximum += var1;
      if (var1 > 0L) {
         ((Object)this).notifyAll();
      }

   }

   public final void cancelStreamIfNecessary$okhttp() throws IOException {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var12 = new StringBuilder();
         var12.append("Thread ");
         Thread var2 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var12.append(var2.getName());
         var12.append(" MUST NOT hold lock on ");
         var12.append(this);
         throw (Throwable)(new AssertionError(var12.toString()));
      } else {
         synchronized(this){}

         boolean var3;
         boolean var4;
         label146: {
            Throwable var10000;
            label145: {
               boolean var10001;
               label144: {
                  label143: {
                     try {
                        if (!this.source.getFinished$okhttp() && this.source.getClosed$okhttp() && (this.sink.getFinished() || this.sink.getClosed())) {
                           break label143;
                        }
                     } catch (Throwable var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label145;
                     }

                     var3 = false;
                     break label144;
                  }

                  var3 = true;
               }

               label131:
               try {
                  var4 = this.isOpen();
                  Unit var11 = Unit.INSTANCE;
                  break label146;
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label131;
               }
            }

            Throwable var1 = var10000;
            throw var1;
         }

         if (var3) {
            this.close(ErrorCode.CANCEL, (IOException)null);
         } else if (!var4) {
            this.connection.removeStream$okhttp(this.id);
         }

      }
   }

   public final void checkOutNotClosed$okhttp() throws IOException {
      if (!this.sink.getClosed()) {
         if (!this.sink.getFinished()) {
            if (this.errorCode != null) {
               IOException var1 = this.errorException;
               Object var2 = var1;
               if (var1 == null) {
                  ErrorCode var3 = this.errorCode;
                  if (var3 == null) {
                     Intrinsics.throwNpe();
                  }

                  var2 = new StreamResetException(var3);
               }

               throw (Throwable)var2;
            }
         } else {
            throw (Throwable)(new IOException("stream finished"));
         }
      } else {
         throw (Throwable)(new IOException("stream closed"));
      }
   }

   public final void close(ErrorCode var1, IOException var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "rstStatusCode");
      if (this.closeInternal(var1, var2)) {
         this.connection.writeSynReset$okhttp(this.id, var1);
      }
   }

   public final void closeLater(ErrorCode var1) {
      Intrinsics.checkParameterIsNotNull(var1, "errorCode");
      if (this.closeInternal(var1, (IOException)null)) {
         this.connection.writeSynResetLater$okhttp(this.id, var1);
      }
   }

   public final void enqueueTrailers(Headers var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trailers");
      synchronized(this){}

      Throwable var10000;
      label250: {
         boolean var2;
         boolean var10001;
         try {
            var2 = this.sink.getFinished();
         } catch (Throwable var33) {
            var10000 = var33;
            var10001 = false;
            break label250;
         }

         boolean var3 = true;
         if (var2 ^ true) {
            label244: {
               label243: {
                  try {
                     if (var1.size() != 0) {
                        break label243;
                     }
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label244;
                  }

                  var3 = false;
               }

               if (var3) {
                  label235: {
                     try {
                        this.sink.setTrailers(var1);
                        Unit var34 = Unit.INSTANCE;
                     } catch (Throwable var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label235;
                     }

                     return;
                  }
               } else {
                  label237:
                  try {
                     IllegalArgumentException var35 = new IllegalArgumentException("trailers.size() == 0".toString());
                     throw (Throwable)var35;
                  } catch (Throwable var30) {
                     var10000 = var30;
                     var10001 = false;
                     break label237;
                  }
               }
            }
         } else {
            label246:
            try {
               IllegalStateException var37 = new IllegalStateException("already finished".toString());
               throw (Throwable)var37;
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label246;
            }
         }
      }

      Throwable var36 = var10000;
      throw var36;
   }

   public final Http2Connection getConnection() {
      return this.connection;
   }

   public final ErrorCode getErrorCode$okhttp() {
      synchronized(this){}

      ErrorCode var1;
      try {
         var1 = this.errorCode;
      } finally {
         ;
      }

      return var1;
   }

   public final IOException getErrorException$okhttp() {
      return this.errorException;
   }

   public final int getId() {
      return this.id;
   }

   public final long getReadBytesAcknowledged() {
      return this.readBytesAcknowledged;
   }

   public final long getReadBytesTotal() {
      return this.readBytesTotal;
   }

   public final Http2Stream.StreamTimeout getReadTimeout$okhttp() {
      return this.readTimeout;
   }

   public final Sink getSink() {
      synchronized(this){}

      Throwable var10000;
      label150: {
         boolean var10001;
         boolean var1;
         label149: {
            label148: {
               try {
                  if (!this.hasResponseHeaders && !this.isLocallyInitiated()) {
                     break label148;
                  }
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label150;
               }

               var1 = true;
               break label149;
            }

            var1 = false;
         }

         if (var1) {
            label136: {
               try {
                  Unit var2 = Unit.INSTANCE;
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label136;
               }

               return (Sink)this.sink;
            }
         } else {
            label138:
            try {
               IllegalStateException var16 = new IllegalStateException("reply before requesting the sink".toString());
               throw (Throwable)var16;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label138;
            }
         }
      }

      Throwable var15 = var10000;
      throw var15;
   }

   public final Http2Stream.FramingSink getSink$okhttp() {
      return this.sink;
   }

   public final Source getSource() {
      return (Source)this.source;
   }

   public final Http2Stream.FramingSource getSource$okhttp() {
      return this.source;
   }

   public final long getWriteBytesMaximum() {
      return this.writeBytesMaximum;
   }

   public final long getWriteBytesTotal() {
      return this.writeBytesTotal;
   }

   public final Http2Stream.StreamTimeout getWriteTimeout$okhttp() {
      return this.writeTimeout;
   }

   public final boolean isLocallyInitiated() {
      int var1 = this.id;
      boolean var2 = true;
      boolean var3;
      if ((var1 & 1) == 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (this.connection.getClient$okhttp() == var3) {
         var3 = var2;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isOpen() {
      synchronized(this){}

      boolean var2;
      label240: {
         Throwable var10000;
         label245: {
            ErrorCode var1;
            boolean var10001;
            try {
               var1 = this.errorCode;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label245;
            }

            if (var1 != null) {
               return false;
            }

            try {
               if (!this.source.getFinished$okhttp() && !this.source.getClosed$okhttp()) {
                  return true;
               }
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label245;
            }

            try {
               if (!this.sink.getFinished() && !this.sink.getClosed()) {
                  return true;
               }
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label245;
            }

            label229:
            try {
               var2 = this.hasResponseHeaders;
               break label240;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break label229;
            }
         }

         Throwable var23 = var10000;
         throw var23;
      }

      if (var2) {
         return false;
      } else {
         return true;
      }
   }

   public final Timeout readTimeout() {
      return (Timeout)this.readTimeout;
   }

   public final void receiveData(BufferedSource var1, int var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Thread ");
         Thread var4 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var4, "Thread.currentThread()");
         var3.append(var4.getName());
         var3.append(" MUST NOT hold lock on ");
         var3.append(this);
         throw (Throwable)(new AssertionError(var3.toString()));
      } else {
         this.source.receive$okhttp(var1, (long)var2);
      }
   }

   public final void receiveHeaders(Headers var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "headers");
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Thread ");
         Thread var36 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var36, "Thread.currentThread()");
         var3.append(var36.getName());
         var3.append(" MUST NOT hold lock on ");
         var3.append(this);
         throw (Throwable)(new AssertionError(var3.toString()));
      } else {
         synchronized(this){}

         label309: {
            Throwable var10000;
            label315: {
               boolean var10001;
               label307: {
                  label306: {
                     try {
                        if (!this.hasResponseHeaders) {
                           break label306;
                        }
                     } catch (Throwable var33) {
                        var10000 = var33;
                        var10001 = false;
                        break label315;
                     }

                     if (var2) {
                        try {
                           this.source.setTrailers(var1);
                           break label307;
                        } catch (Throwable var32) {
                           var10000 = var32;
                           var10001 = false;
                           break label315;
                        }
                     }
                  }

                  try {
                     this.hasResponseHeaders = true;
                     ((Collection)this.headersQueue).add(var1);
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label315;
                  }
               }

               if (var2) {
                  try {
                     this.source.setFinished$okhttp(true);
                  } catch (Throwable var30) {
                     var10000 = var30;
                     var10001 = false;
                     break label315;
                  }
               }

               label293:
               try {
                  var2 = this.isOpen();
                  ((Object)this).notifyAll();
                  Unit var35 = Unit.INSTANCE;
                  break label309;
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label293;
               }
            }

            Throwable var34 = var10000;
            throw var34;
         }

         if (!var2) {
            this.connection.removeStream$okhttp(this.id);
         }

      }
   }

   public final void receiveRstStream(ErrorCode var1) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "errorCode");
         if (this.errorCode == null) {
            this.errorCode = var1;
            ((Object)this).notifyAll();
         }
      } finally {
         ;
      }

   }

   public final void setErrorCode$okhttp(ErrorCode var1) {
      this.errorCode = var1;
   }

   public final void setErrorException$okhttp(IOException var1) {
      this.errorException = var1;
   }

   public final void setReadBytesAcknowledged$okhttp(long var1) {
      this.readBytesAcknowledged = var1;
   }

   public final void setReadBytesTotal$okhttp(long var1) {
      this.readBytesTotal = var1;
   }

   public final void setWriteBytesMaximum$okhttp(long var1) {
      this.writeBytesMaximum = var1;
   }

   public final void setWriteBytesTotal$okhttp(long var1) {
      this.writeBytesTotal = var1;
   }

   public final Headers takeHeaders() throws IOException {
      synchronized(this){}

      Throwable var10000;
      label751: {
         boolean var10001;
         try {
            this.readTimeout.enter();
         } catch (Throwable var91) {
            var10000 = var91;
            var10001 = false;
            break label751;
         }

         while(true) {
            boolean var75 = false;

            try {
               var75 = true;
               if (this.headersQueue.isEmpty()) {
                  if (this.errorCode == null) {
                     this.waitForIo$okhttp();
                     var75 = false;
                     continue;
                  }

                  var75 = false;
                  break;
               }

               var75 = false;
               break;
            } finally {
               if (var75) {
                  try {
                     this.readTimeout.exitAndThrowIfTimedOut();
                  } catch (Throwable var85) {
                     var10000 = var85;
                     var10001 = false;
                     break label751;
                  }
               }
            }
         }

         Object var1;
         try {
            this.readTimeout.exitAndThrowIfTimedOut();
            if (((Collection)this.headersQueue).isEmpty() ^ true) {
               var1 = this.headersQueue.removeFirst();
               Intrinsics.checkExpressionValueIsNotNull(var1, "headersQueue.removeFirst()");
               Headers var96 = (Headers)var1;
               return var96;
            }
         } catch (Throwable var92) {
            var10000 = var92;
            var10001 = false;
            break label751;
         }

         IOException var2;
         try {
            var2 = this.errorException;
         } catch (Throwable var90) {
            var10000 = var90;
            var10001 = false;
            break label751;
         }

         var1 = var2;
         if (var2 == null) {
            ErrorCode var95;
            try {
               var1 = new StreamResetException;
               var95 = this.errorCode;
            } catch (Throwable var89) {
               var10000 = var89;
               var10001 = false;
               break label751;
            }

            if (var95 == null) {
               try {
                  Intrinsics.throwNpe();
               } catch (Throwable var88) {
                  var10000 = var88;
                  var10001 = false;
                  break label751;
               }
            }

            try {
               var1.<init>(var95);
            } catch (Throwable var87) {
               var10000 = var87;
               var10001 = false;
               break label751;
            }
         }

         label715:
         try {
            throw (Throwable)var1;
         } catch (Throwable var86) {
            var10000 = var86;
            var10001 = false;
            break label715;
         }
      }

      Throwable var94 = var10000;
      throw var94;
   }

   public final Headers trailers() throws IOException {
      synchronized(this){}

      Throwable var10000;
      label822: {
         IOException var1;
         boolean var10001;
         label823: {
            try {
               if (this.errorCode != null) {
                  var1 = this.errorException;
                  break label823;
               }
            } catch (Throwable var93) {
               var10000 = var93;
               var10001 = false;
               break label822;
            }

            boolean var3;
            label809: {
               label808: {
                  try {
                     if (this.source.getFinished$okhttp() && this.source.getReceiveBuffer().exhausted() && this.source.getReadBuffer().exhausted()) {
                        break label808;
                     }
                  } catch (Throwable var92) {
                     var10000 = var92;
                     var10001 = false;
                     break label822;
                  }

                  var3 = false;
                  break label809;
               }

               var3 = true;
            }

            if (var3) {
               Headers var2;
               try {
                  var2 = this.source.getTrailers();
               } catch (Throwable var90) {
                  var10000 = var90;
                  var10001 = false;
                  break label822;
               }

               if (var2 == null) {
                  try {
                     var2 = Util.EMPTY_HEADERS;
                  } catch (Throwable var89) {
                     var10000 = var89;
                     var10001 = false;
                     break label822;
                  }
               }

               return var2;
            } else {
               try {
                  IllegalStateException var95 = new IllegalStateException("too early; can't read the trailers yet".toString());
                  throw (Throwable)var95;
               } catch (Throwable var91) {
                  var10000 = var91;
                  var10001 = false;
                  break label822;
               }
            }
         }

         Object var96 = var1;
         if (var1 == null) {
            ErrorCode var94;
            try {
               var96 = new StreamResetException;
               var94 = this.errorCode;
            } catch (Throwable var88) {
               var10000 = var88;
               var10001 = false;
               break label822;
            }

            if (var94 == null) {
               try {
                  Intrinsics.throwNpe();
               } catch (Throwable var87) {
                  var10000 = var87;
                  var10001 = false;
                  break label822;
               }
            }

            try {
               var96.<init>(var94);
            } catch (Throwable var86) {
               var10000 = var86;
               var10001 = false;
               break label822;
            }
         }

         label779:
         try {
            throw (Throwable)var96;
         } catch (Throwable var85) {
            var10000 = var85;
            var10001 = false;
            break label779;
         }
      }

      Throwable var97 = var10000;
      throw var97;
   }

   public final void waitForIo$okhttp() throws InterruptedIOException {
      try {
         ((Object)this).wait();
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         throw (Throwable)(new InterruptedIOException());
      }
   }

   public final void writeHeaders(List<Header> var1, boolean var2, boolean var3) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "responseHeaders");
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var41 = new StringBuilder();
         var41.append("Thread ");
         Thread var39 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var39, "Thread.currentThread()");
         var41.append(var39.getName());
         var41.append(" MUST NOT hold lock on ");
         var41.append(this);
         throw (Throwable)(new AssertionError(var41.toString()));
      } else {
         synchronized(this){}
         boolean var5 = true;

         Throwable var38;
         Throwable var10000;
         boolean var10001;
         label402: {
            label408: {
               try {
                  this.hasResponseHeaders = true;
               } catch (Throwable var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label408;
               }

               if (var2) {
                  try {
                     this.sink.setFinished(true);
                  } catch (Throwable var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label408;
                  }
               }

               label394:
               try {
                  Unit var4 = Unit.INSTANCE;
                  break label402;
               } catch (Throwable var35) {
                  var10000 = var35;
                  var10001 = false;
                  break label394;
               }
            }

            var38 = var10000;
            throw var38;
         }

         boolean var6 = var3;
         if (!var3) {
            Http2Connection var40 = this.connection;
            synchronized(var40){}

            label387: {
               label386: {
                  label385: {
                     label384: {
                        try {
                           if (this.connection.getWriteBytesTotal() >= this.connection.getWriteBytesMaximum()) {
                              break label384;
                           }
                        } catch (Throwable var34) {
                           var10000 = var34;
                           var10001 = false;
                           break label386;
                        }

                        var3 = false;
                        break label385;
                     }

                     var3 = var5;
                  }

                  label378:
                  try {
                     Unit var7 = Unit.INSTANCE;
                     break label387;
                  } catch (Throwable var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label378;
                  }
               }

               var38 = var10000;
               throw var38;
            }

            var6 = var3;
         }

         this.connection.writeHeaders$okhttp(this.id, var2, var1);
         if (var6) {
            this.connection.flush();
         }

      }
   }

   public final Timeout writeTimeout() {
      return (Timeout)this.writeTimeout;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lokhttp3/internal/http2/Http2Stream$Companion;", "", "()V", "EMIT_BUFFER_SIZE", "", "okhttp"},
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

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001f"},
      d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSink;", "Lokio/Sink;", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;Z)V", "closed", "getClosed", "()Z", "setClosed", "(Z)V", "getFinished", "setFinished", "sendBuffer", "Lokio/Buffer;", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "emitFrame", "outFinishedOnLastFrame", "flush", "timeout", "Lokio/Timeout;", "write", "source", "byteCount", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class FramingSink implements Sink {
      private boolean closed;
      private boolean finished;
      private final Buffer sendBuffer;
      private Headers trailers;

      public FramingSink(boolean var2) {
         this.finished = var2;
         this.sendBuffer = new Buffer();
      }

      // $FF: synthetic method
      public FramingSink(boolean var2, int var3, DefaultConstructorMarker var4) {
         if ((var3 & 1) != 0) {
            var2 = false;
         }

         this(var2);
      }

      private final void emitFrame(boolean var1) throws IOException {
         Http2Stream var2 = Http2Stream.this;
         synchronized(var2){}

         long var3;
         label593: {
            Throwable var10000;
            label592: {
               boolean var10001;
               try {
                  Http2Stream.this.getWriteTimeout$okhttp().enter();
               } catch (Throwable var61) {
                  var10000 = var61;
                  var10001 = false;
                  break label592;
               }

               while(true) {
                  boolean var48 = false;

                  try {
                     var48 = true;
                     if (Http2Stream.this.getWriteBytesTotal() >= Http2Stream.this.getWriteBytesMaximum()) {
                        if (!this.finished) {
                           if (!this.closed) {
                              if (Http2Stream.this.getErrorCode$okhttp() == null) {
                                 Http2Stream.this.waitForIo$okhttp();
                                 var48 = false;
                                 continue;
                              }

                              var48 = false;
                           } else {
                              var48 = false;
                           }
                        } else {
                           var48 = false;
                        }
                     } else {
                        var48 = false;
                     }
                  } finally {
                     if (var48) {
                        try {
                           Http2Stream.this.getWriteTimeout$okhttp().exitAndThrowIfTimedOut();
                        } catch (Throwable var57) {
                           var10000 = var57;
                           var10001 = false;
                           break;
                        }
                     }
                  }

                  try {
                     Http2Stream.this.getWriteTimeout$okhttp().exitAndThrowIfTimedOut();
                     Http2Stream.this.checkOutNotClosed$okhttp();
                     var3 = Math.min(Http2Stream.this.getWriteBytesMaximum() - Http2Stream.this.getWriteBytesTotal(), this.sendBuffer.size());
                     Http2Stream var5 = Http2Stream.this;
                     var5.setWriteBytesTotal$okhttp(var5.getWriteBytesTotal() + var3);
                  } catch (Throwable var60) {
                     var10000 = var60;
                     var10001 = false;
                     break;
                  }

                  label575: {
                     label574: {
                        if (var1) {
                           try {
                              if (var3 == this.sendBuffer.size() && Http2Stream.this.getErrorCode$okhttp() == null) {
                                 break label574;
                              }
                           } catch (Throwable var59) {
                              var10000 = var59;
                              var10001 = false;
                              break;
                           }
                        }

                        var1 = false;
                        break label575;
                     }

                     var1 = true;
                  }

                  try {
                     Unit var64 = Unit.INSTANCE;
                     break label593;
                  } catch (Throwable var58) {
                     var10000 = var58;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var63 = var10000;
            throw var63;
         }

         Http2Stream.this.getWriteTimeout$okhttp().enter();

         try {
            Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), var1, this.sendBuffer, var3);
         } finally {
            Http2Stream.this.getWriteTimeout$okhttp().exitAndThrowIfTimedOut();
         }

      }

      public void close() throws IOException {
         Http2Stream var1 = Http2Stream.this;
         if (Util.assertionsEnabled && Thread.holdsLock(var1)) {
            StringBuilder var29 = new StringBuilder();
            var29.append("Thread ");
            Thread var31 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var31, "Thread.currentThread()");
            var29.append(var31.getName());
            var29.append(" MUST NOT hold lock on ");
            var29.append(var1);
            throw (Throwable)(new AssertionError(var29.toString()));
         } else {
            Http2Stream var3 = Http2Stream.this;
            synchronized(var3){}

            boolean var4;
            Unit var27;
            label454: {
               Throwable var10000;
               label460: {
                  boolean var10001;
                  try {
                     var4 = this.closed;
                  } catch (Throwable var26) {
                     var10000 = var26;
                     var10001 = false;
                     break label460;
                  }

                  if (var4) {
                     return;
                  }

                  label448: {
                     label447: {
                        try {
                           if (Http2Stream.this.getErrorCode$okhttp() == null) {
                              break label447;
                           }
                        } catch (Throwable var25) {
                           var10000 = var25;
                           var10001 = false;
                           break label460;
                        }

                        var4 = false;
                        break label448;
                     }

                     var4 = true;
                  }

                  label441:
                  try {
                     var27 = Unit.INSTANCE;
                     break label454;
                  } catch (Throwable var24) {
                     var10000 = var24;
                     var10001 = false;
                     break label441;
                  }
               }

               Throwable var2 = var10000;
               throw var2;
            }

            if (!Http2Stream.this.getSink$okhttp().finished) {
               boolean var5;
               if (this.sendBuffer.size() > 0L) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               boolean var6;
               if (this.trailers != null) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (!var6) {
                  if (var5) {
                     while(this.sendBuffer.size() > 0L) {
                        this.emitFrame(true);
                     }
                  } else if (var4) {
                     Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), true, (Buffer)null, 0L);
                  }
               } else {
                  while(this.sendBuffer.size() > 0L) {
                     this.emitFrame(false);
                  }

                  Http2Connection var30 = Http2Stream.this.getConnection();
                  int var32 = Http2Stream.this.getId();
                  Headers var28 = this.trailers;
                  if (var28 == null) {
                     Intrinsics.throwNpe();
                  }

                  var30.writeHeaders$okhttp(var32, var4, Util.toHeaderList(var28));
               }
            }

            var3 = Http2Stream.this;
            synchronized(var3){}

            try {
               this.closed = true;
               var27 = Unit.INSTANCE;
            } finally {
               ;
            }

            Http2Stream.this.getConnection().flush();
            Http2Stream.this.cancelStreamIfNecessary$okhttp();
         }
      }

      public void flush() throws IOException {
         Http2Stream var1 = Http2Stream.this;
         if (Util.assertionsEnabled && Thread.holdsLock(var1)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Thread ");
            Thread var7 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var7, "Thread.currentThread()");
            var2.append(var7.getName());
            var2.append(" MUST NOT hold lock on ");
            var2.append(var1);
            throw (Throwable)(new AssertionError(var2.toString()));
         } else {
            Http2Stream var3 = Http2Stream.this;
            synchronized(var3){}

            try {
               Http2Stream.this.checkOutNotClosed$okhttp();
               Unit var6 = Unit.INSTANCE;
            } finally {
               ;
            }

            while(this.sendBuffer.size() > 0L) {
               this.emitFrame(false);
               Http2Stream.this.getConnection().flush();
            }

         }
      }

      public final boolean getClosed() {
         return this.closed;
      }

      public final boolean getFinished() {
         return this.finished;
      }

      public final Headers getTrailers() {
         return this.trailers;
      }

      public final void setClosed(boolean var1) {
         this.closed = var1;
      }

      public final void setFinished(boolean var1) {
         this.finished = var1;
      }

      public final void setTrailers(Headers var1) {
         this.trailers = var1;
      }

      public Timeout timeout() {
         return (Timeout)Http2Stream.this.getWriteTimeout$okhttp();
      }

      public void write(Buffer var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Http2Stream var4 = Http2Stream.this;
         if (Util.assertionsEnabled && Thread.holdsLock(var4)) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Thread ");
            Thread var5 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
            var6.append(var5.getName());
            var6.append(" MUST NOT hold lock on ");
            var6.append(var4);
            throw (Throwable)(new AssertionError(var6.toString()));
         } else {
            this.sendBuffer.write(var1, var2);

            while(this.sendBuffer.size() >= 16384L) {
               this.emitFrame(false);
            }

         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0003H\u0016J\u001d\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u0003H\u0000¢\u0006\u0002\b\"J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002R\u001a\u0010\u0007\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006&"},
      d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSource;", "Lokio/Source;", "maxByteCount", "", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;JZ)V", "closed", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getFinished$okhttp", "setFinished$okhttp", "readBuffer", "Lokio/Buffer;", "getReadBuffer", "()Lokio/Buffer;", "receiveBuffer", "getReceiveBuffer", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "read", "sink", "byteCount", "receive", "source", "Lokio/BufferedSource;", "receive$okhttp", "timeout", "Lokio/Timeout;", "updateConnectionFlowControl", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class FramingSource implements Source {
      private boolean closed;
      private boolean finished;
      private final long maxByteCount;
      private final Buffer readBuffer;
      private final Buffer receiveBuffer;
      private Headers trailers;

      public FramingSource(long var2, boolean var4) {
         this.maxByteCount = var2;
         this.finished = var4;
         this.receiveBuffer = new Buffer();
         this.readBuffer = new Buffer();
      }

      private final void updateConnectionFlowControl(long var1) {
         Http2Stream var3 = Http2Stream.this;
         if (Util.assertionsEnabled && Thread.holdsLock(var3)) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Thread ");
            Thread var5 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
            var4.append(var5.getName());
            var4.append(" MUST NOT hold lock on ");
            var4.append(var3);
            throw (Throwable)(new AssertionError(var4.toString()));
         } else {
            Http2Stream.this.getConnection().updateConnectionFlowControl$okhttp(var1);
         }
      }

      public void close() throws IOException {
         Http2Stream var1 = Http2Stream.this;
         synchronized(var1){}

         Throwable var10000;
         label124: {
            boolean var10001;
            long var2;
            Http2Stream var4;
            try {
               this.closed = true;
               var2 = this.readBuffer.size();
               this.readBuffer.clear();
               var4 = Http2Stream.this;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label124;
            }

            if (var4 != null) {
               label128: {
                  try {
                     ((Object)var4).notifyAll();
                     Unit var17 = Unit.INSTANCE;
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label128;
                  }

                  if (var2 > 0L) {
                     this.updateConnectionFlowControl(var2);
                  }

                  Http2Stream.this.cancelStreamIfNecessary$okhttp();
                  return;
               }
            } else {
               label120:
               try {
                  TypeCastException var19 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                  throw var19;
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label120;
               }
            }
         }

         Throwable var18 = var10000;
         throw var18;
      }

      public final boolean getClosed$okhttp() {
         return this.closed;
      }

      public final boolean getFinished$okhttp() {
         return this.finished;
      }

      public final Buffer getReadBuffer() {
         return this.readBuffer;
      }

      public final Buffer getReceiveBuffer() {
         return this.receiveBuffer;
      }

      public final Headers getTrailers() {
         return this.trailers;
      }

      public long read(Buffer var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         boolean var4;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            StringBuilder var172 = new StringBuilder();
            var172.append("byteCount < 0: ");
            var172.append(var2);
            throw (Throwable)(new IllegalArgumentException(var172.toString().toString()));
         } else {
            while(true) {
               IOException var5 = (IOException)null;
               Http2Stream var6 = Http2Stream.this;
               synchronized(var6){}

               Throwable var10000;
               Throwable var170;
               label1811: {
                  boolean var10001;
                  try {
                     Http2Stream.this.getReadTimeout$okhttp().enter();
                  } catch (Throwable var169) {
                     var10000 = var169;
                     var10001 = false;
                     break label1811;
                  }

                  label1812: {
                     label1799: {
                        try {
                           if (Http2Stream.this.getErrorCode$okhttp() == null) {
                              break label1799;
                           }

                           var5 = Http2Stream.this.getErrorException$okhttp();
                        } catch (Throwable var168) {
                           var10000 = var168;
                           var10001 = false;
                           break label1812;
                        }

                        if (var5 == null) {
                           ErrorCode var7;
                           StreamResetException var173;
                           try {
                              var173 = new StreamResetException;
                              var7 = Http2Stream.this.getErrorCode$okhttp();
                           } catch (Throwable var164) {
                              var10000 = var164;
                              var10001 = false;
                              break label1812;
                           }

                           if (var7 == null) {
                              try {
                                 Intrinsics.throwNpe();
                              } catch (Throwable var163) {
                                 var10000 = var163;
                                 var10001 = false;
                                 break label1812;
                              }
                           }

                           try {
                              var173.<init>(var7);
                              var5 = (IOException)var173;
                           } catch (Throwable var162) {
                              var10000 = var162;
                              var10001 = false;
                              break label1812;
                           }
                        }
                     }

                     long var12;
                     label1793: {
                        label1792: {
                           label1814: {
                              label1790: {
                                 long var8;
                                 long var10;
                                 try {
                                    if (this.closed) {
                                       break label1792;
                                    }

                                    if (this.readBuffer.size() <= 0L) {
                                       break label1790;
                                    }

                                    var8 = this.readBuffer.read(var1, Math.min(var2, this.readBuffer.size()));
                                    Http2Stream var174 = Http2Stream.this;
                                    var174.setReadBytesTotal$okhttp(var174.getReadBytesTotal() + var8);
                                    var10 = Http2Stream.this.getReadBytesTotal() - Http2Stream.this.getReadBytesAcknowledged();
                                 } catch (Throwable var167) {
                                    var10000 = var167;
                                    var10001 = false;
                                    break label1812;
                                 }

                                 var12 = var8;
                                 if (var5 == null) {
                                    var12 = var8;

                                    try {
                                       if (var10 < (long)(Http2Stream.this.getConnection().getOkHttpSettings().getInitialWindowSize() / 2)) {
                                          break label1814;
                                       }

                                       Http2Stream.this.getConnection().writeWindowUpdateLater$okhttp(Http2Stream.this.getId(), var10);
                                       Http2Stream.this.setReadBytesAcknowledged$okhttp(Http2Stream.this.getReadBytesTotal());
                                    } catch (Throwable var166) {
                                       var10000 = var166;
                                       var10001 = false;
                                       break label1812;
                                    }

                                    var12 = var8;
                                 }
                                 break label1814;
                              }

                              label1775: {
                                 try {
                                    if (this.finished) {
                                       break label1775;
                                    }
                                 } catch (Throwable var165) {
                                    var10000 = var165;
                                    var10001 = false;
                                    break label1812;
                                 }

                                 if (var5 == null) {
                                    try {
                                       Http2Stream.this.waitForIo$okhttp();
                                    } catch (Throwable var161) {
                                       var10000 = var161;
                                       var10001 = false;
                                       break label1812;
                                    }

                                    var12 = -1L;
                                    var4 = true;
                                    break label1793;
                                 }
                              }

                              var12 = -1L;
                           }

                           var4 = false;
                           break label1793;
                        }

                        try {
                           IOException var171 = new IOException("stream closed");
                           throw (Throwable)var171;
                        } catch (Throwable var160) {
                           var10000 = var160;
                           var10001 = false;
                           break label1812;
                        }
                     }

                     try {
                        Http2Stream.this.getReadTimeout$okhttp().exitAndThrowIfTimedOut();
                        Unit var175 = Unit.INSTANCE;
                     } catch (Throwable var159) {
                        var10000 = var159;
                        var10001 = false;
                        break label1811;
                     }

                     if (!var4) {
                        if (var12 != -1L) {
                           this.updateConnectionFlowControl(var12);
                           return var12;
                        }

                        if (var5 != null) {
                           if (var5 == null) {
                              Intrinsics.throwNpe();
                           }

                           throw (Throwable)var5;
                        }

                        return -1L;
                     }
                     continue;
                  }

                  var170 = var10000;

                  label1749:
                  try {
                     Http2Stream.this.getReadTimeout$okhttp().exitAndThrowIfTimedOut();
                     throw var170;
                  } catch (Throwable var158) {
                     var10000 = var158;
                     var10001 = false;
                     break label1749;
                  }
               }

               var170 = var10000;
               throw var170;
            }
         }
      }

      public final void receive$okhttp(BufferedSource var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Http2Stream var4 = Http2Stream.this;
         long var5 = var2;
         if (Util.assertionsEnabled) {
            if (Thread.holdsLock(var4)) {
               StringBuilder var107 = new StringBuilder();
               var107.append("Thread ");
               Thread var105 = Thread.currentThread();
               Intrinsics.checkExpressionValueIsNotNull(var105, "Thread.currentThread()");
               var107.append(var105.getName());
               var107.append(" MUST NOT hold lock on ");
               var107.append(var4);
               throw (Throwable)(new AssertionError(var107.toString()));
            }

            var5 = var2;
         }

         while(true) {
            if (var5 > 0L) {
               var4 = Http2Stream.this;
               synchronized(var4){}

               Unit var7;
               boolean var8;
               long var9;
               boolean var11;
               boolean var12;
               Throwable var10000;
               boolean var10001;
               Throwable var103;
               label1145: {
                  label1153: {
                     try {
                        var8 = this.finished;
                        var9 = this.readBuffer.size();
                        var2 = this.maxByteCount;
                     } catch (Throwable var102) {
                        var10000 = var102;
                        var10001 = false;
                        break label1153;
                     }

                     var11 = true;
                     if (var9 + var5 > var2) {
                        var12 = true;
                     } else {
                        var12 = false;
                     }

                     label1140:
                     try {
                        var7 = Unit.INSTANCE;
                        break label1145;
                     } catch (Throwable var101) {
                        var10000 = var101;
                        var10001 = false;
                        break label1140;
                     }
                  }

                  var103 = var10000;
                  throw var103;
               }

               if (var12) {
                  var1.skip(var5);
                  Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                  return;
               }

               if (var8) {
                  var1.skip(var5);
                  return;
               }

               var2 = var1.read(this.receiveBuffer, var5);
               if (var2 != -1L) {
                  var9 = var5 - var2;
                  var4 = Http2Stream.this;
                  synchronized(var4){}

                  label1155: {
                     label1158: {
                        try {
                           if (this.closed) {
                              var2 = this.receiveBuffer.size();
                              this.receiveBuffer.clear();
                              break label1158;
                           }
                        } catch (Throwable var100) {
                           var10000 = var100;
                           var10001 = false;
                           break label1155;
                        }

                        label1121: {
                           label1120: {
                              try {
                                 if (this.readBuffer.size() == 0L) {
                                    break label1120;
                                 }
                              } catch (Throwable var99) {
                                 var10000 = var99;
                                 var10001 = false;
                                 break label1155;
                              }

                              var12 = false;
                              break label1121;
                           }

                           var12 = var11;
                        }

                        try {
                           this.readBuffer.writeAll((Source)this.receiveBuffer);
                        } catch (Throwable var98) {
                           var10000 = var98;
                           var10001 = false;
                           break label1155;
                        }

                        if (var12) {
                           Http2Stream var106;
                           try {
                              var106 = Http2Stream.this;
                           } catch (Throwable var97) {
                              var10000 = var97;
                              var10001 = false;
                              break label1155;
                           }

                           if (var106 == null) {
                              try {
                                 TypeCastException var104 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                 throw var104;
                              } catch (Throwable var94) {
                                 var10000 = var94;
                                 var10001 = false;
                                 break label1155;
                              }
                           }

                           try {
                              ((Object)var106).notifyAll();
                           } catch (Throwable var96) {
                              var10000 = var96;
                              var10001 = false;
                              break label1155;
                           }
                        }

                        var2 = 0L;
                     }

                     try {
                        var7 = Unit.INSTANCE;
                     } catch (Throwable var95) {
                        var10000 = var95;
                        var10001 = false;
                        break label1155;
                     }

                     var5 = var9;
                     if (var2 > 0L) {
                        this.updateConnectionFlowControl(var2);
                        var5 = var9;
                     }
                     continue;
                  }

                  var103 = var10000;
                  throw var103;
               }

               throw (Throwable)(new EOFException());
            }

            return;
         }
      }

      public final void setClosed$okhttp(boolean var1) {
         this.closed = var1;
      }

      public final void setFinished$okhttp(boolean var1) {
         this.finished = var1;
      }

      public final void setTrailers(Headers var1) {
         this.trailers = var1;
      }

      public Timeout timeout() {
         return (Timeout)Http2Stream.this.getReadTimeout$okhttp();
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0014J\b\u0010\b\u001a\u00020\u0004H\u0014¨\u0006\t"},
      d2 = {"Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "Lokio/AsyncTimeout;", "(Lokhttp3/internal/http2/Http2Stream;)V", "exitAndThrowIfTimedOut", "", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class StreamTimeout extends AsyncTimeout {
      public final void exitAndThrowIfTimedOut() throws IOException {
         if (this.exit()) {
            throw (Throwable)this.newTimeoutException((IOException)null);
         }
      }

      protected IOException newTimeoutException(IOException var1) {
         SocketTimeoutException var2 = new SocketTimeoutException("timeout");
         if (var1 != null) {
            var2.initCause((Throwable)var1);
         }

         return (IOException)var2;
      }

      protected void timedOut() {
         Http2Stream.this.closeLater(ErrorCode.CANCEL);
         Http2Stream.this.getConnection().sendDegradedPingLater$okhttp();
      }
   }
}
