/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzlt;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.util.Arrays;

public final class zzmy {
    private static final zzmy zzvr = new zzmy(0, new int[0], new Object[0], false);
    private int count;
    private boolean zznh;
    private int zzrr = -1;
    private Object[] zzue;
    private int[] zzvs;

    private zzmy() {
        this(0, new int[8], new Object[8], true);
    }

    private zzmy(int n, int[] arrn, Object[] arrobject, boolean bl) {
        this.count = n;
        this.zzvs = arrn;
        this.zzue = arrobject;
        this.zznh = bl;
    }

    static zzmy zza(zzmy zzmy2, zzmy zzmy3) {
        int n = zzmy2.count + zzmy3.count;
        int[] arrn = Arrays.copyOf(zzmy2.zzvs, n);
        System.arraycopy(zzmy3.zzvs, 0, arrn, zzmy2.count, zzmy3.count);
        Object[] arrobject = Arrays.copyOf(zzmy2.zzue, n);
        System.arraycopy(zzmy3.zzue, 0, arrobject, zzmy2.count, zzmy3.count);
        return new zzmy(n, arrn, arrobject, true);
    }

    private static void zzb(int n, Object object, zzns zzns2) throws IOException {
        int n2 = n >>> 3;
        if ((n &= 7) == 0) {
            zzns2.zzi(n2, (Long)object);
            return;
        }
        if (n == 1) {
            zzns2.zzc(n2, (Long)object);
            return;
        }
        if (n == 2) {
            zzns2.zza(n2, (zzjc)object);
            return;
        }
        if (n != 3) {
            if (n != 5) throw new RuntimeException(zzkq.zzdl());
            zzns2.zzf(n2, (Integer)object);
            return;
        }
        if (zzns2.zzcd() == zzkk.zze.zzsi) {
            zzns2.zzak(n2);
            ((zzmy)object).zzb(zzns2);
            zzns2.zzal(n2);
            return;
        }
        zzns2.zzal(n2);
        ((zzmy)object).zzb(zzns2);
        zzns2.zzak(n2);
    }

    public static zzmy zzfa() {
        return zzvr;
    }

    static zzmy zzfb() {
        return new zzmy();
    }

    public final boolean equals(Object arrobject) {
        int n;
        block6 : {
            int n2;
            Object[] arrobject2;
            block5 : {
                if (this == arrobject) {
                    return true;
                }
                if (arrobject == null) {
                    return false;
                }
                if (!(arrobject instanceof zzmy)) {
                    return false;
                }
                arrobject = (zzmy)arrobject;
                n2 = this.count;
                if (n2 != arrobject.count) return false;
                arrobject2 = this.zzvs;
                int[] arrn = arrobject.zzvs;
                for (n = 0; n < n2; ++n) {
                    if (arrobject2[n] == arrn[n]) continue;
                    n = 0;
                    break block5;
                }
                n = 1;
            }
            if (n == 0) return false;
            arrobject2 = this.zzue;
            arrobject = arrobject.zzue;
            n2 = this.count;
            for (n = 0; n < n2; ++n) {
                if (arrobject2[n].equals(arrobject[n])) continue;
                n = 0;
                break block6;
            }
            n = 1;
        }
        if (n != 0) return true;
        return false;
    }

    public final int hashCode() {
        int n;
        int n2 = this.count;
        Object[] arrobject = this.zzvs;
        int n3 = 0;
        int n4 = 17;
        int n5 = 17;
        for (n = 0; n < n2; ++n) {
            n5 = n5 * 31 + arrobject[n];
        }
        arrobject = this.zzue;
        int n6 = this.count;
        n = n3;
        while (n < n6) {
            n4 = n4 * 31 + arrobject[n].hashCode();
            ++n;
        }
        return ((n2 + 527) * 31 + n5) * 31 + n4;
    }

    final void zza(zzns zzns2) throws IOException {
        if (zzns2.zzcd() == zzkk.zze.zzsj) {
            int n = this.count - 1;
            while (n >= 0) {
                zzns2.zza(this.zzvs[n] >>> 3, this.zzue[n]);
                --n;
            }
            return;
        }
        int n = 0;
        while (n < this.count) {
            zzns2.zza(this.zzvs[n] >>> 3, this.zzue[n]);
            ++n;
        }
    }

    final void zza(StringBuilder stringBuilder, int n) {
        int n2 = 0;
        while (n2 < this.count) {
            zzlt.zza(stringBuilder, n, String.valueOf(this.zzvs[n2] >>> 3), this.zzue[n2]);
            ++n2;
        }
    }

    final void zzb(int n, Object object) {
        if (!this.zznh) throw new UnsupportedOperationException();
        int n2 = this.count;
        if (n2 == this.zzvs.length) {
            n2 = n2 < 4 ? 8 : (n2 >>= 1);
            n2 = this.count + n2;
            this.zzvs = Arrays.copyOf(this.zzvs, n2);
            this.zzue = Arrays.copyOf(this.zzue, n2);
        }
        int[] arrn = this.zzvs;
        n2 = this.count;
        arrn[n2] = n;
        this.zzue[n2] = object;
        this.count = n2 + 1;
    }

    public final void zzb(zzns zzns2) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (zzns2.zzcd() == zzkk.zze.zzsi) {
            int n = 0;
            while (n < this.count) {
                zzmy.zzb(this.zzvs[n], this.zzue[n], zzns2);
                ++n;
            }
            return;
        }
        int n = this.count - 1;
        while (n >= 0) {
            zzmy.zzb(this.zzvs[n], this.zzue[n], zzns2);
            --n;
        }
    }

    public final void zzbp() {
        this.zznh = false;
    }

    public final int zzcx() {
        int n = this.zzrr;
        if (n != -1) {
            return n;
        }
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= this.count) {
                this.zzrr = n3;
                return n3;
            }
            int n4 = this.zzvs[n2];
            n = n4 >>> 3;
            if ((n4 &= 7) != 0) {
                if (n4 != 1) {
                    if (n4 != 2) {
                        if (n4 != 3) {
                            if (n4 != 5) throw new IllegalStateException(zzkq.zzdl());
                            n = zzjr.zzj(n, (Integer)this.zzue[n2]);
                        } else {
                            n = (zzjr.zzab(n) << 1) + ((zzmy)this.zzue[n2]).zzcx();
                        }
                    } else {
                        n = zzjr.zzc(n, (zzjc)this.zzue[n2]);
                    }
                } else {
                    n = zzjr.zzg(n, (Long)this.zzue[n2]);
                }
            } else {
                n = zzjr.zze(n, (Long)this.zzue[n2]);
            }
            n3 += n;
            ++n2;
        } while (true);
    }

    public final int zzfc() {
        int n = this.zzrr;
        if (n != -1) {
            return n;
        }
        n = 0;
        int n2 = 0;
        do {
            if (n >= this.count) {
                this.zzrr = n2;
                return n2;
            }
            n2 += zzjr.zzd(this.zzvs[n] >>> 3, (zzjc)this.zzue[n]);
            ++n;
        } while (true);
    }
}

