/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzit;
import com.google.android.gms.internal.drive.zziu;
import com.google.android.gms.internal.drive.zziv;
import com.google.android.gms.internal.drive.zziz;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzjt;
import com.google.android.gms.internal.drive.zzjv;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlr;
import com.google.android.gms.internal.drive.zzls;
import com.google.android.gms.internal.drive.zzlt;
import com.google.android.gms.internal.drive.zzmd;
import com.google.android.gms.internal.drive.zzme;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmw;
import com.google.android.gms.internal.drive.zzmy;
import com.google.android.gms.internal.drive.zznd;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzkk<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>>
extends zzit<MessageType, BuilderType> {
    private static Map<Object, zzkk<?, ?>> zzrs = new ConcurrentHashMap();
    protected zzmy zzrq = zzmy.zzfa();
    private int zzrr = -1;

    private static <T extends zzkk<T, ?>> T zza(T object, byte[] object2, int n, int n2, zzjx zzjx2) throws zzkq {
        object = (zzkk)((zzkk)object).zza(zze.zzsa, null, null);
        try {
            zzmf<Object> zzmf2 = zzmd.zzej().zzq(object);
            zziz zziz2 = new zziz(zzjx2);
            zzmf2.zza(object, (byte[])object2, 0, n2, zziz2);
            ((zzkk)object).zzbp();
            if (((zzkk)object).zzne == 0) {
                return (T)object;
            }
            object2 = new RuntimeException();
            throw object2;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw zzkq.zzdi().zzg((zzlq)object);
        }
        catch (IOException iOException) {
            if (!(iOException.getCause() instanceof zzkq)) throw new zzkq(iOException.getMessage()).zzg((zzlq)object);
            throw (zzkq)iOException.getCause();
        }
    }

    protected static <T extends zzkk<T, ?>> T zza(T t, byte[] arrby, zzjx zzjx2) throws zzkq {
        if ((t = zzkk.zza(t, arrby, 0, arrby.length, zzjx2)) == null) return t;
        if (!((zzkk)t).isInitialized()) throw new zzkq(new zzmw(t).getMessage()).zzg(t);
        return t;
    }

    protected static Object zza(zzlq zzlq2, String string2, Object[] arrobject) {
        return new zzme(zzlq2, string2, arrobject);
    }

    static Object zza(Method object, Object object2, Object ... arrobject) {
        try {
            return ((Method)object).invoke(object2, arrobject);
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
            if (!(throwable instanceof Error)) throw new RuntimeException("Unexpected exception thrown by generated accessor method.", throwable);
            throw (Error)throwable;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", illegalAccessException);
        }
    }

    protected static <T extends zzkk<?, ?>> void zza(Class<T> class_, T t) {
        zzrs.put(class_, t);
    }

    protected static final <T extends zzkk<T, ?>> boolean zza(T t, boolean bl) {
        int n = ((Byte)((zzkk)t).zza(zze.zzrx, null, null)).byteValue();
        if (n == 1) {
            return true;
        }
        if (n == 0) {
            return false;
        }
        boolean bl2 = zzmd.zzej().zzq(t).zzp(t);
        if (!bl) return bl2;
        n = zze.zzry;
        Object object = bl2 ? t : null;
        ((zzkk)t).zza(n, object, null);
        return bl2;
    }

    static <T extends zzkk<?, ?>> T zzd(Class<T> class_) {
        zzkk zzkk2;
        zzkk zzkk3 = zzkk2 = zzrs.get(class_);
        if (zzkk2 == null) {
            try {
                Class.forName(class_.getName(), true, class_.getClassLoader());
                zzkk3 = zzrs.get(class_);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new IllegalStateException("Class initialization cannot fail.", classNotFoundException);
            }
        }
        zzkk2 = zzkk3;
        if (zzkk3 != null) return (T)zzkk2;
        zzkk2 = (zzkk)((zzkk)zznd.zzh(class_)).zza(zze.zzsc, null, null);
        if (zzkk2 == null) throw new IllegalStateException();
        zzrs.put(class_, zzkk2);
        return (T)zzkk2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (((zzkk)this.zza(zze.zzsc, null, null)).getClass().isInstance(object)) return zzmd.zzej().zzq(this).equals(this, (zzkk)object);
        return false;
    }

    public int hashCode() {
        if (this.zzne != 0) {
            return this.zzne;
        }
        this.zzne = zzmd.zzej().zzq(this).hashCode(this);
        return this.zzne;
    }

    @Override
    public final boolean isInitialized() {
        return zzkk.zza(this, Boolean.TRUE);
    }

    public String toString() {
        return zzlt.zza(this, Object.super.toString());
    }

    protected abstract Object zza(int var1, Object var2, Object var3);

    @Override
    public final void zzb(zzjr zzjr2) throws IOException {
        zzmd.zzej().zzf(this.getClass()).zza(this, zzjt.zza(zzjr2));
    }

    @Override
    final int zzbm() {
        return this.zzrr;
    }

    protected final void zzbp() {
        zzmd.zzej().zzq(this).zzd(this);
    }

    protected final <MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzcw() {
        return (BuilderType)((zza)this.zza(zze.zzsb, null, null));
    }

    @Override
    public final int zzcx() {
        if (this.zzrr != -1) return this.zzrr;
        this.zzrr = zzmd.zzej().zzq(this).zzn(this);
        return this.zzrr;
    }

    @Override
    public final /* synthetic */ zzlr zzcy() {
        zza zza2 = (zza)this.zza(zze.zzsb, null, null);
        zza2.zza(this);
        return zza2;
    }

    @Override
    public final /* synthetic */ zzlr zzcz() {
        return (zza)this.zza(zze.zzsb, null, null);
    }

    @Override
    public final /* synthetic */ zzlq zzda() {
        return (zzkk)this.zza(zze.zzsc, null, null);
    }

    @Override
    final void zzo(int n) {
        this.zzrr = n;
    }

    public static class zza<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>>
    extends zziu<MessageType, BuilderType> {
        private final MessageType zzrt;
        protected MessageType zzru;
        private boolean zzrv;

        protected zza(MessageType MessageType) {
            this.zzrt = MessageType;
            this.zzru = (zzkk)((zzkk)MessageType).zza(zze.zzsa, null, null);
            this.zzrv = false;
        }

        private static void zza(MessageType MessageType, MessageType MessageType2) {
            zzmd.zzej().zzq(MessageType).zzc(MessageType, MessageType2);
        }

        @Override
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza2 = (zza)((zzkk)this.zzrt).zza(zze.zzsb, null, null);
            zza2.zza((MessageType)((zzkk)this.zzde()));
            return zza2;
        }

        @Override
        public final boolean isInitialized() {
            return zzkk.zza(this.zzru, false);
        }

        @Override
        protected final /* synthetic */ zziu zza(zzit zzit2) {
            return this.zza((MessageType)((zzkk)zzit2));
        }

        @Override
        public final BuilderType zza(MessageType MessageType) {
            this.zzdb();
            zza.zza(this.zzru, MessageType);
            return (BuilderType)this;
        }

        @Override
        public final /* synthetic */ zziu zzbn() {
            return (zza)((zziu)this).clone();
        }

        @Override
        public final /* synthetic */ zzlq zzda() {
            return this.zzrt;
        }

        protected final void zzdb() {
            if (!this.zzrv) return;
            zzkk zzkk2 = (zzkk)((zzkk)this.zzru).zza(zze.zzsa, null, null);
            zza.zza(zzkk2, this.zzru);
            this.zzru = zzkk2;
            this.zzrv = false;
        }

        public MessageType zzdc() {
            if (this.zzrv) {
                return this.zzru;
            }
            ((zzkk)this.zzru).zzbp();
            this.zzrv = true;
            return this.zzru;
        }

        public final MessageType zzdd() {
            zzkk zzkk2 = (zzkk)this.zzde();
            if (!zzkk2.isInitialized()) throw new zzmw(zzkk2);
            return (MessageType)zzkk2;
        }

        @Override
        public /* synthetic */ zzlq zzde() {
            return this.zzdc();
        }

        @Override
        public /* synthetic */ zzlq zzdf() {
            return this.zzdd();
        }
    }

    public static final class zzb<T extends zzkk<T, ?>>
    extends zziv<T> {
        private final T zzrt;

        public zzb(T t) {
            this.zzrt = t;
        }
    }

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType>
    extends zzkk<MessageType, BuilderType>
    implements zzls {
        protected zzkb<Object> zzrw = zzkb.zzcn();

        final zzkb<Object> zzdg() {
            if (!this.zzrw.isImmutable()) return this.zzrw;
            this.zzrw = (zzkb)this.zzrw.clone();
            return this.zzrw;
        }
    }

    public static final class zzd<ContainingType extends zzlq, Type>
    extends zzjv<ContainingType, Type> {
    }

    public static final class zze {
        public static final /* enum */ int zzrx = 1;
        public static final /* enum */ int zzry = 2;
        public static final /* enum */ int zzrz = 3;
        public static final /* enum */ int zzsa = 4;
        public static final /* enum */ int zzsb = 5;
        public static final /* enum */ int zzsc = 6;
        public static final /* enum */ int zzsd = 7;
        private static final /* synthetic */ int[] zzse;
        public static final /* enum */ int zzsf = 1;
        public static final /* enum */ int zzsg = 2;
        private static final /* synthetic */ int[] zzsh;
        public static final /* enum */ int zzsi = 1;
        public static final /* enum */ int zzsj = 2;
        private static final /* synthetic */ int[] zzsk;

        static {
            zzse = new int[]{1, 2, 3, 4, 5, 6, 7};
            zzsh = new int[]{1, 2};
            zzsk = new int[]{1, 2};
        }

        public static int[] zzdh() {
            return (int[])zzse.clone();
        }
    }

}

