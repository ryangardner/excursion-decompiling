package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class BinderWrapper implements Parcelable {
   public static final Creator<BinderWrapper> CREATOR = new zza();
   private IBinder zza;

   public BinderWrapper(IBinder var1) {
      this.zza = var1;
   }

   private BinderWrapper(Parcel var1) {
      this.zza = var1.readStrongBinder();
   }

   // $FF: synthetic method
   BinderWrapper(Parcel var1, zza var2) {
      this(var1);
   }

   public final int describeContents() {
      return 0;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var1.writeStrongBinder(this.zza);
   }
}
