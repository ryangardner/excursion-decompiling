/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package androidx.core.os;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class HandlerCompat {
    private static final String TAG = "HandlerCompat";

    private HandlerCompat() {
    }

    public static Handler createAsync(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync((Looper)looper);
        }
        if (Build.VERSION.SDK_INT < 16) return new Handler(looper);
        try {
            return (Handler)Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(new Object[]{looper, null, true});
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
            if (!(throwable instanceof Error)) throw new RuntimeException(throwable);
            throw (Error)throwable;
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException reflectiveOperationException) {
            Log.v((String)TAG, (String)"Unable to invoke Handler(Looper, Callback, boolean) constructor");
        }
        return new Handler(looper);
    }

    public static Handler createAsync(Looper looper, Handler.Callback callback) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync((Looper)looper, (Handler.Callback)callback);
        }
        if (Build.VERSION.SDK_INT < 16) return new Handler(looper, callback);
        try {
            return (Handler)Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(new Object[]{looper, callback, true});
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
            if (!(throwable instanceof Error)) throw new RuntimeException(throwable);
            throw (Error)throwable;
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException reflectiveOperationException) {
            Log.v((String)TAG, (String)"Unable to invoke Handler(Looper, Callback, boolean) constructor");
        }
        return new Handler(looper, callback);
    }

    public static boolean postDelayed(Handler handler, Runnable runnable2, Object object, long l) {
        if (Build.VERSION.SDK_INT >= 28) {
            return handler.postDelayed(runnable2, object, l);
        }
        runnable2 = Message.obtain((Handler)handler, (Runnable)runnable2);
        ((Message)runnable2).obj = object;
        return handler.sendMessageDelayed((Message)runnable2, l);
    }
}

