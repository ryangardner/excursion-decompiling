package com.google.android.gms.drive;

import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.drive.zzaw;

public class ExecutionOptions {
   public static final int CONFLICT_STRATEGY_KEEP_REMOTE = 1;
   public static final int CONFLICT_STRATEGY_OVERWRITE_REMOTE = 0;
   public static final int MAX_TRACKING_TAG_STRING_LENGTH = 65536;
   private final String zzan;
   private final boolean zzao;
   private final int zzap;

   public ExecutionOptions(String var1, boolean var2, int var3) {
      this.zzan = var1;
      this.zzao = var2;
      this.zzap = var3;
   }

   public static boolean zza(int var0) {
      return var0 == 1;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         ExecutionOptions var2 = (ExecutionOptions)var1;
         if (Objects.equal(this.zzan, var2.zzan) && this.zzap == var2.zzap && this.zzao == var2.zzao) {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      return Objects.hashCode(this.zzan, this.zzap, this.zzao);
   }

   @Deprecated
   public final void zza(GoogleApiClient var1) {
      this.zza((zzaw)var1.getClient(Drive.CLIENT_KEY));
   }

   public final void zza(zzaw var1) {
      if (this.zzao && !var1.zzah()) {
         throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to be notified on completion");
      }
   }

   public final String zzl() {
      return this.zzan;
   }

   public final boolean zzm() {
      return this.zzao;
   }

   public final int zzn() {
      return this.zzap;
   }

   public static class Builder {
      protected String zzaq;
      protected boolean zzar;
      protected int zzas = 0;

      public ExecutionOptions build() {
         this.zzo();
         return new ExecutionOptions(this.zzaq, this.zzar, this.zzas);
      }

      public ExecutionOptions.Builder setConflictStrategy(int var1) {
         boolean var2 = true;
         boolean var3 = var2;
         if (var1 != 0) {
            var3 = var2;
            if (var1 != 1) {
               var3 = false;
            }
         }

         if (var3) {
            this.zzas = var1;
            return this;
         } else {
            StringBuilder var4 = new StringBuilder(53);
            var4.append("Unrecognized value for conflict strategy: ");
            var4.append(var1);
            throw new IllegalArgumentException(var4.toString());
         }
      }

      public ExecutionOptions.Builder setNotifyOnCompletion(boolean var1) {
         this.zzar = var1;
         return this;
      }

      public ExecutionOptions.Builder setTrackingTag(String var1) {
         boolean var2;
         if (!TextUtils.isEmpty(var1) && var1.length() <= 65536) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            this.zzaq = var1;
            return this;
         } else {
            throw new IllegalArgumentException(String.format("trackingTag must not be null nor empty, and the length must be <= the maximum length (%s)", 65536));
         }
      }

      protected final void zzo() {
         if (this.zzas == 1 && !this.zzar) {
            throw new IllegalStateException("Cannot use CONFLICT_STRATEGY_KEEP_REMOTE without requesting completion notifications");
         }
      }
   }
}
