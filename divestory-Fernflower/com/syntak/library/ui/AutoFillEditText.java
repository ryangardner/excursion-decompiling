package com.syntak.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class AutoFillEditText extends AppCompatAutoCompleteTextView {
   int mThreshold = 0;

   public AutoFillEditText(Context var1) {
      super(var1);
   }

   public AutoFillEditText(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public AutoFillEditText(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean enoughToFilter() {
      boolean var1;
      if (this.getText().length() >= this.mThreshold) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int getThreshold() {
      return this.mThreshold;
   }

   public void setThreshold(int var1) {
      if (var1 >= 0) {
         this.mThreshold = var1;
      }

   }
}
