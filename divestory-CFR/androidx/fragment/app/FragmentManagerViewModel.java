/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package androidx.fragment.app;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerNonConfig;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class FragmentManagerViewModel
extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory(){

        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            return (T)new FragmentManagerViewModel(true);
        }
    };
    private static final String TAG = "FragmentManager";
    private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap();
    private boolean mHasBeenCleared = false;
    private boolean mHasSavedSnapshot = false;
    private final HashMap<String, Fragment> mRetainedFragments = new HashMap();
    private final boolean mStateAutomaticallySaved;
    private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap();

    FragmentManagerViewModel(boolean bl) {
        this.mStateAutomaticallySaved = bl;
    }

    static FragmentManagerViewModel getInstance(ViewModelStore viewModelStore) {
        return new ViewModelProvider(viewModelStore, FACTORY).get(FragmentManagerViewModel.class);
    }

    boolean addRetainedFragment(Fragment fragment) {
        if (this.mRetainedFragments.containsKey(fragment.mWho)) {
            return false;
        }
        this.mRetainedFragments.put(fragment.mWho, fragment);
        return true;
    }

    void clearNonConfigState(Fragment fragment) {
        Object object;
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Clearing non-config state for ");
            ((StringBuilder)object).append(fragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        if ((object = this.mChildNonConfigs.get(fragment.mWho)) != null) {
            ((FragmentManagerViewModel)object).onCleared();
            this.mChildNonConfigs.remove(fragment.mWho);
        }
        if ((object = this.mViewModelStores.get(fragment.mWho)) == null) return;
        ((ViewModelStore)object).clear();
        this.mViewModelStores.remove(fragment.mWho);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (FragmentManagerViewModel)object;
        if (!this.mRetainedFragments.equals(((FragmentManagerViewModel)object).mRetainedFragments)) return false;
        if (!this.mChildNonConfigs.equals(((FragmentManagerViewModel)object).mChildNonConfigs)) return false;
        if (!this.mViewModelStores.equals(((FragmentManagerViewModel)object).mViewModelStores)) return false;
        return bl;
    }

    Fragment findRetainedFragmentByWho(String string2) {
        return this.mRetainedFragments.get(string2);
    }

    FragmentManagerViewModel getChildNonConfig(Fragment fragment) {
        FragmentManagerViewModel fragmentManagerViewModel;
        FragmentManagerViewModel fragmentManagerViewModel2 = fragmentManagerViewModel = this.mChildNonConfigs.get(fragment.mWho);
        if (fragmentManagerViewModel != null) return fragmentManagerViewModel2;
        fragmentManagerViewModel2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
        this.mChildNonConfigs.put(fragment.mWho, fragmentManagerViewModel2);
        return fragmentManagerViewModel2;
    }

    Collection<Fragment> getRetainedFragments() {
        return this.mRetainedFragments.values();
    }

    @Deprecated
    FragmentManagerNonConfig getSnapshot() {
        if (this.mRetainedFragments.isEmpty() && this.mChildNonConfigs.isEmpty() && this.mViewModelStores.isEmpty()) {
            return null;
        }
        HashMap<String, FragmentManagerNonConfig> hashMap = new HashMap<String, FragmentManagerNonConfig>();
        Iterator<Map.Entry<String, FragmentManagerViewModel>> iterator2 = this.mChildNonConfigs.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mHasSavedSnapshot = true;
                if (!this.mRetainedFragments.isEmpty()) return new FragmentManagerNonConfig(new ArrayList<Fragment>(this.mRetainedFragments.values()), hashMap, new HashMap<String, ViewModelStore>(this.mViewModelStores));
                if (!hashMap.isEmpty()) return new FragmentManagerNonConfig(new ArrayList<Fragment>(this.mRetainedFragments.values()), hashMap, new HashMap<String, ViewModelStore>(this.mViewModelStores));
                if (!this.mViewModelStores.isEmpty()) return new FragmentManagerNonConfig(new ArrayList<Fragment>(this.mRetainedFragments.values()), hashMap, new HashMap<String, ViewModelStore>(this.mViewModelStores));
                return null;
            }
            Map.Entry<String, FragmentManagerViewModel> entry = iterator2.next();
            FragmentManagerNonConfig fragmentManagerNonConfig = entry.getValue().getSnapshot();
            if (fragmentManagerNonConfig == null) continue;
            hashMap.put(entry.getKey(), fragmentManagerNonConfig);
        } while (true);
    }

    ViewModelStore getViewModelStore(Fragment fragment) {
        ViewModelStore viewModelStore;
        ViewModelStore viewModelStore2 = viewModelStore = this.mViewModelStores.get(fragment.mWho);
        if (viewModelStore != null) return viewModelStore2;
        viewModelStore2 = new ViewModelStore();
        this.mViewModelStores.put(fragment.mWho, viewModelStore2);
        return viewModelStore2;
    }

    public int hashCode() {
        return (this.mRetainedFragments.hashCode() * 31 + this.mChildNonConfigs.hashCode()) * 31 + this.mViewModelStores.hashCode();
    }

    boolean isCleared() {
        return this.mHasBeenCleared;
    }

    @Override
    protected void onCleared() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCleared called for ");
            stringBuilder.append(this);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        this.mHasBeenCleared = true;
    }

    boolean removeRetainedFragment(Fragment fragment) {
        if (this.mRetainedFragments.remove(fragment.mWho) == null) return false;
        return true;
    }

    @Deprecated
    void restoreFromSnapshot(FragmentManagerNonConfig object) {
        this.mRetainedFragments.clear();
        this.mChildNonConfigs.clear();
        this.mViewModelStores.clear();
        if (object != null) {
            Object object2 = ((FragmentManagerNonConfig)((Object)object)).getFragments();
            if (object2 != null) {
                Iterator<Object> iterator2 = object2.iterator();
                while (iterator2.hasNext()) {
                    object2 = iterator2.next();
                    if (object2 == null) continue;
                    this.mRetainedFragments.put(((Fragment)object2).mWho, (Fragment)object2);
                }
            }
            if ((object2 = ((FragmentManagerNonConfig)((Object)object)).getChildNonConfigs()) != null) {
                for (Map.Entry entry : object2.entrySet()) {
                    object2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
                    ((FragmentManagerViewModel)object2).restoreFromSnapshot((FragmentManagerNonConfig)entry.getValue());
                    this.mChildNonConfigs.put((String)entry.getKey(), (FragmentManagerViewModel)object2);
                }
            }
            if ((object = ((FragmentManagerNonConfig)((Object)object)).getViewModelStores()) != null) {
                this.mViewModelStores.putAll(object);
            }
        }
        this.mHasSavedSnapshot = false;
    }

    boolean shouldDestroy(Fragment fragment) {
        if (!this.mRetainedFragments.containsKey(fragment.mWho)) {
            return true;
        }
        if (!this.mStateAutomaticallySaved) return this.mHasSavedSnapshot ^ true;
        return this.mHasBeenCleared;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("FragmentManagerViewModel{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("} Fragments (");
        Iterator<Object> iterator2 = this.mRetainedFragments.values().iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append(iterator2.next());
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(") Child Non Config (");
        iterator2 = this.mChildNonConfigs.keySet().iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append((String)iterator2.next());
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(") ViewModelStores (");
        iterator2 = this.mViewModelStores.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                stringBuilder.append(')');
                return stringBuilder.toString();
            }
            stringBuilder.append((String)iterator2.next());
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(", ");
        } while (true);
    }

}

