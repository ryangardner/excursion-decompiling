/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

public class WidgetContainer
extends ConstraintWidget {
    public ArrayList<ConstraintWidget> mChildren = new ArrayList();

    public WidgetContainer() {
    }

    public WidgetContainer(int n, int n2) {
        super(n, n2);
    }

    public WidgetContainer(int n, int n2, int n3, int n4) {
        super(n, n2, n3, n4);
    }

    public void add(ConstraintWidget constraintWidget) {
        this.mChildren.add(constraintWidget);
        if (constraintWidget.getParent() != null) {
            ((WidgetContainer)constraintWidget.getParent()).remove(constraintWidget);
        }
        constraintWidget.setParent(this);
    }

    public void add(ConstraintWidget ... arrconstraintWidget) {
        int n = arrconstraintWidget.length;
        int n2 = 0;
        while (n2 < n) {
            this.add(arrconstraintWidget[n2]);
            ++n2;
        }
    }

    public ArrayList<ConstraintWidget> getChildren() {
        return this.mChildren;
    }

    public ConstraintWidgetContainer getRootConstraintContainer() {
        ConstraintWidget constraintWidget = this.getParent();
        ConstraintWidgetContainer constraintWidgetContainer = this instanceof ConstraintWidgetContainer ? (ConstraintWidgetContainer)this : null;
        while (constraintWidget != null) {
            ConstraintWidget constraintWidget2 = constraintWidget.getParent();
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                constraintWidgetContainer = (ConstraintWidgetContainer)constraintWidget;
            }
            constraintWidget = constraintWidget2;
        }
        return constraintWidgetContainer;
    }

    public void layout() {
        Object object = this.mChildren;
        if (object == null) {
            return;
        }
        int n = ((ArrayList)object).size();
        int n2 = 0;
        while (n2 < n) {
            object = this.mChildren.get(n2);
            if (object instanceof WidgetContainer) {
                ((WidgetContainer)object).layout();
            }
            ++n2;
        }
    }

    public void remove(ConstraintWidget constraintWidget) {
        this.mChildren.remove(constraintWidget);
        constraintWidget.setParent(null);
    }

    public void removeAllChildren() {
        this.mChildren.clear();
    }

    @Override
    public void reset() {
        this.mChildren.clear();
        super.reset();
    }

    @Override
    public void resetSolverVariables(Cache cache) {
        super.resetSolverVariables(cache);
        int n = this.mChildren.size();
        int n2 = 0;
        while (n2 < n) {
            this.mChildren.get(n2).resetSolverVariables(cache);
            ++n2;
        }
    }

    @Override
    public void setOffset(int n, int n2) {
        super.setOffset(n, n2);
        n2 = this.mChildren.size();
        n = 0;
        while (n < n2) {
            this.mChildren.get(n).setOffset(this.getRootX(), this.getRootY());
            ++n;
        }
    }
}

