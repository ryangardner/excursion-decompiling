package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzq implements Creator<zzp> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      MetadataBundle var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 1) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = (MetadataBundle)SafeParcelReader.createParcelable(var1, var4, MetadataBundle.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzp(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzp[var1];
   }
}
