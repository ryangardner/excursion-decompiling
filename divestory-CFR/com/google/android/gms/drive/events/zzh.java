/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.drive.events;

import android.os.Looper;
import com.google.android.gms.drive.events.DriveEventService;
import java.util.concurrent.CountDownLatch;

final class zzh
extends Thread {
    private final /* synthetic */ CountDownLatch zzcn;
    private final /* synthetic */ DriveEventService zzco;

    zzh(DriveEventService driveEventService, CountDownLatch countDownLatch) {
        this.zzco = driveEventService;
        this.zzcn = countDownLatch;
    }

    @Override
    public final void run() {
        try {
            DriveEventService.zza zza2;
            Looper.prepare();
            DriveEventService driveEventService = this.zzco;
            driveEventService.zzck = zza2 = new DriveEventService.zza(this.zzco, null);
            this.zzco.zzcl = false;
            this.zzcn.countDown();
            Looper.loop();
            return;
        }
        finally {
            if (DriveEventService.zzb(this.zzco) != null) {
                DriveEventService.zzb(this.zzco).countDown();
            }
        }
    }
}

