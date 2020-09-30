/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.drive.zzb;
import com.google.android.gms.internal.drive.zzio;
import com.google.android.gms.internal.drive.zziq;

public final class zzip
extends zzb
implements zzio {
    public static zzio zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.realtime.internal.IRealtimeService");
        if (!(iInterface instanceof zzio)) return new zziq(iBinder);
        return (zzio)iInterface;
    }
}

