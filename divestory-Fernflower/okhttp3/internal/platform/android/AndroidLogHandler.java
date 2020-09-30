package okhttp3.internal.platform.android;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"},
   d2 = {"Lokhttp3/internal/platform/android/AndroidLogHandler;", "Ljava/util/logging/Handler;", "()V", "close", "", "flush", "publish", "record", "Ljava/util/logging/LogRecord;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class AndroidLogHandler extends Handler {
   public static final AndroidLogHandler INSTANCE = new AndroidLogHandler();

   private AndroidLogHandler() {
   }

   public void close() {
   }

   public void flush() {
   }

   public void publish(LogRecord var1) {
      Intrinsics.checkParameterIsNotNull(var1, "record");
      AndroidLog var2 = AndroidLog.INSTANCE;
      String var3 = var1.getLoggerName();
      Intrinsics.checkExpressionValueIsNotNull(var3, "record.loggerName");
      int var4 = AndroidLogKt.access$getAndroidLevel$p(var1);
      String var5 = var1.getMessage();
      Intrinsics.checkExpressionValueIsNotNull(var5, "record.message");
      var2.androidLog$okhttp(var3, var4, var5, var1.getThrown());
   }
}
