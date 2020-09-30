package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import java.util.List;

public final class zze extends AbstractSafeParcelable {
   public static final Creator<zze> CREATOR = new zzf();
   private final int zzbw;
   private final boolean zzbx;
   private final List<DriveSpace> zzby;

   zze(int var1, boolean var2, List<DriveSpace> var3) {
      this.zzbw = var1;
      this.zzbx = var2;
      this.zzby = var3;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         zze var2 = (zze)var1;
         if (Objects.equal(this.zzby, var2.zzby) && this.zzbw == var2.zzbw && this.zzbx == var2.zzbx) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzby, this.zzbw, this.zzbx);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzbw);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzbx);
      SafeParcelWriter.writeTypedList(var1, 4, this.zzby, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
