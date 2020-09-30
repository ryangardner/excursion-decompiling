/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.savedstate;

import android.os.Bundle;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.Recreator;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.util.Map;

public final class SavedStateRegistry {
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    boolean mAllowingSavingState = true;
    private SafeIterableMap<String, SavedStateProvider> mComponents = new SafeIterableMap();
    private Recreator.SavedStateProvider mRecreatorProvider;
    private boolean mRestored;
    private Bundle mRestoredState;

    SavedStateRegistry() {
    }

    public Bundle consumeRestoredStateForKey(String string2) {
        if (!this.mRestored) throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
        Bundle bundle = this.mRestoredState;
        if (bundle == null) return null;
        bundle = bundle.getBundle(string2);
        this.mRestoredState.remove(string2);
        if (!this.mRestoredState.isEmpty()) return bundle;
        this.mRestoredState = null;
        return bundle;
    }

    public boolean isRestored() {
        return this.mRestored;
    }

    void performRestore(Lifecycle lifecycle, Bundle bundle) {
        if (this.mRestored) throw new IllegalStateException("SavedStateRegistry was already restored.");
        if (bundle != null) {
            this.mRestoredState = bundle.getBundle(SAVED_COMPONENTS_KEY);
        }
        lifecycle.addObserver(new GenericLifecycleObserver(){

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_START) {
                    SavedStateRegistry.this.mAllowingSavingState = true;
                    return;
                }
                if (event != Lifecycle.Event.ON_STOP) return;
                SavedStateRegistry.this.mAllowingSavingState = false;
            }
        });
        this.mRestored = true;
    }

    void performSave(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Object object = this.mRestoredState;
        if (object != null) {
            bundle2.putAll((Bundle)object);
        }
        object = this.mComponents.iteratorWithAdditions();
        do {
            if (!object.hasNext()) {
                bundle.putBundle(SAVED_COMPONENTS_KEY, bundle2);
                return;
            }
            Map.Entry entry = (Map.Entry)object.next();
            bundle2.putBundle((String)entry.getKey(), ((SavedStateProvider)entry.getValue()).saveState());
        } while (true);
    }

    public void registerSavedStateProvider(String string2, SavedStateProvider savedStateProvider) {
        if (this.mComponents.putIfAbsent(string2, savedStateProvider) != null) throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
    }

    public void runOnNextRecreation(Class<? extends AutoRecreated> class_) {
        if (!this.mAllowingSavingState) throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        if (this.mRecreatorProvider == null) {
            this.mRecreatorProvider = new Recreator.SavedStateProvider(this);
        }
        try {
            class_.getDeclaredConstructor(new Class[0]);
            this.mRecreatorProvider.add(class_.getName());
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class");
            stringBuilder.append(class_.getSimpleName());
            stringBuilder.append(" must have default constructor in order to be automatically recreated");
            throw new IllegalArgumentException(stringBuilder.toString(), noSuchMethodException);
        }
    }

    public void unregisterSavedStateProvider(String string2) {
        this.mComponents.remove(string2);
    }

    public static interface AutoRecreated {
        public void onRecreated(SavedStateRegistryOwner var1);
    }

    public static interface SavedStateProvider {
        public Bundle saveState();
    }

}

