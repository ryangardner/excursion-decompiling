/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class zzi
implements ServiceConnection,
zzk {
    private final Map<ServiceConnection, ServiceConnection> zza;
    private int zzb;
    private boolean zzc;
    private IBinder zzd;
    private final GmsClientSupervisor.zza zze;
    private ComponentName zzf;
    private final /* synthetic */ zzg zzg;

    public zzi(zzg zzg2, GmsClientSupervisor.zza zza2) {
        this.zzg = zzg2;
        this.zze = zza2;
        this.zza = new HashMap<ServiceConnection, ServiceConnection>();
        this.zzb = 2;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HashMap hashMap = zzg.zza(this.zzg);
        synchronized (hashMap) {
            zzg.zzb(this.zzg).removeMessages(1, (Object)this.zze);
            this.zzd = iBinder;
            this.zzf = componentName;
            Iterator<ServiceConnection> iterator2 = this.zza.values().iterator();
            do {
                if (!iterator2.hasNext()) {
                    this.zzb = 1;
                    return;
                }
                iterator2.next().onServiceConnected(componentName, iBinder);
            } while (true);
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        HashMap hashMap = zzg.zza(this.zzg);
        synchronized (hashMap) {
            zzg.zzb(this.zzg).removeMessages(1, (Object)this.zze);
            this.zzd = null;
            this.zzf = componentName;
            Iterator<ServiceConnection> iterator2 = this.zza.values().iterator();
            do {
                if (!iterator2.hasNext()) {
                    this.zzb = 2;
                    return;
                }
                iterator2.next().onServiceDisconnected(componentName);
            } while (true);
        }
    }

    public final void zza(ServiceConnection serviceConnection, ServiceConnection serviceConnection2, String string2) {
        this.zza.put(serviceConnection, serviceConnection2);
    }

    public final void zza(ServiceConnection serviceConnection, String string2) {
        this.zza.remove((Object)serviceConnection);
    }

    public final void zza(String string2) {
        boolean bl;
        this.zzb = 3;
        this.zzc = bl = zzg.zzd(this.zzg).zza(zzg.zzc(this.zzg), string2, this.zze.zza(zzg.zzc(this.zzg)), this, this.zze.zzc());
        if (bl) {
            string2 = zzg.zzb(this.zzg).obtainMessage(1, (Object)this.zze);
            zzg.zzb(this.zzg).sendMessageDelayed((Message)string2, zzg.zze(this.zzg));
            return;
        }
        this.zzb = 2;
        try {
            zzg.zzd(this.zzg).unbindService(zzg.zzc(this.zzg), this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public final boolean zza() {
        return this.zzc;
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zza.containsKey((Object)serviceConnection);
    }

    public final int zzb() {
        return this.zzb;
    }

    public final void zzb(String string2) {
        zzg.zzb(this.zzg).removeMessages(1, (Object)this.zze);
        zzg.zzd(this.zzg).unbindService(zzg.zzc(this.zzg), this);
        this.zzc = false;
        this.zzb = 2;
    }

    public final boolean zzc() {
        return this.zza.isEmpty();
    }

    public final IBinder zzd() {
        return this.zzd;
    }

    public final ComponentName zze() {
        return this.zzf;
    }
}

