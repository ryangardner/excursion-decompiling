/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.state.helpers.ChainReference;
import java.util.ArrayList;
import java.util.Iterator;

public class VerticalChainReference
extends ChainReference {
    private Object mBottomToBottom;
    private Object mBottomToTop;
    private Object mTopToBottom;
    private Object mTopToTop;

    public VerticalChainReference(State state) {
        super(state, State.Helper.VERTICAL_CHAIN);
    }

    @Override
    public void apply() {
        Object object;
        Object e2;
        int n;
        for (Object e2 : this.mReferences) {
            this.mState.constraints(e2).clearVertical();
        }
        Iterator iterator2 = this.mReferences.iterator();
        e2 = null;
        Object object2 = null;
        while (iterator2.hasNext()) {
            object = iterator2.next();
            object = this.mState.constraints(object);
            Object object3 = object2;
            if (object2 == null) {
                object2 = this.mTopToTop;
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
                object3 = object;
            }
            if (e2 != null) {
                ((ConstraintReference)e2).bottomToTop(((ConstraintReference)object).getKey());
                ((ConstraintReference)object).topToBottom(((ConstraintReference)e2).getKey());
            }
            e2 = object;
            object2 = object3;
        }
        if (e2 != null) {
            object = this.mBottomToTop;
            if (object != null) {
                ((ConstraintReference)e2).bottomToTop(object);
            } else {
                object = this.mBottomToBottom;
                if (object != null) {
                    ((ConstraintReference)e2).bottomToBottom(object);
                } else {
                    ((ConstraintReference)e2).bottomToBottom(State.PARENT);
                }
            }
        }
        if (object2 != null && this.mBias != 0.5f) {
            ((ConstraintReference)object2).verticalBias(this.mBias);
        }
        if ((n = 1.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()]) == 1) {
            ((ConstraintReference)object2).setVerticalChainStyle(0);
            return;
        }
        if (n == 2) {
            ((ConstraintReference)object2).setVerticalChainStyle(1);
            return;
        }
        if (n != 3) {
            return;
        }
        ((ConstraintReference)object2).setVerticalChainStyle(2);
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

