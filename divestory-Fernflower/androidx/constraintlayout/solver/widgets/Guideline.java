package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.HashMap;

public class Guideline extends ConstraintWidget {
   public static final int HORIZONTAL = 0;
   public static final int RELATIVE_BEGIN = 1;
   public static final int RELATIVE_END = 2;
   public static final int RELATIVE_PERCENT = 0;
   public static final int RELATIVE_UNKNWON = -1;
   public static final int VERTICAL = 1;
   private ConstraintAnchor mAnchor;
   private int mMinimumPosition;
   private int mOrientation;
   protected int mRelativeBegin = -1;
   protected int mRelativeEnd = -1;
   protected float mRelativePercent = -1.0F;

   public Guideline() {
      this.mAnchor = this.mTop;
      int var1 = 0;
      this.mOrientation = 0;
      this.mMinimumPosition = 0;
      this.mAnchors.clear();
      this.mAnchors.add(this.mAnchor);

      for(int var2 = this.mListAnchors.length; var1 < var2; ++var1) {
         this.mListAnchors[var1] = this.mAnchor;
      }

   }

   public void addToSolver(LinearSystem var1) {
      ConstraintWidgetContainer var2 = (ConstraintWidgetContainer)this.getParent();
      if (var2 != null) {
         ConstraintAnchor var3 = var2.getAnchor(ConstraintAnchor.Type.LEFT);
         ConstraintAnchor var4 = var2.getAnchor(ConstraintAnchor.Type.RIGHT);
         ConstraintWidget var5 = this.mParent;
         boolean var6 = true;
         boolean var7;
         if (var5 != null && this.mParent.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (this.mOrientation == 0) {
            var3 = var2.getAnchor(ConstraintAnchor.Type.TOP);
            var4 = var2.getAnchor(ConstraintAnchor.Type.BOTTOM);
            if (this.mParent != null && this.mParent.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var7 = var6;
            } else {
               var7 = false;
            }
         }

         SolverVariable var8;
         if (this.mRelativeBegin != -1) {
            var8 = var1.createObjectVariable(this.mAnchor);
            var1.addEquality(var8, var1.createObjectVariable(var3), this.mRelativeBegin, 8);
            if (var7) {
               var1.addGreaterThan(var1.createObjectVariable(var4), var8, 0, 5);
            }
         } else if (this.mRelativeEnd != -1) {
            var8 = var1.createObjectVariable(this.mAnchor);
            SolverVariable var9 = var1.createObjectVariable(var4);
            var1.addEquality(var8, var9, -this.mRelativeEnd, 8);
            if (var7) {
               var1.addGreaterThan(var8, var1.createObjectVariable(var3), 0, 5);
               var1.addGreaterThan(var9, var8, 0, 5);
            }
         } else if (this.mRelativePercent != -1.0F) {
            var1.addConstraint(LinearSystem.createRowDimensionPercent(var1, var1.createObjectVariable(this.mAnchor), var1.createObjectVariable(var4), this.mRelativePercent));
         }

      }
   }

   public boolean allowedInBarrier() {
      return true;
   }

   public void copy(ConstraintWidget var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      super.copy(var1, var2);
      Guideline var3 = (Guideline)var1;
      this.mRelativePercent = var3.mRelativePercent;
      this.mRelativeBegin = var3.mRelativeBegin;
      this.mRelativeEnd = var3.mRelativeEnd;
      this.setOrientation(var3.mOrientation);
   }

   public void cyclePosition() {
      if (this.mRelativeBegin != -1) {
         this.inferRelativePercentPosition();
      } else if (this.mRelativePercent != -1.0F) {
         this.inferRelativeEndPosition();
      } else if (this.mRelativeEnd != -1) {
         this.inferRelativeBeginPosition();
      }

   }

   public ConstraintAnchor getAnchor() {
      return this.mAnchor;
   }

   public ConstraintAnchor getAnchor(ConstraintAnchor.Type var1) {
      switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[var1.ordinal()]) {
      case 1:
      case 2:
         if (this.mOrientation == 1) {
            return this.mAnchor;
         }
         break;
      case 3:
      case 4:
         if (this.mOrientation == 0) {
            return this.mAnchor;
         }
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
         return null;
      }

      throw new AssertionError(var1.name());
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public int getRelativeBegin() {
      return this.mRelativeBegin;
   }

   public int getRelativeBehaviour() {
      if (this.mRelativePercent != -1.0F) {
         return 0;
      } else if (this.mRelativeBegin != -1) {
         return 1;
      } else {
         return this.mRelativeEnd != -1 ? 2 : -1;
      }
   }

   public int getRelativeEnd() {
      return this.mRelativeEnd;
   }

   public float getRelativePercent() {
      return this.mRelativePercent;
   }

   public String getType() {
      return "Guideline";
   }

   void inferRelativeBeginPosition() {
      int var1 = this.getX();
      if (this.mOrientation == 0) {
         var1 = this.getY();
      }

      this.setGuideBegin(var1);
   }

   void inferRelativeEndPosition() {
      int var1 = this.getParent().getWidth() - this.getX();
      if (this.mOrientation == 0) {
         var1 = this.getParent().getHeight() - this.getY();
      }

      this.setGuideEnd(var1);
   }

   void inferRelativePercentPosition() {
      float var1 = (float)this.getX() / (float)this.getParent().getWidth();
      if (this.mOrientation == 0) {
         var1 = (float)this.getY() / (float)this.getParent().getHeight();
      }

      this.setGuidePercent(var1);
   }

   public boolean isPercent() {
      boolean var1;
      if (this.mRelativePercent != -1.0F && this.mRelativeBegin == -1 && this.mRelativeEnd == -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setGuideBegin(int var1) {
      if (var1 > -1) {
         this.mRelativePercent = -1.0F;
         this.mRelativeBegin = var1;
         this.mRelativeEnd = -1;
      }

   }

   public void setGuideEnd(int var1) {
      if (var1 > -1) {
         this.mRelativePercent = -1.0F;
         this.mRelativeBegin = -1;
         this.mRelativeEnd = var1;
      }

   }

   public void setGuidePercent(float var1) {
      if (var1 > -1.0F) {
         this.mRelativePercent = var1;
         this.mRelativeBegin = -1;
         this.mRelativeEnd = -1;
      }

   }

   public void setGuidePercent(int var1) {
      this.setGuidePercent((float)var1 / 100.0F);
   }

   public void setMinimumPosition(int var1) {
      this.mMinimumPosition = var1;
   }

   public void setOrientation(int var1) {
      if (this.mOrientation != var1) {
         this.mOrientation = var1;
         this.mAnchors.clear();
         if (this.mOrientation == 1) {
            this.mAnchor = this.mLeft;
         } else {
            this.mAnchor = this.mTop;
         }

         this.mAnchors.add(this.mAnchor);
         int var2 = this.mListAnchors.length;

         for(var1 = 0; var1 < var2; ++var1) {
            this.mListAnchors[var1] = this.mAnchor;
         }

      }
   }

   public void updateFromSolver(LinearSystem var1) {
      if (this.getParent() != null) {
         int var2 = var1.getObjectVariableValue(this.mAnchor);
         if (this.mOrientation == 1) {
            this.setX(var2);
            this.setY(0);
            this.setHeight(this.getParent().getHeight());
            this.setWidth(0);
         } else {
            this.setX(0);
            this.setY(var2);
            this.setWidth(this.getParent().getWidth());
            this.setHeight(0);
         }

      }
   }
}
