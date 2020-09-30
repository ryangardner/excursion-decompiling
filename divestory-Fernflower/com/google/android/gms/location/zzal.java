package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.List;

public final class zzal extends AbstractSafeParcelable {
   public static final Creator<zzal> CREATOR = new zzam();
   private final String tag;
   private final List<String> zzbu;
   private final PendingIntent zzbv;

   zzal(List<String> var1, PendingIntent var2, String var3) {
      if (var1 == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = Collections.unmodifiableList(var1);
      }

      this.zzbu = var1;
      this.zzbv = var2;
      this.tag = var3;
   }

   public static zzal zza(PendingIntent var0) {
      Preconditions.checkNotNull(var0, "PendingIntent can not be null.");
      return new zzal((List)null, var0, "");
   }

   public static zzal zza(List<String> var0) {
      Preconditions.checkNotNull(var0, "geofence can't be null.");
      Preconditions.checkArgument(var0.isEmpty() ^ true, "Geofences must contains at least one id.");
      return new zzal(var0, (PendingIntent)null, "");
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringList(var1, 1, this.zzbu, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzbv, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.tag, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
