package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
public abstract class StatsEvent extends AbstractSafeParcelable implements ReflectedParcelable {
   public String toString() {
      long var1 = this.zza();
      int var3 = this.zzb();
      long var4 = this.zzc();
      String var6 = this.zzd();
      StringBuilder var7 = new StringBuilder(String.valueOf(var6).length() + 53);
      var7.append(var1);
      var7.append("\t");
      var7.append(var3);
      var7.append("\t");
      var7.append(var4);
      var7.append(var6);
      return var7.toString();
   }

   public abstract long zza();

   public abstract int zzb();

   public abstract long zzc();

   public abstract String zzd();

   public interface Types {
      int EVENT_TYPE_ACQUIRE_WAKE_LOCK = 7;
      int EVENT_TYPE_RELEASE_WAKE_LOCK = 8;
   }
}
