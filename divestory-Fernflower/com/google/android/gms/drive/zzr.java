package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzr extends AbstractSafeParcelable {
   public static final Creator<zzr> CREATOR = new zzs();
   private int accountType;
   private String zzbg;
   private String zzbh;
   private String zzbi;
   private int zzbj;
   private boolean zzbk;

   public zzr(String var1, int var2, String var3, String var4, int var5, boolean var6) {
      this.zzbg = var1;
      this.accountType = var2;
      this.zzbh = var3;
      this.zzbi = var4;
      this.zzbj = var5;
      this.zzbk = var6;
   }

   private static boolean zzb(int var0) {
      switch(var0) {
      case 256:
      case 257:
      case 258:
         return true;
      default:
         return false;
      }
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         zzr var2 = (zzr)var1;
         if (Objects.equal(this.zzbg, var2.zzbg) && this.accountType == var2.accountType && this.zzbj == var2.zzbj && this.zzbk == var2.zzbk) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzbg, this.accountType, this.zzbj, this.zzbk);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      String var4;
      if (!zzb(this.accountType)) {
         var4 = null;
      } else {
         var4 = this.zzbg;
      }

      boolean var5 = false;
      SafeParcelWriter.writeString(var1, 2, var4, false);
      boolean var6 = zzb(this.accountType);
      byte var7 = -1;
      if (!var6) {
         var2 = -1;
      } else {
         var2 = this.accountType;
      }

      SafeParcelWriter.writeInt(var1, 3, var2);
      SafeParcelWriter.writeString(var1, 4, this.zzbh, false);
      SafeParcelWriter.writeString(var1, 5, this.zzbi, false);
      var2 = this.zzbj;
      boolean var8;
      if (var2 != 0 && var2 != 1 && var2 != 2 && var2 != 3) {
         var8 = var5;
      } else {
         var8 = true;
      }

      if (!var8) {
         var2 = var7;
      } else {
         var2 = this.zzbj;
      }

      SafeParcelWriter.writeInt(var1, 6, var2);
      SafeParcelWriter.writeBoolean(var1, 7, this.zzbk);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
