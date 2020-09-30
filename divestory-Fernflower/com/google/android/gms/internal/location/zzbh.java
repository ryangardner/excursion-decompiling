package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public final class zzbh extends AbstractSafeParcelable implements Geofence {
   public static final Creator<zzbh> CREATOR = new zzbi();
   private final String zzad;
   private final int zzae;
   private final short zzag;
   private final double zzah;
   private final double zzai;
   private final float zzaj;
   private final int zzak;
   private final int zzal;
   private final long zzdo;

   public zzbh(String var1, int var2, short var3, double var4, double var6, float var8, long var9, int var11, int var12) {
      if (var1 != null && var1.length() <= 100) {
         StringBuilder var14;
         if (var8 > 0.0F) {
            if (var4 <= 90.0D && var4 >= -90.0D) {
               if (var6 <= 180.0D && var6 >= -180.0D) {
                  int var13 = var2 & 7;
                  if (var13 != 0) {
                     this.zzag = (short)var3;
                     this.zzad = var1;
                     this.zzah = var4;
                     this.zzai = var6;
                     this.zzaj = var8;
                     this.zzdo = var9;
                     this.zzae = var13;
                     this.zzak = var11;
                     this.zzal = var12;
                  } else {
                     var14 = new StringBuilder(46);
                     var14.append("No supported transition specified: ");
                     var14.append(var2);
                     throw new IllegalArgumentException(var14.toString());
                  }
               } else {
                  var14 = new StringBuilder(43);
                  var14.append("invalid longitude: ");
                  var14.append(var6);
                  throw new IllegalArgumentException(var14.toString());
               }
            } else {
               var14 = new StringBuilder(42);
               var14.append("invalid latitude: ");
               var14.append(var4);
               throw new IllegalArgumentException(var14.toString());
            }
         } else {
            var14 = new StringBuilder(31);
            var14.append("invalid radius: ");
            var14.append(var8);
            throw new IllegalArgumentException(var14.toString());
         }
      } else {
         var1 = String.valueOf(var1);
         if (var1.length() != 0) {
            var1 = "requestId is null or too long: ".concat(var1);
         } else {
            var1 = new String("requestId is null or too long: ");
         }

         throw new IllegalArgumentException(var1);
      }
   }

   public static zzbh zza(byte[] var0) {
      Parcel var1 = Parcel.obtain();
      var1.unmarshall(var0, 0, var0.length);
      var1.setDataPosition(0);
      zzbh var2 = (zzbh)CREATOR.createFromParcel(var1);
      var1.recycle();
      return var2;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof zzbh)) {
         return false;
      } else {
         zzbh var2 = (zzbh)var1;
         if (this.zzaj != var2.zzaj) {
            return false;
         } else if (this.zzah != var2.zzah) {
            return false;
         } else if (this.zzai != var2.zzai) {
            return false;
         } else {
            return this.zzag == var2.zzag;
         }
      }
   }

   public final String getRequestId() {
      return this.zzad;
   }

   public final int hashCode() {
      long var1 = Double.doubleToLongBits(this.zzah);
      int var3 = (int)(var1 ^ var1 >>> 32);
      var1 = Double.doubleToLongBits(this.zzai);
      return ((((var3 + 31) * 31 + (int)(var1 ^ var1 >>> 32)) * 31 + Float.floatToIntBits(this.zzaj)) * 31 + this.zzag) * 31 + this.zzae;
   }

   public final String toString() {
      Locale var1 = Locale.US;
      String var2;
      if (this.zzag != 1) {
         var2 = null;
      } else {
         var2 = "CIRCLE";
      }

      return String.format(var1, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", var2, this.zzad.replaceAll("\\p{C}", "?"), this.zzae, this.zzah, this.zzai, this.zzaj, this.zzak / 1000, this.zzal, this.zzdo);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.getRequestId(), false);
      SafeParcelWriter.writeLong(var1, 2, this.zzdo);
      SafeParcelWriter.writeShort(var1, 3, this.zzag);
      SafeParcelWriter.writeDouble(var1, 4, this.zzah);
      SafeParcelWriter.writeDouble(var1, 5, this.zzai);
      SafeParcelWriter.writeFloat(var1, 6, this.zzaj);
      SafeParcelWriter.writeInt(var1, 7, this.zzae);
      SafeParcelWriter.writeInt(var1, 8, this.zzak);
      SafeParcelWriter.writeInt(var1, 9, this.zzal);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
