package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.List;

public final class zzm extends AbstractSafeParcelable {
   public static final Creator<zzm> CREATOR = new zzn();
   static final List<ClientIdentity> zzcd = Collections.emptyList();
   static final com.google.android.gms.location.zzj zzce = new com.google.android.gms.location.zzj();
   private String tag;
   private com.google.android.gms.location.zzj zzcf;
   private List<ClientIdentity> zzm;

   zzm(com.google.android.gms.location.zzj var1, List<ClientIdentity> var2, String var3) {
      this.zzcf = var1;
      this.zzm = var2;
      this.tag = var3;
   }

   public final boolean equals(Object var1) {
      if (!(var1 instanceof zzm)) {
         return false;
      } else {
         zzm var2 = (zzm)var1;
         return Objects.equal(this.zzcf, var2.zzcf) && Objects.equal(this.zzm, var2.zzm) && Objects.equal(this.tag, var2.tag);
      }
   }

   public final int hashCode() {
      return this.zzcf.hashCode();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzcf, var2, false);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzm, false);
      SafeParcelWriter.writeString(var1, 3, this.tag, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
