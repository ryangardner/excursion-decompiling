/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package androidx.lifecycle;

import android.app.Application;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

@Deprecated
public class ViewModelProviders {
    @Deprecated
    public static ViewModelProvider of(Fragment fragment) {
        return new ViewModelProvider(fragment);
    }

    @Deprecated
    public static ViewModelProvider of(Fragment fragment, ViewModelProvider.Factory factory2) {
        ViewModelProvider.Factory factory3 = factory2;
        if (factory2 != null) return new ViewModelProvider(fragment.getViewModelStore(), factory3);
        factory3 = fragment.getDefaultViewModelProviderFactory();
        return new ViewModelProvider(fragment.getViewModelStore(), factory3);
    }

    @Deprecated
    public static ViewModelProvider of(FragmentActivity fragmentActivity) {
        return new ViewModelProvider(fragmentActivity);
    }

    @Deprecated
    public static ViewModelProvider of(FragmentActivity fragmentActivity, ViewModelProvider.Factory factory2) {
        ViewModelProvider.Factory factory3 = factory2;
        if (factory2 != null) return new ViewModelProvider(fragmentActivity.getViewModelStore(), factory3);
        factory3 = fragmentActivity.getDefaultViewModelProviderFactory();
        return new ViewModelProvider(fragmentActivity.getViewModelStore(), factory3);
    }

    @Deprecated
    public static class DefaultFactory
    extends ViewModelProvider.AndroidViewModelFactory {
        @Deprecated
        public DefaultFactory(Application application) {
            super(application);
        }
    }

}

