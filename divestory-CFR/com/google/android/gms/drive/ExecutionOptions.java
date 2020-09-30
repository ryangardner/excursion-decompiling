/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.drive;

import android.text.TextUtils;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.internal.drive.zzaw;

public class ExecutionOptions {
    public static final int CONFLICT_STRATEGY_KEEP_REMOTE = 1;
    public static final int CONFLICT_STRATEGY_OVERWRITE_REMOTE = 0;
    public static final int MAX_TRACKING_TAG_STRING_LENGTH = 65536;
    private final String zzan;
    private final boolean zzao;
    private final int zzap;

    public ExecutionOptions(String string2, boolean bl, int n) {
        this.zzan = string2;
        this.zzao = bl;
        this.zzap = n;
    }

    public static boolean zza(int n) {
        if (n == 1) return true;
        return false;
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (ExecutionOptions)object;
        if (!Objects.equal(this.zzan, ((ExecutionOptions)object).zzan)) return false;
        if (this.zzap != ((ExecutionOptions)object).zzap) return false;
        if (this.zzao != ((ExecutionOptions)object).zzao) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzan, this.zzap, this.zzao);
    }

    @Deprecated
    public final void zza(GoogleApiClient googleApiClient) {
        this.zza(googleApiClient.getClient(Drive.CLIENT_KEY));
    }

    public final void zza(zzaw zzaw2) {
        if (!this.zzao) return;
        if (!zzaw2.zzah()) throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to be notified on completion");
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

        public Builder setConflictStrategy(int n) {
            boolean bl;
            boolean bl2 = bl = true;
            if (n != 0) {
                bl2 = bl;
                if (n != 1) {
                    bl2 = false;
                }
            }
            if (bl2) {
                this.zzas = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder(53);
            stringBuilder.append("Unrecognized value for conflict strategy: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setNotifyOnCompletion(boolean bl) {
            this.zzar = bl;
            return this;
        }

        public Builder setTrackingTag(String string2) {
            boolean bl = !TextUtils.isEmpty((CharSequence)string2) && string2.length() <= 65536;
            if (!bl) throw new IllegalArgumentException(String.format("trackingTag must not be null nor empty, and the length must be <= the maximum length (%s)", 65536));
            this.zzaq = string2;
            return this;
        }

        protected final void zzo() {
            if (this.zzas != 1) return;
            if (!this.zzar) throw new IllegalStateException("Cannot use CONFLICT_STRATEGY_KEEP_REMOTE without requesting completion notifications");
        }
    }

}

