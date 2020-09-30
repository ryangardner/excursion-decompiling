/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzix;
import com.google.android.gms.internal.drive.zzne;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zznd {
    private static final Logger logger = Logger.getLogger(zznd.class.getName());
    private static final Class<?> zzni;
    private static final boolean zzog;
    private static final Unsafe zzuc;
    private static final boolean zzvy;
    private static final boolean zzvz;
    private static final zzd zzwa;
    private static final boolean zzwb;
    private static final long zzwc;
    private static final long zzwd;
    private static final long zzwe;
    private static final long zzwf;
    private static final long zzwg;
    private static final long zzwh;
    private static final long zzwi;
    private static final long zzwj;
    private static final long zzwk;
    private static final long zzwl;
    private static final long zzwm;
    private static final long zzwn;
    private static final long zzwo;
    private static final long zzwp;
    private static final int zzwq;
    static final boolean zzwr;

    static {
        zzuc = zznd.zzff();
        zzni = zzix.zzbs();
        zzvy = zznd.zzk(Long.TYPE);
        zzvz = zznd.zzk(Integer.TYPE);
        Object object = zzuc;
        zzd zzd2 = null;
        if (object != null) {
            if (zzix.zzbr()) {
                if (zzvy) {
                    zzd2 = new zzb(zzuc);
                } else if (zzvz) {
                    zzd2 = new zza(zzuc);
                }
            } else {
                zzd2 = new zzc(zzuc);
            }
        }
        zzwa = zzd2;
        zzwb = zznd.zzfh();
        zzog = zznd.zzfg();
        zzwc = zznd.zzi(byte[].class);
        zzwd = zznd.zzi(boolean[].class);
        zzwe = zznd.zzj(boolean[].class);
        zzwf = zznd.zzi(int[].class);
        zzwg = zznd.zzj(int[].class);
        zzwh = zznd.zzi(long[].class);
        zzwi = zznd.zzj(long[].class);
        zzwj = zznd.zzi(float[].class);
        zzwk = zznd.zzj(float[].class);
        zzwl = zznd.zzi(double[].class);
        zzwm = zznd.zzj(double[].class);
        zzwn = zznd.zzi(Object[].class);
        zzwo = zznd.zzj(Object[].class);
        object = zznd.zzfi();
        long l = object != null && (zzd2 = zzwa) != null ? zzd2.zzws.objectFieldOffset((Field)object) : -1L;
        zzwp = l;
        zzwq = (int)(zzwc & 7L);
        boolean bl = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        zzwr = bl;
    }

    private zznd() {
    }

    static byte zza(byte[] arrby, long l) {
        return zzwa.zzx(arrby, zzwc + l);
    }

    private static void zza(Object object, long l, byte by) {
        long l2 = -4L & l;
        int n = zznd.zzj(object, l2);
        int n2 = ((int)l & 3) << 3;
        zznd.zza(object, l2, (255 & by) << n2 | n & 255 << n2);
    }

    static void zza(Object object, long l, double d) {
        zzwa.zza(object, l, d);
    }

    static void zza(Object object, long l, float f) {
        zzwa.zza(object, l, f);
    }

    static void zza(Object object, long l, int n) {
        zzwa.zza(object, l, n);
    }

    static void zza(Object object, long l, long l2) {
        zzwa.zza(object, l, l2);
    }

    static void zza(Object object, long l, Object object2) {
        zznd.zzwa.zzws.putObject(object, l, object2);
    }

    static void zza(Object object, long l, boolean bl) {
        zzwa.zza(object, l, bl);
    }

    static void zza(byte[] arrby, long l, byte by) {
        zzwa.zze(arrby, zzwc + l, by);
    }

    private static Field zzb(Class<?> annotatedElement, String string2) {
        try {
            return annotatedElement.getDeclaredField(string2);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static void zzb(Object object, long l, byte by) {
        long l2 = -4L & l;
        int n = zznd.zzj(object, l2);
        int n2 = ((int)l & 3) << 3;
        zznd.zza(object, l2, (255 & by) << n2 | n & 255 << n2);
    }

    private static void zzb(Object object, long l, boolean bl) {
        zznd.zza(object, l, (byte)(bl ? 1 : 0));
    }

    private static void zzc(Object object, long l, boolean bl) {
        zznd.zzb(object, l, (byte)(bl ? 1 : 0));
    }

    static boolean zzfd() {
        return zzog;
    }

    static boolean zzfe() {
        return zzwb;
    }

    static Unsafe zzff() {
        try {
            Object object = new zzne();
            return AccessController.doPrivileged(object);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static boolean zzfg() {
        Object object = zzuc;
        if (object == null) {
            return false;
        }
        try {
            object = object.getClass();
            ((Class)object).getMethod("objectFieldOffset", Field.class);
            ((Class)object).getMethod("arrayBaseOffset", Class.class);
            ((Class)object).getMethod("arrayIndexScale", Class.class);
            ((Class)object).getMethod("getInt", Object.class, Long.TYPE);
            ((Class)object).getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            ((Class)object).getMethod("getLong", Object.class, Long.TYPE);
            ((Class)object).getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            ((Class)object).getMethod("getObject", Object.class, Long.TYPE);
            ((Class)object).getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzix.zzbr()) {
                return true;
            }
            ((Class)object).getMethod("getByte", Object.class, Long.TYPE);
            ((Class)object).getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            ((Class)object).getMethod("getBoolean", Object.class, Long.TYPE);
            ((Class)object).getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            ((Class)object).getMethod("getFloat", Object.class, Long.TYPE);
            ((Class)object).getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            ((Class)object).getMethod("getDouble", Object.class, Long.TYPE);
            ((Class)object).getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        }
        catch (Throwable throwable) {
            Logger logger = zznd.logger;
            object = Level.WARNING;
            String string2 = String.valueOf(throwable);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(string2);
            logger.logp((Level)object, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zzfh() {
        Object object = zzuc;
        if (object == null) {
            return false;
        }
        try {
            object = object.getClass();
            ((Class)object).getMethod("objectFieldOffset", Field.class);
            ((Class)object).getMethod("getLong", Object.class, Long.TYPE);
            if (zznd.zzfi() == null) {
                return false;
            }
            if (zzix.zzbr()) {
                return true;
            }
            ((Class)object).getMethod("getByte", Long.TYPE);
            ((Class)object).getMethod("putByte", Long.TYPE, Byte.TYPE);
            ((Class)object).getMethod("getInt", Long.TYPE);
            ((Class)object).getMethod("putInt", Long.TYPE, Integer.TYPE);
            ((Class)object).getMethod("getLong", Long.TYPE);
            ((Class)object).getMethod("putLong", Long.TYPE, Long.TYPE);
            ((Class)object).getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            ((Class)object).getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        }
        catch (Throwable throwable) {
            object = logger;
            Level level = Level.WARNING;
            String string2 = String.valueOf(throwable);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(string2);
            ((Logger)object).logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", stringBuilder.toString());
            return false;
        }
    }

    private static Field zzfi() {
        Field field;
        if (zzix.zzbr() && (field = zznd.zzb(Buffer.class, "effectiveDirectAddress")) != null) {
            return field;
        }
        field = zznd.zzb(Buffer.class, "address");
        if (field == null) return null;
        if (field.getType() != Long.TYPE) return null;
        return field;
    }

    static <T> T zzh(Class<T> object) {
        try {
            object = zzuc.allocateInstance((Class<?>)object);
        }
        catch (InstantiationException instantiationException) {
            throw new IllegalStateException(instantiationException);
        }
        return (T)object;
    }

    private static int zzi(Class<?> class_) {
        if (!zzog) return -1;
        return zznd.zzwa.zzws.arrayBaseOffset(class_);
    }

    private static int zzj(Class<?> class_) {
        if (!zzog) return -1;
        return zznd.zzwa.zzws.arrayIndexScale(class_);
    }

    static int zzj(Object object, long l) {
        return zzwa.zzj(object, l);
    }

    static long zzk(Object object, long l) {
        return zzwa.zzk(object, l);
    }

    private static boolean zzk(Class<?> class_) {
        if (!zzix.zzbr()) {
            return false;
        }
        try {
            Class<?> class_2 = zzni;
            class_2.getMethod("peekLong", class_, Boolean.TYPE);
            class_2.getMethod("pokeLong", class_, Long.TYPE, Boolean.TYPE);
            class_2.getMethod("pokeInt", class_, Integer.TYPE, Boolean.TYPE);
            class_2.getMethod("peekInt", class_, Boolean.TYPE);
            class_2.getMethod("pokeByte", class_, Byte.TYPE);
            class_2.getMethod("peekByte", class_);
            class_2.getMethod("pokeByteArray", class_, byte[].class, Integer.TYPE, Integer.TYPE);
            class_2.getMethod("peekByteArray", class_, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    static boolean zzl(Object object, long l) {
        return zzwa.zzl(object, l);
    }

    static float zzm(Object object, long l) {
        return zzwa.zzm(object, l);
    }

    static double zzn(Object object, long l) {
        return zzwa.zzn(object, l);
    }

    static Object zzo(Object object, long l) {
        return zznd.zzwa.zzws.getObject(object, l);
    }

    private static byte zzp(Object object, long l) {
        return (byte)(zznd.zzj(object, -4L & l) >>> (int)((l & 3L) << 3));
    }

    private static byte zzq(Object object, long l) {
        return (byte)(zznd.zzj(object, -4L & l) >>> (int)((l & 3L) << 3));
    }

    private static boolean zzr(Object object, long l) {
        if (zznd.zzp(object, l) == 0) return false;
        return true;
    }

    private static boolean zzs(Object object, long l) {
        if (zznd.zzq(object, l) == 0) return false;
        return true;
    }

    static final class zza
    extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override
        public final void zza(Object object, long l, double d) {
            this.zza(object, l, Double.doubleToLongBits(d));
        }

        @Override
        public final void zza(Object object, long l, float f) {
            this.zza(object, l, Float.floatToIntBits(f));
        }

        @Override
        public final void zza(Object object, long l, boolean bl) {
            if (zzwr) {
                zznd.zzb(object, l, bl);
                return;
            }
            zznd.zzc(object, l, bl);
        }

        @Override
        public final void zze(Object object, long l, byte by) {
            if (zzwr) {
                zznd.zza(object, l, by);
                return;
            }
            zznd.zzb(object, l, by);
        }

        @Override
        public final boolean zzl(Object object, long l) {
            if (!zzwr) return zznd.zzs(object, l);
            return zznd.zzr(object, l);
        }

        @Override
        public final float zzm(Object object, long l) {
            return Float.intBitsToFloat(this.zzj(object, l));
        }

        @Override
        public final double zzn(Object object, long l) {
            return Double.longBitsToDouble(this.zzk(object, l));
        }

        @Override
        public final byte zzx(Object object, long l) {
            if (!zzwr) return zznd.zzq(object, l);
            return zznd.zzp(object, l);
        }
    }

    static final class zzb
    extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override
        public final void zza(Object object, long l, double d) {
            this.zza(object, l, Double.doubleToLongBits(d));
        }

        @Override
        public final void zza(Object object, long l, float f) {
            this.zza(object, l, Float.floatToIntBits(f));
        }

        @Override
        public final void zza(Object object, long l, boolean bl) {
            if (zzwr) {
                zznd.zzb(object, l, bl);
                return;
            }
            zznd.zzc(object, l, bl);
        }

        @Override
        public final void zze(Object object, long l, byte by) {
            if (zzwr) {
                zznd.zza(object, l, by);
                return;
            }
            zznd.zzb(object, l, by);
        }

        @Override
        public final boolean zzl(Object object, long l) {
            if (!zzwr) return zznd.zzs(object, l);
            return zznd.zzr(object, l);
        }

        @Override
        public final float zzm(Object object, long l) {
            return Float.intBitsToFloat(this.zzj(object, l));
        }

        @Override
        public final double zzn(Object object, long l) {
            return Double.longBitsToDouble(this.zzk(object, l));
        }

        @Override
        public final byte zzx(Object object, long l) {
            if (!zzwr) return zznd.zzq(object, l);
            return zznd.zzp(object, l);
        }
    }

    static final class zzc
    extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override
        public final void zza(Object object, long l, double d) {
            this.zzws.putDouble(object, l, d);
        }

        @Override
        public final void zza(Object object, long l, float f) {
            this.zzws.putFloat(object, l, f);
        }

        @Override
        public final void zza(Object object, long l, boolean bl) {
            this.zzws.putBoolean(object, l, bl);
        }

        @Override
        public final void zze(Object object, long l, byte by) {
            this.zzws.putByte(object, l, by);
        }

        @Override
        public final boolean zzl(Object object, long l) {
            return this.zzws.getBoolean(object, l);
        }

        @Override
        public final float zzm(Object object, long l) {
            return this.zzws.getFloat(object, l);
        }

        @Override
        public final double zzn(Object object, long l) {
            return this.zzws.getDouble(object, l);
        }

        @Override
        public final byte zzx(Object object, long l) {
            return this.zzws.getByte(object, l);
        }
    }

    static abstract class zzd {
        Unsafe zzws;

        zzd(Unsafe unsafe) {
            this.zzws = unsafe;
        }

        public abstract void zza(Object var1, long var2, double var4);

        public abstract void zza(Object var1, long var2, float var4);

        public final void zza(Object object, long l, int n) {
            this.zzws.putInt(object, l, n);
        }

        public final void zza(Object object, long l, long l2) {
            this.zzws.putLong(object, l, l2);
        }

        public abstract void zza(Object var1, long var2, boolean var4);

        public abstract void zze(Object var1, long var2, byte var4);

        public final int zzj(Object object, long l) {
            return this.zzws.getInt(object, l);
        }

        public final long zzk(Object object, long l) {
            return this.zzws.getLong(object, l);
        }

        public abstract boolean zzl(Object var1, long var2);

        public abstract float zzm(Object var1, long var2);

        public abstract double zzn(Object var1, long var2);

        public abstract byte zzx(Object var1, long var2);
    }

}

