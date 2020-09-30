package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzm extends AbstractSafeParcelable {
   public static final Creator<zzm> CREATOR = new zzn();
   private final String zzan;
   private final boolean zzao;
   private final boolean zzat;
   private final DriveId zzdd;
   private final MetadataBundle zzde;
   private final Contents zzdf;
   private final int zzdg;
   private final int zzdh;
   private final boolean zzdi;

   public zzm(DriveId var1, MetadataBundle var2, int var3, boolean var4, com.google.android.gms.drive.zzn var5) {
      this(var1, var2, (Contents)null, var5.zzm(), var5.zzl(), var5.zzn(), var3, var4, var5.zzp());
   }

   zzm(DriveId var1, MetadataBundle var2, Contents var3, boolean var4, String var5, int var6, int var7, boolean var8, boolean var9) {
      this.zzdd = var1;
      this.zzde = var2;
      this.zzdf = var3;
      this.zzao = var4;
      this.zzan = var5;
      this.zzdg = var6;
      this.zzdh = var7;
      this.zzdi = var8;
      this.zzat = var9;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdd, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzde, var2, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzdf, var2, false);
      SafeParcelWriter.writeBoolean(var1, 5, this.zzao);
      SafeParcelWriter.writeString(var1, 6, this.zzan, false);
      SafeParcelWriter.writeInt(var1, 7, this.zzdg);
      SafeParcelWriter.writeInt(var1, 8, this.zzdh);
      SafeParcelWriter.writeBoolean(var1, 9, this.zzdi);
      SafeParcelWriter.writeBoolean(var1, 10, this.zzat);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
