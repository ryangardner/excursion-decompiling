package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zae implements Creator<WebImage> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      Uri var4 = null;
      int var5 = 0;
      int var6 = 0;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var6 = SafeParcelReader.readInt(var1, var7);
                  }
               } else {
                  var5 = SafeParcelReader.readInt(var1, var7);
               }
            } else {
               var4 = (Uri)SafeParcelReader.createParcelable(var1, var7, Uri.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new WebImage(var3, var4, var5, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new WebImage[var1];
   }
}
