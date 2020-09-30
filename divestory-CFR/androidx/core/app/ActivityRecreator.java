/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.res.Configuration
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.Log
 */
package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.List;

final class ActivityRecreator {
    private static final String LOG_TAG = "ActivityRecreator";
    protected static final Class<?> activityThreadClass;
    private static final Handler mainHandler;
    protected static final Field mainThreadField;
    protected static final Method performStopActivity2ParamsMethod;
    protected static final Method performStopActivity3ParamsMethod;
    protected static final Method requestRelaunchActivityMethod;
    protected static final Field tokenField;

    static {
        mainHandler = new Handler(Looper.getMainLooper());
        activityThreadClass = ActivityRecreator.getActivityThreadClass();
        mainThreadField = ActivityRecreator.getMainThreadField();
        tokenField = ActivityRecreator.getTokenField();
        performStopActivity3ParamsMethod = ActivityRecreator.getPerformStopActivity3Params(activityThreadClass);
        performStopActivity2ParamsMethod = ActivityRecreator.getPerformStopActivity2Params(activityThreadClass);
        requestRelaunchActivityMethod = ActivityRecreator.getRequestRelaunchActivityMethod(activityThreadClass);
    }

    private ActivityRecreator() {
    }

    private static Class<?> getActivityThreadClass() {
        try {
            return Class.forName("android.app.ActivityThread");
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Field getMainThreadField() {
        try {
            Field field = Activity.class.getDeclaredField("mMainThread");
            field.setAccessible(true);
            return field;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method getPerformStopActivity2Params(Class<?> genericDeclaration) {
        if (genericDeclaration == null) {
            return null;
        }
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE);
            ((Method)genericDeclaration).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method getPerformStopActivity3Params(Class<?> genericDeclaration) {
        if (genericDeclaration == null) {
            return null;
        }
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE, String.class);
            ((Method)genericDeclaration).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method getRequestRelaunchActivityMethod(Class<?> genericDeclaration) {
        if (!ActivityRecreator.needsRelaunchCall()) return null;
        if (genericDeclaration == null) {
            return null;
        }
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("requestRelaunchActivity", IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE);
            ((Method)genericDeclaration).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Field getTokenField() {
        try {
            Field field = Activity.class.getDeclaredField("mToken");
            field.setAccessible(true);
            return field;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static boolean needsRelaunchCall() {
        if (Build.VERSION.SDK_INT == 26) return true;
        if (Build.VERSION.SDK_INT == 27) return true;
        return false;
    }

    protected static boolean queueOnStopIfNecessary(Object runnable2, Activity object) {
        try {
            final Object object2 = tokenField.get(object);
            if (object2 != runnable2) {
                return false;
            }
            object = mainThreadField.get(object);
            Handler handler = mainHandler;
            runnable2 = new Runnable(){

                @Override
                public void run() {
                    try {
                        if (performStopActivity3ParamsMethod != null) {
                            performStopActivity3ParamsMethod.invoke(Object.this, object2, false, "AppCompat recreation");
                            return;
                        }
                        performStopActivity2ParamsMethod.invoke(Object.this, object2, false);
                        return;
                    }
                    catch (Throwable throwable) {
                        Log.e((String)ActivityRecreator.LOG_TAG, (String)"Exception while invoking performStopActivity", (Throwable)throwable);
                        return;
                    }
                    catch (RuntimeException runtimeException) {
                        if (runtimeException.getClass() != RuntimeException.class) return;
                        if (runtimeException.getMessage() == null) return;
                        if (runtimeException.getMessage().startsWith("Unable to stop")) throw runtimeException;
                        return;
                    }
                }
            };
            handler.postAtFrontOfQueue(runnable2);
            return true;
        }
        catch (Throwable throwable) {
            Log.e((String)LOG_TAG, (String)"Exception while fetching field values", (Throwable)throwable);
            return false;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static boolean recreate(Activity object) {
        Object object2;
        Application application;
        LifecycleCheckCallbacks lifecycleCheckCallbacks;
        block11 : {
            Object object4;
            Object object3;
            if (Build.VERSION.SDK_INT >= 28) {
                object.recreate();
                return true;
            }
            if (ActivityRecreator.needsRelaunchCall() && requestRelaunchActivityMethod == null) {
                return false;
            }
            if (performStopActivity2ParamsMethod == null && performStopActivity3ParamsMethod == null) {
                return false;
            }
            try {
                object3 = tokenField.get(object);
                if (object3 == null) {
                    return false;
                }
                object4 = mainThreadField.get(object);
                if (object4 == null) {
                    return false;
                }
                application = object.getApplication();
                lifecycleCheckCallbacks = new LifecycleCheckCallbacks((Activity)object);
                application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)lifecycleCheckCallbacks);
                object2 = mainHandler;
                Runnable runnable2 = new Runnable(){

                    @Override
                    public void run() {
                        LifecycleCheckCallbacks.this.currentlyRecreatingToken = object3;
                    }
                };
                object2.post(runnable2);
            }
            catch (Throwable throwable) {
                return false;
            }
            if (ActivityRecreator.needsRelaunchCall()) {
                requestRelaunchActivityMethod.invoke(object4, object3, null, null, 0, false, null, null, false, false);
                break block11;
            }
            object.recreate();
            {
                catch (Throwable throwable) {
                    object = mainHandler;
                    object2 = new Runnable(lifecycleCheckCallbacks){
                        final /* synthetic */ LifecycleCheckCallbacks val$callbacks;
                        {
                            this.val$callbacks = lifecycleCheckCallbacks;
                        }

                        @Override
                        public void run() {
                            Application.this.unregisterActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)this.val$callbacks);
                        }
                    };
                    object.post((Runnable)object2);
                    throw throwable;
                }
            }
        }
        object2 = mainHandler;
        object = new /* invalid duplicate definition of identical inner class */;
        object2.post((Runnable)object);
        return true;
    }

    private static final class LifecycleCheckCallbacks
    implements Application.ActivityLifecycleCallbacks {
        Object currentlyRecreatingToken;
        private Activity mActivity;
        private boolean mDestroyed = false;
        private boolean mStarted = false;
        private boolean mStopQueued = false;

        LifecycleCheckCallbacks(Activity activity) {
            this.mActivity = activity;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.mActivity != activity) return;
            this.mActivity = null;
            this.mDestroyed = true;
        }

        public void onActivityPaused(Activity activity) {
            if (!this.mDestroyed) return;
            if (this.mStopQueued) return;
            if (this.mStarted) return;
            if (!ActivityRecreator.queueOnStopIfNecessary(this.currentlyRecreatingToken, activity)) return;
            this.mStopQueued = true;
            this.currentlyRecreatingToken = null;
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            if (this.mActivity != activity) return;
            this.mStarted = true;
        }

        public void onActivityStopped(Activity activity) {
        }
    }

}

