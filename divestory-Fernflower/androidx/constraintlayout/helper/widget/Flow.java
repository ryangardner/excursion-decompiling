package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View.MeasureSpec;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.VirtualLayout;

public class Flow extends VirtualLayout {
   public static final int CHAIN_PACKED = 2;
   public static final int CHAIN_SPREAD = 0;
   public static final int CHAIN_SPREAD_INSIDE = 1;
   public static final int HORIZONTAL = 0;
   public static final int HORIZONTAL_ALIGN_CENTER = 2;
   public static final int HORIZONTAL_ALIGN_END = 1;
   public static final int HORIZONTAL_ALIGN_START = 0;
   private static final String TAG = "Flow";
   public static final int VERTICAL = 1;
   public static final int VERTICAL_ALIGN_BASELINE = 3;
   public static final int VERTICAL_ALIGN_BOTTOM = 1;
   public static final int VERTICAL_ALIGN_CENTER = 2;
   public static final int VERTICAL_ALIGN_TOP = 0;
   public static final int WRAP_ALIGNED = 2;
   public static final int WRAP_CHAIN = 1;
   public static final int WRAP_NONE = 0;
   private androidx.constraintlayout.solver.widgets.Flow mFlow;

   public Flow(Context var1) {
      super(var1);
   }

   public Flow(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public Flow(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      this.mFlow = new androidx.constraintlayout.solver.widgets.Flow();
      if (var1 != null) {
         TypedArray var5 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout);
         int var2 = var5.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var5.getIndex(var3);
            if (var4 == R.styleable.ConstraintLayout_Layout_android_orientation) {
               this.mFlow.setOrientation(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_padding) {
               this.mFlow.setPadding(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingStart) {
               this.mFlow.setPaddingStart(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingEnd) {
               this.mFlow.setPaddingEnd(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingLeft) {
               this.mFlow.setPaddingLeft(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingTop) {
               this.mFlow.setPaddingTop(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingRight) {
               this.mFlow.setPaddingRight(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_paddingBottom) {
               this.mFlow.setPaddingBottom(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_wrapMode) {
               this.mFlow.setWrapMode(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_horizontalStyle) {
               this.mFlow.setHorizontalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_verticalStyle) {
               this.mFlow.setVerticalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalStyle) {
               this.mFlow.setFirstHorizontalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalStyle) {
               this.mFlow.setLastHorizontalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_firstVerticalStyle) {
               this.mFlow.setFirstVerticalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_lastVerticalStyle) {
               this.mFlow.setLastVerticalStyle(var5.getInt(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_horizontalBias) {
               this.mFlow.setHorizontalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalBias) {
               this.mFlow.setFirstHorizontalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalBias) {
               this.mFlow.setLastHorizontalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_firstVerticalBias) {
               this.mFlow.setFirstVerticalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_lastVerticalBias) {
               this.mFlow.setLastVerticalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_verticalBias) {
               this.mFlow.setVerticalBias(var5.getFloat(var4, 0.5F));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_horizontalAlign) {
               this.mFlow.setHorizontalAlign(var5.getInt(var4, 2));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_verticalAlign) {
               this.mFlow.setVerticalAlign(var5.getInt(var4, 2));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_horizontalGap) {
               this.mFlow.setHorizontalGap(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_verticalGap) {
               this.mFlow.setVerticalGap(var5.getDimensionPixelSize(var4, 0));
            } else if (var4 == R.styleable.ConstraintLayout_Layout_flow_maxElementsWrap) {
               this.mFlow.setMaxElementsWrap(var5.getInt(var4, -1));
            }
         }
      }

      this.mHelperWidget = this.mFlow;
      this.validateParams();
   }

   public void loadParameters(ConstraintSet.Constraint var1, HelperWidget var2, ConstraintLayout.LayoutParams var3, SparseArray<ConstraintWidget> var4) {
      super.loadParameters(var1, var2, var3, var4);
      if (var2 instanceof androidx.constraintlayout.solver.widgets.Flow) {
         androidx.constraintlayout.solver.widgets.Flow var5 = (androidx.constraintlayout.solver.widgets.Flow)var2;
         if (var3.orientation != -1) {
            var5.setOrientation(var3.orientation);
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      this.onMeasure(this.mFlow, var1, var2);
   }

   public void onMeasure(androidx.constraintlayout.solver.widgets.VirtualLayout var1, int var2, int var3) {
      int var4 = MeasureSpec.getMode(var2);
      var2 = MeasureSpec.getSize(var2);
      int var5 = MeasureSpec.getMode(var3);
      var3 = MeasureSpec.getSize(var3);
      if (var1 != null) {
         var1.measure(var4, var2, var5, var3);
         this.setMeasuredDimension(var1.getMeasuredWidth(), var1.getMeasuredHeight());
      } else {
         this.setMeasuredDimension(0, 0);
      }

   }

   public void resolveRtl(ConstraintWidget var1, boolean var2) {
      this.mFlow.applyRtl(var2);
   }

   public void setFirstHorizontalBias(float var1) {
      this.mFlow.setFirstHorizontalBias(var1);
      this.requestLayout();
   }

   public void setFirstHorizontalStyle(int var1) {
      this.mFlow.setFirstHorizontalStyle(var1);
      this.requestLayout();
   }

   public void setFirstVerticalBias(float var1) {
      this.mFlow.setFirstVerticalBias(var1);
      this.requestLayout();
   }

   public void setFirstVerticalStyle(int var1) {
      this.mFlow.setFirstVerticalStyle(var1);
      this.requestLayout();
   }

   public void setHorizontalAlign(int var1) {
      this.mFlow.setHorizontalAlign(var1);
      this.requestLayout();
   }

   public void setHorizontalBias(float var1) {
      this.mFlow.setHorizontalBias(var1);
      this.requestLayout();
   }

   public void setHorizontalGap(int var1) {
      this.mFlow.setHorizontalGap(var1);
      this.requestLayout();
   }

   public void setHorizontalStyle(int var1) {
      this.mFlow.setHorizontalStyle(var1);
      this.requestLayout();
   }

   public void setMaxElementsWrap(int var1) {
      this.mFlow.setMaxElementsWrap(var1);
      this.requestLayout();
   }

   public void setOrientation(int var1) {
      this.mFlow.setOrientation(var1);
      this.requestLayout();
   }

   public void setPadding(int var1) {
      this.mFlow.setPadding(var1);
      this.requestLayout();
   }

   public void setPaddingBottom(int var1) {
      this.mFlow.setPaddingBottom(var1);
      this.requestLayout();
   }

   public void setPaddingLeft(int var1) {
      this.mFlow.setPaddingLeft(var1);
      this.requestLayout();
   }

   public void setPaddingRight(int var1) {
      this.mFlow.setPaddingRight(var1);
      this.requestLayout();
   }

   public void setPaddingTop(int var1) {
      this.mFlow.setPaddingTop(var1);
      this.requestLayout();
   }

   public void setVerticalAlign(int var1) {
      this.mFlow.setVerticalAlign(var1);
      this.requestLayout();
   }

   public void setVerticalBias(float var1) {
      this.mFlow.setVerticalBias(var1);
      this.requestLayout();
   }

   public void setVerticalGap(int var1) {
      this.mFlow.setVerticalGap(var1);
      this.requestLayout();
   }

   public void setVerticalStyle(int var1) {
      this.mFlow.setVerticalStyle(var1);
      this.requestLayout();
   }

   public void setWrapMode(int var1) {
      this.mFlow.setWrapMode(var1);
      this.requestLayout();
   }
}
