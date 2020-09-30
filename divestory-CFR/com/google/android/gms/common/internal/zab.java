/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.os.Build
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.zad;
import com.google.android.gms.common.internal.zae;
import com.google.android.gms.common.internal.zaf;

public abstract class zab
implements DialogInterface.OnClickListener {
    public static zab zaa(Activity activity, Intent intent, int n) {
        return new zae(intent, activity, n);
    }

    public static zab zaa(Fragment fragment, Intent intent, int n) {
        return new zad(intent, fragment, n);
    }

    public static zab zaa(LifecycleFragment lifecycleFragment, Intent intent, int n) {
        return new zaf(intent, lifecycleFragment, 2);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public void onClick(DialogInterface dialogInterface, int n) {
        Throwable throwable2222;
        this.zaa();
        dialogInterface.dismiss();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (ActivityNotFoundException activityNotFoundException) {}
            String string2 = "Failed to start resolution intent.";
            {
                if (Build.FINGERPRINT.contains("generic")) {
                    string2 = "Failed to start resolution intent.".concat(" This may occur when resolving Google Play services connection issues on emulators with Google APIs but not Google Play Store.");
                }
                Log.e((String)"DialogRedirect", (String)string2, (Throwable)activityNotFoundException);
            }
            dialogInterface.dismiss();
            return;
        }
        dialogInterface.dismiss();
        throw throwable2222;
    }

    protected abstract void zaa();
}

