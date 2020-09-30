package com.google.android.gms.location;

import java.util.Comparator;

final class zze implements Comparator<ActivityTransition> {
   // $FF: synthetic method
   public final int compare(Object var1, Object var2) {
      ActivityTransition var5 = (ActivityTransition)var1;
      ActivityTransition var6 = (ActivityTransition)var2;
      int var3 = var5.getActivityType();
      int var4 = var6.getActivityType();
      if (var3 != var4) {
         return var3 < var4 ? -1 : 1;
      } else {
         var3 = var5.getTransitionType();
         var4 = var6.getTransitionType();
         if (var3 == var4) {
            return 0;
         } else {
            return var3 < var4 ? -1 : 1;
         }
      }
   }
}
