package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class FavaDiagnosticsEntity extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<FavaDiagnosticsEntity> CREATOR = new zaa();
   private final int zaa;
   private final String zab;
   private final int zac;

   public FavaDiagnosticsEntity(int var1, String var2, int var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   public FavaDiagnosticsEntity(String var1, int var2) {
      this.zaa = 1;
      this.zab = var1;
      this.zac = var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeString(var1, 2, this.zab, false);
      SafeParcelWriter.writeInt(var1, 3, this.zac);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
