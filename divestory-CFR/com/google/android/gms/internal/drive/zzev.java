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
import com.google.android.gms.internal.drive.zzeu;
import com.google.android.gms.internal.drive.zzew;

public abstract class zzev
extends zzb
implements zzeu {
    public static zzeu zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IEventReleaseCallback");
        if (!(iInterface instanceof zzeu)) return new zzew(iBinder);
        return (zzeu)iInterface;
    }
}

