package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

public final class zzgz extends AbstractSafeParcelable {
   public static final Creator<zzgz> CREATOR = new zzha();
   private final List<String> zziu;

   public zzgz(List<String> var1) {
      this.zziu = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringList(var1, 2, this.zziu, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
