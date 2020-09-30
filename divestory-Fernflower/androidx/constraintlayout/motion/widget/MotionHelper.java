package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

public class MotionHelper extends ConstraintHelper implements Animatable, MotionLayout.TransitionListener {
   private float mProgress;
   private boolean mUseOnHide = false;
   private boolean mUseOnShow = false;
   protected View[] views;

   public MotionHelper(Context var1) {
      super(var1);
   }

   public MotionHelper(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var2);
   }

   public MotionHelper(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var2);
   }

   public float getProgress() {
      return this.mProgress;
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      if (var1 != null) {
         TypedArray var5 = this.getContext().obtainStyledAttributes(var1, R.styleable.MotionHelper);
         int var2 = var5.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var5.getIndex(var3);
            if (var4 == R.styleable.MotionHelper_onShow) {
               this.mUseOnShow = var5.getBoolean(var4, this.mUseOnShow);
            } else if (var4 == R.styleable.MotionHelper_onHide) {
               this.mUseOnHide = var5.getBoolean(var4, this.mUseOnHide);
            }
         }
      }

   }

   public boolean isUseOnHide() {
      return this.mUseOnHide;
   }

   public boolean isUsedOnShow() {
      return this.mUseOnShow;
   }

   public void onTransitionChange(MotionLayout var1, int var2, int var3, float var4) {
   }

   public void onTransitionCompleted(MotionLayout var1, int var2) {
   }

   public void onTransitionStarted(MotionLayout var1, int var2, int var3) {
   }

   public void onTransitionTrigger(MotionLayout var1, int var2, boolean var3, float var4) {
   }

   public void setProgress(float var1) {
      this.mProgress = var1;
      int var2 = this.mCount;
      int var3 = 0;
      byte var4 = 0;
      if (var2 > 0) {
         this.views = this.getViews((ConstraintLayout)this.getParent());

         for(var3 = var4; var3 < this.mCount; ++var3) {
            this.setProgress(this.views[var3], var1);
         }
      } else {
         ViewGroup var5 = (ViewGroup)this.getParent();

         for(int var7 = var5.getChildCount(); var3 < var7; ++var3) {
            View var6 = var5.getChildAt(var3);
            if (!(var6 instanceof MotionHelper)) {
               this.setProgress(var6, var1);
            }
         }
      }

   }

   public void setProgress(View var1, float var2) {
   }
}
