package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.Reference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

public class GuidelineReference implements Reference {
   private Object key;
   private int mEnd = -1;
   private Guideline mGuidelineWidget;
   private int mOrientation;
   private float mPercent = 0.0F;
   private int mStart = -1;
   final State mState;

   public GuidelineReference(State var1) {
      this.mState = var1;
   }

   public void apply() {
      this.mGuidelineWidget.setOrientation(this.mOrientation);
      int var1 = this.mStart;
      if (var1 != -1) {
         this.mGuidelineWidget.setGuideBegin(var1);
      } else {
         var1 = this.mEnd;
         if (var1 != -1) {
            this.mGuidelineWidget.setGuideEnd(var1);
         } else {
            this.mGuidelineWidget.setGuidePercent(this.mPercent);
         }
      }

   }

   public void end(Object var1) {
      this.mStart = -1;
      this.mEnd = this.mState.convertDimension(var1);
      this.mPercent = 0.0F;
   }

   public ConstraintWidget getConstraintWidget() {
      if (this.mGuidelineWidget == null) {
         this.mGuidelineWidget = new Guideline();
      }

      return this.mGuidelineWidget;
   }

   public Object getKey() {
      return this.key;
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public void percent(float var1) {
      this.mStart = -1;
      this.mEnd = -1;
      this.mPercent = var1;
   }

   public void setConstraintWidget(ConstraintWidget var1) {
      if (var1 instanceof Guideline) {
         this.mGuidelineWidget = (Guideline)var1;
      } else {
         this.mGuidelineWidget = null;
      }

   }

   public void setKey(Object var1) {
      this.key = var1;
   }

   public void setOrientation(int var1) {
      this.mOrientation = var1;
   }

   public void start(Object var1) {
      this.mStart = this.mState.convertDimension(var1);
      this.mEnd = -1;
      this.mPercent = 0.0F;
   }
}
