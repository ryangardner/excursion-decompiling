/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Message;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.internal.Preconditions;

final class zacj
implements Runnable {
    private final /* synthetic */ Result zaa;
    private final /* synthetic */ zack zab;

    zacj(zack zack2, Result result) {
        this.zab = zack2;
        this.zaa = result;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void run() {
        Throwable throwable2222;
        BasePendingResult.zaa.set(true);
        Object object = Preconditions.checkNotNull(zack.zaa(this.zab)).onSuccess(this.zaa);
        zack.zab(this.zab).sendMessage(zack.zab(this.zab).obtainMessage(0, object));
        BasePendingResult.zaa.set(false);
        zack.zaa(this.zab, this.zaa);
        object = (GoogleApiClient)zack.zac(this.zab).get();
        if (object == null) return;
        ((GoogleApiClient)object).zab(this.zab);
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                zack.zab(this.zab).sendMessage(zack.zab(this.zab).obtainMessage(1, (Object)runtimeException));
                BasePendingResult.zaa.set(false);
            }
            zack.zaa(this.zab, this.zaa);
            GoogleApiClient googleApiClient = (GoogleApiClient)zack.zac(this.zab).get();
            if (googleApiClient == null) return;
            googleApiClient.zab(this.zab);
            return;
        }
        BasePendingResult.zaa.set(false);
        zack.zaa(this.zab, this.zaa);
        GoogleApiClient googleApiClient = (GoogleApiClient)zack.zac(this.zab).get();
        if (googleApiClient == null) throw throwable2222;
        googleApiClient.zab(this.zab);
        throw throwable2222;
    }
}

