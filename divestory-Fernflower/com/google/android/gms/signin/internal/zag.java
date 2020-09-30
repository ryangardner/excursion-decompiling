package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

public final class zag extends AbstractSafeParcelable implements Result {
   public static final Creator<zag> CREATOR = new zai();
   private final List<String> zaa;
   private final String zab;

   public zag(List<String> var1, String var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public final Status getStatus() {
      return this.zab != null ? Status.RESULT_SUCCESS : Status.RESULT_CANCELED;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringList(var1, 1, this.zaa, false);
      SafeParcelWriter.writeString(var1, 2, this.zab, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
