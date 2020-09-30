/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzit;
import com.google.android.gms.internal.drive.zzix;
import com.google.android.gms.internal.drive.zzjb;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjs;
import com.google.android.gms.internal.drive.zzjt;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkx;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zznf;
import com.google.android.gms.internal.drive.zznj;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzjr
extends zzjb {
    private static final Logger logger = Logger.getLogger(zzjr.class.getName());
    private static final boolean zzog = zznd.zzfd();
    zzjt zzoh;

    private zzjr() {
    }

    /* synthetic */ zzjr(zzjs zzjs2) {
        this();
    }

    public static int zza(int n, zzkx zzkx2) {
        n = zzjr.zzab(n);
        int n2 = zzkx2.zzcx();
        return n + (zzjr.zzad(n2) + n2);
    }

    public static int zza(zzkx zzkx2) {
        int n = zzkx2.zzcx();
        return zzjr.zzad(n) + n;
    }

    static int zza(zzlq zzlq2, zzmf zzmf2) {
        int n;
        zzlq2 = (zzit)zzlq2;
        int n2 = n = ((zzit)zzlq2).zzbm();
        if (n != -1) return zzjr.zzad(n2) + n2;
        n2 = zzmf2.zzn(zzlq2);
        ((zzit)zzlq2).zzo(n2);
        return zzjr.zzad(n2) + n2;
    }

    public static int zzab(int n) {
        return zzjr.zzad(n << 3);
    }

    public static int zzac(int n) {
        if (n < 0) return 10;
        return zzjr.zzad(n);
    }

    public static int zzad(int n) {
        if ((n & -128) == 0) {
            return 1;
        }
        if ((n & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & n) == 0) {
            return 3;
        }
        if ((n & -268435456) != 0) return 5;
        return 4;
    }

    public static int zzae(int n) {
        return zzjr.zzad(zzjr.zzai(n));
    }

    public static int zzaf(int n) {
        return 4;
    }

    public static int zzag(int n) {
        return 4;
    }

    public static int zzah(int n) {
        return zzjr.zzac(n);
    }

    private static int zzai(int n) {
        return n >> 31 ^ n << 1;
    }

    @Deprecated
    public static int zzaj(int n) {
        return zzjr.zzad(n);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(int n, double d) {
        return zzjr.zzab(n) + 8;
    }

    public static int zzb(int n, float f) {
        return zzjr.zzab(n) + 4;
    }

    public static int zzb(int n, zzkx zzkx2) {
        return (zzjr.zzab(1) << 1) + zzjr.zzh(2, n) + zzjr.zza(3, zzkx2);
    }

    public static int zzb(int n, zzlq zzlq2) {
        return (zzjr.zzab(1) << 1) + zzjr.zzh(2, n) + (zzjr.zzab(3) + zzjr.zzc(zzlq2));
    }

    static int zzb(int n, zzlq zzlq2, zzmf zzmf2) {
        return zzjr.zzab(n) + zzjr.zza(zzlq2, zzmf2);
    }

    public static int zzb(int n, String string2) {
        return zzjr.zzab(n) + zzjr.zzm(string2);
    }

    public static int zzb(zzjc zzjc2) {
        int n = zzjc2.size();
        return zzjr.zzad(n) + n;
    }

    public static zzjr zzb(byte[] arrby) {
        return new zza(arrby, 0, arrby.length);
    }

    public static int zzc(int n, zzjc zzjc2) {
        int n2 = zzjr.zzab(n);
        n = zzjc2.size();
        return n2 + (zzjr.zzad(n) + n);
    }

    @Deprecated
    static int zzc(int n, zzlq zzlq2, zzmf zzmf2) {
        int n2;
        int n3 = zzjr.zzab(n);
        zzlq2 = (zzit)zzlq2;
        n = n2 = ((zzit)zzlq2).zzbm();
        if (n2 != -1) return (n3 << 1) + n;
        n = zzmf2.zzn(zzlq2);
        ((zzit)zzlq2).zzo(n);
        return (n3 << 1) + n;
    }

    public static int zzc(int n, boolean bl) {
        return zzjr.zzab(n) + 1;
    }

    public static int zzc(zzlq zzlq2) {
        int n = zzlq2.zzcx();
        return zzjr.zzad(n) + n;
    }

    public static int zzc(byte[] arrby) {
        int n = arrby.length;
        return zzjr.zzad(n) + n;
    }

    static /* synthetic */ boolean zzcc() {
        return zzog;
    }

    public static int zzd(int n, long l) {
        return zzjr.zzab(n) + zzjr.zzp(l);
    }

    public static int zzd(int n, zzjc zzjc2) {
        return (zzjr.zzab(1) << 1) + zzjr.zzh(2, n) + zzjr.zzc(3, zzjc2);
    }

    @Deprecated
    public static int zzd(zzlq zzlq2) {
        return zzlq2.zzcx();
    }

    public static int zzd(boolean bl) {
        return 1;
    }

    public static int zze(int n, long l) {
        return zzjr.zzab(n) + zzjr.zzp(l);
    }

    public static int zzf(int n, long l) {
        return zzjr.zzab(n) + zzjr.zzp(zzjr.zzt(l));
    }

    public static int zzg(int n, int n2) {
        return zzjr.zzab(n) + zzjr.zzac(n2);
    }

    public static int zzg(int n, long l) {
        return zzjr.zzab(n) + 8;
    }

    public static int zzh(int n, int n2) {
        return zzjr.zzab(n) + zzjr.zzad(n2);
    }

    public static int zzh(int n, long l) {
        return zzjr.zzab(n) + 8;
    }

    public static int zzi(int n, int n2) {
        return zzjr.zzab(n) + zzjr.zzad(zzjr.zzai(n2));
    }

    public static int zzj(int n, int n2) {
        return zzjr.zzab(n) + 4;
    }

    public static int zzk(int n, int n2) {
        return zzjr.zzab(n) + 4;
    }

    public static int zzl(int n, int n2) {
        return zzjr.zzab(n) + zzjr.zzac(n2);
    }

    public static int zzm(String string2) {
        int n;
        try {
            n = zznf.zza(string2);
            return zzjr.zzad(n) + n;
        }
        catch (zznj zznj2) {
            n = string2.getBytes(zzkm.UTF_8).length;
        }
        return zzjr.zzad(n) + n;
    }

    public static int zzo(long l) {
        return zzjr.zzp(l);
    }

    public static int zzp(long l) {
        int n;
        if ((-128L & l) == 0L) {
            return 1;
        }
        if (l < 0L) {
            return 10;
        }
        if ((-34359738368L & l) != 0L) {
            n = 6;
            l >>>= 28;
        } else {
            n = 2;
        }
        int n2 = n;
        long l2 = l;
        if ((-2097152L & l) != 0L) {
            n2 = n + 2;
            l2 = l >>> 14;
        }
        n = n2;
        if ((l2 & -16384L) == 0L) return n;
        return n2 + 1;
    }

    public static int zzq(long l) {
        return zzjr.zzp(zzjr.zzt(l));
    }

    public static int zzr(long l) {
        return 8;
    }

    public static int zzs(long l) {
        return 8;
    }

    private static long zzt(long l) {
        return l >> 63 ^ l << 1;
    }

    public final void zza(double d) throws IOException {
        this.zzn(Double.doubleToRawLongBits(d));
    }

    public final void zza(float f) throws IOException {
        this.zzaa(Float.floatToRawIntBits(f));
    }

    public final void zza(int n, double d) throws IOException {
        this.zzc(n, Double.doubleToRawLongBits(d));
    }

    public final void zza(int n, float f) throws IOException {
        this.zzf(n, Float.floatToRawIntBits(f));
    }

    public abstract void zza(int var1, long var2) throws IOException;

    public abstract void zza(int var1, zzjc var2) throws IOException;

    public abstract void zza(int var1, zzlq var2) throws IOException;

    abstract void zza(int var1, zzlq var2, zzmf var3) throws IOException;

    public abstract void zza(int var1, String var2) throws IOException;

    public abstract void zza(zzjc var1) throws IOException;

    final void zza(String arrby, zznj zznj2) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zznj2);
        arrby = arrby.getBytes(zzkm.UTF_8);
        try {
            this.zzy(arrby.length);
            this.zza(arrby, 0, arrby.length);
            return;
        }
        catch (zzb zzb2) {
            throw zzb2;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new zzb(indexOutOfBoundsException);
        }
    }

    public abstract void zzaa(int var1) throws IOException;

    public abstract void zzb(int var1, int var2) throws IOException;

    public final void zzb(int n, long l) throws IOException {
        this.zza(n, zzjr.zzt(l));
    }

    public abstract void zzb(int var1, zzjc var2) throws IOException;

    public abstract void zzb(int var1, boolean var2) throws IOException;

    public abstract void zzb(zzlq var1) throws IOException;

    public abstract void zzc(byte var1) throws IOException;

    public abstract void zzc(int var1, int var2) throws IOException;

    public abstract void zzc(int var1, long var2) throws IOException;

    public final void zzc(boolean bl) throws IOException {
        this.zzc((byte)(bl ? 1 : 0));
    }

    public abstract int zzca();

    public final void zzcb() {
        if (this.zzca() != 0) throw new IllegalStateException("Did not write as much data as expected.");
    }

    public abstract void zzd(int var1, int var2) throws IOException;

    abstract void zzd(byte[] var1, int var2, int var3) throws IOException;

    public final void zze(int n, int n2) throws IOException {
        this.zzd(n, zzjr.zzai(n2));
    }

    public abstract void zzf(int var1, int var2) throws IOException;

    public abstract void zzl(long var1) throws IOException;

    public abstract void zzl(String var1) throws IOException;

    public final void zzm(long l) throws IOException {
        this.zzl(zzjr.zzt(l));
    }

    public abstract void zzn(long var1) throws IOException;

    public abstract void zzx(int var1) throws IOException;

    public abstract void zzy(int var1) throws IOException;

    public final void zzz(int n) throws IOException {
        this.zzy(zzjr.zzai(n));
    }

    static final class zza
    extends zzjr {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] arrby, int n, int n2) {
            super(null);
            if (arrby == null) throw new NullPointerException("buffer");
            n = arrby.length;
            int n3 = n2 + 0;
            if ((n2 | 0 | n - n3) < 0) throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", arrby.length, 0, n2));
            this.buffer = arrby;
            this.offset = 0;
            this.position = 0;
            this.limit = n3;
        }

        private final void write(byte[] arrby, int n, int n2) throws IOException {
            try {
                System.arraycopy(arrby, n, this.buffer, this.position, n2);
                this.position += n2;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, n2), indexOutOfBoundsException);
            }
        }

        @Override
        public final void zza(int n, long l) throws IOException {
            ((zzjr)this).zzb(n, 0);
            ((zzjr)this).zzl(l);
        }

        @Override
        public final void zza(int n, zzjc zzjc2) throws IOException {
            ((zzjr)this).zzb(n, 2);
            ((zzjr)this).zza(zzjc2);
        }

        @Override
        public final void zza(int n, zzlq zzlq2) throws IOException {
            ((zzjr)this).zzb(1, 3);
            ((zzjr)this).zzd(2, n);
            ((zzjr)this).zzb(3, 2);
            ((zzjr)this).zzb(zzlq2);
            ((zzjr)this).zzb(1, 4);
        }

        @Override
        final void zza(int n, zzlq zzlq2, zzmf zzmf2) throws IOException {
            int n2;
            ((zzjr)this).zzb(n, 2);
            zzit zzit2 = (zzit)zzlq2;
            n = n2 = zzit2.zzbm();
            if (n2 == -1) {
                n = zzmf2.zzn(zzit2);
                zzit2.zzo(n);
            }
            ((zzjr)this).zzy(n);
            zzmf2.zza(zzlq2, this.zzoh);
        }

        @Override
        public final void zza(int n, String string2) throws IOException {
            ((zzjr)this).zzb(n, 2);
            ((zzjr)this).zzl(string2);
        }

        @Override
        public final void zza(zzjc zzjc2) throws IOException {
            ((zzjr)this).zzy(zzjc2.size());
            zzjc2.zza(this);
        }

        @Override
        public final void zza(byte[] arrby, int n, int n2) throws IOException {
            this.write(arrby, n, n2);
        }

        @Override
        public final void zzaa(int n) throws IOException {
            byte[] arrby;
            int n2;
            int n3;
            try {
                arrby = this.buffer;
                n3 = this.position;
                this.position = n2 = n3 + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), indexOutOfBoundsException);
            }
            arrby[n3] = (byte)n;
            arrby = this.buffer;
            this.position = n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            arrby = this.buffer;
            this.position = n2 = n3 + 1;
            arrby[n3] = (byte)(n >> 16);
            arrby = this.buffer;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >>> 24);
        }

        @Override
        public final void zzb(int n, int n2) throws IOException {
            ((zzjr)this).zzy(n << 3 | n2);
        }

        @Override
        public final void zzb(int n, zzjc zzjc2) throws IOException {
            ((zzjr)this).zzb(1, 3);
            ((zzjr)this).zzd(2, n);
            ((zzjr)this).zza(3, zzjc2);
            ((zzjr)this).zzb(1, 4);
        }

        @Override
        public final void zzb(int n, boolean bl) throws IOException {
            ((zzjr)this).zzb(n, 0);
            ((zzjr)this).zzc((byte)(bl ? 1 : 0));
        }

        @Override
        public final void zzb(zzlq zzlq2) throws IOException {
            ((zzjr)this).zzy(zzlq2.zzcx());
            zzlq2.zzb(this);
        }

        @Override
        public final void zzc(byte by) throws IOException {
            try {
                byte[] arrby = this.buffer;
                int n = this.position;
                this.position = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), indexOutOfBoundsException);
            }
            arrby[n] = by;
        }

        @Override
        public final void zzc(int n, int n2) throws IOException {
            ((zzjr)this).zzb(n, 0);
            ((zzjr)this).zzx(n2);
        }

        @Override
        public final void zzc(int n, long l) throws IOException {
            ((zzjr)this).zzb(n, 1);
            ((zzjr)this).zzn(l);
        }

        @Override
        public final int zzca() {
            return this.limit - this.position;
        }

        @Override
        public final void zzd(int n, int n2) throws IOException {
            ((zzjr)this).zzb(n, 0);
            ((zzjr)this).zzy(n2);
        }

        @Override
        public final void zzd(byte[] arrby, int n, int n2) throws IOException {
            ((zzjr)this).zzy(n2);
            this.write(arrby, 0, n2);
        }

        @Override
        public final void zzf(int n, int n2) throws IOException {
            ((zzjr)this).zzb(n, 5);
            ((zzjr)this).zzaa(n2);
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public final void zzl(long var1_1) throws IOException {
            var3_2 = var1_1;
            if (zzjr.zzcc()) {
                var3_2 = var1_1;
                if (this.zzca() >= 10) {
                    do {
                        if ((var1_1 & -128L) == 0L) {
                            var5_3 = this.buffer;
                            var6_6 = this.position;
                            this.position = var6_6 + 1;
                            zznd.zza(var5_3, (long)var6_6, (byte)var1_1);
                            return;
                        }
                        var5_3 = this.buffer;
                        var6_6 = this.position;
                        this.position = var6_6 + 1;
                        zznd.zza(var5_3, (long)var6_6, (byte)((int)var1_1 & 127 | 128));
                        var1_1 >>>= 7;
                    } while (true);
                }
            }
            do lbl-1000: // 2 sources:
            {
                if ((var3_2 & -128L) == 0L) {
                    var5_4 = this.buffer;
                    var6_7 = this.position;
                    this.position = var6_7 + 1;
                    var5_4[var6_7] = (byte)var3_2;
                    return;
                }
                var5_4 = this.buffer;
                var6_7 = this.position;
                this.position = var6_7 + 1;
                break;
            } while (true);
            catch (IndexOutOfBoundsException var5_5) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{this.position, this.limit, 1}), var5_5);
            }
            {
                var5_4[var6_7] = (byte)((int)var3_2 & 127 | 128);
                var3_2 >>>= 7;
                ** while (true)
            }
        }

        @Override
        public final void zzl(String string2) throws IOException {
            int n = this.position;
            try {
                int n2 = zza.zzad(string2.length() * 3);
                int n3 = zza.zzad(string2.length());
                if (n3 == n2) {
                    this.position = n2 = n + n3;
                    n2 = zznf.zza(string2, this.buffer, n2, ((zzjr)this).zzca());
                    this.position = n;
                    ((zzjr)this).zzy(n2 - n - n3);
                    this.position = n2;
                    return;
                }
                ((zzjr)this).zzy(zznf.zza(string2));
                this.position = zznf.zza(string2, this.buffer, this.position, ((zzjr)this).zzca());
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new zzb(indexOutOfBoundsException);
            }
            catch (zznj zznj2) {
                this.position = n;
                this.zza(string2, zznj2);
                return;
            }
        }

        @Override
        public final void zzn(long l) throws IOException {
            int n;
            int n2;
            byte[] arrby;
            try {
                arrby = this.buffer;
                n = this.position;
                this.position = n2 = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), indexOutOfBoundsException);
            }
            arrby[n] = (byte)l;
            arrby = this.buffer;
            this.position = n = n2 + 1;
            arrby[n2] = (byte)(l >> 8);
            arrby = this.buffer;
            this.position = n2 = n + 1;
            arrby[n] = (byte)(l >> 16);
            arrby = this.buffer;
            this.position = n = n2 + 1;
            arrby[n2] = (byte)(l >> 24);
            arrby = this.buffer;
            this.position = n2 = n + 1;
            arrby[n] = (byte)(l >> 32);
            arrby = this.buffer;
            this.position = n = n2 + 1;
            arrby[n2] = (byte)(l >> 40);
            arrby = this.buffer;
            this.position = n2 = n + 1;
            arrby[n] = (byte)(l >> 48);
            arrby = this.buffer;
            this.position = n2 + 1;
            arrby[n2] = (byte)(l >> 56);
        }

        @Override
        public final void zzx(int n) throws IOException {
            if (n >= 0) {
                ((zzjr)this).zzy(n);
                return;
            }
            ((zzjr)this).zzl(n);
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public final void zzy(int var1_1) throws IOException {
            var2_2 = var1_1;
            if (zzjr.zzcc()) {
                var2_2 = var1_1;
                if (!zzix.zzbr()) {
                    var2_2 = var1_1;
                    if (this.zzca() >= 5) {
                        if ((var1_1 & -128) == 0) {
                            var3_3 = this.buffer;
                            var2_2 = this.position;
                            this.position = var2_2 + 1;
                            zznd.zza(var3_3, (long)var2_2, (byte)var1_1);
                            return;
                        }
                        var3_4 = this.buffer;
                        var2_2 = this.position;
                        this.position = var2_2 + 1;
                        zznd.zza(var3_4, (long)var2_2, (byte)(var1_1 | 128));
                        if (((var1_1 >>>= 7) & -128) == 0) {
                            var3_4 = this.buffer;
                            var2_2 = this.position;
                            this.position = var2_2 + 1;
                            zznd.zza(var3_4, (long)var2_2, (byte)var1_1);
                            return;
                        }
                        var3_4 = this.buffer;
                        var2_2 = this.position;
                        this.position = var2_2 + 1;
                        zznd.zza(var3_4, (long)var2_2, (byte)(var1_1 | 128));
                        if (((var1_1 >>>= 7) & -128) == 0) {
                            var3_4 = this.buffer;
                            var2_2 = this.position;
                            this.position = var2_2 + 1;
                            zznd.zza(var3_4, (long)var2_2, (byte)var1_1);
                            return;
                        }
                        var3_4 = this.buffer;
                        var2_2 = this.position;
                        this.position = var2_2 + 1;
                        zznd.zza(var3_4, (long)var2_2, (byte)(var1_1 | 128));
                        if (((var1_1 >>>= 7) & -128) == 0) {
                            var3_4 = this.buffer;
                            var2_2 = this.position;
                            this.position = var2_2 + 1;
                            zznd.zza(var3_4, (long)var2_2, (byte)var1_1);
                            return;
                        }
                        var3_4 = this.buffer;
                        var2_2 = this.position;
                        this.position = var2_2 + 1;
                        zznd.zza(var3_4, (long)var2_2, (byte)(var1_1 | 128));
                        var3_4 = this.buffer;
                        var2_2 = this.position;
                        this.position = var2_2 + 1;
                        zznd.zza(var3_4, (long)var2_2, (byte)(var1_1 >>> 7));
                        return;
                    }
                }
            }
            do lbl-1000: // 2 sources:
            {
                if ((var2_2 & -128) == 0) {
                    var3_5 = this.buffer;
                    var1_1 = this.position;
                    this.position = var1_1 + 1;
                    var3_5[var1_1] = (byte)var2_2;
                    return;
                }
                var3_5 = this.buffer;
                var1_1 = this.position;
                this.position = var1_1 + 1;
                break;
            } while (true);
            catch (IndexOutOfBoundsException var3_6) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{this.position, this.limit, 1}), var3_6);
            }
            {
                var3_5[var1_1] = (byte)(var2_2 & 127 | 128);
                var2_2 >>>= 7;
                ** while (true)
            }
        }
    }

    public static final class zzb
    extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzb(String string2, Throwable throwable) {
            string2 = (string2 = String.valueOf(string2)).length() != 0 ? "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(string2) : new String("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            super(string2, throwable);
        }

        zzb(Throwable throwable) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", throwable);
        }
    }

}

