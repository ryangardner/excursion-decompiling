package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzad extends AbstractSafeParcelable {
   public static final Creator<zzad> CREATOR = new zzae();

   public final void writeToParcel(Parcel var1, int var2) {
      SafeParcelWriter.finishObjectHeader(var1, SafeParcelWriter.beginObjectHeader(var1));
   }
}
