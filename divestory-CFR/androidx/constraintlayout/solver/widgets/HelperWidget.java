/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import java.util.Arrays;
import java.util.HashMap;

public class HelperWidget
extends ConstraintWidget
implements Helper {
    public ConstraintWidget[] mWidgets = new ConstraintWidget[4];
    public int mWidgetsCount = 0;

    @Override
    public void add(ConstraintWidget constraintWidget) {
        if (constraintWidget == this) return;
        if (constraintWidget == null) {
            return;
        }
        int n = this.mWidgetsCount;
        ConstraintWidget[] arrconstraintWidget = this.mWidgets;
        if (n + 1 > arrconstraintWidget.length) {
            this.mWidgets = Arrays.copyOf(arrconstraintWidget, arrconstraintWidget.length * 2);
        }
        arrconstraintWidget = this.mWidgets;
        n = this.mWidgetsCount;
        arrconstraintWidget[n] = constraintWidget;
        this.mWidgetsCount = n + 1;
    }

    @Override
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        constraintWidget = (HelperWidget)constraintWidget;
        int n = 0;
        this.mWidgetsCount = 0;
        int n2 = ((HelperWidget)constraintWidget).mWidgetsCount;
        while (n < n2) {
            this.add(hashMap.get(((HelperWidget)constraintWidget).mWidgets[n]));
            ++n;
        }
    }

    @Override
    public void removeAllIds() {
        this.mWidgetsCount = 0;
        Arrays.fill(this.mWidgets, null);
    }

    @Override
    public void updateConstraints(ConstraintWidgetContainer constraintWidgetContainer) {
    }
}

