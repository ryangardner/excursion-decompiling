package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import java.util.Arrays;
import java.util.Iterator;

public final class zzim extends com.google.android.gms.drive.metadata.internal.zzm<DriveId> {
   public static final zzim zzlj = new zzim();

   private zzim() {
      super("driveId", Arrays.asList("sqlId", "resourceId", "mimeType"), Arrays.asList("dbInstanceId"), 4100000);
   }

   protected final boolean zzb(DataHolder var1, int var2, int var3) {
      Iterator var4 = this.zzaz().iterator();

      do {
         if (!var4.hasNext()) {
            return true;
         }
      } while(var1.hasColumn((String)var4.next()));

      return false;
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      long var4 = var1.getMetadata().getLong("dbInstanceId");
      byte var6 = "application/vnd.google-apps.folder".equals(var1.getString(zzhs.zzki.getName(), var2, var3));
      String var7 = var1.getString("resourceId", var2, var3);
      long var8 = var1.getLong("sqlId", var2, var3);
      String var10;
      if ("generated-android-null".equals(var7)) {
         var10 = null;
      } else {
         var10 = var7;
      }

      return new DriveId(var10, Long.valueOf(var8), var4, var6);
   }
}
