/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zabj;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.common.api.internal.zan;
import com.google.android.gms.common.internal.Preconditions;

final class zal
implements Runnable {
    final /* synthetic */ zak zaa;
    private final zam zab;

    zal(zak zak2, zam zam2) {
        this.zaa = zak2;
        this.zab = zam2;
    }

    @Override
    public final void run() {
        if (!this.zaa.zaa) {
            return;
        }
        ConnectionResult connectionResult = this.zab.zab();
        if (connectionResult.hasResolution()) {
            this.zaa.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa((Context)this.zaa.getActivity(), Preconditions.checkNotNull(connectionResult.getResolution()), this.zab.zaa(), false), 1);
            return;
        }
        if (this.zaa.zac.getErrorResolutionIntent((Context)this.zaa.getActivity(), connectionResult.getErrorCode(), null) != null) {
            this.zaa.zac.zaa(this.zaa.getActivity(), this.zaa.mLifecycleFragment, connectionResult.getErrorCode(), 2, this.zaa);
            return;
        }
        if (connectionResult.getErrorCode() == 18) {
            connectionResult = GoogleApiAvailability.zaa(this.zaa.getActivity(), this.zaa);
            this.zaa.zac.zaa(this.zaa.getActivity().getApplicationContext(), new zan(this, (Dialog)connectionResult));
            return;
        }
        this.zaa.zaa(connectionResult, this.zab.zaa());
    }
}

