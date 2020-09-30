package com.google.android.gms.signin.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zas;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zam extends AbstractSafeParcelable {
   public static final Creator<zam> CREATOR = new zal();
   private final int zaa;
   private final ConnectionResult zab;
   private final zas zac;

   public zam(int var1) {
      this(new ConnectionResult(8, (PendingIntent)null), (zas)null);
   }

   zam(int var1, ConnectionResult var2, zas var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   private zam(ConnectionResult var1, zas var2) {
      this(1, var1, (zas)null);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.zab, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zac, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final ConnectionResult zaa() {
      return this.zab;
   }

   public final zas zab() {
      return this.zac;
   }
}
