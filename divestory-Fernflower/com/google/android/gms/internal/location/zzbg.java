package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzbg implements Creator<zzbf> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zzbd var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      int var8 = 1;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var9)) {
         case 1:
            var8 = SafeParcelReader.readInt(var1, var9);
            break;
         case 2:
            var3 = (zzbd)SafeParcelReader.createParcelable(var1, var9, zzbd.CREATOR);
            break;
         case 3:
            var4 = SafeParcelReader.readIBinder(var1, var9);
            break;
         case 4:
            var5 = (PendingIntent)SafeParcelReader.createParcelable(var1, var9, PendingIntent.CREATOR);
            break;
         case 5:
            var6 = SafeParcelReader.readIBinder(var1, var9);
            break;
         case 6:
            var7 = SafeParcelReader.readIBinder(var1, var9);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzbf(var8, var3, (IBinder)var4, (PendingIntent)var5, (IBinder)var6, (IBinder)var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbf[var1];
   }
}
