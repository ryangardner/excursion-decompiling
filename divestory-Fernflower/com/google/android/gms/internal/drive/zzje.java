package com.google.android.gms.internal.drive;

import java.util.Comparator;

final class zzje implements Comparator<zzjc> {
   // $FF: synthetic method
   public final int compare(Object var1, Object var2) {
      zzjc var6 = (zzjc)var1;
      zzjc var7 = (zzjc)var2;
      zzjj var3 = (zzjj)var6.iterator();
      zzjj var4 = (zzjj)var7.iterator();

      while(var3.hasNext() && var4.hasNext()) {
         int var5 = Integer.compare(zzjc.zzb(var3.nextByte()), zzjc.zzb(var4.nextByte()));
         if (var5 != 0) {
            return var5;
         }
      }

      return Integer.compare(var6.size(), var7.size());
   }
}
