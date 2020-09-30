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

final class zzle
extends zziw<Long>
implements zzkp<Long>,
zzmc,
RandomAccess {
    private static final zzle zztp;
    private int size;
    private long[] zztq;

    static {
        zzle zzle2;
        zztp = zzle2 = new zzle(new long[0], 0);
        zzle2.zzbp();
    }

    zzle() {
        this(new long[10], 0);
    }

    private zzle(long[] arrl, int n) {
        this.zztq = arrl;
        this.size = n;
    }

    private final void zzk(int n, long l) {
        this.zzbq();
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        int n2 = this.size;
        if (n > n2) throw new IndexOutOfBoundsException(this.zzq(n));
        long[] arrl = this.zztq;
        if (n2 < arrl.length) {
            System.arraycopy(arrl, n, arrl, n + 1, n2 - n);
        } else {
            long[] arrl2 = new long[n2 * 3 / 2 + 1];
            System.arraycopy(arrl, 0, arrl2, 0, n);
            System.arraycopy(this.zztq, n, arrl2, n + 1, this.size - n);
            this.zztq = arrl2;
        }
        this.zztq[n] = l;
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
        this.zzk(n, (Long)object);
    }

    @Override
    public final boolean addAll(Collection<? extends Long> arrl) {
        this.zzbq();
        zzkm.checkNotNull(arrl);
        if (!(arrl instanceof zzle)) {
            return super.addAll(arrl);
        }
        zzle zzle2 = (zzle)arrl;
        int n = zzle2.size;
        if (n == 0) {
            return false;
        }
        int n2 = this.size;
        if (Integer.MAX_VALUE - n2 < n) throw new OutOfMemoryError();
        arrl = this.zztq;
        if ((n2 += n) > arrl.length) {
            this.zztq = Arrays.copyOf(arrl, n2);
        }
        System.arraycopy(zzle2.zztq, 0, this.zztq, this.size, zzle2.size);
        this.size = n2;
        ++this.modCount;
        return true;
    }

    @Override
    public final boolean equals(Object arrl) {
        if (this == arrl) {
            return true;
        }
        if (!(arrl instanceof zzle)) {
            return super.equals(arrl);
        }
        arrl = (zzle)arrl;
        if (this.size != arrl.size) {
            return false;
        }
        arrl = arrl.zztq;
        int n = 0;
        while (n < this.size) {
            if (this.zztq[n] != arrl[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        return this.getLong(n);
    }

    public final long getLong(int n) {
        this.zzp(n);
        return this.zztq[n];
    }

    @Override
    public final int hashCode() {
        int n = 1;
        int n2 = 0;
        while (n2 < this.size) {
            n = n * 31 + zzkm.zzu(this.zztq[n2]);
            ++n2;
        }
        return n;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        this.zzp(n);
        long[] arrl = this.zztq;
        long l = arrl[n];
        int n2 = this.size;
        if (n < n2 - 1) {
            System.arraycopy(arrl, n + 1, arrl, n, n2 - n - 1);
        }
        --this.size;
        ++this.modCount;
        return l;
    }

    @Override
    public final boolean remove(Object arrl) {
        this.zzbq();
        int n = 0;
        while (n < this.size) {
            if (arrl.equals(this.zztq[n])) {
                arrl = this.zztq;
                System.arraycopy(arrl, n + 1, arrl, n, this.size - n - 1);
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
        long[] arrl = this.zztq;
        System.arraycopy(arrl, n2, arrl, n, this.size - n2);
        this.size -= n2 - n;
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object set(int n, Object arrl) {
        long l = (Long)arrl;
        this.zzbq();
        this.zzp(n);
        arrl = this.zztq;
        long l2 = arrl[n];
        arrl[n] = l;
        return l2;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size) throw new IllegalArgumentException();
        return new zzle(Arrays.copyOf(this.zztq, n), this.size);
    }

    public final void zzv(long l) {
        this.zzk(this.size, l);
    }
}

