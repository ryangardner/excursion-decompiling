package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.UserMetadata;
import java.util.Arrays;
import java.util.Collections;

public final class zzu extends zzm<UserMetadata> {
   public zzu(String var1, int var2) {
      super(var1, Arrays.asList(zza(var1, "permissionId"), zza(var1, "displayName"), zza(var1, "picture"), zza(var1, "isAuthenticatedUser"), zza(var1, "emailAddress")), Collections.emptyList(), 6000000);
   }

   private static String zza(String var0, String var1) {
      StringBuilder var2 = new StringBuilder(String.valueOf(var0).length() + 1 + String.valueOf(var1).length());
      var2.append(var0);
      var2.append(".");
      var2.append(var1);
      return var2.toString();
   }

   private final String zzh(String var1) {
      return zza(this.getName(), var1);
   }

   protected final boolean zzb(DataHolder var1, int var2, int var3) {
      return var1.hasColumn(this.zzh("permissionId")) && !var1.hasNull(this.zzh("permissionId"), var2, var3);
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      String var4 = var1.getString(this.zzh("permissionId"), var2, var3);
      if (var4 != null) {
         String var5 = var1.getString(this.zzh("displayName"), var2, var3);
         String var6 = var1.getString(this.zzh("picture"), var2, var3);
         boolean var7 = var1.getBoolean(this.zzh("isAuthenticatedUser"), var2, var3);
         String var8 = var1.getString(this.zzh("emailAddress"), var2, var3);
         return new UserMetadata(var4, var5, var6, Boolean.valueOf(var7), var8);
      } else {
         return null;
      }
   }
}
