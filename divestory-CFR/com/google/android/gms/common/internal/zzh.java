/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzi;
import java.util.HashMap;

final class zzh
implements Handler.Callback {
    private final /* synthetic */ zzg zza;

    private zzh(zzg zzg2) {
        this.zza = zzg2;
    }

    /* synthetic */ zzh(zzg zzg2, zzf zzf2) {
        this(zzg2);
    }

    public final boolean handleMessage(Message object) {
        int n = object.what;
        if (n != 0) {
            if (n != 1) {
                return false;
            }
            HashMap hashMap = zzg.zza(this.zza);
            synchronized (hashMap) {
                GmsClientSupervisor.zza zza2 = (GmsClientSupervisor.zza)object.obj;
                zzi zzi2 = (zzi)zzg.zza(this.zza).get(zza2);
                if (zzi2 == null) return true;
                if (zzi2.zzb() != 3) return true;
                object = String.valueOf(zza2);
                n = String.valueOf(object).length();
                CharSequence charSequence = new StringBuilder(n + 47);
                charSequence.append("Timeout waiting for ServiceConnection callback ");
                charSequence.append((String)object);
                charSequence = charSequence.toString();
                object = new Exception();
                Log.e((String)"GmsClientSupervisor", (String)charSequence, (Throwable)object);
                charSequence = zzi2.zze();
                object = charSequence;
                if (charSequence == null) {
                    object = zza2.zzb();
                }
                charSequence = object;
                if (object == null) {
                    charSequence = new ComponentName(Preconditions.checkNotNull(zza2.zza()), "unknown");
                }
                zzi2.onServiceDisconnected((ComponentName)charSequence);
                return true;
            }
        }
        HashMap hashMap = zzg.zza(this.zza);
        synchronized (hashMap) {
            object = (GmsClientSupervisor.zza)object.obj;
            zzi zzi3 = (zzi)zzg.zza(this.zza).get(object);
            if (zzi3 == null) return true;
            if (!zzi3.zzc()) return true;
            if (zzi3.zza()) {
                zzi3.zzb("GmsClientSupervisor");
            }
            zzg.zza(this.zza).remove(object);
            return true;
        }
    }
}

