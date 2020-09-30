package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;

public class FilterHolder extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<FilterHolder> CREATOR = new zzh();
   private final Filter zzbc;
   private final zzb<?> zzmd;
   private final zzd zzme;
   private final zzr zzmf;
   private final zzv zzmg;
   private final zzp<?> zzmh;
   private final zzt zzmi;
   private final zzn zzmj;
   private final zzl zzmk;
   private final zzz zzml;

   public FilterHolder(Filter var1) {
      Preconditions.checkNotNull(var1, "Null filter.");
      boolean var2 = var1 instanceof zzb;
      Object var3 = null;
      zzb var4;
      if (var2) {
         var4 = (zzb)var1;
      } else {
         var4 = null;
      }

      this.zzmd = var4;
      zzd var7;
      if (var1 instanceof zzd) {
         var7 = (zzd)var1;
      } else {
         var7 = null;
      }

      this.zzme = var7;
      zzr var8;
      if (var1 instanceof zzr) {
         var8 = (zzr)var1;
      } else {
         var8 = null;
      }

      this.zzmf = var8;
      zzv var9;
      if (var1 instanceof zzv) {
         var9 = (zzv)var1;
      } else {
         var9 = null;
      }

      this.zzmg = var9;
      zzp var10;
      if (var1 instanceof zzp) {
         var10 = (zzp)var1;
      } else {
         var10 = null;
      }

      this.zzmh = var10;
      zzt var11;
      if (var1 instanceof zzt) {
         var11 = (zzt)var1;
      } else {
         var11 = null;
      }

      this.zzmi = var11;
      zzn var12;
      if (var1 instanceof zzn) {
         var12 = (zzn)var1;
      } else {
         var12 = null;
      }

      this.zzmj = var12;
      zzl var5;
      if (var1 instanceof zzl) {
         var5 = (zzl)var1;
      } else {
         var5 = null;
      }

      this.zzmk = var5;
      zzz var6 = (zzz)var3;
      if (var1 instanceof zzz) {
         var6 = (zzz)var1;
      }

      this.zzml = var6;
      if (this.zzmd == null && this.zzme == null && this.zzmf == null && this.zzmg == null && this.zzmh == null && this.zzmi == null && this.zzmj == null && this.zzmk == null && var6 == null) {
         throw new IllegalArgumentException("Invalid filter type.");
      } else {
         this.zzbc = var1;
      }
   }

   FilterHolder(zzb<?> var1, zzd var2, zzr var3, zzv var4, zzp<?> var5, zzt var6, zzn<?> var7, zzl var8, zzz var9) {
      this.zzmd = var1;
      this.zzme = var2;
      this.zzmf = var3;
      this.zzmg = var4;
      this.zzmh = var5;
      this.zzmi = var6;
      this.zzmj = var7;
      this.zzmk = var8;
      this.zzml = var9;
      if (var1 != null) {
         this.zzbc = var1;
      } else if (var2 != null) {
         this.zzbc = var2;
      } else if (var3 != null) {
         this.zzbc = var3;
      } else if (var4 != null) {
         this.zzbc = var4;
      } else if (var5 != null) {
         this.zzbc = var5;
      } else if (var6 != null) {
         this.zzbc = var6;
      } else if (var7 != null) {
         this.zzbc = var7;
      } else if (var8 != null) {
         this.zzbc = var8;
      } else if (var9 != null) {
         this.zzbc = var9;
      } else {
         throw new IllegalArgumentException("At least one filter must be set.");
      }
   }

   public final Filter getFilter() {
      return this.zzbc;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.zzmd, var2, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzme, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzmf, var2, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzmg, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzmh, var2, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.zzmi, var2, false);
      SafeParcelWriter.writeParcelable(var1, 7, this.zzmj, var2, false);
      SafeParcelWriter.writeParcelable(var1, 8, this.zzmk, var2, false);
      SafeParcelWriter.writeParcelable(var1, 9, this.zzml, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
