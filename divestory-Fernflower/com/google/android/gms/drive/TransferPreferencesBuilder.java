package com.google.android.gms.drive;

import com.google.android.gms.common.internal.Objects;

public class TransferPreferencesBuilder {
   public static final TransferPreferences DEFAULT_PREFERENCES = new TransferPreferencesBuilder.zza(1, true, 256);
   private int zzbl;
   private boolean zzbm;
   private int zzbn;

   public TransferPreferencesBuilder() {
      this(DEFAULT_PREFERENCES);
   }

   public TransferPreferencesBuilder(FileUploadPreferences var1) {
      this.zzbl = var1.getNetworkTypePreference();
      this.zzbm = var1.isRoamingAllowed();
      this.zzbn = var1.getBatteryUsagePreference();
   }

   public TransferPreferencesBuilder(TransferPreferences var1) {
      this.zzbl = var1.getNetworkPreference();
      this.zzbm = var1.isRoamingAllowed();
      this.zzbn = var1.getBatteryUsagePreference();
   }

   public TransferPreferences build() {
      return new TransferPreferencesBuilder.zza(this.zzbl, this.zzbm, this.zzbn);
   }

   public TransferPreferencesBuilder setBatteryUsagePreference(int var1) {
      this.zzbn = var1;
      return this;
   }

   public TransferPreferencesBuilder setIsRoamingAllowed(boolean var1) {
      this.zzbm = var1;
      return this;
   }

   public TransferPreferencesBuilder setNetworkPreference(int var1) {
      this.zzbl = var1;
      return this;
   }

   static final class zza implements TransferPreferences {
      private final int zzbl;
      private final boolean zzbm;
      private final int zzbn;

      zza(int var1, boolean var2, int var3) {
         this.zzbl = var1;
         this.zzbm = var2;
         this.zzbn = var3;
      }

      public final boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else {
            if (var1 != null && this.getClass() == var1.getClass()) {
               TransferPreferencesBuilder.zza var2 = (TransferPreferencesBuilder.zza)var1;
               if (var2.zzbl == this.zzbl && var2.zzbm == this.zzbm && var2.zzbn == this.zzbn) {
                  return true;
               }
            }

            return false;
         }
      }

      public final int getBatteryUsagePreference() {
         return this.zzbn;
      }

      public final int getNetworkPreference() {
         return this.zzbl;
      }

      public final int hashCode() {
         return Objects.hashCode(this.zzbl, this.zzbm, this.zzbn);
      }

      public final boolean isRoamingAllowed() {
         return this.zzbm;
      }

      public final String toString() {
         return String.format("NetworkPreference: %s, IsRoamingAllowed %s, BatteryUsagePreference %s", this.zzbl, this.zzbm, this.zzbn);
      }
   }
}
