package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

public final class zzh extends AbstractSafeParcelable {
   public static final Creator<zzh> CREATOR = new zzi();
   final int status;
   final int zzct;
   final long zzcw;
   final long zzcx;
   final DriveId zzk;

   public zzh(int var1, DriveId var2, int var3, long var4, long var6) {
      this.zzct = var1;
      this.zzk = var2;
      this.status = var3;
      this.zzcw = var4;
      this.zzcx = var6;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         zzh var2 = (zzh)var1;
         if (this.zzct == var2.zzct && Objects.equal(this.zzk, var2.zzk) && this.status == var2.status && this.zzcw == var2.zzcw && this.zzcx == var2.zzcx) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzct, this.zzk, this.status, this.zzcw, this.zzcx);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzct);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzk, var2, false);
      SafeParcelWriter.writeInt(var1, 4, this.status);
      SafeParcelWriter.writeLong(var1, 5, this.zzcw);
      SafeParcelWriter.writeLong(var1, 6, this.zzcx);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
