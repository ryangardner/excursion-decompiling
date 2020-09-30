package com.google.android.material.slider;

public interface LabelFormatter {
   int LABEL_FLOATING = 0;
   int LABEL_GONE = 2;
   int LABEL_WITHIN_BOUNDS = 1;

   String getFormattedValue(float var1);
}
