/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

final class zaf
implements View.OnClickListener {
    private final /* synthetic */ Context zaa;
    private final /* synthetic */ Intent zab;

    zaf(Context context, Intent intent) {
        this.zaa = context;
        this.zab = intent;
    }

    public final void onClick(View view) {
        try {
            this.zaa.startActivity(this.zab);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e((String)"DeferredLifecycleHelper", (String)"Failed to start resolution intent", (Throwable)activityNotFoundException);
            return;
        }
    }
}

