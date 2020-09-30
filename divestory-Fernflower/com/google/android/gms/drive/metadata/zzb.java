package com.google.android.gms.drive.metadata;

import com.google.android.gms.common.data.DataHolder;
import java.util.Collection;

public abstract class zzb<T> extends zza<Collection<T>> {
   protected zzb(String var1, Collection<String> var2, Collection<String> var3, int var4) {
      super(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   protected Object zzc(DataHolder var1, int var2, int var3) {
      return this.zzd(var1, var2, var3);
   }

   protected Collection<T> zzd(DataHolder var1, int var2, int var3) {
      throw new UnsupportedOperationException("Cannot read collections from a dataHolder.");
   }
}
