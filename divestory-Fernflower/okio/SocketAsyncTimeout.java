package okio;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lokio/SocketAsyncTimeout;", "Lokio/AsyncTimeout;", "socket", "Ljava/net/Socket;", "(Ljava/net/Socket;)V", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
final class SocketAsyncTimeout extends AsyncTimeout {
   private final Logger logger;
   private final Socket socket;

   public SocketAsyncTimeout(Socket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      super();
      this.socket = var1;
      this.logger = Logger.getLogger("okio.Okio");
   }

   protected IOException newTimeoutException(IOException var1) {
      SocketTimeoutException var2 = new SocketTimeoutException("timeout");
      if (var1 != null) {
         var2.initCause((Throwable)var1);
      }

      return (IOException)var2;
   }

   protected void timedOut() {
      try {
         this.socket.close();
      } catch (Exception var5) {
         Logger var8 = this.logger;
         Level var9 = Level.WARNING;
         StringBuilder var7 = new StringBuilder();
         var7.append("Failed to close timed out socket ");
         var7.append(this.socket);
         var8.log(var9, var7.toString(), (Throwable)var5);
      } catch (AssertionError var6) {
         if (!Okio.isAndroidGetsocknameError(var6)) {
            throw (Throwable)var6;
         }

         Logger var2 = this.logger;
         Level var3 = Level.WARNING;
         StringBuilder var4 = new StringBuilder();
         var4.append("Failed to close timed out socket ");
         var4.append(this.socket);
         var2.log(var3, var4.toString(), (Throwable)var6);
      }

   }
}
