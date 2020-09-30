/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.core.util.LogWriter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

final class BackStackRecord
extends FragmentTransaction
implements FragmentManager.BackStackEntry,
FragmentManager.OpGenerator {
    private static final String TAG = "FragmentManager";
    boolean mCommitted;
    int mIndex;
    final FragmentManager mManager;

    BackStackRecord(FragmentManager fragmentManager) {
        FragmentFactory fragmentFactory = fragmentManager.getFragmentFactory();
        ClassLoader classLoader = fragmentManager.mHost != null ? fragmentManager.mHost.getContext().getClassLoader() : null;
        super(fragmentFactory, classLoader);
        this.mIndex = -1;
        this.mManager = fragmentManager;
    }

    private static boolean isFragmentPostponed(FragmentTransaction.Op object) {
        object = ((FragmentTransaction.Op)object).mFragment;
        if (object == null) return false;
        if (!((Fragment)object).mAdded) return false;
        if (((Fragment)object).mView == null) return false;
        if (((Fragment)object).mDetached) return false;
        if (((Fragment)object).mHidden) return false;
        if (!((Fragment)object).isPostponed()) return false;
        return true;
    }

    void bumpBackStackNesting(int n) {
        Object object;
        if (!this.mAddToBackStack) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bump nesting in ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" by ");
            ((StringBuilder)object).append(n);
            Log.v((String)TAG, (String)((StringBuilder)object).toString());
        }
        int n2 = this.mOps.size();
        int n3 = 0;
        while (n3 < n2) {
            object = (FragmentTransaction.Op)this.mOps.get(n3);
            if (((FragmentTransaction.Op)object).mFragment != null) {
                Object object2 = ((FragmentTransaction.Op)object).mFragment;
                ((Fragment)object2).mBackStackNesting += n;
                if (FragmentManager.isLoggingEnabled(2)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Bump nesting of ");
                    ((StringBuilder)object2).append(((FragmentTransaction.Op)object).mFragment);
                    ((StringBuilder)object2).append(" to ");
                    ((StringBuilder)object2).append(object.mFragment.mBackStackNesting);
                    Log.v((String)TAG, (String)((StringBuilder)object2).toString());
                }
            }
            ++n3;
        }
    }

    @Override
    public int commit() {
        return this.commitInternal(false);
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.commitInternal(true);
    }

    int commitInternal(boolean bl) {
        if (this.mCommitted) throw new IllegalStateException("commit already called");
        if (FragmentManager.isLoggingEnabled(2)) {
            Appendable appendable = new StringBuilder();
            ((StringBuilder)appendable).append("Commit: ");
            ((StringBuilder)appendable).append(this);
            Log.v((String)TAG, (String)((StringBuilder)appendable).toString());
            appendable = new PrintWriter(new LogWriter(TAG));
            this.dump("  ", (PrintWriter)appendable);
            ((PrintWriter)appendable).close();
        }
        this.mCommitted = true;
        this.mIndex = this.mAddToBackStack ? this.mManager.allocBackStackIndex() : -1;
        this.mManager.enqueueAction(this, bl);
        return this.mIndex;
    }

    @Override
    public void commitNow() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        if (fragment.mFragmentManager == null) return super.detach(fragment);
        if (fragment.mFragmentManager == this.mManager) {
            return super.detach(fragment);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot detach Fragment attached to a different FragmentManager. Fragment ");
        stringBuilder.append(fragment.toString());
        stringBuilder.append(" is already attached to a FragmentManager.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    void doAddOp(int n, Fragment fragment, String string2, int n2) {
        super.doAddOp(n, fragment, string2, n2);
        fragment.mFragmentManager = this.mManager;
    }

    public void dump(String string2, PrintWriter printWriter) {
        this.dump(string2, printWriter, true);
    }

    /*
     * Unable to fully structure code
     */
    public void dump(String var1_1, PrintWriter var2_2, boolean var3_3) {
        if (var3_3) {
            var2_2.print(var1_1);
            var2_2.print("mName=");
            var2_2.print(this.mName);
            var2_2.print(" mIndex=");
            var2_2.print(this.mIndex);
            var2_2.print(" mCommitted=");
            var2_2.println(this.mCommitted);
            if (this.mTransition != 0) {
                var2_2.print(var1_1);
                var2_2.print("mTransition=#");
                var2_2.print(Integer.toHexString(this.mTransition));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                var2_2.print(var1_1);
                var2_2.print("mEnterAnim=#");
                var2_2.print(Integer.toHexString(this.mEnterAnim));
                var2_2.print(" mExitAnim=#");
                var2_2.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                var2_2.print(var1_1);
                var2_2.print("mPopEnterAnim=#");
                var2_2.print(Integer.toHexString(this.mPopEnterAnim));
                var2_2.print(" mPopExitAnim=#");
                var2_2.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                var2_2.print(var1_1);
                var2_2.print("mBreadCrumbTitleRes=#");
                var2_2.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                var2_2.print(" mBreadCrumbTitleText=");
                var2_2.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                var2_2.print(var1_1);
                var2_2.print("mBreadCrumbShortTitleRes=#");
                var2_2.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                var2_2.print(" mBreadCrumbShortTitleText=");
                var2_2.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (this.mOps.isEmpty() != false) return;
        var2_2.print(var1_1);
        var2_2.println("Operations:");
        var4_4 = this.mOps.size();
        var5_5 = 0;
        while (var5_5 < var4_4) {
            var6_6 = (FragmentTransaction.Op)this.mOps.get(var5_5);
            switch (var6_6.mCmd) {
                default: {
                    var7_7 = new StringBuilder();
                    var7_7.append("cmd=");
                    var7_7.append(var6_6.mCmd);
                    var7_7 = var7_7.toString();
                    ** break;
                }
                case 10: {
                    var7_7 = "OP_SET_MAX_LIFECYCLE";
                    ** break;
                }
                case 9: {
                    var7_7 = "UNSET_PRIMARY_NAV";
                    ** break;
                }
                case 8: {
                    var7_7 = "SET_PRIMARY_NAV";
                    ** break;
                }
                case 7: {
                    var7_7 = "ATTACH";
                    ** break;
                }
                case 6: {
                    var7_7 = "DETACH";
                    ** break;
                }
                case 5: {
                    var7_7 = "SHOW";
                    ** break;
                }
                case 4: {
                    var7_7 = "HIDE";
                    ** break;
                }
                case 3: {
                    var7_7 = "REMOVE";
                    ** break;
                }
                case 2: {
                    var7_7 = "REPLACE";
                    ** break;
                }
                case 1: {
                    var7_7 = "ADD";
                    ** break;
                }
                case 0: 
            }
            var7_7 = "NULL";
lbl85: // 12 sources:
            var2_2.print(var1_1);
            var2_2.print("  Op #");
            var2_2.print(var5_5);
            var2_2.print(": ");
            var2_2.print((String)var7_7);
            var2_2.print(" ");
            var2_2.println(var6_6.mFragment);
            if (var3_3) {
                if (var6_6.mEnterAnim != 0 || var6_6.mExitAnim != 0) {
                    var2_2.print(var1_1);
                    var2_2.print("enterAnim=#");
                    var2_2.print(Integer.toHexString(var6_6.mEnterAnim));
                    var2_2.print(" exitAnim=#");
                    var2_2.println(Integer.toHexString(var6_6.mExitAnim));
                }
                if (var6_6.mPopEnterAnim != 0 || var6_6.mPopExitAnim != 0) {
                    var2_2.print(var1_1);
                    var2_2.print("popEnterAnim=#");
                    var2_2.print(Integer.toHexString(var6_6.mPopEnterAnim));
                    var2_2.print(" popExitAnim=#");
                    var2_2.println(Integer.toHexString(var6_6.mPopExitAnim));
                }
            }
            ++var5_5;
        }
    }

    /*
     * Unable to fully structure code
     */
    void executeOps() {
        var1_1 = this.mOps.size();
        var2_2 = 0;
        block11 : do {
            if (var2_2 >= var1_1) {
                if (this.mReorderingAllowed != false) return;
                var3_3 = this.mManager;
                var3_3.moveToState(var3_3.mCurState, true);
                return;
            }
            var3_3 = (FragmentTransaction.Op)this.mOps.get(var2_2);
            var4_4 = var3_3.mFragment;
            if (var4_4 != null) {
                var4_4.setNextTransition(this.mTransition);
            }
            switch (var3_3.mCmd) {
                case 10: {
                    this.mManager.setMaxLifecycle((Fragment)var4_4, var3_3.mCurrentMaxState);
                    ** break;
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment(null);
                    ** break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment((Fragment)var4_4);
                    ** break;
                }
                case 7: {
                    var4_4.setNextAnim(var3_3.mEnterAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, false);
                    this.mManager.attachFragment((Fragment)var4_4);
                    ** break;
                }
                case 6: {
                    var4_4.setNextAnim(var3_3.mExitAnim);
                    this.mManager.detachFragment((Fragment)var4_4);
                    ** break;
                }
                case 5: {
                    var4_4.setNextAnim(var3_3.mEnterAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, false);
                    this.mManager.showFragment((Fragment)var4_4);
                    ** break;
                }
                case 4: {
                    var4_4.setNextAnim(var3_3.mExitAnim);
                    this.mManager.hideFragment((Fragment)var4_4);
                    ** break;
                }
                case 3: {
                    var4_4.setNextAnim(var3_3.mExitAnim);
                    this.mManager.removeFragment((Fragment)var4_4);
                    ** break;
                }
                case 1: {
                    var4_4.setNextAnim(var3_3.mEnterAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, false);
                    this.mManager.addFragment((Fragment)var4_4);
lbl49: // 9 sources:
                    if (!this.mReorderingAllowed && var3_3.mCmd != 1 && var4_4 != null) {
                        this.mManager.moveFragmentToExpectedState((Fragment)var4_4);
                    }
                    ++var2_2;
                    continue block11;
                }
            }
            break;
        } while (true);
        var4_4 = new StringBuilder();
        var4_4.append("Unknown cmd: ");
        var4_4.append(var3_3.mCmd);
        throw new IllegalArgumentException(var4_4.toString());
    }

    /*
     * Unable to fully structure code
     */
    void executePopOps(boolean var1_1) {
        var2_2 = this.mOps.size() - 1;
        block11 : do {
            if (var2_2 < 0) {
                if (this.mReorderingAllowed != false) return;
                if (var1_1 == false) return;
                var3_3 = this.mManager;
                var3_3.moveToState(var3_3.mCurState, true);
                return;
            }
            var3_3 = (FragmentTransaction.Op)this.mOps.get(var2_2);
            var4_4 = var3_3.mFragment;
            if (var4_4 != null) {
                var4_4.setNextTransition(FragmentManager.reverseTransit(this.mTransition));
            }
            switch (var3_3.mCmd) {
                case 10: {
                    this.mManager.setMaxLifecycle((Fragment)var4_4, var3_3.mOldMaxState);
                    ** break;
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment((Fragment)var4_4);
                    ** break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment(null);
                    ** break;
                }
                case 7: {
                    var4_4.setNextAnim(var3_3.mPopExitAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, true);
                    this.mManager.detachFragment((Fragment)var4_4);
                    ** break;
                }
                case 6: {
                    var4_4.setNextAnim(var3_3.mPopEnterAnim);
                    this.mManager.attachFragment((Fragment)var4_4);
                    ** break;
                }
                case 5: {
                    var4_4.setNextAnim(var3_3.mPopExitAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, true);
                    this.mManager.hideFragment((Fragment)var4_4);
                    ** break;
                }
                case 4: {
                    var4_4.setNextAnim(var3_3.mPopEnterAnim);
                    this.mManager.showFragment((Fragment)var4_4);
                    ** break;
                }
                case 3: {
                    var4_4.setNextAnim(var3_3.mPopEnterAnim);
                    this.mManager.addFragment((Fragment)var4_4);
                    ** break;
                }
                case 1: {
                    var4_4.setNextAnim(var3_3.mPopExitAnim);
                    this.mManager.setExitAnimationOrder((Fragment)var4_4, true);
                    this.mManager.removeFragment((Fragment)var4_4);
lbl49: // 9 sources:
                    if (!this.mReorderingAllowed && var3_3.mCmd != 3 && var4_4 != null) {
                        this.mManager.moveFragmentToExpectedState((Fragment)var4_4);
                    }
                    --var2_2;
                    continue block11;
                }
            }
            break;
        } while (true);
        var4_4 = new StringBuilder();
        var4_4.append("Unknown cmd: ");
        var4_4.append(var3_3.mCmd);
        throw new IllegalArgumentException(var4_4.toString());
    }

    Fragment expandOps(ArrayList<Fragment> arrayList, Fragment object) {
        int n = 0;
        Fragment fragment = object;
        while (n < this.mOps.size()) {
            int n2;
            block14 : {
                FragmentTransaction.Op op;
                int n3;
                Fragment fragment2;
                int n4;
                block15 : {
                    block11 : {
                        block12 : {
                            block13 : {
                                op = (FragmentTransaction.Op)this.mOps.get(n);
                                n2 = op.mCmd;
                                if (n2 == 1) break block11;
                                if (n2 == 2) break block12;
                                if (n2 == 3 || n2 == 6) break block13;
                                if (n2 == 7) break block11;
                                if (n2 != 8) {
                                    object = fragment;
                                    n2 = n;
                                } else {
                                    this.mOps.add(n, new FragmentTransaction.Op(9, fragment));
                                    n2 = n + 1;
                                    object = op.mFragment;
                                }
                                break block14;
                            }
                            arrayList.remove(op.mFragment);
                            object = fragment;
                            n2 = n;
                            if (op.mFragment == fragment) {
                                this.mOps.add(n, new FragmentTransaction.Op(9, op.mFragment));
                                n2 = n + 1;
                                object = null;
                            }
                            break block14;
                        }
                        fragment2 = op.mFragment;
                        n4 = fragment2.mContainerId;
                        n3 = 0;
                        n2 = n;
                        object = fragment;
                        break block15;
                    }
                    arrayList.add(op.mFragment);
                    n2 = n;
                    object = fragment;
                    break block14;
                }
                for (int i = arrayList.size() - 1; i >= 0; --i) {
                    Fragment fragment3 = arrayList.get(i);
                    fragment = object;
                    int n5 = n2;
                    n = n3;
                    if (fragment3.mContainerId == n4) {
                        if (fragment3 == fragment2) {
                            n = 1;
                            fragment = object;
                            n5 = n2;
                        } else {
                            fragment = object;
                            n = n2;
                            if (fragment3 == object) {
                                this.mOps.add(n2, new FragmentTransaction.Op(9, fragment3));
                                n = n2 + 1;
                                fragment = null;
                            }
                            object = new FragmentTransaction.Op(3, fragment3);
                            ((FragmentTransaction.Op)object).mEnterAnim = op.mEnterAnim;
                            ((FragmentTransaction.Op)object).mPopEnterAnim = op.mPopEnterAnim;
                            ((FragmentTransaction.Op)object).mExitAnim = op.mExitAnim;
                            ((FragmentTransaction.Op)object).mPopExitAnim = op.mPopExitAnim;
                            this.mOps.add(n, object);
                            arrayList.remove(fragment3);
                            n5 = n + 1;
                            n = n3;
                        }
                    }
                    object = fragment;
                    n2 = n5;
                    n3 = n;
                }
                if (n3 != 0) {
                    this.mOps.remove(n2);
                    --n2;
                } else {
                    op.mCmd = 1;
                    arrayList.add(fragment2);
                }
            }
            n = n2 + 1;
            fragment = object;
        }
        return fragment;
    }

    @Override
    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Run: ");
            stringBuilder.append(this);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.mAddToBackStack) return true;
        this.mManager.addBackStackState(this);
        return true;
    }

    @Override
    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes == 0) return this.mBreadCrumbShortTitleText;
        return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
    }

    @Override
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override
    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes == 0) return this.mBreadCrumbTitleText;
        return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
    }

    @Override
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override
    public int getId() {
        return this.mIndex;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        if (fragment.mFragmentManager == null) return super.hide(fragment);
        if (fragment.mFragmentManager == this.mManager) {
            return super.hide(fragment);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot hide Fragment attached to a different FragmentManager. Fragment ");
        stringBuilder.append(fragment.toString());
        stringBuilder.append(" is already attached to a FragmentManager.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    boolean interactsWith(int n) {
        int n2 = this.mOps.size();
        int n3 = 0;
        while (n3 < n2) {
            FragmentTransaction.Op op = (FragmentTransaction.Op)this.mOps.get(n3);
            int n4 = op.mFragment != null ? op.mFragment.mContainerId : 0;
            if (n4 != 0 && n4 == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    boolean interactsWith(ArrayList<BackStackRecord> var1_1, int var2_2, int var3_3) {
        if (var3_3 == var2_2) {
            return false;
        }
        var4_4 = this.mOps.size();
        var5_5 = -1;
        var6_6 = 0;
        block0 : do {
            if (var6_6 >= var4_4) return false;
            var7_7 = (FragmentTransaction.Op)this.mOps.get(var6_6);
            var8_8 = var7_7.mFragment != null ? var7_7.mFragment.mContainerId : 0;
            var9_9 = var5_5;
            if (var8_8 == 0) ** GOTO lbl21
            var9_9 = var5_5;
            if (var8_8 == var5_5) ** GOTO lbl21
            var5_5 = var2_2;
            do {
                if (var5_5 < var3_3) {
                    var10_10 = var1_1.get(var5_5);
                    var11_11 = var10_10.mOps.size();
                } else {
                    var9_9 = var8_8;
lbl21: // 3 sources:
                    ++var6_6;
                    var5_5 = var9_9;
                    continue block0;
                }
                for (var9_9 = 0; var9_9 < var11_11; ++var9_9) {
                    var7_7 = (FragmentTransaction.Op)var10_10.mOps.get(var9_9);
                    var12_12 = var7_7.mFragment != null ? var7_7.mFragment.mContainerId : 0;
                    if (var12_12 != var8_8) continue;
                    return true;
                }
                ++var5_5;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    boolean isPostponed() {
        int n = 0;
        while (n < this.mOps.size()) {
            if (BackStackRecord.isFragmentPostponed((FragmentTransaction.Op)this.mOps.get(n))) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        if (fragment.mFragmentManager == null) return super.remove(fragment);
        if (fragment.mFragmentManager == this.mManager) {
            return super.remove(fragment);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot remove Fragment attached to a different FragmentManager. Fragment ");
        stringBuilder.append(fragment.toString());
        stringBuilder.append(" is already attached to a FragmentManager.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void runOnCommitRunnables() {
        if (this.mCommitRunnables == null) return;
        int n = 0;
        do {
            if (n >= this.mCommitRunnables.size()) {
                this.mCommitRunnables = null;
                return;
            }
            ((Runnable)this.mCommitRunnables.get(n)).run();
            ++n;
        } while (true);
    }

    @Override
    public FragmentTransaction setMaxLifecycle(Fragment object, Lifecycle.State state) {
        if (((Fragment)object).mFragmentManager != this.mManager) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot setMaxLifecycle for Fragment not attached to FragmentManager ");
            ((StringBuilder)object).append(this.mManager);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (state.isAtLeast(Lifecycle.State.CREATED)) {
            return super.setMaxLifecycle((Fragment)object, state);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot set maximum Lifecycle below ");
        ((StringBuilder)object).append((Object)Lifecycle.State.CREATED);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        int n = 0;
        while (n < this.mOps.size()) {
            FragmentTransaction.Op op = (FragmentTransaction.Op)this.mOps.get(n);
            if (BackStackRecord.isFragmentPostponed(op)) {
                op.mFragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener);
            }
            ++n;
        }
    }

    @Override
    public FragmentTransaction setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment == null) return super.setPrimaryNavigationFragment(fragment);
        if (fragment.mFragmentManager == null) return super.setPrimaryNavigationFragment(fragment);
        if (fragment.mFragmentManager == this.mManager) {
            return super.setPrimaryNavigationFragment(fragment);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment ");
        stringBuilder.append(fragment.toString());
        stringBuilder.append(" is already attached to a FragmentManager.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        if (fragment.mFragmentManager == null) return super.show(fragment);
        if (fragment.mFragmentManager == this.mManager) {
            return super.show(fragment);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot show Fragment attached to a different FragmentManager. Fragment ");
        stringBuilder.append(fragment.toString());
        stringBuilder.append(" is already attached to a FragmentManager.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mName != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mName);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     */
    Fragment trackAddedFragmentsInPop(ArrayList<Fragment> var1_1, Fragment var2_2) {
        var3_3 = this.mOps.size() - 1;
        while (var3_3 >= 0) {
            block8 : {
                var4_4 = (FragmentTransaction.Op)this.mOps.get(var3_3);
                var5_5 = var4_4.mCmd;
                if (var5_5 == 1) break block8;
                if (var5_5 == 3) ** GOTO lbl-1000
                switch (var5_5) {
                    default: {
                        ** break;
                    }
                    case 10: {
                        var4_4.mCurrentMaxState = var4_4.mOldMaxState;
                        ** break;
                    }
                    case 9: {
                        var2_2 = var4_4.mFragment;
                        ** break;
                    }
                    case 8: {
                        var2_2 = null;
                        ** break;
                    }
                    case 6: lbl-1000: // 2 sources:
                    {
                        var1_1.add(var4_4.mFragment);
                        ** break;
                    }
                    case 7: 
                }
            }
            var1_1.remove(var4_4.mFragment);
lbl27: // 6 sources:
            --var3_3;
        }
        return var2_2;
    }
}

