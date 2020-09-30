package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public final class zzbd extends AbstractSafeParcelable {
   public static final Creator<zzbd> CREATOR = new zzbe();
   static final List<ClientIdentity> zzcd = Collections.emptyList();
   private String moduleId;
   private String tag;
   private LocationRequest zzdg;
   private boolean zzdh;
   private boolean zzdi;
   private boolean zzdj;
   private boolean zzdk = true;
   private List<ClientIdentity> zzm;

   zzbd(LocationRequest var1, List<ClientIdentity> var2, String var3, boolean var4, boolean var5, boolean var6, String var7) {
      this.zzdg = var1;
      this.zzm = var2;
      this.tag = var3;
      this.zzdh = var4;
      this.zzdi = var5;
      this.zzdj = var6;
      this.moduleId = var7;
   }

   @Deprecated
   public static zzbd zza(LocationRequest var0) {
      return new zzbd(var0, zzcd, (String)null, false, false, false, (String)null);
   }

   public final boolean equals(Object var1) {
      if (!(var1 instanceof zzbd)) {
         return false;
      } else {
         zzbd var2 = (zzbd)var1;
         return Objects.equal(this.zzdg, var2.zzdg) && Objects.equal(this.zzm, var2.zzm) && Objects.equal(this.tag, var2.tag) && this.zzdh == var2.zzdh && this.zzdi == var2.zzdi && this.zzdj == var2.zzdj && Objects.equal(this.moduleId, var2.moduleId);
      }
   }

   public final int hashCode() {
      return this.zzdg.hashCode();
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.zzdg);
      if (this.tag != null) {
         var1.append(" tag=");
         var1.append(this.tag);
      }

      if (this.moduleId != null) {
         var1.append(" moduleId=");
         var1.append(this.moduleId);
      }

      var1.append(" hideAppOps=");
      var1.append(this.zzdh);
      var1.append(" clients=");
      var1.append(this.zzm);
      var1.append(" forceCoarseLocation=");
      var1.append(this.zzdi);
      if (this.zzdj) {
         var1.append(" exemptFromBackgroundThrottle");
      }

      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzdg, var2, false);
      SafeParcelWriter.writeTypedList(var1, 5, this.zzm, false);
      SafeParcelWriter.writeString(var1, 6, this.tag, false);
      SafeParcelWriter.writeBoolean(var1, 7, this.zzdh);
      SafeParcelWriter.writeBoolean(var1, 8, this.zzdi);
      SafeParcelWriter.writeBoolean(var1, 9, this.zzdj);
      SafeParcelWriter.writeString(var1, 10, this.moduleId, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
