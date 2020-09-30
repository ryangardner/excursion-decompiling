package io.opencensus.tags;

import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;

public abstract class TagKey {
   public static final int MAX_LENGTH = 255;

   TagKey() {
   }

   public static TagKey create(String var0) {
      Utils.checkArgument(isValid(var0), "Invalid TagKey name: %s", var0);
      return new AutoValue_TagKey(var0);
   }

   private static boolean isValid(String var0) {
      boolean var1;
      if (!var0.isEmpty() && var0.length() <= 255 && StringUtils.isPrintableString(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract String getName();
}
