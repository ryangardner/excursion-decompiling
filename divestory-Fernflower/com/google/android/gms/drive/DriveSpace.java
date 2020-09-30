package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.Set;
import java.util.regex.Pattern;

public class DriveSpace extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<DriveSpace> CREATOR = new zzm();
   public static final DriveSpace zzah = new DriveSpace("DRIVE");
   public static final DriveSpace zzai = new DriveSpace("APP_DATA_FOLDER");
   public static final DriveSpace zzaj;
   private static final Set<DriveSpace> zzak;
   private static final String zzal;
   private static final Pattern zzam;
   private final String name;

   static {
      DriveSpace var0 = new DriveSpace("PHOTOS");
      zzaj = var0;
      Set var1 = CollectionUtils.setOf(zzah, zzai, var0);
      zzak = var1;
      zzal = TextUtils.join(",", var1.toArray());
      zzam = Pattern.compile("[A-Z0-9_]*");
   }

   DriveSpace(String var1) {
      this.name = (String)Preconditions.checkNotNull(var1);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1.getClass() == DriveSpace.class ? this.name.equals(((DriveSpace)var1).name) : false;
   }

   public int hashCode() {
      return this.name.hashCode() ^ 1247068382;
   }

   public String toString() {
      return this.name;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 2, this.name, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
