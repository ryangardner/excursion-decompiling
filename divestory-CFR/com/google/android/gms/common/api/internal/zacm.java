/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zabz;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zap;

final class zacm
extends zap {
    private final /* synthetic */ zack zaa;

    public zacm(zack zack2, Looper looper) {
        this.zaa = zack2;
        super(looper);
    }

    public final void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 0) {
            if (n != 1) {
                n = ((Message)object).what;
                object = new StringBuilder(70);
                ((StringBuilder)object).append("TransformationResultHandler received unknown message type: ");
                ((StringBuilder)object).append(n);
                Log.e((String)"TransformedResultImpl", (String)((StringBuilder)object).toString());
                return;
            }
            RuntimeException runtimeException = (RuntimeException)((Message)object).obj;
            object = String.valueOf(runtimeException.getMessage());
            object = ((String)object).length() != 0 ? "Runtime exception on the transformation worker thread: ".concat((String)object) : new String("Runtime exception on the transformation worker thread: ");
            Log.e((String)"TransformedResultImpl", (String)object);
            throw runtimeException;
        }
        Object object2 = (PendingResult)((Message)object).obj;
        object = zack.zad(this.zaa);
        synchronized (object) {
            zack zack2 = Preconditions.checkNotNull(zack.zae(this.zaa));
            if (object2 == null) {
                object2 = new Status(13, "Transform returned null");
                zack.zaa(zack2, (Status)object2);
            } else if (object2 instanceof zabz) {
                zack.zaa(zack2, ((zabz)object2).zaa());
            } else {
                zack2.zaa((PendingResult<?>)object2);
            }
            return;
        }
    }
}

