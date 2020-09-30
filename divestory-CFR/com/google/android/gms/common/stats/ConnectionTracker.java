/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.util.ClientLibraryUtils;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;

public class ConnectionTracker {
    private static final Object zza = new Object();
    @Nullable
    private static volatile ConnectionTracker zzb;
    private static boolean zzc;
    private ConcurrentHashMap<ServiceConnection, ServiceConnection> zzd = new ConcurrentHashMap();

    static {
        zzc = false;
    }

    private ConnectionTracker() {
    }

    public static ConnectionTracker getInstance() {
        if (zzb != null) return Preconditions.checkNotNull(zzb);
        Object object = zza;
        synchronized (object) {
            ConnectionTracker connectionTracker;
            if (zzb != null) return Preconditions.checkNotNull(zzb);
            zzb = connectionTracker = new ConnectionTracker();
            return Preconditions.checkNotNull(zzb);
        }
    }

    private final boolean zza(Context context, String string2, Intent intent, ServiceConnection serviceConnection, int n, boolean bl) {
        boolean bl2;
        ComponentName componentName = intent.getComponent();
        bl = componentName == null ? false : ClientLibraryUtils.zza(context, componentName.getPackageName());
        if (bl) {
            Log.w((String)"ConnectionTracker", (String)"Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        if (!ConnectionTracker.zza(serviceConnection)) {
            return context.bindService(intent, serviceConnection, n);
        }
        componentName = this.zzd.putIfAbsent(serviceConnection, serviceConnection);
        if (componentName != null && serviceConnection != componentName) {
            Log.w((String)"ConnectionTracker", (String)String.format("Duplicate binding with the same ServiceConnection: %s, %s, %s.", new Object[]{serviceConnection, string2, intent.getAction()}));
        }
        try {
            bl = bl2 = context.bindService(intent, serviceConnection, n);
            if (bl2) return bl;
            this.zzd.remove((Object)serviceConnection, (Object)serviceConnection);
        }
        catch (Throwable throwable) {
            this.zzd.remove((Object)serviceConnection, (Object)serviceConnection);
            throw throwable;
        }
        return bl2;
    }

    private static boolean zza(ServiceConnection serviceConnection) {
        if (serviceConnection instanceof zzk) return false;
        return true;
    }

    public boolean bindService(Context context, Intent intent, ServiceConnection serviceConnection, int n) {
        return this.zza(context, context.getClass().getName(), intent, serviceConnection, n);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void unbindService(Context var1_1, ServiceConnection var2_5) {
        if (!ConnectionTracker.zza(var2_5) || !this.zzd.containsKey((Object)var2_5)) ** GOTO lbl8
        try {
            var3_6 = this.zzd.get((Object)var2_5);
            var1_1.unbindService(var3_6);
            return;
lbl8: // 2 sources:
            var1_1.unbindService(var2_5);
            return;
            catch (IllegalArgumentException | IllegalStateException var1_3) {
                return;
            }
        }
        finally {
            this.zzd.remove((Object)var2_5);
        }
        catch (IllegalArgumentException | IllegalStateException var1_4) {
            return;
        }
    }

    public void unbindServiceSafe(Context context, ServiceConnection serviceConnection) {
        try {
            this.unbindService(context, serviceConnection);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.w((String)"ConnectionTracker", (String)"Exception thrown while unbinding", (Throwable)illegalArgumentException);
            return;
        }
    }

    public final boolean zza(Context context, String string2, Intent intent, ServiceConnection serviceConnection, int n) {
        return this.zza(context, string2, intent, serviceConnection, n, true);
    }
}

