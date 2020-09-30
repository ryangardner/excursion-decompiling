/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentState;
import androidx.fragment.app.FragmentStateManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class FragmentStore {
    private static final String TAG = "FragmentManager";
    private final HashMap<String, FragmentStateManager> mActive = new HashMap();
    private final ArrayList<Fragment> mAdded = new ArrayList();

    FragmentStore() {
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void addFragment(Fragment fragment) {
        if (!this.mAdded.contains(fragment)) {
            ArrayList<Fragment> arrayList = this.mAdded;
            synchronized (arrayList) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment already added: ");
        stringBuilder.append(fragment);
        throw new IllegalStateException(stringBuilder.toString());
    }

    void burpActive() {
        this.mActive.values().removeAll(Collections.singleton(null));
    }

    boolean containsActiveFragment(String string2) {
        return this.mActive.containsKey(string2);
    }

    void dispatchStateChange(int n) {
        for (Fragment fragment : this.mAdded) {
            FragmentStateManager object2 = this.mActive.get(fragment.mWho);
            if (object2 == null) continue;
            object2.setFragmentManagerState(n);
        }
        Iterator<FragmentStateManager> iterator2 = this.mActive.values().iterator();
        while (iterator2.hasNext()) {
            FragmentStateManager fragmentStateManager = iterator2.next();
            if (fragmentStateManager == null) continue;
            fragmentStateManager.setFragmentManagerState(n);
        }
    }

    void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        if (!this.mActive.isEmpty()) {
            printWriter.print(string2);
            printWriter.print("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
                printWriter.print(string2);
                if (fragmentStateManager != null) {
                    Fragment fragment = fragmentStateManager.getFragment();
                    printWriter.println(fragment);
                    fragment.dump((String)charSequence, (FileDescriptor)object, printWriter, arrstring);
                    continue;
                }
                printWriter.println("null");
            }
        }
        if ((n = this.mAdded.size()) <= 0) return;
        printWriter.print(string2);
        printWriter.println("Added Fragments:");
        int n2 = 0;
        while (n2 < n) {
            object = this.mAdded.get(n2);
            printWriter.print(string2);
            printWriter.print("  #");
            printWriter.print(n2);
            printWriter.print(": ");
            printWriter.println(((Fragment)object).toString());
            ++n2;
        }
    }

    Fragment findActiveFragment(String object) {
        if ((object = this.mActive.get(object)) == null) return null;
        return ((FragmentStateManager)object).getFragment();
    }

    /*
     * Unable to fully structure code
     */
    Fragment findFragmentById(int var1_1) {
        for (var2_2 = this.mAdded.size() - 1; var2_2 >= 0; --var2_2) {
            var3_3 = this.mAdded.get(var2_2);
            if (var3_3 == null || var3_3.mFragmentId != var1_1) continue;
            return var3_3;
        }
        var3_3 = this.mActive.values().iterator();
        do lbl-1000: // 3 sources:
        {
            if (var3_3.hasNext() == false) return null;
            var4_4 = (FragmentStateManager)var3_3.next();
            if (var4_4 == null) ** GOTO lbl-1000
            var4_4 = var4_4.getFragment();
        } while (var4_4.mFragmentId != var1_1);
        return var4_4;
    }

    /*
     * Unable to fully structure code
     */
    Fragment findFragmentByTag(String var1_1) {
        if (var1_1 != null) {
            for (var2_2 = this.mAdded.size() - 1; var2_2 >= 0; --var2_2) {
                var3_3 = this.mAdded.get(var2_2);
                if (var3_3 == null || !var1_1.equals(var3_3.mTag)) continue;
                return var3_3;
            }
        }
        if (var1_1 == null) return null;
        var3_3 = this.mActive.values().iterator();
        do lbl-1000: // 3 sources:
        {
            if (var3_3.hasNext() == false) return null;
            var4_4 = var3_3.next();
            if (var4_4 == null) ** GOTO lbl-1000
            var4_4 = var4_4.getFragment();
        } while (!var1_1.equals(var4_4.mTag));
        return var4_4;
    }

    Fragment findFragmentByWho(String string2) {
        Object object;
        Iterator<FragmentStateManager> iterator2 = this.mActive.values().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while ((object = iterator2.next()) == null || (object = ((FragmentStateManager)object).getFragment().findFragmentByWho(string2)) == null);
        return object;
    }

    Fragment findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup == null) return null;
        if (view == null) {
            return null;
        }
        int n = this.mAdded.indexOf(fragment) - 1;
        while (n >= 0) {
            fragment = this.mAdded.get(n);
            if (fragment.mContainer == viewGroup && fragment.mView != null) {
                return fragment;
            }
            --n;
        }
        return null;
    }

    int getActiveFragmentCount() {
        return this.mActive.size();
    }

    List<Fragment> getActiveFragments() {
        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();
        Iterator<FragmentStateManager> iterator2 = this.mActive.values().iterator();
        while (iterator2.hasNext()) {
            FragmentStateManager fragmentStateManager = iterator2.next();
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager.getFragment());
                continue;
            }
            arrayList.add(null);
        }
        return arrayList;
    }

    FragmentStateManager getFragmentStateManager(String string2) {
        return this.mActive.get(string2);
    }

    List<Fragment> getFragments() {
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            return new ArrayList<Fragment>(this.mAdded);
        }
    }

    void makeActive(FragmentStateManager fragmentStateManager) {
        this.mActive.put(fragmentStateManager.getFragment().mWho, fragmentStateManager);
    }

    void makeInactive(FragmentStateManager object) {
        object = ((FragmentStateManager)object).getFragment();
        Iterator<FragmentStateManager> iterator2 = this.mActive.values().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mActive.put(((Fragment)object).mWho, null);
                if (((Fragment)object).mTargetWho == null) return;
                ((Fragment)object).mTarget = this.findActiveFragment(((Fragment)object).mTargetWho);
                return;
            }
            Object object2 = iterator2.next();
            if (object2 == null) continue;
            object2 = ((FragmentStateManager)object2).getFragment();
            if (!((Fragment)object).mWho.equals(((Fragment)object2).mTargetWho)) continue;
            ((Fragment)object2).mTarget = object;
            ((Fragment)object2).mTargetWho = null;
        } while (true);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void removeFragment(Fragment fragment) {
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            this.mAdded.remove(fragment);
        }
        fragment.mAdded = false;
    }

    void resetActiveFragments() {
        this.mActive.clear();
    }

    void restoreAddedFragments(List<String> object) {
        this.mAdded.clear();
        if (object == null) return;
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            Fragment fragment = this.findActiveFragment((String)object);
            if (fragment == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No instantiated fragment for (");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(")");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("restoreSaveState: added (");
                stringBuilder.append((String)object);
                stringBuilder.append("): ");
                stringBuilder.append(fragment);
                Log.v((String)TAG, (String)stringBuilder.toString());
            }
            this.addFragment(fragment);
        }
    }

    ArrayList<FragmentState> saveActiveFragments() {
        ArrayList<FragmentState> arrayList = new ArrayList<FragmentState>(this.mActive.size());
        Iterator<FragmentStateManager> iterator2 = this.mActive.values().iterator();
        while (iterator2.hasNext()) {
            Object object = iterator2.next();
            if (object == null) continue;
            Fragment fragment = ((FragmentStateManager)object).getFragment();
            FragmentState fragmentState = ((FragmentStateManager)object).saveState();
            arrayList.add(fragmentState);
            if (!FragmentManager.isLoggingEnabled(2)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Saved state of ");
            ((StringBuilder)object).append(fragment);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append((Object)fragmentState.mSavedFragmentState);
            Log.v((String)TAG, (String)((StringBuilder)object).toString());
        }
        return arrayList;
    }

    ArrayList<String> saveAddedFragments() {
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            if (this.mAdded.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList2 = new ArrayList<String>(this.mAdded.size());
            Iterator<Fragment> iterator2 = this.mAdded.iterator();
            while (iterator2.hasNext()) {
                Fragment fragment = iterator2.next();
                arrayList2.add(fragment.mWho);
                if (!FragmentManager.isLoggingEnabled(2)) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("saveAllState: adding fragment (");
                stringBuilder.append(fragment.mWho);
                stringBuilder.append("): ");
                stringBuilder.append(fragment);
                Log.v((String)TAG, (String)stringBuilder.toString());
            }
            return arrayList2;
        }
    }
}

