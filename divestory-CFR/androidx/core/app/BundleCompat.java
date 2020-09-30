/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package androidx.core.app;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BundleCompat {
    private BundleCompat() {
    }

    public static IBinder getBinder(Bundle bundle, String string2) {
        if (Build.VERSION.SDK_INT < 18) return BundleCompatBaseImpl.getBinder(bundle, string2);
        return bundle.getBinder(string2);
    }

    public static void putBinder(Bundle bundle, String string2, IBinder iBinder) {
        if (Build.VERSION.SDK_INT >= 18) {
            bundle.putBinder(string2, iBinder);
            return;
        }
        BundleCompatBaseImpl.putBinder(bundle, string2, iBinder);
    }

    static class BundleCompatBaseImpl {
        private static final String TAG = "BundleCompatBaseImpl";
        private static Method sGetIBinderMethod;
        private static boolean sGetIBinderMethodFetched;
        private static Method sPutIBinderMethod;
        private static boolean sPutIBinderMethodFetched;

        private BundleCompatBaseImpl() {
        }

        /*
         * WARNING - void declaration
         */
        public static IBinder getBinder(Bundle bundle, String string2) {
            void var0_4;
            Method method;
            if (!sGetIBinderMethodFetched) {
                try {
                    sGetIBinderMethod = method = Bundle.class.getMethod("getIBinder", String.class);
                    method.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve getIBinder method", (Throwable)noSuchMethodException);
                }
                sGetIBinderMethodFetched = true;
            }
            if ((method = sGetIBinderMethod) == null) return null;
            try {
                return (IBinder)method.invoke((Object)bundle, string2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
            Log.i((String)TAG, (String)"Failed to invoke getIBinder via reflection", (Throwable)var0_4);
            sGetIBinderMethod = null;
            return null;
        }

        /*
         * WARNING - void declaration
         */
        public static void putBinder(Bundle bundle, String string2, IBinder iBinder) {
            Method method;
            void var0_4;
            if (!sPutIBinderMethodFetched) {
                try {
                    sPutIBinderMethod = method = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                    method.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve putIBinder method", (Throwable)noSuchMethodException);
                }
                sPutIBinderMethodFetched = true;
            }
            if ((method = sPutIBinderMethod) == null) return;
            try {
                method.invoke((Object)bundle, new Object[]{string2, iBinder});
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
            Log.i((String)TAG, (String)"Failed to invoke putIBinder via reflection", (Throwable)var0_4);
            sPutIBinderMethod = null;
        }
    }

}

