/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.PathMotion;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionPropagation;
import androidx.transition.TransitionValues;
import androidx.transition.TransitionValuesMaps;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionSet
extends Transition {
    private static final int FLAG_CHANGE_EPICENTER = 8;
    private static final int FLAG_CHANGE_INTERPOLATOR = 1;
    private static final int FLAG_CHANGE_PATH_MOTION = 4;
    private static final int FLAG_CHANGE_PROPAGATION = 2;
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    private int mChangeFlags = 0;
    int mCurrentListeners;
    private boolean mPlayTogether = true;
    boolean mStarted = false;
    private ArrayList<Transition> mTransitions = new ArrayList();

    public TransitionSet() {
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.TRANSITION_SET);
        this.setOrdering(TypedArrayUtils.getNamedInt((TypedArray)context, (XmlResourceParser)attributeSet, "transitionOrdering", 0, 0));
        context.recycle();
    }

    private void addTransitionInternal(Transition transition) {
        this.mTransitions.add(transition);
        transition.mParent = this;
    }

    private void setupStartEndListeners() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator<Transition> iterator2 = this.mTransitions.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mCurrentListeners = this.mTransitions.size();
                return;
            }
            iterator2.next().addListener(transitionSetListener);
        } while (true);
    }

    @Override
    public TransitionSet addListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.addListener(transitionListener);
    }

    @Override
    public TransitionSet addTarget(int n) {
        int n2 = 0;
        while (n2 < this.mTransitions.size()) {
            this.mTransitions.get(n2).addTarget(n);
            ++n2;
        }
        return (TransitionSet)super.addTarget(n);
    }

    @Override
    public TransitionSet addTarget(View view) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).addTarget(view);
            ++n;
        }
        return (TransitionSet)super.addTarget(view);
    }

    @Override
    public TransitionSet addTarget(Class<?> class_) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).addTarget(class_);
            ++n;
        }
        return (TransitionSet)super.addTarget(class_);
    }

    @Override
    public TransitionSet addTarget(String string2) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).addTarget(string2);
            ++n;
        }
        return (TransitionSet)super.addTarget(string2);
    }

    public TransitionSet addTransition(Transition transition) {
        this.addTransitionInternal(transition);
        if (this.mDuration >= 0L) {
            transition.setDuration(this.mDuration);
        }
        if ((this.mChangeFlags & 1) != 0) {
            transition.setInterpolator(this.getInterpolator());
        }
        if ((this.mChangeFlags & 2) != 0) {
            transition.setPropagation(this.getPropagation());
        }
        if ((this.mChangeFlags & 4) != 0) {
            transition.setPathMotion(this.getPathMotion());
        }
        if ((this.mChangeFlags & 8) == 0) return this;
        transition.setEpicenterCallback(this.getEpicenterCallback());
        return this;
    }

    @Override
    protected void cancel() {
        super.cancel();
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).cancel();
            ++n2;
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (!this.isValidTarget(transitionValues.view)) return;
        Iterator<Transition> iterator2 = this.mTransitions.iterator();
        while (iterator2.hasNext()) {
            Transition transition = iterator2.next();
            if (!transition.isValidTarget(transitionValues.view)) continue;
            transition.captureEndValues(transitionValues);
            transitionValues.mTargetedTransitions.add(transition);
        }
    }

    @Override
    void capturePropagationValues(TransitionValues transitionValues) {
        super.capturePropagationValues(transitionValues);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).capturePropagationValues(transitionValues);
            ++n2;
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (!this.isValidTarget(transitionValues.view)) return;
        Iterator<Transition> iterator2 = this.mTransitions.iterator();
        while (iterator2.hasNext()) {
            Transition transition = iterator2.next();
            if (!transition.isValidTarget(transitionValues.view)) continue;
            transition.captureStartValues(transitionValues);
            transitionValues.mTargetedTransitions.add(transition);
        }
    }

    @Override
    public Transition clone() {
        TransitionSet transitionSet = (TransitionSet)super.clone();
        transitionSet.mTransitions = new ArrayList();
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            transitionSet.addTransitionInternal(this.mTransitions.get(n2).clone());
            ++n2;
        }
        return transitionSet;
    }

    @Override
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long l = this.getStartDelay();
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            Transition transition = this.mTransitions.get(n2);
            if (l > 0L && (this.mPlayTogether || n2 == 0)) {
                long l2 = transition.getStartDelay();
                if (l2 > 0L) {
                    transition.setStartDelay(l2 + l);
                } else {
                    transition.setStartDelay(l);
                }
            }
            transition.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
            ++n2;
        }
    }

    @Override
    public Transition excludeTarget(int n, boolean bl) {
        int n2 = 0;
        while (n2 < this.mTransitions.size()) {
            this.mTransitions.get(n2).excludeTarget(n, bl);
            ++n2;
        }
        return super.excludeTarget(n, bl);
    }

    @Override
    public Transition excludeTarget(View view, boolean bl) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).excludeTarget(view, bl);
            ++n;
        }
        return super.excludeTarget(view, bl);
    }

    @Override
    public Transition excludeTarget(Class<?> class_, boolean bl) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).excludeTarget(class_, bl);
            ++n;
        }
        return super.excludeTarget(class_, bl);
    }

    @Override
    public Transition excludeTarget(String string2, boolean bl) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).excludeTarget(string2, bl);
            ++n;
        }
        return super.excludeTarget(string2, bl);
    }

    @Override
    void forceToEnd(ViewGroup viewGroup) {
        super.forceToEnd(viewGroup);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).forceToEnd(viewGroup);
            ++n2;
        }
    }

    public int getOrdering() {
        return this.mPlayTogether ^ true;
    }

    public Transition getTransitionAt(int n) {
        if (n < 0) return null;
        if (n < this.mTransitions.size()) return this.mTransitions.get(n);
        return null;
    }

    public int getTransitionCount() {
        return this.mTransitions.size();
    }

    @Override
    public void pause(View view) {
        super.pause(view);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).pause(view);
            ++n2;
        }
    }

    @Override
    public TransitionSet removeListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.removeListener(transitionListener);
    }

    @Override
    public TransitionSet removeTarget(int n) {
        int n2 = 0;
        while (n2 < this.mTransitions.size()) {
            this.mTransitions.get(n2).removeTarget(n);
            ++n2;
        }
        return (TransitionSet)super.removeTarget(n);
    }

    @Override
    public TransitionSet removeTarget(View view) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).removeTarget(view);
            ++n;
        }
        return (TransitionSet)super.removeTarget(view);
    }

    @Override
    public TransitionSet removeTarget(Class<?> class_) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).removeTarget(class_);
            ++n;
        }
        return (TransitionSet)super.removeTarget(class_);
    }

    @Override
    public TransitionSet removeTarget(String string2) {
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).removeTarget(string2);
            ++n;
        }
        return (TransitionSet)super.removeTarget(string2);
    }

    public TransitionSet removeTransition(Transition transition) {
        this.mTransitions.remove(transition);
        transition.mParent = null;
        return this;
    }

    @Override
    public void resume(View view) {
        super.resume(view);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).resume(view);
            ++n2;
        }
    }

    @Override
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            this.start();
            this.end();
            return;
        }
        this.setupStartEndListeners();
        if (!this.mPlayTogether) {
            int n = 1;
            do {
                if (n >= this.mTransitions.size()) {
                    Transition transition = this.mTransitions.get(0);
                    if (transition == null) return;
                    transition.runAnimators();
                    return;
                }
                this.mTransitions.get(n - 1).addListener(new TransitionListenerAdapter(this.mTransitions.get(n)){
                    final /* synthetic */ Transition val$nextTransition;
                    {
                        this.val$nextTransition = transition;
                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        this.val$nextTransition.runAnimators();
                        transition.removeListener(this);
                    }
                });
                ++n;
            } while (true);
        }
        Iterator<Transition> iterator2 = this.mTransitions.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().runAnimators();
        }
    }

    @Override
    void setCanRemoveViews(boolean bl) {
        super.setCanRemoveViews(bl);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setCanRemoveViews(bl);
            ++n2;
        }
    }

    @Override
    public TransitionSet setDuration(long l) {
        super.setDuration(l);
        if (this.mDuration < 0L) return this;
        ArrayList<Transition> arrayList = this.mTransitions;
        if (arrayList == null) return this;
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setDuration(l);
            ++n2;
        }
        return this;
    }

    @Override
    public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        this.mChangeFlags |= 8;
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setEpicenterCallback(epicenterCallback);
            ++n2;
        }
    }

    @Override
    public TransitionSet setInterpolator(TimeInterpolator timeInterpolator) {
        this.mChangeFlags |= 1;
        ArrayList<Transition> arrayList = this.mTransitions;
        if (arrayList == null) return (TransitionSet)super.setInterpolator(timeInterpolator);
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setInterpolator(timeInterpolator);
            ++n2;
        }
        return (TransitionSet)super.setInterpolator(timeInterpolator);
    }

    public TransitionSet setOrdering(int n) {
        if (n == 0) {
            this.mPlayTogether = true;
            return this;
        }
        if (n == 1) {
            this.mPlayTogether = false;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid parameter for TransitionSet ordering: ");
        stringBuilder.append(n);
        throw new AndroidRuntimeException(stringBuilder.toString());
    }

    @Override
    public void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.mChangeFlags |= 4;
        if (this.mTransitions == null) return;
        int n = 0;
        while (n < this.mTransitions.size()) {
            this.mTransitions.get(n).setPathMotion(pathMotion);
            ++n;
        }
    }

    @Override
    public void setPropagation(TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        this.mChangeFlags |= 2;
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setPropagation(transitionPropagation);
            ++n2;
        }
    }

    @Override
    TransitionSet setSceneRoot(ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            this.mTransitions.get(n2).setSceneRoot(viewGroup);
            ++n2;
        }
        return this;
    }

    @Override
    public TransitionSet setStartDelay(long l) {
        return (TransitionSet)super.setStartDelay(l);
    }

    @Override
    String toString(String string2) {
        CharSequence charSequence = super.toString(string2);
        int n = 0;
        while (n < this.mTransitions.size()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("\n");
            Transition transition = this.mTransitions.get(n);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("  ");
            stringBuilder.append(transition.toString(((StringBuilder)charSequence).toString()));
            charSequence = stringBuilder.toString();
            ++n;
        }
        return charSequence;
    }

    static class TransitionSetListener
    extends TransitionListenerAdapter {
        TransitionSet mTransitionSet;

        TransitionSetListener(TransitionSet transitionSet) {
            this.mTransitionSet = transitionSet;
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            TransitionSet transitionSet = this.mTransitionSet;
            --transitionSet.mCurrentListeners;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            transition.removeListener(this);
        }

        @Override
        public void onTransitionStart(Transition transition) {
            if (this.mTransitionSet.mStarted) return;
            this.mTransitionSet.start();
            this.mTransitionSet.mStarted = true;
        }
    }

}

