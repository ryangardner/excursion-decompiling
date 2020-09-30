/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.state.helpers.ChainReference;
import java.util.ArrayList;
import java.util.Iterator;

public class HorizontalChainReference
extends ChainReference {
    private Object mEndToEnd;
    private Object mEndToStart;
    private Object mStartToEnd;
    private Object mStartToStart;

    public HorizontalChainReference(State state) {
        super(state, State.Helper.HORIZONTAL_CHAIN);
    }

    @Override
    public void apply() {
        Object object;
        Object e2;
        int n;
        for (Object e2 : this.mReferences) {
            this.mState.constraints(e2).clearHorizontal();
        }
        Iterator iterator2 = this.mReferences.iterator();
        e2 = null;
        Object object2 = null;
        while (iterator2.hasNext()) {
            object = iterator2.next();
            object = this.mState.constraints(object);
            Object object3 = object2;
            if (object2 == null) {
                object2 = this.mStartToStart;
                if (object2 != null) {
                    ((ConstraintReference)object).startToStart(object2);
                } else {
                    object2 = this.mStartToEnd;
                    if (object2 != null) {
                        ((ConstraintReference)object).startToEnd(object2);
                    } else {
                        ((ConstraintReference)object).startToStart(State.PARENT);
                    }
                }
                object3 = object;
            }
            if (e2 != null) {
                ((ConstraintReference)e2).endToStart(((ConstraintReference)object).getKey());
                ((ConstraintReference)object).startToEnd(((ConstraintReference)e2).getKey());
            }
            e2 = object;
            object2 = object3;
        }
        if (e2 != null) {
            object = this.mEndToStart;
            if (object != null) {
                ((ConstraintReference)e2).endToStart(object);
            } else {
                object = this.mEndToEnd;
                if (object != null) {
                    ((ConstraintReference)e2).endToEnd(object);
                } else {
                    ((ConstraintReference)e2).endToEnd(State.PARENT);
                }
            }
        }
        if (object2 != null && this.mBias != 0.5f) {
            ((ConstraintReference)object2).horizontalBias(this.mBias);
        }
        if ((n = 1.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()]) == 1) {
            ((ConstraintReference)object2).setHorizontalChainStyle(0);
            return;
        }
        if (n == 2) {
            ((ConstraintReference)object2).setHorizontalChainStyle(1);
            return;
        }
        if (n != 3) {
            return;
        }
        ((ConstraintReference)object2).setHorizontalChainStyle(2);
    }

    public void endToEnd(Object object) {
        this.mEndToEnd = object;
    }

    public void endToStart(Object object) {
        this.mEndToStart = object;
    }

    public void startToEnd(Object object) {
        this.mStartToEnd = object;
    }

    public void startToStart(Object object) {
        this.mStartToStart = object;
    }

}

