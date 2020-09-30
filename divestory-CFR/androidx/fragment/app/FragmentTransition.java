/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransitionCompat21;
import androidx.fragment.app.FragmentTransitionImpl;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FragmentTransition {
    private static final int[] INVERSE_OPS = new int[]{0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    private static final FragmentTransitionImpl PLATFORM_IMPL;
    private static final FragmentTransitionImpl SUPPORT_IMPL;

    static {
        FragmentTransitionCompat21 fragmentTransitionCompat21 = Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null;
        PLATFORM_IMPL = fragmentTransitionCompat21;
        SUPPORT_IMPL = FragmentTransition.resolveSupportImpl();
    }

    private FragmentTransition() {
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        int n = arrayMap.size() - 1;
        while (n >= 0) {
            View view = (View)arrayMap.valueAt(n);
            if (collection.contains(ViewCompat.getTransitionName(view))) {
                arrayList.add(view);
            }
            --n;
        }
    }

    /*
     * Unable to fully structure code
     */
    private static void addToFirstInLastOut(BackStackRecord var0, FragmentTransaction.Op var1_1, SparseArray<FragmentContainerTransition> var2_2, boolean var3_3, boolean var4_4) {
        block23 : {
            block24 : {
                block22 : {
                    block19 : {
                        block21 : {
                            block15 : {
                                block16 : {
                                    block17 : {
                                        block20 : {
                                            block18 : {
                                                var5_5 = var1_1.mFragment;
                                                if (var5_5 == null) {
                                                    return;
                                                }
                                                var6_6 = var5_5.mContainerId;
                                                if (var6_6 == 0) {
                                                    return;
                                                }
                                                var7_7 = var3_3 != false ? FragmentTransition.INVERSE_OPS[var1_1.mCmd] : var1_1.mCmd;
                                                var8_8 = false;
                                                var9_9 = false;
                                                if (var7_7 == 1) break block15;
                                                if (var7_7 == 3) break block16;
                                                if (var7_7 == 4) break block17;
                                                if (var7_7 == 5) break block18;
                                                if (var7_7 == 6) break block16;
                                                if (var7_7 == 7) break block15;
                                                var7_7 = 0;
                                                break block19;
                                            }
                                            if (!var4_4) break block20;
                                            if (!var5_5.mHiddenChanged || var5_5.mHidden || !var5_5.mAdded) ** GOTO lbl-1000
                                            ** GOTO lbl-1000
                                        }
                                        var9_9 = var5_5.mHidden;
                                        break block21;
                                    }
                                    if (!(var4_4 != false ? var5_5.mHiddenChanged != false && var5_5.mAdded != false && var5_5.mHidden != false : var5_5.mAdded != false && var5_5.mHidden == false)) ** GOTO lbl-1000
                                    ** GOTO lbl-1000
                                }
                                if (var4_4 != false ? var5_5.mAdded == false && var5_5.mView != null && var5_5.mView.getVisibility() == 0 && var5_5.mPostponedAlpha >= 0.0f : var5_5.mAdded != false && var5_5.mHidden == false) lbl-1000: // 2 sources:
                                {
                                    var7_7 = 1;
                                } else lbl-1000: // 2 sources:
                                {
                                    var7_7 = 0;
                                }
                                var12_12 = 0;
                                var10_10 = true;
                                var9_9 = var8_8;
                                var11_11 = var7_7;
                                var7_7 = var12_12;
                                break block22;
                            }
                            if (var4_4) {
                                var9_9 = var5_5.mIsNewlyAdded;
                            } else if (!var5_5.mAdded && !var5_5.mHidden) lbl-1000: // 2 sources:
                            {
                                var9_9 = true;
                            } else lbl-1000: // 2 sources:
                            {
                                var9_9 = false;
                            }
                        }
                        var7_7 = 1;
                    }
                    var10_10 = false;
                    var11_11 = 0;
                }
                var1_1 = var13_13 = (FragmentContainerTransition)var2_2.get(var6_6);
                if (var9_9) {
                    var1_1 = FragmentTransition.ensureContainer((FragmentContainerTransition)var13_13, var2_2, var6_6);
                    var1_1.lastIn = var5_5;
                    var1_1.lastInIsPop = var3_3;
                    var1_1.lastInTransaction = var0;
                }
                if (!var4_4 && var7_7 != 0) {
                    if (var1_1 != null && var1_1.firstOut == var5_5) {
                        var1_1.firstOut = null;
                    }
                    var13_13 = var0.mManager;
                    if (var5_5.mState < 1 && var13_13.mCurState >= 1 && !var0.mReorderingAllowed) {
                        var13_13.makeActive(var5_5);
                        var13_13.moveToState(var5_5, 1);
                    }
                }
                var13_13 = var1_1;
                if (var11_11 == 0) break block23;
                if (var1_1 == null) break block24;
                var13_13 = var1_1;
                if (var1_1.firstOut != null) break block23;
            }
            var13_13 = FragmentTransition.ensureContainer((FragmentContainerTransition)var1_1, var2_2, var6_6);
            var13_13.firstOut = var5_5;
            var13_13.firstOutIsPop = var3_3;
            var13_13.firstOutTransaction = var0;
        }
        if (var4_4 != false) return;
        if (var10_10 == false) return;
        if (var13_13 == null) return;
        if (var13_13.lastIn != var5_5) return;
        var13_13.lastIn = null;
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        int n = backStackRecord.mOps.size();
        int n2 = 0;
        while (n2 < n) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, (FragmentTransaction.Op)backStackRecord.mOps.get(n2), sparseArray, false, bl);
            ++n2;
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int n, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n2, int n3) {
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
        --n3;
        while (n3 >= n2) {
            Object object = arrayList.get(n3);
            if (((BackStackRecord)object).interactsWith(n)) {
                boolean bl = arrayList2.get(n3);
                if (((BackStackRecord)object).mSharedElementSourceNames != null) {
                    ArrayList arrayList3;
                    ArrayList arrayList4;
                    int n4 = ((BackStackRecord)object).mSharedElementSourceNames.size();
                    if (bl) {
                        arrayList3 = ((BackStackRecord)object).mSharedElementSourceNames;
                        arrayList4 = ((BackStackRecord)object).mSharedElementTargetNames;
                    } else {
                        arrayList4 = ((BackStackRecord)object).mSharedElementSourceNames;
                        arrayList3 = ((BackStackRecord)object).mSharedElementTargetNames;
                    }
                    for (int i = 0; i < n4; ++i) {
                        object = (String)arrayList4.get(i);
                        String string2 = (String)arrayList3.get(i);
                        String string3 = (String)arrayMap.remove(string2);
                        if (string3 != null) {
                            arrayMap.put((String)object, string3);
                            continue;
                        }
                        arrayMap.put((String)object, string2);
                    }
                }
            }
            --n3;
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        if (!backStackRecord.mManager.mContainer.onHasView()) {
            return;
        }
        int n = backStackRecord.mOps.size() - 1;
        while (n >= 0) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, (FragmentTransaction.Op)backStackRecord.mOps.get(n), sparseArray, true, bl);
            --n;
        }
    }

    static void callSharedElementStartEnd(Fragment object, Fragment object2, boolean bl, ArrayMap<String, View> arrayMap, boolean bl2) {
        object = bl ? ((Fragment)object2).getEnterTransitionCallback() : ((Fragment)object).getEnterTransitionCallback();
        if (object == null) return;
        object2 = new ArrayList();
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = arrayMap == null ? 0 : arrayMap.size();
        for (int i = 0; i < n; ++i) {
            arrayList.add((String)arrayMap.keyAt(i));
            ((ArrayList)object2).add(arrayMap.valueAt(i));
        }
        if (bl2) {
            ((SharedElementCallback)object).onSharedElementStart(arrayList, (List<View>)object2, null);
            return;
        }
        ((SharedElementCallback)object).onSharedElementEnd(arrayList, (List<View>)object2, null);
    }

    private static boolean canHandleAll(FragmentTransitionImpl fragmentTransitionImpl, List<Object> list) {
        int n = list.size();
        int n2 = 0;
        while (n2 < n) {
            if (!fragmentTransitionImpl.canHandle(list.get(n2))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl object, ArrayMap<String, String> arrayMap, Object object2, FragmentContainerTransition object3) {
        ArrayMap<String, View> arrayMap2;
        Fragment fragment = ((FragmentContainerTransition)object3).lastIn;
        View view = fragment.getView();
        if (!arrayMap.isEmpty() && object2 != null && view != null) {
            arrayMap2 = new ArrayMap<String, View>();
            ((FragmentTransitionImpl)object).findNamedViews(arrayMap2, view);
            object = ((FragmentContainerTransition)object3).lastInTransaction;
            if (((FragmentContainerTransition)object3).lastInIsPop) {
                object2 = fragment.getExitTransitionCallback();
                object = ((BackStackRecord)object).mSharedElementSourceNames;
            } else {
                object2 = fragment.getEnterTransitionCallback();
                object = ((BackStackRecord)object).mSharedElementTargetNames;
            }
            if (object != null) {
                arrayMap2.retainAll((Collection<?>)object);
                arrayMap2.retainAll(arrayMap.values());
            }
            if (object2 == null) {
                FragmentTransition.retainValues(arrayMap, arrayMap2);
                return arrayMap2;
            }
        } else {
            arrayMap.clear();
            return null;
        }
        ((SharedElementCallback)object2).onMapSharedElements((List<String>)object, arrayMap2);
        int n = ((ArrayList)object).size() - 1;
        while (n >= 0) {
            object3 = (String)((ArrayList)object).get(n);
            object2 = (View)arrayMap2.get(object3);
            if (object2 == null) {
                object2 = FragmentTransition.findKeyForValue(arrayMap, (String)object3);
                if (object2 != null) {
                    arrayMap.remove(object2);
                }
            } else if (!((String)object3).equals(ViewCompat.getTransitionName((View)object2)) && (object3 = FragmentTransition.findKeyForValue(arrayMap, (String)object3)) != null) {
                arrayMap.put((String)object3, ViewCompat.getTransitionName((View)object2));
            }
            --n;
        }
        return arrayMap2;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl object, ArrayMap<String, String> arrayMap, Object object2, FragmentContainerTransition object3) {
        ArrayMap<String, View> arrayMap2;
        if (!arrayMap.isEmpty() && object2 != null) {
            object2 = ((FragmentContainerTransition)object3).firstOut;
            arrayMap2 = new ArrayMap<String, View>();
            ((FragmentTransitionImpl)object).findNamedViews(arrayMap2, ((Fragment)object2).requireView());
            object = ((FragmentContainerTransition)object3).firstOutTransaction;
            if (((FragmentContainerTransition)object3).firstOutIsPop) {
                object2 = ((Fragment)object2).getEnterTransitionCallback();
                object = ((BackStackRecord)object).mSharedElementTargetNames;
            } else {
                object2 = ((Fragment)object2).getExitTransitionCallback();
                object = ((BackStackRecord)object).mSharedElementSourceNames;
            }
            if (object != null) {
                arrayMap2.retainAll((Collection<?>)object);
            }
            if (object2 == null) {
                arrayMap.retainAll(arrayMap2.keySet());
                return arrayMap2;
            }
        } else {
            arrayMap.clear();
            return null;
        }
        ((SharedElementCallback)object2).onMapSharedElements((List<String>)object, arrayMap2);
        int n = ((ArrayList)object).size() - 1;
        while (n >= 0) {
            object3 = (String)((ArrayList)object).get(n);
            object2 = (View)arrayMap2.get(object3);
            if (object2 == null) {
                arrayMap.remove(object3);
            } else if (!((String)object3).equals(ViewCompat.getTransitionName((View)object2))) {
                object3 = (String)arrayMap.remove(object3);
                arrayMap.put(ViewCompat.getTransitionName((View)object2), (String)object3);
            }
            --n;
        }
        return arrayMap2;
    }

    private static FragmentTransitionImpl chooseImpl(Fragment object, Fragment fragment) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (object != null) {
            Object object2 = ((Fragment)object).getExitTransition();
            if (object2 != null) {
                arrayList.add(object2);
            }
            if ((object2 = ((Fragment)object).getReturnTransition()) != null) {
                arrayList.add(object2);
            }
            if ((object = ((Fragment)object).getSharedElementReturnTransition()) != null) {
                arrayList.add(object);
            }
        }
        if (fragment != null) {
            object = fragment.getEnterTransition();
            if (object != null) {
                arrayList.add(object);
            }
            if ((object = fragment.getReenterTransition()) != null) {
                arrayList.add(object);
            }
            if ((object = fragment.getSharedElementEnterTransition()) != null) {
                arrayList.add(object);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        object = PLATFORM_IMPL;
        if (object != null && FragmentTransition.canHandleAll((FragmentTransitionImpl)object, arrayList)) {
            return PLATFORM_IMPL;
        }
        object = SUPPORT_IMPL;
        if (object != null && FragmentTransition.canHandleAll((FragmentTransitionImpl)object, arrayList)) {
            return SUPPORT_IMPL;
        }
        if (PLATFORM_IMPL != null) throw new IllegalArgumentException("Invalid Transition types");
        if (SUPPORT_IMPL != null) throw new IllegalArgumentException("Invalid Transition types");
        return null;
    }

    static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl fragmentTransitionImpl, Object object, Fragment arrayList, ArrayList<View> arrayList2, View view) {
        if (object == null) {
            return null;
        }
        ArrayList<View> arrayList3 = new ArrayList<View>();
        if ((arrayList = ((Fragment)((Object)arrayList)).getView()) != null) {
            fragmentTransitionImpl.captureTransitioningViews(arrayList3, (View)arrayList);
        }
        if (arrayList2 != null) {
            arrayList3.removeAll(arrayList2);
        }
        arrayList = arrayList3;
        if (arrayList3.isEmpty()) return arrayList;
        arrayList3.add(view);
        fragmentTransitionImpl.addTargets(object, arrayList3);
        return arrayList3;
    }

    private static Object configureSharedElementsOrdered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final View view, final ArrayMap<String, String> arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object object, Object object2) {
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment == null) return null;
        if (fragment2 == null) {
            return null;
        }
        final boolean bl = fragmentContainerTransition.lastInIsPop;
        final Object object3 = arrayMap.isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, bl);
        ArrayMap<String, View> arrayMap2 = FragmentTransition.captureOutSharedElements(fragmentTransitionImpl, arrayMap, object3, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            object3 = null;
        } else {
            arrayList.addAll(arrayMap2.values());
        }
        if (object == null && object2 == null && object3 == null) {
            return null;
        }
        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, true);
        if (object3 != null) {
            Rect rect = new Rect();
            fragmentTransitionImpl.setSharedElementTargets(object3, view, arrayList);
            FragmentTransition.setOutEpicenter(fragmentTransitionImpl, object3, object2, arrayMap2, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            object2 = rect;
            if (object != null) {
                fragmentTransitionImpl.setEpicenter(object, rect);
                object2 = rect;
            }
        } else {
            object2 = null;
        }
        OneShotPreDrawListener.add((View)viewGroup, new Runnable((Rect)object2){
            final /* synthetic */ Rect val$inEpicenter;
            {
                this.val$inEpicenter = rect;
            }

            @Override
            public void run() {
                View view2 = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, arrayMap, object3, fragmentContainerTransition);
                if (view2 != null) {
                    arrayList2.addAll(view2.values());
                    arrayList2.add(view);
                }
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, view2, false);
                Object object2 = object3;
                if (object2 == null) return;
                fragmentTransitionImpl.swapSharedElementTargets(object2, arrayList, arrayList2);
                view2 = FragmentTransition.getInEpicenterView(view2, fragmentContainerTransition, object, bl);
                if (view2 == null) return;
                fragmentTransitionImpl.getBoundsOnScreen(view2, this.val$inEpicenter);
            }
        });
        return object3;
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View object, ArrayMap<String, String> object2, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object object3, Object object4) {
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.requireView().setVisibility(0);
        }
        if (fragment == null) return null;
        if (fragment2 == null) {
            return null;
        }
        final boolean bl = fragmentContainerTransition.lastInIsPop;
        Object object5 = ((SimpleArrayMap)object2).isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, bl);
        ArrayMap<String, View> arrayMap = FragmentTransition.captureOutSharedElements(fragmentTransitionImpl, object2, object5, fragmentContainerTransition);
        final ArrayMap<String, View> arrayMap2 = FragmentTransition.captureInSharedElements(fragmentTransitionImpl, object2, object5, fragmentContainerTransition);
        if (((SimpleArrayMap)object2).isEmpty()) {
            if (arrayMap != null) {
                arrayMap.clear();
            }
            if (arrayMap2 != null) {
                arrayMap2.clear();
            }
            object2 = null;
        } else {
            FragmentTransition.addSharedElementsWithMatchingNames(arrayList, arrayMap, ((ArrayMap)object2).keySet());
            FragmentTransition.addSharedElementsWithMatchingNames(arrayList2, arrayMap2, ((ArrayMap)object2).values());
            object2 = object5;
        }
        if (object3 == null && object4 == null && object2 == null) {
            return null;
        }
        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap, true);
        if (object2 != null) {
            arrayList2.add((View)object);
            fragmentTransitionImpl.setSharedElementTargets(object2, (View)object, arrayList);
            FragmentTransition.setOutEpicenter(fragmentTransitionImpl, object2, object4, arrayMap, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            object = new Rect();
            fragmentContainerTransition = FragmentTransition.getInEpicenterView(arrayMap2, fragmentContainerTransition, object3, bl);
            if (fragmentContainerTransition != null) {
                fragmentTransitionImpl.setEpicenter(object3, (Rect)object);
            }
        } else {
            fragmentContainerTransition = null;
            object = fragmentContainerTransition;
        }
        OneShotPreDrawListener.add((View)viewGroup, new Runnable((View)fragmentContainerTransition, fragmentTransitionImpl, (Rect)object){
            final /* synthetic */ Rect val$epicenter;
            final /* synthetic */ View val$epicenterView;
            final /* synthetic */ FragmentTransitionImpl val$impl;
            {
                this.val$epicenterView = view;
                this.val$impl = fragmentTransitionImpl;
                this.val$epicenter = rect;
            }

            @Override
            public void run() {
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, false);
                View view = this.val$epicenterView;
                if (view == null) return;
                this.val$impl.getBoundsOnScreen(view, this.val$epicenter);
            }
        });
        return object2;
    }

    private static void configureTransitionsOrdered(FragmentManager fragmentManager, int n, FragmentContainerTransition object, View view, ArrayMap<String, String> arrayMap, Callback object2) {
        if (!fragmentManager.mContainer.onHasView()) return;
        fragmentManager = (ViewGroup)fragmentManager.mContainer.onFindViewById(n);
        if (fragmentManager == null) {
            return;
        }
        Fragment fragment = ((FragmentContainerTransition)object).firstOut;
        Fragment fragment2 = ((FragmentContainerTransition)object).lastIn;
        FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.chooseImpl(fragment, fragment2);
        if (fragmentTransitionImpl == null) {
            return;
        }
        boolean bl = ((FragmentContainerTransition)object).lastInIsPop;
        boolean bl2 = ((FragmentContainerTransition)object).firstOutIsPop;
        Object object3 = FragmentTransition.getEnterTransition(fragmentTransitionImpl, fragment2, bl);
        Object object4 = FragmentTransition.getExitTransition(fragmentTransitionImpl, fragment, bl2);
        Object object5 = new ArrayList<View>();
        ArrayList<View> arrayList = new ArrayList<View>();
        Object object6 = FragmentTransition.configureSharedElementsOrdered(fragmentTransitionImpl, (ViewGroup)fragmentManager, view, arrayMap, (FragmentContainerTransition)object, object5, arrayList, object3, object4);
        if (object3 == null && object6 == null && object4 == null) {
            return;
        }
        ArrayList<View> arrayList2 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object4, fragment, object5, view);
        if (arrayList2 == null || arrayList2.isEmpty()) {
            object4 = null;
        }
        fragmentTransitionImpl.addTarget(object3, view);
        object = FragmentTransition.mergeTransitions(fragmentTransitionImpl, object3, object4, object6, fragment2, ((FragmentContainerTransition)object).lastInIsPop);
        if (fragment != null && arrayList2 != null && (arrayList2.size() > 0 || ((ArrayList)object5).size() > 0)) {
            object5 = new CancellationSignal();
            object2.onStart(fragment, (CancellationSignal)object5);
            fragmentTransitionImpl.setListenerForTransitionEnd(fragment, object, (CancellationSignal)object5, new Runnable((Callback)object2, fragment, (CancellationSignal)object5){
                final /* synthetic */ Callback val$callback;
                final /* synthetic */ Fragment val$outFragment;
                final /* synthetic */ CancellationSignal val$signal;
                {
                    this.val$callback = callback;
                    this.val$outFragment = fragment;
                    this.val$signal = cancellationSignal;
                }

                @Override
                public void run() {
                    this.val$callback.onComplete(this.val$outFragment, this.val$signal);
                }
            });
        }
        if (object == null) return;
        object2 = new ArrayList();
        fragmentTransitionImpl.scheduleRemoveTargets(object, object3, (ArrayList<View>)object2, object4, arrayList2, object6, arrayList);
        FragmentTransition.scheduleTargetChange(fragmentTransitionImpl, (ViewGroup)fragmentManager, fragment2, view, arrayList, object3, (ArrayList<View>)object2, object4, arrayList2);
        fragmentTransitionImpl.setNameOverridesOrdered((View)fragmentManager, arrayList, arrayMap);
        fragmentTransitionImpl.beginDelayedTransition((ViewGroup)fragmentManager, object);
        fragmentTransitionImpl.scheduleNameReset((ViewGroup)fragmentManager, arrayList, arrayMap);
    }

    private static void configureTransitionsReordered(FragmentManager fragmentManager, int n, FragmentContainerTransition object, View object2, ArrayMap<String, String> arrayMap, Callback object3) {
        if (!fragmentManager.mContainer.onHasView()) return;
        fragmentManager = (ViewGroup)fragmentManager.mContainer.onFindViewById(n);
        if (fragmentManager == null) {
            return;
        }
        Fragment fragment = ((FragmentContainerTransition)object).firstOut;
        Object object4 = ((FragmentContainerTransition)object).lastIn;
        FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.chooseImpl(fragment, (Fragment)object4);
        if (fragmentTransitionImpl == null) {
            return;
        }
        boolean bl = ((FragmentContainerTransition)object).lastInIsPop;
        boolean bl2 = ((FragmentContainerTransition)object).firstOutIsPop;
        ArrayList<View> arrayList = new ArrayList<View>();
        ArrayList<View> arrayList2 = new ArrayList<View>();
        Object object5 = FragmentTransition.getEnterTransition(fragmentTransitionImpl, (Fragment)object4, bl);
        ArrayList<View> arrayList3 = FragmentTransition.getExitTransition(fragmentTransitionImpl, fragment, bl2);
        Object object6 = FragmentTransition.configureSharedElementsReordered(fragmentTransitionImpl, (ViewGroup)fragmentManager, object2, arrayMap, (FragmentContainerTransition)object, arrayList2, arrayList, object5, arrayList3);
        if (object5 == null && object6 == null && arrayList3 == null) {
            return;
        }
        object = arrayList3;
        arrayList3 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object, fragment, arrayList2, object2);
        object2 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object5, (Fragment)object4, arrayList, object2);
        FragmentTransition.setViewVisibility((ArrayList<View>)object2, 4);
        object4 = FragmentTransition.mergeTransitions(fragmentTransitionImpl, object5, object, object6, (Fragment)object4, bl);
        if (fragment != null && arrayList3 != null && (arrayList3.size() > 0 || arrayList2.size() > 0)) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            object3.onStart(fragment, cancellationSignal);
            fragmentTransitionImpl.setListenerForTransitionEnd(fragment, object4, cancellationSignal, new Runnable((Callback)object3, fragment, cancellationSignal){
                final /* synthetic */ Callback val$callback;
                final /* synthetic */ Fragment val$outFragment;
                final /* synthetic */ CancellationSignal val$signal;
                {
                    this.val$callback = callback;
                    this.val$outFragment = fragment;
                    this.val$signal = cancellationSignal;
                }

                @Override
                public void run() {
                    this.val$callback.onComplete(this.val$outFragment, this.val$signal);
                }
            });
        }
        if (object4 == null) return;
        FragmentTransition.replaceHide(fragmentTransitionImpl, object, fragment, arrayList3);
        object3 = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList);
        fragmentTransitionImpl.scheduleRemoveTargets(object4, object5, (ArrayList<View>)object2, object, arrayList3, object6, arrayList);
        fragmentTransitionImpl.beginDelayedTransition((ViewGroup)fragmentManager, object4);
        fragmentTransitionImpl.setNameOverridesReordered((View)fragmentManager, arrayList2, arrayList, (ArrayList<String>)object3, arrayMap);
        FragmentTransition.setViewVisibility((ArrayList<View>)object2, 0);
        fragmentTransitionImpl.swapSharedElementTargets(object6, arrayList2, arrayList);
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int n) {
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        if (fragmentContainerTransition != null) return fragmentContainerTransition2;
        fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(n, (Object)fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String string2) {
        int n = arrayMap.size();
        int n2 = 0;
        while (n2 < n) {
            if (string2.equals(arrayMap.valueAt(n2))) {
                return (String)arrayMap.keyAt(n2);
            }
            ++n2;
        }
        return null;
    }

    private static Object getEnterTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        if (bl) {
            object = ((Fragment)object).getReenterTransition();
            return fragmentTransitionImpl.cloneTransition(object);
        }
        object = ((Fragment)object).getEnterTransition();
        return fragmentTransitionImpl.cloneTransition(object);
    }

    private static Object getExitTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        if (bl) {
            object = ((Fragment)object).getReturnTransition();
            return fragmentTransitionImpl.cloneTransition(object);
        }
        object = ((Fragment)object).getExitTransition();
        return fragmentTransitionImpl.cloneTransition(object);
    }

    static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition object, Object object2, boolean bl) {
        object = ((FragmentContainerTransition)object).lastInTransaction;
        if (object2 == null) return null;
        if (arrayMap == null) return null;
        if (((BackStackRecord)object).mSharedElementSourceNames == null) return null;
        if (((BackStackRecord)object).mSharedElementSourceNames.isEmpty()) return null;
        if (bl) {
            object = (String)((BackStackRecord)object).mSharedElementSourceNames.get(0);
            return (View)arrayMap.get(object);
        }
        object = (String)((BackStackRecord)object).mSharedElementTargetNames.get(0);
        return (View)arrayMap.get(object);
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment object, Fragment fragment, boolean bl) {
        if (object == null) return null;
        if (fragment == null) {
            return null;
        }
        if (bl) {
            object = fragment.getSharedElementReturnTransition();
            return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(object));
        }
        object = ((Fragment)object).getSharedElementEnterTransition();
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(object));
    }

    private static Object mergeTransitions(FragmentTransitionImpl object, Object object2, Object object3, Object object4, Fragment fragment, boolean bl) {
        bl = object2 != null && object3 != null && fragment != null ? (bl ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap()) : true;
        if (!bl) return ((FragmentTransitionImpl)object).mergeTransitionsInSequence(object3, object2, object4);
        return ((FragmentTransitionImpl)object).mergeTransitionsTogether(object3, object2, object4);
    }

    private static void replaceHide(FragmentTransitionImpl fragmentTransitionImpl, Object object, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment == null) return;
        if (object == null) return;
        if (!fragment.mAdded) return;
        if (!fragment.mHidden) return;
        if (!fragment.mHiddenChanged) return;
        fragment.setHideReplaced(true);
        fragmentTransitionImpl.scheduleHideFragmentView(object, fragment.getView(), arrayList);
        OneShotPreDrawListener.add((View)fragment.mContainer, new Runnable(){

            @Override
            public void run() {
                FragmentTransition.setViewVisibility(arrayList, 4);
            }
        });
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl)Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        int n = arrayMap.size() - 1;
        while (n >= 0) {
            if (!arrayMap2.containsKey((String)arrayMap.valueAt(n))) {
                arrayMap.removeAt(n);
            }
            --n;
        }
    }

    private static void scheduleTargetChange(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Object object, final ArrayList<View> arrayList2, final Object object2, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add((View)viewGroup, new Runnable(){

            @Override
            public void run() {
                ArrayList<View> arrayList4 = object;
                if (arrayList4 != null) {
                    fragmentTransitionImpl.removeTarget(arrayList4, view);
                    arrayList4 = FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, object, fragment, arrayList, view);
                    arrayList2.addAll(arrayList4);
                }
                if (arrayList3 == null) return;
                if (object2 != null) {
                    arrayList4 = new ArrayList<View>();
                    arrayList4.add(view);
                    fragmentTransitionImpl.replaceTargets(object2, arrayList3, arrayList4);
                }
                arrayList3.clear();
                arrayList3.add(view);
            }
        });
    }

    private static void setOutEpicenter(FragmentTransitionImpl fragmentTransitionImpl, Object object, Object object2, ArrayMap<String, View> view, boolean bl, BackStackRecord object3) {
        if (((BackStackRecord)object3).mSharedElementSourceNames == null) return;
        if (((BackStackRecord)object3).mSharedElementSourceNames.isEmpty()) return;
        object3 = bl ? (String)((BackStackRecord)object3).mSharedElementTargetNames.get(0) : (String)((BackStackRecord)object3).mSharedElementSourceNames.get(0);
        view = (View)view.get(object3);
        fragmentTransitionImpl.setEpicenter(object, view);
        if (object2 == null) return;
        fragmentTransitionImpl.setEpicenter(object2, view);
    }

    static void setViewVisibility(ArrayList<View> arrayList, int n) {
        if (arrayList == null) {
            return;
        }
        int n2 = arrayList.size() - 1;
        while (n2 >= 0) {
            arrayList.get(n2).setVisibility(n);
            --n2;
        }
    }

    static void startTransitions(FragmentManager fragmentManager, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, boolean bl, Callback callback) {
        BackStackRecord backStackRecord;
        int n3;
        if (fragmentManager.mCurState < 1) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        for (n3 = n; n3 < n2; ++n3) {
            backStackRecord = arrayList.get(n3);
            if (arrayList2.get(n3).booleanValue()) {
                FragmentTransition.calculatePopFragments(backStackRecord, (SparseArray<FragmentContainerTransition>)sparseArray, bl);
                continue;
            }
            FragmentTransition.calculateFragments(backStackRecord, (SparseArray<FragmentContainerTransition>)sparseArray, bl);
        }
        if (sparseArray.size() == 0) return;
        backStackRecord = new View(fragmentManager.mHost.getContext());
        int n4 = sparseArray.size();
        n3 = 0;
        while (n3 < n4) {
            int n5 = sparseArray.keyAt(n3);
            ArrayMap<String, String> arrayMap = FragmentTransition.calculateNameOverrides(n5, arrayList, arrayList2, n, n2);
            FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition)sparseArray.valueAt(n3);
            if (bl) {
                FragmentTransition.configureTransitionsReordered(fragmentManager, n5, fragmentContainerTransition, (View)backStackRecord, arrayMap, callback);
            } else {
                FragmentTransition.configureTransitionsOrdered(fragmentManager, n5, fragmentContainerTransition, (View)backStackRecord, arrayMap, callback);
            }
            ++n3;
        }
    }

    static boolean supportsTransition() {
        if (PLATFORM_IMPL != null) return true;
        if (SUPPORT_IMPL != null) return true;
        return false;
    }

    static interface Callback {
        public void onComplete(Fragment var1, CancellationSignal var2);

        public void onStart(Fragment var1, CancellationSignal var2);
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

}

