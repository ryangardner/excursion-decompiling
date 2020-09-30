package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import java.util.Collection;

final class zzhu extends com.google.android.gms.drive.metadata.internal.zzm<BitmapTeleporter> {
   zzhu(String var1, Collection var2, Collection var3, int var4) {
      super(var1, var2, var3, 4400000);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      throw new IllegalStateException("Thumbnail field is write only");
   }
}
