package okhttp3.internal.platform.android;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"},
   d2 = {"androidLevel", "", "Ljava/util/logging/LogRecord;", "getAndroidLevel", "(Ljava/util/logging/LogRecord;)I", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class AndroidLogKt {
   // $FF: synthetic method
   public static final int access$getAndroidLevel$p(LogRecord var0) {
      return getAndroidLevel(var0);
   }

   private static final int getAndroidLevel(LogRecord var0) {
      byte var1;
      if (var0.getLevel().intValue() > Level.INFO.intValue()) {
         var1 = 5;
      } else if (var0.getLevel().intValue() == Level.INFO.intValue()) {
         var1 = 4;
      } else {
         var1 = 3;
      }

      return var1;
   }
}
