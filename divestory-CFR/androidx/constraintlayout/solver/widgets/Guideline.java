/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.HashMap;

public class Guideline
extends ConstraintWidget {
    public static final int HORIZONTAL = 0;
    public static final int RELATIVE_BEGIN = 1;
    public static final int RELATIVE_END = 2;
    public static final int RELATIVE_PERCENT = 0;
    public static final int RELATIVE_UNKNWON = -1;
    public static final int VERTICAL = 1;
    private ConstraintAnchor mAnchor = this.mTop;
    private int mMinimumPosition;
    private int mOrientation;
    protected int mRelativeBegin = -1;
    protected int mRelativeEnd = -1;
    protected float mRelativePercent = -1.0f;

    public Guideline() {
        int n = 0;
        this.mOrientation = 0;
        this.mMinimumPosition = 0;
        this.mAnchors.clear();
        this.mAnchors.add(this.mAnchor);
        int n2 = this.mListAnchors.length;
        while (n < n2) {
            this.mListAnchors[n] = this.mAnchor;
            ++n;
        }
    }

    @Override
    public void addToSolver(LinearSystem linearSystem) {
        Object object = (ConstraintWidgetContainer)this.getParent();
        if (object == null) {
            return;
        }
        ConstraintAnchor constraintAnchor = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.LEFT);
        Object object2 = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintWidget constraintWidget = this.mParent;
        boolean bl = true;
        boolean bl2 = constraintWidget != null && this.mParent.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (this.mOrientation == 0) {
            constraintAnchor = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.TOP);
            object2 = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BOTTOM);
            bl2 = this.mParent != null && this.mParent.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? bl : false;
        }
        if (this.mRelativeBegin != -1) {
            object = linearSystem.createObjectVariable(this.mAnchor);
            linearSystem.addEquality((SolverVariable)object, linearSystem.createObjectVariable(constraintAnchor), this.mRelativeBegin, 8);
            if (!bl2) return;
            linearSystem.addGreaterThan(linearSystem.createObjectVariable(object2), (SolverVariable)object, 0, 5);
            return;
        }
        if (this.mRelativeEnd != -1) {
            object = linearSystem.createObjectVariable(this.mAnchor);
            object2 = linearSystem.createObjectVariable(object2);
            linearSystem.addEquality((SolverVariable)object, (SolverVariable)object2, -this.mRelativeEnd, 8);
            if (!bl2) return;
            linearSystem.addGreaterThan((SolverVariable)object, linearSystem.createObjectVariable(constraintAnchor), 0, 5);
            linearSystem.addGreaterThan((SolverVariable)object2, (SolverVariable)object, 0, 5);
            return;
        }
        if (this.mRelativePercent == -1.0f) return;
        linearSystem.addConstraint(LinearSystem.createRowDimensionPercent(linearSystem, linearSystem.createObjectVariable(this.mAnchor), linearSystem.createObjectVariable(object2), this.mRelativePercent));
    }

    @Override
    public boolean allowedInBarrier() {
        return true;
    }

    @Override
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        constraintWidget = (Guideline)constraintWidget;
        this.mRelativePercent = ((Guideline)constraintWidget).mRelativePercent;
        this.mRelativeBegin = ((Guideline)constraintWidget).mRelativeBegin;
        this.mRelativeEnd = ((Guideline)constraintWidget).mRelativeEnd;
        this.setOrientation(((Guideline)constraintWidget).mOrientation);
    }

    public void cyclePosition() {
        if (this.mRelativeBegin != -1) {
            this.inferRelativePercentPosition();
            return;
        }
        if (this.mRelativePercent != -1.0f) {
            this.inferRelativeEndPosition();
            return;
        }
        if (this.mRelativeEnd == -1) return;
        this.inferRelativeBeginPosition();
    }

    public ConstraintAnchor getAnchor() {
        return this.mAnchor;
    }

    @Override
    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            default: {
                throw new AssertionError((Object)type.name());
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                return null;
            }
            case 3: 
            case 4: {
                if (this.mOrientation != 0) throw new AssertionError((Object)type.name());
                return this.mAnchor;
            }
            case 1: 
            case 2: 
        }
        if (this.mOrientation != 1) throw new AssertionError((Object)type.name());
        return this.mAnchor;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getRelativeBegin() {
        return this.mRelativeBegin;
    }

    public int getRelativeBehaviour() {
        if (this.mRelativePercent != -1.0f) {
            return 0;
        }
        if (this.mRelativeBegin != -1) {
            return 1;
        }
        if (this.mRelativeEnd == -1) return -1;
        return 2;
    }

    public int getRelativeEnd() {
        return this.mRelativeEnd;
    }

    public float getRelativePercent() {
        return this.mRelativePercent;
    }

    @Override
    public String getType() {
        return "Guideline";
    }

    void inferRelativeBeginPosition() {
        int n = this.getX();
        if (this.mOrientation == 0) {
            n = this.getY();
        }
        this.setGuideBegin(n);
    }

    void inferRelativeEndPosition() {
        int n = this.getParent().getWidth() - this.getX();
        if (this.mOrientation == 0) {
            n = this.getParent().getHeight() - this.getY();
        }
        this.setGuideEnd(n);
    }

    void inferRelativePercentPosition() {
        float f = (float)this.getX() / (float)this.getParent().getWidth();
        if (this.mOrientation == 0) {
            f = (float)this.getY() / (float)this.getParent().getHeight();
        }
        this.setGuidePercent(f);
    }

    public boolean isPercent() {
        if (this.mRelativePercent == -1.0f) return false;
        if (this.mRelativeBegin != -1) return false;
        if (this.mRelativeEnd != -1) return false;
        return true;
    }

    public void setGuideBegin(int n) {
        if (n <= -1) return;
        this.mRelativePercent = -1.0f;
        this.mRelativeBegin = n;
        this.mRelativeEnd = -1;
    }

    public void setGuideEnd(int n) {
        if (n <= -1) return;
        this.mRelativePercent = -1.0f;
        this.mRelativeBegin = -1;
        this.mRelativeEnd = n;
    }

    public void setGuidePercent(float f) {
        if (!(f > -1.0f)) return;
        this.mRelativePercent = f;
        this.mRelativeBegin = -1;
        this.mRelativeEnd = -1;
    }

    public void setGuidePercent(int n) {
        this.setGuidePercent((float)n / 100.0f);
    }

    public void setMinimumPosition(int n) {
        this.mMinimumPosition = n;
    }

    public void setOrientation(int n) {
        if (this.mOrientation == n) {
            return;
        }
        this.mOrientation = n;
        this.mAnchors.clear();
        this.mAnchor = this.mOrientation == 1 ? this.mLeft : this.mTop;
        this.mAnchors.add(this.mAnchor);
        int n2 = this.mListAnchors.length;
        n = 0;
        while (n < n2) {
            this.mListAnchors[n] = this.mAnchor;
            ++n;
        }
    }

    @Override
    public void updateFromSolver(LinearSystem linearSystem) {
        if (this.getParent() == null) {
            return;
        }
        int n = linearSystem.getObjectVariableValue(this.mAnchor);
        if (this.mOrientation == 1) {
            this.setX(n);
            this.setY(0);
            this.setHeight(this.getParent().getHeight());
            this.setWidth(0);
            return;
        }
        this.setX(0);
        this.setY(n);
        this.setWidth(this.getParent().getWidth());
        this.setHeight(0);
    }

}

