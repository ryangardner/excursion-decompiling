/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.util.Log
 *  android.util.SparseArray
 */
package androidx.legacy.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

@Deprecated
public abstract class WakefulBroadcastReceiver
extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "androidx.contentpager.content.wakelockid";
    private static int mNextId;
    private static final SparseArray<PowerManager.WakeLock> sActiveWakeLocks;

    static {
        sActiveWakeLocks = new SparseArray();
        mNextId = 1;
    }

    public static boolean completeWakefulIntent(Intent sparseArray) {
        int n = sparseArray.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
        if (n == 0) {
            return false;
        }
        sparseArray = sActiveWakeLocks;
        synchronized (sparseArray) {
            Object object = (PowerManager.WakeLock)sActiveWakeLocks.get(n);
            if (object != null) {
                object.release();
                sActiveWakeLocks.remove(n);
                return true;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No active wake lock id #");
            ((StringBuilder)object).append(n);
            Log.w((String)"WakefulBroadcastReceiv.", (String)((StringBuilder)object).toString());
            return true;
        }
    }

    public static ComponentName startWakefulService(Context object, Intent intent) {
        SparseArray<PowerManager.WakeLock> sparseArray = sActiveWakeLocks;
        synchronized (sparseArray) {
            int n;
            int n2 = mNextId;
            mNextId = n = mNextId + 1;
            if (n <= 0) {
                mNextId = 1;
            }
            intent.putExtra(EXTRA_WAKE_LOCK_ID, n2);
            intent = object.startService(intent);
            if (intent == null) {
                return null;
            }
            PowerManager powerManager = (PowerManager)object.getSystemService("power");
            object = new StringBuilder();
            ((StringBuilder)object).append("androidx.core:wake:");
            ((StringBuilder)object).append(intent.flattenToShortString());
            object = powerManager.newWakeLock(1, ((StringBuilder)object).toString());
            object.setReferenceCounted(false);
            object.acquire(60000L);
            sActiveWakeLocks.put(n2, object);
            return intent;
        }
    }
}

