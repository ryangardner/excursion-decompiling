/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zabo
extends zak {
    private TaskCompletionSource<Void> zad = new TaskCompletionSource();

    private zabo(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
    }

    public static zabo zaa(Activity object) {
        LifecycleFragment lifecycleFragment = zabo.getFragment(object);
        object = lifecycleFragment.getCallbackOrNull("GmsAvailabilityHelper", zabo.class);
        if (object == null) return new zabo(lifecycleFragment);
        if (!object.zad.getTask().isComplete()) return object;
        object.zad = new TaskCompletionSource();
        return object;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.zad.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    @Override
    protected final void zaa() {
        Activity activity = this.mLifecycleFragment.getLifecycleActivity();
        if (activity == null) {
            this.zad.trySetException(new ApiException(new Status(8)));
            return;
        }
        int n = this.zac.isGooglePlayServicesAvailable((Context)activity);
        if (n == 0) {
            this.zad.trySetResult(null);
            return;
        }
        if (this.zad.getTask().isComplete()) return;
        this.zab(new ConnectionResult(n, null), 0);
    }

    @Override
    protected final void zaa(ConnectionResult connectionResult, int n) {
        String string2;
        String string3 = string2 = connectionResult.getErrorMessage();
        if (string2 == null) {
            string3 = "Error connecting to Google Play services";
        }
        this.zad.setException(new ApiException(new Status(connectionResult, string3, connectionResult.getErrorCode())));
    }

    public final Task<Void> zac() {
        return this.zad.getTask();
    }
}

