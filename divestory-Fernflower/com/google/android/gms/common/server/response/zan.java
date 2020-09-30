package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zan extends AbstractSafeParcelable {
   public static final Creator<zan> CREATOR = new zai();
   final String zaa;
   final FastJsonResponse.Field<?, ?> zab;
   private final int zac;

   zan(int var1, String var2, FastJsonResponse.Field<?, ?> var3) {
      this.zac = var1;
      this.zaa = var2;
      this.zab = var3;
   }

   zan(String var1, FastJsonResponse.Field<?, ?> var2) {
      this.zac = 1;
      this.zaa = var1;
      this.zab = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zac);
      SafeParcelWriter.writeString(var1, 2, this.zaa, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zab, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
