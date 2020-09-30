package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzx implements Creator<zzw> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Integer var3 = null;
      DriveId var4 = null;
      Object var5 = var4;
      Object var6 = var4;
      Object var7 = var4;
      boolean var8 = false;
      int var9 = 0;
      int var10 = 0;

      while(var1.dataPosition() < var2) {
         int var11 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var11)) {
         case 2:
            var4 = (DriveId)SafeParcelReader.createParcelable(var1, var11, DriveId.CREATOR);
            break;
         case 3:
            var5 = (MetadataBundle)SafeParcelReader.createParcelable(var1, var11, MetadataBundle.CREATOR);
            break;
         case 4:
            var6 = (Contents)SafeParcelReader.createParcelable(var1, var11, Contents.CREATOR);
            break;
         case 5:
            var3 = SafeParcelReader.readIntegerObject(var1, var11);
            break;
         case 6:
            var8 = SafeParcelReader.readBoolean(var1, var11);
            break;
         case 7:
            var7 = SafeParcelReader.createString(var1, var11);
            break;
         case 8:
            var9 = SafeParcelReader.readInt(var1, var11);
            break;
         case 9:
            var10 = SafeParcelReader.readInt(var1, var11);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var11);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzw(var4, (MetadataBundle)var5, (Contents)var6, var3, var8, (String)var7, var9, var10);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzw[var1];
   }
}
