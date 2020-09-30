package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzx extends AbstractSafeParcelable {
   public static final Creator<zzx> CREATOR = new zzy();
   public static final zzx zzmq = new zzx("=");
   public static final zzx zzmr = new zzx("<");
   public static final zzx zzms = new zzx("<=");
   public static final zzx zzmt = new zzx(">");
   public static final zzx zzmu = new zzx(">=");
   public static final zzx zzmv = new zzx("and");
   public static final zzx zzmw = new zzx("or");
   private static final zzx zzmx = new zzx("not");
   public static final zzx zzmy = new zzx("contains");
   private final String tag;

   zzx(String var1) {
      this.tag = var1;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         zzx var2 = (zzx)var1;
         String var3 = this.tag;
         if (var3 == null) {
            if (var2.tag != null) {
               return false;
            }
         } else if (!var3.equals(var2.tag)) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public final String getTag() {
      return this.tag;
   }

   public final int hashCode() {
      String var1 = this.tag;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2 + 31;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.tag, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
