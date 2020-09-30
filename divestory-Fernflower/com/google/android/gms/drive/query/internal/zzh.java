package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzh implements Creator<FilterHolder> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zzb var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      Object var8 = var3;
      Object var9 = var3;
      Object var10 = var3;
      Object var11 = var3;

      while(var1.dataPosition() < var2) {
         int var12 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var12)) {
         case 1:
            var3 = (zzb)SafeParcelReader.createParcelable(var1, var12, zzb.CREATOR);
            break;
         case 2:
            var4 = (zzd)SafeParcelReader.createParcelable(var1, var12, zzd.CREATOR);
            break;
         case 3:
            var5 = (zzr)SafeParcelReader.createParcelable(var1, var12, zzr.CREATOR);
            break;
         case 4:
            var6 = (zzv)SafeParcelReader.createParcelable(var1, var12, zzv.CREATOR);
            break;
         case 5:
            var7 = (zzp)SafeParcelReader.createParcelable(var1, var12, zzp.CREATOR);
            break;
         case 6:
            var8 = (zzt)SafeParcelReader.createParcelable(var1, var12, zzt.CREATOR);
            break;
         case 7:
            var9 = (zzn)SafeParcelReader.createParcelable(var1, var12, zzn.CREATOR);
            break;
         case 8:
            var10 = (zzl)SafeParcelReader.createParcelable(var1, var12, zzl.CREATOR);
            break;
         case 9:
            var11 = (zzz)SafeParcelReader.createParcelable(var1, var12, zzz.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var12);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new FilterHolder(var3, (zzd)var4, (zzr)var5, (zzv)var6, (zzp)var7, (zzt)var8, (zzn)var9, (zzl)var10, (zzz)var11);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FilterHolder[var1];
   }
}
