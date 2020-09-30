package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class UserMetadata extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<UserMetadata> CREATOR = new zzt();
   private final String zzbo;
   private final String zzbp;
   private final String zzbq;
   private final boolean zzbr;
   private final String zzbs;

   public UserMetadata(String var1, String var2, String var3, boolean var4, String var5) {
      this.zzbo = var1;
      this.zzbp = var2;
      this.zzbq = var3;
      this.zzbr = var4;
      this.zzbs = var5;
   }

   public String toString() {
      return String.format("Permission ID: '%s', Display Name: '%s', Picture URL: '%s', Authenticated User: %b, Email: '%s'", this.zzbo, this.zzbp, this.zzbq, this.zzbr, this.zzbs);
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.zzbo, false);
      SafeParcelWriter.writeString(var1, 3, this.zzbp, false);
      SafeParcelWriter.writeString(var1, 4, this.zzbq, false);
      SafeParcelWriter.writeBoolean(var1, 5, this.zzbr);
      SafeParcelWriter.writeString(var1, 6, this.zzbs, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
