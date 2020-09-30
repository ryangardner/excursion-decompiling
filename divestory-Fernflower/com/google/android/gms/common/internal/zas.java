package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zas extends AbstractSafeParcelable {
   public static final Creator<zas> CREATOR = new zav();
   private final int zaa;
   private IBinder zab;
   private ConnectionResult zac;
   private boolean zad;
   private boolean zae;

   zas(int var1, IBinder var2, ConnectionResult var3, boolean var4, boolean var5) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = var4;
      this.zae = var5;
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (this == var1) {
         return true;
      } else if (!(var1 instanceof zas)) {
         return false;
      } else {
         zas var2 = (zas)var1;
         return this.zac.equals(var2.zac) && Objects.equal(this.zaa(), var2.zaa());
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeIBinder(var1, 2, this.zab, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zac, var2, false);
      SafeParcelWriter.writeBoolean(var1, 4, this.zad);
      SafeParcelWriter.writeBoolean(var1, 5, this.zae);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final IAccountAccessor zaa() {
      IBinder var1 = this.zab;
      return var1 == null ? null : IAccountAccessor.Stub.asInterface(var1);
   }

   public final ConnectionResult zab() {
      return this.zac;
   }

   public final boolean zac() {
      return this.zad;
   }

   public final boolean zad() {
      return this.zae;
   }
}
