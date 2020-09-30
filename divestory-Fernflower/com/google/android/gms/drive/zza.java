package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.drive.zzez;
import com.google.android.gms.internal.drive.zzkk;

public class zza extends AbstractSafeParcelable {
   public static final Creator<zza> CREATOR = new zzb();
   private final long zze;
   private final long zzf;
   private final long zzg;
   private volatile String zzh = null;

   public zza(long var1, long var3, long var5) {
      boolean var7 = true;
      boolean var8;
      if (var1 != -1L) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8);
      if (var3 != -1L) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8);
      if (var5 != -1L) {
         var8 = var7;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8);
      this.zze = var1;
      this.zzf = var3;
      this.zzg = var5;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == zza.class) {
         zza var2 = (zza)var1;
         if (var2.zzf == this.zzf && var2.zzg == this.zzg && var2.zze == this.zze) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      String var1 = String.valueOf(this.zze);
      String var2 = String.valueOf(this.zzf);
      String var3 = String.valueOf(this.zzg);
      StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + String.valueOf(var2).length() + String.valueOf(var3).length());
      var4.append(var1);
      var4.append(var2);
      var4.append(var3);
      return var4.toString().hashCode();
   }

   public String toString() {
      if (this.zzh == null) {
         String var1 = String.valueOf(Base64.encodeToString(((zzez)((zzkk)zzez.zzaj().zzk(1).zzc(this.zze).zzd(this.zzf).zze(this.zzg).zzdf())).toByteArray(), 10));
         if (var1.length() != 0) {
            var1 = "ChangeSequenceNumber:".concat(var1);
         } else {
            var1 = new String("ChangeSequenceNumber:");
         }

         this.zzh = var1;
      }

      return this.zzh;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeLong(var1, 2, this.zze);
      SafeParcelWriter.writeLong(var1, 3, this.zzf);
      SafeParcelWriter.writeLong(var1, 4, this.zzg);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
