/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package androidx.core.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.KeyEventDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;

public class ComponentActivity
extends Activity
implements LifecycleOwner,
KeyEventDispatcher.Component {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap();
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View view = this.getWindow().getDecorView();
        if (view == null) return KeyEventDispatcher.dispatchKeyEvent(this, view, (Window.Callback)this, keyEvent);
        if (!KeyEventDispatcher.dispatchBeforeHierarchy(view, keyEvent)) return KeyEventDispatcher.dispatchKeyEvent(this, view, (Window.Callback)this, keyEvent);
        return true;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        View view = this.getWindow().getDecorView();
        if (view == null) return super.dispatchKeyShortcutEvent(keyEvent);
        if (!KeyEventDispatcher.dispatchBeforeHierarchy(view, keyEvent)) return super.dispatchKeyShortcutEvent(keyEvent);
        return true;
    }

    @Deprecated
    public <T extends ExtraData> T getExtraData(Class<T> class_) {
        return (T)this.mExtraDataMap.get(class_);
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(bundle);
    }

    @Deprecated
    public void putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData);
    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Deprecated
    public static class ExtraData {
    }

}

