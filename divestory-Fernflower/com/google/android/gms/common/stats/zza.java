package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zza implements Creator<WakeLockEvent> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = var3;
      long var7 = var3;
      String var9 = null;
      Object var10 = var9;
      String var11 = var9;
      String var12 = var9;
      String var13 = var9;
      String var14 = var9;
      int var15 = 0;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      float var19 = 0.0F;
      boolean var20 = false;

      while(var1.dataPosition() < var2) {
         int var21 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var21)) {
         case 1:
            var15 = SafeParcelReader.readInt(var1, var21);
            break;
         case 2:
            var3 = SafeParcelReader.readLong(var1, var21);
            break;
         case 3:
         case 7:
         case 9:
         default:
            SafeParcelReader.skipUnknownField(var1, var21);
            break;
         case 4:
            var9 = SafeParcelReader.createString(var1, var21);
            break;
         case 5:
            var17 = SafeParcelReader.readInt(var1, var21);
            break;
         case 6:
            var10 = SafeParcelReader.createStringList(var1, var21);
            break;
         case 8:
            var5 = SafeParcelReader.readLong(var1, var21);
            break;
         case 10:
            var12 = SafeParcelReader.createString(var1, var21);
            break;
         case 11:
            var16 = SafeParcelReader.readInt(var1, var21);
            break;
         case 12:
            var11 = SafeParcelReader.createString(var1, var21);
            break;
         case 13:
            var13 = SafeParcelReader.createString(var1, var21);
            break;
         case 14:
            var18 = SafeParcelReader.readInt(var1, var21);
            break;
         case 15:
            var19 = SafeParcelReader.readFloat(var1, var21);
            break;
         case 16:
            var7 = SafeParcelReader.readLong(var1, var21);
            break;
         case 17:
            var14 = SafeParcelReader.createString(var1, var21);
            break;
         case 18:
            var20 = SafeParcelReader.readBoolean(var1, var21);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new WakeLockEvent(var15, var3, var16, var9, var17, (List)var10, var11, var5, var18, var12, var13, var19, var7, var14, var20);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new WakeLockEvent[var1];
   }
}
