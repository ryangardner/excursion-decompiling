package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zac implements Creator<DataHolder> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String[] var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      int var6 = 0;
      int var7 = 0;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 3) {
                  if (var9 != 4) {
                     if (var9 != 1000) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var6 = SafeParcelReader.readInt(var1, var8);
                     }
                  } else {
                     var5 = SafeParcelReader.createBundle(var1, var8);
                  }
               } else {
                  var7 = SafeParcelReader.readInt(var1, var8);
               }
            } else {
               var4 = (CursorWindow[])SafeParcelReader.createTypedArray(var1, var8, CursorWindow.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.createStringArray(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      DataHolder var10 = new DataHolder(var6, var3, (CursorWindow[])var4, var7, (Bundle)var5);
      var10.zaa();
      return var10;
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new DataHolder[var1];
   }
}
