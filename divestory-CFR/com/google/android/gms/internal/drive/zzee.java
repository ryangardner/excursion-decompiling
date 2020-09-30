/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Pair
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.internal.drive.zzef;
import com.google.android.gms.internal.drive.zzeg;
import com.google.android.gms.internal.drive.zzet;
import com.google.android.gms.internal.drive.zzfp;
import java.util.ArrayList;
import java.util.List;

public final class zzee
extends zzet {
    private static final GmsLogger zzbz = new GmsLogger("EventCallback", "");
    private final int zzda;
    private final zzi zzgt;
    private final zzeg zzgu;
    private final List<Integer> zzgv = new ArrayList<Integer>();

    public zzee(Looper looper, Context context, int n, zzi zzi2) {
        this.zzda = 1;
        this.zzgt = zzi2;
        this.zzgu = new zzeg(looper, context, null);
    }

    static /* synthetic */ GmsLogger zzai() {
        return zzbz;
    }

    @Override
    public final void zzc(zzfp object) throws RemoteException {
        DriveEvent driveEvent = object.zzat();
        boolean bl = this.zzda == driveEvent.getType();
        Preconditions.checkState(bl);
        Preconditions.checkState(this.zzgv.contains(driveEvent.getType()));
        object = this.zzgu;
        object.sendMessage(object.obtainMessage(1, (Object)new Pair((Object)this.zzgt, (Object)driveEvent)));
    }

    public final void zzf(int n) {
        this.zzgv.add(1);
    }

    public final boolean zzg(int n) {
        return this.zzgv.contains(1);
    }
}

