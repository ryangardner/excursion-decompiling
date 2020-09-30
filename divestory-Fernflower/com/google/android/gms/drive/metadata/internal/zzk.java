package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Locale;

public final class zzk {
   private String zzji;

   private zzk(String var1) {
      this.zzji = var1.toLowerCase(Locale.US);
   }

   public static zzk zzg(String var0) {
      boolean var1;
      if (var0 != null && var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      Preconditions.checkArgument(var1);
      return var0 == null ? null : new zzk(var0);
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 != null && var1.getClass() == this.getClass()) {
         zzk var2 = (zzk)var1;
         return this.zzji.equals(var2.zzji);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.zzji.hashCode();
   }

   public final boolean isFolder() {
      return this.zzji.equals("application/vnd.google-apps.folder");
   }

   public final String toString() {
      return this.zzji;
   }

   public final boolean zzbh() {
      return this.zzji.startsWith("application/vnd.google-apps");
   }
}
