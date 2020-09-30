package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

public abstract class VirtualLayout extends ConstraintHelper {
   private boolean mApplyElevationOnAttach;
   private boolean mApplyVisibilityOnAttach;

   public VirtualLayout(Context var1) {
      super(var1);
   }

   public VirtualLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public VirtualLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      if (var1 != null) {
         TypedArray var5 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout);
         int var2 = var5.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var5.getIndex(var3);
            if (var4 == R.styleable.ConstraintLayout_Layout_android_visibility) {
               this.mApplyVisibilityOnAttach = true;
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_elevation) {
               this.mApplyElevationOnAttach = true;
            }
         }
      }

   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
         ViewParent var1 = this.getParent();
         if (var1 != null && var1 instanceof ConstraintLayout) {
            ConstraintLayout var2 = (ConstraintLayout)var1;
            int var3 = this.getVisibility();
            float var4;
            if (VERSION.SDK_INT >= 21) {
               var4 = this.getElevation();
            } else {
               var4 = 0.0F;
            }

            for(int var5 = 0; var5 < this.mCount; ++var5) {
               View var6 = var2.getViewById(this.mIds[var5]);
               if (var6 != null) {
                  if (this.mApplyVisibilityOnAttach) {
                     var6.setVisibility(var3);
                  }

                  if (this.mApplyElevationOnAttach && var4 > 0.0F && VERSION.SDK_INT >= 21) {
                     var6.setTranslationZ(var6.getTranslationZ() + var4);
                  }
               }
            }
         }
      }

   }

   public void onMeasure(androidx.constraintlayout.solver.widgets.VirtualLayout var1, int var2, int var3) {
   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      this.applyLayoutFeatures();
   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      this.applyLayoutFeatures();
   }
}
