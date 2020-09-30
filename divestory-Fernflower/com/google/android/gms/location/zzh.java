package com.google.android.gms.location;

import java.util.Comparator;

final class zzh implements Comparator<DetectedActivity> {
   // $FF: synthetic method
   public final int compare(Object var1, Object var2) {
      DetectedActivity var4 = (DetectedActivity)var1;
      DetectedActivity var5 = (DetectedActivity)var2;
      int var3 = Integer.valueOf(var5.getConfidence()).compareTo(var4.getConfidence());
      return var3 == 0 ? Integer.valueOf(var4.getType()).compareTo(var5.getType()) : var3;
   }
}
