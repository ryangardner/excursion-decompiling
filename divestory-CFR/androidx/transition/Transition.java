/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.InflateException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.transition.AnimatorUtils;
import androidx.transition.PathMotion;
import androidx.transition.Styleable;
import androidx.transition.TransitionPropagation;
import androidx.transition.TransitionSet;
import androidx.transition.TransitionValues;
import androidx.transition.TransitionValuesMaps;
import androidx.transition.ViewUtils;
import androidx.transition.WindowIdImpl;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition
implements Cloneable {
    static final boolean DBG = false;
    private static final int[] DEFAULT_MATCH_ORDER = new int[]{2, 1, 3, 4};
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";
    private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion(){

        @Override
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal();
    private ArrayList<Animator> mAnimators = new ArrayList();
    boolean mCanRemoveViews = false;
    ArrayList<Animator> mCurrentAnimators = new ArrayList();
    long mDuration = -1L;
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    private ArrayList<TransitionValues> mEndValuesList;
    private boolean mEnded = false;
    private EpicenterCallback mEpicenterCallback;
    private TimeInterpolator mInterpolator = null;
    private ArrayList<TransitionListener> mListeners = null;
    private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    private String mName = this.getClass().getName();
    private ArrayMap<String, String> mNameOverrides;
    private int mNumInstances = 0;
    TransitionSet mParent = null;
    private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;
    private boolean mPaused = false;
    TransitionPropagation mPropagation;
    private ViewGroup mSceneRoot = null;
    private long mStartDelay = -1L;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    private ArrayList<TransitionValues> mStartValuesList;
    private ArrayList<View> mTargetChildExcludes = null;
    private ArrayList<View> mTargetExcludes = null;
    private ArrayList<Integer> mTargetIdChildExcludes = null;
    private ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList();
    private ArrayList<String> mTargetNameExcludes = null;
    private ArrayList<String> mTargetNames = null;
    private ArrayList<Class<?>> mTargetTypeChildExcludes = null;
    private ArrayList<Class<?>> mTargetTypeExcludes = null;
    private ArrayList<Class<?>> mTargetTypes = null;
    ArrayList<View> mTargets = new ArrayList();

    public Transition() {
    }

    public Transition(Context object, AttributeSet object2) {
        int n;
        TypedArray typedArray = object.obtainStyledAttributes(object2, Styleable.TRANSITION);
        object2 = (XmlResourceParser)object2;
        long l = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)object2, "duration", 1, -1);
        if (l >= 0L) {
            this.setDuration(l);
        }
        if ((l = (long)TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)object2, "startDelay", 2, -1)) > 0L) {
            this.setStartDelay(l);
        }
        if ((n = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)object2, "interpolator", 0, 0)) > 0) {
            this.setInterpolator((TimeInterpolator)AnimationUtils.loadInterpolator((Context)object, (int)n));
        }
        if ((object = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)object2, "matchOrder", 3)) != null) {
            this.setMatchOrder(Transition.parseMatchOrder((String)object));
        }
        typedArray.recycle();
    }

    private void addUnmatched(ArrayMap<View, TransitionValues> object, ArrayMap<View, TransitionValues> arrayMap) {
        int n;
        int n2 = 0;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= ((SimpleArrayMap)object).size()) break;
            TransitionValues transitionValues = (TransitionValues)((SimpleArrayMap)object).valueAt(n3);
            if (this.isValidTarget(transitionValues.view)) {
                this.mStartValuesList.add(transitionValues);
                this.mEndValuesList.add(null);
            }
            ++n3;
        } while (true);
        while (n < arrayMap.size()) {
            object = (TransitionValues)arrayMap.valueAt(n);
            if (this.isValidTarget(((TransitionValues)object).view)) {
                this.mEndValuesList.add((TransitionValues)object);
                this.mStartValuesList.add(null);
            }
            ++n;
        }
    }

    private static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues object) {
        transitionValuesMaps.mViewValues.put(view, (TransitionValues)object);
        int n = view.getId();
        if (n >= 0) {
            if (transitionValuesMaps.mIdValues.indexOfKey(n) >= 0) {
                transitionValuesMaps.mIdValues.put(n, null);
            } else {
                transitionValuesMaps.mIdValues.put(n, (Object)view);
            }
        }
        if ((object = ViewCompat.getTransitionName(view)) != null) {
            if (transitionValuesMaps.mNameValues.containsKey(object)) {
                transitionValuesMaps.mNameValues.put((String)object, null);
            } else {
                transitionValuesMaps.mNameValues.put((String)object, view);
            }
        }
        if (!(view.getParent() instanceof ListView)) return;
        object = (ListView)view.getParent();
        if (!object.getAdapter().hasStableIds()) return;
        long l = object.getItemIdAtPosition(object.getPositionForView(view));
        if (transitionValuesMaps.mItemIdValues.indexOfKey(l) >= 0) {
            view = transitionValuesMaps.mItemIdValues.get(l);
            if (view == null) return;
            ViewCompat.setHasTransientState(view, false);
            transitionValuesMaps.mItemIdValues.put(l, null);
            return;
        }
        ViewCompat.setHasTransientState(view, true);
        transitionValuesMaps.mItemIdValues.put(l, view);
    }

    private static boolean alreadyContains(int[] arrn, int n) {
        int n2 = arrn[n];
        int n3 = 0;
        while (n3 < n) {
            if (arrn[n3] == n2) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void captureHierarchy(View view, boolean bl) {
        int n;
        int n2;
        if (view == null) {
            return;
        }
        int n3 = view.getId();
        ArrayList<Constable> arrayList = this.mTargetIdExcludes;
        if (arrayList != null && arrayList.contains(n3)) {
            return;
        }
        arrayList = this.mTargetExcludes;
        if (arrayList != null && arrayList.contains((Object)view)) {
            return;
        }
        arrayList = this.mTargetTypeExcludes;
        int n4 = 0;
        if (arrayList != null) {
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance((Object)view)) continue;
                return;
            }
        }
        if (view.getParent() instanceof ViewGroup) {
            arrayList = new TransitionValues(view);
            if (bl) {
                this.captureStartValues((TransitionValues)((Object)arrayList));
            } else {
                this.captureEndValues((TransitionValues)((Object)arrayList));
            }
            ((TransitionValues)arrayList).mTargetedTransitions.add(this);
            this.capturePropagationValues((TransitionValues)((Object)arrayList));
            if (bl) {
                Transition.addViewValues(this.mStartValues, view, arrayList);
            } else {
                Transition.addViewValues(this.mEndValues, view, arrayList);
            }
        }
        if (!(view instanceof ViewGroup)) return;
        arrayList = this.mTargetIdChildExcludes;
        if (arrayList != null && arrayList.contains(n3)) {
            return;
        }
        arrayList = this.mTargetChildExcludes;
        if (arrayList != null && arrayList.contains((Object)view)) {
            return;
        }
        arrayList = this.mTargetTypeChildExcludes;
        if (arrayList != null) {
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                if (!this.mTargetTypeChildExcludes.get(n).isInstance((Object)view)) continue;
                return;
            }
        }
        view = (ViewGroup)view;
        n = n4;
        while (n < view.getChildCount()) {
            this.captureHierarchy(view.getChildAt(n), bl);
            ++n;
        }
    }

    private ArrayList<Integer> excludeId(ArrayList<Integer> arrayList, int n, boolean bl) {
        ArrayList<Integer> arrayList2 = arrayList;
        if (n <= 0) return arrayList2;
        if (!bl) return ArrayListManager.remove(arrayList, n);
        return ArrayListManager.add(arrayList, n);
    }

    private static <T> ArrayList<T> excludeObject(ArrayList<T> arrayList, T t, boolean bl) {
        ArrayList<T> arrayList2 = arrayList;
        if (t == null) return arrayList2;
        if (!bl) return ArrayListManager.remove(arrayList, t);
        return ArrayListManager.add(arrayList, t);
    }

    private ArrayList<Class<?>> excludeType(ArrayList<Class<?>> arrayList, Class<?> class_, boolean bl) {
        ArrayList<Class<?>> arrayList2 = arrayList;
        if (class_ == null) return arrayList2;
        if (!bl) return ArrayListManager.remove(arrayList, class_);
        return ArrayListManager.add(arrayList, class_);
    }

    private ArrayList<View> excludeView(ArrayList<View> arrayList, View view, boolean bl) {
        ArrayList<View> arrayList2 = arrayList;
        if (view == null) return arrayList2;
        if (!bl) return ArrayListManager.remove(arrayList, view);
        return ArrayListManager.add(arrayList, view);
    }

    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> arrayMap;
        ArrayMap<Object, AnimationInfo> arrayMap2 = arrayMap = sRunningAnimators.get();
        if (arrayMap != null) return arrayMap2;
        arrayMap2 = new ArrayMap();
        sRunningAnimators.set(arrayMap2);
        return arrayMap2;
    }

    private static boolean isValidMatch(int n) {
        boolean bl = true;
        if (n < 1) return false;
        if (n > 4) return false;
        return bl;
    }

    private static boolean isValueChanged(TransitionValues object, TransitionValues object2, String string2) {
        object = ((TransitionValues)object).values.get(string2);
        object2 = ((TransitionValues)object2).values.get(string2);
        boolean bl = true;
        if (object == null && object2 == null) {
            return false;
        }
        boolean bl2 = bl;
        if (object == null) return bl2;
        if (object2 != null) return true ^ object.equals(object2);
        return bl;
    }

    private void matchIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        int n = sparseArray.size();
        int n2 = 0;
        while (n2 < n) {
            View view;
            View view2 = (View)sparseArray.valueAt(n2);
            if (view2 != null && this.isValidTarget(view2) && (view = (View)sparseArray2.get(sparseArray.keyAt(n2))) != null && this.isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
                TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove((Object)view2);
                    arrayMap2.remove((Object)view);
                }
            }
            ++n2;
        }
    }

    private void matchInstances(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        int n = arrayMap.size() - 1;
        while (n >= 0) {
            Object object = (View)arrayMap.keyAt(n);
            if (object != null && this.isValidTarget((View)object) && (object = (TransitionValues)arrayMap2.remove(object)) != null && this.isValidTarget(object.view)) {
                TransitionValues transitionValues = (TransitionValues)arrayMap.removeAt(n);
                this.mStartValuesList.add(transitionValues);
                this.mEndValuesList.add((TransitionValues)object);
            }
            --n;
        }
    }

    private void matchItemIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        int n = longSparseArray.size();
        int n2 = 0;
        while (n2 < n) {
            View view;
            View view2 = longSparseArray.valueAt(n2);
            if (view2 != null && this.isValidTarget(view2) && (view = longSparseArray2.get(longSparseArray.keyAt(n2))) != null && this.isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
                TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove((Object)view2);
                    arrayMap2.remove((Object)view);
                }
            }
            ++n2;
        }
    }

    private void matchNames(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        int n = arrayMap3.size();
        int n2 = 0;
        while (n2 < n) {
            View view;
            View view2 = (View)arrayMap3.valueAt(n2);
            if (view2 != null && this.isValidTarget(view2) && (view = (View)arrayMap4.get(arrayMap3.keyAt(n2))) != null && this.isValidTarget(view)) {
                TransitionValues transitionValues = (TransitionValues)arrayMap.get((Object)view2);
                TransitionValues transitionValues2 = (TransitionValues)arrayMap2.get((Object)view);
                if (transitionValues != null && transitionValues2 != null) {
                    this.mStartValuesList.add(transitionValues);
                    this.mEndValuesList.add(transitionValues2);
                    arrayMap.remove((Object)view2);
                    arrayMap2.remove((Object)view);
                }
            }
            ++n2;
        }
    }

    private void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<View, TransitionValues>(transitionValuesMaps.mViewValues);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<View, TransitionValues>(transitionValuesMaps2.mViewValues);
        int n = 0;
        do {
            int[] arrn;
            if (n >= (arrn = this.mMatchOrder).length) {
                this.addUnmatched(arrayMap, arrayMap2);
                return;
            }
            int n2 = arrn[n];
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 == 4) {
                            this.matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.mItemIdValues, transitionValuesMaps2.mItemIdValues);
                        }
                    } else {
                        this.matchIds(arrayMap, arrayMap2, transitionValuesMaps.mIdValues, transitionValuesMaps2.mIdValues);
                    }
                } else {
                    this.matchNames(arrayMap, arrayMap2, transitionValuesMaps.mNameValues, transitionValuesMaps2.mNameValues);
                }
            } else {
                this.matchInstances(arrayMap, arrayMap2);
            }
            ++n;
        } while (true);
    }

    private static int[] parseMatchOrder(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, ",");
        object = new int[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int[] arrn = stringTokenizer.nextToken().trim();
            if (MATCH_ID_STR.equalsIgnoreCase((String)arrn)) {
                object[n] = 3;
            } else if (MATCH_INSTANCE_STR.equalsIgnoreCase((String)arrn)) {
                object[n] = 1;
            } else if (MATCH_NAME_STR.equalsIgnoreCase((String)arrn)) {
                object[n] = 2;
            } else if (MATCH_ITEM_ID_STR.equalsIgnoreCase((String)arrn)) {
                object[n] = 4;
            } else {
                if (!arrn.isEmpty()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown match type in matchOrder: '");
                    ((StringBuilder)object).append((String)arrn);
                    ((StringBuilder)object).append("'");
                    throw new InflateException(((StringBuilder)object).toString());
                }
                arrn = new int[((int[])object).length - 1];
                System.arraycopy(object, 0, arrn, 0, n);
                --n;
                object = arrn;
            }
            ++n;
        }
        return object;
    }

    private void runAnimator(Animator animator2, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator2 == null) return;
        animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                arrayMap.remove((Object)animator2);
                Transition.this.mCurrentAnimators.remove((Object)animator2);
            }

            public void onAnimationStart(Animator animator2) {
                Transition.this.mCurrentAnimators.add(animator2);
            }
        });
        this.animate(animator2);
    }

    public Transition addListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    public Transition addTarget(int n) {
        if (n == 0) return this;
        this.mTargetIds.add(n);
        return this;
    }

    public Transition addTarget(View view) {
        this.mTargets.add(view);
        return this;
    }

    public Transition addTarget(Class<?> class_) {
        if (this.mTargetTypes == null) {
            this.mTargetTypes = new ArrayList();
        }
        this.mTargetTypes.add(class_);
        return this;
    }

    public Transition addTarget(String string2) {
        if (this.mTargetNames == null) {
            this.mTargetNames = new ArrayList();
        }
        this.mTargetNames.add(string2);
        return this;
    }

    protected void animate(Animator animator2) {
        if (animator2 == null) {
            this.end();
            return;
        }
        if (this.getDuration() >= 0L) {
            animator2.setDuration(this.getDuration());
        }
        if (this.getStartDelay() >= 0L) {
            animator2.setStartDelay(this.getStartDelay() + animator2.getStartDelay());
        }
        if (this.getInterpolator() != null) {
            animator2.setInterpolator(this.getInterpolator());
        }
        animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                Transition.this.end();
                animator2.removeListener((Animator.AnimatorListener)this);
            }
        });
        animator2.start();
    }

    protected void cancel() {
        int n;
        for (n = this.mCurrentAnimators.size() - 1; n >= 0; --n) {
            this.mCurrentAnimators.get(n).cancel();
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList == null) return;
        if (arrayList.size() <= 0) return;
        arrayList = (ArrayList)this.mListeners.clone();
        int n2 = arrayList.size();
        n = 0;
        while (n < n2) {
            ((TransitionListener)arrayList.get(n)).onTransitionCancel(this);
            ++n;
        }
    }

    public abstract void captureEndValues(TransitionValues var1);

    void capturePropagationValues(TransitionValues transitionValues) {
        int n;
        block2 : {
            if (this.mPropagation == null) return;
            if (transitionValues.values.isEmpty()) return;
            String[] arrstring = this.mPropagation.getPropagationProperties();
            if (arrstring == null) {
                return;
            }
            int n2 = 0;
            for (n = 0; n < arrstring.length; ++n) {
                if (transitionValues.values.containsKey(arrstring[n])) continue;
                n = n2;
                break block2;
            }
            n = 1;
        }
        if (n != 0) return;
        this.mPropagation.captureValues(transitionValues);
    }

    public abstract void captureStartValues(TransitionValues var1);

    void captureValues(ViewGroup object, boolean bl) {
        View view;
        Object object2;
        int n;
        this.clearValues(bl);
        int n2 = this.mTargetIds.size();
        int n3 = 0;
        if (n2 <= 0 && this.mTargets.size() <= 0 || (object2 = this.mTargetNames) != null && !((ArrayList)object2).isEmpty() || (object2 = this.mTargetTypes) != null && !((ArrayList)object2).isEmpty()) {
            this.captureHierarchy((View)object, bl);
        } else {
            for (n2 = 0; n2 < this.mTargetIds.size(); ++n2) {
                view = object.findViewById(this.mTargetIds.get(n2).intValue());
                if (view == null) continue;
                object2 = new TransitionValues(view);
                if (bl) {
                    this.captureStartValues((TransitionValues)object2);
                } else {
                    this.captureEndValues((TransitionValues)object2);
                }
                ((TransitionValues)object2).mTargetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object2);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, view, (TransitionValues)object2);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, view, (TransitionValues)object2);
            }
            for (n2 = 0; n2 < this.mTargets.size(); ++n2) {
                object = this.mTargets.get(n2);
                object2 = new TransitionValues((View)object);
                if (bl) {
                    this.captureStartValues((TransitionValues)object2);
                } else {
                    this.captureEndValues((TransitionValues)object2);
                }
                ((TransitionValues)object2).mTargetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object2);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, (View)object, (TransitionValues)object2);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, (View)object, (TransitionValues)object2);
            }
        }
        if (bl) return;
        object = this.mNameOverrides;
        if (object == null) return;
        int n4 = ((SimpleArrayMap)object).size();
        object = new ArrayList<E>(n4);
        n2 = 0;
        do {
            n = n3;
            if (n2 >= n4) break;
            object2 = (String)this.mNameOverrides.keyAt(n2);
            ((ArrayList)object).add(this.mStartValues.mNameValues.remove(object2));
            ++n2;
        } while (true);
        while (n < n4) {
            view = (View)((ArrayList)object).get(n);
            if (view != null) {
                object2 = (String)this.mNameOverrides.valueAt(n);
                this.mStartValues.mNameValues.put((String)object2, view);
            }
            ++n;
        }
    }

    void clearValues(boolean bl) {
        if (bl) {
            this.mStartValues.mViewValues.clear();
            this.mStartValues.mIdValues.clear();
            this.mStartValues.mItemIdValues.clear();
            return;
        }
        this.mEndValues.mViewValues.clear();
        this.mEndValues.mIdValues.clear();
        this.mEndValues.mItemIdValues.clear();
    }

    public Transition clone() {
        try {
            Transition transition = (Transition)super.clone();
            Object object = new ArrayList();
            transition.mAnimators = object;
            transition.mStartValues = object = new TransitionValuesMaps();
            transition.mEndValues = object = new TransitionValuesMaps();
            transition.mStartValuesList = null;
            transition.mEndValuesList = null;
            return transition;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps object, TransitionValuesMaps transitionValuesMaps, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        int n;
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int n2 = arrayList.size();
        long l = Long.MAX_VALUE;
        int n3 = 0;
        while (n3 < n2) {
            long l2;
            block17 : {
                Object object2;
                TransitionValues transitionValues;
                Object object3;
                Object object4;
                Object object5;
                block19 : {
                    block13 : {
                        int n4;
                        block18 : {
                            block15 : {
                                block16 : {
                                    block12 : {
                                        block14 : {
                                            object2 = arrayList.get(n3);
                                            object = arrayList2.get(n3);
                                            transitionValues = object2;
                                            if (object2 != null) {
                                                transitionValues = object2;
                                                if (!((TransitionValues)object2).mTargetedTransitions.contains(this)) {
                                                    transitionValues = null;
                                                }
                                            }
                                            object4 = object;
                                            if (object != null) {
                                                object4 = object;
                                                if (!((TransitionValues)object).mTargetedTransitions.contains(this)) {
                                                    object4 = null;
                                                }
                                            }
                                            if (transitionValues == null && object4 == null) break block14;
                                            n = transitionValues != null && object4 != null && !this.isTransitionRequired(transitionValues, (TransitionValues)object4) ? 0 : 1;
                                            if (n == 0 || (object = this.createAnimator(viewGroup, transitionValues, (TransitionValues)object4)) == null) break block14;
                                            if (object4 == null) break block15;
                                            object3 = ((TransitionValues)object4).view;
                                            String[] arrstring = this.getTransitionProperties();
                                            if (arrstring == null || arrstring.length <= 0) break block16;
                                            object5 = new TransitionValues((View)object3);
                                            object2 = (TransitionValues)transitionValuesMaps.mViewValues.get(object3);
                                            n = n3;
                                            if (object2 != null) {
                                                n4 = 0;
                                                do {
                                                    n = n3;
                                                    if (n4 < arrstring.length) {
                                                        ((TransitionValues)object5).values.put(arrstring[n4], ((TransitionValues)object2).values.get(arrstring[n4]));
                                                        ++n4;
                                                        continue;
                                                    }
                                                    break block12;
                                                    break;
                                                } while (true);
                                            }
                                            break block12;
                                        }
                                        l2 = l;
                                        n = n3;
                                        break block17;
                                    }
                                    n3 = n;
                                    n4 = arrayMap.size();
                                    break block18;
                                }
                                object2 = null;
                                break block13;
                            }
                            object3 = transitionValues.view;
                            object5 = object;
                            object2 = null;
                            object = object3;
                            break block19;
                        }
                        for (n = 0; n < n4; ++n) {
                            object2 = (AnimationInfo)arrayMap.get((Object)((Animator)arrayMap.keyAt(n)));
                            if (((AnimationInfo)object2).mValues == null || ((AnimationInfo)object2).mView != object3 || !((AnimationInfo)object2).mName.equals(this.getName()) || !((AnimationInfo)object2).mValues.equals(object5)) continue;
                            object = null;
                            object2 = object5;
                            break block13;
                        }
                        object2 = object5;
                    }
                    object5 = object;
                    object = object3;
                }
                l2 = l;
                n = n3;
                if (object5 != null) {
                    object3 = this.mPropagation;
                    l2 = l;
                    if (object3 != null) {
                        l2 = ((TransitionPropagation)object3).getStartDelay(viewGroup, this, transitionValues, (TransitionValues)object4);
                        sparseIntArray.put(this.mAnimators.size(), (int)l2);
                        l2 = Math.min(l2, l);
                    }
                    arrayMap.put((Animator)object5, new AnimationInfo((View)object, this.getName(), this, ViewUtils.getWindowId((View)viewGroup), (TransitionValues)object2));
                    this.mAnimators.add((Animator)object5);
                    n = n3;
                }
            }
            n3 = n + 1;
            l = l2;
        }
        if (sparseIntArray.size() == 0) return;
        n3 = 0;
        while (n3 < sparseIntArray.size()) {
            n = sparseIntArray.keyAt(n3);
            viewGroup = this.mAnimators.get(n);
            viewGroup.setStartDelay((long)sparseIntArray.valueAt(n3) - l + viewGroup.getStartDelay());
            ++n3;
        }
    }

    protected void end() {
        int n;
        this.mNumInstances = n = this.mNumInstances - 1;
        if (n != 0) return;
        ArrayList arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
            arrayList = (ArrayList)this.mListeners.clone();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                ((TransitionListener)arrayList.get(n)).onTransitionEnd(this);
            }
        }
        for (n = 0; n < this.mStartValues.mItemIdValues.size(); ++n) {
            arrayList = this.mStartValues.mItemIdValues.valueAt(n);
            if (arrayList == null) continue;
            ViewCompat.setHasTransientState((View)arrayList, false);
        }
        n = 0;
        do {
            if (n >= this.mEndValues.mItemIdValues.size()) {
                this.mEnded = true;
                return;
            }
            arrayList = this.mEndValues.mItemIdValues.valueAt(n);
            if (arrayList != null) {
                ViewCompat.setHasTransientState((View)arrayList, false);
            }
            ++n;
        } while (true);
    }

    public Transition excludeChildren(int n, boolean bl) {
        this.mTargetIdChildExcludes = this.excludeId(this.mTargetIdChildExcludes, n, bl);
        return this;
    }

    public Transition excludeChildren(View view, boolean bl) {
        this.mTargetChildExcludes = this.excludeView(this.mTargetChildExcludes, view, bl);
        return this;
    }

    public Transition excludeChildren(Class<?> class_, boolean bl) {
        this.mTargetTypeChildExcludes = this.excludeType(this.mTargetTypeChildExcludes, class_, bl);
        return this;
    }

    public Transition excludeTarget(int n, boolean bl) {
        this.mTargetIdExcludes = this.excludeId(this.mTargetIdExcludes, n, bl);
        return this;
    }

    public Transition excludeTarget(View view, boolean bl) {
        this.mTargetExcludes = this.excludeView(this.mTargetExcludes, view, bl);
        return this;
    }

    public Transition excludeTarget(Class<?> class_, boolean bl) {
        this.mTargetTypeExcludes = this.excludeType(this.mTargetTypeExcludes, class_, bl);
        return this;
    }

    public Transition excludeTarget(String string2, boolean bl) {
        this.mTargetNameExcludes = Transition.excludeObject(this.mTargetNameExcludes, string2, bl);
        return this;
    }

    void forceToEnd(ViewGroup object) {
        Object object2 = Transition.getRunningAnimators();
        int n = ((SimpleArrayMap)object2).size();
        if (object == null) return;
        if (n == 0) {
            return;
        }
        object = ViewUtils.getWindowId((View)object);
        ArrayMap<K, V> arrayMap = new ArrayMap<K, V>((SimpleArrayMap)object2);
        ((SimpleArrayMap)object2).clear();
        --n;
        while (n >= 0) {
            object2 = (AnimationInfo)arrayMap.valueAt(n);
            if (((AnimationInfo)object2).mView != null && object != null && object.equals(((AnimationInfo)object2).mWindowId)) {
                ((Animator)arrayMap.keyAt(n)).end();
            }
            --n;
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    public Rect getEpicenter() {
        EpicenterCallback epicenterCallback = this.mEpicenterCallback;
        if (epicenterCallback != null) return epicenterCallback.onGetEpicenter(this);
        return null;
    }

    public EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    TransitionValues getMatchedTransitionValues(View object, boolean bl) {
        int n;
        Cloneable cloneable = this.mParent;
        if (cloneable != null) {
            return ((Transition)cloneable).getMatchedTransitionValues((View)object, bl);
        }
        cloneable = bl ? this.mStartValuesList : this.mEndValuesList;
        Object var4_4 = null;
        if (cloneable == null) {
            return null;
        }
        int n2 = ((ArrayList)cloneable).size();
        int n3 = -1;
        int n4 = 0;
        do {
            n = n3;
            if (n4 >= n2) break;
            TransitionValues transitionValues = (TransitionValues)((ArrayList)cloneable).get(n4);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == object) {
                n = n4;
                break;
            }
            ++n4;
        } while (true);
        object = var4_4;
        if (n < 0) return object;
        object = bl ? this.mEndValuesList : this.mStartValuesList;
        return (TransitionValues)((ArrayList)object).get(n);
    }

    public String getName() {
        return this.mName;
    }

    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    public TransitionPropagation getPropagation() {
        return this.mPropagation;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public List<Integer> getTargetIds() {
        return this.mTargetIds;
    }

    public List<String> getTargetNames() {
        return this.mTargetNames;
    }

    public List<Class<?>> getTargetTypes() {
        return this.mTargetTypes;
    }

    public List<View> getTargets() {
        return this.mTargets;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public TransitionValues getTransitionValues(View view, boolean bl) {
        Object object = this.mParent;
        if (object != null) {
            return ((Transition)object).getTransitionValues(view, bl);
        }
        if (bl) {
            object = this.mStartValues;
            return (TransitionValues)((TransitionValuesMaps)object).mViewValues.get((Object)view);
        }
        object = this.mEndValues;
        return (TransitionValues)((TransitionValuesMaps)object).mViewValues.get((Object)view);
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (transitionValues == null) return bl2;
        bl2 = bl;
        if (transitionValues2 == null) return bl2;
        Object object = this.getTransitionProperties();
        if (object == null) {
            object = transitionValues.values.keySet().iterator();
            do {
                bl2 = bl;
                if (!object.hasNext()) return bl2;
            } while (!Transition.isValueChanged(transitionValues, transitionValues2, (String)object.next()));
            return true;
        } else {
            int n = ((String[])object).length;
            int n2 = 0;
            do {
                bl2 = bl;
                if (n2 >= n) return bl2;
                if (Transition.isValueChanged(transitionValues, transitionValues2, (String)object[n2])) return true;
                ++n2;
            } while (true);
        }
    }

    boolean isValidTarget(View view) {
        int n;
        int n2 = view.getId();
        ArrayList<Object> arrayList = this.mTargetIdExcludes;
        if (arrayList != null && arrayList.contains(n2)) {
            return false;
        }
        arrayList = this.mTargetExcludes;
        if (arrayList != null && arrayList.contains((Object)view)) {
            return false;
        }
        arrayList = this.mTargetTypeExcludes;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (n = 0; n < n3; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance((Object)view)) continue;
                return false;
            }
        }
        if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(view) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && ((arrayList = this.mTargetTypes) == null || arrayList.isEmpty())) {
            arrayList = this.mTargetNames;
            if (arrayList == null) return true;
            if (arrayList.isEmpty()) {
                return true;
            }
        }
        if (this.mTargetIds.contains(n2)) return true;
        if (this.mTargets.contains((Object)view)) {
            return true;
        }
        arrayList = this.mTargetNames;
        if (arrayList != null && arrayList.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.mTargetTypes == null) return false;
        n = 0;
        while (n < this.mTargetTypes.size()) {
            if (this.mTargetTypes.get(n).isInstance((Object)view)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public void pause(View object) {
        if (this.mEnded) return;
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)object);
        --n;
        while (n >= 0) {
            object = (AnimationInfo)arrayMap.valueAt(n);
            if (((AnimationInfo)object).mView != null && windowIdImpl.equals(((AnimationInfo)object).mWindowId)) {
                AnimatorUtils.pause((Animator)arrayMap.keyAt(n));
            }
            --n;
        }
        object = this.mListeners;
        if (object != null && ((ArrayList)object).size() > 0) {
            object = (ArrayList)this.mListeners.clone();
            int n2 = ((ArrayList)object).size();
            for (n = 0; n < n2; ++n) {
                ((TransitionListener)((ArrayList)object).get(n)).onTransitionPause(this);
            }
        }
        this.mPaused = true;
    }

    void playTransition(ViewGroup viewGroup) {
        this.mStartValuesList = new ArrayList<E>();
        this.mEndValuesList = new ArrayList<E>();
        this.matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)viewGroup);
        --n;
        do {
            AnimationInfo animationInfo;
            if (n < 0) {
                this.createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
                this.runAnimators();
                return;
            }
            Animator animator2 = (Animator)arrayMap.keyAt(n);
            if (animator2 != null && (animationInfo = (AnimationInfo)arrayMap.get((Object)animator2)) != null && animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId)) {
                boolean bl;
                TransitionValues transitionValues;
                TransitionValues transitionValues2 = animationInfo.mValues;
                View view = animationInfo.mView;
                TransitionValues transitionValues3 = this.getTransitionValues(view, true);
                TransitionValues transitionValues4 = transitionValues = this.getMatchedTransitionValues(view, true);
                if (transitionValues3 == null) {
                    transitionValues4 = transitionValues;
                    if (transitionValues == null) {
                        transitionValues4 = (TransitionValues)this.mEndValues.mViewValues.get((Object)view);
                    }
                }
                if (bl = (transitionValues3 != null || transitionValues4 != null) && animationInfo.mTransition.isTransitionRequired(transitionValues2, transitionValues4)) {
                    if (!animator2.isRunning() && !animator2.isStarted()) {
                        arrayMap.remove((Object)animator2);
                    } else {
                        animator2.cancel();
                    }
                }
            }
            --n;
        } while (true);
    }

    public Transition removeListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(transitionListener);
        if (this.mListeners.size() != 0) return this;
        this.mListeners = null;
        return this;
    }

    public Transition removeTarget(int n) {
        if (n == 0) return this;
        this.mTargetIds.remove((Object)n);
        return this;
    }

    public Transition removeTarget(View view) {
        this.mTargets.remove((Object)view);
        return this;
    }

    public Transition removeTarget(Class<?> class_) {
        ArrayList<Class<?>> arrayList = this.mTargetTypes;
        if (arrayList == null) return this;
        arrayList.remove(class_);
        return this;
    }

    public Transition removeTarget(String string2) {
        ArrayList<String> arrayList = this.mTargetNames;
        if (arrayList == null) return this;
        arrayList.remove(string2);
        return this;
    }

    public void resume(View object) {
        if (!this.mPaused) return;
        if (!this.mEnded) {
            ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
            int n = arrayMap.size();
            object = ViewUtils.getWindowId((View)object);
            --n;
            while (n >= 0) {
                AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(n);
                if (animationInfo.mView != null && object.equals(animationInfo.mWindowId)) {
                    AnimatorUtils.resume((Animator)arrayMap.keyAt(n));
                }
                --n;
            }
            object = this.mListeners;
            if (object != null && ((ArrayList)object).size() > 0) {
                object = (ArrayList)this.mListeners.clone();
                int n2 = ((ArrayList)object).size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)((ArrayList)object).get(n)).onTransitionResume(this);
                }
            }
        }
        this.mPaused = false;
    }

    protected void runAnimators() {
        this.start();
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        Iterator<Animator> iterator2 = this.mAnimators.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mAnimators.clear();
                this.end();
                return;
            }
            Animator animator2 = iterator2.next();
            if (!arrayMap.containsKey((Object)animator2)) continue;
            this.start();
            this.runAnimator(animator2, arrayMap);
        } while (true);
    }

    void setCanRemoveViews(boolean bl) {
        this.mCanRemoveViews = bl;
    }

    public Transition setDuration(long l) {
        this.mDuration = l;
        return this;
    }

    public void setEpicenterCallback(EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    public Transition setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public void setMatchOrder(int ... arrn) {
        if (arrn != null && arrn.length != 0) {
            int n = 0;
            do {
                if (n >= arrn.length) {
                    this.mMatchOrder = (int[])arrn.clone();
                    return;
                }
                if (!Transition.isValidMatch(arrn[n])) throw new IllegalArgumentException("matches contains invalid value");
                if (Transition.alreadyContains(arrn, n)) throw new IllegalArgumentException("matches contains a duplicate value");
                ++n;
            } while (true);
        }
        this.mMatchOrder = DEFAULT_MATCH_ORDER;
    }

    public void setPathMotion(PathMotion pathMotion) {
        if (pathMotion == null) {
            this.mPathMotion = STRAIGHT_PATH_MOTION;
            return;
        }
        this.mPathMotion = pathMotion;
    }

    public void setPropagation(TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    Transition setSceneRoot(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
        return this;
    }

    public Transition setStartDelay(long l) {
        this.mStartDelay = l;
        return this;
    }

    protected void start() {
        if (this.mNumInstances == 0) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                arrayList = (ArrayList)this.mListeners.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((TransitionListener)arrayList.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        ++this.mNumInstances;
    }

    public String toString() {
        return this.toString("");
    }

    String toString(String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(this.getClass().getSimpleName());
        ((StringBuilder)charSequence2).append("@");
        ((StringBuilder)charSequence2).append(Integer.toHexString(this.hashCode()));
        ((StringBuilder)charSequence2).append(": ");
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = charSequence;
        if (this.mDuration != -1L) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("dur(");
            ((StringBuilder)charSequence2).append(this.mDuration);
            ((StringBuilder)charSequence2).append(") ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.mStartDelay != -1L) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("dly(");
            ((StringBuilder)charSequence).append(this.mStartDelay);
            ((StringBuilder)charSequence).append(") ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.mInterpolator != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("interp(");
            ((StringBuilder)charSequence2).append((Object)this.mInterpolator);
            ((StringBuilder)charSequence2).append(") ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        if (this.mTargetIds.size() <= 0) {
            charSequence = charSequence2;
            if (this.mTargets.size() <= 0) return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("tgts(");
        charSequence2 = ((StringBuilder)charSequence).toString();
        int n = this.mTargetIds.size();
        int n2 = 0;
        charSequence = charSequence2;
        if (n > 0) {
            n = 0;
            do {
                charSequence = charSequence2;
                if (n >= this.mTargetIds.size()) break;
                charSequence = charSequence2;
                if (n > 0) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(", ");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(this.mTargetIds.get(n));
                charSequence2 = ((StringBuilder)charSequence2).toString();
                ++n;
            } while (true);
        }
        charSequence2 = charSequence;
        if (this.mTargets.size() > 0) {
            n = n2;
            do {
                charSequence2 = charSequence;
                if (n >= this.mTargets.size()) break;
                charSequence2 = charSequence;
                if (n > 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(", ");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append((Object)this.mTargets.get(n));
                charSequence = ((StringBuilder)charSequence).toString();
                ++n;
            } while (true);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(")");
        return ((StringBuilder)charSequence).toString();
    }

    private static class AnimationInfo {
        String mName;
        Transition mTransition;
        TransitionValues mValues;
        View mView;
        WindowIdImpl mWindowId;

        AnimationInfo(View view, String string2, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.mView = view;
            this.mName = string2;
            this.mValues = transitionValues;
            this.mWindowId = windowIdImpl;
            this.mTransition = transition;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
            ArrayList<Object> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList();
            }
            if (arrayList2.contains(t)) return arrayList2;
            arrayList2.add(t);
            return arrayList2;
        }

        static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
            ArrayList<T> arrayList2 = arrayList;
            if (arrayList == null) return arrayList2;
            arrayList.remove(t);
            arrayList2 = arrayList;
            if (!arrayList.isEmpty()) return arrayList2;
            return null;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(Transition var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MatchOrder {
    }

    public static interface TransitionListener {
        public void onTransitionCancel(Transition var1);

        public void onTransitionEnd(Transition var1);

        public void onTransitionPause(Transition var1);

        public void onTransitionResume(Transition var1);

        public void onTransitionStart(Transition var1);
    }

}

