package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class Scope extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<Scope> CREATOR = new zza();
   private final int zza;
   private final String zzb;

   Scope(int var1, String var2) {
      Preconditions.checkNotEmpty(var2, "scopeUri must not be null or empty");
      this.zza = var1;
      this.zzb = var2;
   }

   public Scope(String var1) {
      this(1, var1);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof Scope) ? false : this.zzb.equals(((Scope)var1).zzb);
      }
   }

   public final String getScopeUri() {
      return this.zzb;
   }

   public final int hashCode() {
      return this.zzb.hashCode();
   }

   public final String toString() {
      return this.zzb;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zza);
      SafeParcelWriter.writeString(var1, 2, this.getScopeUri(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
