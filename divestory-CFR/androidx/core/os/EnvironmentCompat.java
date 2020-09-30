/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.util.Log
 */
package androidx.core.os;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    private EnvironmentCompat() {
    }

    public static String getStorageState(File object) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Environment.getExternalStorageState((File)object);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getStorageState((File)object);
        }
        try {
            if (!((File)object).getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) return MEDIA_UNKNOWN;
            return Environment.getExternalStorageState();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to resolve canonical path: ");
            ((StringBuilder)object).append(iOException);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
        return MEDIA_UNKNOWN;
    }
}

