package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;

public class zzl<T extends Parcelable> extends com.google.android.gms.drive.metadata.zzb<T> {
   public zzl(String var1, Collection<String> var2, Collection<String> var3, int var4) {
      super(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   protected final void zza(Bundle var1, Object var2) {
      Collection var4 = (Collection)var2;
      String var3 = this.getName();
      ArrayList var5;
      if (var4 instanceof ArrayList) {
         var5 = (ArrayList)var4;
      } else {
         var5 = new ArrayList(var4);
      }

      var1.putParcelableArrayList(var3, var5);
   }

   // $FF: synthetic method
   protected Object zzb(Bundle var1) {
      return this.zzc(var1);
   }

   protected Collection<T> zzc(Bundle var1) {
      return var1.getParcelableArrayList(this.getName());
   }
}
