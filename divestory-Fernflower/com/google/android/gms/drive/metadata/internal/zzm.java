package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.ReflectedParcelable;
import java.util.Collection;

public abstract class zzm<T extends ReflectedParcelable> extends com.google.android.gms.drive.metadata.zza<T> {
   public zzm(String var1, Collection<String> var2, Collection<String> var3, int var4) {
      super(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   protected final void zza(Bundle var1, Object var2) {
      ReflectedParcelable var3 = (ReflectedParcelable)var2;
      var1.putParcelable(this.getName(), var3);
   }

   // $FF: synthetic method
   protected final Object zzb(Bundle var1) {
      return (ReflectedParcelable)var1.getParcelable(this.getName());
   }
}
