/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public interface Reference {
    public void apply();

    public ConstraintWidget getConstraintWidget();

    public Object getKey();

    public void setConstraintWidget(ConstraintWidget var1);

    public void setKey(Object var1);
}

