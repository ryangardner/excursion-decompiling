package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class ClientIdentity extends AbstractSafeParcelable {
   public static final Creator<ClientIdentity> CREATOR = new zaa();
   private final int zaa;
   private final String zab;

   public ClientIdentity(int var1, String var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ClientIdentity)) {
         return false;
      } else {
         ClientIdentity var2 = (ClientIdentity)var1;
         return var2.zaa == this.zaa && Objects.equal(var2.zab, this.zab);
      }
   }

   public int hashCode() {
      return this.zaa;
   }

   public String toString() {
      int var1 = this.zaa;
      String var2 = this.zab;
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 12);
      var3.append(var1);
      var3.append(":");
      var3.append(var2);
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeString(var1, 2, this.zab, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
