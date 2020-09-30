/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzfe;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzls;
import com.google.android.gms.internal.drive.zzmb;

public final class zzfd
extends zzkk<zzfd, zza>
implements zzls {
    private static volatile zzmb<zzfd> zzhk;
    private static final zzfd zzhq;
    private int zzhd;
    private long zzhg = -1L;
    private byte zzhi = (byte)2;
    private long zzhn = -1L;

    static {
        zzfd zzfd2;
        zzhq = zzfd2 = new zzfd();
        zzkk.zza(zzfd.class, zzfd2);
    }

    private zzfd() {
    }

    private final void zza(long l) {
        this.zzhd |= 2;
        this.zzhg = l;
    }

    public static zza zzap() {
        return (zza)zzhq.zzcw();
    }

    private final void zzf(long l) {
        this.zzhd |= 1;
        this.zzhn = l;
    }

    @Override
    protected final Object zza(int n, Object object, Object object2) {
        object2 = zzfe.zzhl;
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
                synchronized (zzfd.class) {
                    object = object2 = zzhk;
                    if (object2 != null) return object;
                    zzhk = object = new zzkk.zzb(zzhq);
                    return object;
                }
            }
            case 4: {
                return zzhq;
            }
            case 3: {
                return zzfd.zza(zzhq, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001\u0510\u0000\u0002\u0510\u0001", new Object[]{"zzhd", "zzhn", "zzhg"});
            }
            case 2: {
                return new zza(null);
            }
            case 1: 
        }
        return new zzfd();
    }

    public static final class zza
    extends zzkk.zza<zzfd, zza>
    implements zzls {
        private zza() {
            super(zzhq);
        }

        /* synthetic */ zza(zzfe zzfe2) {
            this();
        }

        public final zza zzi(long l) {
            this.zzdb();
            ((zzfd)this.zzru).zzf(l);
            return this;
        }

        public final zza zzj(long l) {
            this.zzdb();
            ((zzfd)this.zzru).zza(l);
            return this;
        }
    }

}

