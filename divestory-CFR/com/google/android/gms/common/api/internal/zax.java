/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.common.internal.Preconditions;

public class zax
extends zak {
    private final ArraySet<ApiKey<?>> zad = new ArraySet();
    private final GoogleApiManager zae;

    private zax(LifecycleFragment lifecycleFragment, GoogleApiManager googleApiManager) {
        this(lifecycleFragment, googleApiManager, GoogleApiAvailability.getInstance());
    }

    private zax(LifecycleFragment lifecycleFragment, GoogleApiManager googleApiManager, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment, googleApiAvailability);
        this.zae = googleApiManager;
        this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
    }

    public static void zaa(Activity object, GoogleApiManager googleApiManager, ApiKey<?> apiKey) {
        LifecycleFragment lifecycleFragment = zax.getFragment(object);
        zax zax2 = lifecycleFragment.getCallbackOrNull("ConnectionlessLifecycleHelper", zax.class);
        object = zax2;
        if (zax2 == null) {
            object = new zax(lifecycleFragment, googleApiManager);
        }
        Preconditions.checkNotNull(apiKey, "ApiKey cannot be null");
        object.zad.add(apiKey);
        googleApiManager.zaa((zax)object);
    }

    private final void zad() {
        if (this.zad.isEmpty()) return;
        this.zae.zaa(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.zad();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.zad();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.zae.zab(this);
    }

    @Override
    protected final void zaa() {
        this.zae.zac();
    }

    @Override
    protected final void zaa(ConnectionResult connectionResult, int n) {
        this.zae.zab(connectionResult, n);
    }

    final ArraySet<ApiKey<?>> zac() {
        return this.zad;
    }
}

