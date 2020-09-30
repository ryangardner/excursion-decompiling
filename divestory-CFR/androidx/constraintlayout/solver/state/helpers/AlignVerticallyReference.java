/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import java.util.ArrayList;
import java.util.Iterator;

public class AlignVerticallyReference
extends HelperReference {
    private float mBias = 0.5f;
    private Object mBottomToBottom;
    private Object mBottomToTop;
    private Object mTopToBottom;
    private Object mTopToTop;

    public AlignVerticallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
    }

    @Override
    public void apply() {
        Iterator iterator2 = this.mReferences.iterator();
        while (iterator2.hasNext()) {
            float f;
            Object object = iterator2.next();
            object = this.mState.constraints(object);
            ((ConstraintReference)object).clearVertical();
            Object object2 = this.mTopToTop;
            if (object2 != null) {
                ((ConstraintReference)object).topToTop(object2);
            } else {
                object2 = this.mTopToBottom;
                if (object2 != null) {
                    ((ConstraintReference)object).topToBottom(object2);
                } else {
                    ((ConstraintReference)object).topToTop(State.PARENT);
                }
            }
            object2 = this.mBottomToTop;
            if (object2 != null) {
                ((ConstraintReference)object).bottomToTop(object2);
            } else {
                object2 = this.mBottomToBottom;
                if (object2 != null) {
                    ((ConstraintReference)object).bottomToBottom(object2);
                } else {
                    ((ConstraintReference)object).bottomToBottom(State.PARENT);
                }
            }
            if ((f = this.mBias) == 0.5f) continue;
            ((ConstraintReference)object).verticalBias(f);
        }
    }

    public void bias(float f) {
        this.mBias = f;
    }

    public void bottomToBottom(Object object) {
        this.mBottomToBottom = object;
    }

    public void bottomToTop(Object object) {
        this.mBottomToTop = object;
    }

    public void topToBottom(Object object) {
        this.mTopToBottom = object;
    }

    public void topToTop(Object object) {
        this.mTopToTop = object;
    }
}

