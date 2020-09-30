package okhttp3.internal.ws;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001f\u001a\u00020\u0006J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020$J\u000e\u0010%\u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000fX\u0080T¢\u0006\u0002\n\u0000¨\u0006&"},
   d2 = {"Lokhttp3/internal/ws/WebSocketProtocol;", "", "()V", "ACCEPT_MAGIC", "", "B0_FLAG_FIN", "", "B0_FLAG_RSV1", "B0_FLAG_RSV2", "B0_FLAG_RSV3", "B0_MASK_OPCODE", "B1_FLAG_MASK", "B1_MASK_LENGTH", "CLOSE_CLIENT_GOING_AWAY", "CLOSE_MESSAGE_MAX", "", "CLOSE_NO_STATUS_CODE", "OPCODE_BINARY", "OPCODE_CONTINUATION", "OPCODE_CONTROL_CLOSE", "OPCODE_CONTROL_PING", "OPCODE_CONTROL_PONG", "OPCODE_FLAG_CONTROL", "OPCODE_TEXT", "PAYLOAD_BYTE_MAX", "PAYLOAD_LONG", "PAYLOAD_SHORT", "PAYLOAD_SHORT_MAX", "acceptHeader", "key", "closeCodeExceptionMessage", "code", "toggleMask", "", "cursor", "Lokio/Buffer$UnsafeCursor;", "", "validateCloseCode", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class WebSocketProtocol {
   public static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
   public static final int B0_FLAG_FIN = 128;
   public static final int B0_FLAG_RSV1 = 64;
   public static final int B0_FLAG_RSV2 = 32;
   public static final int B0_FLAG_RSV3 = 16;
   public static final int B0_MASK_OPCODE = 15;
   public static final int B1_FLAG_MASK = 128;
   public static final int B1_MASK_LENGTH = 127;
   public static final int CLOSE_CLIENT_GOING_AWAY = 1001;
   public static final long CLOSE_MESSAGE_MAX = 123L;
   public static final int CLOSE_NO_STATUS_CODE = 1005;
   public static final WebSocketProtocol INSTANCE = new WebSocketProtocol();
   public static final int OPCODE_BINARY = 2;
   public static final int OPCODE_CONTINUATION = 0;
   public static final int OPCODE_CONTROL_CLOSE = 8;
   public static final int OPCODE_CONTROL_PING = 9;
   public static final int OPCODE_CONTROL_PONG = 10;
   public static final int OPCODE_FLAG_CONTROL = 8;
   public static final int OPCODE_TEXT = 1;
   public static final long PAYLOAD_BYTE_MAX = 125L;
   public static final int PAYLOAD_LONG = 127;
   public static final int PAYLOAD_SHORT = 126;
   public static final long PAYLOAD_SHORT_MAX = 65535L;

   private WebSocketProtocol() {
   }

   public final String acceptHeader(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      ByteString.Companion var2 = ByteString.Companion;
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      var3.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
      return var2.encodeUtf8(var3.toString()).sha1().base64();
   }

   public final String closeCodeExceptionMessage(int var1) {
      StringBuilder var2;
      String var3;
      if (var1 >= 1000 && var1 < 5000) {
         if ((1004 > var1 || 1006 < var1) && (1015 > var1 || 2999 < var1)) {
            var3 = null;
         } else {
            var2 = new StringBuilder();
            var2.append("Code ");
            var2.append(var1);
            var2.append(" is reserved and may not be used.");
            var3 = var2.toString();
         }
      } else {
         var2 = new StringBuilder();
         var2.append("Code must be in range [1000,5000): ");
         var2.append(var1);
         var3 = var2.toString();
      }

      return var3;
   }

   public final void toggleMask(Buffer.UnsafeCursor var1, byte[] var2) {
      Intrinsics.checkParameterIsNotNull(var1, "cursor");
      Intrinsics.checkParameterIsNotNull(var2, "key");
      int var3 = var2.length;
      int var4 = 0;

      do {
         byte[] var5 = var1.data;
         int var6 = var1.start;
         int var7 = var1.end;
         int var8 = var4;
         if (var5 != null) {
            while(true) {
               var8 = var4;
               if (var6 >= var7) {
                  break;
               }

               var4 %= var3;
               var5[var6] = (byte)((byte)(var5[var6] ^ var2[var4]));
               ++var6;
               ++var4;
            }
         }

         var4 = var8;
      } while(var1.next() != -1);

   }

   public final void validateCloseCode(int var1) {
      String var2 = this.closeCodeExceptionMessage(var1);
      boolean var3;
      if (var2 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         throw (Throwable)(new IllegalArgumentException(var2.toString()));
      }
   }
}
