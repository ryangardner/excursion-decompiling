/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingServiceConnection
implements ServiceConnection {
    private boolean zza = false;
    private final BlockingQueue<IBinder> zzb = new LinkedBlockingQueue<IBinder>();

    public IBinder getService() throws InterruptedException {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getService() called on main thread");
        if (this.zza) throw new IllegalStateException("Cannot call get on this connection more than once");
        this.zza = true;
        return this.zzb.take();
    }

    public IBinder getServiceWithTimeout(long l, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.zza) throw new IllegalStateException("Cannot call get on this connection more than once");
        this.zza = true;
        if ((timeUnit = this.zzb.poll(l, timeUnit)) == null) throw new TimeoutException("Timed out waiting for the service connection");
        return timeUnit;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzb.add(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}

