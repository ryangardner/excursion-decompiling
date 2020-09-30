/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzfa;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzls;
import com.google.android.gms.internal.drive.zzmb;

public final class zzez
extends zzkk<zzez, zza>
implements zzls {
    private static final zzez zzhj;
    private static volatile zzmb<zzez> zzhk;
    private int zzhd;
    private int zzhe = 1;
    private long zzhf = -1L;
    private long zzhg = -1L;
    private long zzhh = -1L;
    private byte zzhi = (byte)2;

    static {
        zzez zzez2;
        zzhj = zzez2 = new zzez();
        zzkk.zza(zzez.class, zzez2);
    }

    private zzez() {
    }

    private final void setSequenceNumber(long l) {
        this.zzhd |= 2;
        this.zzhf = l;
    }

    private final void zza(long l) {
        this.zzhd |= 4;
        this.zzhg = l;
    }

    public static zza zzaj() {
        return (zza)zzhj.zzcw();
    }

    private final void zzb(long l) {
        this.zzhd |= 8;
        this.zzhh = l;
    }

    private final void zzj(int n) {
        this.zzhd |= 1;
        this.zzhe = n;
    }

    @Override
    protected final Object zza(int n, Object object, Object object2) {
        object2 = zzfa.zzhl;
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
                synchronized (zzez.class) {
                    object = object2 = zzhk;
                    if (object2 != null) return object;
                    zzhk = object = new zzkk.zzb(zzhj);
                    return object;
                }
            }
            case 4: {
                return zzhj;
            }
            case 3: {
                return zzez.zza(zzhj, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0004\u0001\u0504\u0000\u0002\u0510\u0001\u0003\u0510\u0002\u0004\u0510\u0003", new Object[]{"zzhd", "zzhe", "zzhf", "zzhg", "zzhh"});
            }
            case 2: {
                return new zza(null);
            }
            case 1: 
        }
        return new zzez();
    }

    public static final class zza
    extends zzkk.zza<zzez, zza>
    implements zzls {
        private zza() {
            super(zzhj);
        }

        /* synthetic */ zza(zzfa zzfa2) {
            this();
        }

        public final zza zzc(long l) {
            this.zzdb();
            ((zzez)this.zzru).setSequenceNumber(l);
            return this;
        }

        public final zza zzd(long l) {
            this.zzdb();
            ((zzez)this.zzru).zza(l);
            return this;
        }

        public final zza zze(long l) {
            this.zzdb();
            ((zzez)this.zzru).zzb(l);
            return this;
        }

        public final zza zzk(int n) {
            this.zzdb();
            ((zzez)this.zzru).zzj(1);
            return this;
        }
    }

}

