package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\b\u0086\u0001\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0015B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0016"},
   d2 = {"Lokhttp3/internal/http2/ErrorCode;", "", "httpCode", "", "(Ljava/lang/String;II)V", "getHttpCode", "()I", "NO_ERROR", "PROTOCOL_ERROR", "INTERNAL_ERROR", "FLOW_CONTROL_ERROR", "SETTINGS_TIMEOUT", "STREAM_CLOSED", "FRAME_SIZE_ERROR", "REFUSED_STREAM", "CANCEL", "COMPRESSION_ERROR", "CONNECT_ERROR", "ENHANCE_YOUR_CALM", "INADEQUATE_SECURITY", "HTTP_1_1_REQUIRED", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public enum ErrorCode {
   CANCEL,
   COMPRESSION_ERROR,
   CONNECT_ERROR;

   public static final ErrorCode.Companion Companion;
   ENHANCE_YOUR_CALM,
   FLOW_CONTROL_ERROR,
   FRAME_SIZE_ERROR,
   HTTP_1_1_REQUIRED,
   INADEQUATE_SECURITY,
   INTERNAL_ERROR,
   NO_ERROR,
   PROTOCOL_ERROR,
   REFUSED_STREAM,
   SETTINGS_TIMEOUT,
   STREAM_CLOSED;

   private final int httpCode;

   static {
      ErrorCode var0 = new ErrorCode("NO_ERROR", 0, 0);
      NO_ERROR = var0;
      ErrorCode var1 = new ErrorCode("PROTOCOL_ERROR", 1, 1);
      PROTOCOL_ERROR = var1;
      ErrorCode var2 = new ErrorCode("INTERNAL_ERROR", 2, 2);
      INTERNAL_ERROR = var2;
      ErrorCode var3 = new ErrorCode("FLOW_CONTROL_ERROR", 3, 3);
      FLOW_CONTROL_ERROR = var3;
      ErrorCode var4 = new ErrorCode("SETTINGS_TIMEOUT", 4, 4);
      SETTINGS_TIMEOUT = var4;
      ErrorCode var5 = new ErrorCode("STREAM_CLOSED", 5, 5);
      STREAM_CLOSED = var5;
      ErrorCode var6 = new ErrorCode("FRAME_SIZE_ERROR", 6, 6);
      FRAME_SIZE_ERROR = var6;
      ErrorCode var7 = new ErrorCode("REFUSED_STREAM", 7, 7);
      REFUSED_STREAM = var7;
      ErrorCode var8 = new ErrorCode("CANCEL", 8, 8);
      CANCEL = var8;
      ErrorCode var9 = new ErrorCode("COMPRESSION_ERROR", 9, 9);
      COMPRESSION_ERROR = var9;
      ErrorCode var10 = new ErrorCode("CONNECT_ERROR", 10, 10);
      CONNECT_ERROR = var10;
      ErrorCode var11 = new ErrorCode("ENHANCE_YOUR_CALM", 11, 11);
      ENHANCE_YOUR_CALM = var11;
      ErrorCode var12 = new ErrorCode("INADEQUATE_SECURITY", 12, 12);
      INADEQUATE_SECURITY = var12;
      ErrorCode var13 = new ErrorCode("HTTP_1_1_REQUIRED", 13, 13);
      HTTP_1_1_REQUIRED = var13;
      Companion = new ErrorCode.Companion((DefaultConstructorMarker)null);
   }

   private ErrorCode(int var3) {
      this.httpCode = var3;
   }

   public final int getHttpCode() {
      return this.httpCode;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/http2/ErrorCode$Companion;", "", "()V", "fromHttp2", "Lokhttp3/internal/http2/ErrorCode;", "code", "", "okhttp"},
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

      public final ErrorCode fromHttp2(int var1) {
         ErrorCode[] var2 = ErrorCode.values();
         int var3 = var2.length;
         int var4 = 0;

         ErrorCode var5;
         while(true) {
            if (var4 >= var3) {
               var5 = null;
               break;
            }

            var5 = var2[var4];
            boolean var6;
            if (var5.getHttpCode() == var1) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               break;
            }

            ++var4;
         }

         return var5;
      }
   }
}
