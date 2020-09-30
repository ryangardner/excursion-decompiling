package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;

@Deprecated
public class StatsUtils {
   public static String getEventKey(Context var0, Intent var1) {
      long var2 = (long)System.identityHashCode(var0);
      return String.valueOf((long)System.identityHashCode(var1) | var2 << 32);
   }

   public static String getEventKey(WakeLock var0, String var1) {
      String var2 = String.valueOf(String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(var0)));
      String var3 = var1;
      if (TextUtils.isEmpty(var1)) {
         var3 = "";
      }

      var3 = String.valueOf(var3);
      return var3.length() != 0 ? var2.concat(var3) : new String(var2);
   }
}
