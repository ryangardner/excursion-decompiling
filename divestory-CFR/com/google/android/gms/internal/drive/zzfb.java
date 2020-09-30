/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzfc;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzls;
import com.google.android.gms.internal.drive.zzmb;

public final class zzfb
extends zzkk<zzfb, zza>
implements zzls {
    private static volatile zzmb<zzfb> zzhk;
    private static final zzfb zzhp;
    private int zzhd;
    private int zzhe = 1;
    private long zzhg = -1L;
    private byte zzhi = (byte)2;
    private String zzhm = "";
    private long zzhn = -1L;
    private int zzho = -1;

    static {
        zzfb zzfb2;
        zzhp = zzfb2 = new zzfb();
        zzkk.zza(zzfb.class, zzfb2);
    }

    private zzfb() {
    }

    public static zzfb zza(byte[] arrby, zzjx zzjx2) throws zzkq {
        return zzkk.zza(zzhp, arrby, zzjx2);
    }

    private final void zza(long l) {
        this.zzhd |= 8;
        this.zzhg = l;
    }

    public static zza zzan() {
        return (zza)zzhp.zzcw();
    }

    private final void zzd(String string2) {
        if (string2 == null) throw null;
        this.zzhd |= 2;
        this.zzhm = string2;
    }

    private final void zzf(long l) {
        this.zzhd |= 4;
        this.zzhn = l;
    }

    private final void zzj(int n) {
        this.zzhd |= 1;
        this.zzhe = n;
    }

    private final void zzl(int n) {
        this.zzhd |= 16;
        this.zzho = n;
    }

    public final String getResourceId() {
        return this.zzhm;
    }

    public final int getResourceType() {
        return this.zzho;
    }

    @Override
    protected final Object zza(int n, Object object, Object object2) {
        object2 = zzfc.zzhl;
        int n2 = 1;
        switch (object2[n - 1]) {
            default: {
                throw new UnsupportedOperationException();
            }
            case 7: {
                n = n2;
                if (object == null) {
                    n = 0;
                }
                this.zzhi = (byte)n;
                return null;
            }
            case 6: {
                return this.zzhi;
            }
            case 5: {
                object = object2 = zzhk;
                if (object2 != null) return object;
                synchronized (zzfb.class) {
                    object = object2 = zzhk;
                    if (object2 != null) return object;
                    zzhk = object = new zzkk.zzb(zzhp);
                    return object;
                }
            }
            case 4: {
                return zzhp;
            }
            case 3: {
                return zzfb.zza(zzhp, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0004\u0001\u0504\u0000\u0002\u0508\u0001\u0003\u0510\u0002\u0004\u0510\u0003\u0005\u0004\u0004", new Object[]{"zzhd", "zzhe", "zzhm", "zzhn", "zzhg", "zzho"});
            }
            case 2: {
                return new zza(null);
            }
            case 1: 
        }
        return new zzfb();
    }

    public final long zzal() {
        return this.zzhn;
    }

    public final long zzam() {
        return this.zzhg;
    }

    public static final class zza
    extends zzkk.zza<zzfb, zza>
    implements zzls {
        private zza() {
            super(zzhp);
        }

        /* synthetic */ zza(zzfc zzfc2) {
            this();
        }

        public final zza zze(String string2) {
            this.zzdb();
            ((zzfb)this.zzru).zzd(string2);
            return this;
        }

        public final zza zzg(long l) {
            this.zzdb();
            ((zzfb)this.zzru).zzf(l);
            return this;
        }

        public final zza zzh(long l) {
            this.zzdb();
            ((zzfb)this.zzru).zza(l);
            return this;
        }

        public final zza zzm(int n) {
            this.zzdb();
            ((zzfb)this.zzru).zzj(1);
            return this;
        }

        public final zza zzn(int n) {
            this.zzdb();
            ((zzfb)this.zzru).zzl(n);
            return this;
        }
    }

}

