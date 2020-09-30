/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.TransferPreferences;

public class TransferPreferencesBuilder {
    public static final TransferPreferences DEFAULT_PREFERENCES = new zza(1, true, 256);
    private int zzbl;
    private boolean zzbm;
    private int zzbn;

    public TransferPreferencesBuilder() {
        this(DEFAULT_PREFERENCES);
    }

    public TransferPreferencesBuilder(FileUploadPreferences fileUploadPreferences) {
        this.zzbl = fileUploadPreferences.getNetworkTypePreference();
        this.zzbm = fileUploadPreferences.isRoamingAllowed();
        this.zzbn = fileUploadPreferences.getBatteryUsagePreference();
    }

    public TransferPreferencesBuilder(TransferPreferences transferPreferences) {
        this.zzbl = transferPreferences.getNetworkPreference();
        this.zzbm = transferPreferences.isRoamingAllowed();
        this.zzbn = transferPreferences.getBatteryUsagePreference();
    }

    public TransferPreferences build() {
        return new zza(this.zzbl, this.zzbm, this.zzbn);
    }

    public TransferPreferencesBuilder setBatteryUsagePreference(int n) {
        this.zzbn = n;
        return this;
    }

    public TransferPreferencesBuilder setIsRoamingAllowed(boolean bl) {
        this.zzbm = bl;
        return this;
    }

    public TransferPreferencesBuilder setNetworkPreference(int n) {
        this.zzbl = n;
        return this;
    }

    static final class zza
    implements TransferPreferences {
        private final int zzbl;
        private final boolean zzbm;
        private final int zzbn;

        zza(int n, boolean bl, int n2) {
            this.zzbl = n;
            this.zzbm = bl;
            this.zzbn = n2;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) return false;
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (zza)object;
            if (((zza)object).zzbl != this.zzbl) return false;
            if (((zza)object).zzbm != this.zzbm) return false;
            if (((zza)object).zzbn != this.zzbn) return false;
            return true;
        }

        @Override
        public final int getBatteryUsagePreference() {
            return this.zzbn;
        }

        @Override
        public final int getNetworkPreference() {
            return this.zzbl;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zzbl, this.zzbm, this.zzbn);
        }

        @Override
        public final boolean isRoamingAllowed() {
            return this.zzbm;
        }

        public final String toString() {
            return String.format("NetworkPreference: %s, IsRoamingAllowed %s, BatteryUsagePreference %s", this.zzbl, this.zzbm, this.zzbn);
        }
    }

}

