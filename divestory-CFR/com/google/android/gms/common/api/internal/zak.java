/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zal;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.internal.base.zap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zak
extends LifecycleCallback
implements DialogInterface.OnCancelListener {
    protected volatile boolean zaa;
    protected final AtomicReference<zam> zab = new AtomicReference<Object>(null);
    protected final GoogleApiAvailability zac;
    private final Handler zad = new zap(Looper.getMainLooper());

    protected zak(LifecycleFragment lifecycleFragment) {
        this(lifecycleFragment, GoogleApiAvailability.getInstance());
    }

    zak(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zac = googleApiAvailability;
    }

    private static int zaa(zam zam2) {
        if (zam2 != null) return zam2.zaa();
        return -1;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onActivityResult(int var1_1, int var2_2, Intent var3_3) {
        block11 : {
            block9 : {
                block10 : {
                    var4_4 = this.zab.get();
                    var5_5 = 1;
                    var6_6 = 1;
                    if (var1_1 == 1) break block9;
                    if (var1_1 == 2) break block10;
                    var7_7 = var4_4;
                    ** GOTO lbl35
                }
                var5_5 = this.zac.isGooglePlayServicesAvailable((Context)this.getActivity());
                var1_1 = var5_5 == 0 ? var6_6 : 0;
                if (var4_4 == null) {
                    return;
                }
                var7_7 = var4_4;
                var2_2 = var1_1;
                if (var4_4.zab().getErrorCode() == 18) {
                    var7_7 = var4_4;
                    var2_2 = var1_1;
                    if (var5_5 == 18) {
                        return;
                    }
                }
                break block11;
            }
            if (var2_2 == -1) {
                var7_7 = var4_4;
                var2_2 = var5_5;
            } else {
                var7_7 = var4_4;
                if (var2_2 == 0) {
                    if (var4_4 == null) {
                        return;
                    }
                    var1_1 = 13;
                    if (var3_3 != null) {
                        var1_1 = var3_3.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
                    }
                    var7_7 = new zam(new ConnectionResult(var1_1, null, var4_4.zab().toString()), zak.zaa(var4_4));
                    this.zab.set(var7_7);
                }
lbl35: // 4 sources:
                var2_2 = 0;
            }
        }
        if (var2_2 != 0) {
            this.zab();
            return;
        }
        if (var7_7 == null) return;
        this.zaa(var7_7.zab(), var7_7.zaa());
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zaa(new ConnectionResult(13, null), zak.zaa(this.zab.get()));
        this.zab();
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        if (object == null) return;
        AtomicReference<zam> atomicReference = this.zab;
        object = object.getBoolean("resolving_error", false) ? new zam(new ConnectionResult(object.getInt("failed_status"), (PendingIntent)object.getParcelable("failed_resolution")), object.getInt("failed_client_id", -1)) : null;
        atomicReference.set((zam)object);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zam zam2 = this.zab.get();
        if (zam2 == null) return;
        bundle.putBoolean("resolving_error", true);
        bundle.putInt("failed_client_id", zam2.zaa());
        bundle.putInt("failed_status", zam2.zab().getErrorCode());
        bundle.putParcelable("failed_resolution", (Parcelable)zam2.zab().getResolution());
    }

    @Override
    public void onStart() {
        super.onStart();
        this.zaa = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.zaa = false;
    }

    protected abstract void zaa();

    protected abstract void zaa(ConnectionResult var1, int var2);

    protected final void zab() {
        this.zab.set(null);
        this.zaa();
    }

    public final void zab(ConnectionResult object, int n) {
        if (!this.zab.compareAndSet(null, (zam)(object = new zam((ConnectionResult)object, n)))) return;
        this.zad.post((Runnable)new zal(this, (zam)object));
    }
}

