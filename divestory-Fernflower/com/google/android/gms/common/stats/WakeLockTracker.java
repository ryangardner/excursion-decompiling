package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import java.util.List;

@Deprecated
public class WakeLockTracker {
   private static WakeLockTracker zza = new WakeLockTracker();
   private static boolean zzb = false;

   public static WakeLockTracker getInstance() {
      return zza;
   }

   public void registerAcquireEvent(Context var1, Intent var2, String var3, String var4, String var5, int var6, String var7) {
   }

   public void registerDeadlineEvent(Context var1, String var2, String var3, String var4, int var5, List<String> var6, boolean var7, long var8) {
   }

   public void registerEvent(Context var1, String var2, int var3, String var4, String var5, String var6, int var7, List<String> var8) {
   }

   public void registerEvent(Context var1, String var2, int var3, String var4, String var5, String var6, int var7, List<String> var8, long var9) {
   }

   public void registerReleaseEvent(Context var1, Intent var2) {
   }
}
