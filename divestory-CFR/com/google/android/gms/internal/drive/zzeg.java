/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Pair
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzb;
import com.google.android.gms.drive.events.zzd;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.drive.events.zzk;
import com.google.android.gms.drive.events.zzl;
import com.google.android.gms.drive.events.zzn;
import com.google.android.gms.drive.events.zzo;
import com.google.android.gms.drive.events.zzq;
import com.google.android.gms.drive.events.zzr;
import com.google.android.gms.internal.drive.zze;
import com.google.android.gms.internal.drive.zzee;
import com.google.android.gms.internal.drive.zzef;
import com.google.android.gms.internal.drive.zzeh;
import com.google.android.gms.internal.drive.zzh;
import com.google.android.gms.internal.drive.zzir;

final class zzeg
extends zzir {
    private final Context zzgw;

    private zzeg(Looper looper, Context context) {
        super(looper);
        this.zzgw = context;
    }

    /* synthetic */ zzeg(Looper looper, Context context, zzef zzef2) {
        this(looper, context);
    }

    public final void handleMessage(Message object) {
        if (((Message)object).what != 1) {
            zzee.zzai().efmt("EventCallback", "Don't know how to handle this event in context %s", new Object[]{this.zzgw});
            return;
        }
        Object object2 = (Pair)((Message)object).obj;
        object = (zzi)((Pair)object2).first;
        object2 = (DriveEvent)((Pair)object2).second;
        int n = object2.getType();
        if (n == 1) {
            ((ChangeListener)object).onChange((ChangeEvent)object2);
            return;
        }
        if (n == 2) {
            ((CompletionListener)object).onCompletion((CompletionEvent)object2);
            return;
        }
        if (n != 3) {
            if (n == 4) {
                ((zzd)object).zza((zzb)object2);
                return;
            }
            if (n != 8) {
                zzee.zzai().wfmt("EventCallback", "Unexpected event: %s", object2);
                return;
            }
            object2 = new zze(((zzr)object2).zzac());
            ((zzl)object).zza((zzk)object2);
            return;
        }
        object = (zzq)object;
        DataHolder dataHolder = ((zzo)(object2 = (zzo)object2)).zzz();
        if (dataHolder != null) {
            object.zza(new zzeh(new MetadataBuffer(dataHolder)));
        }
        if (!((zzo)object2).zzaa()) return;
        object.zzc(((zzo)object2).zzab());
    }
}

