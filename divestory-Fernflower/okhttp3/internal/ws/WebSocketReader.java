package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001&B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0006\u0010 \u001a\u00020\u001fJ\b\u0010!\u001a\u00020\u001fH\u0002J\b\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\b\u0010%\u001a\u00020\u001fH\u0002R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006'"},
   d2 = {"Lokhttp3/internal/ws/WebSocketReader;", "Ljava/io/Closeable;", "isClient", "", "source", "Lokio/BufferedSource;", "frameCallback", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "perMessageDeflate", "noContextTakeover", "(ZLokio/BufferedSource;Lokhttp3/internal/ws/WebSocketReader$FrameCallback;ZZ)V", "closed", "controlFrameBuffer", "Lokio/Buffer;", "frameLength", "", "isControlFrame", "isFinalFrame", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageFrameBuffer", "messageInflater", "Lokhttp3/internal/ws/MessageInflater;", "opcode", "", "readingCompressedMessage", "getSource", "()Lokio/BufferedSource;", "close", "", "processNextFrame", "readControlFrame", "readHeader", "readMessage", "readMessageFrame", "readUntilNonControlFrame", "FrameCallback", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class WebSocketReader implements Closeable {
   private boolean closed;
   private final Buffer controlFrameBuffer;
   private final WebSocketReader.FrameCallback frameCallback;
   private long frameLength;
   private final boolean isClient;
   private boolean isControlFrame;
   private boolean isFinalFrame;
   private final Buffer.UnsafeCursor maskCursor;
   private final byte[] maskKey;
   private final Buffer messageFrameBuffer;
   private MessageInflater messageInflater;
   private final boolean noContextTakeover;
   private int opcode;
   private final boolean perMessageDeflate;
   private boolean readingCompressedMessage;
   private final BufferedSource source;

   public WebSocketReader(boolean var1, BufferedSource var2, WebSocketReader.FrameCallback var3, boolean var4, boolean var5) {
      Intrinsics.checkParameterIsNotNull(var2, "source");
      Intrinsics.checkParameterIsNotNull(var3, "frameCallback");
      super();
      this.isClient = var1;
      this.source = var2;
      this.frameCallback = var3;
      this.perMessageDeflate = var4;
      this.noContextTakeover = var5;
      this.controlFrameBuffer = new Buffer();
      this.messageFrameBuffer = new Buffer();
      var1 = this.isClient;
      var3 = null;
      byte[] var6;
      if (var1) {
         var6 = null;
      } else {
         var6 = new byte[4];
      }

      this.maskKey = var6;
      Buffer.UnsafeCursor var7;
      if (this.isClient) {
         var7 = var3;
      } else {
         var7 = new Buffer.UnsafeCursor();
      }

      this.maskCursor = var7;
   }

   private final void readControlFrame() throws IOException {
      long var1 = this.frameLength;
      if (var1 > 0L) {
         this.source.readFully(this.controlFrameBuffer, var1);
         if (!this.isClient) {
            Buffer var3 = this.controlFrameBuffer;
            Buffer.UnsafeCursor var4 = this.maskCursor;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var3.readAndWriteUnsafe(var4);
            this.maskCursor.seek(0L);
            WebSocketProtocol var9 = WebSocketProtocol.INSTANCE;
            Buffer.UnsafeCursor var7 = this.maskCursor;
            byte[] var5 = this.maskKey;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            var9.toggleMask(var7, var5);
            this.maskCursor.close();
         }
      }

      switch(this.opcode) {
      case 8:
         short var6 = 1005;
         var1 = this.controlFrameBuffer.size();
         if (var1 == 1L) {
            throw (Throwable)(new ProtocolException("Malformed close payload length of 1."));
         }

         String var8;
         if (var1 != 0L) {
            var6 = this.controlFrameBuffer.readShort();
            var8 = this.controlFrameBuffer.readUtf8();
            String var11 = WebSocketProtocol.INSTANCE.closeCodeExceptionMessage(var6);
            if (var11 != null) {
               throw (Throwable)(new ProtocolException(var11));
            }
         } else {
            var8 = "";
         }

         this.frameCallback.onReadClose(var6, var8);
         this.closed = true;
         break;
      case 9:
         this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
         break;
      case 10:
         this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
         break;
      default:
         StringBuilder var10 = new StringBuilder();
         var10.append("Unknown control opcode: ");
         var10.append(Util.toHexString(this.opcode));
         throw (Throwable)(new ProtocolException(var10.toString()));
      }

   }

   private final void readHeader() throws IOException, ProtocolException {
      if (!this.closed) {
         long var1 = this.source.timeout().timeoutNanos();
         this.source.timeout().clearTimeout();

         int var3;
         try {
            var3 = Util.and((byte)this.source.readByte(), 255);
         } finally {
            this.source.timeout().timeout(var1, TimeUnit.NANOSECONDS);
         }

         this.opcode = var3 & 15;
         boolean var4 = false;
         boolean var5;
         if ((var3 & 128) != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.isFinalFrame = var5;
         if ((var3 & 8) != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.isControlFrame = var5;
         if (var5 && !this.isFinalFrame) {
            throw (Throwable)(new ProtocolException("Control frames must be final."));
         } else {
            boolean var6;
            if ((var3 & 64) != 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            int var7 = this.opcode;
            if (var7 != 1 && var7 != 2) {
               if (var6) {
                  throw (Throwable)(new ProtocolException("Unexpected rsv1 flag"));
               }
            } else if (var6) {
               if (!this.perMessageDeflate) {
                  throw (Throwable)(new ProtocolException("Unexpected rsv1 flag"));
               }

               this.readingCompressedMessage = true;
            } else {
               this.readingCompressedMessage = false;
            }

            if ((var3 & 32) != 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (!var6) {
               if ((var3 & 16) != 0) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (!var6) {
                  int var14 = Util.and((byte)this.source.readByte(), 255);
                  var5 = var4;
                  if ((var14 & 128) != 0) {
                     var5 = true;
                  }

                  if (var5 == this.isClient) {
                     String var13;
                     if (this.isClient) {
                        var13 = "Server-sent frames must not be masked.";
                     } else {
                        var13 = "Client-sent frames must be masked.";
                     }

                     throw (Throwable)(new ProtocolException(var13));
                  } else {
                     var1 = (long)(var14 & 127);
                     this.frameLength = var1;
                     if (var1 == (long)126) {
                        this.frameLength = (long)Util.and((short)this.source.readShort(), 65535);
                     } else if (var1 == (long)127) {
                        var1 = this.source.readLong();
                        this.frameLength = var1;
                        if (var1 < 0L) {
                           StringBuilder var12 = new StringBuilder();
                           var12.append("Frame length 0x");
                           var12.append(Util.toHexString(this.frameLength));
                           var12.append(" > 0x7FFFFFFFFFFFFFFF");
                           throw (Throwable)(new ProtocolException(var12.toString()));
                        }
                     }

                     if (this.isControlFrame && this.frameLength > 125L) {
                        throw (Throwable)(new ProtocolException("Control frame must be less than 125B."));
                     } else {
                        if (var5) {
                           BufferedSource var8 = this.source;
                           byte[] var9 = this.maskKey;
                           if (var9 == null) {
                              Intrinsics.throwNpe();
                           }

                           var8.readFully(var9);
                        }

                     }
                  }
               } else {
                  throw (Throwable)(new ProtocolException("Unexpected rsv3 flag"));
               }
            } else {
               throw (Throwable)(new ProtocolException("Unexpected rsv2 flag"));
            }
         }
      } else {
         throw (Throwable)(new IOException("closed"));
      }
   }

   private final void readMessage() throws IOException {
      while(true) {
         if (!this.closed) {
            long var1 = this.frameLength;
            if (var1 > 0L) {
               this.source.readFully(this.messageFrameBuffer, var1);
               if (!this.isClient) {
                  Buffer var3 = this.messageFrameBuffer;
                  Buffer.UnsafeCursor var4 = this.maskCursor;
                  if (var4 == null) {
                     Intrinsics.throwNpe();
                  }

                  var3.readAndWriteUnsafe(var4);
                  this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
                  WebSocketProtocol var6 = WebSocketProtocol.INSTANCE;
                  var4 = this.maskCursor;
                  byte[] var5 = this.maskKey;
                  if (var5 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6.toggleMask(var4, var5);
                  this.maskCursor.close();
               }
            }

            if (this.isFinalFrame) {
               return;
            }

            this.readUntilNonControlFrame();
            if (this.opcode == 0) {
               continue;
            }

            StringBuilder var7 = new StringBuilder();
            var7.append("Expected continuation opcode. Got: ");
            var7.append(Util.toHexString(this.opcode));
            throw (Throwable)(new ProtocolException(var7.toString()));
         }

         throw (Throwable)(new IOException("closed"));
      }
   }

   private final void readMessageFrame() throws IOException {
      int var1 = this.opcode;
      if (var1 != 1 && var1 != 2) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Unknown opcode: ");
         var3.append(Util.toHexString(var1));
         throw (Throwable)(new ProtocolException(var3.toString()));
      } else {
         this.readMessage();
         if (this.readingCompressedMessage) {
            MessageInflater var2 = this.messageInflater;
            if (var2 == null) {
               var2 = new MessageInflater(this.noContextTakeover);
               this.messageInflater = var2;
            }

            var2.inflate(this.messageFrameBuffer);
         }

         if (var1 == 1) {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
         } else {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
         }

      }
   }

   private final void readUntilNonControlFrame() throws IOException {
      while(true) {
         if (!this.closed) {
            this.readHeader();
            if (this.isControlFrame) {
               this.readControlFrame();
               continue;
            }
         }

         return;
      }
   }

   public void close() throws IOException {
      MessageInflater var1 = this.messageInflater;
      if (var1 != null) {
         var1.close();
      }

   }

   public final BufferedSource getSource() {
      return this.source;
   }

   public final void processNextFrame() throws IOException {
      this.readHeader();
      if (this.isControlFrame) {
         this.readControlFrame();
      } else {
         this.readMessageFrame();
      }

   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000bH&¨\u0006\u000f"},
      d2 = {"Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "", "onReadClose", "", "code", "", "reason", "", "onReadMessage", "text", "bytes", "Lokio/ByteString;", "onReadPing", "payload", "onReadPong", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public interface FrameCallback {
      void onReadClose(int var1, String var2);

      void onReadMessage(String var1) throws IOException;

      void onReadMessage(ByteString var1) throws IOException;

      void onReadPing(ByteString var1);

      void onReadPong(ByteString var1);
   }
}
