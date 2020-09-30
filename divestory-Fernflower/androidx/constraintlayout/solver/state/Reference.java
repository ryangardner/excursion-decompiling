package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public interface Reference {
   void apply();

   ConstraintWidget getConstraintWidget();

   Object getKey();

   void setConstraintWidget(ConstraintWidget var1);

   void setKey(Object var1);
}
