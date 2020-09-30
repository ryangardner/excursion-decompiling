package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;

public class Group extends ConstraintHelper {
   public Group(Context var1) {
      super(var1);
   }

   public Group(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public Group(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      this.mUseViewMeasure = false;
   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.applyLayoutFeatures();
   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      this.applyLayoutFeatures();
   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      this.applyLayoutFeatures();
   }

   public void updatePostLayout(ConstraintLayout var1) {
      ConstraintLayout.LayoutParams var2 = (ConstraintLayout.LayoutParams)this.getLayoutParams();
      var2.widget.setWidth(0);
      var2.widget.setHeight(0);
   }
}
