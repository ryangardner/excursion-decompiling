/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnKeyListener
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.KeyEvent
 *  android.view.KeyEvent$Callback
 *  android.view.KeyEvent$DispatcherState
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package androidx.core.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class KeyEventDispatcher {
    private static boolean sActionBarFieldsFetched = false;
    private static Method sActionBarOnMenuKeyMethod;
    private static boolean sDialogFieldsFetched = false;
    private static Field sDialogKeyListenerField;

    private KeyEventDispatcher() {
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static boolean actionBarOnMenuKeyEventPre28(ActionBar var0, KeyEvent var1_2) {
        if (KeyEventDispatcher.sActionBarFieldsFetched) ** GOTO lbl-1000
        try {
            KeyEventDispatcher.sActionBarOnMenuKeyMethod = var0.getClass().getMethod("onMenuKeyEvent", new Class[]{KeyEvent.class});
lbl4: // 2 sources:
            do {
                KeyEventDispatcher.sActionBarFieldsFetched = true;
                break;
            } while (true);
        }
        catch (NoSuchMethodException var2_3) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if ((var2_4 = KeyEventDispatcher.sActionBarOnMenuKeyMethod) == null) return false;
            return (Boolean)var2_4.invoke((Object)var0, new Object[]{var1_2});
        }
        catch (IllegalAccessException | InvocationTargetException var0_1) {
            return false;
        }
    }

    private static boolean activitySuperDispatchKeyEventPre28(Activity activity, KeyEvent keyEvent) {
        activity.onUserInteraction();
        Window window = activity.getWindow();
        if (window.hasFeature(8)) {
            ActionBar actionBar = activity.getActionBar();
            if (keyEvent.getKeyCode() == 82 && actionBar != null && KeyEventDispatcher.actionBarOnMenuKeyEventPre28(actionBar, keyEvent)) {
                return true;
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback((View)(window = window.getDecorView()), keyEvent)) {
            return true;
        }
        if (window != null) {
            window = window.getKeyDispatcherState();
            return keyEvent.dispatch((KeyEvent.Callback)activity, (KeyEvent.DispatcherState)window, (Object)activity);
        }
        window = null;
        return keyEvent.dispatch((KeyEvent.Callback)activity, (KeyEvent.DispatcherState)window, (Object)activity);
    }

    private static boolean dialogSuperDispatchKeyEventPre28(Dialog dialog, KeyEvent keyEvent) {
        DialogInterface.OnKeyListener onKeyListener = KeyEventDispatcher.getDialogKeyListenerPre28(dialog);
        if (onKeyListener != null && onKeyListener.onKey((DialogInterface)dialog, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        onKeyListener = dialog.getWindow();
        if (onKeyListener.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback((View)(onKeyListener = onKeyListener.getDecorView()), keyEvent)) {
            return true;
        }
        if (onKeyListener != null) {
            onKeyListener = onKeyListener.getKeyDispatcherState();
            return keyEvent.dispatch((KeyEvent.Callback)dialog, (KeyEvent.DispatcherState)onKeyListener, (Object)dialog);
        }
        onKeyListener = null;
        return keyEvent.dispatch((KeyEvent.Callback)dialog, (KeyEvent.DispatcherState)onKeyListener, (Object)dialog);
    }

    public static boolean dispatchBeforeHierarchy(View view, KeyEvent keyEvent) {
        return ViewCompat.dispatchUnhandledKeyEventBeforeHierarchy(view, keyEvent);
    }

    public static boolean dispatchKeyEvent(Component component, View view, Window.Callback callback, KeyEvent keyEvent) {
        boolean bl = false;
        if (component == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return component.superDispatchKeyEvent(keyEvent);
        }
        if (callback instanceof Activity) {
            return KeyEventDispatcher.activitySuperDispatchKeyEventPre28((Activity)callback, keyEvent);
        }
        if (callback instanceof Dialog) {
            return KeyEventDispatcher.dialogSuperDispatchKeyEventPre28((Dialog)callback, keyEvent);
        }
        if (view != null) {
            if (ViewCompat.dispatchUnhandledKeyEventBeforeCallback(view, keyEvent)) return true;
        }
        if (!component.superDispatchKeyEvent(keyEvent)) return bl;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static DialogInterface.OnKeyListener getDialogKeyListenerPre28(Dialog var0) {
        if (KeyEventDispatcher.sDialogFieldsFetched) ** GOTO lbl-1000
        try {
            KeyEventDispatcher.sDialogKeyListenerField = var1_2 = Dialog.class.getDeclaredField("mOnKeyListener");
            var1_2.setAccessible(true);
lbl5: // 2 sources:
            do {
                KeyEventDispatcher.sDialogFieldsFetched = true;
                break;
            } while (true);
        }
        catch (NoSuchFieldException var1_3) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if ((var1_2 = KeyEventDispatcher.sDialogKeyListenerField) == null) return null;
            return (DialogInterface.OnKeyListener)var1_2.get((Object)var0);
        }
        catch (IllegalAccessException var0_1) {
            return null;
        }
    }

    public static interface Component {
        public boolean superDispatchKeyEvent(KeyEvent var1);
    }

}

