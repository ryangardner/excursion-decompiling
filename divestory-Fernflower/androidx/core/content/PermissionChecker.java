package androidx.core.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import androidx.core.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
   public static final int PERMISSION_DENIED = -1;
   public static final int PERMISSION_DENIED_APP_OP = -2;
   public static final int PERMISSION_GRANTED = 0;

   private PermissionChecker() {
   }

   public static int checkCallingOrSelfPermission(Context var0, String var1) {
      String var2;
      if (Binder.getCallingPid() == Process.myPid()) {
         var2 = var0.getPackageName();
      } else {
         var2 = null;
      }

      return checkPermission(var0, var1, Binder.getCallingPid(), Binder.getCallingUid(), var2);
   }

   public static int checkCallingPermission(Context var0, String var1, String var2) {
      return Binder.getCallingPid() == Process.myPid() ? -1 : checkPermission(var0, var1, Binder.getCallingPid(), Binder.getCallingUid(), var2);
   }

   public static int checkPermission(Context var0, String var1, int var2, int var3, String var4) {
      if (var0.checkPermission(var1, var2, var3) == -1) {
         return -1;
      } else {
         String var5 = AppOpsManagerCompat.permissionToOp(var1);
         if (var5 == null) {
            return 0;
         } else {
            var1 = var4;
            if (var4 == null) {
               String[] var6 = var0.getPackageManager().getPackagesForUid(var3);
               if (var6 == null || var6.length <= 0) {
                  return -1;
               }

               var1 = var6[0];
            }

            return AppOpsManagerCompat.noteProxyOpNoThrow(var0, var5, var1) != 0 ? -2 : 0;
         }
      }
   }

   public static int checkSelfPermission(Context var0, String var1) {
      return checkPermission(var0, var1, Process.myPid(), Process.myUid(), var0.getPackageName());
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface PermissionResult {
   }
}
