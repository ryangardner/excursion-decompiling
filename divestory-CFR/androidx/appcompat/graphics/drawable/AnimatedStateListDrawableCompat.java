/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Outline
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.StateSet
 *  android.util.Xml
 */
package androidx.appcompat.graphics.drawable;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.appcompat.graphics.drawable.DrawableContainer;
import androidx.appcompat.graphics.drawable.StateListDrawable;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedStateListDrawableCompat
extends StateListDrawable
implements TintAwareDrawable {
    private static final String ELEMENT_ITEM = "item";
    private static final String ELEMENT_TRANSITION = "transition";
    private static final String ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String LOGTAG = AnimatedStateListDrawableCompat.class.getSimpleName();
    private static final String TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable";
    private static final String TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes";
    private boolean mMutated;
    private AnimatedStateListState mState;
    private Transition mTransition;
    private int mTransitionFromIndex = -1;
    private int mTransitionToIndex = -1;

    public AnimatedStateListDrawableCompat() {
        this(null, null);
    }

    AnimatedStateListDrawableCompat(AnimatedStateListState animatedStateListState, Resources resources) {
        super(null);
        this.setConstantState(new AnimatedStateListState(animatedStateListState, this, resources));
        this.onStateChange(this.getState());
        this.jumpToCurrentState();
    }

    public static AnimatedStateListDrawableCompat create(Context object, int n, Resources.Theme theme) {
        try {
            Resources resources = object.getResources();
            XmlResourceParser xmlResourceParser = resources.getXml(n);
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
            while ((n = xmlResourceParser.next()) != 2 && n != 1) {
            }
            if (n == 2) {
                return AnimatedStateListDrawableCompat.createFromXmlInner(object, resources, xmlResourceParser, attributeSet, theme);
            }
            object = new XmlPullParserException("No start tag found");
            throw object;
        }
        catch (IOException iOException) {
            Log.e((String)LOGTAG, (String)"parser error", (Throwable)iOException);
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            Log.e((String)LOGTAG, (String)"parser error", (Throwable)xmlPullParserException);
        }
        return null;
    }

    public static AnimatedStateListDrawableCompat createFromXmlInner(Context object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        Object object2 = xmlPullParser.getName();
        if (((String)object2).equals("animated-selector")) {
            object2 = new AnimatedStateListDrawableCompat();
            ((AnimatedStateListDrawableCompat)object2).inflate((Context)object, resources, xmlPullParser, attributeSet, theme);
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid animated-selector tag ");
        ((StringBuilder)object).append((String)object2);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    private void inflateChildElements(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth() + 1;
        while ((n = xmlPullParser.next()) != 1) {
            int n3 = xmlPullParser.getDepth();
            if (n3 < n2) {
                if (n == 3) return;
            }
            if (n != 2 || n3 > n2) continue;
            if (xmlPullParser.getName().equals(ELEMENT_ITEM)) {
                this.parseItem(context, resources, xmlPullParser, attributeSet, theme);
                continue;
            }
            if (!xmlPullParser.getName().equals(ELEMENT_TRANSITION)) continue;
            this.parseTransition(context, resources, xmlPullParser, attributeSet, theme);
        }
    }

    private void init() {
        this.onStateChange(this.getState());
    }

    private int parseItem(Context object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int[] arrn = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableItem);
        int n = arrn.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_id, 0);
        int n2 = arrn.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_drawable, -1);
        Drawable drawable2 = n2 > 0 ? ResourceManagerInternal.get().getDrawable((Context)object, n2) : null;
        arrn.recycle();
        arrn = this.extractStateSet(attributeSet);
        object = drawable2;
        if (drawable2 == null) {
            while ((n2 = xmlPullParser.next()) == 4) {
            }
            if (n2 != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                ((StringBuilder)object).append(ITEM_MISSING_DRAWABLE_ERROR);
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            object = xmlPullParser.getName().equals("vector") ? VectorDrawableCompat.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : (Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet, (Resources.Theme)theme) : Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet));
        }
        if (object != null) {
            return this.mState.addStateSet(arrn, (Drawable)object, n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(ITEM_MISSING_DRAWABLE_ERROR);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    private int parseTransition(Context object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableTransition);
        int n = object2.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_fromId, -1);
        int n2 = object2.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_toId, -1);
        int n3 = object2.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_drawable, -1);
        Drawable drawable2 = n3 > 0 ? ResourceManagerInternal.get().getDrawable((Context)object, n3) : null;
        boolean bl = object2.getBoolean(R.styleable.AnimatedStateListDrawableTransition_android_reversible, false);
        object2.recycle();
        object2 = drawable2;
        if (drawable2 == null) {
            while ((n3 = xmlPullParser.next()) == 4) {
            }
            if (n3 != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                ((StringBuilder)object).append(TRANSITION_MISSING_DRAWABLE_ERROR);
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            object2 = xmlPullParser.getName().equals("animated-vector") ? AnimatedVectorDrawableCompat.createFromXmlInner((Context)object, resources, xmlPullParser, attributeSet, theme) : (Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet, (Resources.Theme)theme) : Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet));
        }
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)object).append(TRANSITION_MISSING_DRAWABLE_ERROR);
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        if (n != -1 && n2 != -1) {
            return this.mState.addTransition(n, n2, (Drawable)object2, bl);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(TRANSITION_MISSING_FROM_TO_ID);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    private boolean selectTransition(int n) {
        int n2;
        Transition transition = this.mTransition;
        if (transition != null) {
            if (n == this.mTransitionToIndex) {
                return true;
            }
            if (n == this.mTransitionFromIndex && transition.canReverse()) {
                transition.reverse();
                this.mTransitionToIndex = this.mTransitionFromIndex;
                this.mTransitionFromIndex = n;
                return true;
            }
            n2 = this.mTransitionToIndex;
            transition.stop();
        } else {
            n2 = this.getCurrentIndex();
        }
        this.mTransition = null;
        this.mTransitionFromIndex = -1;
        this.mTransitionToIndex = -1;
        AnimatedStateListState animatedStateListState = this.mState;
        int n3 = animatedStateListState.getKeyframeIdAt(n2);
        int n4 = animatedStateListState.getKeyframeIdAt(n);
        if (n4 == 0) return false;
        if (n3 == 0) {
            return false;
        }
        int n5 = animatedStateListState.indexOfTransition(n3, n4);
        if (n5 < 0) {
            return false;
        }
        boolean bl = animatedStateListState.transitionHasReversibleFlag(n3, n4);
        this.selectDrawable(n5);
        transition = this.getCurrent();
        if (transition instanceof AnimationDrawable) {
            boolean bl2 = animatedStateListState.isTransitionReversed(n3, n4);
            transition = new AnimationDrawableTransition((AnimationDrawable)transition, bl2, bl);
        } else if (transition instanceof AnimatedVectorDrawableCompat) {
            transition = new AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat)((Object)transition));
        } else {
            if (!(transition instanceof Animatable)) return false;
            transition = new AnimatableTransition((Animatable)transition);
        }
        transition.start();
        this.mTransition = transition;
        this.mTransitionFromIndex = n2;
        this.mTransitionToIndex = n;
        return true;
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedStateListState animatedStateListState = this.mState;
        if (Build.VERSION.SDK_INT >= 21) {
            animatedStateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        }
        animatedStateListState.setVariablePadding(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, animatedStateListState.mVariablePadding));
        animatedStateListState.setConstantSize(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_constantSize, animatedStateListState.mConstantSize));
        animatedStateListState.setEnterFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, animatedStateListState.mEnterFadeDuration));
        animatedStateListState.setExitFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, animatedStateListState.mExitFadeDuration));
        this.setDither(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_dither, animatedStateListState.mDither));
    }

    public void addState(int[] arrn, Drawable drawable2, int n) {
        if (drawable2 == null) throw new IllegalArgumentException("Drawable must not be null");
        this.mState.addStateSet(arrn, drawable2, n);
        this.onStateChange(this.getState());
    }

    public <T extends Drawable> void addTransition(int n, int n2, T t, boolean bl) {
        if (t == null) throw new IllegalArgumentException("Transition drawable must not be null");
        this.mState.addTransition(n, n2, t, bl);
    }

    @Override
    void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    AnimatedStateListState cloneConstantState() {
        return new AnimatedStateListState(this.mState, this, null);
    }

    @Override
    public void inflate(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableCompat);
        this.setVisible(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
        this.updateStateFromTypedArray(typedArray);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(context, resources, xmlPullParser, attributeSet, theme);
        this.init();
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        Transition transition = this.mTransition;
        if (transition == null) return;
        transition.stop();
        this.mTransition = null;
        this.selectDrawable(this.mTransitionToIndex);
        this.mTransitionToIndex = -1;
        this.mTransitionFromIndex = -1;
    }

    @Override
    public Drawable mutate() {
        if (this.mMutated) return this;
        if (super.mutate() != this) return this;
        this.mState.mutate();
        this.mMutated = true;
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        int n = this.mState.indexOfKeyframe(arrn);
        boolean bl = n != this.getCurrentIndex() && (this.selectTransition(n) || this.selectDrawable(n));
        Drawable drawable2 = this.getCurrent();
        boolean bl2 = bl;
        if (drawable2 == null) return bl2;
        return bl | drawable2.setState(arrn);
    }

    @Override
    void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (!(drawableContainerState instanceof AnimatedStateListState)) return;
        this.mState = (AnimatedStateListState)drawableContainerState;
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        if (this.mTransition == null) return bl3;
        if (!bl3) {
            if (!bl2) return bl3;
        }
        if (bl) {
            this.mTransition.start();
            return bl3;
        }
        this.jumpToCurrentState();
        return bl3;
    }

    private static class AnimatableTransition
    extends Transition {
        private final Animatable mA;

        AnimatableTransition(Animatable animatable) {
            this.mA = animatable;
        }

        @Override
        public void start() {
            this.mA.start();
        }

        @Override
        public void stop() {
            this.mA.stop();
        }
    }

    static class AnimatedStateListState
    extends StateListDrawable.StateListState {
        private static final long REVERSED_BIT = 0x100000000L;
        private static final long REVERSIBLE_FLAG_BIT = 0x200000000L;
        SparseArrayCompat<Integer> mStateIds;
        LongSparseArray<Long> mTransitions;

        AnimatedStateListState(AnimatedStateListState animatedStateListState, AnimatedStateListDrawableCompat animatedStateListDrawableCompat, Resources resources) {
            super(animatedStateListState, animatedStateListDrawableCompat, resources);
            if (animatedStateListState != null) {
                this.mTransitions = animatedStateListState.mTransitions;
                this.mStateIds = animatedStateListState.mStateIds;
                return;
            }
            this.mTransitions = new LongSparseArray();
            this.mStateIds = new SparseArrayCompat();
        }

        private static long generateTransitionKey(int n, int n2) {
            long l = n;
            return (long)n2 | l << 32;
        }

        int addStateSet(int[] arrn, Drawable drawable2, int n) {
            int n2 = super.addStateSet(arrn, drawable2);
            this.mStateIds.put(n2, n);
            return n2;
        }

        int addTransition(int n, int n2, Drawable object, boolean bl) {
            int n3 = super.addChild((Drawable)object);
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            long l2 = bl ? 0x200000000L : 0L;
            object = this.mTransitions;
            long l3 = n3;
            ((LongSparseArray)object).append(l, l3 | l2);
            if (!bl) return n3;
            l = AnimatedStateListState.generateTransitionKey(n2, n);
            this.mTransitions.append(l, 0x100000000L | l3 | l2);
            return n3;
        }

        int getKeyframeIdAt(int n) {
            int n2 = 0;
            if (n >= 0) return this.mStateIds.get(n, 0);
            return n2;
        }

        int indexOfKeyframe(int[] arrn) {
            int n = super.indexOfStateSet(arrn);
            if (n < 0) return super.indexOfStateSet(StateSet.WILD_CARD);
            return n;
        }

        int indexOfTransition(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            return (int)this.mTransitions.get(l, -1L).longValue();
        }

        boolean isTransitionReversed(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            if ((this.mTransitions.get(l, -1L) & 0x100000000L) == 0L) return false;
            return true;
        }

        @Override
        void mutate() {
            this.mTransitions = this.mTransitions.clone();
            this.mStateIds = this.mStateIds.clone();
        }

        @Override
        public Drawable newDrawable() {
            return new AnimatedStateListDrawableCompat(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimatedStateListDrawableCompat(this, resources);
        }

        boolean transitionHasReversibleFlag(int n, int n2) {
            long l = AnimatedStateListState.generateTransitionKey(n, n2);
            if ((this.mTransitions.get(l, -1L) & 0x200000000L) == 0L) return false;
            return true;
        }
    }

    private static class AnimatedVectorDrawableTransition
    extends Transition {
        private final AnimatedVectorDrawableCompat mAvd;

        AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat animatedVectorDrawableCompat) {
            this.mAvd = animatedVectorDrawableCompat;
        }

        @Override
        public void start() {
            this.mAvd.start();
        }

        @Override
        public void stop() {
            this.mAvd.stop();
        }
    }

    private static class AnimationDrawableTransition
    extends Transition {
        private final ObjectAnimator mAnim;
        private final boolean mHasReversibleFlag;

        AnimationDrawableTransition(AnimationDrawable animationDrawable, boolean bl, boolean bl2) {
            int n = animationDrawable.getNumberOfFrames();
            int n2 = bl ? n - 1 : 0;
            if (bl) {
                n = 0;
            }
            FrameInterpolator frameInterpolator = new FrameInterpolator(animationDrawable, bl);
            animationDrawable = ObjectAnimator.ofInt((Object)animationDrawable, (String)"currentIndex", (int[])new int[]{n2, --n});
            if (Build.VERSION.SDK_INT >= 18) {
                animationDrawable.setAutoCancel(true);
            }
            animationDrawable.setDuration((long)frameInterpolator.getTotalDuration());
            animationDrawable.setInterpolator((TimeInterpolator)frameInterpolator);
            this.mHasReversibleFlag = bl2;
            this.mAnim = animationDrawable;
        }

        @Override
        public boolean canReverse() {
            return this.mHasReversibleFlag;
        }

        @Override
        public void reverse() {
            this.mAnim.reverse();
        }

        @Override
        public void start() {
            this.mAnim.start();
        }

        @Override
        public void stop() {
            this.mAnim.cancel();
        }
    }

    private static class FrameInterpolator
    implements TimeInterpolator {
        private int[] mFrameTimes;
        private int mFrames;
        private int mTotalDuration;

        FrameInterpolator(AnimationDrawable animationDrawable, boolean bl) {
            this.updateFrames(animationDrawable, bl);
        }

        public float getInterpolation(float f) {
            int n;
            int n2 = (int)(f * (float)this.mTotalDuration + 0.5f);
            int n3 = this.mFrames;
            int[] arrn = this.mFrameTimes;
            for (n = 0; n < n3 && n2 >= arrn[n]; n2 -= arrn[n], ++n) {
            }
            if (n < n3) {
                f = (float)n2 / (float)this.mTotalDuration;
                return (float)n / (float)n3 + f;
            }
            f = 0.0f;
            return (float)n / (float)n3 + f;
        }

        int getTotalDuration() {
            return this.mTotalDuration;
        }

        int updateFrames(AnimationDrawable animationDrawable, boolean bl) {
            int n;
            this.mFrames = n = animationDrawable.getNumberOfFrames();
            int[] arrn = this.mFrameTimes;
            if (arrn == null || arrn.length < n) {
                this.mFrameTimes = new int[n];
            }
            arrn = this.mFrameTimes;
            int n2 = 0;
            int n3 = 0;
            do {
                if (n2 >= n) {
                    this.mTotalDuration = n3;
                    return n3;
                }
                int n4 = bl ? n - n2 - 1 : n2;
                arrn[n2] = n4 = animationDrawable.getDuration(n4);
                n3 += n4;
                ++n2;
            } while (true);
        }
    }

    private static abstract class Transition {
        private Transition() {
        }

        public boolean canReverse() {
            return false;
        }

        public void reverse() {
        }

        public abstract void start();

        public abstract void stop();
    }

}

