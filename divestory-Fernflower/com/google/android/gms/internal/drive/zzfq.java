package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;

public final class zzfq implements Creator<zzfp> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ChangeEvent var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      Object var8 = var3;
      int var9 = 0;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         int var11 = SafeParcelReader.getFieldId(var10);
         if (var11 != 2) {
            if (var11 != 3) {
               if (var11 != 5) {
                  if (var11 != 6) {
                     if (var11 != 7) {
                        if (var11 != 9) {
                           if (var11 != 10) {
                              SafeParcelReader.skipUnknownField(var1, var10);
                           } else {
                              var8 = (com.google.android.gms.drive.events.zzr)SafeParcelReader.createParcelable(var1, var10, com.google.android.gms.drive.events.zzr.CREATOR);
                           }
                        } else {
                           var7 = (com.google.android.gms.drive.events.zzv)SafeParcelReader.createParcelable(var1, var10, com.google.android.gms.drive.events.zzv.CREATOR);
                        }
                     } else {
                        var6 = (com.google.android.gms.drive.events.zzb)SafeParcelReader.createParcelable(var1, var10, com.google.android.gms.drive.events.zzb.CREATOR);
                     }
                  } else {
                     var5 = (com.google.android.gms.drive.events.zzo)SafeParcelReader.createParcelable(var1, var10, com.google.android.gms.drive.events.zzo.CREATOR);
                  }
               } else {
                  var4 = (CompletionEvent)SafeParcelReader.createParcelable(var1, var10, CompletionEvent.CREATOR);
               }
            } else {
               var3 = (ChangeEvent)SafeParcelReader.createParcelable(var1, var10, ChangeEvent.CREATOR);
            }
         } else {
            var9 = SafeParcelReader.readInt(var1, var10);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzfp(var9, var3, (CompletionEvent)var4, (com.google.android.gms.drive.events.zzo)var5, (com.google.android.gms.drive.events.zzb)var6, (com.google.android.gms.drive.events.zzv)var7, (com.google.android.gms.drive.events.zzr)var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzfp[var1];
   }
}
