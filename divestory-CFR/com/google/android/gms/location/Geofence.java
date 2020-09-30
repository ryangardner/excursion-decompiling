/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.location.zzbh;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1L;

    public String getRequestId();

    public static final class Builder {
        private String zzad = null;
        private int zzae = 0;
        private long zzaf = Long.MIN_VALUE;
        private short zzag = (short)-1;
        private double zzah;
        private double zzai;
        private float zzaj;
        private int zzak = 0;
        private int zzal = -1;

        public final Geofence build() {
            if (this.zzad == null) throw new IllegalArgumentException("Request ID not set.");
            int n = this.zzae;
            if (n == 0) throw new IllegalArgumentException("Transitions types not set.");
            if ((n & 4) != 0) {
                if (this.zzal < 0) throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            }
            if (this.zzaf == Long.MIN_VALUE) throw new IllegalArgumentException("Expiration not set.");
            if (this.zzag == -1) throw new IllegalArgumentException("Geofence region not set.");
            if (this.zzak < 0) throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            return new zzbh(this.zzad, this.zzae, 1, this.zzah, this.zzai, this.zzaj, this.zzaf, this.zzak, this.zzal);
        }

        public final Builder setCircularRegion(double d, double d2, float f) {
            this.zzag = (short)(true ? 1 : 0);
            this.zzah = d;
            this.zzai = d2;
            this.zzaj = f;
            return this;
        }

        public final Builder setExpirationDuration(long l) {
            if (l < 0L) {
                this.zzaf = -1L;
                return this;
            }
            this.zzaf = SystemClock.elapsedRealtime() + l;
            return this;
        }

        public final Builder setLoiteringDelay(int n) {
            this.zzal = n;
            return this;
        }

        public final Builder setNotificationResponsiveness(int n) {
            this.zzak = n;
            return this;
        }

        public final Builder setRequestId(String string2) {
            this.zzad = string2;
            return this;
        }

        public final Builder setTransitionTypes(int n) {
            this.zzae = n;
            return this;
        }
    }

}

