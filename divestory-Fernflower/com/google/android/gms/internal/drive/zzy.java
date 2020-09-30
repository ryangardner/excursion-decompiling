package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzy extends AbstractSafeParcelable {
   public static final Creator<zzy> CREATOR = new zzz();
   private final MetadataBundle zzdn;
   private final DriveId zzdp;

   public zzy(DriveId var1, MetadataBundle var2) {
      this.zzdp = (DriveId)Preconditions.checkNotNull(var1);
      this.zzdn = (MetadataBundle)Preconditions.checkNotNull(var2);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdp, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzdn, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
