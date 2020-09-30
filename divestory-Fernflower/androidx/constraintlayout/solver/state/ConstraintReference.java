package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

public class ConstraintReference implements Reference {
   private Object key;
   Object mBaselineToBaseline = null;
   Object mBottomToBottom = null;
   Object mBottomToTop = null;
   private ConstraintWidget mConstraintWidget;
   Object mEndToEnd = null;
   Object mEndToStart = null;
   float mHorizontalBias = 0.5F;
   int mHorizontalChainStyle = 0;
   Dimension mHorizontalDimension;
   State.Constraint mLast = null;
   Object mLeftToLeft = null;
   Object mLeftToRight = null;
   int mMarginBottom = 0;
   int mMarginBottomGone = 0;
   int mMarginEnd = 0;
   int mMarginEndGone = 0;
   int mMarginLeft = 0;
   int mMarginLeftGone = 0;
   int mMarginRight = 0;
   int mMarginRightGone = 0;
   int mMarginStart = 0;
   int mMarginStartGone = 0;
   int mMarginTop = 0;
   int mMarginTopGone = 0;
   Object mRightToLeft = null;
   Object mRightToRight = null;
   Object mStartToEnd = null;
   Object mStartToStart = null;
   final State mState;
   Object mTopToBottom = null;
   Object mTopToTop = null;
   float mVerticalBias = 0.5F;
   int mVerticalChainStyle = 0;
   Dimension mVerticalDimension;
   private Object mView;

   public ConstraintReference(State var1) {
      this.mHorizontalDimension = Dimension.Fixed(Dimension.WRAP_DIMENSION);
      this.mVerticalDimension = Dimension.Fixed(Dimension.WRAP_DIMENSION);
      this.mState = var1;
   }

   private void applyConnection(ConstraintWidget var1, Object var2, State.Constraint var3) {
      ConstraintWidget var5 = this.getTarget(var2);
      if (var5 != null) {
         int var4 = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[var3.ordinal()];
         switch(null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[var3.ordinal()]) {
         case 1:
            var1.getAnchor(ConstraintAnchor.Type.LEFT).connect(var5.getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginLeft, this.mMarginLeftGone, false);
            break;
         case 2:
            var1.getAnchor(ConstraintAnchor.Type.LEFT).connect(var5.getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginLeft, this.mMarginLeftGone, false);
            break;
         case 3:
            var1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var5.getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginRight, this.mMarginRightGone, false);
            break;
         case 4:
            var1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var5.getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginRight, this.mMarginRightGone, false);
            break;
         case 5:
            var1.getAnchor(ConstraintAnchor.Type.LEFT).connect(var5.getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginStart, this.mMarginStartGone, false);
            break;
         case 6:
            var1.getAnchor(ConstraintAnchor.Type.LEFT).connect(var5.getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginStart, this.mMarginStartGone, false);
            break;
         case 7:
            var1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var5.getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginEnd, this.mMarginEndGone, false);
            break;
         case 8:
            var1.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var5.getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginEnd, this.mMarginEndGone, false);
            break;
         case 9:
            var1.getAnchor(ConstraintAnchor.Type.TOP).connect(var5.getAnchor(ConstraintAnchor.Type.TOP), this.mMarginTop, this.mMarginTopGone, false);
            break;
         case 10:
            var1.getAnchor(ConstraintAnchor.Type.TOP).connect(var5.getAnchor(ConstraintAnchor.Type.BOTTOM), this.mMarginTop, this.mMarginTopGone, false);
            break;
         case 11:
            var1.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var5.getAnchor(ConstraintAnchor.Type.TOP), this.mMarginBottom, this.mMarginBottomGone, false);
            break;
         case 12:
            var1.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var5.getAnchor(ConstraintAnchor.Type.BOTTOM), this.mMarginBottom, this.mMarginBottomGone, false);
            break;
         case 13:
            var1.immediateConnect(ConstraintAnchor.Type.BASELINE, var5, ConstraintAnchor.Type.BASELINE, 0, 0);
         }

      }
   }

   private void dereference() {
      this.mLeftToLeft = this.get(this.mLeftToLeft);
      this.mLeftToRight = this.get(this.mLeftToRight);
      this.mRightToLeft = this.get(this.mRightToLeft);
      this.mRightToRight = this.get(this.mRightToRight);
      this.mStartToStart = this.get(this.mStartToStart);
      this.mStartToEnd = this.get(this.mStartToEnd);
      this.mEndToStart = this.get(this.mEndToStart);
      this.mEndToEnd = this.get(this.mEndToEnd);
      this.mTopToTop = this.get(this.mTopToTop);
      this.mTopToBottom = this.get(this.mTopToBottom);
      this.mBottomToTop = this.get(this.mBottomToTop);
      this.mBottomToBottom = this.get(this.mBottomToBottom);
      this.mBaselineToBaseline = this.get(this.mBaselineToBaseline);
   }

   private Object get(Object var1) {
      if (var1 == null) {
         return null;
      } else {
         Object var2 = var1;
         if (!(var1 instanceof ConstraintReference)) {
            var2 = this.mState.reference(var1);
         }

         return var2;
      }
   }

   private ConstraintWidget getTarget(Object var1) {
      return var1 instanceof Reference ? ((Reference)var1).getConstraintWidget() : null;
   }

   public void apply() {
      ConstraintWidget var1 = this.mConstraintWidget;
      if (var1 != null) {
         this.mHorizontalDimension.apply(this.mState, var1, 0);
         this.mVerticalDimension.apply(this.mState, this.mConstraintWidget, 1);
         this.dereference();
         this.applyConnection(this.mConstraintWidget, this.mLeftToLeft, State.Constraint.LEFT_TO_LEFT);
         this.applyConnection(this.mConstraintWidget, this.mLeftToRight, State.Constraint.LEFT_TO_RIGHT);
         this.applyConnection(this.mConstraintWidget, this.mRightToLeft, State.Constraint.RIGHT_TO_LEFT);
         this.applyConnection(this.mConstraintWidget, this.mRightToRight, State.Constraint.RIGHT_TO_RIGHT);
         this.applyConnection(this.mConstraintWidget, this.mStartToStart, State.Constraint.START_TO_START);
         this.applyConnection(this.mConstraintWidget, this.mStartToEnd, State.Constraint.START_TO_END);
         this.applyConnection(this.mConstraintWidget, this.mEndToStart, State.Constraint.END_TO_START);
         this.applyConnection(this.mConstraintWidget, this.mEndToEnd, State.Constraint.END_TO_END);
         this.applyConnection(this.mConstraintWidget, this.mTopToTop, State.Constraint.TOP_TO_TOP);
         this.applyConnection(this.mConstraintWidget, this.mTopToBottom, State.Constraint.TOP_TO_BOTTOM);
         this.applyConnection(this.mConstraintWidget, this.mBottomToTop, State.Constraint.BOTTOM_TO_TOP);
         this.applyConnection(this.mConstraintWidget, this.mBottomToBottom, State.Constraint.BOTTOM_TO_BOTTOM);
         this.applyConnection(this.mConstraintWidget, this.mBaselineToBaseline, State.Constraint.BASELINE_TO_BASELINE);
         int var2 = this.mHorizontalChainStyle;
         if (var2 != 0) {
            this.mConstraintWidget.setHorizontalChainStyle(var2);
         }

         var2 = this.mVerticalChainStyle;
         if (var2 != 0) {
            this.mConstraintWidget.setVerticalChainStyle(var2);
         }

         this.mConstraintWidget.setHorizontalBiasPercent(this.mHorizontalBias);
         this.mConstraintWidget.setVerticalBiasPercent(this.mVerticalBias);
      }
   }

   public ConstraintReference baseline() {
      this.mLast = State.Constraint.BASELINE_TO_BASELINE;
      return this;
   }

   public ConstraintReference baselineToBaseline(Object var1) {
      this.mLast = State.Constraint.BASELINE_TO_BASELINE;
      this.mBaselineToBaseline = var1;
      return this;
   }

   public ConstraintReference bias(float var1) {
      if (this.mLast == null) {
         return this;
      } else {
         switch(null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[this.mLast.ordinal()]) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 14:
            this.mHorizontalBias = var1;
            break;
         case 9:
         case 10:
         case 11:
         case 12:
         case 15:
            this.mVerticalBias = var1;
         case 13:
         }

         return this;
      }
   }

   public ConstraintReference bottom() {
      if (this.mBottomToTop != null) {
         this.mLast = State.Constraint.BOTTOM_TO_TOP;
      } else {
         this.mLast = State.Constraint.BOTTOM_TO_BOTTOM;
      }

      return this;
   }

   public ConstraintReference bottomToBottom(Object var1) {
      this.mLast = State.Constraint.BOTTOM_TO_BOTTOM;
      this.mBottomToBottom = var1;
      return this;
   }

   public ConstraintReference bottomToTop(Object var1) {
      this.mLast = State.Constraint.BOTTOM_TO_TOP;
      this.mBottomToTop = var1;
      return this;
   }

   public ConstraintReference centerHorizontally(Object var1) {
      var1 = this.get(var1);
      this.mStartToStart = var1;
      this.mEndToEnd = var1;
      this.mLast = State.Constraint.CENTER_HORIZONTALLY;
      this.mHorizontalBias = 0.5F;
      return this;
   }

   public ConstraintReference centerVertically(Object var1) {
      var1 = this.get(var1);
      this.mTopToTop = var1;
      this.mBottomToBottom = var1;
      this.mLast = State.Constraint.CENTER_VERTICALLY;
      this.mVerticalBias = 0.5F;
      return this;
   }

   public ConstraintReference clear() {
      if (this.mLast != null) {
         switch(null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[this.mLast.ordinal()]) {
         case 1:
         case 2:
            this.mLeftToLeft = null;
            this.mLeftToRight = null;
            this.mMarginLeft = 0;
            this.mMarginLeftGone = 0;
            break;
         case 3:
         case 4:
            this.mRightToLeft = null;
            this.mRightToRight = null;
            this.mMarginRight = 0;
            this.mMarginRightGone = 0;
            break;
         case 5:
         case 6:
            this.mStartToStart = null;
            this.mStartToEnd = null;
            this.mMarginStart = 0;
            this.mMarginStartGone = 0;
            break;
         case 7:
         case 8:
            this.mEndToStart = null;
            this.mEndToEnd = null;
            this.mMarginEnd = 0;
            this.mMarginEndGone = 0;
            break;
         case 9:
         case 10:
            this.mTopToTop = null;
            this.mTopToBottom = null;
            this.mMarginTop = 0;
            this.mMarginTopGone = 0;
            break;
         case 11:
         case 12:
            this.mBottomToTop = null;
            this.mBottomToBottom = null;
            this.mMarginBottom = 0;
            this.mMarginBottomGone = 0;
            break;
         case 13:
            this.mBaselineToBaseline = null;
         }
      } else {
         this.mLeftToLeft = null;
         this.mLeftToRight = null;
         this.mMarginLeft = 0;
         this.mRightToLeft = null;
         this.mRightToRight = null;
         this.mMarginRight = 0;
         this.mStartToStart = null;
         this.mStartToEnd = null;
         this.mMarginStart = 0;
         this.mEndToStart = null;
         this.mEndToEnd = null;
         this.mMarginEnd = 0;
         this.mTopToTop = null;
         this.mTopToBottom = null;
         this.mMarginTop = 0;
         this.mBottomToTop = null;
         this.mBottomToBottom = null;
         this.mMarginBottom = 0;
         this.mBaselineToBaseline = null;
         this.mHorizontalBias = 0.5F;
         this.mVerticalBias = 0.5F;
         this.mMarginLeftGone = 0;
         this.mMarginRightGone = 0;
         this.mMarginStartGone = 0;
         this.mMarginEndGone = 0;
         this.mMarginTopGone = 0;
         this.mMarginBottomGone = 0;
      }

      return this;
   }

   public ConstraintReference clearHorizontal() {
      this.start().clear();
      this.end().clear();
      this.left().clear();
      this.right().clear();
      return this;
   }

   public ConstraintReference clearVertical() {
      this.top().clear();
      this.baseline().clear();
      this.bottom().clear();
      return this;
   }

   public ConstraintWidget createConstraintWidget() {
      return new ConstraintWidget(this.getWidth().getValue(), this.getHeight().getValue());
   }

   public ConstraintReference end() {
      if (this.mEndToStart != null) {
         this.mLast = State.Constraint.END_TO_START;
      } else {
         this.mLast = State.Constraint.END_TO_END;
      }

      return this;
   }

   public ConstraintReference endToEnd(Object var1) {
      this.mLast = State.Constraint.END_TO_END;
      this.mEndToEnd = var1;
      return this;
   }

   public ConstraintReference endToStart(Object var1) {
      this.mLast = State.Constraint.END_TO_START;
      this.mEndToStart = var1;
      return this;
   }

   public ConstraintWidget getConstraintWidget() {
      if (this.mConstraintWidget == null) {
         ConstraintWidget var1 = this.createConstraintWidget();
         this.mConstraintWidget = var1;
         var1.setCompanionWidget(this.mView);
      }

      return this.mConstraintWidget;
   }

   public Dimension getHeight() {
      return this.mVerticalDimension;
   }

   public int getHorizontalChainStyle() {
      return this.mHorizontalChainStyle;
   }

   public Object getKey() {
      return this.key;
   }

   public int getVerticalChainStyle(int var1) {
      return this.mVerticalChainStyle;
   }

   public Object getView() {
      return this.mView;
   }

   public Dimension getWidth() {
      return this.mHorizontalDimension;
   }

   public ConstraintReference height(Dimension var1) {
      return this.setHeight(var1);
   }

   public ConstraintReference horizontalBias(float var1) {
      this.mHorizontalBias = var1;
      return this;
   }

   public ConstraintReference left() {
      if (this.mLeftToLeft != null) {
         this.mLast = State.Constraint.LEFT_TO_LEFT;
      } else {
         this.mLast = State.Constraint.LEFT_TO_RIGHT;
      }

      return this;
   }

   public ConstraintReference leftToLeft(Object var1) {
      this.mLast = State.Constraint.LEFT_TO_LEFT;
      this.mLeftToLeft = var1;
      return this;
   }

   public ConstraintReference leftToRight(Object var1) {
      this.mLast = State.Constraint.LEFT_TO_RIGHT;
      this.mLeftToRight = var1;
      return this;
   }

   public ConstraintReference margin(int var1) {
      if (this.mLast != null) {
         switch(null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[this.mLast.ordinal()]) {
         case 1:
         case 2:
            this.mMarginLeft = var1;
            break;
         case 3:
         case 4:
            this.mMarginRight = var1;
            break;
         case 5:
         case 6:
            this.mMarginStart = var1;
            break;
         case 7:
         case 8:
            this.mMarginEnd = var1;
            break;
         case 9:
         case 10:
            this.mMarginTop = var1;
            break;
         case 11:
         case 12:
            this.mMarginBottom = var1;
         }
      } else {
         this.mMarginLeft = var1;
         this.mMarginRight = var1;
         this.mMarginStart = var1;
         this.mMarginEnd = var1;
         this.mMarginTop = var1;
         this.mMarginBottom = var1;
      }

      return this;
   }

   public ConstraintReference margin(Object var1) {
      return this.margin(this.mState.convertDimension(var1));
   }

   public ConstraintReference marginGone(int var1) {
      if (this.mLast != null) {
         switch(null.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[this.mLast.ordinal()]) {
         case 1:
         case 2:
            this.mMarginLeftGone = var1;
            break;
         case 3:
         case 4:
            this.mMarginRightGone = var1;
            break;
         case 5:
         case 6:
            this.mMarginStartGone = var1;
            break;
         case 7:
         case 8:
            this.mMarginEndGone = var1;
            break;
         case 9:
         case 10:
            this.mMarginTopGone = var1;
            break;
         case 11:
         case 12:
            this.mMarginBottomGone = var1;
         }
      } else {
         this.mMarginLeftGone = var1;
         this.mMarginRightGone = var1;
         this.mMarginStartGone = var1;
         this.mMarginEndGone = var1;
         this.mMarginTopGone = var1;
         this.mMarginBottomGone = var1;
      }

      return this;
   }

   public ConstraintReference right() {
      if (this.mRightToLeft != null) {
         this.mLast = State.Constraint.RIGHT_TO_LEFT;
      } else {
         this.mLast = State.Constraint.RIGHT_TO_RIGHT;
      }

      return this;
   }

   public ConstraintReference rightToLeft(Object var1) {
      this.mLast = State.Constraint.RIGHT_TO_LEFT;
      this.mRightToLeft = var1;
      return this;
   }

   public ConstraintReference rightToRight(Object var1) {
      this.mLast = State.Constraint.RIGHT_TO_RIGHT;
      this.mRightToRight = var1;
      return this;
   }

   public void setConstraintWidget(ConstraintWidget var1) {
      if (var1 != null) {
         this.mConstraintWidget = var1;
         var1.setCompanionWidget(this.mView);
      }
   }

   public ConstraintReference setHeight(Dimension var1) {
      this.mVerticalDimension = var1;
      return this;
   }

   public void setHorizontalChainStyle(int var1) {
      this.mHorizontalChainStyle = var1;
   }

   public void setKey(Object var1) {
      this.key = var1;
   }

   public void setVerticalChainStyle(int var1) {
      this.mVerticalChainStyle = var1;
   }

   public void setView(Object var1) {
      this.mView = var1;
      ConstraintWidget var2 = this.mConstraintWidget;
      if (var2 != null) {
         var2.setCompanionWidget(var1);
      }

   }

   public ConstraintReference setWidth(Dimension var1) {
      this.mHorizontalDimension = var1;
      return this;
   }

   public ConstraintReference start() {
      if (this.mStartToStart != null) {
         this.mLast = State.Constraint.START_TO_START;
      } else {
         this.mLast = State.Constraint.START_TO_END;
      }

      return this;
   }

   public ConstraintReference startToEnd(Object var1) {
      this.mLast = State.Constraint.START_TO_END;
      this.mStartToEnd = var1;
      return this;
   }

   public ConstraintReference startToStart(Object var1) {
      this.mLast = State.Constraint.START_TO_START;
      this.mStartToStart = var1;
      return this;
   }

   public ConstraintReference top() {
      if (this.mTopToTop != null) {
         this.mLast = State.Constraint.TOP_TO_TOP;
      } else {
         this.mLast = State.Constraint.TOP_TO_BOTTOM;
      }

      return this;
   }

   public ConstraintReference topToBottom(Object var1) {
      this.mLast = State.Constraint.TOP_TO_BOTTOM;
      this.mTopToBottom = var1;
      return this;
   }

   public ConstraintReference topToTop(Object var1) {
      this.mLast = State.Constraint.TOP_TO_TOP;
      this.mTopToTop = var1;
      return this;
   }

   public void validate() throws ConstraintReference.IncorrectConstraintException {
      ArrayList var1 = new ArrayList();
      if (this.mLeftToLeft != null && this.mLeftToRight != null) {
         var1.add("LeftToLeft and LeftToRight both defined");
      }

      if (this.mRightToLeft != null && this.mRightToRight != null) {
         var1.add("RightToLeft and RightToRight both defined");
      }

      if (this.mStartToStart != null && this.mStartToEnd != null) {
         var1.add("StartToStart and StartToEnd both defined");
      }

      if (this.mEndToStart != null && this.mEndToEnd != null) {
         var1.add("EndToStart and EndToEnd both defined");
      }

      if ((this.mLeftToLeft != null || this.mLeftToRight != null || this.mRightToLeft != null || this.mRightToRight != null) && (this.mStartToStart != null || this.mStartToEnd != null || this.mEndToStart != null || this.mEndToEnd != null)) {
         var1.add("Both left/right and start/end constraints defined");
      }

      if (var1.size() > 0) {
         throw new ConstraintReference.IncorrectConstraintException(var1);
      }
   }

   public ConstraintReference verticalBias(float var1) {
      this.mVerticalBias = var1;
      return this;
   }

   public ConstraintReference width(Dimension var1) {
      return this.setWidth(var1);
   }

   public interface ConstraintReferenceFactory {
      ConstraintReference create(State var1);
   }

   class IncorrectConstraintException extends Exception {
      private final ArrayList<String> mErrors;

      public IncorrectConstraintException(ArrayList<String> var2) {
         this.mErrors = var2;
      }

      public ArrayList<String> getErrors() {
         return this.mErrors;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("IncorrectConstraintException: ");
         var1.append(this.mErrors.toString());
         return var1.toString();
      }
   }
}
