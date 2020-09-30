package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;
import javax.annotation.Nullable;

@Deprecated
public final class WakeLockEvent extends StatsEvent {
   public static final Creator<WakeLockEvent> CREATOR = new zza();
   private final int zza;
   private final long zzb;
   private int zzc;
   private final String zzd;
   private final String zze;
   private final String zzf;
   private final int zzg;
   @Nullable
   private final List<String> zzh;
   private final String zzi;
   private final long zzj;
   private int zzk;
   private final String zzl;
   private final float zzm;
   private final long zzn;
   private final boolean zzo;
   private long zzp;

   WakeLockEvent(int var1, long var2, int var4, String var5, int var6, @Nullable List<String> var7, String var8, long var9, int var11, String var12, String var13, float var14, long var15, String var17, boolean var18) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var4;
      this.zzd = var5;
      this.zze = var12;
      this.zzf = var17;
      this.zzg = var6;
      this.zzp = -1L;
      this.zzh = var7;
      this.zzi = var8;
      this.zzj = var9;
      this.zzk = var11;
      this.zzl = var13;
      this.zzm = var14;
      this.zzn = var15;
      this.zzo = var18;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zza);
      SafeParcelWriter.writeLong(var1, 2, this.zza());
      SafeParcelWriter.writeString(var1, 4, this.zzd, false);
      SafeParcelWriter.writeInt(var1, 5, this.zzg);
      SafeParcelWriter.writeStringList(var1, 6, this.zzh, false);
      SafeParcelWriter.writeLong(var1, 8, this.zzj);
      SafeParcelWriter.writeString(var1, 10, this.zze, false);
      SafeParcelWriter.writeInt(var1, 11, this.zzb());
      SafeParcelWriter.writeString(var1, 12, this.zzi, false);
      SafeParcelWriter.writeString(var1, 13, this.zzl, false);
      SafeParcelWriter.writeInt(var1, 14, this.zzk);
      SafeParcelWriter.writeFloat(var1, 15, this.zzm);
      SafeParcelWriter.writeLong(var1, 16, this.zzn);
      SafeParcelWriter.writeString(var1, 17, this.zzf, false);
      SafeParcelWriter.writeBoolean(var1, 18, this.zzo);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final long zza() {
      return this.zzb;
   }

   public final int zzb() {
      return this.zzc;
   }

   public final long zzc() {
      return this.zzp;
   }

   public final String zzd() {
      List var1 = this.zzh;
      String var2 = this.zzd;
      int var3 = this.zzg;
      String var4 = "";
      String var11;
      if (var1 == null) {
         var11 = "";
      } else {
         var11 = TextUtils.join(",", var1);
      }

      int var5 = this.zzk;
      String var6 = this.zze;
      String var7 = var6;
      if (var6 == null) {
         var7 = "";
      }

      String var8 = this.zzl;
      var6 = var8;
      if (var8 == null) {
         var6 = "";
      }

      float var9 = this.zzm;
      var8 = this.zzf;
      if (var8 != null) {
         var4 = var8;
      }

      boolean var10 = this.zzo;
      StringBuilder var12 = new StringBuilder(String.valueOf(var2).length() + 51 + String.valueOf(var11).length() + String.valueOf(var7).length() + String.valueOf(var6).length() + String.valueOf(var4).length());
      var12.append("\t");
      var12.append(var2);
      var12.append("\t");
      var12.append(var3);
      var12.append("\t");
      var12.append(var11);
      var12.append("\t");
      var12.append(var5);
      var12.append("\t");
      var12.append(var7);
      var12.append("\t");
      var12.append(var6);
      var12.append("\t");
      var12.append(var9);
      var12.append("\t");
      var12.append(var4);
      var12.append("\t");
      var12.append(var10);
      return var12.toString();
   }
}
