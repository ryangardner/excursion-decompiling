/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.activity;

import androidx.activity.Cancellable;
import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class OnBackPressedDispatcher {
    private final Runnable mFallbackOnBackPressed;
    final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks = new ArrayDeque();

    public OnBackPressedDispatcher() {
        this(null);
    }

    public OnBackPressedDispatcher(Runnable runnable2) {
        this.mFallbackOnBackPressed = runnable2;
    }

    public void addCallback(OnBackPressedCallback onBackPressedCallback) {
        this.addCancellableCallback(onBackPressedCallback);
    }

    public void addCallback(LifecycleOwner object, OnBackPressedCallback onBackPressedCallback) {
        if (((Lifecycle)(object = object.getLifecycle())).getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable((Lifecycle)object, onBackPressedCallback));
    }

    Cancellable addCancellableCallback(OnBackPressedCallback onBackPressedCallback) {
        this.mOnBackPressedCallbacks.add(onBackPressedCallback);
        OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(onBackPressedCallback);
        onBackPressedCallback.addCancellable(onBackPressedCancellable);
        return onBackPressedCancellable;
    }

    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> iterator2 = this.mOnBackPressedCallbacks.descendingIterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!iterator2.next().isEnabled());
        return true;
    }

    public void onBackPressed() {
        Object object;
        Iterator<OnBackPressedCallback> iterator2 = this.mOnBackPressedCallbacks.descendingIterator();
        do {
            if (iterator2.hasNext()) continue;
            object = this.mFallbackOnBackPressed;
            if (object == null) return;
            object.run();
            return;
        } while (!((OnBackPressedCallback)(object = iterator2.next())).isEnabled());
        ((OnBackPressedCallback)object).handleOnBackPressed();
    }

    private class LifecycleOnBackPressedCancellable
    implements LifecycleEventObserver,
    Cancellable {
        private Cancellable mCurrentCancellable;
        private final Lifecycle mLifecycle;
        private final OnBackPressedCallback mOnBackPressedCallback;

        LifecycleOnBackPressedCancellable(Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override
        public void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.mCurrentCancellable;
            if (cancellable == null) return;
            cancellable.cancel();
            this.mCurrentCancellable = null;
        }

        @Override
        public void onStateChanged(LifecycleOwner object, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
                return;
            }
            if (event == Lifecycle.Event.ON_STOP) {
                object = this.mCurrentCancellable;
                if (object == null) return;
                object.cancel();
                return;
            }
            if (event != Lifecycle.Event.ON_DESTROY) return;
            this.cancel();
        }
    }

    private class OnBackPressedCancellable
    implements Cancellable {
        private final OnBackPressedCallback mOnBackPressedCallback;

        OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        @Override
        public void cancel() {
            OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
            this.mOnBackPressedCallback.removeCancellable(this);
        }
    }

}

