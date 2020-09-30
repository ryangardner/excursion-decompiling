package com.google.android.material.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R;

public class Slider extends BaseSlider<Slider, Slider.OnChangeListener, Slider.OnSliderTouchListener> {
   public Slider(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public Slider(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.sliderStyle);
   }

   public Slider(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      TypedArray var4 = var1.obtainStyledAttributes(var2, new int[]{16842788});
      if (var4.hasValue(0)) {
         this.setValue(var4.getFloat(0, 0.0F));
      }

      var4.recycle();
   }

   public float getValue() {
      return (Float)this.getValues().get(0);
   }

   protected boolean pickActiveThumb() {
      if (this.getActiveThumbIndex() != -1) {
         return true;
      } else {
         this.setActiveThumbIndex(0);
         return true;
      }
   }

   public void setValue(float var1) {
      this.setValues(new Float[]{var1});
   }

   public interface OnChangeListener extends BaseOnChangeListener<Slider> {
   }

   public interface OnSliderTouchListener extends BaseOnSliderTouchListener<Slider> {
   }
}
