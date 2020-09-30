/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzix;
import com.google.android.gms.internal.drive.zzjb;
import com.google.android.gms.internal.drive.zzjd;
import com.google.android.gms.internal.drive.zzje;
import com.google.android.gms.internal.drive.zzjg;
import com.google.android.gms.internal.drive.zzji;
import com.google.android.gms.internal.drive.zzjk;
import com.google.android.gms.internal.drive.zzjm;
import com.google.android.gms.internal.drive.zzjn;
import com.google.android.gms.internal.drive.zzkm;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzjc
implements Serializable,
Iterable<Byte> {
    public static final zzjc zznq = new zzjm(zzkm.zzsn);
    private static final zzji zznr;
    private static final Comparator<zzjc> zznt;
    private int zzns = 0;

    static {
        zzji zzji2 = zzix.zzbr() ? new zzjn(null) : new zzjg(null);
        zznr = zzji2;
        zznt = new zzje();
    }

    zzjc() {
    }

    private static int zza(byte by) {
        return by & 255;
    }

    static /* synthetic */ int zzb(byte by) {
        return zzjc.zza(by);
    }

    static int zzb(int n, int n2, int n3) {
        int n4 = n2 - n;
        if ((n | n2 | n4 | n3 - n2) >= 0) return n4;
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append("Beginning index: ");
            stringBuilder.append(n);
            stringBuilder.append(" < 0");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        if (n2 < n) {
            StringBuilder stringBuilder = new StringBuilder(66);
            stringBuilder.append("Beginning index larger than ending index: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(37);
        stringBuilder.append("End index: ");
        stringBuilder.append(n2);
        stringBuilder.append(" >= ");
        stringBuilder.append(n3);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public static zzjc zzb(byte[] arrby, int n, int n2) {
        zzjc.zzb(n, n + n2, arrby.length);
        return new zzjm(zznr.zzc(arrby, n, n2));
    }

    public static zzjc zzk(String string2) {
        return new zzjm(string2.getBytes(zzkm.UTF_8));
    }

    static zzjk zzu(int n) {
        return new zzjk(n, null);
    }

    public abstract boolean equals(Object var1);

    public final int hashCode() {
        int n;
        int n2 = n = this.zzns;
        if (n != 0) return n2;
        n2 = this.size();
        n2 = n = this.zza(n2, 0, n2);
        if (n == 0) {
            n2 = 1;
        }
        this.zzns = n2;
        return n2;
    }

    @Override
    public /* synthetic */ Iterator iterator() {
        return new zzjd(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
    }

    protected abstract int zza(int var1, int var2, int var3);

    public abstract zzjc zza(int var1, int var2);

    protected abstract String zza(Charset var1);

    abstract void zza(zzjb var1) throws IOException;

    public final String zzbt() {
        Charset charset = zzkm.UTF_8;
        if (this.size() != 0) return this.zza(charset);
        return "";
    }

    public abstract boolean zzbu();

    protected final int zzbv() {
        return this.zzns;
    }

    public abstract byte zzs(int var1);

    abstract byte zzt(int var1);
}

