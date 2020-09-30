package io.opencensus.tags;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;

public abstract class TagValue {
   public static final int MAX_LENGTH = 255;

   TagValue() {
   }

   public static TagValue create(String var0) {
      Utils.checkArgument(isValid(var0), "Invalid TagValue: %s", var0);
      return new AutoValue_TagValue(var0);
   }

   private static boolean isValid(String var0) {
      boolean var1;
      if (var0.length() <= 255 && StringUtils.isPrintableString(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract String asString();
}
