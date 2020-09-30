package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;

public abstract class Measure {
   private static final String ERROR_MESSAGE_INVALID_NAME = "Name should be a ASCII string with a length no greater than 255 characters.";
   static final int NAME_MAX_LENGTH = 255;

   private Measure() {
   }

   // $FF: synthetic method
   Measure(Object var1) {
      this();
   }

   public abstract String getDescription();

   public abstract String getName();

   public abstract String getUnit();

   public abstract <T> T match(Function<? super Measure.MeasureDouble, T> var1, Function<? super Measure.MeasureLong, T> var2, Function<? super Measure, T> var3);

   public abstract static class MeasureDouble extends Measure {
      MeasureDouble() {
         super(null);
      }

      public static Measure.MeasureDouble create(String var0, String var1, String var2) {
         boolean var3;
         if (StringUtils.isPrintableString(var0) && var0.length() <= 255) {
            var3 = true;
         } else {
            var3 = false;
         }

         Utils.checkArgument(var3, "Name should be a ASCII string with a length no greater than 255 characters.");
         return new AutoValue_Measure_MeasureDouble(var0, var1, var2);
      }

      public abstract String getDescription();

      public abstract String getName();

      public abstract String getUnit();

      public <T> T match(Function<? super Measure.MeasureDouble, T> var1, Function<? super Measure.MeasureLong, T> var2, Function<? super Measure, T> var3) {
         return var1.apply(this);
      }
   }

   public abstract static class MeasureLong extends Measure {
      MeasureLong() {
         super(null);
      }

      public static Measure.MeasureLong create(String var0, String var1, String var2) {
         boolean var3;
         if (StringUtils.isPrintableString(var0) && var0.length() <= 255) {
            var3 = true;
         } else {
            var3 = false;
         }

         Utils.checkArgument(var3, "Name should be a ASCII string with a length no greater than 255 characters.");
         return new AutoValue_Measure_MeasureLong(var0, var1, var2);
      }

      public abstract String getDescription();

      public abstract String getName();

      public abstract String getUnit();

      public <T> T match(Function<? super Measure.MeasureDouble, T> var1, Function<? super Measure.MeasureLong, T> var2, Function<? super Measure, T> var3) {
         return var2.apply(this);
      }
   }
}
