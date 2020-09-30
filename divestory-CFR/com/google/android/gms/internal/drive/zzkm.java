/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjo;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlr;
import com.google.android.gms.internal.drive.zznf;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class zzkm {
    private static final Charset ISO_8859_1;
    static final Charset UTF_8;
    public static final byte[] zzsn;
    private static final ByteBuffer zzso;
    private static final zzjo zzsp;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        byte[] arrby = new byte[]{};
        zzsn = arrby;
        zzso = ByteBuffer.wrap(arrby);
        arrby = zzsn;
        zzsp = zzjo.zza(arrby, 0, arrby.length, false);
    }

    static <T> T checkNotNull(T t) {
        if (t == null) throw null;
        return t;
    }

    public static int hashCode(byte[] arrby) {
        int n;
        int n2 = arrby.length;
        n2 = n = zzkm.zza(n2, arrby, 0, n2);
        if (n != 0) return n2;
        return 1;
    }

    static int zza(int n, byte[] arrby, int n2, int n3) {
        int n4 = n2;
        int n5 = n;
        n = n4;
        while (n < n2 + n3) {
            n5 = n5 * 31 + arrby[n];
            ++n;
        }
        return n5;
    }

    static Object zza(Object object, Object object2) {
        return ((zzlq)object).zzcy().zza((zzlq)object2).zzde();
    }

    static <T> T zza(T t, String string2) {
        if (t == null) throw new NullPointerException(string2);
        return t;
    }

    public static boolean zzd(byte[] arrby) {
        return zznf.zzd(arrby);
    }

    public static int zze(boolean bl) {
        if (!bl) return 1237;
        return 1231;
    }

    public static String zze(byte[] arrby) {
        return new String(arrby, UTF_8);
    }

    static boolean zzf(zzlq zzlq2) {
        return false;
    }

    public static int zzu(long l) {
        return (int)(l ^ l >>> 32);
    }
}

