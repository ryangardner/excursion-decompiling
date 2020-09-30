/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.Preconditions;

public class GoogleApiActivity
extends Activity
implements DialogInterface.OnCancelListener {
    private int zaa = 0;

    public static PendingIntent zaa(Context context, PendingIntent pendingIntent, int n) {
        return PendingIntent.getActivity((Context)context, (int)0, (Intent)GoogleApiActivity.zaa(context, pendingIntent, n, true), (int)134217728);
    }

    public static Intent zaa(Context context, PendingIntent pendingIntent, int n, boolean bl) {
        context = new Intent(context, GoogleApiActivity.class);
        context.putExtra("pending_intent", (Parcelable)pendingIntent);
        context.putExtra("failing_client_id", n);
        context.putExtra("notify_manager", bl);
        return context;
    }

    protected void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        if (n == 1) {
            boolean bl = this.getIntent().getBooleanExtra("notify_manager", true);
            this.zaa = 0;
            this.setResult(n2, (Intent)object);
            if (bl) {
                object = GoogleApiManager.zaa((Context)this);
                if (n2 != -1) {
                    if (n2 == 0) {
                        ((GoogleApiManager)object).zab(new ConnectionResult(13, null), this.getIntent().getIntExtra("failing_client_id", -1));
                    }
                } else {
                    ((GoogleApiManager)object).zac();
                }
            }
        } else if (n == 2) {
            this.zaa = 0;
            this.setResult(n2, (Intent)object);
        }
        this.finish();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zaa = 0;
        this.setResult(0);
        this.finish();
    }

    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        if (object != null) {
            this.zaa = object.getInt("resolution");
        }
        if (this.zaa == 1) return;
        object = this.getIntent().getExtras();
        if (object == null) {
            Log.e((String)"GoogleApiActivity", (String)"Activity started without extras");
            this.finish();
            return;
        }
        Object object2 = (PendingIntent)object.get("pending_intent");
        Integer n = (Integer)object.get("error_code");
        if (object2 == null && n == null) {
            Log.e((String)"GoogleApiActivity", (String)"Activity started without resolution");
            this.finish();
            return;
        }
        if (object2 == null) {
            int n2 = Preconditions.checkNotNull(n);
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, n2, 2, this);
            this.zaa = 1;
            return;
        }
        try {
            this.startIntentSenderForResult(object2.getIntentSender(), 1, null, 0, 0, 0);
            this.zaa = 1;
            return;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            Log.e((String)"GoogleApiActivity", (String)"Failed to launch pendingIntent", (Throwable)sendIntentException);
            this.finish();
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            if (object.getBoolean("notify_manager", true)) {
                GoogleApiManager.zaa((Context)this).zab(new ConnectionResult(22, null), this.getIntent().getIntExtra("failing_client_id", -1));
            } else {
                object2 = String.valueOf(object2);
                object = new StringBuilder(String.valueOf(object2).length() + 36);
                ((StringBuilder)object).append("Activity not found while launching ");
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(".");
                object2 = ((StringBuilder)object).toString();
                object = object2;
                if (Build.FINGERPRINT.contains("generic")) {
                    object = String.valueOf(object2).concat(" This may occur when resolving Google Play services connection issues on emulators with Google APIs but not Google Play Store.");
                }
                Log.e((String)"GoogleApiActivity", (String)object, (Throwable)activityNotFoundException);
            }
            this.zaa = 1;
            this.finish();
            return;
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.zaa);
        super.onSaveInstanceState(bundle);
    }
}

