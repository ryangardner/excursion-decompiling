package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzae extends AbstractSafeParcelable {
   public static final Creator<zzae> CREATOR = new zzaf();
   private final String zzbd;
   private final String zzbe;
   private final String zzbf;

   zzae(String var1, String var2, String var3) {
      this.zzbf = var1;
      this.zzbd = var2;
      this.zzbe = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.zzbd, false);
      SafeParcelWriter.writeString(var1, 2, this.zzbe, false);
      SafeParcelWriter.writeString(var1, 5, this.zzbf, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
