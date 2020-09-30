/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzka;
import com.google.android.gms.internal.drive.zzkj;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzla;
import com.google.android.gms.internal.drive.zzlg;
import com.google.android.gms.internal.drive.zzlh;
import com.google.android.gms.internal.drive.zzln;
import com.google.android.gms.internal.drive.zzlo;
import com.google.android.gms.internal.drive.zzlp;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlu;
import com.google.android.gms.internal.drive.zzlw;
import com.google.android.gms.internal.drive.zzma;
import com.google.android.gms.internal.drive.zzmf;
import com.google.android.gms.internal.drive.zzmg;
import com.google.android.gms.internal.drive.zzmh;
import java.lang.reflect.Method;

final class zzlf
implements zzmg {
    private static final zzlp zzts = new zzlg();
    private final zzlp zztr;

    public zzlf() {
        this(new zzlh(zzkj.zzcv(), zzlf.zzdv()));
    }

    private zzlf(zzlp zzlp2) {
        this.zztr = zzkm.zza(zzlp2, "messageInfoFactory");
    }

    private static boolean zza(zzlo zzlo2) {
        if (zzlo2.zzec() != zzkk.zze.zzsf) return false;
        return true;
    }

    private static zzlp zzdv() {
        try {
            return (zzlp)Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            return zzts;
        }
    }

    @Override
    public final <T> zzmf<T> zze(Class<T> class_) {
        zzmh.zzg(class_);
        zzlo zzlo2 = this.zztr.zzc(class_);
        if (zzlo2.zzed()) {
            if (!zzkk.class.isAssignableFrom(class_)) return zzlw.zza(zzmh.zzem(), zzka.zzcm(), zzlo2.zzee());
            return zzlw.zza(zzmh.zzeo(), zzka.zzcl(), zzlo2.zzee());
        }
        if (zzkk.class.isAssignableFrom(class_)) {
            if (!zzlf.zza(zzlo2)) return zzlu.zza(class_, zzlo2, zzma.zzeh(), zzla.zzdu(), zzmh.zzeo(), null, zzln.zzea());
            return zzlu.zza(class_, zzlo2, zzma.zzeh(), zzla.zzdu(), zzmh.zzeo(), zzka.zzcl(), zzln.zzea());
        }
        if (!zzlf.zza(zzlo2)) return zzlu.zza(class_, zzlo2, zzma.zzeg(), zzla.zzdt(), zzmh.zzen(), null, zzln.zzdz());
        return zzlu.zza(class_, zzlo2, zzma.zzeg(), zzla.zzdt(), zzmh.zzem(), zzka.zzcm(), zzln.zzdz());
    }
}

