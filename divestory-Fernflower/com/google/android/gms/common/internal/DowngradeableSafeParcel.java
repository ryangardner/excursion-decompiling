package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
   private static final Object zza = new Object();
   private static ClassLoader zzb = null;
   private static Integer zzc = null;
   private boolean zzd = false;

   protected static boolean canUnparcelSafely(String var0) {
      zza();
      return true;
   }

   protected static Integer getUnparcelClientVersion() {
      // $FF: Couldn't be decompiled
   }

   private static ClassLoader zza() {
      // $FF: Couldn't be decompiled
   }

   protected abstract boolean prepareForClientVersion(int var1);

   public void setShouldDowngrade(boolean var1) {
      this.zzd = var1;
   }

   protected boolean shouldDowngrade() {
      return this.zzd;
   }
}
