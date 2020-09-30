package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;

public class Barrier extends ConstraintHelper {
   public static final int BOTTOM = 3;
   public static final int END = 6;
   public static final int LEFT = 0;
   public static final int RIGHT = 1;
   public static final int START = 5;
   public static final int TOP = 2;
   private androidx.constraintlayout.solver.widgets.Barrier mBarrier;
   private int mIndicatedType;
   private int mResolvedType;

   public Barrier(Context var1) {
      super(var1);
      super.setVisibility(8);
   }

   public Barrier(Context var1, AttributeSet var2) {
      super(var1, var2);
      super.setVisibility(8);
   }

   public Barrier(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      super.setVisibility(8);
   }

   private void updateType(ConstraintWidget var1, int var2, boolean var3) {
      this.mResolvedType = var2;
      if (VERSION.SDK_INT < 17) {
         var2 = this.mIndicatedType;
         if (var2 == 5) {
            this.mResolvedType = 0;
         } else if (var2 == 6) {
            this.mResolvedType = 1;
         }
      } else if (var3) {
         var2 = this.mIndicatedType;
         if (var2 == 5) {
            this.mResolvedType = 1;
         } else if (var2 == 6) {
            this.mResolvedType = 0;
         }
      } else {
         var2 = this.mIndicatedType;
         if (var2 == 5) {
            this.mResolvedType = 0;
         } else if (var2 == 6) {
            this.mResolvedType = 1;
         }
      }

      if (var1 instanceof androidx.constraintlayout.solver.widgets.Barrier) {
         ((androidx.constraintlayout.solver.widgets.Barrier)var1).setBarrierType(this.mResolvedType);
      }

   }

   public boolean allowsGoneWidget() {
      return this.mBarrier.allowsGoneWidget();
   }

   public int getMargin() {
      return this.mBarrier.getMargin();
   }

   public int getType() {
      return this.mIndicatedType;
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      this.mBarrier = new androidx.constraintlayout.solver.widgets.Barrier();
      if (var1 != null) {
         TypedArray var5 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout);
         int var2 = var5.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var5.getIndex(var3);
            if (var4 == R.styleable.ConstraintLayout_Layout_barrierDirection) {
               this.setType(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
               this.mBarrier.setAllowsGoneWidget(var5.getBoolean(var4, true));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_barrierMargin) {
               var4 = var5.getDimensionPixelSize(var4, 0);
               this.mBarrier.setMargin(var4);
            }
         }
      }

      this.mHelperWidget = this.mBarrier;
      this.validateParams();
   }

   public void loadParameters(ConstraintSet.Constraint var1, HelperWidget var2, ConstraintLayout.LayoutParams var3, SparseArray<ConstraintWidget> var4) {
      super.loadParameters(var1, var2, var3, var4);
      if (var2 instanceof androidx.constraintlayout.solver.widgets.Barrier) {
         androidx.constraintlayout.solver.widgets.Barrier var6 = (androidx.constraintlayout.solver.widgets.Barrier)var2;
         boolean var5 = ((ConstraintWidgetContainer)var2.getParent()).isRtl();
         this.updateType(var6, var1.layout.mBarrierDirection, var5);
         var6.setAllowsGoneWidget(var1.layout.mBarrierAllowsGoneWidgets);
         var6.setMargin(var1.layout.mBarrierMargin);
      }

   }

   public void resolveRtl(ConstraintWidget var1, boolean var2) {
      this.updateType(var1, this.mIndicatedType, var2);
   }

   public void setAllowsGoneWidget(boolean var1) {
      this.mBarrier.setAllowsGoneWidget(var1);
   }

   public void setDpMargin(int var1) {
      float var2 = this.getResources().getDisplayMetrics().density;
      var1 = (int)((float)var1 * var2 + 0.5F);
      this.mBarrier.setMargin(var1);
   }

   public void setMargin(int var1) {
      this.mBarrier.setMargin(var1);
   }

   public void setType(int var1) {
      this.mIndicatedType = var1;
   }
}
