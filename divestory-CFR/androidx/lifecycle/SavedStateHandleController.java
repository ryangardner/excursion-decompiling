/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Iterator;
import java.util.Set;

final class SavedStateHandleController
implements LifecycleEventObserver {
    static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";
    private final SavedStateHandle mHandle;
    private boolean mIsAttached = false;
    private final String mKey;

    SavedStateHandleController(String string2, SavedStateHandle savedStateHandle) {
        this.mKey = string2;
        this.mHandle = savedStateHandle;
    }

    static void attachHandleIfNeeded(ViewModel object, SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        if ((object = (SavedStateHandleController)((ViewModel)object).getTag(TAG_SAVED_STATE_HANDLE_CONTROLLER)) == null) return;
        if (((SavedStateHandleController)object).isAttached()) return;
        ((SavedStateHandleController)object).attachToLifecycle(savedStateRegistry, lifecycle);
        SavedStateHandleController.tryToAddRecreator(savedStateRegistry, lifecycle);
    }

    static SavedStateHandleController create(SavedStateRegistry savedStateRegistry, Lifecycle lifecycle, String object, Bundle bundle) {
        object = new SavedStateHandleController((String)object, SavedStateHandle.createHandle(savedStateRegistry.consumeRestoredStateForKey((String)object), bundle));
        ((SavedStateHandleController)object).attachToLifecycle(savedStateRegistry, lifecycle);
        SavedStateHandleController.tryToAddRecreator(savedStateRegistry, lifecycle);
        return object;
    }

    private static void tryToAddRecreator(final SavedStateRegistry savedStateRegistry, final Lifecycle lifecycle) {
        Lifecycle.State state = lifecycle.getCurrentState();
        if (state != Lifecycle.State.INITIALIZED && !state.isAtLeast(Lifecycle.State.STARTED)) {
            lifecycle.addObserver(new LifecycleEventObserver(){

                @Override
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event != Lifecycle.Event.ON_START) return;
                    lifecycle.removeObserver(this);
                    savedStateRegistry.runOnNextRecreation(OnRecreation.class);
                }
            });
            return;
        }
        savedStateRegistry.runOnNextRecreation(OnRecreation.class);
    }

    void attachToLifecycle(SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        if (this.mIsAttached) throw new IllegalStateException("Already attached to lifecycleOwner");
        this.mIsAttached = true;
        lifecycle.addObserver(this);
        savedStateRegistry.registerSavedStateProvider(this.mKey, this.mHandle.savedStateProvider());
    }

    SavedStateHandle getHandle() {
        return this.mHandle;
    }

    boolean isAttached() {
        return this.mIsAttached;
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_DESTROY) return;
        this.mIsAttached = false;
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    static final class OnRecreation
    implements SavedStateRegistry.AutoRecreated {
        OnRecreation() {
        }

        @Override
        public void onRecreated(SavedStateRegistryOwner savedStateRegistryOwner) {
            if (!(savedStateRegistryOwner instanceof ViewModelStoreOwner)) throw new IllegalStateException("Internal error: OnRecreation should be registered only on componentsthat implement ViewModelStoreOwner");
            ViewModelStore viewModelStore = ((ViewModelStoreOwner)((Object)savedStateRegistryOwner)).getViewModelStore();
            SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
            Iterator<String> iterator2 = viewModelStore.keys().iterator();
            do {
                if (!iterator2.hasNext()) {
                    if (viewModelStore.keys().isEmpty()) return;
                    savedStateRegistry.runOnNextRecreation(OnRecreation.class);
                    return;
                }
                SavedStateHandleController.attachHandleIfNeeded(viewModelStore.get(iterator2.next()), savedStateRegistry, savedStateRegistryOwner.getLifecycle());
            } while (true);
        }
    }

}

