package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzw extends AbstractSafeParcelable {
   public static final Creator<zzw> CREATOR = new zzx();
   private final String zzan;
   private final Contents zzdf;
   private final MetadataBundle zzdn;
   private final Integer zzdo;
   private final DriveId zzdp;
   private final boolean zzdq;
   private final int zzdr;
   private final int zzds;

   public zzw(DriveId var1, MetadataBundle var2, int var3, int var4, ExecutionOptions var5) {
      this(var1, var2, (Contents)null, var4, var5.zzm(), var5.zzl(), var5.zzn(), var3);
   }

   zzw(DriveId var1, MetadataBundle var2, Contents var3, int var4, boolean var5, String var6, int var7, int var8) {
      if (var3 != null && var8 != 0) {
         boolean var9;
         if (var3.getRequestId() == var8) {
            var9 = true;
         } else {
            var9 = false;
         }

         Preconditions.checkArgument(var9, "inconsistent contents reference");
      }

      if (var4 == 0 && var3 == null && var8 == 0) {
         throw new IllegalArgumentException("Need a valid contents");
      } else {
         this.zzdp = (DriveId)Preconditions.checkNotNull(var1);
         this.zzdn = (MetadataBundle)Preconditions.checkNotNull(var2);
         this.zzdf = var3;
         this.zzdo = var4;
         this.zzan = var6;
         this.zzdr = var7;
         this.zzdq = var5;
         this.zzds = var8;
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdp, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzdn, var2, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzdf, var2, false);
      SafeParcelWriter.writeIntegerObject(var1, 5, this.zzdo, false);
      SafeParcelWriter.writeBoolean(var1, 6, this.zzdq);
      SafeParcelWriter.writeString(var1, 7, this.zzan, false);
      SafeParcelWriter.writeInt(var1, 8, this.zzdr);
      SafeParcelWriter.writeInt(var1, 9, this.zzds);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
