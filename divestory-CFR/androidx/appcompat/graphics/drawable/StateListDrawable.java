/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.StateSet
 */
package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.appcompat.graphics.drawable.DrawableContainer;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class StateListDrawable
extends DrawableContainer {
    private static final boolean DEBUG = false;
    private static final String TAG = "StateListDrawable";
    private boolean mMutated;
    private StateListState mStateListState;

    StateListDrawable() {
        this(null, null);
    }

    StateListDrawable(StateListState stateListState) {
        if (stateListState == null) return;
        this.setConstantState(stateListState);
    }

    StateListDrawable(StateListState stateListState, Resources resources) {
        this.setConstantState(new StateListState(stateListState, this, resources));
        this.onStateChange(this.getState());
    }

    private void inflateChildElements(Context object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        StateListState stateListState = this.mStateListState;
        int n2 = xmlPullParser.getDepth() + 1;
        while ((n = xmlPullParser.next()) != 1) {
            int n3 = xmlPullParser.getDepth();
            if (n3 < n2) {
                if (n == 3) return;
            }
            if (n != 2 || n3 > n2 || !xmlPullParser.getName().equals("item")) continue;
            TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawableItem);
            Drawable drawable2 = null;
            n3 = typedArray.getResourceId(R.styleable.StateListDrawableItem_android_drawable, -1);
            if (n3 > 0) {
                drawable2 = ResourceManagerInternal.get().getDrawable((Context)object, n3);
            }
            typedArray.recycle();
            int[] arrn = this.extractStateSet(attributeSet);
            typedArray = drawable2;
            if (drawable2 == null) {
                while ((n3 = xmlPullParser.next()) == 4) {
                }
                if (n3 != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    throw new XmlPullParserException(((StringBuilder)object).toString());
                }
                typedArray = Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet, (Resources.Theme)theme) : Drawable.createFromXmlInner((Resources)resources, (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet);
            }
            stateListState.addStateSet(arrn, (Drawable)typedArray);
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        StateListState stateListState = this.mStateListState;
        if (Build.VERSION.SDK_INT >= 21) {
            stateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        }
        stateListState.mVariablePadding = typedArray.getBoolean(R.styleable.StateListDrawable_android_variablePadding, stateListState.mVariablePadding);
        stateListState.mConstantSize = typedArray.getBoolean(R.styleable.StateListDrawable_android_constantSize, stateListState.mConstantSize);
        stateListState.mEnterFadeDuration = typedArray.getInt(R.styleable.StateListDrawable_android_enterFadeDuration, stateListState.mEnterFadeDuration);
        stateListState.mExitFadeDuration = typedArray.getInt(R.styleable.StateListDrawable_android_exitFadeDuration, stateListState.mExitFadeDuration);
        stateListState.mDither = typedArray.getBoolean(R.styleable.StateListDrawable_android_dither, stateListState.mDither);
    }

    public void addState(int[] arrn, Drawable drawable2) {
        if (drawable2 == null) return;
        this.mStateListState.addStateSet(arrn, drawable2);
        this.onStateChange(this.getState());
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.onStateChange(this.getState());
    }

    @Override
    void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    StateListState cloneConstantState() {
        return new StateListState(this.mStateListState, this, null);
    }

    int[] extractStateSet(AttributeSet attributeSet) {
        int n = attributeSet.getAttributeCount();
        int[] arrn = new int[n];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = attributeSet.getAttributeNameResource(n2);
            int n5 = n3;
            if (n4 != 0) {
                n5 = n3;
                if (n4 != 16842960) {
                    n5 = n3;
                    if (n4 != 16843161) {
                        n5 = attributeSet.getAttributeBooleanValue(n2, false) ? n4 : -n4;
                        arrn[n3] = n5;
                        n5 = n3 + 1;
                    }
                }
            }
            ++n2;
            n3 = n5;
        }
        return StateSet.trimStateSet((int[])arrn, (int)n3);
    }

    int getStateCount() {
        return this.mStateListState.getChildCount();
    }

    Drawable getStateDrawable(int n) {
        return this.mStateListState.getChild(n);
    }

    int getStateDrawableIndex(int[] arrn) {
        return this.mStateListState.indexOfStateSet(arrn);
    }

    StateListState getStateListState() {
        return this.mStateListState;
    }

    int[] getStateSet(int n) {
        return this.mStateListState.mStateSets[n];
    }

    public void inflate(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawable);
        this.setVisible(typedArray.getBoolean(R.styleable.StateListDrawable_android_visible, true), true);
        this.updateStateFromTypedArray(typedArray);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(context, resources, xmlPullParser, attributeSet, theme);
        this.onStateChange(this.getState());
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public Drawable mutate() {
        if (this.mMutated) return this;
        if (super.mutate() != this) return this;
        this.mStateListState.mutate();
        this.mMutated = true;
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        int n;
        boolean bl = super.onStateChange(arrn);
        int n2 = n = this.mStateListState.indexOfStateSet(arrn);
        if (n < 0) {
            n2 = this.mStateListState.indexOfStateSet(StateSet.WILD_CARD);
        }
        if (this.selectDrawable(n2)) return true;
        if (bl) return true;
        return false;
    }

    @Override
    void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (!(drawableContainerState instanceof StateListState)) return;
        this.mStateListState = (StateListState)drawableContainerState;
    }

    static class StateListState
    extends DrawableContainer.DrawableContainerState {
        int[][] mStateSets;

        StateListState(StateListState stateListState, StateListDrawable stateListDrawable, Resources resources) {
            super(stateListState, stateListDrawable, resources);
            if (stateListState != null) {
                this.mStateSets = stateListState.mStateSets;
                return;
            }
            this.mStateSets = new int[this.getCapacity()][];
        }

        int addStateSet(int[] arrn, Drawable drawable2) {
            int n = this.addChild(drawable2);
            this.mStateSets[n] = arrn;
            return n;
        }

        @Override
        public void growArray(int n, int n2) {
            super.growArray(n, n2);
            int[][] arrarrn = new int[n2][];
            System.arraycopy(this.mStateSets, 0, arrarrn, 0, n);
            this.mStateSets = arrarrn;
        }

        int indexOfStateSet(int[] arrn) {
            int[][] arrn2 = this.mStateSets;
            int n = this.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                if (StateSet.stateSetMatches((int[])arrn2[n2], (int[])arrn)) {
                    return n2;
                }
                ++n2;
            }
            return -1;
        }

        @Override
        void mutate() {
            Object object = this.mStateSets;
            int[][] arrarrn = new int[((int[][])object).length][];
            int n = ((int[][])object).length - 1;
            do {
                if (n < 0) {
                    this.mStateSets = arrarrn;
                    return;
                }
                object = this.mStateSets;
                object = object[n] != null ? (int[])object[n].clone() : null;
                arrarrn[n] = object;
                --n;
            } while (true);
        }

        public Drawable newDrawable() {
            return new StateListDrawable(this, null);
        }

        public Drawable newDrawable(Resources resources) {
            return new StateListDrawable(this, resources);
        }
    }

}

