/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziw;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzmc;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzkh
extends zziw<Float>
implements zzkp<Float>,
zzmc,
RandomAccess {
    private static final zzkh zzrm;
    private int size;
    private float[] zzrn;

    static {
        zzkh zzkh2;
        zzrm = zzkh2 = new zzkh(new float[0], 0);
        zzkh2.zzbp();
    }

    zzkh() {
        this(new float[10], 0);
    }

    private zzkh(float[] arrf, int n) {
        this.zzrn = arrf;
        this.size = n;
    }

    private final void zzc(int n, float f) {
        this.zzbq();
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        int n2 = this.size;
        if (n > n2) throw new IndexOutOfBoundsException(this.zzq(n));
        float[] arrf = this.zzrn;
        if (n2 < arrf.length) {
            System.arraycopy(arrf, n, arrf, n + 1, n2 - n);
        } else {
            float[] arrf2 = new float[n2 * 3 / 2 + 1];
            System.arraycopy(arrf, 0, arrf2, 0, n);
            System.arraycopy(this.zzrn, n, arrf2, n + 1, this.size - n);
            this.zzrn = arrf2;
        }
        this.zzrn[n] = f;
        ++this.size;
        ++this.modCount;
    }

    private final void zzp(int n) {
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        if (n >= this.size) throw new IndexOutOfBoundsException(this.zzq(n));
    }

    private final String zzq(int n) {
        int n2 = this.size;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(n);
        stringBuilder.append(", Size:");
        stringBuilder.append(n2);
        return stringBuilder.toString();
    }

    @Override
    public final /* synthetic */ void add(int n, Object object) {
        this.zzc(n, ((Float)object).floatValue());
    }

    @Override
    public final boolean addAll(Collection<? extends Float> arrf) {
        this.zzbq();
        zzkm.checkNotNull(arrf);
        if (!(arrf instanceof zzkh)) {
            return super.addAll(arrf);
        }
        zzkh zzkh2 = (zzkh)arrf;
        int n = zzkh2.size;
        if (n == 0) {
            return false;
        }
        int n2 = this.size;
        if (Integer.MAX_VALUE - n2 < n) throw new OutOfMemoryError();
        arrf = this.zzrn;
        if ((n2 += n) > arrf.length) {
            this.zzrn = Arrays.copyOf(arrf, n2);
        }
        System.arraycopy(zzkh2.zzrn, 0, this.zzrn, this.size, zzkh2.size);
        this.size = n2;
        ++this.modCount;
        return true;
    }

    @Override
    public final boolean equals(Object arrf) {
        if (this == arrf) {
            return true;
        }
        if (!(arrf instanceof zzkh)) {
            return super.equals(arrf);
        }
        arrf = (zzkh)arrf;
        if (this.size != arrf.size) {
            return false;
        }
        arrf = arrf.zzrn;
        int n = 0;
        while (n < this.size) {
            if (Float.floatToIntBits(this.zzrn[n]) != Float.floatToIntBits(arrf[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        this.zzp(n);
        return Float.valueOf(this.zzrn[n]);
    }

    @Override
    public final int hashCode() {
        int n = 1;
        int n2 = 0;
        while (n2 < this.size) {
            n = n * 31 + Float.floatToIntBits(this.zzrn[n2]);
            ++n2;
        }
        return n;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        this.zzp(n);
        float[] arrf = this.zzrn;
        float f = arrf[n];
        int n2 = this.size;
        if (n < n2 - 1) {
            System.arraycopy(arrf, n + 1, arrf, n, n2 - n - 1);
        }
        --this.size;
        ++this.modCount;
        return Float.valueOf(f);
    }

    @Override
    public final boolean remove(Object arrf) {
        this.zzbq();
        int n = 0;
        while (n < this.size) {
            if (arrf.equals(Float.valueOf(this.zzrn[n]))) {
                arrf = this.zzrn;
                System.arraycopy(arrf, n + 1, arrf, n, this.size - n - 1);
                --this.size;
                ++this.modCount;
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    protected final void removeRange(int n, int n2) {
        this.zzbq();
        if (n2 < n) throw new IndexOutOfBoundsException("toIndex < fromIndex");
        float[] arrf = this.zzrn;
        System.arraycopy(arrf, n2, arrf, n, this.size - n2);
        this.size -= n2 - n;
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object set(int n, Object arrf) {
        float f = ((Float)arrf).floatValue();
        this.zzbq();
        this.zzp(n);
        arrf = this.zzrn;
        float f2 = arrf[n];
        arrf[n] = f;
        return Float.valueOf(f2);
    }

    @Override
    public final int size() {
        return this.size;
    }

    public final void zzc(float f) {
        this.zzc(this.size, f);
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size) throw new IllegalArgumentException();
        return new zzkh(Arrays.copyOf(this.zzrn, n), this.size);
    }
}

