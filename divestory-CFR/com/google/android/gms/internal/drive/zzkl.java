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

final class zzkl
extends zziw<Integer>
implements zzkp<Integer>,
zzmc,
RandomAccess {
    private static final zzkl zzsl;
    private int size;
    private int[] zzsm;

    static {
        zzkl zzkl2;
        zzsl = zzkl2 = new zzkl(new int[0], 0);
        zzkl2.zzbp();
    }

    zzkl() {
        this(new int[10], 0);
    }

    private zzkl(int[] arrn, int n) {
        this.zzsm = arrn;
        this.size = n;
    }

    private final void zzo(int n, int n2) {
        this.zzbq();
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        int n3 = this.size;
        if (n > n3) throw new IndexOutOfBoundsException(this.zzq(n));
        int[] arrn = this.zzsm;
        if (n3 < arrn.length) {
            System.arraycopy(arrn, n, arrn, n + 1, n3 - n);
        } else {
            int[] arrn2 = new int[n3 * 3 / 2 + 1];
            System.arraycopy(arrn, 0, arrn2, 0, n);
            System.arraycopy(this.zzsm, n, arrn2, n + 1, this.size - n);
            this.zzsm = arrn2;
        }
        this.zzsm[n] = n2;
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
        this.zzo(n, (Integer)object);
    }

    @Override
    public final boolean addAll(Collection<? extends Integer> zzkl2) {
        this.zzbq();
        zzkm.checkNotNull(zzkl2);
        if (!(zzkl2 instanceof zzkl)) {
            return super.addAll(zzkl2);
        }
        zzkl2 = zzkl2;
        int n = zzkl2.size;
        if (n == 0) {
            return false;
        }
        int n2 = this.size;
        if (Integer.MAX_VALUE - n2 < n) throw new OutOfMemoryError();
        int[] arrn = this.zzsm;
        if ((n = n2 + n) > arrn.length) {
            this.zzsm = Arrays.copyOf(arrn, n);
        }
        System.arraycopy(zzkl2.zzsm, 0, this.zzsm, this.size, zzkl2.size);
        this.size = n;
        ++this.modCount;
        return true;
    }

    @Override
    public final boolean equals(Object arrn) {
        if (this == arrn) {
            return true;
        }
        if (!(arrn instanceof zzkl)) {
            return super.equals(arrn);
        }
        arrn = (zzkl)arrn;
        if (this.size != arrn.size) {
            return false;
        }
        arrn = arrn.zzsm;
        int n = 0;
        while (n < this.size) {
            if (this.zzsm[n] != arrn[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        return this.getInt(n);
    }

    public final int getInt(int n) {
        this.zzp(n);
        return this.zzsm[n];
    }

    @Override
    public final int hashCode() {
        int n = 1;
        int n2 = 0;
        while (n2 < this.size) {
            n = n * 31 + this.zzsm[n2];
            ++n2;
        }
        return n;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        this.zzp(n);
        int[] arrn = this.zzsm;
        int n2 = arrn[n];
        int n3 = this.size;
        if (n < n3 - 1) {
            System.arraycopy(arrn, n + 1, arrn, n, n3 - n - 1);
        }
        --this.size;
        ++this.modCount;
        return n2;
    }

    @Override
    public final boolean remove(Object arrn) {
        this.zzbq();
        int n = 0;
        while (n < this.size) {
            if (arrn.equals(this.zzsm[n])) {
                arrn = this.zzsm;
                System.arraycopy(arrn, n + 1, arrn, n, this.size - n - 1);
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
        int[] arrn = this.zzsm;
        System.arraycopy(arrn, n2, arrn, n, this.size - n2);
        this.size -= n2 - n;
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object set(int n, Object arrn) {
        int n2 = (Integer)arrn;
        this.zzbq();
        this.zzp(n);
        arrn = this.zzsm;
        int n3 = arrn[n];
        arrn[n] = n2;
        return n3;
    }

    @Override
    public final int size() {
        return this.size;
    }

    public final void zzam(int n) {
        this.zzo(this.size, n);
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size) throw new IllegalArgumentException();
        return new zzkl(Arrays.copyOf(this.zzsm, n), this.size);
    }
}

