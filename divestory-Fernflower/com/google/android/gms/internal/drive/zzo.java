package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;

public final class zzo extends AbstractSafeParcelable {
   public static final Creator<zzo> CREATOR = new zzp();
   private final Contents zzdf;
   private final int zzdh;
   private final Boolean zzdj;

   public zzo(int var1, boolean var2) {
      this((Contents)null, false, var1);
   }

   public zzo(Contents var1, Boolean var2, int var3) {
      this.zzdf = var1;
      this.zzdj = var2;
      this.zzdh = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdf, var2, false);
      SafeParcelWriter.writeBooleanObject(var1, 3, this.zzdj, false);
      SafeParcelWriter.writeInt(var1, 4, this.zzdh);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
