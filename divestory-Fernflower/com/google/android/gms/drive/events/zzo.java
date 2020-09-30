package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzo extends com.google.android.gms.drive.zzu implements DriveEvent {
   public static final Creator<zzo> CREATOR = new zzp();
   private final DataHolder zzav;
   private final boolean zzcq;
   private final int zzcr;

   public zzo(DataHolder var1, boolean var2, int var3) {
      this.zzav = var1;
      this.zzcq = var2;
      this.zzcr = var3;
   }

   public final int getType() {
      return 3;
   }

   public final void zza(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzav, var2, false);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzcq);
      SafeParcelWriter.writeInt(var1, 4, this.zzcr);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final boolean zzaa() {
      return this.zzcq;
   }

   public final int zzab() {
      return this.zzcr;
   }

   public final DataHolder zzz() {
      return this.zzav;
   }
}
