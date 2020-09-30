package com.google.android.material.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R;
import java.util.ArrayList;
import java.util.List;

public class RangeSlider extends BaseSlider<RangeSlider, RangeSlider.OnChangeListener, RangeSlider.OnSliderTouchListener> {
   public RangeSlider(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public RangeSlider(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.sliderStyle);
   }

   public RangeSlider(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      TypedArray var4 = var1.obtainStyledAttributes(var2, new int[]{R.attr.values});
      if (var4.hasValue(0)) {
         var3 = var4.getResourceId(0, 0);
         this.setValues(convertToFloat(var4.getResources().obtainTypedArray(var3)));
      }

      var4.recycle();
   }

   private static List<Float> convertToFloat(TypedArray var0) {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         var1.add(var0.getFloat(var2, -1.0F));
      }

      return var1;
   }

   public List<Float> getValues() {
      return super.getValues();
   }

   public void setValues(List<Float> var1) {
      super.setValues(var1);
   }

   public void setValues(Float... var1) {
      super.setValues(var1);
   }

   public interface OnChangeListener extends BaseOnChangeListener<RangeSlider> {
   }

   public interface OnSliderTouchListener extends BaseOnSliderTouchListener<RangeSlider> {
   }
}
