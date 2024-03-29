package androidx.core.app;

import android.app.Service;
import android.os.Build.VERSION;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ServiceCompat {
   public static final int START_STICKY = 1;
   public static final int STOP_FOREGROUND_DETACH = 2;
   public static final int STOP_FOREGROUND_REMOVE = 1;

   private ServiceCompat() {
   }

   public static void stopForeground(Service var0, int var1) {
      if (VERSION.SDK_INT >= 24) {
         var0.stopForeground(var1);
      } else {
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         var0.stopForeground(var2);
      }

   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface StopForegroundFlags {
   }
}
