package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzn implements Creator<zzm> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DriveId var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      boolean var7 = false;
      int var8 = 0;
      int var9 = 0;
      boolean var10 = false;
      boolean var11 = true;

      while(var1.dataPosition() < var2) {
         int var12 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var12)) {
         case 2:
            var3 = (DriveId)SafeParcelReader.createParcelable(var1, var12, DriveId.CREATOR);
            break;
         case 3:
            var4 = (MetadataBundle)SafeParcelReader.createParcelable(var1, var12, MetadataBundle.CREATOR);
            break;
         case 4:
            var5 = (Contents)SafeParcelReader.createParcelable(var1, var12, Contents.CREATOR);
            break;
         case 5:
            var7 = SafeParcelReader.readBoolean(var1, var12);
            break;
         case 6:
            var6 = SafeParcelReader.createString(var1, var12);
            break;
         case 7:
            var8 = SafeParcelReader.readInt(var1, var12);
            break;
         case 8:
            var9 = SafeParcelReader.readInt(var1, var12);
            break;
         case 9:
            var10 = SafeParcelReader.readBoolean(var1, var12);
            break;
         case 10:
            var11 = SafeParcelReader.readBoolean(var1, var12);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var12);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzm(var3, (MetadataBundle)var4, (Contents)var5, var7, (String)var6, var8, var9, var10, var11);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzm[var1];
   }
}
