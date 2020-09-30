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

final class zzja
extends zziw<Boolean>
implements zzkp<Boolean>,
zzmc,
RandomAccess {
    private static final zzja zzno;
    private int size;
    private boolean[] zznp;

    static {
        zzja zzja2;
        zzno = zzja2 = new zzja(new boolean[0], 0);
        zzja2.zzbp();
    }

    zzja() {
        this(new boolean[10], 0);
    }

    private zzja(boolean[] arrbl, int n) {
        this.zznp = arrbl;
        this.size = n;
    }

    private final void zza(int n, boolean bl) {
        this.zzbq();
        if (n < 0) throw new IndexOutOfBoundsException(this.zzq(n));
        int n2 = this.size;
        if (n > n2) throw new IndexOutOfBoundsException(this.zzq(n));
        boolean[] arrbl = this.zznp;
        if (n2 < arrbl.length) {
            System.arraycopy(arrbl, n, arrbl, n + 1, n2 - n);
        } else {
            boolean[] arrbl2 = new boolean[n2 * 3 / 2 + 1];
            System.arraycopy(arrbl, 0, arrbl2, 0, n);
            System.arraycopy(this.zznp, n, arrbl2, n + 1, this.size - n);
            this.zznp = arrbl2;
        }
        this.zznp[n] = bl;
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
        this.zza(n, (Boolean)object);
    }

    @Override
    public final boolean addAll(Collection<? extends Boolean> arrbl) {
        this.zzbq();
        zzkm.checkNotNull(arrbl);
        if (!(arrbl instanceof zzja)) {
            return super.addAll(arrbl);
        }
        zzja zzja2 = (zzja)arrbl;
        int n = zzja2.size;
        if (n == 0) {
            return false;
        }
        int n2 = this.size;
        if (Integer.MAX_VALUE - n2 < n) throw new OutOfMemoryError();
        arrbl = this.zznp;
        if ((n = n2 + n) > arrbl.length) {
            this.zznp = Arrays.copyOf(arrbl, n);
        }
        System.arraycopy(zzja2.zznp, 0, this.zznp, this.size, zzja2.size);
        this.size = n;
        ++this.modCount;
        return true;
    }

    public final void addBoolean(boolean bl) {
        this.zza(this.size, bl);
    }

    @Override
    public final boolean equals(Object arrbl) {
        if (this == arrbl) {
            return true;
        }
        if (!(arrbl instanceof zzja)) {
            return super.equals(arrbl);
        }
        arrbl = (zzja)arrbl;
        if (this.size != arrbl.size) {
            return false;
        }
        arrbl = arrbl.zznp;
        int n = 0;
        while (n < this.size) {
            if (this.zznp[n] != arrbl[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        this.zzp(n);
        return this.zznp[n];
    }

    @Override
    public final int hashCode() {
        int n = 1;
        int n2 = 0;
        while (n2 < this.size) {
            n = n * 31 + zzkm.zze(this.zznp[n2]);
            ++n2;
        }
        return n;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        this.zzp(n);
        boolean[] arrbl = this.zznp;
        boolean bl = arrbl[n];
        int n2 = this.size;
        if (n < n2 - 1) {
            System.arraycopy(arrbl, n + 1, arrbl, n, n2 - n - 1);
        }
        --this.size;
        ++this.modCount;
        return bl;
    }

    @Override
    public final boolean remove(Object arrbl) {
        this.zzbq();
        int n = 0;
        while (n < this.size) {
            if (arrbl.equals(this.zznp[n])) {
                arrbl = this.zznp;
                System.arraycopy(arrbl, n + 1, arrbl, n, this.size - n - 1);
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
        boolean[] arrbl = this.zznp;
        System.arraycopy(arrbl, n2, arrbl, n, this.size - n2);
        this.size -= n2 - n;
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object set(int n, Object arrbl) {
        boolean bl = (Boolean)arrbl;
        this.zzbq();
        this.zzp(n);
        arrbl = this.zznp;
        boolean bl2 = arrbl[n];
        arrbl[n] = bl;
        return bl2;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size) throw new IllegalArgumentException();
        return new zzja(Arrays.copyOf(this.zznp, n), this.size);
    }
}

