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

final class zzju
extends zziw<Double>
implements zzkp<Double>,
zzmc,
RandomAccess {
    private static final zzju zzoi;
    private int size;
    private double[] zzoj;

    static {
        zzju zzju2;
        zzoi = zzju2 = new zzju(new double[0], 0);
        zzju2.zzbp();
    }

    zzju() {
        this(new double[10], 0);
    }

    private zzju(double[] arrd, int n) {
        this.zzoj = arrd;
        this.size = n;
    }

    private final void zzc(int n, double d) {
        this.zzbq();
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        int n2 = this.size;
        if (n > n2) throw new IndexOutOfBoundsException(this.zzq(n));
        double[] arrd = this.zzoj;
        if (n2 < arrd.length) {
            System.arraycopy(arrd, n, arrd, n + 1, n2 - n);
        } else {
            double[] arrd2 = new double[n2 * 3 / 2 + 1];
            System.arraycopy(arrd, 0, arrd2, 0, n);
            System.arraycopy(this.zzoj, n, arrd2, n + 1, this.size - n);
            this.zzoj = arrd2;
        }
        this.zzoj[n] = d;
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
        this.zzc(n, (Double)object);
    }

    @Override
    public final boolean addAll(Collection<? extends Double> arrd) {
        this.zzbq();
        zzkm.checkNotNull(arrd);
        if (!(arrd instanceof zzju)) {
            return super.addAll(arrd);
        }
        zzju zzju2 = (zzju)arrd;
        int n = zzju2.size;
        if (n == 0) {
            return false;
        }
        int n2 = this.size;
        if (Integer.MAX_VALUE - n2 < n) throw new OutOfMemoryError();
        arrd = this.zzoj;
        if ((n = n2 + n) > arrd.length) {
            this.zzoj = Arrays.copyOf(arrd, n);
        }
        System.arraycopy(zzju2.zzoj, 0, this.zzoj, this.size, zzju2.size);
        this.size = n;
        ++this.modCount;
        return true;
    }

    @Override
    public final boolean equals(Object arrd) {
        if (this == arrd) {
            return true;
        }
        if (!(arrd instanceof zzju)) {
            return super.equals(arrd);
        }
        arrd = (zzju)arrd;
        if (this.size != arrd.size) {
            return false;
        }
        arrd = arrd.zzoj;
        int n = 0;
        while (n < this.size) {
            if (Double.doubleToLongBits(this.zzoj[n]) != Double.doubleToLongBits(arrd[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        this.zzp(n);
        return this.zzoj[n];
    }

    @Override
    public final int hashCode() {
        int n = 1;
        int n2 = 0;
        while (n2 < this.size) {
            n = n * 31 + zzkm.zzu(Double.doubleToLongBits(this.zzoj[n2]));
            ++n2;
        }
        return n;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        this.zzp(n);
        double[] arrd = this.zzoj;
        double d = arrd[n];
        int n2 = this.size;
        if (n < n2 - 1) {
            System.arraycopy(arrd, n + 1, arrd, n, n2 - n - 1);
        }
        --this.size;
        ++this.modCount;
        return d;
    }

    @Override
    public final boolean remove(Object arrd) {
        this.zzbq();
        int n = 0;
        while (n < this.size) {
            if (arrd.equals(this.zzoj[n])) {
                arrd = this.zzoj;
                System.arraycopy(arrd, n + 1, arrd, n, this.size - n - 1);
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
        double[] arrd = this.zzoj;
        System.arraycopy(arrd, n2, arrd, n, this.size - n2);
        this.size -= n2 - n;
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object set(int n, Object arrd) {
        double d = (Double)arrd;
        this.zzbq();
        this.zzp(n);
        arrd = this.zzoj;
        double d2 = arrd[n];
        arrd[n] = d;
        return d2;
    }

    @Override
    public final int size() {
        return this.size;
    }

    public final void zzc(double d) {
        this.zzc(this.size, d);
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size) throw new IllegalArgumentException();
        return new zzju(Arrays.copyOf(this.zzoj, n), this.size);
    }
}

