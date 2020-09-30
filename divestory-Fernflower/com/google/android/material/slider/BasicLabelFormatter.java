package com.google.android.material.slider;

import java.util.Locale;

public final class BasicLabelFormatter implements LabelFormatter {
   private static final int BILLION = 1000000000;
   private static final int MILLION = 1000000;
   private static final int THOUSAND = 1000;
   private static final long TRILLION = 1000000000000L;

   public String getFormattedValue(float var1) {
      if (var1 >= 1.0E12F) {
         return String.format(Locale.US, "%.1fT", var1 / 1.0E12F);
      } else if (var1 >= 1.0E9F) {
         return String.format(Locale.US, "%.1fB", var1 / 1.0E9F);
      } else if (var1 >= 1000000.0F) {
         return String.format(Locale.US, "%.1fM", var1 / 1000000.0F);
      } else {
         return var1 >= 1000.0F ? String.format(Locale.US, "%.1fK", var1 / 1000.0F) : String.format(Locale.US, "%.0f", var1);
      }
   }
}
