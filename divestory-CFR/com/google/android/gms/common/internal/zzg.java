/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashMap;

final class zzg
extends GmsClientSupervisor {
    private final HashMap<GmsClientSupervisor.zza, zzi> zza = new HashMap();
    private final Context zzb;
    private final Handler zzc;
    private final ConnectionTracker zzd;
    private final long zze;
    private final long zzf;

    zzg(Context context) {
        this.zzb = context.getApplicationContext();
        this.zzc = new com.google.android.gms.internal.common.zzi(context.getMainLooper(), new zzh(this, null));
        this.zzd = ConnectionTracker.getInstance();
        this.zze = 5000L;
        this.zzf = 300000L;
    }

    static /* synthetic */ HashMap zza(zzg zzg2) {
        return zzg2.zza;
    }

    static /* synthetic */ Handler zzb(zzg zzg2) {
        return zzg2.zzc;
    }

    static /* synthetic */ Context zzc(zzg zzg2) {
        return zzg2.zzb;
    }

    static /* synthetic */ ConnectionTracker zzd(zzg zzg2) {
        return zzg2.zzd;
    }

    static /* synthetic */ long zze(zzg zzg2) {
        return zzg2.zzf;
    }

    @Override
    protected final boolean zza(GmsClientSupervisor.zza object, ServiceConnection object2, String string2) {
        Preconditions.checkNotNull(object2, "ServiceConnection must not be null");
        HashMap<GmsClientSupervisor.zza, zzi> hashMap = this.zza;
        synchronized (hashMap) {
            zzi zzi2 = this.zza.get(object);
            if (zzi2 == null) {
                zzi2 = new zzi(this, (GmsClientSupervisor.zza)object);
                zzi2.zza((ServiceConnection)object2, (ServiceConnection)object2, string2);
                zzi2.zza(string2);
                this.zza.put((GmsClientSupervisor.zza)object, zzi2);
                object = zzi2;
                return ((zzi)object).zza();
            } else {
                this.zzc.removeMessages(0, object);
                if (zzi2.zza((ServiceConnection)object2)) {
                    string2 = String.valueOf(object);
                    int n = String.valueOf(string2).length();
                    object = new StringBuilder(n + 81);
                    ((StringBuilder)object).append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    ((StringBuilder)object).append(string2);
                    object2 = new IllegalStateException(((StringBuilder)object).toString());
                    throw object2;
                }
                zzi2.zza((ServiceConnection)object2, (ServiceConnection)object2, string2);
                int n = zzi2.zzb();
                if (n != 1) {
                    if (n != 2) {
                        object = zzi2;
                        return ((zzi)object).zza();
                    } else {
                        zzi2.zza(string2);
                        object = zzi2;
                    }
                    return ((zzi)object).zza();
                } else {
                    object2.onServiceConnected(zzi2.zze(), zzi2.zzd());
                    object = zzi2;
                }
            }
            return ((zzi)object).zza();
        }
    }

    @Override
    protected final void zzb(GmsClientSupervisor.zza object, ServiceConnection object2, String charSequence) {
        Preconditions.checkNotNull(object2, "ServiceConnection must not be null");
        HashMap<GmsClientSupervisor.zza, zzi> hashMap = this.zza;
        synchronized (hashMap) {
            zzi zzi2 = this.zza.get(object);
            if (zzi2 == null) {
                object = String.valueOf(object);
                int n = String.valueOf(object).length();
                charSequence = new StringBuilder(n + 50);
                ((StringBuilder)charSequence).append("Nonexistent connection status for service config: ");
                ((StringBuilder)charSequence).append((String)object);
                object2 = new IllegalStateException(((StringBuilder)charSequence).toString());
                throw object2;
            }
            if (zzi2.zza((ServiceConnection)object2)) {
                zzi2.zza((ServiceConnection)object2, (String)charSequence);
                if (!zzi2.zzc()) return;
                object = this.zzc.obtainMessage(0, object);
                this.zzc.sendMessageDelayed((Message)object, this.zze);
                return;
            }
            charSequence = String.valueOf(object);
            int n = String.valueOf(charSequence).length();
            object = new StringBuilder(n + 76);
            ((StringBuilder)object).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
            ((StringBuilder)object).append((String)charSequence);
            object2 = new IllegalStateException(((StringBuilder)object).toString());
            throw object2;
        }
    }
}

