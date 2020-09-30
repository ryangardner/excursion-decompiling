package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveSpace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class zzhz extends com.google.android.gms.drive.metadata.internal.zzl<DriveSpace> {
   public zzhz(int var1) {
      super("spaces", Arrays.asList("inDriveSpace", "isAppData", "inGooglePhotosSpace"), Collections.emptySet(), 7000000);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return this.zzd(var1, var2, var3);
   }

   protected final Collection<DriveSpace> zzd(DataHolder var1, int var2, int var3) {
      ArrayList var4 = new ArrayList();
      if (var1.getBoolean("inDriveSpace", var2, var3)) {
         var4.add(DriveSpace.zzah);
      }

      if (var1.getBoolean("isAppData", var2, var3)) {
         var4.add(DriveSpace.zzai);
      }

      if (var1.getBoolean("inGooglePhotosSpace", var2, var3)) {
         var4.add(DriveSpace.zzaj);
      }

      return var4;
   }
}
