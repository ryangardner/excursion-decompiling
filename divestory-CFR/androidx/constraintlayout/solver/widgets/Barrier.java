/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.HashMap;

public class Barrier
extends HelperWidget {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private boolean mAllowsGoneWidget = true;
    private int mBarrierType = 0;
    private int mMargin = 0;

    @Override
    public void addToSolver(LinearSystem linearSystem) {
        Object object;
        ConstraintAnchor constraintAnchor;
        boolean bl;
        int n;
        int n2;
        block9 : {
            this.mListAnchors[0] = this.mLeft;
            this.mListAnchors[2] = this.mTop;
            this.mListAnchors[1] = this.mRight;
            this.mListAnchors[3] = this.mBottom;
            for (n = 0; n < this.mListAnchors.length; ++n) {
                this.mListAnchors[n].mSolverVariable = linearSystem.createObjectVariable(this.mListAnchors[n]);
            }
            n = this.mBarrierType;
            if (n < 0) return;
            if (n >= 4) return;
            constraintAnchor = this.mListAnchors[this.mBarrierType];
            for (n = 0; n < this.mWidgetsCount; ++n) {
                object = this.mWidgets[n];
                if (!this.mAllowsGoneWidget && !((ConstraintWidget)object).allowedInBarrier() || ((n2 = this.mBarrierType) != 0 && n2 != 1 || ((ConstraintWidget)object).getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || object.mLeft.mTarget == null || object.mRight.mTarget == null) && ((n2 = this.mBarrierType) != 2 && n2 != 3 || ((ConstraintWidget)object).getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || object.mTop.mTarget == null || object.mBottom.mTarget == null)) continue;
                bl = true;
                break block9;
            }
            bl = false;
        }
        n = !this.mLeft.hasCenteredDependents() && !this.mRight.hasCenteredDependents() ? 0 : 1;
        n2 = !this.mTop.hasCenteredDependents() && !this.mBottom.hasCenteredDependents() ? 0 : 1;
        n2 = !bl && (this.mBarrierType == 0 && n != 0 || this.mBarrierType == 2 && n2 != 0 || this.mBarrierType == 1 && n != 0 || this.mBarrierType == 3 && n2 != 0) ? 1 : 0;
        n = 5;
        if (n2 == 0) {
            n = 4;
        }
        for (n2 = 0; n2 < this.mWidgetsCount; ++n2) {
            ConstraintWidget constraintWidget = this.mWidgets[n2];
            if (!this.mAllowsGoneWidget && !constraintWidget.allowedInBarrier()) continue;
            constraintWidget.mListAnchors[this.mBarrierType].mSolverVariable = object = linearSystem.createObjectVariable(constraintWidget.mListAnchors[this.mBarrierType]);
            int n3 = constraintWidget.mListAnchors[this.mBarrierType].mTarget != null && constraintWidget.mListAnchors[this.mBarrierType].mTarget.mOwner == this ? constraintWidget.mListAnchors[this.mBarrierType].mMargin + 0 : 0;
            int n4 = this.mBarrierType;
            if (n4 != 0 && n4 != 2) {
                linearSystem.addGreaterBarrier(constraintAnchor.mSolverVariable, (SolverVariable)object, this.mMargin + n3, bl);
            } else {
                linearSystem.addLowerBarrier(constraintAnchor.mSolverVariable, (SolverVariable)object, this.mMargin - n3, bl);
            }
            linearSystem.addEquality(constraintAnchor.mSolverVariable, (SolverVariable)object, this.mMargin + n3, n);
        }
        n = this.mBarrierType;
        if (n == 0) {
            linearSystem.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 4);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 0);
            return;
        }
        if (n == 1) {
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 4);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 0);
            return;
        }
        if (n == 2) {
            linearSystem.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 4);
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 0);
            return;
        }
        if (n != 3) return;
        linearSystem.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 8);
        linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 4);
        linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 0);
    }

    @Override
    public boolean allowedInBarrier() {
        return true;
    }

    public boolean allowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    @Override
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        constraintWidget = (Barrier)constraintWidget;
        this.mBarrierType = ((Barrier)constraintWidget).mBarrierType;
        this.mAllowsGoneWidget = ((Barrier)constraintWidget).mAllowsGoneWidget;
        this.mMargin = ((Barrier)constraintWidget).mMargin;
    }

    public int getBarrierType() {
        return this.mBarrierType;
    }

    public int getMargin() {
        return this.mMargin;
    }

    protected void markWidgets() {
        int n = 0;
        while (n < this.mWidgetsCount) {
            ConstraintWidget constraintWidget = this.mWidgets[n];
            int n2 = this.mBarrierType;
            if (n2 != 0 && n2 != 1) {
                if (n2 == 2 || n2 == 3) {
                    constraintWidget.setInBarrier(1, true);
                }
            } else {
                constraintWidget.setInBarrier(0, true);
            }
            ++n;
        }
    }

    public void setAllowsGoneWidget(boolean bl) {
        this.mAllowsGoneWidget = bl;
    }

    public void setBarrierType(int n) {
        this.mBarrierType = n;
    }

    public void setMargin(int n) {
        this.mMargin = n;
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("[Barrier] ");
        ((StringBuilder)charSequence).append(this.getDebugName());
        ((StringBuilder)charSequence).append(" {");
        charSequence = ((StringBuilder)charSequence).toString();
        int n = 0;
        do {
            CharSequence charSequence2;
            if (n >= this.mWidgetsCount) {
                charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append("}");
                return charSequence2.toString();
            }
            ConstraintWidget constraintWidget = this.mWidgets[n];
            charSequence2 = charSequence;
            if (n > 0) {
                charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append(", ");
                charSequence2 = charSequence2.toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(constraintWidget.getDebugName());
            charSequence = ((StringBuilder)charSequence).toString();
            ++n;
        } while (true);
    }
}

